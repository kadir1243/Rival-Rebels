/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package io.github.kadir1243.rivalrebels.common.round;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.client.gui.GuiNextBattle;
import io.github.kadir1243.rivalrebels.client.gui.GuiOmegaWin;
import io.github.kadir1243.rivalrebels.client.gui.GuiSigmaWin;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.packet.GuiSpawnPacket;
import com.mojang.datafixers.util.Function9;
import com.mojang.datafixers.util.Pair;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RivalRebelsRound extends SavedData implements CustomPacketPayload {
    private static final SavedData.Factory<RivalRebelsRound> WORLD_DATA_TYPE = new SavedData.Factory<>(RivalRebelsRound::new, RivalRebelsRound::fromNbt, null);
    public static final StreamCodec<FriendlyByteBuf, RivalRebelsRound> STREAM_CODEC = composite(
        ByteBufCodecs.BOOL,
        rivalRebelsRound -> rivalRebelsRound.roundstarted,
        BlockPos.STREAM_CODEC,
        rivalRebelsRound -> rivalRebelsRound.cSpawn,
        TeamData.STREAM_CODEC,
        rivalRebelsRound -> rivalRebelsRound.omegaData,
        TeamData.STREAM_CODEC,
        rivalRebelsRound -> rivalRebelsRound.sigmaData,
        ByteBufCodecs.INT,
        rivalRebelsRound -> rivalRebelsRound.winCountdown,
        RivalRebelsTeam.STREAM_CODEC,
        rivalRebelsRound -> rivalRebelsRound.lastWinnerTeam,
        ByteBufCodecs.BOOL,
        rivalRebelsRound -> rivalRebelsRound.fatnuke,
        ByteBufCodecs.STRING_UTF8,
        RivalRebelsRound::getMotD,
        RivalRebelsPlayerList.STREAM_CODEC,
        rivalRebelsRound -> rivalRebelsRound.rrplayerlist,
        RivalRebelsRound::new
    );
    public static final Type<RivalRebelsRound> PACKET_TYPE = new Type<>(RRIdentifiers.create("rivalrebelsrounddata"));
    public BlockPos cSpawn;
    public TeamData omegaData;
    public TeamData sigmaData;
	private String					MotD;
	public RivalRebelsPlayerList	rrplayerlist;
	public Level					world;
	private int						winCountdown;
    private RivalRebelsTeam lastWinnerTeam;
	private boolean					fatnuke;
	public int						waitVotes		= 0;
	public int						newBattleVotes	= 0;
	private int						spawnRadius 	= 20;
	private int						spawnRadius2	= 20*20;
	private int						objRadius		= 29;
	private int						objRadius2		= 29*29;
	private int						spawnDist		= 150;
	private int						objDist			= 200;
	private boolean					roundstarted;

    public RivalRebelsRound() {
        this.MotD = "Select your class and nuke the enemy team's objective to win.";
        this.cSpawn = new BlockPos(-1, -1, -1);
        this.rrplayerlist = new RivalRebelsPlayerList();
        this.lastWinnerTeam = RivalRebelsTeam.NONE;
        this.winCountdown = 0;
        this.fatnuke = false;
        this.roundstarted = false;
    }

    private RivalRebelsRound(boolean roundStarted,
                             BlockPos cSpawn,
                             TeamData omegaData,
                             TeamData sigmaData,
                             int winCountdown,
                             RivalRebelsTeam lastWinnerTeam,
                             boolean fatnuke,
                             String motD,
                             RivalRebelsPlayerList playerList
    ) {
        this.roundstarted = roundStarted;
        this.cSpawn = cSpawn;
        this.omegaData = omegaData;
        this.sigmaData = sigmaData;
        this.winCountdown = winCountdown;
        this.lastWinnerTeam = lastWinnerTeam;
        this.fatnuke = fatnuke;
        this.MotD = motD;
        this.rrplayerlist = playerList;
    }

    public void setRoundDistances(int spawnRadius, int spawnDist, int objDist) {
        this.spawnRadius = spawnRadius;
        spawnRadius2 = spawnRadius * spawnRadius;
        this.spawnDist = spawnDist;
        this.objDist = objDist;
        this.omegaData = new TeamData();
        this.sigmaData = new TeamData();
    }

    public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(
        StreamCodec<? super B, T1> codec1,
        Function<C, T1> getter1,
        StreamCodec<? super B, T2> codec2,
        Function<C, T2> getter2,
        StreamCodec<? super B, T3> codec3,
        Function<C, T3> getter3,
        StreamCodec<? super B, T4> codec4,
        Function<C, T4> getter4,
        StreamCodec<? super B, T5> codec5,
        Function<C, T5> getter5,
        StreamCodec<? super B, T6> codec6,
        Function<C, T6> getter6,
        StreamCodec<? super B, T7> codec7,
        Function<C, T7> getter7,
        StreamCodec<? super B, T8> codec8,
        Function<C, T8> getter8,
        StreamCodec<? super B, T9> codec9,
        Function<C, T9> getter9,
        Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> factory
    ) {
        return new StreamCodec<>() {
            @Override
            public C decode(B buffer) {
                T1 t1 = codec1.decode(buffer);
                T2 t2 = codec2.decode(buffer);
                T3 t3 = codec3.decode(buffer);
                T4 t4 = codec4.decode(buffer);
                T5 t5 = codec5.decode(buffer);
                T6 t6 = codec6.decode(buffer);
                T7 t7 = codec7.decode(buffer);
                T8 t8 = codec8.decode(buffer);
                T9 t9 = codec9.decode(buffer);
                return factory.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
            }

            @Override
            public void encode(B object, C object2) {
                codec1.encode(object, getter1.apply(object2));
                codec2.encode(object, getter2.apply(object2));
                codec3.encode(object, getter3.apply(object2));
                codec4.encode(object, getter4.apply(object2));
                codec5.encode(object, getter5.apply(object2));
                codec6.encode(object, getter6.apply(object2));
                codec7.encode(object, getter7.apply(object2));
                codec8.encode(object, getter8.apply(object2));
                codec9.encode(object, getter9.apply(object2));
            }
        };
    }

    public void init(IEventBus modEventBus, Dist dist) {
        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.addListener(ServerTickEvent.Post.class, event -> {
            if (roundstarted && winCountdown > 0) {
                updateServer();
            }
        });
        eventBus.addListener(PlayerEvent.PlayerRespawnEvent.class, event -> {
            if (event.getEntity().level().isClientSide()) return;
            if (!roundstarted) return;
            Player player = event.getEntity();
            BlockPos pos = cSpawn.atY(200);
            player.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
            ((ServerPlayer) player).connection.send(GuiSpawnPacket.INSTANCE);
        });
        eventBus.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> {
            if (event.getEntity().level().isClientSide()) return;
            ServerPlayer player = (ServerPlayer) event.getEntity();
            if (!roundstarted) return;
            if (!rrplayerlist.contains(player.getGameProfile())) {
                player.getInventory().clearContent();
                BlockPos pos = cSpawn.atY(200);
                player.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
                rrplayerlist.add(new RivalRebelsPlayer(player.getGameProfile(), RRConfig.SERVER.getMaximumResets()));
            }
            player.connection.send(rrplayerlist);
            if (isInSpawn(player)) player.connection.send(GuiSpawnPacket.INSTANCE);
        });
        eventBus.addListener(LevelEvent.Load.class, event -> {
            if (event.getLevel().isClientSide()) return;
            load((Level) event.getLevel());
        });
        eventBus.addListener(LevelEvent.Save.class, event -> {
            if (event.getLevel().isClientSide()) return;
            save((Level) event.getLevel());
        });

        if (dist.isClient()) {
            eventBus.addListener(ClientTickEvent.Post.class, event -> {
                if (roundstarted && winCountdown > 0) {
                    updateClient(Minecraft.getInstance());
                }
            });
            eventBus.addListener(LevelTickEvent.Post.class, event -> {
                if (event.getLevel().isClientSide()) {
                    this.updateInvisible((ClientLevel) event.getLevel());
                }
            });
        }
    }

    public static RivalRebelsRound fromNbt(CompoundTag nbt, HolderLookup.Provider registries) {
        RivalRebelsRound packet = new RivalRebelsRound();
        packet.roundstarted = nbt.getBoolean("roundstarted");
        packet.cSpawn = BlockPos.of(nbt.getLong("cSpawn"));
        packet.omegaData = TeamData.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("omega_data")).map(Pair::getFirst).getOrThrow();
        packet.sigmaData = TeamData.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("sigma_data")).map(Pair::getFirst).getOrThrow();
        packet.winCountdown = nbt.getInt("winCountdown");
        packet.lastWinnerTeam = RivalRebelsTeam.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("lastWinTeam")).map(Pair::getFirst).getOrThrow();
        packet.fatnuke = nbt.getBoolean("fatnuke");
        packet.MotD = nbt.getString("MotD");
        packet.rrplayerlist = RivalRebelsPlayerList.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("rrplayerlist")).map(Pair::getFirst).getOrThrow();
        return packet;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putBoolean("roundstarted", roundstarted);
        tag.putLong("cSpawn", cSpawn.asLong());
        tag.put("omega_data", TeamData.CODEC.encode(omegaData, NbtOps.INSTANCE, new CompoundTag()).getOrThrow());
        tag.put("sigma_data", TeamData.CODEC.encode(sigmaData, NbtOps.INSTANCE, new CompoundTag()).getOrThrow());
        tag.putInt("winCountdown", winCountdown);
        tag.put("lastWinTeam", RivalRebelsTeam.CODEC.encode(lastWinnerTeam, NbtOps.INSTANCE, new CompoundTag()).getOrThrow());
        tag.putBoolean("fatnuke", fatnuke);
        tag.putString("MotD", MotD);
        tag.put("rrplayerlist", RivalRebelsPlayerList.CODEC.encode(rrplayerlist, NbtOps.INSTANCE, new CompoundTag()).getOrThrow());
        return tag;
    }

    public static void onMessage(RivalRebelsRound m, IPayloadContext context) {
        RivalRebels.round.copy(m);
    }

	public void copy(RivalRebelsRound m) {
		roundstarted = m.roundstarted;
		cSpawn = m.cSpawn;
        omegaData = m.omegaData;
        sigmaData = m.sigmaData;
		winCountdown = m.winCountdown;
        lastWinnerTeam = m.lastWinnerTeam;
		MotD = m.MotD;
		rrplayerlist = m.rrplayerlist;
	}

	public void newRound()
	{
		fatnuke = false;
		if (!roundstarted)
		{
			startRound(0,0);
			return;
		}
		rrplayerlist = new RivalRebelsPlayerList();
        for (Player player : world.players()) {
            player.hurt(world.damageSources().fellOutOfWorld(), 20000);
            player.getInventory().clearContent();
        }
        cSpawn = new BlockPos(cSpawn.getX(), -1, cSpawn.getZ() + spawnDist);
        if (!world.isClientSide()) {
            ((ServerLevel) world).setDefaultSpawnPos(cSpawn.atY(200), 0);
        }
		float f = RRConfig.SERVER.getRhodesInRoundsChance();
		while (f >= 1)
		{
			f--;
			world.addFreshEntity(new EntityRhodes(world, cSpawn.getX()+world.random.nextDouble()-0.5f, 170, cSpawn.getZ()+world.random.nextDouble()-0.5f,1));
		}
		if (f > world.random.nextDouble()) world.addFreshEntity(new EntityRhodes(world, cSpawn.getX()+world.random.nextDouble()-0.5f, 170, cSpawn.getZ()+world.random.nextDouble()-0.5f,1));
		buildSpawn();
        omegaData.health=RRConfig.SERVER.getObjectiveHealth();
        sigmaData.health=RRConfig.SERVER.getObjectiveHealth();
        sendUpdatePacket();
	}

	public void startRound(int x, int z)
	{
		fatnuke = false;
		roundstarted = true;
        cSpawn = new BlockPos(x, -1, z - spawnDist);
		newRound();
	}

	public void roundManualStart()
	{
		if (!roundstarted || fatnuke)
		{
			fatnuke = true;
			roundstarted = true;
			rrplayerlist = new RivalRebelsPlayerList();
			cSpawn = new BlockPos((omegaData.objPos().getX()+sigmaData.objPos().getX())/2, -1, (omegaData.objPos().getZ()+sigmaData.objPos().getZ())/2);
            ((ServerLevel) world).setDefaultSpawnPos(cSpawn.atY(world.getChunk(cSpawn).getHeight()), 0F);
            omegaData.health=RRConfig.SERVER.getObjectiveHealth();
            sigmaData.health=RRConfig.SERVER.getObjectiveHealth();
			sendUpdatePacket();
		}
	}

	public static class PlayerInvisibility
	{
		public PlayerInvisibility(Player p, int i)
		{
			player = p;
			durationleft = i;
		}
		public Player player;
		public int durationleft;
	}

	private final List<PlayerInvisibility> players = new ArrayList<>();

	public void setInvisible(Player player)
	{
		if (player != null)
		{
			boolean contained = false;
			for (int i = players.size()-1; i >= 0; i--)
			{
				PlayerInvisibility t = players.get(i);
				if (t.player == player)
				{
					t.durationleft = 120;
					contained = true;
				}
			}
			if (!contained)
			{
				players.add(new PlayerInvisibility(player, 120));
			}
		}
	}

	public void updateServer()
	{
		winCountdown--;
		if (winCountdown == 0 && !fatnuke)
		{
			if (newBattleVotes >= waitVotes)
			{
				newBattleVotes = 0;
				waitVotes = 0;
				rrplayerlist.clearVotes();
				newRound();
			}
			else
			{
				newBattleVotes = 0;
				waitVotes = 0;
				rrplayerlist.clearVotes();
				winCountdown = 1199;
                sendUpdatePacket();
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateInvisible(ClientLevel level) {
		if (level == null) return;
        for (Player player : level.players()) {
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(RRItems.camera))
                setInvisible(player);
        }
		for (int i = players.size()-1; i >= 0; i--)
		{
			PlayerInvisibility t = players.get(i);
			t.durationleft--;
			if (t.durationleft <= 0)
			{
                Entity.setViewScale(1);
				players.remove(i);
			}
			else
			{
                Entity.setViewScale(0);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateClient(Minecraft minecraft) {
		winCountdown--;
        if (winCountdown == 0 && !fatnuke) minecraft.setScreen(null);//cleargui
		else if (winCountdown == 400 && !fatnuke) minecraft.setScreen(new GuiNextBattle());//open vote gui
		else if (winCountdown == 1000) minecraft.setScreen(null);//close gui
		else if (winCountdown == 1200) { //open winner gui
            if (lastWinnerTeam == RivalRebelsTeam.OMEGA) minecraft.setScreen(new GuiOmegaWin());
            else if (lastWinnerTeam == RivalRebelsTeam.SIGMA) minecraft.setScreen(new GuiSigmaWin());
            else minecraft.player.sendSystemMessage(Component.literal("Error No Winner ?").withStyle(ChatFormatting.RED));
        }
	}

	public void load(Level world) {
        this.world = world;

        Scoreboard scrb = this.world.getScoreboard();
        try {
            PlayerTeam omega = scrb.getPlayerTeam(RivalRebelsTeam.OMEGA.toString());
            PlayerTeam sigma = scrb.getPlayerTeam(RivalRebelsTeam.SIGMA.toString());
            if (omega == null) omega = scrb.addPlayerTeam(RivalRebelsTeam.OMEGA.toString());
            if (sigma == null) sigma = scrb.addPlayerTeam(RivalRebelsTeam.SIGMA.toString());
            omega.setPlayerPrefix(Component.literal("Ω").withStyle(ChatFormatting.GREEN));
            sigma.setPlayerPrefix(Component.literal("Σ").withStyle(ChatFormatting.BLUE));
            omega.setAllowFriendlyFire(false);
            sigma.setAllowFriendlyFire(false);
            Objective killObjective = new Objective(scrb, "kills", ObjectiveCriteria.KILL_COUNT_PLAYERS, Component.nullToEmpty(ObjectiveCriteria.KILL_COUNT_PLAYERS.getName()), ObjectiveCriteria.KILL_COUNT_PLAYERS.getDefaultRenderType(), true, StyledFormat.NO_STYLE);
            Objective deathObjective = new Objective(scrb, "deaths", ObjectiveCriteria.DEATH_COUNT, Component.nullToEmpty(ObjectiveCriteria.DEATH_COUNT.getName()), ObjectiveCriteria.DEATH_COUNT.getDefaultRenderType(), true, StyledFormat.NO_STYLE);
            scrb.setDisplayObjective(DisplaySlot.LIST, killObjective);
            for (Player player : world.players()) {
                scrb.getOrCreatePlayerScore(player, killObjective);
                ScoreAccess deaths = scrb.getOrCreatePlayerScore(player, deathObjective);
                deaths.display(Component.nullToEmpty("§8R§7I§fV§7A§8L R§7E§fBE§7L§8S"));
            }
            if (RRConfig.SERVER.isScoreboardEnabled()) {
                scrb.setDisplayObjective(DisplaySlot.SIDEBAR, deathObjective);
            }
        } catch(Exception ignored) {} //just in case teams already exist etc

        if (world.isClientSide()) return;
        this.world.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(true, world.getServer());
        ((ServerLevel) world).getDataStorage().get(WORLD_DATA_TYPE, "rivalrebelsgamedata");
    }

    public void save(Level world) {
        this.world = world;
        if (world.isClientSide()) return;
        ((ServerLevel) world).getDataStorage().set("rivalrebelsgamedata", this);
	}

	private void buildSpawn()
	{
        int rs4 = (spawnRadius - 2) * (spawnRadius - 2);
		int rs1 = (spawnRadius - 1) * (spawnRadius - 1);
		int hrs = (int) (spawnRadius*0.65f);
		for (int x1 = -spawnRadius; x1 < spawnRadius; x1++)
		{
			int XX = x1 * x1;
			for (int z1 = -spawnRadius; z1 < spawnRadius; z1++)
			{
				int ZZ = z1 * z1 + XX;
				for (int y1 = -5; y1 < hrs; y1++)
				{
					int YY = y1 * y1 + ZZ;
					if ((YY > rs4 && YY < spawnRadius2) || ((y1 == -2 || y1 == -3 || y1 == -4) && YY < rs1)) world.setBlockAndUpdate(cSpawn.atY(200).offset(x1, y1, z1), RRBlocks.fshield.get().defaultBlockState());
				}
			}
		}

        omegaData.objPos = new BlockPos(cSpawn.getX() + objDist, omegaData.objPos().getY(), cSpawn.getZ());
        sigmaData.objPos = new BlockPos(cSpawn.getX() - objDist, sigmaData.objPos().getY(), cSpawn.getZ());

		ChunkAccess chunk = world.getChunk(omegaData.objPos());
        for (omegaData.objPos = new BlockPos(omegaData.objPos().getX(), chunk.getHighestFilledSectionIndex() + 15, omegaData.objPos().getZ()); omegaData.objPos().getY() > world.getMinBuildHeight(); omegaData.objPos().below())
        {
            if (!chunk.getBlockState(omegaData.objPos()).isAir())
            {
                break;
            }
        }
        chunk = world.getChunk(sigmaData.objPos());
        sigmaData.objPos = new BlockPos(sigmaData.objPos().getX(), chunk.getHighestFilledSectionIndex() + 15, sigmaData.objPos().getZ());
        for (; sigmaData.objPos().getY() > world.getMinBuildHeight(); sigmaData.objPos = sigmaData.objPos().below())
        {
            if (!chunk.getBlockState(sigmaData.objPos()).isAir())
            {
                break;
            }
        }

		for (int xx = -objRadius; xx <= objRadius; xx++)
		{
			int XX = xx * xx;
			for (int zz = -objRadius; zz <= objRadius; zz++)
			{
				int ZZ = zz * zz + XX;
				if (ZZ <= objRadius2)
				{
					if (Mth.abs(xx) == 15 && Mth.abs(zz) == 15)
					{
						world.setBlockAndUpdate(omegaData.objPos().offset(xx, 0, zz), RRBlocks.fshield.get().defaultBlockState());
						world.setBlockAndUpdate(sigmaData.objPos().offset(xx, 0, zz), RRBlocks.fshield.get().defaultBlockState());
					}
					else
					{
						world.setBlockAndUpdate(omegaData.objPos().offset(xx, 0, zz), RRBlocks.reactive.get().defaultBlockState());
						world.setBlockAndUpdate(sigmaData.objPos().offset(xx, 0, zz), RRBlocks.reactive.get().defaultBlockState());
					}
					for (int yy = 1; yy <= 7; yy++)
					{
						world.setBlockAndUpdate(omegaData.objPos().offset(xx, yy, zz), Blocks.AIR.defaultBlockState());
						world.setBlockAndUpdate(sigmaData.objPos().offset(xx, yy, zz), Blocks.AIR.defaultBlockState());
					}
				}
			}
		}

		world.setBlockAndUpdate(omegaData.objPos().offset(21, 0, 21),   RRBlocks.conduit.get().defaultBlockState());
		world.setBlockAndUpdate(omegaData.objPos().offset(21, 0, -21),  RRBlocks.conduit.get().defaultBlockState());
		world.setBlockAndUpdate(omegaData.objPos().offset(-21, 0, 21),  RRBlocks.conduit.get().defaultBlockState());
		world.setBlockAndUpdate(omegaData.objPos().offset(-21, 0, -21), RRBlocks.conduit.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos().offset(21, 0, 21),   RRBlocks.conduit.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos().offset(21, 0, -21),  RRBlocks.conduit.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos().offset(-21, 0, 21),  RRBlocks.conduit.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos().offset(-21, 0, -21), RRBlocks.conduit.get().defaultBlockState());

		for (int i = 0; i < 4; i++)
		{
			world.setBlockAndUpdate(omegaData.objPos().offset(21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(omegaData.objPos().offset(21, 1 + i, -21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(omegaData.objPos().offset(-21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(omegaData.objPos().offset(-21, 1 + i, -21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaData.objPos().offset(21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaData.objPos().offset(21, 1 + i, -21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaData.objPos().offset(-21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaData.objPos().offset(-21, 1 + i, -21), Blocks.AIR.defaultBlockState());
		}

		world.setBlockAndUpdate(omegaData.objPos().above(), RRBlocks.omegaobj.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos().above(), RRBlocks.sigmaobj.get().defaultBlockState());
		if (RRConfig.SERVER.isRhodesRoundsBase()) {
			world.setBlockAndUpdate(omegaData.objPos().above(2), RRBlocks.buildrhodes.get().defaultBlockState());
			world.setBlockAndUpdate(sigmaData.objPos().above(2), RRBlocks.buildrhodes.get().defaultBlockState());
		}
	}

	private boolean isInSpawn(Player player)
	{
		return player.position().y() < 203 && player.position().y() > 198 && player.distanceToSqr(cSpawn.getX(), 200, cSpawn.getZ()) < spawnRadius2;
	}

	public void winSigma()
	{
		if (!roundstarted) return;
		winCountdown = 1400;
        sigmaData.winCount++;
        lastWinnerTeam = RivalRebelsTeam.SIGMA;
		world.setBlockAndUpdate(omegaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
		for (int xpl = -objRadius; xpl < objRadius; xpl++) {
			int xxpl = xpl * xpl;
			for (int zpl = -objRadius; zpl < objRadius; zpl++) {
				int zzpl = zpl * zpl + xxpl;
				if (zzpl < objRadius2) {
                    for (int ypl = -1; ypl < 7; ypl++) {
                        for (int i = 0; i < 16; i++)
                        {
                            world.setBlockAndUpdate(omegaData.objPos().offset(xpl, ypl, zpl), Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
		}
        sendUpdatePacket();
	}

	public void winOmega()
	{
		if (!roundstarted) return;
		winCountdown = 1400;
        omegaData.winCount++;
        lastWinnerTeam = RivalRebelsTeam.OMEGA;
		world.setBlockAndUpdate(omegaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
		for (int xpl = -objRadius; xpl < objRadius; xpl++)
		{
			int xxpl = xpl * xpl;
			for (int zpl = -objRadius; zpl < objRadius; zpl++)
			{
				int zzpl = zpl * zpl + xxpl;
				if (zzpl < objRadius2) for (int ypl = -1; ypl < 7; ypl++)
				{
					for (int i = 0; i < 16; i++)
					{
						world.setBlockAndUpdate(sigmaData.objPos().offset(xpl, ypl, zpl), Blocks.AIR.defaultBlockState());
					}
				}
			}
		}
        sendUpdatePacket();
	}

	public void stopRounds()
	{
		if (!roundstarted) return;
		world.setBlockAndUpdate(omegaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
		world.setBlockAndUpdate(sigmaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
		winCountdown = 0;
		roundstarted = false;
		rrplayerlist.clearTeam();
        sendUpdatePacket();
	}

    public float takeOmegaHealth(float amnt) {
        if (amnt > omegaData.health()) {
            int tmp = omegaData.health();
            omegaData.health = 0;
            winSigma();
            return tmp;
        } else {
            omegaData.health -= amnt;
            return amnt;
        }
    }

    public float takeSigmaHealth(float amnt) {
        if (amnt > sigmaData.health()) {
            int tmp = sigmaData.health();
            sigmaData.health = 0;
            winOmega();
            return tmp;
        } else {
            sigmaData.health -= amnt;
            return amnt;
        }
    }

	public int getOmegaWins()
	{
		return omegaData.winCount;
	}

	public int getSigmaWins()
	{
		return sigmaData.winCount;
	}

	public String getMotD()
	{
		return MotD;
	}

    public void setMotD(String motD) {
        MotD = motD;
        sendUpdatePacket();
    }

    private void sendUpdatePacket() {
        if (world.isClientSide()) {
            return;
        }
        for (Player player : world.players()) {
            ((ServerPlayer) player).connection.send(this);
        }
    }

    public boolean isStarted()
	{
		return roundstarted;
	}

	public float addOmegaHealth(float power)
	{
        omegaData.health += power;
		if (omegaData.health() > RRConfig.SERVER.getObjectiveHealth())
		{
			int tmp = omegaData.health()-RRConfig.SERVER.getObjectiveHealth();
            omegaData.health = RRConfig.SERVER.getObjectiveHealth();
			return tmp;
		}
		return 0;
	}

	public float addSigmaHealth(float power)
	{
        sigmaData.health += power;
		if (sigmaData.health() > RRConfig.SERVER.getObjectiveHealth())
		{
			int tmp = sigmaData.health()-RRConfig.SERVER.getObjectiveHealth();
            sigmaData.health = RRConfig.SERVER.getObjectiveHealth();
			return tmp;
		}
		return 0;
	}
}

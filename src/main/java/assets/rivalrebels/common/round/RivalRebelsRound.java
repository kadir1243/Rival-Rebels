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
package assets.rivalrebels.common.round;

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.packet.GuiSpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
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
import java.util.ArrayList;
import java.util.List;

public class RivalRebelsRound extends SavedData implements CustomPacketPayload {
    private static final SavedData.Factory<RivalRebelsRound> WORLD_DATA_TYPE = new SavedData.Factory<>(RivalRebelsRound::new, RivalRebelsRound::fromNbt, null);
    public static final StreamCodec<FriendlyByteBuf, RivalRebelsRound> STREAM_CODEC = StreamCodec.ofMember(RivalRebelsRound::toBytes, RivalRebelsRound::fromBytes);
    public static final Type<RivalRebelsRound> PACKET_TYPE = new Type<>(RRIdentifiers.create("rivalrebelsrounddata"));
    public ChunkPos cSpawn = new ChunkPos(-1, -1);
    public BlockPos omegaObjPos = new BlockPos(-1, -1, -1);
    public BlockPos sigmaObjPos = new BlockPos(-1, -1, -1);
	private String					MotD			= "Select your class and nuke the enemy team's objective to win.";
	public RivalRebelsPlayerList	rrplayerlist	= new RivalRebelsPlayerList();
	public Level					world;
	private int						winCountdown	= 0;
	private int						omegaWins		= 0;
	private int						sigmaWins		= 0;
	private boolean					lastwinomega 	= false; //t: omega f: sigma
	private boolean					fatnuke			= false;
	public int						waitVotes		= 0;
	public int						newBattleVotes	= 0;
	private int						spawnRadius 	= 20;
	private int						spawnRadius2	= 20*20;
	private int						objRadius		= 29;
	private int						objRadius2		= 29*29;
	private int						spawnDist		= 150;
	private int						objDist			= 200;
	private boolean					roundstarted	= false;
	public int omegaHealth;
	public int sigmaHealth;

	public RivalRebelsRound() {
    }

    public void setRoundDistances(int spawnRadius, int spawnDist, int objDist) {
        this.spawnRadius = spawnRadius;
        spawnRadius2 = spawnRadius * spawnRadius;
        this.spawnDist = spawnDist;
        this.objDist = objDist;
        omegaHealth = RRConfig.SERVER.getObjectiveHealth();
        sigmaHealth = RRConfig.SERVER.getObjectiveHealth();
    }

	public static RivalRebelsRound fromBytes(FriendlyByteBuf buf) {
        RivalRebelsRound packet = new RivalRebelsRound();
        packet.roundstarted = buf.readBoolean();
        packet.cSpawn = buf.readChunkPos();
        packet.omegaObjPos = buf.readBlockPos();
        packet.sigmaObjPos = buf.readBlockPos();
        packet.winCountdown = buf.readInt();
        packet.omegaWins = buf.readInt();
        packet.sigmaWins = buf.readInt();
        packet.lastwinomega = buf.readBoolean();
        packet.fatnuke = buf.readBoolean();
        packet.MotD = buf.readUtf();
        packet.rrplayerlist = RivalRebelsPlayerList.fromBytes(buf);
        return packet;
    }

    public void initClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (roundstarted && winCountdown > 0) {
                updateClient();
            }
            updateInvisible();
        });
    }

    public void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (roundstarted && winCountdown > 0) {
                updateServer();
            }
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (!roundstarted) return;
            newPlayer.setPosRaw(cSpawn.x, 200, cSpawn.z);
            ServerPlayNetworking.send(newPlayer, GuiSpawnPacket.INSTANCE);
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.getPlayer();
            if (!roundstarted) return;
            if (!rrplayerlist.contains(player.getGameProfile()))
            {
                player.getInventory().clearContent();
                player.setPosRaw(cSpawn.x, 200, cSpawn.z);
                //rrplayerlist.add(new RivalRebelsPlayer(player.getCommandSenderName(), RivalRebelsTeam.NONE, RivalRebelsClass.NONE, RivalRebelsRank.REGULAR, RRConfig.SERVER.getMaximumResets()));
            }
            ServerPlayNetworking.send(player, rrplayerlist);
            if (isInSpawn(player)) ServerPlayNetworking.send(player, GuiSpawnPacket.INSTANCE);
        });
        ServerWorldEvents.LOAD.register((server, world) -> load(world));
        ServerWorldEvents.UNLOAD.register((server, world) -> save(world));
    }

    public static RivalRebelsRound fromNbt(CompoundTag nbt, HolderLookup.Provider registries) {
        RivalRebelsRound packet = new RivalRebelsRound();
        packet.roundstarted = nbt.getBoolean("roundstarted");
        packet.cSpawn = new ChunkPos(nbt.getLong("cSpawn"));
        packet.omegaObjPos = BlockPos.of(nbt.getLong("omegaObjPos"));
        packet.sigmaObjPos = BlockPos.of(nbt.getLong("sigmaObjPos"));
        packet.winCountdown = nbt.getInt("winCountdown");
        packet.omegaWins = nbt.getInt("omegaWins");
        packet.sigmaWins = nbt.getInt("sigmaWins");
        packet.lastwinomega = nbt.getBoolean("lastwinomega");
        packet.fatnuke = nbt.getBoolean("fatnuke");
        packet.MotD = nbt.getString("MotD");
        packet.rrplayerlist = RivalRebelsPlayerList.fromNbt(nbt.getCompound("rrplayerlist"));
        return packet;
    }

    public static void toBytes(RivalRebelsRound packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.roundstarted);
		buf.writeChunkPos(packet.cSpawn);
        buf.writeBlockPos(packet.omegaObjPos);
		buf.writeBlockPos(packet.sigmaObjPos);
		buf.writeInt(packet.winCountdown);
		buf.writeInt(packet.omegaWins);
		buf.writeInt(packet.sigmaWins);
		buf.writeBoolean(packet.lastwinomega);
		buf.writeBoolean(packet.fatnuke);
		buf.writeUtf(packet.MotD);
		RivalRebelsPlayerList.toBytes(packet.rrplayerlist, buf);
	}

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putBoolean("roundstarted", roundstarted);
        tag.putLong("cSpawn", cSpawn.toLong());
        tag.putLong("omegaObjPos", omegaObjPos.asLong());
        tag.putLong("sigmaObjPos", sigmaObjPos.asLong());
        tag.putInt("winCountdown", winCountdown);
        tag.putInt("omegaWins", omegaWins);
        tag.putInt("sigmaWins", sigmaWins);
        tag.putBoolean("lastwinomega", lastwinomega);
        tag.putBoolean("fatnuke", fatnuke);
        tag.putString("MotD", MotD);
        CompoundTag compound = new CompoundTag();
        rrplayerlist.toNbt(compound);
        tag.put("rrplayerlist", compound);
        return tag;
    }

    public static void onMessage(RivalRebelsRound m, ClientPlayNetworking.Context context) {
        RivalRebels.round.copy(m);
    }

	public void copy(RivalRebelsRound m)
	{
		roundstarted = m.roundstarted;
		cSpawn = m.cSpawn;
        omegaObjPos = m.omegaObjPos;
        sigmaObjPos = m.sigmaObjPos;
		winCountdown = m.winCountdown;
		omegaWins = m.omegaWins;
		sigmaWins = m.sigmaWins;
		lastwinomega = m.lastwinomega;
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
        cSpawn = new ChunkPos(cSpawn.x, cSpawn.z + spawnDist);
        if (!world.isClientSide()) {
            ((ServerLevel) world).setDefaultSpawnPos(new BlockPos(cSpawn.x, 200, cSpawn.z), 0);
        }
		float f = RRConfig.SERVER.getRhodesInRoundsChance();
		while (f >= 1)
		{
			f--;
			world.addFreshEntity(new EntityRhodes(world, cSpawn.x+world.random.nextDouble()-0.5f, 170, cSpawn.z+world.random.nextDouble()-0.5f,1));
		}
		if (f > world.random.nextDouble()) world.addFreshEntity(new EntityRhodes(world, cSpawn.x+world.random.nextDouble()-0.5f, 170, cSpawn.z+world.random.nextDouble()-0.5f,1));
		buildSpawn();
		omegaHealth=RRConfig.SERVER.getObjectiveHealth();
		sigmaHealth=RRConfig.SERVER.getObjectiveHealth();
        sendUpdatePacket();
	}

	public void startRound(int x, int z)
	{
		fatnuke = false;
		roundstarted = true;
        cSpawn = new ChunkPos(x, z - spawnDist);
		newRound();
	}

	public void roundManualStart()
	{
		if (!roundstarted || fatnuke)
		{
			fatnuke = true;
			roundstarted = true;
			rrplayerlist = new RivalRebelsPlayerList();
			cSpawn = new ChunkPos((omegaObjPos.getX()+sigmaObjPos.getX())/2, (omegaObjPos.getZ()+sigmaObjPos.getZ())/2);
            ((ServerLevel) world).setDefaultSpawnPos(new BlockPos(cSpawn.x, world.getChunk(new BlockPos(cSpawn.x, 0, cSpawn.z)).getHeight(), cSpawn.z), 0F);
			omegaHealth=RRConfig.SERVER.getObjectiveHealth();
			sigmaHealth=RRConfig.SERVER.getObjectiveHealth();
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

	@Environment(EnvType.CLIENT)
	public void updateInvisible() {
		if (Minecraft.getInstance().level == null) return;
        for (Player player : Minecraft.getInstance().level.players()) {
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

	@Environment(EnvType.CLIENT)
	public void updateClient()
	{
		winCountdown--;
		if (winCountdown == 0 && !fatnuke) RivalRebels.proxy.closeGui();//cleargui
		else if (winCountdown == 400 && !fatnuke) RivalRebels.proxy.nextBattle();//open vote gui
		else if (winCountdown == 1000) RivalRebels.proxy.closeGui();//close gui
		else if (winCountdown == 1200) RivalRebels.proxy.teamWin(lastwinomega);//open winner gui
	}

	public void load(Level world)
	{
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
					if ((YY > rs4 && YY < spawnRadius2) || ((y1 == -2 || y1 == -3 || y1 == -4) && YY < rs1)) world.setBlockAndUpdate(new BlockPos(cSpawn.x + x1, 200 + y1, cSpawn.z + z1), RRBlocks.fshield.defaultBlockState());
				}
			}
		}

        omegaObjPos = new BlockPos(cSpawn.x + objDist, omegaObjPos.getY(), cSpawn.z);
        sigmaObjPos = new BlockPos(cSpawn.x - objDist, sigmaObjPos.getY(), cSpawn.z);

		ChunkAccess chunk = world.getChunk(omegaObjPos);
        for (omegaObjPos = new BlockPos(omegaObjPos.getX(), chunk.getHighestFilledSectionIndex() + 15, omegaObjPos.getZ()); omegaObjPos.getY() > world.getMinBuildHeight(); omegaObjPos.below())
        {
            if (!chunk.getBlockState(omegaObjPos).isAir())
            {
                break;
            }
        }
        chunk = world.getChunk(sigmaObjPos);
        sigmaObjPos = new BlockPos(sigmaObjPos.getX(), chunk.getHighestFilledSectionIndex() + 15, sigmaObjPos.getZ());
        for (; sigmaObjPos.getY() > world.getMinBuildHeight(); sigmaObjPos = sigmaObjPos.below())
        {
            if (!chunk.getBlockState(sigmaObjPos).isAir())
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
						world.setBlockAndUpdate(omegaObjPos.offset(xx, 0, zz), RRBlocks.fshield.defaultBlockState());
						world.setBlockAndUpdate(sigmaObjPos.offset(xx, 0, zz), RRBlocks.fshield.defaultBlockState());
					}
					else
					{
						world.setBlockAndUpdate(omegaObjPos.offset(xx, 0, zz), RRBlocks.reactive.defaultBlockState());
						world.setBlockAndUpdate(sigmaObjPos.offset(xx, 0, zz), RRBlocks.reactive.defaultBlockState());
					}
					for (int yy = 1; yy <= 7; yy++)
					{
						world.setBlockAndUpdate(omegaObjPos.offset(xx, yy, zz), Blocks.AIR.defaultBlockState());
						world.setBlockAndUpdate(sigmaObjPos.offset(xx, yy, zz), Blocks.AIR.defaultBlockState());
					}
				}
			}
		}

		world.setBlockAndUpdate(omegaObjPos.offset(21, 0, 21),   RRBlocks.conduit.defaultBlockState());
		world.setBlockAndUpdate(omegaObjPos.offset(21, 0, -21),  RRBlocks.conduit.defaultBlockState());
		world.setBlockAndUpdate(omegaObjPos.offset(-21, 0, 21),  RRBlocks.conduit.defaultBlockState());
		world.setBlockAndUpdate(omegaObjPos.offset(-21, 0, -21), RRBlocks.conduit.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos.offset(21, 0, 21),   RRBlocks.conduit.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos.offset(21, 0, -21),  RRBlocks.conduit.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos.offset(-21, 0, 21),  RRBlocks.conduit.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos.offset(-21, 0, -21), RRBlocks.conduit.defaultBlockState());

		for (int i = 0; i < 4; i++)
		{
			world.setBlockAndUpdate(omegaObjPos.offset(21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(omegaObjPos.offset(21, 1 + i, -21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(omegaObjPos.offset(-21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(omegaObjPos.offset(-21, 1 + i, -21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaObjPos.offset(21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaObjPos.offset(21, 1 + i, -21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaObjPos.offset(-21, 1 + i, 21), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(sigmaObjPos.offset(-21, 1 + i, -21), Blocks.AIR.defaultBlockState());
		}

		world.setBlockAndUpdate(omegaObjPos.above(), RRBlocks.omegaobj.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos.above(), RRBlocks.sigmaobj.defaultBlockState());
		if (RRConfig.SERVER.isRhodesRoundsBase()) {
			world.setBlockAndUpdate(omegaObjPos.above(2), RRBlocks.buildrhodes.defaultBlockState());
			world.setBlockAndUpdate(sigmaObjPos.above(2), RRBlocks.buildrhodes.defaultBlockState());
		}
	}

	private boolean isInSpawn(Player player)
	{
		return player.position().y() < 203 && player.position().y() > 198 && player.distanceToSqr(cSpawn.x, 200, cSpawn.z) < spawnRadius2;
	}

	public void winSigma()
	{
		if (!roundstarted) return;
		winCountdown = 1400;
		sigmaWins++;
		lastwinomega = false;
		world.setBlockAndUpdate(omegaObjPos, RRBlocks.plasmaexplosion.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos, RRBlocks.plasmaexplosion.defaultBlockState());
		for (int xpl = -objRadius; xpl < objRadius; xpl++) {
			int xxpl = xpl * xpl;
			for (int zpl = -objRadius; zpl < objRadius; zpl++) {
				int zzpl = zpl * zpl + xxpl;
				if (zzpl < objRadius2) {
                    for (int ypl = -1; ypl < 7; ypl++) {
                        for (int i = 0; i < 16; i++)
                        {
                            world.setBlockAndUpdate(omegaObjPos.offset(xpl, ypl, zpl), Blocks.AIR.defaultBlockState());
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
		omegaWins++;
		lastwinomega = true;
		world.setBlockAndUpdate(omegaObjPos, RRBlocks.plasmaexplosion.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos, RRBlocks.plasmaexplosion.defaultBlockState());
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
						world.setBlockAndUpdate(sigmaObjPos.offset(xpl, ypl, zpl), Blocks.AIR.defaultBlockState());
					}
				}
			}
		}
        sendUpdatePacket();
	}

	public void stopRounds()
	{
		if (!roundstarted) return;
		world.setBlockAndUpdate(omegaObjPos, RRBlocks.plasmaexplosion.defaultBlockState());
		world.setBlockAndUpdate(sigmaObjPos, RRBlocks.plasmaexplosion.defaultBlockState());
		winCountdown = 0;
		roundstarted = false;
		rrplayerlist.clearTeam();
        sendUpdatePacket();
	}

	public int takeOmegaHealth(int amnt)
	{
		if (amnt > omegaHealth)
		{
			int tmp = omegaHealth;
			omegaHealth = 0;
			winSigma();
			return tmp;
		}
		else
		{
			omegaHealth-=amnt;
			return amnt;
		}
	}

	public int takeSigmaHealth(int amnt)
	{
		if (amnt > sigmaHealth)
		{
			int tmp = sigmaHealth;
			sigmaHealth = 0;
			winOmega();
			return tmp;
		}
		else
		{
			sigmaHealth-=amnt;
			return amnt;
		}
	}

	public int getOmegaWins()
	{
		return omegaWins;
	}

	public int getSigmaWins()
	{
		return sigmaWins;
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
            ServerPlayNetworking.send((ServerPlayer) player, this);
        }
    }

    public boolean isStarted()
	{
		return roundstarted;
	}

	public float addOmegaHealth(float power)
	{
		omegaHealth += power;
		if (omegaHealth > RRConfig.SERVER.getObjectiveHealth())
		{
			int tmp = omegaHealth-RRConfig.SERVER.getObjectiveHealth();
			omegaHealth = RRConfig.SERVER.getObjectiveHealth();
			return tmp;
		}
		return 0;
	}

	public float addSigmaHealth(float power)
	{
		sigmaHealth += power;
		if (sigmaHealth > RRConfig.SERVER.getObjectiveHealth())
		{
			int tmp = sigmaHealth-RRConfig.SERVER.getObjectiveHealth();
			sigmaHealth = RRConfig.SERVER.getObjectiveHealth();
			return tmp;
		}
		return 0;
	}
}

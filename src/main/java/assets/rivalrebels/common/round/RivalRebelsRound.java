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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.packet.GuiSpawnPacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RivalRebelsRound extends PersistentState {
    private int						cSpawnx			= -1, cSpawnz = -1;
    public BlockPos omegaObjPos = new BlockPos(-1, -1, -1);
    public BlockPos sigmaObjPos = new BlockPos(-1, -1, -1);
	private String					MotD			= "Select your class and nuke the enemy team's objective to win.";
	public RivalRebelsPlayerList	rrplayerlist	= new RivalRebelsPlayerList();
	public World					world;
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
	public int omegaHealth=RivalRebels.objectiveHealth;
	public int sigmaHealth=RivalRebels.objectiveHealth;

	public RivalRebelsRound() {
    }

	public RivalRebelsRound(int spawnRadius, int spawnDist, int objDist) {
		this.spawnRadius = spawnRadius;
		spawnRadius2 = spawnRadius * spawnRadius;
		this.spawnDist = spawnDist;
		this.objDist = objDist;
	}

	public static RivalRebelsRound fromBytes(PacketByteBuf buf) {
        RivalRebelsRound packet = new RivalRebelsRound();
        packet.roundstarted = buf.readBoolean();
        packet.cSpawnx = buf.readInt();
        packet.cSpawnz = buf.readInt();
        packet.omegaObjPos = buf.readBlockPos();
        packet.sigmaObjPos = buf.readBlockPos();
        packet.winCountdown = buf.readInt();
        packet.omegaWins = buf.readInt();
        packet.sigmaWins = buf.readInt();
        packet.lastwinomega = buf.readBoolean();
        packet.fatnuke = buf.readBoolean();
        packet.MotD = buf.readString();
        packet.rrplayerlist = RivalRebelsPlayerList.fromBytes(buf);
        return packet;
    }

    public static RivalRebelsRound fromNbt(NbtCompound nbt) {
        RivalRebelsRound packet = new RivalRebelsRound();
        packet.roundstarted = nbt.getBoolean("roundstarted");
        packet.cSpawnx = nbt.getInt("cSpawnx");
        packet.cSpawnz = nbt.getInt("cSpawnz");
        packet.omegaObjPos = BlockPos.fromLong(nbt.getLong("omegaObjPos"));
        packet.sigmaObjPos = BlockPos.fromLong(nbt.getLong("sigmaObjPos"));
        packet.winCountdown = nbt.getInt("winCountdown");
        packet.omegaWins = nbt.getInt("omegaWins");
        packet.sigmaWins = nbt.getInt("sigmaWins");
        packet.lastwinomega = nbt.getBoolean("lastwinomega");
        packet.fatnuke = nbt.getBoolean("fatnuke");
        packet.MotD = nbt.getString("MotD");
        packet.rrplayerlist = RivalRebelsPlayerList.fromNbt(nbt.getCompound("rrplayerlist"));
        return packet;
    }

	public static void toBytes(RivalRebelsRound packet, PacketByteBuf buf) {
        buf.writeBoolean(packet.roundstarted);
		buf.writeInt(packet.cSpawnx);
		buf.writeInt(packet.cSpawnz);
        buf.writeBlockPos(packet.omegaObjPos);
		buf.writeBlockPos(packet.sigmaObjPos);
		buf.writeInt(packet.winCountdown);
		buf.writeInt(packet.omegaWins);
		buf.writeInt(packet.sigmaWins);
		buf.writeBoolean(packet.lastwinomega);
		buf.writeBoolean(packet.fatnuke);
		buf.writeString(packet.MotD);
		RivalRebelsPlayerList.toBytes(packet.rrplayerlist, buf);
	}

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("roundstarted", roundstarted);
        nbt.putInt("cSpawnx", cSpawnx);
        nbt.putInt("cSpawnz", cSpawnz);
        nbt.putLong("omegaObjPos", omegaObjPos.asLong());
        nbt.putLong("sigmaObjPos", sigmaObjPos.asLong());
        nbt.putInt("winCountdown", winCountdown);
        nbt.putInt("omegaWins", omegaWins);
        nbt.putInt("sigmaWins", sigmaWins);
        nbt.putBoolean("lastwinomega", lastwinomega);
        nbt.putBoolean("fatnuke", fatnuke);
        nbt.putString("MotD", MotD);
        NbtCompound compound = new NbtCompound();
        rrplayerlist.toNbt(compound);
        nbt.put("rrplayerlist", compound);
        return nbt;
    }

    public static void onMessage(RivalRebelsRound m, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> RivalRebels.round.copy(m));
    }

	public void copy(RivalRebelsRound m)
	{
		roundstarted = m.roundstarted;
		cSpawnx = m.cSpawnx;
		cSpawnz = m.cSpawnz;
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
        for (PlayerEntity player : world.getPlayers()) {
            player.damage(DamageSource.OUT_OF_WORLD, 20000);
            player.getInventory().clear();
        }
		cSpawnz += spawnDist;
        if (!world.isClient) {
            ((ServerWorld) world).setSpawnPos(new BlockPos(cSpawnx, 200, cSpawnz), 0);
        }
		float f = RivalRebels.rhodesChance;
		while (f >= 1)
		{
			f--;
			world.spawnEntity(new EntityRhodes(world, cSpawnx+world.random.nextDouble()-0.5f, 170, cSpawnz+world.random.nextDouble()-0.5f,1));
		}
		if (f > world.random.nextDouble()) world.spawnEntity(new EntityRhodes(world, cSpawnx+world.random.nextDouble()-0.5f, 170, cSpawnz+world.random.nextDouble()-0.5f,1));
		buildSpawn();
		omegaHealth=RivalRebels.objectiveHealth;
		sigmaHealth=RivalRebels.objectiveHealth;
        sendUpdatePacket();
	}

	public void startRound(int x, int z)
	{
		fatnuke = false;
		roundstarted = true;
		cSpawnx = x;
		cSpawnz = z - spawnDist;
		newRound();
	}

	public void roundManualStart()
	{
		if (!roundstarted || fatnuke)
		{
			fatnuke = true;
			roundstarted = true;
			rrplayerlist = new RivalRebelsPlayerList();
			cSpawnx = (omegaObjPos.getX()+sigmaObjPos.getX())/2;
			cSpawnz = (omegaObjPos.getZ()+sigmaObjPos.getZ())/2;
            ((ServerWorld) world).setSpawnPos(new BlockPos(cSpawnx, world.getChunk(new BlockPos(cSpawnx, 0, cSpawnz)).getHeight(), cSpawnz), 0F);
			omegaHealth=RivalRebels.objectiveHealth;
			sigmaHealth=RivalRebels.objectiveHealth;
			sendUpdatePacket();
		}
	}

	public static class PlayerInvisibility
	{
		public PlayerInvisibility(PlayerEntity p, int i)
		{
			player = p;
			durationleft = i;
		}
		public PlayerEntity player;
		public int durationleft;
	}

	private final List<PlayerInvisibility> players = new ArrayList<>();

	public void setInvisible(PlayerEntity player)
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

	@SubscribeEvent
	public void updateRound(TickEvent event)
	{
		if (roundstarted && winCountdown > 0 && event.phase.equals(Phase.END))
		{
			if (event.type == TickEvent.Type.SERVER) updateServer();
			if (event.type == TickEvent.Type.CLIENT) updateClient();
		}
		if (event.type == TickEvent.Type.CLIENT && event.phase.equals(Phase.END)) updateInvisible();
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
	public void updateInvisible()
	{
		if (MinecraftClient.getInstance().world == null) return;
        for (PlayerEntity player : MinecraftClient.getInstance().world.getPlayers()) {
            if (player.getEquippedStack(EquipmentSlot.HEAD).getItem() == RRItems.camera)
                setInvisible(player);
        }
		for (int i = players.size()-1; i >= 0; i--)
		{
			PlayerInvisibility t = players.get(i);
			t.durationleft--;
			if (t.durationleft <= 0)
			{
                Entity.setRenderDistanceMultiplier(1);
				players.remove(i);
			}
			else
			{
                Entity.setRenderDistanceMultiplier(0);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateClient()
	{
		winCountdown--;
		if (winCountdown == 0 && !fatnuke) RivalRebels.proxy.closeGui();//cleargui
		else if (winCountdown == 400 && !fatnuke) RivalRebels.proxy.nextBattle();//open vote gui
		else if (winCountdown == 1000) RivalRebels.proxy.closeGui();//close gui
		else if (winCountdown == 1200) RivalRebels.proxy.teamWin(lastwinomega);//open winner gui
	}

	public void load(World world)
	{
        this.world = world;

        Scoreboard scrb = this.world.getScoreboard();
        try {
            Team omega = scrb.getTeam(RivalRebelsTeam.OMEGA.toString());
            Team sigma = scrb.getTeam(RivalRebelsTeam.SIGMA.toString());
            if (omega == null) omega = scrb.addTeam(RivalRebelsTeam.OMEGA.toString());
            if (sigma == null) sigma = scrb.addTeam(RivalRebelsTeam.SIGMA.toString());
            omega.setPrefix(new LiteralText("Ω").formatted(Formatting.GREEN));
            sigma.setPrefix(new LiteralText("Σ").formatted(Formatting.BLUE));
            omega.setFriendlyFireAllowed(false);
            sigma.setFriendlyFireAllowed(false);
            ScoreboardObjective kills = scrb.containsObjective("kills") ? scrb.getObjective("kills") : scrb.addObjective("kills", ScoreboardCriterion.PLAYER_KILL_COUNT, Text.of(ScoreboardCriterion.PLAYER_KILL_COUNT.getName()), ScoreboardCriterion.PLAYER_KILL_COUNT.getDefaultRenderType());
            scrb.setObjectiveSlot(0, kills);
            ScoreboardObjective deaths = scrb.containsObjective("deaths") ? scrb.getObjective("deaths") : scrb.addObjective("deaths", ScoreboardCriterion.DEATH_COUNT, Text.of(ScoreboardCriterion.DEATH_COUNT.getName()), ScoreboardCriterion.DEATH_COUNT.getDefaultRenderType());
            if (RivalRebels.scoreboardenabled) {
                scrb.setObjectiveSlot(1, deaths);
            }
            deaths.setDisplayName(Text.of("§8R§7I§fV§7A§8L R§7E§fBE§7L§8S"));
        } catch(Exception ignored) {} //just in case teams already exist etc

        if (world.isClient()) return;
        this.world.getGameRules().get(GameRules.KEEP_INVENTORY).set(true, world.getServer());
        ((ServerWorld) world).getPersistentStateManager().get(RivalRebelsRound::fromNbt, "rivalrebelsgamedata");
    }

    public void save(World world) {
        this.world = world;
        if (world.isClient()) return;
        ((ServerWorld) world).getPersistentStateManager().set("rivalrebelsgamedata", this);
	}

    @SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (!roundstarted) return;
		if (!rrplayerlist.contains(event.getPlayer().getGameProfile()))
		{
			event.getPlayer().getInventory().clear();
			event.getPlayer().setPos(cSpawnx, 200, cSpawnz);
			//rrplayerlist.add(new RivalRebelsPlayer(event.player.getCommandSenderName(), RivalRebelsTeam.NONE, RivalRebelsClass.NONE, RivalRebelsRank.REGULAR, RivalRebels.resetMax));
		}
		PacketDispatcher.packetsys.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), rrplayerlist);
		if (isInSpawn(event.getPlayer())) PacketDispatcher.packetsys.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new GuiSpawnPacket());
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if (!roundstarted) return;
		event.getPlayer().setPos(cSpawnx, 200, cSpawnz);
		PacketDispatcher.packetsys.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new GuiSpawnPacket());
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
					if ((YY > rs4 && YY < spawnRadius2) || ((y1 == -2 || y1 == -3 || y1 == -4) && YY < rs1)) world.setBlockState(new BlockPos(cSpawnx + x1, 200 + y1, cSpawnz + z1), RRBlocks.fshield.getDefaultState());
				}
			}
		}

        omegaObjPos = new BlockPos(cSpawnx + objDist, omegaObjPos.getY(), cSpawnz);
        sigmaObjPos = new BlockPos(cSpawnx - objDist, sigmaObjPos.getY(), cSpawnz);

		Chunk chunk = world.getChunk(omegaObjPos);
        for (omegaObjPos = new BlockPos(omegaObjPos.getX(), chunk.getHighestNonEmptySectionYOffset() + 15, omegaObjPos.getZ()); omegaObjPos.getY() > 0; omegaObjPos.down())
        {
            if (chunk.getBlockState(omegaObjPos).getBlock() != Blocks.AIR)
            {
                break;
            }
        }
        chunk = world.getChunk(sigmaObjPos);
        sigmaObjPos = new BlockPos(sigmaObjPos.getX(), chunk.getHighestNonEmptySectionYOffset() + 15, sigmaObjPos.getZ());
        for (; sigmaObjPos.getY() > 0; sigmaObjPos = sigmaObjPos.down())
        {
            if (chunk.getBlockState(sigmaObjPos).getBlock() != Blocks.AIR)
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
					if (Math.abs(xx) == 15 && Math.abs(zz) == 15)
					{
						world.setBlockState(omegaObjPos.add(xx, 0, zz), RRBlocks.fshield.getDefaultState());
						world.setBlockState(sigmaObjPos.add(xx, 0, zz), RRBlocks.fshield.getDefaultState());
					}
					else
					{
						world.setBlockState(omegaObjPos.add(xx, 0, zz), RRBlocks.reactive.getDefaultState());
						world.setBlockState(sigmaObjPos.add(xx, 0, zz), RRBlocks.reactive.getDefaultState());
					}
					for (int yy = 1; yy <= 7; yy++)
					{
						world.setBlockState(omegaObjPos.add(xx, yy, zz), Blocks.AIR.getDefaultState());
						world.setBlockState(sigmaObjPos.add(xx, yy, zz), Blocks.AIR.getDefaultState());
					}
				}
			}
		}

		world.setBlockState(omegaObjPos.add(21, 0, 21),   RRBlocks.conduit.getDefaultState());
		world.setBlockState(omegaObjPos.add(21, 0, -21),  RRBlocks.conduit.getDefaultState());
		world.setBlockState(omegaObjPos.add(-21, 0, 21),  RRBlocks.conduit.getDefaultState());
		world.setBlockState(omegaObjPos.add(-21, 0, -21), RRBlocks.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(21, 0, 21),   RRBlocks.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(21, 0, -21),  RRBlocks.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(-21, 0, 21),  RRBlocks.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(-21, 0, -21), RRBlocks.conduit.getDefaultState());

		for (int i = 0; i < 4; i++)
		{
			world.setBlockState(omegaObjPos.add(21, 1 + i, 21), Blocks.AIR.getDefaultState());
			world.setBlockState(omegaObjPos.add(21, 1 + i, -21), Blocks.AIR.getDefaultState());
			world.setBlockState(omegaObjPos.add(-21, 1 + i, 21), Blocks.AIR.getDefaultState());
			world.setBlockState(omegaObjPos.add(-21, 1 + i, -21), Blocks.AIR.getDefaultState());
			world.setBlockState(sigmaObjPos.add(21, 1 + i, 21), Blocks.AIR.getDefaultState());
			world.setBlockState(sigmaObjPos.add(21, 1 + i, -21), Blocks.AIR.getDefaultState());
			world.setBlockState(sigmaObjPos.add(-21, 1 + i, 21), Blocks.AIR.getDefaultState());
			world.setBlockState(sigmaObjPos.add(-21, 1 + i, -21), Blocks.AIR.getDefaultState());
		}

		world.setBlockState(omegaObjPos.up(), RRBlocks.omegaobj.getDefaultState());
		world.setBlockState(sigmaObjPos.up(), RRBlocks.sigmaobj.getDefaultState());
		if (RRConfig.SERVER.isRhodesRoundsBase()) {
			world.setBlockState(omegaObjPos.up(2), RRBlocks.buildrhodes.getDefaultState());
			world.setBlockState(sigmaObjPos.up(2), RRBlocks.buildrhodes.getDefaultState());
		}
	}

	private boolean isInSpawn(PlayerEntity player)
	{
		return player.getPos().getY() < 203 && player.getPos().getY() > 198 && player.squaredDistanceTo(cSpawnx, 200, cSpawnz) < spawnRadius2;
	}

	public void winSigma()
	{
		if (!roundstarted) return;
		winCountdown = 1400;
		sigmaWins++;
		lastwinomega = false;
		world.setBlockState(omegaObjPos, RRBlocks.plasmaexplosion.getDefaultState());
		world.setBlockState(sigmaObjPos, RRBlocks.plasmaexplosion.getDefaultState());
		for (int xpl = -objRadius; xpl < objRadius; xpl++) {
			int xxpl = xpl * xpl;
			for (int zpl = -objRadius; zpl < objRadius; zpl++) {
				int zzpl = zpl * zpl + xxpl;
				if (zzpl < objRadius2) {
                    for (int ypl = -1; ypl < 7; ypl++) {
                        for (int i = 0; i < 16; i++)
                        {
                            world.setBlockState(omegaObjPos.add(xpl, ypl, zpl), Blocks.AIR.getDefaultState());
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
		world.setBlockState(omegaObjPos, RRBlocks.plasmaexplosion.getDefaultState());
		world.setBlockState(sigmaObjPos, RRBlocks.plasmaexplosion.getDefaultState());
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
						world.setBlockState(sigmaObjPos.add(xpl, ypl, zpl), Blocks.AIR.getDefaultState());
					}
				}
			}
		}
        sendUpdatePacket();
	}

	public void stopRounds()
	{
		if (!roundstarted) return;
		world.setBlockState(omegaObjPos, RRBlocks.plasmaexplosion.getDefaultState());
		world.setBlockState(sigmaObjPos, RRBlocks.plasmaexplosion.getDefaultState());
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
        PacketDispatcher.packetsys.send(PacketDistributor.ALL.noArg(), this);
    }

    public boolean isStarted()
	{
		return roundstarted;
	}

	public float addOmegaHealth(float power)
	{
		omegaHealth += power;
		if (omegaHealth > RivalRebels.objectiveHealth)
		{
			int tmp = omegaHealth-RivalRebels.objectiveHealth;
			omegaHealth = RivalRebels.objectiveHealth;
			return tmp;
		}
		return 0;
	}

	public float addSigmaHealth(float power)
	{
		sigmaHealth += power;
		if (sigmaHealth > RivalRebels.objectiveHealth)
		{
			int tmp = sigmaHealth-RivalRebels.objectiveHealth;
			sigmaHealth = RivalRebels.objectiveHealth;
			return tmp;
		}
		return 0;
	}
}

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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.packet.GuiSpawnPacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RivalRebelsRound implements IMessage {
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

	@Override
	public void fromBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
		roundstarted = buf.readBoolean();
		cSpawnx = buf.readInt();
		cSpawnz = buf.readInt();
        omegaObjPos = buffer.readBlockPos();
        sigmaObjPos = buffer.readBlockPos();
		winCountdown = buf.readInt();
		omegaWins = buf.readInt();
		sigmaWins = buf.readInt();
		lastwinomega = buf.readBoolean();
		fatnuke = buf.readBoolean();
		MotD = ByteBufUtils.readUTF8String(buf);
		rrplayerlist.fromBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
		buf.writeBoolean(roundstarted);
		buf.writeInt(cSpawnx);
		buf.writeInt(cSpawnz);
        buffer.writeBlockPos(omegaObjPos);
		buffer.writeBlockPos(sigmaObjPos);
		buf.writeInt(winCountdown);
		buf.writeInt(omegaWins);
		buf.writeInt(sigmaWins);
		buf.writeBoolean(lastwinomega);
		buf.writeBoolean(fatnuke);
		ByteBufUtils.writeUTF8String(buf, MotD);
		rrplayerlist.toBytes(buf);
	}

	public static class Handler implements IMessageHandler<RivalRebelsRound, IMessage> {
		@Override
		public IMessage onMessage(RivalRebelsRound m, MessageContext ctx) {
			RivalRebels.round.copy(m);
			return null;
		}
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
        for (EntityPlayer player : world.playerEntities) {
            player.attackEntityFrom(DamageSource.OUT_OF_WORLD, 20000);
            player.inventory.clear();
        }
		cSpawnz += spawnDist;
		world.setSpawnPoint(new BlockPos(cSpawnx, 200, cSpawnz));
		float f = RivalRebels.rhodesChance;
		while (f >= 1)
		{
			f--;
			world.spawnEntity(new EntityRhodes(world, cSpawnx+world.rand.nextDouble()-0.5f, 170, cSpawnz+world.rand.nextDouble()-0.5f,1));
		}
		if (f > world.rand.nextDouble()) world.spawnEntity(new EntityRhodes(world, cSpawnx+world.rand.nextDouble()-0.5f, 170, cSpawnz+world.rand.nextDouble()-0.5f,1));
		buildSpawn();
		omegaHealth=RivalRebels.objectiveHealth;
		sigmaHealth=RivalRebels.objectiveHealth;
		PacketDispatcher.packetsys.sendToAll(this);
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
			world.setSpawnPoint(new BlockPos(cSpawnx, world.getHeight(cSpawnx, cSpawnz), cSpawnz));
			omegaHealth=RivalRebels.objectiveHealth;
			sigmaHealth=RivalRebels.objectiveHealth;
			PacketDispatcher.packetsys.sendToAll(this);
		}
	}

	public static class PlayerInvisibility
	{
		public PlayerInvisibility(EntityPlayer p, int i)
		{
			player = p;
			durationleft = i;
		}
		public EntityPlayer player;
		public int durationleft;
	}

	private final List<PlayerInvisibility> players = new ArrayList<>();

	public void setInvisible(EntityPlayer player)
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
				PacketDispatcher.packetsys.sendToAll(this);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateInvisible()
	{
		if (Minecraft.getMinecraft().world == null) return;
		List<EntityPlayer> playerlist = Minecraft.getMinecraft().world.playerEntities;
        for (EntityPlayer player : playerlist) {
            if (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == RivalRebels.camera)
                setInvisible(player);
        }
		for (int i = players.size()-1; i >= 0; i--)
		{
			PlayerInvisibility t = players.get(i);
			t.durationleft--;
			if (t.durationleft <= 0)
			{
                Entity.setRenderDistanceWeight(1);
				players.remove(i);
			}
			else
			{
                Entity.setRenderDistanceWeight(0);
			}
		}
	}

	@SideOnly(Side.CLIENT)
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
            ScorePlayerTeam omega = scrb.createTeam(RivalRebelsTeam.OMEGA.toString());
            ScorePlayerTeam sigma = scrb.createTeam(RivalRebelsTeam.SIGMA.toString());
            omega.setPrefix(TextFormatting.GREEN + "Ω");
            sigma.setPrefix(TextFormatting.BLUE + "Σ");
            omega.setAllowFriendlyFire(false);
            sigma.setAllowFriendlyFire(false);
            ScoreObjective kills = scrb.addScoreObjective("kills", IScoreCriteria.PLAYER_KILL_COUNT);
            scrb.setObjectiveInDisplaySlot(0, kills);
            ScoreObjective deaths = scrb.addScoreObjective("deaths", IScoreCriteria.DEATH_COUNT);
            if (RivalRebels.scoreboardenabled) {
                scrb.setObjectiveInDisplaySlot(1, deaths);
            }
            deaths.setDisplayName("§8R§7I§fV§7A§8L R§7E§fBE§7L§8S");
        } catch(Exception ignored) {} //just in case teams already exist etc

        this.world.getGameRules().setOrCreateGameRule("keepInventory", "true");
        WorldData rivalrebelsgamedata = (WorldData) world.getPerWorldStorage().getOrLoadData(WorldData.class, "rivalrebelsgamedata");
        if (rivalrebelsgamedata == null) return;
        NBTTagCompound nbt = rivalrebelsgamedata.nbt;
        rivalrebelsgamedata.round = this;
        rivalrebelsgamedata.readFromNBT(nbt);
        rivalrebelsgamedata.nbt = null;
    }

    public void save(World world) {
        this.world = world;
        WorldData worldData = new WorldData(this);
        world.getPerWorldStorage().setData("rivalrebelsgamedata", worldData);
	}

    private static class WorldData extends WorldSavedData {
        private RivalRebelsRound round = new RivalRebelsRound();
        private NBTTagCompound nbt;

        public WorldData(String name) {
            super(name);
        }

        public WorldData(RivalRebelsRound round) {
            this("rivalrebelsgamedata");
            this.round = round;
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            this.nbt = nbt;
            ByteBuf buf = Unpooled.wrappedBuffer(nbt.getByteArray("round_data"));
            round.fromBytes(buf);
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {
            ByteBuf buf = Unpooled.buffer();
            round.toBytes(buf);
            compound.setByteArray("round_data", buf.array());
            return compound;
        }
    }

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (!roundstarted) return;
		if (!rrplayerlist.contains(event.player.getGameProfile()))
		{
			event.player.inventory.clear();
			event.player.setLocationAndAngles(cSpawnx, 200, cSpawnz,0,0);
			//rrplayerlist.add(new RivalRebelsPlayer(event.player.getCommandSenderName(), RivalRebelsTeam.NONE, RivalRebelsClass.NONE, RivalRebelsRank.REGULAR, RivalRebels.resetMax));
		}
		PacketDispatcher.packetsys.sendTo(rrplayerlist, (EntityPlayerMP) event.player);
		if (isInSpawn(event.player)) PacketDispatcher.packetsys.sendTo(new GuiSpawnPacket(), (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if (!roundstarted) return;
		event.player.setLocationAndAngles(cSpawnx, 200, cSpawnz,0,0);
		PacketDispatcher.packetsys.sendTo(new GuiSpawnPacket(), (EntityPlayerMP) event.player);
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
					if ((YY > rs4 && YY < spawnRadius2) || ((y1 == -2 || y1 == -3 || y1 == -4) && YY < rs1)) world.setBlockState(new BlockPos(cSpawnx + x1, 200 + y1, cSpawnz + z1), RivalRebels.fshield.getDefaultState());
				}
			}
		}

        omegaObjPos = new BlockPos(cSpawnx + objDist, omegaObjPos.getY(), cSpawnz);
        sigmaObjPos = new BlockPos(cSpawnx - objDist, sigmaObjPos.getY(), cSpawnz);

		Chunk chunk = world.getChunk(omegaObjPos);
        int x = omegaObjPos.getX()&15;
        int z = omegaObjPos.getZ()&15;
        for (omegaObjPos = new BlockPos(omegaObjPos.getX(), chunk.getTopFilledSegment() + 15, omegaObjPos.getZ()); omegaObjPos.getY() > 0; omegaObjPos.down())
        {
            if (chunk.getBlockState(x, omegaObjPos.getY(), z).getBlock() != Blocks.AIR)
            {
                break;
            }
        }
        chunk = world.getChunk(sigmaObjPos);
        x = sigmaObjPos.getX()&15;
        z = sigmaObjPos.getZ()&15;
        sigmaObjPos = new BlockPos(sigmaObjPos.getX(), chunk.getTopFilledSegment() + 15, sigmaObjPos.getZ());
        for (; sigmaObjPos.getY() > 0; sigmaObjPos = sigmaObjPos.down())
        {
            if (chunk.getBlockState(x, sigmaObjPos.getY(), z).getBlock() != Blocks.AIR)
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
						world.setBlockState(omegaObjPos.add(xx, 0, zz), RivalRebels.fshield.getDefaultState());
						world.setBlockState(sigmaObjPos.add(xx, 0, zz), RivalRebels.fshield.getDefaultState());
					}
					else
					{
						world.setBlockState(omegaObjPos.add(xx, 0, zz), RivalRebels.reactive.getDefaultState());
						world.setBlockState(sigmaObjPos.add(xx, 0, zz), RivalRebels.reactive.getDefaultState());
					}
					for (int yy = 1; yy <= 7; yy++)
					{
						world.setBlockToAir(omegaObjPos.add(xx, yy, zz));
						world.setBlockToAir(sigmaObjPos.add(xx, yy, zz));
					}
				}
			}
		}

		world.setBlockState(omegaObjPos.add(21, 0, 21),   RivalRebels.conduit.getDefaultState());
		world.setBlockState(omegaObjPos.add(21, 0, -21),  RivalRebels.conduit.getDefaultState());
		world.setBlockState(omegaObjPos.add(-21, 0, 21),  RivalRebels.conduit.getDefaultState());
		world.setBlockState(omegaObjPos.add(-21, 0, -21), RivalRebels.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(21, 0, 21),   RivalRebels.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(21, 0, -21),  RivalRebels.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(-21, 0, 21),  RivalRebels.conduit.getDefaultState());
		world.setBlockState(sigmaObjPos.add(-21, 0, -21), RivalRebels.conduit.getDefaultState());

		for (int i = 0; i < 4; i++)
		{
			world.setBlockToAir(omegaObjPos.add(21, 1 + i, 21));
			world.setBlockToAir(omegaObjPos.add(21, 1 + i, -21));
			world.setBlockToAir(omegaObjPos.add(-21, 1 + i, 21));
			world.setBlockToAir(omegaObjPos.add(-21, 1 + i, -21));
			world.setBlockToAir(sigmaObjPos.add(21, 1 + i, 21));
			world.setBlockToAir(sigmaObjPos.add(21, 1 + i, -21));
			world.setBlockToAir(sigmaObjPos.add(-21, 1 + i, 21));
			world.setBlockToAir(sigmaObjPos.add(-21, 1 + i, -21));
		}

		world.setBlockState(omegaObjPos.up(), RivalRebels.omegaobj.getDefaultState());
		world.setBlockState(sigmaObjPos.up(), RivalRebels.sigmaobj.getDefaultState());
		if (RivalRebels.rhodesRoundsBase)
		{
			world.setBlockState(omegaObjPos.up(2), RivalRebels.buildrhodes.getDefaultState());
			world.setBlockState(sigmaObjPos.up(2), RivalRebels.buildrhodes.getDefaultState());
		}
	}

	private boolean isInSpawn(EntityPlayer player)
	{
		return player.posY < 203 && player.posY > 198 && player.getDistanceSq(cSpawnx, 200, cSpawnz) < spawnRadius2;
	}

	public void winSigma()
	{
		if (!roundstarted) return;
		winCountdown = 1400;
		sigmaWins++;
		lastwinomega = false;
		world.setBlockState(omegaObjPos, RivalRebels.plasmaexplosion.getDefaultState());
		world.setBlockState(sigmaObjPos, RivalRebels.plasmaexplosion.getDefaultState());
		for (int xpl = -objRadius; xpl < objRadius; xpl++) {
			int xxpl = xpl * xpl;
			for (int zpl = -objRadius; zpl < objRadius; zpl++) {
				int zzpl = zpl * zpl + xxpl;
				if (zzpl < objRadius2) {
                    for (int ypl = -1; ypl < 7; ypl++) {
                        for (int i = 0; i < 16; i++)
                        {
                            world.setBlockToAir(omegaObjPos.add(xpl, ypl, zpl));
                        }
                    }
                }
            }
		}
		PacketDispatcher.packetsys.sendToAll(this);
	}

	public void winOmega()
	{
		if (!roundstarted) return;
		winCountdown = 1400;
		omegaWins++;
		lastwinomega = true;
		world.setBlockState(omegaObjPos, RivalRebels.plasmaexplosion.getDefaultState());
		world.setBlockState(sigmaObjPos, RivalRebels.plasmaexplosion.getDefaultState());
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
						world.setBlockToAir(sigmaObjPos.add(xpl, ypl, zpl));
					}
				}
			}
		}
		PacketDispatcher.packetsys.sendToAll(this);
	}

	public void stopRounds()
	{
		if (!roundstarted) return;
		world.setBlockState(omegaObjPos, RivalRebels.plasmaexplosion.getDefaultState());
		world.setBlockState(sigmaObjPos, RivalRebels.plasmaexplosion.getDefaultState());
		winCountdown = 0;
		roundstarted = false;
		rrplayerlist.clearTeam();
		PacketDispatcher.packetsys.sendToAll(this);
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

	public void setMotD(String[] array)
	{
        MotD = Arrays.stream(array).map(string -> string + " ").collect(Collectors.joining());
		PacketDispatcher.packetsys.sendToAll(this);
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

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
package assets.rivalrebels.common.entity;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.RhodesJumpPacket;
import assets.rivalrebels.common.packet.RhodesPacket;
import assets.rivalrebels.common.packet.TextPacket;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
import assets.rivalrebels.common.tileentity.TileEntityRhodesActivator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import net.minecraftforge.network.PacketDistributor;

import java.util.*;

public class EntityRhodes extends Entity {
    public static final TrackedData<Boolean> FIRE = DataTracker.registerData(EntityRhodes.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> PLASMA = DataTracker.registerData(EntityRhodes.class, TrackedDataHandlerRegistry.BOOLEAN);

    public int health = RivalRebels.rhodesHealth;
	public float scale = 1;
	private int damageUntilWake = 100;
	private static final Set<Block> blocklist = new HashSet<>();
	static {
		blocklist.add(Blocks.BEDROCK);
		blocklist.add(Blocks.DIRT);
		blocklist.add(Blocks.STONE);
		blocklist.add(Blocks.SAND);
		blocklist.add(Blocks.SANDSTONE);
		blocklist.add(Blocks.GRAVEL);
		blocklist.add(Blocks.IRON_BLOCK);
		blocklist.add(Blocks.LAPIS_BLOCK);
		blocklist.add(Blocks.STONE_SLAB);
		blocklist.add(Blocks.STONE_BRICKS);
		blocklist.add(Blocks.COBBLESTONE);
		blocklist.add(Blocks.OBSIDIAN);
		blocklist.add(Blocks.NETHERRACK);
		blocklist.add(Blocks.END_STONE);
		blocklist.add(Blocks.TERRACOTTA);
		//blocklist.add(Blocks.GLAZED_TERRACOTTA);
		blocklist.add(RRBlocks.reactive);
	}
	public static String[] names =
	{
		"Rhodes",
		"Magnesium",
		"Arsenic",
		"Vanadium",
		"Aurum",
		"Iodine",
		"Iron",
		"Astatine",
		"Cobalt",
		"Strontium",
		"Bismuth",
		"Zinc",
		"Osmium",
		"Neon",
		"Argent",
		"Wolfram",
		"Space"
	};
	public float bodyyaw = 0;
	public float headyaw = 0;
	public float headpitch = 0;
	public float leftarmyaw = 0;
	public float leftarmpitch = 0;
	public float rightarmyaw = 0;
	public float rightarmpitch = 0;
	public float leftthighpitch = 0;
	public float rightthighpitch = 0;
	public float leftshinpitch = 0;
	public float rightshinpitch = 0;
	public float lastbodyyaw = 0;
	public float lastheadyaw = 0;
	public float lastheadpitch = 0;
	public float lastleftarmyaw = 0;
	public float lastleftarmpitch = 0;
	public float lastrightarmyaw = 0;
	public float lastrightarmpitch = 0;
	public float lastleftthighpitch = 0;
	public float lastrightthighpitch = 0;
	public float lastleftshinpitch = 0;
	public float lastrightshinpitch = 0;
    public boolean endangered = false;
	public int flying = 0;

	public int ticksSinceLastPacket = 0;
	public byte laserOn = 0; // 0 = off, 1 = top, 2 = bottom, 3 = both
	public byte colorType = 0;
	public static byte lastct = 0;
	public static int forcecolor = -1;
	public PlayerEntity rider;
	public PlayerEntity passenger1;
	public PlayerEntity passenger2;
	public static final int recharge = 3;
	public static final int ecjet = 10+recharge;
	public static final int eclaser = 6+recharge;
	public static final int ecshield = 8+recharge;
	public static final int maxenergy = 800;
	public int energy = maxenergy;
	public int b2energy = 0;
	public int nukecount = 8;
	public int rocketcount = 5000;
	public int flamecount = 10000;
	public boolean rocket = false;
	public boolean laser = false;
	public boolean prevflame = false;
	public boolean flame = false;
	public boolean forcefield = false;
    public boolean bomb = false;
	public boolean jet = false;
	public boolean stop = true;
	public boolean guard = false;
	public boolean b2spirit = false;
	public boolean freeze = false;
	public int plasmacharge = 0;
	public int tickssincenuke = 10;
	public static String texloc;
	public static int texfolder = -1;
	public String itexloc;
	public int itexfolder;

	public int wakeX = -1;
	public int wakeY = -1;
	public int wakeZ = -1;

    public EntityRhodes(EntityType<? extends EntityRhodes> type, World world) {
        super(type, world);
    }

	public EntityRhodes(World w)
	{
		this(RREntities.RHODES, w);
		ignoreCameraFrustum = true;
		setBoundingBox(new Box(-5*scale, -15*scale, -5*scale, 5*scale, 15*scale, 5*scale));
		noClip = true;
		//pushSpeedReduction = 100;
		stepHeight = 0;
		actionqueue.add(new RhodesAction(0, 1));
		RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
		itexfolder = texfolder;
		if (texfolder != -1)
		{
			itexloc = texloc;
		}
		if (forcecolor == -1)
		{
			colorType = (byte) RivalRebels.rhodesTeams[lastct];
			if (!w.isClient)
			{
				lastct++;
				if (lastct == RivalRebels.rhodesTeams.length) lastct = 0;
			}
		}
		else
		{
			colorType = (byte) forcecolor;
		}
		Random random = new Random(RivalRebels.rhodesRandomSeed);
		nukecount = RivalRebels.rhodesNukes;
		nukecount += nukecount * random.nextFloat() * RivalRebels.rhodesRandom;
		rocketcount += rocketcount * random.nextFloat() * RivalRebels.rhodesRandom;
		flamecount += flamecount * random.nextFloat() * RivalRebels.rhodesRandom;
	}

	public EntityRhodes(World w, double x, double y, double z, float s)
	{
		this(w);
		scale = s;
		if (scale >= 2.0)
		{
			nukecount *= 0.25;
			rocketcount *= 0.004;
		}
		health = health - 5000 + (int)(5000 * Math.min(scale,4));
		setBoundingBox(new Box(-5*scale, -15*scale, -5*scale, 5*scale, 15*scale, 5*scale));
		setPosition(x, y, z);
		if (!world.isClient) {
            for (PlayerEntity player : world.getPlayers()) {
                player.sendMessage(new TranslatableText(RivalRebels.MODID + ".warning_tsar_is_armed", getName()), false);
            }
        }
	}

    @Override
	public void tick() {
		if ((wakeY != -1) && (getBlock(wakeX, wakeY, wakeZ) != RRBlocks.rhodesactivator))
		{
			damageUntilWake -= 100;

		}
		if (getY() <= 0) {
			kill();
			return;
		}
        slowMovement(Blocks.COBWEB.getDefaultState(), new Vec3d(0.25, 0.05F, 0.25));
        fallDistance = 0.0F;
		if (health > 0) {
			float headY = 0;
			float syaw = MathHelper.sin(bodyyaw * 0.01745329252f);
			float cyaw = MathHelper.cos(bodyyaw * 0.01745329252f);
			float leftlegheight = 7.26756f
					+ (MathHelper.cos((leftthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
					+ (MathHelper.cos((leftthighpitch+leftshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
			float rightlegheight = 7.26756f
					+ (MathHelper.cos((rightthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
					+ (MathHelper.cos((rightthighpitch+rightshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
			leftlegheight *= scale;
			rightlegheight *= scale;
			float bodyY = Math.max(leftlegheight, rightlegheight);
			if (!world.isClient)
			{
				doAITick(syaw, cyaw);
				breakBlocks(syaw, cyaw, leftlegheight, rightlegheight, bodyY);
				if (age % 5 == 0) PacketDispatcher.packetsys.send(PacketDistributor.ALL.noArg(), new RhodesPacket(this));
			}
            Vec3d add = getVelocity().add(getPos());
            setPos(add.getX(), add.getY(), add.getZ());
			if (age % 3 == 0) doCollisions();
			ticksSinceLastPacket++;
			setPosition(getX(), getY(), getZ());

			RivalRebels.proxy.setOverlay(this);

			if (rider!=null)
			{
				rider.setPosition(((getX()+syaw*5.5*scale) - rider.getX()) * 0.33f + rider.getX(), ((getY() + bodyY - 10*scale - (world.isClient?0:rider.getEyeHeight(rider.getPose()))) - rider.getY()) * 0.33f + rider.getY(), ((getZ()+cyaw*5.5*scale) - rider.getZ()) * 0.33f + rider.getZ());
				rider.setOnGround(true);
				if (world.isClient) RivalRebels.round.setInvisible(rider);
				rider.slowMovement(Blocks.COBWEB.getDefaultState(), new Vec3d(0.25, 0.05F, 0.25));
				rider.getAbilities().invulnerable = true;
				if (world.isClient && rider == MinecraftClient.getInstance().player) PacketDispatcher.packetsys.sendToServer(new RhodesJumpPacket(this.getId(), RivalRebels.proxy.spacebar(), RivalRebels.proxy.a(), RivalRebels.proxy.w(), RivalRebels.proxy.d(), RivalRebels.proxy.c(), RivalRebels.proxy.f(), RivalRebels.proxy.s(), RivalRebels.proxy.x(), RivalRebels.proxy.z(), RivalRebels.proxy.g()));
			}
			if (passenger1 != null)
			{
				float offset = 1.62f;
				if (world.isClient)
				{
					if (MinecraftClient.getInstance().player == passenger1)
					{
						offset = 0;
					}
				}
				passenger1.setPosition(getX()+cyaw*6.5f*scale,
										getY() + bodyY - 6.38f*scale - offset,
										getZ()-syaw*6.5f*scale);
				passenger1.setOnGround(true);
				passenger1.slowMovement(Blocks.COBWEB.getDefaultState(), new Vec3d(0.25, 0.05F, 0.25));
			}
			if (passenger2 != null)
			{
				float offset = 1.62f;
				if (world.isClient)
				{
					if (MinecraftClient.getInstance().player == passenger2)
					{
						offset = 0;
					}
				}
				passenger2.setPosition(getX()-cyaw*6.5f*scale,
										getY() + bodyY - 6.38f*scale - offset,
										getZ()+syaw*6.5f*scale);
				passenger2.setOnGround(true);
				passenger2.slowMovement(Blocks.COBWEB.getDefaultState(), new Vec3d(0.25, 0.05F, 0.25));
			}
		}
		else
		{
			if (!world.isClient)
			{
				if (health == 0) {
                    PacketDispatcher.packetsys.send(PacketDistributor.ALL.noArg(), new TextPacket("RivalRebels.Status " + getName() + " RivalRebels.meltdown" + (rider == null ? "" : " " + rider.getName())));
                }
				if (age % 5 == 0) {
                    PacketDispatcher.packetsys.send(PacketDistributor.ALL.noArg(), new RhodesPacket(this));
                }
				if (health < -100)
				{
					kill();
				}
				if (health == 0)
				{
					float syaw = MathHelper.sin(bodyyaw * 0.01745329252f);
					float cyaw = MathHelper.cos(bodyyaw * 0.01745329252f);
					world.spawnEntity(new EntityRhodesHead(world, getX(), getY()+13*scale, getZ(), scale, colorType));
					world.spawnEntity(new EntityRhodesTorso(world, getX(), getY()+7*scale, getZ(), scale, colorType));
					world.spawnEntity(new EntityRhodesLeftUpperArm(world, getX()+cyaw*6.4*scale, getY()+7*scale, getZ()+syaw*6.4*scale,scale, colorType));
					world.spawnEntity(new EntityRhodesRightUpperArm(world, getX()-cyaw*6.4*scale, getY()+7*scale, getZ()-syaw*6.4*scale,scale, colorType));
					world.spawnEntity(new EntityRhodesLeftLowerArm(world, getX()+cyaw*6.4*scale, getY()+3*scale, getZ()+syaw*6.4*scale,scale, colorType));
					world.spawnEntity(new EntityRhodesRightLowerArm(world, getX()-cyaw*6.4*scale, getY()+3*scale, getZ()-syaw*6.4*scale,scale, colorType));
					world.spawnEntity(new EntityRhodesLeftUpperLeg(world, getX()+cyaw*3*scale, getY()-3*scale, getZ()+syaw*3*scale,scale, colorType));
					world.spawnEntity(new EntityRhodesRightUpperLeg(world, getX()-cyaw*3*scale, getY()-3*scale, getZ()-syaw*3*scale,scale, colorType));
					world.spawnEntity(new EntityRhodesLeftLowerLeg(world, getX()+cyaw*3*scale, getY()-10*scale, getZ()+syaw*3*scale,scale, colorType));
					world.spawnEntity(new EntityRhodesRightLowerLeg(world, getX()-cyaw*3*scale, getY()-10*scale, getZ()-syaw*3*scale,scale, colorType));
				}
			}
			health--;
		}

		if (rider != null)
		{
			if ((rider.isSneaking() || !rider.isAlive()) && RivalRebels.rhodesExit)
			{
				freeze = false;
				if (!rider.getAbilities().creativeMode) rider.getAbilities().invulnerable = false;
				rider = null;
			}
			if (health <= 0 && rider != null)
			{
				freeze = false;
				if (!rider.getAbilities().creativeMode)
				{
					rider.getAbilities().invulnerable = false;
					rider.damage(DamageSource.OUT_OF_WORLD, 2000000);
				}
				rider = null;
			}
		}

		if (passenger1 != null)
		{
			if ((passenger1.isSneaking() || !passenger1.isAlive()) && RivalRebels.rhodesExit)
			{
				if (!passenger1.getAbilities().creativeMode) passenger1.getAbilities().invulnerable = false;
				passenger1 = null;
			}
			if (health <= 0 && passenger1 != null)
			{
				if (!passenger1.getAbilities().creativeMode)
				{
					passenger1.getAbilities().invulnerable = false;
					passenger1.damage(DamageSource.OUT_OF_WORLD, 2000000);
				}
				passenger1 = null;
			}
		}

		if (passenger2 != null)
		{
			if ((passenger2.isSneaking() || !passenger2.isAlive()) && RivalRebels.rhodesExit)
			{
				if (!passenger2.getAbilities().creativeMode) passenger2.getAbilities().invulnerable = false;
				passenger2 = null;
			}
			if (health <= 0 && passenger2 != null)
			{
				if (!passenger2.getAbilities().creativeMode)
				{
					passenger2.getAbilities().invulnerable = false;
					passenger2.damage(DamageSource.OUT_OF_WORLD, 2000000);
				}
				passenger2 = null;
			}
		}

		prevflame = flame;
	}

	private void breakBlocks(float syaw, float cyaw, float leftlegheight, float rightlegheight, float bodyY)
	{
		float leftlegstride = (MathHelper.sin((leftthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
				+ (MathHelper.sin((leftthighpitch+leftshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
		float rightlegstride = (MathHelper.sin((rightthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
				+ (MathHelper.sin((rightthighpitch+rightshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
		leftlegstride *= scale;
		rightlegstride *= scale;

		float lpx = (float)getX() - syaw * leftlegstride + cyaw * 3.6846f*scale;
		float lpy = (float)getY()-15*scale + bodyY - leftlegheight;
		float lpz = (float)getZ() - cyaw * leftlegstride - syaw * 3.6846f*scale;
		float rpx = (float)getX() - syaw * rightlegstride - cyaw * 3.6846f*scale;
		float rpy = (float)getY()-15*scale + bodyY - rightlegheight;
		float rpz = (float)getZ() - cyaw * rightlegstride + syaw * 3.6846f*scale;
		int ilpx = (int)lpx;
		int ilpy = (int)lpy;
		int ilpz = (int)lpz;
		int irpx = (int)rpx;
		int irpy = (int)rpy;
		int irpz = (int)rpz;

		if (ac > 1)
		{
			if (RivalRebels.rhodesBlockBreak > 0.0f)
			{
				int sx = (int) (getX() - 5.0f * scale);
				int sy = (int) (getY() - 15.0f * scale);
				int sz = (int) (getZ() - 5.0f * scale);
				int ex = (int) (getX() + 5.0f * scale);
				int ey = (int) (getY() + 15.0f * scale);
				int ez = (int) (getZ() + 5.0f * scale);
				for (int y = sy; y < ey; y++)
				{
					if ((y + age) % 8 == 0)
					{
						for (int x = sx; x < ex; x++)
						{
							for (int z = sz; z < ez; z++)
							{
								setBlock(x,y,z, Blocks.AIR);
							}
						}
					}
				}
			}
			else
			{
				int irpyyoff = irpy + (age % 6);
				int ilpyyoff = ilpy + (age % 6);
				Block b = getBlock(irpx, irpyyoff, irpz);
				if (b != Blocks.WATER && b != Blocks.AIR)
				{
					setBlock(irpx-2, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz+2, Blocks.AIR);
				}
				b = getBlock(ilpx, ilpyyoff, ilpz);
				if (b != Blocks.WATER && b != Blocks.AIR)
				{
					setBlock(ilpx-2, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz+2, Blocks.AIR);
				}
				int px = (int) getX();
				int py = (int) (getY()-5*scale + (age%20)*scale);
				int pz = (int) getZ();
				for (int x = -4; x < 5; x++)
				{
					for (int z = -4; z < 5; z++)
					{
						b = getBlock(px+x, py, pz+z);
						if (b != Blocks.AIR && b != Blocks.WATER)
						{
							setBlock(px+x, py, pz+z, Blocks.AIR);
							if (random.nextInt(333)==0)
							{
								new Explosion(world, px, py, pz, 3, false, true, RivalRebelsDamageSource.rocket);
								RivalRebelsSoundPlayer.playSound(this, 23, 3, 4.5f, (float) (0.8f + random.nextDouble()*0.3f));
							}
						}
					}
				}
			}
		}

		boolean bl = blocklist.contains(getBlock(ilpx, ilpy-1, ilpz));
		boolean ol = blocklist.contains(getBlock(ilpx, ilpy, ilpz));
		boolean br = blocklist.contains(getBlock(irpx, irpy-1, irpz));
		boolean or = blocklist.contains(getBlock(irpx, irpy, irpz));

		if (bl||br)
		{
			if (ol||or)
			{
				if (getVelocity().getY() < 0.125)
				{
                    setVelocity(getVelocity().getX(), 0.125, getVelocity().getZ());
					if (!isFire() && random.nextInt(32)==0)
					{
						RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
						setFire(true);
					}
				}
			} else if (getVelocity().getY() < 0) {
                setVelocity(getVelocity().getX(), 0, getVelocity().getZ());
                setFire(false);
                setPos(getX(), (int) getY(), getZ());
			}
		}
		else if (flying == 0) {
            addVelocity(0, -0.03f, 0);
			if (!isFire() && random.nextInt(32)==0)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
				setFire(true);
			}
		}
		if (flying!=0)
		{
			flying--;
			if (b2spirit)
			{
                setVelocity(getVelocity().getX(), 0.03, getVelocity().getZ());
			}
			else
			{
                addVelocity(0, 0.03f, 0);
			}
			if (random.nextInt(32)==0)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
			}
			setFire(true);
		}
	}

	private void doCollisions() {
        for (Entity e : world.getOtherEntities(this, IForgeBlockEntity.INFINITE_EXTENT_AABB)) {
            if (e == rider || e == passenger1 || e == passenger2) continue;
            double bbd = (e.getWidth() + 10 * scale) * 0.5;
            double bbh = (e.getHeight() + 30 * scale) * 0.5;
            if (e instanceof EntityRhodes) {
                bbd = 10 * (((EntityRhodes) e).scale + scale) * 0.5;
                bbh = 30 * (((EntityRhodes) e).scale + scale) * 0.5;
            }
            double dist = (e.getX() - getX()) * (e.getX() - getX()) + (e.getZ() - getZ()) * (e.getZ() - getZ());
            if ((ac == 0 || ac == 1 || ac == 11 || !RivalRebels.rhodesAI) && e instanceof PlayerEntity && dist < bbd * bbd * 0.25f && e.getY() < getY() + bbh + 1 && e.getY() > getY() - bbh + 1) {
                if (rider == null) {
                    rider = (PlayerEntity) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                } else if (passenger1 == null) {
                    passenger1 = (PlayerEntity) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                } else if (passenger2 == null) {
                    passenger2 = (PlayerEntity) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                }
            } else if (dist < bbd * bbd && e.getY() > getY() - bbh && e.getY() < getY() + bbh) {
                if (e != this && !(e instanceof FallingBlockEntity) && !(e instanceof EntityDebris)) {
                    if (e.getY() > getY() + 15) {
                        e.setVelocity(getVelocity().multiply(1, -0.5, 1));
                        e.setPos(getX(), getY() + bbh, getZ());
                    } else if (e.getY() < getY() - 15) {
                        e.setVelocity(getVelocity().multiply(1, -0.5, 1));
                        e.setPos(getX(), getY() - bbh, getZ());
                    } else {
                        e.setVelocity(getVelocity().multiply(-1, 1, -1));
                        double d3 = bbd / Math.sqrt(dist);
                        e.setPos(getX() + (e.getX() - getX()) * d3,
                             e.getY(),
                            getZ() + (e.getZ() - getZ()) * d3);
                    }
                    e.setPosition(e.getX(), e.getY(), e.getZ());
                }

                if (e instanceof EntityRocket) {
                    e.age = RivalRebels.rpgDecay;
                    this.damage(DamageSource.GENERIC, 20);
                } else if (e instanceof EntitySeekB83) {
                    e.age = 800;
                    this.damage(DamageSource.GENERIC, 24);
                } else if (e instanceof EntityHackB83) {
                    ((EntityHackB83) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 40);
                } else if (e instanceof EntityB83) {
                    ((EntityB83) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 40);
                } else if (e instanceof EntityBomb) {
                    ((EntityBomb) e).explode(true);
                    for (int i = 0; i < RivalRebels.bombDamageToRhodes; i++)
                        this.damage(DamageSource.GENERIC, 50);
                } else if (e instanceof EntityNuke) {
                    ((EntityNuke) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 80);
                } else if (e instanceof EntityTsar) {
                    ((EntityTsar) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 100);
                } else if (e instanceof EntityTheoreticalTsar) {
                    ((EntityTheoreticalTsar) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 100);
                } else if (e instanceof EntityAntimatterBomb) {
                    ((EntityAntimatterBomb) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 100);
                } else if (e instanceof EntityTachyonBomb) {
                    ((EntityTachyonBomb) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 100);
                } else if (e instanceof EntityHotPotato) {
                    ((EntityHotPotato) e).age = -100;
                    this.damage(DamageSource.GENERIC, 100);
                } else if (e instanceof EntityB83NoShroom) {
                    ((EntityB83NoShroom) e).ticksInAir = -100;
                    this.damage(DamageSource.GENERIC, 40);
                } else if (e instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) e).explode();
                    this.damage(DamageSource.GENERIC, 8);
                } else if (e instanceof EntityFlameBall) {
                    e.kill();
                    this.damage(DamageSource.GENERIC, 3);
                } else if (e instanceof EntityFlameBall1) {
                    e.kill();
                    this.damage(DamageSource.GENERIC, 4);
                } else if (e instanceof EntityFlameBall2) {
                    e.kill();
                    this.damage(DamageSource.GENERIC, 2);
                } else if (e instanceof EntityLaserBurst) {
                    e.kill();
                    this.damage(DamageSource.GENERIC, 4);
                }
            }
        }
	}

	private static class RhodesAction
	{
		public int action;
		public int duration;
		public RhodesAction(int a, int d)
		{
			action = a;
			duration = d;
		}
	}

	private int ac = 0;
	private int counter = 0;
	private Queue<RhodesAction> actionqueue = new LinkedList<>();
	private int teamToRaid;
	private boolean raidedOmegaAlready = false;
	private boolean raidedSigmaAlready = false;
	private void doAITick(float syaw, float cyaw)
	{
		if (health*2 < RivalRebels.rhodesHealth) endangered = true;
		if (!b2spirit) {
            setVelocity(0, getVelocity().getY(), 0);
		}
		if (rider != null)
		{
			if (b2spirit && !freeze && b2energy == 0 && scale < 1.5f && scale > 0.5f)
			{
				freeze = true;
				nukecount--;
				health -= 1000;
				world.spawnEntity(new EntityB2Spirit(this));
			}
			if (RivalRebels.rhodesHold) return;
			if (energy < maxenergy) energy += recharge;
			if (!RivalRebels.infiniteAmmo)
			{
				rocket &= rocketcount > 0;
				flame &= flamecount > 0;
			}
			if (!RivalRebels.infiniteNukes)
			{
				bomb &= nukecount > 0;
			}
			forcefield &= energy > ecshield;
			laser &= energy > eclaser;
			jet &= (energy+b2energy) > ecjet;
			b2spirit &= b2energy > 0;

			if (forcefield)
			{
				energy -= ecshield;
				if (age%8==0)	RivalRebelsSoundPlayer.playSound(this, 5, 0, 10f, 1f);
			}
			if (laser) energy -= eclaser;
			if (jet || b2spirit)
			{
				if (b2energy > 0)
				{
					b2energy -= ecjet;
					if (b2energy <= 0)
					{
						b2energy = 0;
						world.createExplosion(null, this.getX(), this.getY(), this.getZ(), 6.0F, net.minecraft.world.explosion.Explosion.DestructionType.DESTROY);
						world.spawnEntity(new EntityB2Frag(world, this, 0));
						world.spawnEntity(new EntityB2Frag(world, this, 1));
					}
				}
				else
				{
					energy -= ecjet;
				}
				flying = 3;
			}
			else
			{
				flying = 0;
			}
			laserOn = 0;
			if (!stop)
			{
				float goal = ((((rider.headYaw+bodyyaw)%360)+360+360)%360)-180;
				bodyyaw += Math.max(Math.min(goal, 2), -2);
				if (flying > 0)
				{
					if (b2spirit)
					{
                        setVelocity(getVelocity().add(syaw * 0.1f, 0, cyaw * 0.1f));
						double speed = Math.sqrt(getVelocity().getX() * getVelocity().getX() + getVelocity().getZ() * getVelocity().getZ());
						if (speed > 0.7) {
                            Vec3d vec3d = getVelocity().multiply(1 / speed).multiply(0.7);
                            setVelocity(vec3d.getX(), getVelocity().getY(), vec3d.getZ());
						}
					}
					else
					{
                        setVelocity(syaw * 0.5f, getVelocity().getY(), cyaw * 0.5f);
					}
					rightthighpitch = approach(rightthighpitch,-30);
					leftthighpitch  = approach(leftthighpitch, -30);
					rightshinpitch  = approach(rightshinpitch, 60);
					leftshinpitch   = approach(leftshinpitch,  60);
				}
				else
				{
					doWalkingAnimation(syaw, cyaw);
				}
			}
			else
			{
				flying = 0;
				rightthighpitch = approach(rightthighpitch,0);
				leftthighpitch  = approach(leftthighpitch, 0);
				rightshinpitch  = approach(rightshinpitch, 0);
				leftshinpitch   = approach(leftshinpitch,  0);
			}
			double startx = rider.getX();
			double starty = rider.getY() + rider.getEyeHeight(rider.getPose());
			double startz = rider.getZ();
			double range = 100;
			double endx = startx + range * (-MathHelper.sin(rider.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(rider.getPitch() / 180.0F * (float) Math.PI));
			double endy = starty + range * (-MathHelper.sin(rider.getPitch() / 180.0F * (float) Math.PI));
			double endz = startz + range * (MathHelper.cos(rider.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(rider.getPitch() / 180.0F * (float) Math.PI));

			Vec3d hit = rayTraceBlocks((float)startx, (float)starty, (float)startz, (float)endx, (float)endy, (float)endz);

			if (hit != null)
			{
				endx = hit.x;
				endy = hit.y;
				endz = hit.z;
			}
			/*if (b2spirit && tickssincenuke >= 40 && nukecount > 0 && health > 2000)
 			{
				tickssincenuke = 0;
				nukecount--;
				health -= 1000;
				world.spawnEntity(new EntityB2Spirit(world, endx, endy, endz, getX(), getY(), getZ(), null, false, false));
 			}*/

			if (laser)
			{
				laserOn = (byte)(world.random.nextInt(2)+1);
				RivalRebelsSoundPlayer.playSound(this, 22, 1, 30f, 0f);
				float x = (float) (getX() - endx);
				float y = (float) (getY() + 13*scale - endy);
				float z = (float) (getZ() - endz);
				float pitch = (720f-atan2((float)Math.sqrt(x*x+z*z)*(syaw*x+cyaw*z>0?-1f:1f), y)) %360-270;

				headpitch += Math.max(-20, Math.min(20, (pitch-headpitch)));

				if (Math.abs(headpitch-pitch) < 10f && age % 3 == 0)
				{
					range = 70*scale;
					Vec3d start = new Vec3d(getX(), getY()+13*scale, getZ());
					Vec3d end = new Vec3d(0, 0, range);
					end = end.rotateX(-headpitch / 180.0F * (float) Math.PI);
					end = end.rotateY(bodyyaw / 180.0F * (float) Math.PI);
					end = end.add(getX(), getY()+13*scale, getZ());
                    for (Entity e : world.getOtherEntities(this, new Box(
                        Math.min(start.x, end.x) - 5,
                            Math.min(start.y, end.y) - 5,
                            Math.min(start.z, end.z) - 5,
                            Math.max(start.x, end.x) + 5,
                            Math.max(start.y, end.y) + 5,
                            Math.max(start.z, end.z) + 5))) {
                        if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) &&
                                !(e instanceof ThrownEntity
                                        || e instanceof EntityInanimate
                                        || e instanceof ItemEntity
                                        || e instanceof BoatEntity
                                        || e instanceof MinecartEntity)
                                && e != rider) {
                            Vec3d entity = new Vec3d(e.getX(), e.getY(), e.getZ());
                            double bbx = 1;
                            if (e instanceof EntityRhodes) bbx = 20 * ((EntityRhodes) e).scale;
                            if (entity.subtract(start).crossProduct(entity.subtract(end)).squaredDistanceTo(0, 0, 0) < 10000 * bbx) {
                                e.damage(RivalRebelsDamageSource.laserburst, 24);
                                if (e instanceof PlayerEntity player) {
                                    DefaultedList<ItemStack> armorSlots = player.getInventory().armor;
                                    int i = world.random.nextInt(4);
                                    if (!armorSlots.get(i).isEmpty()) {
                                        armorSlots.get(i).damage(24, player, player1 -> {});
                                    }
                                    if (player.getHealth() < 3 && player.isAlive()) {
                                        player.damage(RivalRebelsDamageSource.laserburst, 2000000);
                                        player.deathTime = 0;
                                        world.spawnEntity(new EntityGore(world, e, 0, 0));
                                        world.spawnEntity(new EntityGore(world, e, 1, 0));
                                        world.spawnEntity(new EntityGore(world, e, 2, 0));
                                        world.spawnEntity(new EntityGore(world, e, 2, 0));
                                        world.spawnEntity(new EntityGore(world, e, 3, 0));
                                        world.spawnEntity(new EntityGore(world, e, 3, 0));
                                    }
                                } else {
                                    if (!e.isAlive() || (e instanceof LivingEntity && ((LivingEntity) e).getHealth() < 3)) {
                                        int legs = -1;
                                        int arms = -1;
                                        int mobs = -1;
                                        RivalRebelsSoundPlayer.playSound(this, 2, 1, 4);
                                        if (e instanceof ZombifiedPiglinEntity) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 2;
                                        } else if (e instanceof ZombieEntity) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 1;
                                        } else if (e instanceof SkeletonEntity) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 3;
                                        } else if (e instanceof EndermanEntity) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 4;
                                        } else if (e instanceof CreeperEntity) {
                                            legs = 4;
                                            arms = 0;
                                            mobs = 5;
                                        } else if (e instanceof MagmaCubeEntity) {
                                            legs = 0;
                                            arms = 0;
                                            mobs = 7;
                                        } else if (e instanceof SlimeEntity) {
                                            legs = 0;
                                            arms = 0;
                                            mobs = 6;
                                        } else if (e instanceof CaveSpiderEntity) {
                                            legs = 8;
                                            arms = 0;
                                            mobs = 9;
                                        } else if (e instanceof SpiderEntity) {
                                            legs = 8;
                                            arms = 0;
                                            mobs = 8;
                                        } else if (e instanceof GhastEntity) {
                                            legs = 9;
                                            arms = 0;
                                            mobs = 10;
                                        } else if (e instanceof EntityB2Spirit
                                                || e instanceof EntityRhodes
                                                || e instanceof EntityRhodesPiece) {
                                            return;
                                        } else {
                                            legs = (int) (e.getBoundingBox().getAverageSideLength() * 2);
                                            arms = (int) (e.getBoundingBox().getAverageSideLength() * 2);
                                            mobs = 11;
                                        }
                                        world.spawnEntity(new EntityGore(world, e, 0, mobs));
                                        world.spawnEntity(new EntityGore(world, e, 1, mobs));
                                        for (int i = 0; i < arms; i++)
                                            world.spawnEntity(new EntityGore(world, e, 2, mobs));
                                        for (int i = 0; i < legs; i++)
                                            world.spawnEntity(new EntityGore(world, e, 3, mobs));
                                        e.kill();
                                    }
                                }
                            }
                        }
                    }
				}
			}
			else
			{
				headpitch = rider.getPitch();
			}

			if (flame || prevflame)
			{
				float px = (float)getX()-cyaw*6.4f*scale;
				float py = (float)getY()+6.26759f*scale;
				float pz = (float)getZ()+syaw*6.4f*scale;
				float x = px - (float)endx;
				float y = py - (float)endy;
				float z = pz - (float)endz;
				float yaw = ((atan2(x, z)-bodyyaw+810)%360)-270;
				float pitch = -(atan2((float)Math.sqrt(x*x+z*z), y));
				rightarmyaw += Math.max(-3, Math.min(3, (yaw-rightarmyaw)));
				rightarmpitch += Math.max(-3, Math.min(3, (pitch-rightarmpitch)));

				if (isPlasma())
				{
					plasmacharge++;
					if (Math.abs(rightarmyaw-yaw) < 3f && Math.abs(rightarmpitch-pitch) < 3f)
					{
						if (!flame && prevflame)
						{
							float f = plasmacharge / 20.0F;
							f = (f * f + f * 2) * 0.3333f;
							if (f > 1.0F) f = 1.0F;
							f+=0.7f;
							RivalRebelsSoundPlayer.playSound(this, 16, 2, 1, 0.5f);
							float cp = -f/(float)Math.sqrt(x*x+y*y+z*z)*scale;
							x*=cp;
							y*=cp;
							z*=cp;
							world.spawnEntity(new EntityPlasmoid(world, px, py, pz,
									x, y, z, 8));
							flamecount -= plasmacharge;
							plasmacharge = 0;
						}
					}
					if (flame && !prevflame)
					{
						RivalRebelsSoundPlayer.playSound(this, 16, 1, 0.0f);
					}
				}
				else
				{
					if (Math.abs(rightarmyaw-yaw) < 3f && Math.abs(rightarmpitch-pitch) < 3f)
					{
						flamecount--;
						RivalRebelsSoundPlayer.playSound(this, 8, 1, 1f);
						float cp = -1f/(float)Math.sqrt(x*x+y*y+z*z);
						x*=cp;
						y*=cp;
						z*=cp;
						world.spawnEntity(new EntityFlameBall(world, px, py, pz,
								x, y, z, (8+random.nextDouble()*8)*scale, 0.4f));
						world.spawnEntity(new EntityFlameBall(world, px, py, pz,
								x, y, z, (8+random.nextDouble()*8)*scale, 0.4f));
					}
				}
			}
			tickssincenuke++;
			if (rocket || bomb)
			{
				float px = (float)getX()+cyaw*6.4f*scale;
				float py = (float)getY()+6.26759f*scale;
				float pz = (float)getZ()-syaw*6.4f*scale;
				float x = px - (float)endx;
				float y = py - (float)endy;
				float z = pz - (float)endz;
				float yaw = ((atan2(x, z)-bodyyaw+630)%360)-90;
				float pitch = -(atan2((float)Math.sqrt(x*x+z*z), y));
				leftarmyaw += Math.max(-3, Math.min(3, (yaw-leftarmyaw)));
				leftarmpitch += Math.max(-3, Math.min(3, (pitch-leftarmpitch)));

				if (Math.abs(leftarmyaw-yaw) < 3f && Math.abs(leftarmpitch-pitch) < 3f)
				{
					if (bomb && !rocket)
					{
						if (tickssincenuke >= 10)
						{
							tickssincenuke = 0;
							nukecount--;
							RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
							float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);
							if (scale >= 3.0)
								world.spawnEntity(new EntityHotPotato(world, px, py, pz,
										x*cp*5.0f, y*cp*5.0f, z*cp*5.0f));
							else if (scale >= 2.0)
								world.spawnEntity(new EntityTsar(world, px, py, pz,
										x*cp*5.0f, y*cp*5.0f, z*cp*5.0f));
							else
								world.spawnEntity(new EntityB83NoShroom(world, px, py, pz,
										x*cp, y*cp, z*cp));
						}
					}

					if (rocket && age-lastshot > ((scale >= 2.0)?30:((shotstaken == 21)?80:5)))
					{
						rocketcount--;
						lastshot = age;
						if (shotstaken == 21) shotstaken = 0;
						shotstaken++;
						RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
						float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);

						if (scale >= 2.0)
							world.spawnEntity(new EntityB83NoShroom(world, px, py, pz,
									x*cp, y*cp, z*cp));
						else
							world.spawnEntity(new EntitySeekB83(world, px, py, pz,
									x*cp, y*cp, z*cp));
					}
				}
			}
			ac = 11;
			return;
		}

		if (RivalRebels.rhodesHold || guard)
		{
			rightthighpitch = approach(rightthighpitch,0);
			leftthighpitch  = approach(leftthighpitch, 0);
			rightshinpitch  = approach(rightshinpitch, 0);
			leftshinpitch   = approach(leftshinpitch,  0);
			if (RivalRebels.rhodesAI)
			{
				shootRocketsAtBestTarget(-syaw, cyaw);
				shootFlameAtBestTarget(-syaw, cyaw);
				shootLaserAtBestTarget(-syaw, cyaw);
			}
			return;
		}

		if (!RivalRebels.rhodesAI && ac != 0 && ac != 1 && ac != 11) return;

		if (counter == 0)
		{
			if (!actionqueue.isEmpty())
			{
				RhodesAction nextaction = actionqueue.remove();
				ac = nextaction.action;
				counter = nextaction.duration;
			}
			else ac = 2;
		}

		float movescale = scale;
		if (RivalRebels.rhodesScaleSpeed) movescale *= RivalRebels.rhodesSpeedScale;
		else movescale = RivalRebels.rhodesSpeedScale;
		switch (ac)
		{
		case 0: //Spawned
			if (damageUntilWake < 100)
			{
				RivalRebelsSoundPlayer.playSound(this, 13, 0, 900f, 1f);
				actionqueue.add(new RhodesAction(1, 1));
			} else counter++;
			rightthighpitch = approach(rightthighpitch,0);
			leftthighpitch  = approach(leftthighpitch, 0);
			rightshinpitch  = approach(rightshinpitch, 0);
			leftshinpitch   = approach(leftshinpitch,  0);
			rightarmpitch = approach(rightarmpitch,0);
			leftarmpitch  = approach(leftarmpitch, 0);
			rightarmyaw  = approach(rightarmyaw,   0);
			leftarmyaw   = approach(leftarmyaw,    0);
			headpitch   = approach(headpitch, 0);
			break;

		case 1: //Ready to engage
			if (damageUntilWake <= 0)
			{
				RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
				actionqueue.add(new RhodesAction(2, 1));
			} else counter++;
			rightthighpitch = approach(rightthighpitch,0);
			leftthighpitch  = approach(leftthighpitch, 0);
			rightshinpitch  = approach(rightshinpitch, 0);
			leftshinpitch   = approach(leftshinpitch,  0);
			rightarmpitch = approach(rightarmpitch,0);
			leftarmpitch  = approach(leftarmpitch, 0);
			rightarmyaw  = approach(rightarmyaw,   0);
			leftarmyaw   = approach(leftarmyaw,    0);
			headpitch   = approach(headpitch, 0);
			break;

		case 2: //Idle
			if (raidedOmegaAlready == raidedSigmaAlready) raidedSigmaAlready = raidedOmegaAlready = false;
			if (endangered && RivalRebels.round.isStarted())
			{
				if (teamToRaid == 0)
				{
					if (raidedOmegaAlready && RivalRebels.round.sigmaHealth > 0) teamToRaid = 2;
					else if (raidedSigmaAlready && RivalRebels.round.omegaHealth > 0) teamToRaid = 1;
					else if (RivalRebels.round.omegaHealth > 0 && RivalRebels.round.sigmaHealth > 0)
					{
						RivalRebelsPlayer[] rrps = RivalRebels.round.rrplayerlist.getArray();
						int o = 0;
						int s = 0;
                        for (RivalRebelsPlayer rrp : rrps) {
                            if (rrp != null && rrp.rrteam == RivalRebelsTeam.OMEGA) o++;
                            else if (rrp != null && rrp.rrteam == RivalRebelsTeam.SIGMA) s++;
                        }
						if (o>s)
						{
							teamToRaid = 1;
							raidedOmegaAlready = true;
						}
						if (o<s)
						{
							teamToRaid = 2;
							raidedSigmaAlready = true;
						}
						if (o==s)
						{
							teamToRaid = random.nextBoolean()?2:1;
							if (teamToRaid == 1) raidedOmegaAlready = true;
							else raidedSigmaAlready = true;
						}
					}
				}
				if (teamToRaid != 0)
				{
					float dx = (float) ((teamToRaid==1?RivalRebels.round.omegaObjPos.getX():RivalRebels.round.sigmaObjPos.getX())-getX());
					float dz = (float) ((teamToRaid==1?RivalRebels.round.omegaObjPos.getZ():RivalRebels.round.sigmaObjPos.getZ())-getZ());
					float angle = ((atan2(dx, dz) - bodyyaw)%360);
					if (angle > 1f)
					{
						actionqueue.add(new RhodesAction(5, (int) Math.abs(angle)));
					}
					else if (angle < -1f)
					{
						actionqueue.add(new RhodesAction(7, (int) Math.abs(angle)));
					}
					else
					{
						float d = Math.abs(dx)+Math.abs(dz);
						if (d<5)
						{
							RivalRebelsSoundPlayer.playSound(this, 13, 0, 900f, 1f);
							actionqueue.add(new RhodesAction(10, 1));
						}
						else
						{
							if (d > 50 && random.nextBoolean())
							{
								actionqueue.add(new RhodesAction(9, 100));
								flying = 50;
							}
							else actionqueue.add(new RhodesAction(3, 20));
						}
					}
					actionqueue.add(new RhodesAction(2, 1));
				}
			}
			else
			{
				Entity t = findTarget();
				if (t != null)
				{
					float dx = (float) (t.getX()-getX());
					float dz = (float) (t.getZ()-getZ());
					float angle = ((atan2(dx, dz) - bodyyaw)%360);
					if (angle > 1 && random.nextBoolean())
					{
						actionqueue.add(new RhodesAction(5, (int) Math.abs(angle)));
					}
					else if (angle < -1 && random.nextBoolean())
					{
						actionqueue.add(new RhodesAction(7, (int) Math.abs(angle)));
					}
					else
					{
						if (random.nextInt(20)<(endangered?2:1))
						{
							actionqueue.add(new RhodesAction(9, 100));
							flying = 50;
						}
						else actionqueue.add(new RhodesAction(3, 60));
					}
					actionqueue.add(new RhodesAction(2, 1));
				}
				else
				{
					rightthighpitch = approach(rightthighpitch,0);
					leftthighpitch  = approach(leftthighpitch, 0);
					rightshinpitch  = approach(rightshinpitch, 0);
					leftshinpitch   = approach(leftshinpitch,  0);
					rightarmpitch = approach(rightarmpitch,0);
					leftarmpitch  = approach(leftarmpitch, 0);
					rightarmyaw  = approach(rightarmyaw,   0);
					leftarmyaw   = approach(leftarmyaw,    0);
					headpitch   = approach(headpitch, 0);
					counter++;
				}
			}

			break;

		case 3:
			shootRocketsAtBestTarget(-syaw, cyaw);
			shootFlameAtBestTarget(-syaw, cyaw);
			shootLaserAtBestTarget(-syaw, cyaw);
		case 4:
			doWalkingAnimation(syaw, cyaw);
			break;

		case 5:
			shootRocketsAtBestTarget(-syaw, cyaw);
			shootFlameAtBestTarget(-syaw, cyaw);
			shootLaserAtBestTarget(-syaw, cyaw);
		case 6:
			bodyyaw += 1.5f * movescale;
			doWalkingAnimation(syaw, cyaw);
			break;

		case 7:
			shootRocketsAtBestTarget(-syaw, cyaw);
			shootFlameAtBestTarget(-syaw, cyaw);
			shootLaserAtBestTarget(-syaw, cyaw);
		case 8:
			bodyyaw -= 1.5f * movescale;
			doWalkingAnimation(syaw, cyaw);
			break;

		case 9:
			shootRocketsAtBestTarget(-syaw, cyaw);
			shootFlameAtBestTarget(-syaw, cyaw);
			shootLaserAtBestTarget(-syaw, cyaw);
			setVelocity(syaw * 0.5f * movescale,
                getVelocity().getY(),
                cyaw * 0.5f * movescale);
			rightthighpitch = approach(rightthighpitch,-30);
			leftthighpitch  = approach(leftthighpitch, -30);
			rightshinpitch  = approach(rightshinpitch, 60);
			leftshinpitch   = approach(leftshinpitch,  60);
			break;
		case 10:
			if (teamToRaid == 1 && RivalRebels.round.omegaHealth > 0 && world.getBlockState(RivalRebels.round.omegaObjPos).getBlock() == RRBlocks.omegaobj || teamToRaid != 1 && (teamToRaid == 2 && RivalRebels.round.sigmaHealth > 0 && world.getBlockState(RivalRebels.round.sigmaObjPos).getBlock() == RRBlocks.sigmaobj))
			{
				rightthighpitch = approach(rightthighpitch,0);
				leftthighpitch  = approach(leftthighpitch, 0);
				rightshinpitch  = approach(rightshinpitch, 0);
				leftshinpitch   = approach(leftshinpitch,  0);
				rightarmpitch = approach(rightarmpitch,0);
				leftarmpitch  = approach(leftarmpitch, 0);
				rightarmyaw  = approach(rightarmyaw,   0);
				leftarmyaw   = approach(leftarmyaw,    0);
				headpitch   = approach(headpitch, 0);
				laserOn = 0;
				if (teamToRaid == 1)
				{
					health += RivalRebels.round.takeOmegaHealth(Math.min(50, RivalRebels.rhodesHealth-health));
				}
				if (teamToRaid == 2)
				{
					health += RivalRebels.round.takeSigmaHealth(Math.min(50, RivalRebels.rhodesHealth-health));
				}
				if (health != RivalRebels.rhodesHealth) counter++;
				else
				{
					endangered = false;
					teamToRaid = 0;
					actionqueue.add(new RhodesAction(2, 1));
				}
			}
			else
			{
				health = 0;
				RivalRebelsSoundPlayer.playSound(this, 0, 0, 30, 1);
			}
			break;
		case 11:
			ac = 1;
			counter = 2;
			break;
		}
		counter--;
	}

	public static float atan2(float y, float x)
	{
		float dx = ((float)Math.sqrt(x*x+y*y)+x);
		if (y > dx)
		{
			float r = dx/y;
			return 180-(110.8653352702f-20.8654f*r*r)*r;
		}
		else if (y > -dx)
		{
			float r = y/dx;
			return (110.8653352702f-20.8654f*r*r)*r;
		}
		else
		{
			float r = dx/y;
			return -180-(110.8653352702f-20.8654f*r*r)*r;
		}
	}

	private Entity findTarget()
	{
		Entity target = null;
		double priority = 0;
        List<Entity> otherEntities = world.getOtherEntities(this, IForgeBlockEntity.INFINITE_EXTENT_AABB);
        for (Entity e : otherEntities) {
            if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) && (!(e instanceof EntityRhodes) || (RivalRebels.rhodesFF && (RivalRebels.rhodesCC || ((EntityRhodes) e).colorType != colorType))) &&
                    !(e instanceof ThrownEntity
                            || e instanceof EntityInanimate
                            || e instanceof ItemEntity
                            || e instanceof AnimalEntity
                            || e instanceof VillagerEntity
                            || e instanceof BatEntity
                            || e instanceof SquidEntity
                            || e instanceof BoatEntity
                            || e instanceof MinecartEntity)) {
                double prio = getPriority(e) - distanceTo(e);
                if (prio > priority) {
                    target = e;
                    priority = prio;
                }
            }
        }
		return target;
	}

	private int walkstate = 0;
	private void doWalkingAnimation(float syaw, float cyaw)
	{
		float movescale = scale;
		if (RivalRebels.rhodesScaleSpeed) movescale *= RivalRebels.rhodesSpeedScale;
		else movescale = RivalRebels.rhodesSpeedScale;
		setVelocity(syaw * 0.125f*movescale,
            getVelocity().getY(),
            cyaw * 0.125f*movescale);
        switch (walkstate) {
            case 0 -> {
                int rtg = -60;
                int ltg = 0;
                int rsg = 60;
                int lsg = 30;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 1 -> {
                int rtg = -60;
                int ltg = 0;
                int rsg = 60;
                int lsg = 60;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) {
                    walkstate++;
                    RivalRebelsSoundPlayer.playSound(this, 0, 2, 4.5f, 0.8f);
                }
            }
            case 2 -> {
                int rtg = -30;
                int ltg = 0;
                int rsg = 30;
                int lsg = 90;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 3 -> {
                int rtg = 0;
                int ltg = -30;
                int rsg = 0;
                int lsg = 90;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 4 -> {
                int rtg = 0;
                int ltg = -60;
                int rsg = 30;
                int lsg = 60;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 5 -> {
                int rtg = 0;
                int ltg = -60;
                int rsg = 60;
                int lsg = 60;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) {
                    walkstate++;
                    RivalRebelsSoundPlayer.playSound(this, 0, 2, 4.5f, 0.8f);
                }
            }
            case 6 -> {
                int rtg = 0;
                int ltg = -30;
                int rsg = 90;
                int lsg = 30;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 7 -> {
                int rtg = -30;
                int ltg = 0;
                int rsg = 90;
                int lsg = 0;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 8 -> {
                int rtg = -60;
                int ltg = 0;
                int rsg = 60;
                int lsg = 30;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate = 0;
            }
        }
	}

	private float approach(float f, float t)
	{
		float r = f-t;
		if (r>1)r=1;
		if (r<-1)r=-1;
		return f-r;
	}

	private Entity lastRocketTarget = null;
	private int lastshot;
	private int shotstaken;
	private int lastrockettargetting = 0;
	boolean nuke = false;
	private void shootRocketsAtBestTarget(float syaw, float cyaw)
	{
		if (rocketcount < 0) return;
		float px = (float)getX()+cyaw*6.4f*scale;
		float py = (float)getY()+6.26759f*scale;
		float pz = (float)getZ()+syaw*6.4f*scale;
		BlockEntity te = null;
		lastrockettargetting--;
		if (lastrockettargetting<0||lastRocketTarget==null||!lastRocketTarget.isAlive() || (lastRocketTarget instanceof LivingEntity && ((LivingEntity)lastRocketTarget).getHealth()<=0))
		{
			lastrockettargetting = 10;
			float priority = 0;
			if (lastRocketTarget != null && lastRocketTarget.isAlive())
			{
				float dx = ((float)lastRocketTarget.getX()-px);
				float dz = ((float)lastRocketTarget.getZ()-pz);
				float dot = (cyaw * dx + syaw * dz);
				if (dot*Math.abs(dot) > -0.25 * (dx*dx+dz*dz))
				{
					float dy = ((float)lastRocketTarget.getY()-(float)getY()-6.2f*scale);
					float dist = dx*dx+dy*dy+dz*dz;
					if (dist < 100*100*scale*scale && rayTraceBlocks(px, py, pz, (float)lastRocketTarget.getX(), (float)lastRocketTarget.getY()+lastRocketTarget.getHeight()/2f, (float)lastRocketTarget.getZ()) == null)
					{
						priority = getPriority(lastRocketTarget)-(float)Math.sqrt(dist)+10;
					}
				}
			}
			if (priority <= 0) lastRocketTarget = null;
            for (Entity e : world.getOtherEntities(this, new Box(px - 100 * scale, py - 100 * scale, pz - 100 * scale, px + 100 * scale, py + 100 * scale, pz + 100 * scale))) {
                if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) &&
                    !(e instanceof ThrownEntity
                        || e instanceof EntityInanimate
                        || e instanceof ItemEntity
                        || e instanceof AnimalEntity
                        || e instanceof VillagerEntity
                        || e instanceof BatEntity
                        || e instanceof SquidEntity
                        || e instanceof BoatEntity
                        || e instanceof MinecartEntity)) {
                    float dx = (float) e.getX() - px;
                    float dz = (float) e.getZ() - pz;
                    float dot = (cyaw * dx + syaw * dz);
                    if (dot * Math.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                        float dy = (float) e.getY() - py;
                        float dist = dx * dx + dy * dy + dz * dz;
                        if (dist < 100 * 100 * scale * scale) {
                            float prio = getPriority(e) - (float) Math.sqrt(dist);
                            if (prio > priority && rayTraceBlocks(px, py, pz, (float) e.getX(), (float) e.getY() + e.getHeight() / 2f, (float) e.getZ()) == null) {
                                lastRocketTarget = e;
                                priority = prio;
                            }
                        }
                    }
                }
            }

            for (BlockEntity e : BLOCK_ENTITIES.values()) {
                if (e instanceof TileEntityRhodesActivator || e instanceof TileEntityNukeCrate || e instanceof TileEntityReactor) {
                    float dx = (float) e.getPos().getX() - px;
                    float dz = (float) e.getPos().getZ() - pz;
                    float dot = (cyaw * dx + syaw * dz);
                    if (dot * Math.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                        float dy = (float) e.getPos().getY() - py;
                        float dist = dx * dx + dy * dy + dz * dz;
                        if (dist < 100 * 100) {
                            float prio = 300 - (float) Math.sqrt(dist);
                            if (prio > priority && rayTraceBlocks(px, py, pz, (float) e.getPos().getX(), (float) e.getPos().getY(), (float) e.getPos().getZ()) == null) {
                                te = e;
                                lastRocketTarget = null;
                                priority = prio;
                            }
                        }
                    }
                }
            }
		}
		if (te != null && !nuke)
		{
			float x = px - (float)te.getPos().getX();
			float y = py - (float)te.getPos().getY();
			float z = pz - (float)te.getPos().getZ();
			float yaw = ((atan2(x, z)-bodyyaw+630)%360)-90;
			float pitch = -(atan2((float)Math.sqrt(x*x+z*z), y));
			boolean pointing = true;
			if (Math.abs(leftarmyaw-yaw) >= 0.001f)
			{
				leftarmyaw += Math.max(-3, Math.min(3, (yaw-leftarmyaw)));
				if (Math.abs(leftarmyaw-yaw) < 3f) pointing &= true;
				else pointing = false;
			}
			if (Math.abs(leftarmpitch-pitch) >= 0.001f)
			{
				leftarmpitch += Math.max(-3, Math.min(3, (pitch-leftarmpitch)));
				if (Math.abs(leftarmpitch-pitch) < 3f) pointing &= true;
				else pointing = false;
			}

			if (pointing && age-lastshot > ((scale >= 2.0)?30:((shotstaken == 21)?80:5)))
			{
				rocketcount--;
				lastshot = age;
				if (shotstaken == 21) shotstaken = 0;
				shotstaken++;
				RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
				float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);
				if (scale >= 2.0)
					world.spawnEntity(new EntityB83NoShroom(world, px, py, pz,
							x*cp, y*cp, z*cp));
				else
					world.spawnEntity(new EntitySeekB83(world, px, py, pz,
							x*cp, y*cp, z*cp));
			}
		}
		else if (lastRocketTarget != null && lastRocketTarget.isAlive())
		{
			float x = px - (float)lastRocketTarget.getX();
			float y = py - (float)lastRocketTarget.getY();
			float z = pz - (float)lastRocketTarget.getZ();
			float yaw = ((atan2(x, z)-bodyyaw+630)%360)-90;
			float pitch = -(atan2((float)Math.sqrt(x*x+z*z), y));
			boolean pointing = true;
			if (Math.abs(leftarmyaw-yaw) >= 0.001f)
			{
				leftarmyaw += Math.max(-3, Math.min(3, (yaw-leftarmyaw)));
				if (Math.abs(leftarmyaw-yaw) < 3f) pointing &= true;
				else pointing = false;
			}
			if (Math.abs(leftarmpitch-pitch) >= 0.001f)
			{
				leftarmpitch += Math.max(-3, Math.min(3, (pitch-leftarmpitch)));
				if (Math.abs(leftarmpitch-pitch) < 3f) pointing &= true;
				else pointing = false;
			}

			if (lastRocketTarget instanceof LivingEntity && ((LivingEntity)lastRocketTarget).getMaxHealth() > 1000)
			{
				if (pointing && age % 100 == 0)
				{
					RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
					float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);
					if (scale >= 2.0)
						world.spawnEntity(new EntityTsar(world, px, py, pz,
								x*cp*5.0f, y*cp*5.0f, z*cp*5.0f));
					else
						world.spawnEntity(new EntityB83NoShroom(world, px, py, pz,
								x*cp, y*cp, z*cp));
				}
			}
			else
			{
				if (pointing && age-lastshot > ((scale >= 2.0)?30:((shotstaken == 21)?80:5)))
				{
					rocketcount--;
					lastshot = age;
					if (shotstaken == 21) shotstaken = 0;
					shotstaken++;
					RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
					float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);
					if (scale >= 2.0)
						world.spawnEntity(new EntityB83NoShroom(world, px, py, pz,
								x*cp, y*cp, z*cp));
					else
						world.spawnEntity(new EntitySeekB83(world, px, py, pz,
								x*cp, y*cp, z*cp));
				}
			}
		}
	}

    public static final Map<BlockPos, BlockEntity> BLOCK_ENTITIES = new HashMap<>();
	private Entity lastFlameTarget = null;
	private int lastflametargetting = 0;
	private void shootFlameAtBestTarget(float syaw, float cyaw)
	{
		float px = (float)getX()-cyaw*6.4f*scale;
		float py = (float)getY()+6.26759f*scale;
		float pz = (float)getZ()-syaw*6.4f*scale;
		lastflametargetting--;
		if (lastflametargetting<0||lastFlameTarget==null||!lastFlameTarget.isAlive() || (lastFlameTarget instanceof LivingEntity && ((LivingEntity)lastFlameTarget).getHealth()<=0))
		{
			lastflametargetting = 10;
			float priority = 0;
			if (lastFlameTarget != null && lastFlameTarget.isAlive())
			{
				float dx = ((float)lastFlameTarget.getX()-px);
				float dz = ((float)lastFlameTarget.getZ()-pz);
				float dot = (-cyaw * dx + -syaw * dz);
				if (dot*Math.abs(dot) > -0.25 * (dx*dx+dz*dz))
				{
					float dy = ((float)lastFlameTarget.getY()-(float)getY()-6.2f);
					float dist = dx*dx+dy*dy+dz*dz;
					if (dist < 40*40*scale*scale && rayTraceBlocks(px, py, pz, (float)lastFlameTarget.getX(), (float)lastFlameTarget.getY()+lastFlameTarget.getHeight(), (float)lastFlameTarget.getZ()) == null)
					{
						priority = getPriority(lastFlameTarget)-(float)Math.sqrt(dist)+10;
					}
				}
			}
			if (priority <= 0) lastFlameTarget = null;
            for (Entity e : world.getOtherEntities(this, new Box(px - 40 * scale, py - 40 * scale, pz - 40 * scale, px + 40 * scale, py + 40 * scale, pz + 40 * scale))) {
                if (e.isAlive() &&
                    !(e instanceof ThrownEntity
                        || e instanceof EntityInanimate
                        || e instanceof ItemEntity
                        || e instanceof AnimalEntity
                        || e instanceof VillagerEntity
                        || e instanceof BatEntity
                        || e instanceof SquidEntity
                        || e instanceof BoatEntity
                        || e instanceof MinecartEntity)) {
                    float dx = (float) e.getX() - px;
                    float dz = (float) e.getZ() - pz;
                    float dot = (-cyaw * dx + -syaw * dz);
                    if (dot * Math.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                        float dy = (float) e.getY() - py;
                        float dist = dx * dx + dy * dy + dz * dz;
                        if (dist < 40 * 40 * scale * scale) {
                            float prio = getPriority(e) - (float) Math.sqrt(dist);
                            if (prio > priority && rayTraceBlocks(px, py, pz, (float) e.getX(), (float) e.getY() + e.getHeight(), (float) e.getZ()) == null) {
                                lastFlameTarget = e;
                                priority = prio;
                            }
                        }
                    }
                }
            }
		}

		if (lastFlameTarget != null && lastFlameTarget.isAlive())
		{
			float x = px - (float)lastFlameTarget.getX();
			float y = py - (float)lastFlameTarget.getY() - (lastFlameTarget.getHeight()*0.5f);
			float z = pz - (float)lastFlameTarget.getZ();
			float yaw = ((atan2(x, z)-bodyyaw+810)%360)-270;
			float pitch = -(atan2((float)Math.sqrt(x*x+z*z), y));
			boolean pointing = true;
			if (Math.abs(rightarmyaw-yaw) >= 0.001f)
			{
				rightarmyaw += Math.max(-3, Math.min(3, (yaw-rightarmyaw)));
				if (Math.abs(rightarmyaw-yaw) < 0.001f) pointing &= true;
				else pointing = false;
			}
			if (Math.abs(rightarmpitch-pitch) >= 0.001f)
			{
				rightarmpitch += Math.max(-3, Math.min(3, (pitch-rightarmpitch)));
				if (Math.abs(rightarmpitch-pitch) < 0.001f) pointing &= true;
				else pointing = false;
			}

			if (pointing)
			{
				RivalRebelsSoundPlayer.playSound(this, 8, 1, 1f);
				float cp = -1f/(float)Math.sqrt(x*x+y*y+z*z);
				x*=cp;
				y*=cp;
				z*=cp;
				world.spawnEntity(new EntityFlameBall(world, px, py, pz,
						x, y, z, (8+random.nextDouble()*8)*scale, 0.4f));
				world.spawnEntity(new EntityFlameBall(world, px, py, pz,
						x, y, z, (8+random.nextDouble()*8)*scale, 0.4f));
			}
		}
	}

	private Entity lastLaserTarget = null;
	private int lastlasertargetting = 0;
	private void shootLaserAtBestTarget(float syaw, float cyaw)
	{
		lastlasertargetting--;
		if (lastlasertargetting<0||lastLaserTarget==null||!lastLaserTarget.isAlive() || (lastLaserTarget instanceof LivingEntity && ((LivingEntity)lastLaserTarget).getHealth()<=0))
		{
			lastlasertargetting=10;
			float priority = 0;
			if (lastLaserTarget != null && lastLaserTarget.isAlive())
			{
				float tempdi = (float) ((lastLaserTarget.getX()-getX())*(lastLaserTarget.getX()-getX())
						+(lastLaserTarget.getY()-13-getY())*(lastLaserTarget.getY()-13-getY())
						+(lastLaserTarget.getZ()-getZ())*(lastLaserTarget.getZ()-getZ()));
				if (Math.abs(cyaw*(lastLaserTarget.getX()-getX())+syaw*(lastLaserTarget.getZ()-getZ()))<2&&tempdi<70*70
						&&rayTraceBlocks((float)getX(), (float)getY() + 13, (float)getZ(), (float)lastLaserTarget.getX(), (float)lastLaserTarget.getY()+lastLaserTarget.getHeight()/2f, (float)lastLaserTarget.getZ()) == null)
				{
					priority = getPriority(lastLaserTarget)-(float)Math.sqrt(tempdi);
					if (priority > 0) priority += 10;
				}
			}
			if (priority <= 0) lastLaserTarget = null;

            for (Entity e : world.getOtherEntities(this, new Box(getX() - 70 * scale, getY() + 13 * scale - 70 * scale, getZ() - 70 * scale, getX() + 70 * scale, getY() + 13 * scale + 70 * scale, getZ() + 70 * scale))) {
                if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) &&
                        !(e instanceof ThrownEntity
                                || e instanceof EntityInanimate
                                || e instanceof ItemEntity
                                || e instanceof AnimalEntity
                                || e instanceof VillagerEntity
                                || e instanceof BatEntity
                                || e instanceof SquidEntity
                                || e instanceof BoatEntity
                                || e instanceof MinecartEntity)) {
                    float x = (float) (e.getX() - getX());
                    float z = (float) (e.getZ() - getZ());
                    if (Math.abs(cyaw * x + syaw * z) < 2) {
                        float y = (float) (e.getY() - 13 - getY());
                        float dist = x * x + y * y + z * z;
                        if (dist < 70 * 70 * scale * scale) {
                            if (y * Math.abs(y) > -0.64f * dist) {
                                float prio = getPriority(e) - (float) Math.sqrt(dist);
                                if (prio > priority && rayTraceBlocks((float) getX(), (float) getY() + 13, (float) getZ(), (float) e.getX(), (float) e.getY() + e.getHeight() / 2f, (float) e.getZ()) == null) {
                                    lastLaserTarget = e;
                                    priority = prio;
                                }
                            }
                        }
                    }
                }
            }
		}

		laserOn = (byte) 0;
		if (lastLaserTarget != null && lastLaserTarget.isAlive())
		{
			float x = (float) (getX() - lastLaserTarget.getX());
			float y = (float) (getY() + 13 - lastLaserTarget.getY());
			float z = (float) (getZ() - lastLaserTarget.getZ());
			float dot = -syaw*x+cyaw*z;
			float pitch = (720f-atan2((float)Math.sqrt(x*x+z*z)*(dot>0?-1f:1f), y)) %360-270;

			boolean pointing = true;
			if (Math.abs(headpitch-pitch) >= 0.001f)
			{
				headpitch += Math.max(-20, Math.min(20, (pitch-headpitch)));
                pointing = Math.abs(headpitch - pitch) < 3f;
			}

			if (pointing)
			{
				laserOn = (byte) (endangered?3:random.nextInt(2)+1);
				RivalRebelsSoundPlayer.playSound(this, 22, 1, 30f, 0f);
				if (lastLaserTarget instanceof PlayerEntity player)
				{
                    DefaultedList<ItemStack> armorSlots = player.getInventory().armor;
					int i = world.random.nextInt(4);
					if (!armorSlots.get(i).isEmpty())
					{
						armorSlots.get(i).damage(24, player, player1 -> {});
					}
					lastLaserTarget.damage(RivalRebelsDamageSource.laserburst, laserOn==3?16:8);
					if (player.getHealth() < 3 && player.isAlive())
					{
						player.damage(RivalRebelsDamageSource.laserburst, 2000000);
						player.deathTime = 0;
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 0, 0));
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 1, 0));
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 2, 0));
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 2, 0));
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 3, 0));
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 3, 0));
					}
				}
				else
				{
					lastLaserTarget.damage(RivalRebelsDamageSource.laserburst, laserOn==3?16:8);
					if (!lastLaserTarget.isAlive() || (lastLaserTarget instanceof LivingEntity && ((LivingEntity)lastLaserTarget).getHealth() < 3))
					{
						int legs;
						int arms;
						int mobs;
						RivalRebelsSoundPlayer.playSound(this, 2, 1, 4);
						if (lastLaserTarget instanceof ZombifiedPiglinEntity)
						{
							legs = 2;
							arms = 2;
							mobs = 2;
						}
						else if (lastLaserTarget instanceof ZombieEntity)
						{
							legs = 2;
							arms = 2;
							mobs = 1;
						}
						else if (lastLaserTarget instanceof SkeletonEntity)
						{
							legs = 2;
							arms = 2;
							mobs = 3;
						}
						else if (lastLaserTarget instanceof EndermanEntity)
						{
							legs = 2;
							arms = 2;
							mobs = 4;
						}
						else if (lastLaserTarget instanceof CreeperEntity)
						{
							legs = 4;
							arms = 0;
							mobs = 5;
						}
						else if (lastLaserTarget instanceof MagmaCubeEntity)
						{
							legs = 0;
							arms = 0;
							mobs = 7;
						}
						else if (lastLaserTarget instanceof SlimeEntity)
						{
							legs = 0;
							arms = 0;
							mobs = 6;
						}
						else if (lastLaserTarget instanceof CaveSpiderEntity)
						{
							legs = 8;
							arms = 0;
							mobs = 9;
						}
						else if (lastLaserTarget instanceof SpiderEntity)
						{
							legs = 8;
							arms = 0;
							mobs = 8;
						}
						else if (lastLaserTarget instanceof GhastEntity)
						{
							legs = 9;
							arms = 0;
							mobs = 10;
						}
						else if (lastLaserTarget instanceof EntityB2Spirit
						      || lastLaserTarget instanceof EntityRhodes
						      || lastLaserTarget instanceof EntityRhodesPiece)
						{
							return;
						}
						else
						{
							legs = (int) (lastLaserTarget.getBoundingBox().getAverageSideLength() * 2);
							arms = (int) (lastLaserTarget.getBoundingBox().getAverageSideLength() * 2);
							mobs = 11;
						}
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 0, mobs));
						world.spawnEntity(new EntityGore(world, lastLaserTarget, 1, mobs));
						for (int i = 0; i < arms; i++)
							world.spawnEntity(new EntityGore(world, lastLaserTarget, 2, mobs));
						for (int i = 0; i < legs; i++)
							world.spawnEntity(new EntityGore(world, lastLaserTarget, 3, mobs));
						lastLaserTarget.kill();
					}
				}
			}
		}
	}

	//lower is less prior
	private float getPriority(Entity e)
	{
		if (e instanceof PlayerEntity) return  ((PlayerEntity) e).getAbilities().invulnerable?-100:600;
		if (e instanceof LivingEntity) return ((LivingEntity)e).getMaxHealth()+100;
		if ((e instanceof EntityRhodes && (RivalRebels.rhodesFF && (RivalRebels.rhodesCC || ((EntityRhodes)e).colorType != colorType))) || e instanceof EntityB2Spirit) return 800;
		if (e.getBoundingBox().getAverageSideLength() > 3) return (float) (e.getBoundingBox().getAverageSideLength()*3 + 500 + e.getHeight());
		return 0;
	}

	private Vec3d rayTraceBlocks(float sx, float sy, float sz, float ex, float ey, float ez)
	{
		int X = (int) sx;
		int Y = (int) sy;
		int Z = (int) sz;
		float dx = ex-sx;
		float dy = ey-sy;
		float dz = ez-sz;
		float distsq = (dx*dx+dy*dy+dz*dz);
		int stepX = dx>0?1:-1;
		int stepY = dy>0?1:-1;
		int stepZ = dz>0?1:-1;
		int x = X-stepX;
		int y = Y-stepY;
		int z = Z-stepZ;
		float tDeltaX = stepX/dx;
		float tDeltaY = stepY/dy;
		float tDeltaZ = stepZ/dz;
		float tMaxX = ((1+X)-sx)*tDeltaX;
		float tMaxY = ((1+Y)-sy)*tDeltaY;
		float tMaxZ = ((1+Z)-sz)*tDeltaZ;
		while((X-x)*(X-x)+(Y-y)*(Y-y)+(Z-z)*(Z-z) < distsq)
		{
			if (world.getBlockState(new BlockPos(X, Y, Z)).isOpaque())
			{
				return new Vec3d(X, Y, Z);
			}
			if(tMaxX < tMaxY)
			{
				if(tMaxX < tMaxZ)
				{
					X += stepX;
					tMaxX += tDeltaX;
				}
				else
				{
					Z += stepZ;
					tMaxZ += tDeltaZ;
				}
			}
			else
			{
				if(tMaxY < tMaxZ)
				{
					Y += stepY;
					tMaxY += tDeltaY;
				}
				else
				{
					Z += stepZ;
					tMaxZ += tDeltaZ;
				}
			}
		}
		return null;
	}

	public float getbodyyaw(float f)
	{
		return lastbodyyaw + (bodyyaw - lastbodyyaw) * f;
	}

	public float getheadyaw(float f)
	{
		return lastheadyaw + (headyaw - lastheadyaw) * f;
	}

	public float getheadpitch(float f)
	{
		return lastheadpitch + (headpitch - lastheadpitch) * f;
	}

	public float getleftarmyaw(float f)
	{
		return lastleftarmyaw + (leftarmyaw - lastleftarmyaw) * f;
	}

	public float getleftarmpitch(float f)
	{
		return lastleftarmpitch + (leftarmpitch - lastleftarmpitch) * f;
	}

	public float getrightarmyaw(float f)
	{
		return lastrightarmyaw + (rightarmyaw - lastrightarmyaw) * f;
	}

	public float getrightarmpitch(float f)
	{
		return lastrightarmpitch + (rightarmpitch - lastrightarmpitch) * f;
	}

	public float getleftthighpitch(float f)
	{
		return lastleftthighpitch + (leftthighpitch - lastleftthighpitch) * f;
	}

	public float getrightthighpitch(float f)
	{
		return lastrightthighpitch + (rightthighpitch - lastrightthighpitch) * f;
	}

	public float getleftshinpitch(float f)
	{
		return lastleftshinpitch + (leftshinpitch - lastleftshinpitch) * f;
	}

	public float getrightshinpitch(float f)
	{
		return lastrightshinpitch + (rightshinpitch - lastrightshinpitch) * f;
	}

	public Text getName()
	{
        return Text.of(names[colorType]);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt)
	{
		nbt.putFloat("bodyyaw", bodyyaw);
		nbt.putFloat("headyaw", headyaw);
		nbt.putFloat("headpitch", headpitch);
		nbt.putFloat("leftarmyaw", leftarmyaw);
		nbt.putFloat("leftarmpitch", leftarmpitch);
		nbt.putFloat("rightarmyaw", rightarmyaw);
		nbt.putFloat("rightarmpitch", rightarmpitch);
		nbt.putFloat("leftthighpitch", leftthighpitch);
		nbt.putFloat("rightthighpitch", rightthighpitch);
		nbt.putFloat("leftshinpitch", leftshinpitch);
		nbt.putFloat("rightshinpitch", rightshinpitch);
		nbt.putBoolean("endangered", endangered);
		nbt.putInt("ac", ac);
		nbt.putInt("counter", counter);
		nbt.putInt("walkstate", walkstate);
		nbt.putInt("health", health);
		nbt.putInt("damageuntilwake", damageUntilWake);
		nbt.putByte("color", colorType);
		nbt.putInt("rocketcount", rocketcount);
		nbt.putInt("energy", energy);
		nbt.putInt("b2energy", b2energy);
		nbt.putInt("flamecount", flamecount);
		nbt.putInt("nukecount", nukecount);
		nbt.putInt("texfolder", itexfolder);
		nbt.putFloat("scale", scale);
		if (itexfolder != -1) nbt.putString("texloc", itexloc);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt)
	{
		bodyyaw = nbt.getFloat("bodyyaw");
		headyaw = nbt.getFloat("headyaw");
		headpitch = nbt.getFloat("headpitch");
		leftarmyaw = nbt.getFloat("leftarmyaw");
		leftarmpitch = nbt.getFloat("leftarmpitch");
		rightarmyaw = nbt.getFloat("rightarmyaw");
		rightarmpitch = nbt.getFloat("rightarmpitch");
		leftthighpitch = nbt.getFloat("leftthighpitch");
		rightthighpitch = nbt.getFloat("rightthighpitch");
		leftshinpitch = nbt.getFloat("leftshinpitch");
		rightshinpitch = nbt.getFloat("rightshinpitch");
		endangered = nbt.getBoolean("endangered");
		ac = nbt.getInt("ac");
		counter = nbt.getInt("counter");
		walkstate = nbt.getInt("walkstate");
		health = nbt.getInt("health");
		damageUntilWake = nbt.getInt("damageuntilwake");
		colorType = nbt.getByte("color");
		rocketcount = nbt.getInt("rocketcount");
		energy = nbt.getInt("energy");
		b2energy = nbt.getInt("b2energy");
		flamecount = nbt.getInt("flamecount");
		nukecount = nbt.getInt("nukecount");
		itexfolder = nbt.getInt("texfolder");
		scale = nbt.getFloat("scale");
		if (itexfolder != -1) itexloc = nbt.getString("texloc");
	}

	@Override
	public boolean damage(DamageSource par1DamageSource, float par2)
	{
		super.damage(par1DamageSource, par2);
		if (isAlive() && !world.isClient && health > 0 && !forcefield)
		{
			if (par2 > 50)
			{
				health -= 50;
				if (rider == null) damageUntilWake -= 50;
				endangered = true;
			}
			else
			{
				health -= par2;
				if (rider == null) damageUntilWake -= par2;
			}

			if (health <= 0)
			{
				health = 0;
				RivalRebelsSoundPlayer.playSound(this, 0, 0, 30, 1);
			}
		}

		return true;
	}

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(FIRE, false);
        dataTracker.startTracking(PLASMA, false);
    }

    public boolean isFire() {
        return dataTracker.get(FIRE);
    }

    public void setFire(boolean fire) {
        dataTracker.set(FIRE, fire);
    }

    public boolean isPlasma() {
        return dataTracker.get(PLASMA);
    }

    public void setPlasma(boolean plasma) {
        dataTracker.set(PLASMA, plasma);
    }

    @OnlyIn(Dist.CLIENT)
    public int getBrightnessForRender()
    {
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(this.getZ());

        if (this.world.isChunkLoaded(this.getBlockPos()))
        {
            return this.world.getLightLevel(LightType.BLOCK, new BlockPos(i, 255, j));
        }
        else
        {
            return 0;
        }
    }

    public float getBrightnessAtEyes() {
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(this.getZ());

        if (this.world.isChunkLoaded(getBlockPos()))
        {
            return this.world.getBrightness(new BlockPos(i, 255, j));
        } else {
            return 0.0F;
        }
    }

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    private void setBlock(int x, int y, int z, Block block) {
        world.setBlockState(new BlockPos(x, y, z), block.getDefaultState());
    }

    private Block getBlock(int x, int y, int z) {
        return world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}

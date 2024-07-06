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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import assets.rivalrebels.common.packet.RhodesJumpPacket;
import assets.rivalrebels.common.packet.RhodesPacket;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
import assets.rivalrebels.common.tileentity.TileEntityRhodesActivator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import java.util.*;

public class EntityRhodes extends Entity {
    public static final EntityDataAccessor<Boolean> FIRE = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> PLASMA = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.BOOLEAN);

    public int health = RRConfig.SERVER.getRhodesHealth();
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
	public Player rider;
	public Player passenger1;
	public Player passenger2;
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

    public EntityRhodes(EntityType<? extends EntityRhodes> type, Level world) {
        super(type, world);
    }

	public EntityRhodes(Level w)
	{
		this(RREntities.RHODES, w);
		noCulling = true;
		setBoundingBox(new AABB(-5*scale, -15*scale, -5*scale, 5*scale, 15*scale, 5*scale));
		noPhysics = true;
		//pushSpeedReduction = 100;
		//setMaxUpStep(0);
		actionqueue.add(new RhodesAction(0, 1));
		RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
		itexfolder = texfolder;
		if (texfolder != -1)
		{
			itexloc = texloc;
		}
		if (forcecolor == -1) {
            int[] rhodesTeams = RRConfig.SERVER.getRhodesTeams();
            colorType = (byte) rhodesTeams[lastct];
			if (!w.isClientSide())
			{
				lastct++;
				if (lastct == rhodesTeams.length) lastct = 0;
			}
		}
		else
		{
			colorType = (byte) forcecolor;
		}
		RandomSource random = RandomSource.create(RRConfig.SERVER.getRhodesRandomSeed());
		nukecount = RRConfig.SERVER.getRhodesNukes();
		nukecount += nukecount * random.nextFloat() * RRConfig.SERVER.getRhodesRandomAmmoBonus();
		rocketcount += rocketcount * random.nextFloat() * RRConfig.SERVER.getRhodesRandomAmmoBonus();
		flamecount += flamecount * random.nextFloat() * RRConfig.SERVER.getRhodesRandomAmmoBonus();
	}

	public EntityRhodes(Level w, double x, double y, double z, float s)
	{
		this(w);
		scale = s;
		if (scale >= 2.0) {
			nukecount *= 0.25;
			rocketcount *= 0.004;
		}
		health = health - 5000 + (int)(5000 * Math.min(scale,4));
		setBoundingBox(new AABB(-5*scale, -15*scale, -5*scale, 5*scale, 15*scale, 5*scale));
		setPos(x, y, z);
		if (!level().isClientSide()) {
            for (Player player : level().players()) {
                player.displayClientMessage(RRIdentifiers.warning().append(" ").append(Component.translatable(RivalRebels.MODID + ".warning_tsar_is_armed", getName())), false);
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
        makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
        fallDistance = 0.0F;
		if (health > 0) {
			float headY = 0;
			float syaw = Mth.sin(bodyyaw * 0.01745329252f);
			float cyaw = Mth.cos(bodyyaw * 0.01745329252f);
			float leftlegheight = 7.26756f
					+ (Mth.cos((leftthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
					+ (Mth.cos((leftthighpitch+leftshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
			float rightlegheight = 7.26756f
					+ (Mth.cos((rightthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
					+ (Mth.cos((rightthighpitch+rightshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
			leftlegheight *= scale;
			rightlegheight *= scale;
			float bodyY = Math.max(leftlegheight, rightlegheight);
			if (!level().isClientSide())
			{
				doAITick(syaw, cyaw);
				breakBlocks(syaw, cyaw, leftlegheight, rightlegheight, bodyY);
				if (tickCount % 5 == 0) {
                    for (Player player : level().players()) {
                        ServerPlayNetworking.send((ServerPlayer) player, new RhodesPacket(this));
                    }
                }
			}
            Vec3 add = getDeltaMovement().add(position());
            setPosRaw(add.x(), add.y(), add.z());
			if (tickCount % 3 == 0) doCollisions();
			ticksSinceLastPacket++;
			setPos(getX(), getY(), getZ());

			RivalRebels.proxy.setOverlay(this);

			if (rider!=null)
			{
				rider.setPos(((getX()+syaw*5.5*scale) - rider.getX()) * 0.33f + rider.getX(), ((getY() + bodyY - 10*scale - (level().isClientSide?0:rider.getEyeHeight(rider.getPose()))) - rider.getY()) * 0.33f + rider.getY(), ((getZ()+cyaw*5.5*scale) - rider.getZ()) * 0.33f + rider.getZ());
				rider.setOnGround(true);
				if (level().isClientSide()) RivalRebels.round.setInvisible(rider);
				rider.makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
				rider.getAbilities().invulnerable = true;
				if (level().isClientSide && rider == Minecraft.getInstance().player) ClientPlayNetworking.send(new RhodesJumpPacket(this.getId(), RivalRebels.proxy.spacebar(), RivalRebels.proxy.a(), RivalRebels.proxy.w(), RivalRebels.proxy.d(), RivalRebels.proxy.c(), RivalRebels.proxy.f(), RivalRebels.proxy.s(), RivalRebels.proxy.x(), RivalRebels.proxy.z(), RivalRebels.proxy.g()));
			}
			if (passenger1 != null)
			{
				float offset = 1.62f;
				if (level().isClientSide())
				{
					if (Minecraft.getInstance().player == passenger1)
					{
						offset = 0;
					}
				}
				passenger1.setPos(getX()+cyaw*6.5f*scale,
										getY() + bodyY - 6.38f*scale - offset,
										getZ()-syaw*6.5f*scale);
				passenger1.setOnGround(true);
				passenger1.makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
			}
			if (passenger2 != null)
			{
				float offset = 1.62f;
				if (level().isClientSide())
				{
					if (Minecraft.getInstance().player == passenger2)
					{
						offset = 0;
					}
				}
				passenger2.setPos(getX()-cyaw*6.5f*scale,
										getY() + bodyY - 6.38f*scale - offset,
										getZ()+syaw*6.5f*scale);
				passenger2.setOnGround(true);
				passenger2.makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
			}
		}
		else
		{
			if (!level().isClientSide())
			{
				if (health == 0) {
                    MutableComponent text = RRIdentifiers.status().append(" ").append(getName()).append(" ").append("RivalRebels.meltdown").append((rider == null ? Component.empty() : Component.empty().append(" ").append(rider.getName())));
                    for (Player player : level().players()) {
                        player.sendSystemMessage(text);
                    }
                }
				if (tickCount % 5 == 0) {
                    for (Player player : level().players()) {
                        ServerPlayNetworking.send((ServerPlayer) player, new RhodesPacket(this));
                    }
                }
				if (health < -100)
				{
					kill();
				}
				if (health == 0)
				{
					float syaw = Mth.sin(bodyyaw * 0.01745329252f);
					float cyaw = Mth.cos(bodyyaw * 0.01745329252f);
					level().addFreshEntity(new EntityRhodesHead(level(), getX(), getY()+13*scale, getZ(), scale, colorType));
					level().addFreshEntity(new EntityRhodesTorso(level(), getX(), getY()+7*scale, getZ(), scale, colorType));
					level().addFreshEntity(new EntityRhodesLeftUpperArm(level(), getX()+cyaw*6.4*scale, getY()+7*scale, getZ()+syaw*6.4*scale,scale, colorType));
					level().addFreshEntity(new EntityRhodesRightUpperArm(level(), getX()-cyaw*6.4*scale, getY()+7*scale, getZ()-syaw*6.4*scale,scale, colorType));
					level().addFreshEntity(new EntityRhodesLeftLowerArm(level(), getX()+cyaw*6.4*scale, getY()+3*scale, getZ()+syaw*6.4*scale,scale, colorType));
					level().addFreshEntity(new EntityRhodesRightLowerArm(level(), getX()-cyaw*6.4*scale, getY()+3*scale, getZ()-syaw*6.4*scale,scale, colorType));
					level().addFreshEntity(new EntityRhodesLeftUpperLeg(level(), getX()+cyaw*3*scale, getY()-3*scale, getZ()+syaw*3*scale,scale, colorType));
					level().addFreshEntity(new EntityRhodesRightUpperLeg(level(), getX()-cyaw*3*scale, getY()-3*scale, getZ()-syaw*3*scale,scale, colorType));
					level().addFreshEntity(new EntityRhodesLeftLowerLeg(level(), getX()+cyaw*3*scale, getY()-10*scale, getZ()+syaw*3*scale,scale, colorType));
					level().addFreshEntity(new EntityRhodesRightLowerLeg(level(), getX()-cyaw*3*scale, getY()-10*scale, getZ()-syaw*3*scale,scale, colorType));
				}
			}
			health--;
		}

		if (rider != null)
		{
			if ((rider.isShiftKeyDown() || !rider.isAlive()) && RivalRebels.rhodesExit)
			{
				freeze = false;
				if (!rider.isCreative()) rider.getAbilities().invulnerable = false;
				rider = null;
			}
			if (health <= 0 && rider != null)
			{
				freeze = false;
				if (!rider.isCreative())
				{
					rider.getAbilities().invulnerable = false;
					rider.hurt(level().damageSources().fellOutOfWorld(), 2000000);
				}
				rider = null;
			}
		}

		if (passenger1 != null)
		{
			if ((passenger1.isShiftKeyDown() || !passenger1.isAlive()) && RivalRebels.rhodesExit)
			{
				if (!passenger1.isCreative()) passenger1.getAbilities().invulnerable = false;
				passenger1 = null;
			}
			if (health <= 0 && passenger1 != null)
			{
				if (!passenger1.isCreative())
				{
					passenger1.getAbilities().invulnerable = false;
					passenger1.hurt(level().damageSources().fellOutOfWorld(), 2000000);
				}
				passenger1 = null;
			}
		}

		if (passenger2 != null)
		{
			if ((passenger2.isShiftKeyDown() || !passenger2.isAlive()) && RivalRebels.rhodesExit)
			{
				if (!passenger2.isCreative()) passenger2.getAbilities().invulnerable = false;
				passenger2 = null;
			}
			if (health <= 0 && passenger2 != null)
			{
				if (!passenger2.isCreative())
				{
					passenger2.getAbilities().invulnerable = false;
					passenger2.hurt(level().damageSources().fellOutOfWorld(), 2000000);
				}
				passenger2 = null;
			}
		}

		prevflame = flame;
	}

	private void breakBlocks(float syaw, float cyaw, float leftlegheight, float rightlegheight, float bodyY)
	{
		float leftlegstride = (Mth.sin((leftthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
				+ (Mth.sin((leftthighpitch+leftshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
		float rightlegstride = (Mth.sin((rightthighpitch+11.99684962f)*0.01745329252f) * 7.331691240f)
				+ (Mth.sin((rightthighpitch+rightshinpitch-12.2153067f)*0.01745329252f) * 8.521366426f);
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
			if (RRConfig.SERVER.getRhodesBlockBreak())
			{
				int sx = (int) (getX() - 5.0f * scale);
				int sy = (int) (getY() - 15.0f * scale);
				int sz = (int) (getZ() - 5.0f * scale);
				int ex = (int) (getX() + 5.0f * scale);
				int ey = (int) (getY() + 15.0f * scale);
				int ez = (int) (getZ() + 5.0f * scale);
				for (int y = sy; y < ey; y++)
				{
					if ((y + tickCount) % 8 == 0)
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
				int irpyyoff = irpy + (tickCount % 6);
				int ilpyyoff = ilpy + (tickCount % 6);
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
				int py = (int) (getY()-5*scale + (tickCount%20)*scale);
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
								new Explosion(level(), px, py, pz, 3, false, true, RivalRebelsDamageSource.rocket(level()));
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
				if (getDeltaMovement().y() < 0.125)
				{
                    setDeltaMovement(getDeltaMovement().x(), 0.125, getDeltaMovement().z());
					if (!isFire() && random.nextInt(32)==0)
					{
						RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
						setFire(true);
					}
				}
			} else if (getDeltaMovement().y() < 0) {
                setDeltaMovement(getDeltaMovement().x(), 0, getDeltaMovement().z());
                setFire(false);
                setPosRaw(getX(), (int) getY(), getZ());
			}
		}
		else if (flying == 0) {
            push(0, -0.03f, 0);
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
                setDeltaMovement(getDeltaMovement().x(), 0.03, getDeltaMovement().z());
			}
			else
			{
                push(0, 0.03f, 0);
			}
			if (random.nextInt(32)==0)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
			}
			setFire(true);
		}
	}

	private void doCollisions() {
        for (Entity e : level().getEntities(this, Shapes.INFINITY.bounds())) {
            if (e == rider || e == passenger1 || e == passenger2) continue;
            double bbd = (e.getBbWidth() + 10 * scale) * 0.5;
            double bbh = (e.getBbHeight() + 30 * scale) * 0.5;
            if (e instanceof EntityRhodes) {
                bbd = 10 * (((EntityRhodes) e).scale + scale) * 0.5;
                bbh = 30 * (((EntityRhodes) e).scale + scale) * 0.5;
            }
            double dist = (e.getX() - getX()) * (e.getX() - getX()) + (e.getZ() - getZ()) * (e.getZ() - getZ());
            if ((ac == 0 || ac == 1 || ac == 11 || !RRConfig.SERVER.isRhodesAIEnabled()) && e instanceof Player && dist < bbd * bbd * 0.25f && e.getY() < getY() + bbh + 1 && e.getY() > getY() - bbh + 1) {
                if (rider == null) {
                    rider = (Player) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                } else if (passenger1 == null) {
                    passenger1 = (Player) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                } else if (passenger2 == null) {
                    passenger2 = (Player) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                }
            } else if (dist < bbd * bbd && e.getY() > getY() - bbh && e.getY() < getY() + bbh) {
                if (e != this && !(e instanceof FallingBlockEntity) && !(e instanceof EntityDebris)) {
                    if (e.getY() > getY() + 15) {
                        e.setDeltaMovement(getDeltaMovement().multiply(1, -0.5, 1));
                        e.setPosRaw(getX(), getY() + bbh, getZ());
                    } else if (e.getY() < getY() - 15) {
                        e.setDeltaMovement(getDeltaMovement().multiply(1, -0.5, 1));
                        e.setPosRaw(getX(), getY() - bbh, getZ());
                    } else {
                        e.setDeltaMovement(getDeltaMovement().multiply(-1, 1, -1));
                        double d3 = bbd / Math.sqrt(dist);
                        e.setPosRaw(getX() + (e.getX() - getX()) * d3,
                             e.getY(),
                            getZ() + (e.getZ() - getZ()) * d3);
                    }
                    e.setPos(e.getX(), e.getY(), e.getZ());
                }

                if (e instanceof EntityRocket) {
                    e.tickCount = RRConfig.SERVER.getRpgDecay();
                    this.hurt(level().damageSources().generic(), 20);
                } else if (e instanceof EntitySeekB83) {
                    e.tickCount = 800;
                    this.hurt(level().damageSources().generic(), 24);
                } else if (e instanceof EntityHackB83) {
                    ((EntityHackB83) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 40);
                } else if (e instanceof EntityB83) {
                    ((EntityB83) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 40);
                } else if (e instanceof EntityBomb) {
                    ((EntityBomb) e).explode(true);
                    for (int i = 0; i < RRConfig.SERVER.getBombDamageToRhodes(); i++)
                        this.hurt(level().damageSources().generic(), 50);
                } else if (e instanceof EntityNuke) {
                    ((EntityNuke) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 80);
                } else if (e instanceof EntityTsar) {
                    ((EntityTsar) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 100);
                } else if (e instanceof EntityTheoreticalTsar) {
                    ((EntityTheoreticalTsar) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 100);
                } else if (e instanceof EntityAntimatterBomb) {
                    ((EntityAntimatterBomb) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 100);
                } else if (e instanceof EntityTachyonBomb) {
                    ((EntityTachyonBomb) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 100);
                } else if (e instanceof EntityHotPotato) {
                    e.tickCount = -100;
                    this.hurt(level().damageSources().generic(), 100);
                } else if (e instanceof EntityB83NoShroom) {
                    ((EntityB83NoShroom) e).ticksInAir = -100;
                    this.hurt(level().damageSources().generic(), 40);
                } else if (e instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) e).explode();
                    this.hurt(level().damageSources().generic(), 8);
                } else if (e instanceof EntityFlameBall) {
                    e.kill();
                    this.hurt(level().damageSources().generic(), 3);
                } else if (e instanceof EntityFlameBall1) {
                    e.kill();
                    this.hurt(level().damageSources().generic(), 4);
                } else if (e instanceof EntityFlameBall2) {
                    e.kill();
                    this.hurt(level().damageSources().generic(), 2);
                } else if (e instanceof EntityLaserBurst) {
                    e.kill();
                    this.hurt(level().damageSources().generic(), 4);
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
		if (health*2 < RRConfig.SERVER.getRhodesHealth()) endangered = true;
		if (!b2spirit) {
            setDeltaMovement(0, getDeltaMovement().y(), 0);
		}
		if (rider != null)
		{
			if (b2spirit && !freeze && b2energy == 0 && scale < 1.5f && scale > 0.5f)
			{
				freeze = true;
				nukecount--;
				health -= 1000;
				level().addFreshEntity(new EntityB2Spirit(this));
			}
			if (RivalRebels.rhodesHold) return;
			if (energy < maxenergy) energy += recharge;
			if (!RRConfig.SERVER.isInfiniteAmmo())
			{
				rocket &= rocketcount > 0;
				flame &= flamecount > 0;
			}
			if (!RRConfig.SERVER.isInfiniteNukes())
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
				if (tickCount%8==0) {
                    this.playSound(RRSounds.FORCE_FIELD, 10, 1);
                }
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
						level().explode(null, this.getX(), this.getY(), this.getZ(), 6.0F, Level.ExplosionInteraction.MOB);
						level().addFreshEntity(new EntityB2Frag(level(), this, 0));
						level().addFreshEntity(new EntityB2Frag(level(), this, 1));
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
				float goal = ((((rider.yHeadRot+bodyyaw)%360)+360+360)%360)-180;
				bodyyaw += Math.max(Math.min(goal, 2), -2);
				if (flying > 0)
				{
					if (b2spirit)
					{
                        setDeltaMovement(getDeltaMovement().add(syaw * 0.1f, 0, cyaw * 0.1f));
						double speed = Math.sqrt(getDeltaMovement().x() * getDeltaMovement().x() + getDeltaMovement().z() * getDeltaMovement().z());
						if (speed > 0.7) {
                            Vec3 vec3d = getDeltaMovement().scale(1 / speed).scale(0.7);
                            setDeltaMovement(vec3d.x(), getDeltaMovement().y(), vec3d.z());
						}
					}
					else
					{
                        setDeltaMovement(syaw * 0.5f, getDeltaMovement().y(), cyaw * 0.5f);
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
			double endx = startx + range * (-Mth.sin(rider.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(rider.getXRot() / 180.0F * (float) Math.PI));
			double endy = starty + range * (-Mth.sin(rider.getXRot() / 180.0F * (float) Math.PI));
			double endz = startz + range * (Mth.cos(rider.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(rider.getXRot() / 180.0F * (float) Math.PI));

			Vec3 hit = rayTraceBlocks((float)startx, (float)starty, (float)startz, (float)endx, (float)endy, (float)endz);

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
				laserOn = (byte)(level().random.nextInt(2)+1);
				RivalRebelsSoundPlayer.playSound(this, 22, 1, 30f, 0f);
				float x = (float) (getX() - endx);
				float y = (float) (getY() + 13*scale - endy);
				float z = (float) (getZ() - endz);
				float pitch = (720f-atan2((float)Math.sqrt(x*x+z*z)*(syaw*x+cyaw*z>0?-1f:1f), y)) %360-270;

				headpitch += Math.max(-20, Math.min(20, (pitch-headpitch)));

				if (Math.abs(headpitch-pitch) < 10f && tickCount % 3 == 0)
				{
					range = 70*scale;
					Vec3 start = new Vec3(getX(), getY()+13*scale, getZ());
					Vec3 end = new Vec3(0, 0, range);
					end = end.xRot(-headpitch / 180.0F * (float) Math.PI);
					end = end.yRot(bodyyaw / 180.0F * (float) Math.PI);
					end = end.add(getX(), getY()+13*scale, getZ());
                    for (Entity e : level().getEntities(this, new AABB(
                        Math.min(start.x, end.x) - 5,
                            Math.min(start.y, end.y) - 5,
                            Math.min(start.z, end.z) - 5,
                            Math.max(start.x, end.x) + 5,
                            Math.max(start.y, end.y) + 5,
                            Math.max(start.z, end.z) + 5))) {
                        if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) &&
                                !(e instanceof ThrowableProjectile
                                        || e instanceof EntityInanimate
                                        || e instanceof ItemEntity
                                        || e instanceof Boat
                                        || e instanceof Minecart)
                                && e != rider) {
                            Vec3 entity = new Vec3(e.getX(), e.getY(), e.getZ());
                            double bbx = 1;
                            if (e instanceof EntityRhodes) bbx = 20 * ((EntityRhodes) e).scale;
                            if (entity.subtract(start).cross(entity.subtract(end)).distanceToSqr(0, 0, 0) < 10000 * bbx) {
                                e.hurt(RivalRebelsDamageSource.laserBurst(level()), 24);
                                if (e instanceof Player player) {
                                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
                                    if (!player.getItemBySlot(slot).isEmpty()) {
                                        player.getItemBySlot(slot).hurtAndBreak(24, player, slot);
                                    }
                                    if (player.getHealth() < 3 && player.isAlive()) {
                                        player.hurt(RivalRebelsDamageSource.laserBurst(level()), 2000000);
                                        player.deathTime = 0;
                                        level().addFreshEntity(new EntityGore(level(), e, 0, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 1, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 2, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 2, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 3, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 3, 0));
                                    }
                                } else {
                                    if (!e.isAlive() || (e instanceof LivingEntity && ((LivingEntity) e).getHealth() < 3)) {
                                        int legs = -1;
                                        int arms = -1;
                                        int mobs = -1;
                                        level().playLocalSound(this, RRSounds.BLASTER_FIRE, getSoundSource(), 1, 4);
                                        if (e instanceof ZombifiedPiglin) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 2;
                                        } else if (e instanceof Zombie) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 1;
                                        } else if (e instanceof Skeleton) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 3;
                                        } else if (e instanceof EnderMan) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 4;
                                        } else if (e instanceof Creeper) {
                                            legs = 4;
                                            arms = 0;
                                            mobs = 5;
                                        } else if (e instanceof MagmaCube) {
                                            legs = 0;
                                            arms = 0;
                                            mobs = 7;
                                        } else if (e instanceof Slime) {
                                            legs = 0;
                                            arms = 0;
                                            mobs = 6;
                                        } else if (e instanceof CaveSpider) {
                                            legs = 8;
                                            arms = 0;
                                            mobs = 9;
                                        } else if (e instanceof Spider) {
                                            legs = 8;
                                            arms = 0;
                                            mobs = 8;
                                        } else if (e instanceof Ghast) {
                                            legs = 9;
                                            arms = 0;
                                            mobs = 10;
                                        } else if (e instanceof EntityB2Spirit
                                                || e instanceof EntityRhodes
                                                || e instanceof EntityRhodesPiece) {
                                            return;
                                        } else {
                                            legs = (int) (e.getBoundingBox().getSize() * 2);
                                            arms = (int) (e.getBoundingBox().getSize() * 2);
                                            mobs = 11;
                                        }
                                        level().addFreshEntity(new EntityGore(level(), e, 0, mobs));
                                        level().addFreshEntity(new EntityGore(level(), e, 1, mobs));
                                        for (int i = 0; i < arms; i++)
                                            level().addFreshEntity(new EntityGore(level(), e, 2, mobs));
                                        for (int i = 0; i < legs; i++)
                                            level().addFreshEntity(new EntityGore(level(), e, 3, mobs));
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
				headpitch = rider.getXRot();
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
							level().addFreshEntity(new EntityPlasmoid(level(), px, py, pz,
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
						level().addFreshEntity(new EntityFlameBall(level(), px, py, pz,
								x, y, z, (8+random.nextDouble()*8)*scale, 0.4f));
						level().addFreshEntity(new EntityFlameBall(level(), px, py, pz,
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
								level().addFreshEntity(new EntityHotPotato(level(), px, py, pz,
										x*cp*5.0f, y*cp*5.0f, z*cp*5.0f));
							else if (scale >= 2.0)
								level().addFreshEntity(new EntityTsar(level(), px, py, pz,
										x*cp*5.0f, y*cp*5.0f, z*cp*5.0f));
							else
								level().addFreshEntity(new EntityB83NoShroom(level(), px, py, pz,
										x*cp, y*cp, z*cp));
						}
					}

					if (rocket && tickCount-lastshot > ((scale >= 2.0)?30:((shotstaken == 21)?80:5)))
					{
						rocketcount--;
						lastshot = tickCount;
						if (shotstaken == 21) shotstaken = 0;
						shotstaken++;
						RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
						float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);

						if (scale >= 2.0)
							level().addFreshEntity(new EntityB83NoShroom(level(), px, py, pz,
									x*cp, y*cp, z*cp));
						else
							level().addFreshEntity(new EntitySeekB83(level(), px, py, pz,
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
			if (RRConfig.SERVER.isRhodesAIEnabled())
			{
				shootRocketsAtBestTarget(-syaw, cyaw);
				shootFlameAtBestTarget(-syaw, cyaw);
				shootLaserAtBestTarget(-syaw, cyaw);
			}
			return;
		}

		if (!RRConfig.SERVER.isRhodesAIEnabled() && ac != 0 && ac != 1 && ac != 11) return;

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
		if (RRConfig.SERVER.isRhodesScaleSpeed()) movescale *= RRConfig.SERVER.getRhodesSpeedScale();
		else movescale = RRConfig.SERVER.getRhodesSpeedScale();
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
			setDeltaMovement(syaw * 0.5f * movescale,
                getDeltaMovement().y(),
                cyaw * 0.5f * movescale);
			rightthighpitch = approach(rightthighpitch,-30);
			leftthighpitch  = approach(leftthighpitch, -30);
			rightshinpitch  = approach(rightshinpitch, 60);
			leftshinpitch   = approach(leftshinpitch,  60);
			break;
		case 10:
			if (teamToRaid == 1 && RivalRebels.round.omegaHealth > 0 && level().getBlockState(RivalRebels.round.omegaObjPos).is(RRBlocks.omegaobj) || teamToRaid != 1 && (teamToRaid == 2 && RivalRebels.round.sigmaHealth > 0 && level().getBlockState(RivalRebels.round.sigmaObjPos).is(RRBlocks.sigmaobj)))
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
					health += RivalRebels.round.takeOmegaHealth(Math.min(50, RRConfig.SERVER.getRhodesHealth()-health));
				}
				if (teamToRaid == 2)
				{
					health += RivalRebels.round.takeSigmaHealth(Math.min(50, RRConfig.SERVER.getRhodesHealth()-health));
				}
				if (health != RRConfig.SERVER.getRhodesHealth()) counter++;
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
                level().playLocalSound(this, RRSounds.ARTILLERY_EXPLODE, getSoundSource(), 30, 1);
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
        List<Entity> otherEntities = level().getEntities(this, Shapes.INFINITY.bounds());
        for (Entity e : otherEntities) {
            if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) && (!(e instanceof EntityRhodes) || (RRConfig.SERVER.isFriendlyFireRhodesEnabled() && (RRConfig.SERVER.isTeamFriendlyFireRhodesEnabled() || ((EntityRhodes) e).colorType != colorType))) &&
                    !(e instanceof ThrowableProjectile
                            || e instanceof EntityInanimate
                            || e instanceof ItemEntity
                            || e instanceof Animal
                            || e instanceof Villager
                            || e instanceof Bat
                            || e instanceof Squid
                            || e instanceof Boat
                            || e instanceof Minecart)) {
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
		if (RRConfig.SERVER.isRhodesScaleSpeed()) movescale *= RRConfig.SERVER.getRhodesSpeedScale();
		else movescale = RRConfig.SERVER.getRhodesSpeedScale();
		setDeltaMovement(syaw * 0.125f*movescale,
            getDeltaMovement().y(),
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
                    level().playLocalSound(this, RRSounds.ARTILLERY_DESTROY, getSoundSource(), 4.5f, 0.8f);
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
                    level().playLocalSound(this, RRSounds.ARTILLERY_DESTROY, getSoundSource(), 4.5f, 0.8f);
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
					if (dist < 100*100*scale*scale && rayTraceBlocks(px, py, pz, (float)lastRocketTarget.getX(), (float)lastRocketTarget.getY()+lastRocketTarget.getBbHeight()/2f, (float)lastRocketTarget.getZ()) == null)
					{
						priority = getPriority(lastRocketTarget)-(float)Math.sqrt(dist)+10;
					}
				}
			}
			if (priority <= 0) lastRocketTarget = null;
            for (Entity e : level().getEntities(this, new AABB(px - 100 * scale, py - 100 * scale, pz - 100 * scale, px + 100 * scale, py + 100 * scale, pz + 100 * scale))) {
                if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) &&
                    !(e instanceof ThrowableProjectile
                        || e instanceof EntityInanimate
                        || e instanceof ItemEntity
                        || e instanceof Animal
                        || e instanceof Villager
                        || e instanceof Bat
                        || e instanceof Squid
                        || e instanceof Boat
                        || e instanceof Minecart)) {
                    float dx = (float) e.getX() - px;
                    float dz = (float) e.getZ() - pz;
                    float dot = (cyaw * dx + syaw * dz);
                    if (dot * Math.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                        float dy = (float) e.getY() - py;
                        float dist = dx * dx + dy * dy + dz * dz;
                        if (dist < 100 * 100 * scale * scale) {
                            float prio = getPriority(e) - (float) Math.sqrt(dist);
                            if (prio > priority && rayTraceBlocks(px, py, pz, (float) e.getX(), (float) e.getY() + e.getBbHeight() / 2f, (float) e.getZ()) == null) {
                                lastRocketTarget = e;
                                priority = prio;
                            }
                        }
                    }
                }
            }

            for (BlockEntity e : BLOCK_ENTITIES.values()) {
                if (e instanceof TileEntityRhodesActivator || e instanceof TileEntityNukeCrate || e instanceof TileEntityReactor) {
                    float dx = (float) e.getBlockPos().getX() - px;
                    float dz = (float) e.getBlockPos().getZ() - pz;
                    float dot = (cyaw * dx + syaw * dz);
                    if (dot * Math.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                        float dy = (float) e.getBlockPos().getY() - py;
                        float dist = dx * dx + dy * dy + dz * dz;
                        if (dist < 100 * 100) {
                            float prio = 300 - (float) Math.sqrt(dist);
                            if (prio > priority && rayTraceBlocks(px, py, pz, (float) e.getBlockPos().getX(), (float) e.getBlockPos().getY(), (float) e.getBlockPos().getZ()) == null) {
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
			float x = px - (float)te.getBlockPos().getX();
			float y = py - (float)te.getBlockPos().getY();
			float z = pz - (float)te.getBlockPos().getZ();
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

			if (pointing && tickCount-lastshot > ((scale >= 2.0)?30:((shotstaken == 21)?80:5)))
			{
				rocketcount--;
				lastshot = tickCount;
				if (shotstaken == 21) shotstaken = 0;
				shotstaken++;
				RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
				float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);
				if (scale >= 2.0)
					level().addFreshEntity(new EntityB83NoShroom(level(), px, py, pz,
							x*cp, y*cp, z*cp));
				else
					level().addFreshEntity(new EntitySeekB83(level(), px, py, pz,
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
				if (pointing && tickCount % 100 == 0)
				{
					RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
					float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);
					if (scale >= 2.0)
						level().addFreshEntity(new EntityTsar(level(), px, py, pz,
								x*cp*5.0f, y*cp*5.0f, z*cp*5.0f));
					else
						level().addFreshEntity(new EntityB83NoShroom(level(), px, py, pz,
								x*cp, y*cp, z*cp));
				}
			}
			else
			{
				if (pointing && tickCount-lastshot > ((scale >= 2.0)?30:((shotstaken == 21)?80:5)))
				{
					rocketcount--;
					lastshot = tickCount;
					if (shotstaken == 21) shotstaken = 0;
					shotstaken++;
					RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
					float cp = -0.5f/(float)Math.sqrt(x*x+y*y+z*z);
					if (scale >= 2.0)
						level().addFreshEntity(new EntityB83NoShroom(level(), px, py, pz,
								x*cp, y*cp, z*cp));
					else
						level().addFreshEntity(new EntitySeekB83(level(), px, py, pz,
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
					if (dist < 40*40*scale*scale && rayTraceBlocks(px, py, pz, (float)lastFlameTarget.getX(), (float)lastFlameTarget.getY()+lastFlameTarget.getBbHeight(), (float)lastFlameTarget.getZ()) == null)
					{
						priority = getPriority(lastFlameTarget)-(float)Math.sqrt(dist)+10;
					}
				}
			}
			if (priority <= 0) lastFlameTarget = null;
            for (Entity e : level().getEntities(this, new AABB(px - 40 * scale, py - 40 * scale, pz - 40 * scale, px + 40 * scale, py + 40 * scale, pz + 40 * scale))) {
                if (e.isAlive() &&
                    !(e instanceof ThrowableProjectile
                        || e instanceof EntityInanimate
                        || e instanceof ItemEntity
                        || e instanceof Animal
                        || e instanceof Villager
                        || e instanceof Bat
                        || e instanceof Squid
                        || e instanceof Boat
                        || e instanceof Minecart)) {
                    float dx = (float) e.getX() - px;
                    float dz = (float) e.getZ() - pz;
                    float dot = (-cyaw * dx + -syaw * dz);
                    if (dot * Math.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                        float dy = (float) e.getY() - py;
                        float dist = dx * dx + dy * dy + dz * dz;
                        if (dist < 40 * 40 * scale * scale) {
                            float prio = getPriority(e) - (float) Math.sqrt(dist);
                            if (prio > priority && rayTraceBlocks(px, py, pz, (float) e.getX(), (float) e.getY() + e.getBbHeight(), (float) e.getZ()) == null) {
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
			float y = py - (float)lastFlameTarget.getY() - (lastFlameTarget.getBbHeight()*0.5f);
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
				level().addFreshEntity(new EntityFlameBall(level(), px, py, pz,
						x, y, z, (8+random.nextDouble()*8)*scale, 0.4f));
				level().addFreshEntity(new EntityFlameBall(level(), px, py, pz,
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
						&&rayTraceBlocks((float)getX(), (float)getY() + 13, (float)getZ(), (float)lastLaserTarget.getX(), (float)lastLaserTarget.getY()+lastLaserTarget.getBbHeight()/2f, (float)lastLaserTarget.getZ()) == null)
				{
					priority = getPriority(lastLaserTarget)-(float)Math.sqrt(tempdi);
					if (priority > 0) priority += 10;
				}
			}
			if (priority <= 0) lastLaserTarget = null;

            for (Entity e : level().getEntities(this, new AABB(getX() - 70 * scale, getY() + 13 * scale - 70 * scale, getZ() - 70 * scale, getX() + 70 * scale, getY() + 13 * scale + 70 * scale, getZ() + 70 * scale))) {
                if (e.isAlive() && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() > 0) &&
                        !(e instanceof ThrowableProjectile
                                || e instanceof EntityInanimate
                                || e instanceof ItemEntity
                                || e instanceof Animal
                                || e instanceof Villager
                                || e instanceof Bat
                                || e instanceof Squid
                                || e instanceof Boat
                                || e instanceof Minecart)) {
                    float x = (float) (e.getX() - getX());
                    float z = (float) (e.getZ() - getZ());
                    if (Math.abs(cyaw * x + syaw * z) < 2) {
                        float y = (float) (e.getY() - 13 - getY());
                        float dist = x * x + y * y + z * z;
                        if (dist < 70 * 70 * scale * scale) {
                            if (y * Math.abs(y) > -0.64f * dist) {
                                float prio = getPriority(e) - (float) Math.sqrt(dist);
                                if (prio > priority && rayTraceBlocks((float) getX(), (float) getY() + 13, (float) getZ(), (float) e.getX(), (float) e.getY() + e.getBbHeight() / 2f, (float) e.getZ()) == null) {
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
				if (lastLaserTarget instanceof Player player) {
					EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
					if (!player.getItemBySlot(slot).isEmpty()) {
                        player.getItemBySlot(slot).hurtAndBreak(24, player, slot);
					}
					lastLaserTarget.hurt(RivalRebelsDamageSource.laserBurst(level()), laserOn==3?16:8);
					if (player.getHealth() < 3 && player.isAlive()) {
						player.hurt(RivalRebelsDamageSource.laserBurst(level()), 2000000);
						player.deathTime = 0;
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 0, 0));
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 1, 0));
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 2, 0));
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 2, 0));
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 3, 0));
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 3, 0));
					}
				}
				else
				{
					lastLaserTarget.hurt(RivalRebelsDamageSource.laserBurst(level()), laserOn==3?16:8);
					if (!lastLaserTarget.isAlive() || (lastLaserTarget instanceof LivingEntity && ((LivingEntity)lastLaserTarget).getHealth() < 3))
					{
						int legs;
						int arms;
						int mobs;
                        level().playLocalSound(this, RRSounds.BLASTER_FIRE, getSoundSource(), 1, 4);
						if (lastLaserTarget instanceof ZombifiedPiglin)
						{
							legs = 2;
							arms = 2;
							mobs = 2;
						}
						else if (lastLaserTarget instanceof Zombie)
						{
							legs = 2;
							arms = 2;
							mobs = 1;
						}
						else if (lastLaserTarget instanceof Skeleton)
						{
							legs = 2;
							arms = 2;
							mobs = 3;
						}
						else if (lastLaserTarget instanceof EnderMan)
						{
							legs = 2;
							arms = 2;
							mobs = 4;
						}
						else if (lastLaserTarget instanceof Creeper)
						{
							legs = 4;
							arms = 0;
							mobs = 5;
						}
						else if (lastLaserTarget instanceof MagmaCube)
						{
							legs = 0;
							arms = 0;
							mobs = 7;
						}
						else if (lastLaserTarget instanceof Slime)
						{
							legs = 0;
							arms = 0;
							mobs = 6;
						}
						else if (lastLaserTarget instanceof CaveSpider)
						{
							legs = 8;
							arms = 0;
							mobs = 9;
						}
						else if (lastLaserTarget instanceof Spider)
						{
							legs = 8;
							arms = 0;
							mobs = 8;
						}
						else if (lastLaserTarget instanceof Ghast)
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
							legs = (int) (lastLaserTarget.getBoundingBox().getSize() * 2);
							arms = (int) (lastLaserTarget.getBoundingBox().getSize() * 2);
							mobs = 11;
						}
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 0, mobs));
						level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 1, mobs));
						for (int i = 0; i < arms; i++)
							level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 2, mobs));
						for (int i = 0; i < legs; i++)
							level().addFreshEntity(new EntityGore(level(), lastLaserTarget, 3, mobs));
						lastLaserTarget.kill();
					}
				}
			}
		}
	}

	//lower is less prior
	private float getPriority(Entity e) {
		if (e instanceof Player) return e.isInvulnerable()?-100:600;
		if (e instanceof LivingEntity) return ((LivingEntity)e).getMaxHealth()+100;
		if ((e instanceof EntityRhodes && (RRConfig.SERVER.isFriendlyFireRhodesEnabled() && (RRConfig.SERVER.isTeamFriendlyFireRhodesEnabled() || ((EntityRhodes)e).colorType != colorType))) || e instanceof EntityB2Spirit) return 800;
		if (e.getBoundingBox().getSize() > 3) return (float) (e.getBoundingBox().getSize()*3 + 500 + e.getBbHeight());
		return 0;
	}

	private Vec3 rayTraceBlocks(float sx, float sy, float sz, float ex, float ey, float ez)
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
			if (level().getBlockState(new BlockPos(X, Y, Z)).canOcclude())
			{
				return new Vec3(X, Y, Z);
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

	public Component getName()
	{
        return Component.nullToEmpty(names[colorType]);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt)
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
	public void readAdditionalSaveData(CompoundTag nbt)
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
	public boolean hurt(DamageSource par1DamageSource, float par2)
	{
		super.hurt(par1DamageSource, par2);
		if (isAlive() && !level().isClientSide && health > 0 && !forcefield)
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
                level().playLocalSound(this, RRSounds.ARTILLERY_EXPLODE, getSoundSource(), 30, 1);
			}
		}

		return true;
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(FIRE, false);
        builder.define(PLASMA, false);
    }

    public boolean isFire() {
        return entityData.get(FIRE);
    }

    public void setFire(boolean fire) {
        entityData.set(FIRE, fire);
    }

    public boolean isPlasma() {
        return entityData.get(PLASMA);
    }

    public void setPlasma(boolean plasma) {
        entityData.set(PLASMA, plasma);
    }

    @Environment(EnvType.CLIENT)
    public int getBrightnessForRender()
    {
        int i = Mth.floor(this.getX());
        int j = Mth.floor(this.getZ());

        if (this.level().hasChunkAt(this.blockPosition()))
        {
            return this.level().getBrightness(LightLayer.BLOCK, new BlockPos(i, 255, j));
        }
        else
        {
            return 0;
        }
    }

    public float getLightLevelDependentMagicValue() {
        int i = Mth.floor(this.getX());
        int j = Mth.floor(this.getZ());

        if (this.level().hasChunkAt(blockPosition()))
        {
            return this.level().getLightLevelDependentMagicValue(new BlockPos(i, 255, j));
        } else {
            return 0.0F;
        }
    }

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    private void setBlock(int x, int y, int z, Block block) {
        level().setBlockAndUpdate(new BlockPos(x, y, z), block.defaultBlockState());
    }

    private Block getBlock(int x, int y, int z) {
        return level().getBlockState(new BlockPos(x, y, z)).getBlock();
    }
}

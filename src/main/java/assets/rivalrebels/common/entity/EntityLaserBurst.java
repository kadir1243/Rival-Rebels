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

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.item.PrimedTnt;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;

public class EntityLaserBurst extends EntityInanimate {
	private LivingEntity shooter;

    public EntityLaserBurst(EntityType<? extends EntityLaserBurst> type, Level world) {
        super(type, world);
    }

	public EntityLaserBurst(Level par1World) {
		this(RREntities.LASER_BURST, par1World);
	}

	public EntityLaserBurst(Level par1World, LivingEntity player)
	{
		this(par1World);
		shooter = player;
		moveTo(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYRot(), player.getXRot());
        setPosRaw(getX() - (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * 0.2F),
            getY() - 0.12D,
            getZ() - (Mth.sin(getYRot() / 180.0F * (float) Math.PI) * 0.2F));
		setPos(getX(), getY(), getZ());
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (-Mth.sin(getXRot() / 180.0F * (float) Math.PI)));

        setAccurateHeading(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), 4F, 0.075f);
	}

	public EntityLaserBurst(Level par1World, Player player, boolean accurate)
	{
		this(par1World);
		shooter = player;
		moveTo(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYRot(), player.getXRot());
        setPosRaw(getX() - (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * 0.2F),
            getY() - 0.12D,
            getZ() - (Mth.sin(getYRot() / 180.0F * (float) Math.PI) * 0.2F));
		setPos(getX(), getY(), getZ());
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (-Mth.sin(getXRot() / 180.0F * (float) Math.PI)));

        setAccurateHeading(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), 4F * (float)random.nextDouble() + 1.0F, accurate?0.001F:0.075F);
	}
	public EntityLaserBurst(Level par1World, double x, double y,double z, double mx, double my, double mz) {
		this(par1World);
		setPos(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setDeltaMovement(mx, my, mz);
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

	public EntityLaserBurst(Level par1World, double x, double y, double z, double mx, double my, double mz, LivingEntity player)
	{
		this(par1World);
		shooter = player;
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
	}

	public void setAccurateHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = Mth.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += random.nextGaussian() * par8;
		par3 += random.nextGaussian() * par8;
		par5 += random.nextGaussian() * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
        setDeltaMovement(par1, par3, par5);
		float var10 = Mth.sqrt((float) (par1 * par1 + par5 * par5));
		setYRot(yRotO = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
		setXRot(xRotO = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
	}

	@Override
	public float getLightLevelDependentMagicValue()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    @Override
	public void tick()
	{
		super.tick();

		++tickCount;
		if (tickCount > 60) kill();

		Vec3 var15 = position();
		Vec3 var2 = position().add(getDeltaMovement());
		HitResult var3 = level().clip(new ClipContext(var15, var2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		var15 = position();
		var2 = position().add(getDeltaMovement());

		if (var3 != null)
		{
			var2 = var3.getLocation();
		}

		if (!level().isClientSide)
		{
			Entity var4 = null;
			List<Entity> var5 = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.canBeCollidedWith() && var9 != shooter) {
                    float var10 = 0.3F;
                    AABB var11 = var9.getBoundingBox().inflate(var10, var10, var10);
                    Optional<Vec3> var12 = var11.clip(var15, var2);

                    if (var12.isPresent()) {
                        double var13 = var15.distanceTo(var12.get());

                        if (var13 < var6 || var6 == 0.0D) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

			if (var4 != null)
			{
				var3 = new EntityHitResult(var4);
			}
		}

		if (var3 != null)
		{
			if (var3.getType() == HitResult.Type.BLOCK)
			{
                BlockPos pos = ((BlockHitResult) var3).getBlockPos();
                BlockState state = level().getBlockState(pos);
                Block block = state.getBlock();
				if (block == Blocks.TNT) {
					if (!level().isClientSide) {
						PrimedTnt entitytntprimed = new PrimedTnt(level(), (pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), shooter);
						entitytntprimed.setFuse(level().random.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
						level().addFreshEntity(entitytntprimed);
						level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					}
				} else if (block == RRBlocks.remotecharge) {
					state.onExplosionHit(level(), pos, null, (stack, pos1) -> {});
				} else if (block == RRBlocks.timedbomb) {
					state.onExplosionHit(level(), pos, null, (stack, pos1) -> {});
				}
				kill();
			}
			else if (var3.getType() == HitResult.Type.ENTITY)
			{
                Entity hitEntity = ((EntityHitResult) var3).getEntity();
                if (hitEntity instanceof Player player && shooter != hitEntity) {
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
					if (!player.getItemBySlot(slot).isEmpty() && !level().isClientSide)
					{
						player.getItemBySlot(slot).hurtAndBreak(24, player, slot);
					}
					player.hurt(RivalRebelsDamageSource.laserBurst(level()), 16);
					if (player.getHealth() < 3 && player.isAlive())
					{
						player.hurt(RivalRebelsDamageSource.laserBurst(level()), 2000000);
						player.deathTime = 0;
						level().addFreshEntity(new EntityGore(level(), hitEntity, 0, 0));
						level().addFreshEntity(new EntityGore(level(), hitEntity, 1, 0));
						level().addFreshEntity(new EntityGore(level(), hitEntity, 2, 0));
						level().addFreshEntity(new EntityGore(level(), hitEntity, 2, 0));
						level().addFreshEntity(new EntityGore(level(), hitEntity, 3, 0));
						level().addFreshEntity(new EntityGore(level(), hitEntity, 3, 0));
					}
					kill();
				}
				else if ((hitEntity instanceof LivingEntity entity
						&& !(hitEntity instanceof Animal)
						&& !(hitEntity instanceof Bat)
						&& !(hitEntity instanceof Villager)
						&& !(hitEntity instanceof Squid))) {
					entity.hurt(RivalRebelsDamageSource.laserBurst(level()), 6);
					if (entity.getHealth() < 3)
					{
						int legs = -1;
						int arms = -1;
						int mobs = -1;
						entity.kill();
                        level().playLocalSound(this, RRSounds.BLASTER_FIRE, getSoundSource(), 1, 4);
						if (entity instanceof Zombie && !(entity instanceof ZombifiedPiglin))
						{
							legs = 2;
							arms = 2;
							mobs = 1;
						}
						else if (entity instanceof ZombifiedPiglin)
						{
							legs = 2;
							arms = 2;
							mobs = 2;
						}
						else if (entity instanceof Skeleton)
						{
							legs = 2;
							arms = 2;
							mobs = 3;
						}
						else if (entity instanceof EnderMan)
						{
							legs = 2;
							arms = 2;
							mobs = 4;
						}
						else if (entity instanceof Creeper)
						{
							legs = 4;
							arms = 0;
							mobs = 5;
						}
                        else if (entity instanceof MagmaCube)
                        {
                            legs = 0;
                            arms = 0;
                            mobs = 7;
                        }
						else if (entity instanceof Slime)
						{
							legs = 0;
							arms = 0;
							mobs = 6;
						}
						else if (entity instanceof Spider && !(entity instanceof CaveSpider))
						{
							legs = 8;
							arms = 0;
							mobs = 8;
						}
						else if (entity instanceof CaveSpider)
						{
							legs = 8;
							arms = 0;
							mobs = 9;
						}
						else if (entity instanceof Ghast)
						{
							legs = 9;
							arms = 0;
							mobs = 10;
						}
						else
						{
							legs = (int) (entity.getBoundingBox().getSize() * 2);
							arms = (int) (entity.getBoundingBox().getSize() * 2);
							mobs = 11;
						}
						level().addFreshEntity(new EntityGore(level(), hitEntity, 0, mobs));
						level().addFreshEntity(new EntityGore(level(), hitEntity, 1, mobs));
						for (int i = 0; i < arms; i++)
							level().addFreshEntity(new EntityGore(level(), hitEntity, 2, mobs));
						for (int i = 0; i < legs; i++)
							level().addFreshEntity(new EntityGore(level(), hitEntity, 3, mobs));
					}
					kill();
				}
				else if((hitEntity instanceof EntityRhodesHead
					  || hitEntity instanceof EntityRhodesLeftLowerArm
				      || hitEntity instanceof EntityRhodesLeftLowerLeg
				      || hitEntity instanceof EntityRhodesLeftUpperArm
				      || hitEntity instanceof EntityRhodesLeftUpperLeg
				      || hitEntity instanceof EntityRhodesRightLowerArm
				      || hitEntity instanceof EntityRhodesRightLowerLeg
				      || hitEntity instanceof EntityRhodesRightUpperArm
				      || hitEntity instanceof EntityRhodesRightUpperLeg
				      || hitEntity instanceof EntityRhodesTorso))
				{
					hitEntity.hurt(RivalRebelsDamageSource.laserBurst(level()), 6);
				}
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var16 = Mth.sqrt((float) (getDeltaMovement().x() * getDeltaMovement().x() + getDeltaMovement().z() * getDeltaMovement().z()));
		setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));

		for (setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * 180.0D / Math.PI)); getXRot() - xRotO < -180.0F; xRotO -= 360.0F)
		{
        }
		while (getXRot() - xRotO >= 180.0F)
		{
			xRotO += 360.0F;
		}

		while (getYRot() - yRotO < -180.0F)
		{
			yRotO -= 360.0F;
		}

		while (getYRot() - yRotO >= 180.0F)
		{
			yRotO += 360.0F;
		}

		setXRot(xRotO + (getXRot() - xRotO) * 0.2F);
		setYRot(yRotO + (getYRot() - yRotO) * 0.2F);
		setPos(getX(), getY(), getZ());
	}
}

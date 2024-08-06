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
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityLaserBurst extends Projectile {
	private Entity shooter;

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
		moveTo(player.getEyePosition(), player.getYRot(), player.getXRot());
        setPosRaw(getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.2F),
            getY() - 0.12D,
            getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.2F));
		setPos(getX(), getY(), getZ());
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

        setAccurateHeading(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), 4F, 0.075f);
	}

	public EntityLaserBurst(Level par1World, LivingEntity player, boolean accurate)
	{
		this(par1World);
		shooter = player;
		moveTo(player.getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.2F),
            player.getEyeY() - 0.12D,
            player.getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.2F),
            player.getYRot(),
            player.getXRot());

        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

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
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
	}

	public EntityLaserBurst(Level par1World, double x, double y, double z, double mx, double my, double mz, Entity player)
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
		setYRot(yRotO = (float) (Math.atan2(par1, par5) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(par3, var10) * Mth.RAD_TO_DEG));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick()
	{
		super.tick();

		++tickCount;
		if (tickCount > 60) kill();

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

		if (hitResult.getType() != HitResult.Type.MISS) {
            onHit(hitResult);
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var16 = (float) this.getDeltaMovement().horizontalDistance();
		setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * Mth.RAD_TO_DEG));

		for (setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * Mth.RAD_TO_DEG)); getXRot() - xRotO < -180.0F; xRotO -= 360.0F)
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

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        BlockState state = level().getBlockState(pos);
        if (state.is(Blocks.TNT)) {
            if (!level().isClientSide()) {
                PrimedTnt tnt = new PrimedTnt(level(), (pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), shooter instanceof LivingEntity ? (LivingEntity) shooter : null);
                tnt.setFuse(level().random.nextInt(tnt.getFuse() / 4) + tnt.getFuse() / 8);
                level().addFreshEntity(tnt);
                level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
        } else if (state.is(RRBlocks.remotecharge)) {
            state.onExplosionHit(level(), pos, null, (stack, pos1) -> {});
        } else if (state.is(RRBlocks.timedbomb)) {
            state.onExplosionHit(level(), pos, null, (stack, pos1) -> {});
        }
        kill();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity hitEntity = result.getEntity();
        if (hitEntity instanceof Player player && shooter != hitEntity) {
            EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
            if (!player.getItemBySlot(slot).isEmpty() && !level().isClientSide())
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

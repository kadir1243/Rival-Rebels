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
package io.github.kadir1243.rivalrebels.common.entity;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
    public EntityLaserBurst(EntityType<? extends EntityLaserBurst> type, Level world) {
        super(type, world);
    }

	public EntityLaserBurst(Level level) {
		this(RREntities.LASER_BURST.get(), level);
	}

	public EntityLaserBurst(Level level, Entity player) {
		this(level);
        this.setOwner(player);
        moveTo(player.getX() - (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.2F),
            player.getEyeY() - 0.12D,
            player.getZ() - (Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.2F),
            player.getYRot(),
            player.getXRot());

        shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 4F, 0.075f);
	}

	public EntityLaserBurst(Level level, Entity player, boolean accurate) {
		this(level);
        this.setOwner(player);
		moveTo(player.getX() - (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.2F),
            player.getEyeY() - 0.12D,
            player.getZ() - (Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.2F),
            player.getYRot(),
            player.getXRot());

        shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 4F * (float)random.nextDouble() + 1.0F, accurate?0.001F:0.075F);
	}

	public EntityLaserBurst(Level level, double mx, double my, double mz) {
		this(level);
        setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
        setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
    }

    public EntityLaserBurst(Level level, double x, double y, double z, double mx, double my, double mz, Entity player)
	{
		this(level);
        this.setOwner(player);
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
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

        setPos(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		this.updateRotation();
	}

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        BlockState state = level().getBlockState(pos);
        if (state.is(Blocks.TNT)) {
            if (!level().isClientSide()) {
                PrimedTnt tnt = new PrimedTnt(level(), (pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), getOwner() instanceof LivingEntity entity ? entity : null);
                tnt.setFuse(random.nextInt(tnt.getFuse() / 4) + tnt.getFuse() / 8);
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
        if (hitEntity instanceof Player player) {
            ItemUtil.damageRandomArmor(player, 24, random);
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
                int legs;
                int arms;
                int mobs;
                entity.kill();
                this.playSound(RRSounds.BLASTER_FIRE.get(), 1, 4);
                switch (entity) {
                    case ZombifiedPiglin ignored -> {
                        legs = 2;
                        arms = 2;
                        mobs = 2;
                    }
                    case Zombie ignored -> {
                        legs = 2;
                        arms = 2;
                        mobs = 1;
                    }
                    case Skeleton ignored -> {
                        legs = 2;
                        arms = 2;
                        mobs = 3;
                    }
                    case EnderMan ignored -> {
                        legs = 2;
                        arms = 2;
                        mobs = 4;
                    }
                    case Creeper ignored -> {
                        legs = 4;
                        arms = 0;
                        mobs = 5;
                    }
                    case MagmaCube ignored -> {
                        legs = 0;
                        arms = 0;
                        mobs = 7;
                    }
                    case Slime ignored -> {
                        legs = 0;
                        arms = 0;
                        mobs = 6;
                    }
                    case CaveSpider ignored -> {
                        legs = 8;
                        arms = 0;
                        mobs = 9;
                    }
                    case Spider ignored -> {
                        legs = 8;
                        arms = 0;
                        mobs = 8;
                    }
                    case Ghast ignored -> {
                        legs = 9;
                        arms = 0;
                        mobs = 10;
                    }
                    default -> {
                        legs = (int) (entity.getBoundingBox().getSize() * 2);
                        arms = (int) (entity.getBoundingBox().getSize() * 2);
                        mobs = 11;
                    }
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

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

import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityCuchillo extends ThrowableProjectile {
    private boolean	inGround;
	private int		ticksInGround;

    public EntityCuchillo(EntityType<? extends EntityCuchillo> type, Level level) {
        super(type, level);
    }

	public EntityCuchillo(Level level) {
		this(RREntities.CUCHILLO.get(), level);
	}

	public EntityCuchillo(Level level, Entity player, float speed) {
		this(level);
        this.setOwner(player);
		moveTo(player.getEyePosition(), player.getYRot(), player.getXRot());
        setPos(getX() - (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.16F),
            getY() - 0.1f,
            getZ() - (Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.16F));
		setDeltaMovement((-Mth.sin(getYRot() * Mth.DEG_TO_RAD) * Mth.cos(getXRot() * Mth.DEG_TO_RAD)) * speed,
            (-Mth.sin(getXRot() * Mth.DEG_TO_RAD)) * speed,
            (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * Mth.cos(getXRot() * Mth.DEG_TO_RAD)) * speed);
	}

    public EntityCuchillo(Level level, double mx, double my, double mz) {
		this(level);
        setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
        setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        result.getEntity().hurt(RivalRebelsDamageSource.cuchillo(level()), 7);
        kill();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        BlockState state = level().getBlockState(pos);
        if (state.is(ModBlockTags.GLASS_BLOCKS) || state.is(ModBlockTags.GLASS_PANES)) {
            level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            this.playSound(RRSounds.CUCHILLO_GLASS_BREAK.get(), 5F, 0.3F);
        } else if (state.is(BlockTags.BASE_STONE_OVERWORLD)) {
            level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.knife.toStack()));
            kill();
        } else {
            inGround = true;
        }
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;
        if (inGround) {
            setDeltaMovement(getDeltaMovement().scale(0.2));
            ticksInGround++;
            if (ticksInGround == 60) {
                level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.knife.toStack()));
                kill();
            }
        }
    }

    @Override
    protected void updateRotation() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setXRot(getXRot() - 30);
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI)));
    }

    @Override
	public void playerTouch(Player player) {
		if (!level().isClientSide && inGround) {
            this.hitTargetOrDeflectSelf(new EntityHitResult(player, position()));
			playSound(SoundEvents.LAVA_POP, 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			kill();
		}
	}

    @Override
    protected double getDefaultGravity() {
        return 0.026;
    }

    @Override
	public boolean isAttackable()
	{
		return false;
	}
}

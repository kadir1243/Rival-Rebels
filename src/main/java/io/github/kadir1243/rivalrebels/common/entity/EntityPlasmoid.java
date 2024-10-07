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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.core.BlackList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPlasmoid extends Projectile {
    public int				rotation	= 45;
    public float			slide		= 0;

    public EntityPlasmoid(EntityType<? extends EntityPlasmoid> type, Level world) {
        super(type, world);
    }

	public EntityPlasmoid(Level level) {
		this(RREntities.PLASMOID.get(), level);
        rotation = random.nextInt(360);
        slide = random.nextInt(21) - 10;
    }

	public EntityPlasmoid(Level level, double x, double y, double z) {
		this(level);
		setPos(x, y, z);
	}

    public EntityPlasmoid(Level level, Entity thrower, float speed, boolean drop)
	{
		this(level);
        setNoGravity(!drop);
        speed *= (isNoGravity() ? 1 : 3);
        this.setOwner(thrower);
		moveTo(
            thrower.getX() - (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.16F),
            thrower.getEyeY(),
            thrower.getZ() - (Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.16F),
            thrower.getYRot(),
            thrower.getXRot());
		shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0, speed * 1.5F, 1.0F);
	}

	public EntityPlasmoid(Level world, double px, double py, double pz, double x, double y, double z, float d) {
		this(world);
        Vec3 vector = new Vec3(x, y, z);
		setPos(vector.add(px, py, pz).normalize().scale(d));
        setDeltaMovement(x, y, z);
		double var10 = vector.horizontalDistance();
		setYRot(yRotO = (float) (Math.atan2(x, z) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(y, var10) * Mth.RAD_TO_DEG));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
		super.tick();
		applyGravity();
		++tickCount;
		rotation += (int) slide;
		slide *= 0.9F;
		if (tickCount >= RRConfig.SERVER.getPlasmoidDecay() * (isNoGravity() ? 1 : 3)) explode();

        HitResult mop = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

		if (mop.getType() != HitResult.Type.MISS) explode();

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
        this.updateRotation();
        reapplyPosition();
	}

    @Override
    protected double getDefaultGravity() {
        return 0.05;
    }

    protected void explode() {
		if (!level().isClientSide()) {
			kill();
			BlockState state = Blocks.STONE.defaultBlockState();
			int i = -1;
            while ((state.canOcclude() || BlackList.plasmaExplosion(state)) && i < 4) {
				++i;
				state = level().getBlockState(BlockPos.containing(position().subtract(getDeltaMovement().scale(i))));
			}
			level().setBlockAndUpdate(BlockPos.containing(position().subtract(getDeltaMovement().scale(i))), RRBlocks.plasmaexplosion.get().defaultBlockState());
		}
	}

}

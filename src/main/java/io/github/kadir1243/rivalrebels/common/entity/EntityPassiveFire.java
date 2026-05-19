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

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EntityPassiveFire extends Projectile {
    private int		ticksInAir;
	private double	damage;

    public EntityPassiveFire(EntityType<? extends EntityPassiveFire> type, Level world) {
        super(type, world);
    }

	public EntityPassiveFire(Level level) {
		this(RREntities.PASSIVE_FIRE.get(), level);
		ticksInAir = 0;
		damage = 2;
	}

	public EntityPassiveFire(Level level, double x, double y, double z) {
		this(level);
		setPos(x, y, z);
	}

    public EntityPassiveFire(Level level, Entity entity, float par3) {
		this(level);
        this.setOwner(entity);
		snapTo(entity.getEyePosition(), entity.getYRot(), entity.getXRot());
		setYRot((getYRot() + 25) % 360);
        setPos(
            getX() - Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.16F,
            getY() - 0.2D,
            getZ() - Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.16F
        );
        super.setDeltaMovement(-Mth.sin(getYRot() * Mth.DEG_TO_RAD) * Mth.cos(getXRot() * Mth.DEG_TO_RAD),
		 Mth.cos(getYRot() * Mth.DEG_TO_RAD) * Mth.cos(getXRot() * Mth.DEG_TO_RAD),
		 -Mth.sin(getXRot() * Mth.DEG_TO_RAD));
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return (distance <= 16);
	}

	public EntityPassiveFire(Level world, int x, int y, int z, int mx, int my, int mz)
	{
		this(world);
		setPos(x, y, z);
        super.setDeltaMovement(mx, my, mz);
	}

    @Override
	public void setDeltaMovement(double x, double y, double z) {
        super.setDeltaMovement(
            x + (random.nextFloat() - 0.5) / 50,
            y + (random.nextFloat() - 0.5) / 50,
            z + (random.nextFloat() - 0.5) / 50);
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
		super.tick();

		if (ticksInAir > 7) {
			this.kill((ServerLevel) level());
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var17 = 0.4F;

		if (this.isInWater()) {
			kill((ServerLevel) level());
		}

        setDeltaMovement(getDeltaMovement().scale(var17));
        applyGravity();
        this.reapplyPosition();
		ticksInAir++;
	}

    @Override
    protected double getDefaultGravity() {
        return -0.02F;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putDouble("damage", damage);
	}

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        damage = input.getDoubleOr("damage", 0);
	}

	@Override
	public boolean isAttackable()
	{
		return false;
	}
}

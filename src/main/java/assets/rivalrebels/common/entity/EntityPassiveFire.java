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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class EntityPassiveFire extends Projectile {
    private boolean	inGround;

    private int		ticksInAir;
	private double	damage;

    public EntityPassiveFire(EntityType<? extends EntityPassiveFire> type, Level world) {
        super(type, world);
    }

	public EntityPassiveFire(Level level) {
		this(RREntities.PASSIVE_FIRE, level);
        inGround = false;
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
		moveTo(entity.getEyePosition(), entity.getYRot(), entity.getXRot());
		setYRot((getYRot() + 25) % 360);
        setPos(
            getX() - Mth.cos((getYRot() / 180F) * Mth.PI) * 0.16F,
            getY() - 0.2D,
            getZ() - Mth.sin((getYRot() / 180F) * Mth.PI) * 0.16F
        );
        super.setDeltaMovement(-Mth.sin((getYRot() / 180F) * Mth.PI) * Mth.cos((getXRot() / 180F) * Mth.PI),
		 Mth.cos((getYRot() / 180F) * Mth.PI) * Mth.cos((getXRot() / 180F) * Mth.PI),
		 -Mth.sin((getXRot() / 180F) * Mth.PI));
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
            x + (level().random.nextFloat() - 0.5) / 50,
            y + (level().random.nextFloat() - 0.5) / 50,
            z + (level().random.nextFloat() - 0.5) / 50);
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
		super.tick();

		if (ticksInAir > 7) {
			this.kill();
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var17 = 0.4F;

		if (this.isInWaterOrBubble()) {
			kill();
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
	public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
		nbt.putBoolean("inGround", inGround);
		nbt.putDouble("damage", damage);
	}

    @Override
	public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        inGround = nbt.getBoolean("inGround");
        damage = nbt.getDouble("damage");
	}

	@Override
	public boolean isAttackable()
	{
		return false;
	}
}

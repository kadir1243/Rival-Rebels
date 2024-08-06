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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class EntityPassiveFire extends EntityInanimate {
    private boolean	inGround;

	public Entity	shootingEntity;
	private int		ticksInAir;
	private double	damage;

    public EntityPassiveFire(EntityType<? extends EntityPassiveFire> type, Level world) {
        super(type, world);
    }

	public EntityPassiveFire(Level par1World) {
		this(RREntities.PASSIVE_FIRE, par1World);
        inGround = false;
		ticksInAir = 0;
		damage = 2D;
	}

	public EntityPassiveFire(Level par1World, double par2, double par4, double par6) {
		this(par1World);
		setPos(par2, par4, par6);
	}

	public EntityPassiveFire(Level par1World, Mob shootingEntity, Mob par3EntityLiving, float par4, float par5) {
		this(par1World);
		this.shootingEntity = shootingEntity;
        setPosRaw(getX(), shootingEntity.getEyeY() - 0.1D, getZ());
		double d = par3EntityLiving.getX() - shootingEntity.getX();
		double d1 = par3EntityLiving.getEyeY() - 0.7D - getY();
		double d2 = par3EntityLiving.getZ() - shootingEntity.getZ();
		double d3 = Math.sqrt(d * d + d2 * d2);

		if (d3 < 9.9999999999999995E-008D) {
        } else {
			float f = (float) ((Math.atan2(d2, d) * 180D) / Mth.PI) - 90F;
			float f1 = (float) (-((Math.atan2(d1, d3) * 180D) / Mth.PI));
			double d4 = d / d3;
			double d5 = d2 / d3;
			moveTo(shootingEntity.getX() + d4, getY(), shootingEntity.getZ() + d5, f, f1);
			float f2 = (float) d3 * 0.2F;
			setDeltaMovement(d, d1 + f2, d2);
        }
	}

	public EntityPassiveFire(Level par1World, Entity entity, float par3) {
		this(par1World);
		shootingEntity = entity;
		moveTo(entity.getEyePosition(), entity.getYRot(), entity.getXRot());
		setYRot((getYRot() + 25) % 360);
        setPosRaw(
            getX() - Mth.cos((getYRot() / 180F) * Mth.PI) * 0.16F,
            getY() - 0.2D,
            getZ() - Mth.sin((getYRot() / 180F) * Mth.PI) * 0.16F
        );
		setPos(getX(), getY(), getZ());
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
	public void tick() {
		super.tick();

		if (ticksInAir > 7)
		{
			this.kill();
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var17 = 0.4F;
		float var18 = -0.02F;

		if (this.isInWaterOrBubble())
		{
			kill();
		}

        setDeltaMovement(getDeltaMovement().scale(var17));
        setDeltaMovement(getDeltaMovement().subtract(0, var18, 0));
		this.setPos(this.getX(), this.getY(), this.getZ());
		ticksInAir++;
	}

    @Override
	public void addAdditionalSaveData(CompoundTag nbt)
	{
		nbt.putBoolean("inGround", inGround);
		nbt.putDouble("damage", damage);
	}

    @Override
	public void readAdditionalSaveData(CompoundTag nbt)
	{
		inGround = nbt.getBoolean("inGround");
        damage = nbt.getDouble("damage");
	}

	@Override
	public boolean isAttackable()
	{
		return false;
	}
}

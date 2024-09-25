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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EntityRhodesPiece extends Entity implements VariantHolder<Holder<RhodesType>> {
    public static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(EntityRhodesPiece.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Holder<RhodesType>> TYPE = SynchedEntityData.defineId(EntityRhodesPiece.class, RhodesTypes.HOLDER_DATA_SERIALIZER);
    protected double health;
	private float myaw;
	private float mpitch;

	public EntityRhodesPiece(EntityType<? extends EntityRhodesPiece> type, Level w) {
		super(type, w);
		setBoundingBox(new AABB(-1.5, -1.5, -1.5, 1.5, 1.5, 1.5));
	}

	public EntityRhodesPiece(EntityType<? extends EntityRhodesPiece> type, Level w, double x, double y, double z, float scale, Holder<RhodesType> rhodesTypeHolder) {
		this(type, w);
		setPos(x, y, z);
		setScale(scale);
		setVariant(rhodesTypeHolder);
		myaw = (float) (random.nextGaussian()*20);
		mpitch = (float) (random.nextGaussian()*20);
		setDeltaMovement((float) (random.nextGaussian()*0.75),
            Mth.abs((float) (random.nextGaussian()*0.75F)),
        (float) (random.nextGaussian()*0.75));
	}

    public float getScale() {
        return entityData.get(SCALE);
    }

    public void setScale(float scale) {
        entityData.set(SCALE, scale);
    }

    public Holder<RhodesType> getVariant() {
        return entityData.get(TYPE);
    }

    @Override
    public void setVariant(Holder<RhodesType> variant) {
        entityData.set(TYPE, variant);
    }

    public int getColorRGBA() {
        return getVariant().value().getColor();
    }

    @Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();
		if (random.nextInt(Math.max(getMaxAge()*(RRConfig.SERVER.isRhodesPromode() ?1:30) - tickCount, RRConfig.SERVER.isRhodesPromode()?100:1))==0) {
			kill();
		}
        setDeltaMovement(getDeltaMovement().scale(0.999));
		myaw *= 0.98f;
		mpitch *= 0.98f;
        setYRot(getYRot() + myaw);
        setXRot(getXRot() + mpitch);
		if (verticalCollision)
		{
			setXRot(Math.round(getXRot() / 90f) * 90);
            setDeltaMovement(getDeltaMovement().multiply(0.7, 1, 0.7));
		}
		else
		{
            setDeltaMovement(getDeltaMovement().subtract(0, 0.1, 0));
		}
		move(MoverType.SELF, getDeltaMovement());
	}

	public int getMaxAge()
	{
		return 100;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount)
	{
		super.hurt(damageSource, amount);
		if (isAlive() && !level().isClientSide())
		{
			health -= amount;
			if (health <= 0)
			{
				kill();
				new Explosion(level(), getX(), getY(), getZ(), 6, true, true, RivalRebelsDamageSource.rocket(level()));
                level().playLocalSound(this, RRSounds.ARTILLERY_EXPLODE, getSoundSource(), 30, 1);
			}
		}

		return true;
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(SCALE, 1F);
        builder.define(TYPE, RivalRebels.RHODES_TYPE_REGISTRY.wrapAsHolder(RhodesTypes.Rhodes));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
		health = nbt.getDouble("health");
	}

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putDouble("health", health);
	}
}

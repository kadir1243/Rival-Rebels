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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class EntityRhodesPiece extends Entity {
    public static final TrackedData<Float> SCALE = DataTracker.registerData(EntityRhodesPiece.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Integer> COLOR = DataTracker.registerData(EntityRhodesPiece.class, TrackedDataHandlerRegistry.INTEGER);
    protected double health;
	private float myaw;
	private float mpitch;
    public int color = 0;

	public EntityRhodesPiece(EntityType<? extends EntityRhodesPiece> type, World w) {
		super(type, w);
		setBoundingBox(new Box(-1.5, -1.5, -1.5, 1.5, 1.5, 1.5));
	}

	public EntityRhodesPiece(EntityType<? extends EntityRhodesPiece> type, World w, double x, double y, double z, float scale, int color)
	{
		this(type, w);
		setPosition(x, y, z);
		setScale(scale);
		setColor(color);
		myaw = (float) (random.nextGaussian()*20);
		mpitch = (float) (random.nextGaussian()*20);
		setVelocity((float) (random.nextGaussian()*0.75),
        (float) Math.abs(random.nextGaussian()*0.75),
        (float) (random.nextGaussian()*0.75));
	}

    public float getScale() {
        return dataTracker.get(SCALE);
    }

    public void setScale(float scale) {
        dataTracker.set(SCALE, scale);
    }

    public int getColor() {
        return dataTracker.get(COLOR);
    }

    public void setColor(int color) {
        dataTracker.set(COLOR, color);
    }

    @Override
	public boolean isCollidable()
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
		age++;
		if (getWorld().random.nextInt(Math.max(getMaxAge()*(RivalRebels.rhodesPromode?1:30) - age, RivalRebels.rhodesPromode?100:1))==0)
		{
			kill();
		}
        setVelocity(getVelocity().multiply(0.999));
		myaw *= 0.98f;
		mpitch *= 0.98f;
        setYaw(getYaw() + myaw);
        setPitch(getPitch() + mpitch);
		if (verticalCollision)
		{
			setPitch(Math.round(getPitch() / 90f) * 90);
            setVelocity(getVelocity().multiply(0.7, 1, 0.7));
		}
		else
		{
            setVelocity(getVelocity().subtract(0, 0.1, 0));
		}
		move(MovementType.SELF, getVelocity());
	}

	public int getMaxAge()
	{
		return 100;
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

	@Override
	public boolean damage(DamageSource par1DamageSource, float par2)
	{
		super.damage(par1DamageSource, par2);
		if (isAlive() && !getWorld().isClient)
		{
			health -= par2;
			if (health <= 0)
			{
				kill();
				new Explosion(getWorld(), getX(), getY(), getZ(), 6, true, true, RivalRebelsDamageSource.rocket(getWorld()));
                getWorld().playSoundFromEntity(this, RRSounds.ARTILLERY_EXPLODE, getSoundCategory(), 30, 1);
			}
		}

		return true;
	}

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(SCALE, 1F);
        dataTracker.startTracking(COLOR, 0);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
		health = nbt.getDouble("health");
	}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putDouble("health", health);
	}
}

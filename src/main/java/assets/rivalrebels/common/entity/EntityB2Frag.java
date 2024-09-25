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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.explosion.Explosion;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityB2Frag extends EntityInanimate
{
    private int			ticksInGround;
    private boolean		isSliding	= false;
	public int			type		= 0;
	float				motionyaw	= 0;
	float				motionpitch	= 0;
	float				offset		= 0;
    public int			health;

    public EntityB2Frag(EntityType<? extends EntityB2Frag> type, Level level) {
        super(type, level);
    }

	public EntityB2Frag(Level level) {
		this(RREntities.B2FRAG, level);
		health = 300;
		setBoundingBox(new AABB(-2.5, -2.5, -2.5, 2.5, 2.5, 2.5));
		noCulling = true;
	}

	public EntityB2Frag(Level level, Entity toBeGibbed, int Type)
	{
		this(level);
		health = 300;
		setBoundingBox(new AABB(-2.5, -2.5, -2.5, 2.5, 2.5, 2.5));
		noCulling = true;

		isSliding = false;
		type = Type;

		motionyaw = (float) ((random.nextDouble() - 0.5) * 35);
		motionpitch = (float) ((random.nextDouble() - 0.5) * 25);

		moveTo(toBeGibbed.getX(), toBeGibbed.getY(), toBeGibbed.getZ(), toBeGibbed.getYRot(), toBeGibbed.getXRot());

		double ox = getX();
		double oz = getZ();

		if (Type == 1)
		{
            setPos(getX() - (Mth.cos(-getYRot() * Mth.DEG_TO_RAD) * 7.5F),
                getY(),
                getZ() - (Mth.sin((-getYRot()) * Mth.DEG_TO_RAD) * 7.5F));
		}
		else if (Type == 0)
		{
            setPos(getX() - (Mth.cos((-getYRot() + 180) * Mth.DEG_TO_RAD) * 7.5F),
                getY(),
                getZ() - (Mth.sin((-getYRot() + 180) * Mth.DEG_TO_RAD) * 7.5F));
		}

        setDeltaMovement(toBeGibbed.getDeltaMovement());

        setDeltaMovement(getDeltaMovement().add((-ox + getX()) * 0.1, 0, (-oz + getZ()) * 0.1));

		igniteForSeconds(10);
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public void tick() {
		super.tick();

		if (onGround()) {
			++ticksInGround;

			if (ticksInGround == 1200)
			{
				kill();
			}

            setOnGround(false);
			setDeltaMovement(getDeltaMovement().multiply((random.nextFloat() * 0.2F),
                (random.nextFloat() * 0.2F),
                (random.nextFloat() * 0.2F)));
			ticksInGround = 0;
        }

		Vec3 vec3 = position();
		Vec3 vec31 = position().add(getDeltaMovement());
		BlockHitResult hitResult = level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

		if (hitResult != null)
		{
			isSliding = true;
            setPosRaw(getX(), hitResult.getLocation().y() + offset, getZ());
		}

		if (!level().isClientSide())
		{
			List<Entity> var5 = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRocket) {
                    ((EntityRocket) var9).explode();
                }

                if (var9 instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) var9).explode();
                }

                if (var9 instanceof EntityLaserBurst) {
                    var9.kill();
                    hurt(damageSources().generic(), 6);
                }
            }
		}

        setXRot(getXRot() + motionpitch);
		setYRot(getYRot() + motionyaw);
        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

		setXRot(xRotO + (getXRot() - xRotO) * 0.5F);
		setYRot(yRotO + (getYRot() - yRotO) * 0.5F);

		float f2 = 0.99F;

		if (isSliding) {
			motionpitch = 0;
			motionyaw = 0;
            setDeltaMovement(getDeltaMovement().x(), 0, getDeltaMovement().z());
			f2 = 0.7f;
		}

		motionpitch *= (double) f2;
		motionyaw *= (double) f2;
        setDeltaMovement(getDeltaMovement().scale(f2));
        applyGravity();

        reapplyPosition();
	}

    @Override
    protected double getDefaultGravity() {
        return isSliding ? super.getDefaultGravity() : 0.05F;
    }

    @Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putInt("Type", type);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		type = nbt.getInt("Type");
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (isAlive()) {
			health -= amount;

			if (health <= 0) {
				kill();
				new Explosion(level(), getX(), getY(), getZ(), 6, true, true, RivalRebelsDamageSource.rocket(level()));
                level().playLocalSound(this, RRSounds.ARTILLERY_EXPLODE, getSoundSource(), 30, 1);
			}
		}

		return true;
	}

}

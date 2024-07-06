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
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.weapon.ItemRoda;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityB2Spirit extends EntityInanimate
{
	private int					ticksSinceStart	= 0;
	private int					timeLeft	= -1;
	private double				tx				= 0;
	private double				ty				= 0;
	private double				tz				= 0;
	public int					health;
	public boolean 				carpet	= false;
	public EntityRhodes			rhodeswing = null;
	public static int			staticEntityIndex		= 10;
	public int					entityIndex				= 10;
	public boolean dropAnything = true;
    public boolean dropOnlyOne = false;
	public static boolean trash = true;
	public static boolean leave = true;

	public int mode = 0; //0=straight 1=left 2=right

	public EntityB2Spirit(EntityType<? extends EntityB2Spirit> entityType, Level par1World) {
		super(entityType, par1World);
		noCulling = true;
		setBoundingBox(new AABB(-10, -3, -10, 10, 4, 10));
		health = RRConfig.SERVER.getB2spirithealth();
	}

    public EntityB2Spirit(Level par1World) {
        this(RREntities.B2SPIRIT, par1World);
    }

	public EntityB2Spirit(Level par1World, double x, double y, double z, double x1, double y1, double z1, boolean c, boolean da)
	{
		this(par1World);
		carpet = c;
		tx = x;
		ty = y;
		tz = z;
		dropOnlyOne = !da;
		if (carpet)
		{
			entityIndex = 10;
			if (staticEntityIndex <= 23)
			{
				entityIndex = staticEntityIndex;
			}
		}
		else
		{
			entityIndex = 24;
			if (staticEntityIndex > 23)
			{
				entityIndex = staticEntityIndex;
			}
		}
		if (!level().isClientSide()) startBombRun(tz-z1, x1-tx); //perpendicular to view
	}

	public EntityB2Spirit(EntityRhodes r)
	{
		this(r.level());
		rhodeswing = r;
		setPosRaw(
            r.getX() - r.getDeltaMovement().x() * 500,
            120,
            r.getZ() - r.getDeltaMovement().z() * 500
        );
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

    @Override
	public void tick()
	{
		super.tick();

		if (random.nextDouble() > 0.8f) RivalRebelsSoundPlayer.playSound(this, 8, 0, 4.5f, 1.3f);

		if (rhodeswing != null) {
            setDeltaMovement(rhodeswing.position().subtract(position()));
			double t = Math.sqrt(getDeltaMovement().lengthSqr());
            setDeltaMovement(getDeltaMovement().scale(1/t));
            setYRot(rhodeswing.getYRot());
			setXRot((float) (Math.min(t, 90.0)));
			if (t < 25.0 || tickCount > 100)
			{
				rhodeswing.b2energy = 8000;
				rhodeswing.freeze = false;
				kill();
			}
		}

		if (!this.level().isClientSide())
		{
			double distfromtarget = Math.sqrt((tx-getX())*(tx-getX())+(tz-getZ())*(tz-getZ()));
			ticksSinceStart++;

			if (ticksSinceStart >= 60 && mode == 0)
			{
				if (carpet || (dropOnlyOne ? ticksSinceStart == 80 : ticksSinceStart % 40 == 0))
					dropNuke();

				if (distfromtarget > 80.0f)
				{
					mode = level().random.nextBoolean() ? 1 : 2;
					if (trash)
					{
						carpet = true;
						entityIndex = level().random.nextInt(ItemRoda.rodaindex);
					}
					if (leave)
					{
						if (ticksSinceStart > 1000 && level().random.nextInt(4) == 1)
						{
                            setDeltaMovement(getDeltaMovement().x(), 2F, getDeltaMovement().z());
						}
						if (!trash && dropOnlyOne)
						{
                            setDeltaMovement(getDeltaMovement().x(), 2F, getDeltaMovement().z());
						}
					}
				}
			}
			if (mode > 0)
			{
				if (mode == 1)
                    setYRot(getYRot() + 10.0f);
				else if (mode == 2)
                    setYRot(getYRot() - 10.0f);
				setDeltaMovement(Mth.sin(getYRot() / 180.0F * (float) Math.PI),
                    getDeltaMovement().y(),
                    Mth.cos(getYRot() / 180.0F * (float) Math.PI)
                );
				if (distfromtarget < 80.0f)
					mode = 0;
			}

			List<Entity> var5 = this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D, 1.0D, 1.0D));

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRocket) {
                    ((EntityRocket) var9).explode(null);
                }

                if (var9 instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) var9).explode();
                }

                if (var9 instanceof EntityLaserBurst) {
                    var9.kill();
                    this.hurt(damageSources().generic(), 6);
                }
            }

			timeLeft--;
			if (timeLeft == 0)
			{
                setDeltaMovement(getDeltaMovement().x(), 2F, getDeltaMovement().z());
			}
			if (getY() > 256.0f)
			{
				kill();
			}
		}

        Vec3 vec3d = position().add(getDeltaMovement());
        setPosRaw(vec3d.x(), vec3d.y(), vec3d.z());
		if (rhodeswing == null)
		{
			double var16 = Math.sqrt(this.getDeltaMovement().x() * this.getDeltaMovement().x() + this.getDeltaMovement().z() * this.getDeltaMovement().z());
			this.setYRot((float) (Math.atan2(this.getDeltaMovement().x(), this.getDeltaMovement().z()) * 180.0D / Math.PI));

			for (this.setXRot((float) (Math.atan2(-this.getDeltaMovement().y(), var16) * 180.0D / Math.PI)); this.getXRot() - this.xRotO < -180.0F; this.xRotO -= 360.0F)
			{
            }

			while (this.getXRot() - this.xRotO >= 180.0F)
			{
				this.xRotO += 360.0F;
			}

			while (this.getYRot() - this.yRotO < -180.0F)
			{
				this.yRotO -= 360.0F;
			}

			while (this.getYRot() - this.yRotO >= 180.0F)
			{
				this.yRotO += 360.0F;
			}

			this.setXRot(this.xRotO + (this.getXRot() - this.xRotO) * 0.2F);
			this.setYRot(this.yRotO + (this.getYRot() - this.yRotO) * 0.2F);
		}
		this.setPos(this.getX(), this.getY(), this.getZ());
	}

	public void dropNuke()
	{
		if (dropAnything) ItemRoda.spawn(entityIndex, level(), getX()+random.nextDouble()*4-2, getY() - 3.5f, getZ()+random.nextDouble()*4-2, getDeltaMovement().x() * 0.1f, -1.0f, getDeltaMovement().z() * 0.1f, 1.0f, 0.0f);
	}
	Entity rhodes = null;
	public void startBombRun(double x, double z)
	{
		if (rhodes != null)
		{
			tx = rhodes.getX();
			ty = rhodes.getY();
			tz = rhodes.getZ();
			x = -rhodes.getDeltaMovement().x();
			z = -rhodes.getDeltaMovement().z();
		}
		double dist = 1.0/Math.sqrt(x*x + z*z);
		x *= dist;
		z *= dist;
        setDeltaMovement(-x, getDeltaMovement().y(), -z);
		setPos(tx + x*80, ty+60, tz + z*80);
        setYRot(yRotO = (float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt)
	{
		nbt.putInt("drop", entityIndex);
		nbt.putFloat("tx", (float) tx);
		nbt.putFloat("ty", (float) ty);
		nbt.putFloat("tz", (float) tz);
		nbt.putInt("age", ticksSinceStart);
		nbt.putInt("health", health);
		nbt.putInt("duration", timeLeft);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt)
	{
		entityIndex = nbt.getInt("drop");
		carpet = entityIndex < ItemRoda.rodaindex;
		tx = nbt.getFloat("tx");
		ty = nbt.getFloat("ty");
		tz = nbt.getFloat("tz");
		ticksSinceStart = nbt.getInt("age");
		health = nbt.getInt("health");
		timeLeft = nbt.getInt("duration");
		if (ticksSinceStart == 0)
		{
			double dx = tx - getX();
			double dy = ty - getY();
			startBombRun(dx, dy);
		}
	}

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
	public boolean hurt(DamageSource par1DamageSource, float par2)
	{
		super.hurt(par1DamageSource, par2);
		if (this.isAlive() && !this.level().isClientSide()) {
			this.health -= par2;
			if (this.health <= 0) {
				this.kill();
				this.level().explode(null, this.getX(), this.getY(), this.getZ(), 6.0F, Level.ExplosionInteraction.MOB);
				level().addFreshEntity(new EntityB2Frag(level(), this, 0));
				level().addFreshEntity(new EntityB2Frag(level(), this, 1));
				Zombie pz = new Zombie(level());
				pz.setPos(getX(), getY(), getZ());
				level().addFreshEntity(pz);
                level().playLocalSound(this, RRSounds.ARTILLERY_EXPLODE, getSoundSource(), 30, 1);
			}
		}

		return true;
	}
}

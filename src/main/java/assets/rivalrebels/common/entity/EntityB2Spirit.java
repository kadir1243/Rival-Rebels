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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.weapon.ItemRoda;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

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

	public EntityB2Spirit(EntityType<? extends EntityB2Spirit> entityType, World par1World) {
		super(entityType, par1World);
		ignoreCameraFrustum = true;
		setBoundingBox(new Box(-10, -3, -10, 10, 4, 10));
		health = RivalRebels.b2spirithealth;
	}

    public EntityB2Spirit(World par1World) {
        this(RREntities.B2SPIRIT, par1World);
    }

	public EntityB2Spirit(World par1World, double x, double y, double z, double x1, double y1, double z1, boolean c, boolean da)
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
		if (!getWorld().isClient) startBombRun(tz-z1, x1-tx); //perpendicular to view
	}

	public EntityB2Spirit(EntityRhodes r)
	{
		this(r.getWorld());
		rhodeswing = r;
		setPos(
            r.getX() - r.getVelocity().getX() * 500,
            120,
            r.getZ() - r.getVelocity().getZ() * 500
        );
	}

	@Override
	public boolean isCollidable()
	{
		return true;
	}

    @Override
	public void tick()
	{
		super.tick();

		if (random.nextDouble() > 0.8f) RivalRebelsSoundPlayer.playSound(this, 8, 0, 4.5f, 1.3f);

		if (rhodeswing != null) {
            setVelocity(rhodeswing.getPos().subtract(getPos()));
			double t = Math.sqrt(getVelocity().lengthSquared());
            setVelocity(getVelocity().multiply(1/t));
            setYaw(rhodeswing.getYaw());
			setPitch((float) (Math.min(t, 90.0)));
			if (t < 25.0 || age > 100)
			{
				rhodeswing.b2energy = 8000;
				rhodeswing.freeze = false;
				kill();
			}
		}

		if (!this.getWorld().isClient)
		{
			double distfromtarget = Math.sqrt((tx-getX())*(tx-getX())+(tz-getZ())*(tz-getZ()));
			ticksSinceStart++;

			if (ticksSinceStart >= 60 && mode == 0)
			{
				if (carpet || (dropOnlyOne ? ticksSinceStart == 80 : ticksSinceStart % 40 == 0))
					dropNuke();

				if (distfromtarget > 80.0f)
				{
					mode = getWorld().random.nextBoolean() ? 1 : 2;
					if (trash)
					{
						carpet = true;
						entityIndex = getWorld().random.nextInt(ItemRoda.rodaindex);
					}
					if (leave)
					{
						if (ticksSinceStart > 1000 && getWorld().random.nextInt(4) == 1)
						{
                            setVelocity(getVelocity().getX(), 2F, getVelocity().getZ());
						}
						if (!trash && dropOnlyOne)
						{
                            setVelocity(getVelocity().getX(), 2F, getVelocity().getZ());
						}
					}
				}
			}
			if (mode > 0)
			{
				if (mode == 1)
                    setYaw(getYaw() + 10.0f);
				else if (mode == 2)
                    setYaw(getYaw() - 10.0f);
				setVelocity(MathHelper.sin(getYaw() / 180.0F * (float) Math.PI),
                    getVelocity().getY(),
                    MathHelper.cos(getYaw() / 180.0F * (float) Math.PI)
                );
				if (distfromtarget < 80.0f)
					mode = 0;
			}

			List<Entity> var5 = this.getWorld().getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D, 1.0D, 1.0D));

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRocket) {
                    ((EntityRocket) var9).explode(null);
                }

                if (var9 instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) var9).explode();
                }

                if (var9 instanceof EntityLaserBurst) {
                    var9.kill();
                    this.damage(getDamageSources().generic(), 6);
                }
            }

			timeLeft--;
			if (timeLeft == 0)
			{
                setVelocity(getVelocity().getX(), 2F, getVelocity().getZ());
			}
			if (getY() > 256.0f)
			{
				kill();
			}
		}

        Vec3d vec3d = getPos().add(getVelocity());
        setPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
		if (rhodeswing == null)
		{
			double var16 = Math.sqrt(this.getVelocity().getX() * this.getVelocity().getX() + this.getVelocity().getZ() * this.getVelocity().getZ());
			this.setYaw((float) (Math.atan2(this.getVelocity().getX(), this.getVelocity().getZ()) * 180.0D / Math.PI));

			for (this.setPitch((float) (Math.atan2(-this.getVelocity().getY(), var16) * 180.0D / Math.PI)); this.getPitch() - this.prevPitch < -180.0F; this.prevPitch -= 360.0F)
			{
            }

			while (this.getPitch() - this.prevPitch >= 180.0F)
			{
				this.prevPitch += 360.0F;
			}

			while (this.getYaw() - this.prevYaw < -180.0F)
			{
				this.prevYaw -= 360.0F;
			}

			while (this.getYaw() - this.prevYaw >= 180.0F)
			{
				this.prevYaw += 360.0F;
			}

			this.setPitch(this.prevPitch + (this.getPitch() - this.prevPitch) * 0.2F);
			this.setYaw(this.prevYaw + (this.getYaw() - this.prevYaw) * 0.2F);
		}
		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

	public void dropNuke()
	{
		if (dropAnything) ItemRoda.spawn(entityIndex, getWorld(), getX()+random.nextDouble()*4-2, getY() - 3.5f, getZ()+random.nextDouble()*4-2, getVelocity().getX() * 0.1f, -1.0f, getVelocity().getZ() * 0.1f, 1.0f, 0.0f);
	}
	Entity rhodes = null;
	public void startBombRun(double x, double z)
	{
		if (rhodes != null)
		{
			tx = rhodes.getX();
			ty = rhodes.getY();
			tz = rhodes.getZ();
			x = -rhodes.getVelocity().getX();
			z = -rhodes.getVelocity().getZ();
		}
		double dist = 1.0/Math.sqrt(x*x + z*z);
		x *= dist;
		z *= dist;
        setVelocity(-x, getVelocity().getY(), -z);
		setPosition(tx + x*80, ty+60, tz + z*80);
        setYaw(prevYaw = (float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt)
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
	public void readCustomDataFromNbt(NbtCompound nbt)
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
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
	public boolean damage(DamageSource par1DamageSource, float par2)
	{
		super.damage(par1DamageSource, par2);
		if (this.isAlive() && !this.getWorld().isClient) {
			this.health -= par2;
			if (this.health <= 0) {
				this.kill();
				this.getWorld().createExplosion(null, this.getX(), this.getY(), this.getZ(), 6.0F, World.ExplosionSourceType.MOB);
				getWorld().spawnEntity(new EntityB2Frag(getWorld(), this, 0));
				getWorld().spawnEntity(new EntityB2Frag(getWorld(), this, 1));
				ZombieEntity pz = new ZombieEntity(getWorld());
				pz.setPosition(getX(), getY(), getZ());
				getWorld().spawnEntity(pz);
                getWorld().playSoundFromEntity(this, RRSounds.ARTILLERY_EXPLODE, getSoundCategory(), 30, 1);
			}
		}

		return true;
	}
}

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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.weapon.ItemRoda;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityB2Spirit extends Entity
{
	private int					ticksSinceStart	= 0;
	private int					timeLeft	= -1;
    private BlockPos tpos = BlockPos.ORIGIN;
	public int					health;
	public boolean 				carpet	= false;
	public EntityRhodes			rhodeswing = null;
	public static int			staticEntityIndex		= 10;
	public int					entityIndex				= 10;
	public boolean dropAnything = true;
	public static boolean randchance = true;
	public boolean dropOnlyOne = false;
	public static boolean trash = true;
	public static boolean leave = true;

	public int mode = 0; //0=straight 1=left 2=right

	public EntityB2Spirit(World par1World)
	{
		super(par1World);
		setSize(30F, 4F);
		ignoreFrustumCheck = true;
        setEntityBoundingBox(new AxisAlignedBB(-10, -3, -10, 10, 4, 10));
		health = RivalRebels.b2spirithealth;
		yOffset = 0.0F;
	}

    public EntityB2Spirit(World world, BlockPos tpos, double x1, double y1, double z1, boolean carpet, boolean dropOnlyOne) {
        this(world, tpos.getX(), tpos.getY(), tpos.getZ(), x1, y1, z1, carpet, dropOnlyOne);
    }

	public EntityB2Spirit(World par1World, double x, double y, double z, double x1, double y1, double z1, boolean carpet, boolean dropOnlyOne) {
		this(par1World);
		this.carpet = carpet;
        tpos = new BlockPos(x, y, z);
		this.dropOnlyOne = !dropOnlyOne;
		if (this.carpet)
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
		if (!worldObj.isRemote) startBombRun(tpos.getZ() - z1, x1 - tpos.getX()); //perpendicular to view
	}

	public EntityB2Spirit(EntityRhodes r)
	{
		this(r.worldObj);
		rhodeswing = r;
		posX = r.posX - r.motionX * 500;
		posY = 120;
		posZ = r.posZ - r.motionZ * 500;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.getEntityBoundingBox();
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

    /**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;

		if (Math.random() > 0.8f) RivalRebelsSoundPlayer.playSound(this, 8, 0, 4.5f, 1.3f);

		if (rhodeswing != null)
		{
			motionX = rhodeswing.posX - posX;
			motionY = rhodeswing.posY - posY;
			motionZ = rhodeswing.posZ - posZ;
			double t = Math.sqrt(motionX*motionX+motionY*motionY+motionZ*motionZ);
			motionX /= t;
			motionY /= t;
			motionZ /= t;
			rotationYaw = rhodeswing.rotationYaw;
			rotationPitch = (float) (Math.min(t,90.0));
			if (t < 25.0 || ticksExisted > 100)
			{
				rhodeswing.b2energy = 8000;
				rhodeswing.freeze = false;
				setDead();
			}
		}

		if (!this.worldObj.isRemote)
		{
			double distfromtarget = Math.sqrt((tpos.getX()-posX)*(tpos.getX()-posX)+(tpos.getZ()-posZ)*(tpos.getZ()-posZ));
			ticksSinceStart++;

			if (ticksSinceStart >= 60 && mode == 0)
			{
				if (carpet || (dropOnlyOne ? ticksSinceStart == 80 : ticksSinceStart % 40 == 0))
					dropNuke();

				if (distfromtarget > 80.0f)
				{
					mode = worldObj.rand.nextBoolean() ? 1 : 2;
					if (trash)
					{
						carpet = true;
						entityIndex = worldObj.rand.nextInt(ItemRoda.rodaindex);
					}
					if (leave)
					{
						if (ticksSinceStart > 1000 && worldObj.rand.nextInt(4) == 1)
						{
							motionY = 2.0f;
						}
						if (!trash && dropOnlyOne)
						{
							motionY = 2.0f;
						}
					}
				}
			}
			if (mode > 0)
			{
				if (mode == 1)
					rotationYaw += 10.0f;
				else if (mode == 2)
					rotationYaw -= 10.0f;
				motionX = MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI);
				motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI);
				if (distfromtarget < 80.0f)
					mode = 0;
			}

			List<Entity> var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRocket) {
                    ((EntityRocket) var9).explode(null);
                }

                if (var9 instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) var9).explode();
                }

                if (var9 instanceof EntityLaserBurst) {
                    var9.setDead();
                    this.attackEntityFrom(DamageSource.generic, 6);
                }
            }

			timeLeft--;
			if (timeLeft == 0)
			{
				motionY = 2.0f;
			}
			if (posY > 256.0f)
			{
				setDead();
			}
		}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		if (rhodeswing == null)
		{
			float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            this.rotationPitch = (float) (Math.atan2(-this.motionY, var16) * 180.0D / Math.PI);
            while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
                this.prevRotationPitch -= 360.0F;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		}
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void dropNuke()
	{
		if (dropAnything) ItemRoda.spawn(entityIndex, worldObj, posX+Math.random()*4-2, posY - 3.5f, posZ+Math.random()*4-2, motionX * 0.1f, -1.0f, motionZ * 0.1f, 1.0f, 0.0f);
	}
	Entity rhodes = null;
	public void startBombRun(double x, double z)
	{
		if (rhodes != null)
		{
            tpos = rhodes.getPosition();
			x = -rhodes.motionX;
			z = -rhodes.motionZ;
		}
		double dist = 1.0/Math.sqrt(x*x + z*z);
		x *= dist;
		z *= dist;
		motionX = -x;
		motionZ = -z;
        BlockPos position = tpos.add(x * 80, 60, z * 80);
        setPosition(position.getX(), position.getY(), position.getZ());
		prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("drop", entityIndex);
        nbt.setLong("tpos", tpos.toLong());
		nbt.setInteger("age", ticksSinceStart);
		nbt.setInteger("health", health);
		nbt.setInteger("duration", timeLeft);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		entityIndex = nbt.getInteger("drop");
		carpet = entityIndex < ItemRoda.rodaindex;
        tpos = BlockPos.fromLong(nbt.getLong("tpos"));
		ticksSinceStart = nbt.getInteger("age");
		health = nbt.getInteger("health");
		timeLeft = nbt.getInteger("duration");
		if (ticksSinceStart == 0) {
			double dx = tpos.getX() - posX;
			double dy = tpos.getY() - posY;
			startBombRun(dx, dy);
		}
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		super.attackEntityFrom(par1DamageSource, par2);
		if (!this.isDead && !this.worldObj.isRemote)
		{
			this.health -= par2;
			if (this.health <= 0)
			{
				this.setDead();
				this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 6.0F, true);
				worldObj.spawnEntityInWorld(new EntityB2Frag(worldObj, this, 0));
				worldObj.spawnEntityInWorld(new EntityB2Frag(worldObj, this, 1));
				EntityZombie pz = new EntityZombie(worldObj);
				pz.setPosition(posX, posY, posZ);
				worldObj.spawnEntityInWorld(pz);
				RivalRebelsSoundPlayer.playSound(this, 0, 0, 30, 1);
			}
		}

		return true;
	}

	@Override
	protected void entityInit()
	{
	}
}

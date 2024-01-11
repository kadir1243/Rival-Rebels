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
import assets.rivalrebels.common.command.CommandHotPotato;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.TsarBomba;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class EntityHotPotato extends EntityThrowable
{
	public int	ticksExisted	= 0;
	public int round = 0;
	public int nextx = 0;
	public int nexty = 0;
	public int nextz = 0;
	public boolean dorounds = false;
	public int charges = RivalRebels.tsarBombaStrength + 9;

	public EntityHotPotato(World par1World)
	{
		super(par1World);
		this.setSize(0.5F, 0.5F);
	}

	public EntityHotPotato(World par1World, int x, int y, int z, int count)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(x+0.5f, y+0.5f, z+0.5f);
		round = count;
		nextx = x;
		nexty = y;
		nextz = z;
		dorounds = true;
	}

	public EntityHotPotato(World world, double px, double py, double pz, double f, double g, double h)
	{
		super(world);
		setSize(0.5F, 0.5F);
		setPosition(px, py, pz);
		motionX = f;
		motionY = g;
		motionZ = h;
		round = 1;
		nextx = (int)px;
		nexty = (int)py;
		nextz = (int)pz;
		ticksExisted = 1;
		dorounds = true;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		if (ticksExisted == -100) explode();
		++this.ticksExisted;
		if (ticksExisted < 2 && dorounds)
		{
			RivalRebelsSoundPlayer.playSound(world, 14, 0, posX, posY, posZ, 100);
			motionX = 0;
			motionY = 0;
			motionZ = 0;
			setSize(0.5F, 0.5F);
			setPosition(nextx+0.5f, nexty+0.5f, nextz+0.5f);
			world.setBlockState(new BlockPos(nextx, nexty-400, nextz), RivalRebels.jump.getDefaultState());
			setPosition(posX, posY, posZ);
			return;
		}

		if (!world.isRemote)
		{
			Vec3d var15 = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult var3 = this.world.rayTraceBlocks(var15, var2);
			var15 = new Vec3d(this.posX, this.posY, this.posZ);
			var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (var3 != null)
			{
				var2 = var3.hitVec;
			}
			Entity var4 = null;
			List<Entity> var5 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.canBeCollidedWith()) {
                    float var10 = 0.3F;
                    AxisAlignedBB var11 = var9.getEntityBoundingBox().grow(var10, var10, var10);
                    RayTraceResult var12 = var11.calculateIntercept(var15, var2);

                    if (var12 != null) {
                        double var13 = var15.distanceTo(var12.hitVec);

                        if (var13 < var6 || var6 == 0.0D) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

			if (var4 != null)
			{
				var3 = new RayTraceResult(var4);
			}

			if (var3 != null)
			{
				this.onImpact(var3);
			}

			if (world.getBlockState(new BlockPos(getPositionVector())).getBlock() == Blocks.WATER)
			{
				motionY += 0.06f;
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		if (posY < 0) setDead();

		if (this.getRidingEntity() ==null)
		{
		float var16 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, var16) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{
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

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.05F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.05F;
		}
		float var17 = 0.98f;
		float var18 = this.getGravityVelocity();

		this.motionX *= var17;
		this.motionY *= var17;
		this.motionZ *= var17;
		this.motionY -= var18;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("charge", charges);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		charges = nbt.getInteger("charge");
		if (charges == 0) charges = RivalRebels.tsarBombaStrength + 9;
		prevRotationYaw = rotationYaw = nbt.getFloat("rot");
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	protected void onImpact(RayTraceResult var1)
	{
		if (var1.entityHit == null)
		{
			Block b = world.getBlockState(var1.getBlockPos()).getBlock();
			if (b == RivalRebels.jump || b == Blocks.ICE)
			{
				motionY = Math.max(-motionY, 0.2f);
				return;
			}
			if (world.rand.nextInt(10)!=0)
			{
				motionY = Math.max(-motionY, 0.2f);
				return;
			}
		}
		explode();
	}

    public void explode()
	{
		if (!world.isRemote)
		{
			TsarBomba tsar = new TsarBomba((int)posX, (int)posY, (int)posZ, world, charges);
			EntityTsarBlast tsarblast = new EntityTsarBlast(world, (int)posX, (int)posY, (int)posZ, tsar, charges);
			world.spawnEntity(tsarblast);
			ticksExisted = 0;
			round = round - 1;
			CommandHotPotato.roundinprogress = false;
			if (round <= 0) this.setDead();
		}
	}
}

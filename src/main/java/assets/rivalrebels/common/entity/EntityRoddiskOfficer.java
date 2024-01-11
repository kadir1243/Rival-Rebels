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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityRoddiskOfficer extends EntityInanimate
{
	public Entity shooter;

	public EntityRoddiskOfficer(World par1World)
	{
		super(par1World);
	}

	public EntityRoddiskOfficer(World par1World, EntityPlayer par2EntityLiving, float par3)
	{
		super(par1World);
		this.shooter = par2EntityLiving;
		this.setSize(0.5F, 0.5F);
		this.setEntityBoundingBox(new AxisAlignedBB(-0.4, -0.0625, -0.4, 0.4, 0.0625, 0.4));
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.posY -= 0.1;
		this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
	}

	public void setHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += this.rand.nextGaussian() * 0.005 * par8;
		par3 += this.rand.nextGaussian() * 0.005 * par8;
		par5 += this.rand.nextGaussian() * 0.005 * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float var10 = MathHelper.sqrt(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
	}

	@Override
	public void onUpdate()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;

		if (ticksExisted > 100 && shooter == null && !world.isRemote)
		{
			//world.spawnEntity(new EntityItem(world, posX, posY, posZ, new ItemStack(RivalRebels.roddisk)));
			setDead();
			RivalRebelsSoundPlayer.playSound(this, 5, 0);
		}
		if (ticksExisted >= 120 && !world.isRemote && shooter != null)
		{
			EntityItem ei = new EntityItem(world, shooter.posX, shooter.posY, shooter.posZ, new ItemStack(RivalRebels.roddisk));
			world.spawnEntity(ei);
			setDead();
			RivalRebelsSoundPlayer.playSound(this, 6, 1);
		}
		if (ticksExisted == 10)
		{
			RivalRebelsSoundPlayer.playSound(this, 6, 0);
		}

		int radius = 2;
		int nx = MathHelper.floor(posX - radius - 1.0D);
		int px = MathHelper.floor(posX + radius + 1.0D);
		int ny = MathHelper.floor(posY - radius - 1.0D);
		int py = MathHelper.floor(posY + radius + 1.0D);
		int nz = MathHelper.floor(posZ - radius - 1.0D);
		int pz = MathHelper.floor(posZ + radius + 1.0D);
		List<Entity> par9 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(nx, ny, nz, px, py, pz));

        for (Entity var31 : par9) {
            if (var31 instanceof EntityArrow) {
                var31.setDead();
            }
        }

		Vec3d var15 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult var3 = this.world.rayTraceBlocks(var15, var2);
		var15 = new Vec3d(this.posX, this.posY, this.posZ);
		var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (var3 != null)
		{
			var2 = var3.hitVec;
		}

		if (!this.world.isRemote)
		{
			Entity var4 = null;
			List<Entity> var5 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRoddiskRegular || var9 instanceof EntityRoddiskRebel) {
                    var9.setDead();
                    EntityItem ei = new EntityItem(world, var9.posX, var9.posY, var9.posZ, new ItemStack(RivalRebels.roddisk));
                    world.spawnEntity(ei);
                } else if (var9 instanceof EntityRoddiskOfficer) {
                    if (motionX + motionY + motionZ >= var9.motionX + var9.motionY + var9.motionZ) {
                        var9.setDead();
                    } else {
                        setDead();
                    }
                    EntityItem ei = new EntityItem(world, var9.posX, var9.posY, var9.posZ, new ItemStack(RivalRebels.roddisk));
                    world.spawnEntity(ei);
                } else if (var9.canBeCollidedWith() && var9 != this.shooter) {
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
		}

		if (var3 != null)
		{
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var3.hitVec.x, var3.hitVec.y, var3.hitVec.z, motionX * 0.1, motionY * 0.1, motionZ * 0.1);
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var3.hitVec.x, var3.hitVec.y, var3.hitVec.z, motionX * 0.1, motionY * 0.1, motionZ * 0.1);
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var3.hitVec.x, var3.hitVec.y, var3.hitVec.z, motionX * 0.1, motionY * 0.1, motionZ * 0.1);
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var3.hitVec.x, var3.hitVec.y, var3.hitVec.z, motionX * 0.1, motionY * 0.1, motionZ * 0.1);

			if (var3.entityHit != null)
			{
				RivalRebelsSoundPlayer.playSound(this, 5, 1);
				if (var3.entityHit instanceof EntityPlayer && shooter instanceof EntityPlayer && var3.entityHit != shooter)
				{
					EntityPlayer entityPlayerHit = (EntityPlayer) var3.entityHit;
                    for (ItemStack armorSlot : entityPlayerHit.inventory.armorInventory) {
                        if (!armorSlot.isEmpty()) {
                            armorSlot.damageItem(20, entityPlayerHit);
                            entityPlayerHit.attackEntityFrom(RivalRebelsDamageSource.tron, 1);
                        } else {
                            entityPlayerHit.attackEntityFrom(RivalRebelsDamageSource.tron, 10);
                        }
                    }
				}
				else
				{
					var3.entityHit.attackEntityFrom(RivalRebelsDamageSource.tron, 10);
					if (var3.entityHit instanceof EntitySkeleton)
					{
						var3.entityHit.setDead();
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 0, 3));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 1, 3));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 2, 3));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 2, 3));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 3, 3));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 3, 3));
					}
					if (var3.entityHit instanceof EntityZombie && !(var3.entityHit instanceof EntityPigZombie))
					{
						var3.entityHit.setDead();
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 0, 1));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 1, 1));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 2, 1));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 2, 1));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 3, 1));
						this.world.spawnEntity(new EntityGore(world, var3.entityHit, 3, 1));
					}
				}
			}
			else {
                IBlockState state = world.getBlockState(var3.getBlockPos());
                if (state.getBlock() == RivalRebels.flare)
                {
                    state.getBlock().onPlayerDestroy(world, var3.getBlockPos(), RivalRebels.flare.getDefaultState());
                }
                else if (state.getBlock() == RivalRebels.landmine || state.getBlock() == RivalRebels.alandmine)
                {
                    state.getBlock().onEntityCollision(world, var3.getBlockPos(), state, this);
                }
                else
                {
                    Block block = state.getBlock();
                    if (block == Blocks.GLASS || block == Blocks.GLASS_PANE)
                    {
                        world.setBlockToAir(var3.getBlockPos());
                    }
                    RivalRebelsSoundPlayer.playSound(this, 5, 2);

                    if (var3.sideHit == EnumFacing.WEST || var3.sideHit == EnumFacing.EAST) this.motionX *= -1;
                    if (var3.sideHit == EnumFacing.DOWN || var3.sideHit == EnumFacing.UP) this.motionY *= -1;
                    if (var3.sideHit == EnumFacing.NORTH || var3.sideHit == EnumFacing.SOUTH) this.motionZ *= -1;
                }
            }
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
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

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

		if (shooter != null)
		{
			motionX += (shooter.posX - posX) * 0.01f;
			motionY += ((shooter.posY + 1.62) - posY) * 0.01f;
			motionZ += (shooter.posZ - posZ) * 0.01f;
		}
		motionX *= 0.995f;
		motionY *= 0.995f;
		motionZ *= 0.995f;

		this.setPosition(this.posX, this.posY, this.posZ);
	}

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (ticksExisted < 10 || player != shooter) return false;
		if (player.inventory.addItemStackToInventory(new ItemStack(RivalRebels.roddisk)))
		{
			setDead();
			RivalRebelsSoundPlayer.playSound(this, 6, 1);
		}
		return true;
	}

	@Override
	public int getBrightnessForRender()
	{
		return 1000;
	}

	@Override
	public float getBrightness()
	{
		return 1000F;
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound var1)
	{

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound var1)
	{

	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.getEntityBoundingBox();
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	@Override
	public boolean canBePushed()
	{
		return true;
	}
}

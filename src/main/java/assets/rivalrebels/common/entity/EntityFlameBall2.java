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
import assets.rivalrebels.common.tileentity.TileEntityReciever;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityFlameBall2 extends EntityInanimate
{
	public int		sequence;
	public float	rotation;
	public float	motionr;
	public boolean	gonnadie;

	public EntityFlameBall2(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		rotation = (float) (Math.random() * 360);
		motionr = (float) (Math.random() - 0.5f) * 5;
	}

	public EntityFlameBall2(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
		rotation = (float) (Math.random() * 360);
		motionr = (float) (Math.random() - 0.5f) * 5;
	}

	public EntityFlameBall2(World par1World, EntityPlayer player, float par3)
	{
		super(par1World);
		// par3/=3f;
		setSize(0.5F, 0.5F);
		setPosition(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		motionX = (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI));
		posX -= (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		posY -= 0.13;
		posZ -= (MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		motionX *= par3;
		motionY *= par3;
		motionZ *= par3;
		rotation = (float) (Math.random() * 360);
		motionr = (float) (Math.random() - 0.5f) * 5;
		// Side side = FMLCommonHandler.instance().getEffectiveSide();
		// if (side == Side.SERVER)
		// {
		// ByteArrayOutputStream bos = new ByteArrayOutputStream(9);
		// DataOutputStream outputStream = new DataOutputStream(bos);
		// try
		// {
		// outputStream.writeInt(24);
		// outputStream.writeInt(entityId);
		// outputStream.writeByte((byte)mode);
		// }
		// catch (Exception ex)
		// {
		// ex.printStackTrace();
		// }
		// finally
		// {
		// try
		// {
		// if (outputStream != null) outputStream.close();
		// }
		// catch (IOException error)
		// {
		//
		// }
		// }
		// Packet250CustomPayload packet = new Packet250CustomPayload();
		// packet.channel = "RodolRivalRebels";
		// packet.data = bos.toByteArray();
		// packet.length = bos.size();
		// PacketDispatcher.sendPacketToAllPlayers(packet);
		// }
	}

	public EntityFlameBall2(World par1World, TileEntityReciever ter, float f)
	{
		super(par1World);
		rotationYaw = (float) (180 - ter.yaw);
		rotationPitch = (float) (-ter.pitch);
		setSize(0.5F, 0.5F);
        BlockPos position = ter.getPos().add(0.5, 0.5, 0.5).add(ter.xO, 0, ter.zO);
        setPosition(position.getX(), position.getY(), position.getZ());
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		motionX *= f;
		motionY *= f;
		motionZ *= f;
		rotation = (float) (Math.random() * 360);
		motionr = (float) (Math.random() - 0.5f) * 5;
	}

	public EntityFlameBall2(World world, double x, double y, double z, double mx, double my, double mz)
	{
		super(world);
		setSize(0.5F, 0.5F);
		setPosition(x, y, z);
		motionX = mx;
		motionY = my;
		motionZ = mz;
		rotation = (float) (Math.random() * 360);
		motionr = (float) (Math.random() - 0.5f) * 5;
	}

	@Override
	public void onUpdate()
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();
		ticksExisted++;
		sequence++;
		if (sequence > 15/* > RivalRebels.flamethrowerDecay */) setDead();
		if (ticksExisted > 5 && Math.random() > 0.5) gonnadie = true;

		if (gonnadie && sequence < 15) sequence++;

		Vec3 start = new Vec3(posX, posY, posZ);
		Vec3 end = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition mop = worldObj.rayTraceBlocks(start, end);
		start = new Vec3(posX, posY, posZ);
		end = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);

		if (mop != null) end = new Vec3(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);

		Entity e = null;
		List<Entity> var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;
		Iterator<Entity> var8 = var5.iterator();

		if (!worldObj.isRemote)
		{
			while (var8.hasNext())
			{
				Entity var9 = var8.next();

				if (var9.canBeCollidedWith())
				{
					float var10 = 0.3F;
					AxisAlignedBB var11 = var9.getEntityBoundingBox().expand(var10, var10, var10);
					MovingObjectPosition var12 = var11.calculateIntercept(start, end);

					if (var12 != null)
					{
						double var13 = start.distanceTo(var12.hitVec);

						if (var13 < var6 || var6 == 0.0D)
						{
							e = var9;
							var6 = var13;
						}
					}
				}
			}
		}

		if (e != null)
		{
			mop = new MovingObjectPosition(e);
		}

		if (mop != null && ticksExisted >= 6)
		{
			posX -= 0.5f;
			posY -= 0.5f;
			posZ -= 0.5f;
			fire();
			posX++;
			fire();
			posX--;
			posX--;
			fire();
			posX++;
			posY++;
			fire();
			posY--;
			posY--;
			fire();
			posY++;
			posZ++;
			fire();
			posZ--;
			posZ--;
			fire();
			posZ++;
			posX += 0.5f;
			posY += 0.5f;
			posZ += 0.5f;
			setDead();
			if (mop.entityHit != null)
			{
				mop.entityHit.setFire(3);
				mop.entityHit.attackEntityFrom(RivalRebelsDamageSource.cooked, 12);
				if (mop.entityHit instanceof EntityPlayer)
				{
                    EntityPlayer player = (EntityPlayer) mop.entityHit;
                    ItemStack[] armorSlots = player.inventory.armorInventory;
					int i = worldObj.rand.nextInt(4);
					if (armorSlots[i] != null && !worldObj.isRemote)
					{
						armorSlots[i].damageItem(8, player);
					}
				}
			}
		}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;

		rotation += motionr;
		motionr *= 1.06f;

		if (isInWater()) setDead();
		float airFriction = 0.9F;
		if (gonnadie)
		{
			motionY += 0.05;
			airFriction = 0.7f;
		}
		motionX *= airFriction;
		motionY *= airFriction;
		motionZ *= airFriction;
		setPosition(posX, posY, posZ);
	}

    @Override
	public int getBrightnessForRender(float par1)
	{
		return 1000;
	}

	@Override
	public float getBrightness(float par1)
	{
		return 1000;
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	public boolean canAttackWithItem()
	{
		return false;
	}

	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return true;
	}

	private void fire()
	{
		if (!worldObj.isRemote)
		{
            IBlockState state = worldObj.getBlockState(getPosition());
            Block id = state.getBlock();
			if (id == Blocks.air || id == Blocks.snow || id == Blocks.ice) worldObj.setBlockState(getPosition(), Blocks.fire.getDefaultState());
			else if (id == Blocks.sand && worldObj.rand.nextInt(60) == 0) worldObj.setBlockState(getPosition(), Blocks.glass.getDefaultState());
			else if (id == Blocks.glass && worldObj.rand.nextInt(120) == 0) worldObj.setBlockState(getPosition(), Blocks.obsidian.getDefaultState());
			else if (id == Blocks.obsidian && worldObj.rand.nextInt(90) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if ((id == Blocks.stone || id == Blocks.cobblestone) && worldObj.rand.nextInt(30) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.iron_ore && worldObj.rand.nextInt(30) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.coal_ore && worldObj.rand.nextInt(30) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.gold_ore && worldObj.rand.nextInt(30) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.lapis_ore && worldObj.rand.nextInt(30) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.gravel && worldObj.rand.nextInt(30) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.sandstone && worldObj.rand.nextInt(30) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.iron_block && worldObj.rand.nextInt(50) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if (id == Blocks.bedrock && worldObj.rand.nextInt(500) == 0) worldObj.setBlockState(getPosition(), Blocks.obsidian.getDefaultState());
			else if (id == Blocks.lava) worldObj.setBlockToAir(getPosition());
			else if (id == Blocks.leaves || id == Blocks.leaves2) worldObj.setBlockState(getPosition(), Blocks.fire.getDefaultState());
			else if (id == Blocks.grass) worldObj.setBlockState(getPosition(), Blocks.dirt.getDefaultState());
			else if (id == Blocks.dirt) worldObj.setBlockState(getPosition(), Blocks.fire.getDefaultState());
			else if (id == RivalRebels.flare) RivalRebels.flare.onBlockDestroyedByPlayer(worldObj, getPosition(), state);
			else if (id == RivalRebels.timedbomb) RivalRebels.timedbomb.onBlockDestroyedByPlayer(worldObj, getPosition(), state);
			else if (id == RivalRebels.remotecharge) RivalRebels.remotecharge.onBlockDestroyedByPlayer(worldObj, getPosition(), state);
			else if (id == RivalRebels.landmine) RivalRebels.landmine.onBlockDestroyedByPlayer(worldObj, getPosition(), state);
			else if (id == RivalRebels.alandmine) RivalRebels.remotecharge.onBlockDestroyedByPlayer(worldObj, getPosition(), state);
			else if (id == Blocks.tnt) {
				worldObj.setBlockToAir(getPosition());
				worldObj.createExplosion(null, posX, posY, posZ, 4, false);
			}
			else if (id == RivalRebels.conduit) worldObj.setBlockState(getPosition(), Blocks.fire.getDefaultState());
			else if (id == RivalRebels.reactive && worldObj.rand.nextInt(20) == 0) worldObj.setBlockState(getPosition(), Blocks.lava.getDefaultState());
			else if ((id == RivalRebels.camo1 || id == RivalRebels.camo2 || id == RivalRebels.camo3) && worldObj.rand.nextInt(40) == 0) worldObj.setBlockState(getPosition(), RivalRebels.steel.getDefaultState());
			else if (id == RivalRebels.steel && worldObj.rand.nextInt(40) == 0) worldObj.setBlockState(getPosition(), Blocks.fire.getDefaultState());
		}
	}
}

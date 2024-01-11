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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
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
		rotation = (float) (rand.nextDouble() * 360);
		motionr = (float) (rand.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall2(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
		rotation = (float) (rand.nextDouble() * 360);
		motionr = (float) (rand.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall2(World par1World, Entity player, float par3)
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
		rotation = (float) (rand.nextDouble() * 360);
		motionr = (float) (rand.nextDouble() - 0.5f) * 5;
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
		setPosition(ter.getPos().getX() + ter.xO + 0.5, ter.getPos().getY() + 0.5, ter.getPos().getZ() + ter.zO + 0.5);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		motionX *= f;
		motionY *= f;
		motionZ *= f;
		rotation = (float) (rand.nextDouble() * 360);
		motionr = (float) (rand.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall2(World world, double x, double y, double z, double mx, double my, double mz)
	{
		super(world);
		setSize(0.5F, 0.5F);
		setPosition(x, y, z);
		motionX = mx;
		motionY = my;
		motionZ = mz;
		rotation = (float) (rand.nextDouble() * 360);
		motionr = (float) (rand.nextDouble() - 0.5f) * 5;
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
		if (ticksExisted > 5 && rand.nextDouble() > 0.5) gonnadie = true;

		if (gonnadie && sequence < 15) sequence++;

		Vec3d start = new Vec3d(posX, posY, posZ);
		Vec3d end = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		RayTraceResult mop = world.rayTraceBlocks(start, end);
		start = new Vec3d(posX, posY, posZ);
		end = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

		if (mop != null) end = mop.hitVec;

		Entity e = null;
		List<Entity> var5 = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;
		Iterator<Entity> var8 = var5.iterator();

		if (!world.isRemote)
		{
			while (var8.hasNext())
			{
				Entity var9 = var8.next();

				if (var9.canBeCollidedWith())
				{
					float var10 = 0.3F;
					AxisAlignedBB var11 = var9.getEntityBoundingBox().grow(var10, var10, var10);
					RayTraceResult var12 = var11.calculateIntercept(start, end);

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
			mop = new RayTraceResult(e);
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
				if (mop.entityHit instanceof EntityPlayer player)
				{
                    NonNullList<ItemStack> armorSlots = player.inventory.armorInventory;
					int i = world.rand.nextInt(4);
					if (!armorSlots.get(i).isEmpty() && !world.isRemote)
					{
						armorSlots.get(i).damageItem(8, player);
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
	public int getBrightnessForRender()
	{
		return 1000;
	}

	@Override
	public float getBrightness()
	{
		return 1000;
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	public boolean canBeAttackedWithItem()
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
		if (!world.isRemote)
		{
			Block id = world.getBlockState(getPosition()).getBlock();
			if (id == Blocks.AIR || id == Blocks.SNOW || id == Blocks.ICE) world.setBlockState(getPosition(), Blocks.FIRE.getDefaultState());
			else if (id == Blocks.SAND && world.rand.nextInt(60) == 0) world.setBlockState(getPosition(), Blocks.GLASS.getDefaultState());
			else if (id == Blocks.GLASS && world.rand.nextInt(120) == 0) world.setBlockState(getPosition(), Blocks.OBSIDIAN.getDefaultState());
			else if (id == Blocks.OBSIDIAN && world.rand.nextInt(90) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if ((id == Blocks.STONE || id == Blocks.COBBLESTONE) && world.rand.nextInt(30) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.IRON_ORE && world.rand.nextInt(30) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.COAL_ORE && world.rand.nextInt(30) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.GOLD_ORE && world.rand.nextInt(30) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.LAPIS_ORE && world.rand.nextInt(30) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.GRAVEL && world.rand.nextInt(30) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.SANDSTONE && world.rand.nextInt(30) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.IRON_BLOCK && world.rand.nextInt(50) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.BEDROCK && world.rand.nextInt(500) == 0) world.setBlockState(getPosition(), Blocks.OBSIDIAN.getDefaultState());
			else if (id == Blocks.LAVA) world.setBlockToAir(getPosition());
			else if (id == Blocks.LEAVES || id == Blocks.LEAVES2) world.setBlockState(getPosition(), Blocks.FIRE.getDefaultState());
			else if (id == Blocks.GRASS) world.setBlockState(getPosition(), Blocks.DIRT.getDefaultState());
			else if (id == Blocks.DIRT) world.setBlockState(getPosition(), Blocks.FIRE.getDefaultState());
			else if (id == RivalRebels.flare) RivalRebels.flare.onPlayerDestroy(world, getPosition(), RivalRebels.flare.getDefaultState());
			else if (id == RivalRebels.timedbomb) RivalRebels.timedbomb.onPlayerDestroy(world, getPosition(), RivalRebels.timedbomb.getDefaultState());
			else if (id == RivalRebels.remotecharge) RivalRebels.remotecharge.onPlayerDestroy(world, getPosition(), RivalRebels.remotecharge.getDefaultState());
			else if (id == RivalRebels.landmine) RivalRebels.remotecharge.onPlayerDestroy(world, getPosition(), RivalRebels.landmine.getDefaultState());
			else if (id == RivalRebels.alandmine) RivalRebels.remotecharge.onPlayerDestroy(world, getPosition(), RivalRebels.alandmine.getDefaultState());
			else if (id == Blocks.TNT)
			{
				world.setBlockToAir(getPosition());
				world.createExplosion(null, posX, posY, posZ, 4, false);
			}
			else if (id == RivalRebels.conduit) world.setBlockState(getPosition(), Blocks.FIRE.getDefaultState());
			else if (id == RivalRebels.reactive && world.rand.nextInt(20) == 0) world.setBlockState(getPosition(), Blocks.LAVA.getDefaultState());
			else if ((id == RivalRebels.camo1 || id == RivalRebels.camo2 || id == RivalRebels.camo3) && world.rand.nextInt(40) == 0) world.setBlockState(getPosition(), RivalRebels.steel.getDefaultState());
			else if (id == RivalRebels.steel && world.rand.nextInt(40) == 0) world.setBlockState(getPosition(), Blocks.FIRE.getDefaultState());
		}
	}
}

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLaptop extends EntityInanimate
{
	public double	slide	= 0;
	double			test	= Math.PI;

	public EntityLaptop(World par1World)
	{
		super(par1World);
		this.setSize(1F, 0.6F);
		setEntityBoundingBox(new AxisAlignedBB(-0.21875, 0, -0.28125, 0.21875, 0.125, 0.28125));
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

	public EntityLaptop(World par1World, float x, float y, float z, float yaw)
	{
		super(par1World);
		setPosition(x, y, z);
		rotationYaw = yaw;
		setEntityBoundingBox(new AxisAlignedBB(-0.21875, 0, -0.28125, 0.21875, 0.125, 0.28125));
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		slide = (Math.cos(test) + 1) * 45;

        boolean i = false;
        for (EntityPlayer player : world.playerEntities) {
            if (player.getDistanceSq(posX + 0.5f, posY + 0.5f, posZ + 0.5f) <= 9) {
                i = true;
            }
        }
		if (i)
		{
			if (slide < 89.995) test += 0.05;
		}
		else
		{
			if (slide > 0.004) test -= 0.05;
		}
	}

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (player.isSneaking() && !player.world.isRemote) {
			player.openGui(RivalRebels.instance, 0, player.world, 0, 0, 0);
		}
		if (!player.isSneaking() && player.inventory.addItemStackToInventory(new ItemStack(RivalRebels.controller)))
		{
			player.swingArm(hand);
			setDead();
		}
		return true;
	}

}

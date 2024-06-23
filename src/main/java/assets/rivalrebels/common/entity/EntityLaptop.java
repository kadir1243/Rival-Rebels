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

import assets.rivalrebels.common.block.RRBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class EntityLaptop extends EntityInanimate
{
	public double	slide	= 0;
	double			test	= Math.PI;

    public EntityLaptop(EntityType<? extends EntityLaptop> type, World world) {
        super(type, world);
    }

	public EntityLaptop(World par1World) {
		this(RREntities.LAPTOP, par1World);
		setBoundingBox(new Box(-0.21875, 0, -0.28125, 0.21875, 0.125, 0.28125));
	}

    @Override
    public boolean isCollidable() {
        return this.isAlive();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    public EntityLaptop(World par1World, float x, float y, float z, float yaw) {
		this(par1World);
		setPosition(x, y, z);
        this.setYaw(yaw);
	}

	@Override
	public void tick()
	{
		super.tick();
		slide = (Math.cos(test) + 1) * 45;

        if (getWorld().isPlayerInRange(getX() + 0.5F, getY() + 0.5F, getZ() + 0.5F, 9)) {
			if (slide < 89.995) test += 0.05;
		} else {
			if (slide > 0.004) test -= 0.05;
		}
	}

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
		if (player.isSneaking() && !player.getWorld().isClient) {
			player.openHandledScreen(null);
		}
		if (!player.isSneaking() && player.getInventory().insertStack(RRBlocks.controller.asItem().getDefaultStack()))
		{
			player.swingHand(hand);
			kill();
		}
		return ActionResult.success(getWorld().isClient);
	}

}

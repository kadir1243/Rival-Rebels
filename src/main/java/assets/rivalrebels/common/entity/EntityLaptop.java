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
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EntityLaptop extends EntityInanimate
{
	public double	slide	= 0;
	private float test = Mth.PI;

    public EntityLaptop(EntityType<? extends EntityLaptop> type, Level world) {
        super(type, world);
    }

	public EntityLaptop(Level level) {
		this(RREntities.LAPTOP, level);
		setBoundingBox(new AABB(-0.21875, 0, -0.28125, 0.21875, 0.125, 0.28125));
	}

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    public EntityLaptop(Level level, float x, float y, float z, float yaw) {
		this(level);
		setPos(x, y, z);
        this.setYRot(yaw);
	}

	@Override
	public void tick()
	{
		super.tick();
		slide = (Mth.cos(test) + 1) * 45;

        if (level().hasNearbyAlivePlayer(getX() + 0.5F, getY() + 0.5F, getZ() + 0.5F, 9)) {
			if (slide < 89.995) test += 0.05F;
		} else {
			if (slide > 0.004) test -= 0.05F;
		}
	}

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
		if (player.isShiftKeyDown() && !player.level().isClientSide()) {
			player.openMenu(null);
		}
		if (!player.isShiftKeyDown() && player.getInventory().add(RRBlocks.controller.asItem().getDefaultInstance())) {
			kill();
            return InteractionResult.sidedSuccess(level().isClientSide());
		}
		return InteractionResult.PASS;
	}

}

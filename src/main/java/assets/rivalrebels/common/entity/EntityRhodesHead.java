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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityRhodesHead extends EntityRhodesPiece
{
	public EntityRhodesHead(EntityType<? extends EntityRhodesHead> type, World w)
	{
		super(type, w);
	}

	public EntityRhodesHead(World w, double x, double y, double z, float scale, int color)
	{
		super(RREntities.RHODES_HEAD, w, x, y, z, scale, color);
		health = 700;
	}

	@Override
	public int getMaxAge()
	{
		return 3000;
	}

	@Override
	public boolean damage(DamageSource par1DamageSource, float par2) {
		if (isAlive() && !world.isClient) {
			health -= par2;
			if (health <= 0)
			{
				kill();
				world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(RRItems.nuclearelement, 4)));
				world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), RRItems.core3.getDefaultStack()));
				world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), RRItems.einsten.getDefaultStack()));
				if (random.nextBoolean())
				{
					world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), RRBlocks.buildrhodes.asItem().getDefaultStack()));
				}
				RivalRebelsSoundPlayer.playSound(this, 0, 0, 30, 1);
			}
		}

		return true;
	}
}

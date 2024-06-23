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
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EntityRhodesHead extends EntityRhodesPiece
{
	public EntityRhodesHead(EntityType<? extends EntityRhodesHead> type, Level w)
	{
		super(type, w);
	}

	public EntityRhodesHead(Level w, double x, double y, double z, float scale, int color)
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
	public boolean hurt(DamageSource par1DamageSource, float par2) {
		if (isAlive() && !level().isClientSide) {
			health -= par2;
			if (health <= 0)
			{
				kill();
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), new ItemStack(RRItems.nuclearelement, 4)));
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.core3.getDefaultInstance()));
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.einsten.getDefaultInstance()));
				if (random.nextBoolean())
				{
					level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRBlocks.buildrhodes.asItem().getDefaultInstance()));
				}
                level().playLocalSound(this, RRSounds.ARTILLERY_EXPLODE, getSoundSource(), 30, 1);
			}
		}

		return true;
	}
}

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
package io.github.kadir1243.rivalrebels.common.entity;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

public class EntityRhodesHead extends EntityRhodesPiece
{
	public EntityRhodesHead(EntityType<? extends EntityRhodesHead> type, Level w)
	{
		super(type, w);
	}

	public EntityRhodesHead(Level w, double x, double y, double z, float scale, Holder<RhodesType> color)
	{
		super(RREntities.RHODES_HEAD.get(), w, x, y, z, scale, color);
		health = 700;
	}

	@Override
	public int getMaxAge()
	{
		return 3000;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isAlive() && !level().isClientSide()) {
			health -= amount;
			if (health <= 0)
			{
				kill();
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.NUCLEAR_ROD.toStack(4)));
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.core3.toStack()));
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.einsten.toStack()));
				if (random.nextBoolean())
				{
					level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRBlocks.buildrhodes.toStack()));
				}
                this.playSound(RRSounds.ARTILLERY_EXPLODE.get(), 30, 1);
			}
		}

		return true;
	}
}

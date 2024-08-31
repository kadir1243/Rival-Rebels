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

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityRhodesLeftUpperLeg extends EntityRhodesPiece
{
	public EntityRhodesLeftUpperLeg(EntityType<? extends EntityRhodesLeftUpperLeg> type, Level w)
	{
		super(type, w);
	}

	public EntityRhodesLeftUpperLeg(Level w, double x, double y, double z, float scale, RhodesType color)
	{
		super(RREntities.RHODES_LEFT_UPPER_LEG, w, x, y, z, scale, color);
		health = 600;
	}

	@Override
	public int getMaxAge()
	{
		return 700;
	}
}

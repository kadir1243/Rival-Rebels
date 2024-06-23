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

public class EntityRhodesLeftUpperArm extends EntityRhodesPiece
{
	public EntityRhodesLeftUpperArm(EntityType<? extends EntityRhodesLeftUpperArm> type, Level w)
	{
		super(type, w);
	}

	public EntityRhodesLeftUpperArm(Level w, double x, double y, double z, float scale, int color)
	{
		super(RREntities.RHODES_LEFT_UPPER_ARM, w, x, y, z, scale, color);
		health = 400;
	}

	@Override
	public int getMaxAge()
	{
		return 700;
	}
}

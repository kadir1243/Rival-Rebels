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
package assets.rivalrebels.common.item;

import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;

public class ItemRod extends ToolItem
{
	public int power;

	public ItemRod() {
		super(ToolMaterials.DIAMOND, new Settings().maxDamage(32));
	}

    public ItemRod(Settings settings) {
        super(ToolMaterials.DIAMOND, settings);
    }

	@Override
	public boolean isDamageable()
	{
		return true;
	}
}

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
package assets.rivalrebels.common.core;

import assets.rivalrebels.common.item.RRItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class RivalRebelsTab extends ItemGroup
{
	private final String name;
	private final int icon;

	public RivalRebelsTab(String name, int icon)
	{
		super(name);
		this.name = name;
		this.icon = icon;
	}

    @Override
    public ItemStack createIcon() {
        if (icon == 0) return RRItems.nuclearelement.getDefaultStack();
        else return RRItems.hydrod.getDefaultStack();
    }

    @Override
    public String getName() {
        return name;
    }
}

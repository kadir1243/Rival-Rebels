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

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ItemClassArmor extends ArmorItem {
	public int team;
	public int stateclass;

	public ItemClassArmor(Holder<ArmorMaterial> material, Type type, int team, int stateclass) {
		super(material, type, new Properties().durability(material.value().getDefense(type)));
		this.team = team;
		this.stateclass = stateclass;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		String str = "";
		if (team == 0) str = "o";
		if (team == 1) str = "s";
		if (stateclass == 0) str = "r" + str;
		if (stateclass == 1) str = "n" + str;
		if (stateclass == 2) str = "i" + str;
		if (stateclass == 3) str = "h" + str;
		if (armorType == 0) itemIcon = iconregister.registerIcon("rivalrebels:" + str + "h");
		if (armorType == 1) itemIcon = iconregister.registerIcon("rivalrebels:" + str + "c");
		if (armorType == 2) itemIcon = iconregister.registerIcon("rivalrebels:" + str + "p");
		if (armorType == 3) itemIcon = iconregister.registerIcon("rivalrebels:" + str + "b");
	}*/
}

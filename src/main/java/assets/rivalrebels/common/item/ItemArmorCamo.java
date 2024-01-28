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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemArmorCamo extends ArmorItem {
	public int team;

	public ItemArmorCamo(ArmorMaterial material, EquipmentSlot par4, int team) {
		super(material, par4, new Item.Settings().group(RRItems.rrarmortab).maxDamage(material.getDurability(par4) * 2));
		this.team = team;
	}

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (stack.getItem() == RRItems.camohat || stack.getItem() == RRItems.camoshirt || stack.getItem() == RRItems.camoshoes)
		{
			return "rivalrebels:textures/armors/c.png";
		}
		if (stack.getItem() == RRItems.camopants)
		{
			return "rivalrebels:textures/armors/d.png";
		}
		if (stack.getItem() == RRItems.camohat2 || stack.getItem() == RRItems.camoshirt2 || stack.getItem() == RRItems.camoshoes2)
		{
			return "rivalrebels:textures/armors/a.png";
		}
		if (stack.getItem() == RRItems.camopants2)
		{
			return "rivalrebels:textures/armors/b.png";
		}
		return null;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		String str = "";
		if (team == 0) str = "o";
		if (team == 1) str = "s";
		if (armorType == 0) itemIcon = iconregister.registerIcon("RivalRebels:" + str + "h");
		if (armorType == 1) itemIcon = iconregister.registerIcon("RivalRebels:" + str + "v");
		if (armorType == 2) itemIcon = iconregister.registerIcon("RivalRebels:" + str + "p");
		if (armorType == 3) itemIcon = iconregister.registerIcon("RivalRebels:" + str + "b");
	}*/
}

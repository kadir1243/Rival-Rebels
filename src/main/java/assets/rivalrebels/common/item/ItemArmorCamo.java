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

import assets.rivalrebels.RivalRebels;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemArmorCamo extends ItemArmor

{
	private static final int	maxDamageArray[]	= { 11, 16, 15, 13 };
	public int					team;

	public ItemArmorCamo(ArmorMaterial par2EnumArmorMaterial, EntityEquipmentSlot par4, int team)
	{
		super(par2EnumArmorMaterial, 0, par4);
		setCreativeTab(RivalRebels.rrarmortab);
		setMaxDamage(par2EnumArmorMaterial.getDurability(par4) * 2);
		setMaxStackSize(1);
		this.team = team;
	}

	static int[] getMaxDamageArray()
	{
		return maxDamageArray;
	}

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == RivalRebels.camohat || stack.getItem() == RivalRebels.camoshirt || stack.getItem() == RivalRebels.camoshoes)
		{
			return "rivalrebels:textures/armors/c.png";
		}
		if (stack.getItem() == RivalRebels.camopants)
		{
			return "rivalrebels:textures/armors/d.png";
		}
		if (stack.getItem() == RivalRebels.camohat2 || stack.getItem() == RivalRebels.camoshirt2 || stack.getItem() == RivalRebels.camoshoes2)
		{
			return "rivalrebels:textures/armors/a.png";
		}
		if (stack.getItem() == RivalRebels.camopants2)
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

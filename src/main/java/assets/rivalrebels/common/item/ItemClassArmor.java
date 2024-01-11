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
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemClassArmor extends ItemArmor {
	public int					team;
	public int					stateclass;

	public ItemClassArmor(ArmorMaterial par2EnumArmorMaterial, EntityEquipmentSlot par4, int team, int stateclass)
	{
		super(par2EnumArmorMaterial, 0, par4);
		setCreativeTab(RivalRebels.rrarmortab);
		setMaxDamage(par2EnumArmorMaterial.getDurability(par4));
		setMaxStackSize(1);
		this.team = team;
		this.stateclass = stateclass;
	}

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		Item item = stack.getItem();
		if (item == RivalRebels.orebelhelmet) return "rivalrebels:textures/armors/l.png";
		if (item == RivalRebels.orebelchest) return "rivalrebels:textures/armors/l.png";
		if (item == RivalRebels.orebelpants) return "rivalrebels:textures/armors/k.png";
		if (item == RivalRebels.orebelboots) return "rivalrebels:textures/armors/l.png";
		if (item == RivalRebels.onukerhelmet) return "rivalrebels:textures/armors/i.png";
		if (item == RivalRebels.onukerchest) return "rivalrebels:textures/armors/i.png";
		if (item == RivalRebels.onukerpants) return "rivalrebels:textures/armors/k.png";
		if (item == RivalRebels.onukerboots) return "rivalrebels:textures/armors/i.png";
		if (item == RivalRebels.ointelhelmet) return "rivalrebels:textures/armors/g.png";
		if (item == RivalRebels.ointelchest) return "rivalrebels:textures/armors/g.png";
		if (item == RivalRebels.ointelpants) return "rivalrebels:textures/armors/k.png";
		if (item == RivalRebels.ointelboots) return "rivalrebels:textures/armors/g.png";
		if (item == RivalRebels.ohackerhelmet) return "rivalrebels:textures/armors/e.png";
		if (item == RivalRebels.ohackerchest) return "rivalrebels:textures/armors/e.png";
		if (item == RivalRebels.ohackerpants) return "rivalrebels:textures/armors/k.png";
		if (item == RivalRebels.ohackerboots) return "rivalrebels:textures/armors/e.png";
		if (item == RivalRebels.srebelhelmet) return "rivalrebels:textures/armors/m.png";
		if (item == RivalRebels.srebelchest) return "rivalrebels:textures/armors/m.png";
		if (item == RivalRebels.srebelpants) return "rivalrebels:textures/armors/n.png";
		if (item == RivalRebels.srebelboots) return "rivalrebels:textures/armors/m.png";
		if (item == RivalRebels.snukerhelmet) return "rivalrebels:textures/armors/j.png";
		if (item == RivalRebels.snukerchest) return "rivalrebels:textures/armors/j.png";
		if (item == RivalRebels.snukerpants) return "rivalrebels:textures/armors/n.png";
		if (item == RivalRebels.snukerboots) return "rivalrebels:textures/armors/j.png";
		if (item == RivalRebels.sintelhelmet) return "rivalrebels:textures/armors/h.png";
		if (item == RivalRebels.sintelchest) return "rivalrebels:textures/armors/h.png";
		if (item == RivalRebels.sintelpants) return "rivalrebels:textures/armors/n.png";
		if (item == RivalRebels.sintelboots) return "rivalrebels:textures/armors/h.png";
		if (item == RivalRebels.shackerhelmet) return "rivalrebels:textures/armors/f.png";
		if (item == RivalRebels.shackerchest) return "rivalrebels:textures/armors/f.png";
		if (item == RivalRebels.shackerpants) return "rivalrebels:textures/armors/n.png";
		if (item == RivalRebels.shackerboots) return "rivalrebels:textures/armors/f.png";
		return "";
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

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

public class ItemClassArmor extends ArmorItem {
	public int team;
	public int stateclass;

	public ItemClassArmor(ArmorMaterial material, EquipmentSlot par4, int team, int stateclass) {
		super(material, par4, new Settings().group(RRItems.rrarmortab).maxDamage(material.getDurability(par4)));
		this.team = team;
		this.stateclass = stateclass;
	}

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		Item item = stack.getItem();
		if (item == RRItems.orebelhelmet) return "rivalrebels:textures/armors/l.png";
		if (item == RRItems.orebelchest) return "rivalrebels:textures/armors/l.png";
		if (item == RRItems.orebelpants) return "rivalrebels:textures/armors/k.png";
		if (item == RRItems.orebelboots) return "rivalrebels:textures/armors/l.png";
		if (item == RRItems.onukerhelmet) return "rivalrebels:textures/armors/i.png";
		if (item == RRItems.onukerchest) return "rivalrebels:textures/armors/i.png";
		if (item == RRItems.onukerpants) return "rivalrebels:textures/armors/k.png";
		if (item == RRItems.onukerboots) return "rivalrebels:textures/armors/i.png";
		if (item == RRItems.ointelhelmet) return "rivalrebels:textures/armors/g.png";
		if (item == RRItems.ointelchest) return "rivalrebels:textures/armors/g.png";
		if (item == RRItems.ointelpants) return "rivalrebels:textures/armors/k.png";
		if (item == RRItems.ointelboots) return "rivalrebels:textures/armors/g.png";
		if (item == RRItems.ohackerhelmet) return "rivalrebels:textures/armors/e.png";
		if (item == RRItems.ohackerchest) return "rivalrebels:textures/armors/e.png";
		if (item == RRItems.ohackerpants) return "rivalrebels:textures/armors/k.png";
		if (item == RRItems.ohackerboots) return "rivalrebels:textures/armors/e.png";
		if (item == RRItems.srebelhelmet) return "rivalrebels:textures/armors/m.png";
		if (item == RRItems.srebelchest) return "rivalrebels:textures/armors/m.png";
		if (item == RRItems.srebelpants) return "rivalrebels:textures/armors/n.png";
		if (item == RRItems.srebelboots) return "rivalrebels:textures/armors/m.png";
		if (item == RRItems.snukerhelmet) return "rivalrebels:textures/armors/j.png";
		if (item == RRItems.snukerchest) return "rivalrebels:textures/armors/j.png";
		if (item == RRItems.snukerpants) return "rivalrebels:textures/armors/n.png";
		if (item == RRItems.snukerboots) return "rivalrebels:textures/armors/j.png";
		if (item == RRItems.sintelhelmet) return "rivalrebels:textures/armors/h.png";
		if (item == RRItems.sintelchest) return "rivalrebels:textures/armors/h.png";
		if (item == RRItems.sintelpants) return "rivalrebels:textures/armors/n.png";
		if (item == RRItems.sintelboots) return "rivalrebels:textures/armors/h.png";
		if (item == RRItems.shackerhelmet) return "rivalrebels:textures/armors/f.png";
		if (item == RRItems.shackerchest) return "rivalrebels:textures/armors/f.png";
		if (item == RRItems.shackerpants) return "rivalrebels:textures/armors/n.png";
		if (item == RRItems.shackerboots) return "rivalrebels:textures/armors/f.png";
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

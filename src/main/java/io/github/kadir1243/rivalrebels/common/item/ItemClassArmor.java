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
package io.github.kadir1243.rivalrebels.common.item;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemClassArmor extends ArmorItem {
    public ItemClassArmor(Holder<ArmorMaterial> material, Type type) {
        super(material, type, new Properties().durability(material.value().getDefense(type)));
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        String name = null;
        if (stack.is(RRItems.orebelhelmet)) name = "textures/armors/l.png";
        else if (stack.is(RRItems.orebelchest)) name = "textures/armors/l.png";
        else if (stack.is(RRItems.orebelpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.orebelboots)) name = "textures/armors/l.png";
        else if (stack.is(RRItems.onukerhelmet)) name = "textures/armors/i.png";
        else if (stack.is(RRItems.onukerchest)) name = "textures/armors/i.png";
        else if (stack.is(RRItems.onukerpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.onukerboots)) name = "textures/armors/i.png";
        else if (stack.is(RRItems.ointelhelmet)) name = "textures/armors/g.png";
        else if (stack.is(RRItems.ointelchest)) name = "textures/armors/g.png";
        else if (stack.is(RRItems.ointelpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.ointelboots)) name = "textures/armors/g.png";
        else if (stack.is(RRItems.ohackerhelmet)) name = "textures/armors/e.png";
        else if (stack.is(RRItems.ohackerchest)) name = "textures/armors/e.png";
        else if (stack.is(RRItems.ohackerpants)) name = "textures/armors/k.png";
        else if (stack.is(RRItems.ohackerboots)) name = "textures/armors/e.png";
        else if (stack.is(RRItems.srebelhelmet)) name = "textures/armors/m.png";
        else if (stack.is(RRItems.srebelchest)) name = "textures/armors/m.png";
        else if (stack.is(RRItems.srebelpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.srebelboots)) name = "textures/armors/m.png";
        else if (stack.is(RRItems.snukerhelmet)) name = "textures/armors/j.png";
        else if (stack.is(RRItems.snukerchest)) name = "textures/armors/j.png";
        else if (stack.is(RRItems.snukerpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.snukerboots)) name = "textures/armors/j.png";
        else if (stack.is(RRItems.sintelhelmet)) name = "textures/armors/h.png";
        else if (stack.is(RRItems.sintelchest)) name = "textures/armors/h.png";
        else if (stack.is(RRItems.sintelpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.sintelboots)) name = "textures/armors/h.png";
        else if (stack.is(RRItems.shackerhelmet)) name = "textures/armors/f.png";
        else if (stack.is(RRItems.shackerchest)) name = "textures/armors/f.png";
        else if (stack.is(RRItems.shackerpants)) name = "textures/armors/n.png";
        else if (stack.is(RRItems.shackerboots)) name = "textures/armors/f.png";
        if (name != null) return RRIdentifiers.create(name);
        return super.getArmorTexture(stack, entity, slot, layer, innerModel);
    }
}

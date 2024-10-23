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

import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRodNuclear extends Item {
	public ItemRodNuclear(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world.random.nextInt(16) == 0) {
            entity.hurt(RivalRebelsDamageSource.radioactivePoisoning(world), world.random.nextInt(4));
        }
	}
}

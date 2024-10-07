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
package io.github.kadir1243.rivalrebels.common.block.crate;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class BlockFlagBox3 extends AbstractFlagBoxBlock {
	public BlockFlagBox3(Properties settings)
	{
		super(settings);
	}

    @Override
    public BlockState getStateToReplace() {
        return RRBlocks.flagbox4.get().defaultBlockState();
    }

    @Override
    public ItemStack[] getItems() {
        return new ItemStack[] {RRBlocks.flag3.toStack(10)};
    }
}

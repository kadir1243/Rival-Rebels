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
package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityNukeCrate extends BlockEntity {
    public TileEntityNukeCrate(BlockPos pos, BlockState state) {
        super(RRTileEntities.NUKE_CRATE, pos, state);
        EntityRhodes.BLOCK_ENTITIES.put(pos, this);
    }

    @Override
    public void markRemoved() {
        super.markRemoved();
        EntityRhodes.BLOCK_ENTITIES.remove(getPos());
    }
}

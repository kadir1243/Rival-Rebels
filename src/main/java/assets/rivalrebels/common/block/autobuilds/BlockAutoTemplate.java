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
package assets.rivalrebels.common.block.autobuilds;

import assets.rivalrebels.common.core.BlackList;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.Translations;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BlockAutoTemplate extends FallingBlock {
    public int		time	= 15;
	public String	name	= "building";

    public BlockAutoTemplate(Properties settings) {
        super(settings);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (!stack.is(RRItems.pliers)){
                player.displayClientMessage(Translations.warning().append(" ").append(Component.translatable(Translations.USE_PLIERS_TO_BUILD_TRANSLATION.toLanguageKey())), true);
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
		}
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	public void build(Level world, int x, int y, int z) {
        world.playLocalSound(x, y, z, RRSounds.AUTO_BUILD, SoundSource.BLOCKS, 10, 1, false);
	}

	public void placeBlockCarefully(Level world, int x, int y, int z, Block block) {
		placeBlockCarefully(world, x, y, z, block.defaultBlockState());
	}

    public void placeBlockCarefully(Level world, int x, int y, int z, BlockState state) {
        placeBlockCarefully(world, new BlockPos(x, y, z), state);
    }

    public void placeBlockCarefully(Level world, BlockPos pos, Block block) {
        placeBlockCarefully(world, pos, block.defaultBlockState());
    }

    public void placeBlockCarefully(Level world, BlockPos pos, BlockState state) {
        if (!BlackList.autobuild(world.getBlockState(pos))) {
            world.setBlockAndUpdate(pos, state);
        }
    }

    @Override
    public void onLand(Level world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
		if (!world.isClientSide()) build(world, pos.getX(), pos.getY(), pos.getZ());
	}

}

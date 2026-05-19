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
import io.github.kadir1243.rivalrebels.common.block.trap.*;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityNukeCrate;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class BlockNukeCrate extends BaseEntityBlock {
    public static final MapCodec<BlockNukeCrate> CODEC = simpleCodec(BlockNukeCrate::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	public BlockNukeCrate(Properties settings)
	{
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.UP));
	}

    @Override
    protected MapCodec<BlockNukeCrate> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public Direction determineOrientation(Level world, BlockPos pos) {
        Direction targetFacing = Direction.UP;
		if (this == RRBlocks.nukeCrateTop.get()) {
            for (Direction facing : Direction.values()) {
                BlockPos offset = pos.relative(facing);
                if (world.getBlockState(offset).is(RRBlocks.nukeCrateBottom)) {
                    targetFacing = facing.getOpposite();
                }
            }
		} else if (this == RRBlocks.nukeCrateBottom.get()) {
            for (Direction facing : Direction.values()) {
                BlockPos offset = pos.relative(facing);
                if (world.getBlockState(offset).is(RRBlocks.nukeCrateTop)) {
                    targetFacing = facing;
                }
            }
		}
		return targetFacing;
	}

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
		world.setBlockAndUpdate(pos, state.setValue(FACING, determineOrientation(world, pos)));
	}

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.setBlockAndUpdate(pos, state.setValue(FACING, determineOrientation(level, pos)));

        for (Direction facing : Direction.values()) {
            BlockPos offset = pos.relative(facing);
            BlockState offsetState = level.getBlockState(offset);
            if (offsetState.is(RRBlocks.nukeCrateBottom)) {
                neighborChanged(state, level, pos, this, null, true);
            } else if (offsetState.getFluidState().is(FluidTags.LAVA)) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 3, Level.ExplosionInteraction.NONE);
            }
        }
	}

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (this == RRBlocks.nukeCrateTop.get()) {
			if (!stack.isEmpty()) {
				if (stack.is(RRItems.pliers)) {
					Direction orientation = null;
					if (	getBlockState(level, pos.east()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().east()).is(RRBlocks.nukeCrateBottom)) {
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.east(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().east(), RRBlocks.antimatterbombblock.get().defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.WEST));
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.west()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().west()).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.west(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().west(), RRBlocks.antimatterbombblock.get().defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.EAST));
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.south()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().south()).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.south(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().south(), RRBlocks.antimatterbombblock.get().defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.NORTH));
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.north()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().north()).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.north(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().north(), RRBlocks.antimatterbombblock.get().defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.SOUTH));
						return InteractionResult.SUCCESS;
					}
					if (	getBlockState(level, pos.east()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().east()).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().east(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.east(), RRBlocks.tachyonbombblock.get().defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.WEST));
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.west()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().west()).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().west(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.west(), RRBlocks.tachyonbombblock.get().defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.EAST));
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.south()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().south()).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().south(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.south(), RRBlocks.tachyonbombblock.get().defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.NORTH));
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.north()).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().north()).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().north(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.north(), RRBlocks.tachyonbombblock.get().defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.SOUTH));
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.east()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.east(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.east(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().east()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().east(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below().east(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.east(), Blocks.AIR);
						setBlock(level, pos.east(2), Blocks.AIR);
						setBlock(level, pos.east(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().east(), RRBlocks.tsarbombablock.get().defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.WEST));
						setBlock(level, pos.below().east(2), Blocks.AIR);
						setBlock(level, pos.below().east(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.west()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.west(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.west(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().west()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().west(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below().west(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.west(), Blocks.AIR);
						setBlock(level, pos.west(2), Blocks.AIR);
						setBlock(level, pos.west(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().west(), RRBlocks.tsarbombablock.get().defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.EAST));
						setBlock(level, pos.below().west(2), Blocks.AIR);
						setBlock(level, pos.below().west(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.south()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.south(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.south(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().south()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().south(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below().south(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.south(), Blocks.AIR);
						setBlock(level, pos.south(2), Blocks.AIR);
						setBlock(level, pos.south(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().south(), RRBlocks.tsarbombablock.get().defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.NORTH));
                        setBlock(level, pos.below().south(2), Blocks.AIR);
						setBlock(level, pos.below().south(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.north()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.north(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.north(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().north()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.below().north(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.below().north(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.north(), Blocks.AIR);
						setBlock(level, pos.north(2), Blocks.AIR);
						setBlock(level, pos.north(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().north(), RRBlocks.tsarbombablock.get().defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.SOUTH));
						setBlock(level, pos.below().north(2), Blocks.AIR);
						setBlock(level, pos.below().north(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.east()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.east(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.east(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().east()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().east(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above().east(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().east(), Blocks.AIR);
						setBlock(level, pos.east(2), Blocks.AIR);
						setBlock(level, pos.east(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.east(), RRBlocks.theoreticaltsarbombablock.get().defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.WEST));
						setBlock(level, pos.above().east(2), Blocks.AIR);
						setBlock(level, pos.above().east(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.west()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.west(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.west(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().west()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().west(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above().west(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().west(), Blocks.AIR);
						setBlock(level, pos.west(2), Blocks.AIR);
						setBlock(level, pos.west(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.west(), RRBlocks.theoreticaltsarbombablock.get().defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.EAST));
						setBlock(level, pos.above().west(2), Blocks.AIR);
						setBlock(level, pos.above().west(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.south()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.south(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.south(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().south()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().south(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above().south(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().south(), Blocks.AIR);
						setBlock(level, pos.south(2), Blocks.AIR);
						setBlock(level, pos.south(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.south(), RRBlocks.theoreticaltsarbombablock.get().defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.NORTH));
						setBlock(level, pos.above().south(2), Blocks.AIR);
						setBlock(level, pos.above().south(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					}
					else if (getBlockState(level, pos.north()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.north(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.north(3)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().north()).is(RRBlocks.nukeCrateTop) &&
							getBlockState(level, pos.above().north(2)).is(RRBlocks.nukeCrateBottom) &&
							getBlockState(level, pos.above().north(3)).is(RRBlocks.nukeCrateBottom))
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().north(), Blocks.AIR);
						setBlock(level, pos.north(2), Blocks.AIR);
						setBlock(level, pos.north(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.north(), RRBlocks.theoreticaltsarbombablock.get().defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.SOUTH));
						setBlock(level, pos.above().north(2), Blocks.AIR);
						setBlock(level, pos.above().north(3), Blocks.AIR);
						return InteractionResult.SUCCESS;
					} else {
                        for (Direction direction : Direction.values()) {
                            if (level.getBlockState(pos.relative(direction.getOpposite())).is(RRBlocks.nukeCrateBottom.get())) {
                                level.setBlockAndUpdate(pos.relative(direction.getOpposite()), Blocks.AIR.defaultBlockState());
                                orientation = direction;
                                break;
                            }
                        }
                        if (orientation == null) {
                            return InteractionResult.FAIL;
                        }
                    }
                    level.setBlockAndUpdate(pos, RRBlocks.nuclearBomb.get().defaultBlockState().setValue(BlockNuclearBomb.FACING, orientation));
					return InteractionResult.SUCCESS;
				}
				else if (!level.isClientSide())
				{
                    player.sendSystemMessage(Translations.use(RRItems.pliers.toStack().getItemName()));
				}
			}
			else if (!level.isClientSide())
			{
				player.sendSystemMessage(Translations.orders().append(" ").append(Translations.USE_PLIERS_TO_BUILD_TRANSLATION.translate().withStyle(ChatFormatting.RED)));
			}
		}
		return InteractionResult.FAIL;
	}

    private static void setBlock(Level world, BlockPos pos, Block block) {
        world.setBlockAndUpdate(pos, block.defaultBlockState());
    }

    private static void setBlock(Level world, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos, state);
    }

    private static BlockState getBlockState(Level world, BlockPos pos) {
        return world.getBlockState(pos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNukeCrate(pos, state);
	}
}

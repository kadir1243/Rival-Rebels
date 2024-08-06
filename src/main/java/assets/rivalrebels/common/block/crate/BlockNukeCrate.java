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
package assets.rivalrebels.common.block.crate;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.*;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import assets.rivalrebels.common.util.Translations;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockNukeCrate extends BaseEntityBlock {
    public static final MapCodec<BlockNukeCrate> CODEC = simpleCodec(BlockNukeCrate::new);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
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
		if (this == RRBlocks.nukeCrateTop) {
            for (Direction facing : Direction.values()) {
                BlockPos offset = pos.relative(facing);
                if (world.getBlockState(offset).is(RRBlocks.nukeCrateBottom)) {
                    targetFacing = facing.getOpposite();
                }
            }
		} else if (this == RRBlocks.nukeCrateBottom) {
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
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		world.setBlockAndUpdate(pos, state.setValue(FACING, determineOrientation(world, pos)));
	}

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        world.setBlockAndUpdate(pos, state.setValue(FACING, determineOrientation(world, pos)));

        for (Direction facing : Direction.values()) {
            BlockPos offset = pos.relative(facing);
            BlockState offsetState = world.getBlockState(offset);
            if (offsetState.is(RRBlocks.nukeCrateBottom)) {
                neighborChanged(state, world, pos, this, offset, true);
            } else if (offsetState.getFluidState().is(FluidTags.LAVA)) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                world.explode(null, x, y, z, 3, Level.ExplosionInteraction.NONE);
            }
        }
	}

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (this == RRBlocks.nukeCrateTop) {
			if (!stack.isEmpty()) {
				if (stack.is(RRItems.pliers)) {
					Direction orientation = null;
					if (	getBlock(level, pos.east()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().east()) == RRBlocks.nukeCrateBottom) {
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.east(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().east(), RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.WEST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.west()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().west()) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.west(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().west(), RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.EAST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.south()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().south()) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.south(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().south(), RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.NORTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.north()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().north()) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.north(), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().north(), RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.SOUTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					if (	getBlock(level, pos.east()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().east()) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().east(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.east(), RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.WEST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.west()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().west()) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().west(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.west(), RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.EAST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.south()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().south()) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().south(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.south(), RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.NORTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.north()) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().north()) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.above().north(), Blocks.AIR);
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.north(), RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.SOUTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.east()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.east(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.east(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().east()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().east(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below().east(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.east(), Blocks.AIR);
						setBlock(level, pos.east(2), Blocks.AIR);
						setBlock(level, pos.east(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().east(), RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.WEST));
						setBlock(level, pos.below().east(2), Blocks.AIR);
						setBlock(level, pos.below().east(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.west()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.west(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.west(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().west()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().west(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below().west(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.west(), Blocks.AIR);
						setBlock(level, pos.west(2), Blocks.AIR);
						setBlock(level, pos.west(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().west(), RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.EAST));
						setBlock(level, pos.below().west(2), Blocks.AIR);
						setBlock(level, pos.below().west(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.south()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.south(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.south(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().south()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().south(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below().south(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.south(), Blocks.AIR);
						setBlock(level, pos.south(2), Blocks.AIR);
						setBlock(level, pos.south(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().south(), RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.NORTH));
                        setBlock(level, pos.below().south(2), Blocks.AIR);
						setBlock(level, pos.below().south(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.north()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.north(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.north(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().north()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.below().north(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.below().north(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.north(), Blocks.AIR);
						setBlock(level, pos.north(2), Blocks.AIR);
						setBlock(level, pos.north(3), Blocks.AIR);
						setBlock(level, pos.below(), Blocks.AIR);
						setBlock(level, pos.below().north(), RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.SOUTH));
						setBlock(level, pos.below().north(2), Blocks.AIR);
						setBlock(level, pos.below().north(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.east()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.east(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.east(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().east()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().east(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above().east(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().east(), Blocks.AIR);
						setBlock(level, pos.east(2), Blocks.AIR);
						setBlock(level, pos.east(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.east(), RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.WEST));
						setBlock(level, pos.above().east(2), Blocks.AIR);
						setBlock(level, pos.above().east(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.west()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.west(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.west(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().west()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().west(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above().west(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().west(), Blocks.AIR);
						setBlock(level, pos.west(2), Blocks.AIR);
						setBlock(level, pos.west(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.west(), RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.EAST));
						setBlock(level, pos.above().west(2), Blocks.AIR);
						setBlock(level, pos.above().west(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.south()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.south(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.south(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().south()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().south(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above().south(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().south(), Blocks.AIR);
						setBlock(level, pos.south(2), Blocks.AIR);
						setBlock(level, pos.south(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.south(), RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.NORTH));
						setBlock(level, pos.above().south(2), Blocks.AIR);
						setBlock(level, pos.above().south(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, pos.north()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.north(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.north(3)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().north()) == RRBlocks.nukeCrateTop &&
							getBlock(level, pos.above().north(2)) == RRBlocks.nukeCrateBottom &&
							getBlock(level, pos.above().north(3)) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, pos, Blocks.AIR);
						setBlock(level, pos.above().north(), Blocks.AIR);
						setBlock(level, pos.north(2), Blocks.AIR);
						setBlock(level, pos.north(3), Blocks.AIR);
						setBlock(level, pos.above(), Blocks.AIR);
						setBlock(level, pos.north(), RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.SOUTH));
						setBlock(level, pos.above().north(2), Blocks.AIR);
						setBlock(level, pos.above().north(3), Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					} else {
                        for (Direction direction : Direction.values()) {
                            if (level.getBlockState(pos.relative(direction.getOpposite())).is(RRBlocks.nukeCrateBottom)) {
                                level.setBlockAndUpdate(pos.relative(direction.getOpposite()), Blocks.AIR.defaultBlockState());
                                orientation = direction;
                                break;
                            }
                        }
                        if (orientation == null) {
                            return ItemInteractionResult.FAIL;
                        }
                    }
                    level.setBlockAndUpdate(pos, RRBlocks.nuclearBomb.defaultBlockState().setValue(BlockNuclearBomb.FACING, orientation));
					return ItemInteractionResult.sidedSuccess(level.isClientSide());
				}
				else if (!level.isClientSide())
				{
                    player.displayClientMessage(Translations.orders().append(" ").append(Component.translatable("RivalRebels.message.use")).append(" ").append(RRItems.pliers.getDescription()), false);
				}
			}
			else if (!level.isClientSide())
			{
				player.displayClientMessage(Translations.orders().append(" ").append(Component.translatable(Translations.USE_PLIERS_TO_BUILD_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.RED)), false);
			}
		}
		return ItemInteractionResult.FAIL;
	}

    private static void setBlock(Level world, BlockPos pos, Block block) {
        world.setBlockAndUpdate(pos, block.defaultBlockState());
    }

    private static void setBlock(Level world, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos, state);
    }

    private static Block getBlock(Level world, BlockPos pos) {
        return world.getBlockState(pos).getBlock();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNukeCrate(pos, state);
	}
}

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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.autobuilds.BlockAutoTemplate;
import assets.rivalrebels.common.block.trap.*;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
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
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (this == RRBlocks.nukeCrateTop)
		{
			if (!stack.isEmpty())
			{
				if (stack.is(RRItems.pliers))
				{
					Direction orientation = null;
					if (	getBlock(level, x + 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 1, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x + 1, y, z, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x + 1, y - 1, z, RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.WEST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x - 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 1, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x - 1, y, z, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x - 1, y - 1, z, RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.EAST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z + 1) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y - 1, z + 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y, z + 1, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x, y - 1, z + 1, RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.NORTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z - 1) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y - 1, z - 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y, z - 1, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x, y - 1, z - 1, RRBlocks.antimatterbombblock.defaultBlockState().setValue(BlockAntimatterBomb.FACING, Direction.SOUTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					if (	getBlock(level, x + 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 1, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x + 1, y + 1, z, Blocks.AIR);
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x + 1, y, z, RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.WEST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x - 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 1, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x - 1, y + 1, z, Blocks.AIR);
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x - 1, y, z, RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.EAST));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z + 1) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y + 1, z + 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x, y + 1, z + 1, Blocks.AIR);
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y, z + 1, RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.NORTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z - 1) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y + 1, z - 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x, y + 1, z - 1, Blocks.AIR);
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y, z - 1, RRBlocks.tachyonbombblock.defaultBlockState().setValue(BlockTachyonBomb.FACING, Direction.SOUTH));
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x + 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x + 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 1, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 2, y - 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x + 3, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x + 1, y, z, Blocks.AIR);
						setBlock(level, x + 2, y, z, Blocks.AIR);
						setBlock(level, x + 3, y, z, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x + 1, y - 1, z, RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.WEST));
						setBlock(level, x + 2, y - 1, z, Blocks.AIR);
						setBlock(level, x + 3, y - 1, z, Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x - 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x - 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 1, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 2, y - 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x - 3, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x - 1, y, z, Blocks.AIR);
						setBlock(level, x - 2, y, z, Blocks.AIR);
						setBlock(level, x - 3, y, z, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x - 1, y - 1, z, RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.EAST));
						setBlock(level, x - 2, y - 1, z, Blocks.AIR);
						setBlock(level, x - 3, y - 1, z, Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y, z + 3) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y - 1, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y - 1, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z + 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y, z + 1, Blocks.AIR);
						setBlock(level, x, y, z + 2, Blocks.AIR);
						setBlock(level, x, y, z + 3, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x, y - 1, z + 1, RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.NORTH));
                        setBlock(level, x, y - 1, z + 2, Blocks.AIR);
						setBlock(level, x, y - 1, z + 3, Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y, z - 3) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y - 1, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y - 1, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y - 1, z - 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y, z - 1, Blocks.AIR);
						setBlock(level, x, y, z - 2, Blocks.AIR);
						setBlock(level, x, y, z - 3, Blocks.AIR);
						setBlock(level, x, y - 1, z, Blocks.AIR);
						setBlock(level, x, y - 1, z - 1, RRBlocks.tsarbombablock.defaultBlockState().setValue(BlockTsarBomba.FACING, Direction.SOUTH));
						setBlock(level, x, y - 1, z - 2, Blocks.AIR);
						setBlock(level, x, y - 1, z - 3, Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x + 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x + 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 1, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x + 2, y + 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x + 3, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x + 1, y + 1, z, Blocks.AIR);
						setBlock(level, x + 2, y, z, Blocks.AIR);
						setBlock(level, x + 3, y, z, Blocks.AIR);
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x + 1, y, z, RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.WEST));
						setBlock(level, x + 2, y + 1, z, Blocks.AIR);
						setBlock(level, x + 3, y + 1, z, Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x - 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x - 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 1, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x - 2, y + 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x - 3, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x - 1, y + 1, z, Blocks.AIR);
						setBlock(level, x - 2, y, z, Blocks.AIR);
						setBlock(level, x - 3, y, z, Blocks.AIR);
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x - 1, y, z, RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.EAST));
						setBlock(level, x - 2, y + 1, z, Blocks.AIR);
						setBlock(level, x - 3, y + 1, z, Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y, z + 3) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y + 1, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y + 1, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z + 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y + 1, z + 1, Blocks.AIR);
						setBlock(level, x, y, z + 2, Blocks.AIR);
						setBlock(level, x, y, z + 3, Blocks.AIR);
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x, y, z + 1, RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.NORTH));
						setBlock(level, x, y + 1, z + 2, Blocks.AIR);
						setBlock(level, x, y + 1, z + 3, Blocks.AIR);
						return ItemInteractionResult.sidedSuccess(level.isClientSide());
					}
					else if (getBlock(level, x, y, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y, z - 3) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y + 1, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(level, x, y + 1, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(level, x, y + 1, z - 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(level, x, y, z, Blocks.AIR);
						setBlock(level, x, y + 1, z - 1, Blocks.AIR);
						setBlock(level, x, y, z - 2, Blocks.AIR);
						setBlock(level, x, y, z - 3, Blocks.AIR);
						setBlock(level, x, y + 1, z, Blocks.AIR);
						setBlock(level, x, y, z - 1, RRBlocks.theoreticaltsarbombablock.defaultBlockState().setValue(BlockTheoreticalTsarBomba.FACING, Direction.SOUTH));
						setBlock(level, x, y + 1, z - 2, Blocks.AIR);
						setBlock(level, x, y + 1, z - 3, Blocks.AIR);
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
                    level.setBlockAndUpdate(new BlockPos(x, y, z), RRBlocks.nuclearBomb.defaultBlockState().setValue(BlockNuclearBomb.FACING, orientation));
					return ItemInteractionResult.sidedSuccess(level.isClientSide());
				}
				else if (!level.isClientSide())
				{
                    player.displayClientMessage(RRIdentifiers.orders().append(" ").append(Component.translatable("RivalRebels.message.use")).append(" ").append(RRItems.pliers.getDescription()), false);
				}
			}
			else if (!level.isClientSide())
			{
				player.displayClientMessage(RRIdentifiers.orders().append(" ").append(Component.translatable(BlockAutoTemplate.USE_PLIERS_TO_BUILD_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.RED)), false);
			}
		}
		return ItemInteractionResult.FAIL;
	}

    private static void setBlock(Level world, int x, int y, int z, Block block) {
        world.setBlockAndUpdate(new BlockPos(x, y, z), block.defaultBlockState());
    }

    private static void setBlock(Level world, int x, int y, int z, BlockState state) {
        world.setBlockAndUpdate(new BlockPos(x, y, z), state);
    }

    private static Block getBlock(Level world, int x, int y, int z) {
        return world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNukeCrate(pos, state);
	}
}

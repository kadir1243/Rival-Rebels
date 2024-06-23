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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.*;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockNukeCrate extends BlockWithEntity {
    public static final MapCodec<BlockNukeCrate> CODEC = createCodec(BlockNukeCrate::new);
    public static final DirectionProperty DIRECTION = DirectionProperty.of("direction");
	public BlockNukeCrate(Settings settings)
	{
		super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(DIRECTION, Direction.UP));
	}

    @Override
    protected MapCodec<BlockNukeCrate> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION);
    }

    public Direction determineOrientation(World world, BlockPos pos) {
        Direction targetFacing = Direction.UP;
		if (this == RRBlocks.nukeCrateTop) {
            for (Direction facing : Direction.values()) {
                BlockPos offset = pos.offset(facing);
                Block block = world.getBlockState(offset).getBlock();
                if (block == RRBlocks.nukeCrateBottom) {
                    targetFacing = facing.getOpposite();
                }
            }
		} else if (this == RRBlocks.nukeCrateBottom) {
            for (Direction facing : Direction.values()) {
                BlockPos offset = pos.offset(facing);
                Block block = world.getBlockState(offset).getBlock();
                if (block == RRBlocks.nukeCrateTop) {
                    targetFacing = facing;
                }
            }
		}
		return targetFacing;
	}

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		world.setBlockState(pos, state.with(DIRECTION, determineOrientation(world, pos)));
	}

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        world.setBlockState(pos, state.with(DIRECTION, determineOrientation(world, pos)));

        for (Direction facing : Direction.values()) {
            BlockPos offset = pos.offset(facing);
            BlockState offsetState = world.getBlockState(offset);
            if (offsetState.isOf(RRBlocks.nukeCrateBottom)) {
                neighborUpdate(state, world, pos, this, offset, true);
            } else if (offsetState.getFluidState().isIn(FluidTags.LAVA)) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.createExplosion(null, x, y, z, 3, World.ExplosionSourceType.NONE);
            }
        }
	}

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        ItemStack stack = player.getStackInHand(hand);

        if (this == RRBlocks.nukeCrateTop)
		{
			if (!stack.isEmpty())
			{
				if (stack.getItem() == RRItems.pliers)
				{
					int orientation;
					if (	getBlock(world, x + 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 1, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x + 1, y - 1, z, RRBlocks.antimatterbombblock.getDefaultState().with(BlockAntimatterBomb.META, 4));
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x - 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 1, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x - 1, y - 1, z, RRBlocks.antimatterbombblock.getDefaultState().with(BlockAntimatterBomb.META, 5));
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z + 1) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y - 1, z + 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x, y - 1, z + 1, RRBlocks.antimatterbombblock.getDefaultState().with(BlockAntimatterBomb.META, 2));
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z - 1) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y - 1, z - 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x, y - 1, z - 1, RRBlocks.antimatterbombblock.getDefaultState().with(BlockAntimatterBomb.META, 3));
						return ActionResult.success(world.isClient);
					}
					if (	getBlock(world, x + 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 1, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x + 1, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, RRBlocks.tachyonbombblock.getDefaultState().with(BlockTachyonBomb.META, 4));
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x - 1, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 1, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x - 1, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, RRBlocks.tachyonbombblock.getDefaultState().with(BlockTachyonBomb.META, 5));
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z + 1) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y + 1, z + 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y + 1, z + 1, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, RRBlocks.tachyonbombblock.getDefaultState().with(BlockTachyonBomb.META, 2));
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z - 1) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y + 1, z - 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y + 1, z - 1, Blocks.AIR);
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, RRBlocks.tachyonbombblock.getDefaultState().with(BlockTachyonBomb.META, 3));
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x + 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x + 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 1, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 2, y - 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x + 3, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, Blocks.AIR);
						setBlock(world, x + 2, y, z, Blocks.AIR);
						setBlock(world, x + 3, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x + 1, y - 1, z, RRBlocks.tsarbombablock.getDefaultState().with(BlockTsarBomba.META, 4));
						setBlock(world, x + 2, y - 1, z, Blocks.AIR);
						setBlock(world, x + 3, y - 1, z, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x - 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x - 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 1, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 2, y - 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x - 3, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, Blocks.AIR);
						setBlock(world, x - 2, y, z, Blocks.AIR);
						setBlock(world, x - 3, y, z, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x - 1, y - 1, z, RRBlocks.tsarbombablock.getDefaultState().with(BlockTsarBomba.META, 5));
						setBlock(world, x - 2, y - 1, z, Blocks.AIR);
						setBlock(world, x - 3, y - 1, z, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y, z + 3) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y - 1, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y - 1, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z + 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, Blocks.AIR);
						setBlock(world, x, y, z + 2, Blocks.AIR);
						setBlock(world, x, y, z + 3, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x, y - 1, z + 1, RRBlocks.tsarbombablock.getDefaultState().with(BlockTsarBomba.META, 2));
                        setBlock(world, x, y - 1, z + 2, Blocks.AIR);
						setBlock(world, x, y - 1, z + 3, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y, z - 3) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y - 1, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y - 1, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y - 1, z - 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, Blocks.AIR);
						setBlock(world, x, y, z - 2, Blocks.AIR);
						setBlock(world, x, y, z - 3, Blocks.AIR);
						setBlock(world, x, y - 1, z, Blocks.AIR);
						setBlock(world, x, y - 1, z - 1, RRBlocks.tsarbombablock.getDefaultState().with(BlockTsarBomba.META, 3));
						setBlock(world, x, y - 1, z - 2, Blocks.AIR);
						setBlock(world, x, y - 1, z - 3, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x + 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x + 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 1, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x + 2, y + 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x + 3, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x + 1, y + 1, z, Blocks.AIR);
						setBlock(world, x + 2, y, z, Blocks.AIR);
						setBlock(world, x + 3, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x + 1, y, z, RRBlocks.theoreticaltsarbombablock.getDefaultState().with(BlockTheoreticalTsarBomba.META, 4));
						setBlock(world, x + 2, y + 1, z, Blocks.AIR);
						setBlock(world, x + 3, y + 1, z, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x - 1, y, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 2, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x - 3, y, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 1, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x - 2, y + 1, z) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x - 3, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x - 1, y + 1, z, Blocks.AIR);
						setBlock(world, x - 2, y, z, Blocks.AIR);
						setBlock(world, x - 3, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x - 1, y, z, RRBlocks.theoreticaltsarbombablock.getDefaultState().with(BlockTheoreticalTsarBomba.META, 5));
						setBlock(world, x - 2, y + 1, z, Blocks.AIR);
						setBlock(world, x - 3, y + 1, z, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y, z + 3) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y + 1, z + 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y + 1, z + 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z + 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z + 1, Blocks.AIR);
						setBlock(world, x, y, z + 2, Blocks.AIR);
						setBlock(world, x, y, z + 3, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z + 1, RRBlocks.theoreticaltsarbombablock.getDefaultState().with(BlockTheoreticalTsarBomba.META, 2));
						setBlock(world, x, y + 1, z + 2, Blocks.AIR);
						setBlock(world, x, y + 1, z + 3, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y, z - 3) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y + 1, z - 1) == RRBlocks.nukeCrateTop &&
							getBlock(world, x, y + 1, z - 2) == RRBlocks.nukeCrateBottom &&
							getBlock(world, x, y + 1, z - 3) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z, Blocks.AIR);
						setBlock(world, x, y + 1, z - 1, Blocks.AIR);
						setBlock(world, x, y, z - 2, Blocks.AIR);
						setBlock(world, x, y, z - 3, Blocks.AIR);
						setBlock(world, x, y + 1, z, Blocks.AIR);
						setBlock(world, x, y, z - 1, RRBlocks.theoreticaltsarbombablock.getDefaultState().with(BlockTheoreticalTsarBomba.META, 3));
						setBlock(world, x, y + 1, z - 2, Blocks.AIR);
						setBlock(world, x, y + 1, z - 3, Blocks.AIR);
						return ActionResult.success(world.isClient);
					}
					else if (getBlock(world, x, y + 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y + 1, z, Blocks.AIR);
						orientation = 0;
					}
					else if (getBlock(world, x, y - 1, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y - 1, z, Blocks.AIR);
						orientation = 1;
					}
					else if (getBlock(world, x, y, z + 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z + 1, Blocks.AIR);
						orientation = 2;
					}
					else if (getBlock(world, x, y, z - 1) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x, y, z - 1, Blocks.AIR);
						orientation = 3;
					}
					else if (getBlock(world, x + 1, y, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x + 1, y, z, Blocks.AIR);
						orientation = 4;
					}
					else if (getBlock(world, x - 1, y, z) == RRBlocks.nukeCrateBottom)
					{
						setBlock(world, x - 1, y, z, Blocks.AIR);
						orientation = 5;
					}
					else
					{
						return ActionResult.FAIL;
					}
                    world.setBlockState(new BlockPos(x, y, z), RRBlocks.nuclearBomb.getDefaultState().with(BlockNuclearBomb.META, orientation));
					return ActionResult.success(world.isClient);
				}
				else if (!world.isClient)
				{
                    player.sendMessage(Text.translatable("RivalRebels.Orders").append(" ").append(Text.translatable("RivalRebels.message.use")).append(" ").append(RRItems.pliers.getName()), false);
				}
			}
			else if (!world.isClient)
			{
				player.sendMessage(Text.translatable(RivalRebels.MODID + ".use_pliars", RRItems.pliers.getName()), false);
			}
		}
		return ActionResult.PASS;
	}

    private static void setBlock(World world, int x, int y, int z, Block block) {
        world.setBlockState(new BlockPos(x, y, z), block.getDefaultState());
    }

    private static void setBlock(World world, int x, int y, int z, BlockState state) {
        world.setBlockState(new BlockPos(x, y, z), state);
    }

    private static Block getBlock(World world, int x, int y, int z) {
        return world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNukeCrate(pos, state);
	}
}

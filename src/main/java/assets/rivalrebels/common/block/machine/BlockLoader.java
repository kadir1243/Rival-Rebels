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
package assets.rivalrebels.common.block.machine;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.tileentity.Tickable;
import assets.rivalrebels.common.tileentity.TileEntityLoader;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockLoader extends BaseEntityBlock {
    public static final MapCodec<BlockLoader> CODEC = simpleCodec(BlockLoader::new);
    public static final IntegerProperty META = IntegerProperty.create("meta", 0, 15);
    public BlockLoader(Properties settings) {
		super(settings);
	}

    @Override
    protected MapCodec<BlockLoader> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        int l = Mth.floor((ctx.getRotation() * 4.0F / 360.0F) + 0.5D) & 3;

        int meta = 0;
        if (l == 0) {
            meta = 2;
        } else if (l == 1) {
            meta = 5;
        } else if (l == 2) {
            meta = 3;
        } else if (l == 3) {
            meta = 4;
        }
        return super.getStateForPlacement(ctx).setValue(META, meta);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        TileEntityLoader var7 = null;
        try {
            var7 = (TileEntityLoader) world.getBlockEntity(pos);
        } catch (Exception e) {
            // no error message ;]
        }

        int x = pos.getX();
        int y = pos.getY();
        int z =pos.getZ();
        world.addFreshEntity(new ItemEntity(world, x, y, z, new ItemStack(RRBlocks.loader)));
        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getContainerSize(); ++var8)
            {
                ItemStack var9 = var7.getItem(var8);

                if (!var9.isEmpty())
                {
                    float var10 = world.random.nextFloat() * 0.8F + 0.1F;
                    float var11 = world.random.nextFloat() * 0.8F + 0.1F;
                    ItemEntity var14;

                    for (float var12 = world.random.nextFloat() * 0.8F + 0.1F; var9.getCount() > 0; world.addFreshEntity(var14))
                    {
                        int var13 = world.random.nextInt(21) + 10;

                        if (var13 > var9.getCount())
                        {
                            var13 = var9.getCount();
                        }

                        var9.shrink(var13);
                        ItemStack copy = var9.copyWithCount(var13);
                        var14 = new ItemEntity(world, (x + var10), (y + var11), (z + var12), copy);
                        float var15 = 0.05F;
                        var14.setDeltaMovement(((float) world.random.nextGaussian() * var15),
                            ((float) world.random.nextGaussian() * var15 + 0.2F),
                            ((float) world.random.nextGaussian() * var15));
                    }
                }
            }
            var7.setRemoved();
        }
        super.playerWillDestroy(world, pos, state, player);
        return state;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        player.openMenu((MenuProvider) level.getBlockEntity(pos));

		RivalRebelsSoundPlayer.playSound(level, 10, 3, pos);

		return InteractionResult.sidedSuccess(level.isClientSide);
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityLoader(pos, state);
	}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }
}

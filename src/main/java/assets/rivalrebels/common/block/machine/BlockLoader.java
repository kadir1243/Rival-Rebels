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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockLoader extends BlockWithEntity {
    public static final MapCodec<BlockLoader> CODEC = createCodec(BlockLoader::new);
    public static final IntProperty META = IntProperty.of("meta", 0, 15);
    public BlockLoader(Settings settings) {
		super(settings);
	}

    @Override
    protected MapCodec<BlockLoader> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        int l = MathHelper.floor((ctx.getPlayerYaw() * 4.0F / 360.0F) + 0.5D) & 3;

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
        return super.getPlacementState(ctx).with(META, meta);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntityLoader var7 = null;
        try {
            var7 = (TileEntityLoader) world.getBlockEntity(pos);
        } catch (Exception e) {
            // no error message ;]
        }

        int x = pos.getX();
        int y = pos.getY();
        int z =pos.getZ();
        world.spawnEntity(new ItemEntity(world, x, y, z, new ItemStack(RRBlocks.loader)));
        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.size(); ++var8)
            {
                ItemStack var9 = var7.getStack(var8);

                if (!var9.isEmpty())
                {
                    float var10 = world.random.nextFloat() * 0.8F + 0.1F;
                    float var11 = world.random.nextFloat() * 0.8F + 0.1F;
                    ItemEntity var14;

                    for (float var12 = world.random.nextFloat() * 0.8F + 0.1F; var9.getCount() > 0; world.spawnEntity(var14))
                    {
                        int var13 = world.random.nextInt(21) + 10;

                        if (var13 > var9.getCount())
                        {
                            var13 = var9.getCount();
                        }

                        var9.decrement(var13);
                        ItemStack copy = var9.copyWithCount(var13);
                        var14 = new ItemEntity(world, (x + var10), (y + var11), (z + var12), copy);
                        float var15 = 0.05F;
                        var14.setVelocity(((float) world.random.nextGaussian() * var15),
                            ((float) world.random.nextGaussian() * var15 + 0.2F),
                            ((float) world.random.nextGaussian() * var15));
                    }
                }
            }
            var7.markRemoved();
        }
        super.onBreak(world, pos, state, player);
        return state;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
            player.openHandledScreen((NamedScreenHandlerFactory) world.getBlockEntity(pos));
        }
		RivalRebelsSoundPlayer.playSound(world, 10, 3, pos);

		return ActionResult.success(world.isClient);
	}

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityLoader(pos, state);
	}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }
}

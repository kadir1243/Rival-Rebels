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
package assets.rivalrebels.common.block;

import assets.rivalrebels.common.entity.EntityBlood;
import assets.rivalrebels.common.entity.EntityGoo;
import assets.rivalrebels.common.tileentity.TileEntityGore;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockGore extends BaseEntityBlock {
    public static final MapCodec<BlockGore> CODEC = simpleCodec(BlockGore::new);
    public static final IntegerProperty META = IntegerProperty.create("meta", 0, 5);
	public BlockGore(Properties settings)
	{
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(META, 0));
    }

    @Override
    protected MapCodec<BlockGore> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (player.isCreative()) {
			int meta = state.getValue(META) + 1;
			if (meta >= 6) meta = 0;
			level.setBlockAndUpdate(pos, state.setValue(META, meta));
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.FAIL;
	}

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (world.isEmptyBlock(pos.below()) && state.getValue(META) < 2) world.addFreshEntity(new EntityBlood(world, pos.getX() + random.nextDouble(), pos.getY() + 0.9f, pos.getZ() + random.nextDouble()));
		else if (world.isEmptyBlock(pos.below()) && state.getValue(META) < 4) world.addFreshEntity(new EntityGoo(world, pos.getX() + random.nextDouble(), pos.getY() + 0.9f, pos.getZ() + random.nextDouble()));
	}

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!Block.canSupportCenter(world, pos.below(), Direction.UP) ||
            !Block.canSupportCenter(world, pos.east(), Direction.WEST) ||
            !Block.canSupportCenter(world, pos.west(), Direction.EAST) ||
            !Block.canSupportCenter(world, pos.south(), Direction.NORTH) ||
            !Block.canSupportCenter(world, pos.north(), Direction.SOUTH) ||
            !Block.canSupportCenter(world, pos.above(), Direction.DOWN)) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityGore(pos, state);
	}

	/*@Environment(EnvType.CLIENT)
	IIcon	icon;
	@Environment(EnvType.CLIENT)
	IIcon	icon2;
	@Environment(EnvType.CLIENT)
	IIcon	icon3;
	@Environment(EnvType.CLIENT)
	IIcon	icon4;
	@Environment(EnvType.CLIENT)
	IIcon	icon5;
	@Environment(EnvType.CLIENT)
	IIcon	icon6;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (meta == 0) return icon;
		if (meta == 1) return icon2;
		if (meta == 2) return icon3;
		if (meta == 3) return icon4;
		if (meta == 4) return icon5;
		if (meta == 5) return icon6;
		else
		{
			return icon;
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon = iconregister.registerIcon("RivalRebels:br");
		icon2 = iconregister.registerIcon("RivalRebels:bs");
		icon3 = iconregister.registerIcon("RivalRebels:bt");
		icon4 = iconregister.registerIcon("RivalRebels:bu");
		icon5 = iconregister.registerIcon("RivalRebels:bv");
		icon6 = iconregister.registerIcon("RivalRebels:bw");
	}*/
}

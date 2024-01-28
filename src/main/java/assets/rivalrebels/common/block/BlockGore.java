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
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockGore extends BlockWithEntity {
    public static final IntProperty META = IntProperty.of("meta", 0, 5);
	public BlockGore(Settings settings)
	{
		super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(META, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getAbilities().creativeMode)
		{
			int meta = state.get(META) + 1;
			if (meta >= 6) meta = 0;
			world.setBlockState(pos, state.with(META, meta));
			return ActionResult.success(world.isClient);
		}
		return ActionResult.FAIL;
	}

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isAir(pos.down()) && state.get(META) < 2) world.spawnEntity(new EntityBlood(world, pos.getX() + random.nextDouble(), pos.getY() + 0.9f, pos.getZ() + random.nextDouble()));
		else if (world.isAir(pos.down()) && state.get(META) < 4) world.spawnEntity(new EntityGoo(world, pos.getX() + random.nextDouble(), pos.getY() + 0.9f, pos.getZ() + random.nextDouble()));
	}

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!Block.sideCoversSmallSquare(world, pos.down(), Direction.UP) ||
            !Block.sideCoversSmallSquare(world, pos.east(), Direction.WEST) ||
            !Block.sideCoversSmallSquare(world, pos.west(), Direction.EAST) ||
            !Block.sideCoversSmallSquare(world, pos.south(), Direction.NORTH) ||
            !Block.sideCoversSmallSquare(world, pos.north(), Direction.SOUTH) ||
            !Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN)) {
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityGore(pos, state);
	}

	/*@OnlyIn(Dist.CLIENT)
	IIcon	icon;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon2;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon3;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon4;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon5;
	@OnlyIn(Dist.CLIENT)
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

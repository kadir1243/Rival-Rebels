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
package assets.rivalrebels.common.block.trap;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRoddiskLeader;
import assets.rivalrebels.common.entity.EntityRoddiskOfficer;
import assets.rivalrebels.common.entity.EntityRoddiskRebel;
import assets.rivalrebels.common.entity.EntityRoddiskRegular;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRemoteCharge extends FallingBlock {
    public static final IntProperty META = IntProperty.of("meta", 0, 15);
	public BlockRemoteCharge(Settings settings)
	{
		super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(META, 0));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
		int i = state.get(META);
		float f = 0.0625F;
		float f1 = (1 + i * 2) / 16F;
		float f2 = 0.5F;
		return VoxelShapes.cuboid(new Box(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f));
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		int meta = state.get(META);
		float f = 0.0625F;
		float f1 = (1 + meta * 2) / 16F;
		float f2 = 0.5F;
		return VoxelShapes.cuboid(new Box(x + f1, y, z + f, (x + 1) - f, (y + f2) - f, (z + 1) - f));
	}

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int i = state.get(META);
		float f = 0.0625F;
		float f1 = (1 + i * 2) / 16F;
		float f2 = 0.5F;
		return VoxelShapes.cuboid(new Box(x + f1, y, z + f, (x + 1) - f, y + f2, (z + 1) - f));
	}

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        explode(world, pos);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, net.minecraft.world.explosion.Explosion explosion) {
		explode(world, pos);
	}

	public boolean boom = false;

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		world.createAndScheduleBlockTick(pos, this, 1);
	}

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        for (Direction facing : Direction.values()) {
            if (world.getBlockState(pos.offset(facing)).isIn(BlockTags.FIRE)) {
                explode(world, pos);
            }
        }

		if (boom)
		{
			explode(world, pos);
			boom = false;
		}
		world.createAndScheduleBlockTick(pos, this, 1);
	}

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityRoddiskRegular || entity instanceof EntityRoddiskRebel || entity instanceof EntityRoddiskOfficer || entity instanceof EntityRoddiskLeader) {
			explode(world, pos);
		}
	}

	public static void explode(World world, BlockPos pos)
	{
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        world.setBlockState(pos, Blocks.AIR.getDefaultState());
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RivalRebels.chargeExplodeSize, false, false, RivalRebelsDamageSource.charge);
		RivalRebelsSoundPlayer.playSound(world, 22, 0, x, y, z, 1f, 0.3f);
	}

	/*@OnlyIn(Dist.CLIENT)
	IIcon	icon1;
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

	@OnlyIn(Dist.CLIENT)
	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (side == 0) return icon1;
		if (side == 1) return icon2;
		if (side == 2) return icon3;
		if (side == 3) return icon4;
		if (side == 4) return icon5;
		if (side == 5) return icon6;
		return icon1;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:ag"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:ag"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:af"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:af"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:af"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:af"); // SIDE E
	}*/

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        explode(world, pos);
    }
}

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
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRoddiskLeader;
import assets.rivalrebels.common.entity.EntityRoddiskOfficer;
import assets.rivalrebels.common.entity.EntityRoddiskRebel;
import assets.rivalrebels.common.entity.EntityRoddiskRegular;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

public class BlockLandMine extends FallingBlock
{
    public static final BooleanProperty META = BooleanProperty.of("meta");
	public BlockLandMine(Settings settings)
	{
		super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(META, false));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(META)) {
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			if (!world.isClient) world.createExplosion(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RivalRebels.landmineExplodeSize, Explosion.DestructionType.DESTROY);
		}
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

		float f = 0.01F;
		return VoxelShapes.cuboid(new Box(x, y, z, x + 1, y + 1 - f, z + 1));
	}

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof PlayerEntity || entity instanceof AnimalEntity || entity instanceof MobEntity || entity instanceof EntityRoddiskRegular || entity instanceof EntityRoddiskRebel || entity instanceof EntityRoddiskOfficer || entity instanceof EntityRoddiskLeader) {
			world.setBlockState(pos, state.with(META, true));
			world.createAndScheduleBlockTick(pos, this, 5);
			RivalRebelsSoundPlayer.playSound(world, 11, 1, pos, 3, 2);
		}
	}

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (!world.isClient) world.createExplosion(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RivalRebels.landmineExplodeSize, Explosion.DestructionType.DESTROY);
	}

	/*@Override
	public final IIcon getIcon(IBlockAccess world, int x, int y, int z, int s)
	{
		if (this == RivalRebels.landmine) return Blocks.GRASS.getIcon(world, x, y, z, s);
		Block[] n = new Block[6];
		n[0] = world.getBlock(x + 1, y, z);
		n[1] = world.getBlock(x - 1, y, z);
		n[2] = world.getBlock(x, y + 1, z);
		n[3] = world.getBlock(x, y - 1, z);
		n[4] = world.getBlock(x, y, z + 1);
		n[5] = world.getBlock(x, y, z - 1);

		int popularity1 = 0;
		int popularity2 = 0;
		Block mode = Blocks.GRASS;
		Block array_item = null;
		for (int i = 0; i < 6; i++)
		{
			array_item = n[i];
			if (array_item == null || !array_item.isOpaqueCube() || array_item == RivalRebels.landmine || array_item == RivalRebels.alandmine || array_item == RivalRebels.mario || array_item == RivalRebels.amario || array_item == RivalRebels.quicksand || array_item == RivalRebels.aquicksand) continue;
			for (int j = 0; j < n.length; j++)
			{
				if (array_item == n[j]) popularity1++;
				if (popularity1 >= popularity2)
				{
					mode = array_item;
					popularity2 = popularity1;
				}
			}
			popularity1 = 0;
		}
		return mode.getIcon(world, x, y, z, s);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		Block[] n = new Block[6];
		n[0] = world.getBlock(x + 1, y, z);
		n[1] = world.getBlock(x - 1, y, z);
		n[2] = world.getBlock(x, y + 1, z);
		n[3] = world.getBlock(x, y - 1, z);
		n[4] = world.getBlock(x, y, z + 1);
		n[5] = world.getBlock(x, y, z - 1);

		int popularity1 = 0;
		int popularity2 = 0;
		Block mode = Blocks.GRASS;
		Block array_item;
		for (int i = 0; i < 6; i++)
		{
			array_item = n[i];
			if (array_item == null || !array_item.isOpaqueCube() || array_item == RivalRebels.landmine || array_item == RivalRebels.alandmine || array_item == RivalRebels.mario || array_item == RivalRebels.amario || array_item == RivalRebels.quicksand || array_item == RivalRebels.aquicksand) continue;
            for (Block block : n) {
                if (array_item == block) popularity1++;
                if (popularity1 >= popularity2) {
                    mode = array_item;
                    popularity2 = popularity1;
                }
            }
			popularity1 = 0;
		}
		if (mode == Blocks.GRASS) world.setBlock(x, y, z, RivalRebels.landmine);
	}*/

	/*@Override
	public final IIcon getIcon(int side, int meta)
	{
		return Blocks.GRASS.getIcon(side, meta);
	}*/

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return RRBlocks.alandmine.asItem().getDefaultStack();
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
		if (!world.isClient) world.createExplosion(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RivalRebels.landmineExplodeSize, Explosion.DestructionType.DESTROY);
	}
}

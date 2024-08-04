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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRoddiskLeader;
import assets.rivalrebels.common.entity.EntityRoddiskOfficer;
import assets.rivalrebels.common.entity.EntityRoddiskRebel;
import assets.rivalrebels.common.entity.EntityRoddiskRegular;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiConsumer;

public class BlockLandMine extends FallingBlock
{
    public static final MapCodec<BlockLandMine> CODEC = simpleCodec(BlockLandMine::new);

    public static final BooleanProperty META = BooleanProperty.create("meta");
	public BlockLandMine(Properties settings)
	{
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(META, false));
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (state.getValue(META)) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			if (!world.isClientSide()) world.explode(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RRConfig.SERVER.getLandmineExplodeSize(), Level.ExplosionInteraction.BLOCK);
		}
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		float f = 0.01F;
		return Shapes.create(0, 0, 0, 1, 1 - f, 1);
	}

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (entity instanceof Player || entity instanceof Animal || entity instanceof Mob || entity instanceof EntityRoddiskRegular || entity instanceof EntityRoddiskRebel || entity instanceof EntityRoddiskOfficer || entity instanceof EntityRoddiskLeader) {
			world.setBlockAndUpdate(pos, state.setValue(META, true));
			world.scheduleTick(pos, this, 5);
			RivalRebelsSoundPlayer.playSound(world, 11, 1, pos, 3, 2);
		}
	}

    @Override
    protected void onExplosionHit(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (!level.isClientSide()) level.explode(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RRConfig.SERVER.getLandmineExplodeSize(), Level.ExplosionInteraction.BLOCK);
    }

/*@Override
	public final IIcon getIcon(BlockGetter world, int x, int y, int z, int s)
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
	public void onBlockAdded(Level world, int x, int y, int z)
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
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return RRBlocks.alandmine.asItem().getDefaultInstance();
    }

    @Override
    public void onLand(Level world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
		if (!world.isClientSide()) world.explode(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RRConfig.SERVER.getLandmineExplodeSize(), Level.ExplosionInteraction.BLOCK);
	}
}

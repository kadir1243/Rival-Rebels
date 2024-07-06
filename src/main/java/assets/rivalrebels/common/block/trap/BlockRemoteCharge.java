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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRoddiskLeader;
import assets.rivalrebels.common.entity.EntityRoddiskOfficer;
import assets.rivalrebels.common.entity.EntityRoddiskRebel;
import assets.rivalrebels.common.entity.EntityRoddiskRegular;
import assets.rivalrebels.common.explosion.Explosion;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockRemoteCharge extends FallingBlock {
    public static final MapCodec<BlockRemoteCharge> CODEC = simpleCodec(BlockRemoteCharge::new);
    public static final IntegerProperty META = IntegerProperty.create("meta", 0, 15);
	public BlockRemoteCharge(Properties settings)
	{
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(META, 0));
    }

    @Override
    protected MapCodec<BlockRemoteCharge> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
		int i = state.getValue(META);
		float f = 0.0625F;
		float f1 = (1 + i * 2) / 16F;
		float f2 = 0.5F;
		return Shapes.create(new AABB(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f));
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		int meta = state.getValue(META);
		float f = 0.0625F;
		float f1 = (1 + meta * 2) / 16F;
		float f2 = 0.5F;
		return Shapes.create(new AABB(x + f1, y, z + f, (x + 1) - f, (y + f2) - f, (z + 1) - f));
	}

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int i = state.getValue(META);
		float f = 0.0625F;
		float f1 = (1 + i * 2) / 16F;
		float f2 = 0.5F;
		return Shapes.create(new AABB(x + f1, y, z + f, (x + 1) - f, y + f2, (z + 1) - f));
	}

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        explode(world, pos);
        return state;
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, net.minecraft.world.level.Explosion explosion) {
		explode(world, pos);
	}

	public boolean boom = false;

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		world.scheduleTick(pos, this, 1);
	}

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        for (Direction facing : Direction.values()) {
            if (world.getBlockState(pos.relative(facing)).is(BlockTags.FIRE)) {
                explode(world, pos);
            }
        }

		if (boom)
		{
			explode(world, pos);
			boom = false;
		}
		world.scheduleTick(pos, this, 1);
	}

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityRoddiskRegular || entity instanceof EntityRoddiskRebel || entity instanceof EntityRoddiskOfficer || entity instanceof EntityRoddiskLeader) {
			explode(world, pos);
		}
	}

	public static void explode(Level world, BlockPos pos)
	{
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RRConfig.SERVER.getChargeExplosionSize(), false, false, RivalRebelsDamageSource.charge(world));
		RivalRebelsSoundPlayer.playSound(world, 22, 0, x, y, z, 1f, 0.3f);
	}

    @Override
    public void onLand(Level world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        explode(world, pos);
    }
}

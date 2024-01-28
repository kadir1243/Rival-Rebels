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

import assets.rivalrebels.common.tileentity.TileEntityJumpBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockJump extends Block implements BlockEntityProvider
{
	public BlockJump(Settings settings)
	{
		super(settings);
	}

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.addVelocity(0, 2, 0);
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ARROW_HIT, SoundCategory.BLOCKS, 3F, 2, true);
		}
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        float f = 0.0625F;
		return VoxelShapes.cuboid(new Box(x, y, z, x + 1, y + 1 - f, z + 1));
	}

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(new Box(pos, pos.add(1, 1, 1)));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityJumpBlock(pos, state);
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
		icon1 = iconregister.registerIcon("RivalRebels:ah"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:au"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:at"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:at"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:at"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:at"); // SIDE E
	}*/
}

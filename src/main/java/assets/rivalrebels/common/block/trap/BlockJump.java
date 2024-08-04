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
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockJump extends Block implements EntityBlock
{
	public BlockJump(Properties settings)
	{
		super(settings);
	}

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.push(0, 2, 0);
			world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ARROW_HIT, SoundSource.BLOCKS, 3F, 2, true);
		}
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        float f = 0.0625F;
		return Shapes.create(0, 0, 0, 1, 1 - f, 1);
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityJumpBlock(pos, state);
    }

    /*@Environment(EnvType.CLIENT)
	IIcon	icon1;
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

	@Environment(EnvType.CLIENT)
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

	@Environment(EnvType.CLIENT)
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

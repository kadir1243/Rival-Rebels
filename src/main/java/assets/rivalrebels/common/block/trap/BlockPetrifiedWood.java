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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockPetrifiedWood extends Block {
    public static final IntProperty META = IntProperty.of("meta", 0, 15);
	public BlockPetrifiedWood(Settings settings)
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
		if (player.getAbilities().creativeMode) {
			world.setBlockState(pos, state.with(META, state.get(META) + 1), 3);
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	}

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world.random.nextInt(2) == 0) {
			entity.damage(RivalRebelsDamageSource.radioactivepoisoning, ((16 - world.getBlockState(pos).get(META)) / 2) + world.random.nextInt(3) - 1);
		}
	}

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof PlayerEntity) {
			world.setBlockState(pos, state.with(META, 7));
		}
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
		icon1 = iconregister.registerIcon("RivalRebels:bh"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:bh"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:bg"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:bg"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:bg"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:bg"); // SIDE E
	}*/
}

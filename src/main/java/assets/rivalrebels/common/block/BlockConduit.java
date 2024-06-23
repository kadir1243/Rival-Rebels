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

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class BlockConduit extends Block
{
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 8);
	public BlockConduit(Properties settings)
	{
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(VARIANT, 0));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(VARIANT, ctx.getLevel().getRandom().nextInt(9));
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
	IIcon	icon7;
	@Environment(EnvType.CLIENT)
	IIcon	icon8;
	@Environment(EnvType.CLIENT)
	IIcon	icon9;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (meta == 1) return icon1;
		if (meta == 2) return icon2;
		if (meta == 3) return icon3;
		if (meta == 4) return icon4;
		if (meta == 5) return icon5;
		if (meta == 6) return icon6;
		if (meta == 7) return icon7;
		if (meta == 8) return icon8;
		if (meta == 9) return icon9;
		return icon1;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:co");
		icon2 = iconregister.registerIcon("RivalRebels:cp");
		icon3 = iconregister.registerIcon("RivalRebels:cq");
		icon4 = iconregister.registerIcon("RivalRebels:cr");
		icon5 = iconregister.registerIcon("RivalRebels:cs");
		icon6 = iconregister.registerIcon("RivalRebels:ct");
		icon7 = iconregister.registerIcon("RivalRebels:cu");
		icon8 = iconregister.registerIcon("RivalRebels:cv");
		icon9 = iconregister.registerIcon("RivalRebels:cw");
	}*/
}

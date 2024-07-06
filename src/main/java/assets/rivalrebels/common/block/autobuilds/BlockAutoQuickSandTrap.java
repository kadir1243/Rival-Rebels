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
package assets.rivalrebels.common.block.autobuilds;

import assets.rivalrebels.common.block.RRBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BlockAutoQuickSandTrap extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoQuickSandTrap> CODEC = simpleCodec(BlockAutoQuickSandTrap::new);

    public BlockAutoQuickSandTrap(Properties settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoQuickSandTrap> codec() {
        return CODEC;
    }

    @Override
	public void build(Level world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClientSide())
		{
			placeBlockCarefully(world, x, y, z, Blocks.AIR);
			int r = 2;
			for (int z1 = -r; z1 <= r; z1++)
			{
				for (int x1 = -r; x1 <= r; x1++)
				{
					placeBlockCarefully(world, x + x1, y - 1, z + z1, RRBlocks.aquicksand);
					placeBlockCarefully(world, x + x1, y - 2, z + z1, Blocks.AIR);
					placeBlockCarefully(world, x + x1, y - 3, z + z1, Blocks.AIR);
				}
			}
		}
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
		icon1 = iconregister.registerIcon("RivalRebels:dh"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:dh"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:dg"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:dg"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:dg"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:dg"); // SIDE E
	}*/
}

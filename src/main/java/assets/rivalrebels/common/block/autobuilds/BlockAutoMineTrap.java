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
import net.minecraft.block.Blocks;
import net.minecraft.world.World;

public class BlockAutoMineTrap extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoMineTrap> CODEC = createCodec(BlockAutoMineTrap::new);

    public BlockAutoMineTrap(Settings settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoMineTrap> getCodec() {
        return CODEC;
    }

    @Override
	public void build(World world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClient)
		{
			placeBlockCarefully(world, x, y, z, Blocks.AIR);
			int r = 2;
			for (int z1 = -r; z1 <= r; z1++)
			{
				for (int x1 = -r; x1 <= r; x1++)
				{
					placeBlockCarefully(world, x + x1, y - 1, z + z1, RRBlocks.alandmine);
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
		icon3 = iconregister.registerIcon("RivalRebels:df"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:df"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:df"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:df"); // SIDE E
	}*/
}

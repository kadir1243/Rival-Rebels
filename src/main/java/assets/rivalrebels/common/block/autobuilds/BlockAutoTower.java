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

public class BlockAutoTower extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoTower> CODEC = simpleCodec(BlockAutoTower::new);

    public BlockAutoTower(Properties settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoTower> codec() {
        return CODEC;
    }

    @Override
	public void build(Level world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		for (int y1 = 0; y1 <= 16; y1++)
		{
			placeBlockCarefully(world, x + 1, y + y1, z, RRBlocks.steel);
			placeBlockCarefully(world, x - 1, y + y1, z, RRBlocks.steel);
			placeBlockCarefully(world, x, y + y1, z + 1, RRBlocks.steel);
			placeBlockCarefully(world, x, y + y1, z - 1, RRBlocks.steel);
			placeBlockCarefully(world, x - 1, y + y1, z + 1, RRBlocks.steel);
			placeBlockCarefully(world, x + 1, y + y1, z - 1, RRBlocks.steel);
			placeBlockCarefully(world, x + 1, y + y1, z + 1, RRBlocks.steel);
			placeBlockCarefully(world, x - 1, y + y1, z - 1, RRBlocks.steel);
			placeBlockCarefully(world, x, y + y1, z, Blocks.AIR);

			if (y1 <= 1)
			{
				placeBlockCarefully(world, x + 1, y + y1, z, Blocks.AIR);
				placeBlockCarefully(world, x - 1, y + y1, z, Blocks.AIR);
				placeBlockCarefully(world, x, y + y1, z + 1, Blocks.AIR);
				placeBlockCarefully(world, x, y + y1, z - 1, Blocks.AIR);
			}
			else if (y1 == 8)
			{
				placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.NETHERRACK);
				placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.NETHERRACK);
				placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.NETHERRACK);
				placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.NETHERRACK);
			}
			else if (y1 == 11)
			{
				placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.NETHERRACK);
				placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.NETHERRACK);
				placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.NETHERRACK);
				placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.NETHERRACK);
			}
			else if (y1 == 9)
			{
				placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.AIR);
				placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.AIR);
				placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.AIR);
				placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.AIR);
			}
			else if (y1 == 10)
			{
				placeBlockCarefully(world, x - 1, y + y1, z, Blocks.AIR);
				placeBlockCarefully(world, x + 1, y + y1, z, Blocks.AIR);
				placeBlockCarefully(world, x, y + y1, z + 1, Blocks.AIR);
				placeBlockCarefully(world, x, y + y1, z - 1, Blocks.AIR);
			}
			else if (y1 == 12)
			{
				placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.AIR);
				placeBlockCarefully(world, x + 1, y + y1, z - 1, Blocks.AIR);
				placeBlockCarefully(world, x + 1, y + y1, z + 1, Blocks.AIR);
				placeBlockCarefully(world, x - 1, y + y1, z - 1, Blocks.AIR);
			}
			else if (y1 == 16)
			{
				placeBlockCarefully(world, x - 1, y + y1, z + 1, Blocks.OAK_LOG);
				placeBlockCarefully(world, x - 1, y + y1 + 1, z + 1, Blocks.REDSTONE_TORCH);
				placeBlockCarefully(world, x + 1, y + y1 + 1, z - 1, Blocks.REDSTONE_TORCH);
				placeBlockCarefully(world, x + 1, y + y1 + 1, z + 1, Blocks.REDSTONE_TORCH);
				placeBlockCarefully(world, x - 1, y + y1 + 1, z - 1, Blocks.REDSTONE_TORCH);
			}
		}
		placeBlockCarefully(world, x, y - 1, z, RRBlocks.jump);
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
		icon2 = iconregister.registerIcon("RivalRebels:ai"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:cy"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:cy"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:cy"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:cy"); // SIDE E
	}*/
}

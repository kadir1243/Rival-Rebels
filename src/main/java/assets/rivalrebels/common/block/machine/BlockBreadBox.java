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
package assets.rivalrebels.common.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBreadBox extends Block
{
	public BlockBreadBox(Settings settings)
	{
		super(settings);
	}

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (!player.isSneaking())
        {
            ItemEntity ei = new ItemEntity(world, x + .5, y + 1, z + .5, Items.BREAD.getDefaultStack());
            if (!world.isClient)
            {
                world.spawnEntity(ei);
                if (world.random.nextInt(64) == 0) player.sendMessage(Text.of("§7[§4Orders§7] §cShift-click (Sneak) to pack up toaster."), false);
            }
        }
        else
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, this.asItem().getDefaultStack());
            if (!world.isClient)
            {
                world.spawnEntity(ei);
            }
        }
        return ActionResult.success(world.isClient);
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
		icon1 = iconregister.registerIcon("RivalRebels:ca"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:cc"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:cb"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:ca"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:ca"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:ca"); // SIDE E
	}*/
}

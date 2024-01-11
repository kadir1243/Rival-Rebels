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
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockBreadBox extends Block
{
	public BlockBreadBox()
	{
		super(Material.IRON);
	}

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (!player.isSneaking())
        {
            EntityItem ei = new EntityItem(world, x + .5, y + 1, z + .5, Items.BREAD.getDefaultInstance());
            if (!world.isRemote)
            {
                world.spawnEntity(ei);
                if (world.rand.nextInt(64) == 0) player.sendMessage(new TextComponentString("§7[§4Orders§7] §cShift-click (Sneak) to pack up toaster."));
            }
        }
        else
        {
            world.setBlockToAir(pos);
            EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(this));
            if (!world.isRemote)
            {
                world.spawnEntity(ei);
            }
        }
    }

    /*@SideOnly(Side.CLIENT)
	IIcon	icon1;
	@SideOnly(Side.CLIENT)
	IIcon	icon2;
	@SideOnly(Side.CLIENT)
	IIcon	icon3;
	@SideOnly(Side.CLIENT)
	IIcon	icon4;
	@SideOnly(Side.CLIENT)
	IIcon	icon5;
	@SideOnly(Side.CLIENT)
	IIcon	icon6;

	@SideOnly(Side.CLIENT)
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

	@SideOnly(Side.CLIENT)
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

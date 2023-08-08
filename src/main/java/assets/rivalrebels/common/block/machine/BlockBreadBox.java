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
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockBreadBox extends Block {
	public BlockBreadBox()
	{
		super(Material.iron);
	}

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        blockActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if (!player.isSneaking()) {
			EntityItem ei = new EntityItem(world, x + .5, y + 1, z + .5, new ItemStack(Items.bread, 1));
            if (!world.isRemote) {
				world.spawnEntityInWorld(ei);
				if (world.rand.nextInt(64) == 0) player.addChatMessage(new ChatComponentText("§7[§4Orders§7] §cShift-click (Sneak) to pack up toaster."));
			}
		} else {
			world.setBlockToAir(new BlockPos(x, y, z));
			EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(this));
			if (!world.isRemote) {
				world.spawnEntityInWorld(ei);
			}
		}
		return true;
	}
}

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
package assets.rivalrebels.common.item;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityLaptop;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemLaptop extends Item
{
	public ItemLaptop()
	{
		super();
		maxStackSize = 4;
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			player.addChatMessage(new ChatComponentText("Out of service"));
			Block var11 = world.getBlockState(pos).getBlock();
            pos = pos.add(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
			double var12 = 0.0D;

			if (side == EnumFacing.UP && var11.getRenderType() == 11)
			{
				var12 = 0.5D;
			}

			EntityLaptop laptop = new EntityLaptop(world);

			laptop.setLocationAndAngles(pos.getX() + 0.5f, (float) (pos.getY() + var12), pos.getZ() + 0.5f, player.rotationYaw, 0f);

			world.spawnEntityInWorld(laptop);
            if (!player.capabilities.isCreativeMode) {
                --stack.stackSize;
            }
        }
        return true;
    }

	@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:aj");
	}
}

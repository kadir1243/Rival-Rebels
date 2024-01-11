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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemLaptop extends Item
{
	public ItemLaptop()
	{
		super();
		setMaxStackSize(4);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
		{
			player.sendMessage(new TextComponentString("Out of service"));
			IBlockState var11 = world.getBlockState(pos);
            pos = pos.offset(facing);
			double var12 = 0.0D;

			if (facing == EnumFacing.UP && var11.getRenderType().ordinal() == 11)
			{
				var12 = 0.5D;
			}

			EntityLaptop laptop = new EntityLaptop(world);

			laptop.setLocationAndAngles(pos.getX() + 0.5f, (float) (pos.getY() + var12), pos.getZ() + 0.5f, player.rotationYaw, 0f);

			world.spawnEntity(laptop);
            player.getHeldItem(hand).shrink(1);
            return EnumActionResult.PASS;
        }
        return EnumActionResult.SUCCESS;
    }

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:aj");
	}*/
}

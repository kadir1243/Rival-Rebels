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
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemTrollHelmet extends ItemArmor
{
	public ItemTrollHelmet() {
		super(EnumHelper.addArmorMaterial("Troll", "rivalrebels:textures/armors/o.png", 5000, new int[] { 0, 0, 0, 0 }, 1000), 0, 0);
		setCreativeTab(RivalRebels.rrarmortab);
		setMaxDamage(5000);
		maxStackSize = 64;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bf");
	}*/

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlockState(pos).getBlock();

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (block == Blocks.snow_layer && (world.getBlockState(pos).getValue(BlockSnow.LAYERS) & 7) < 1)
        {
            side = EnumFacing.UP;
        }
        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, pos)) {
            pos = pos.add(side.getDirectionVec());
        }

        if (stack.stackSize == 0)
        {
            return false;
        }
        else if (!player.canPlayerEdit(pos, side, stack))
        {
            return false;
        }
        else if (world.canBlockBePlaced(RivalRebels.flag2, pos, false, side, player, stack))
        {
            int i1 = this.getMetadata(stack.getItemDamage());
            IBlockState j1 = RivalRebels.flag2.onBlockPlaced(world, pos, side, hitX, hitY, hitZ, i1, player);
            world.setBlockState(pos, j1);
            world.playSoundEffect((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, RivalRebels.flag2.stepSound.getPlaceSound(), (RivalRebels.flag2.stepSound.getVolume() + 1.0F) / 2.0F, RivalRebels.flag2.stepSound.getFrequency() * 0.8F);
            if (!player.capabilities.isCreativeMode) {
                --stack.stackSize;
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}

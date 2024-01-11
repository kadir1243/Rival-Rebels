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
package assets.rivalrebels.common.item.weapon;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityCuchillo;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.HashSet;

public class ItemCuchillo extends ItemTool
{
	public ItemCuchillo()
	{
		super(1, 1, ToolMaterial.IRON, new HashSet<>());
		setMaxStackSize(5);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	@Override
	public boolean isFull3D()
	{
		return false;
	}

    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        if (!(entityLiving instanceof EntityPlayer player)) return;

        ItemStack itemStack = ItemUtil.getItemStack(player, RivalRebels.knife);
        if (player.capabilities.isCreativeMode || !itemStack.isEmpty())
		{
			float f = (getMaxItemUseDuration(stack) - timeLeft) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f < 0.1D) return;
			if (f > 1.0F) f = 1.0F;
			if (!player.capabilities.isCreativeMode) stack.shrink(1);
			RivalRebelsSoundPlayer.playSound(player, 4, 3);
			if (!world.isRemote) world.spawnEntity(new EntityCuchillo(world, player, 0.5f + f));
		}
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        return super.onItemRightClick(world, player, hand);
    }

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ad");
	}*/
}

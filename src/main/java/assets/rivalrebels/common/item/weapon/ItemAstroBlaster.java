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
import assets.rivalrebels.common.entity.EntityLaserBurst;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.HashSet;

public class ItemAstroBlaster extends ItemTool
{
	boolean	isA	= true;

	public ItemAstroBlaster()
	{
		super(1, 1, ToolMaterial.DIAMOND, new HashSet<>());
		setMaxStackSize(1);
		setCreativeTab(RivalRebels.rralltab);
	}

	@Override
	public int getItemEnchantability()
	{
		return 100;
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 2000;
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        ItemStack itemStack = ItemUtil.getItemStack(player, RivalRebels.redrod);
        if (player.capabilities.isCreativeMode || !itemStack.isEmpty() || RivalRebels.infiniteAmmo)
		{
			if (player.world.isRemote) stack.setRepairCost(1);
			player.setActiveHand(hand);
			RivalRebelsSoundPlayer.playSound(player, 12, 0, 0.7f, 0.7f);
		}
		else if (!world.isRemote)
		{
			player.sendMessage(new TextComponentString("§cNot enough redstone rods"));
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
        if (!(entityLiving instanceof EntityPlayer player)) return;

        World world = player.world;
        if (count < 1980 && !world.isRemote)
		{

			if (!player.capabilities.isCreativeMode && !RivalRebels.infiniteAmmo)
			{
                ItemStack redrodStack = ItemUtil.getItemStack(player, RivalRebels.redrod);
                if (!redrodStack.isEmpty())
				{
					redrodStack.damageItem(1, player);
					if (redrodStack.getItemDamage() == redrodStack.getMaxDamage())
					{
						redrodStack.shrink(1);
                        if (redrodStack.isEmpty()) {
                            player.inventory.deleteStack(redrodStack);
                        }
						player.inventory.addItemStackToInventory(RivalRebels.emptyrod.getDefaultInstance());
					}
				}
				else
				{
					return;
				}
			}

			if (isA) RivalRebelsSoundPlayer.playSound(player, 2, 2, 0.5f, 0.3f);
			else RivalRebelsSoundPlayer.playSound(player, 2, 3, 0.4f, 1.7f);

			isA = !isA;
			world.spawnEntity(new EntityLaserBurst(world, player, stack.isItemEnchanted()));
		}
		else if (world.isRemote)
		{
			stack.setRepairCost((2000 - count) + 1);
		}
	}

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		if (world.isRemote) stack.setRepairCost(0);
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ab");
	}*/
}

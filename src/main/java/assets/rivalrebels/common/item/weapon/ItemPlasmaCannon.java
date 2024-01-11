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
import assets.rivalrebels.common.entity.EntityPlasmoid;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.Entity;
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

public class ItemPlasmaCannon extends ItemTool
{
	public ItemPlasmaCannon()
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
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 64;
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack hydrodStack = ItemUtil.getItemStack(player, RivalRebels.hydrod);
        if (player.capabilities.isCreativeMode || !hydrodStack.isEmpty() || RivalRebels.infiniteAmmo) {
			player.setActiveHand(hand);
			if (!player.capabilities.isCreativeMode && !RivalRebels.infiniteAmmo) {
				if (!hydrodStack.isEmpty())
				{
					hydrodStack.damageItem(1, player);
					if (hydrodStack.getItemDamage() == hydrodStack.getMaxDamage())
					{
						hydrodStack.shrink(1);
                        if (hydrodStack.isEmpty())
                            player.inventory.deleteStack(hydrodStack);
						player.inventory.addItemStackToInventory(RivalRebels.emptyrod.getDefaultInstance());
					}
					player.setActiveHand(hand);
				}
				else
				{
					return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
				}
			}
			RivalRebelsSoundPlayer.playSound(player, 16, 1, 0.25f);
		}
		else if (!world.isRemote)
		{
			player.sendMessage(new TextComponentString("§cOut of Hydrogen"));
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		if (!world.isRemote)
		{
			float f = (getMaxItemUseDuration(stack) - timeLeft) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f > 1.0F) f = 1.0F;
			f+=0.2f;
			RivalRebelsSoundPlayer.playSound(entityLiving, 16, 2);
			Entity entity = new EntityPlasmoid(world, entityLiving, f+0.5f, stack.isItemEnchanted());
			world.spawnEntity(entity);
		}
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ao");
	}*/
}

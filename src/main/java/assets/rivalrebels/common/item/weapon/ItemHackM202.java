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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityHackB83;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.HashSet;

public class ItemHackM202 extends ItemTool
{
	public ItemHackM202()
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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
		player.setHeldItem(hand, ItemStack.EMPTY);
		if (!world.isRemote)
		{
			world.spawnEntity(new EntityHackB83(world, player.posX, player.posY, player.posZ, -player.rotationYawHead, player.rotationPitch, stack.isItemEnchanted()));
		}
		RivalRebelsSoundPlayer.playSound(player, 23, 2, 0.4f);
		new Explosion(world, player.posX, player.posY, player.posZ, 2, true, false, RivalRebelsDamageSource.flare);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bg");
	}*/
}

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
import assets.rivalrebels.common.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemRodDisk extends Item
{
	public ItemRodDisk() {
		super();
		setMaxStackSize(1);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 300;
	}

	boolean pass = false;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

		if (!pass)
		{
			player.sendMessage(new TextComponentString("Password?"));
			pass = true;
		}
		player.setActiveHand(hand);
		if (RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrrank.id > 1) RivalRebelsSoundPlayer.playSound(player, 6, 2);
		else RivalRebelsSoundPlayer.playSound(player, 7, 2);
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		EntityPlayer player;
        if (entityLiving instanceof EntityPlayer) player = (EntityPlayer) entityLiving;
        else return;
        if (!world.isRemote) {
			if (!player.capabilities.isCreativeMode) player.inventory.deleteStack(stack);
			int rank = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrrank.id;
			//float f = (getMaxItemUseDuration(item) - i) / 20.0F;
			//f = (f * f + f * 2) * 0.33333f;
			//if (f > 1.0F) f = 1.0F;
			//f *= 1.0f-rank*0.1f;
			//f += 0.3f;
			if (rank == 0)
			{
				Entity entity = new EntityRoddiskRegular(world, player, 1);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 7, 3);
			}
			else if (rank == 1)
			{
				Entity entity = new EntityRoddiskRebel(world, player, 1.1f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 7, 3);
			}
			else if (rank == 2)
			{
				Entity entity = new EntityRoddiskOfficer(world, player, 1.2f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 6, 3);
			}
			else if (rank == 3)
			{
				Entity entity = new EntityRoddiskLeader(world, player, 4f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 6, 3);
			}
			else if (rank == 4)
			{
				Entity entity = new EntityRoddiskRep(world, player, 4f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 6, 3);
			}
		}
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:as");
	}*/
}

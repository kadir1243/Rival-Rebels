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
import assets.rivalrebels.common.block.autobuilds.BlockAutoTemplate;
import assets.rivalrebels.common.command.CommandHotPotato;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.TextPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemPliers extends Item
{
	private int	i	= 0;

	public ItemPliers()
	{
		super();
		maxStackSize = 1;
		setContainerItem(this);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.swingItem();
		if (!world.isRemote)
		{
			if (world.getBlockState(pos).getBlock() == RivalRebels.jump && player.capabilities.isCreativeMode)
			{
                CommandHotPotato.pos = pos.add(0, 400, 0);
				CommandHotPotato.world = world;
				PacketDispatcher.packetsys.sendTo(new TextPacket("Hot Potato drop point set. Use /rrhotpotato to start a round."),(EntityPlayerMP) player);
			}
			if (world.getBlockState(pos).getBlock() == RivalRebels.remotecharge)
			{
				int t = 25;
				i = i + 1;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Defuse " + i * 100 / t + "§7'/."),(EntityPlayerMP) player);
				if (i >= t)
				{
					EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(RivalRebels.remotecharge, 1));
					world.spawnEntityInWorld(ei);
					world.setBlockToAir(pos);
					i = 0;
					return true;
				}
			}
			if (world.getBlockState(pos).getBlock() == RivalRebels.timedbomb)
			{
				int t = 25;
				i = i + 1;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Defuse " + i * 100 / t + "§7'/."),(EntityPlayerMP) player);
				if (i >= t)
				{
					EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(RivalRebels.timedbomb, 1));
					world.spawnEntityInWorld(ei);
					world.setBlockToAir(pos);
					world.setBlockToAir(pos.up());
					i = 0;
					return true;
				}
			}
			if (world.getBlockState(pos).getBlock() instanceof BlockAutoTemplate)
			{
                BlockAutoTemplate block = (BlockAutoTemplate) world.getBlockState(pos).getBlock();
                i = i + 1;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Status RivalRebels.building " + i * 100 / block.time + "§7'/."),(EntityPlayerMP) player);
				if (i >= block.time)
				{
					world.setBlockToAir(pos);
					block.build(world, pos.getX(), pos.getY(), pos.getZ());
					i = 0;
					return true;
				}
			}
			if (world.getBlockState(pos).getBlock() == RivalRebels.supplies && world.getBlockState(pos.down()).getBlock() == RivalRebels.supplies)
			{
				i++;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Status RivalRebels.building ToKaMaK " + i * 100 / 15 + "§7'/."),(EntityPlayerMP) player);
				if (i >= 15)
				{
					world.setBlockToAir(pos);
					world.setBlockState(pos.down(), RivalRebels.reactor.getDefaultState());
					i = 0;
					return true;
				}
			}
		}
		return false;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ap");
	}*/
}

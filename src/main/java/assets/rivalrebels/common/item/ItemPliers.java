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
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPliers extends Item
{
	private int	i = 0;

	public ItemPliers()
	{
		super();
		setMaxStackSize(1);
		setContainerItem(this);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		player.swingArm(hand);
		if (!world.isRemote)
		{
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            Block block = world.getBlockState(pos).getBlock();
            if (block == RivalRebels.jump && player.capabilities.isCreativeMode)
			{
                CommandHotPotato.pos = pos.up(400);
				CommandHotPotato.world = world;
				PacketDispatcher.packetsys.sendTo(new TextPacket("Hot Potato drop point set. Use /rrhotpotato to start a round."),(EntityPlayerMP) player);
			}
			if (block == RivalRebels.remotecharge)
			{
				int t = 25;
				i = i + 1;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Defuse " + i * 100 / t + "§7'/."),(EntityPlayerMP) player);
				if (i >= t)
				{
					EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.remotecharge, 1));
					world.spawnEntity(ei);
					world.setBlockToAir(pos);
					i = 0;
					return EnumActionResult.SUCCESS;
				}
			}
			if (block == RivalRebels.timedbomb)
			{
				int t = 25;
				i = i + 1;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Defuse " + i * 100 / t + "§7'/."),(EntityPlayerMP) player);
				if (i >= t)
				{
					EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.timedbomb, 1));
					world.spawnEntity(ei);
					world.setBlockToAir(pos);
					world.setBlockToAir(pos.up());
					i = 0;
					return EnumActionResult.SUCCESS;
				}
			}
			if (block instanceof BlockAutoTemplate worldBlock)
			{
                i = i + 1;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Status RivalRebels.building " + i * 100 / worldBlock.time + "§7'/."),(EntityPlayerMP) player);
				if (i >= worldBlock.time)
				{
					world.setBlockToAir(pos);
					worldBlock.build(world, x, y, z);
					i = 0;
					return EnumActionResult.SUCCESS;
				}
			}
			if (block == RivalRebels.supplies && world.getBlockState(pos.down()).getBlock() == RivalRebels.supplies)
			{
				i++;
				PacketDispatcher.packetsys.sendTo(new TextPacket("RivalRebels.Status RivalRebels.building ToKaMaK " + i * 100 / 15 + "§7'/."),(EntityPlayerMP) player);
				if (i >= 15)
				{
					world.setBlockToAir(pos);
					world.setBlockState(pos.down(), RivalRebels.reactor.getDefaultState());
					i = 0;
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ap");
	}*/
}

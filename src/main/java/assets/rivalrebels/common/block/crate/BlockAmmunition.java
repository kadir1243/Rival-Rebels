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
package assets.rivalrebels.common.block.crate;

import assets.rivalrebels.RivalRebels;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAmmunition extends Block
{
	public BlockAmmunition()
	{
		super(Material.wood);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        return blockActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if (world.isRemote) {
			player.addChatMessage(new ChatComponentText(I18n.format("RivalRebels.Inventory")));
			player.addChatMessage(new ChatComponentText("§a" + I18n.format(RivalRebels.rocket.getUnlocalizedName() + ".name") + ". §9(" + I18n.format(RivalRebels.rpg.getUnlocalizedName() + ".name") + " " + I18n.format("RivalRebels.ammunition") + ")"));
			player.addChatMessage(new ChatComponentText("§a" + I18n.format(RivalRebels.battery.getUnlocalizedName() + ".name") + ". §9(" + I18n.format(RivalRebels.tesla.getUnlocalizedName() + ".name") + " " + I18n.format("RivalRebels.ammunition") + ")"));
			player.addChatMessage(new ChatComponentText("§a" + I18n.format(RivalRebels.hydrod.getUnlocalizedName() + ".name") + ". §9(" + I18n.format(RivalRebels.plasmacannon.getUnlocalizedName() + ".name") + " " + I18n.format("RivalRebels.ammunition") + ")"));
			player.addChatMessage(new ChatComponentText("§a" + I18n.format(RivalRebels.fuel.getUnlocalizedName() + ".name") + ". §9(" + I18n.format(RivalRebels.flamethrower.getUnlocalizedName() + ".name") + " " + I18n.format("RivalRebels.ammunition") + ")"));
			player.addChatMessage(new ChatComponentText("§a" + I18n.format(RivalRebels.redrod.getUnlocalizedName() + ".name") + ". §9(" + I18n.format(RivalRebels.einsten.getUnlocalizedName() + ".name") + " " + I18n.format("RivalRebels.ammunition") + ")"));
			player.addChatMessage(new ChatComponentText("§a" + I18n.format(RivalRebels.gasgrenade.getUnlocalizedName() + ".name") + ". §9(" + I18n.format("RivalRebels.chemicalweapon") + ")"));
		}
		if (!world.isRemote)
		{
			EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.rocket, 32));
			EntityItem ei1 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.battery, 16));
			EntityItem ei2 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.hydrod, 1));
			EntityItem ei3 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.hydrod, 1));
			EntityItem ei4 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.hydrod, 1));
			EntityItem ei5 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.hydrod, 1));
			EntityItem ei10 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.fuel, 64));
			EntityItem ei11 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.gasgrenade, 6));
			EntityItem ei12 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.redrod, 1));
			EntityItem ei13 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.redrod, 1));
			EntityItem ei14 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.redrod, 1));
			EntityItem ei15 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.redrod, 1));
			world.spawnEntityInWorld(ei);
			world.spawnEntityInWorld(ei1);
			world.spawnEntityInWorld(ei2);
			world.spawnEntityInWorld(ei3);
			world.spawnEntityInWorld(ei4);
			world.spawnEntityInWorld(ei5);
			world.spawnEntityInWorld(ei10);
			world.spawnEntityInWorld(ei11);
			world.spawnEntityInWorld(ei12);
			world.spawnEntityInWorld(ei13);
			world.spawnEntityInWorld(ei14);
			world.spawnEntityInWorld(ei15);
			world.setBlockToAir(new BlockPos(x, y, z));
			if (world.rand.nextInt(3) == 0) {
				world.spawnEntityInWorld(new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.nuclearelement, 1)));
				player.addChatMessage(new ChatComponentText("§a" + StatCollector.translateToLocal(RivalRebels.nuclearelement.getUnlocalizedName() + ".name") + ". §9(" + "Used in nuclear weapons" + ")"));
			}
		}
		return true;
	}
}

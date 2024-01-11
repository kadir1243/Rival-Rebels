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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.Random;

public class BlockExplosives extends Block
{
	public BlockExplosives()
	{
		super(Material.WOOD);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        blockActivated(world, pos.getX(), pos.getY(), pos.getZ(), player);
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if (world.isRemote)
		{
			player.sendMessage(new TextComponentTranslation("RivalRebels.Inventory"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.timedbomb.getTranslationKey() + ".name") + ". §9(" + "1 minute countdown." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.pliers.getTranslationKey() + ".name") + ". §9(" + "to defuse explosives." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.remotecharge.getTranslationKey() + ".name") + ". §9(" + "Remote charge." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.remote.getTranslationKey() + ".name") + ". §9(" + "Set and detonate charge." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.minetrap.getTranslationKey() + ".name") + ". §9(" + "Handle with care." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.flare.getTranslationKey() + ".name") + ". §9(" + "Incendiary defense." + ")"));
		}
		if (!world.isRemote)
		{
			EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.timedbomb, 1));
			EntityItem ei1 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.remotecharge, 8));
			EntityItem ei2 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.minetrap, 16));
			EntityItem ei3 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.flare, 8));
			EntityItem ei4 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.remote));
			EntityItem ei5 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.pliers));
			world.spawnEntity(ei);
			world.spawnEntity(ei1);
			world.spawnEntity(ei2);
			world.spawnEntity(ei3);
			world.spawnEntity(ei4);
			world.spawnEntity(ei5);
			world.setBlockToAir(new BlockPos(x, y, z));
			return true;
		}
		return true;

	}

	/*@SideOnly(Side.CLIENT)
	IIcon	icon1;
	@SideOnly(Side.CLIENT)
	IIcon	icon2;
	@SideOnly(Side.CLIENT)
	IIcon	icon3;
	@SideOnly(Side.CLIENT)
	IIcon	icon4;
	@SideOnly(Side.CLIENT)
	IIcon	icon5;
	@SideOnly(Side.CLIENT)
	IIcon	icon6;

	@SideOnly(Side.CLIENT)
	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (side == 0) return icon1;
		if (side == 1) return icon2;
		if (side == 2) return icon3;
		if (side == 3) return icon4;
		if (side == 4) return icon5;
		if (side == 5) return icon6;
		return icon1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:ah"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:ai"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:am"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:am"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:am"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:am"); // SIDE E
	}*/
}

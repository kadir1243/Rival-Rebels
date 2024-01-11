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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSupplies extends Block
{
	public BlockSupplies()
	{
		super(Material.WOOD);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        blockActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if (world.isRemote)
		{
			player.sendMessage(new TextComponentTranslation("RivalRebels.Inventory"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.armyshovel.getTranslationKey() + ".name") + ". §9(" + "Ideal for special blocks." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.jump.getTranslationKey() + ".name") + ". §9(" + "Use at your own risk." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.quicksand.getTranslationKey() + ".name") + ". §9(" + "Sand that is quick" + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.mario.getTranslationKey() + ".name") + ". §9(" + "For trap making." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.loader.getTranslationKey() + ".name") + ". §9(" + "Modular item container." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.steel.getTranslationKey() + ".name") + ". §9(" + "Climbable and blast resistant." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.expill.getTranslationKey() + ".name") + ". §9(" + "Take at your own risk." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.safepill.getTranslationKey() + ".name") + ". §9(" + "Restores health." + ")"));
			player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.breadbox.getTranslationKey() + ".name") + ". §9(" + "Unlimited toast! You don't say..." + ")"));
		}
		if (!world.isRemote)
		{
			EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.breadbox));
			EntityItem ei1 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.armyshovel));
			EntityItem ei2 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.jump, 4));
			EntityItem ei3 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.quicksandtrap, 4));
			EntityItem ei4 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.steel, 32));
			EntityItem ei5 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.loader, 2));
			EntityItem ei6 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(Items.BUCKET, 2));
			EntityItem ei7 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.mariotrap, 4));
			EntityItem ei8 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.expill, 6));
			EntityItem ei9 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.safepill, 3));
			world.spawnEntity(ei);
			world.spawnEntity(ei1);
			world.spawnEntity(ei2);
			world.spawnEntity(ei3);
			world.spawnEntity(ei4);
			world.spawnEntity(ei5);
			world.spawnEntity(ei6);
			world.spawnEntity(ei7);
			world.spawnEntity(ei8);
			world.spawnEntity(ei9);
			world.setBlockToAir(new BlockPos(x, y, z));
			if (world.rand.nextInt(5) == 0)
			{
				world.spawnEntity(new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.nuclearelement, 1)));
				player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.nuclearelement.getTranslationKey() + ".name") + ". §9" + "(Used in nuclear weapons)"));
			}
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
		icon3 = iconregister.registerIcon("RivalRebels:bz"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:bz"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:bz"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:bz"); // SIDE E
	}*/
}

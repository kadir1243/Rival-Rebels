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

public class BlockWeapons extends Block
{
	public BlockWeapons()
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
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.isRemote) {
            player.sendMessage(new TextComponentTranslation("RivalRebels.Inventory"));
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.rpg.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.consume") + " " + I18n.translateToLocal(RivalRebels.rocket.getTranslationKey() + ".name") + ")"));
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.tesla.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.consume") + " " + I18n.translateToLocal(RivalRebels.hydrod.getTranslationKey() + ".name") + ")"));
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.flamethrower.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.consume") + " " + I18n.translateToLocal(RivalRebels.fuel.getTranslationKey() + ".name") + ")"));
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.plasmacannon.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.consume") + " " + I18n.translateToLocal(RivalRebels.battery.getTranslationKey() + ".name") + ")"));
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.einsten.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.consume") + " " + I18n.translateToLocal(RivalRebels.redrod.getTranslationKey() + ".name") + ")"));
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.roddisk.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.message.use") + " /rr)"));
            // player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.bastion.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translateToLocal("RivalRebels.build") + " " + I18n.translateToLocal(RivalRebels.barricade.getTranslationKey() + ".name") + ")");
            // player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.tower.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translateToLocal("RivalRebels.build") + " " + I18n.translateToLocal(RivalRebels.tower.getTranslationKey() + ".name") + ")");
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.knife.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.opknife") + ")"));
            player.sendMessage(new TextComponentString("§a" + I18n.translateToLocal(RivalRebels.gasgrenade.getTranslationKey() + ".name") + ". §9(" + I18n.translateToLocal("RivalRebels.chemicalweapon") + ")"));
            player.sendMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.Orders") + " " + I18n.translateToLocal("RivalRebels.equipweapons")));
        }
        if (!world.isRemote)
        {
            EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.rpg));
            EntityItem ei1 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.tesla));
            EntityItem ei2 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.plasmacannon));
            EntityItem ei3 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.flamethrower));
            EntityItem ei4 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.roddisk));
            // EntityItem ei5 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.barricade, 6));
            // EntityItem ei6 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.tower, 3));
            EntityItem ei7 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.knife, 10));
            EntityItem ei8 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.gasgrenade, 6));
            EntityItem ei9 = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.einsten));
            world.spawnEntity(ei);
            world.spawnEntity(ei1);
            world.spawnEntity(ei2);
            world.spawnEntity(ei3);
            world.spawnEntity(ei4);
            // world.spawnEntity(ei5);
            // world.spawnEntity(ei6);
            world.spawnEntity(ei7);
            world.spawnEntity(ei8);
            world.spawnEntity(ei9);
            world.setBlockToAir(pos);
        }

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
		icon3 = iconregister.registerIcon("RivalRebels:ce"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:ce"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:ce"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:ce"); // SIDE E
	}*/
}

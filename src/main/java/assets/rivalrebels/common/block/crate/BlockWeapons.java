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

import assets.rivalrebels.common.item.RRItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWeapons extends Block
{
	public BlockWeapons(Settings settings)
	{
		super(settings);
	}

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.isClient) {
            player.sendMessage(new TranslatableText("RivalRebels.Inventory"), false);
            player.sendMessage(new LiteralText("§a" + RRItems.rpg.getName() + ". §9(" + I18n.translate("RivalRebels.consume") + " " + (RRItems.rocket.getName()) + ")"), false);
            player.sendMessage(new LiteralText("§a" + RRItems.tesla.getName() + ". §9(" + I18n.translate("RivalRebels.consume") + " " + (RRItems.hydrod.getName()) + ")"), false);
            player.sendMessage(new LiteralText("§a" + (RRItems.flamethrower.getName()) + ". §9(" + I18n.translate("RivalRebels.consume") + " " + (RRItems.fuel.getName()) + ")"), false);
            player.sendMessage(new LiteralText("§a" + (RRItems.plasmacannon.getName()) + ". §9(" + I18n.translate("RivalRebels.consume") + " " + (RRItems.battery.getName()) + ")"), false);
            player.sendMessage(new LiteralText("§a" + (RRItems.einsten.getName()) + ". §9(" + I18n.translate("RivalRebels.consume") + " " + (RRItems.redrod.getName()) + ")"), false);
            player.sendMessage(new LiteralText("§a" + (RRItems.roddisk.getName()) + ". §9(" + I18n.translate("RivalRebels.message.use") + " /rr)"), false);
            // player.sendMessage(new LiteralText("§a" + I18n.translate(RivalRebels.bastion.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.barricade.getTranslationKey() + ".name") + ")");
            // player.sendMessage(new LiteralText("§a" + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ")");
            player.sendMessage(new LiteralText("§a" + (RRItems.knife.getName()) + ". §9(" + I18n.translate("RivalRebels.opknife") + ")"), false);
            player.sendMessage(new LiteralText("§a" + (RRItems.gasgrenade.getName()) + ". §9(" + I18n.translate("RivalRebels.chemicalweapon") + ")"), false);
            player.sendMessage(new LiteralText(I18n.translate("RivalRebels.Orders") + " " + I18n.translate("RivalRebels.equipweapons")), false);
        }
        if (!world.isClient)
        {
            ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.rpg.getDefaultStack());
            ItemEntity ei1 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.tesla.getDefaultStack());
            ItemEntity ei2 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.plasmacannon.getDefaultStack());
            ItemEntity ei3 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.flamethrower.getDefaultStack());
            ItemEntity ei4 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.roddisk.getDefaultStack());
            // ItemEntity ei5 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.barricade, 6));
            // ItemEntity ei6 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.tower, 3));
            ItemEntity ei7 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.knife, 10));
            ItemEntity ei8 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.gasgrenade, 6));
            ItemEntity ei9 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.einsten.getDefaultStack());
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
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        return ActionResult.PASS;
    }

    /*@OnlyIn(Dist.CLIENT)
	IIcon	icon1;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon2;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon3;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon4;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon5;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon6;

	@OnlyIn(Dist.CLIENT)
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

	@OnlyIn(Dist.CLIENT)
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

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
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockOmegaArmor extends Block
{
	public BlockOmegaArmor(Settings settings)
	{
		super(settings);
	}

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

		if (!world.isClient) {
			player.sendMessage(Text.of("§7[§2Inventory§7]"), false);
			player.sendMessage(Text.of("§aArmor. §9(Omega's color armor.)"), false);
			player.sendMessage(Text.of("§7[§4Orders§7] §cEquipt your set of armor."), false);
			ItemEntity ei7 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.camohat));
			ItemEntity ei8 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.camoshirt));
			ItemEntity ei9 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.camopants));
			ItemEntity ei10 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.camoshoes));
			world.spawnEntity(ei7);
			world.spawnEntity(ei8);
			world.spawnEntity(ei9);
			world.spawnEntity(ei10);
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
		}
		return ActionResult.success(world.isClient);
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
		icon3 = iconregister.registerIcon("RivalRebels:az"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:az"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:ah"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:ah"); // SIDE E
	}*/
}

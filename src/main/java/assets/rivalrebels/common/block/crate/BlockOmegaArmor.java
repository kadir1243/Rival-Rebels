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
import assets.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockOmegaArmor extends Block
{
	public BlockOmegaArmor(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

		if (!level.isClientSide()) {
			player.displayClientMessage(Component.nullToEmpty("§7[§2Inventory§7]"), false);
			player.displayClientMessage(Component.nullToEmpty("§aArmor. §9(Omega's color armor.)"), false);
			player.displayClientMessage(Translations.orders().append(" ").append(Component.literal("Equip your set of armor.").withStyle(ChatFormatting.RED)), false);
			ItemEntity ei7 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.camohat));
			ItemEntity ei8 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.camoshirt));
			ItemEntity ei9 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.camopants));
			ItemEntity ei10 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.camoshoes));
			level.addFreshEntity(ei7);
			level.addFreshEntity(ei8);
			level.addFreshEntity(ei9);
			level.addFreshEntity(ei10);
			level.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState());
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	/*@Environment(EnvType.CLIENT)
	IIcon	icon1;
	@Environment(EnvType.CLIENT)
	IIcon	icon2;
	@Environment(EnvType.CLIENT)
	IIcon	icon3;
	@Environment(EnvType.CLIENT)
	IIcon	icon4;
	@Environment(EnvType.CLIENT)
	IIcon	icon5;
	@Environment(EnvType.CLIENT)
	IIcon	icon6;

	@Environment(EnvType.CLIENT)
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

	@Environment(EnvType.CLIENT)
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

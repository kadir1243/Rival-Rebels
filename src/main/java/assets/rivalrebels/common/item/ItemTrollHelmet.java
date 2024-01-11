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
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import org.jetbrains.annotations.Nullable;

public class ItemTrollHelmet extends ItemArmor
{

    private static final ArmorMaterial TROLL_MATERIAL = EnumHelper.addArmorMaterial("Troll", "", 5000, new int[]{0, 0, 0, 0}, 1000, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);

    public ItemTrollHelmet() {
		super(TROLL_MATERIAL, 0, EntityEquipmentSlot.HEAD);
		setCreativeTab(RivalRebels.rrarmortab);
		setMaxDamage(5000);
	}

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "rivalrebels:textures/armors/o.png";
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bf");
	}*/

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == Blocks.SNOW_LAYER && state.getValue(BlockSnow.LAYERS) < 1) {
            facing = EnumFacing.UP;
        } else if (block != Blocks.VINE && block != Blocks.TALLGRASS && block != Blocks.DEADBUSH && !block.isReplaceable(world, pos)) {
            pos = pos.offset(facing);
        }
        if (stack.isItemEnchanted() || !player.canPlayerEdit(pos, facing, stack)) {
            return EnumActionResult.FAIL;
        }
        if (world.mayPlace(RivalRebels.flag2, pos, false, facing, player)) {
            int meta = this.getMetadata(stack.getItemDamage());
            IBlockState flagState = RivalRebels.flag2.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player, hand);
            world.setBlockState(pos, flagState);
            world.playSound((double)((float) pos.getX() + 0.5F), (double)((float) pos.getY() + 0.5F), (double)((float) pos.getZ() + 0.5F), RivalRebels.flag2.getSoundType().getStepSound(), SoundCategory.PLAYERS, (RivalRebels.flag2.getSoundType().getVolume() + 1.0F) / 2.0F, RivalRebels.flag2.getSoundType().getPitch() * 0.8F, false);
            stack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}

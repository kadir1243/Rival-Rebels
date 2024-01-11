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
package assets.rivalrebels.common.item.weapon;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRaytrace;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;

public class ItemTesla extends ItemTool
{
	public ItemTesla()
	{
		super(1, 1, ToolMaterial.DIAMOND, new HashSet<>());
		setMaxStackSize(1);
		setCreativeTab(RivalRebels.rralltab);
	}

	@Override
	public int getItemEnchantability()
	{
		return 100;
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 20;
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
        int degree = getDegree(stack);
		float chance = Math.abs(degree - 90) / 90f;
        ItemStack battery = ItemUtil.getItemStack(player, RivalRebels.battery);
        if (player.capabilities.isCreativeMode || !battery.isEmpty() || RivalRebels.infiniteAmmo)
		{
			if (!player.capabilities.isCreativeMode && !RivalRebels.infiniteAmmo)
			{
                battery.shrink(1);
				if (chance > 0.33333) battery.shrink(1);
				if (chance > 0.66666) battery.shrink(1);
                if (battery.isEmpty()) {
                    player.inventory.deleteStack(battery);
                }
            }
			player.setActiveHand(hand);
		}
		else if (!world.isRemote)
		{
			player.sendMessage(new TextComponentString("§cOut of batteries"));
		}
		if (message && world.isRemote)
		{
			player.sendMessage(new TextComponentTranslation("RivalRebels.Orders").appendText(" ").appendSibling(new TextComponentTranslation("RivalRebels.message.use")).appendText(" [R]."));
			message = false;
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	boolean message = true;

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (world.isRemote) {
            if ((RivalRebels.altRkey ? Keyboard.isKeyDown(Keyboard.KEY_F) : Keyboard.isKeyDown(Keyboard.KEY_R)) && isSelected && Minecraft.getMinecraft().currentScreen == null) {
                RivalRebels.proxy.teslaGui(getDegree(stack));
            }
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		if (player.isWet() && !player.isEntityInvulnerable(RivalRebelsDamageSource.electricity))
		{
			player.attackEntityFrom(RivalRebelsDamageSource.electricity, 2);
		}
		World world = player.world;
		if (player.world.rand.nextInt(10) == 0) RivalRebelsSoundPlayer.playSound(player, 25, 1);

		int degree = getDegree(stack);
		float chance = Math.abs(degree - 90) / 90f;
		if (degree - 90 > 0) chance /= 10f;

		float dist = 7 + (1 - (degree / 180f)) * 73;

		float randomness = degree / 720f;

		int num = (degree / 25) + 1;

		if (!world.isRemote) for (int i = 0; i < num; i++)
			world.spawnEntity(new EntityRaytrace(world, player, dist, randomness, chance, !stack.isItemEnchanted()));
	}

	public int getDegree(ItemStack item)
	{
		if (!item.hasTagCompound()) return 0;
		else return item.getTagCompound().getInteger("dial") + 90;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ax");
	}*/
}

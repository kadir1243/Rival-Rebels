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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityFlameBall;
import assets.rivalrebels.common.entity.EntityFlameBall1;
import assets.rivalrebels.common.entity.EntityFlameBall2;
import assets.rivalrebels.common.entity.EntityFlameBallGreen;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;

public class ItemFlameThrower extends ItemTool {
	public ItemFlameThrower()
	{
		super(1, 1, ToolMaterial.DIAMOND, new HashSet<>());
		setMaxStackSize(1);
		// entities = new HashMap<String, Entity>();
		setCreativeTab(RivalRebels.rralltab);
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
		return 64;
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        ItemStack itemStack = ItemUtil.getItemStack(player, RivalRebels.fuel);
        if (player.capabilities.isCreativeMode || !itemStack.isEmpty() || RivalRebels.infiniteAmmo)
		{
			player.setActiveHand(hand);
			if (!player.capabilities.isCreativeMode && !RivalRebels.infiniteAmmo)
			{
				itemStack.shrink(1);
				if (getMode(stack) != 2) itemStack.shrink(1);
				if (getMode(stack) != 2) itemStack.shrink(1);
				if (getMode(stack) == 0) itemStack.shrink(1);
				if (getMode(stack) == 0) itemStack.shrink(1);
                if (itemStack.isEmpty())
                    player.inventory.deleteStack(itemStack);
			}
			if (stack.isItemEnchanted() && !world.isRemote)
			{
				world.spawnEntity(new EntityFlameBallGreen(world, player, world.rand.nextFloat() + 1.0f));
			}
		}
		else if (!world.isRemote)
		{
			player.sendMessage(new TextComponentString("§cOut of fuel"));
		}
		if (message && world.isRemote)
		{
			player.sendMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.Orders")+" "+I18n.translateToLocal("RivalRebels.message.use")+" [R]."));
			message = false;
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	boolean message = true;

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
		World world = entityLiving.world;
		if (!world.isRemote)
		{
            if (world.rand.nextInt(10) == 0 && !entityLiving.isInWater()) {
                RivalRebelsSoundPlayer.playSound(entityLiving, 8, 0, 0.03f);
                if (world.rand.nextInt(3) == 0 && !entityLiving.isInWater()) {
                    RivalRebelsSoundPlayer.playSound(entityLiving, 8, 1, 0.1f);
                }
            }
            if (!stack.isItemEnchanted()) {
                switch (getMode(stack)) {
                    case 0:
                        for (int i = 0; i < 4; i++)
                            world.spawnEntity(new EntityFlameBall2(world, entityLiving, world.rand.nextFloat() + 0.5f));
                        break;
                    case 1:
                        world.spawnEntity(new EntityFlameBall1(world, entityLiving, 1));
                        break;
                    case 2:
                        world.spawnEntity(new EntityFlameBall(world, entityLiving, 1));
                        break;
                }
            }
        }
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().getBoolean("isReady"))
		{
			stack.getTagCompound().setBoolean("isReady", true);
			stack.getTagCompound().setInteger("mode", 2);
		}
		if (entity instanceof EntityPlayer player) {
            if (isSelected && world.rand.nextInt(10) == 0 && !player.isInWater()) {
                RivalRebelsSoundPlayer.playSound(player, 8, 0, 0.03f);
            }
		}
        if (world.isRemote) openGui(stack);
	}

	public void openGui(ItemStack item) {
		if ((RivalRebels.altRkey?Keyboard.isKeyDown(Keyboard.KEY_F):Keyboard.isKeyDown(Keyboard.KEY_R)) && Minecraft.getMinecraft().currentScreen == null)
		{
			RivalRebels.proxy.flamethrowerGui(getMode(item));
		}
	}

	public int getMode(ItemStack item)
	{
		if (!item.hasTagCompound()) return 0;
		else return item.getTagCompound().getInteger("mode");
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ae");
	}*/
}

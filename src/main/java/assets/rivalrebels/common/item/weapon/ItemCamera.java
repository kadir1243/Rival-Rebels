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

import assets.rivalrebels.common.item.RRItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static org.lwjgl.glfw.GLFW.*;

public class ItemCamera extends ArmorItem
{
	public ItemCamera() {
		super(ArmorMaterials.CHAIN, EquipmentSlot.HEAD, new Settings().maxCount(1).group(RRItems.rralltab));
	}

	float	zoom		= 30f;
	float 	fovset		= 0f;
	float	senset		= 0f;
	boolean prevheld = false;
	boolean bkey = false;
	public static boolean zoomed = false;

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (world.isClient) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (player == client.player) {
				boolean key = glfwGetKey(client.getWindow().getHandle(), GLFW_KEY_B) == GLFW_PRESS && client.currentScreen == null;
				if (key != bkey && key) zoomed = !zoomed;
				bkey = key;
				if (zoomed) {
					if (!prevheld)
					{
						fovset = (float) client.options.fov;
						senset = (float) client.options.mouseSensitivity;
						client.options.smoothCameraEnabled = true;
					}
					zoom += (client.mouse.getYVelocity() * 0.01f);
					if (zoom < 10) zoom = 10;
					if (zoom > 67) zoom = 67;
					client.options.hudHidden = true;
					client.options.fov = zoom + (client.options.fov - zoom) * 0.85f;
					client.options.mouseSensitivity = senset * MathHelper.sqrt(zoom) * 0.1f;
				}
				else
				{
					if (prevheld)
					{
						client.options.fov = fovset;
						client.options.mouseSensitivity = senset;
						client.options.hudHidden = false;
						client.options.smoothCameraEnabled = false;
					}
				}
				prevheld = zoomed;
			}
		}
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bi");
	}*/
}

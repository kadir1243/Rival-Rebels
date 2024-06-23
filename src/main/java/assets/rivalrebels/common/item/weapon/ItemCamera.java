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

import static org.lwjgl.glfw.GLFW.*;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemCamera extends ArmorItem
{
	public ItemCamera() {
		super(ArmorMaterials.CHAIN, Type.HELMET, new Properties().stacksTo(1));
	}

	float	zoom		= 30f;
	float 	fovset		= 0f;
	float	senset		= 0f;
	boolean prevheld = false;
	boolean bkey = false;
	public static boolean zoomed = false;

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity living) {
            ItemStack equippedStack = living.getItemBySlot(getEquipmentSlot());
            if (equippedStack == stack) {
                if (world.isClientSide) {
                    Minecraft client = Minecraft.getInstance();
                    if (entity == client.player) {
                        boolean key = glfwGetKey(client.getWindow().getWindow(), GLFW_KEY_B) == GLFW_PRESS && client.screen == null;
                        if (key != bkey && key) zoomed = !zoomed;
                        bkey = key;
                        if (zoomed) {
                            if (!prevheld)
                            {
                                fovset = (float) client.options.fov().get();
                                senset = client.options.sensitivity().get().floatValue();
                                client.options.smoothCamera = true;
                            }
                            zoom += (client.mouseHandler.ypos() * 0.01f);
                            if (zoom < 10) zoom = 10;
                            if (zoom > 67) zoom = 67;
                            client.options.hideGui = true;
                            client.options.fov().set((int) (zoom + (client.options.fov().get() - zoom) * 0.85f));
                            client.options.sensitivity().set((double) (senset * Mth.sqrt(zoom) * 0.1f));
                        }
                        else
                        {
                            if (prevheld)
                            {
                                client.options.fov().set((int) fovset);
                                client.options.sensitivity().set((double) senset);
                                client.options.hideGui = false;
                                client.options.smoothCamera = false;
                            }
                        }
                        prevheld = zoomed;
                    }
                }
            }
        }
    }
}

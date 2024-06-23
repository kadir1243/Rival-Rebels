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

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static org.lwjgl.glfw.GLFW.*;

public class ItemCamera extends ArmorItem
{
	public ItemCamera() {
		super(ArmorMaterials.CHAIN, Type.HELMET, new Settings().maxCount(1));
	}

	float	zoom		= 30f;
	float 	fovset		= 0f;
	float	senset		= 0f;
	boolean prevheld = false;
	boolean bkey = false;
	public static boolean zoomed = false;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity living) {
            ItemStack equippedStack = living.getEquippedStack(getSlotType());
            if (equippedStack == stack) {
                if (world.isClient) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (entity == client.player) {
                        boolean key = glfwGetKey(client.getWindow().getHandle(), GLFW_KEY_B) == GLFW_PRESS && client.currentScreen == null;
                        if (key != bkey && key) zoomed = !zoomed;
                        bkey = key;
                        if (zoomed) {
                            if (!prevheld)
                            {
                                fovset = (float) client.options.getFov().getValue();
                                senset = client.options.getMouseSensitivity().getValue().floatValue();
                                client.options.smoothCameraEnabled = true;
                            }
                            zoom += (client.mouse.getY() * 0.01f);
                            if (zoom < 10) zoom = 10;
                            if (zoom > 67) zoom = 67;
                            client.options.hudHidden = true;
                            client.options.getFov().setValue((int) (zoom + (client.options.getFov().getValue() - zoom) * 0.85f));
                            client.options.getMouseSensitivity().setValue((double) (senset * MathHelper.sqrt(zoom) * 0.1f));
                        }
                        else
                        {
                            if (prevheld)
                            {
                                client.options.getFov().setValue((int) fovset);
                                client.options.getMouseSensitivity().setValue((double) senset);
                                client.options.hudHidden = false;
                                client.options.smoothCameraEnabled = false;
                            }
                        }
                        prevheld = zoomed;
                    }
                }
            }
        }
    }
}

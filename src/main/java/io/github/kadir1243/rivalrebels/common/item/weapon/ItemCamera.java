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
package io.github.kadir1243.rivalrebels.common.item.weapon;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import org.jspecify.annotations.Nullable;

public class ItemCamera extends Item
{
	public ItemCamera(Properties properties) {
		super(properties);
	}

	float	zoom		= 30f;
	float 	fovset		= 0f;
	float	senset		= 0f;
	boolean prevheld = false;
	boolean bkey = false;
	public static boolean zoomed = false;

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof Player player) {//FIXME
            ItemStack equippedStack = player.getItemBySlot(slot);
            if (equippedStack == stack) {
                if (level.isClientSide()) {
                    Minecraft client = Minecraft.getInstance();
                    if (entity == client.player) {
                        boolean key = InputConstants.isKeyDown(client.getWindow(), InputConstants.KEY_B) && client.gui.screen() == null;
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
                            if (!client.gui.hud.isHidden()) {
                                client.gui.hud.toggle();
                            }
                            client.options.fov().set((int) (zoom + (client.options.fov().get() - zoom) * 0.85f));
                            client.options.sensitivity().set((double) (senset * Mth.sqrt(zoom) * 0.1f));
                        }
                        else
                        {
                            if (prevheld)
                            {
                                client.options.fov().set((int) fovset);
                                client.options.sensitivity().set((double) senset);
                                if (client.gui.hud.isHidden()) {
                                    client.gui.hud.toggle();
                                }
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

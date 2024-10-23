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
package io.github.kadir1243.rivalrebels.client.gui;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.container.ContainerTheoreticalTsar;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@OnlyIn(Dist.CLIENT)
public class GuiTheoreticalTsar extends BombContainerScreen<ContainerTheoreticalTsar> {
	public GuiTheoreticalTsar(ContainerTheoreticalTsar container, Inventory inventoryPlayer, Component title) {
		super(container, inventoryPlayer, title);
		imageHeight = 206;
	}

    @Override
    public void renderName(GuiGraphics graphics) {
        graphics.drawString(font, Component.translatable(Translations.TSAR_NAME.toLanguageKey()), 18, 16, 4210752, false);
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);
        if (menu.isArmed()) {
            context.drawString(font, Component.translatable(Translations.BOMB_ARMED.toLanguageKey()), 6, imageHeight - 97, 0xFF0000, false);
        } else {
            context.drawString(font, Component.literal(menu.getMegaton() + " ").append(Component.translatable(Translations.BOMB_MEGATONS.toLanguageKey())), 6, imageHeight - 97, 0xFFFFFF, false);
        }
    }

    @Override
    public Vec2 getTimerPos() {
        return new Vec2(6, imageHeight - 107);
    }

    @Override
    public int getTimerColor() {
        return getCountdown() % 20 >= 10 ? 0xFFFFFF : 0xFF0000;
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return RRIdentifiers.guitheoreticaltsar;
    }
}

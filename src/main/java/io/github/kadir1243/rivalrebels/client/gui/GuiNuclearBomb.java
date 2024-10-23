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
import io.github.kadir1243.rivalrebels.common.container.ContainerNuclearBomb;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@OnlyIn(Dist.CLIENT)
public class GuiNuclearBomb extends BombContainerScreen<ContainerNuclearBomb> {
    public GuiNuclearBomb(ContainerNuclearBomb containerNuclearBomb, Inventory inventoryPlayer, Component title) {
		super(containerNuclearBomb, inventoryPlayer, title);
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);
		if (menu.isArmed()) {
            context.drawString(font, Component.translatable(Translations.BOMB_ARMED.toLanguageKey()), 80, imageHeight - 96 + 2, 0xffffff, false);
		} else if (!menu.hasTrollFace()) {
            context.drawString(font, Component.literal(menu.getAmountOfCharges() * 2.5 + " ").append(Component.translatable(Translations.BOMB_MEGATONS.toLanguageKey())), 80, imageHeight - 96 + 2, 0xffffff, false);
        } else {
            context.drawString(font, "Umad bro?", 80, imageHeight - 96 + 2, 0xffffff, false);
        }
    }

    @Override
    public boolean scaleName() {
        return false;
    }

    @Override
    public void renderName(GuiGraphics graphics) {
        // graphics.drawString(font, Component.translatable(NUKE_TRANSLATION.toLanguageKey()), 8, 6, 0xffffff, false);
        graphics.drawString(font, Component.translatable("container.inventory"), 8, imageHeight - 96 + 2, 0xffffff, false);
    }

    @Override
    public Vec2 getTimerPos() {
        return new Vec2(80, 6);
    }

    @Override
    public int getTimerColor() {
        return getCountdown() % 20 >= 10 ? 0x000000 : 0xff0000;
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return RRIdentifiers.guitnuke;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
		if (menu.getAmountOfCharges() != 0) graphics.setColor((menu.getAmountOfCharges() * 0.1F), 1 - (menu.getAmountOfCharges() * 0.1F), 0, 1);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		graphics.blit(RRIdentifiers.guitnuke, x, y, 0, 0, imageWidth, 81);
		graphics.setColor(1, 1, 1, 1);
        graphics.blit(RRIdentifiers.guitnuke, x, y + 81, 0, 81, imageWidth, imageHeight - 81);
	}
}

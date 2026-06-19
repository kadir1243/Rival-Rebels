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

import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.container.ContainerNuclearBomb;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@OnlyIn(Dist.CLIENT)
public class GuiNuclearBomb extends BombContainerScreen<ContainerNuclearBomb> {
    public GuiNuclearBomb(ContainerNuclearBomb containerNuclearBomb, Inventory inventoryPlayer, Component title) {
		super(containerNuclearBomb, inventoryPlayer, title, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
	}

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);

		if (menu.isArmed()) {
            graphics.text(font, Translations.BOMB_ARMED.translate(), 80, imageHeight - 96 + 2, CommonColors.WHITE, false);
		} else if (!menu.hasTrollFace()) {
            graphics.text(font, Translations.BOMB_MEGATONS.translate(menu.getMegaton()), 80, imageHeight - 96 + 2, CommonColors.WHITE, false);
        } else {
            graphics.text(font, Component.literal("Umad bro?"), 80, imageHeight - 96 + 2, CommonColors.WHITE, false);
        }
    }

    @Override
    public boolean scaleName() {
        return false;
    }

    @Override
    public void renderName(GuiGraphicsExtractor graphics) {
        graphics.text(font, this.title, this.titleLabelX, this.titleLabelY, CommonColors.WHITE, false);
        graphics.text(font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, CommonColors.WHITE, false);
    }

    @Override
    public Vec2 getTimerPos() {
        return new Vec2(80, 6);
    }

    @Override
    public int getTimerColor(int countdown) {
        return countdown % 20 >= 10 ? CommonColors.BLACK : CommonColors.RED;
    }

    @Override
    public RRTextures.Texture getBackgroundTexture() {
        return RRTextures.guitnuke;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int color = CommonColors.WHITE;
        float megaton = menu.getMegaton() / 2.5F;
        if (megaton != 0) color = ARGB.colorFromFloat(1F, (megaton * 0.1F), 1 - (megaton * 0.1F), 0F);
		int x = this.leftPos;
		int y = this.topPos;
		RRTextures.guitnuke.blit(graphics, x, y, 0, 0, imageWidth, 81, color);
        RRTextures.guitnuke.blit(graphics, x, y + 81, 0, 81, imageWidth, imageHeight - 81, CommonColors.WHITE);
	}
}

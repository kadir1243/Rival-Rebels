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
import io.github.kadir1243.rivalrebels.common.container.ContainerTsar;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@OnlyIn(Dist.CLIENT)
public class GuiTsar extends BombContainerScreen<ContainerTsar> {
    public GuiTsar(ContainerTsar container, Inventory inventory, Component title) {
		super(container, inventory, title, DEFAULT_IMAGE_WIDTH, 206);
        this.titleLabelX = 18;
        this.titleLabelY = 16;
    }

    @Override
    public RRTextures.Texture getBackgroundTexture() {
        return RRTextures.guittsar;
    }

    @Override
    public void renderName(GuiGraphicsExtractor graphics) {
        graphics.text(font, Translations.TSAR_NAME.translate(), this.titleLabelX, this.titleLabelY, CommonColors.DARK_GRAY, false);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);

		if (menu.isUnbalanced()) {
            graphics.text(font, Translations.UNBALANCED_BOMB.translate(), 6, imageHeight - 97, CommonColors.RED, false);
		} else if (menu.isArmed()) {
            graphics.text(font, Translations.BOMB_ARMED.translate(), 6, imageHeight - 97, CommonColors.RED, false);
		} else {
            graphics.text(font, Translations.BOMB_MEGATONS.translate(menu.getMegaton()), 6, imageHeight - 97, CommonColors.WHITE, false);
		}
    }

    @Override
    public Vec2 getTimerPos() {
        return new Vec2(6, imageHeight - 107);
    }

    @Override
    public int getTimerColor(int countdown) {
        return countdown % 20 >= 10 ? CommonColors.WHITE : CommonColors.RED;
    }
}

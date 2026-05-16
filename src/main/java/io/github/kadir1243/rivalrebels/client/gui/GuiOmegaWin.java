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

import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@OnlyIn(Dist.CLIENT)
public class GuiOmegaWin extends Screen {
	public GuiOmegaWin() {
        super(Component.empty());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.centeredText(font, Translations.OMEGA_WIN_SUBTITLE.translate(), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
        graphics.pose().pushMatrix();
        graphics.pose().scale(scalefactor, scalefactor);
        graphics.centeredText(font, Translations.OMEGA_WIN_TITLE.translate(), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
        graphics.pose().popMatrix();

		graphics.text(font, Component.literal("Omega: " + RivalRebels.round.getOmegaWins()), (this.width / 2) - 60, (this.height / 2 + 70), 0x44FF44);
		graphics.text(font, Component.literal("Sigma: " + RivalRebels.round.getSigmaWins()), (this.width / 2) + 10, (this.height / 2 + 70), 0x4444FF);
	}
}

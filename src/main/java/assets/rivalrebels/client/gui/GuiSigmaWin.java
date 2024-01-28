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
package assets.rivalrebels.client.gui;

import assets.rivalrebels.RivalRebels;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class GuiSigmaWin extends Screen
{
	public GuiSigmaWin() {
        super(Text.of(""));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		drawCenteredText(matrices, textRenderer, new TranslatableText("RivalRebels.sigmawin.subtitle"), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
		drawCenteredText(matrices, textRenderer, new TranslatableText("RivalRebels.sigmawin.title"), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		drawStringWithShadow(matrices, textRenderer, "Sigma: " + RivalRebels.round.getSigmaWins(), (this.width / 2) - 60, (this.height / 2 + 70), 0x4444FF);
		drawStringWithShadow(matrices, textRenderer, "Omega: " + RivalRebels.round.getOmegaWins(), (this.width / 2) + 10, (this.height / 2 + 70), 0x44FF44);
	}
}

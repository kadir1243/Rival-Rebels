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
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSigmaWin extends GuiScreen
{
	public GuiSigmaWin()
	{
	}

    @Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

    @Override
	public void drawScreen(int x, int y, float d) {
		drawCenteredString(fontRenderer, I18n.format("RivalRebels.sigmawin.subtitle"), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
		GlStateManager.scale(scalefactor, scalefactor, scalefactor);
		drawCenteredString(fontRenderer, I18n.format("RivalRebels.sigmawin.title"), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
		GlStateManager.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		fontRenderer.drawStringWithShadow("Sigma: " + RivalRebels.round.getSigmaWins(), (this.width / 2) - 60, (this.height / 2 + 70), 0x4444FF);
		fontRenderer.drawStringWithShadow("Omega: " + RivalRebels.round.getOmegaWins(), (this.width / 2) + 10, (this.height / 2 + 70), 0x44FF44);
	}
}

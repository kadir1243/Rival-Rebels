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
package assets.rivalrebels.client.guihelper;

import assets.rivalrebels.RRIdentifiers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class GuiScroll extends GuiButton {
	/** Scroll limit */
	public float limit;

	/** Current scroll amount (from the top down) */
	public float scroll;

    public GuiScroll(int x, int y, int limit) {
		super(x, y, 5, 11, Component.empty());
		this.limit = limit;
	}

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		int state = 0;
		if (mouseClicked(mouseX, mouseY, 0)) state = 11;
		context.blit(RRIdentifiers.guitbutton, this.getX(), (int) (this.getY() + scroll), 0, state, this.width, this.height);
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        scroll = (float) (mouseY - getY() - 5);
        scroll = Mth.clamp(scroll, 0, limit);
    }

	public float getScroll() {
		return scroll;
	}
}

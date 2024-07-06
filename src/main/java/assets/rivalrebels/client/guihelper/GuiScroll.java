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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class GuiScroll extends GuiButton
{
	/** Scroll limit */
	public int		limit;

	/** Current scroll amount (from the top down) */
	public int		scroll;

	/** Keeps if the scroll is active */
	public boolean	pressed;

	public GuiScroll(int par2, int par3, int par4)
	{
		super(par2, par3, 5, 11, Component.empty());
		this.limit = par4;
		pressed = false;
	}

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		this.mouseDragged(mouseX, mouseY, 0, 0, 0);
		if (scroll > limit) scroll = limit;
		if (scroll < 0) scroll = 0;
		int state = 0;
		if (pressed || mouseClicked(mouseX, mouseY, 0)) state = 11;
		context.blit(RRIdentifiers.guitbutton, this.getX(), this.getY() + scroll, 0, state, this.width, this.height);
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (Minecraft.getInstance().mouseHandler.isLeftPressed()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) scroll = (int) (mouseY - getY() - 5);
		} else {
			pressed = false;
		}
	}

    @Override
    public void onRelease(double mouseX, double mouseY) {
		pressed = false;
	}

	public int getScroll()
	{
		return scroll;
	}
}

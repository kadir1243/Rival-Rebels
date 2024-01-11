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

import assets.rivalrebels.RivalRebels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

public class GuiScroll extends GuiButton
{
	/** Scroll limit */
	public int		limit;

	/** Current scroll amount (from the top down) */
	public int		scroll;

	/** Keeps if the scroll is active */
	public boolean	pressed;

	public GuiScroll(int par1, int par2, int par3, int par4)
	{
		super(par1, par2, par3, 5, 11, "");
		this.limit = par4;
		pressed = false;
	}

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.mouseDragged(mc, mouseX, mouseY);
		if (scroll > limit) scroll = limit;
		if (scroll < 0) scroll = 0;
		mc.renderEngine.bindTexture(RivalRebels.guitbutton);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int state = 0;
		if (pressed || mousePressed(mc, mouseX, mouseY)) state = 11;
		this.drawTexturedModalRect(this.x, this.y + scroll, 0, state, this.width, this.height);
	}

	@Override
	protected void mouseDragged(Minecraft mc, int par2, int par3)
	{
		if (Mouse.isButtonDown(0))
		{
			if (mousePressed(mc, par2, par3)) pressed = true;
			if (pressed) scroll = par3 - y - 5;
		}
		else
		{
			pressed = false;
		}
	}

	@Override
	public void mouseReleased(int par2, int par3)
	{
		pressed = false;
	}

	public int getScroll()
	{
		return scroll;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int par2, int par3)
	{
		return this.enabled && this.visible && par2 >= this.x && par3 >= this.y && par2 < this.x + this.width && par3 < this.y + limit + this.height;
	}
}

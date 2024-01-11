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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class GuiFTKnob extends GuiButton
{
	protected int		mode;
	protected boolean	pressed;

	public GuiFTKnob(int id, int x, int y, int minDegree, int maxDegree, int startDegree, boolean respectLimits, String par6Str)
	{
		super(id, x, y, 36, 36, par6Str);
		mode = startDegree;
	}

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.mouseDragged(mc, mouseX, mouseY);
		if (mode > 2) mode = 2;
		if (mode < 0) mode = 0;
		mc.renderEngine.bindTexture(RivalRebels.guitbutton);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int state = 0;
		if (pressed || mousePressed(mc, mouseX, mouseY)) state = 36;
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.x + (width / 2f), this.y + (height / 2f), 0);
		GlStateManager.rotate(mode * 90 - 90, 0, 0, 1);
		GlStateManager.translate(-(this.x + (width / 2f)), -(this.y + (height / 2f)), 0);
		this.drawTexturedModalRect(this.x, this.y, 76 + state, 0, this.width, this.height);
		GlStateManager.popMatrix();
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
	{
		if (Mouse.isButtonDown(0))
		{
			if (mousePressed(par1Minecraft, par2, par3)) pressed = true;
			if (pressed) mode = (((((int) (Math.atan2(y - par3 + (height / 2), x - par2 + (width / 2)) * 180 / Math.PI)) + 450) % 360) - 45) / 90;
		}
		else
		{
			pressed = false;
			int move = Mouse.getDWheel();
			mode += move > 0 ? -1 : move < 0 ? 1 : 0;
			while (mode < 0)
				mode += 3;
			mode %= 3;
		}
	}

	@Override
	public void mouseReleased(int par2, int par3)
	{
		pressed = false;
	}

	public int getDegree()
	{
		return mode;
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent e).
	 */
	@Override
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
	{
		return this.enabled && this.visible && Math.sqrt(((x - par2 + (width / 2f)) * (x - par2 + (width / 2f))) + ((y - par3 + (height / 2f)) * (y - par3 + (height / 2f)))) <= (width / 2f);
	}
}

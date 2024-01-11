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
public class GuiRotor extends GuiButton
{
	protected int		degree;
	protected boolean	pressed;

	public GuiRotor(int id, int x, int y, int yawLimit, String par6Str)
	{
		super(id, x, y, 32, 32, par6Str);
		degree = yawLimit / 2;
	}

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.mouseDragged(mc, mouseX, mouseY);
		mc.renderEngine.bindTexture(RivalRebels.guitray);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		int deg = (degree % 180);
		if (degree >= 180) deg = 180 - deg;
		if (deg < 22) deg = 22;
		degree = deg;
		this.drawTexturedModalRect(this.x, this.y, 224, 66, this.width, this.height * deg / (180));
		this.drawCenteredString(mc.fontRenderer, (deg * 2) + "°", x + width / 2, y + height / 2 - 4, 0xffffff);
		GlStateManager.popMatrix();
	}

	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if (Mouse.isButtonDown(0)) {
			if (mousePressed(mc, mouseX, mouseY)) pressed = true;
			if (pressed) degree = ((int) (Math.atan2(y - mouseY + (height / 2), x - mouseX + (width / 2)) * 180 / Math.PI) + 270) % 360;
		} else {
			pressed = false;
			// float movement = -Mouse.getDWheel() * 0.375f;
			// degree += movement;
			// degree += 360000;
			// degree %= 360;
		}
	}

	@Override
	public void mouseReleased(int par2, int par3)
	{
		pressed = false;
	}

	public int getDegree()
	{
		return degree;
	}

    @Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		return this.enabled && this.visible && Math.sqrt(((x - mouseX + (width / 2f)) * (x - mouseX + (width / 2f))) + ((y - mouseY + (height / 2f)) * (y - mouseY + (height / 2f)))) <= (width / 2f);
	}
}

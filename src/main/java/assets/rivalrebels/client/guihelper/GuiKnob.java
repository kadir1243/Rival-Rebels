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
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GuiKnob extends GuiButton
{
	protected int		degree;
	protected int		maxdegreelimit	= 360;
	protected int		mindegreelimit	= 0;
	protected boolean	pressed;

	public GuiKnob(int x, int y, int minDegree, int maxDegree, int startDegree, boolean respectLimits, String par6Str)
	{
		super(x, y, 36, 36, par6Str);
		if (respectLimits)
		{
			maxdegreelimit = maxDegree;
			mindegreelimit = minDegree;
		}
		degree = startDegree;
	}

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.mouseDragged(mouseX, mouseY, 0, 0, 0);
		if (degree > maxdegreelimit) degree = maxdegreelimit;
		if (degree < mindegreelimit) degree = mindegreelimit;
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guitbutton);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int state = 0;
		if (pressed || mouseClicked(mouseX, mouseY, 0)) state = 36;
		matrices.push();
		matrices.translate(this.x + (width / 2f), this.y + (height / 2f), 0);
		matrices.multiply(new Quaternion(degree, 0, 0, 1));
		matrices.translate(-(this.x + (width / 2f)), -(this.y + (height / 2f)), 0);
		GuiUtils.drawTexturedModalRect(matrices, this.x, this.y, 76 + state, 0, this.width, this.height, getZOffset());
		matrices.pop();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (MinecraftClient.getInstance().mouse.wasLeftButtonClicked()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) degree = ((((int) (Math.atan2(y - mouseY + (height / 2), x - mouseX + (width / 2)) * 180 / Math.PI)) + 450) % 360) - 180;
		} else {
			pressed = false;

			float movement = (float) (-MinecraftClient.getInstance().mouse.getYVelocity() * 0.375f);
			degree += movement;
		}
	}

    @Override
    public void onRelease(double mouseX, double mouseY) {
		pressed = false;
	}

	public int getDegree()
	{
		return degree;
	}

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
		return this.active && this.visible && Math.sqrt(((x - mouseX + (width / 2f)) * (x - mouseX + (width / 2f))) + ((y - mouseY + (height / 2f)) * (y - mouseY + (height / 2f)))) <= (width / 2f);
	}
}

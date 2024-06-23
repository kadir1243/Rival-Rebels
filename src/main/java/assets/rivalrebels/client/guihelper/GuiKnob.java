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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
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
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		this.mouseDragged(mouseX, mouseY, 0, 0, 0);
		if (degree > maxdegreelimit) degree = maxdegreelimit;
		if (degree < mindegreelimit) degree = mindegreelimit;
		context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int state = 0;
		if (pressed || mouseClicked(mouseX, mouseY, 0)) state = 36;
        MatrixStack matrices = context.getMatrices();
        matrices.push();
		matrices.translate(this.getX() + (width / 2f), this.getY() + (height / 2f), 0);
		matrices.multiply(new Quaternionf(degree, 0, 0, 1));
		matrices.translate(-(this.getX() + (width / 2f)), -(this.getY() + (height / 2f)), 0);
		context.drawTexture(RRIdentifiers.guitbutton, this.getX(), this.getY(), 76 + state, 0, this.width, this.height);
		matrices.pop();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (MinecraftClient.getInstance().mouse.wasLeftButtonClicked()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) degree = ((((int) (Math.atan2(getY() - mouseY + (height / 2), getX() - mouseX + (width / 2)) * 180 / Math.PI)) + 450) % 360) - 180;
		} else {
			pressed = false;

			float movement = (float) (-deltaY * 0.375f);
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
		return this.active && this.visible && Math.sqrt(((getX() - mouseX + (width / 2f)) * (getX() - mouseX + (width / 2f))) + ((getY() - mouseY + (height / 2f)) * (getY() - mouseY + (height / 2f)))) <= (width / 2f);
	}
}

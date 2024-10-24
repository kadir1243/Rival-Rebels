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
package io.github.kadir1243.rivalrebels.client.guihelper;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

@OnlyIn(Dist.CLIENT)
public class GuiKnob extends GuiButton
{
	protected int		degree;
	protected int		maxdegreelimit	= 360;
	protected int		mindegreelimit	= 0;
	protected boolean	pressed;

	public GuiKnob(int x, int y, int minDegree, int maxDegree, int startDegree, boolean respectLimits, Component message)
	{
		super(x, y, 36, 36, message);
		if (respectLimits)
		{
			maxdegreelimit = maxDegree;
			mindegreelimit = minDegree;
		}
		degree = startDegree;
	}

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		if (degree > maxdegreelimit) degree = maxdegreelimit;
		if (degree < mindegreelimit) degree = mindegreelimit;
		int state = 0;
		if (pressed || mouseClicked(mouseX, mouseY, 0)) state = 36;
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
		matrices.translate(this.getX() + (width / 2f), this.getY() + (height / 2f), 0);
		matrices.mulPose(Axis.ZP.rotationDegrees(degree));
		matrices.translate(-(this.getX() + (width / 2f)), -(this.getY() + (height / 2f)), 0);
		graphics.blit(RRIdentifiers.guitbutton, this.getX(), this.getY(), 76 + state, 0, this.width, this.height);
		matrices.popPose();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (Minecraft.getInstance().mouseHandler.isLeftPressed()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) degree = ((((int) (Math.atan2(getY() - mouseY + (height / 2), getX() - mouseX + (width / 2)) * Mth.RAD_TO_DEG)) + 450) % 360) - 180;
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
}

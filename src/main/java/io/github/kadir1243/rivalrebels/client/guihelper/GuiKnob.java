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

import com.mojang.blaze3d.platform.InputConstants;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
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
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (degree > maxdegreelimit) degree = maxdegreelimit;
		if (degree < mindegreelimit) degree = mindegreelimit;
		int state = 0;
		if (pressed || mouseClicked(new MouseButtonEvent(mouseX, mouseY, new MouseButtonInfo(InputConstants.MOUSE_BUTTON_LEFT, 0)), false)) state = 36;
        graphics.pose().pushMatrix();
		graphics.pose().translate(this.getX() + (width / 2f), this.getY() + (height / 2f));
		graphics.pose().rotate(degree);
        graphics.pose().translate(-(this.getX() + (width / 2f)), -(this.getY() + (height / 2f)));
        RRTextures.guitbutton.blit(graphics, this.getX(), this.getY(), 76 + state, 0, this.width, this.height, CommonColors.WHITE);
        graphics.pose().popMatrix();
	}

    @Override
    protected void onDrag(MouseButtonEvent event, double mouseX, double mouseY) {
		if (Minecraft.getInstance().mouseHandler.isLeftPressed()) {
			if (mouseClicked(event, false)) pressed = true;
			if (pressed) degree = ((((int) (Math.atan2(getY() - mouseY + (height / 2), getX() - mouseX + (width / 2)) * Mth.RAD_TO_DEG)) + 450) % 360) - 180;
		} else {
			pressed = false;

			float movement = (float) (-(event.y() - mouseY) * 0.375f);
			degree += movement;
		}
	}

    @Override
    public void onRelease(MouseButtonEvent event) {
		pressed = false;
	}

	public int getDegree()
	{
		return degree;
	}
}

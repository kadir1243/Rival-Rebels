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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

@OnlyIn(Dist.CLIENT)
public class GuiRotor extends GuiButton
{
	protected int		degree;
	protected boolean	pressed;

	public GuiRotor(int x, int y, int yawLimit, Component message)
	{
		super(x, y, 32, 32, message);
		degree = yawLimit / 2;
	}

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = graphics.pose();
		matrices.pushPose();
		int deg = (degree % 180);
		if (degree >= 180) deg = 180 - deg;
		if (deg < 22) deg = 22;
		degree = deg;
		graphics.blit(RRIdentifiers.guitray, this.getX(), this.getY(), 224, 66, this.width, this.height * deg / (180));
        graphics.drawCenteredString(Minecraft.getInstance().font, (deg * 2) + "°", getX() + width / 2, getY() + height / 2 - 4, 0xffffff);
		matrices.popPose();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (Minecraft.getInstance().mouseHandler.isLeftPressed()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) degree = ((int) (Math.atan2(getY() - mouseY + (height / 2), getX() - mouseX + (width / 2)) * Mth.RAD_TO_DEG) + 270) % 360;
		} else {
			pressed = false;
			// float movement = -Mouse.getDWheel() * 0.375f;
			// degree += movement;
			// degree += 360000;
			// degree %= 360;
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

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
import net.minecraft.util.Mth;

@OnlyIn(Dist.CLIENT)
public class GuiFTKnob extends GuiButton {
	protected int mode;
	protected boolean pressed;

    public GuiFTKnob(Builder builder, int mode) {
        super(builder);
        this.mode = mode;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = graphics.pose();
		if (mode > 2) mode = 2;
		if (mode < 0) mode = 0;
		int state = 0;
		if (pressed || mouseClicked(mouseX, mouseY, 0)) state = 36;
		matrices.pushPose();
		matrices.translate(this.getX() + (width / 2f), this.getY() + (height / 2f), 0);
		matrices.mulPose(Axis.ZP.rotationDegrees(mode * 90 - 90));
		matrices.translate(-(this.getX() + (width / 2f)), -(this.getY() + (height / 2f)), 0);
		graphics.blit(RRIdentifiers.guitbutton, this.getX(), this.getY(), 76 + state, 0, this.width, this.height);
		matrices.popPose();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (Minecraft.getInstance().mouseHandler.isLeftPressed()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) mode = (((((int) (Math.atan2(getY() - mouseY + (height / 2), getX() - mouseX + (width / 2)) * Mth.RAD_TO_DEG)) + 450) % 360) - 45) / 90;
		} else {
			pressed = false;
			int move = (int) Minecraft.getInstance().mouseHandler.ypos();
			mode += Integer.compare(0, move);
			while (mode < 0)
				mode += 3;
			mode %= 3;
		}
	}

    @Override
    public void onRelease(double mouseX, double mouseY) {
        pressed = false;
    }

    public int getDegree()
	{
		return mode;
	}
}

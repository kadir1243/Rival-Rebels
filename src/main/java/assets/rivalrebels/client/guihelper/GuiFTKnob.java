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
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class GuiFTKnob extends GuiButton
{
	protected int		mode;
	protected boolean	pressed;

	public GuiFTKnob(int x, int y, int minDegree, int maxDegree, int startDegree, boolean respectLimits, String par6Str) {
		super(x, y, 36, 36, par6Str);
		mode = startDegree;
	}

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        PoseStack matrices = context.pose();
        this.mouseDragged(mouseX, mouseY, 0, 0,  0);
		if (mode > 2) mode = 2;
		if (mode < 0) mode = 0;
		context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		int state = 0;
		if (pressed || mouseClicked(mouseX, mouseY, 0)) state = 36;
		matrices.pushPose();
		matrices.translate(this.getX() + (width / 2f), this.getY() + (height / 2f), 0);
		matrices.mulPose(new Quaternionf(mode * 90 - 90, 0, 0, 1));
		matrices.translate(-(this.getX() + (width / 2f)), -(this.getY() + (height / 2f)), 0);
		context.blit(RRIdentifiers.guitbutton, this.getX(), this.getY(), 76 + state, 0, this.width, this.height);
		matrices.popPose();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (Minecraft.getInstance().mouseHandler.isLeftPressed()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) mode = (((((int) (Math.atan2(getY() - mouseY + (height / 2), getX() - mouseX + (width / 2)) * 180 / Math.PI)) + 450) % 360) - 45) / 90;
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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.active && this.visible && Math.sqrt(((getX() - mouseX + (width / 2f)) * (getX() - mouseX + (width / 2f))) + ((getY() - mouseY + (height / 2f)) * (getY() - mouseY + (height / 2f)))) <= (width / 2f);
	}
}

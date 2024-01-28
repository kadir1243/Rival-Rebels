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
public class GuiFTKnob extends GuiButton
{
	protected int		mode;
	protected boolean	pressed;

	public GuiFTKnob(int x, int y, int minDegree, int maxDegree, int startDegree, boolean respectLimits, String par6Str) {
		super(x, y, 36, 36, par6Str);
		mode = startDegree;
	}

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.mouseDragged(mouseX, mouseY, 0, 0,  0);
		if (mode > 2) mode = 2;
		if (mode < 0) mode = 0;
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guitbutton);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int state = 0;
		if (pressed || mouseClicked(mouseX, mouseY, 0)) state = 36;
		matrices.push();
		matrices.translate(this.x + (width / 2f), this.y + (height / 2f), 0);
		matrices.multiply(new Quaternion(mode * 90 - 90, 0, 0, 1));
		matrices.translate(-(this.x + (width / 2f)), -(this.y + (height / 2f)), 0);
		GuiUtils.drawTexturedModalRect(matrices, this.x, this.y, 76 + state, 0, this.width, this.height, getZOffset());
		matrices.pop();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (MinecraftClient.getInstance().mouse.wasLeftButtonClicked()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
			if (pressed) mode = (((((int) (Math.atan2(y - mouseY + (height / 2), x - mouseX + (width / 2)) * 180 / Math.PI)) + 450) % 360) - 45) / 90;
		} else {
			pressed = false;
			int move = (int) MinecraftClient.getInstance().mouse.getYVelocity();
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
		return this.active && this.visible && Math.sqrt(((x - mouseX + (width / 2f)) * (x - mouseX + (width / 2f))) + ((y - mouseY + (height / 2f)) * (y - mouseY + (height / 2f)))) <= (width / 2f);
	}
}

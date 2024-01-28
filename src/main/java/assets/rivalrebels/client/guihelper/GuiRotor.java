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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GuiRotor extends GuiButton
{
	protected int		degree;
	protected boolean	pressed;

	public GuiRotor(int x, int y, int yawLimit, String par6Str)
	{
		super(x, y, 32, 32, par6Str);
		degree = yawLimit / 2;
	}

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.mouseDragged(mouseX, mouseY, 0, 0, 0);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guitray);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		matrices.push();
		int deg = (degree % 180);
		if (degree >= 180) deg = 180 - deg;
		if (deg < 22) deg = 22;
		degree = deg;
		GuiUtils.drawTexturedModalRect(matrices, this.x, this.y, 224, 66, this.width, this.height * deg / (180), getZOffset());
		drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, (deg * 2) + "°", x + width / 2, y + height / 2 - 4, 0xffffff);
		matrices.pop();
	}

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (MinecraftClient.getInstance().mouse.wasLeftButtonClicked()) {
			if (mouseClicked(mouseX, mouseY, 0)) pressed = true;
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
    public void onRelease(double mouseX, double mouseY) {
		pressed = false;
	}

	public int getDegree()
	{
		return degree;
	}

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.active && this.visible && Math.sqrt(((x - mouseX + (width / 2f)) * (x - mouseX + (width / 2f))) + ((y - mouseY + (height / 2f)) * (y - mouseY + (height / 2f)))) <= (width / 2f);
	}
}

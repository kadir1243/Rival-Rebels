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

package assets.rivalrebels.client.gui;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.guihelper.GuiButton;
import assets.rivalrebels.common.container.ContainerLaptop;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GuiLaptop extends HandledScreen<ContainerLaptop> {
    GuiButton button;
	boolean prevButtonDown;

	public GuiLaptop(ContainerLaptop containerLaptop, PlayerInventory playerInventory, Text title)
	{
		super(containerLaptop, playerInventory, title);
		backgroundHeight = 206;
	}

    @Override
    protected void init() {
        super.init();

		int x = (width - getXSize()) / 2;
		int y = (height - getYSize()) / 2;
		drawables.clear();
		button = new GuiButton(x + 131, y + 89, 16, 16, LiteralText.EMPTY);
		addDrawable(button);
	}

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.guilaptopnuke);

		if (handler.isReady()) GuiUtils.drawTexturedModalRect(matrices, 131, 89, 239, 9, 16, 16, getZOffset());
		else GuiUtils.drawTexturedModalRect(matrices, 131, 89, 131, 89, 16, 16, getZOffset());

		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - getXSize()) / 2;
		int posy = (height - getYSize()) / 2;
		int coordx = posx + 53;
		int coordy = posy + 194;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			GuiUtils.drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), mousex, mousey, mousex + textRenderer.getWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			textRenderer.draw(matrices, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF);
			if (!buttondown && client.mouse.wasLeftButtonClicked()) {
                Util.getOperatingSystem().open("http://rivalrebels.com");
			}
		}
		buttondown = client.mouse.wasLeftButtonClicked();
	}

	boolean	buttondown;

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, RRIdentifiers.guilaptopnuke);
		int x = (width - getXSize()) / 2;
		int y = (height - getYSize()) / 2;
		GuiUtils.drawTexturedModalRect(matrices, x, y, 0, 0, getXSize(), getYSize(), getZOffset());
		if (handler.hasChips()) GuiUtils.drawTexturedModalRect(matrices, x + 135, y + 79, 248, 0, 8, 8, getZOffset());
		textRenderer.draw(matrices, new TranslatableText("RivalRebels.controller.B83"), x + 118, y + 11, 0xffffff);
		textRenderer.draw(matrices, new TranslatableText("RivalRebels.controller.b2spirit"), x + 25, y + 11, 0xffffff);
		textRenderer.draw(matrices, new TranslatableText("x" + handler.getB2spirit()), x + 154, y + 96, 0xffffff);
		textRenderer.draw(matrices, new TranslatableText("x" + handler.getB2carpet()), x + 154, y + 85, 0xffffff);
		if (button.mouseClicked(mouseX, mouseY, 0) && client.mouse.wasLeftButtonClicked() && !prevButtonDown) {
            handler.onGoButtonPressed();
		}
		prevButtonDown = client.mouse.wasLeftButtonClicked();
	}
}

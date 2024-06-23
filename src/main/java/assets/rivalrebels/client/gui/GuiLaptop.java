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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

@Environment(EnvType.CLIENT)
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

		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
        this.clearChildren();
		button = new GuiButton(x + 131, y + 89, 16, 16, Text.empty());
		addDrawable(button);
	}

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);

		if (handler.isReady()) context.drawTexture(RRIdentifiers.guilaptopnuke, 131, 89, 239, 9, 16, 16);
		else context.drawTexture(RRIdentifiers.guilaptopnuke, 131, 89, 131, 89, 16, 16);

		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - backgroundWidth) / 2;
		int posy = (height - backgroundHeight) / 2;
		int coordx = posx + 53;
		int coordy = posy + 194;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			context.fillGradient(mousex, mousey, mousex + textRenderer.getWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			context.drawText(textRenderer, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF, false);
			if (!buttondown && client.mouse.wasLeftButtonClicked()) {
                Util.getOperatingSystem().open("http://rivalrebels.com");
			}
		}
		buttondown = client.mouse.wasLeftButtonClicked();
	}

	boolean	buttondown;

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.setShaderColor(1, 1, 1, 1);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		context.drawTexture(RRIdentifiers.guilaptopnuke, x, y, 0, 0, backgroundWidth, backgroundHeight);
		if (handler.hasChips()) context.drawTexture(RRIdentifiers.guilaptopnuke, x + 135, y + 79, 248, 0, 8, 8);
		context.drawText(textRenderer, Text.translatable("RivalRebels.controller.B83"), x + 118, y + 11, 0xffffff, false);
		context.drawText(textRenderer, Text.translatable("RivalRebels.controller.b2spirit"), x + 25, y + 11, 0xffffff, false);
		context.drawText(textRenderer, Text.translatable("x" + handler.getB2spirit()), x + 154, y + 96, 0xffffff, false);
		context.drawText(textRenderer, Text.translatable("x" + handler.getB2carpet()), x + 154, y + 85, 0xffffff, false);
		if (button.mouseClicked(mouseX, mouseY, 0) && client.mouse.wasLeftButtonClicked() && !prevButtonDown) {
            handler.onGoButtonPressed();
		}
		prevButtonDown = client.mouse.wasLeftButtonClicked();
	}
}

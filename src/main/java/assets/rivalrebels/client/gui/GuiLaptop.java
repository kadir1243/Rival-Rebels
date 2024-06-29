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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class GuiLaptop extends AbstractContainerScreen<ContainerLaptop> {
    GuiButton button;

	public GuiLaptop(ContainerLaptop containerLaptop, Inventory playerInventory, Component title)
	{
		super(containerLaptop, playerInventory, title);
		imageHeight = 206;
	}

    @Override
    protected void init() {
        super.init();

		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
        this.clearWidgets();
		button = new GuiButton(x + 131, y + 89, 16, 16, Component.empty(), button1 -> menu.onGoButtonPressed());
		addRenderableOnly(button);
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);

		if (menu.isReady()) context.blit(RRIdentifiers.guilaptopnuke, 131, 89, 239, 9, 16, 16);
		else context.blit(RRIdentifiers.guilaptopnuke, 131, 89, 131, 89, 16, 16);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.setColor(1, 1, 1, 1);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		context.blit(RRIdentifiers.guilaptopnuke, x, y, 0, 0, imageWidth, imageHeight);
		if (menu.hasChips()) context.blit(RRIdentifiers.guilaptopnuke, x + 135, y + 79, 248, 0, 8, 8);
		context.drawString(font, Component.translatable("RivalRebels.controller.B83"), x + 118, y + 11, 0xffffff, false);
		context.drawString(font, Component.translatable("RivalRebels.controller.b2spirit"), x + 25, y + 11, 0xffffff, false);
		context.drawString(font, Component.translatable("x" + menu.getB2spirit()), x + 154, y + 96, 0xffffff, false);
		context.drawString(font, Component.translatable("x" + menu.getB2carpet()), x + 154, y + 85, 0xffffff, false);
	}
}

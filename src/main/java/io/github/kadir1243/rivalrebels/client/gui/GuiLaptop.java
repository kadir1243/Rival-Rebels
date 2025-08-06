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

package io.github.kadir1243.rivalrebels.client.gui;

import io.github.kadir1243.rivalrebels.client.guihelper.GuiButton;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.container.ContainerLaptop;
import io.github.kadir1243.rivalrebels.common.packet.LaptopPressPacket;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.Minecraft;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@OnlyIn(Dist.CLIENT)
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

		button = new GuiButton(this.leftPos + 131, this.topPos + 89, 16, 16, Component.empty(), button1 -> Minecraft.getInstance().getConnection().send(new LaptopPressPacket(menu.getLaptopPos())));
		addRenderableWidget(button);
	}

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

		if (menu.isReady()) RRTextures.guilaptopnuke.blit(graphics, 131, 89, 239, 9, 16, 16, CommonColors.WHITE);
		else RRTextures.guilaptopnuke.blit(graphics, 131, 89, 131, 89, 16, 16, CommonColors.WHITE);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
        RRTextures.guilaptopnuke.blit(context, x, y, 0, 0, imageWidth, imageHeight, CommonColors.WHITE);
		if (menu.hasChips()) RRTextures.guilaptopnuke.blit(context, x + 135, y + 79, 248, 0, 8, 8, CommonColors.WHITE);
		context.drawString(font, Component.translatable("RivalRebels.controller.B83"), x + 118, y + 11, 0xffffff, false);
		context.drawString(font, Translations.LAPTOP_B2_SPIRIT.translate(), x + 25, y + 11, 0xffffff, false);
		context.drawString(font, Component.translatable("x" + menu.getB2spirit()), x + 154, y + 96, 0xffffff, false);
		context.drawString(font, Component.translatable("x" + menu.getB2carpet()), x + 154, y + 85, 0xffffff, false);
	}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

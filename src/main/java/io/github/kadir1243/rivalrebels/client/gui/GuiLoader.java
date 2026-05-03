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

import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.container.ContainerLoader;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@OnlyIn(Dist.CLIENT)
public class GuiLoader extends AbstractContainerScreen<ContainerLoader> {
    public GuiLoader(ContainerLoader containerLoader, Inventory playerInv, Component title) {
        int BASE_IMAGE_HEIGHT = 114;
        int inventoryRows = containerLoader.size() / 9;
        super(containerLoader, playerInv, title, 256, BASE_IMAGE_HEIGHT + inventoryRows * 18);
	}

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.pose().pushMatrix();
		graphics.pose().rotate(-13);
		graphics.text(font, "Loader", 165, 237, 0x444444, false);
		graphics.pose().popMatrix();
	}

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        RRTextures.guitloader.blit(graphics, width / 2 - 128, height / 2 - 103, 0, 0, 256, 210, CommonColors.WHITE);
	}
}

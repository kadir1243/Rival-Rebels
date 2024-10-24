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

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.container.ContainerLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@OnlyIn(Dist.CLIENT)
public class GuiLoader extends AbstractContainerScreen<ContainerLoader> {
    public GuiLoader(ContainerLoader containerLoader, Inventory playerInv, Component title) {
		super(containerLoader, playerInv, title);
        int BASE_IMAGE_HEIGHT = 114;
        int inventoryRows = containerLoader.size() / 9;
		this.imageHeight = BASE_IMAGE_HEIGHT + inventoryRows * 18;
		this.imageWidth = 256;
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        PoseStack matrices = context.pose();
        matrices.pushPose();
		matrices.mulPose(Axis.ZP.rotationDegrees(-13));
		context.drawString(font, "Loader", 165, 237, 0x444444, false);
		matrices.popPose();
	}

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
		context.blit(RRIdentifiers.guitloader, width / 2 - 128, height / 2 - 103, 0, 0, 256, 210);
	}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

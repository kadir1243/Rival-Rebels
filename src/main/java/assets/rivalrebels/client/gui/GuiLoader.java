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
import assets.rivalrebels.common.container.ContainerLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class GuiLoader extends AbstractContainerScreen<ContainerLoader> {
    /**
	 * window height is calculated with this values, the more rows, the heigher
	 */
	private final int inventoryRows;

	public GuiLoader(ContainerLoader containerLoader, Inventory playerInv, Component title)
	{
		super(containerLoader, playerInv, title);
        int var4 = 114;
		this.inventoryRows = containerLoader.size() / 9;
		this.imageHeight = var4 + this.inventoryRows * 18;
		this.imageWidth = 256;
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        PoseStack matrices = context.pose();
        matrices.pushPose();
		matrices.mulPose(new Quaternionf(-13, 0, 0, 1));
		context.drawString(font, "Loader", 165, 237, 0x444444, false);
		matrices.popPose();
		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - imageWidth) / 2;
		int posy = (height - imageHeight) / 2;
		int coordx = posx + 92;
		int coordy = posy + 202;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			context.fillGradient(mousex, mousey, mousex + font.width("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			context.drawString(font, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF, false);
			if (!buttondown && minecraft.mouseHandler.isLeftPressed())
			{
                Util.getPlatform().openUri("http://rivalrebels.com");
            }
		}
		buttondown = minecraft.mouseHandler.isLeftPressed();
	}

	boolean	buttondown;

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		context.blit(RRIdentifiers.guitloader, width / 2 - 128, height / 2 - 103, 0, 0, 256, 210);
	}
}

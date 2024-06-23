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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class GuiLoader extends HandledScreen<ContainerLoader> {
    /**
	 * window height is calculated with this values, the more rows, the heigher
	 */
	private final int inventoryRows;

	public GuiLoader(ContainerLoader containerLoader, PlayerInventory playerInv, Text title)
	{
		super(containerLoader, playerInv, title);
        int var4 = 114;
		this.inventoryRows = containerLoader.size() / 9;
		this.backgroundHeight = var4 + this.inventoryRows * 18;
		this.backgroundWidth = 256;
	}

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
		matrices.multiply(new Quaternionf(-13, 0, 0, 1));
		context.drawText(textRenderer, "Loader", 165, 237, 0x444444, false);
		matrices.pop();
		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - backgroundWidth) / 2;
		int posy = (height - backgroundHeight) / 2;
		int coordx = posx + 92;
		int coordy = posy + 202;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			context.fillGradient(mousex, mousey, mousex + textRenderer.getWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			context.drawText(textRenderer, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF, false);
			if (!buttondown && client.mouse.wasLeftButtonClicked())
			{
                Util.getOperatingSystem().open("http://rivalrebels.com");
            }
		}
		buttondown = client.mouse.wasLeftButtonClicked();
	}

	boolean	buttondown;

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		context.drawTexture(RRIdentifiers.guitloader, width / 2 - 128, height / 2 - 103, 0, 0, 256, 210);
	}
}

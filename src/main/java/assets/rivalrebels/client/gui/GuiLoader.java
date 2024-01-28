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
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
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
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		matrices.push();
		matrices.multiply(new Quaternion(-13, 0, 0, 1));
		textRenderer.draw(matrices, "Loader", 165, 237, 0x444444);
		matrices.pop();
		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - getXSize()) / 2;
		int posy = (height - getYSize()) / 2;
		int coordx = posx + 92;
		int coordy = posy + 202;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			GuiUtils.drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), mousex, mousey, mousex + textRenderer.getWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			textRenderer.draw(matrices, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF);
			if (!buttondown && client.mouse.wasLeftButtonClicked())
			{
                Util.getOperatingSystem().open("http://rivalrebels.com");
            }
		}
		buttondown = client.mouse.wasLeftButtonClicked();
	}

	boolean	buttondown;

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RRIdentifiers.guitloader);
		GuiUtils.drawTexturedModalRect(matrices, width / 2 - 128, height / 2 - 103, 0, 0, 256, 210, getZOffset());
	}
}

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
import assets.rivalrebels.common.container.ContainerTachyonBomb;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class GuiTachyonBomb extends HandledScreen<ContainerTachyonBomb> {
	public GuiTachyonBomb(ContainerTachyonBomb containerTachyonBomb, PlayerInventory inventoryPlayer, Text title) {
		super(containerTachyonBomb, inventoryPlayer, title);
		backgroundHeight = 206;
	}

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        MatrixStack matrices = context.getMatrices();
        int seconds = (handler.getCountdown() / 20);
		int millis = (handler.getCountdown() % 20) * 3;
		String milli;
		if (millis < 10)
		{
			milli = "0" + millis;
		}
		else
		{
			milli = "" + millis;
		}
		if (handler.getCountdown() % 20 >= 10)
		{
			context.drawText(textRenderer, Text.translatable("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 6, backgroundHeight - 107, 0xFFFFFF, false);
		}
		else
		{
            context.drawText(textRenderer, Text.translatable("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 6, backgroundHeight - 107, 0xFF0000, false);
		}
		float scalef = 0.666f;
		matrices.push();
		matrices.scale(scalef, scalef, scalef);
        context.drawText(textRenderer, "Tachyon", 18, 16, 4210752, false);
		matrices.pop();
		if (handler.isUnbalanced())
		{
            context.drawText(textRenderer, Text.translatable("RivalRebels.tsar.unbalanced"), 6, backgroundHeight - 97, 0xFF0000, false);
		}
		else if (handler.isArmed())
		{
            context.drawText(textRenderer, Text.translatable("RivalRebels.tsar.armed"), 6, backgroundHeight - 97, 0xFF0000, false);
		}
		else
		{
            context.drawText(textRenderer, Text.literal(handler.getMegaton() + " ").append(Text.translatable("RivalRebels.tsar.megatons")), 6, backgroundHeight - 97, 0xFFFFFF, false);
		}

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
        context.setShaderColor(1, 1, 1, 1);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		context.drawTexture(RRIdentifiers.guitachyonbomb, x, y, 0, 0, backgroundWidth, backgroundHeight);
	}
}

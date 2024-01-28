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
import com.mojang.blaze3d.systems.RenderSystem;
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
public class GuiTachyonBomb extends HandledScreen<ContainerTachyonBomb> {
	public GuiTachyonBomb(ContainerTachyonBomb containerTachyonBomb, PlayerInventory inventoryPlayer, Text title) {
		super(containerTachyonBomb, inventoryPlayer, title);
		backgroundHeight = 206;
	}

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
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
			textRenderer.draw(matrices, new TranslatableText("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 6, getYSize() - 107, 0xFFFFFF);
		}
		else
		{
			textRenderer.draw(matrices, new TranslatableText("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 6, getYSize() - 107, 0xFF0000);
		}
		float scalef = 0.666f;
		matrices.push();
		matrices.scale(scalef, scalef, scalef);
		textRenderer.draw(matrices, "Tachyon", 18, 16, 4210752);
		matrices.pop();
		if (handler.isUnbalanced())
		{
			textRenderer.draw(matrices, new TranslatableText("RivalRebels.tsar.unbalanced"), 6, getYSize() - 97, 0xFF0000);
		}
		else if (handler.isArmed())
		{
			textRenderer.draw(matrices, new TranslatableText("RivalRebels.tsar.armed"), 6, getYSize() - 97, 0xFF0000);
		}
		else
		{
			textRenderer.draw(matrices, new LiteralText(handler.getMegaton() + " ").append(new TranslatableText("RivalRebels.tsar.megatons")), 6, getYSize() - 97, 0xFFFFFF);
		}

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
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, RRIdentifiers.guitachyonbomb);
		int x = (width - getXSize()) / 2;
		int y = (height - getYSize()) / 2;
		GuiUtils.drawTexturedModalRect(matrices, x, y, 0, 0, getXSize(), getYSize(), getZOffset());
	}
}

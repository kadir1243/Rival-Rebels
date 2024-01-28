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
import assets.rivalrebels.common.container.ContainerNuclearBomb;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GuiNuclearBomb extends HandledScreen<ContainerNuclearBomb> {

	public GuiNuclearBomb(ContainerNuclearBomb containerNuclearBomb, PlayerInventory inventoryPlayer, Text title) {
		super(containerNuclearBomb, inventoryPlayer, title);
	}

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
		int seconds = (handler.getCountDown() / 20);
		int millis = (handler.getCountDown() % 20) * 3;
		String milli;
		if (millis < 10)
		{
			milli = "0" + millis;
		}
		else
		{
			milli = "" + millis;
		}
		if (handler.getCountDown() % 20 >= 10)
		{
			textRenderer.draw(matrices, new TranslatableText("RivalRebels.tsar.timer").append(": -" + seconds + ":" + milli), 80, 6, 0x000000);
		}
		else
		{
			textRenderer.draw(matrices, new TranslatableText("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 80, 6, 0xFF0000);
		}
		textRenderer.draw(matrices, new TranslatableText("RivalRebels.nuke.name"), 8, 6, 0xffffff);
		textRenderer.draw(matrices, new TranslatableText("container.inventory"), 8, getYSize() - 96 + 2, 0xffffff);
		if (handler.isArmed())
		{
			textRenderer.draw(matrices, new TranslatableText("RivalRebels.tsar.armed"), 80, getYSize() - 96 + 2, 0xffffff);
		}
		else
		{
			if (!handler.hasTrollFace())
			{
				textRenderer.draw(matrices, new LiteralText(handler.getAmountOfCharges() * 2.5 + " ").append(new TranslatableText("RivalRebels.tsar.megatons")), 80, getYSize() - 96 + 2, 0xffffff);
			}
			else
			{
				textRenderer.draw(matrices, "Umad bro?", 80, getYSize() - 96 + 2, 0xffffff);
			}
		}

		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - getXSize()) / 2;
		int posy = (height - getYSize()) / 2;
		int coordx = posx + 53;
		int coordy = posy + 158;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			GuiUtils.drawGradientRect(new Matrix4f(), getZOffset(), mousex, mousey, mousex + textRenderer.getWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
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
		if (handler.getAmountOfCharges() != 0) RenderSystem.setShaderColor((handler.getAmountOfCharges() * 0.1F), 1 - (handler.getAmountOfCharges() * 0.1F), 0, 1);
        RenderSystem.setShaderTexture(0, RRIdentifiers.guitnuke);
		int x = (width - getXSize()) / 2;
		int y = (height - getYSize()) / 2;
		GuiUtils.drawTexturedModalRect(matrices, x, y, 0, 0, getXSize(), 81, getZOffset());
		RenderSystem.setShaderColor(1, 1, 1, 1);
        GuiUtils.drawTexturedModalRect(matrices, x, y + 81, 0, 81, getXSize(), getYSize() - 81, getZOffset());
	}
}

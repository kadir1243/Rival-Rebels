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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

@Environment(EnvType.CLIENT)
public class GuiNuclearBomb extends HandledScreen<ContainerNuclearBomb> {

	public GuiNuclearBomb(ContainerNuclearBomb containerNuclearBomb, PlayerInventory inventoryPlayer, Text title) {
		super(containerNuclearBomb, inventoryPlayer, title);
	}

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
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
			context.drawText(textRenderer, Text.translatable("RivalRebels.tsar.timer").append(": -" + seconds + ":" + milli), 80, 6, 0x000000, false);
		}
		else
		{
            context.drawText(textRenderer, Text.translatable("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 80, 6, 0xFF0000, false);
		}
		context.drawText(textRenderer, Text.translatable("RivalRebels.nuke.name"), 8, 6, 0xffffff, false);
		context.drawText(textRenderer, Text.translatable("container.inventory"), 8, backgroundHeight - 96 + 2, 0xffffff, false);
		if (handler.isArmed())
		{
            context.drawText(textRenderer, Text.translatable("RivalRebels.tsar.armed"), 80, backgroundHeight - 96 + 2, 0xffffff, false);
		}
		else
		{
			if (!handler.hasTrollFace())
			{
                context.drawText(textRenderer, Text.literal(handler.getAmountOfCharges() * 2.5 + " ").append(Text.translatable("RivalRebels.tsar.megatons")), 80, backgroundHeight - 96 + 2, 0xffffff, false);
			}
			else
			{
                context.drawText(textRenderer, "Umad bro?", 80, backgroundHeight - 96 + 2, 0xffffff, false);
			}
		}

		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - backgroundWidth) / 2;
		int posy = (height - backgroundHeight) / 2;
		int coordx = posx + 53;
		int coordy = posy + 158;
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
		if (handler.getAmountOfCharges() != 0) context.setShaderColor((handler.getAmountOfCharges() * 0.1F), 1 - (handler.getAmountOfCharges() * 0.1F), 0, 1);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		context.drawTexture(RRIdentifiers.guitnuke, x, y, 0, 0, backgroundWidth, 81);
		context.setShaderColor(1, 1, 1, 1);
        context.drawTexture(RRIdentifiers.guitnuke, x, y + 81, 0, 81, backgroundWidth, backgroundHeight - 81);
	}
}

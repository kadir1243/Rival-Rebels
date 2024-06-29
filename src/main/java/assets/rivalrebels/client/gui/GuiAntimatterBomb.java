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
import assets.rivalrebels.common.container.ContainerAntimatterBomb;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class GuiAntimatterBomb extends AbstractContainerScreen<ContainerAntimatterBomb> {
	public GuiAntimatterBomb(ContainerAntimatterBomb bomb, Inventory inventoryPlayer, Component title) {
		super(bomb, inventoryPlayer, title);
		imageHeight = 206;
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);
		int seconds = (menu.getCountdown() / 20);
		int millis = (menu.getCountdown() % 20) * 3;
		String milli;
		if (millis < 10)
		{
			milli = "0" + millis;
		}
		else
		{
			milli = "" + millis;
		}
		if (menu.getCountdown() % 20 >= 10) {
			context.drawString(font, Component.translatable("RivalRebels.tsar.timer").append(": -" + seconds + ":" + milli), 6, imageHeight - 107, 0xFFFFFF, false);
		} else {
			context.drawString(font, Component.translatable("RivalRebels.tsar.timer").append(": -" + seconds + ":" + milli), 6, imageHeight - 107, 0xFF0000, false);
		}
		float scalef = 0.666f;
        PoseStack matrices = context.pose();
        matrices.pushPose();
		matrices.scale(scalef, scalef, scalef);
		context.drawString(font, Component.translatable("RivalRebels.antimatterbomb"), 18, 16, 4210752, false);
		matrices.popPose();
		if (menu.isUnbalanced())
		{
			context.drawString(font, Component.translatable("RivalRebels.tsar.unbalanced"), 6, imageHeight - 97, 0xFF0000, false);
		}
		else if (menu.isArmed())
		{
            context.drawString(font, Component.translatable("RivalRebels.tsar.armed"), 6, imageHeight - 97, 0xFF0000, false);
		}
		else
		{
            context.drawString(font, Component.literal(menu.getMegaton() + " ").append(Component.translatable("RivalRebels.tsar.megatons")), 6, imageHeight - 97, 0xFFFFFF, false);
		}
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.setColor(1, 1, 1, 1);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
        context.blit(RRIdentifiers.guitantimatterbomb, x, y, 0, 0, imageWidth, imageHeight);
	}
}

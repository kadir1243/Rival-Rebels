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
import assets.rivalrebels.common.container.ContainerTheoreticalTsar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class GuiTheoreticalTsar extends AbstractContainerScreen<ContainerTheoreticalTsar> {
	public GuiTheoreticalTsar(ContainerTheoreticalTsar container, Inventory inventoryPlayer, Component title) {
		super(container, inventoryPlayer, title);
		imageHeight = 206;
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);
        PoseStack matrices = context.pose();
        showTimer(context);
        float scalef = 0.666f;
		matrices.pushPose();
		matrices.scale(scalef, scalef, scalef);
		context.drawString(font, Component.translatable("RivalRebels.tsar.tsar"), 18, 16, 4210752, false);
		matrices.popPose();
		if (menu.isArmed())
		{
			context.drawString(font, Component.translatable(RRIdentifiers.BOMB_ARMED.toLanguageKey()), 6, imageHeight - 97, 0xFF0000, false);
		}
		else
		{
			context.drawString(font, Component.literal(menu.getMegaton() + " ").append(Component.translatable(RRIdentifiers.BOMB_MEGATONS.toLanguageKey())), 6, imageHeight - 97, 0xFFFFFF, false);
		}
    }

    private void showTimer(GuiGraphics graphics) {
        int seconds = (menu.getCountdown() / 20);
        int millis = (menu.getCountdown() % 20) * 3;
        String milli;
        if (millis < 10) {
            milli = "0" + millis;
        } else {
            milli = "" + millis;
        }
        if (menu.getCountdown() % 20 >= 10) {
            graphics.drawString(font, Component.translatable(RRIdentifiers.BOMB_TIMER.toLanguageKey()).append(": -" + seconds + ":" + milli), 6, imageHeight - 107, 0xFFFFFF, false);
        } else {
            graphics.drawString(font, Component.translatable(RRIdentifiers.BOMB_TIMER.toLanguageKey()).append(": -" + seconds + ":" + milli), 6, imageHeight - 107, 0xFF0000, false);
        }
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
        context.blit(RRIdentifiers.guitheoreticaltsar, x, y, 0, 0, imageWidth, imageHeight);
	}
}

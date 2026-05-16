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

import io.github.kadir1243.rivalrebels.client.guihelper.GuiButton;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.packet.VotePacket;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@OnlyIn(Dist.CLIENT)
public class GuiNextBattle extends Screen {
	private static final int	xSizeOfTexture	= 256;
	private static final int	ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
	private GuiButton	nextBattleButton;
	private GuiButton	waitButton;
	private int			num				= 0;
	private int			count			= 0;

	public GuiNextBattle() {
        super(Component.empty());
	}

	@Override
	public void init() {
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;

		nextBattleButton = new GuiButton(posX + 66, posY + 203, 60, 11, Translations.NEXT_BATTLE_YES.translate(), button -> {
            Minecraft.getInstance().getConnection().send(new VotePacket(true));
            onClose();
        });
		waitButton = new GuiButton(posX + 128, posY + 203, 60, 11, Translations.NEXT_BATTLE_NO.translate(), button -> {
            Minecraft.getInstance().getConnection().send(new VotePacket(false));
            onClose();
        });
		this.addRenderableWidget(nextBattleButton);
		this.addRenderableWidget(waitButton);
	}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        count++;
		if (count == 60)
		{
			num = 1 - num;
			count = 0;
		}
        float f = 0.00390625F;
        graphics.blit(
            num == 0 ? RRTextures.guitwarning0 : RRTextures.guitwarning1,
            posX,
            posY,
            posX + xSizeOfTexture,
            posY + ySizeOfTexture,
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f,
            0
        );
        graphics.centeredText(font, Translations.NEXT_BATTLE_SUBTITLE.translate(), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
        graphics.pose().pushMatrix();
        graphics.pose().scale(scalefactor, scalefactor);
        graphics.centeredText(font, Translations.NEXT_BATTLE_TITLE.translate(), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
        graphics.pose().popMatrix();
        MultiLineLabel.create(font, Translations.NEXT_BATTLE_QUESTION.translate(), 128).visitLines(TextAlignment.LEFT, posX + 64, posY + 160, this.font.lineHeight, graphics.textRenderer());
        super.extractRenderState(graphics, mouseX, mouseY, a);
	}
}

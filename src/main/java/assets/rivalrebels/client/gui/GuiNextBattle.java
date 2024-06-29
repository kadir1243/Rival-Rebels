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
import assets.rivalrebels.client.guihelper.GuiButton;
import assets.rivalrebels.common.packet.VotePacket;
import assets.rivalrebels.mixin.client.DrawContextAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GuiNextBattle extends Screen
{
	private final int	xSizeOfTexture	= 256;
	private final int	ySizeOfTexture	= 256;
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
		this.clearWidgets();

		nextBattleButton = new GuiButton(posX + 66, posY + 203, 60, 11, Component.translatable("RivalRebels.nextbattle.yes"), button -> {
            ClientPlayNetworking.send(new VotePacket(true));
            onClose();
        });
		waitButton = new GuiButton(posX + 128, posY + 203, 60, 11, Component.translatable("RivalRebels.nextbattle.no"), button -> {
            ClientPlayNetworking.send(new VotePacket(false));
            onClose();
        });
		this.addRenderableOnly(nextBattleButton);
		this.addRenderableOnly(waitButton);
	}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = graphics.pose();
        count++;
		if (count == 60)
		{
			num = 1 - num;
			count = 0;
		}
        float f = 0.00390625F;
        ((DrawContextAccessor) graphics).callDrawTexturedQuad(
            num == 0 ? RRIdentifiers.guitwarning0 : RRIdentifiers.guitwarning1,
            posX,
            posX + xSizeOfTexture,
            posY,
            posY + ySizeOfTexture,
            0, // z offset
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f,
            0
        );
        graphics.drawCenteredString(font, Component.translatable("RivalRebels.nextbattle.subtitle"), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
        graphics.drawCenteredString(font, Component.translatable("RivalRebels.nextbattle.title"), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
        MultiLineLabel.create(font, Component.translatable("RivalRebels.nextbattle.question"), 128).renderLeftAlignedNoShadow(graphics, posX + 64, posY + 160, this.font.lineHeight, 0xffffff);
        super.render(graphics, mouseX, mouseY, delta);
	}
}

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
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

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
	private boolean		prevclick;

	public GuiNextBattle() {
        super(Text.empty());
	}

	@Override
	public void init() {
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.clearChildren();

		nextBattleButton = new GuiButton(posX + 66, posY + 203, 60, 11, Text.translatable("RivalRebels.nextbattle.yes"));
		waitButton = new GuiButton(posX + 128, posY + 203, 60, 11, Text.translatable("RivalRebels.nextbattle.no"));
		this.addDrawable(nextBattleButton);
		this.addDrawable(waitButton);
	}

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MatrixStack matrices = context.getMatrices();
        count++;
		if (count == 60)
		{
			num = 1 - num;
			count = 0;
		}
        float f = 0.00390625F;
        ((DrawContextAccessor) context).callDrawTexturedQuad(
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
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("RivalRebels.nextbattle.subtitle"), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("RivalRebels.nextbattle.title"), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
        MultilineText.create(textRenderer, Text.translatable("RivalRebels.nextbattle.question"), 128).draw(context, posX + 64, posY + 160, this.textRenderer.fontHeight, 0xffffff);
        super.render(context, mouseX, mouseY, delta);

		if (client.mouse.wasLeftButtonClicked() && !prevclick) {
			if (nextBattleButton.mouseClicked(mouseX, mouseY, 0)) {
                ClientPlayNetworking.send(new VotePacket(true));
				this.client.setScreen(null);
			}
			if (waitButton.mouseClicked(mouseX, mouseY, 0)) {
                ClientPlayNetworking.send(new VotePacket(false));
				this.client.setScreen(null);
			}
		}
		prevclick = client.mouse.wasLeftButtonClicked();
	}
}

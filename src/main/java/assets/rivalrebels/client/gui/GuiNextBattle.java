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
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.VotePacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

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
        super(Text.of(""));
	}

	@Override
	public void init() {
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.drawables.clear();

		nextBattleButton = new GuiButton(posX + 66, posY + 203, 60, 11, new TranslatableText("RivalRebels.nextbattle.yes"));
		waitButton = new GuiButton(posX + 128, posY + 203, 60, 11, new TranslatableText("RivalRebels.nextbattle.no"));
		this.addDrawable(nextBattleButton);
		this.addDrawable(waitButton);
	}

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		count++;
		if (count == 60)
		{
			num = 1 - num;
			count = 0;
		}
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        float f = 0.00390625F;
		if (num == 0) this.client.textureManager.bindTexture(RRIdentifiers.guitwarning0);
		if (num == 1) this.client.textureManager.bindTexture(RRIdentifiers.guitwarning1);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		buffer.vertex(posX, posY + ySizeOfTexture, getZOffset()).texture(0, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY + ySizeOfTexture, getZOffset()).texture(xSizeOfTexture * f, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY, getZOffset()).texture(xSizeOfTexture * f, 0).next();
		buffer.vertex(posX, posY, getZOffset()).texture(0, 0).next();
		tessellator.draw();
		drawCenteredText(matrices, textRenderer, new TranslatableText("RivalRebels.nextbattle.subtitle"), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
		drawCenteredText(matrices, textRenderer, new TranslatableText("RivalRebels.nextbattle.title"), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
		textRenderer.drawTrimmed(new TranslatableText("RivalRebels.nextbattle.question"), posX + 64, posY + 160, 128, 0xffffff);
        super.render(matrices, mouseX, mouseY, delta);

		if (client.mouse.wasLeftButtonClicked() && !prevclick) {
			if (nextBattleButton.mouseClicked(mouseX, mouseY, 0)) {
				PacketDispatcher.packetsys.sendToServer(new VotePacket(true));
				this.client.setScreen(null);
			}
			if (waitButton.mouseClicked(mouseX, mouseY, 0)) {
				PacketDispatcher.packetsys.sendToServer(new VotePacket(false));
				this.client.setScreen(null);
			}
		}
		prevclick = client.mouse.wasLeftButtonClicked();
	}
}

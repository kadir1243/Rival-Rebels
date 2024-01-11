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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.guihelper.GuiButton;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.VotePacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNextBattle extends GuiScreen
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

	public GuiNextBattle()
	{
	}

	@Override
	public void initGui()
	{
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.buttonList.clear();

		nextBattleButton = new GuiButton(0, posX + 66, posY + 203, 60, 11, "RivalRebels.nextbattle.yes");
		waitButton = new GuiButton(1, posX + 128, posY + 203, 60, 11, "RivalRebels.nextbattle.no");
		this.buttonList.add(nextBattleButton);
		this.buttonList.add(waitButton);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

    @Override
	public void handleKeyboardInput()
	{

	}

	@Override
	public void drawScreen(int x, int y, float d)
	{
		count++;
		if (count == 60)
		{
			num = 1 - num;
			count = 0;
		}
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        float f = 0.00390625F;
		if (num == 0) this.mc.renderEngine.bindTexture(RivalRebels.guitwarning0);
		if (num == 1) this.mc.renderEngine.bindTexture(RivalRebels.guitwarning1);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY + ySizeOfTexture, zLevel).tex(0, ySizeOfTexture * f).endVertex();
		buffer.pos(posX + xSizeOfTexture, posY + ySizeOfTexture, zLevel).tex(xSizeOfTexture * f, ySizeOfTexture * f).endVertex();
		buffer.pos(posX + xSizeOfTexture, posY, zLevel).tex(xSizeOfTexture * f, 0).endVertex();
		buffer.pos(posX, posY, zLevel).tex(0, 0).endVertex();
		tessellator.draw();
		drawCenteredString(fontRenderer, I18n.format("RivalRebels.nextbattle.subtitle"), (this.width / 2), (this.height / 2 - 120), 0xffffff);
		float scalefactor = 4f;
		GlStateManager.scale(scalefactor, scalefactor, scalefactor);
		drawCenteredString(fontRenderer, I18n.format("RivalRebels.nextbattle.title"), (int) ((this.width / 2) / scalefactor), (int) ((this.height / 2 - 100) / scalefactor), 0xffffff);
		GlStateManager.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
		fontRenderer.drawSplitString(I18n.format("RivalRebels.nextbattle.question"), posX + 64, posY + 160, 128, 0xffffff);
		super.drawScreen(x, y, d);

		if (Mouse.isButtonDown(0) && !prevclick)
		{
			if (nextBattleButton.mousePressed(mc, x, y))
			{
				//RivalRebelsClientPacketHandler.sendPacket(5, 0);
				PacketDispatcher.packetsys.sendToServer(new VotePacket(true));
				this.mc.displayGuiScreen(null);
			}
			if (waitButton.mousePressed(mc, x, y))
			{
				//RivalRebelsClientPacketHandler.sendPacket(5, 1);
				PacketDispatcher.packetsys.sendToServer(new VotePacket(false));
				this.mc.displayGuiScreen(null);
			}
		}
		prevclick = Mouse.isButtonDown(0);
	}
}

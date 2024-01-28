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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.guihelper.GuiButton;
import assets.rivalrebels.client.guihelper.GuiScroll;
import assets.rivalrebels.common.packet.JoinTeamPacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.ResetPacket;
import assets.rivalrebels.common.round.RivalRebelsClass;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static net.minecraftforge.client.gui.GuiUtils.drawGradientRect;

public class GuiSpawn extends Screen
{
	private final int	xSizeOfTexture	= 256;
	private final int	ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
	private GuiButton	classButton;
	private GuiButton	resetButton;
	private GuiButton	omegaButton;
	private GuiButton	sigmaButton;
	private GuiScroll	omegaScroll;
	private GuiScroll	sigmaScroll;
	private GuiScroll	playerScroll;
	private GuiScroll	gameScroll;
	private boolean		prevClick		= true;
	private final RivalRebelsClass rrclass;

	public GuiSpawn(RivalRebelsClass rrc)
	{
        super(Text.of(""));
        posX = (width - xSizeOfTexture) / 2;
		posY = (height - ySizeOfTexture) / 2;
		rrclass = rrc;
	}

	@Override
	public void init()
	{
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.drawables.clear();

		classButton = new GuiButton(posX + 188, posY + 102, 60, 11, new TranslatableText("RivalRebels.spawn.class"));
		resetButton = new GuiButton(posX + 188, posY + 119, 60, 11, new TranslatableText("RivalRebels.spawn.reset"));
		omegaButton = new GuiButton(posX + 35, posY + 237, 60, 11, new TranslatableText("RivalRebels.spawn.joinomega"));
		sigmaButton = new GuiButton(posX + 160, posY + 237, 60, 11, new TranslatableText("RivalRebels.spawn.joinsigma"));
		omegaScroll = new GuiScroll(posX + 118, posY + 140, 80);
		sigmaScroll = new GuiScroll(posX + 243, posY + 140, 80);
		playerScroll = new GuiScroll(posX + 154, posY + 103, 16);
		gameScroll = new GuiScroll(posX + 243, posY + 66, 16);
		RivalRebelsPlayer nw = RivalRebels.round.rrplayerlist.getForGameProfile(MinecraftClient.getInstance().player.getGameProfile());
		resetButton.active = nw.resets > 0 && !nw.isreset;
		omegaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.OMEGA;
		sigmaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.SIGMA;
		classButton.active = nw.isreset;
		this.addDrawable(classButton);
		this.addDrawable(resetButton);
		this.addDrawable(omegaButton);
		this.addDrawable(sigmaButton);
		this.addDrawable(omegaScroll);
		this.addDrawable(sigmaScroll);
		this.addDrawable(playerScroll);
		this.addDrawable(gameScroll);
	}

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RivalRebelsPlayer nw = RivalRebels.round.rrplayerlist.getForGameProfile(MinecraftClient.getInstance().player.getGameProfile());
		classButton.active = nw.isreset;
		omegaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.OMEGA;
		sigmaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.SIGMA;
		resetButton.active = nw.resets > 0 && !nw.isreset;
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        float f = 0.00390625F;
		renderBackground(matrices);
		drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), posX, posY, posX + xSizeOfTexture, posY + ySizeOfTexture, 0xFF000000, 0xFF000000); // 0xFF587075, 0xFF041010);
		drawPanel(matrices, posX + 10, posY + 142, 80, omegaScroll.getScroll(), omegaScroll.limit, RivalRebelsTeam.OMEGA);
		drawPanel(matrices, posX + 135, posY + 142, 80, sigmaScroll.getScroll(), sigmaScroll.limit, RivalRebelsTeam.SIGMA);
		drawPanel(matrices, posX + 10, posY + 68, 228, 50, gameScroll.getScroll(), gameScroll.limit, RivalRebels.round.getMotD() + "\nMod by Rodolphito. \nVisit www.RivalRebels.com for official downloads.");
		drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), posX + 6, posY + 99, posX + 161, posY + 131, 0xFF000000, 0xFF000000);
		drawPanel(matrices, posX + 10, posY + 105, 50, playerScroll.getScroll(), playerScroll.limit, new String[] { rrclass.name }, new int[] { rrclass.color });

		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, RRIdentifiers.guitspawn);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		buffer.vertex(posX, posY + ySizeOfTexture, getZOffset()).texture(0, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY + ySizeOfTexture, getZOffset()).texture(xSizeOfTexture * f, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY, getZOffset()).texture(xSizeOfTexture * f, 0).next();
		buffer.vertex(posX, posY, getZOffset()).texture(0, 0).next();
		tessellator.draw();

		if (RRIdentifiers.banner != null) {
            RenderSystem.setShaderTexture(0, RRIdentifiers.banner);
			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			buffer.vertex(posX + 3, posY + 61, getZOffset()).texture(0, 1).next();
			buffer.vertex(posX + 253, posY + 61, getZOffset()).texture(1, 1).next();
			buffer.vertex(posX + 253, posY + 3, getZOffset()).texture(1, 0).next();
			buffer.vertex(posX + 3, posY + 3, getZOffset()).texture(0, 0).next();
			tessellator.draw();
		}

		super.render(matrices, mouseX, mouseY, delta);

		textRenderer.draw(matrices, String.valueOf(RivalRebels.round.getOmegaWins()), posX + 9, posY + 239, 0xFFFFFF);
		textRenderer.draw(matrices, String.valueOf(RivalRebels.round.getSigmaWins()), posX + 134, posY + 239, 0xFFFFFF);

		if (resetButton.mouseClicked(mouseX, mouseY, 0) && resetButton.active)
		{
			drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), mouseX, mouseY, mouseX + 120, mouseY + 20, 0xaa111111, 0xaa111111);
			float scalefactor = 0.666f;
			matrices.scale(scalefactor, scalefactor, scalefactor);
			textRenderer.drawTrimmed(new TranslatableText("RivalRebels.spawn.resetwarning"), (int) ((mouseX + 2) / scalefactor), (int) ((mouseY + 2) / scalefactor), (int) (116 / scalefactor), 0xFF0000);
			matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
		}

		if (client.mouse.wasLeftButtonClicked() && !prevClick)
		{
			if (classButton.mouseClicked(mouseX, mouseY, 0)) this.client.setScreen(new GuiClass(rrclass));
			if (resetButton.mouseClicked(mouseX, mouseY, 0))
			{
				this.client.setScreen(new GuiClass(rrclass));
				PacketDispatcher.packetsys.sendToServer(new ResetPacket());
			}
			if (omegaButton.mouseClicked(mouseX, mouseY, 0))
			{
				PacketDispatcher.packetsys.sendToServer(new JoinTeamPacket(rrclass, RivalRebelsTeam.OMEGA));
				this.client.setScreen(null);
			}
			if (sigmaButton.mouseClicked(mouseX, mouseY, 0))
			{
				PacketDispatcher.packetsys.sendToServer(new JoinTeamPacket(rrclass, RivalRebelsTeam.SIGMA));
				this.client.setScreen(null);
			}
		}
		prevClick = client.mouse.wasLeftButtonClicked();
	}

	protected void drawPanel(MatrixStack matrices, int x, int y, int height, int scroll, int scrolllimit, RivalRebelsTeam team)
	{
		RivalRebelsPlayer[] nlist = new RivalRebelsPlayer[RivalRebels.round.rrplayerlist.getSize()];
		RivalRebelsPlayer[] list = RivalRebels.round.rrplayerlist.getArray();

		int index = 0;
		for (int i = 0; i < nlist.length; i++)
		{
			if (isOnline(list[i].profile) && list[i].rrteam.equals(team))
			{
				nlist[index] = list[i];
				index++;
			}
		}
		for (int i = 0; i < nlist.length; i++)
		{
			if (!isOnline(list[i].profile) && list[i].rrteam.equals(team))
			{
				nlist[index] = list[i];
				index++;
			}
		}
		if (index == 0) return;
		int dist = (int) (-((float) scroll / (float) scrolllimit) * ((index * 10) - height));
		boolean shouldScroll = index * 10 > height;
		for (int i = 0; i < nlist.length; i++)
		{
			if (nlist[i] == null) break;
			int Y = dist + (i * 10);
			if (!shouldScroll) Y -= dist;
			if (Y > -9 && Y < height + 9)
			{
				int color = nlist[i].rrclass.color;
				int r = (color & 0xFF0000) >> 16;
				int g = (color & 0xFF00) >> 8;
				int b = (color & 0xFF);
				if (!isOnline(nlist[i].profile))
				{
					r /= 2;
					g /= 2;
					b /= 2;
				}
				color = (r << 16) | (g << 8) | b;
                textRenderer.draw(matrices, nlist[i].getUsername(), x, y + Y, color);
			}
		}
	}

	protected void drawPanel(MatrixStack matrices, int x, int y, int height, int scroll, int scrolllimit, String[] display, int[] color)
	{
		int dist = (int) (-((float) scroll / (float) scrolllimit) * (((display.length) * 10) - height));
		boolean shouldScroll = (display.length) * 10 > height;
		for (int i = 0; i < display.length; i++)
		{
			int Y = dist + (i * 10);
			if (!shouldScroll) Y -= dist;
			if (Y > -9 && Y < height + 9) textRenderer.draw(matrices, new TranslatableText(display[i]), x, y + Y, color[i]);
		}
	}

	protected void drawPanel(MatrixStack matrices, int x, int y, int width, int height, int scroll, int scrolllimit, String display)
	{
		int length = 10;
		int dist = (int) (-((float) scroll / (float) scrolllimit) * (((length) * 10) - height));
		float scalefactor = 0.6666f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
		textRenderer.drawTrimmed(Text.of(display), (int) (x * 1.5), (int) ((y + dist) * 1.5), (int) (width * 1.5), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
	}

	protected boolean isOnline(GameProfile user)
	{
        if (client == null || client.getNetworkHandler() == null || client.getNetworkHandler().getPlayerList().isEmpty()) return false;
        for (PlayerListEntry guiPlayerInfo : client.getNetworkHandler().getPlayerList()) {
            if (user.equals(guiPlayerInfo.getProfile())) return true;
        }
		return false;
	}
}

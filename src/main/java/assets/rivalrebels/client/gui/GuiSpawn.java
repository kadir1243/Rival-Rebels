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
import assets.rivalrebels.common.packet.ResetPacket;
import assets.rivalrebels.common.round.RivalRebelsClass;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.mixin.client.DrawContextAccessor;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

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
        super(Text.empty());
        posX = (width - xSizeOfTexture) / 2;
		posY = (height - ySizeOfTexture) / 2;
		rrclass = rrc;
	}

	@Override
	public void init()
	{
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
        this.clearChildren();

		classButton = new GuiButton(posX + 188, posY + 102, 60, 11, Text.translatable("RivalRebels.spawn.class"));
		resetButton = new GuiButton(posX + 188, posY + 119, 60, 11, Text.translatable("RivalRebels.spawn.reset"));
		omegaButton = new GuiButton(posX + 35, posY + 237, 60, 11, Text.translatable("RivalRebels.spawn.joinomega"));
		sigmaButton = new GuiButton(posX + 160, posY + 237, 60, 11, Text.translatable("RivalRebels.spawn.joinsigma"));
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
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MatrixStack matrices = context.getMatrices();
        RivalRebelsPlayer nw = RivalRebels.round.rrplayerlist.getForGameProfile(MinecraftClient.getInstance().player.getGameProfile());
		classButton.active = nw.isreset;
		omegaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.OMEGA;
		sigmaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.SIGMA;
		resetButton.active = nw.resets > 0 && !nw.isreset;
        float f = 0.00390625F;
		renderBackgroundTexture(context);
        context.fillGradient(posX, posY, posX + xSizeOfTexture, posY + ySizeOfTexture, Colors.BLACK, Colors.BLACK); // 0xFF587075, 0xFF041010);
		drawPanel(context, posX + 10, posY + 142, 80, omegaScroll.getScroll(), omegaScroll.limit, RivalRebelsTeam.OMEGA);
		drawPanel(context, posX + 135, posY + 142, 80, sigmaScroll.getScroll(), sigmaScroll.limit, RivalRebelsTeam.SIGMA);
		drawPanel(context, posX + 10, posY + 68, 228, 50, gameScroll.getScroll(), gameScroll.limit, RivalRebels.round.getMotD() + "\nMod by Rodolphito. \nVisit www.RivalRebels.com for official downloads.");
        context.fillGradient(posX + 6, posY + 99, posX + 161, posY + 131, 0xFF000000, 0xFF000000);
		drawPanel(context, posX + 10, posY + 105, 50, playerScroll.getScroll(), playerScroll.limit, new String[] { rrclass.name }, new int[] { rrclass.color });

        ((DrawContextAccessor) context).callDrawTexturedQuad(
            RRIdentifiers.guitspawn,
            posX,
            posX + xSizeOfTexture,
            posY,
            posY + ySizeOfTexture,
            0, // z offset
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f,
            0,
            1F, 1F, 1F, 1F
        );

        if (RRIdentifiers.banner != null) {
            ((DrawContextAccessor) context).callDrawTexturedQuad(
                RRIdentifiers.banner,
                posX + 3,
                posX + 253,
                posY + 3,
                posY + 61,
                0, // z offset
                0,
                1,
                1,
                0
            );
        }

		super.render(context, mouseX, mouseY, delta);

		context.drawText(textRenderer, String.valueOf(RivalRebels.round.getOmegaWins()), posX + 9, posY + 239, 0xFFFFFF, false);
		context.drawText(textRenderer, String.valueOf(RivalRebels.round.getSigmaWins()), posX + 134, posY + 239, 0xFFFFFF, false);

		if (resetButton.mouseClicked(mouseX, mouseY, 0) && resetButton.active)
		{
            context.fillGradient(mouseX, mouseY, mouseX + 120, mouseY + 20, 0xaa111111, 0xaa111111);
			float scalefactor = 0.666f;
			matrices.scale(scalefactor, scalefactor, scalefactor);
            MultilineText.create(textRenderer, Text.translatable("RivalRebels.spawn.resetwarning"), (int) (116 / scalefactor)).draw(context, (int) ((mouseX + 2) / scalefactor), (int) ((mouseY + 2) / scalefactor), textRenderer.fontHeight, 0xFF0000);
			matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
		}

		if (client.mouse.wasLeftButtonClicked() && !prevClick)
		{
			if (classButton.mouseClicked(mouseX, mouseY, 0)) this.client.setScreen(new GuiClass(rrclass));
			if (resetButton.mouseClicked(mouseX, mouseY, 0))
			{
				this.client.setScreen(new GuiClass(rrclass));
                ClientPlayNetworking.send(new ResetPacket());
			}
			if (omegaButton.mouseClicked(mouseX, mouseY, 0))
			{
                ClientPlayNetworking.send(new JoinTeamPacket(rrclass, RivalRebelsTeam.OMEGA));
				this.client.setScreen(null);
			}
			if (sigmaButton.mouseClicked(mouseX, mouseY, 0))
			{
                ClientPlayNetworking.send(new JoinTeamPacket(rrclass, RivalRebelsTeam.SIGMA));
				this.client.setScreen(null);
			}
		}
		prevClick = client.mouse.wasLeftButtonClicked();
	}

	protected void drawPanel(DrawContext context, int x, int y, int height, int scroll, int scrolllimit, RivalRebelsTeam team)
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
                context.drawText(textRenderer, nlist[i].getUsername(), x, y + Y, color, false);
			}
		}
	}

	protected void drawPanel(DrawContext context, int x, int y, int height, int scroll, int scrolllimit, String[] display, int[] color)
	{
		int dist = (int) (-((float) scroll / (float) scrolllimit) * (((display.length) * 10) - height));
		boolean shouldScroll = (display.length) * 10 > height;
		for (int i = 0; i < display.length; i++)
		{
			int Y = dist + (i * 10);
			if (!shouldScroll) Y -= dist;
			if (Y > -9 && Y < height + 9) context.drawText(textRenderer, Text.translatable(display[i]), x, y + Y, color[i], false);
		}
	}

	protected void drawPanel(DrawContext context, int x, int y, int width, int height, int scroll, int scrolllimit, String display)
	{
        MatrixStack matrices = context.getMatrices();
        int length = 10;
		int dist = (int) (-((float) scroll / (float) scrolllimit) * (((length) * 10) - height));
		float scalefactor = 0.6666f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
        MultilineText.create(textRenderer, Text.of(display), (int) (width * 1.5)).draw(context, (int) (x * 1.5), (int) ((y + dist) * 1.5), textRenderer.fontHeight, 0xffffff);
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

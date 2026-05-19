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

import com.mojang.blaze3d.platform.InputConstants;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiButton;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiScroll;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.packet.JoinTeamPacket;
import io.github.kadir1243.rivalrebels.common.packet.ResetPacket;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsClass;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class GuiSpawn extends Screen {
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
	private final RivalRebelsClass rrclass;

	public GuiSpawn(RivalRebelsClass rrc)
	{
        super(Component.empty());
        posX = (width - xSizeOfTexture) / 2;
		posY = (height - ySizeOfTexture) / 2;
		rrclass = rrc;
	}

	@Override
	public void init() {
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;

		classButton = new GuiButton(posX + 188, posY + 102, 60, 11, Translations.SELECT_CLASS_TITLE.translate(), button -> this.minecraft.setScreen(new GuiClass(rrclass)));
		resetButton = new GuiButton(posX + 188, posY + 119, 60, 11, Translations.SPAWN_RESET.translate(), button -> {
            this.minecraft.setScreen(new GuiClass(rrclass));
            Minecraft.getInstance().getConnection().send(ResetPacket.INSTANCE);
        });
		omegaButton = new GuiButton(posX + 35, posY + 237, 60, 11, Translations.JOIN_OMEGA.translate(), button -> {
            Minecraft.getInstance().getConnection().send(new JoinTeamPacket(rrclass, RivalRebelsTeam.OMEGA));
            onClose();
        });
		sigmaButton = new GuiButton(posX + 160, posY + 237, 60, 11, Translations.JOIN_SIGMA.translate(), button -> {
            Minecraft.getInstance().getConnection().send(new JoinTeamPacket(rrclass, RivalRebelsTeam.SIGMA));
            onClose();
        });
		omegaScroll = (GuiScroll) Button.builder(Component.empty(), button -> {}).bounds(posX + 118, posY + 140, 5, 11).build(builder -> new GuiScroll(builder, 80));
		sigmaScroll = (GuiScroll) Button.builder(Component.empty(), button -> {}).bounds(posX + 243, posY + 140, 5, 11).build(builder -> new GuiScroll(builder, 80));
		playerScroll = (GuiScroll) Button.builder(Component.empty(), button -> {}).bounds(posX + 154, posY + 103, 5, 11).build(builder -> new GuiScroll(builder, 16));
		gameScroll = (GuiScroll) Button.builder(Component.empty(), button -> {}).bounds(posX + 243, posY + 66, 5, 11).build(builder -> new GuiScroll(builder, 16));
		RivalRebelsPlayer nw = RivalRebels.round.rrplayerlist.getForGameProfile(minecraft.player.getGameProfile());
		resetButton.active = nw.resets > 0 && !nw.isreset;
		omegaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.OMEGA;
		sigmaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.SIGMA;
		classButton.active = nw.isreset;
		this.addRenderableWidget(classButton);
		this.addRenderableWidget(resetButton);
		this.addRenderableWidget(omegaButton);
		this.addRenderableWidget(sigmaButton);
		this.addRenderableWidget(omegaScroll);
		this.addRenderableWidget(sigmaScroll);
		this.addRenderableWidget(playerScroll);
		this.addRenderableWidget(gameScroll);
	}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        RivalRebelsPlayer nw = RivalRebels.round.rrplayerlist.getForGameProfile(minecraft.player.getGameProfile());
		classButton.active = nw.isreset;
		omegaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.OMEGA;
		sigmaButton.active = nw.rrteam == RivalRebelsTeam.NONE || nw.rrteam == RivalRebelsTeam.SIGMA;
		resetButton.active = nw.resets > 0 && !nw.isreset;
        float f = 0.00390625F;
		extractTransparentBackground(graphics);
        graphics.fillGradient(posX, posY, posX + xSizeOfTexture, posY + ySizeOfTexture, CommonColors.BLACK, CommonColors.BLACK); // 0xFF587075, 0xFF041010);
		drawPanel(graphics, posX + 10, posY + 142, 80, omegaScroll.getScroll(), omegaScroll.limit, RivalRebelsTeam.OMEGA);
		drawPanel(graphics, posX + 135, posY + 142, 80, sigmaScroll.getScroll(), sigmaScroll.limit, RivalRebelsTeam.SIGMA);
		drawPanel(graphics, posX + 10, posY + 68, 228, 50, gameScroll.getScroll(), gameScroll.limit, RivalRebels.round.getMotD() + "\nMod by Rodolphito. \nVisit www.RivalRebels.com for official downloads.");
        graphics.fillGradient(posX + 6, posY + 99, posX + 161, posY + 131, CommonColors.BLACK, CommonColors.BLACK);
		drawPanel(graphics, posX + 10, posY + 105, 50, playerScroll.getScroll(), playerScroll.limit, new String[] { rrclass.name }, new int[] { rrclass.color });

        graphics.blit(
            RRTextures.guitspawn,
            posX,
            posY,
            posX + xSizeOfTexture,
            posY + ySizeOfTexture,
            0, // z offset
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f
        );

        if (RRIdentifiers.banner != null) {
            graphics.blit(
                RRIdentifiers.banner,
                posX + 3,
                posY + 3,
                posX + 253,
                posY + 61,
                0, // z offset
                0,
                1,
                1
            );
        }

		super.extractRenderState(graphics, mouseX, mouseY, a);

		graphics.text(font, Component.literal(String.valueOf(RivalRebels.round.getOmegaWins())), posX + 9, posY + 239, CommonColors.WHITE, false);
		graphics.text(font, Component.literal(String.valueOf(RivalRebels.round.getSigmaWins())), posX + 134, posY + 239, CommonColors.WHITE, false);

		if (resetButton.mouseClicked(new MouseButtonEvent(mouseX, mouseY, new MouseButtonInfo(InputConstants.MOUSE_BUTTON_LEFT, 0)), false) && resetButton.active) {
            graphics.fillGradient(mouseX, mouseY, mouseX + 120, mouseY + 20, 0xaa111111, 0xaa111111);
			float scalefactor = 0.666f;
            graphics.pose().pushMatrix();
            graphics.pose().scale(scalefactor, scalefactor);
            MultiLineLabel.create(font, Translations.SPAWN_RESET_WARNING.translate().withColor(CommonColors.RED), (int) (116 / scalefactor)).visitLines(TextAlignment.LEFT, (int) ((mouseX + 2) / scalefactor), (int) ((mouseY + 2) / scalefactor), font.lineHeight, graphics.textRenderer());
            graphics.pose().popMatrix();
		}
    }

	protected void drawPanel(GuiGraphicsExtractor graphics, int x, int y, int height, float scroll, float scrolllimit, RivalRebelsTeam team) {
        List<RivalRebelsPlayer> newList;

        newList = RivalRebels.round.rrplayerlist.players().stream().filter(player -> player.rrteam.equals(team)).collect(Collectors.toList());
		if (newList.isEmpty()) return;
        newList.sort(Comparator.comparing(this::isOnline));

        int dist = (int) (-(scroll / scrolllimit) * ((newList.size() * 10) - height));
		boolean shouldScroll = newList.size() * 10 > height;
        for (int i = 0; i < newList.size(); i++) {
            RivalRebelsPlayer player = newList.get(i);
			if (player == null) break;
			int Y = dist + (i * 10);
			if (!shouldScroll) Y -= dist;
			if (Y > -9 && Y < height + 9)
			{
				int color = player.rrclass.color;
				int r = ARGB.red(color);
				int g = ARGB.green(color);
				int b = ARGB.blue(color);
				if (!isOnline(player))
				{
					r /= 2;
					g /= 2;
					b /= 2;
				}
                color = ARGB.color(r, g, b);
                graphics.text(font, Component.literal(player.getUsername()), x, y + Y, color, false);
			}
		}
	}

	protected void drawPanel(GuiGraphicsExtractor graphics, int x, int y, int height, float scroll, float scrolllimit, String[] display, int[] color)
	{
		int dist = (int) (-(scroll / scrolllimit) * (((display.length) * 10) - height));
		boolean shouldScroll = (display.length) * 10 > height;
		for (int i = 0; i < display.length; i++)
		{
			int Y = dist + (i * 10);
			if (!shouldScroll) Y -= dist;
			if (Y > -9 && Y < height + 9) graphics.text(font, Component.literal(display[i]), x, y + Y, color[i], false);
		}
	}

	protected void drawPanel(GuiGraphicsExtractor graphics, int x, int y, int width, int height, float scroll, float scrolllimit, String display) {
        int length = 10;
		int dist = (int) (-(scroll / scrolllimit) * (((length) * 10) - height));
		float scalefactor = 0.6666f;
        graphics.pose().pushMatrix();
        graphics.pose().scale(scalefactor, scalefactor);
        MultiLineLabel.create(font, Component.literal(display), (int) (width * 1.5)).visitLines(TextAlignment.LEFT, (int) (x * 1.5), (int) ((y + dist) * 1.5), font.lineHeight, graphics.textRenderer());
        graphics.pose().popMatrix();
	}

	protected boolean isOnline(RivalRebelsPlayer user)
	{
        if (minecraft == null || minecraft.getConnection() == null || minecraft.getConnection().getOnlinePlayers().isEmpty()) return false;
        for (PlayerInfo guiPlayerInfo : minecraft.getConnection().getOnlinePlayers()) {
            if (user.profile.equals(guiPlayerInfo.getProfile())) return true;
        }
		return false;
	}
}

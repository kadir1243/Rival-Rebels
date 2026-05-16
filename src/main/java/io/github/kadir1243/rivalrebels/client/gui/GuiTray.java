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
import io.github.kadir1243.rivalrebels.client.guihelper.GuiCustomButton;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiDropdownOption;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiRotor;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.client.renderhelper.TrayModelPIPRenderState;
import io.github.kadir1243.rivalrebels.common.container.ContainerReciever;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Vector2i;

@OnlyIn(Dist.CLIENT)
public class GuiTray extends AbstractContainerScreen<ContainerReciever> {
    private float				xSize_lo;
	private float				ySize_lo;
    private GuiRotor range;
    private GuiCustomButton chip;
    private GuiCustomButton players;
    private GuiCustomButton mobs;
    private GuiDropdownOption select1;

	public GuiTray(ContainerReciever containerReciever, Inventory inventoryPlayer, Component title)
	{
		super(containerReciever, inventoryPlayer, title, DEFAULT_IMAGE_WIDTH, 206);
	}

	@Override
	public void init() {
		super.init();
		int x = this.leftPos;
		int y = this.topPos;
		range = new GuiRotor(x + 93 - 16, y + 92 - 16, menu.getYawLimit(), Component.literal("Range"));
		chip = new GuiCustomButton(new ScreenRectangle(x + 94, y + 10, 19, 19), RRTextures.guitray, new Vector2i(237, 10), true);
		chip.isPressed = menu.getKTeam();
		players = new GuiCustomButton(new ScreenRectangle(x + 94, y + 28, 19, 19), RRTextures.guitray, new Vector2i(237, 28), true);
		players.isPressed = menu.getKPlayers();
		mobs = new GuiCustomButton(new ScreenRectangle(x + 94, y + 46, 19, 19), RRTextures.guitray, new Vector2i(237, 46), true);
		mobs.isPressed = menu.getKMobs();
		select1 = new GuiDropdownOption(new Vector2i(119 + x, 8 + y), 45, 0, Translations.ADS_DRAGON.translate(), button -> {
            if (menu.hasWepReqs()) {
                menu.setWep(true);
            }
        }, this);
		addRenderableWidget(range);
		addRenderableWidget(chip);
		addRenderableWidget(players);
		addRenderableWidget(mobs);
		addRenderableWidget(select1);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.xSize_lo = mouseX;
        this.ySize_lo = mouseY;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
		if ((event.modifiers() & InputConstants.MOD_SHIFT) != 0) {
            onClose();
            menu.setKMobs(mobs.isPressed);
            menu.setKTeam(chip.isPressed);
            menu.setKPlayers(players.isPressed);
            menu.setYawLimit(range.getDegree() * 2);
		}
        return super.keyPressed(event);
	}

	static int spinfac	= 0;

	public void drawADS(GuiGraphicsExtractor graphics, int x, int y, int scale, float px, float py) {
		spinfac += 1;
        graphics.submitPictureInPictureRenderState(new TrayModelPIPRenderState(new Vec3(x, y - 40, 50), menu.hasWeapon(), spinfac, x, y, (int) px, (int) py, scale, graphics.peekScissorStack()));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
        RRTextures.guitray.blit(graphics, x, y, 0, 0, imageWidth, imageHeight, CommonColors.WHITE);

		if (menu.getPInR() > 0) {
            RRTextures.guitray.blit(graphics, x + 104, y + 68, 248, 0, 8, 8, CommonColors.WHITE);
		}

		graphics.text(font, Translations.ADS_TRAY.translate(), x + 25, y + 66, 0xffffff, false);
		drawADS(graphics, this.leftPos + 51, this.topPos + 75, 30, this.leftPos + 51 - this.xSize_lo, this.topPos + 25 - this.ySize_lo);
	}
}

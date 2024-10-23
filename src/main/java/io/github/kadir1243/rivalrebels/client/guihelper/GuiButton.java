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
package io.github.kadir1243.rivalrebels.client.guihelper;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

@OnlyIn(Dist.CLIENT)
public class GuiButton extends Button {
    protected static final WidgetSprites SPRITES = new WidgetSprites(
        RRIdentifiers.button_enabled,
        RRIdentifiers.button_disabled,
        RRIdentifiers.button_hovered
    );
    public GuiButton(int x, int y, int width, int height, Component message) {
        this(x, y, width, height, message, button -> {});
    }

    public GuiButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    public GuiButton(Builder builder) {
        super(builder);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        Minecraft client = Minecraft.getInstance();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        graphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        int i = getFGColor();
        this.renderString(graphics, client.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}

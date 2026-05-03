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

import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

@OnlyIn(Dist.CLIENT)
public class GuiButton extends Button {
    protected static final WidgetSprites SPRITES = new WidgetSprites(
        RRTextures.button_enabled,
        RRTextures.button_disabled,
        RRTextures.button_hovered
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
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blitSprite(
            RenderPipelines.GUI_TEXTURED,
            SPRITES.get(this.active, this.isHoveredOrFocused()),
            this.getX(),
            this.getY(),
            this.getWidth(),
            this.getHeight(),
            ARGB.white(this.alpha)
        );

        this.extractDefaultLabel(graphics.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.NONE));
    }
}

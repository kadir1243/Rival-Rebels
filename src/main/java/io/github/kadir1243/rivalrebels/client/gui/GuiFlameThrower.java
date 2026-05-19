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

import io.github.kadir1243.rivalrebels.RRClient;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiFTKnob;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.item.components.FlameThrowerMode;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.packet.ItemUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.KeyEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class GuiFlameThrower extends Screen {
    private final int xSizeOfTexture = 256;
    private final int ySizeOfTexture = 256;
    private int posX;
    private int posY;
    private GuiFTKnob knob;
    private final int start;

    public GuiFlameThrower(int start) {
        super(Component.empty());
        this.start = start;
    }

    @Override
    public void init() {
        posX = (width - xSizeOfTexture) / 2;
        posY = (height - ySizeOfTexture) / 2;
        knob = (GuiFTKnob) Button.builder(Component.literal("Knob"), button -> {})
            .bounds(posX + 108, posY + 176, 36, 36)
            .build(builder -> new GuiFTKnob(builder, start));
        addRenderableWidget(knob);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean isInGameUi() {
        return true;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        float f = 0.00390625F;
        graphics.blit(
            RRTextures.guiflamethrower,
            posX,
            posY,
            posX + xSizeOfTexture,
            posY + ySizeOfTexture,
            0,
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f
        );
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        if (RRClient.USE_KEY.matches(event)) {
            onClose();
        }
        return super.keyReleased(event);
    }

    @Override
    public void onClose() {
        super.onClose();
        ItemStack stack = minecraft.player.getMainHandItem();
        if (stack.has(RRComponents.FLAME_THROWER_MODE)) {
            stack.set(RRComponents.FLAME_THROWER_MODE, new FlameThrowerMode(knob.getDegree()));
            Minecraft.getInstance().getConnection().send(new ItemUpdate(minecraft.player.getInventory().getSelectedSlot(), knob.getDegree()));
        }
    }
}

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
import io.github.kadir1243.rivalrebels.client.guihelper.GuiKnob;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.packet.ItemUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.KeyEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class GuiTesla extends Screen {
	private final int	xSizeOfTexture	= 256;
	private final int	ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
	private GuiKnob		knob;
	private final int s;

	public GuiTesla(int start) {
        super(Component.empty());
        s = start - 90;
	}

    @Override
    protected void init() {
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		knob = new GuiKnob(posX + 108, posY + 176, -90, 90, s, true, Component.literal("Knob"));
		this.addRenderableWidget(knob);
	}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        float f = 0.00390625F;
        graphics.blit(
            RRTextures.guitesla.location(),
            posX,
            posY,
            posX + xSizeOfTexture,
            posY + ySizeOfTexture,
            0,
            xSizeOfTexture * f,
            0,
            ySizeOfTexture * f
        );
	}

    @Override
    public boolean keyReleased(KeyEvent event) {
        if (RRClient.USE_KEY.matches(event)) {
            onClose();
            Minecraft.getInstance().getConnection().send(new ItemUpdate(minecraft.player.getInventory().getSelectedSlot(), knob.getDegree()));
            ItemStack stack = minecraft.player.getInventory().getSelectedItem();
            if (stack.has(RRComponents.TESLA_DIAL)) {
                stack.set(RRComponents.TESLA_DIAL, knob.getDegree());
            }
        }
        return super.keyReleased(event);
    }
}

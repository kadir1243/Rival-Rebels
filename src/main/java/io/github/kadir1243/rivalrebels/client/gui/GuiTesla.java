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
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiKnob;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.ItemTesla;
import io.github.kadir1243.rivalrebels.common.packet.ItemUpdate;
import io.github.kadir1243.rivalrebels.mixin.client.GuiGraphicsAccessor;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
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
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        float f = 0.00390625F;
        ((GuiGraphicsAccessor) context).blit(
            RRIdentifiers.guitesla,
            posX,
            posX + xSizeOfTexture,
            posY,
            posY + ySizeOfTexture,
            0, // z offset
            0,
            xSizeOfTexture * f,
            0,
            ySizeOfTexture * f
        );
	}

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (RRClient.USE_KEY.matches(keyCode, scanCode)) {
            onClose();
            this.minecraft.setWindowActive(true);
            Minecraft.getInstance().getConnection().send(new ItemUpdate(minecraft.player.getInventory().selected, knob.getDegree()));
            ItemStack stack = minecraft.player.getInventory().getItem(minecraft.player.getInventory().selected);
            if (stack.getItem() instanceof ItemTesla) {
                stack.set(RRComponents.TESLA_DIAL, knob.getDegree());
            }
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }
}

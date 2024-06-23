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

import assets.rivalrebels.ClientProxy;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.guihelper.GuiKnob;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.item.weapon.ItemTesla;
import assets.rivalrebels.common.packet.ItemUpdate;
import assets.rivalrebels.mixin.client.DrawContextAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class GuiTesla extends Screen
{
	private final int	xSizeOfTexture	= 256;
	private final int	ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
	private GuiKnob		knob;
	private final int s;

	public GuiTesla(int start)
	{
        super(Component.empty());
        s = start - 90;
	}

    @Override
    protected void init() {
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
        this.clearWidgets();
		knob = new GuiKnob(posX + 108, posY + 176, -90, 90, s, true, "Knob");
		this.addRenderableOnly(knob);
	}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        float f = 0.00390625F;
		minecraft = Minecraft.getInstance();
        ((DrawContextAccessor) context).callDrawTexturedQuad(
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
        super.render(context, mouseX, mouseY, delta);
		if (!(ClientProxy.USE_KEY.isDown()))
		{
			this.minecraft.setScreen(null);
			this.minecraft.setWindowActive(true);
            ClientPlayNetworking.send(new ItemUpdate(minecraft.player.getInventory().selected, knob.getDegree()));
			ItemStack stack = minecraft.player.getInventory().getItem(minecraft.player.getInventory().selected);
			if (stack.getItem() instanceof ItemTesla) {
                stack.set(RRComponents.TESLA_DIAL, knob.getDegree());
			}
		}
	}
}

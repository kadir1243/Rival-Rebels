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
import assets.rivalrebels.client.guihelper.GuiFTKnob;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.packet.ItemUpdate;
import assets.rivalrebels.mixin.client.DrawContextAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class GuiFlameThrower extends Screen
{
	private final int	xSizeOfTexture	= 256;
	private final int	ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
	private GuiFTKnob	knob;
	private final int start;

	public GuiFlameThrower(int start) {
        super(Text.empty());
        this.start = start;
	}

	@Override
	public void init() {
		posX = (width - xSizeOfTexture) / 2;
		posY = (height - ySizeOfTexture) / 2;
		clearChildren();
		knob = new GuiFTKnob(posX + 108, posY + 176, -90, 90, start, true, "Knob");
		addDrawable(knob);
	}

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		float f = 0.00390625F;
		client = MinecraftClient.getInstance();
        ((DrawContextAccessor) context).callDrawTexturedQuad(
            RRIdentifiers.guiflamethrower,
            posX,
            posX + xSizeOfTexture,
            posY,
            posY + ySizeOfTexture,
            0, // z offset
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f,
            0
        );
        super.render(context, mouseX, mouseY, delta);
		if (!(ClientProxy.USE_KEY.isPressed())) {
			client.setScreen(null);
			client.onWindowFocusChanged(true);
			ItemStack itemstack = client.player.getStackInHand(Hand.MAIN_HAND);
            if (itemstack.getItem() != RRItems.flamethrower) {
                itemstack = client.player.getStackInHand(Hand.OFF_HAND);
            }
			itemstack.getOrCreateNbt().putInt("mode", knob.getDegree());
            ClientPlayNetworking.send(new ItemUpdate(client.player.getInventory().selectedSlot, knob.getDegree()));
		}
	}
}

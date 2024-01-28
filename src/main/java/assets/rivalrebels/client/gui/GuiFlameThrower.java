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
import assets.rivalrebels.common.packet.PacketDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
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
        super(Text.of(""));
        this.start = start;
	}

	@Override
	public void init() {
		posX = (width - xSizeOfTexture) / 2;
		posY = (height - ySizeOfTexture) / 2;
		drawables.clear();
		knob = new GuiFTKnob(posX + 108, posY + 176, -90, 90, start, true, "Knob");
		addDrawable(knob);
	}

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Tessellator tessellator = Tessellator.getInstance();
		float f = 0.00390625F;
		client = MinecraftClient.getInstance();
		client.textureManager.bindTexture(RRIdentifiers.guiflamethrower);
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		buffer.vertex(posX, posY + ySizeOfTexture, getZOffset()).texture(0, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY + ySizeOfTexture, getZOffset()).texture(xSizeOfTexture * f, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY, getZOffset()).texture(xSizeOfTexture * f, 0).next();
		buffer.vertex(posX, posY, getZOffset()).texture(0, 0).next();
		tessellator.draw();
        super.render(matrices, mouseX, mouseY, delta);
		if (!(ClientProxy.USE_KEY.isPressed())) {
			client.setScreen(null);
			client.onWindowFocusChanged(true);
			ItemStack itemstack = client.player.getStackInHand(Hand.MAIN_HAND);
            if (itemstack.getItem() != RRItems.flamethrower) {
                itemstack = client.player.getStackInHand(Hand.OFF_HAND);
            }
			itemstack.getOrCreateNbt().putInt("mode", knob.getDegree());
			PacketDispatcher.packetsys.sendToServer(new ItemUpdate(client.player.getInventory().selectedSlot, knob.getDegree()));
		}
	}
}

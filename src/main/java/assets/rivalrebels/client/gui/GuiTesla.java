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
import assets.rivalrebels.common.item.weapon.ItemTesla;
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
        super(Text.of(""));
        s = start - 90;
	}

    @Override
    protected void init() {
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.drawables.clear();
		knob = new GuiKnob(posX + 108, posY + 176, -90, 90, s, true, "Knob");
		this.addDrawable(knob);
	}

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        float f = 0.00390625F;
		client = MinecraftClient.getInstance();
		client.textureManager.bindTexture(RRIdentifiers.guitesla);
		buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		buffer.vertex(posX, posY + ySizeOfTexture, getZOffset()).texture(0, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY + ySizeOfTexture, getZOffset()).texture(xSizeOfTexture * f, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY, getZOffset()).texture(xSizeOfTexture * f, 0).next();
		buffer.vertex(posX, posY, getZOffset()).texture(0, 0).next();
		tessellator.draw();
        super.render(matrices, mouseX, mouseY, delta);
		if (!(ClientProxy.USE_KEY.isPressed()))
		{
			this.client.setScreen(null);
			this.client.onWindowFocusChanged(true);
			PacketDispatcher.packetsys.sendToServer(new ItemUpdate(client.player.getInventory().selectedSlot, knob.getDegree()));
			ItemStack stack = client.player.getInventory().getStack(client.player.getInventory().selectedSlot);
			if (stack.getItem() instanceof ItemTesla) {
				stack.getOrCreateNbt().putInt("dial", knob.getDegree());
			}
		}
	}
}

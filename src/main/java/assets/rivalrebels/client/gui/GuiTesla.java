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

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.input.Keyboard;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.guihelper.GuiKnob;
import assets.rivalrebels.common.item.weapon.ItemTesla;
import assets.rivalrebels.common.packet.ItemUpdate;
import assets.rivalrebels.common.packet.PacketDispatcher;
import org.lwjgl.opengl.GL11;

public class GuiTesla extends GuiScreen
{
	private final int	xSizeOfTexture	= 256;
	private final int	ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
	private GuiKnob		knob;
	private final int start;

	public GuiTesla(int start)
	{
		this.start = start - 90;
	}

	@Override
	public void initGui()
	{
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.buttonList.clear();
		knob = new GuiKnob(0, posX + 108, posY + 176, -90, 90, start, true, "Knob");
		this.buttonList.add(knob);
		// mc.inGameHasFocus = true;
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

    @Override
	public void drawScreen(int x, int y, float d)
	{
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        float f = 0.00390625F;
		mc.renderEngine.bindTexture(RivalRebels.guitesla);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(posX, posY + ySizeOfTexture, zLevel).tex(0, ySizeOfTexture * f).endVertex();
		worldRenderer.pos(posX + xSizeOfTexture, posY + ySizeOfTexture, zLevel).tex(xSizeOfTexture * f, ySizeOfTexture * f).endVertex();
		worldRenderer.pos(posX + xSizeOfTexture, posY, zLevel).tex(xSizeOfTexture * f, 0).endVertex();
		worldRenderer.pos(posX, posY, zLevel).tex(0, 0).endVertex();
		tessellator.draw();
		super.drawScreen(x, y, d);
		if (!(RivalRebels.altRkey?Keyboard.isKeyDown(Keyboard.KEY_F):Keyboard.isKeyDown(Keyboard.KEY_R)))
		{
			this.mc.displayGuiScreen(null);
			this.mc.setIngameFocus();
			PacketDispatcher.packetsys.sendToServer(new ItemUpdate(mc.thePlayer.inventory.currentItem, knob.getDegree()));
			ItemStack itemstack = mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem);
			if (itemstack != null && itemstack.getItem() instanceof ItemTesla)
			{
				if (!itemstack.hasTagCompound()) itemstack.setTagCompound(new NBTTagCompound());
				itemstack.getTagCompound().setInteger("dial", knob.getDegree());
			}
		}
	}
}

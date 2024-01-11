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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.container.ContainerLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class GuiLoader extends GuiContainer
{

    /**
	 * window height is calculated with this values, the more rows, the heigher
	 */
	private int			inventoryRows	= 0;

	public GuiLoader(IInventory par1IInventory, IInventory par2IInventory)
	{
		super(new ContainerLoader(par1IInventory, par2IInventory));
        this.allowUserInput = false;
		short var3 = 222;
		int var4 = var3 - 108;
		this.inventoryRows = par2IInventory.getSizeInventory() / 9;
		this.ySize = var4 + this.inventoryRows * 18;
		this.xSize = 256;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		GlStateManager.pushMatrix();
		GlStateManager.rotate(-13, 0, 0, 1);
		fontRenderer.drawString("Loader", 165, 237, 0x444444);
		GlStateManager.popMatrix();
		int mousex = par1;
		int mousey = par2;
		int posx = (width - xSize) / 2;
		int posy = (height - ySize) / 2;
		int coordx = posx + 92;
		int coordy = posy + 202;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			drawGradientRect(mousex, mousey, mousex + fontRenderer.getStringWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			fontRenderer.drawString("rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF);
			if (!buttondown && Mouse.isButtonDown(0))
			{
                Sys.openURL("http://rivalrebels.com");
            }
		}
		buttondown = Mouse.isButtonDown(0);
	}

	boolean	buttondown;

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.guitloader);
		this.drawTexturedModalRect(width / 2 - 128, height / 2 - 103, 0, 0, 256, 210);
	}
}

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
import assets.rivalrebels.client.guihelper.GuiButton;
import assets.rivalrebels.common.container.ContainerLaptop;
import assets.rivalrebels.common.packet.LaptopButtonPacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SideOnly(Side.CLIENT)
public class GuiLaptop extends GuiContainer
{
	ContainerLaptop		container;
	TileEntityLaptop	lt;
	GuiButton			button;
	boolean				prevButtonDown;

	public GuiLaptop(Container container)
	{
		super(container);
		this.container = (ContainerLaptop) container;
	}

	public GuiLaptop(InventoryPlayer inventoryPlayer, TileEntityLaptop tileEntity)
	{
		super(new ContainerLaptop(inventoryPlayer, tileEntity));
		ySize = 206;
		lt = tileEntity;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		buttonList.clear();
		button = new GuiButton(0, x + 131, y + 89, 16, 16, "");
		buttonList.add(button);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.guilaptopnuke);

		if (lt.isReady()) this.drawTexturedModalRect(131, 89, 239, 9, 16, 16);
		else this.drawTexturedModalRect(131, 89, 131, 89, 16, 16);

		int mousex = par1;
		int mousey = par2;
		int posx = (width - xSize) / 2;
		int posy = (height - ySize) / 2;
		int coordx = posx + 53;
		int coordy = posy + 194;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			drawGradientRect(mousex, mousey, mousex + fontRendererObj.getStringWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			fontRendererObj.drawString("rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF);
			if (Desktop.isDesktopSupported() && !buttondown && Mouse.isButtonDown(0))
			{
				try
				{
					Desktop.getDesktop().browse(new URI("http://rivalrebels.com"));
				}
				catch (IOException | URISyntaxException e)
				{
					e.printStackTrace();
				}
            }
		}
		buttondown = Mouse.isButtonDown(0);
	}

	boolean	buttondown;

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor3f(1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.guilaptopnuke);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		if (lt.hasChips()) this.drawTexturedModalRect(x + 135, y + 79, 248, 0, 8, 8);
		Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("RivalRebels.controller.B83"), x + 118, y + 11, 0xffffff);
		Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("RivalRebels.controller.b2spirit"), x + 25, y + 11, 0xffffff);
		Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("x" + lt.b2spirit), x + 154, y + 96, 0xffffff);
		Minecraft.getMinecraft().fontRendererObj.drawString(StatCollector.translateToLocal("x" + lt.b2carpet), x + 154, y + 85, 0xffffff);
		if (button.mousePressed(mc, par2, par3) && Mouse.isButtonDown(0) && !prevButtonDown)
		{
			PacketDispatcher.packetsys.sendToServer(new LaptopButtonPacket(lt.getPos()));
		}
		prevButtonDown = Mouse.isButtonDown(0);
	}
}
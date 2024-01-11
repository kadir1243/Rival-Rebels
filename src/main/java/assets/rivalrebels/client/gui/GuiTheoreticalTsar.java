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
import assets.rivalrebels.common.container.ContainerTheoreticalTsar;
import assets.rivalrebels.common.tileentity.TileEntityTheoreticalTsarBomba;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class GuiTheoreticalTsar extends GuiContainer
{
	TileEntityTheoreticalTsarBomba	tsar;

	public GuiTheoreticalTsar(Container container)
	{
		super(container);
	}

	public GuiTheoreticalTsar(InventoryPlayer inventoryPlayer, TileEntityTheoreticalTsarBomba tileEntity)
	{
		super(new ContainerTheoreticalTsar(inventoryPlayer, tileEntity));
		ySize = 206;
		tsar = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		int seconds = (tsar.countdown / 20);
		int millis = (tsar.countdown % 20) * 3;
		String milli;
		if (millis < 10)
		{
			milli = "0" + millis;
		}
		else
		{
			milli = "" + millis;
		}
		if (tsar.countdown % 20 >= 10)
		{
			fontRenderer.drawString(I18n.format("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 6, ySize - 107, 0xFFFFFF);
		}
		else
		{
			fontRenderer.drawString(I18n.format("RivalRebels.tsar.timer") + ": -" + seconds + ":" + milli, 6, ySize - 107, 0xFF0000);
		}
		float scalef = 0.666f;
		GlStateManager.pushMatrix();
		GlStateManager.scale(scalef, scalef, scalef);
		fontRenderer.drawString(I18n.format("RivalRebels.tsar.tsar"), 18, 16, 4210752);
		GlStateManager.popMatrix();
		if (tsar.hasExplosive && tsar.hasFuse && tsar.hasAntennae)
		{
			fontRenderer.drawString(I18n.format("RivalRebels.tsar.armed"), 6, ySize - 97, 0xFF0000);
		}
		else
		{
			fontRenderer.drawString(tsar.megaton + " " + I18n.format("RivalRebels.tsar.megatons"), 6, ySize - 97, 0xFFFFFF);
		}

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

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.guitheoreticaltsar);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}

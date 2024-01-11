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
import assets.rivalrebels.client.guihelper.*;
import assets.rivalrebels.client.tileentityrender.TileEntityRecieverRenderer;
import assets.rivalrebels.common.container.ContainerReciever;
import assets.rivalrebels.common.packet.ADSClosePacket;
import assets.rivalrebels.common.packet.ADSWeaponPacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.tileentity.TileEntityReciever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class GuiTray extends GuiContainer {
	public TileEntityReciever	r;
	private float				xSize_lo;
	private float				ySize_lo;
	GuiRotor					range;
	GuiCustomButton				chip;
	GuiCustomButton				players;
	GuiCustomButton				mobs;
	GuiDropdownOption			select1;

	public GuiTray(Container container)
	{
		super(container);
	}

	public GuiTray(InventoryPlayer inventoryPlayer, TileEntityReciever tileEntity)
	{
		super(new ContainerReciever(inventoryPlayer, tileEntity));
		ySize = 206;
		r = tileEntity;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		buttonList.clear();
		range = new GuiRotor(0, x + 93 - 16, y + 92 - 16, r.yawLimit, "Range");
		chip = new GuiCustomButton(1, new Rectangle(x + 94, y + 10, 19, 19), RivalRebels.guitray, new Vector(237, 10), true);
		chip.isPressed = r.kTeam;
		players = new GuiCustomButton(2, new Rectangle(x + 94, y + 28, 19, 19), RivalRebels.guitray, new Vector(237, 28), true);
		players.isPressed = r.kPlayers;
		mobs = new GuiCustomButton(3, new Rectangle(x + 94, y + 46, 19, 19), RivalRebels.guitray, new Vector(237, 46), true);
		mobs.isPressed = r.kMobs;
		select1 = new GuiDropdownOption(4, new Vector(119 + x, 8 + y), 45, 0, "RivalRebels.ads.dragon", this);
		buttonList.add(range);
		buttonList.add(chip);
		buttonList.add(players);
		buttonList.add(mobs);
		buttonList.add(select1);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.xSize_lo = par1;
		this.ySize_lo = par2;
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par2 == 1)
		{
			this.mc.displayGuiScreen(null);
			this.mc.setIngameFocus();
			PacketDispatcher.packetsys.sendToServer(new ADSClosePacket(r.getPos(), mobs.isPressed, chip.isPressed, players.isPressed, range.getDegree()*2));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);

		if (select1.isPressed)
		{
			PacketDispatcher.packetsys.sendToServer(new ADSWeaponPacket(r.getPos(), 1));
		}
		select1.isPressed = false;

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

	boolean		buttondown;

	static int	spinfac	= 0;

	public void drawADS(int x, int y, int scale, float px, float py)
	{
		spinfac += 1;
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y - 40, 50);
		GlStateManager.scale(-scale, scale, scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(20, 1.0F, 0.0F, 0.0F);
		if (!r.hasWeapon)
		{
			GlStateManager.translate(0, 0, -0.5f);
			GlStateManager.rotate(spinfac, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0, 0, 0.5f);
		}
		// GL11.glRotated(Math.sin(spinfac/(40 * Math.PI)) * 10, 1.0F, 0.0F, 0.0F);

		// entity.rotationPitch = -((float)Math.atan((double)(py / 40.0F))) * 40.0F;
		// entity.rotationYawHead = (float)Math.atan((double)(px / 40.0F)) * 40.0F;
		// + (Math.sin(spinfac/(40 * Math.PI)) * 10)
		// - (spinfac * 0.5)

		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etreciever);
		GlStateManager.rotate(180, 0, 1, 0);
		// GL11.glRotated((spinfac * 0.5), 0, 1, 0);
		GlStateManager.translate(0, -0.5 * 1.5, (-0.5 - 0.34) * -1.5);
		TileEntityRecieverRenderer.base.render();
		if (r.hasWeapon)
		{
			GlStateManager.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			GlStateManager.rotate((float) (-Math.atan(px / 40.0F) * 40.0F), 0, 1, 0);
			TileEntityRecieverRenderer.arm.render();
			GlStateManager.rotate((float) (Math.atan(py / 40.0F) * 40.0F + 20), 1, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etadsdragon);
			TileEntityRecieverRenderer.adsdragon.render();
		}
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.guitray);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (r.pInR > 0)
		{
			drawTexturedModalRect(x + 104, y + 68, 248, 0, 8, 8);
		}

		fontRenderer.drawString(I18n.format("RivalRebels.ads.tray"), x + 25, y + 66, 0xffffff);
		drawADS(guiLeft + 51, guiTop + 75, 30, guiLeft + 51 - this.xSize_lo, guiTop + 25 - this.ySize_lo);
	}
}

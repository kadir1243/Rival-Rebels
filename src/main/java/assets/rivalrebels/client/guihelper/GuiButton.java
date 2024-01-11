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
package assets.rivalrebels.client.guihelper;

import assets.rivalrebels.RivalRebels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButton extends net.minecraft.client.gui.GuiButton
{
	public GuiButton(int par1, int par2, int par3, String par4Str)
	{
		this(par1, par2, par3, 60, 11, par4Str);
	}

	public GuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
	{
		super(par1, par2, par3, par4, par5, par6Str);
	}

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible)
		{
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.renderEngine.bindTexture(RivalRebels.guitbutton);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int k = this.getHoverState(this.hovered);
			this.drawTexturedModalRect(this.x, this.y, 5, k * 11, this.width, this.height);
			this.mouseDragged(mc, mouseX, mouseY);
			int l = 0xffffff;

			if (!this.enabled)
			{
				l = 0xcccccc;
			}
			else if (this.hovered)
			{
				l = 0x88e8ff;
			}

			this.drawCenteredString(fontrenderer, I18n.format(this.displayString), this.x + this.width / 2, this.y + (this.height - 7) / 2, l);
		}
	}
}

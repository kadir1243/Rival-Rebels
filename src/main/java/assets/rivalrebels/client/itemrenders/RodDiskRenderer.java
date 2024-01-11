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
package assets.rivalrebels.client.itemrenders;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelDisk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class RodDiskRenderer extends TileEntityItemStackRenderer
{
	ModelDisk	md;

	public RodDiskRenderer()
	{
		md = new ModelDisk();
	}

    @Override
    public void renderByItem(ItemStack stack) {
		GlStateManager.enableLighting();
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etdisk0);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5f, 0.25f, 0f);
		GlStateManager.rotate(35, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(-25, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(0.5f, 0.5f, 0.5f);
		GlStateManager.pushMatrix();

		md.render();

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}


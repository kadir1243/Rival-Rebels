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
import assets.rivalrebels.client.model.ModelLaptop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class LaptopRenderer extends TileEntityItemStackRenderer
{
	ModelLaptop	ml;

	public LaptopRenderer()
	{
		ml = new ModelLaptop();
	}

    @Override
    public void renderByItem(ItemStack stack) {
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) 0.3, (float) 0.3, 0);
		GlStateManager.rotate(180, 0, 1, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etlaptop);
		ml.renderModel(-90);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etubuntu);
		ml.renderScreen(-90);
		GlStateManager.popMatrix();
	}
}


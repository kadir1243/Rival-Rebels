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
import assets.rivalrebels.client.model.ModelLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class LoaderRenderer extends TileEntityItemStackRenderer
{
	ModelLoader	ml;

	public LoaderRenderer()
	{
		ml = new ModelLoader();
	}

    @Override
    public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 0.05F, 0.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etloader);
		ml.renderA();
		ml.renderB(0);
		GlStateManager.popMatrix();
	}
}

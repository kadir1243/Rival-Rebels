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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class FlamethrowerRenderer extends TileEntityItemStackRenderer
{
	private final ModelFromObj ft;

	public FlamethrowerRenderer() {
        ft = ModelFromObj.readObjFile("n.obj");
	}

    @Override
    public void renderByItem(ItemStack stack) {
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etflamethrower);
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.rotate(35, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(0.7f, 0.1f, 00f);
		GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.18f, 0.18f, 0.18f);
		// GlStateManager.translate(0.3f, 0.05f, -0.1f);

		ft.render();
		if (stack.isItemEnchanted())
		{
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			ft.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}

		GlStateManager.popMatrix();
	}
}


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

public class RodaRenderer extends TileEntityItemStackRenderer
{
	ModelFromObj	model;

	public RodaRenderer() {
        model = ModelFromObj.readObjFile("e.obj");
	}

    @Override
    public void renderByItem(ItemStack stack) {
		GlStateManager.enableLighting();
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etrust);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5f, 0.5f, -0.03f);
		GlStateManager.rotate(35, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.35f, 0.35f, 0.35f);
		/*if (type != ItemRenderType.EQUIPPED_FIRST_PERSON) GlStateManager.scale(-1, 1, 1);*/
		GlStateManager.translate(0.2f, -0.55f, 0.1f);

		model.render();
		GlStateManager.pushMatrix();
		GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.disableLighting();
		model.render();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
	}
}


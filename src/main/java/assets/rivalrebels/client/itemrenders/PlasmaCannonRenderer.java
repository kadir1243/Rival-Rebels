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
import assets.rivalrebels.client.model.ModelRod;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class PlasmaCannonRenderer extends TileEntityItemStackRenderer {
	private final ModelRod md2;
	private final ModelRod md3;
	private final ModelFromObj model;

	public PlasmaCannonRenderer()
	{
		md2 = new ModelRod();
		md2.rendersecondcap = false;
		md3 = new ModelRod();
        model = ModelFromObj.readObjFile("m.obj");
	}

    @Override
    public void renderByItem(ItemStack stack) {
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.1f, 0f, 0f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etplasmacannon);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5f, 0.2f, -0.03f);
		GlStateManager.rotate(35, 0.0F, 0.0F, 1.0F);
		GlStateManager.scale(0.03125f, 0.03125f, 0.03125f);
		GlStateManager.pushMatrix();

		model.render();
		if (stack.isItemEnchanted())
		{
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			model.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.ethydrod);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5f, 0.2f, -0.03f);
		GlStateManager.rotate(35, 0.0F, 0.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(225, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(-0.5f, 0.5f, 0.0f);
		GlStateManager.scale(0.25f, 0.5f, 0.25f);
		md2.render();
		if (stack.isItemEnchanted())
		{
            GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			md2.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5f, 0.2f, -0.03f);
		GlStateManager.rotate(35, 0.0F, 0.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(247.5f, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(-0.175f, 0.1f, 0.0f);
		GlStateManager.scale(0.25f, 0.5f, 0.25f);
		md3.render();
		if (stack.isItemEnchanted())
		{
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			md3.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}


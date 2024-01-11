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
package assets.rivalrebels.client.tileentityrender;

import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.tileentity.TileEntityPlasmaExplosion;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityPlasmaExplosionRenderer extends TileEntitySpecialRenderer<TileEntityPlasmaExplosion>
{
	ModelBlastSphere	model;

	public TileEntityPlasmaExplosionRenderer()
	{
		model = new ModelBlastSphere();
	}

    @Override
    public void render(TileEntityPlasmaExplosion te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		float fsize = (float) Math.sin(te.size);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(te.size * 50, 0f, 1, 0f);
		model.renderModel(fsize * 5.5f, 0.45f, 0.45f, 0.65f, 0.4f);
		GlStateManager.rotate(te.size * 50, 0f, 1, 0f);
		model.renderModel(fsize * 5.6f, 0.45f, 0.35f, 0.65f, 0.4f);
		GlStateManager.rotate(te.size * 50, 0f, 1, 0f);
		model.renderModel(fsize * 5.7f, 0.45f, 0.35f, 0.95f, 0.4f);
		GlStateManager.rotate(te.size * 50, 0f, 1, 0f);
		model.renderModel(fsize * 5.8f, 0.45f, 0.35f, 0.65f, 0.4f);
		GlStateManager.popMatrix();
		model.renderModel(fsize * 5.9f, 0.45f, 0.35f, 0.65f, 0.4f);
		GlStateManager.popMatrix();
	}
}

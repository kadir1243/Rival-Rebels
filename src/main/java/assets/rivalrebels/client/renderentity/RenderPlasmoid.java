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
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntityPlasmoid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderPlasmoid extends Render
{
	ModelBlastSphere	model	= new ModelBlastSphere();

    public RenderPlasmoid(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
	{
		renderPlasmoid((EntityPlasmoid) var1, var2, var4, var6, var8, var9);
	}

	public void renderPlasmoid(EntityPlasmoid e, double x, double y, double z, float var8, float var9)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(e.rotationYaw - 90.0f, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(e.rotationPitch - 90.0f, 0.0F, 0.0F, 1.0F);
		GlStateManager.scale(0.4f, 2.5f, 0.4f);
		GlStateManager.pushMatrix();

		GlStateManager.rotate(e.rotation, 0.0F, 1.0F, 0.0F);
		model.renderModel(0.4f, 0.65f, 0.55f, 0.95f, 0.9f);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(e.rotation, 0.0F, 1.0F, 0.0F);
		model.renderModel(0.6f, 0.65f, 0.55f, 0.95f, 0.9f);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(e.rotation, 0.0F, 1.0F, 0.0F);
		model.renderModel(0.8f, 0.65f, 0.55f, 0.95f, 0.9f);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(e.rotation, 0.0F, 1.0F, 0.0F);
		model.renderModel(1f, 0.65f, 0.55f, 0.95f, 0.9f);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(e.rotation, 0.0F, 1.0F, 0.0F);
		model.renderModel(1.2f, 0.65f, 0.55f, 0.95f, 0.9f);
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

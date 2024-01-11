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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.common.entity.EntityRoddiskOfficer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRoddiskOfficer extends Render<EntityRoddiskOfficer>
{
	int					er	= 0;
	private ModelDisk	model;

	public RenderRoddiskOfficer(RenderManager manager)
	{
        super(manager);
		model = new ModelDisk();
	}

	@Override
	public void doRender(EntityRoddiskOfficer entity, double x, double y, double z, float var8, float var9)
	{
		er += 13.46;
        Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etdisk2);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(entity.rotationYaw - 90.0f + er, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.4f, 0.4f, 0.4f);
		GlStateManager.pushMatrix();

		model.render();

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRoddiskOfficer entity)
	{
		return null;
	}
}

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
import assets.rivalrebels.common.entity.EntityRoddiskLeader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRoddiskLeader extends Render
{
	int					er	= 0;
	private ModelDisk	model;

	public RenderRoddiskLeader(RenderManager manager)
	{
        super(manager);
		model = new ModelDisk();
	}

	@Override
	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
	{
		er += 13.46;
		EntityRoddiskLeader erd = (EntityRoddiskLeader) var1;
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etdisk3);
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.4f, 0.4f, 0.4f);
		GlStateManager.translate((float) var2, (float) var4, (float) var6);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(erd.rotationPitch, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(erd.rotationYaw - 90.0f + er, 0.0F, 1.0F, 0.0F);

		model.render();

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

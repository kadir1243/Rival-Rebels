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
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.common.entity.EntityLaptop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderLaptop extends Render
{
	ModelLaptop	ml;

	public RenderLaptop(RenderManager manager)
	{
        super(manager);
		ml = new ModelLaptop();
	}

	@Override
	public void doRender(Entity var1, double d, double d1, double d2, float var8, float var9)
	{
		GlStateManager.enableLighting();
		EntityLaptop tile = (EntityLaptop) var1;
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) d, (float) d1, (float) d2);
		GlStateManager.rotate(180 - var1.rotationYaw, 0, 1, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etlaptop);
		ml.renderModel((float) -tile.slide);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etubuntu);
		ml.renderScreen((float) -tile.slide);
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

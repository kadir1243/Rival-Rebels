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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.common.entity.EntityLaptop;

public class RenderLaptop extends Render<EntityLaptop>
{
	ModelLaptop	ml;

	public RenderLaptop(RenderManager renderManager)
	{
        super(renderManager);
		ml = new ModelLaptop();
	}

	@Override
	public void doRender(EntityLaptop laptop, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180 - laptop.rotationYaw, 0, 1, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etlaptop);
		ml.renderModel((float) -laptop.slide);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etubuntu);
		ml.renderScreen((float) -laptop.slide);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLaptop entity)
	{
		return null;
	}
}

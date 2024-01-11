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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.entity.EntityB2Spirit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderB2Spirit extends Render
{
	ModelFromObj	b2;
	public static ModelFromObj	shuttle;
	public static ModelFromObj	tupolev;

	public RenderB2Spirit(RenderManager manager)
	{
        super(manager);
        b2 = ModelFromObj.readObjFile("d.obj");
		b2.scale(3, 3, 3);
		tupolev = ModelFromObj.readObjFile("tupolev.obj");
		shuttle = ModelFromObj.readObjFile("shuttle.obj");
	}

	public void renderB2Spirit(EntityB2Spirit b2spirit, double x, double y, double z, float par8, float par9)
	{
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.rotate(b2spirit.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(b2spirit.rotationPitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.disableCull();
		if (RivalRebels.bombertype.equals("sh"))
		{
			GlStateManager.scale(3.0f, 3.0f, 3.0f);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etb2spirit);
			shuttle.render();
		}
		else if (RivalRebels.bombertype.equals("tu"))
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.ettupolev);
			tupolev.render();
		}
		else
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etb2spirit);
			b2.render();
		}
		GlStateManager.popMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all
	 * probabilty, the class Render is generic (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre
	 * 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderB2Spirit((EntityB2Spirit) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

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
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderB2Spirit extends Render<EntityB2Spirit>
{
	ModelFromObj	b2;
	public static ModelFromObj	shuttle;
	public static ModelFromObj	tupolev;

	public RenderB2Spirit(RenderManager renderManager)
	{
        super(renderManager);
		try
		{
			b2 = ModelFromObj.readObjFile("b2spirit.obj");
			b2.scale(3, 3, 3);
			tupolev = ModelFromObj.readObjFile("tupolev.obj");
			shuttle = ModelFromObj.readObjFile("shuttle.obj");
		}
		catch (Exception e)
		{
            RivalRebels.LOGGER.error(e);
		}
	}

	public void doRender(EntityB2Spirit b2spirit, double x, double y, double z, float par8, float par9)
	{
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(b2spirit.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(b2spirit.rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		if (RivalRebels.bombertype.equals("sh"))
		{
			GL11.glScalef(3.0f, 3.0f, 3.0f);
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
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityB2Spirit entity)
	{
		return null;
	}
}

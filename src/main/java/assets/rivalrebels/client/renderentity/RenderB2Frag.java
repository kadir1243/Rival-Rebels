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
import assets.rivalrebels.common.entity.EntityB2Frag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderB2Frag extends Render<EntityB2Frag>
{
	ModelFromObj	md1;
	ModelFromObj	md2;

	public RenderB2Frag(RenderManager renderManager)
	{
        super(renderManager);
        shadowSize = 0F;
        try
		{
			md1 = ModelFromObj.readObjFile("b2side1.obj");
			md1.scale(3, 3, 3);
			md2 = ModelFromObj.readObjFile("b2side2.obj");
			md2.scale(3, 3, 3);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void doRender(EntityB2Frag b2spirit, double x, double y, double z, float par8, float par9)
	{
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(b2spirit.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(b2spirit.rotationPitch, 0.0F, 0.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etb2spirit);
		GL11.glDisable(GL11.GL_CULL_FACE);
		if (b2spirit.type == 0) md1.render();
		if (b2spirit.type == 1) md2.render();
		GL11.glPopMatrix();
	}


	@Override
	protected ResourceLocation getEntityTexture(EntityB2Frag entity)
	{
		return null;
	}
}

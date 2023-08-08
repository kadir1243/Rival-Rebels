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
import assets.rivalrebels.common.entity.EntityCuchillo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderCuchillo extends Render<EntityCuchillo>
{
	public RenderCuchillo(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0F;
    }

	public void doRender(EntityCuchillo entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etknife);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		Tessellator var10 = Tessellator.getInstance();
        WorldRenderer worldRenderer = var10.getWorldRenderer();
        float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = 0.0f;
		float var15 = 0.15625f;
		float var16 = 0.0F;
		float var17 = 0.15625F;
		float var18 = 0.15625f;
		float var19 = 0.3125f;
		float var20 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(var20, var20, var20);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(var20, 0.0F, 0.0F);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-7.0D, -2.0D, -2.0D).tex(var16, var18).endVertex();
		worldRenderer.pos(-7.0D, -2.0D, 2.0D).tex(var17, var18).endVertex();
		worldRenderer.pos(-7.0D, 2.0D, 2.0D).tex(var17, var19).endVertex();
		worldRenderer.pos(-7.0D, 2.0D, -2.0D).tex(var16, var19).endVertex();
		var10.draw();
		GL11.glNormal3f(-var20, 0.0F, 0.0F);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-7.0D, 2.0D, -2.0D).tex(var16, var18).endVertex();
		worldRenderer.pos(-7.0D, 2.0D, 2.0D).tex(var17, var18).endVertex();
		worldRenderer.pos(-7.0D, -2.0D, 2.0D).tex(var17, var19).endVertex();
		worldRenderer.pos(-7.0D, -2.0D, -2.0D).tex(var16, var19).endVertex();
		var10.draw();

		for (int var23 = 0; var23 < 4; ++var23)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, var20);
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(-8.0D, -2.0D, 0.0D).tex(var12, var14).endVertex();
			worldRenderer.pos(8.0D, -2.0D, 0.0D).tex(var13, var14).endVertex();
			worldRenderer.pos(8.0D, 2.0D, 0.0D).tex(var13, var15).endVertex();
			worldRenderer.pos(-8.0D, 2.0D, 0.0D).tex(var12, var15).endVertex();
			var10.draw();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCuchillo entity)
	{
		return null;
	}
}

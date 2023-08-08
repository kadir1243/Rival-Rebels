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
import assets.rivalrebels.common.entity.EntityGasGrenade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGasGrenade extends Render<EntityGasGrenade>
{
	public RenderGasGrenade(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0F;
    }

    @Override
    public void doRender(EntityGasGrenade entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etgasgrenade);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        byte var11 = 0;
		float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = (0 + var11 * 10) / 32.0F;
		float var15 = (5 + var11 * 10) / 32.0F;
		float var16 = 0.0F;
		float var17 = 0.15625F;
		float var18 = (5 + var11 * 10) / 32.0F;
		float var19 = (10 + var11 * 10) / 32.0F;
		float var20 = 0.05625F;

		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(var20, var20, var20);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(var20, 0.0F, 0.0F);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-7.0D, -2.0D, -2.0D).tex(var16, var18).endVertex();
		worldRenderer.pos(-7.0D, -2.0D, 2.0D).tex(var17, var18).endVertex();
		worldRenderer.pos(-7.0D, 2.0D, 2.0D).tex(var17, var19).endVertex();
		worldRenderer.pos(-7.0D, 2.0D, -2.0D).tex(var16, var19).endVertex();
		tessellator.draw();
		GL11.glNormal3f(-var20, 0.0F, 0.0F);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-7.0D, 2.0D, -2.0D).tex(var16, var18).endVertex();
		worldRenderer.pos(-7.0D, 2.0D, 2.0D).tex(var17, var18).endVertex();
		worldRenderer.pos(-7.0D, -2.0D, 2.0D).tex(var17, var19).endVertex();
		worldRenderer.pos(-7.0D, -2.0D, -2.0D).tex(var16, var19).endVertex();
		tessellator.draw();

		for (int var23 = 0; var23 < 4; ++var23)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, var20);
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(-8.0D, -2.0D, 0.0D).tex(var12, var14).endVertex();
			worldRenderer.pos(8.0D, -2.0D, 0.0D).tex(var13, var14).endVertex();
			worldRenderer.pos(8.0D, 2.0D, 0.0D).tex(var13, var15).endVertex();
			worldRenderer.pos(-8.0D, 2.0D, 0.0D).tex(var12, var15).endVertex();
			tessellator.draw();
		}

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGasGrenade entity)
	{
		return null;
	}
}

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
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGasGrenade extends Render<EntityGasGrenade>
{
    public RenderGasGrenade(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityGasGrenade entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etgasgrenade);
        GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
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

		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(var20, var20, var20);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);
		GlStateManager.glNormal3f(var20, 0.0F, 0.0F);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(-7.0D, -2.0D, -2.0D).tex(var16, var18).endVertex();
		buffer.pos(-7.0D, -2.0D, 2.0D).tex(var17, var18).endVertex();
		buffer.pos(-7.0D, 2.0D, 2.0D).tex(var17, var19).endVertex();
		buffer.pos(-7.0D, 2.0D, -2.0D).tex(var16, var19).endVertex();
		tessellator.draw();
		GlStateManager.glNormal3f(-var20, 0.0F, 0.0F);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(-7.0D, 2.0D, -2.0D).tex(var16, var18).endVertex();
		buffer.pos(-7.0D, 2.0D, 2.0D).tex(var17, var18).endVertex();
		buffer.pos(-7.0D, -2.0D, 2.0D).tex(var17, var19).endVertex();
		buffer.pos(-7.0D, -2.0D, -2.0D).tex(var16, var19).endVertex();
		tessellator.draw();

		for (int var23 = 0; var23 < 4; ++var23)
		{
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.glNormal3f(0.0F, 0.0F, var20);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.pos(-8.0D, -2.0D, 0.0D).tex(var12, var14).endVertex();
			buffer.pos(8.0D, -2.0D, 0.0D).tex(var13, var14).endVertex();
			buffer.pos(8.0D, 2.0D, 0.0D).tex(var13, var15).endVertex();
			buffer.pos(-8.0D, 2.0D, 0.0D).tex(var12, var15).endVertex();
			tessellator.draw();
		}

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGasGrenade entity)
	{
		return null;
	}
}

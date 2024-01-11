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
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCuchillo extends Render {
    public RenderCuchillo(RenderManager renderManager) {
        super(renderManager);
    }

    public void renderKnife(EntityCuchillo par1EntityArrow, double par2, double par4, double par6, float par8, float par9)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etknife);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) par2, (float) par4, (float) par6);
		GlStateManager.rotate(par1EntityArrow.prevRotationYaw + (par1EntityArrow.rotationYaw - par1EntityArrow.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(par1EntityArrow.prevRotationPitch + (par1EntityArrow.rotationPitch - par1EntityArrow.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
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
		GlStateManager.enableRescaleNormal();

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

		GlStateManager.disableRescaleNormal();
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
		this.renderKnife((EntityCuchillo) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

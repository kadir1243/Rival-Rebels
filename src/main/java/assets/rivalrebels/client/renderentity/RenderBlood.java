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

public class RenderBlood extends Render
{
    public RenderBlood(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1)
	{
		GlStateManager.enableLighting();
		if (entity.ticksExisted > 1)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.scale(0.25F, 0.25F, 0.25F);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etblood);
			renderFaceMe();
			GlStateManager.popMatrix();
		}
	}

	private void renderFaceMe()
	{
		float var7 = 1.0F;
		float var8 = 0.5F;
		float var9 = 0.25F;
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		buffer.pos((0.0F - var8), (0.0F - var9), 0.0D).tex(0, 0).normal(0, 1, 0).endVertex();
		buffer.pos((var7 - var8), (0.0F - var9), 0.0D).tex(1, 0).normal(0, 1, 0).endVertex();
		buffer.pos((var7 - var8), (var7 - var9), 0.0D).tex(1, 1).normal(0, 1, 0).endVertex();
		buffer.pos((0.0F - var8), (var7 - var9), 0.0D).tex(0, 1).normal(0, 1, 0).endVertex();
		t.draw();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

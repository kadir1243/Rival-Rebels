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
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityFlameBall2;

public class RenderFlameBlue extends Render
{
    public RenderFlameBlue(RenderManager renderManager) {
        super(renderManager);
    }

    public void renderFlame(EntityFlameBall2 ell, double x, double y, double z, float yaw, float pitch)
	{
		if (ell.ticksExisted < 3) return;
		GlStateManager.pushMatrix();
		GlStateManager.depthMask(false);
		GlStateManager.enableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		// GL11.glBlendEquationSeparate(GL14.GL_FUNC_ADD, GL14.GL_FUNC_ADD);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.glBlendEquation(GL14.GL_FUNC_ADD);
		GlStateManager.color(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etflameblue);

		GlStateManager.pushMatrix();
		float X = (ell.sequence % 4) / 4f;
		float Y = (ell.sequence - (ell.sequence % 4)) / 16f;
		float size = 0.070f * ell.ticksExisted;
		size *= size;
		if (size >= 0.3) size = 0.3f;
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        GlStateManager.translate(x, y, z);
		GlStateManager.rotate(180 - Minecraft.getMinecraft().player.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(90 - Minecraft.getMinecraft().player.rotationPitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(ell.rotation, 0.0F, 1.0F, 0.0F);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		buffer.pos(-size, 0, -size).tex(X, Y).normal(0, 1, 0).endVertex();
		buffer.pos(size, 0, -size).tex(X + 0.25f, Y).normal(0, 1, 0).endVertex();
		buffer.pos(size, 0, size).tex(X + 0.25f, Y + 0.25f).normal(0, 1, 0).endVertex();
		buffer.pos(-size, 0, size).tex(X, Y + 0.25f).normal(0, 1, 0).endVertex();
		t.draw();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();
	}

	@Override
	public void doRender(Entity entityLaserLink, double x, double y, double z, float yaw, float pitch)
	{
		renderFlame((EntityFlameBall2) entityLaserLink, x, y, z, yaw, pitch);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

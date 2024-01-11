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

import assets.rivalrebels.common.entity.EntityLaserBurst;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLaserBurst extends Render
{
	static float	red		= 1F;
	static float	green	= 0.0F;
	static float	blue	= 0.0F;

    public RenderLaserBurst(RenderManager renderManager) {
        super(renderManager);
    }

    public void renderLaserBurst(EntityLaserBurst ell, double x, double y, double z, float yaw, float pitch)
	{
		float radius = 0.12F;
		int distance = 4;
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableCull();
        GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.translate((float) x, (float) y, (float) z);
		// Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsteel);
		// RenderHelper.drawPoint(new Vertice(0, 0, 0), 1);

		GlStateManager.rotate(ell.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-ell.rotationPitch, 1.0F, 0.0F, 0.0F);

		for (float o = 0; o <= radius; o += radius / 8)
		{
			float color = 1f - (o * 8.333f);
			if (color < 0) color = 0;
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			buffer.pos(0 + o, 0 - o, 0).color(red, color, color, 1).endVertex();
			buffer.pos(0 + o, 0 + o, 0).color(red, color, color, 1).endVertex();
			buffer.pos(0 + o, 0 + o, 0 + distance).color(red, color, color, 1).endVertex();
			buffer.pos(0 + o, 0 - o, 0 + distance).color(red, color, color, 1).endVertex();
			buffer.pos(0 - o, 0 - o, 0).color(red, color, color, 1).endVertex();
			buffer.pos(0 - o, 0 - o, 0 + distance).color(red, color, color, 1).endVertex();
			buffer.pos(0 - o, 0 + o, 0).color(red, color, color, 1).endVertex();
			buffer.pos(0 - o, 0 + o, 0 + distance).color(red, color, color, 1).endVertex();
			tessellator.draw();
		}
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}

	@Override
	public void doRender(Entity entityLaserBurst, double x, double y, double z, float yaw, float pitch)
	{
		this.renderLaserBurst((EntityLaserBurst) entityLaserBurst, x, y, z, yaw, pitch);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

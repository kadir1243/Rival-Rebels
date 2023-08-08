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
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLaserBurst extends Render<EntityLaserBurst>
{
	static float	red		= 1F;
	static float	green	= 0.0F;
	static float	blue	= 0.0F;

    public RenderLaserBurst(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityLaserBurst ell, double x, double y, double z, float entityYaw, float partialTicks) {
		float radius = 0.12F;
		int distance = 4;
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glTranslatef((float) x, (float) y, (float) z);
		// Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsteel);
		// RenderHelper.drawPoint(new Vertice(0, 0, 0), 1);

		GL11.glRotatef(ell.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-ell.rotationPitch, 1.0F, 0.0F, 0.0F);

		for (float o = 0; o <= radius; o += radius / 8)
		{
			float color = 1f - (o * 8.333f);
			if (color < 0) color = 0;
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			worldRenderer.color(red, color, color, 1f);
			worldRenderer.pos(0 + o, 0 - o, 0).endVertex();
			worldRenderer.pos(0 + o, 0 + o, 0).endVertex();
			worldRenderer.pos(0 + o, 0 + o, 0 + distance).endVertex();
			worldRenderer.pos(0 + o, 0 - o, 0 + distance).endVertex();
			tessellator.draw();
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			worldRenderer.color(red, color, color, 1f);
			worldRenderer.pos(0 - o, 0 - o, 0).endVertex();
			worldRenderer.pos(0 + o, 0 - o, 0).endVertex();
			worldRenderer.pos(0 + o, 0 - o, 0 + distance).endVertex();
			worldRenderer.pos(0 - o, 0 - o, 0 + distance).endVertex();
			tessellator.draw();
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			worldRenderer.color(red, color, color, 1f);
			worldRenderer.pos(0 - o, 0 + o, 0).endVertex();
			worldRenderer.pos(0 - o, 0 - o, 0).endVertex();
			worldRenderer.pos(0 - o, 0 - o, 0 + distance).endVertex();
			worldRenderer.pos(0 - o, 0 + o, 0 + distance).endVertex();
			tessellator.draw();
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			worldRenderer.color(red, color, color, 1f);
			worldRenderer.pos(0 + o, 0 + o, 0).endVertex();
			worldRenderer.pos(0 - o, 0 + o, 0).endVertex();
			worldRenderer.pos(0 - o, 0 + o, 0 + distance).endVertex();
			worldRenderer.pos(0 + o, 0 + o, 0 + distance).endVertex();
			tessellator.draw();
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLaserBurst entity)
	{
		return null;
	}
}

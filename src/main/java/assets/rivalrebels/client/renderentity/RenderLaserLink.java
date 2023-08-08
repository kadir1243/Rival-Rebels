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

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import assets.rivalrebels.common.entity.EntityLaserLink;

public class RenderLaserLink extends Render
{
	static float	red		= 0.5F;
	static float	green	= 0.1F;
	static float	blue	= 0.1F;

    public RenderLaserLink(RenderManager renderManager) {
        super(renderManager);
    }

	public void renderLaserLink(EntityLaserLink ell, double x, double y, double z, float yaw, float pitch)
	{
		double distance = ell.motionX * 100;
		if (distance > 0)
		{
			float radius = 0.7F;
			Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();

            GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glRotatef(-ell.rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(ell.rotationPitch, 1.0F, 0.0F, 0.0F);

			for (float o = 0; o <= radius; o += radius / 16)
			{
				worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
				worldRenderer.color(red, green, blue, 1f);
				worldRenderer.pos(0 + o, 0 - o, 0).endVertex();
				worldRenderer.pos(0 + o, 0 + o, 0).endVertex();
				worldRenderer.pos(0 + o, 0 + o, 0 + distance).endVertex();
				worldRenderer.pos(0 + o, 0 - o, 0 + distance).endVertex();
				tessellator.draw();
				worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
				worldRenderer.color(red, green, blue, 1f);
				worldRenderer.pos(0 - o, 0 - o, 0).endVertex();
				worldRenderer.pos(0 + o, 0 - o, 0).endVertex();
				worldRenderer.pos(0 + o, 0 - o, 0 + distance).endVertex();
				worldRenderer.pos(0 - o, 0 - o, 0 + distance).endVertex();
				tessellator.draw();
				worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
				worldRenderer.color(red, green, blue, 1f);
				worldRenderer.pos(0 - o, 0 + o, 0).endVertex();
				worldRenderer.pos(0 - o, 0 - o, 0).endVertex();
				worldRenderer.pos(0 - o, 0 - o, 0 + distance).endVertex();
				worldRenderer.pos(0 - o, 0 + o, 0 + distance).endVertex();
				tessellator.draw();
				worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
				worldRenderer.color(red, green, blue, 1f);
				worldRenderer.pos(0 + o, 0 + o, 0).endVertex();
				worldRenderer.pos(0 - o, 0 + o, 0).endVertex();
				worldRenderer.pos(0 - o, 0 + o, 0 + distance).endVertex();
				worldRenderer.pos(0 + o, 0 + o, 0 + distance).endVertex();
				tessellator.draw();
			}

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void doRender(Entity entityLaserLink, double x, double y, double z, float yaw, float pitch)
	{
		this.renderLaserLink((EntityLaserLink) entityLaserLink, x, y, z, yaw, pitch);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

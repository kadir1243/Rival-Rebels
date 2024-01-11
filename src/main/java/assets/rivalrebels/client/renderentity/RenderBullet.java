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

public class RenderBullet extends Render<Entity>
{
	/**
	 * Have the icon index (in items.png) that will be used to render the image. Currently, eggs and snowballs uses this classes.
	 */
	private final String path;

	public RenderBullet(RenderManager manager, String par1)
	{
        super(manager);
		path = par1;
	}

    @Override
	public void doRender(Entity entity, double x, double y, double z, float par8, float par9) {
		if (entity.ticksExisted > 1)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			if (path.equals("flame")) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etflame);
			if (path.equals("fire")) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etfire);
			Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            float var7 = 1.0F;
            float var8 = 0.5F;
            float var9 = 0.25F;
            GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
            buffer.pos((0.0F - var8), (0.0F - var9), 0.0D).tex(0, 0).normal(0, 1, 0).endVertex();
            buffer.pos((var7 - var8), (0.0F - var9), 0.0D).tex(1, 0).normal(0, 1, 0).endVertex();
            buffer.pos((var7 - var8), (var7 - var9), 0.0D).tex(1, 1).normal(0, 1, 0).endVertex();
            buffer.pos((0.0F - var8), (var7 - var9), 0.0D).tex(0, 1).normal(0, 1, 0).endVertex();
            tessellator.draw();
			GlStateManager.popMatrix();
		}
	}

    @Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

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
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGoo extends Render
{
    public RenderGoo(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0F;
    }

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1)
	{
		if (entity.ticksExisted > 1)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etgoo);
			renderFaceMe();
			GL11.glPopMatrix();
		}
	}

	private void renderFaceMe()
	{
		float var7 = 1.0F;
		float var8 = 0.5F;
		float var9 = 0.25F;
		Tessellator t = Tessellator.getInstance();
        WorldRenderer worldRenderer = t.getWorldRenderer();
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.putNormal(0.0F, 1.0F, 0.0F);
		worldRenderer.pos((0.0F - var8), (0.0F - var9), 0.0D).tex(0, 0).endVertex();
		worldRenderer.pos((var7 - var8), (0.0F - var9), 0.0D).tex(1, 0).endVertex();
		worldRenderer.pos((var7 - var8), (var7 - var9), 0.0D).tex(1, 1).endVertex();
		worldRenderer.pos((0.0F - var8), (var7 - var9), 0.0D).tex(0, 1).endVertex();
		t.draw();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

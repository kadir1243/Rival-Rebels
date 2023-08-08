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
package assets.rivalrebels.client.model;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelTrollFlag
{
	public void renderModel(World world, double x, double y, double z, int metadata)
	{
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        double var18 = 0.05;

		if (metadata == 5)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(var18, 1, 1).tex(0, 0).endVertex();
			worldRenderer.pos(var18, 0, 1).tex(0, 1).endVertex();
			worldRenderer.pos(var18, 0, 0).tex(1, 1).endVertex();
			worldRenderer.pos(var18, 1, 0).tex(1, 0).endVertex();
			tessellator.draw();
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(var18, 1, 0).tex(1, 0).endVertex();
			worldRenderer.pos(var18, 0, 0).tex(1, 1).endVertex();
			worldRenderer.pos(var18, 0, 1).tex(0, 1).endVertex();
			worldRenderer.pos(var18, 1, 1).tex(0, 0).endVertex();
			tessellator.draw();
		}

		if (metadata == 4)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(1 - var18, 0, 1).tex(1, 1).endVertex();
			worldRenderer.pos(1 - var18, 1, 1).tex(1, 0).endVertex();
			worldRenderer.pos(1 - var18, 1, 0).tex(0, 0).endVertex();
			worldRenderer.pos(1 - var18, 0, 0).tex(0, 1).endVertex();
			tessellator.draw();
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(1 - var18, 0, 0).tex(0, 1).endVertex();
			worldRenderer.pos(1 - var18, 1, 0).tex(0, 0).endVertex();
			worldRenderer.pos(1 - var18, 1, 1).tex(1, 0).endVertex();
			worldRenderer.pos(1 - var18, 0, 1).tex(1, 1).endVertex();
			tessellator.draw();
		}

		if (metadata == 3)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(1, 0, var18).tex(1, 1).endVertex();
			worldRenderer.pos(1, 1, var18).tex(1, 0).endVertex();
			worldRenderer.pos(0, 1, var18).tex(0, 0).endVertex();
			worldRenderer.pos(0, 0, var18).tex(0, 1).endVertex();
			tessellator.draw();
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(0, 0, var18).tex(0, 1).endVertex();
			worldRenderer.pos(0, 1, var18).tex(0, 0).endVertex();
			worldRenderer.pos(1, 1, var18).tex(1, 0).endVertex();
			worldRenderer.pos(1, 0, var18).tex(1, 1).endVertex();
			tessellator.draw();
		}

		if (metadata == 2)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(1, 1, 1 - var18).tex(0, 0).endVertex();
			worldRenderer.pos(1, 0, 1 - var18).tex(0, 1).endVertex();
			worldRenderer.pos(0, 0, 1 - var18).tex(1, 1).endVertex();
			worldRenderer.pos(0, 1, 1 - var18).tex(1, 0).endVertex();
			tessellator.draw();
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(0, 1, 1 - var18).tex(1, 0).endVertex();
			worldRenderer.pos(0, 0, 1 - var18).tex(1, 1).endVertex();
			worldRenderer.pos(1, 0, 1 - var18).tex(0, 1).endVertex();
			worldRenderer.pos(1, 1, 1 - var18).tex(0, 0).endVertex();
			tessellator.draw();
		}
	}
}

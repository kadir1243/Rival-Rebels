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
package assets.rivalrebels.client.tileentityrender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.renderhelper.Vertice;
import assets.rivalrebels.common.tileentity.TileEntityGore;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityGoreRenderer extends TileEntitySpecialRenderer<TileEntityGore>
{
	float	s	= 0.5F;

	Vertice	v1	= new Vertice(s, s, s);
	Vertice	v2	= new Vertice(s, s, -s);
	Vertice	v3	= new Vertice(-s, s, -s);
	Vertice	v4	= new Vertice(-s, s, s);

	Vertice	v5	= new Vertice(s, -s, s);
	Vertice	v6	= new Vertice(s, -s, -s);
	Vertice	v7	= new Vertice(-s, -s, -s);
	Vertice	v8	= new Vertice(-s, -s, s);

    @Override
    public void render(TileEntityGore te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		World world = te.getWorld();

		boolean ceil = world.isBlockNormalCube(te.getPos().up(), false);
		boolean floor = world.isBlockNormalCube(te.getPos().down(), false);
		boolean side1 = world.isBlockNormalCube(te.getPos().south(), false);
		boolean side2 = world.isBlockNormalCube(te.getPos().west(), false);
		boolean side3 = world.isBlockNormalCube(te.getPos().north(), false);
		boolean side4 = world.isBlockNormalCube(te.getPos().east(), false);
		int meta = te.getBlockMetadata();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		if (meta == 0) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash1);
		else if (meta == 1) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash2);
		else if (meta == 2) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash3);
		else if (meta == 3) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash4);
		else if (meta == 4) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash5);
		else if (meta == 5) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash6);
		else Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash1);
		GlStateManager.disableLighting();
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        if (side1)
		{
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(buffer, v1, 0, 0);
			addVertex(buffer, v5, 1, 0);
			addVertex(buffer, v8, 1, 1);
			addVertex(buffer, v4, 0, 1);
			tessellator.draw();
		}

		if (side2)
		{
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(buffer, v4, 0, 0);
			addVertex(buffer, v8, 1, 0);
			addVertex(buffer, v7, 1, 1);
			addVertex(buffer, v3, 0, 1);
			tessellator.draw();
		}

		if (side3)
		{
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(buffer, v3, 0, 0);
			addVertex(buffer, v7, 1, 0);
			addVertex(buffer, v6, 1, 1);
			addVertex(buffer, v2, 0, 1);
			tessellator.draw();
		}

		if (side4)
		{
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(buffer, v2, 0, 0);
			addVertex(buffer, v6, 1, 0);
			addVertex(buffer, v5, 1, 1);
			addVertex(buffer, v1, 0, 1);
			tessellator.draw();
		}

		if (ceil)
		{
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(buffer, v4, 0, 0);
			addVertex(buffer, v3, 1, 0);
			addVertex(buffer, v2, 1, 1);
			addVertex(buffer, v1, 0, 1);
			tessellator.draw();
		}

		if (floor)
		{
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(buffer, v5, 0, 0);
			addVertex(buffer, v6, 1, 0);
			addVertex(buffer, v7, 1, 1);
			addVertex(buffer, v8, 0, 1);
			tessellator.draw();
		}

		GlStateManager.popMatrix();
	}

	private void addVertex(BufferBuilder buffer, Vertice v, double t, double t2) {
		buffer.pos(v.x * 0.999, v.y * 0.999, v.z * 0.999).tex(t, t2).endVertex();
	}
}

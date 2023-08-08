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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.renderhelper.Vertice;
import assets.rivalrebels.common.tileentity.TileEntityGore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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
    public void renderTileEntityAt(TileEntityGore tile, double x, double y, double z, float partialTicks, int destroyStage) {
		World world = tile.getWorld();
        BlockPos pos = tile.getPos();

        boolean ceil = world.isBlockNormalCube(pos.up(), false);
		boolean floor = world.isBlockNormalCube(pos.down(), false);
		boolean side1 = world.isBlockNormalCube(pos.south(), false);
		boolean side2 = world.isBlockNormalCube(pos.west(), false);
		boolean side3 = world.isBlockNormalCube(pos.north(), false);
		boolean side4 = world.isBlockNormalCube(pos.east(), false);
		int meta = tile.getBlockMetadata();

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		if (meta == 0) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash1);
		else if (meta == 1) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash2);
		else if (meta == 2) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash3);
		else if (meta == 3) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash4);
		else if (meta == 4) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash5);
		else if (meta == 5) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash6);
		else Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btsplash1);
		GL11.glDisable(GL11.GL_LIGHTING);
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        if (side1)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(worldRenderer, v1, 0, 0);
			addVertex(worldRenderer, v5, 1, 0);
			addVertex(worldRenderer, v8, 1, 1);
			addVertex(worldRenderer, v4, 0, 1);
			tessellator.draw();
		}

		if (side2)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(worldRenderer, v4, 0, 0);
			addVertex(worldRenderer, v8, 1, 0);
			addVertex(worldRenderer, v7, 1, 1);
			addVertex(worldRenderer, v3, 0, 1);
			tessellator.draw();
		}

		if (side3)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(worldRenderer, v3, 0, 0);
			addVertex(worldRenderer, v7, 1, 0);
			addVertex(worldRenderer, v6, 1, 1);
			addVertex(worldRenderer, v2, 0, 1);
			tessellator.draw();
		}

		if (side4)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(worldRenderer, v2, 0, 0);
			addVertex(worldRenderer, v6, 1, 0);
			addVertex(worldRenderer, v5, 1, 1);
			addVertex(worldRenderer, v1, 0, 1);
			tessellator.draw();
		}

		if (ceil)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(worldRenderer, v4, 0, 0);
			addVertex(worldRenderer, v3, 1, 0);
			addVertex(worldRenderer, v2, 1, 1);
			addVertex(worldRenderer, v1, 0, 1);
			tessellator.draw();
		}

		if (floor)
		{
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addVertex(worldRenderer, v5, 0, 0);
			addVertex(worldRenderer, v6, 1, 0);
			addVertex(worldRenderer, v7, 1, 1);
			addVertex(worldRenderer, v8, 0, 1);
			tessellator.draw();
		}

		GL11.glPopMatrix();
	}

	private void addVertex(WorldRenderer worldRenderer, Vertice vertice, double u, double v) {
        worldRenderer.pos(vertice.x * 0.999, vertice.y * 0.999, vertice.z * 0.999).tex(u, v).endVertex();
	}
}

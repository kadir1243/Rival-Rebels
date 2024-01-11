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
package assets.rivalrebels.client.itemrenders;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.*;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class AstroBlasterRenderer extends TileEntityItemStackRenderer
{
	ModelAstroBlasterBarrel	md1;
	ModelAstroBlasterHandle	md2;
	ModelAstroBlasterBody	md3;
	ModelAstroBlasterBack	md4;
	ModelRod				md5;
	float					pullback		= 0;
	float					rotation		= 0;
	boolean					isreloading		= false;
	int						stage			= 0;
	int						spin			= 0;
	int						time			= 1;
	int						reloadcooldown	= 0;

	public AstroBlasterRenderer()
	{
		md1 = new ModelAstroBlasterBarrel();
		md2 = new ModelAstroBlasterHandle();
		md3 = new ModelAstroBlasterBody();
		md4 = new ModelAstroBlasterBack();
		md5 = new ModelRod();
	}

    @Override
    public void renderByItem(ItemStack item) {
		spin++;
		if (item.getRepairCost() >= 1)
		{
			spin += item.getRepairCost() / 2.2;
		}
		spin %= 628;
		if (reloadcooldown > 0) reloadcooldown--;
		if (item.getRepairCost() > 20 && reloadcooldown == 0) isreloading = true;
		if (isreloading)
		{
			if (stage == 0) if (pullback < 0.3) pullback += 0.03;
			else stage = 1;
			if (stage == 1) if (rotation < 90) rotation += 4.5;
			else stage = 2;
			if (stage == 2) if (pullback > 0) pullback -= 0.03;
			else
			{
				stage = 0;
				isreloading = false;
				reloadcooldown = 60;
				rotation = 0;
			}

		}
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.translate(0.4f, 0.35f, -0.03f);
		GlStateManager.rotate(-55, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(0f, -0.05f, 0.05f);

		GlStateManager.pushMatrix();
		GlStateManager.translate(0f, 0.9f, 0f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.eteinstenbarrel);
		md1.render();
		if (item.isItemEnchanted())
		{
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			md1.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.22f, -0.025f, 0f);
		GlStateManager.rotate(90, 0.0F, 0.0F, 1.0F);
		GlStateManager.scale(0.03125f, 0.03125f, 0.03125f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.eteinstenhandle);
		md2.render();
		if (item.isItemEnchanted()) {
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			md2.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();

		// GlStateManager.pushMatrix();
		// GlStateManager.translate(0f, 0.8f, 0f);
		// GlStateManager.rotate(180, 0.0F, 0.0F, 1.0F);
		// GlStateManager.scale(0.9, 4.5, 0.9);
		// md3.render(0.2f, 0.3f, 0.3f, 0.3f, 1f);
		// GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0f, 0.2f, 0f);
		GlStateManager.scale(0.85, 0.85, 0.85);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.eteinstenback);
		md4.render();
		if (item.isItemEnchanted()) {
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			md4.render();
            GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0f, -pullback, 0f);
		GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.12f, 0.1f, 0.12f);
		GlStateManager.rotate(pullback * 270, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.3f, 0.7f, 0.3f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etredrod);
		md5.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.12f, 0.1f, 0.12f);
		GlStateManager.rotate(pullback * 270, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.3f, 0.7f, 0.3f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etredrod);
		md5.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.12f, 0.1f, -0.12f);
		GlStateManager.rotate(pullback * 270, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.3f, 0.7f, 0.3f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etredrod);
		md5.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.12f, 0.1f, -0.12f);
		GlStateManager.rotate(pullback * 270, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.3f, 0.7f, 0.3f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etredrod);
		md5.render();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.translate(0, 0.25f, 0);
		float segmentDistance = 0.1f;
		float distance = 0.5f;
		float radius = 0.01F;
		Random rand = new Random();
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        double AddedX = 0;
		double AddedZ = 0;
		double prevAddedX = 0;
		double prevAddedZ = 0;
		// double angle = 0;
		for (float AddedY = distance; AddedY >= 0; AddedY -= segmentDistance)
		{
			prevAddedX = AddedX;
			prevAddedZ = AddedZ;
			AddedX = (rand.nextFloat() - 0.5) * 0.1f;
			AddedZ = (rand.nextFloat() - 0.5) * 0.1f;
			double dist = Math.sqrt(AddedX * AddedX + AddedZ * AddedZ);
			if (dist != 0)
			{
				double tempAddedX = AddedX / dist;
				double tempAddedZ = AddedZ / dist;
				if (Math.abs(tempAddedX) < Math.abs(AddedX))
				{
					AddedX = tempAddedX;
				}
				if (Math.abs(tempAddedZ) < Math.abs(AddedZ))
				{
					AddedZ = tempAddedZ;
				}
				// angle = Math.atan2(tempAddedX, tempAddedZ);
			}
			if (AddedY <= 0)
			{
				AddedX = AddedZ = 0;
			}

			for (float o = 0; o <= radius; o += radius / 2f)
			{
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
				buffer.pos(AddedX + o, AddedY, AddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(AddedX + o, AddedY, AddedZ + o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(AddedX - o, AddedY, AddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(AddedX + o, AddedY, AddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(AddedX - o, AddedY, AddedZ + o).color(1, 0, 0, 1).endVertex();
				buffer.pos(AddedX - o, AddedY, AddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).endVertex();
				buffer.pos(AddedX + o, AddedY, AddedZ + o).color(1, 0, 0, 1).endVertex();
				buffer.pos(AddedX - o, AddedY, AddedZ + o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).endVertex();
				buffer.pos(prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).endVertex();
				tessellator.draw();
			}
			// GlStateManager.pushMatrix();
			// GlStateManager.rotate(90f, 0.0F, 0.0F, 1.0F);
			// GlStateManager.rotate((float) angle, 0.0F, 1.0F, 0.0F);
			// float o = 0.075f;
			// float s = 0.1f;
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( + o, AddedY, - o);
			// tessellator.addVertex( + o, AddedY, + o);
			// tessellator.addVertex( + o, AddedY + s, + o);
			// tessellator.addVertex( + o, AddedY + s, - o);
			// tessellator.draw();
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( - o, AddedY, - o);
			// tessellator.addVertex( + o, AddedY, - o);
			// tessellator.addVertex( + o, AddedY + s, - o);
			// tessellator.addVertex( - o, AddedY + s, - o);
			// tessellator.draw();
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( - o, AddedY, + o);
			// tessellator.addVertex( - o, AddedY, - o);
			// tessellator.addVertex( - o, AddedY + s, - o);
			// tessellator.addVertex( - o, AddedY + s, + o);
			// tessellator.draw();
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( + o, AddedY, + o);
			// tessellator.addVertex( - o, AddedY, + o);
			// tessellator.addVertex( - o, AddedY + s, + o);
			// tessellator.addVertex( + o, AddedY + s, + o);
			// tessellator.draw();
			// GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0f, 0.8f, 0f);
		GlStateManager.rotate(180, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(spin, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.9, 4.1, 0.9);
		md3.render((float) (0.22f + (Math.sin(spin / 10) * 0.005)), 0.5f, 0f, 0f, 1f);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0f, 0.8f, 0f);
		GlStateManager.rotate(180, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(-spin, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.9, 4.1, 0.9);
		md3.render((float) (0.22f + (Math.cos(-spin / 15) * 0.005)), 0.5f, 0f, 0f, 1f);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
	}
}


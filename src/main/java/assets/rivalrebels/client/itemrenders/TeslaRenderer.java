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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class TeslaRenderer extends TileEntityItemStackRenderer
{
	ModelFromObj	tesla;
	ModelFromObj	dynamo;
	int				spin	= 0;

	public TeslaRenderer() {
		tesla = ModelFromObj.readObjFile("i.obj");
		dynamo = ModelFromObj.readObjFile("j.obj");
	}

	public int getDegree(ItemStack item) {
		if (!item.hasTagCompound()) return 0;
		else return item.getTagCompound().getInteger("dial");
	}

    @Override
    public void renderByItem(ItemStack stack) {
        if (!stack.isItemEnchanted()) {
			GlStateManager.enableLighting();
			int degree = getDegree(stack);
			spin += 5 + (degree / 36f);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.ettesla);
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.8f, 0.5f, -0.03f);
			GlStateManager.rotate(35, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			GlStateManager.scale(0.12f, 0.12f, 0.12f);
			// GlStateManager.translate(0.3f, 0.05f, -0.1f);

			tesla.render();
			GlStateManager.rotate(spin, 1.0F, 0.0F, 0.0F);
			dynamo.render();

			GlStateManager.popMatrix();
		}
		else
		{
			/*if (type != ItemRenderType.ENTITY) GlStateManager.popMatrix();*/
			GlStateManager.pushMatrix();
			Tessellator t = Tessellator.getInstance();
            BufferBuilder buffer = t.getBuffer();
            GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.scale(1.01f, 1.01f, 1.01f);
			GlStateManager.rotate(45.0f, 0, 1, 0);
			GlStateManager.rotate(10.0f, 0, 0, 1);
			GlStateManager.scale(0.6f, 0.2f, 0.2f);
			GlStateManager.translate(-0.99f, 0.5f, 0.0f);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            GlStateManager.disableLighting();
			GlStateManager.enableCull();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(-1, 1, -1).tex(1, 0).endVertex();
            buffer.pos(-1, 1, 1).tex(1, 1).endVertex();
            buffer.pos(-1, -1, 1).tex(0, 1).endVertex();
            buffer.pos(1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(1, -1, 1).tex(0, 1).endVertex();
            buffer.pos(1, 1, 1).tex(1, 1).endVertex();
            buffer.pos(1, 1, -1).tex(1, 0).endVertex();
            buffer.pos(-1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(-1, -1, 1).tex(0, 1).endVertex();
            buffer.pos(1, -1, 1).tex(3, 1).endVertex();
            buffer.pos(1, -1, -1).tex(3, 0).endVertex();
            buffer.pos(-1, 1, -1).tex(0, 0).endVertex();
            buffer.pos(1, 1, -1).tex(3, 0).endVertex();
            buffer.pos(1, 1, 1).tex(3, 1).endVertex();
            buffer.pos(-1, 1, 1).tex(0, 1).endVertex();
            buffer.pos(-1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(1, -1, -1).tex(3, 0).endVertex();
            buffer.pos(1, 1, -1).tex(3, 1).endVertex();
            buffer.pos(-1, 1, -1).tex(0, 1).endVertex();
            buffer.pos(-1, -1, 1).tex(0, 0).endVertex();
            buffer.pos(-1, 1, 1).tex(0, 1).endVertex();
            buffer.pos(1, 1, 1).tex(3, 1).endVertex();
            buffer.pos(1, -1, 1).tex(3, 0).endVertex();
            buffer.pos(-1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(-1, 1, -1).tex(1, 0).endVertex();
            buffer.pos(-1, 1, 1).tex(1, 1).endVertex();
            buffer.pos(-1, -1, 1).tex(0, 1).endVertex();
            buffer.pos(1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(1, -1, 1).tex(0, 1).endVertex();
            buffer.pos(1, 1, 1).tex(1, 1).endVertex();
            buffer.pos(1, 1, -1).tex(1, 0).endVertex();
            buffer.pos(-1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(-1, -1, 1).tex(0, 1).endVertex();
            buffer.pos(1, -1, 1).tex(3, 1).endVertex();
            buffer.pos(1, -1, -1).tex(3, 0).endVertex();
            buffer.pos(-1, 1, -1).tex(0, 0).endVertex();
            buffer.pos(1, 1, -1).tex(3, 0).endVertex();
            buffer.pos(1, 1, 1).tex(3, 1).endVertex();
            buffer.pos(-1, 1, 1).tex(0, 1).endVertex();
            buffer.pos(-1, -1, -1).tex(0, 0).endVertex();
            buffer.pos(1, -1, -1).tex(3, 0).endVertex();
            buffer.pos(1, 1, -1).tex(3, 1).endVertex();
            buffer.pos(-1, 1, -1).tex(0, 1).endVertex();
            buffer.pos(-1, -1, 1).tex(0, 0).endVertex();
            buffer.pos(-1, 1, 1).tex(0, 1).endVertex();
            buffer.pos(1, 1, 1).tex(3, 1).endVertex();
            buffer.pos(1, -1, 1).tex(3, 0).endVertex();
            t.draw();
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.disableCull();
			GlStateManager.popMatrix();
			/*if (type != ItemRenderType.ENTITY) GlStateManager.pushMatrix();*/
		}
	}

}

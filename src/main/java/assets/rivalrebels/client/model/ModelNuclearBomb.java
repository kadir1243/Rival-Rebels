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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.renderhelper.Vertice;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelNuclearBomb
{
	float	s	= 0.5F;
	float	g	= 0.0625F;
	Vertice	v0	= new Vertice(0, g * 22, 0);

	Vertice	v1	= new Vertice(g * 3, g * 19, g * 3);
	Vertice	v2	= new Vertice(g * 3, g * 19, -g * 3);
	Vertice	v3	= new Vertice(-g * 3, g * 19, -g * 3);
	Vertice	v4	= new Vertice(-g * 3, g * 19, g * 3);

	Vertice	v5	= new Vertice(g * 7, g * 7, g * 7);
	Vertice	v6	= new Vertice(g * 7, g * 7, -g * 7);
	Vertice	v7	= new Vertice(-g * 7, g * 7, -g * 7);
	Vertice	v8	= new Vertice(-g * 7, g * 7, g * 7);

	Vertice	v21	= new Vertice(g * 7, -g * 8, g * 7);
	Vertice	v22	= new Vertice(g * 7, -g * 8, -g * 7);
	Vertice	v23	= new Vertice(-g * 7, -g * 8, -g * 7);
	Vertice	v24	= new Vertice(-g * 7, -g * 8, g * 7);

	Vertice	v9	= new Vertice(g * 4, -g * 18, g * 4);
	Vertice	v10	= new Vertice(g * 4, -g * 18, -g * 4);
	Vertice	v11	= new Vertice(-g * 4, -g * 18, -g * 4);
	Vertice	v12	= new Vertice(-g * 4, -g * 18, g * 4);

	Vertice	v13	= new Vertice(s, -g * 7, s);
	Vertice	v14	= new Vertice(s, -g * 7, -s);
	Vertice	v15	= new Vertice(-s, -g * 7, -s);
	Vertice	v16	= new Vertice(-s, -g * 7, s);

	Vertice	v17	= new Vertice(s, -g * 24, s);
	Vertice	v18	= new Vertice(s, -g * 24, -s);
	Vertice	v19	= new Vertice(-s, -g * 24, -s);
	Vertice	v20	= new Vertice(-s, -g * 24, s);

	public void renderModel(boolean hasFuse)
	{
		GlStateManager.pushMatrix();
		GlStateManager.scale(RivalRebels.nukeScale,RivalRebels.nukeScale,RivalRebels.nukeScale);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        int itemIcon = 39;
		float var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		float var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		float var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		float var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		GlStateManager.pushMatrix();
		GlStateManager.scale(1.01f, 1.01f, 1.01f);
		GlStateManager.disableLighting();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(v2.x, v2.y, v2.z).tex(var3, var6).endVertex();
		buffer.pos(v1.x, v1.y, v1.z).tex(var4, var6).endVertex();
		buffer.pos(v5.x, v5.y, v5.z).tex(var4, var5).endVertex();
		buffer.pos(v6.x, v6.y, v6.z).tex(var3, var5).endVertex();
		buffer.pos(v3.x, v3.y, v3.z).tex(var3, var6).endVertex();
		buffer.pos(v2.x, v2.y, v2.z).tex(var4, var6).endVertex();
		buffer.pos(v6.x, v6.y, v6.z).tex(var4, var5).endVertex();
		buffer.pos(v7.x, v7.y, v7.z).tex(var3, var5).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).tex(var3, var6).endVertex();
		buffer.pos(v3.x, v3.y, v3.z).tex(var4, var6).endVertex();
		buffer.pos(v7.x, v7.y, v7.z).tex(var4, var5).endVertex();
		buffer.pos(v8.x, v8.y, v8.z).tex(var3, var5).endVertex();
		buffer.pos(v1.x, v1.y, v1.z).tex(var3, var6).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).tex(var4, var6).endVertex();
		buffer.pos(v8.x, v8.y, v8.z).tex(var4, var5).endVertex();
		buffer.pos(v5.x, v5.y, v5.z).tex(var3, var5).endVertex();

		itemIcon = 40;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		buffer.pos(v6.x, v6.y, v6.z).tex(var3, var6).endVertex();
		buffer.pos(v5.x, v5.y, v5.z).tex(var4, var6).endVertex();
		buffer.pos(v21.x, v21.y, v21.z).tex(var4, var5).endVertex();
		buffer.pos(v22.x, v22.y, v22.z).tex(var3, var5).endVertex();
		buffer.pos(v7.x, v7.y, v7.z).tex(var3, var6).endVertex();
		buffer.pos(v6.x, v6.y, v6.z).tex(var4, var6).endVertex();
		buffer.pos(v22.x, v22.y, v22.z).tex(var4, var5).endVertex();
		buffer.pos(v23.x, v23.y, v23.z).tex(var3, var5).endVertex();
		buffer.pos(v8.x, v8.y, v8.z).tex(var3, var6).endVertex();
		buffer.pos(v7.x, v7.y, v7.z).tex(var4, var6).endVertex();
		buffer.pos(v23.x, v23.y, v23.z).tex(var4, var5).endVertex();
		buffer.pos(v24.x, v24.y, v24.z).tex(var3, var5).endVertex();
		buffer.pos(v5.x, v5.y, v5.z).tex(var3, var6).endVertex();
		buffer.pos(v8.x, v8.y, v8.z).tex(var4, var6).endVertex();
		buffer.pos(v24.x, v24.y, v24.z).tex(var4, var5).endVertex();
		buffer.pos(v21.x, v21.y, v21.z).tex(var3, var5).endVertex();
		buffer.pos(v22.x, v22.y, v22.z).tex(var3, var6).endVertex();
		buffer.pos(v21.x, v21.y, v21.z).tex(var4, var6).endVertex();
		buffer.pos(v9.x, v9.y, v9.z).tex(var4, var5).endVertex();
		buffer.pos(v10.x, v10.y, v10.z).tex(var3, var5).endVertex();
		buffer.pos(v23.x, v23.y, v23.z).tex(var3, var6).endVertex();
		buffer.pos(v22.x, v22.y, v22.z).tex(var4, var6).endVertex();
		buffer.pos(v10.x, v10.y, v10.z).tex(var4, var5).endVertex();
		buffer.pos(v11.x, v11.y, v11.z).tex(var3, var5).endVertex();
		buffer.pos(v24.x, v24.y, v24.z).tex(var3, var6).endVertex();
		buffer.pos(v23.x, v23.y, v23.z).tex(var4, var6).endVertex();
		buffer.pos(v11.x, v11.y, v11.z).tex(var4, var5).endVertex();
		buffer.pos(v12.x, v12.y, v12.z).tex(var3, var5).endVertex();
		buffer.pos(v21.x, v21.y, v21.z).tex(var3, var6).endVertex();
		buffer.pos(v24.x, v24.y, v24.z).tex(var4, var6).endVertex();
		buffer.pos(v12.x, v12.y, v12.z).tex(var4, var5).endVertex();
		buffer.pos(v9.x, v9.y, v9.z).tex(var3, var5).endVertex();

		itemIcon = 38;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		buffer.pos(v10.x, v10.y, v10.z).tex(var3, var6).endVertex();
		buffer.pos(v9.x, v9.y, v9.z).tex(var4, var6).endVertex();
		buffer.pos(v12.x, v12.y, v12.z).tex(var4, var5).endVertex();
		buffer.pos(v11.x, v11.y, v11.z).tex(var3, var5).endVertex();

		itemIcon = 41;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;
		float o = 0.999F;

		buffer.pos(v13.x * o, v13.y, v13.z * o).tex(var3, var6).endVertex();
		buffer.pos(v14.x * o, v14.y, v14.z * o).tex(var4, var6).endVertex();
		buffer.pos(v18.x * o, v18.y, v18.z * o).tex(var4, var5).endVertex();
		buffer.pos(v17.x * o, v17.y, v17.z * o).tex(var3, var5).endVertex();
		buffer.pos(v14.x * o, v14.y, v14.z * o).tex(var3, var6).endVertex();
		buffer.pos(v15.x * o, v15.y, v15.z * o).tex(var4, var6).endVertex();
		buffer.pos(v19.x * o, v19.y, v19.z * o).tex(var4, var5).endVertex();
		buffer.pos(v18.x * o, v18.y, v18.z * o).tex(var3, var5).endVertex();
		buffer.pos(v15.x * o, v15.y, v15.z * o).tex(var3, var6).endVertex();
		buffer.pos(v16.x * o, v16.y, v16.z * o).tex(var4, var6).endVertex();
		buffer.pos(v20.x * o, v20.y, v20.z * o).tex(var4, var5).endVertex();
		buffer.pos(v19.x * o, v19.y, v19.z * o).tex(var3, var5).endVertex();
		buffer.pos(v16.x * o, v16.y, v16.z * o).tex(var3, var6).endVertex();
		buffer.pos(v13.x * o, v13.y, v13.z * o).tex(var4, var6).endVertex();
		buffer.pos(v17.x * o, v17.y, v17.z * o).tex(var4, var5).endVertex();
		buffer.pos(v20.x * o, v20.y, v20.z * o).tex(var3, var5).endVertex();
		buffer.pos(v14.x, v14.y, v14.z).tex(var3, var6).endVertex();
		buffer.pos(v13.x, v13.y, v13.z).tex(var4, var6).endVertex();
		buffer.pos(v17.x, v17.y, v17.z).tex(var4, var5).endVertex();
		buffer.pos(v18.x, v18.y, v18.z).tex(var3, var5).endVertex();
		buffer.pos(v15.x, v15.y, v15.z).tex(var3, var6).endVertex();
		buffer.pos(v14.x, v14.y, v14.z).tex(var4, var6).endVertex();
		buffer.pos(v18.x, v18.y, v18.z).tex(var4, var5).endVertex();
		buffer.pos(v19.x, v19.y, v19.z).tex(var3, var5).endVertex();
		buffer.pos(v16.x, v16.y, v16.z).tex(var3, var6).endVertex();
		buffer.pos(v15.x, v15.y, v15.z).tex(var4, var6).endVertex();
		buffer.pos(v19.x, v19.y, v19.z).tex(var4, var5).endVertex();
		buffer.pos(v20.x, v20.y, v20.z).tex(var3, var5).endVertex();
		buffer.pos(v13.x, v13.y, v13.z).tex(var3, var6).endVertex();
		buffer.pos(v16.x, v16.y, v16.z).tex(var4, var6).endVertex();
		buffer.pos(v20.x, v20.y, v20.z).tex(var4, var5).endVertex();
		buffer.pos(v17.x, v17.y, v17.z).tex(var3, var5).endVertex();

		itemIcon = 42;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		buffer.pos(v13.x, v13.y, v13.z).tex(var3, var6).endVertex();
		buffer.pos(v15.x, v15.y, v15.z).tex(var4, var6).endVertex();
		buffer.pos(v19.x, v19.y, v19.z).tex(var4, var5).endVertex();
		buffer.pos(v17.x, v17.y, v17.z).tex(var3, var5).endVertex();
		buffer.pos(v16.x, v16.y, v16.z).tex(var3, var6).endVertex();
		buffer.pos(v14.x, v14.y, v14.z).tex(var4, var6).endVertex();
		buffer.pos(v18.x, v18.y, v18.z).tex(var4, var5).endVertex();
		buffer.pos(v20.x, v20.y, v20.z).tex(var3, var5).endVertex();
		buffer.pos(v15.x, v15.y, v15.z).tex(var3, var6).endVertex();
		buffer.pos(v13.x, v13.y, v13.z).tex(var4, var6).endVertex();
		buffer.pos(v17.x, v17.y, v17.z).tex(var4, var5).endVertex();
		buffer.pos(v19.x, v19.y, v19.z).tex(var3, var5).endVertex();
		buffer.pos(v14.x, v14.y, v14.z).tex(var3, var6).endVertex();
		buffer.pos(v16.x, v16.y, v16.z).tex(var4, var6).endVertex();
		buffer.pos(v20.x, v20.y, v20.z).tex(var4, var5).endVertex();
		buffer.pos(v18.x, v18.y, v18.z).tex(var3, var5).endVertex();
		if (!hasFuse)
		{
			itemIcon = 37;
			var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
			var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
			var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
			var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

			buffer.pos(v1.x, v1.y, v1.z).tex(var3, var6).endVertex();
			buffer.pos(v2.x, v2.y, v2.z).tex(var4, var6).endVertex();
			buffer.pos(v3.x, v3.y, v3.z).tex(var4, var5).endVertex();
			buffer.pos(v4.x, v4.y, v4.z).tex(var3, var5).endVertex();
			tessellator.draw();
		}
		else
		{
			tessellator.draw();
			itemIcon = 43;
			var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
			var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
			var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
			var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

			buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
			buffer.pos(v0.x, v0.y, v0.z).tex(var3, var6).endVertex();
			buffer.pos(v1.x, v1.y, v1.z).tex(var4, var6).endVertex();
			buffer.pos(v2.x, v2.y, v2.z).tex(var4, var5).endVertex();
			buffer.pos(v0.x, v0.y, v0.z).tex(var3, var6).endVertex();
			buffer.pos(v2.x, v2.y, v2.z).tex(var4, var5).endVertex();
			buffer.pos(v3.x, v3.y, v3.z).tex(var4, var6).endVertex();
			buffer.pos(v0.x, v0.y, v0.z).tex(var3, var6).endVertex();
			buffer.pos(v3.x, v3.y, v3.z).tex(var3, var5).endVertex();
			buffer.pos(v4.x, v4.y, v4.z).tex(var4, var5).endVertex();
			buffer.pos(v0.x, v0.y, v0.z).tex(var3, var6).endVertex();
			buffer.pos(v4.x, v4.y, v4.z).tex(var4, var5).endVertex();
			buffer.pos(v1.x, v1.y, v1.z).tex(var4, var6).endVertex();
			tessellator.draw();
		}
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}

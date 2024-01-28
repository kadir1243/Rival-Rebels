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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.client.renderhelper.Vertice;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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

	public void renderModel(MatrixStack matrices, VertexConsumer buffer, boolean hasFuse)
	{
		matrices.push();
		matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
        int itemIcon = 39;
		float var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		float var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		float var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		float var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		matrices.push();
		matrices.scale(1.01f, 1.01f, 1.01f);

		buffer.vertex(v2.x, v2.y, v2.z).texture(var3, var6).next();
		buffer.vertex(v1.x, v1.y, v1.z).texture(var4, var6).next();
		buffer.vertex(v5.x, v5.y, v5.z).texture(var4, var5).next();
		buffer.vertex(v6.x, v6.y, v6.z).texture(var3, var5).next();
		buffer.vertex(v3.x, v3.y, v3.z).texture(var3, var6).next();
		buffer.vertex(v2.x, v2.y, v2.z).texture(var4, var6).next();
		buffer.vertex(v6.x, v6.y, v6.z).texture(var4, var5).next();
		buffer.vertex(v7.x, v7.y, v7.z).texture(var3, var5).next();
		buffer.vertex(v4.x, v4.y, v4.z).texture(var3, var6).next();
		buffer.vertex(v3.x, v3.y, v3.z).texture(var4, var6).next();
		buffer.vertex(v7.x, v7.y, v7.z).texture(var4, var5).next();
		buffer.vertex(v8.x, v8.y, v8.z).texture(var3, var5).next();
		buffer.vertex(v1.x, v1.y, v1.z).texture(var3, var6).next();
		buffer.vertex(v4.x, v4.y, v4.z).texture(var4, var6).next();
		buffer.vertex(v8.x, v8.y, v8.z).texture(var4, var5).next();
		buffer.vertex(v5.x, v5.y, v5.z).texture(var3, var5).next();

		itemIcon = 40;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		buffer.vertex(v6.x, v6.y, v6.z).texture(var3, var6).next();
		buffer.vertex(v5.x, v5.y, v5.z).texture(var4, var6).next();
		buffer.vertex(v21.x, v21.y, v21.z).texture(var4, var5).next();
		buffer.vertex(v22.x, v22.y, v22.z).texture(var3, var5).next();
		buffer.vertex(v7.x, v7.y, v7.z).texture(var3, var6).next();
		buffer.vertex(v6.x, v6.y, v6.z).texture(var4, var6).next();
		buffer.vertex(v22.x, v22.y, v22.z).texture(var4, var5).next();
		buffer.vertex(v23.x, v23.y, v23.z).texture(var3, var5).next();
		buffer.vertex(v8.x, v8.y, v8.z).texture(var3, var6).next();
		buffer.vertex(v7.x, v7.y, v7.z).texture(var4, var6).next();
		buffer.vertex(v23.x, v23.y, v23.z).texture(var4, var5).next();
		buffer.vertex(v24.x, v24.y, v24.z).texture(var3, var5).next();
		buffer.vertex(v5.x, v5.y, v5.z).texture(var3, var6).next();
		buffer.vertex(v8.x, v8.y, v8.z).texture(var4, var6).next();
		buffer.vertex(v24.x, v24.y, v24.z).texture(var4, var5).next();
		buffer.vertex(v21.x, v21.y, v21.z).texture(var3, var5).next();
		buffer.vertex(v22.x, v22.y, v22.z).texture(var3, var6).next();
		buffer.vertex(v21.x, v21.y, v21.z).texture(var4, var6).next();
		buffer.vertex(v9.x, v9.y, v9.z).texture(var4, var5).next();
		buffer.vertex(v10.x, v10.y, v10.z).texture(var3, var5).next();
		buffer.vertex(v23.x, v23.y, v23.z).texture(var3, var6).next();
		buffer.vertex(v22.x, v22.y, v22.z).texture(var4, var6).next();
		buffer.vertex(v10.x, v10.y, v10.z).texture(var4, var5).next();
		buffer.vertex(v11.x, v11.y, v11.z).texture(var3, var5).next();
		buffer.vertex(v24.x, v24.y, v24.z).texture(var3, var6).next();
		buffer.vertex(v23.x, v23.y, v23.z).texture(var4, var6).next();
		buffer.vertex(v11.x, v11.y, v11.z).texture(var4, var5).next();
		buffer.vertex(v12.x, v12.y, v12.z).texture(var3, var5).next();
		buffer.vertex(v21.x, v21.y, v21.z).texture(var3, var6).next();
		buffer.vertex(v24.x, v24.y, v24.z).texture(var4, var6).next();
		buffer.vertex(v12.x, v12.y, v12.z).texture(var4, var5).next();
		buffer.vertex(v9.x, v9.y, v9.z).texture(var3, var5).next();

		itemIcon = 38;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		buffer.vertex(v10.x, v10.y, v10.z).texture(var3, var6).next();
		buffer.vertex(v9.x, v9.y, v9.z).texture(var4, var6).next();
		buffer.vertex(v12.x, v12.y, v12.z).texture(var4, var5).next();
		buffer.vertex(v11.x, v11.y, v11.z).texture(var3, var5).next();

		itemIcon = 41;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;
		float o = 0.999F;

		buffer.vertex(v13.x * o, v13.y, v13.z * o).texture(var3, var6).next();
		buffer.vertex(v14.x * o, v14.y, v14.z * o).texture(var4, var6).next();
		buffer.vertex(v18.x * o, v18.y, v18.z * o).texture(var4, var5).next();
		buffer.vertex(v17.x * o, v17.y, v17.z * o).texture(var3, var5).next();
		buffer.vertex(v14.x * o, v14.y, v14.z * o).texture(var3, var6).next();
		buffer.vertex(v15.x * o, v15.y, v15.z * o).texture(var4, var6).next();
		buffer.vertex(v19.x * o, v19.y, v19.z * o).texture(var4, var5).next();
		buffer.vertex(v18.x * o, v18.y, v18.z * o).texture(var3, var5).next();
		buffer.vertex(v15.x * o, v15.y, v15.z * o).texture(var3, var6).next();
		buffer.vertex(v16.x * o, v16.y, v16.z * o).texture(var4, var6).next();
		buffer.vertex(v20.x * o, v20.y, v20.z * o).texture(var4, var5).next();
		buffer.vertex(v19.x * o, v19.y, v19.z * o).texture(var3, var5).next();
		buffer.vertex(v16.x * o, v16.y, v16.z * o).texture(var3, var6).next();
		buffer.vertex(v13.x * o, v13.y, v13.z * o).texture(var4, var6).next();
		buffer.vertex(v17.x * o, v17.y, v17.z * o).texture(var4, var5).next();
		buffer.vertex(v20.x * o, v20.y, v20.z * o).texture(var3, var5).next();
		buffer.vertex(v14.x, v14.y, v14.z).texture(var3, var6).next();
		buffer.vertex(v13.x, v13.y, v13.z).texture(var4, var6).next();
		buffer.vertex(v17.x, v17.y, v17.z).texture(var4, var5).next();
		buffer.vertex(v18.x, v18.y, v18.z).texture(var3, var5).next();
		buffer.vertex(v15.x, v15.y, v15.z).texture(var3, var6).next();
		buffer.vertex(v14.x, v14.y, v14.z).texture(var4, var6).next();
		buffer.vertex(v18.x, v18.y, v18.z).texture(var4, var5).next();
		buffer.vertex(v19.x, v19.y, v19.z).texture(var3, var5).next();
		buffer.vertex(v16.x, v16.y, v16.z).texture(var3, var6).next();
		buffer.vertex(v15.x, v15.y, v15.z).texture(var4, var6).next();
		buffer.vertex(v19.x, v19.y, v19.z).texture(var4, var5).next();
		buffer.vertex(v20.x, v20.y, v20.z).texture(var3, var5).next();
		buffer.vertex(v13.x, v13.y, v13.z).texture(var3, var6).next();
		buffer.vertex(v16.x, v16.y, v16.z).texture(var4, var6).next();
		buffer.vertex(v20.x, v20.y, v20.z).texture(var4, var5).next();
		buffer.vertex(v17.x, v17.y, v17.z).texture(var3, var5).next();

		itemIcon = 42;
		var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
		var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
		var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
		var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

		buffer.vertex(v13.x, v13.y, v13.z).texture(var3, var6).next();
		buffer.vertex(v15.x, v15.y, v15.z).texture(var4, var6).next();
		buffer.vertex(v19.x, v19.y, v19.z).texture(var4, var5).next();
		buffer.vertex(v17.x, v17.y, v17.z).texture(var3, var5).next();
		buffer.vertex(v16.x, v16.y, v16.z).texture(var3, var6).next();
		buffer.vertex(v14.x, v14.y, v14.z).texture(var4, var6).next();
		buffer.vertex(v18.x, v18.y, v18.z).texture(var4, var5).next();
		buffer.vertex(v20.x, v20.y, v20.z).texture(var3, var5).next();
		buffer.vertex(v15.x, v15.y, v15.z).texture(var3, var6).next();
		buffer.vertex(v13.x, v13.y, v13.z).texture(var4, var6).next();
		buffer.vertex(v17.x, v17.y, v17.z).texture(var4, var5).next();
		buffer.vertex(v19.x, v19.y, v19.z).texture(var3, var5).next();
		buffer.vertex(v14.x, v14.y, v14.z).texture(var3, var6).next();
		buffer.vertex(v16.x, v16.y, v16.z).texture(var4, var6).next();
		buffer.vertex(v20.x, v20.y, v20.z).texture(var4, var5).next();
		buffer.vertex(v18.x, v18.y, v18.z).texture(var3, var5).next();
		if (!hasFuse)
		{
			itemIcon = 37;
			var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
			var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
			var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
			var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

			buffer.vertex(v1.x, v1.y, v1.z).texture(var3, var6).next();
			buffer.vertex(v2.x, v2.y, v2.z).texture(var4, var6).next();
			buffer.vertex(v3.x, v3.y, v3.z).texture(var4, var5).next();
			buffer.vertex(v4.x, v4.y, v4.z).texture(var3, var5).next();
		}
		else {
			itemIcon = 43;
			var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
			var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
			var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
			var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

			buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).next();
			buffer.vertex(v1.x, v1.y, v1.z).texture(var4, var6).next();
			buffer.vertex(v2.x, v2.y, v2.z).texture(var4, var5).next();
			buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).next();
			buffer.vertex(v2.x, v2.y, v2.z).texture(var4, var5).next();
			buffer.vertex(v3.x, v3.y, v3.z).texture(var4, var6).next();
			buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).next();
			buffer.vertex(v3.x, v3.y, v3.z).texture(var3, var5).next();
			buffer.vertex(v4.x, v4.y, v4.z).texture(var4, var5).next();
			buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).next();
			buffer.vertex(v4.x, v4.y, v4.z).texture(var4, var5).next();
			buffer.vertex(v1.x, v1.y, v1.z).texture(var4, var6).next();
		}
		matrices.pop();
		matrices.pop();
	}
}

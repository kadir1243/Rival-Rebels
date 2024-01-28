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

import assets.rivalrebels.client.renderhelper.Vertice;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelSteel
{
    float		s			= 0.5F;

	Vertice		v1			= new Vertice(s, s, s);
	Vertice		v2			= new Vertice(s, s, -s);
	Vertice		v3			= new Vertice(-s, s, -s);
	Vertice		v4			= new Vertice(-s, s, s);

	Vertice		v5			= new Vertice(s, -s, s);
	Vertice		v6			= new Vertice(s, -s, -s);
	Vertice		v7			= new Vertice(-s, -s, -s);
	Vertice		v8			= new Vertice(-s, -s, s);

	public void renderModel(MatrixStack matrices, VertexConsumer buffer)
	{
		matrices.push();

		addVertex(buffer, v1, 0, 0);
		addVertex(buffer, v5, 1, 0);
		addVertex(buffer, v8, 1, 1);
		addVertex(buffer, v4, 0, 1);
		addVertex(buffer, v4, 0, 0);
		addVertex(buffer, v8, 1, 0);
		addVertex(buffer, v7, 1, 1);
		addVertex(buffer, v3, 0, 1);
		addVertex(buffer, v3, 0, 0);
		addVertex(buffer, v7, 1, 0);
		addVertex(buffer, v6, 1, 1);
		addVertex(buffer, v2, 0, 1);
		addVertex(buffer, v2, 0, 0);
		addVertex(buffer, v6, 1, 0);
		addVertex(buffer, v5, 1, 1);
		addVertex(buffer, v1, 0, 1);
		addVertex(buffer, v3, 1, 0);
		addVertex(buffer, v2, 1, 1);
		addVertex(buffer, v5, 0, 0);
		addVertex(buffer, v8, 0, 1);

		matrices.pop();
	}

	private void addVertex(VertexConsumer buffer, Vertice v, float t, float t2) {
		buffer.vertex(v.x * 0.999, v.y * 0.999, v.z * 0.999).texture(t, t2).next();
	}
}

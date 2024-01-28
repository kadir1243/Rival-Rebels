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
// Copyrighted Rodolian Material
package assets.rivalrebels.client.model;

import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.TextureVertice;
import assets.rivalrebels.client.renderhelper.Vertice;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;

public class ModelAstroBlasterBarrel
{
	private float	i			= 0.035714286f;
	private float[]	barrelx		= { 0f, 0.2f, 0.2f, 0.25f, 0.25f, 0.2f, 0f };
	private float[]	barrely		= { 0f, 0f, 0.2f, 0.2f, 0f, -0.1f, -0.1f };
	private float[]	tsart		= { i * 28, i * 24, i * 16, i * 15, i * 7, i * 4, i * 3, 0 };

	private int		segments	= 8;
	private float	deg			= (float) Math.PI * 2f / segments;
	private float	sin			= (float) Math.sin(deg);
	private float	cos			= (float) Math.cos(deg);
	private float	add			= 360 / segments;

	public void render(MatrixStack matrices, VertexConsumer buffer)
	{
		matrices.push();
		for (float i = 0; i < segments; i++)
		{
			matrices.push();
			matrices.multiply(new Quaternion(add * i, 0, 1, 0));
			for (int f = 1; f < barrelx.length; f++)
			{
				TextureVertice t1 = new TextureVertice((1f / segments) * i, tsart[f]);
				TextureVertice t2 = new TextureVertice((1f / segments) * i, tsart[f - 1]);
				TextureVertice t3 = new TextureVertice((1f / segments) * (i + 1), tsart[f - 1]);
				TextureVertice t4 = new TextureVertice((1f / segments) * (i + 1), tsart[f]);
				RenderHelper.addFace(buffer, new Vertice(0f, barrely[f], barrelx[f]),
						new Vertice(0f, barrely[f - 1], barrelx[f - 1]),
						new Vertice(barrelx[f - 1] * sin, barrely[f - 1], barrelx[f - 1] * cos),
						new Vertice(barrelx[f] * sin, barrely[f], barrelx[f] * cos), t1, t2, t3, t4);
			}
			matrices.pop();
		}
		matrices.pop();
	}
}

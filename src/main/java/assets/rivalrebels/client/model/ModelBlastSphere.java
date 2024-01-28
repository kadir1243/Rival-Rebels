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

import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.Vertice;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vector4f;

public class ModelBlastSphere
{
	Vertice	vx	= new Vertice(1, 0, 0).normalize();
	Vertice	vy	= new Vertice(0, 1, 0).normalize();
	Vertice	vz	= new Vertice(0, 0, 1).normalize();
	Vertice	vxy	= new Vertice(0.5f, 0.5f, 0).normalize();
	Vertice	vyz	= new Vertice(0, 0.5f, 0.5f).normalize();
	Vertice	vxz	= new Vertice(0.5f, 0, 0.5f).normalize();
	Vertice	vx1	= new Vertice(0.75f, 0.25f, 0).normalize();
	Vertice	vx2	= new Vertice(0.5f, 0.25f, 0.25f).normalize();
	Vertice	vx3	= new Vertice(0.75f, 0, 0.25f).normalize();
	Vertice	vy1	= new Vertice(0, 0.75f, 0.25f).normalize();
	Vertice	vy2	= new Vertice(0.25f, 0.5f, 0.25f).normalize();
	Vertice	vy3	= new Vertice(0.25f, 0.75f, 0).normalize();
	Vertice	vz1	= new Vertice(0.25f, 0, 0.75f).normalize();
	Vertice	vz2	= new Vertice(0.25f, 0.25f, 0.5f).normalize();
	Vertice	vz3	= new Vertice(0, 0.25f, 0.75f).normalize();

	public void renderModel(MatrixStack matrices, VertexConsumer buffer, float size, float red, float green, float blue, float alpha)
	{
        matrices.push();
        matrices.scale(size, size, size);
        Vector4f color = new Vector4f(red, green, blue, alpha);
        RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
        for (int i = 0; i < 2; i++)
		{
			matrices.multiply(new Quaternion(i * 180, 0, 0, 1));
			for (int p = 0; p < 4; p++)
			{
				matrices.push();
				matrices.multiply(new Quaternion(p * 90, 0, 1, 0));

				RenderHelper.addTri(buffer, vy, vy1, vy3, color);
				RenderHelper.addTri(buffer, vy1, vyz, vy2, color);
				RenderHelper.addTri(buffer, vy3, vy2, vxy, color);
				RenderHelper.addTri(buffer, vy1, vy2, vy3, color);

				RenderHelper.addTri(buffer, vx, vx1, vx3, color);
				RenderHelper.addTri(buffer, vx1, vxy, vx2, color);
				RenderHelper.addTri(buffer, vx3, vx2, vxz, color);
				RenderHelper.addTri(buffer, vx1, vx2, vx3, color);

				RenderHelper.addTri(buffer, vz, vz1, vz3, color);
				RenderHelper.addTri(buffer, vz1, vxz, vz2, color);
				RenderHelper.addTri(buffer, vz3, vz2, vyz, color);
				RenderHelper.addTri(buffer, vz1, vz2, vz3, color);

				RenderHelper.addTri(buffer, vyz, vz2, vy2, color);
				RenderHelper.addTri(buffer, vxy, vy2, vx2, color);
				RenderHelper.addTri(buffer, vxz, vx2, vz2, color);
				RenderHelper.addTri(buffer, vx2, vy2, vz2, color);

				matrices.pop();
			}
		}
        matrices.pop();
    }

}

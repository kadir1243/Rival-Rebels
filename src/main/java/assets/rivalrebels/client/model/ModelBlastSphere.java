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
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ModelBlastSphere {
	private static final Vector3f	vx	= new Vector3f(1, 0, 0).normalize();
	private static final Vector3f	vy	= new Vector3f(0, 1, 0).normalize();
	private static final Vector3f	vz	= new Vector3f(0, 0, 1).normalize();
	private static final Vector3f	vxy	= new Vector3f(0.5f, 0.5f, 0).normalize();
	private static final Vector3f	vyz	= new Vector3f(0, 0.5f, 0.5f).normalize();
	private static final Vector3f	vxz	= new Vector3f(0.5f, 0, 0.5f).normalize();
	private static final Vector3f	vx1	= new Vector3f(0.75f, 0.25f, 0).normalize();
	private static final Vector3f	vx2	= new Vector3f(0.5f, 0.25f, 0.25f).normalize();
	private static final Vector3f	vx3	= new Vector3f(0.75f, 0, 0.25f).normalize();
	private static final Vector3f	vy1	= new Vector3f(0, 0.75f, 0.25f).normalize();
	private static final Vector3f	vy2	= new Vector3f(0.25f, 0.5f, 0.25f).normalize();
	private static final Vector3f	vy3	= new Vector3f(0.25f, 0.75f, 0).normalize();
	private static final Vector3f	vz1	= new Vector3f(0.25f, 0, 0.75f).normalize();
	private static final Vector3f	vz2	= new Vector3f(0.25f, 0.25f, 0.5f).normalize();
	private static final Vector3f	vz3	= new Vector3f(0, 0.25f, 0.75f).normalize();

	public static void renderModel(MatrixStack matrices, VertexConsumerProvider vertexConsumers, float size, float red, float green, float blue, float alpha)
	{
        matrices.push();
        matrices.scale(size, size, size);
        Vector4f color = new Vector4f(red, green, blue, alpha);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        for (int i = 0; i < 2; i++) {
			matrices.multiply(new Quaternionf(i * 180, 0, 0, 1));
			for (int p = 0; p < 4; p++) {
				matrices.push();
				matrices.multiply(new Quaternionf(p * 90, 0, 1, 0));

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

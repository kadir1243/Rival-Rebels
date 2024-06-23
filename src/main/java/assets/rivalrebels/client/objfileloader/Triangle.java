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
package assets.rivalrebels.client.objfileloader;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class Triangle
{
	public Vertice[]	pa;

	public Triangle(Vertice[] PA)
	{
		if (PA.length != 3) throw new IllegalArgumentException("Invalid Triangle! Specified Vec3d Array must have 3 Vec3s");
		pa = PA;
	}

    public void render(VertexConsumer buffer, int light, int overlay) {
        render(buffer, new Vector4f(1, 1, 1, 1), light, overlay);
    }

    public void render(VertexConsumer buffer, Vector4f color, int light, int overlay) {
        for (Vertice vertice : pa) {
            vertice.render(buffer, color, light, overlay);
        }
    }

    public void normalize()
	{
        for (Vertice vertice : pa) {
            vertice.normalize();
        }
	}

	public void scale(Vec3 v)
	{
        for (Vertice vertice : pa) {
            vertice.scale(v);
        }
	}

	public Triangle[] refine()
	{
		Triangle[] p = new Triangle[4];
		if (pa.length == 3)
		{
			Vertice mp1 = Vertice.average(pa[0], pa[1]);
			Vertice mp2 = Vertice.average(pa[1], pa[2]);
			Vertice mp3 = Vertice.average(pa[2], pa[0]);
			p[0] = new Triangle(new Vertice[] { pa[0].copy(), mp1.copy(), mp3.copy() });
			p[1] = new Triangle(new Vertice[] { pa[1].copy(), mp2.copy(), mp1.copy() });
			p[2] = new Triangle(new Vertice[] { pa[2].copy(), mp3.copy(), mp2.copy() });
			p[3] = new Triangle(new Vertice[] { mp1.copy(), mp2.copy(), mp3.copy() });
		}
		return p;
	}
}

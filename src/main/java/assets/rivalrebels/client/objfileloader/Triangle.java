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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class Triangle
{
	public Vertice[]	pa;

	public Triangle(Vertice[] PA)
	{
		if (PA.length != 3) throw new IllegalArgumentException("Invalid Triangle! Specified Vec3d Array must have 3 Vec3s");
		pa = PA;
	}

    public void render(PoseStack pose, VertexConsumer buffer, int light, int overlay) {
        render(pose, buffer, CommonColors.WHITE, light, overlay);
    }

    public void render(PoseStack pose, VertexConsumer buffer, int color, int light, int overlay) {
        for (Vertice vertice : pa) {
            vertice.render(pose, buffer, color, light, overlay);
        }
    }

    public void normalize() {
        Arrays.setAll(pa, i -> pa[i].normalize());
	}

	public void scale(Vec3 v) {
        Arrays.setAll(pa, i -> pa[i].scale(v));
	}

	public Triangle[] refine()
	{
		Triangle[] p = new Triangle[4];
		if (pa.length == 3)
		{
			Vertice mp1 = Vertice.average(pa[0], pa[1]);
			Vertice mp2 = Vertice.average(pa[1], pa[2]);
			Vertice mp3 = Vertice.average(pa[2], pa[0]);
			p[0] = new Triangle(new Vertice[] { pa[0], mp1, mp3});
			p[1] = new Triangle(new Vertice[] { pa[1], mp2, mp1});
			p[2] = new Triangle(new Vertice[] { pa[2], mp3, mp2});
			p[3] = new Triangle(new Vertice[] { mp1,   mp2, mp3});
		}
		return p;
	}
}

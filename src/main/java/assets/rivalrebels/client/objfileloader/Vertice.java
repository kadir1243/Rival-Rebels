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
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class Vertice
{
	public Vec3 p;
	public Vec3 n;
	public Vec2 t;

    public Vertice(float a, float b, float c, float d, float e, float f, float g, float h)
	{
		p = new Vec3(a, b, c);
		n = new Vec3(d, e, f);
		t = new Vec2(g, h);
	}

	public Vertice(Vec3 P, Vec3 N, Vec2 T)
	{
		p = P;
		n = N;
		t = T;
	}

	public void normalize() {
        n = n.normalize();
	}

    public void render(VertexConsumer buffer, int light, int overlay) {
        render(buffer, new Vector4f(1, 1, 1, 1), light, overlay);
    }

    public void render(VertexConsumer buffer, Vector4f color, int light, int overlay) {
        buffer.addVertex((float) p.x, (float) p.y, (float) p.z, FastColor.ARGB32.colorFromFloat(color.x, color.y, color.z, color.w), t.x, t.y, overlay, light, (float) n.x, (float) n.y, (float) n.z);
    }

    public Vertice copy() {
        return new Vertice(p, n, t);
    }

	public static Vertice average(Vertice v1, Vertice v2)
	{
        return new Vertice((float) ((v1.p.x + v2.p.x) / 2F), (float) ((v1.p.y + v2.p.y) / 2f), (float) ((v1.p.z + v2.p.z) / 2f), (float) ((v1.n.x + v2.n.x) / 2f), (float) ((v1.n.y + v2.n.y) / 2f), (float) ((v1.n.z + v2.n.z) / 2f), (v1.t.x + v2.t.x) / 2f, (v1.t.y + v2.t.y) / 2f);
	}

	public void scale(Vec3 v) {
        p = p.multiply(v);
	}
}

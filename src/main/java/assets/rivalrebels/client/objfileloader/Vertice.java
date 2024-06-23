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

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class Vertice
{
	public Vec3d p;
	public Vec3d n;
	public Vec2f t;

    public Vertice(float a, float b, float c, float d, float e, float f, float g, float h)
	{
		p = new Vec3d(a, b, c);
		n = new Vec3d(d, e, f);
		t = new Vec2f(g, h);
	}

	public Vertice(Vec3d P, Vec3d N, Vec2f T)
	{
		p = P;
		n = N;
		t = T;
	}

	public void normalize() {
        n = n.normalize();
	}

    @Deprecated
	public void render(VertexConsumer buffer) {
        buffer.vertex(p.x, p.y, p.z).color(1, 1, 1, 1).texture(t.x, t.y).overlay(OverlayTexture.DEFAULT_UV).normal((float) n.x, (float) n.y, (float) n.z).next();
	}

    public void render(VertexConsumer buffer, int light, int overlay) {
        render(buffer, new Vector4f(1, 1, 1, 1), light, overlay);
    }

    public void render(VertexConsumer buffer, Vector4f color, int light, int overlay) {
        buffer.vertex((float) p.x, (float) p.y, (float) p.z, color.x, color.y, color.z, color.w, t.x, t.y, overlay, light, (float) n.x, (float) n.y, (float) n.z);
    }

    public Vertice copy() {
        return new Vertice(p, n, t);
    }

	public static Vertice average(Vertice v1, Vertice v2)
	{
        return new Vertice((float) ((v1.p.x + v2.p.x) / 2F), (float) ((v1.p.y + v2.p.y) / 2f), (float) ((v1.p.z + v2.p.z) / 2f), (float) ((v1.n.x + v2.n.x) / 2f), (float) ((v1.n.y + v2.n.y) / 2f), (float) ((v1.n.z + v2.n.z) / 2f), (v1.t.x + v2.t.x) / 2f, (v1.t.y + v2.t.y) / 2f);
	}

	public void scale(Vec3d v) {
        p = p.multiply(v);
	}
}

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

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.Vec3;

public class Vertice
{
	public Vec3	p;
	public Vec3	n;
	public Vec2	t;

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

	public void render(WorldRenderer worldRenderer) {
        worldRenderer.pos(p.xCoord, p.yCoord, p.zCoord).tex(t.x, t.y).normal((float) n.xCoord, (float) n.yCoord, (float) n.zCoord).endVertex();
	}

	public void renderWireframe(WorldRenderer worldRenderer)
	{
		worldRenderer.pos(p.xCoord, p.yCoord, p.zCoord).endVertex();
	}

	@Override
	public Vertice clone()
	{
		return new Vertice(p, n, t.clone());
	}

	public static Vertice average(Vertice v1, Vertice v2)
	{
        return new Vertice((float) ((v1.p.xCoord + v2.p.xCoord) / 2F), (float) ((v1.p.yCoord + v2.p.yCoord) / 2f), (float) ((v1.p.zCoord + v2.p.zCoord) / 2f), (float) ((v1.n.xCoord + v2.n.xCoord) / 2f), (float) ((v1.n.yCoord + v2.n.yCoord) / 2f), (float) ((v1.n.zCoord + v2.n.zCoord) / 2f), (v1.t.x + v2.t.x) / 2f, (v1.t.y + v2.t.y) / 2f);
	}

	public void scale(Vec3 v) {
        p = new Vec3(p.xCoord * v.xCoord, p.yCoord * v.yCoord, p.zCoord * v.zCoord);
	}
}

class Vec2
{
	float	x, y;

	public Vec2(float X, float Y)
	{
		x = X;
		y = Y;
	}

	@Override
	public Vec2 clone()
	{
		return new Vec2(x, y);
	}
}

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
import assets.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Vector3f;

public class ModelRocketLauncherBody
{
	private static final float			m			= 0.3125f;
	private static final float			l			= 0.4375f;
	private static final float			o			= 0.5f;
	private static final float			n			= 1.5f;

	private static final Vector3f			llauncher1	= new Vector3f(-m, o, n);
	private static final Vector3f			llauncher2	= new Vector3f(-l, l, n);
	private static final Vector3f			llauncher3	= new Vector3f(-o, m, n);
	private static final Vector3f			llauncher4	= new Vector3f(-o, -m, n);
	private static final Vector3f			llauncher5	= new Vector3f(-l, -l, n);
	private static final Vector3f			llauncher6	= new Vector3f(-m, -o, n);
	private static final Vector3f			llauncher7	= new Vector3f(m, -o, n);
	private static final Vector3f			llauncher8	= new Vector3f(l, -l, n);
	private static final Vector3f			llauncher9	= new Vector3f(o, -m, n);
	private static final Vector3f			llauncher10	= new Vector3f(o, m, n);
	private static final Vector3f			llauncher11	= new Vector3f(l, l, n);
	private static final Vector3f			llauncher12	= new Vector3f(m, o, n);

	private static final Vector3f			rlauncher1	= new Vector3f(-m, o, -n);
	private static final Vector3f			rlauncher2	= new Vector3f(-l, l, -n);
	private static final Vector3f			rlauncher3	= new Vector3f(-o, m, -n);
	private static final Vector3f			rlauncher4	= new Vector3f(-o, -m, -n);
	private static final Vector3f			rlauncher5	= new Vector3f(-l, -l, -n);
	private static final Vector3f			rlauncher6	= new Vector3f(-m, -o, -n);
	private static final Vector3f			rlauncher7	= new Vector3f(m, -o, -n);
	private static final Vector3f			rlauncher8	= new Vector3f(l, -l, -n);
	private static final Vector3f			rlauncher9	= new Vector3f(o, -m, -n);
	private static final Vector3f			rlauncher10	= new Vector3f(o, m, -n);
	private static final Vector3f			rlauncher11	= new Vector3f(l, l, -n);
	private static final Vector3f			rlauncher12	= new Vector3f(m, o, -n);

	private static final float			a			= 0f;
	private static final float			b			= 0.09375f;
	private static final float			c			= 0.1875f;
	private static final float			d			= 0.5f;
	private static final float			e			= 0.59375f;
	private static final float			f			= 0.6875f;
	private static final float			g			= 1f;
	private static final float			h			= 0.125f;
	private static final float			i			= 0.625f;

	private static final TextureVertice	l1f			= new TextureVertice(i, f);
	private static final TextureVertice	l1s			= new TextureVertice(i, c);
	private static final TextureVertice	l2			= new TextureVertice(i, b);
	private static final TextureVertice	l3f			= new TextureVertice(i, a);
	private static final TextureVertice	l3s			= new TextureVertice(g, c);
	private static final TextureVertice	l4			= new TextureVertice(g, d);
	private static final TextureVertice	l5			= new TextureVertice(g, e);
	private static final TextureVertice	l6f			= new TextureVertice(g, f);
	private static final TextureVertice	l6s			= new TextureVertice(h, g);
	private static final TextureVertice	l7			= new TextureVertice(h, f);
	private static final TextureVertice	l8			= new TextureVertice(h, e);
	private static final TextureVertice	l9			= new TextureVertice(h, d);
	private static final TextureVertice	l10			= new TextureVertice(h, c);
	private static final TextureVertice	l11			= new TextureVertice(h, b);
	private static final TextureVertice	l12f		= new TextureVertice(h, a);
	private static final TextureVertice	l12s		= new TextureVertice(i, g);

	private static final TextureVertice	r1f			= new TextureVertice(g, f);
	private static final TextureVertice	r1s			= new TextureVertice(g, c);
	private static final TextureVertice	r2			= new TextureVertice(g, b);
	private static final TextureVertice	r3f			= new TextureVertice(g, a);
	private static final TextureVertice	r3s			= new TextureVertice(i, c);
	private static final TextureVertice	r4			= new TextureVertice(i, d);
	private static final TextureVertice	r5			= new TextureVertice(i, e);
	private static final TextureVertice	r6f			= new TextureVertice(i, f);
	private static final TextureVertice	r6s			= new TextureVertice(d, g);
	private static final TextureVertice	r7			= new TextureVertice(d, f);
	private static final TextureVertice	r8			= new TextureVertice(d, e);
	private static final TextureVertice	r9			= new TextureVertice(d, d);
	private static final TextureVertice	r10			= new TextureVertice(d, c);
	private static final TextureVertice	r11			= new TextureVertice(d, b);
	private static final TextureVertice	r12f		= new TextureVertice(d, a);
	private static final TextureVertice	r12s		= new TextureVertice(g, g);

	private static final float			p			= 0.125f;
	private static final float			q			= 0.1093755f;
	private static final float			r			= 0.078125f;
	private static final float			k			= 0.34375f;
	private static final float			t			= 0.125f;
	private static final float			u			= 0.625f;

	private static final TextureVertice	ls1			= new TextureVertice((t - r) / 2f, k + p * 2);
	private static final TextureVertice	ls2			= new TextureVertice((t - q) / 2f, k + q * 2);
	private static final TextureVertice	ls3			= new TextureVertice((t - p) / 2f, k + r * 2);
	private static final TextureVertice	ls4			= new TextureVertice((t - p) / 2f, k - r * 2);
	private static final TextureVertice	ls5			= new TextureVertice((t - q) / 2f, k - q * 2);
	private static final TextureVertice	ls6			= new TextureVertice((t - r) / 2f, k - p * 2);
	private static final TextureVertice	ls7			= new TextureVertice((t + r) / 2f, k - p * 2);
	private static final TextureVertice	ls8			= new TextureVertice((t + q) / 2f, k - q * 2);
	private static final TextureVertice	ls9			= new TextureVertice((t + p) / 2f, k - r * 2);
	private static final TextureVertice	ls10		= new TextureVertice((t + p) / 2f, k + r * 2);
	private static final TextureVertice	ls11		= new TextureVertice((t + q) / 2f, k + q * 2);
	private static final TextureVertice	ls12		= new TextureVertice((t + r) / 2f, k + p * 2);

	private static final TextureVertice	rs1			= new TextureVertice((u - r) / 2f + 0.25f, k + p * 2);
	private static final TextureVertice	rs2			= new TextureVertice((u - q) / 2f + 0.25f, k + q * 2);
	private static final TextureVertice	rs3			= new TextureVertice((u - p) / 2f + 0.25f, k + r * 2);
	private static final TextureVertice	rs4			= new TextureVertice((u - p) / 2f + 0.25f, k - r * 2);
	private static final TextureVertice	rs5			= new TextureVertice((u - q) / 2f + 0.25f, k - q * 2);
	private static final TextureVertice	rs6			= new TextureVertice((u - r) / 2f + 0.25f, k - p * 2);
	private static final TextureVertice	rs7			= new TextureVertice((u + r) / 2f + 0.25f, k - p * 2);
	private static final TextureVertice	rs8			= new TextureVertice((u + q) / 2f + 0.25f, k - q * 2);
	private static final TextureVertice	rs9			= new TextureVertice((u + p) / 2f + 0.25f, k - r * 2);
	private static final TextureVertice	rs10		= new TextureVertice((u + p) / 2f + 0.25f, k + r * 2);
	private static final TextureVertice	rs11		= new TextureVertice((u + q) / 2f + 0.25f, k + q * 2);
	private static final TextureVertice	rs12		= new TextureVertice((u + r) / 2f + 0.25f, k + p * 2);

	public static void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay)
	{
		matrices.pushPose();
		RenderHelper.addFace(buffer, llauncher1, llauncher12, rlauncher12, rlauncher1, l1f, l12s, r12s, r1f, light, overlay);
		RenderHelper.addFace(buffer, llauncher2, llauncher1, rlauncher1, rlauncher2, l2, l1s, r1s, r2, light, overlay);
		RenderHelper.addFace(buffer, llauncher3, llauncher2, rlauncher2, rlauncher3, l3f, l2, r2, r3f, light, overlay);
		RenderHelper.addFace(buffer, llauncher4, llauncher3, rlauncher3, rlauncher4, l4, l3s, r3s, r4, light, overlay);
		RenderHelper.addFace(buffer, llauncher5, llauncher4, rlauncher4, rlauncher5, l5, l4, r4, r5, light, overlay);
		RenderHelper.addFace(buffer, llauncher6, llauncher5, rlauncher5, rlauncher6, l6f, l5, r5, r6f, light, overlay);
		RenderHelper.addFace(buffer, llauncher7, llauncher6, rlauncher6, rlauncher7, l7, l6s, r6s, r7, light, overlay);
		RenderHelper.addFace(buffer, llauncher8, llauncher7, rlauncher7, rlauncher8, l8, l7, r7, r8, light, overlay);
		RenderHelper.addFace(buffer, llauncher9, llauncher8, rlauncher8, rlauncher9, l9, l8, r8, r9, light, overlay);
		RenderHelper.addFace(buffer, llauncher10, llauncher9, rlauncher9, rlauncher10, l10, l9, r9, r10, light, overlay);
		RenderHelper.addFace(buffer, llauncher11, llauncher10, rlauncher10, rlauncher11, l11, l10, r10, r11, light, overlay);
		RenderHelper.addFace(buffer, llauncher12, llauncher11, rlauncher11, rlauncher12, l12f, l11, r11, r12f, light, overlay);

		RenderHelper.addFace(buffer, llauncher1, llauncher2, llauncher11, llauncher12, ls6, ls5, ls8, ls7, light, overlay); // left side
		RenderHelper.addFace(buffer, llauncher2, llauncher3, llauncher10, llauncher11, ls5, ls4, ls9, ls8, light, overlay);
		RenderHelper.addFace(buffer, llauncher3, llauncher4, llauncher9, llauncher10, ls4, ls3, ls10, ls9, light, overlay);
		RenderHelper.addFace(buffer, llauncher4, llauncher5, llauncher8, llauncher9, ls3, ls2, ls11, ls10, light, overlay);
		RenderHelper.addFace(buffer, llauncher5, llauncher6, llauncher7, llauncher8, ls2, ls1, ls12, ls11, light, overlay);

		RenderHelper.addFace(buffer, rlauncher2, rlauncher1, rlauncher12, rlauncher11, rs8, rs7, rs6, rs5, light, overlay); // right side
		RenderHelper.addFace(buffer, rlauncher3, rlauncher2, rlauncher11, rlauncher10, rs9, rs8, rs5, rs4, light, overlay);
		RenderHelper.addFace(buffer, rlauncher4, rlauncher3, rlauncher10, rlauncher9, rs10, rs9, rs4, rs3, light, overlay);
		RenderHelper.addFace(buffer, rlauncher5, rlauncher4, rlauncher9, rlauncher8, rs11, rs10, rs3, rs2, light, overlay);
		RenderHelper.addFace(buffer, rlauncher6, rlauncher5, rlauncher8, rlauncher7, rs12, rs11, rs2, rs1, light, overlay);
		matrices.popPose();
	}
}

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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ModelLoader
{
	private static final float			m			= 0.3125f;
	private static final float			l			= 0.4375f;
	private static final float			o			= 0.5f;

	private static final Vector3f			lloader1	= new Vector3f(-m, o, o);
	private static final Vector3f			lloader2	= new Vector3f(-l, l, o);
	private static final Vector3f			lloader3	= new Vector3f(-o, m, o);
	private static final Vector3f			lloader4	= new Vector3f(-o, -m, o);
	private static final Vector3f			lloader5	= new Vector3f(-l, -l, o);
	private static final Vector3f			lloader6	= new Vector3f(-m, -o, o);
	private static final Vector3f			lloader7	= new Vector3f(m, -o, o);
	private static final Vector3f			lloader8	= new Vector3f(l, -l, o);
	private static final Vector3f			lloader9	= new Vector3f(o, -m, o);
	private static final Vector3f			lloader10	= new Vector3f(o, m, o);
	private static final Vector3f			lloader11	= new Vector3f(l, l, o);
	private static final Vector3f			lloader12	= new Vector3f(m, o, o);

	private static final Vector3f			rloader1	= new Vector3f(-m, o, -o);
	private static final Vector3f			rloader2	= new Vector3f(-l, l, -o);
	private static final Vector3f			rloader3	= new Vector3f(-o, m, -o);
	private static final Vector3f			rloader4	= new Vector3f(-o, -m, -o);
	private static final Vector3f			rloader5	= new Vector3f(-l, -l, -o);
	private static final Vector3f			rloader6	= new Vector3f(-m, -o, -o);
	private static final Vector3f			rloader7	= new Vector3f(m, -o, -o);
	private static final Vector3f			rloader8	= new Vector3f(l, -l, -o);
	private static final Vector3f			rloader9	= new Vector3f(o, -m, -o);
	private static final Vector3f			rloader10	= new Vector3f(o, m, -o);
	private static final Vector3f			rloader11	= new Vector3f(l, l, -o);
	private static final Vector3f			rloader12	= new Vector3f(m, o, -o);

	private static final float			a			= 0f;
	private static final float			b			= 0.09375f;
	private static final float			c			= 0.1875f;
	private static final float			d			= 0.5f;
	private static final float			e			= 0.59375f;
	private static final float			f			= 0.6875f;
	private static final float			g			= 1f;
	private static final float			h			= 0.25f;
	private static final float			i			= 0.75f;

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

	private static final TextureVertice	ls1			= new TextureVertice(t - r, k + p * 2);
	private static final TextureVertice	ls2			= new TextureVertice(t - q, k + q * 2);
	private static final TextureVertice	ls3			= new TextureVertice(t - p, k + r * 2);
	private static final TextureVertice	ls4			= new TextureVertice(t - p, k - r * 2);
	private static final TextureVertice	ls5			= new TextureVertice(t - q, k - q * 2);
	private static final TextureVertice	ls6			= new TextureVertice(t - r, k - p * 2);
	private static final TextureVertice	ls7			= new TextureVertice(t + r, k - p * 2);
	private static final TextureVertice	ls8			= new TextureVertice(t + q, k - q * 2);
	private static final TextureVertice	ls9			= new TextureVertice(t + p, k - r * 2);
	private static final TextureVertice	ls10		= new TextureVertice(t + p, k + r * 2);
	private static final TextureVertice	ls11		= new TextureVertice(t + q, k + q * 2);
	private static final TextureVertice	ls12		= new TextureVertice(t + r, k + p * 2);

	private static final float			u			= 0.625f;

	private static final TextureVertice	rs1			= new TextureVertice(u - r, k + p * 2);
	private static final TextureVertice	rs2			= new TextureVertice(u - q, k + q * 2);
	private static final TextureVertice	rs3			= new TextureVertice(u - p, k + r * 2);
	private static final TextureVertice	rs4			= new TextureVertice(u - p, k - r * 2);
	private static final TextureVertice	rs5			= new TextureVertice(u - q, k - q * 2);
	private static final TextureVertice	rs6			= new TextureVertice(u - r, k - p * 2);
	private static final TextureVertice	rs7			= new TextureVertice(u + r, k - p * 2);
	private static final TextureVertice	rs8			= new TextureVertice(u + q, k - q * 2);
	private static final TextureVertice	rs9			= new TextureVertice(u + p, k - r * 2);
	private static final TextureVertice	rs10		= new TextureVertice(u + p, k + r * 2);
	private static final TextureVertice	rs11		= new TextureVertice(u + q, k + q * 2);
	private static final TextureVertice	rs12		= new TextureVertice(u + r, k + p * 2);

	public static void renderA(VertexConsumer buffer, PoseStack matrices, int light, int overlay)
	{
		matrices.pushPose();
		RenderHelper.addFace(matrices, buffer, lloader1, lloader12, rloader12, rloader1, l1f, l12s, r12s, r1f, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader2, lloader1, rloader1, rloader2, l2, l1s, r1s, r2, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader3, lloader2, rloader2, rloader3, l3f, l2, r2, r3f, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader4, lloader3, rloader3, rloader4, l4, l3s, r3s, r4, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader5, lloader4, rloader4, rloader5, l5, l4, r4, r5, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader6, lloader5, rloader5, rloader6, l6f, l5, r5, r6f, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader7, lloader6, rloader6, rloader7, l7, l6s, r6s, r7, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader8, lloader7, rloader7, rloader8, l8, l7, r7, r8, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader9, lloader8, rloader8, rloader9, l9, l8, r8, r9, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader10, lloader9, rloader9, rloader10, l10, l9, r9, r10, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader11, lloader10, rloader10, rloader11, l11, l10, r10, r11, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader12, lloader11, rloader11, rloader12, l12f, l11, r11, r12f, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader1, lloader2, lloader11, lloader12, ls6, ls5, ls8, ls7, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader2, lloader3, lloader10, lloader11, ls5, ls4, ls9, ls8, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader3, lloader4, lloader9, lloader10, ls4, ls3, ls10, ls9, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader4, lloader5, lloader8, lloader9, ls3, ls2, ls11, ls10, light, overlay);
		RenderHelper.addFace(matrices, buffer, lloader5, lloader6, lloader7, lloader8, ls2, ls1, ls12, ls11, light, overlay);
		RenderHelper.addFace(matrices, buffer, rloader2, rloader1, rloader12, rloader11, rs8, rs7, rs6, rs5, light, overlay);
		RenderHelper.addFace(matrices, buffer, rloader3, rloader2, rloader11, rloader10, rs9, rs8, rs5, rs4, light, overlay);
		RenderHelper.addFace(matrices, buffer, rloader4, rloader3, rloader10, rloader9, rs10, rs9, rs4, rs3, light, overlay);
		RenderHelper.addFace(matrices, buffer, rloader5, rloader4, rloader9, rloader8, rs11, rs10, rs3, rs2, light, overlay);
		RenderHelper.addFace(matrices, buffer, rloader6, rloader5, rloader8, rloader7, rs12, rs11, rs2, rs1, light, overlay);
		matrices.popPose();
	}

	private static final TextureVertice	front1	= new TextureVertice(70f / 256f, 24f / 128f);
	private static final TextureVertice	front2	= new TextureVertice(70f / 256f, 64f / 128f);
	private static final TextureVertice	front3	= new TextureVertice(122f / 256f, 64f / 128f);
	private static final TextureVertice	front4	= new TextureVertice(122f / 256f, 24f / 128f);
	private static final TextureVertice	front5	= new TextureVertice(70f / 256f, 12f / 128f);
	private static final TextureVertice	front6	= new TextureVertice(122f / 256f, 12f / 128f);

	private static final TextureVertice	panel1	= new TextureVertice(92f / 256f, 28f / 128f);
	private static final TextureVertice	panel2	= new TextureVertice(92f / 256f, 60f / 128f);
	private static final TextureVertice	panel3	= new TextureVertice(120f / 256f, 60f / 128f);
	private static final TextureVertice	panel4	= new TextureVertice(120f / 256f, 28f / 128f);
	private static final TextureVertice	panel5	= new TextureVertice(96f / 256f, 32f / 128f);
	private static final TextureVertice	panel6	= new TextureVertice(96f / 256f, 44f / 128f);
	private static final TextureVertice	panel7	= new TextureVertice(116f / 256f, 44f / 128f);
	private static final TextureVertice	panel8	= new TextureVertice(116f / 256f, 32f / 128f);

	private static final TextureVertice	cside1	= new TextureVertice(4f / 256f, 76f / 128f);
	private static final TextureVertice	cside2	= new TextureVertice(0, 88f / 128f);
	private static final TextureVertice	cside3	= new TextureVertice(0, 1);
	private static final TextureVertice	cside4	= new TextureVertice(60f / 256f, 1);
	private static final TextureVertice	cside5	= new TextureVertice(60f / 256f, 88f / 128f);
	private static final TextureVertice	cside6	= new TextureVertice(60f / 256f, 76f / 128f);

	private static final TextureVertice	ctop1	= new TextureVertice(0.5f, 76f / 128f);
	private static final TextureVertice	ctop2	= new TextureVertice(0.5f, 1);
	private static final TextureVertice	ctop3	= new TextureVertice(192f / 256f, 1);
	private static final TextureVertice	ctop4	= new TextureVertice(192f / 256f, 76f / 128f);

	private static final Vector3f			vfront1	= new Vector3f(0.5f, 0.3125f, 0.40625f);
	private static final Vector3f			vfront2	= new Vector3f(0.5f, -0.3125f, 0.40625f);
	private static final Vector3f			vfront3	= new Vector3f(0.5f, -0.3125f, -0.40625f);
	private static final Vector3f			vfront4	= new Vector3f(0.5f, 0.3125f, -0.40625f);
	private static final Vector3f			vfront5	= new Vector3f(0.4375f, 0.4375f, 0.40625f);
	private static final Vector3f			vfront6	= new Vector3f(0.4375f, 0.4375f, -0.40625f);

	private static final Vector3f			vpanel1	= new Vector3f(0.5f, 0.25f, 0.0625f);
	private static final Vector3f			vpanel2	= new Vector3f(0.5f, -0.25f, 0.0625f);
	private static final Vector3f			vpanel3	= new Vector3f(0.5f, -0.25f, -0.375f);
	private static final Vector3f			vpanel4	= new Vector3f(0.5f, 0.25f, -0.375f);
	private static final Vector3f			vpanel5	= new Vector3f(0.6f, 0.1875f, 0f);
	private static final Vector3f			vpanel6	= new Vector3f(0.6f, 0f, 0f);
	private static final Vector3f			vpanel7	= new Vector3f(0.6f, 0f, -0.3125f);
	private static final Vector3f			vpanel8	= new Vector3f(0.6f, 0.1875f, -0.3125f);

	private static final Vector3f			vcside1	= new Vector3f(-0.4375f, 0.4375f, 0.40625f);
	private static final Vector3f			vcside2	= new Vector3f(-0.4375f, 0.3125f, 0.40625f);
	private static final Vector3f			vcside3	= new Vector3f(-0.4375f, -0.3125f, 0.40625f);
	private static final Vector3f			vcside4	= new Vector3f(-0.4375f, -0.3125f, -0.40625f);
	private static final Vector3f			vcside5	= new Vector3f(-0.4375f, 0.3125f, -0.40625f);
	private static final Vector3f			vcside6	= new Vector3f(-0.4375f, 0.4375f, -0.40625f);

	public static void renderB(VertexConsumer buffer, PoseStack matrices, float slide, int light, int overlay)
	{
		matrices.pushPose();
		matrices.translate(slide * 0.9f, 0, 0);
		RenderHelper.addFace(matrices, buffer, vfront1, vfront2, vfront3, vfront4, front1, front2, front3, front4, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfront5, vfront1, vfront4, vfront6, front5, front1, front4, front6, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpanel5, vpanel6, vpanel7, vpanel8, panel5, panel6, panel7, panel8, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpanel1, vpanel5, vpanel8, vpanel4, panel1, panel5, panel8, panel4, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpanel1, vpanel2, vpanel6, vpanel5, panel1, panel2, panel6, panel5, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpanel6, vpanel2, vpanel3, vpanel7, panel6, panel2, panel3, panel7, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpanel8, vpanel7, vpanel3, vpanel4, panel8, panel7, panel3, panel4, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfront1, vfront5, vcside1, vcside2, cside2, cside1, cside6, cside5, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfront6, vfront4, vcside5, vcside6, cside1, cside2, cside5, cside6, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfront2, vfront1, vcside2, vcside3, cside3, cside2, cside5, cside4, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfront4, vfront3, vcside4, vcside5, cside2, cside3, cside4, cside5, light, overlay);
		RenderHelper.addFace(matrices, buffer, vcside1, vfront5, vfront6, vcside6, ctop4, ctop1, ctop2, ctop3, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfront2, vcside3, vcside4, vfront3, ctop2, ctop3, ctop4, ctop1, light, overlay);
		matrices.popPose();
	}
}

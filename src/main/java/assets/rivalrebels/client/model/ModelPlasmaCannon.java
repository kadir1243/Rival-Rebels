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
import assets.rivalrebels.client.renderhelper.TextureFace;
import assets.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Vector3f;

public class ModelPlasmaCannon
{
	TextureFace	bodytop				= new TextureFace(
											new TextureVertice(14f / 64f, 23f / 32f),
											new TextureVertice(37f / 64f, 23f / 32f),
											new TextureVertice(37f / 64f, 29f / 32f),
											new TextureVertice(14f / 64f, 29f / 32f));

	TextureFace	bodytopfront		= new TextureFace(
											new TextureVertice(37f / 64f, 29f / 32f),
											new TextureVertice(43f / 64f, 29f / 32f),
											new TextureVertice(43f / 64f, 23f / 32f),
											new TextureVertice(37f / 64f, 23f / 32f));

	TextureFace	bodytopback			= new TextureFace(
											new TextureVertice(10f / 64f, 20f / 32f),
											new TextureVertice(14f / 64f, 23f / 32f),
											new TextureVertice(14f / 64f, 29f / 32f),
											new TextureVertice(10f / 64f, 32f / 32f));

	TextureFace	bodyback			= new TextureFace(
											new TextureVertice(7f / 64f, 20f / 32f),
											new TextureVertice(10f / 64f, 20f / 32f),
											new TextureVertice(10f / 64f, 32f / 32f),
											new TextureVertice(7f / 64f, 32f / 32f));

	TextureFace	bodybottomback		= new TextureFace(
											new TextureVertice(0f / 64f, 23f / 32f),
											new TextureVertice(7f / 64f, 20f / 32f),
											new TextureVertice(7f / 64f, 32f / 32f),
											new TextureVertice(0f / 64f, 29f / 32f));

	TextureFace	bodybottom			= new TextureFace(
											new TextureVertice(7f / 64f, 11f / 32f),
											new TextureVertice(27f / 64f, 11f / 32f),
											new TextureVertice(27f / 64f, 17f / 32f),
											new TextureVertice(7f / 64f, 17f / 32f));

	TextureFace	bodybottomfront		= new TextureFace(
											new TextureVertice(27f / 64f, 11f / 32f),
											new TextureVertice(33f / 64f, 11f / 32f),
											new TextureVertice(33f / 64f, 17f / 32f),
											new TextureVertice(27f / 64f, 17f / 32f));

	TextureFace	bodytopside			= new TextureFace(
											new TextureVertice(0f / 64f, 4f / 32f),
											new TextureVertice(4f / 64f, 0f / 32f),
											new TextureVertice(27f / 64f, 0f / 32f),
											new TextureVertice(25f / 64f, 4f / 32f));

	TextureFace	bodyside			= new TextureFace(
											new TextureVertice(0f / 64f, 7f / 32f),
											new TextureVertice(0f / 64f, 4f / 32f),
											new TextureVertice(25f / 64f, 4f / 32f),
											new TextureVertice(25f / 64f, 7f / 32f));

	TextureFace	bodybottomside		= new TextureFace(
											new TextureVertice(0f / 64f, 7f / 32f),
											new TextureVertice(25f / 64f, 7f / 32f),
											new TextureVertice(27f / 64f, 11f / 32f),
											new TextureVertice(7f / 64f, 11f / 32f));

	TextureFace	bodysidefront		= new TextureFace(
											new TextureVertice(27f / 64f, 7f / 32f),
											new TextureVertice(27f / 64f, 4f / 32f),
											new TextureVertice(33f / 64f, 4f / 32f),
											new TextureVertice(31f / 64f, 7f / 32f));

	TextureFace	bodysidefrontedge	= new TextureFace(
											new TextureVertice(25f / 64f, 7f / 32f),
											new TextureVertice(25f / 64f, 4f / 32f),
											new TextureVertice(27f / 64f, 4f / 32f),
											new TextureVertice(27f / 64f, 7f / 32f));

	TextureFace	bodytopfrontside	= new TextureFace(
											new TextureVertice(37f / 64f, 23f / 32f),
											new TextureVertice(43f / 64f, 23f / 32f),
											new TextureVertice(37f / 64f, 19f / 32f),
											new TextureVertice(37f / 64f, 19f / 32f));

	TextureFace	detailpurple		= new TextureFace(
											new TextureVertice(21f / 64f, 23f / 32f),
											new TextureVertice(30f / 64f, 23f / 32f),
											new TextureVertice(30f / 64f, 21f / 32f),
											new TextureVertice(21f / 64f, 21f / 32f));

	TextureFace	detailedge1			= new TextureFace(
											new TextureVertice(20f / 64f, 23f / 32f),
											new TextureVertice(21f / 64f, 23f / 32f),
											new TextureVertice(21f / 64f, 21f / 32f),
											new TextureVertice(20f / 64f, 21f / 32f));

	TextureFace	detailedge2			= new TextureFace(
											new TextureVertice(30f / 64f, 20f / 32f),
											new TextureVertice(30f / 64f, 21f / 32f),
											new TextureVertice(21f / 64f, 21f / 32f),
											new TextureVertice(21f / 64f, 20f / 32f));

	TextureFace	handleside			= new TextureFace(
											new TextureVertice(0f / 64f, 11f / 32f),
											new TextureVertice(0f / 64f, 18f / 32f),
											new TextureVertice(4f / 64f, 18f / 32f),
											new TextureVertice(4f / 64f, 11f / 32f));

	TextureFace	handlefront			= new TextureFace(
											new TextureVertice(4f / 64f, 11f / 32f),
											new TextureVertice(4f / 64f, 18f / 32f),
											new TextureVertice(7f / 64f, 18f / 32f),
											new TextureVertice(7f / 64f, 11f / 32f));

	TextureFace	handlebottom		= new TextureFace(
											new TextureVertice(4f / 64f, 18f / 32f),
											new TextureVertice(0f / 64f, 18f / 32f),
											new TextureVertice(0f / 64f, 21f / 32f),
											new TextureVertice(4f / 64f, 21f / 32f));

	TextureFace	bottomside			= new TextureFace(
											new TextureVertice(49f / 64f, 10f / 32f),
											new TextureVertice(46f / 64f, 13f / 32f),
											new TextureVertice(36f / 64f, 13f / 32f),
											new TextureVertice(36f / 64f, 10f / 32f));

	TextureFace	bottomfront			= new TextureFace(
											new TextureVertice(46f / 64f, 13f / 32f),
											new TextureVertice(50f / 64f, 13f / 32f),
											new TextureVertice(50f / 64f, 17f / 32f),
											new TextureVertice(46f / 64f, 17f / 32f));

	TextureFace	bottombottom		= new TextureFace(
											new TextureVertice(46f / 64f, 17f / 32f),
											new TextureVertice(36f / 64f, 17f / 32f),
											new TextureVertice(36f / 64f, 13f / 32f),
											new TextureVertice(46f / 64f, 13f / 32f));

	TextureFace	bottomback			= new TextureFace(
											new TextureVertice(33f / 64f, 13f / 32f),
											new TextureVertice(36f / 64f, 13f / 32f),
											new TextureVertice(36f / 64f, 17f / 32f),
											new TextureVertice(33f / 64f, 17f / 32f));

	Vector3f		vt1					= new Vector3f(3f, 12f, 3f);
	Vector3f		vt2					= new Vector3f(27f, 12f, 3f);
	Vector3f		vt3					= new Vector3f(27f, 12f, -3f);
	Vector3f		vt4					= new Vector3f(3f, 12f, -3f);
	Vector3f		vf1					= new Vector3f(34f, 9f, 3f);
	Vector3f		vf2					= new Vector3f(34f, 9f, -3f);
	Vector3f		vs1					= new Vector3f(0f, 9f, 6f);
	Vector3f		vs2					= new Vector3f(0f, 6f, 6f);
	Vector3f		vs3					= new Vector3f(25f, 6f, 6f);
	Vector3f		vs4					= new Vector3f(25f, 9f, 6f);
	Vector3f		vs5					= new Vector3f(0f, 9f, -6f);
	Vector3f		vs6					= new Vector3f(0f, 6f, -6f);
	Vector3f		vs7					= new Vector3f(25f, 6f, -6f);
	Vector3f		vs8					= new Vector3f(25f, 9f, -6f);
	Vector3f		vfs1				= new Vector3f(25f, 6f, 4f);
	Vector3f		vfs2				= new Vector3f(25f, 9f, 4f);
	Vector3f		vfs3				= new Vector3f(25f, 6f, -4f);
	Vector3f		vfs4				= new Vector3f(25f, 9f, -4f);
	Vector3f		vff1				= new Vector3f(30f, 6f, 0f);
	Vector3f		vff2				= new Vector3f(32f, 9f, 0f);
	Vector3f		vb1					= new Vector3f(7f, 3f, 3f);
	Vector3f		vb2					= new Vector3f(27f, 3f, 3f);
	Vector3f		vb3					= new Vector3f(27f, 3f, -3f);
	Vector3f		vb4					= new Vector3f(7f, 3f, -3f);
	Vector3f		vfb1				= new Vector3f(31f, 6f, 3f);
	Vector3f		vfb2				= new Vector3f(31f, 6f, -3f);
	Vector3f		vdt1				= new Vector3f(10.25f, 12f, 1f);
	Vector3f		vdt2				= new Vector3f(19.75f, 12f, 1f);
	Vector3f		vdt3				= new Vector3f(19.75f, 12f, -1f);
	Vector3f		vdt4				= new Vector3f(10.25f, 12f, -1f);
	Vector3f		vdb1				= new Vector3f(10.25f, 11.5f, 1f);
	Vector3f		vdb2				= new Vector3f(19.75f, 11.5f, 1f);
	Vector3f		vdb3				= new Vector3f(19.75f, 11.5f, -1f);
	Vector3f		vdb4				= new Vector3f(10.25f, 11.5f, -1f);
	Vector3f		vht1				= new Vector3f(11f, 0f, 2f);
	Vector3f		vht2				= new Vector3f(15f, 0f, 2f);
	Vector3f		vht3				= new Vector3f(15f, 0f, -2f);
	Vector3f		vht4				= new Vector3f(11f, 0f, -2f);
	Vector3f		vhb1				= new Vector3f(7f, -7f, 2f);
	Vector3f		vhb2				= new Vector3f(11f, -7f, 2f);
	Vector3f		vhb3				= new Vector3f(11f, -7f, -2f);
	Vector3f		vhb4				= new Vector3f(7f, -7f, -2f);
	Vector3f		vbt1				= new Vector3f(8f, 3f, 2f);
	Vector3f		vbt2				= new Vector3f(23f, 3f, 2f);
	Vector3f		vbt3				= new Vector3f(23f, 3f, -2f);
	Vector3f		vbt4				= new Vector3f(8f, 3f, -2f);
	Vector3f		vbb1				= new Vector3f(8f, 0f, 2f);
	Vector3f		vbb2				= new Vector3f(20f, 0f, 2f);
	Vector3f		vbb3				= new Vector3f(20f, 0f, -2f);
	Vector3f		vbb4				= new Vector3f(8f, 0f, -2f);

	public void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay)
	{
		matrices.pushPose();
		// body
        RenderHelper.addFace(matrices, buffer, vt1, vt2, vt3, vt4, bodytop, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs1, vt1, vt2, vs4, bodytopside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs5, vt4, vt3, vs8, bodytopside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs2, vs1, vs4, vs3, bodyside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs6, vs5, vs8, vs7, bodyside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs1, vt1, vt4, vs5, bodytopback, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs2, vs1, vs5, vs6, bodyback, light, overlay);
		RenderHelper.addFace(matrices, buffer, vt2, vf1, vf2, vt3, bodytopfront, light, overlay);
		RenderHelper.addFace(matrices, buffer, vt2, vf1, vs4, vs4, bodytopfrontside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vt3, vf2, vs8, vs8, bodytopfrontside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs3, vs4, vfs2, vfs1, bodysidefrontedge, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs7, vs8, vfs4, vfs3, bodysidefrontedge, light, overlay);
		RenderHelper.addFace(matrices, buffer, vf2, vs8, vs4, vf1, bodybottomback, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfs2, vfs1, vff1, vff2, bodysidefront, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfs4, vfs3, vff1, vff2, bodysidefront, light, overlay);
		RenderHelper.addFace(matrices, buffer, vb1, vb2, vb3, vb4, bodybottom, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs2, vs3, vb2, vb1, bodybottomside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vs6, vs7, vb3, vb4, bodybottomside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vb2, vfb1, vs3, vs3, bodytopfrontside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vb3, vfb2, vs7, vs7, bodytopfrontside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vb2, vfb1, vfb2, vb3, bodybottomfront, light, overlay);
		RenderHelper.addFace(matrices, buffer, vb1, vs2, vs6, vb4, bodybottomback, light, overlay);
		RenderHelper.addFace(matrices, buffer, vfb2, vs7, vs3, vfb1, bodybottomback, light, overlay);

		// bottom
		RenderHelper.addFace(matrices, buffer, vbb1, vbt1, vbt4, vbb4, bottomfront, light, overlay);
		RenderHelper.addFace(matrices, buffer, vbb3, vbt3, vbt2, vbb2, bottomback, light, overlay);
		RenderHelper.addFace(matrices, buffer, vbt2, vbb2, vbb1, vbt1, bottomside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vbt3, vbb3, vbb4, vbt4, bottomside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vbb3, vbb4, vbb1, vbb2, bottombottom, light, overlay);

		// detail
		RenderHelper.addFace(matrices, buffer, vdt4, vdb4, vdb1, vdt1, detailedge1, light, overlay);
		RenderHelper.addFace(matrices, buffer, vdt3, vdb3, vdb2, vdt2, detailedge1, light, overlay);
		RenderHelper.addFace(matrices, buffer, vdt2, vdb2, vdb1, vdt1, detailedge2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vdt4, vdb4, vdb3, vdt3, detailedge2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vdb1, vdb2, vdb3, vdb4, detailpurple, light, overlay);

		// handle
		RenderHelper.addFace(matrices, buffer, vht4, vhb4, vhb1, vht1, handlefront, light, overlay);
		RenderHelper.addFace(matrices, buffer, vht2, vhb2, vhb3, vht3, handlefront, light, overlay);
		RenderHelper.addFace(matrices, buffer, vht1, vhb1, vhb2, vht2, handleside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vht3, vhb3, vhb4, vht4, handleside, light, overlay);
		RenderHelper.addFace(matrices, buffer, vhb2, vhb1, vhb4, vhb3, handlebottom, light, overlay);
		matrices.popPose();
	}
}

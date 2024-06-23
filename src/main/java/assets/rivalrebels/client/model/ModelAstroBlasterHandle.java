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
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;

public class ModelAstroBlasterHandle {
	private static final TextureFace	handleside		= new TextureFace(
										new TextureVertice(0f / 64f, 11f / 32f),
										new TextureVertice(0f / 64f, 18f / 32f),
										new TextureVertice(4f / 64f, 18f / 32f),
										new TextureVertice(4f / 64f, 11f / 32f));

    private static final TextureFace	handlefront		= new TextureFace(
										new TextureVertice(4f / 64f, 11f / 32f),
										new TextureVertice(4f / 64f, 18f / 32f),
										new TextureVertice(7f / 64f, 18f / 32f),
										new TextureVertice(7f / 64f, 11f / 32f));

    private static final TextureFace	handlebottom	= new TextureFace(
										new TextureVertice(4f / 64f, 18f / 32f),
										new TextureVertice(0f / 64f, 18f / 32f),
										new TextureVertice(0f / 64f, 21f / 32f),
										new TextureVertice(4f / 64f, 21f / 32f));

    private static final TextureFace	bottomside		= new TextureFace(
										new TextureVertice(49f / 64f, 10f / 32f),
										new TextureVertice(46f / 64f, 13f / 32f),
										new TextureVertice(36f / 64f, 13f / 32f),
										new TextureVertice(36f / 64f, 10f / 32f));

    private static final TextureFace	bottomfront		= new TextureFace(
										new TextureVertice(46f / 64f, 13f / 32f),
										new TextureVertice(50f / 64f, 13f / 32f),
										new TextureVertice(50f / 64f, 17f / 32f),
										new TextureVertice(46f / 64f, 17f / 32f));

    private static final TextureFace	bottombottom	= new TextureFace(
										new TextureVertice(46f / 64f, 17f / 32f),
										new TextureVertice(36f / 64f, 17f / 32f),
										new TextureVertice(36f / 64f, 13f / 32f),
										new TextureVertice(46f / 64f, 13f / 32f));

    private static final TextureFace	bottomback		= new TextureFace(
										new TextureVertice(33f / 64f, 13f / 32f),
										new TextureVertice(36f / 64f, 13f / 32f),
										new TextureVertice(36f / 64f, 17f / 32f),
										new TextureVertice(33f / 64f, 17f / 32f));

	private static final Vector3f		vht1			= new Vector3f(11f, 0f, 2f);
	private static final Vector3f		vht2			= new Vector3f(15f, 0f, 2f);
	private static final Vector3f		vht3			= new Vector3f(15f, 0f, -2f);
	private static final Vector3f		vht4			= new Vector3f(11f, 0f, -2f);
	private static final Vector3f		vhb1			= new Vector3f(7f, -7f, 2f);
	private static final Vector3f		vhb2			= new Vector3f(11f, -7f, 2f);
	private static final Vector3f		vhb3			= new Vector3f(11f, -7f, -2f);
	private static final Vector3f		vhb4			= new Vector3f(7f, -7f, -2f);
	private static final Vector3f		vbt1			= new Vector3f(8f, 3f, 2f);
	private static final Vector3f		vbt2			= new Vector3f(23f, 3f, 2f);
	private static final Vector3f		vbt3			= new Vector3f(23f, 3f, -2f);
	private static final Vector3f		vbt4			= new Vector3f(8f, 3f, -2f);
	private static final Vector3f		vbb1			= new Vector3f(8f, 0f, 2f);
	private static final Vector3f		vbb2			= new Vector3f(20f, 0f, 2f);
	private static final Vector3f		vbb3			= new Vector3f(20f, 0f, -2f);
	private static final Vector3f		vbb4			= new Vector3f(8f, 0f, -2f);

    public static void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
		matrices.push();

		matrices.push();
		matrices.scale(1.3F, 1, 1);
		// bottom
		RenderHelper.addFace(vertices, vbt3, vbt4, vbt1, vbt2, bottombottom, light, overlay);
		RenderHelper.addFace(vertices, vbb1, vbt1, vbt4, vbb4, bottomfront, light, overlay);
		RenderHelper.addFace(vertices, vbb3, vbt3, vbt2, vbb2, bottomback, light, overlay);
		RenderHelper.addFace(vertices, vbt2, vbb2, vbb1, vbt1, bottomside, light, overlay);
		RenderHelper.addFace(vertices, vbt3, vbb3, vbb4, vbt4, bottomside, light, overlay);
		RenderHelper.addFace(vertices, vbb3, vbb4, vbb1, vbb2, bottombottom, light, overlay);
		matrices.pop();

		// handle
		RenderHelper.addFace(vertices, vht4, vhb4, vhb1, vht1, handlefront, light, overlay);
		RenderHelper.addFace(vertices, vht2, vhb2, vhb3, vht3, handlefront, light, overlay);
		RenderHelper.addFace(vertices, vht1, vhb1, vhb2, vht2, handleside, light, overlay);
		RenderHelper.addFace(vertices, vht3, vhb3, vhb4, vht4, handleside, light, overlay);
		RenderHelper.addFace(vertices, vhb2, vhb1, vhb4, vhb3, handlebottom, light, overlay);
		matrices.pop();
	}
}

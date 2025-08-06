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
package io.github.kadir1243.rivalrebels.client.model;

import com.mojang.math.Transformation;
import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureFace;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.pipeline.TransformingVertexPipeline;
import org.joml.Vector3f;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
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

    public static final Supplier<QuadHelper.BakedData> BAKED_MODEL = QuadHelper.createBakedModel(vertexConsumer -> {
        TransformingVertexPipeline scaledVertex = new TransformingVertexPipeline(vertexConsumer, new Transformation(null, null, new Vector3f(1.3F, 1, 1), null));
        // bottom
        QuadHelper.addFace(scaledVertex, vbt3, vbt4, vbt1, vbt2, bottombottom);
        QuadHelper.addFace(scaledVertex, vbb1, vbt1, vbt4, vbb4, bottomfront);
        QuadHelper.addFace(scaledVertex, vbb3, vbt3, vbt2, vbb2, bottomback);
        QuadHelper.addFace(scaledVertex, vbt2, vbb2, vbb1, vbt1, bottomside);
        QuadHelper.addFace(scaledVertex, vbt3, vbb3, vbb4, vbt4, bottomside);
        QuadHelper.addFace(scaledVertex, vbb3, vbb4, vbb1, vbb2, bottombottom);

        // handle
        QuadHelper.addFace(vertexConsumer, vht4, vhb4, vhb1, vht1, handlefront);
        QuadHelper.addFace(vertexConsumer, vht2, vhb2, vhb3, vht3, handlefront);
        QuadHelper.addFace(vertexConsumer, vht1, vhb1, vhb2, vht2, handleside);
        QuadHelper.addFace(vertexConsumer, vht3, vhb3, vhb4, vht4, handleside);
        QuadHelper.addFace(vertexConsumer, vhb2, vhb1, vhb4, vhb3, handlebottom);
    });
}

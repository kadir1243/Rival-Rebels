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

import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import org.joml.Vector3f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ModelSteel {
    private static final float s = 0.5F;

	private static final Vector3f		v1			= new Vector3f(s, s, s);
	private static final Vector3f		v2			= new Vector3f(s, s, -s);
	private static final Vector3f		v3			= new Vector3f(-s, s, -s);
	private static final Vector3f		v4			= new Vector3f(-s, s, s);

	private static final Vector3f		v5			= new Vector3f(s, -s, s);
	private static final Vector3f		v6			= new Vector3f(s, -s, -s);
	private static final Vector3f		v7			= new Vector3f(-s, -s, -s);
	private static final Vector3f		v8			= new Vector3f(-s, -s, s);
    public static final Supplier<QuadHelper.BakedData> BAKED_MODEL = QuadHelper.createBakedModel(buffer -> {
        addVertex(buffer, v1, 0, 0);
        addVertex(buffer, v5, 1, 0);
        addVertex(buffer, v8, 1, 1);
        addVertex(buffer, v4, 0, 1);

        addVertex(buffer, v4, 0, 0);
        addVertex(buffer, v8, 1, 0);
        addVertex(buffer, v7, 1, 1);
        addVertex(buffer, v3, 0, 1);

        addVertex(buffer, v3, 0, 0);
        addVertex(buffer, v7, 1, 0);
        addVertex(buffer, v6, 1, 1);
        addVertex(buffer, v2, 0, 1);

        addVertex(buffer, v2, 0, 0);
        addVertex(buffer, v6, 1, 0);
        addVertex(buffer, v5, 1, 1);
        addVertex(buffer, v1, 0, 1);

        addVertex(buffer, v3, 1, 0);
        addVertex(buffer, v2, 1, 1);
        addVertex(buffer, v5, 0, 0);
        addVertex(buffer, v8, 0, 1);
    });

	private static void addVertex(VertexConsumer buffer, Vector3f v, float tx, float ty) {
        QuadHelper.addVertice(buffer, v.mul(0.999F, new Vector3f()), new TextureVertice(tx, ty));
	}
}

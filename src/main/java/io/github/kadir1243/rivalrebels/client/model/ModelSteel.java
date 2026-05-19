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
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureFace;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.Identifier;
import org.joml.Vector3f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.Function;
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
    public static final Function<Identifier, Supplier<List<QuadHelper.BakedQuadWrapper>>> BAKED_MODEL = id -> QuadHelper.createQuads(Sheets.BLOCKS_MAPPER.apply(id), buffer -> {
        TextureVertice t1 = new TextureVertice(0, 0);
        TextureVertice t2 = new TextureVertice(1, 0);
        TextureVertice t3 = new TextureVertice(1, 1);
        TextureVertice t4 = new TextureVertice(0, 1);
        TextureFace t = new TextureFace(t1, t2, t3, t4);
        addFace(buffer, v1, v5, v8, v4, t);
        addFace(buffer, v4, v8, v7, v3, t);
        addFace(buffer, v3, v7, v6, v2, t);
        addFace(buffer, v2, v6, v5, v1, t);
        addFace(buffer, v3, v2, v5, v8, t);
    });

    private static void addFace(List<QuadHelper.MutableQuadWrapper> buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureFace t) {
        QuadHelper.addFace(buffer, v1.mul(0.999F, new Vector3f()), v2.mul(0.999F, new Vector3f()), v3.mul(0.999F, new Vector3f()), v4.mul(0.999F, new Vector3f()), t);
    }
}

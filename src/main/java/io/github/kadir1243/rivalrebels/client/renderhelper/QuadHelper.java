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
package io.github.kadir1243.rivalrebels.client.renderhelper;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.pipeline.QuadBakingVertexConsumer;
import net.neoforged.neoforge.client.model.pipeline.TransformingVertexPipeline;
import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;
import org.joml.Vector3f;

import java.util.function.Consumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class QuadHelper {
    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4) {
        addVertice(buffer, v1, t1);
        addVertice(buffer, v2, t2);
        addVertice(buffer, v3, t3);
        addVertice(buffer, v4, t4);
    }

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, int color) {
        addVertice(buffer, v1, color);
        addVertice(buffer, v2, color);
        addVertice(buffer, v3, color);
        addVertice(buffer, v4, color);
    }

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureFace t) {
        addFace(buffer, v1, v2, v3, v4, t.v1(), t.v2(), t.v3(), t.v4());
    }

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, float x1, float x2, float y2, float y1) {
        addVertice(buffer, v1, new TextureVertice(x1, y1));
        addVertice(buffer, v2, new TextureVertice(x2, y1));
        addVertice(buffer, v3, new TextureVertice(x2, y2));
        addVertice(buffer, v4, new TextureVertice(x1, y2));
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, TextureVertice t, int color) {
        buffer.addVertex(v)
            .setColor(color)
            .setUv(t.x(), t.y())
            .setNormal(0, 0, 1);
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, TextureVertice t, int color, Vector3f normal) {
        buffer.addVertex(v)
            .setColor(color)
            .setUv(t.x(), t.y())
            .setNormal(normal.x(), normal.y(), normal.z());
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, TextureVertice t) {
        addVertice(buffer, v, t, CommonColors.WHITE);
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, TextureVertice t, Vector3f normal) {
        addVertice(buffer, v, t, CommonColors.WHITE, normal);
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, int color) {
        buffer.addVertex(v)
            .setColor(color)
            .setUv(16, 16);
    }

    public static Supplier<BakedData> createBakedModel(Consumer<VertexConsumer> bakedQuadSupplier) {
        return Suppliers.memoize(() -> {
            var model = createBakedModel(bakedQuadSupplier, Transformation.identity());
            SimpleModelWrapper wrapped = new SimpleModelWrapper(model, false, Minecraft.getInstance().getGuiSprites().getSprite(MissingTextureAtlasSprite.getLocation()));
            return new BakedData(new SingleVariant(wrapped), wrapped);
        });
    }

    public record BakedData(SingleVariant blockStateModel, BlockModelPart original) {}

    public static QuadCollection createBakedModel(Consumer<VertexConsumer> bakedQuadSupplier, Transformation transforms) {
        QuadBakingVertexConsumer buffer = new QuadBakingVertexConsumer();
        buffer.setSprite(Minecraft.getInstance().getGuiSprites().getSprite(TextureAtlas.LOCATION_BLOCKS));

        QuadCollection.Builder builder = new QuadCollection.Builder();
        bakedQuadSupplier.accept(new VertexConsumerWrapper(new TransformingVertexPipeline(buffer, transforms)) {
            private int vertexCount;

            @Override
            public VertexConsumer addVertex(float x, float y, float z) {
                super.addVertex(x, y, z);
                vertexCount++;
                if (vertexCount == 4) {
                    builder.addUnculledFace(buffer.bakeQuad());
                    vertexCount = 0;
                }

                return this;
            }
        });
        return builder.build();
    }
}

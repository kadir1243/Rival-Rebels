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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.pipeline.QuadBakingVertexConsumer;
import net.neoforged.neoforge.client.model.pipeline.TransformingVertexPipeline;
import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;
import net.neoforged.neoforge.client.model.quad.MutableQuad;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class QuadHelper {
    public static void submitQuadSupplier(OrderedSubmitNodeCollector nodeCollector, PoseStack poseStack, RenderType renderType, Supplier<List<BakedQuadWrapper>> quads, int light, int overlay) {
        QuadInstance qi = new QuadInstance();
        qi.setLightCoords(light);
        qi.setOverlayCoords(overlay);
        for (BakedQuadWrapper quad : quads.get()) {
            if (quad.transformation() != null) {
                Vector3fc scale = quad.transformation().scale();
                Vector3fc translation = quad.transformation().translation();
                poseStack.pushPose();
                poseStack.translate(translation.x(), translation.y(), translation.z());
                poseStack.scale(scale.x(), scale.y(), scale.z());
                poseStack.mulPose(quad.transformation().leftRotation());
                nodeCollector.submitCustomGeometry(poseStack, renderType, (pose, buffer) -> buffer.putBakedQuad(pose, quad.quad(), qi));
                poseStack.popPose();
            } else
                nodeCollector.submitCustomGeometry(poseStack, renderType, (pose, buffer) -> buffer.putBakedQuad(pose, quad.quad(), qi));
        }
    }
    public static void addFace(List<MutableQuadWrapper> quads, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4) {
        quads.add(new MutableQuadWrapper(createMutableQuadFaceWrapper(v1, v2, v3, v4, t1, t2, t3, t4), null));
    }
    public static void addFace(List<MutableQuadWrapper> quads, Transformation transformation, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4) {
        quads.add(new MutableQuadWrapper(createMutableQuadFaceWrapper(v1, v2, v3, v4, t1, t2, t3, t4), transformation));
    }
    public static void addFace(List<MutableQuadWrapper> quads, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureFace t) {
        addFace(quads, v1, v2, v3, v4, t.v1(), t.v2(), t.v3(), t.v4());
    }
    public static void addFace(List<MutableQuadWrapper> quads, Transformation transformation, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureFace t) {
        addFace(quads, transformation, v1, v2, v3, v4, t.v1(), t.v2(), t.v3(), t.v4());
    }

    private static MutableQuad createMutableQuadFaceWrapper(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4) {
        MutableQuad quad = new MutableQuad();
        quad.setPosition(0, v1);
        quad.setPosition(1, v2);
        quad.setPosition(2, v3);
        quad.setPosition(3, v4);
        quad.setUv(0, t1.x(), t1.y());
        quad.setUv(1, t2.x(), t2.y());
        quad.setUv(2, t3.x(), t3.y());
        quad.setUv(3, t4.x(), t4.y());
        return quad;
    }

    public static void addFace(List<MutableQuadWrapper> quads, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, float x1, float x2, float y2, float y1) {
        addFace(quads, v1, v2, v3, v4, new TextureVertice(x1, y1), new TextureVertice(x2, y1), new TextureVertice(x2, y2), new TextureVertice(x1, y2));
    }

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
            var model = createBakedModel(bakedQuadSupplier, Transformation.IDENTITY);
            SimpleModelWrapper wrapped = new SimpleModelWrapper(model, false, new Material.Baked(Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(TextureAtlas.LOCATION_BLOCKS).missingSprite(), false));
            return new BakedData(new SingleVariant(wrapped), wrapped, model);
        });
    }

    public static Supplier<List<BakedQuadWrapper>> createQuads(SpriteId sprite, Consumer<List<MutableQuadWrapper>> consumer) {
        return Suppliers.memoize(() -> {
            List<MutableQuadWrapper> quads = new ArrayList<>();
            consumer.accept(quads);
            TextureAtlasSprite atlasSprite = Minecraft.getInstance().getAtlasManager().get(sprite);
            return quads.stream()
                .peek(mutableQuad -> mutableQuad.quad().setSprite(new Material.Baked(atlasSprite, false)))
                .map(w -> new BakedQuadWrapper(w.quad().toBakedQuad(), w.transformation()))
                .toList();
        });
    }

    public record BakedData(SingleVariant blockStateModel, BlockStateModelPart original, QuadCollection quadCollection) {}

    public record MutableQuadWrapper(MutableQuad quad, @Nullable Transformation transformation) {}

    public record BakedQuadWrapper(BakedQuad quad, @Nullable Transformation transformation) {}

    public static QuadCollection createBakedModel(Consumer<VertexConsumer> bakedQuadSupplier, Transformation transforms) {
        QuadBakingVertexConsumer buffer = new QuadBakingVertexConsumer();
        buffer.setSprite(new Material.Baked(Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(RRIdentifiers.create("nonexistingsprite"))), false));

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

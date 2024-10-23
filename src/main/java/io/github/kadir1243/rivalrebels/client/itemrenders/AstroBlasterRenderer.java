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
package io.github.kadir1243.rivalrebels.client.itemrenders;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.*;
import io.github.kadir1243.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class AstroBlasterRenderer implements DynamicItemRenderer {
    private static final RenderType LIGHTNING = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        99999,
        RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
            .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
            .createCompositeState(false)
    );
    private static final RenderType LIGHTNING_TRIANGLES = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast_triangles",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.TRIANGLES,
        99999,
        RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
            .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
            .createCompositeState(false)
    );

    private float pullback = 0;
    private float rotation = 0;
    private boolean isreloading = false;
    private int stage = 0;
    private int spin = 0;
    private int reloadcooldown = 0;

    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        int repairCost = stack.getOrDefault(DataComponents.REPAIR_COST, 0);
        spin++;
        if (repairCost >= 1) {
            spin = (int) (spin + repairCost / 2.2);
        }
        spin %= 628;
        if (reloadcooldown > 0) reloadcooldown--;
        if (repairCost > 20 && reloadcooldown == 0) isreloading = true;
        if (isreloading) {
            if (stage == 0) if (pullback < 0.3) pullback += 0.03;
            else stage = 1;
            if (stage == 1) if (rotation < 90) rotation += 4.5;
            else stage = 2;
            if (stage == 2) if (pullback > 0) pullback -= 0.03;
            else {
                stage = 0;
                isreloading = false;
                reloadcooldown = 60;
                rotation = 0;
            }

        }
        matrices.pushPose();
        matrices.translate(0.4f, 0.35f, -0.03f);
        matrices.mulPose(Axis.ZP.rotationDegrees(-55));
        matrices.translate(0f, -0.05f, 0.05f);

        matrices.pushPose();
        matrices.translate(0f, 0.9f, 0f);
        VertexConsumer cellular_noise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE_TRIANGLES);
        ModelAstroBlasterBarrel.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.eteinstenbarrel)), light, overlay);
        if (stack.isEnchanted()) {
            ModelAstroBlasterBarrel.render(matrices, cellular_noise, light, overlay);
        }
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.22f, -0.025f, 0f);
        matrices.mulPose(Axis.ZP.rotationDegrees(90));
        matrices.scale(0.03125f, 0.03125f, 0.03125f);
        ModelAstroBlasterHandle.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.eteinstenhandle)), light, overlay);
        if (stack.isEnchanted()) {
            ModelAstroBlasterHandle.render(matrices, cellular_noise, light, overlay);
        }
        matrices.popPose();

        // matrices.push();
        // matrices.translate(0f, 0.8f, 0f);
        // matrices.mulPose(180, 0.0F, 0.0F, 1.0F);
        // matrices.scale(0.9F, 4.5F, 0.9F);
        // md3.render(0.2f, 0.3f, 0.3f, 0.3f, 1f);
        // matrices.pop();

        matrices.pushPose();
        matrices.translate(0f, 0.2f, 0f);
        matrices.scale(0.85F, 0.85F, 0.85F);
        ModelAstroBlasterBack.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.eteinstenback)), light, overlay);
        if (stack.isEnchanted()) {
            ModelAstroBlasterBack.render(matrices, vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE_TRIANGLES), light, overlay);
        }
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0f, -pullback, 0f);
        matrices.mulPose(Axis.YP.rotationDegrees(rotation));
        matrices.pushPose();
        VertexConsumer redstoneRodTextureVertexConsumer = vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.etredrod));
        matrices.translate(0.12f, 0.1f, 0.12f);
        matrices.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        matrices.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(-0.12f, 0.1f, 0.12f);
        matrices.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        matrices.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(-0.12f, 0.1f, -0.12f);
        matrices.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        matrices.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.12f, 0.1f, -0.12f);
        matrices.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        matrices.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0, 0.25f, 0);
        float segmentDistance = 0.1f;
        float distance = 0.5f;
        float radius = 0.01F;
        RandomSource random = RandomSource.create();

        float AddedX = 0;
        float AddedZ = 0;
        float prevAddedX;
        float prevAddedZ;
        // double angle = 0;
        VertexConsumer lightningQuads = vertexConsumers.getBuffer(LIGHTNING);
        for (float AddedY = distance; AddedY >= 0; AddedY -= segmentDistance) {
            prevAddedX = AddedX;
            prevAddedZ = AddedZ;
            AddedX = (random.nextFloat() - 0.5F) * 0.1f;
            AddedZ = (random.nextFloat() - 0.5F) * 0.1f;
            float dist = Mth.sqrt(AddedX * AddedX + AddedZ * AddedZ);
            if (dist != 0) {
                float tempAddedX = AddedX / dist;
                float tempAddedZ = AddedZ / dist;
                if (Mth.abs(tempAddedX) < Mth.abs(AddedX)) {
                    AddedX = tempAddedX;
                }
                if (Mth.abs(tempAddedZ) < Mth.abs(AddedZ)) {
                    AddedZ = tempAddedZ;
                }
                // angle = Math.atan2(tempAddedX, tempAddedZ);
            }
            if (AddedY <= 0) {
                AddedX = AddedZ = 0;
            }

            for (float o = 0; o <= radius; o += radius / 2f) {
                lightningQuads.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ - o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ + o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).setColor(CommonColors.RED);

                lightningQuads.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ - o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ - o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).setColor(CommonColors.RED);

                lightningQuads.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ + o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ - o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).setColor(CommonColors.RED);

                lightningQuads.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ + o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ + o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).setColor(CommonColors.RED);
                lightningQuads.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).setColor(CommonColors.RED);
            }
            //matrices.pushPose();
            //matrices.mulPose(Axis.ZP.rotationDegrees(90));
            //matrices.mulPose(Axis.YP.rotationDegrees((float) angle));
            //float o = 0.075f;
            //float s = 0.1f;
            //Tesselator tesselator = Tesselator.getInstance();
            //BufferBuilder builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            //builder.addVertex( + o, AddedY, - o).setColor(CommonColors.RED);
            //builder.addVertex( + o, AddedY, + o).setColor(CommonColors.RED);
            //builder.addVertex( + o, AddedY + s, + o).setColor(CommonColors.RED);
            //builder.addVertex( + o, AddedY + s, - o).setColor(CommonColors.RED);

            //builder.addVertex( - o, AddedY, - o).setColor(CommonColors.RED);
            //builder.addVertex( + o, AddedY, - o).setColor(CommonColors.RED);
            //builder.addVertex( + o, AddedY + s, - o).setColor(CommonColors.RED);
            //builder.addVertex( - o, AddedY + s, - o).setColor(CommonColors.RED);

            //builder.addVertex( - o, AddedY, + o).setColor(CommonColors.RED);
            //builder.addVertex( - o, AddedY, - o).setColor(CommonColors.RED);
            //builder.addVertex( - o, AddedY + s, - o).setColor(CommonColors.RED);
            //builder.addVertex( - o, AddedY + s, + o).setColor(CommonColors.RED);

            //builder.addVertex( + o, AddedY, + o).setColor(CommonColors.RED);
            //builder.addVertex( - o, AddedY, + o).setColor(CommonColors.RED);
            //builder.addVertex( - o, AddedY + s, + o).setColor(CommonColors.RED);
            //builder.addVertex( + o, AddedY + s, + o).setColor(CommonColors.RED);

            //BufferUploader.drawWithShader(builder.buildOrThrow());
            //matrices.popPose();
        }

        matrices.popPose();

        VertexConsumer lightningTriangles = vertexConsumers.getBuffer(LIGHTNING_TRIANGLES);
        matrices.pushPose();
        matrices.translate(0f, 0.8f, 0f);
        matrices.mulPose(Axis.ZP.rotationDegrees(180));
        matrices.mulPose(Axis.YP.rotationDegrees(spin));
        matrices.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(matrices, lightningTriangles, (float) (0.22f + (Mth.sin(spin / 10) * 0.005)), 0.5f, 0f, 0f, 1f);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0f, 0.8f, 0f);
        matrices.mulPose(Axis.ZP.rotationDegrees(180));
        matrices.mulPose(Axis.YP.rotationDegrees(-spin));
        matrices.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(matrices, lightningTriangles, (float) (0.22f + (Mth.cos(-spin / 15) * 0.005)), 0.5f, 0f, 0f, 1f);
        matrices.popPose();

        matrices.popPose();
    }
}


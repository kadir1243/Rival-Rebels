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
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class AstroBlasterRenderer implements SpecialModelRenderer<Integer> {
    private float pullback = 0;
    private float rotation = 0;
    private boolean isreloading = false;
    private int stage = 0;
    private int spin = 0;
    private int reloadcooldown = 0;

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }

    @Override
    public @Nullable Integer extractArgument(ItemStack stack) {
        return stack.getOrDefault(DataComponents.REPAIR_COST, 0);
    }

    @Override
    public void submit(@Nullable Integer repairCost, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
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
        poseStack.pushPose();
        poseStack.translate(0.4f, 0.35f, -0.03f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-55));
        poseStack.translate(0f, -0.05f, 0.05f);

        poseStack.pushPose();
        poseStack.translate(0f, 0.9f, 0f);
        ModelAstroBlasterBarrel.render(poseStack, submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.eteinstenbarrel), lightCoords, overlayCoords);
        if (hasFoil) {
            ModelAstroBlasterBarrel.render(poseStack, submitNodeCollector, RRRenderTypes.CELLULAR_NOISE, lightCoords, overlayCoords);
        }
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.22f, -0.025f, 0f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        poseStack.scale(0.03125f, 0.03125f, 0.03125f);

        ObjModels.submit(submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.eteinstenhandle), ModelAstroBlasterHandle.BAKED_MODEL.get().quadCollection(), poseStack, CommonColors.WHITE, lightCoords, overlayCoords);
        if (hasFoil) {
            ObjModels.submit(submitNodeCollector, RRRenderTypes.CELLULAR_NOISE, ModelAstroBlasterHandle.BAKED_MODEL.get().quadCollection(), poseStack, CommonColors.WHITE, lightCoords, overlayCoords);
        }
        poseStack.popPose();

        // poseStack.push();
        // poseStack.translate(0f, 0.8f, 0f);
        // poseStack.mulPose(180, 0.0F, 0.0F, 1.0F);
        // poseStack.scale(0.9F, 4.5F, 0.9F);
        // md3.render(0.2f, 0.3f, 0.3f, 0.3f, 1f);
        // poseStack.pop();

        poseStack.pushPose();
        poseStack.translate(0f, 0.2f, 0f);
        poseStack.scale(0.85F, 0.85F, 0.85F);
        ModelAstroBlasterBack.render(poseStack, submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.eteinstenback), lightCoords, overlayCoords);
        if (hasFoil) {
            ModelAstroBlasterBack.render(poseStack, submitNodeCollector, RRRenderTypes.CELLULAR_NOISE, lightCoords, overlayCoords);
        }
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0f, -pullback, 0f);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.pushPose();
        RenderType redstoneRodRenderType = RenderTypes.entitySolid(RRIdentifiers.etredrod);
        poseStack.translate(0.12f, 0.1f, 0.12f);
        poseStack.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        poseStack.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(poseStack, submitNodeCollector, redstoneRodRenderType, lightCoords, overlayCoords);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(-0.12f, 0.1f, 0.12f);
        poseStack.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        poseStack.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(poseStack, submitNodeCollector, redstoneRodRenderType, lightCoords, overlayCoords);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(-0.12f, 0.1f, -0.12f);
        poseStack.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        poseStack.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(poseStack, submitNodeCollector, redstoneRodRenderType, lightCoords, overlayCoords);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.12f, 0.1f, -0.12f);
        poseStack.mulPose(Axis.YP.rotationDegrees(pullback * 270));
        poseStack.scale(0.3f, 0.7f, 0.3f);
        ModelRod.render(poseStack, submitNodeCollector, redstoneRodRenderType, lightCoords, overlayCoords);
        poseStack.popPose();
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0, 0.25f, 0);
        float segmentDistance = 0.1f;
        float distance = 0.5f;
        float radius = 0.01F;
        RandomSource random = RandomSource.create();

        float AddedX = 0;
        float AddedZ = 0;
        float prevAddedX;
        float prevAddedZ;
        // double angle = 0;
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
                float finalAddedX = AddedX;
                float finalPrevAddedX = prevAddedX;
                float finalO = o;
                float finalAddedY = AddedY;
                float finalAddedZ = AddedZ;
                float finalPrevAddedZ = prevAddedZ;
                submitNodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.LIGHTNING_ASTRO_BLAST, (pose, consumer) -> {
                    consumer.addVertex(pose, finalAddedX + finalO, finalAddedY, finalAddedZ - finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalAddedX + finalO, finalAddedY, finalAddedZ + finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX + finalO, finalAddedY + segmentDistance, finalPrevAddedZ + finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX + finalO, finalAddedY + segmentDistance, finalPrevAddedZ - finalO).setColor(CommonColors.RED);

                    consumer.addVertex(pose, finalAddedX - finalO, finalAddedY, finalAddedZ - finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalAddedX + finalO, finalAddedY, finalAddedZ - finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX + finalO, finalAddedY + segmentDistance, finalPrevAddedZ - finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX - finalO, finalAddedY + segmentDistance, finalPrevAddedZ - finalO).setColor(CommonColors.RED);

                    consumer.addVertex(pose, finalAddedX - finalO, finalAddedY, finalAddedZ + finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalAddedX - finalO, finalAddedY, finalAddedZ - finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX - finalO, finalAddedY + segmentDistance, finalPrevAddedZ - finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX - finalO, finalAddedY + segmentDistance, finalPrevAddedZ + finalO).setColor(CommonColors.RED);

                    consumer.addVertex(pose, finalAddedX + finalO, finalAddedY, finalAddedZ + finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalAddedX - finalO, finalAddedY, finalAddedZ + finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX - finalO, finalAddedY + segmentDistance, finalPrevAddedZ + finalO).setColor(CommonColors.RED);
                    consumer.addVertex(pose, finalPrevAddedX + finalO, finalAddedY + segmentDistance, finalPrevAddedZ + finalO).setColor(CommonColors.RED);
                });
            }
            //poseStack.pushPose();
            //poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            //poseStack.mulPose(Axis.YP.rotationDegrees((float) angle));
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
            //poseStack.popPose();
        }

        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0f, 0.8f, 0f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(spin));
        poseStack.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(poseStack, submitNodeCollector, RRRenderTypes.LIGHTNING_ASTRO_BLAST_TRIANGLES, (float) (0.22f + (Mth.sin(spin / 10) * 0.005)), 0.5f, 0f, 0f, 1f);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0f, 0.8f, 0f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(-spin));
        poseStack.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(poseStack, submitNodeCollector, RRRenderTypes.LIGHTNING_ASTRO_BLAST_TRIANGLES, (float) (0.22f + (Mth.cos(-spin / 15) * 0.005)), 0.5f, 0f, 0f, 1f);
        poseStack.popPose();

        poseStack.popPose();
    }
}


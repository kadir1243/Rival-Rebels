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

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class TeslaRenderer implements SpecialModelRenderer<Integer> {
    private static final DeltaTracker TIMER = Minecraft.getInstance().getDeltaTracker();
    private int spin;
    private final Supplier<QuadCollection> teslaModel = Suppliers.memoize(() -> Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.TESLA_MODEL));
    private final Supplier<QuadCollection> dynamoModel = Suppliers.memoize(() -> Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.DYNAMO_MODEL));

    @Override
    public @Nullable Integer extractArgument(ItemStack stack) {
        return stack.getOrDefault(RRComponents.TESLA_DIAL, 0);
	}

    @Override
    public void submit(@Nullable Integer argument, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        //if (mode == ItemDisplayContext.GUI) poseStack.mulPose(Axis.YP.rotationDegrees(45));

        if (!hasFoil) {
            int degree = argument;
            spin = (int) Mth.lerp(TIMER.getGameTimeDeltaTicks(), spin, spin + (5 + (degree / 36F)));
			poseStack.pushPose();
			poseStack.translate(0.8f, 0.5f, -0.03f);
			poseStack.scale(0.12f, 0.12f, 0.12f);
			// poseStack.translate(0.3f, 0.05f, -0.1f);

            ObjModels.submit(submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.ettesla), teslaModel.get(), poseStack, CommonColors.WHITE, lightCoords, overlayCoords);
            poseStack.mulPose(Axis.XP.rotationDegrees(spin));
            ObjModels.submit(submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.ettesla), dynamoModel.get(), poseStack, CommonColors.WHITE, lightCoords, overlayCoords);

			poseStack.popPose();
		} else {
			poseStack.pushPose();
			poseStack.scale(1.01f, 1.01f, 1.01f);
			poseStack.mulPose(Axis.YP.rotationDegrees(45));
			poseStack.mulPose(Axis.ZP.rotationDegrees(10));
			poseStack.scale(0.6f, 0.2f, 0.2f);
			poseStack.translate(-0.99f, 0.5f, 0.0f);
            submitNodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.CELLULAR_NOISE,  (pose, buffer) -> {
                buffer.addVertex(pose, -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose,  1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, 1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose,  1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);

                buffer.addVertex(pose, -1, -1,  1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose, -1,  1,  1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1,  1,  1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
                buffer.addVertex(pose,  1, -1,  1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(lightCoords).setOverlay(overlayCoords).setNormal(0, 1, 0);
            });

            poseStack.popPose();
		}
	}

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<Integer> {
        public static final Identifier ID = RRIdentifiers.create("tesla_renderer");
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);
        @Override
        public SpecialModelRenderer<Integer> bake(BakingContext context) {
            return new TeslaRenderer();
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }

}

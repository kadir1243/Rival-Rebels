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
import io.github.kadir1243.rivalrebels.client.model.ModelRod;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3fc;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class PlasmaCannonRenderer implements NoDataSpecialModelRenderer {
    private final QuadCollection plasmaCannonModel = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.PLASMA_CANNON_MODEL);
    public PlasmaCannonRenderer() {
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        poseStack.pushPose();
		poseStack.translate(-0.1f, 0f, 0f);
		poseStack.pushPose();
		poseStack.translate(0.5f, 0.2f, -0.03f);
		poseStack.mulPose(Axis.ZP.rotationDegrees(35));
		poseStack.scale(0.03125f, 0.03125f, 0.03125f);
		poseStack.pushPose();

        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(RRIdentifiers.etplasmacannon), (pose, consumer) -> {
            ObjModels.render(plasmaCannonModel, consumer, pose, CommonColors.WHITE, lightCoords, overlayCoords);
        });
        if (hasFoil) {
            submitNodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.CELLULAR_NOISE, (pose, consumer) -> {
                ObjModels.render(plasmaCannonModel, consumer, pose, CommonColors.WHITE, lightCoords, overlayCoords);
            });
		}

		poseStack.popPose();
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(0.5f, 0.2f, -0.03f);
		poseStack.mulPose(Axis.ZP.rotationDegrees(35));
		poseStack.pushPose();
		poseStack.mulPose(Axis.ZP.rotationDegrees(225));
		poseStack.translate(-0.5f, 0.5f, 0.0f);
		poseStack.scale(0.25f, 0.5f, 0.25f);
        ModelRod.render(poseStack, submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.ethydrod), lightCoords, overlayCoords, false);
		if (hasFoil) {
			ModelRod.render(poseStack, submitNodeCollector, RRRenderTypes.CELLULAR_NOISE, lightCoords, overlayCoords, false);
		}
		poseStack.popPose();
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(0.5f, 0.2f, -0.03f);
		poseStack.mulPose(Axis.ZP.rotationDegrees(35));
		poseStack.pushPose();
		poseStack.mulPose(Axis.ZP.rotationDegrees(247.5f));
		poseStack.translate(-0.175f, 0.1f, 0.0f);
		poseStack.scale(0.25f, 0.5f, 0.25f);
		ModelRod.render(poseStack, submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.ethydrod), lightCoords, overlayCoords);
		if (hasFoil) {
			ModelRod.render(poseStack, submitNodeCollector, RRRenderTypes.CELLULAR_NOISE, lightCoords, overlayCoords);
		}
		poseStack.popPose();
		poseStack.popPose();
		poseStack.popPose();
	}

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }
}


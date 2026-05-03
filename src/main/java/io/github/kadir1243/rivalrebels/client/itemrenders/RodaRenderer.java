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
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
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
public class RodaRenderer implements NoDataSpecialModelRenderer {
    private final QuadCollection rodaModel = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.RODA_MODEL);

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        poseStack.pushPose();
		poseStack.translate(0.5f, 0.5f, -0.03f);
		poseStack.mulPose(Axis.ZP.rotationDegrees(35));
		poseStack.mulPose(Axis.YP.rotationDegrees(90));
		poseStack.scale(0.35f, 0.35f, 0.35f);
		//if (!mode.firstPerson()) poseStack.scale(-1, 1, 1);
		poseStack.translate(0.2f, -0.55f, 0.1f);

        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(RRIdentifiers.etrust), (pose, consumer) ->
            ObjModels.render(rodaModel, consumer, pose, CommonColors.WHITE, lightCoords, overlayCoords)
        );
        submitNodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.CELLULAR_NOISE, (pose, consumer) ->
            ObjModels.render(rodaModel, consumer, pose, CommonColors.WHITE, lightCoords, overlayCoords)
        );

		poseStack.popPose();
	}

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }
}


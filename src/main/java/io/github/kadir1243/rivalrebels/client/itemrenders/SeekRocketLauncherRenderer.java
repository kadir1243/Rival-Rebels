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
import io.github.kadir1243.rivalrebels.client.model.ModelRocketLauncherBody;
import io.github.kadir1243.rivalrebels.client.model.ModelRocketLauncherHandle;
import io.github.kadir1243.rivalrebels.client.model.ModelRocketLauncherTube;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3fc;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class SeekRocketLauncherRenderer implements NoDataSpecialModelRenderer {
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        poseStack.pushPose();
		poseStack.translate(0.4f, 0.35f, -0.03f);
		poseStack.mulPose(Axis.ZP.rotationDegrees(-55));
		poseStack.translate(0f, 0.05f, 0.05f);
		//if (mode.firstPerson()) poseStack.scale(1, 1, -1);
		poseStack.pushPose();
		poseStack.translate(0.22f, -0.025f, 0f);
		poseStack.mulPose(Axis.ZP.rotationDegrees(90));
		poseStack.scale(0.03125f, 0.03125f, 0.03125f);
        ModelRocketLauncherHandle.render(poseStack, submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.etrocketseekhandle202), lightCoords, overlayCoords);
		if (hasFoil) {
			ModelRocketLauncherHandle.render(poseStack, submitNodeCollector, RRRenderTypes.CELLULAR_NOISE, lightCoords, overlayCoords);
		}
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(-0.07f, 0.31f, 0f);
		poseStack.mulPose(Axis.ZP.rotationDegrees(90));
		poseStack.mulPose(Axis.YP.rotationDegrees(90));
		poseStack.scale(0.4f, 0.4f, 0.4f);
		ModelRocketLauncherBody.render(poseStack, submitNodeCollector, RenderTypes.entitySolid(RRIdentifiers.etseek202), lightCoords, overlayCoords);
		if (hasFoil) {
			ModelRocketLauncherBody.render(poseStack, submitNodeCollector, RRRenderTypes.CELLULAR_NOISE, lightCoords, overlayCoords);
        }
		poseStack.popPose();

		float s = 0.0812f;

        RenderType rocketSeekTube202RenderType = RenderTypes.entitySolid(RRIdentifiers.etrocketseektube202);
		poseStack.pushPose();
		poseStack.translate(-0.07f + s, 0.71f, s);
		poseStack.scale(0.15f, 0.1f, 0.15f);
        ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(-0.07f - s, 0.71f, s);
		poseStack.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(-0.07f + s, 0.71f, -s);
		poseStack.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(-0.07f - s, 0.71f, -s);
		poseStack.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		// ---

		poseStack.pushPose();
		poseStack.translate(-0.07f + s, -0.285f, s);
		poseStack.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(-0.07f - s, -0.285f, s);
		poseStack.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(-0.07f + s, -0.285f, -s);
		poseStack.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(-0.07f - s, -0.285f, -s);
		poseStack.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(poseStack, submitNodeCollector, rocketSeekTube202RenderType, lightCoords, overlayCoords);
		poseStack.popPose();

		poseStack.popPose();
	}

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }
}

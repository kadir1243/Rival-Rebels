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
package assets.rivalrebels.client.itemrenders;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelRocketLauncherBody;
import assets.rivalrebels.client.model.ModelRocketLauncherHandle;
import assets.rivalrebels.client.model.ModelRocketLauncherTube;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SeekRocketLauncherRenderer implements DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0.4f, 0.35f, -0.03f);
		matrices.mulPose(Axis.ZP.rotationDegrees(-55));
		matrices.translate(0f, 0.05f, 0.05f);
		if (mode.firstPerson()) matrices.scale(1, 1, -1);
		matrices.pushPose();
		matrices.translate(0.22f, -0.025f, 0f);
		matrices.mulPose(Axis.ZP.rotationDegrees(90));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        ModelRocketLauncherHandle.render(matrices, vertexConsumers.getBuffer(RenderType.entitySolid(RRIdentifiers.etrocketseekhandle202)), light, overlay);
		if (stack.isEnchanted()) {
			ModelRocketLauncherHandle.render(matrices, cellularNoise, light, overlay);
		}
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f, 0.31f, 0f);
		matrices.mulPose(Axis.ZP.rotationDegrees(90));
		matrices.mulPose(Axis.YP.rotationDegrees(90));
		matrices.scale(0.4f, 0.4f, 0.4f);
		ModelRocketLauncherBody.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.etseek202)), light, overlay);
		if (stack.isEnchanted()) {
			ModelRocketLauncherBody.render(matrices, vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE_TRIANGLES), light, overlay);
        }
		matrices.popPose();

		float s = 0.0812f;

		matrices.pushPose();
		matrices.translate(-0.07f + s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
        VertexConsumer rocketSeekTube202TextureVertexConsumer = vertexConsumers.getBuffer(RenderType.entitySolid(RRIdentifiers.etrocketseektube202));
        ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f + s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		// ---

		matrices.pushPose();
		matrices.translate(-0.07f + s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f + s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(-0.07f - s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.popPose();

		matrices.popPose();
	}
}

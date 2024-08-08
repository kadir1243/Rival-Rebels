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
import assets.rivalrebels.client.model.ModelRod;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class PlasmaCannonRenderer implements DynamicItemRenderer {
    public PlasmaCannonRenderer() {
    }

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(-0.1f, 0f, 0f);
		matrices.pushPose();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.mulPose(Axis.ZP.rotationDegrees(35));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
		matrices.pushPose();

        ObjModels.renderSolid(ObjModels.plasma_cannon, RRIdentifiers.etplasmacannon, matrices, vertexConsumers, light, overlay);
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE_TRIANGLES);
        if (stack.isEnchanted()) {
			ObjModels.renderNoise(ObjModels.plasma_cannon, matrices, vertexConsumers, light, overlay);
		}

		matrices.popPose();
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.mulPose(Axis.ZP.rotationDegrees(35));
		matrices.pushPose();
		matrices.mulPose(Axis.ZP.rotationDegrees(225));
		matrices.translate(-0.5f, 0.5f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
        VertexConsumer hydrodVertexConsumer = vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.ethydrod));
        ModelRod.render(matrices, hydrodVertexConsumer, light, overlay, false);
		if (stack.isEnchanted()) {
			ModelRod.render(matrices, cellularNoise, light, overlay, false);
		}
		matrices.popPose();
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.mulPose(Axis.ZP.rotationDegrees(35));
		matrices.pushPose();
		matrices.mulPose(Axis.ZP.rotationDegrees(247.5f));
		matrices.translate(-0.175f, 0.1f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
		ModelRod.render(matrices, hydrodVertexConsumer, light, overlay);
		if (stack.isEnchanted()) {
			ModelRod.render(matrices, cellularNoise, light, overlay);
		}
		matrices.popPose();
		matrices.popPose();
		matrices.popPose();
	}
}


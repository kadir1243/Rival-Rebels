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
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class PlasmaCannonRenderer implements DynamicItemRenderer {
    private final QuadCollection plasmaCannonModel = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.PLASMA_CANNON_MODEL);
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

        ObjModels.render(plasmaCannonModel, vertexConsumers.getBuffer(RenderType.entitySolid(RRIdentifiers.etplasmacannon)), matrices, CommonColors.WHITE, light, overlay);
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RenderTypes.CELLULAR_NOISE);
        if (stack.isEnchanted()) {
			ObjModels.render(plasmaCannonModel, cellularNoise, matrices, CommonColors.WHITE, light, overlay);
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
        VertexConsumer hydrodVertexConsumer = vertexConsumers.getBuffer(RenderType.entitySolid(RRIdentifiers.ethydrod));
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


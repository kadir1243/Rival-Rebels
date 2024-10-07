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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class RodaRenderer implements DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0.5f, 0.5f, -0.03f);
		matrices.mulPose(Axis.ZP.rotationDegrees(35));
		matrices.mulPose(Axis.YP.rotationDegrees(90));
		matrices.scale(0.35f, 0.35f, 0.35f);
		if (!mode.firstPerson()) matrices.scale(-1, 1, 1);
		matrices.translate(0.2f, -0.55f, 0.1f);

        ObjModels.renderSolid(ObjModels.roda, RRIdentifiers.etrust, matrices, vertexConsumers, light, overlay);
		matrices.pushPose();
		ObjModels.renderNoise(ObjModels.roda, matrices, vertexConsumers, light, overlay);
		matrices.popPose();

		matrices.popPose();
	}
}


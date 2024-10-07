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
package io.github.kadir1243.rivalrebels.client.tileentityrender;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelLaptop;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockLaptop;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityLaptop;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class TileEntityLaptopRenderer implements BlockEntityRenderer<TileEntityLaptop> {
    public TileEntityLaptopRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityLaptop entity, float tickDelta, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
		pose.pushPose();
		pose.translate(0.5F, 0, 0.5F);
        pose.mulPose(Axis.YP.rotationDegrees(entity.getBlockState().getValue(BlockLaptop.FACING).toYRot()));
        ModelLaptop.renderModel(vertexConsumers, pose, (float) -entity.slide, light, overlay);
		ModelLaptop.renderScreen(vertexConsumers, RRIdentifiers.etubuntu, pose, (float) -entity.slide, light, overlay);
		pose.popPose();
	}
}

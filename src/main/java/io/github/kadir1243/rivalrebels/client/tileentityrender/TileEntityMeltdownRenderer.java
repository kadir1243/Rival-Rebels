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

import io.github.kadir1243.rivalrebels.client.model.ModelBlastSphere;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityMeltDown;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@OnlyIn(Dist.CLIENT)
public class TileEntityMeltdownRenderer implements BlockEntityRenderer<TileEntityMeltDown> {
	public TileEntityMeltdownRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public void render(TileEntityMeltDown blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        float fsize = Mth.sin(blockEntity.size);
		if (fsize <= 0) return;
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.size * 50));

        ModelBlastSphere.renderModel(poseStack, bufferSource, fsize * 5.5f, 1, 1, 1, 0.4f);

		poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.size * 50));

		ModelBlastSphere.renderModel(poseStack, bufferSource, fsize * 5.6f, 1, 1, 1, 0.4f);

		poseStack.popPose();

		ModelBlastSphere.renderModel(poseStack, bufferSource, fsize * 5.9f, 1, 1, 1, 0.4f);

		poseStack.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityMeltDown blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-2, -2, -2), blockEntity.getBlockPos().offset(3, 3, 3)));
    }
}

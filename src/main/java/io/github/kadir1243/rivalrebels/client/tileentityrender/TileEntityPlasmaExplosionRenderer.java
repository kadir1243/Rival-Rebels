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
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityPlasmaExplosion;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@OnlyIn(Dist.CLIENT)
public class TileEntityPlasmaExplosionRenderer implements BlockEntityRenderer<TileEntityPlasmaExplosion> {
    public TileEntityPlasmaExplosionRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public void render(TileEntityPlasmaExplosion entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		float fsize = Mth.sin(entity.size);
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);

		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.size * 50));
        ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.5f, 0.45f, 0.45f, 0.65f, 0.4f);
        matrices.mulPose(Axis.YP.rotationDegrees(entity.size * 50));
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.6f, 0.45f, 0.35f, 0.65f, 0.4f);
        matrices.mulPose(Axis.YP.rotationDegrees(entity.size * 50));
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.7f, 0.45f, 0.35f, 0.95f, 0.4f);
        matrices.mulPose(Axis.YP.rotationDegrees(entity.size * 50));
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.8f, 0.45f, 0.35f, 0.65f, 0.4f);
		matrices.popPose();
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.9f, 0.45f, 0.35f, 0.65f, 0.4f);
		matrices.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityPlasmaExplosion blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-2, -2, -2), blockEntity.getBlockPos().offset(3, 3, 3)));
    }
}

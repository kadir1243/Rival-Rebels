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
import io.github.kadir1243.rivalrebels.client.model.ModelLoader;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockLoader;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@OnlyIn(Dist.CLIENT)
public class TileEntityLoaderRenderer implements BlockEntityRenderer<TileEntityLoader> {
    public TileEntityLoaderRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityLoader loader, float tickDelta, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
		pose.pushPose();
		pose.translate(0.5F, 0.5F, 0.5F);
        pose.mulPose(Axis.YP.rotationDegrees(loader.getBlockState().getValue(BlockLoader.FACING).toYRot()));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.etloader));
		ModelLoader.renderA(vertexConsumer, pose, light, overlay);
		ModelLoader.renderB(vertexConsumer, pose, loader.slide, light, overlay);
		pose.popPose();
        for (BlockEntity machine : loader.machines) {
			pose.pushPose();
			pose.translate(0.5F, 0.5F, 0.5F);
			int xdif = machine.getBlockPos().getX() - loader.getBlockPos().getX();
			int zdif = machine.getBlockPos().getZ() - loader.getBlockPos().getZ();
			pose.mulPose(Axis.YP.rotationDegrees((float) (-90 + (Math.atan2(xdif, zdif) / Mth.PI) * 180F)));
			pose.translate(-1f, -0.40f, 0);
			pose.scale(0.5F, 0.15F, 0.15F);
			int dist = (int) Mth.sqrt((xdif * xdif) + (zdif * zdif));
            for (int d = 0; d < dist; d++) {
				pose.translate(2, 0, 0);
                ObjModels.renderSolid(ObjModels.tube, RRIdentifiers.ettube, pose, vertexConsumers, light, overlay);
            }
			pose.popPose();
		}
	}

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityLoader blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, -1, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }
}

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
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockReciever;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityReciever;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@OnlyIn(Dist.CLIENT)
public class TileEntityRecieverRenderer implements BlockEntityRenderer<TileEntityReciever> {
    public TileEntityRecieverRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public void render(TileEntityReciever entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0.5F, 0, 0.5F);
        Direction facing = entity.getBlockState().getValue(BlockReciever.FACING);

		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
        matrices.translate(0, 0, 0.5);
        ObjModels.renderSolid(ObjModels.tray, RRIdentifiers.etreciever, matrices, vertexConsumers, light, overlay);
		if (entity.hasWeapon) {
            matrices.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			matrices.mulPose(Axis.YP.rotationDegrees(entity.yaw - facing.toYRot()));
			ObjModels.renderSolid(ObjModels.arm, RRIdentifiers.etreciever, matrices, vertexConsumers, light, overlay);
            matrices.mulPose(Axis.XP.rotationDegrees(entity.pitch));
			ObjModels.renderSolid(ObjModels.adsdragon, RRIdentifiers.etadsdragon, matrices, vertexConsumers, light, overlay);
		}
		matrices.popPose();
		matrices.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityReciever blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }
}

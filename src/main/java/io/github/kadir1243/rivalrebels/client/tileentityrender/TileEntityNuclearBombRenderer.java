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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.block.trap.BlockNuclearBomb;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityNuclearBomb;
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
public class TileEntityNuclearBombRenderer implements BlockEntityRenderer<TileEntityNuclearBomb> {
    public TileEntityNuclearBombRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityNuclearBomb entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        Direction direction = entity.getBlockState().getValue(BlockNuclearBomb.FACING);
        if (direction.getAxis().isVertical()) {
            matrices.mulPose(Axis.XP.rotationDegrees(direction.toYRot()));
        } else {
            matrices.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));
        }
		ObjModels.renderSolid(ObjModels.nuke, RRIdentifiers.etwacknuke, matrices, vertexConsumers, light, overlay);
		matrices.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 65536;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityNuclearBomb blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }
}

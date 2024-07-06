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
package assets.rivalrebels.client.tileentityrender;

import assets.rivalrebels.client.model.ModelTsarBomba;
import assets.rivalrebels.common.block.trap.BlockTsarBomba;
import assets.rivalrebels.common.tileentity.TileEntityTsarBomba;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@Environment(EnvType.CLIENT)
public class TileEntityTsarBombaRenderer implements BlockEntityRenderer<TileEntityTsarBomba>, CustomRenderBoxExtension<TileEntityTsarBomba> {
    public TileEntityTsarBombaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityTsarBomba entity, float tickDelta, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
        pose.pushPose();
        pose.translate(0.5F, 1F, 0.5F);
        Direction facing = entity.getBlockState().getValue(BlockTsarBomba.FACING);

        pose.mulPose(facing.getRotation());
        ModelTsarBomba.render(pose, vertexConsumers, light, overlay);
        pose.popPose();
    }

    @Override
    public int getViewDistance() {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityTsarBomba blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, 0, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }
}

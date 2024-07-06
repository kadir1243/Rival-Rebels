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

import assets.rivalrebels.client.model.ModelTheoreticalTsarBomba;
import assets.rivalrebels.common.block.trap.BlockTheoreticalTsarBomba;
import assets.rivalrebels.common.tileentity.TileEntityTheoreticalTsarBomba;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@Environment(EnvType.CLIENT)
public class TileEntityTheoreticalTsarBombaRenderer implements BlockEntityRenderer<TileEntityTheoreticalTsarBomba>, CustomRenderBoxExtension<TileEntityTheoreticalTsarBomba> {
    public TileEntityTheoreticalTsarBombaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityTheoreticalTsarBomba entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5F, 1F, 0.5F);
        matrices.scale(1.3f, 1.3f, 1.3f);
        int metadata = entity.getBlockState().getValue(BlockTheoreticalTsarBomba.FACING).get3DDataValue();

        if (metadata == 2) {
            matrices.mulPose(Axis.YP.rotationDegrees(180));
            matrices.mulPose(Axis.XP.rotationDegrees(90));
        } else if (metadata == 3) {
            matrices.mulPose(Axis.XP.rotationDegrees(90));
        } else if (metadata == 4) {
            matrices.mulPose(Axis.YP.rotationDegrees(-90));
            matrices.mulPose(Axis.XP.rotationDegrees(90));
        } else if (metadata == 5) {
            matrices.mulPose(Axis.YP.rotationDegrees(90));
            matrices.mulPose(Axis.XP.rotationDegrees(90));
        }
        ModelTheoreticalTsarBomba.render(matrices, vertexConsumers, light, overlay);
        matrices.popPose();
    }

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityTheoreticalTsarBomba blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, 0, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }
}

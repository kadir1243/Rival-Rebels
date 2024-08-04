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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.block.trap.BlockTachyonBomb;
import assets.rivalrebels.common.tileentity.TileEntityTachyonBomb;
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
public class TileEntityTachyonBombRenderer implements BlockEntityRenderer<TileEntityTachyonBomb>, CustomRenderBoxExtension<TileEntityTachyonBomb> {
    public TileEntityTachyonBombRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityTachyonBomb entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5F, 1F, 0.5F);
        matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());

        matrices.mulPose(Axis.YP.rotationDegrees(entity.getBlockState().getValue(BlockTachyonBomb.FACING).toYRot()));
        ObjModels.renderSolid(ObjModels.bomb, RRIdentifiers.ettachyonbomb, matrices, vertexConsumers, light, overlay);
        matrices.popPose();
    }

    @Override
    public int getViewDistance() {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityTachyonBomb blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, 0, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }
}

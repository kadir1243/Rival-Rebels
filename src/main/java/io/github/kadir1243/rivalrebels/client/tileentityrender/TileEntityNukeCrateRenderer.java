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
import io.github.kadir1243.rivalrebels.client.model.ModelNukeCrate;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.block.crate.BlockNukeCrate;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityNukeCrate;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

@OnlyIn(Dist.CLIENT)
public class TileEntityNukeCrateRenderer implements BlockEntityRenderer<TileEntityNukeCrate> {
    public TileEntityNukeCrateRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityNukeCrate entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5F, 0.5F, 0.5F);
        Direction metadata = entity.getBlockState().getValue(BlockNukeCrate.FACING);
        switch (metadata) {
            case DOWN -> matrices.mulPose(Axis.XP.rotationDegrees(180));
            case NORTH -> matrices.mulPose(Axis.XP.rotationDegrees(-90));
            case SOUTH -> matrices.mulPose(Axis.XP.rotationDegrees(90));
            case WEST -> matrices.mulPose(Axis.ZP.rotationDegrees(90));
            case EAST -> matrices.mulPose(Axis.ZP.rotationDegrees(-90));
        }
        VertexConsumer buffer;
        if (entity.getBlockState().is(RRBlocks.nukeCrateBottom))
            buffer = vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.btnukebottom));
        else if (entity.getBlockState().is(RRBlocks.nukeCrateTop))
            buffer = vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.btnuketop));
        else throw new UnsupportedOperationException("Unknown block to render");
        ModelNukeCrate.renderModelA(matrices, buffer, light, overlay);
        ModelNukeCrate.renderModelB(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.btcrate)), light, overlay);
        matrices.popPose();
    }
}

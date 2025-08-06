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
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.block.crate.BlockNukeCrate;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityNukeCrate;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
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
    public void render(TileEntityNukeCrate blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        Direction metadata = blockEntity.getBlockState().getValue(BlockNukeCrate.FACING);
        switch (metadata) {
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(180));
            case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            case SOUTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
        }
        VertexConsumer buffer;
        if (blockEntity.getBlockState().is(RRBlocks.nukeCrateBottom))
            buffer = bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.btnukebottom));
        else if (blockEntity.getBlockState().is(RRBlocks.nukeCrateTop))
            buffer = bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.btnuketop));
        else throw new UnsupportedOperationException("Unknown block to render");
        ModelNukeCrate.renderModelA(poseStack, buffer, packedLight, packedOverlay);
        ModelNukeCrate.renderModelB(poseStack, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.btcrate)), packedLight, packedOverlay);
        poseStack.popPose();
    }
}

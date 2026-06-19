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
package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.common.entity.EntityDebris;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.entity.state.FallingBlockRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RenderDebris extends EntityRenderer<EntityDebris, FallingBlockRenderState> {
    private final BlockRenderDispatcher dispatcher;

    public RenderDebris(EntityRendererProvider.Context manager) {
        super(manager);
        this.shadowRadius = 0.5F;
        dispatcher = manager.getBlockRenderDispatcher();
    }

    @Override
    public FallingBlockRenderState createRenderState() {
        return new FallingBlockRenderState();
    }

    @Override
    public void extractRenderState(EntityDebris p_entity, FallingBlockRenderState reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.blockState = p_entity.getState();
        reusedState.blockPos = BlockPos.containing(p_entity.getX(), p_entity.getBoundingBox().maxY, p_entity.getZ());
        reusedState.level = p_entity.level();
    }

    @Override
    public void render(FallingBlockRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        BlockState state = renderState.blockState;
        if (state == null || state.isAir()) return; // Why ???
        poseStack.pushPose();

        BlockPos blockpos = renderState.blockPos;

        poseStack.translate(-0.5, 0.0, -0.5);
        if (state.getRenderShape() != RenderShape.INVISIBLE) {
            List<BlockModelPart> list = dispatcher.getBlockModel(state).collectParts(renderState.level, blockpos, state, RandomSource.create(state.getSeed(blockpos)));
            dispatcher.getModelRenderer().tesselateBlock(renderState.level, list, state, blockpos, poseStack, bufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)), false, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
    }
}

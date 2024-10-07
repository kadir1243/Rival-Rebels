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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

@OnlyIn(Dist.CLIENT)
public class RenderDebris extends EntityRenderer<EntityDebris> {
    private final BlockRenderDispatcher dispatcher;

    public RenderDebris(EntityRendererProvider.Context manager) {
        super(manager);
        this.shadowRadius = 0.5F;
        dispatcher = manager.getBlockRenderDispatcher();
    }

    @Override
    public void render(EntityDebris entity, float yaw, float tickDelta, PoseStack pose, MultiBufferSource vertexConsumers, int light) {
		if (!entity.isAlive()) return;
        BlockState state = entity.getState();
        if (state == null || state.isAir()) return; // Why ???
        pose.pushPose();

        BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());

        pose.translate(-0.5, 0.0, -0.5);
        if (state.getRenderShape() != RenderShape.INVISIBLE) {
            dispatcher.getModelRenderer().tesselateBlock(entity.level(), dispatcher.getBlockModel(state), state, blockpos, pose, vertexConsumers.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)), false, RandomSource.create(), state.getSeed(blockpos), OverlayTexture.NO_OVERLAY);
        }

        pose.popPose();
    }

	@Override
	public ResourceLocation getTextureLocation(EntityDebris entity)
	{
		return InventoryMenu.BLOCK_ATLAS;
	}
}

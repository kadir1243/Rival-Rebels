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
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.common.entity.EntityDebris;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class RenderDebris extends EntityRenderer<EntityDebris> {

    private final BlockRenderManager blockRenderManager;

    public RenderDebris(EntityRendererFactory.Context manager)
	{
        super(manager);
        this.shadowRadius = 0.5F;
        blockRenderManager = manager.getBlockRenderManager();
    }

    @Override
    public void render(EntityDebris entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		if (!entity.isAlive()) return;
        BlockState state = entity.getState();
        if (state == null || state.getRenderType() != BlockRenderType.MODEL) return; // Why ???
        matrices.push();

        BlockPos blockpos = new BlockPos(MathHelper.floor(entity.getX()), MathHelper.floor(entity.getBoundingBox().maxY), MathHelper.floor(entity.getZ()));

        blockRenderManager.getModelRenderer().render(entity.getWorld(), blockRenderManager.getModel(state), state, blockpos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state)), false, Random.create(), state.getRenderingSeed(blockpos), OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }

	@Override
	public Identifier getTexture(EntityDebris entity)
	{
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}
}

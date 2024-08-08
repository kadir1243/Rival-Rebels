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
import assets.rivalrebels.mixin.client.BlockEntityRenderersAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class RenderDebris extends EntityRenderer<EntityDebris> {
    private final BlockRenderDispatcher dispatcher;
    private final BlockEntityRendererProvider.Context context;
    private BlockEntityRenderer<BlockEntity> blockEntityRenderer;

    public RenderDebris(EntityRendererProvider.Context manager) {
        super(manager);
        this.shadowRadius = 0.5F;
        dispatcher = manager.getBlockRenderDispatcher();
        context = new BlockEntityRendererProvider.Context(Minecraft.getInstance().getBlockEntityRenderDispatcher(), manager.getBlockRenderDispatcher(), manager.getItemRenderer(), manager.getEntityRenderDispatcher(), manager.getModelSet(), manager.getFont());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(EntityDebris entity, float yaw, float tickDelta, PoseStack pose, MultiBufferSource vertexConsumers, int light) {
		if (!entity.isAlive()) return;
        BlockState state = entity.getState();
        if (state == null) return; // Why ???
        pose.pushPose();

        BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());

        pose.translate(-0.5, 0.0, -0.5);
        if (state.getRenderShape() != RenderShape.INVISIBLE) {
            dispatcher.getModelRenderer().tesselateBlock(entity.level(), dispatcher.getBlockModel(state), state, blockpos, pose, vertexConsumers.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)), false, RandomSource.create(), state.getSeed(blockpos), OverlayTexture.NO_OVERLAY);
        } else {
            BlockEntity blockEntity = entity.getBlockEntity();
            if (blockEntity != null) {
                if (blockEntityRenderer == null) {
                    blockEntityRenderer = (BlockEntityRenderer<BlockEntity>) BlockEntityRenderersAccessor.getProviders()
                        .get(blockEntity.getType())
                        .create(context);
                }
                blockEntityRenderer.render(blockEntity, tickDelta, pose, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
            }
        }

        pose.popPose();
    }

	@Override
	public ResourceLocation getTextureLocation(EntityDebris entity)
	{
		return InventoryMenu.BLOCK_ATLAS;
	}
}

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
import io.github.kadir1243.rivalrebels.client.model.ModelJump;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityJumpBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class TileEntityJumpBlockRenderer implements BlockEntityRenderer<TileEntityJumpBlock> {
    public TileEntityJumpBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityJumpBlock blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		ModelJump.renderModel(poseStack, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.btcrate)), packedLight, packedOverlay);
		poseStack.popPose();
	}
}

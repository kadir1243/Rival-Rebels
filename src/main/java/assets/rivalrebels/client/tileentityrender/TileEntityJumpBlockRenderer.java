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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelJump;
import assets.rivalrebels.common.tileentity.TileEntityJumpBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;

@Environment(EnvType.CLIENT)
public class TileEntityJumpBlockRenderer implements BlockEntityRenderer<TileEntityJumpBlock> {
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.btcrate);
    public TileEntityJumpBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityJumpBlock entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		ModelJump.renderModel(matrices, TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		matrices.popPose();
	}
}

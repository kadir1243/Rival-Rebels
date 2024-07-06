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
import assets.rivalrebels.client.model.ModelNukeCrate;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.crate.BlockNukeCrate;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;

public class TileEntityNukeCrateRenderer implements BlockEntityRenderer<TileEntityNukeCrate> {
    public static final Material NUKE_CRATE_TOP_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.btnuketop);
    public static final Material NUKE_CRATE_BOTTOM_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.btnukebottom);
    public static final Material CRATE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.btcrate);

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
            buffer = NUKE_CRATE_BOTTOM_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
        else if (entity.getBlockState().is(RRBlocks.nukeCrateTop))
            buffer = NUKE_CRATE_TOP_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
        else throw new UnsupportedOperationException("Unknown block to render");
        ModelNukeCrate.renderModelA(matrices, buffer, light, overlay);
        ModelNukeCrate.renderModelB(matrices, CRATE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
        matrices.popPose();
    }
}

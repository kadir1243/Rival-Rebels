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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;

public class TileEntityNukeCrateRenderer implements BlockEntityRenderer<TileEntityNukeCrate> {
    private final ModelNukeCrate model;

    public TileEntityNukeCrateRenderer(BlockEntityRendererFactory.Context context) {
        model = new ModelNukeCrate();
    }

    @Override
    public void render(TileEntityNukeCrate entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
        Direction metadata = entity.getCachedState().get(BlockNukeCrate.DIRECTION);
        switch (metadata) {
            case DOWN -> matrices.multiply(new Quaternion(180, 1, 0, 0));
            case NORTH -> matrices.multiply(new Quaternion(-90, 1, 0, 0));
            case SOUTH -> matrices.multiply(new Quaternion(90, 1, 0, 0));
            case WEST -> matrices.multiply(new Quaternion(90, 0, 0, 1));
            case EAST -> matrices.multiply(new Quaternion(-90, 0, 0, 1));
        }
        if (entity.getWorld().getBlockState(entity.getPos()).getBlock() == RRBlocks.nukeCrateBottom)
            MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btnukebottom);
        if (entity.getWorld().getBlockState(entity.getPos()).getBlock() == RRBlocks.nukeCrateTop)
            MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btnuketop);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        model.renderModelA(matrices, buffer);
        MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btcrate);
        model.renderModelB(matrices, buffer);
        matrices.pop();
    }
}

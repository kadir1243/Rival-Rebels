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

import assets.rivalrebels.client.model.ModelTsarBomba;
import assets.rivalrebels.common.block.trap.BlockTsarBomba;
import assets.rivalrebels.common.tileentity.TileEntityTsarBomba;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityTsarBombaRenderer implements BlockEntityRenderer<TileEntityTsarBomba> {
    private final ModelTsarBomba model;

    public TileEntityTsarBombaRenderer(BlockEntityRendererFactory.Context context) {
        model = new ModelTsarBomba();
    }

    @Override
    public void render(TileEntityTsarBomba entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 1F, (float) entity.getPos().getZ() + 0.5F);
        int metadata = entity.getCachedState().get(BlockTsarBomba.META);

        if (metadata == 2) {
            matrices.multiply(new Quaternion(180, 0, 1, 0));
            matrices.multiply(new Quaternion(90, 1, 0, 0));
        } else if (metadata == 3) {
            matrices.multiply(new Quaternion(90, 1, 0, 0));
        } else if (metadata == 4) {
            matrices.multiply(new Quaternion(-90, 0, 1, 0));
            matrices.multiply(new Quaternion(90, 1, 0, 0));
        } else if (metadata == 5) {
            matrices.multiply(new Quaternion(90, 0, 1, 0));
            matrices.multiply(new Quaternion(90, 1, 0, 0));
        }
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getSolid()));
        matrices.pop();
    }

    @Override
    public int getRenderDistance() {
        return 16384;
    }

}

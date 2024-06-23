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

import assets.rivalrebels.client.model.ModelTheoreticalTsarBomba;
import assets.rivalrebels.common.block.trap.BlockTheoreticalTsarBomba;
import assets.rivalrebels.common.tileentity.TileEntityTheoreticalTsarBomba;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Box;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityTheoreticalTsarBombaRenderer implements BlockEntityRenderer<TileEntityTheoreticalTsarBomba>, CustomRenderBoxExtension<TileEntityTheoreticalTsarBomba> {
    public TileEntityTheoreticalTsarBombaRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(TileEntityTheoreticalTsarBomba entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 1F, (float) entity.getPos().getZ() + 0.5F);
        matrices.scale(1.3f, 1.3f, 1.3f);
        int metadata = entity.getCachedState().get(BlockTheoreticalTsarBomba.META);

        if (metadata == 2) {
            matrices.multiply(new Quaternionf(180, 0, 1, 0));
            matrices.multiply(new Quaternionf(90, 1, 0, 0));
        } else if (metadata == 3) {
            matrices.multiply(new Quaternionf(90, 1, 0, 0));
        } else if (metadata == 4) {
            matrices.multiply(new Quaternionf(-90, 0, 1, 0));
            matrices.multiply(new Quaternionf(90, 1, 0, 0));
        } else if (metadata == 5) {
            matrices.multiply(new Quaternionf(90, 0, 1, 0));
            matrices.multiply(new Quaternionf(90, 1, 0, 0));
        }
        ModelTheoreticalTsarBomba.render(matrices, vertexConsumers, light, overlay);
        matrices.pop();
    }

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

    @Override
    public Box getRenderBoundingBox(TileEntityTheoreticalTsarBomba blockEntity) {
        return Box.from(BlockBox.create(blockEntity.getPos().add(-5, 0, -5), blockEntity.getPos().add(6, 2, 6)));
    }
}

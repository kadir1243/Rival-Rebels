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

import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.tileentity.TileEntityPlasmaExplosion;
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
public class TileEntityPlasmaExplosionRenderer implements BlockEntityRenderer<TileEntityPlasmaExplosion>, CustomRenderBoxExtension<TileEntityPlasmaExplosion> {

    public TileEntityPlasmaExplosionRenderer(BlockEntityRendererFactory.Context context) {
	}

    @Override
    public void render(TileEntityPlasmaExplosion entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		float fsize = (float) Math.sin(entity.size);
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);

		matrices.push();
		matrices.multiply(new Quaternionf(entity.size * 50, 0f, 1, 0f));
        ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.5f, 0.45f, 0.45f, 0.65f, 0.4f);
        matrices.multiply(new Quaternionf(entity.size * 50, 0f, 1, 0f));
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.6f, 0.45f, 0.35f, 0.65f, 0.4f);
        matrices.multiply(new Quaternionf(entity.size * 50, 0f, 1, 0f));
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.7f, 0.45f, 0.35f, 0.95f, 0.4f);
        matrices.multiply(new Quaternionf(entity.size * 50, 0f, 1, 0f));
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.8f, 0.45f, 0.35f, 0.65f, 0.4f);
		matrices.pop();
		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.9f, 0.45f, 0.35f, 0.65f, 0.4f);
		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

    @Override
    public Box getRenderBoundingBox(TileEntityPlasmaExplosion blockEntity) {
        return Box.from(BlockBox.create(blockEntity.getPos().add(-2, -2, -2), blockEntity.getPos().add(3, 3, 3)));
    }
}

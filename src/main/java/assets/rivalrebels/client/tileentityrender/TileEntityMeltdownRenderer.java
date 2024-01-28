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
import assets.rivalrebels.common.tileentity.TileEntityMeltDown;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityMeltdownRenderer implements BlockEntityRenderer<TileEntityMeltDown> {
	private final ModelBlastSphere model;

	public TileEntityMeltdownRenderer(BlockEntityRendererFactory.Context context) {
		model = new ModelBlastSphere();
	}

    @Override
    public void render(TileEntityMeltDown entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float fsize = (float) Math.sin(entity.size);
		if (fsize <= 0) return;
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
		matrices.push();
		matrices.multiply(new Quaternion(entity.size * 50, 0f, 1, 0f));

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        model.renderModel(matrices, buffer, fsize * 5.5f, 1, 1, 1, 0.4f);

		matrices.multiply(new Quaternion(entity.size * 50, 0f, 1, 0f));

		model.renderModel(matrices, buffer, fsize * 5.6f, 1, 1, 1, 0.4f);

		matrices.pop();

		model.renderModel(matrices, buffer, fsize * 5.9f, 1, 1, 1, 0.4f);

		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

}

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
import assets.rivalrebels.client.model.ModelObjective;
import assets.rivalrebels.common.tileentity.TileEntityOmegaObjective;
import net.minecraft.client.MinecraftClient;
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
public class TileEntityOmegaObjectiveRenderer implements BlockEntityRenderer<TileEntityOmegaObjective>
{
	private final ModelObjective loaderModel;

	public TileEntityOmegaObjectiveRenderer(BlockEntityRendererFactory.Context context) {
		loaderModel = new ModelObjective();
	}

    @Override
    public void render(TileEntityOmegaObjective entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etomegaobj);

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        matrices.multiply(new Quaternion(90, 1, 0, 0));
		loaderModel.renderA(matrices, buffer);
		matrices.multiply(new Quaternion(-90, 1, 0, 0));
		matrices.multiply(new Quaternion(90, 0, 0, 1));
		loaderModel.renderB(matrices, buffer, (float) entity.slide, 96f / 256f, 44f / 128f, 0.125f, 0.84375f);
		matrices.multiply(new Quaternion(-90, 0, 0, 1));
		loaderModel.renderB(matrices, buffer, (float) entity.slide, 32f / 256f, 44f / 128f, 0.625f, 0.84375f);
		matrices.multiply(new Quaternion(90, 0, 1, 0));
		loaderModel.renderB(matrices, buffer, (float) entity.slide, 96f / 256f, 108f / 128f, 0.625f, 0.84375f);
		matrices.multiply(new Quaternion(90, 0, 1, 0));
		loaderModel.renderB(matrices, buffer, (float) entity.slide, 160f / 256f, 44f / 128f, 0.625f, 0.84375f);
		matrices.multiply(new Quaternion(90, 0, 1, 0));
		loaderModel.renderB(matrices, buffer, (float) entity.slide, 224f / 256f, 108f / 128f, 0.625f, 0.84375f);
		matrices.multiply(new Quaternion(90, 0, 1, 0));
		matrices.multiply(new Quaternion(-90, 0, 0, 1));
		loaderModel.renderB(matrices, buffer, (float) entity.slide, 224f / 256f, 44f / 128f, 0.625f, 0.84375f);
		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

}

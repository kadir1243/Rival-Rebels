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
import assets.rivalrebels.client.model.ModelLoader;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.common.block.machine.BlockLoader;
import assets.rivalrebels.common.tileentity.TileEntityLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityLoaderRenderer implements BlockEntityRenderer<TileEntityLoader> {
	private final ModelLoader loaderModel;
	private final ModelFromObj tube;

	public TileEntityLoaderRenderer(BlockEntityRendererFactory.Context context) {
		loaderModel = new ModelLoader();
        tube = ModelFromObj.readObjFile("l.obj");
	}

    @Override
    public void render(TileEntityLoader entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etloader);
		int var9 = entity.getCachedState().get(BlockLoader.META);
		short var11 = 0;
		if (var9 == 2)
		{
			var11 = 90;
		}

		if (var9 == 3)
		{
			var11 = -90;
		}

		if (var9 == 4)
		{
			var11 = 180;
		}

		if (var9 == 5)
		{
			var11 = 0;
		}

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderHelper.TRINGLES_POS_TEX_COLOR);
        matrices.multiply(new Quaternion(var11, 0.0F, 1.0F, 0.0F));
		loaderModel.renderA(vertexConsumer, matrices);
		loaderModel.renderB(vertexConsumer, matrices, (float) entity.slide);
		matrices.pop();
		for (int i = 0; i < entity.machines.size(); i++)
		{
			matrices.push();
			matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
			int xdif = entity.machines.get(i).getPos().getX() - entity.getPos().getX();
			int zdif = entity.machines.get(i).getPos().getZ() - entity.getPos().getZ();
			matrices.multiply(new Quaternion((float) (-90 + (Math.atan2(xdif, zdif) / Math.PI) * 180F), 0, 1, 0));
			matrices.translate(-1f, -0.40f, 0);
			MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.ettube);
			matrices.scale(0.5F, 0.15F, 0.15F);
			int dist = (int) Math.sqrt((xdif * xdif) + (zdif * zdif));
			for (int d = 0; d < dist; d++)
			{
				matrices.translate(2, 0, 0);
                tube.render(vertexConsumer);
			}
			matrices.pop();
		}
	}

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getRenderDistance()
    {
        return 16384;
    }

}

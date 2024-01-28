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
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.common.block.machine.BlockLaptop;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
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
public class TileEntityLaptopRenderer implements BlockEntityRenderer<TileEntityLaptop> {
	private final ModelLaptop model;

	public TileEntityLaptopRenderer(BlockEntityRendererFactory.Context context) {
		model = new ModelLaptop();
	}

    @Override
    public void render(TileEntityLaptop entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY(), (float) entity.getPos().getZ() + 0.5F);
        short var11 = switch (entity.getCachedState().get(BlockLaptop.META)) {
            case 2 -> 180;
            case 3 -> 0;
            case 4 -> -90;
            case 5 -> 90;
            default -> 0;
        };
        matrices.multiply(new Quaternion(var11, 0.0F, 1.0F, 0.0F));
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etlaptop);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderHelper.TRINGLES_POS_TEX);
        model.renderModel(vertexConsumer, matrices, (float) -entity.slide);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etubuntu);
		model.renderScreen(vertexConsumer, matrices, (float) -entity.slide);
		matrices.pop();
	}
}

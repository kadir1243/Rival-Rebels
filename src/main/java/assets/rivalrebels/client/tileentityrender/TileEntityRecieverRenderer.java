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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.block.machine.BlockReciever;
import assets.rivalrebels.common.tileentity.TileEntityReciever;
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
public class TileEntityRecieverRenderer implements BlockEntityRenderer<TileEntityReciever> {
	public static ModelFromObj base;
	public static ModelFromObj arm;
	public static ModelFromObj adsdragon;

	public TileEntityRecieverRenderer(BlockEntityRendererFactory.Context context) {
        base = ModelFromObj.readObjFile("p.obj");
        arm = ModelFromObj.readObjFile("q.obj");
        adsdragon = ModelFromObj.readObjFile("r.obj");
	}

    @Override
    public void render(TileEntityReciever entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etreciever);
		matrices.push();
		matrices.translate(entity.getPos().getX() + 0.5, entity.getPos().getY(), entity.getPos().getZ() + 0.5);
		int m = entity.getCachedState().get(BlockReciever.META);
		int r = 0;

		if (m == 2) r = 0;
		if (m == 3) r = 180;
		if (m == 4) r = 90;
		if (m == 5) r = -90;

		matrices.push();
		matrices.multiply(new Quaternion(r, 0, 1, 0));
        matrices.translate(0, 0, 0.5);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        base.render(buffer);
		if (entity.hasWeapon)
		{
            matrices.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			matrices.multiply(new Quaternion((float) (entity.yaw - r), 0, 1, 0));
			arm.render(buffer);
            matrices.multiply(new Quaternion((float) entity.pitch, 1, 0, 0));
			MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etadsdragon);
			adsdragon.render(buffer);
		}
		matrices.pop();
		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

}

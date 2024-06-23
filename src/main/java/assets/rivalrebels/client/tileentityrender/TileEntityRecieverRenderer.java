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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Box;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityRecieverRenderer implements BlockEntityRenderer<TileEntityReciever>, CustomRenderBoxExtension<TileEntityReciever> {
    public static final SpriteIdentifier RECIEVER_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etreciever);
    public static final SpriteIdentifier ETS_DRAGON = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etadsdragon);
    public static final ModelFromObj base = ModelFromObj.readObjFile("p.obj");
	public static final ModelFromObj arm = ModelFromObj.readObjFile("q.obj");
	public static final ModelFromObj adsdragon = ModelFromObj.readObjFile("r.obj");

	public TileEntityRecieverRenderer(BlockEntityRendererFactory.Context context) {
	}

    @Override
    public void render(TileEntityReciever entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(entity.getPos().getX() + 0.5, entity.getPos().getY(), entity.getPos().getZ() + 0.5);
		int m = entity.getCachedState().get(BlockReciever.META);
		int r = 0;

		if (m == 2) r = 0;
		if (m == 3) r = 180;
		if (m == 4) r = 90;
		if (m == 5) r = -90;

		matrices.push();
		matrices.multiply(new Quaternionf(r, 0, 1, 0));
        matrices.translate(0, 0, 0.5);
        VertexConsumer recieverTextureVertexConsumer = RECIEVER_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        base.render(recieverTextureVertexConsumer, light, overlay);
		if (entity.hasWeapon) {
            matrices.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			matrices.multiply(new Quaternionf((float) (entity.yaw - r), 0, 1, 0));
			arm.render(recieverTextureVertexConsumer, light, overlay);
            matrices.multiply(new Quaternionf((float) entity.pitch, 1, 0, 0));
			adsdragon.render(ETS_DRAGON.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		}
		matrices.pop();
		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

    @Override
    public Box getRenderBoundingBox(TileEntityReciever blockEntity) {
        return Box.from(BlockBox.create(blockEntity.getPos().add(-1, -1, -1), blockEntity.getPos().add(2, 2, 2)));
    }
}

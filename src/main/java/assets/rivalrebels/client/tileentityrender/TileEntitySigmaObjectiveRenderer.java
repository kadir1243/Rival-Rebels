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
import assets.rivalrebels.common.tileentity.TileEntitySigmaObjective;
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
public class TileEntitySigmaObjectiveRenderer implements BlockEntityRenderer<TileEntitySigmaObjective>, CustomRenderBoxExtension<TileEntitySigmaObjective> {
	public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etsigmaobj);

    public TileEntitySigmaObjectiveRenderer(BlockEntityRendererFactory.Context context) {
	}

    @Override
    public void render(TileEntitySigmaObjective entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);

        VertexConsumer buffer = TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        matrices.multiply(new Quaternionf(90, 1, 0, 0));
        ModelObjective.renderA(matrices, buffer, light, overlay);
        matrices.multiply(new Quaternionf(-90, 1, 0, 0));
        matrices.multiply(new Quaternionf(90, 0, 0, 1));
        ModelObjective.renderB(matrices, buffer, (float) entity.slide, 96f / 256f, 44f / 128f, 0.125f, 0.84375f, light, overlay);
        matrices.multiply(new Quaternionf(-90, 0, 0, 1));
        ModelObjective.renderB(matrices, buffer, (float) entity.slide, 32f / 256f, 44f / 128f, 0.625f, 0.84375f, light, overlay);
        matrices.multiply(new Quaternionf(90, 0, 1, 0));
        ModelObjective.renderB(matrices, buffer, (float) entity.slide, 96f / 256f, 108f / 128f, 0.625f, 0.84375f, light, overlay);
        matrices.multiply(new Quaternionf(90, 0, 1, 0));
        ModelObjective.renderB(matrices, buffer, (float) entity.slide, 160f / 256f, 44f / 128f, 0.625f, 0.84375f, light, overlay);
        matrices.multiply(new Quaternionf(90, 0, 1, 0));
        ModelObjective.renderB(matrices, buffer, (float) entity.slide, 224f / 256f, 108f / 128f, 0.625f, 0.84375f, light, overlay);
        matrices.multiply(new Quaternionf(90, 0, 1, 0));
        matrices.multiply(new Quaternionf(-90, 0, 0, 1));
        ModelObjective.renderB(matrices, buffer, (float) entity.slide, 224f / 256f, 44f / 128f, 0.625f, 0.84375f, light, overlay);
		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

    @Override
    public Box getRenderBoundingBox(TileEntitySigmaObjective blockEntity) {
        return Box.from(BlockBox.create(blockEntity.getPos().add(-1, -1, -1), blockEntity.getPos().add(2, 2, 2)));
    }
}

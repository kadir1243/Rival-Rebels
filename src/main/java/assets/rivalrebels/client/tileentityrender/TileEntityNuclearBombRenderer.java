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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.renderentity.RenderNuke;
import assets.rivalrebels.common.block.trap.BlockNuclearBomb;
import assets.rivalrebels.common.tileentity.TileEntityNuclearBomb;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
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
public class TileEntityNuclearBombRenderer implements BlockEntityRenderer<TileEntityNuclearBomb>, CustomRenderBoxExtension<TileEntityNuclearBomb> {
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etwacknuke);
    public TileEntityNuclearBombRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(TileEntityNuclearBomb entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
		matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
		int metadata = entity.getCachedState().get(BlockNuclearBomb.META);
        switch (metadata) {
            case 0 -> matrices.multiply(new Quaternionf(90, 1, 0, 0));
            case 1 -> matrices.multiply(new Quaternionf(-90, 1, 0, 0));
            case 2 -> matrices.multiply(new Quaternionf(180, 0, 1, 0));
            case 3 -> matrices.multiply(new Quaternionf(0, 0, 1, 0));
            case 4 -> matrices.multiply(new Quaternionf(-90, 0, 1, 0));
            case 5 -> matrices.multiply(new Quaternionf(90, 0, 1, 0));
        }
		RenderNuke.model.render(TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 65536;
    }

    @Override
    public Box getRenderBoundingBox(TileEntityNuclearBomb blockEntity) {
        return Box.from(BlockBox.create(blockEntity.getPos().add(-1, -1, -1), blockEntity.getPos().add(2, 2, 2)));
    }
}

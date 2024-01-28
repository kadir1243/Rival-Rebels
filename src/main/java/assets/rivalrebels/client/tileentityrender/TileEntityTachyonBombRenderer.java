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
import assets.rivalrebels.common.block.trap.BlockTachyonBomb;
import assets.rivalrebels.common.tileentity.TileEntityTachyonBomb;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityTachyonBombRenderer implements BlockEntityRenderer<TileEntityTachyonBomb> {
    public TileEntityTachyonBombRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(TileEntityTachyonBomb entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 1F, (float) entity.getPos().getZ() + 0.5F);
		matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
		int metadata = entity.getCachedState().get(BlockTachyonBomb.META);

		if (metadata == 2)
		{
			matrices.multiply(new Quaternion(180, 0, 1, 0));
		}

		if (metadata == 3)
		{
		}

		if (metadata == 4)
		{
			matrices.multiply(new Quaternion(-90, 0, 1, 0));
		}

		if (metadata == 5)
		{
			matrices.multiply(new Quaternion(90, 0, 1, 0));
		}
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.ettachyonbomb);
		//RenderTachyonBomb.bomb.renderAll();
		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

}

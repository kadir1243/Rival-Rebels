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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@Environment(EnvType.CLIENT)
public class TileEntityMeltdownRenderer implements BlockEntityRenderer<TileEntityMeltDown>, CustomRenderBoxExtension<TileEntityMeltDown> {
	public TileEntityMeltdownRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public void render(TileEntityMeltDown entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        float fsize = Mth.sin(entity.size);
		if (fsize <= 0) return;
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.size * 50));

        ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.5f, 1, 1, 1, 0.4f);

		matrices.mulPose(Axis.YP.rotationDegrees(entity.size * 50));

		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.6f, 1, 1, 1, 0.4f);

		matrices.popPose();

		ModelBlastSphere.renderModel(matrices, vertexConsumers, fsize * 5.9f, 1, 1, 1, 0.4f);

		matrices.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityMeltDown blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-2, -2, -2), blockEntity.getBlockPos().offset(3, 3, 3)));
    }
}

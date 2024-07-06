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
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.block.machine.BlockLoader;
import assets.rivalrebels.common.tileentity.TileEntityLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@Environment(EnvType.CLIENT)
public class TileEntityLoaderRenderer implements BlockEntityRenderer<TileEntityLoader>, CustomRenderBoxExtension<TileEntityLoader> {
    public static final Material TUBE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettube);
    public static final Material LOADER_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etloader);

    public TileEntityLoaderRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityLoader loader, float tickDelta, PoseStack pose, MultiBufferSource vertexConsumers, int light, int overlay) {
		pose.pushPose();
		pose.translate(0.5F, 0.5F, 0.5F);
        pose.mulPose(Axis.YP.rotationDegrees(loader.getBlockState().getValue(BlockLoader.FACING).toYRot()));

        VertexConsumer vertexConsumer = LOADER_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
		ModelLoader.renderA(vertexConsumer, pose, light, overlay);
		ModelLoader.renderB(vertexConsumer, pose, (float) loader.slide, light, overlay);
		pose.popPose();
        for (BlockEntity machine : loader.machines) {
			pose.pushPose();
			pose.translate(0.5F, 0.5F, 0.5F);
			int xdif = machine.getBlockPos().getX() - loader.getBlockPos().getX();
			int zdif = machine.getBlockPos().getZ() - loader.getBlockPos().getZ();
			pose.mulPose(Axis.YP.rotationDegrees((float) (-90 + (Math.atan2(xdif, zdif) / Math.PI) * 180F)));
			pose.translate(-1f, -0.40f, 0);
			pose.scale(0.5F, 0.15F, 0.15F);
			int dist = (int) Math.sqrt((xdif * xdif) + (zdif * zdif));
            VertexConsumer ettubeBuffer = TUBE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
            for (int d = 0; d < dist; d++) {
				pose.translate(2, 0, 0);
                ObjModels.tube.render(pose, ettubeBuffer, light, overlay);
			}
			pose.popPose();
		}
	}

    @Override
    @Environment(EnvType.CLIENT)
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityLoader blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, -1, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }
}

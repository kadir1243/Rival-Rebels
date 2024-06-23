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
import assets.rivalrebels.common.block.machine.BlockLoader;
import assets.rivalrebels.common.tileentity.TileEntityLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityLoaderRenderer implements BlockEntityRenderer<TileEntityLoader>, CustomRenderBoxExtension<TileEntityLoader> {
    public static final Material TUBE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettube);
    public static final Material LOADER_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etloader);
    private static final ModelFromObj tube = ModelFromObj.readObjFile("l.obj");

	public TileEntityLoaderRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityLoader entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY() + 0.5F, (float) entity.getBlockPos().getZ() + 0.5F);
		int var9 = entity.getBlockState().getValue(BlockLoader.META);
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

        VertexConsumer vertexConsumer = LOADER_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
        matrices.mulPose(new Quaternionf(var11, 0.0F, 1.0F, 0.0F));
		ModelLoader.renderA(vertexConsumer, matrices, light, overlay);
		ModelLoader.renderB(vertexConsumer, matrices, (float) entity.slide, light, overlay);
		matrices.popPose();
		for (int i = 0; i < entity.machines.size(); i++)
		{
			matrices.pushPose();
			matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY() + 0.5F, (float) entity.getBlockPos().getZ() + 0.5F);
			int xdif = entity.machines.get(i).getBlockPos().getX() - entity.getBlockPos().getX();
			int zdif = entity.machines.get(i).getBlockPos().getZ() - entity.getBlockPos().getZ();
			matrices.mulPose(new Quaternionf((float) (-90 + (Math.atan2(xdif, zdif) / Math.PI) * 180F), 0, 1, 0));
			matrices.translate(-1f, -0.40f, 0);
			matrices.scale(0.5F, 0.15F, 0.15F);
			int dist = (int) Math.sqrt((xdif * xdif) + (zdif * zdif));
            VertexConsumer ettubeBuffer = TUBE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
            for (int d = 0; d < dist; d++) {
				matrices.translate(2, 0, 0);
                tube.render(ettubeBuffer, light, overlay);
			}
			matrices.popPose();
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

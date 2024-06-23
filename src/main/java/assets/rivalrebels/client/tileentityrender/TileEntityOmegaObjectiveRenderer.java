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
import assets.rivalrebels.common.tileentity.TileEntityOmegaObjective;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityOmegaObjectiveRenderer implements BlockEntityRenderer<TileEntityOmegaObjective>, CustomRenderBoxExtension<TileEntityOmegaObjective> {
	public static final Material ETOMEGA = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etomegaobj);
    public TileEntityOmegaObjectiveRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public void render(TileEntityOmegaObjective entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
		matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY() + 0.5F, (float) entity.getBlockPos().getZ() + 0.5F);

        VertexConsumer buffer = ETOMEGA.buffer(vertexConsumers, RenderType::entitySolid);
        matrices.mulPose(new Quaternionf(90, 1, 0, 0));
		ModelObjective.renderA(matrices, buffer, light, overlay);
		matrices.mulPose(new Quaternionf(-90, 1, 0, 0));
		matrices.mulPose(new Quaternionf(90, 0, 0, 1));
		ModelObjective.renderB(matrices, buffer, (float) entity.slide, 96f / 256f, 44f / 128f, 0.125f, 0.84375f, light, overlay);
		matrices.mulPose(new Quaternionf(-90, 0, 0, 1));
		ModelObjective.renderB(matrices, buffer, (float) entity.slide, 32f / 256f, 44f / 128f, 0.625f, 0.84375f, light, overlay);
		matrices.mulPose(new Quaternionf(90, 0, 1, 0));
		ModelObjective.renderB(matrices, buffer, (float) entity.slide, 96f / 256f, 108f / 128f, 0.625f, 0.84375f, light, overlay);
		matrices.mulPose(new Quaternionf(90, 0, 1, 0));
		ModelObjective.renderB(matrices, buffer, (float) entity.slide, 160f / 256f, 44f / 128f, 0.625f, 0.84375f, light, overlay);
		matrices.mulPose(new Quaternionf(90, 0, 1, 0));
		ModelObjective.renderB(matrices, buffer, (float) entity.slide, 224f / 256f, 108f / 128f, 0.625f, 0.84375f, light, overlay);
		matrices.mulPose(new Quaternionf(90, 0, 1, 0));
		matrices.mulPose(new Quaternionf(-90, 0, 0, 1));
		ModelObjective.renderB(matrices, buffer, (float) entity.slide, 224f / 256f, 44f / 128f, 0.625f, 0.84375f, light, overlay);
		matrices.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityOmegaObjective blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }
}

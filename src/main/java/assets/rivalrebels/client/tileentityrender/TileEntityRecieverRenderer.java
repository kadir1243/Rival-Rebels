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
public class TileEntityRecieverRenderer implements BlockEntityRenderer<TileEntityReciever>, CustomRenderBoxExtension<TileEntityReciever> {
    public static final Material RECIEVER_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etreciever);
    public static final Material ETS_DRAGON = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etadsdragon);
    public static final ModelFromObj base = ModelFromObj.readObjFile("p.obj");
	public static final ModelFromObj arm = ModelFromObj.readObjFile("q.obj");
	public static final ModelFromObj adsdragon = ModelFromObj.readObjFile("r.obj");

	public TileEntityRecieverRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public void render(TileEntityReciever entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(entity.getBlockPos().getX() + 0.5, entity.getBlockPos().getY(), entity.getBlockPos().getZ() + 0.5);
		int m = entity.getBlockState().getValue(BlockReciever.META);
		int r = 0;

		if (m == 2) r = 0;
		if (m == 3) r = 180;
		if (m == 4) r = 90;
		if (m == 5) r = -90;

		matrices.pushPose();
		matrices.mulPose(new Quaternionf(r, 0, 1, 0));
        matrices.translate(0, 0, 0.5);
        VertexConsumer recieverTextureVertexConsumer = RECIEVER_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
        base.render(recieverTextureVertexConsumer, light, overlay);
		if (entity.hasWeapon) {
            matrices.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			matrices.mulPose(new Quaternionf((float) (entity.yaw - r), 0, 1, 0));
			arm.render(recieverTextureVertexConsumer, light, overlay);
            matrices.mulPose(new Quaternionf((float) entity.pitch, 1, 0, 0));
			adsdragon.render(ETS_DRAGON.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		}
		matrices.popPose();
		matrices.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityReciever blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }
}

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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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

@Environment(EnvType.CLIENT)
public class TileEntityNuclearBombRenderer implements BlockEntityRenderer<TileEntityNuclearBomb>, CustomRenderBoxExtension<TileEntityNuclearBomb> {
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etwacknuke);
    public TileEntityNuclearBombRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityNuclearBomb entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY() + 0.5F, (float) entity.getBlockPos().getZ() + 0.5F);
		matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
		int metadata = entity.getBlockState().getValue(BlockNuclearBomb.META);
        switch (metadata) {
            case 0 -> matrices.mulPose(Axis.XP.rotationDegrees(90));
            case 1 -> matrices.mulPose(Axis.XP.rotationDegrees(-90));
            case 2 -> matrices.mulPose(Axis.YP.rotationDegrees(180));
            case 3 -> matrices.mulPose(Axis.YP.rotationDegrees(0));
            case 4 -> matrices.mulPose(Axis.YP.rotationDegrees(-90));
            case 5 -> matrices.mulPose(Axis.YP.rotationDegrees(90));
        }
		RenderNuke.model.render(matrices, TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		matrices.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 65536;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityNuclearBomb blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }
}

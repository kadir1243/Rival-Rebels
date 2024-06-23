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
import assets.rivalrebels.client.renderentity.RenderTachyonBomb;
import assets.rivalrebels.common.block.trap.BlockTachyonBomb;
import assets.rivalrebels.common.tileentity.TileEntityTachyonBomb;
import com.mojang.blaze3d.vertex.PoseStack;
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
public class TileEntityTachyonBombRenderer implements BlockEntityRenderer<TileEntityTachyonBomb>, CustomRenderBoxExtension<TileEntityTachyonBomb> {
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettachyonbomb);
    public TileEntityTachyonBombRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityTachyonBomb entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY() + 1F, (float) entity.getBlockPos().getZ() + 0.5F);
        matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        int metadata = entity.getBlockState().getValue(BlockTachyonBomb.META);

        if (metadata == 2) {
            matrices.mulPose(new Quaternionf(180, 0, 1, 0));
        } else if (metadata == 3) {
        } else if (metadata == 4) {
            matrices.mulPose(new Quaternionf(-90, 0, 1, 0));
        } else if (metadata == 5) {
            matrices.mulPose(new Quaternionf(90, 0, 1, 0));
        }
        RenderTachyonBomb.bomb.render(TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
        matrices.popPose();
    }

    @Override
    public int getViewDistance() {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityTachyonBomb blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, 0, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }
}

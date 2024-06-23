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
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.common.block.machine.BlockLaptop;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityLaptopRenderer implements BlockEntityRenderer<TileEntityLaptop> {
    public static final Material LAPTOP_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etlaptop);
    public static final Material SCREEN_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etubuntu);

    public TileEntityLaptopRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityLaptop entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY(), (float) entity.getBlockPos().getZ() + 0.5F);
        short var11 = switch (entity.getBlockState().getValue(BlockLaptop.META)) {
            case 2 -> 180;
            case 3 -> 0;
            case 4 -> -90;
            case 5 -> 90;
            default -> 0;
        };
        matrices.mulPose(new Quaternionf(var11, 0.0F, 1.0F, 0.0F));
        ModelLaptop.renderModel(LAPTOP_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, (float) -entity.slide, light, overlay);
		ModelLaptop.renderScreen(SCREEN_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, (float) -entity.slide, light, overlay);
		matrices.popPose();
	}
}

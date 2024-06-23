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
package assets.rivalrebels.client.itemrenders;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.client.model.ModelReactor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ReactorRenderer implements DynamicItemRenderer {
    public static final Material REACTOR_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etreactor);
    public static final Material LAPTOP_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etlaptop);
    public static final Material SCREEN_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etscreen);
    private final ModelReactor mr = new ModelReactor();

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
        matrices.translate(0.5F, 1.1875F, 0.5F);
		ModelLaptop.renderModel(LAPTOP_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, 0, light, overlay);
		ModelLaptop.renderScreen(SCREEN_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, 0, light, overlay);
		matrices.popPose();
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		mr.renderModel(matrices, REACTOR_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		matrices.popPose();
	}
}


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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class GasRenderer implements DynamicItemRenderer {
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etflamethrower);
    private static final ModelFromObj ft = ModelFromObj.readObjFile("o.obj");

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.8f, 0.5f, -0.03f);
		// GlStateManager.rotatef(35, 0.0F, 0.0F, 1.0F);
        matrices.mulPose(new Quaternionf(-90, 1.0F, 0.0F, 0.0F));
        matrices.mulPose(new Quaternionf(160, 0.0F, 0.0F, 1.0F));
        matrices.scale(0.15f, 0.15f, 0.15f);
		// matrices.translate(0.3f, 0.05f, -0.1f);

		ft.render(TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);

		matrices.popPose();
	}
}


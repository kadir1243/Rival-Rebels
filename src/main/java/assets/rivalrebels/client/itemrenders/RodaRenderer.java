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
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RodaRenderer implements DynamicItemRenderer {
    public static final Material RUST_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etrust);

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0.5f, 0.5f, -0.03f);
		matrices.mulPose(Axis.ZP.rotationDegrees(35));
		matrices.mulPose(Axis.YP.rotationDegrees(90));
		matrices.scale(0.35f, 0.35f, 0.35f);
		if (!mode.firstPerson()) matrices.scale(-1, 1, 1);
		matrices.translate(0.2f, -0.55f, 0.1f);

        ObjModels.roda.render(matrices, RUST_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
		matrices.pushPose();
		ObjModels.roda.render(matrices, vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE), light, overlay);
		matrices.popPose();

		matrices.popPose();
	}
}


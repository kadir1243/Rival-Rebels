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
import assets.rivalrebels.client.model.ObjModels;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ReactorRenderer implements DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
        matrices.translate(0.5F, 1.1875F, 0.5F);
		ModelLaptop.renderModel(vertexConsumers, matrices, 0, light, overlay);
		ModelLaptop.renderScreen(vertexConsumers, RRIdentifiers.etscreen, matrices, 0, light, overlay);
		matrices.popPose();
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		ModelReactor.renderModel(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.etreactor)), light, overlay);
		matrices.popPose();
	}
}


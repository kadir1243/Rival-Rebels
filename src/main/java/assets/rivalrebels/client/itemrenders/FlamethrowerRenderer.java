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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FlamethrowerRenderer implements DynamicItemRenderer {
    public FlamethrowerRenderer() {
    }

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(35));
		matrices.translate(0.7f, 0.1f, 00f);
        matrices.mulPose(Axis.YP.rotationDegrees(270));
		matrices.scale(0.18f, 0.18f, 0.18f);
		// matrices.translate(0.3f, 0.05f, -0.1f);

        ObjModels.renderSolid(ObjModels.flamethrower, RRIdentifiers.etflamethrower, matrices, vertexConsumers, light, overlay);
		if (stack.isEnchanted()) {
            ObjModels.renderNoise(ObjModels.flamethrower, matrices, vertexConsumers, light, overlay);
		}

		matrices.popPose();
	}
}


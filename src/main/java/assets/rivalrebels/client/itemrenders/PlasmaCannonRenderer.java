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
import assets.rivalrebels.client.model.ModelRod;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class PlasmaCannonRenderer implements DynamicItemRenderer {
    public static final Material PLASMA_CANNON = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etplasmacannon);
    public static final Material HYDROD = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ethydrod);
	private final ModelRod md2;
	private final ModelRod md3 = new ModelRod();
	private static final ModelFromObj model = ModelFromObj.readObjFile("m.obj");

	public PlasmaCannonRenderer() {
		md2 = new ModelRod();
		md2.rendersecondcap = false;
    }

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(-0.1f, 0f, 0f);
		matrices.pushPose();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.mulPose(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
		matrices.pushPose();

        VertexConsumer plasmaCannonVertexConsumer = PLASMA_CANNON.buffer(vertexConsumers, RenderType::entitySolid);
        model.render(plasmaCannonVertexConsumer, light, overlay);
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        if (stack.isEnchanted()) {
			model.render(cellularNoise, light, overlay);
		}

		matrices.popPose();
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.mulPose(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.pushPose();
		matrices.mulPose(new Quaternionf(225, 0.0F, 0.0F, 1.0F));
		matrices.translate(-0.5f, 0.5f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
        VertexConsumer hydrodVertexConsumer = HYDROD.buffer(vertexConsumers, RenderType::entitySolid);
        md2.render(matrices, hydrodVertexConsumer, light, overlay);
		if (stack.isEnchanted()) {
			md2.render(matrices, cellularNoise, light, overlay);
		}
		matrices.popPose();
		matrices.popPose();

		matrices.pushPose();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.mulPose(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.pushPose();
		matrices.mulPose(new Quaternionf(247.5f, 0.0F, 0.0F, 1.0F));
		matrices.translate(-0.175f, 0.1f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
		md3.render(matrices, hydrodVertexConsumer, light, overlay);
		if (stack.isEnchanted()) {
			md3.render(matrices, cellularNoise, light, overlay);
		}
		matrices.popPose();
		matrices.popPose();
		matrices.popPose();
	}
}


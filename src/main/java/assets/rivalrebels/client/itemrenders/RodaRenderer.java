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
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;

public class RodaRenderer extends BuiltinModelItemRenderer
{
	private final ModelFromObj model;

	public RodaRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
        model = ModelFromObj.readObjFile("e.obj");
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etrust);
		matrices.push();
		matrices.translate(0.5f, 0.5f, -0.03f);
		matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternion(90, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.35f, 0.35f, 0.35f);
		if (!mode.isFirstPerson()) matrices.scale(-1, 1, 1);
		matrices.translate(0.2f, -0.55f, 0.1f);

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true));

        model.render(buffer);
		matrices.push();
		RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
		model.render(buffer);
		RenderSystem.disableBlend();
		matrices.pop();

		matrices.pop();
	}
}


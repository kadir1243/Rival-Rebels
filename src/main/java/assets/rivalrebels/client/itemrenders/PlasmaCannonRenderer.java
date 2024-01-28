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
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;

public class PlasmaCannonRenderer extends BuiltinModelItemRenderer {
	private final ModelRod md2;
	private final ModelRod md3;
	private final ModelFromObj model;

	public PlasmaCannonRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
		md2 = new ModelRod();
		md2.rendersecondcap = false;
		md3 = new ModelRod();
        model = ModelFromObj.readObjFile("m.obj");
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(-0.1f, 0f, 0f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etplasmacannon);
		matrices.push();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
		matrices.push();

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        model.render(buffer);
		if (stack.hasEnchantments()) {
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			model.render(buffer);
			RenderSystem.disableBlend();
		}

		matrices.pop();
		matrices.pop();

		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.ethydrod);
		matrices.push();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
		matrices.push();
		matrices.multiply(new Quaternion(225, 0.0F, 0.0F, 1.0F));
		matrices.translate(-0.5f, 0.5f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
		md2.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
            RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md2.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();
		matrices.pop();

		matrices.push();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
		matrices.push();
		matrices.multiply(new Quaternion(247.5f, 0.0F, 0.0F, 1.0F));
		matrices.translate(-0.175f, 0.1f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
		md3.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md3.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();
		matrices.pop();
		matrices.pop();
	}
}


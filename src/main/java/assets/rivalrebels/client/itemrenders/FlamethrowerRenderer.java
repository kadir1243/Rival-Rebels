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

public class FlamethrowerRenderer extends BuiltinModelItemRenderer
{
	private final ModelFromObj ft;

	public FlamethrowerRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
        ft = ModelFromObj.readObjFile("n.obj");
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etflamethrower);
		matrices.push();
        matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
		matrices.translate(0.7f, 0.1f, 00f);
        matrices.multiply(new Quaternion(270, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.18f, 0.18f, 0.18f);
		// matrices.translate(0.3f, 0.05f, -0.1f);

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true));
        ft.render(buffer);
		if (stack.hasEnchantments())
		{
            RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
            RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
            ft.render(buffer);
            RenderSystem.disableBlend();
		}

		matrices.pop();
	}
}


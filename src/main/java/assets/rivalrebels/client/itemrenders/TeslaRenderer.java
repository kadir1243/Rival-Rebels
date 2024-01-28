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

public class TeslaRenderer extends BuiltinModelItemRenderer
{
	private final ModelFromObj tesla;
	private final ModelFromObj dynamo;
	private int	spin;

	public TeslaRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
		tesla = ModelFromObj.readObjFile("i.obj");
		dynamo = ModelFromObj.readObjFile("j.obj");
	}

	public int getDegree(ItemStack item) {
        return item.getOrCreateNbt().getInt("dial");
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true));
        if (!stack.hasEnchantments()) {
			int degree = getDegree(stack);
			spin += 5 + (degree / 36f);
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.ettesla);
			matrices.push();
			matrices.translate(0.8f, 0.5f, -0.03f);
			matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
			matrices.multiply(new Quaternion(90, 0.0F, 1.0F, 0.0F));
			matrices.scale(0.12f, 0.12f, 0.12f);
			// matrices.translate(0.3f, 0.05f, -0.1f);

			tesla.render(buffer);
			matrices.multiply(new Quaternion(spin, 1.0F, 0.0F, 0.0F));
			dynamo.render(buffer);

			matrices.pop();
		} else {
			if (mode != ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND) matrices.pop();
			matrices.push();
            RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			matrices.scale(1.01f, 1.01f, 1.01f);
			matrices.multiply(new Quaternion(45.0f, 0, 1, 0));
			matrices.multiply(new Quaternion(10.0f, 0, 0, 1));
			matrices.scale(0.6f, 0.2f, 0.2f);
			matrices.translate(-0.99f, 0.5f, 0.0f);
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
            buffer.vertex(-1, -1, -1).texture(0, 0).next();
            buffer.vertex(-1, 1, -1).texture(1, 0).next();
            buffer.vertex(-1, 1, 1).texture(1, 1).next();
            buffer.vertex(-1, -1, 1).texture(0, 1).next();
            buffer.vertex(1, -1, -1).texture(0, 0).next();
            buffer.vertex(1, -1, 1).texture(0, 1).next();
            buffer.vertex(1, 1, 1).texture(1, 1).next();
            buffer.vertex(1, 1, -1).texture(1, 0).next();
            buffer.vertex(-1, -1, -1).texture(0, 0).next();
            buffer.vertex(-1, -1, 1).texture(0, 1).next();
            buffer.vertex(1, -1, 1).texture(3, 1).next();
            buffer.vertex(1, -1, -1).texture(3, 0).next();
            buffer.vertex(-1, 1, -1).texture(0, 0).next();
            buffer.vertex(1, 1, -1).texture(3, 0).next();
            buffer.vertex(1, 1, 1).texture(3, 1).next();
            buffer.vertex(-1, 1, 1).texture(0, 1).next();
            buffer.vertex(-1, -1, -1).texture(0, 0).next();
            buffer.vertex(1, -1, -1).texture(3, 0).next();
            buffer.vertex(1, 1, -1).texture(3, 1).next();
            buffer.vertex(-1, 1, -1).texture(0, 1).next();
            buffer.vertex(-1, -1, 1).texture(0, 0).next();
            buffer.vertex(-1, 1, 1).texture(0, 1).next();
            buffer.vertex(1, 1, 1).texture(3, 1).next();
            buffer.vertex(1, -1, 1).texture(3, 0).next();
            buffer.vertex(-1, -1, -1).texture(0, 0).next();
            buffer.vertex(-1, 1, -1).texture(1, 0).next();
            buffer.vertex(-1, 1, 1).texture(1, 1).next();
            buffer.vertex(-1, -1, 1).texture(0, 1).next();
            buffer.vertex(1, -1, -1).texture(0, 0).next();
            buffer.vertex(1, -1, 1).texture(0, 1).next();
            buffer.vertex(1, 1, 1).texture(1, 1).next();
            buffer.vertex(1, 1, -1).texture(1, 0).next();
            buffer.vertex(-1, -1, -1).texture(0, 0).next();
            buffer.vertex(-1, -1, 1).texture(0, 1).next();
            buffer.vertex(1, -1, 1).texture(3, 1).next();
            buffer.vertex(1, -1, -1).texture(3, 0).next();
            buffer.vertex(-1, 1, -1).texture(0, 0).next();
            buffer.vertex(1, 1, -1).texture(3, 0).next();
            buffer.vertex(1, 1, 1).texture(3, 1).next();
            buffer.vertex(-1, 1, 1).texture(0, 1).next();
            buffer.vertex(-1, -1, -1).texture(0, 0).next();
            buffer.vertex(1, -1, -1).texture(3, 0).next();
            buffer.vertex(1, 1, -1).texture(3, 1).next();
            buffer.vertex(-1, 1, -1).texture(0, 1).next();
            buffer.vertex(-1, -1, 1).texture(0, 0).next();
            buffer.vertex(-1, 1, 1).texture(0, 1).next();
            buffer.vertex(1, 1, 1).texture(3, 1).next();
            buffer.vertex(1, -1, 1).texture(3, 0).next();
            RenderSystem.disableBlend();
			matrices.pop();
			if (mode != ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND) matrices.push();
		}
	}

}

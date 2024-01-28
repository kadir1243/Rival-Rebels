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
import assets.rivalrebels.client.model.ModelRocketLauncherBody;
import assets.rivalrebels.client.model.ModelRocketLauncherHandle;
import assets.rivalrebels.client.model.ModelRocketLauncherTube;
import assets.rivalrebels.client.renderentity.RenderB83;
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

public class HackRocketLauncherRenderer extends BuiltinModelItemRenderer
{
	private final ModelRocketLauncherHandle	md2;
	private final ModelRocketLauncherBody		md3;
	private final ModelRocketLauncherTube		md4;

	public HackRocketLauncherRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
		md2 = new ModelRocketLauncherHandle();
		md3 = new ModelRocketLauncherBody();
		md4 = new ModelRocketLauncherTube();
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(0.4f, 0.35f, -0.03f);
		matrices.multiply(new Quaternion(-55, 0.0F, 0.0F, 1.0F));
		matrices.translate(0f, 0.05f, 0.05f);
		if (mode.isFirstPerson()) matrices.scale(1, 1, -1);
		matrices.push();
		matrices.translate(0.22f, -0.025f, 0f);
		matrices.multiply(new Quaternion(90, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etrocketlauncherhandle);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true));
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

		matrices.push();
		matrices.translate(-0.07f, 0.31f, 0f);
		matrices.multiply(new Quaternion(90, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternion(90, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.4f, 0.4f, 0.4f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.ethack202);
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

		float s = 0.0812f;

		matrices.push();
		matrices.translate(-0.07f + s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etrocketlaunchertube);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f + s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		// ---

		matrices.push();
		matrices.translate(-0.07f + s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f + s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments()) {
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		md4.render(matrices, buffer);
		if (stack.hasEnchantments()) {
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md4.render(matrices, buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		matrices.push();
		matrices.multiply(new Quaternion(-90, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.7f, 0.7f, 0.7f);
		matrices.translate(-0.5f, -0.1f, 0);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etb83);
		RenderB83.md.render(buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			RenderB83.md.render(buffer);
			RenderSystem.disableBlend();
		}
		matrices.pop();
		matrices.pop();
	}
}


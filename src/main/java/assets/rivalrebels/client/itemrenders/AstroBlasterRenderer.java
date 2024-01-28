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
import assets.rivalrebels.client.model.*;
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

import java.util.Random;

public class AstroBlasterRenderer extends BuiltinModelItemRenderer
{
	private final ModelAstroBlasterBarrel	md1;
	private final ModelAstroBlasterHandle	md2;
	private final ModelAstroBlasterBody	md3;
	private final ModelAstroBlasterBack	md4;
	private final ModelRod				md5;
	private float					pullback		= 0;
	private float					rotation		= 0;
	private boolean					isreloading		= false;
	private int						stage			= 0;
	private int						spin			= 0;
    private int						reloadcooldown	= 0;

	public AstroBlasterRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
		md1 = new ModelAstroBlasterBarrel();
		md2 = new ModelAstroBlasterHandle();
		md3 = new ModelAstroBlasterBody();
		md4 = new ModelAstroBlasterBack();
		md5 = new ModelRod();
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        spin++;
		if (stack.getRepairCost() >= 1)
		{
			spin += stack.getRepairCost() / 2.2;
		}
		spin %= 628;
		if (reloadcooldown > 0) reloadcooldown--;
		if (stack.getRepairCost() > 20 && reloadcooldown == 0) isreloading = true;
		if (isreloading)
		{
			if (stage == 0) if (pullback < 0.3) pullback += 0.03;
			else stage = 1;
			if (stage == 1) if (rotation < 90) rotation += 4.5;
			else stage = 2;
			if (stage == 2) if (pullback > 0) pullback -= 0.03;
			else
			{
				stage = 0;
				isreloading = false;
				reloadcooldown = 60;
				rotation = 0;
			}

		}
		matrices.push();
		matrices.translate(0.4f, 0.35f, -0.03f);
		matrices.multiply(new Quaternion(-55, 0.0F, 0.0F, 1.0F));
		matrices.translate(0f, -0.05f, 0.05f);

		matrices.push();
		matrices.translate(0f, 0.9f, 0f);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.eteinstenbarrel);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        md1.render(matrices, buffer);
		if (stack.hasEnchantments())
		{
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md1.render(matrices, buffer);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(0.22f, -0.025f, 0f);
		matrices.multiply(new Quaternion(90, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.eteinstenhandle);
		md2.render(matrices, buffer, light, overlay, 0, 0, 0, 0);
		if (stack.hasEnchantments()) {
			RenderSystem.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			md2.render(matrices, buffer, light, overlay, 0, 0, 0, 0);
			RenderSystem.disableBlend();
		}
		matrices.pop();

		// matrices.push();
		// matrices.translate(0f, 0.8f, 0f);
		// RenderSystem.rotate(180, 0.0F, 0.0F, 1.0F);
		// matrices.scale(0.9F, 4.5F, 0.9F);
		// md3.render(0.2f, 0.3f, 0.3f, 0.3f, 1f);
		// matrices.pop();

		matrices.push();
		matrices.translate(0f, 0.2f, 0f);
		matrices.scale(0.85F, 0.85F, 0.85F);
		MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.eteinstenback);
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
		matrices.translate(0f, -pullback, 0f);
		matrices.multiply(new Quaternion(rotation, 0.0F, 1.0F, 0.0F));
		matrices.push();
		matrices.translate(0.12f, 0.1f, 0.12f);
		matrices.multiply(new Quaternion(pullback * 270, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.3f, 0.7f, 0.3f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etredrod);
		md5.render(matrices, buffer);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.12f, 0.1f, 0.12f);
		matrices.multiply(new Quaternion(pullback * 270, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.3f, 0.7f, 0.3f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etredrod);
		md5.render(matrices, buffer);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.12f, 0.1f, -0.12f);
		matrices.multiply(new Quaternion(pullback * 270, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.3f, 0.7f, 0.3f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etredrod);
		md5.render(matrices, buffer);
		matrices.pop();

		matrices.push();
		matrices.translate(0.12f, 0.1f, -0.12f);
		matrices.multiply(new Quaternion(pullback * 270, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.3f, 0.7f, 0.3f);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etredrod);
		md5.render(matrices, buffer);
		matrices.pop();
		matrices.pop();

		matrices.push();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
		matrices.translate(0, 0.25f, 0);
		float segmentDistance = 0.1f;
		float distance = 0.5f;
		float radius = 0.01F;
		Random random = new Random();

        double AddedX = 0;
		double AddedZ = 0;
		double prevAddedX = 0;
		double prevAddedZ = 0;
		// double angle = 0;
		for (float AddedY = distance; AddedY >= 0; AddedY -= segmentDistance)
		{
			prevAddedX = AddedX;
			prevAddedZ = AddedZ;
			AddedX = (random.nextFloat() - 0.5) * 0.1f;
			AddedZ = (random.nextFloat() - 0.5) * 0.1f;
			double dist = Math.sqrt(AddedX * AddedX + AddedZ * AddedZ);
			if (dist != 0)
			{
				double tempAddedX = AddedX / dist;
				double tempAddedZ = AddedZ / dist;
				if (Math.abs(tempAddedX) < Math.abs(AddedX))
				{
					AddedX = tempAddedX;
				}
				if (Math.abs(tempAddedZ) < Math.abs(AddedZ))
				{
					AddedZ = tempAddedZ;
				}
				// angle = Math.atan2(tempAddedX, tempAddedZ);
			}
			if (AddedY <= 0)
			{
				AddedX = AddedZ = 0;
			}

			for (float o = 0; o <= radius; o += radius / 2f)
			{
				buffer.vertex(AddedX + o, AddedY, AddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(AddedX + o, AddedY, AddedZ + o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(AddedX - o, AddedY, AddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(AddedX + o, AddedY, AddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(AddedX - o, AddedY, AddedZ + o).color(1, 0, 0, 1).next();
				buffer.vertex(AddedX - o, AddedY, AddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).next();
				buffer.vertex(AddedX + o, AddedY, AddedZ + o).color(1, 0, 0, 1).next();
				buffer.vertex(AddedX - o, AddedY, AddedZ + o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).next();
				buffer.vertex(prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).color(1, 0, 0, 1).next();
			}
			// matrices.push();
			// RenderSystem.rotate(90f, 0.0F, 0.0F, 1.0F);
			// RenderSystem.rotate((float) angle, 0.0F, 1.0F, 0.0F);
			// float o = 0.075f;
			// float s = 0.1f;
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( + o, AddedY, - o);
			// tessellator.addVertex( + o, AddedY, + o);
			// tessellator.addVertex( + o, AddedY + s, + o);
			// tessellator.addVertex( + o, AddedY + s, - o);
			// tessellator.draw();
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( - o, AddedY, - o);
			// tessellator.addVertex( + o, AddedY, - o);
			// tessellator.addVertex( + o, AddedY + s, - o);
			// tessellator.addVertex( - o, AddedY + s, - o);
			// tessellator.draw();
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( - o, AddedY, + o);
			// tessellator.addVertex( - o, AddedY, - o);
			// tessellator.addVertex( - o, AddedY + s, - o);
			// tessellator.addVertex( - o, AddedY + s, + o);
			// tessellator.draw();
			// tessellator.startDrawingQuads();
			// tessellator.setColorRGBA_F(1, 0, 0, 1);
			// tessellator.addVertex( + o, AddedY, + o);
			// tessellator.addVertex( - o, AddedY, + o);
			// tessellator.addVertex( - o, AddedY + s, + o);
			// tessellator.addVertex( + o, AddedY + s, + o);
			// tessellator.draw();
			// matrices.pop();
		}

		matrices.pop();

		matrices.push();
		matrices.translate(0f, 0.8f, 0f);
		matrices.multiply(new Quaternion(180, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternion(spin, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.9F, 4.1F, 0.9F);
		md3.render(matrices, buffer, (float) (0.22f + (Math.sin(spin / 10) * 0.005)), 0.5f, 0f, 0f, 1f);
		matrices.pop();

		matrices.push();
		matrices.translate(0f, 0.8f, 0f);
		matrices.multiply(new Quaternion(180, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternion(-spin, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.9F, 4.1F, 0.9F);
		md3.render(matrices, buffer, (float) (0.22f + (Math.cos(-spin / 15) * 0.005)), 0.5f, 0f, 0f, 1f);
		matrices.pop();

		matrices.pop();
	}
}


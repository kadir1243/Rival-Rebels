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
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;

public class AstroBlasterRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier REDSTONE_ROD_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etredrod);
    public static final SpriteIdentifier EINSTEIN_HANDLE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.eteinstenhandle);
    public static final SpriteIdentifier EINSTEIN_BARREL_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.eteinstenbarrel);
    public static final SpriteIdentifier EINSTEIN_BACK_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.eteinstenback);
    private final ModelRod md5 = new ModelRod();
    private float pullback = 0;
    private float rotation = 0;
    private boolean isreloading = false;
    private int stage = 0;
    private int spin = 0;
    private int reloadcooldown = 0;

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        spin++;
        if (stack.getRepairCost() >= 1) {
            spin += stack.getRepairCost() / 2.2;
        }
        spin %= 628;
        if (reloadcooldown > 0) reloadcooldown--;
        if (stack.getRepairCost() > 20 && reloadcooldown == 0) isreloading = true;
        if (isreloading) {
            if (stage == 0) if (pullback < 0.3) pullback += 0.03;
            else stage = 1;
            if (stage == 1) if (rotation < 90) rotation += 4.5;
            else stage = 2;
            if (stage == 2) if (pullback > 0) pullback -= 0.03;
            else {
                stage = 0;
                isreloading = false;
                reloadcooldown = 60;
                rotation = 0;
            }

        }
        matrices.push();
        matrices.translate(0.4f, 0.35f, -0.03f);
        matrices.multiply(new Quaternionf(-55, 0.0F, 0.0F, 1.0F));
        matrices.translate(0f, -0.05f, 0.05f);

        matrices.push();
        matrices.translate(0f, 0.9f, 0f);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        VertexConsumer cellular_noise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        ModelAstroBlasterBarrel.render(matrices, EINSTEIN_BARREL_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
        if (stack.hasEnchantments()) {
            ModelAstroBlasterBarrel.render(matrices, cellular_noise, light, overlay);
        }
        matrices.pop();

        matrices.push();
        matrices.translate(0.22f, -0.025f, 0f);
        matrices.multiply(new Quaternionf(90, 0.0F, 0.0F, 1.0F));
        matrices.scale(0.03125f, 0.03125f, 0.03125f);
        ModelAstroBlasterHandle.render(matrices, EINSTEIN_HANDLE_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
        if (stack.hasEnchantments()) {
            ModelAstroBlasterHandle.render(matrices, cellular_noise, light, overlay);
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
        ModelAstroBlasterBack.render(matrices, EINSTEIN_BACK_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
        if (stack.hasEnchantments()) {
            ModelAstroBlasterBack.render(matrices, cellular_noise, light, overlay);
        }
        matrices.pop();

        matrices.push();
        matrices.translate(0f, -pullback, 0f);
        matrices.multiply(new Quaternionf(rotation, 0.0F, 1.0F, 0.0F));
        matrices.push();
        VertexConsumer redstoneRodTextureVertexConsumer = REDSTONE_ROD_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        matrices.translate(0.12f, 0.1f, 0.12f);
        matrices.multiply(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.pop();

        matrices.push();
        matrices.translate(-0.12f, 0.1f, 0.12f);
        matrices.multiply(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.pop();

        matrices.push();
        matrices.translate(-0.12f, 0.1f, -0.12f);
        matrices.multiply(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.pop();

        matrices.push();
        matrices.translate(0.12f, 0.1f, -0.12f);
        matrices.multiply(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.pop();
        matrices.pop();

        matrices.push();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
        matrices.translate(0, 0.25f, 0);
        float segmentDistance = 0.1f;
        float distance = 0.5f;
        float radius = 0.01F;
        Random random = Random.create();

        double AddedX = 0;
        double AddedZ = 0;
        double prevAddedX;
        double prevAddedZ;
        // double angle = 0;
        for (float AddedY = distance; AddedY >= 0; AddedY -= segmentDistance) {
            prevAddedX = AddedX;
            prevAddedZ = AddedZ;
            AddedX = (random.nextFloat() - 0.5) * 0.1f;
            AddedZ = (random.nextFloat() - 0.5) * 0.1f;
            double dist = Math.sqrt(AddedX * AddedX + AddedZ * AddedZ);
            if (dist != 0) {
                double tempAddedX = AddedX / dist;
                double tempAddedZ = AddedZ / dist;
                if (Math.abs(tempAddedX) < Math.abs(AddedX)) {
                    AddedX = tempAddedX;
                }
                if (Math.abs(tempAddedZ) < Math.abs(AddedZ)) {
                    AddedZ = tempAddedZ;
                }
                // angle = Math.atan2(tempAddedX, tempAddedZ);
            }
            if (AddedY <= 0) {
                AddedX = AddedZ = 0;
            }

            for (float o = 0; o <= radius; o += radius / 2f) {
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
        matrices.multiply(new Quaternionf(180, 0.0F, 0.0F, 1.0F));
        matrices.multiply(new Quaternionf(spin, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(matrices, buffer, (float) (0.22f + (Math.sin(spin / 10) * 0.005)), 0.5f, 0f, 0f, 1f);
        matrices.pop();

        matrices.push();
        matrices.translate(0f, 0.8f, 0f);
        matrices.multiply(new Quaternionf(180, 0.0F, 0.0F, 1.0F));
        matrices.multiply(new Quaternionf(-spin, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(matrices, buffer, (float) (0.22f + (Math.cos(-spin / 15) * 0.005)), 0.5f, 0f, 0f, 1f);
        matrices.pop();

        matrices.pop();
    }
}


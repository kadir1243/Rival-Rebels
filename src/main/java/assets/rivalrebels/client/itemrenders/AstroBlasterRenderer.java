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
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class AstroBlasterRenderer implements DynamicItemRenderer {
    public static final Material REDSTONE_ROD_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etredrod);
    public static final Material EINSTEIN_HANDLE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.eteinstenhandle);
    public static final Material EINSTEIN_BARREL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.eteinstenbarrel);
    public static final Material EINSTEIN_BACK_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.eteinstenback);
    private final ModelRod md5 = new ModelRod();
    private float pullback = 0;
    private float rotation = 0;
    private boolean isreloading = false;
    private int stage = 0;
    private int spin = 0;
    private int reloadcooldown = 0;

    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        spin++;
        if (stack.get(DataComponents.REPAIR_COST) >= 1) {
            spin += stack.get(DataComponents.REPAIR_COST) / 2.2;
        }
        spin %= 628;
        if (reloadcooldown > 0) reloadcooldown--;
        if (stack.get(DataComponents.REPAIR_COST) > 20 && reloadcooldown == 0) isreloading = true;
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
        matrices.pushPose();
        matrices.translate(0.4f, 0.35f, -0.03f);
        matrices.mulPose(new Quaternionf(-55, 0.0F, 0.0F, 1.0F));
        matrices.translate(0f, -0.05f, 0.05f);

        matrices.pushPose();
        matrices.translate(0f, 0.9f, 0f);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.lightning());
        VertexConsumer cellular_noise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        ModelAstroBlasterBarrel.render(matrices, EINSTEIN_BARREL_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
        if (stack.isEnchanted()) {
            ModelAstroBlasterBarrel.render(matrices, cellular_noise, light, overlay);
        }
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.22f, -0.025f, 0f);
        matrices.mulPose(new Quaternionf(90, 0.0F, 0.0F, 1.0F));
        matrices.scale(0.03125f, 0.03125f, 0.03125f);
        ModelAstroBlasterHandle.render(matrices, EINSTEIN_HANDLE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
        if (stack.isEnchanted()) {
            ModelAstroBlasterHandle.render(matrices, cellular_noise, light, overlay);
        }
        matrices.popPose();

        // matrices.push();
        // matrices.translate(0f, 0.8f, 0f);
        // RenderSystem.rotate(180, 0.0F, 0.0F, 1.0F);
        // matrices.scale(0.9F, 4.5F, 0.9F);
        // md3.render(0.2f, 0.3f, 0.3f, 0.3f, 1f);
        // matrices.pop();

        matrices.pushPose();
        matrices.translate(0f, 0.2f, 0f);
        matrices.scale(0.85F, 0.85F, 0.85F);
        ModelAstroBlasterBack.render(matrices, EINSTEIN_BACK_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, overlay);
        if (stack.isEnchanted()) {
            ModelAstroBlasterBack.render(matrices, cellular_noise, light, overlay);
        }
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0f, -pullback, 0f);
        matrices.mulPose(new Quaternionf(rotation, 0.0F, 1.0F, 0.0F));
        matrices.pushPose();
        VertexConsumer redstoneRodTextureVertexConsumer = REDSTONE_ROD_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
        matrices.translate(0.12f, 0.1f, 0.12f);
        matrices.mulPose(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(-0.12f, 0.1f, 0.12f);
        matrices.mulPose(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(-0.12f, 0.1f, -0.12f);
        matrices.mulPose(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.12f, 0.1f, -0.12f);
        matrices.mulPose(new Quaternionf(pullback * 270, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.3f, 0.7f, 0.3f);
        md5.render(matrices, redstoneRodTextureVertexConsumer, light, overlay);
        matrices.popPose();
        matrices.popPose();

        matrices.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        matrices.translate(0, 0.25f, 0);
        float segmentDistance = 0.1f;
        float distance = 0.5f;
        float radius = 0.01F;
        RandomSource random = RandomSource.create();

        float AddedX = 0;
        float AddedZ = 0;
        float prevAddedX;
        float prevAddedZ;
        // double angle = 0;
        for (float AddedY = distance; AddedY >= 0; AddedY -= segmentDistance) {
            prevAddedX = AddedX;
            prevAddedZ = AddedZ;
            AddedX = (random.nextFloat() - 0.5F) * 0.1f;
            AddedZ = (random.nextFloat() - 0.5F) * 0.1f;
            float dist = Mth.sqrt(AddedX * AddedX + AddedZ * AddedZ);
            if (dist != 0) {
                float tempAddedX = AddedX / dist;
                float tempAddedZ = AddedZ / dist;
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
                buffer.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ - o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ + o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).setColor(1, 0, 0, 1);

                buffer.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ - o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ - o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ - o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).setColor(1, 0, 0, 1);

                buffer.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ + o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ - o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ - o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).setColor(1, 0, 0, 1);

                buffer.addVertex(matrices.last(), AddedX + o, AddedY, AddedZ + o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), AddedX - o, AddedY, AddedZ + o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX - o, AddedY + segmentDistance, prevAddedZ + o).setColor(1, 0, 0, 1);
                buffer.addVertex(matrices.last(), prevAddedX + o, AddedY + segmentDistance, prevAddedZ + o).setColor(1, 0, 0, 1);
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

        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0f, 0.8f, 0f);
        matrices.mulPose(new Quaternionf(180, 0.0F, 0.0F, 1.0F));
        matrices.mulPose(new Quaternionf(spin, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(matrices, buffer, (float) (0.22f + (Math.sin(spin / 10) * 0.005)), 0.5f, 0f, 0f, 1f);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0f, 0.8f, 0f);
        matrices.mulPose(new Quaternionf(180, 0.0F, 0.0F, 1.0F));
        matrices.mulPose(new Quaternionf(-spin, 0.0F, 1.0F, 0.0F));
        matrices.scale(0.9F, 4.1F, 0.9F);
        ModelAstroBlasterBody.render(matrices, buffer, (float) (0.22f + (Math.cos(-spin / 15) * 0.005)), 0.5f, 0f, 0f, 1f);
        matrices.popPose();

        matrices.popPose();
    }
}


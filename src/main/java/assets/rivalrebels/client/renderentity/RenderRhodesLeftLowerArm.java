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
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.EntityRhodesLeftLowerArm;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

@Environment(EnvType.CLIENT)
public class RenderRhodesLeftLowerArm extends EntityRenderer<EntityRhodesLeftLowerArm>
{
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RenderRhodes.texture);
    public RenderRhodesLeftLowerArm(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityRhodesLeftLowerArm entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());
        matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        matrices.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        matrices.translate(0, 4f, 0);
        matrices.scale(-1, 1, 1);
        VertexConsumer vertexConsumer = TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
        ObjModels.lowerarm.render(matrices, vertexConsumer, entity.getColorRGBA(), light, OverlayTexture.NO_OVERLAY);
        ObjModels.rhodes_rocketlauncher.render(matrices, vertexConsumer, entity.getColorRGBA(), light, OverlayTexture.NO_OVERLAY);
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityRhodesLeftLowerArm entity) {
        return RenderRhodes.texture;
    }
}

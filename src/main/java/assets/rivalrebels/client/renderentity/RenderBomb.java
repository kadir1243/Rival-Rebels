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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.model.ModelNuclearBomb;
import assets.rivalrebels.common.entity.EntityBomb;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class RenderBomb extends EntityRenderer<EntityBomb> {
    public static final Material NUKE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etnuke);
    public RenderBomb(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityBomb entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.mulPose(new Quaternionf(entity.getYRot() - 90.0f, 0.0F, 1.0F, 0.0F));
        matrices.mulPose(new Quaternionf(entity.getXRot() - 90.0f, 0.0F, 0.0F, 1.0F));
        if (entity.getDeltaMovement().x()==0&& entity.getDeltaMovement().z()==0)
        {
            if (entity.getDeltaMovement().y() == 1)
            {
                ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount * 0.2f, 0.25f, 0.25f, 1.0f, 0.75f);
            }
            else if (entity.getDeltaMovement().y() == 0)
            {
                ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount * 0.2f, 0.8f, 0.8f, 1f, 0.75f);
            }
        }
        else {
            matrices.scale(0.25f, 0.5f, 0.25f);
            ModelNuclearBomb.renderModel(matrices, NUKE_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, true);
        }
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBomb entity) {
        return null;
    }
}

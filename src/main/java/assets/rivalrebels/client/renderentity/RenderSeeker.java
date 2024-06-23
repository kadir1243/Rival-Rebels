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
import assets.rivalrebels.client.model.ModelRocket;
import assets.rivalrebels.common.entity.EntitySeekB83;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RenderSeeker extends EntityRenderer<EntitySeekB83>
{
    public static final Material ROCKET_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etrocketseek202);
	public RenderSeeker(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntitySeekB83 entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(new Quaternionf(entity.getYRot() - 90.0f, 0.0F, 1.0F, 0.0F));
		matrices.mulPose(new Quaternionf(entity.getXRot() - 90.0f, 0.0F, 0.0F, 1.0F));
		matrices.scale(2.0f, 2.0f, 2.0f);
		ModelRocket.render(matrices, ROCKET_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), true, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntitySeekB83 entity) {
        return RRIdentifiers.etrocketseek202;
    }
}

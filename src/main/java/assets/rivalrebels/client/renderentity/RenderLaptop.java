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
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.common.entity.EntityLaptop;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;

public class RenderLaptop extends EntityRenderer<EntityLaptop> {
    public static final Material LAPTOP_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etlaptop);
    public static final Material SCREEN_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etubuntu);
    public RenderLaptop(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityLaptop entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
		matrices.mulPose(new Quaternionf(180 - entity.getYRot(), 0, 1, 0));
		ModelLaptop.renderModel(LAPTOP_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, (float) -entity.slide, light, OverlayTexture.NO_OVERLAY);
		ModelLaptop.renderScreen(SCREEN_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), matrices, (float) -entity.slide, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityLaptop entity) {
        return null;
    }
}

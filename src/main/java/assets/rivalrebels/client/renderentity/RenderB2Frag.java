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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.entity.EntityB2Frag;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class RenderB2Frag extends EntityRenderer<EntityB2Frag> {
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etb2spirit);
    private static final ModelFromObj md1 = ModelFromObj.readObjFile("f.obj");
	private static final ModelFromObj md2 = ModelFromObj.readObjFile("g.obj");

    static {
        md1.scale(3, 3, 3);
        md2.scale(3, 3, 3);
    }

	public RenderB2Frag(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB2Frag entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(new Quaternionf(entity.getYRot(), 0.0F, 1.0F, 0.0F));
		matrices.mulPose(new Quaternionf(entity.getXRot(), 0.0F, 0.0F, 1.0F));

        if (entity.type == 0) md1.render(TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, OverlayTexture.NO_OVERLAY);
		else if (entity.type == 1) md2.render(TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityB2Frag entity) {
        return RRIdentifiers.etb2spirit;
    }
}

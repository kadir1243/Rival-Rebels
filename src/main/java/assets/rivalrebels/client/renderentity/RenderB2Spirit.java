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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.EntityB2Spirit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

@Environment(EnvType.CLIENT)
public class RenderB2Spirit extends EntityRenderer<EntityB2Spirit>
{
    public static final Material B2_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etb2spirit);
    public static final Material TUPOLEV_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettupolev);

    public RenderB2Spirit(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB2Spirit entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
		matrices.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
			matrices.scale(3.0f, 3.0f, 3.0f);
			ObjModels.shuttle.render(matrices, B2_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light);
		} else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
			ObjModels.tupolev.render(matrices, TUPOLEV_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light);
		} else {
			ObjModels.b2ForSpirit.render(matrices, B2_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), light);
		}
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityB2Spirit entity) {
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
            return RRIdentifiers.etb2spirit;
        } else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
            return RRIdentifiers.ettupolev;
        } else {
            return RRIdentifiers.etb2spirit;
        }
    }
}

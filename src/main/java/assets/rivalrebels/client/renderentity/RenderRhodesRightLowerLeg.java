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

import assets.rivalrebels.common.entity.EntityRhodesRightLowerLeg;
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
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class RenderRhodesRightLowerLeg extends EntityRenderer<EntityRhodesRightLowerLeg>
{
    public static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RenderRhodes.texture);
    public RenderRhodesRightLowerLeg(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityRhodesRightLowerLeg entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());
        float[] colors = RenderRhodes.colors;
		matrices.mulPose(new Quaternionf(entity.getYRot(), 0, 1, 0));
		matrices.mulPose(new Quaternionf(entity.getXRot(), 1, 0, 0));
		matrices.translate(-3, 4f, 0);
		RenderRhodes.shin.render(TEXTURE.buffer(vertexConsumers, RenderType::entitySolid), new Vector3f(colors[entity.getColor()*3], colors[entity.getColor()*3+1], colors[entity.getColor()*3+2]), light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityRhodesRightLowerLeg entity) {
        return RenderRhodes.texture;
    }

}

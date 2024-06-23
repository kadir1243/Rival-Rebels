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
import assets.rivalrebels.client.objfileloader.WavefrontObject;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.common.entity.EntityNuke;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class RenderNuke extends EntityRenderer<EntityNuke> {
    public static final RenderType RENDER_LAYER = RenderType.entitySolid(RRIdentifiers.etwacknuke);
    public static WavefrontObject model = RenderHelper.getModel("wacknuke");

	public RenderNuke(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public void render(EntityNuke entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        matrices.mulPose(new Quaternionf(entity.getYRot() - 90.0f, 0.0F, 1.0F, 0.0F));
        matrices.mulPose(new Quaternionf(entity.getXRot() - 90.0f, 0.0F, 0.0F, 1.0F));
        model.render(vertexConsumers.getBuffer(RENDER_LAYER), light, OverlayTexture.NO_OVERLAY);
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityNuke entity) {
        return RRIdentifiers.etwacknuke;
    }
}

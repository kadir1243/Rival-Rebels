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
import assets.rivalrebels.common.entity.EntityTachyonBomb;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class RenderTachyonBomb extends EntityRenderer<EntityTachyonBomb> {
    public static final WavefrontObject bomb = RenderHelper.getModel("t");
    private static final RenderLayer RENDER_LAYER = RenderLayer.getEntitySolid(RRIdentifiers.ettachyonbomb);

    public RenderTachyonBomb(EntityRendererFactory.Context manager) {
        super(manager);
    }

    @Override
    public void render(EntityTachyonBomb entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
        matrices.multiply(new Quaternionf(entity.getYaw() - 90.0f, 0.0F, 1.0F, 0.0F));
        // GlStateManager.rotatef(90.0f, 1.0F, 0.0F, 0.0F);
        matrices.multiply(new Quaternionf(entity.getPitch(), 0.0F, 0.0F, 1.0F));
        bomb.render(vertexConsumers.getBuffer(RENDER_LAYER), light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityTachyonBomb entity) {
        return RRIdentifiers.ettachyonbomb;
    }
}

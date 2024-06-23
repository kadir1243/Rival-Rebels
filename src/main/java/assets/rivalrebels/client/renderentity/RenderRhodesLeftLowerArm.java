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

import assets.rivalrebels.common.entity.EntityRhodesLeftLowerArm;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class RenderRhodesLeftLowerArm extends EntityRenderer<EntityRhodesLeftLowerArm>
{
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RenderRhodes.texture);
    public RenderRhodesLeftLowerArm(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityRhodesLeftLowerArm entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());
        float[] colors = RenderRhodes.colors;
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));
        matrices.translate(0, 4f, 0);
        matrices.scale(-1, 1, 1);
        VertexConsumer vertexConsumer = TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        RenderRhodes.lowerarm.render(vertexConsumer, new Vector3f(colors[entity.getColor()*3], colors[entity.getColor()*3+1], colors[entity.getColor()*3+2]), light, OverlayTexture.DEFAULT_UV);
        RenderRhodes.rocketlauncher.render(vertexConsumer, new Vector3f(colors[entity.getColor()*3], colors[entity.getColor()*3+1], colors[entity.getColor()*3+2]), light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityRhodesLeftLowerArm entity) {
        return RenderRhodes.texture;
    }
}

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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class RenderBomb extends EntityRenderer<EntityBomb> {
    public static final SpriteIdentifier NUKE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etnuke);
    public RenderBomb(EntityRendererFactory.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityBomb entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(new Quaternionf(entity.getYaw() - 90.0f, 0.0F, 1.0F, 0.0F));
        matrices.multiply(new Quaternionf(entity.getPitch() - 90.0f, 0.0F, 0.0F, 1.0F));
        if (entity.getVelocity().getX()==0&& entity.getVelocity().getZ()==0)
        {
            if (entity.getVelocity().getY() == 1)
            {
                ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.age * 0.2f, 0.25f, 0.25f, 1.0f, 0.75f);
            }
            else if (entity.getVelocity().getY() == 0)
            {
                ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.age * 0.2f, 0.8f, 0.8f, 1f, 0.75f);
            }
        }
        else {
            matrices.scale(0.25f, 0.5f, 0.25f);
            ModelNuclearBomb.renderModel(matrices, NUKE_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, true);
        }
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityBomb entity) {
        return null;
    }
}

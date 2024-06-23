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
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class RenderRhodesRightLowerLeg extends EntityRenderer<EntityRhodesRightLowerLeg>
{
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RenderRhodes.texture);
    public RenderRhodesRightLowerLeg(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityRhodesRightLowerLeg entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());
        float[] colors = RenderRhodes.colors;
		matrices.multiply(new Quaternionf(entity.getYaw(), 0, 1, 0));
		matrices.multiply(new Quaternionf(entity.getPitch(), 1, 0, 0));
		matrices.translate(-3, 4f, 0);
		RenderRhodes.shin.render(TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), new Vector3f(colors[entity.getColor()*3], colors[entity.getColor()*3+1], colors[entity.getColor()*3+2]), light, OverlayTexture.DEFAULT_UV);
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityRhodesRightLowerLeg entity) {
        return RenderRhodes.texture;
    }

}

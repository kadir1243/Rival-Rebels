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
import assets.rivalrebels.common.entity.EntityHackB83;
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
public class RenderHackB83 extends EntityRenderer<EntityHackB83>
{
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etb83);
    public RenderHackB83(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityHackB83 entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
		matrices.multiply(new Quaternionf(entity.getYaw() - 90.0f, 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternionf(entity.getPitch() - 180.0f, 0.0F, 0.0F, 1.0F));
		RenderB83.md.render(TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light);
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityHackB83 entity) {
        return RRIdentifiers.etb83;
    }
}

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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.entity.EntityB2Spirit;
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
public class RenderB2Spirit extends EntityRenderer<EntityB2Spirit>
{
    public static final SpriteIdentifier B2_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etb2spirit);
    public static final SpriteIdentifier TUPOLEV_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.ettupolev);
    private static final ModelFromObj	b2 = ModelFromObj.readObjFile("d.obj");
	public static final ModelFromObj	shuttle = ModelFromObj.readObjFile("shuttle.obj");
	public static final ModelFromObj	tupolev = ModelFromObj.readObjFile("tupolev.obj");
    static {
        b2.scale(3, 3, 3);
    }

    public RenderB2Spirit(EntityRendererFactory.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB2Spirit entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.multiply(new Quaternionf(entity.getYaw(), 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternionf(entity.getPitch(), 1.0F, 0.0F, 0.0F));
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
			matrices.scale(3.0f, 3.0f, 3.0f);
			shuttle.render(B2_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light);
		} else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
			tupolev.render(TUPOLEV_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light);
		} else {
			b2.render(B2_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light);
		}
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityB2Spirit entity) {
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
            return RRIdentifiers.etb2spirit;
        } else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
            return RRIdentifiers.ettupolev;
        } else {
            return RRIdentifiers.etb2spirit;
        }
    }
}

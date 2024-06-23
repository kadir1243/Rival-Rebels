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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class RenderB2Frag extends EntityRenderer<EntityB2Frag> {
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etb2spirit);
    private static final ModelFromObj md1 = ModelFromObj.readObjFile("f.obj");
	private static final ModelFromObj md2 = ModelFromObj.readObjFile("g.obj");

    static {
        md1.scale(3, 3, 3);
        md2.scale(3, 3, 3);
    }

	public RenderB2Frag(EntityRendererFactory.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB2Frag entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.multiply(new Quaternionf(entity.getYaw(), 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternionf(entity.getPitch(), 0.0F, 0.0F, 1.0F));

        if (entity.type == 0) md1.render(TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, OverlayTexture.DEFAULT_UV);
		else if (entity.type == 1) md2.render(TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, OverlayTexture.DEFAULT_UV);
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityB2Frag entity) {
        return RRIdentifiers.etb2spirit;
    }
}

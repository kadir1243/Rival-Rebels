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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.model.ModelTsarBlast;
import assets.rivalrebels.common.entity.EntityTsarBlast;
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

public class RenderTheoreticalTsarBlast extends EntityRenderer<EntityTsarBlast> {
    public static final SpriteIdentifier BLACK_TSAR_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etblacktsar);
    private final ModelTsarBlast model;

    public RenderTheoreticalTsarBlast(EntityRendererFactory.Context manager) {
        super(manager);
        model = new ModelTsarBlast();
	}

    @Override
    public void render(EntityTsarBlast entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
		entity.time++;
		double radius = (((entity.getVelocity().getX() * 10) - 1) * ((entity.getVelocity().getX() * 10) - 1) * 2) + RivalRebels.tsarBombaStrength;
        matrices.push();
		if (entity.time < 60) {
			double elev = entity.time / 5f;
			matrices.translate(x, y + elev, z);
            ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.time * RRConfig.CLIENT.getShroomScale(), 1, 1, 1, 1);
		}
		else if (entity.time < 300 && radius - RivalRebels.tsarBombaStrength > 9)
		{
			double elev = (entity.time - 60f) / 4f;
			matrices.translate(x, y + elev, z);
			matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
			matrices.push();
			matrices.multiply(new Quaternionf((float) (elev * 2), 0, 1, 0));
			matrices.multiply(new Quaternionf((float) (elev * 3), 1, 0, 0));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) elev, 1, 0.25f, 0, 1f);
			matrices.pop();
			matrices.push();
			matrices.multiply(new Quaternionf((float) (elev * -2), 0, 1, 0));
			matrices.multiply(new Quaternionf((float) (elev * 4), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
			matrices.pop();
			matrices.push();
			matrices.multiply(new Quaternionf((float) (elev * -3), 1, 0, 0));
			matrices.multiply(new Quaternionf((float) (elev * 2), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.4f), 1, 0, 0, 1f);
			matrices.pop();
			matrices.push();
			matrices.multiply(new Quaternionf((float) (elev * -1), 0, 1, 0));
			matrices.multiply(new Quaternionf((float) (elev * 3), 0, 0, 1));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.6f), 1, 1, 0, 1);
			matrices.pop();
		}
		else
		{
			matrices.translate(x, y + 10 + ((entity.getVelocity().getX() - 0.1d) * 14.14213562), z);
			matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
			matrices.scale((float) (radius * 0.116f), (float) (radius * 0.065f), (float) (radius * 0.116f));
			matrices.scale(0.8f, 0.8f, 0.8f);
			model.render(matrices, BLACK_TSAR_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, OverlayTexture.DEFAULT_UV);
		}
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityTsarBlast entity) {
        return null;
    }

}

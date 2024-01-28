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

import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntityPlasmoid;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class RenderPlasmoid extends EntityRenderer<EntityPlasmoid>
{
	ModelBlastSphere	model	= new ModelBlastSphere();

    public RenderPlasmoid(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityPlasmoid entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        matrices.multiply(new Quaternion(entity.getYaw() - 90.0f, 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternion(entity.getPitch() - 90.0f, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.4f, 2.5f, 0.4f);
		matrices.push();

		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.rotation));
		model.renderModel(matrices, buffer, 0.4f, 0.65f, 0.55f, 0.95f, 0.9f);
		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.rotation));
		model.renderModel(matrices, buffer, 0.6f, 0.65f, 0.55f, 0.95f, 0.9f);
		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.rotation));
		model.renderModel(matrices, buffer, 0.8f, 0.65f, 0.55f, 0.95f, 0.9f);
		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.rotation));
		model.renderModel(matrices, buffer, 1f, 0.65f, 0.55f, 0.95f, 0.9f);
		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.rotation));
		model.renderModel(matrices, buffer, 1.2f, 0.65f, 0.55f, 0.95f, 0.9f);
		matrices.pop();
		matrices.pop();
		matrices.pop();
		matrices.pop();
		matrices.pop();
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityPlasmoid entity) {
        return null;
    }
}

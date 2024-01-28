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
import assets.rivalrebels.common.entity.EntitySphereBlast;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class RenderSphereBlast extends EntityRenderer<EntitySphereBlast>
{
	private final ModelBlastSphere modelsphere;

	public RenderSphereBlast(EntityRendererFactory.Context manager) {
        super(manager);
		modelsphere = new ModelBlastSphere();
	}

    @Override
    public void render(EntitySphereBlast entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        entity.time++;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        matrices.push();
        double elev = ((MathHelper.sin(entity.time / 40f) + 1.5f) * 10);
        matrices.translate(entity.getX(), entity.getY() + elev, entity.getZ());
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) (elev * 2)));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) (elev * 3)));
        modelsphere.renderModel(matrices, buffer, (float) elev, 1, 0.25f, 0, 1f);
        matrices.pop();
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) (elev * -2)));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) (elev * 4)));
        modelsphere.renderModel(matrices, buffer, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
        matrices.pop();
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) (elev * -3)));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) (elev * 2)));
        modelsphere.renderModel(matrices, buffer, (float) (elev - 0.4f), 1, 0, 0, 1f);
        matrices.pop();
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) (elev * -1)));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) (elev * 3)));
        modelsphere.renderModel(matrices, buffer, (float) (elev - 0.6f), 1, 1, 0, 1);
        matrices.pop();
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntitySphereBlast entity) {
        return null;
    }
}

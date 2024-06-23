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

import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.common.entity.EntityRoddiskRep;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RenderRoddiskRep extends EntityRenderer<EntityRoddiskRep>
{
	private int	er	= 0;

	public RenderRoddiskRep(EntityRendererFactory.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityRoddiskRep entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		er += 13.46;
        matrices.push();
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw() - 90.0f + er));

		ModelDisk.render(matrices, vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE), light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityRoddiskRep entity) {
        return null;
    }
}

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

import assets.rivalrebels.client.model.ModelTsarBomba;
import assets.rivalrebels.common.entity.EntityHotPotato;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class RenderHotPotato extends EntityRenderer<EntityHotPotato> {
	public RenderHotPotato(EntityRendererFactory.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityHotPotato entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
		matrices.multiply(new Quaternionf(entity.getYaw() - 90.0f, 0.0F, 1.0F, 0.0F));
		//GlStateManager.rotatef(90.0f, 1.0F, 0.0F, 0.0F);
		matrices.multiply(new Quaternionf(entity.getPitch() - 90.0f, 0.0F, 0.0F, 1.0F));
		matrices.scale(2.0f, 1.0f, 2.0f);
		ModelTsarBomba.render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityHotPotato entity) {
        return null;
    }
}

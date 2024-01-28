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

import assets.rivalrebels.common.entity.EntityRhodesLeftLowerLeg;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesLeftLowerLeg extends EntityRenderer<EntityRhodesLeftLowerLeg>
{
    public RenderRhodesLeftLowerLeg(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityRhodesLeftLowerLeg entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());
		RenderSystem.setShaderColor(RenderRhodes.colors[entity.getColor()*3], RenderRhodes.colors[entity.getColor()*3+1], RenderRhodes.colors[entity.getColor()*3+2], 1);
        matrices.multiply(new Quaternion(yaw, 0, 1, 0));
		matrices.multiply(new Quaternion(entity.getPitch(), 1, 0, 0));
		matrices.translate(3, 4f, 0);
		matrices.scale(-1, 1, 1);
		//RenderRhodes.thigh.renderAll();
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityRhodesLeftLowerLeg entity) {
        return RenderRhodes.texture;
    }
}

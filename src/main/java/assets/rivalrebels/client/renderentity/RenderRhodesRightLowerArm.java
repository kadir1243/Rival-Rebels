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

import assets.rivalrebels.common.entity.EntityRhodesRightLowerArm;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesRightLowerArm extends EntityRenderer<EntityRhodesRightLowerArm>
{
    public RenderRhodesRightLowerArm(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityRhodesRightLowerArm entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());
        RenderSystem.setShaderColor(RenderRhodes.colors[entity.getColor()*3],
            RenderRhodes.colors[entity.getColor()*3+1],
            RenderRhodes.colors[entity.getColor()*3+2],
            1);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getYaw()));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(entity.getPitch()));
        matrices.translate(0, 4f, 0);
        matrices.scale(-1, 1, 1);
        //RenderRhodes.lowerarm.renderAll();
        //RenderRhodes.flamethrower.renderAll();
        matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityRhodesRightLowerArm entity) {
        return RenderRhodes.texture;
    }
}

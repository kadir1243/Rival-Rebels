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
import assets.rivalrebels.common.entity.EntityTachyonBomb;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderTachyonBomb extends EntityRenderer<EntityTachyonBomb> {
    //public static IModel bomb;

	public RenderTachyonBomb(EntityRendererFactory.Context manager) {
        super(manager);
        try {
            //bomb = OBJLoader.INSTANCE.loadModel(new Identifier(RivalRebels.MODID, "models/t.obj"));
        } catch (Exception e) {
            RivalRebels.LOGGER.error("Error loading model for tachyon bomb", e);
        }
    }

    @Override
    public void render(EntityTachyonBomb entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
        matrices.multiply(new Quaternion(entity.getYaw() - 90.0f, 0.0F, 1.0F, 0.0F));
        // GlStateManager.rotatef(90.0f, 1.0F, 0.0F, 0.0F);
        matrices.multiply(new Quaternion(entity.getPitch(), 0.0F, 0.0F, 1.0F));
        //bomb.renderAll();
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityTachyonBomb entity) {
        return RRIdentifiers.ettachyonbomb;
    }
}

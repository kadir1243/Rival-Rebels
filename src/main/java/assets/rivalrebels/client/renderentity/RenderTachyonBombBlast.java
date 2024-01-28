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
import assets.rivalrebels.common.entity.EntityTachyonBombBlast;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class RenderTachyonBombBlast extends EntityRenderer<EntityTachyonBombBlast> {
	private final ModelTsarBlast model;
	private final ModelBlastSphere modelsphere;

	public RenderTachyonBombBlast(EntityRendererFactory.Context manager)
	{
        super(manager);
		model = new ModelTsarBlast();
		modelsphere = new ModelBlastSphere();
	}

    @Override
    public void render(EntityTachyonBombBlast entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        entity.time++;
        double radius = (((entity.getVelocity().getX() * 10) - 1) * ((entity.getVelocity().getX() * 10) - 1) * 2) + RivalRebels.tsarBombaStrength;
        matrices.push();
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        if (entity.time < 60) {
            double elev = entity.time / 5f;
            matrices.translate(x, y + elev, z);
            modelsphere.renderModel(matrices, buffer, entity.time * RRConfig.CLIENT.getShroomScale(), 1, 1, 1, 1);
        } else if (entity.time < 600 && radius - RivalRebels.tsarBombaStrength > 9) {
            double elev = (entity.time - 60f) / 32f + 10.0f;
            matrices.push();
            matrices.translate(x, y, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale() * 2.0f, RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 2.0f);
            matrices.multiply(new Quaternion((float) (elev * 2), 0, 1, 0));
            matrices.multiply(new Quaternion((float) (elev * 3), 1, 0, 0));
            modelsphere.renderModel(matrices, buffer, (float) elev, 1, 0.25f, 0, 1f);
            matrices.pop();
            matrices.push();
            matrices.translate(x, y, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale() * 2.0f, RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 2.0f);
            matrices.multiply(new Quaternion((float) (elev * -2), 0, 1, 0));
            matrices.multiply(new Quaternion((float) (elev * 4), 0, 0, 1));
            modelsphere.renderModel(matrices, buffer, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
            matrices.pop();
            matrices.push();
            matrices.translate(x, y + elev * 4, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 3.0f, RRConfig.CLIENT.getShroomScale());
            matrices.multiply(new Quaternion((float) (elev * -3), 1, 0, 0));
            matrices.multiply(new Quaternion((float) (elev * 2), 0, 0, 1 ));
            modelsphere.renderModel(matrices, buffer, (float) (elev - 0.4f), 1, 0, 0, 1f);
            matrices.pop();
            matrices.push();
            matrices.translate(x, y + elev * 4, z);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale() * 3.0f, RRConfig.CLIENT.getShroomScale());
            matrices.multiply(new Quaternion((float) (elev * -1), 0, 1, 0));
            matrices.multiply(new Quaternion((float) (elev * 3), 0, 0, 1 ));
            modelsphere.renderModel(matrices, buffer, (float) (elev - 0.6f), 1, 1, 0, 1);
            matrices.pop();
        } else {
            float elev = (entity.time - (radius - RivalRebels.tsarBombaStrength > 9 ? 600f : 0f)) / 8f;
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.0f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 2.0f), (float) z);
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.1f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 6.0f), (float) z);
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.2f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 10.0f), (float) z);
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.3f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 14.0f), (float) z);
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.4f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 18.0f), (float) z);
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.5f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 22.0f), (float) z);
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.6f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 26.0f), (float) z);
            RenderNuclearBlast.model.renderModel(matrices, buffer, RRConfig.CLIENT.getShroomScale() * (elev) * 1.7f, 32, 2, 0.5f, 0, 0, 0, (float) x, (float) (y + 30.0f), (float) z);
            MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.ettsarflame);
            matrices.translate(x, y + 10 + ((entity.getVelocity().getX() - 0.1d) * 14.14213562), z);
            matrices.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale());
            float horizontal = elev * 0.025f + 1.0f;
            matrices.scale((float) (horizontal * radius * 0.116f), (float) (radius * 0.065f), (float) (horizontal * radius * 0.116f));
            matrices.scale(0.8f, 0.8f, 0.8f);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
            model.render(matrices, buffer);
            RenderSystem.disableBlend();
        }
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityTachyonBombBlast entity) {
        return null;
    }

}

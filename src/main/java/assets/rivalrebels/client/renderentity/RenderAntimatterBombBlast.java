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
import assets.rivalrebels.client.model.ModelAntimatterBombBlast;
import assets.rivalrebels.client.model.ModelBlastRing;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntityAntimatterBombBlast;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

import java.util.Random;

public class RenderAntimatterBombBlast extends EntityRenderer<EntityAntimatterBombBlast>
{
	private ModelBlastSphere modelsphere;
	private ModelBlastRing modelring;
	private ModelAntimatterBombBlast modelabomb;

	public RenderAntimatterBombBlast(EntityRendererFactory.Context manager)
	{
        super(manager);
        modelsphere = new ModelBlastSphere();
		modelabomb = new ModelAntimatterBombBlast();
		modelring = new ModelBlastRing();
	}

    @Override
    public void render(EntityAntimatterBombBlast entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        Random random = entity.getEntityWorld().random;
        entity.time++;
		double radius = (((entity.getVelocity().getX() * 10) - 1) * ((entity.getVelocity().getX() * 10) - 1) * 2) + RivalRebels.tsarBombaStrength;
		matrices.push();
		matrices.push();
		matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
        RenderSystem.setShaderColor(0.0f, 0.0f, 0.2f, 1);
		float size = (entity.time % 100) * 2.0f;
		modelring.renderModel(matrices, vertexConsumers.getBuffer(RenderLayer.getSolid()), size, 64, 6f, 2f, 0f, 0f, 0f, (float)entity.getX(), (float)entity.getY(), (float)entity.getZ());
		matrices.pop();
		if (entity.time < 60)
		{
			double elev = entity.time / 5f;
			matrices.translate(entity.getX(), entity.getY() + elev, entity.getZ());
			modelsphere.renderModel(matrices, vertexConsumers.getBuffer(RenderLayer.getLightning()), entity.time, 1, 1, 1, 1);
		}
		else
		{
			//double elev = Math.sin(entity.time * 0.1f) * 5.0f + 60.0f;
			//double noisy = 5.0f;
			//double hnoisy = noisy * 0.5f;
			matrices.translate(entity.getX(), entity.getY(), entity.getZ());
			matrices.scale((float) (radius * 0.06f), (float) (radius * 0.06f), (float) (radius * 0.06f));
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1F);
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etantimatterblast);
			modelabomb.render(matrices, vertexConsumers.getBuffer(RenderLayer.getSolid()));
			/*modelsphere.renderModel(50.0f, 0.0f, 0.0f, 0.0f, 1.0f, false);
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * 2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 1, 0, 0);
			modelsphere.renderModel((float) elev, 0.2f, 0.6f, 1, 1f);
			matrices.pop();
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 4), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.2f), 0.6f, 0.2f, 1, 1f);
			matrices.pop();
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -3), 1, 0, 0);
			RenderSystem.rotatef((float) (elev * 2), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.4f), 0.4f, 0, 1, 1f);
			matrices.pop();
			matrices.push();
			//RenderSystem.translatef(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -1), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.6f), 0, 0.4f, 1, 1);
			matrices.pop();*/
			///summon rivalrebels.rivalrebelsentity51 ~ ~-2 ~ {charge:5}
		}
		matrices.pop();
		if (RRConfig.CLIENT.isAntimatterFlash()) {
			int ran = (int) (random.nextDouble() * 10f - 5f);
			for (int i = 0; i < ran; i++) {
				matrices.pop();
			}
			for (int i = -5; i < 0; i++) {
				matrices.push();
			}
			RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
			matrices.scale(random.nextFloat(), random.nextFloat(), random.nextFloat());
			matrices.multiply(new Quaternion(random.nextFloat() * 360.0f, random.nextFloat(), random.nextFloat(), random.nextFloat()));
			matrices.translate(random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f);
			modelsphere.renderModel(matrices, vertexConsumers.getBuffer(RenderLayer.getLightning()), entity.time, (float)random.nextDouble(), (float)random.nextDouble(), (float)random.nextDouble(), 1);
		}
	}

    @Override
    public Identifier getTexture(EntityAntimatterBombBlast entity) {
        return null;
    }
}

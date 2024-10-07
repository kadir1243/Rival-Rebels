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
package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelAntimatterBombBlast;
import io.github.kadir1243.rivalrebels.client.model.ModelBlastRing;
import io.github.kadir1243.rivalrebels.client.model.ModelBlastSphere;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityAntimatterBombBlast;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.lighting.LightEngine;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class RenderAntimatterBombBlast extends EntityRenderer<EntityAntimatterBombBlast> {
    private final ModelAntimatterBombBlast modelabomb = new ModelAntimatterBombBlast();

	public RenderAntimatterBombBlast(EntityRendererProvider.Context manager)
	{
        super(manager);
    }

    @Override
    public void render(EntityAntimatterBombBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        RandomSource random = entity.getRandom();
		double radius = (((entity.getDeltaMovement().x() * 10) - 1) * ((entity.getDeltaMovement().x() * 10) - 1) * 2) + RRConfig.SERVER.getTsarBombaStrength();
		matrices.pushPose();
		matrices.pushPose();
		matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
		float size = (entity.tickCount % 100) * 2.0f;
		ModelBlastRing.renderModel(matrices, vertexConsumers.getBuffer(RenderType.solid()), size, 64, 6f, 2f, 0f, 0f, 0f, 0, 0, 0, FastColor.ARGB32.colorFromFloat(1, 0, 0, 0.2F), light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
		if (entity.tickCount < 60) {
			double elev = entity.tickCount / 5f;
			matrices.translate(0, elev, 0);
			ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount, CommonColors.WHITE);
		}
		else
		{
			//double elev = Math.sin(entity.time * 0.1f) * 5.0f + 60.0f;
			//double noisy = 5.0f;
			//double hnoisy = noisy * 0.5f;
			matrices.scale((float) (radius * 0.06f), (float) (radius * 0.06f), (float) (radius * 0.06f));
			modelabomb.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.etantimatterblast)), light);
			/*modelsphere.renderModel(50.0f, 0.0f, 0.0f, 0.0f, 1.0f, false);
			matrices.push();
			//matrices.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * 2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 1, 0, 0);
			modelsphere.renderModel((float) elev, 0.2f, 0.6f, 1, 1f);
			matrices.pop();
			matrices.push();
			//matrices.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 4), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.2f), 0.6f, 0.2f, 1, 1f);
			matrices.pop();
			matrices.push();
			//matrices.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -3), 1, 0, 0);
			RenderSystem.rotatef((float) (elev * 2), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.4f), 0.4f, 0, 1, 1f);
			matrices.pop();
			matrices.push();
			//matrices.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -1), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.6f), 0, 0.4f, 1, 1);
			matrices.pop();*/
			///summon rivalrebels.rivalrebelsentity51 ~ ~-2 ~ {charge:5}
		}
		matrices.popPose();
		if (RRConfig.CLIENT.isAntimatterFlash()) {
			int ran = (int) (random.nextDouble() * 10f - 5f);
			for (int i = 0; i < ran; i++) {
				matrices.popPose();
			}
			for (int i = -5; i < 0; i++) {
				matrices.pushPose();
			}
			RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE);
			matrices.scale(random.nextFloat(), random.nextFloat(), random.nextFloat());
			matrices.mulPose(Axis.of(new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat())).rotationDegrees(random.nextFloat() * 360));
			matrices.translate(random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f);
			ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount, (float)random.nextDouble(), (float)random.nextDouble(), (float)random.nextDouble(), 1);
		}
	}

    @Override
    public ResourceLocation getTextureLocation(EntityAntimatterBombBlast entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityAntimatterBombBlast livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityAntimatterBombBlast entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}

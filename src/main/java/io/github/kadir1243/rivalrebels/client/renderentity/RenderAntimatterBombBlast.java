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
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntityAntimatterBombBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CommonColors;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.lighting.LightEngine;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class RenderAntimatterBombBlast extends EntityRenderer<EntityAntimatterBombBlast, RenderAntimatterBombBlast.State> {
    private final ModelAntimatterBombBlast modelabomb = new ModelAntimatterBombBlast();

	public RenderAntimatterBombBlast(EntityRendererProvider.Context manager)
	{
        super(manager);
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        RandomSource random = renderState.random;
		double radius = (((renderState.deltaMovement.x() * 10) - 1) * ((renderState.deltaMovement.x() * 10) - 1) * 2) + RRConfig.SERVER.getTsarBombaStrength();
		poseStack.pushPose();
		poseStack.pushPose();
		poseStack.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
		float size = (renderState.ageInTicks % 100) * 2.0f;
		ModelBlastRing.renderModel(poseStack, bufferSource.getBuffer(RenderType.solid()), size, 64, 6f, 2f, 0f, 0f, 0f, 0, 0, 0, ARGB.colorFromFloat(1, 0, 0, 0.2F), packedLight, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
		if (renderState.ageInTicks < 60) {
			double elev = renderState.ageInTicks / 5f;
			poseStack.translate(0, elev, 0);
			ModelBlastSphere.renderModel(poseStack, bufferSource, renderState.ageInTicks, CommonColors.WHITE);
		}
		else
		{
			//double elev = Math.sin(renderState.time * 0.1f) * 5.0f + 60.0f;
			//double noisy = 5.0f;
			//double hnoisy = noisy * 0.5f;
			poseStack.scale((float) (radius * 0.06f), (float) (radius * 0.06f), (float) (radius * 0.06f));
			modelabomb.render(poseStack, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etantimatterblast)), packedLight);
			/*modelsphere.renderModel(50.0f, 0.0f, 0.0f, 0.0f, 1.0f, false);
			poseStack.push();
			//poseStack.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * 2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 1, 0, 0);
			modelsphere.renderModel((float) elev, 0.2f, 0.6f, 1, 1f);
			poseStack.pop();
			poseStack.push();
			//poseStack.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -2), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 4), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.2f), 0.6f, 0.2f, 1, 1f);
			poseStack.pop();
			poseStack.push();
			//poseStack.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -3), 1, 0, 0);
			RenderSystem.rotatef((float) (elev * 2), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.4f), 0.4f, 0, 1, 1f);
			poseStack.pop();
			poseStack.push();
			//poseStack.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			RenderSystem.rotatef((float) (elev * -1), 0, 1, 0);
			RenderSystem.rotatef((float) (elev * 3), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.6f), 0, 0.4f, 1, 1);
			poseStack.pop();*/
			///summon rivalrebels.rivalrebelsentity51 ~ ~-2 ~ {charge:5}
		}
		poseStack.popPose();
		if (RRConfig.CLIENT.isAntimatterFlash()) {
			int ran = (int) (random.nextDouble() * 10f - 5f);
			for (int i = 0; i < ran; i++) {
				poseStack.popPose();
			}
			for (int i = -5; i < 0; i++) {
				poseStack.pushPose();
			}
			poseStack.scale(random.nextFloat(), random.nextFloat(), random.nextFloat());
			poseStack.mulPose(Axis.of(new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat())).rotationDegrees(random.nextFloat() * 360));
			poseStack.translate(random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f);
			ModelBlastSphere.renderModel(poseStack, bufferSource.getBuffer(RenderTypes.ANTIMATTER_BOMB_BLAST_ENTITY), renderState.ageInTicks, ARGB.colorFromFloat(1F, (float)random.nextDouble(), (float)random.nextDouble(), (float)random.nextDouble()));
		}
	}

    @Override
    public boolean shouldRender(EntityAntimatterBombBlast livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected boolean affectedByCulling(EntityAntimatterBombBlast p_365169_) {
        return false;
    }

    @Override
    protected int getBlockLightLevel(EntityAntimatterBombBlast entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityAntimatterBombBlast p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.random = p_entity.getRandom();
        reusedState.deltaMovement = p_entity.getDeltaMovement();
    }

    public static class State extends EntityRenderState {
        public RandomSource random;
        public Vec3 deltaMovement;
    }
}

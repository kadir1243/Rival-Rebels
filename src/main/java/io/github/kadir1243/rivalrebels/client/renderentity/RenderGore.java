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

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderHelper;
import io.github.kadir1243.rivalrebels.common.entity.EntityGore;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderGore extends EntityRenderer<EntityGore, RenderGore.State> {
    private static final Identifier player = Identifier.withDefaultNamespace("textures/entity/steve.png");
    private static final Identifier creeper = Identifier.withDefaultNamespace("textures/entity/creeper/creeper.png");
    private static final Identifier enderman = Identifier.withDefaultNamespace("textures/entity/enderman/enderman.png");
    private static final Identifier ghast = Identifier.withDefaultNamespace("textures/entity/ghast/ghast.png");
    private static final Identifier skeleton = Identifier.withDefaultNamespace("textures/entity/skeleton/skeleton.png");
    private static final Identifier slime = Identifier.withDefaultNamespace("textures/entity/slime/slime.png");
    private static final Identifier magmacube = Identifier.withDefaultNamespace("textures/entity/slime/magmacube.png");
    private static final Identifier spider = Identifier.withDefaultNamespace("textures/entity/spider/spider.png");
    private static final Identifier cavespider = Identifier.withDefaultNamespace("textures/entity/spider/cave_spider.png");
    private static final Identifier zombiepigman = Identifier.withDefaultNamespace("textures/entity/zombie_pigman.png");
    private static final Identifier zombie = Identifier.withDefaultNamespace("textures/entity/zombie/zombie.png");

    public RenderGore(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        this.shadowRadius = 0F;
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(-renderState.yRot + 180));
		poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
        int mob = renderState.mob;
		int type = renderState.type;
		float size = renderState.size;
        int packedLight = renderState.lightCoords;

        RenderType renderType = RenderTypes.entityTranslucentCullItemTarget(getTextureLocation(renderState));
        if (mob == 0) {
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 0, 64, 32, 16, packedLight);
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 8, 16, 16, 64, 32, 16, packedLight);
			else if (type == 2) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 4, 40, 16, 64, 32, 16, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 4, 0, 16, 64, 32, 16, packedLight);
		}
		else if (mob == 1)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 0, 64, 64, 16, packedLight);
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 8, 16, 16, 64, 64, 16, packedLight);
			else if (type == 2) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 4, 40, 16, 64, 64, 16, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 4, 0, 16, 64, 64, 16, packedLight);
		}
		else if (mob == 2)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 0, 64, 64, 16, packedLight);
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 8, 16, 16, 64, 64, 16, packedLight);
			else if (type == 2) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 4, 40, 16, 64, 64, 16, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 4, 0, 16, 64, 64, 16, packedLight);
		}
		else if (mob == 3)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 0, 64, 32, 16, packedLight);
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 8, 16, 16, 64, 32, 16, packedLight);
			else if (type == 2) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 2, 10, 2, 40, 16, 64, 32, 16, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 2, 10, 2, 0, 16, 64, 32, 16, packedLight);
		}
		else if (mob == 4)
		{
			if (type == 0)
			{
				RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 0, 64, 32, 16, packedLight);
				poseStack.translate(0, -0.125, 0);
				poseStack.scale(0.875F, 0.875F, 0.875F);
				RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 16, 64, 32, 16, packedLight);
			}
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 8, 32, 16, 64, 32, 16, packedLight);
			else if (type == 2) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 2, 30, 2, 56, 0, 64, 32, 16, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 2, 30, 2, 56, 0, 64, 32, 16, packedLight);
		}
		else if (mob == 5)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 0, 64, 32, 16, packedLight);
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 12, 8, 16, 16, 64, 32, 16, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 4, 6, 4, 0, 16, 64, 32, 16, packedLight);
		}
		else if (mob == 6)
		{
			if (type == 0) {
				RenderHelper.renderBox(poseStack, nodeCollector, RenderTypes.entityTranslucent(getTextureLocation(renderState)), 8, 8, 8, 0, 0, 64, 32, 16, packedLight);
			} else if (type == 1) {
				RenderHelper.renderBox(poseStack, nodeCollector, RenderTypes.entityTranslucent(getTextureLocation(renderState)), 6, 6, 6, 0, 16, 64, 32, 16, packedLight);
			}
		}
		else if (mob == 7)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 0, 0, 64, 32, 16, packedLight);
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 6, 6, 6, 0, 16, 64, 32, 16, packedLight);
		}
		else if (mob == 8)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 32, 4, 64, 32, 16, packedLight);
			else if (type == 1)
			{
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
				RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 12, 10, 4, 12, 64, 32, 16, packedLight);
			}
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 2, 2, 16, 18, 0, 64, 32, 16, packedLight);
		}
		else if (mob == 9)
		{
			poseStack.scale(0.666f, 0.666f, 0.666f);
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 8, 8, 32, 4, 64, 32, 16, packedLight);
			else if (type == 1)
			{
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
				RenderHelper.renderBox(poseStack, nodeCollector, renderType, 8, 12, 10, 4, 12, 64, 32, 16, packedLight);
			}
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 2, 2, 16, 18, 0, 64, 32, 16, packedLight);
		}
		else if (mob == 10)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 16, 16, 16, 0, 0, 64, 32, 4, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, 2, 14, 2, 0, 0, 64, 32, 4, packedLight);
		}
		else if (mob == 11)
		{
			if (type == 0) RenderHelper.renderBox(poseStack, nodeCollector, renderType, (int) (8 * size), (int) (8 * size), (int) (8 * size), 0, 0, 64, 64, 16, packedLight);
			else if (type == 1) RenderHelper.renderBox(poseStack, nodeCollector, renderType, (int) (4 * size), (int) (12 * size), (int) (8 * size), 0, 0, 64, 64, 16, packedLight);
			else if (type == 2) RenderHelper.renderBox(poseStack, nodeCollector, renderType, (int) (4 * size), (int) (12 * size), (int) (4 * size), 0, 0, 64, 64, 16, packedLight);
			else if (type == 3) RenderHelper.renderBox(poseStack, nodeCollector, renderType, (int) (4 * size), (int) (12 * size), (int) (4 * size), 0, 0, 64, 64, 16, packedLight);
		}
		poseStack.popPose();
	}

    public Identifier getTextureLocation(State entity) {
        return switch (entity.mob) {
            case 0 -> Objects.requireNonNullElse(entity.playerSkin, player);
            case 1 -> zombie;
            case 2 -> zombiepigman;
            case 3 -> skeleton;
            case 4 -> enderman;
            case 5 -> creeper;
            case 6 -> slime;
            case 7 -> magmacube;
            case 8 -> spider;
            case 9 -> cavespider;
            case 10 -> ghast;
            case 11 -> {
                if (entity.size < 1) yield RRIdentifiers.btsplash5;
                else if (entity.size < 2) yield RRIdentifiers.btsplash1;
                yield RRIdentifiers.btsplash3;
            }
            default -> null;
        };
	}

    @Override
    public boolean shouldRender(EntityGore livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityGore entity, State reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
        reusedState.mob = entity.getMob();
        reusedState.type = entity.getTypeOfGore();
        reusedState.size = entity.getSize();
        reusedState.playerSkin = entity.playerSkin;
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public int mob;
        public int type;
        public float size;
        public Identifier playerSkin;
    }
}

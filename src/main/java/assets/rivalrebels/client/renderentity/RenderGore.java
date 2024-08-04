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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.common.entity.EntityGore;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import java.util.Objects;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderGore extends EntityRenderer<EntityGore>
{
	private static final ResourceLocation	player			= ResourceLocation.withDefaultNamespace("textures/entity/steve.png");
	private static final ResourceLocation	creeper			= ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper.png");
	private static final ResourceLocation	enderman		= ResourceLocation.withDefaultNamespace("textures/entity/enderman/enderman.png");
	private static final ResourceLocation	ghast			= ResourceLocation.withDefaultNamespace("textures/entity/ghast/ghast.png");
	private static final ResourceLocation	skeleton		= ResourceLocation.withDefaultNamespace("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation	slime			= ResourceLocation.withDefaultNamespace("textures/entity/slime/slime.png");
	private static final ResourceLocation	magmacube		= ResourceLocation.withDefaultNamespace("textures/entity/slime/magmacube.png");
	private static final ResourceLocation	spider			= ResourceLocation.withDefaultNamespace("textures/entity/spider/spider.png");
	private static final ResourceLocation	cavespider		= ResourceLocation.withDefaultNamespace("textures/entity/spider/cave_spider.png");
	private static final ResourceLocation	zombiepigman	= ResourceLocation.withDefaultNamespace("textures/entity/zombie_pigman.png");
	private static final ResourceLocation	zombie			= ResourceLocation.withDefaultNamespace("textures/entity/zombie/zombie.png");

    public RenderGore(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        this.shadowRadius = 0F;
    }

    @Override
    public void render(EntityGore entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(-entity.getYRot() + 180));
		matrices.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        int mob = entity.getMob();
		int type = entity.getTypeOfGore();
		float size = entity.getSize();

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.itemEntityTranslucentCull(getTextureLocation(entity)));
        if (mob == 0) {
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 0, 64, 32, 16, light);
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, 4, 12, 8, 16, 16, 64, 32, 16, light);
			else if (type == 2) RenderHelper.renderBox(matrices, buffer, 4, 12, 4, 40, 16, 64, 32, 16, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 4, 12, 4, 0, 16, 64, 32, 16, light);
		}
		else if (mob == 1)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 0, 64, 64, 16, light);
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, 4, 12, 8, 16, 16, 64, 64, 16, light);
			else if (type == 2) RenderHelper.renderBox(matrices, buffer, 4, 12, 4, 40, 16, 64, 64, 16, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 4, 12, 4, 0, 16, 64, 64, 16, light);
		}
		else if (mob == 2)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 0, 64, 64, 16, light);
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, 4, 12, 8, 16, 16, 64, 64, 16, light);
			else if (type == 2) RenderHelper.renderBox(matrices, buffer, 4, 12, 4, 40, 16, 64, 64, 16, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 4, 12, 4, 0, 16, 64, 64, 16, light);
		}
		else if (mob == 3)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 0, 64, 32, 16, light);
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, 4, 12, 8, 16, 16, 64, 32, 16, light);
			else if (type == 2) RenderHelper.renderBox(matrices, buffer, 2, 10, 2, 40, 16, 64, 32, 16, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 2, 10, 2, 0, 16, 64, 32, 16, light);
		}
		else if (mob == 4)
		{
			if (type == 0)
			{
				RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 0, 64, 32, 16, light);
				matrices.translate(0, -0.125, 0);
				matrices.scale(0.875F, 0.875F, 0.875F);
				RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 16, 64, 32, 16, light);
			}
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, 4, 12, 8, 32, 16, 64, 32, 16, light);
			else if (type == 2) RenderHelper.renderBox(matrices, buffer, 2, 30, 2, 56, 0, 64, 32, 16, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 2, 30, 2, 56, 0, 64, 32, 16, light);
		}
		else if (mob == 5)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 0, 64, 32, 16, light);
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, 4, 12, 8, 16, 16, 64, 32, 16, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 4, 6, 4, 0, 16, 64, 32, 16, light);
		}
		else if (mob == 6)
		{
			if (type == 0) {
				RenderHelper.renderBox(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity))), 8, 8, 8, 0, 0, 64, 32, 16, light);
			} else if (type == 1) {
				RenderHelper.renderBox(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity))), 6, 6, 6, 0, 16, 64, 32, 16, light);
			}
		}
		else if (mob == 7)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 0, 0, 64, 32, 16, light);
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, 6, 6, 6, 0, 16, 64, 32, 16, light);
		}
		else if (mob == 8)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 32, 4, 64, 32, 16, light);
			else if (type == 1)
			{
                matrices.mulPose(Axis.YP.rotationDegrees(90));
				RenderHelper.renderBox(matrices, buffer, 8, 12, 10, 4, 12, 64, 32, 16, light);
			}
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 2, 2, 16, 18, 0, 64, 32, 16, light);
		}
		else if (mob == 9)
		{
			matrices.scale(0.666f, 0.666f, 0.666f);
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 8, 8, 8, 32, 4, 64, 32, 16, light);
			else if (type == 1)
			{
                matrices.mulPose(Axis.YP.rotationDegrees(90));
				RenderHelper.renderBox(matrices, buffer, 8, 12, 10, 4, 12, 64, 32, 16, light);
			}
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 2, 2, 16, 18, 0, 64, 32, 16, light);
		}
		else if (mob == 10)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, 16, 16, 16, 0, 0, 64, 32, 4, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, 2, 14, 2, 0, 0, 64, 32, 4, light);
		}
		else if (mob == 11)
		{
			if (type == 0) RenderHelper.renderBox(matrices, buffer, (int) (8 * size), (int) (8 * size), (int) (8 * size), 0, 0, 64, 64, 16, light);
			else if (type == 1) RenderHelper.renderBox(matrices, buffer, (int) (4 * size), (int) (12 * size), (int) (8 * size), 0, 0, 64, 64, 16, light);
			else if (type == 2) RenderHelper.renderBox(matrices, buffer, (int) (4 * size), (int) (12 * size), (int) (4 * size), 0, 0, 64, 64, 16, light);
			else if (type == 3) RenderHelper.renderBox(matrices, buffer, (int) (4 * size), (int) (12 * size), (int) (4 * size), 0, 0, 64, 64, 16, light);
		}
		matrices.popPose();
	}

	@Override
    public ResourceLocation getTextureLocation(EntityGore entity) {
        return switch (entity.getMob()) {
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
                if (entity.getSize() < 1) yield RRIdentifiers.btsplash5;
                else if (entity.getSize() < 2) yield RRIdentifiers.btsplash1;
                yield RRIdentifiers.btsplash3;
            }
            default -> null;
        };
	}
}

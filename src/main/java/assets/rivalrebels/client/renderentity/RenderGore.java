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
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

import java.util.Objects;

public class RenderGore extends EntityRenderer<EntityGore>
{
	private static final Identifier	player			= new Identifier("textures/entity/steve.png");
	private static final Identifier	creeper			= new Identifier("textures/entity/creeper/creeper.png");
	private static final Identifier	enderman		= new Identifier("textures/entity/enderman/enderman.png");
	private static final Identifier	ghast			= new Identifier("textures/entity/ghast/ghast.png");
	private static final Identifier	skeleton		= new Identifier("textures/entity/skeleton/skeleton.png");
	private static final Identifier	slime			= new Identifier("textures/entity/slime/slime.png");
	private static final Identifier	magmacube		= new Identifier("textures/entity/slime/magmacube.png");
	private static final Identifier	spider			= new Identifier("textures/entity/spider/spider.png");
	private static final Identifier	cavespider		= new Identifier("textures/entity/spider/cave_spider.png");
	private static final Identifier	zombiepigman	= new Identifier("textures/entity/zombie_pigman.png");
	private static final Identifier	zombie			= new Identifier("textures/entity/zombie/zombie.png");

    public RenderGore(EntityRendererFactory.Context renderManager) {
        super(renderManager);
        this.shadowRadius = 0F;
    }

    @Override
    public void render(EntityGore entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

		matrices.push();
		matrices.translate(x, y, z);
		matrices.multiply(new Quaternionf(-entity.getYaw() + 180, 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternionf(entity.getPitch(), 1.0F, 0.0F, 0.0F));
        int mob = entity.getMob();
		int type = entity.getTypeOfGore();
		float size = entity.getSize();

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getItemEntityTranslucentCull(getTexture(entity)));
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
				RenderHelper.renderBox(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))), 8, 8, 8, 0, 0, 64, 32, 16, light);
			} else if (type == 1) {
				RenderHelper.renderBox(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))), 6, 6, 6, 0, 16, 64, 32, 16, light);
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
                matrices.multiply(new Quaternionf(90, 0, 1, 0));
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
                matrices.multiply(new Quaternionf(90, 0, 1, 0));
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
		matrices.pop();
	}

	@Override
    public Identifier getTexture(EntityGore entity) {
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

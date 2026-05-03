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
package io.github.kadir1243.rivalrebels.common.entity;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.Entity;

@OnlyIn(Dist.CLIENT)
public class EntityBloodFX extends SingleQuadParticle {
	public EntityBloodFX(ClientLevel level, double x, double y, double z, boolean b) {
		this(level, x, y, z, level.getRandom().nextGaussian() * 0.1, level.getRandom().nextGaussian() * 0.1, level.getRandom().nextGaussian() * 0.1, b);
	}

	public EntityBloodFX(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, boolean isBlood) {
		super(level, x, y, z, dx, dy, dz, Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(TextureAtlas.LOCATION_BLOCKS).getSprite(isBlood ? RRIdentifiers.etblood : RRIdentifiers.etgoo));

        this.setParticleSpeed(dx, dy, dz);
		gravity = 0.75F;
		lifetime = 20;
	}

	public EntityBloodFX(ClientLevel level, EntityGore gore, boolean isBlood)
	{
		this(level, gore.getX(), gore.getY(), gore.getZ(), isBlood);
	}

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    public void renderParticle(PoseStack pose, VertexConsumer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f10 = 0.1F * this.quadSize;

		float f11 = (float) (xo + (x - xo) * partialTicks /*- interpPosX*/);
		float f12 = (float) (yo + (y - yo) * partialTicks /*- interpPosY*/);
		float f13 = (float) (zo + (z - zo) * partialTicks /*- interpPosZ*/);
		buffer.addVertex(pose.last(), f11 - rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 - rotationYZ * f10 - rotationXZ * f10).setUv(1, 1).setColor(CommonColors.WHITE);
		buffer.addVertex(pose.last(), f11 - rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 - rotationYZ * f10 + rotationXZ * f10).setUv(1, 0).setColor(CommonColors.WHITE);
		buffer.addVertex(pose.last(), f11 + rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 + rotationYZ * f10 + rotationXZ * f10).setUv(0, 0).setColor(CommonColors.WHITE);
		buffer.addVertex(pose.last(), f11 + rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 + rotationYZ * f10 - rotationXZ * f10).setUv(0, 1).setColor(CommonColors.WHITE);
	}
}

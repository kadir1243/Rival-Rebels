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
package assets.rivalrebels.common.entity;

import assets.rivalrebels.RRIdentifiers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;

@Environment(EnvType.CLIENT)
public class EntityBloodFX extends TextureSheetParticle {
	public EntityBloodFX(ClientLevel level, double x, double y, double z, boolean b) {
		this(level, x, y, z, level.random.nextGaussian() * 0.1, level.random.nextGaussian() * 0.1, level.random.nextGaussian() * 0.1, b);
	}

	public EntityBloodFX(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, boolean isBlood) {
		super(level, x, y, z, dx, dy, dz);

        this.setParticleSpeed(dx, dy, dz);
		gravity = 0.75F;
		lifetime = 20;
        setSprite(Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(isBlood ? RRIdentifiers.etblood : RRIdentifiers.etgoo));
	}

	public EntityBloodFX(ClientLevel level, EntityGore gore, boolean isBlood)
	{
		this(level, gore.getX(), gore.getY(), gore.getZ(), isBlood);
	}

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
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

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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EntityBloodFX extends SpriteBillboardParticle {
	boolean	isBlood;

	public EntityBloodFX(ClientWorld w, double x, double y, double z, boolean b)
	{
		this(w, x, y, z, w.random.nextGaussian() * 0.1, w.random.nextGaussian() * 0.1, w.random.nextGaussian() * 0.1, b);
	}

	public EntityBloodFX(ClientWorld w, double x, double y, double z, double r, double g, double b, boolean bl) {
		super(w, x, y, z, r, g, b);

		velocityX = r;
		velocityY = g;
		velocityZ = b;
		gravityStrength = 0.75F;
		maxAge = 20;
		isBlood = bl;
        setSprite(MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(isBlood ? RRIdentifiers.etblood : RRIdentifiers.etgoo));
	}

	public EntityBloodFX(ClientWorld w, EntityGore g, boolean b)
	{
		this(w, g.getX(), g.getY(), g.getZ(), b);
	}

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public void renderParticle(VertexConsumer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f10 = 0.1F * this.scale;

		float f11 = (float) (prevPosX + (x - prevPosX) * partialTicks /*- interpPosX*/);
		float f12 = (float) (prevPosY + (y - prevPosY) * partialTicks /*- interpPosY*/);
		float f13 = (float) (prevPosZ + (z - prevPosZ) * partialTicks /*- interpPosZ*/);
		buffer.vertex(f11 - rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 - rotationYZ * f10 - rotationXZ * f10).texture(1, 1).color(1, 1, 1, 1).next();
		buffer.vertex(f11 - rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 - rotationYZ * f10 + rotationXZ * f10).texture(1, 0).color(1, 1, 1, 1).next();
		buffer.vertex(f11 + rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 + rotationYZ * f10 + rotationXZ * f10).texture(0, 0).color(1, 1, 1, 1).next();
		buffer.vertex(f11 + rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 + rotationYZ * f10 - rotationXZ * f10).texture(0, 1).color(1, 1, 1, 1).next();
	}
}

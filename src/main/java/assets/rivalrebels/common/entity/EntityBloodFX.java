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
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;

@Environment(EnvType.CLIENT)
public class EntityBloodFX extends TextureSheetParticle {
	boolean	isBlood;

	public EntityBloodFX(ClientLevel w, double x, double y, double z, boolean b)
	{
		this(w, x, y, z, w.random.nextGaussian() * 0.1, w.random.nextGaussian() * 0.1, w.random.nextGaussian() * 0.1, b);
	}

	public EntityBloodFX(ClientLevel w, double x, double y, double z, double r, double g, double b, boolean bl) {
		super(w, x, y, z, r, g, b);

		xd = r;
		yd = g;
		zd = b;
		gravity = 0.75F;
		lifetime = 20;
		isBlood = bl;
        setSprite(Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(isBlood ? RRIdentifiers.etblood : RRIdentifiers.etgoo));
	}

	public EntityBloodFX(ClientLevel w, EntityGore g, boolean b)
	{
		this(w, g.getX(), g.getY(), g.getZ(), b);
	}

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void renderParticle(VertexConsumer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f10 = 0.1F * this.quadSize;

		float f11 = (float) (xo + (x - xo) * partialTicks /*- interpPosX*/);
		float f12 = (float) (yo + (y - yo) * partialTicks /*- interpPosY*/);
		float f13 = (float) (zo + (z - zo) * partialTicks /*- interpPosZ*/);
		buffer.addVertex(f11 - rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 - rotationYZ * f10 - rotationXZ * f10).setUv(1, 1).setColor(1, 1, 1, 1);
		buffer.addVertex(f11 - rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 - rotationYZ * f10 + rotationXZ * f10).setUv(1, 0).setColor(1, 1, 1, 1);
		buffer.addVertex(f11 + rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 + rotationYZ * f10 + rotationXZ * f10).setUv(0, 0).setColor(1, 1, 1, 1);
		buffer.addVertex(f11 + rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 + rotationYZ * f10 - rotationXZ * f10).setUv(0, 1).setColor(1, 1, 1, 1);
	}
}

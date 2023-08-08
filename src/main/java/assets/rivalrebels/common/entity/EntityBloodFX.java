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

import assets.rivalrebels.RivalRebels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityBloodFX extends EntityFX
{
	boolean	isBlood;

	public EntityBloodFX(World w, double x, double y, double z, boolean b)
	{
		this(w, x, y, z, w.rand.nextGaussian() * 0.1, w.rand.nextGaussian() * 0.1, w.rand.nextGaussian() * 0.1, b);
	}

	public EntityBloodFX(World w, double x, double y, double z, double r, double g, double b, boolean bl)
	{
		super(w, x, y, z, r, g, b);

		posX = x;
		posY = y;
		posZ = z;
		motionX = r;
		motionY = g;
		motionZ = b;
		particleGravity = 0.75F;
		particleMaxAge = 20;
		isBlood = bl;
	}

	public EntityBloodFX(World w, EntityGore g, boolean b)
	{
		this(w, g.posX, g.posY, g.posZ, b);
	}

    @Override
    public void renderParticle(WorldRenderer worldRenderer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		Minecraft.getMinecraft().renderEngine.bindTexture(isBlood ? RivalRebels.etblood : RivalRebels.etgoo);
		float f10 = 0.1F * this.particleScale;

		float f11 = (float) (prevPosX + (posX - prevPosX) * partialTicks - interpPosX);
		float f12 = (float) (prevPosY + (posY - prevPosY) * partialTicks - interpPosY);
		float f13 = (float) (prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ);
		worldRenderer.putColorRGB_F(1, 1, 1, 1);
		worldRenderer.pos(f11 - rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 - rotationYZ * f10 - rotationXZ * f10).tex(1, 1).endVertex();
		worldRenderer.pos(f11 - rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 - rotationYZ * f10 + rotationXZ * f10).tex(1, 0).endVertex();
		worldRenderer.pos(f11 + rotationX * f10 + rotationXY * f10, f12 + rotationZ * f10, f13 + rotationYZ * f10 + rotationXZ * f10).tex(0, 0).endVertex();
		worldRenderer.pos(f11 + rotationX * f10 - rotationXY * f10, f12 - rotationZ * f10, f13 + rotationYZ * f10 - rotationXZ * f10).tex(0, 1).endVertex();
	}
}

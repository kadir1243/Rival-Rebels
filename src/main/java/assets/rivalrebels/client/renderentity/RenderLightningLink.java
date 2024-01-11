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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityLightningLink;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderLightningLink extends Render
{
	static float	red		= 0.65F;
	static float	green	= 0.75F;
	static float	blue	= 1F;

    public RenderLightningLink(RenderManager renderManager) {
        super(renderManager);
    }

    public void renderLightningLink(EntityLightningLink ell, double x, double y, double z, float yaw, float pitch)
	{
		float segmentDistance = RivalRebels.teslasegments;
		float distance = (float) ell.motionX * 100;
		distance = 100;

		// RenderLibrary.instance.renderModel((float) x, (float) y, (float) z,
		// (float) Math.sin(-ell.rotationYaw / 180 * Math.PI) * distance,
		// (float) Math.sin(-ell.rotationPitch / 180 * Math.PI) * distance,
		// (float) Math.cos(-ell.rotationYaw / 180 * Math.PI) * distance,
		// 2f, 0.07f, 8, 5f, 0.5f, red, green, blue, 1);

		if (distance > 0)
		{
			Random rand = ell.world.rand;
			float radius = 0.07F;
			Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

            GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.rotate(ell.rotationYaw, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-ell.rotationPitch, 1.0F, 0.0F, 0.0F);

			double AddedX = 0;
			double AddedY = 0;
			double prevAddedX = 0;
			double prevAddedY = 0;
			for (int addedZ = (int) distance; addedZ >= 0; addedZ -= segmentDistance)
			{
				prevAddedX = AddedX;
				prevAddedY = AddedY;
				AddedX += (rand.nextFloat() - 0.5) * 2;
				AddedY += (rand.nextFloat() - 0.5) * 2;
				double dist = Math.sqrt(AddedX * AddedX + AddedY * AddedY) / 1.5;
				if (dist != 0)
				{
					double tempAddedX = AddedX / dist;
					double tempAddedY = AddedY / dist;
					if (Math.abs(tempAddedX) < Math.abs(AddedX)) AddedX = tempAddedX;
					if (Math.abs(tempAddedY) < Math.abs(AddedY)) AddedY = tempAddedY;
				}
				if (addedZ <= 0)
				{
					AddedX = AddedY = 0;
				}

				for (float o = 0; o <= radius; o += radius / 8)
				{
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
					buffer.pos(AddedX + o, AddedY - o, addedZ).color(red, green, blue, 0.95f).endVertex();
					buffer.pos(AddedX + o, AddedY + o, addedZ).color(red, green, blue, 0.95f).endVertex();
					buffer.pos(prevAddedX + o, prevAddedY + o, addedZ + segmentDistance).color(red, green, blue, 0.95f).endVertex();
					buffer.pos(prevAddedX + o, prevAddedY - o, addedZ + segmentDistance).color(red, green, blue, 0.95f).endVertex();
					buffer.pos(AddedX - o, AddedY - o, addedZ).color(red, green, blue, 0.95f).endVertex();
					buffer.pos(prevAddedX - o, prevAddedY - o, addedZ + segmentDistance).color(red, green, blue, 0.95f).endVertex();
					buffer.pos(AddedX - o, AddedY + o, addedZ).color(red, green, blue, 0.95f).endVertex();
					buffer.pos(prevAddedX - o, prevAddedY + o, addedZ + segmentDistance).color(red, green, blue, 0.95f).endVertex();
					tessellator.draw();
				}
			}

			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
		}
	}

	@Override
	public void doRender(Entity entityLightningLink, double x, double y, double z, float yaw, float pitch)
	{
		this.renderLightningLink((EntityLightningLink) entityLightningLink, x, y, z, yaw, pitch);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

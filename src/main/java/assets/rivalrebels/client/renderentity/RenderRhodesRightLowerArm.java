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

import assets.rivalrebels.common.entity.EntityRhodesRightLowerArm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRhodesRightLowerArm extends Render
{
    public RenderRhodesRightLowerArm(RenderManager renderManager) {
        super(renderManager);
    }

    public void renderRhodes(EntityRhodesRightLowerArm rhodes, double x, double y, double z, float par8, float ptt)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.scale(rhodes.scale, rhodes.scale, rhodes.scale);
		GlStateManager.color(RenderRhodes.colors[rhodes.color*3], RenderRhodes.colors[rhodes.color*3+1], RenderRhodes.colors[rhodes.color*3+2]);
		Minecraft.getMinecraft().renderEngine.bindTexture(RenderRhodes.texture);
		GlStateManager.disableCull();
		GlStateManager.rotate(rhodes.rotationYaw, 0, 1, 0);
		GlStateManager.rotate(rhodes.rotationPitch, 1, 0, 0);
		GlStateManager.translate(0, 4f, 0);
		GlStateManager.scale(-1, 1, 1);
		//RenderRhodes.lowerarm.renderAll();
		//RenderRhodes.flamethrower.renderAll();
		GlStateManager.popMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all
	 * probabilty, the class Render is generic (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre
	 * 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderRhodes((EntityRhodesRightLowerArm) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

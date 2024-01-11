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

import assets.rivalrebels.common.entity.EntityRhodesHead;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRhodesHead extends Render<EntityRhodesHead>
{
    public RenderRhodesHead(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
	public void doRender(EntityRhodesHead par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) par2, (float) par4, (float) par6);
        GlStateManager.scale(par1Entity.scale, par1Entity.scale, par1Entity.scale);
        GlStateManager.color(RenderRhodes.colors[par1Entity.color*3], RenderRhodes.colors[par1Entity.color*3+1], RenderRhodes.colors[par1Entity.color*3+2]);
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderRhodes.texture);
        GlStateManager.disableCull();
        GlStateManager.rotate(par1Entity.rotationYaw, 0, 1, 0);
        GlStateManager.rotate(par1Entity.rotationPitch, 1, 0, 0);
        //RenderRhodes.head.renderAll();
        GlStateManager.popMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityRhodesHead entity)
	{
		return null;
	}
}

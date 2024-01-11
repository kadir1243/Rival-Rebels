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
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.model.ModelNuclearBomb;
import assets.rivalrebels.common.entity.EntityBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBomb extends Render<EntityBomb>
{
	private ModelNuclearBomb	model;
	private ModelBlastSphere	modelsphere;
	public RenderBomb(RenderManager manager)
	{
        super(manager);
		modelsphere = new ModelBlastSphere();
		model = new ModelNuclearBomb();
	}

    @Override
	public void doRender(EntityBomb entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.rotate(entity.rotationYaw - 90.0f, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.rotationPitch - 90.0f, 0.0F, 0.0F, 1.0F);
        if (entity.motionX==0&& entity.motionZ==0)
        {
            if (entity.motionY == 1)
            {
                modelsphere.renderModel(entity.ticksExisted * 0.2f, 0.25f, 0.25f, 1.0f, 0.75f);
            }
            else if (entity.motionY == 0)
            {
                modelsphere.renderModel(entity.ticksExisted * 0.2f, 0.8f, 0.8f, 1f, 0.75f);
            }
        }
        else
        {
            Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etnuke);
            GlStateManager.scale(0.25f, 0.5f, 0.25f);
            model.renderModel(true);
        }
        GlStateManager.popMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityBomb entity)
	{
		return null;
	}
}

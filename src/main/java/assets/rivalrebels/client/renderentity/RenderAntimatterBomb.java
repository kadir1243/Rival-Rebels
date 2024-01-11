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
import assets.rivalrebels.common.entity.EntityAntimatterBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntimatterBomb extends Render<EntityAntimatterBomb>
{
    public static IModel bomb;

	public RenderAntimatterBomb(RenderManager manager)
	{
        super(manager);
        try {
            bomb = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RivalRebels.MODID, "models/t.obj"));
        } catch (Exception e) {
            RivalRebels.LOGGER.error(e);
        }
    }

	@Override
	public void doRender(EntityAntimatterBomb entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
        GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.scale(RivalRebels.nukeScale,RivalRebels.nukeScale,RivalRebels.nukeScale);
        GlStateManager.rotate(entity.rotationYaw - 90.0f, 0.0F, 1.0F, 0.0F);
        // GlStateManager.rotate(90.0f, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etantimatterbomb);
        //bomb.renderAll();
        GlStateManager.popMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityAntimatterBomb entity)
	{
		return null;
	}
}

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
/*package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityTachyonBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTachyonBomb extends Render<EntityTachyonBomb>
{
    public static IModelCustom bomb;

	public RenderTachyonBomb(RenderManager renderManager)
	{
        super(renderManager);
    	bomb = AdvancedModelLoader.loadModel(new ResourceLocation(RivalRebels.MODID, "models/antimatterBomb.obj"));
	}

    @Override
    public void doRender(EntityTachyonBomb entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glScalef(RivalRebels.nukeScale,RivalRebels.nukeScale,RivalRebels.nukeScale);
		GL11.glRotatef(entity.rotationYaw - 90.0f, 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(90.0f, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.ettachyonbomb);
    	bomb.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTachyonBomb entity)
	{
		return null;
	}
}
*/

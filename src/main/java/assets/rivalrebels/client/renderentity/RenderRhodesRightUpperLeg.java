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

import assets.rivalrebels.common.entity.EntityRhodesRightUpperLeg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderRhodesRightUpperLeg extends Render
{
	public RenderRhodesRightUpperLeg(RenderManager renderManager) {
        super(renderManager);
    }

	public void renderRhodes(EntityRhodesRightUpperLeg rhodes, double x, double y, double z, float par8, float ptt)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glScalef(rhodes.scale, rhodes.scale, rhodes.scale);
		GL11.glColor3f(RenderRhodes.colors[rhodes.color*3], RenderRhodes.colors[rhodes.color*3+1], RenderRhodes.colors[rhodes.color*3+2]);
		Minecraft.getMinecraft().renderEngine.bindTexture(RenderRhodes.texture);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(rhodes.rotationYaw, 0, 1, 0);
		GL11.glRotatef(rhodes.rotationPitch, 1, 0, 0);
		GL11.glTranslatef(-3, 5f, 0);
		RenderRhodes.thigh.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderRhodes((EntityRhodesRightUpperLeg) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}*/

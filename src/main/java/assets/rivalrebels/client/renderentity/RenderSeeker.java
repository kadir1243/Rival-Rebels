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
import assets.rivalrebels.client.model.ModelRocket;
import assets.rivalrebels.common.entity.EntitySeekB83;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSeeker extends Render<EntitySeekB83>
{
	ModelRocket	md;

	public RenderSeeker(RenderManager renderManager)
	{
        super(renderManager);
		md = new ModelRocket();
	}

    @Override
    public void doRender(EntitySeekB83 rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(rocket.rotationYaw - 90.0f, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rocket.rotationPitch - 90.0f, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etrocketseek202);
		md.render(true);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySeekB83 entity) {
		return null;
	}
}

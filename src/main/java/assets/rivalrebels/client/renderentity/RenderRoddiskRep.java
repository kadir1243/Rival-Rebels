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

import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import assets.rivalrebels.common.entity.EntityRoddiskRep;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderRoddiskRep extends Render<EntityRoddiskRep> {
	private int er = 0;
	private final ModelDisk	model;

	public RenderRoddiskRep(RenderManager renderManager) {
        super(renderManager);
		model = new ModelDisk();
	}

    @Override
    public void doRender(EntityRoddiskRep erd, double x, double y, double z, float entityYaw, float partialTicks) {
		er += 13.46;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glScalef(0.4f, 0.4f, 0.4f);
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glPushMatrix();
		GL11.glRotatef(erd.rotationPitch, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(erd.rotationYaw - 90.0f + er, 0.0F, 1.0F, 0.0F);

		model.render();
		model.render();
		model.render();

		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRoddiskRep entity)
	{
		return null;
	}
}

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

import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntitySphereBlast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderSphereBlast extends Render<EntitySphereBlast>
{
	private ModelBlastSphere	modelsphere;

	public RenderSphereBlast(RenderManager manager) {
        super(manager);
		modelsphere = new ModelBlastSphere();
	}

	@Override
	public void doRender(EntitySphereBlast tsar, double x, double y, double z, float var8, float var9) {
		tsar.time++;
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		double elev = ((MathHelper.sin(tsar.time/40f)+1.5f)*10);
		GlStateManager.translate(x, y + elev, z);
		GlStateManager.pushMatrix();
		GlStateManager.rotate((float) (elev * 2), 0, 1, 0);
		GlStateManager.rotate((float) (elev * 3), 1, 0, 0);
		modelsphere.renderModel((float) elev, 1, 0.25f, 0, 1f);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.rotate((float) (elev * -2), 0, 1, 0);
		GlStateManager.rotate((float) (elev * 4), 0, 0, 1);
		modelsphere.renderModel((float) (elev - 0.2f), 1, 0.5f, 0, 1f);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.rotate((float) (elev * -3), 1, 0, 0);
		GlStateManager.rotate((float) (elev * 2), 0, 0, 1);
		modelsphere.renderModel((float) (elev - 0.4f), 1, 0, 0, 1f);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.rotate((float) (elev * -1), 0, 1, 0);
		GlStateManager.rotate((float) (elev * 3), 0, 0, 1);
		modelsphere.renderModel((float) (elev - 0.6f), 1, 1, 0, 1);
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySphereBlast entity)
	{
		return null;
	}
}

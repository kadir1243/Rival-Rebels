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
import assets.rivalrebels.client.model.ModelAntimatterBombBlast;
import assets.rivalrebels.client.model.ModelBlastRing;
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntityAntimatterBombBlast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderAntimatterBombBlast extends Render<EntityAntimatterBombBlast>
{
	private ModelBlastSphere modelsphere;
	private ModelBlastRing modelring;
	private ModelAntimatterBombBlast modelabomb;

	public RenderAntimatterBombBlast(RenderManager manager)
	{
        super(manager);
        modelsphere = new ModelBlastSphere();
		modelabomb = new ModelAntimatterBombBlast();
		modelring = new ModelBlastRing();
	}

	@Override
	public void doRender(EntityAntimatterBombBlast tsar, double x, double y, double z, float var8, float var9) {
        Random random = tsar.getEntityWorld().rand;
        tsar.time++;
		double radius = (((tsar.motionX * 10) - 1) * ((tsar.motionX * 10) - 1) * 2) + RivalRebels.tsarBombaStrength;
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.scale(RivalRebels.shroomScale,RivalRebels.shroomScale,RivalRebels.shroomScale);
		GlStateManager.color(0.0f, 0.0f, 0.2f);
		float size = (tsar.time % 100) * 2.0f;
		modelring.renderModel(size, 64, 6f, 2f, 0f, 0f, 0f, (float)x, (float)y, (float)z);
		GlStateManager.popMatrix();
		if (tsar.time < 60)
		{
			double elev = tsar.time / 5f;
			GlStateManager.translate(x, y + elev, z);
			modelsphere.renderModel(tsar.time, 1, 1, 1, 1);
		}
		else
		{
			//double elev = Math.sin(tsar.time * 0.1f) * 5.0f + 60.0f;
			//double noisy = 5.0f;
			//double hnoisy = noisy * 0.5f;
			GlStateManager.translate(x, y, z);
			GlStateManager.scale(radius * 0.06f, radius * 0.06f, radius * 0.06f);
			GlStateManager.color(1.0f, 1.0f, 1.0f);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etantimatterblast);
			modelabomb.render();
			/*modelsphere.renderModel(50.0f, 0.0f, 0.0f, 0.0f, 1.0f, false);
			GlStateManager.pushMatrix();
			//GlStateManager.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			GlStateManager.rotate((float) (elev * 2), 0, 1, 0);
			GlStateManager.rotate((float) (elev * 3), 1, 0, 0);
			modelsphere.renderModel((float) elev, 0.2f, 0.6f, 1, 1f);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			//GlStateManager.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			GlStateManager.rotate((float) (elev * -2), 0, 1, 0);
			GlStateManager.rotate((float) (elev * 4), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.2f), 0.6f, 0.2f, 1, 1f);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			//GlStateManager.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			GlStateManager.rotate((float) (elev * -3), 1, 0, 0);
			GlStateManager.rotate((float) (elev * 2), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.4f), 0.4f, 0, 1, 1f);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			//GlStateManager.translate(random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy, random.nextDouble() * noisy - hnoisy);
			GlStateManager.rotate((float) (elev * -1), 0, 1, 0);
			GlStateManager.rotate((float) (elev * 3), 0, 0, 1);
			modelsphere.renderModel((float) (elev - 0.6f), 0, 0.4f, 1, 1);
			GlStateManager.popMatrix();*/
			///summon rivalrebels.rivalrebelsentity51 ~ ~-2 ~ {charge:5}
		}
		GlStateManager.popMatrix();
		if (RivalRebels.antimatterFlash) {
			int ran = (int) (random.nextDouble() * 10f - 5f);
			for (int i = 0; i < ran; i++) {
				GlStateManager.popMatrix();
			}
			for (int i = -5; i < 0; i++) {
				GlStateManager.pushMatrix();
			}
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			GlStateManager.scale(random.nextDouble(), random.nextDouble(), random.nextDouble());
			GlStateManager.rotate(random.nextFloat() * 360.0f, random.nextFloat(), random.nextFloat(), random.nextFloat());
			GlStateManager.translate(random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f, random.nextDouble() * 10.0f - 5.0f);
			modelsphere.renderModel(tsar.time, (float)random.nextDouble(), (float)random.nextDouble(), (float)random.nextDouble(), 1);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAntimatterBombBlast entity)
	{
		return null;
	}
}

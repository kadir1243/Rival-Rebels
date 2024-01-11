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
package assets.rivalrebels.client.tileentityrender;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.client.model.ModelReactor;
import assets.rivalrebels.client.model.RenderLibrary;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.tileentity.TileEntityMachineBase;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityReactorRenderer extends TileEntitySpecialRenderer<TileEntityReactor>
{
	private ModelReactor	mr;
	private ModelLaptop		ml;
	private ModelFromObj	mo;

	public TileEntityReactorRenderer()
	{
		mr = new ModelReactor();
		ml = new ModelLaptop();
        mo = ModelFromObj.readObjFile("a.obj");
	}

    @Override
    public void render(TileEntityReactor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.enableLighting();
		int var9 = te.getBlockMetadata();
		short var11 = 0;
		if (var9 == 2)
		{
			var11 = 180;
		}

		if (var9 == 3)
		{
			var11 = 0;
		}

		if (var9 == 4)
		{
			var11 = -90;
		}

		if (var9 == 5)
		{
			var11 = 90;
		}
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.1875F, (float) z + 0.5F);
		GlStateManager.rotate(var11, 0.0F, 1.0F, 0.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etlaptop);
		ml.renderModel((float) -te.slide);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etscreen);
		ml.renderScreen((float) -te.slide);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GlStateManager.rotate(var11, 0.0F, 1.0F, 0.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etreactor);
		mr.renderModel();
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etelectrode);
		GlStateManager.translate(0, 2, -0.125f);
		GlStateManager.scale(0.2f, 0.2f, 0.2f);
		mo.render();
		GlStateManager.popMatrix();
		for (int i = 0; i < te.machines.size(); i++)
		{
			TileEntityMachineBase temb = (TileEntityMachineBase) te.machines.get(i);
			if (temb.powerGiven > 0)
			{
				float radius = (temb.powerGiven * temb.powerGiven) / 40000;
				radius += 0.03;
				int steps = 2;
				if (radius > 0.05) steps++;
				if (radius > 0.10) steps++;
				if (radius > 0.15) steps++;
				if (radius > 0.25) radius = 0.25f;
				// if (steps == 2 && temb.world.rand.nextInt(5) != 0) return;
				RenderLibrary.instance.renderModel((float) x + 0.5f, (float) y + 2.5f, (float) z + 0.5f, temb.getPos().getX() - te.getPos().getX(), temb.getPos().getY() - te.getPos().getY() - 2.5f, temb.getPos().getZ() - te.getPos().getZ(), 0.5f, radius, steps, (temb.edist / 2), 0.1f, 0.45f, 0.45f, 0.5f, 0.5f);
			}
		}
	}
}

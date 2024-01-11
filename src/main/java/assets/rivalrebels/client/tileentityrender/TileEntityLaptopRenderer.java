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
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityLaptopRenderer extends TileEntitySpecialRenderer<TileEntityLaptop> {
	private final ModelLaptop model;

	public TileEntityLaptopRenderer()
	{
		model = new ModelLaptop();
	}

    @Override
    public void render(TileEntityLaptop te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.disableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y, (float) z + 0.5F);
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
		GlStateManager.rotate(var11, 0.0F, 1.0F, 0.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etlaptop);
		model.renderModel((float) -te.slide);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etubuntu);
		model.renderScreen((float) -te.slide);
		GlStateManager.popMatrix();
	}
}

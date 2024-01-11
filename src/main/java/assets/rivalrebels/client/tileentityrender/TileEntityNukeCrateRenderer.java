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
import assets.rivalrebels.client.model.ModelNukeCrate;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntityNukeCrateRenderer extends TileEntitySpecialRenderer<TileEntityNukeCrate>
{
	private ModelNukeCrate	model;

	public TileEntityNukeCrateRenderer()
	{
		model = new ModelNukeCrate();
	}

    @Override
    public void render(TileEntityNukeCrate te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		int metadata = te.getBlockMetadata();
		if (metadata == 0)
		{
			GlStateManager.rotate(180, 1, 0, 0);
		}

		if (metadata == 2)
		{
			GlStateManager.rotate(-90, 1, 0, 0);
		}

		if (metadata == 3)
		{
			GlStateManager.rotate(90, 1, 0, 0);
		}

		if (metadata == 4)
		{
			GlStateManager.rotate(90, 0, 0, 1);
		}

		if (metadata == 5)
		{
			GlStateManager.rotate(-90, 0, 0, 1);
		}
		if (te.getWorld().getBlockState(te.getPos()).getBlock() == RivalRebels.nukeCrateBottom) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btnukebottom);
		if (te.getWorld().getBlockState(te.getPos()).getBlock() == RivalRebels.nukeCrateTop) Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btnuketop);
		model.renderModelA();
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.btcrate);
		model.renderModelB();
        GlStateManager.popMatrix();
	}
}

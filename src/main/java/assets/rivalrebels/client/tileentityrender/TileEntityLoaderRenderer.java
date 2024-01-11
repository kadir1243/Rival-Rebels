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
import assets.rivalrebels.client.model.ModelLoader;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.tileentity.TileEntityLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityLoaderRenderer extends TileEntitySpecialRenderer<TileEntityLoader>
{
	private ModelLoader		loaderModel;
	private ModelFromObj	tube;

	public TileEntityLoaderRenderer()
	{
		loaderModel = new ModelLoader();
        tube = ModelFromObj.readObjFile("l.obj");
	}

    @Override
    public void render(TileEntityLoader te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etloader);
		int var9 = te.getBlockMetadata();
		short var11 = 0;
		if (var9 == 2)
		{
			var11 = 90;
		}

		if (var9 == 3)
		{
			var11 = -90;
		}

		if (var9 == 4)
		{
			var11 = 180;
		}

		if (var9 == 5)
		{
			var11 = 0;
		}

		GlStateManager.rotate(var11, 0.0F, 1.0F, 0.0F);
		loaderModel.renderA();
		loaderModel.renderB((float) te.slide);
		GlStateManager.popMatrix();
		for (int i = 0; i < te.machines.size(); i++)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			int xdif = te.machines.get(i).getPos().getX() - te.getPos().getX();
			int zdif = te.machines.get(i).getPos().getZ() - te.getPos().getZ();
			GlStateManager.rotate((float) (-90 + (Math.atan2(xdif, zdif) / Math.PI) * 180F), 0, 1, 0);
			GlStateManager.translate(-1f, -0.40f, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.ettube);
			GlStateManager.scale(0.5, 0.15, 0.15);
			int dist = (int) Math.sqrt((xdif * xdif) + (zdif * zdif));
			for (int d = 0; d < dist; d++)
			{
				GlStateManager.translate(2, 0, 0);
				tube.render();
			}
			GlStateManager.popMatrix();
		}
	}
}

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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.tileentity.TileEntityReciever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRecieverRenderer extends TileEntitySpecialRenderer<TileEntityReciever>
{
	public static ModelFromObj	base;
	public static ModelFromObj	arm;
	public static ModelFromObj	adsdragon;

	public TileEntityRecieverRenderer()
	{
        base = ModelFromObj.readObjFile("p.obj");
        arm = ModelFromObj.readObjFile("q.obj");
        adsdragon = ModelFromObj.readObjFile("r.obj");
	}

    @Override
    public void render(TileEntityReciever te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etreciever);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y, z + 0.5);
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		int m = te.getBlockMetadata();
		short r = 0;

		if (m == 2) r = 0;
		if (m == 3) r = 180;
		if (m == 4) r = 90;
		if (m == 5) r = -90;

		GlStateManager.pushMatrix();
		GlStateManager.rotate(r, 0, 1, 0);
        GlStateManager.translate(0, 0, 0.5);
		base.render();
		if (te.hasWeapon)
		{
            GlStateManager.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			GlStateManager.rotate((float) (te.yaw - r), 0, 1, 0);
			arm.render();
            GlStateManager.rotate((float) te.pitch, 1, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etadsdragon);
			adsdragon.render();
		}
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}

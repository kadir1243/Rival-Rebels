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
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileEntityRecieverRenderer extends TileEntitySpecialRenderer<TileEntityReciever>
{
	public static ModelFromObj	base;
	public static ModelFromObj	arm;
	public static ModelFromObj	adsdragon;

	public TileEntityRecieverRenderer()
	{
		try
		{
			base = ModelFromObj.readObjFile("tray.obj");
			arm = ModelFromObj.readObjFile("q.obj");
			adsdragon = ModelFromObj.readObjFile("dragon.obj");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    @Override
    public void renderTileEntityAt(TileEntityReciever tile, double x, double y, double z, float partialTicks, int destroyStage) {
        Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etreciever);
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		int m = tile.getBlockMetadata();
		short r = 0;

		if (m == 2) r = 0;
		if (m == 3) r = 180;
		if (m == 4) r = 90;
		if (m == 5) r = -90;

		GL11.glPushMatrix();
		GL11.glRotatef(r, 0, 1, 0);
		GL11.glTranslated(0, 0, 0.5);
		base.render();
		if (tile.hasWeapon)
		{
			GL11.glTranslated(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			GL11.glRotated(tile.yaw - r, 0, 1, 0);
			arm.render();
			GL11.glRotated(tile.pitch, 1, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etadsdragon);
			adsdragon.render();
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}

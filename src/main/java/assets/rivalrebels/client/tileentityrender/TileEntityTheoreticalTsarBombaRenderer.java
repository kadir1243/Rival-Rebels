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

import assets.rivalrebels.client.model.ModelTheoreticalTsarBomba;
import assets.rivalrebels.common.tileentity.TileEntityTheoreticalTsarBomba;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileEntityTheoreticalTsarBombaRenderer extends TileEntitySpecialRenderer<TileEntityTheoreticalTsarBomba>
{
	private final ModelTheoreticalTsarBomba	model;

	public TileEntityTheoreticalTsarBombaRenderer()
	{
		model = new ModelTheoreticalTsarBomba();
	}

    @Override
    public void renderTileEntityAt(TileEntityTheoreticalTsarBomba tile, double x, double y, double z, float partialTicks, int destroyStage) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1F, (float) z + 0.5F);
		GL11.glScalef(1.3f, 1.3f, 1.3f);
		int metadata = tile.getBlockMetadata();

		if (metadata == 2)
		{
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glRotatef(90, 1, 0, 0);
		}

		if (metadata == 3)
		{
			GL11.glRotatef(90, 1, 0, 0);
		}

		if (metadata == 4)
		{
			GL11.glRotatef(-90, 0, 1, 0);
			GL11.glRotatef(90, 1, 0, 0);
		}

		if (metadata == 5)
		{
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glRotatef(90, 1, 0, 0);
		}
		model.render();
		GL11.glPopMatrix();
	}
}
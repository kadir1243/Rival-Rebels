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
import assets.rivalrebels.client.model.ModelObjective;
import assets.rivalrebels.common.tileentity.TileEntityOmegaObjective;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityOmegaObjectiveRenderer extends TileEntitySpecialRenderer<TileEntityOmegaObjective>
{
	private ModelObjective	loaderModel;

	public TileEntityOmegaObjectiveRenderer()
	{
		loaderModel = new ModelObjective();
	}

    @Override
    public void render(TileEntityOmegaObjective te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etomegaobj);

		GlStateManager.rotate(90, 1, 0, 0);
		loaderModel.renderA();
		GlStateManager.rotate(-90, 1, 0, 0);
		GlStateManager.rotate(90, 0, 0, 1);
		loaderModel.renderB((float) te.slide, 96f / 256f, 44f / 128f, 0.125f, 0.84375f);
		GlStateManager.rotate(-90, 0, 0, 1);
		loaderModel.renderB((float) te.slide, 32f / 256f, 44f / 128f, 0.625f, 0.84375f);
		GlStateManager.rotate(90, 0, 1, 0);
		loaderModel.renderB((float) te.slide, 96f / 256f, 108f / 128f, 0.625f, 0.84375f);
		GlStateManager.rotate(90, 0, 1, 0);
		loaderModel.renderB((float) te.slide, 160f / 256f, 44f / 128f, 0.625f, 0.84375f);
		GlStateManager.rotate(90, 0, 1, 0);
		loaderModel.renderB((float) te.slide, 224f / 256f, 108f / 128f, 0.625f, 0.84375f);
		GlStateManager.rotate(90, 0, 1, 0);
		GlStateManager.rotate(-90, 0, 0, 1);
		loaderModel.renderB((float) te.slide, 224f / 256f, 44f / 128f, 0.625f, 0.84375f);
		GlStateManager.popMatrix();
	}
}

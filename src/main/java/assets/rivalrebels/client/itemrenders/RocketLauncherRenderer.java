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
package assets.rivalrebels.client.itemrenders;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.model.ModelRocketLauncherBody;
import assets.rivalrebels.client.model.ModelRocketLauncherHandle;
import assets.rivalrebels.client.model.ModelRocketLauncherTube;
import assets.rivalrebels.client.tileentityrender.TileEntityForceFieldNodeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class RocketLauncherRenderer extends TileEntityItemStackRenderer
{
	ModelRocketLauncherHandle	md2;
	ModelRocketLauncherBody		md3;
	ModelRocketLauncherTube		md4;

	public RocketLauncherRenderer()
	{
		md2 = new ModelRocketLauncherHandle();
		md3 = new ModelRocketLauncherBody();
		md4 = new ModelRocketLauncherTube();
	}

    @Override
    public void renderByItem(ItemStack item) {
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.translate(0.4f, 0.35f, -0.03f);
		GlStateManager.rotate(-55, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(0f, 0.05f, 0.05f);
		/*if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) GlStateManager.scale(1, 1, -1);*/
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.22f, -0.025f, 0f);
		GlStateManager.rotate(90, 0.0F, 0.0F, 1.0F);
		GlStateManager.scale(0.03125f, 0.03125f, 0.03125f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etrocketlauncherhandle);
		md2.render();
		if (item.isItemEnchanted())
		{
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			md2.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f, 0.31f, 0f);
		GlStateManager.rotate(90, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.4f, 0.4f, 0.4f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etrocketlauncherbody);
		md3.render();
		if (item.isItemDamaged())
		{
			GlStateManager.bindTexture(TileEntityForceFieldNodeRenderer.id[(int) ((TileEntityForceFieldNodeRenderer.getTime() / 100) % TileEntityForceFieldNodeRenderer.frames)]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableLighting();
			md3.render();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();

		float s = 0.0812f;

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f + s, 0.71f, s);
		GlStateManager.scale(0.15f, 0.1f, 0.15f);
		Minecraft.getMinecraft().renderEngine.bindTexture(RivalRebels.etrocketlaunchertube);
		md4.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f - s, 0.71f, s);
		GlStateManager.scale(0.15f, 0.1f, 0.15f);
		md4.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f + s, 0.71f, -s);
		GlStateManager.scale(0.15f, 0.1f, 0.15f);
		md4.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f - s, 0.71f, -s);
		GlStateManager.scale(0.15f, 0.1f, 0.15f);
		md4.render();
		GlStateManager.popMatrix();

		// ---

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f + s, -0.285f, s);
		GlStateManager.scale(0.15f, -0.1f, 0.15f);
		md4.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f - s, -0.285f, s);
		GlStateManager.scale(0.15f, -0.1f, 0.15f);
		md4.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f + s, -0.285f, -s);
		GlStateManager.scale(0.15f, -0.1f, 0.15f);
		md4.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.07f - s, -0.285f, -s);
		GlStateManager.scale(0.15f, -0.1f, 0.15f);
		md4.render();
		GlStateManager.popMatrix();

		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
}


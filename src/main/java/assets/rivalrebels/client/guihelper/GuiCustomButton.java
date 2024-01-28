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
package assets.rivalrebels.client.guihelper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraftforge.client.gui.GuiUtils.drawTexturedModalRect;

@OnlyIn(Dist.CLIENT)
public class GuiCustomButton extends ButtonWidget
{
	Rectangle			bbox;
	Vector				tbox;
	Identifier	resloc;
	boolean				toggleable;
	public boolean		isPressed	= false;
	public boolean		wasPressed	= false;
	public boolean		mouseDown	= false;

	public GuiCustomButton(Rectangle rec, Identifier rl, Vector uv, boolean isToggle)
	{
		super(rec.xMin, rec.yMin, rec.xMax - rec.xMin, rec.yMax - rec.yMin, Text.of(""), button -> {});
		bbox = rec;
		tbox = uv;
		resloc = rl;
		toggleable = isToggle;
	}

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		boolean current = MinecraftClient.getInstance().mouse.wasLeftButtonClicked() && bbox.isVecInside(new Vector(mouseX, mouseY));
		wasPressed = false;
		if (toggleable && current && !mouseDown)
		{
			mouseDown = true;
			isPressed = !isPressed;
			wasPressed = true;
		}
		else if (toggleable && !current)
		{
			mouseDown = false;
		}
		else if (!toggleable && current)
		{
			isPressed = true;
		}
		else if (!toggleable && !current)
		{
			isPressed = false;
		}

		if (isPressed)
		{
			MinecraftClient.getInstance().getTextureManager().bindTexture(resloc);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(matrices, bbox.xMin, bbox.yMin, tbox.x, tbox.y, bbox.xMax - bbox.xMin, bbox.yMax - bbox.yMin, getZOffset());
		}
	}
}

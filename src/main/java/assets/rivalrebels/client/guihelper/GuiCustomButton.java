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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public class GuiCustomButton extends Button
{
	Rectangle			bbox;
    Vector2i				tbox;
	ResourceLocation	resloc;
	boolean				toggleable;
	public boolean		isPressed	= false;
	public boolean		wasPressed	= false;
	public boolean		mouseDown	= false;

    public GuiCustomButton(Rectangle rec, ResourceLocation rl, Vector2i uv, boolean isToggle) {
        this(rec, rl, uv, isToggle, button -> {});
    }

	public GuiCustomButton(Rectangle rec, ResourceLocation rl, Vector2i uv, boolean isToggle, Button.OnPress onPress) {
		super(rec.xMin, rec.yMin, rec.xMax - rec.xMin, rec.yMax - rec.yMin, Component.empty(), onPress, DEFAULT_NARRATION);
		bbox = rec;
		tbox = uv;
		resloc = rl;
		toggleable = isToggle;
	}

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		boolean current = Minecraft.getInstance().mouseHandler.isLeftPressed() && bbox.isVecInside(new Vector2i(mouseX, mouseY));
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

		if (isPressed) {
			context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            context.blit(resloc, bbox.xMin, bbox.yMin, tbox.x, tbox.y, bbox.xMax - bbox.xMin, bbox.yMax - bbox.yMin);
		}
	}
}

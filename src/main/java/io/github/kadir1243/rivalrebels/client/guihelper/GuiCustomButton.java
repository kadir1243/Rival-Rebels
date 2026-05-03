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
package io.github.kadir1243.rivalrebels.client.guihelper;

import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;

@OnlyIn(Dist.CLIENT)
public class GuiCustomButton extends Button {
    ScreenRectangle bbox;
    Vector2i tbox;
    private final RRTextures.Texture resloc;
	boolean				toggleable;
	public boolean	 isPressed	= false;
	public boolean	 wasPressed	= false;
	public boolean	 mouseDown	= false;

    public GuiCustomButton(ScreenRectangle rec, RRTextures.Texture rl, Vector2i uv, boolean isToggle) {
        this(rec, rl, uv, isToggle, button -> {});
    }

	public GuiCustomButton(ScreenRectangle rec, RRTextures.Texture rl, Vector2i uv, boolean isToggle, Button.OnPress onPress) {
		super(rec.position().x(), rec.position().y(), rec.width(), rec.height(), Component.empty(), onPress, DEFAULT_NARRATION);
		bbox = rec;
		tbox = uv;
		resloc = rl;
		toggleable = isToggle;
	}

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        boolean current = Minecraft.getInstance().mouseHandler.isLeftPressed() && bbox.containsPoint(mouseX, mouseY);
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
            resloc.blit(graphics, bbox.position().x(), bbox.position().y(), tbox.x, tbox.y, bbox.width(), bbox.height(), CommonColors.WHITE);
		}
	}
}

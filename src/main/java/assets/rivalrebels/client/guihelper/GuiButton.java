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

import assets.rivalrebels.RRIdentifiers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class GuiButton extends Button {
    public GuiButton(int x, int y, int width, int height, Component message) {
        this(x, y, width, height, message, button -> {});
    }

    public GuiButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		if (this.visible) {
            Minecraft client = Minecraft.getInstance();
			this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
			int k = this.getYImage(this.isHovered);
			context.blit(RRIdentifiers.guitbutton, this.getX(), this.getY(), 5, k * 11, this.width, this.height);
			this.mouseDragged(mouseX, mouseY, 0, 0, 0);
			int l = 0xffffff;

			if (!this.active) {
				l = 0xcccccc;
			} else if (this.isHovered) {
				l = 0x88e8ff;
			}

            context.drawCenteredString(client.font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 7) / 2, l);
		}
	}

    protected int getYImage(boolean mouseOver) {
        int i = 1;

        if (!this.active) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }
}

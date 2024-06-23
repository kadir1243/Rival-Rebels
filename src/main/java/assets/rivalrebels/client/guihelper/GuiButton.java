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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class GuiButton extends ButtonWidget {
    public GuiButton(int x, int y, int width, int height, String message) {
		this(x, y, width, height, Text.of(message), button -> {});
	}

    public GuiButton(int x, int y, int width, int height, Text message) {
        this(x, y, width, height, message, button -> {});
    }

    public GuiButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		if (this.visible) {
            MinecraftClient client = MinecraftClient.getInstance();
			context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
			int k = this.getYImage(this.hovered);
			context.drawTexture(RRIdentifiers.guitbutton, this.getX(), this.getY(), 5, k * 11, this.width, this.height);
			this.mouseDragged(mouseX, mouseY, 0, 0, 0);
			int l = 0xffffff;

			if (!this.active) {
				l = 0xcccccc;
			} else if (this.hovered) {
				l = 0x88e8ff;
			}

            context.drawCenteredTextWithShadow(client.textRenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 7) / 2, l);
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

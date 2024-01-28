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
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButton extends ButtonWidget {
    public GuiButton(int x, int y, int width, int height, String message) {
		this(x, y, width, height, Text.of(message), button -> {});
	}

    public GuiButton(int x, int y, int width, int height, Text message) {
        this(x, y, width, height, message, button -> {});
    }

    public GuiButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (this.visible) {
            MinecraftClient client = MinecraftClient.getInstance();
			TextRenderer fontrenderer = client.textRenderer;
			client.textureManager.bindTexture(RRIdentifiers.guitbutton);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int k = this.getYImage(this.hovered);
			this.drawTexture(matrices, this.x, this.y, 5, k * 11, this.width, this.height);
			this.mouseDragged(mouseX, mouseY, 0, 0, 0);
			int l = 0xffffff;

			if (!this.active)
			{
				l = 0xcccccc;
			}
			else if (this.hovered)
			{
				l = 0x88e8ff;
			}

			drawCenteredText(matrices, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 7) / 2, l);
		}
	}
}

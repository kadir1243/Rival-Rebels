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

import com.mojang.blaze3d.platform.InputConstants;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.util.Mth;

@OnlyIn(Dist.CLIENT)
public class GuiScroll extends GuiButton {
	/** Scroll limit */
	public float limit;

	/** Current scroll amount (from the top down) */
	public float scroll;

    public GuiScroll(Builder builder, float limit) {
        super(builder);
        this.limit = limit;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		int state = 0;
		if (mouseClicked(new MouseButtonEvent(mouseX, mouseY, new MouseButtonInfo(InputConstants.MOUSE_BUTTON_LEFT, 0)), false)) state = 11;
        RRTextures.guitbutton.blit(graphics, this.getX(), (int) (this.getY() + scroll), 0, state, this.width, this.height, CommonColors.WHITE);
	}

    @Override
    protected void onDrag(MouseButtonEvent event, double mouseX, double mouseY) {
        scroll = (float) (mouseY - getY() - 5);
        scroll = Mth.clamp(scroll, 0, limit);
    }

	public float getScroll() {
		return scroll;
	}
}

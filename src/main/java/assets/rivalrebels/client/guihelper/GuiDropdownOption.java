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

import assets.rivalrebels.client.gui.GuiTray;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class GuiDropdownOption extends ButtonWidget
{
	Rectangle		bbox;
	public boolean	isPressed	= false;
	public boolean	mouseDown	= false;
	public GuiTray	t;

	public GuiDropdownOption(Vector p, int l, int n, Text text, GuiTray tray)
	{
		super(p.x, p.y + n * 10, l, (n + 1) * 10, text, button -> {}, DEFAULT_NARRATION_SUPPLIER);
		bbox = new Rectangle(p.x, p.y + n * 10, l, (n + 1) * 10);
		t = tray;
	}

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		boolean inside = bbox.isVecInside(new Vector(mouseX, mouseY));
		boolean current = MinecraftClient.getInstance().mouse.wasLeftButtonClicked() && inside;
		boolean on = t.getScreenHandler().hasWepReqs();
		if (current && !mouseDown && on) isPressed = !isPressed;
		int color = 0x999999;
		if (on)
		{
			int team = 0;
			if (t.getScreenHandler().getSlot(6).hasStack() && t.getScreenHandler().getSlot(6).getStack().hasNbt())
			{
				team = t.getScreenHandler().getSlot(6).getStack().getNbt().getInt("team");
			}
			if (team == 0) color = 0xffff55;
			if (team == 1) color = 0x55ff55;
			if (team == 2) color = 0x5555ff;
		}
		if (inside && on) color = 0xffffff;
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, getMessage(), bbox.xMin + 1, bbox.yMin + 1, color);
		mouseDown = current;
	}
}

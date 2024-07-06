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
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public class GuiDropdownOption extends Button {
	Rectangle		bbox;
	public boolean	isPressed	= false;
	public boolean	mouseDown	= false;
	public GuiTray	t;

	public GuiDropdownOption(Vector2i p, int l, int n, Component text, GuiTray tray) {
		super(p.x, p.y + n * 10, l, (n + 1) * 10, text, button -> {}, DEFAULT_NARRATION);
		bbox = new Rectangle(p.x, p.y + n * 10, l, (n + 1) * 10);
		t = tray;
	}

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		boolean inside = bbox.isVecInside(new Vector2i(mouseX, mouseY));
		boolean current = Minecraft.getInstance().mouseHandler.isLeftPressed() && inside;
		boolean on = t.getMenu().hasWepReqs();
		if (current && !mouseDown && on) isPressed = !isPressed;
		int color = 0x999999;
		if (on) {
			RivalRebelsTeam team = RivalRebelsTeam.NONE;
			if (t.getMenu().getSlot(6).hasItem() && t.getMenu().getSlot(6).getItem().has(RRComponents.CHIP_DATA)) {
				team = t.getMenu().getSlot(6).getItem().get(RRComponents.CHIP_DATA).team();
			}
            color = switch (team) {
                case NONE -> 0xffff55;
                case OMEGA -> 0x55ff55;
                case SIGMA -> 0x5555ff;
            };
		}
		if (inside && on) color = 0xffffff;
        context.drawCenteredString(Minecraft.getInstance().font, getMessage(), bbox.xMin + 1, bbox.yMin + 1, color);
		mouseDown = current;
	}
}

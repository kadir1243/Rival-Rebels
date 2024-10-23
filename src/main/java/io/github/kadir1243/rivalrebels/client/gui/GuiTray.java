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

package io.github.kadir1243.rivalrebels.client.gui;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiCustomButton;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiDropdownOption;
import io.github.kadir1243.rivalrebels.client.guihelper.GuiRotor;
import io.github.kadir1243.rivalrebels.client.guihelper.Rectangle;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.container.ContainerReciever;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Vector2i;

@OnlyIn(Dist.CLIENT)
public class GuiTray extends AbstractContainerScreen<ContainerReciever> {
    private float				xSize_lo;
	private float				ySize_lo;
	GuiRotor range;
	GuiCustomButton chip;
	GuiCustomButton				players;
	GuiCustomButton				mobs;
	GuiDropdownOption select1;

	public GuiTray(ContainerReciever containerReciever, Inventory inventoryPlayer, Component title)
	{
		super(containerReciever, inventoryPlayer, title);
		imageHeight = 206;
	}

	@Override
	public void init() {
		super.init();
		int x = this.leftPos;
		int y = this.topPos;
		range = new GuiRotor(x + 93 - 16, y + 92 - 16, menu.getYawLimit(), Component.literal("Range"));
		chip = new GuiCustomButton(new Rectangle(x + 94, y + 10, 19, 19), RRIdentifiers.guitray, new Vector2i(237, 10), true);
		chip.isPressed = menu.getKTeam();
		players = new GuiCustomButton(new Rectangle(x + 94, y + 28, 19, 19), RRIdentifiers.guitray, new Vector2i(237, 28), true);
		players.isPressed = menu.getKPlayers();
		mobs = new GuiCustomButton(new Rectangle(x + 94, y + 46, 19, 19), RRIdentifiers.guitray, new Vector2i(237, 46), true);
		mobs.isPressed = menu.getKMobs();
		select1 = new GuiDropdownOption(new Vector2i(119 + x, 8 + y), 45, 0, Component.translatable("RivalRebels.ads.dragon"), button -> {
            if (menu.hasWepReqs()) {
                menu.setWep(true);
            }
        }, this);
		addRenderableWidget(range);
		addRenderableWidget(chip);
		addRenderableWidget(players);
		addRenderableWidget(mobs);
		addRenderableWidget(select1);
	}

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.xSize_lo = mouseX;
        this.ySize_lo = mouseY;
        this.renderTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
		if (modifiers == 1) {
            onClose();
            menu.setKMobs(mobs.isPressed);
            menu.setKTeam(chip.isPressed);
            menu.setKPlayers(players.isPressed);
            menu.setYawLimit(range.getDegree() * 2);
		}
        return super.charTyped(chr, modifiers);
	}

	static int	spinfac	= 0;

	public void drawADS(GuiGraphics graphics, int x, int y, int scale, float px, float py)
	{
		spinfac += 1;
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
		matrices.translate(x, y - 40, 50);
		matrices.scale(-scale, scale, scale);
		matrices.mulPose(Axis.ZP.rotationDegrees(180));
		matrices.mulPose(Axis.YP.rotationDegrees(135.0F));
		matrices.mulPose(Axis.YP.rotationDegrees(-135.0F));
		matrices.mulPose(Axis.XP.rotationDegrees(20));
		if (!menu.hasWeapon())
		{
			matrices.translate(0, 0, -0.5f);
			matrices.mulPose(Axis.YP.rotationDegrees(spinfac));
			matrices.translate(0, 0, 0.5f);
		}
		// matrices.mulPose(Axis.XP.rotationDegrees(Math.sin(spinfac/(40 * Math.PI)) * 10));

		// entity.pitch = -((float)Math.atan((double)(py / 40.0F))) * 40.0F;
		// entity.headYaw = (float)Math.atan((double)(px / 40.0F)) * 40.0F;
		// + (Math.sin(spinfac/(40 * Math.PI)) * 10)
		// - (spinfac * 0.5)

		matrices.mulPose(Axis.YP.rotationDegrees(180));
		// matrices.mulPose((spinfac * 0.5), 0, 1, 0);
		matrices.translate(0, -0.5 * 1.5, (-0.5 - 0.34) * -1.5);
        MultiBufferSource vertexConsumers = graphics.bufferSource();
        ObjModels.renderSolid(ObjModels.tray, RRIdentifiers.etreciever, matrices, vertexConsumers, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		if (menu.hasWeapon()) {
			matrices.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			matrices.mulPose(Axis.YP.rotationDegrees((float) (-Math.atan(px / 40.0F) * 40.0F)));
			ObjModels.renderSolid(ObjModels.arm, RRIdentifiers.etreciever, matrices, vertexConsumers, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
			matrices.mulPose(Axis.XP.rotationDegrees((float) (Math.atan(py / 40.0F) * 40.0F + 20)));
			ObjModels.renderSolid(ObjModels.adsdragon, RRIdentifiers.etadsdragon, matrices, vertexConsumers, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		}
        graphics.flush();
        matrices.popPose();
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
        context.blit(RRIdentifiers.guitray, x, y, 0, 0, imageWidth, imageHeight);

		if (menu.getPInR() > 0)
		{
            context.blit(RRIdentifiers.guitray, x + 104, y + 68, 248, 0, 8, 8);
		}

		context.drawString(font, Component.translatable("RivalRebels.ads.tray"), x + 25, y + 66, 0xffffff, false);
		drawADS(context, this.leftPos + 51, this.topPos + 75, 30, this.leftPos + 51 - this.xSize_lo, this.topPos + 25 - this.ySize_lo);
	}
}

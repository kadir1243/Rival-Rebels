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
package assets.rivalrebels.client.gui;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.guihelper.GuiButton;
import assets.rivalrebels.client.guihelper.GuiScroll;
import assets.rivalrebels.common.round.RivalRebelsClass;
import assets.rivalrebels.mixin.client.GuiGraphicsAccessor;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GuiClass extends Screen {
	private static final int xSizeOfTexture	= 256;
	private static final int ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
    private static final float[]		sizelookup		= new float[] { 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f };
	private GuiButton	nextButton;
	private GuiButton	doneButton;
	private GuiScroll	gameScroll;
	private RivalRebelsClass rrclass;

	public GuiClass(RivalRebelsClass rrc) {
        super(Component.empty());
        posX = (width - xSizeOfTexture) / 2;
		posY = (height - ySizeOfTexture) / 2;
		rrclass = rrc;
	}

	@Override
	public void init()
	{
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.clearWidgets();

		nextButton = new GuiButton(posX + 188, posY + 102, 60, 11, Component.translatable("RivalRebels.class.next"), button -> {
            switch (rrclass) {
                case HACKER:
                    rrclass = RivalRebelsClass.REBEL;
                    break;
                case INTEL:
                    rrclass = RivalRebelsClass.HACKER;
                    break;
                case NONE:
                    rrclass = RivalRebelsClass.REBEL;
                    break;
                case NUKER:
                    rrclass = RivalRebelsClass.INTEL;
                    break;
                case REBEL:
                    rrclass = RivalRebelsClass.NUKER;
                    break;
            }
        });
		doneButton = new GuiButton(posX + 188, posY + 119, 60, 11, Component.translatable("RivalRebels.class.done"), button -> this.minecraft.setScreen(new GuiSpawn(rrclass)));
		gameScroll = new GuiScroll(posX + 243, posY + 9, 74);
		this.addRenderableWidget(nextButton);
		this.addRenderableWidget(doneButton);
		this.addRenderableWidget(gameScroll);
	}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        PoseStack pose = graphics.pose();
        if (rrclass == RivalRebelsClass.NONE) rrclass = RivalRebelsClass.REBEL;
        float f = 0.00390625F;
        renderTransparentBackground(graphics);
        graphics.fillGradient(posX, posY, posX + xSizeOfTexture, posY + ySizeOfTexture, CommonColors.BLACK, CommonColors.BLACK);
		drawPanel(graphics, posX + 162, posY + 40, 80, 74, gameScroll.getScroll(), gameScroll.limit, rrclass);
        graphics.fillGradient(posX + 160, posY + 9, posX + 244, posY + 38, 0xFF000000, 0xFF000000);

        ((GuiGraphicsAccessor) graphics).blit(
            RRIdentifiers.guitclass,
            posX,
            posX + xSizeOfTexture,
            posY + ySizeOfTexture,
            posY,
            0,
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f,
            0
        );

        graphics.blit(
            rrclass.resource,
            posX + 12,
            posY + 12,
            128,
            128,
            xSizeOfTexture,
            ySizeOfTexture,
            512,
            512
        );

        float scalefactor = 1.5f;
        pose.scale(scalefactor * 1.2f, scalefactor, scalefactor);
        graphics.drawCenteredString(font, rrclass.name, (int) ((posX + 76) / (scalefactor * 1.2f)), (int) ((posY + 16) / scalefactor), rrclass.color);
        pose.scale(1 / (scalefactor * 1.2f), 1 / scalefactor, 1 / scalefactor);

		scalefactor = 0.666f;
        pose.scale(scalefactor, scalefactor, scalefactor);
        graphics.drawCenteredString(font, rrclass.getMiniDescriptionTranslation(), (int) ((posX + 76) / scalefactor), (int) ((posY + 28) / scalefactor), rrclass.color);
        pose.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		scalefactor = 0.666f;
		pose.scale(scalefactor, scalefactor, scalefactor);
        graphics.drawCenteredString(font, Component.translatable("RivalRebels.class.description"), (int) ((posX + 181) / scalefactor), (int) ((posY + 28) / scalefactor), rrclass.color);
        pose.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		for (int i = 0; i < sizelookup.length; i++)
		{
			int X = posX + 18 + (i % 9) * 22;
			int Y = posY + 158 + 22 * Mth.floor(i / 9D);
			float size = sizelookup[i];
			if (mouseX >= X && mouseY >= Y && mouseX < X + 16 && mouseY < Y + 16)
			{
				if (size < 1.5)
				{
					size += 0.1F;
				}
			}
			else if (size > 1)
			{
				size -= 0.1F;
			}
			sizelookup[i] = size;
		}
		RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		for (int i = rrclass.inventory.length - 1; i >= 0; i--) {
			int X = posX + 18 + (i % 9) * 22;
			int Y = posY + 158 + 22 * (i / 9);
			pose.pushPose();
			pose.translate(X + 8, Y + 8, 0);
			pose.scale(sizelookup[i], sizelookup[i], sizelookup[i]);
			pose.translate(-X - 8, -Y - 8, 0);
			graphics.renderItem(rrclass.inventory[i], X, Y);
			pose.popPose();
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		}
		for (int i = rrclass.inventory.length - 1; i >= 0; i--) {
			int X = posX + 18 + (i % 9) * 22;
			int Y = posY + 158 + 22 * (i / 9);
			pose.pushPose();
			pose.translate(X + 8, Y + 8, 20);
			pose.scale(sizelookup[i], sizelookup[i], sizelookup[i]);
			pose.translate(-X - 8, -Y - 8, 0);
			ItemStack stack = rrclass.inventory[i];
			if (!stack.isEmpty()) graphics.drawString(font, Component.nullToEmpty(String.valueOf(stack.getCount())),X+17-font.width(String.valueOf(stack.getCount())),Y+9,0xFFFFFF);
			if (sizelookup[i] > 1)
			{
                graphics.fillGradient(X + 17, Y + 3, (int) (X + ((font.width(stack.getHoverName()) + 4) * (sizelookup[i] - 1) * 2) + 15), Y + 13, 0xaa111111, 0xaa111111);
				graphics.drawString(font, stack.getHoverName(), X + 18, Y + 4, 0xFFFFFF);
			}
			pose.popPose();
		}

		super.render(graphics, mouseX, mouseY, delta);
	}

    protected void drawPanel(GuiGraphics context, int x, int y, int width, int height, float scroll, float scrolllimit, RivalRebelsClass rrclass) {
		int length = 10;
		int dist = (int) (-(scroll / scrolllimit) * (((length) * 10) - height));
		float scalefactor = 0.6666f;
        PoseStack matrices = context.pose();
        matrices.scale(scalefactor, scalefactor, scalefactor);
		context.drawWordWrap(font, rrclass.getDescriptionTranslation(), (int) (x * 1.5), (int) ((y + dist) * 1.5), (int) (width * 1.5), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
	}
}

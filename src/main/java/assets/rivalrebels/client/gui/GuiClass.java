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
import assets.rivalrebels.mixin.client.DrawContextAccessor;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.item.ItemStack;

public class GuiClass extends Screen
{
	private static final int xSizeOfTexture	= 256;
	private static final int ySizeOfTexture	= 256;
	private int			posX;
	private int			posY;
    private float[]		sizelookup		= new float[] { 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f };
	private GuiButton	nextButton;
	private GuiButton	doneButton;
	private GuiScroll	gameScroll;
	private boolean		prevClick		= true;
	private RivalRebelsClass rrclass = RivalRebelsClass.NONE;

	public GuiClass(RivalRebelsClass rrc)
	{
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

		nextButton = new GuiButton(posX + 188, posY + 102, 60, 11, Component.translatable("RivalRebels.class.next"));
		doneButton = new GuiButton(posX + 188, posY + 119, 60, 11, Component.translatable("RivalRebels.class.done"));
		gameScroll = new GuiScroll(posX + 243, posY + 9, 74);
		this.addRenderableOnly(nextButton);
		this.addRenderableOnly(doneButton);
		this.addRenderableOnly(gameScroll);
	}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        PoseStack matrices = context.pose();
        if (rrclass == RivalRebelsClass.NONE) rrclass = RivalRebelsClass.REBEL;
        float f = 0.00390625F;
        renderTransparentBackground(context);
        context.fillGradient(posX, posY, posX + xSizeOfTexture, posY + ySizeOfTexture, CommonColors.BLACK, CommonColors.BLACK);
		drawPanel(context, posX + 162, posY + 40, 80, 74, gameScroll.getScroll(), gameScroll.limit, rrclass.name + ".description");
        context.fillGradient(posX + 160, posY + 9, posX + 244, posY + 38, 0xFF000000, 0xFF000000);
        ((DrawContextAccessor) context).callDrawTexturedQuad(
            RRIdentifiers.guitclass,
            posX,
            posX + xSizeOfTexture,
            posY + ySizeOfTexture,
            posY,
            0, // z offset
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f,
            0,
            1F,
            1F,
            1F,
            1F
        );

        ((DrawContextAccessor) context).callDrawTexturedQuad(
            rrclass.resource,
            posX + 12,
            posX + 140,
            posY + 140,
            posY + 12,
            0, // z offset
            0,
            xSizeOfTexture * f,
            ySizeOfTexture * f,
            0,
            1, 1, 1, 1
        );

        float scalefactor = 1.5f;
        matrices.scale(scalefactor * 1.2f, scalefactor, scalefactor);
        context.drawCenteredString(font, rrclass.name, (int) ((posX + 76) / (scalefactor * 1.2f)), (int) ((posY + 16) / scalefactor), rrclass.color);
        matrices.scale(1 / (scalefactor * 1.2f), 1 / scalefactor, 1 / scalefactor);

		scalefactor = 0.666f;
        matrices.scale(scalefactor, scalefactor, scalefactor);
        context.drawCenteredString(font, Component.translatable("RivalRebels.class." + rrclass.name + ".minidesc"), (int) ((posX + 76) / scalefactor), (int) ((posY + 28) / scalefactor), rrclass.color);
        matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		scalefactor = 0.666f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
        context.drawCenteredString(font, Component.translatable("RivalRebels.class.description"), (int) ((posX + 181) / scalefactor), (int) ((posY + 28) / scalefactor), rrclass.color);
        matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		context.setColor(1F, 1F, 1F, 1F);

		for (int i = 0; i < sizelookup.length; i++)
		{
			int X = posX + 18 + (i % 9) * 22;
			int Y = posY + 158 + 22 * (int) Math.floor(i / 9D);
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
			matrices.pushPose();
			matrices.translate(X + 8, Y + 8, 0);
			matrices.scale(sizelookup[i], sizelookup[i], sizelookup[i]);
			matrices.translate(-X - 8, -Y - 8, 0);
			context.renderItem(rrclass.inventory[i], X, Y);
			matrices.popPose();
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		}
		for (int i = rrclass.inventory.length - 1; i >= 0; i--) {
			int X = posX + 18 + (i % 9) * 22;
			int Y = posY + 158 + 22 * (i / 9);
			matrices.pushPose();
			matrices.translate(X + 8, Y + 8, 20);
			matrices.scale(sizelookup[i], sizelookup[i], sizelookup[i]);
			matrices.translate(-X - 8, -Y - 8, 0);
			ItemStack stack = rrclass.inventory[i];
			if (!stack.isEmpty()) context.drawString(font, Component.nullToEmpty(String.valueOf(stack.getCount())),X+17-font.width(String.valueOf(stack.getCount())),Y+9,0xFFFFFF);
			if (sizelookup[i] > 1)
			{
                context.fillGradient(X + 17, Y + 3, (int) (X + ((font.width(stack.getHoverName()) + 4) * (sizelookup[i] - 1) * 2) + 15), Y + 13, 0xaa111111, 0xaa111111);
				context.drawString(font, stack.getHoverName(), X + 18, Y + 4, 0xFFFFFF);
			}
			matrices.popPose();
		}

		super.render(context, mouseX, mouseY, delta);

		if (minecraft.mouseHandler.isLeftPressed() && !prevClick)
		{
			if (nextButton.mouseClicked(mouseX, mouseY, 0))
			{
				switch (rrclass)
				{
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
			}
			if (doneButton.mouseClicked(mouseX, mouseY, 0))
			{
				this.minecraft.setScreen(new GuiSpawn(rrclass));
			}
		}
		prevClick = minecraft.mouseHandler.isLeftPressed();
	}

	protected void drawPanel(GuiGraphics context, int x, int y, int width, int height, int scroll, int scrolllimit, String display) {
		int length = 10;
		int dist = (int) (-((float) scroll / (float) scrolllimit) * (((length) * 10) - height));
		float scalefactor = 0.6666f;
        PoseStack matrices = context.pose();
        matrices.scale(scalefactor, scalefactor, scalefactor);
		context.drawWordWrap(font, Component.translatable("RivalRebels.class." + display), (int) (x * 1.5), (int) ((y + dist) * 1.5), (int) (width * 1.5), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
	}
}

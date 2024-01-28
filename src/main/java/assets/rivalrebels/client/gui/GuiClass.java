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
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static net.minecraftforge.client.gui.GuiUtils.drawGradientRect;

public class GuiClass extends Screen
{
	private final int	xSizeOfTexture	= 256;
	private final int	ySizeOfTexture	= 256;
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
        super(Text.of(""));
        posX = (width - xSizeOfTexture) / 2;
		posY = (height - ySizeOfTexture) / 2;
		rrclass = rrc;
	}

	@Override
	public void init()
	{
		posX = (this.width - xSizeOfTexture) / 2;
		posY = (this.height - ySizeOfTexture) / 2;
		this.drawables.clear();

		nextButton = new GuiButton(posX + 188, posY + 102, 60, 11, new TranslatableText("RivalRebels.class.next"));
		doneButton = new GuiButton(posX + 188, posY + 119, 60, 11, new TranslatableText("RivalRebels.class.done"));
		gameScroll = new GuiScroll(posX + 243, posY + 9, 74);
		this.addDrawable(nextButton);
		this.addDrawable(doneButton);
		this.addDrawable(gameScroll);
	}

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (rrclass == RivalRebelsClass.NONE) rrclass = RivalRebelsClass.REBEL;
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        float f = 0.00390625F;
        renderBackground(matrices);
		drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), posX, posY, posX + xSizeOfTexture, posY + ySizeOfTexture, 0xFF000000, 0xFF000000);
		drawPanel(matrices, posX + 162, posY + 40, 80, 74, gameScroll.getScroll(), gameScroll.limit, rrclass.name + ".description");
		drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), posX + 160, posY + 9, posX + 244, posY + 38, 0xFF000000, 0xFF000000);
		this.client.getTextureManager().bindTexture(RRIdentifiers.guitclass);
		buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
		buffer.vertex(posX, posY + ySizeOfTexture, getZOffset()).color(1F, 1F, 1F, 1F).texture(0, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY + ySizeOfTexture, getZOffset()).color(1F, 1F, 1F, 1F).texture(xSizeOfTexture * f, ySizeOfTexture * f).next();
		buffer.vertex(posX + xSizeOfTexture, posY, getZOffset()).color(1F, 1F, 1F, 1F).texture(xSizeOfTexture * f, 0).next();
		buffer.vertex(posX, posY, getZOffset()).color(1F, 1F, 1F, 1F).texture(0, 0).next();
		tessellator.draw();

		this.client.getTextureManager().bindTexture(rrclass.resource);

        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
		buffer.vertex(posX + 12, posY + 140, getZOffset()).color(1F, 1F, 1F, 1F).texture(0, ySizeOfTexture * f).next();
		buffer.vertex(posX + 140, posY + 140, getZOffset()).color(1F, 1F, 1F, 1F).texture(xSizeOfTexture * f, ySizeOfTexture * f).next();
		buffer.vertex(posX + 140, posY + 12, getZOffset()).color(1F, 1F, 1F, 1F).texture(xSizeOfTexture * f, 0).next();
		buffer.vertex(posX + 12, posY + 12, getZOffset()).color(1F, 1F, 1F, 1F).texture(0, 0).next();
		tessellator.draw();

		float scalefactor = 1.5f;
        matrices.scale(scalefactor * 1.2f, scalefactor, scalefactor);
		drawCenteredText(matrices, textRenderer, rrclass.name, (int) ((posX + 76) / (scalefactor * 1.2f)), (int) ((posY + 16) / scalefactor), rrclass.color);
        matrices.scale(1 / (scalefactor * 1.2f), 1 / scalefactor, 1 / scalefactor);

		scalefactor = 0.666f;
        matrices.scale(scalefactor, scalefactor, scalefactor);
		drawCenteredText(matrices, textRenderer, new TranslatableText("RivalRebels.class." + rrclass.name + ".minidesc"), (int) ((posX + 76) / scalefactor), (int) ((posY + 28) / scalefactor), rrclass.color);
        matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		scalefactor = 0.666f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
		drawCenteredText(matrices, textRenderer, new TranslatableText("RivalRebels.class.description"), (int) ((posX + 181) / scalefactor), (int) ((posY + 28) / scalefactor), rrclass.color);
        matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);

		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

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
		RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
		for (int i = rrclass.inventory.length - 1; i >= 0; i--) {
			int X = posX + 18 + (i % 9) * 22;
			int Y = posY + 158 + 22 * (i / 9);
			matrices.push();
			matrices.translate(X + 8, Y + 8, 0);
			matrices.scale(sizelookup[i], sizelookup[i], sizelookup[i]);
			matrices.translate(-X - 8, -Y - 8, 0);
			client.getItemRenderer().renderInGui(rrclass.inventory[i], X, Y);
			matrices.pop();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
		}
		for (int i = rrclass.inventory.length - 1; i >= 0; i--) {
			int X = posX + 18 + (i % 9) * 22;
			int Y = posY + 158 + 22 * (i / 9);
			matrices.push();
			matrices.translate(X + 8, Y + 8, 20);
			matrices.scale(sizelookup[i], sizelookup[i], sizelookup[i]);
			matrices.translate(-X - 8, -Y - 8, 0);
			ItemStack stack = rrclass.inventory[i];
			if (!stack.isEmpty()) textRenderer.drawWithShadow(matrices, Text.of(String.valueOf(stack.getCount())),X+17-textRenderer.getWidth(String.valueOf(stack.getCount())),Y+9,0xFFFFFF);
			if (sizelookup[i] > 1)
			{
				drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), X + 17, Y + 3, (int) (X + ((textRenderer.getWidth(stack.getName()) + 4) * (sizelookup[i] - 1) * 2) + 15), Y + 13, 0xaa111111, 0xaa111111);
				textRenderer.drawWithShadow(matrices, stack.getName(), X + 18, Y + 4, 0xFFFFFF);
			}
			matrices.pop();
		}

		super.render(matrices, mouseX, mouseY, delta);

		if (client.mouse.wasLeftButtonClicked() && !prevClick)
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
				this.client.setScreen(new GuiSpawn(rrclass));
			}
		}
		prevClick = client.mouse.wasLeftButtonClicked();
	}

	protected void drawPanel(MatrixStack matrices, int x, int y, int width, int height, int scroll, int scrolllimit, String display)
	{
		int length = 10;
		int dist = (int) (-((float) scroll / (float) scrolllimit) * (((length) * 10) - height));
		float scalefactor = 0.6666f;
		matrices.scale(scalefactor, scalefactor, scalefactor);
		textRenderer.drawTrimmed(new TranslatableText("RivalRebels.class." + display), (int) (x * 1.5), (int) ((y + dist) * 1.5), (int) (width * 1.5), 0xffffff);
		matrices.scale(1 / scalefactor, 1 / scalefactor, 1 / scalefactor);
	}
}

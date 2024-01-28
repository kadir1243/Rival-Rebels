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
import assets.rivalrebels.client.guihelper.*;
import assets.rivalrebels.client.tileentityrender.TileEntityRecieverRenderer;
import assets.rivalrebels.common.container.ContainerReciever;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.GuiUtils;

import static net.minecraftforge.client.gui.GuiUtils.drawTexturedModalRect;

@OnlyIn(Dist.CLIENT)
public class GuiTray extends HandledScreen<ContainerReciever> {
	private float				xSize_lo;
	private float				ySize_lo;
	GuiRotor					range;
	GuiCustomButton				chip;
	GuiCustomButton				players;
	GuiCustomButton				mobs;
	GuiDropdownOption			select1;

	public GuiTray(ContainerReciever containerReciever, PlayerInventory inventoryPlayer, Text title)
	{
		super(containerReciever, inventoryPlayer, title);
		backgroundHeight = 206;
	}

	@Override
	public void init()
	{
		super.init();
		int x = (width - getXSize()) / 2;
		int y = (height - getYSize()) / 2;
		drawables.clear();
		range = new GuiRotor(x + 93 - 16, y + 92 - 16, handler.getYawLimit(), "Range");
		chip = new GuiCustomButton(new Rectangle(x + 94, y + 10, 19, 19), RRIdentifiers.guitray, new Vector(237, 10), true);
		chip.isPressed = handler.getKTeam();
		players = new GuiCustomButton(new Rectangle(x + 94, y + 28, 19, 19), RRIdentifiers.guitray, new Vector(237, 28), true);
		players.isPressed = handler.getKPlayers();
		mobs = new GuiCustomButton(new Rectangle(x + 94, y + 46, 19, 19), RRIdentifiers.guitray, new Vector(237, 46), true);
		mobs.isPressed = handler.getKMobs();
		select1 = new GuiDropdownOption(new Vector(119 + x, 8 + y), 45, 0, new TranslatableText("RivalRebels.ads.dragon"), this);
		addDrawable(range);
		addDrawable(chip);
		addDrawable(players);
		addDrawable(mobs);
		addDrawable(select1);
	}

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.xSize_lo = mouseX;
        this.ySize_lo = mouseY;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
		if (modifiers == 1) {
			this.client.setScreen(null);
            handler.setKMobs(mobs.isPressed);
            handler.setKTeam(chip.isPressed);
            handler.setKPlayers(players.isPressed);
            handler.setYawLimit(range.getDegree() * 2);
		}
        return super.charTyped(chr, modifiers);
	}

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

		if (select1.isPressed)
		{
            if (handler.hasWepReqs()) {
                handler.setWep(true);
            }
		}
		select1.isPressed = false;

		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - getXSize()) / 2;
		int posy = (height - getYSize()) / 2;
		int coordx = posx + 53;
		int coordy = posy + 194;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			GuiUtils.drawGradientRect(matrices.peek().getPositionMatrix(), getZOffset(), mousex, mousey, mousex + textRenderer.getWidth("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			textRenderer.draw(matrices, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF);
			if (!buttondown && client.mouse.wasLeftButtonClicked())
			{
                Util.getOperatingSystem().open("http://rivalrebels.com");
			}
		}
		buttondown = client.mouse.wasLeftButtonClicked();
	}

	boolean		buttondown;

	static int	spinfac	= 0;

	public void drawADS(MatrixStack matrices, int x, int y, int scale, float px, float py)
	{
		spinfac += 1;
		matrices.push();
		matrices.translate(x, y - 40, 50);
		matrices.scale(-scale, scale, scale);
		matrices.multiply(new Quaternion(180.0F, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternion(135.0F, 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternion(-135.0F, 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternion(20, 1.0F, 0.0F, 0.0F));
		if (!handler.hasWeapon())
		{
			matrices.translate(0, 0, -0.5f);
			matrices.multiply(new Quaternion(spinfac, 0.0F, 1.0F, 0.0F));
			matrices.translate(0, 0, 0.5f);
		}
		// GL11.glRotated(Math.sin(spinfac/(40 * Math.PI)) * 10, 1.0F, 0.0F, 0.0F);

		// entity.pitch = -((float)Math.atan((double)(py / 40.0F))) * 40.0F;
		// entity.headYaw = (float)Math.atan((double)(px / 40.0F)) * 40.0F;
		// + (Math.sin(spinfac/(40 * Math.PI)) * 10)
		// - (spinfac * 0.5)

		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etreciever);
		matrices.multiply(new Quaternion(180, 0, 1, 0));
		// GL11.glRotated((spinfac * 0.5), 0, 1, 0);
		matrices.translate(0, -0.5 * 1.5, (-0.5 - 0.34) * -1.5);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        VertexConsumerProvider.Immediate vertexConsumerProvider = VertexConsumerProvider.immediate(bufferBuilder);
        VertexConsumer buffer = vertexConsumerProvider.getBuffer(RenderLayer.getSolid());
        TileEntityRecieverRenderer.base.render(buffer);
		if (handler.hasWeapon())
		{
			matrices.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			matrices.multiply(new Quaternion((float) (-Math.atan(px / 40.0F) * 40.0F), 0, 1, 0));
			TileEntityRecieverRenderer.arm.render(buffer);
			matrices.multiply(new Quaternion((float) (Math.atan(py / 40.0F) * 40.0F + 20), 1, 0, 0));
			MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etadsdragon);
			TileEntityRecieverRenderer.adsdragon.render(buffer);
		}
		matrices.pop();
        vertexConsumerProvider.draw();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, RRIdentifiers.guitray);
		int x = (width - getXSize()) / 2;
		int y = (height - getYSize()) / 2;
        drawTexturedModalRect(matrices, x, y, 0, 0, getXSize(), getYSize(), getZOffset());

		if (handler.getPInR() > 0)
		{
			drawTexturedModalRect(matrices, x + 104, y + 68, 248, 0, 8, 8, getZOffset());
		}

		textRenderer.draw(matrices, new TranslatableText("RivalRebels.ads.tray"), x + 25, y + 66, 0xffffff);
		drawADS(matrices, getGuiLeft() + 51, getGuiTop() + 75, 30, getGuiLeft() + 51 - this.xSize_lo, getGuiTop() + 25 - this.ySize_lo);
	}
}

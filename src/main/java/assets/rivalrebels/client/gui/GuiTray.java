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
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class GuiTray extends AbstractContainerScreen<ContainerReciever> {
	private float				xSize_lo;
	private float				ySize_lo;
	GuiRotor					range;
	GuiCustomButton				chip;
	GuiCustomButton				players;
	GuiCustomButton				mobs;
	GuiDropdownOption			select1;

	public GuiTray(ContainerReciever containerReciever, Inventory inventoryPlayer, Component title)
	{
		super(containerReciever, inventoryPlayer, title);
		imageHeight = 206;
	}

	@Override
	public void init()
	{
		super.init();
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
        this.clearWidgets();
		range = new GuiRotor(x + 93 - 16, y + 92 - 16, menu.getYawLimit(), "Range");
		chip = new GuiCustomButton(new Rectangle(x + 94, y + 10, 19, 19), RRIdentifiers.guitray, new Vector(237, 10), true);
		chip.isPressed = menu.getKTeam();
		players = new GuiCustomButton(new Rectangle(x + 94, y + 28, 19, 19), RRIdentifiers.guitray, new Vector(237, 28), true);
		players.isPressed = menu.getKPlayers();
		mobs = new GuiCustomButton(new Rectangle(x + 94, y + 46, 19, 19), RRIdentifiers.guitray, new Vector(237, 46), true);
		mobs.isPressed = menu.getKMobs();
		select1 = new GuiDropdownOption(new Vector(119 + x, 8 + y), 45, 0, Component.translatable("RivalRebels.ads.dragon"), this);
		addRenderableOnly(range);
		addRenderableOnly(chip);
		addRenderableOnly(players);
		addRenderableOnly(mobs);
		addRenderableOnly(select1);
	}

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.xSize_lo = mouseX;
        this.ySize_lo = mouseY;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
		if (modifiers == 1) {
			this.minecraft.setScreen(null);
            menu.setKMobs(mobs.isPressed);
            menu.setKTeam(chip.isPressed);
            menu.setKPlayers(players.isPressed);
            menu.setYawLimit(range.getDegree() * 2);
		}
        return super.charTyped(chr, modifiers);
	}

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);

		if (select1.isPressed)
		{
            if (menu.hasWepReqs()) {
                menu.setWep(true);
            }
		}
		select1.isPressed = false;

		int mousex = mouseX;
		int mousey = mouseY;
		int posx = (width - imageWidth) / 2;
		int posy = (height - imageHeight) / 2;
		int coordx = posx + 53;
		int coordy = posy + 194;
		int widthx = 72;
		int widthy = 8;
		if (mousex > coordx && mousey > coordy && mousex < coordx + widthx && mousey < coordy + widthy)
		{
			mousex -= posx;
			mousey -= posy;
			context.fillGradient(mousex, mousey, mousex + font.width("rivalrebels.com") + 3, mousey + 12, 0xaa111111, 0xaa111111);
			context.drawString(font, "rivalrebels.com", mousex + 2, mousey + 2, 0xFFFFFF, false);
			if (!buttondown && minecraft.mouseHandler.isLeftPressed())
			{
                Util.getPlatform().openUri("http://rivalrebels.com");
			}
		}
		buttondown = minecraft.mouseHandler.isLeftPressed();
	}

	boolean		buttondown;

	static int	spinfac	= 0;

	public void drawADS(GuiGraphics context, int x, int y, int scale, float px, float py)
	{
		spinfac += 1;
        PoseStack matrices = context.pose();
        matrices.pushPose();
		matrices.translate(x, y - 40, 50);
		matrices.scale(-scale, scale, scale);
		matrices.mulPose(new Quaternionf(180.0F, 0.0F, 0.0F, 1.0F));
		matrices.mulPose(new Quaternionf(135.0F, 0.0F, 1.0F, 0.0F));
		matrices.mulPose(new Quaternionf(-135.0F, 0.0F, 1.0F, 0.0F));
		matrices.mulPose(new Quaternionf(20, 1.0F, 0.0F, 0.0F));
		if (!menu.hasWeapon())
		{
			matrices.translate(0, 0, -0.5f);
			matrices.mulPose(new Quaternionf(spinfac, 0.0F, 1.0F, 0.0F));
			matrices.translate(0, 0, 0.5f);
		}
		// GL11.glRotated(Math.sin(spinfac/(40 * Math.PI)) * 10, 1.0F, 0.0F, 0.0F);

		// entity.pitch = -((float)Math.atan((double)(py / 40.0F))) * 40.0F;
		// entity.headYaw = (float)Math.atan((double)(px / 40.0F)) * 40.0F;
		// + (Math.sin(spinfac/(40 * Math.PI)) * 10)
		// - (spinfac * 0.5)

		RenderSystem.setShaderTexture(0, RRIdentifiers.etreciever);
		matrices.mulPose(new Quaternionf(180, 0, 1, 0));
		// GL11.glRotated((spinfac * 0.5), 0, 1, 0);
		matrices.translate(0, -0.5 * 1.5, (-0.5 - 0.34) * -1.5);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.NEW_ENTITY);
        TileEntityRecieverRenderer.base.render(bufferBuilder, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		if (menu.hasWeapon()) {
			matrices.translate(0, 0.5 * 1.5, (-0.5 - 0.34) * 1.5);
			matrices.mulPose(new Quaternionf((float) (-Math.atan(px / 40.0F) * 40.0F), 0, 1, 0));
			TileEntityRecieverRenderer.arm.render(bufferBuilder, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
			matrices.mulPose(new Quaternionf((float) (Math.atan(py / 40.0F) * 40.0F + 20), 1, 0, 0));
            RenderSystem.setShaderTexture(0, RRIdentifiers.etadsdragon);
			TileEntityRecieverRenderer.adsdragon.render(bufferBuilder, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		}
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        matrices.popPose();
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.setColor(1, 1, 1, 1);
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

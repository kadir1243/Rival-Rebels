package io.github.kadir1243.rivalrebels.client.gui;

import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.container.BombContainer;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec2;

public abstract class BombContainerScreen<T extends AbstractContainerMenu & BombContainer> extends AbstractContainerScreen<T> {
    public BombContainerScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        showTimer(guiGraphics);
        if (scaleName()) {
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().scale(0.666F, 0.666F);
        }
        renderName(guiGraphics);
        if (scaleName()) {
            guiGraphics.pose().popMatrix();
        }
    }

    public void showTimer(GuiGraphics graphics) {
        int seconds = (getCountdown() / 20);
        int millis = (getCountdown() % 20) * 3;
        String milli;
        if (millis < 10) {
            milli = "0" + millis;
        } else {
            milli = "" + millis;
        }
        graphics.drawString(font, Translations.BOMB_TIMER.translate().append(": -" + seconds + ":" + milli), (int) getTimerPos().x, (int) getTimerPos().y, getTimerColor(), false);
    }

    public abstract Vec2 getTimerPos();

    public abstract int getTimerColor();

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public int getCountdown() {
        return menu.getCountdown();
    }

    public abstract void renderName(GuiGraphics graphics);

    public boolean scaleName() {
        return true;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        getBackgroundTexture().blit(graphics, x, y, 0, 0, imageWidth, imageHeight, CommonColors.WHITE);
    }

    public abstract RRTextures.Texture getBackgroundTexture();
}

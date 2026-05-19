package io.github.kadir1243.rivalrebels.client.gui;

import io.github.kadir1243.rivalrebels.client.renderhelper.RRTextures;
import io.github.kadir1243.rivalrebels.common.container.BombContainer;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BombContainerScreen<T extends AbstractContainerMenu & BombContainer> extends AbstractContainerScreen<T> {
    public BombContainerScreen(T menu, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
    }

    private Vec2 timerPos;

    @Override
    protected void init() {
        super.init();
        this.timerPos = getTimerPos();
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        showTimer(graphics);
        boolean shouldScaleName = scaleName();
        if (shouldScaleName) {
            graphics.pose().pushMatrix();
            graphics.pose().scale(0.666F, 0.666F);
        }
        renderName(graphics);
        if (shouldScaleName) {
            graphics.pose().popMatrix();
        }
    }

    public void showTimer(GuiGraphicsExtractor graphics) {
        int countdown = getCountdown();
        int seconds = countdown / 20;
        int millis = (countdown % 20) * 3;
        String milli;
        if (millis < 10) {
            milli = "0" + millis;
        } else {
            milli = "" + millis;
        }
        graphics.text(font, Translations.BOMB_TIMER.translate(seconds, milli), (int) timerPos.x, (int) timerPos.y, getTimerColor(countdown), false);
    }

    public abstract Vec2 getTimerPos();

    public abstract int getTimerColor(int countdown);

    public int getCountdown() {
        return menu.getCountdown();
    }

    public abstract void renderName(GuiGraphicsExtractor graphics);

    public boolean scaleName() {
        return true;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        getBackgroundTexture().blit(graphics, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, CommonColors.WHITE);
    }

    public abstract RRTextures.Texture getBackgroundTexture();
}

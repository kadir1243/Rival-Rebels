package io.github.kadir1243.rivalrebels.client.renderhelper;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import org.jspecify.annotations.Nullable;

public record TrayModelPIPRenderState(
    Vec3 translation,
    boolean hasWeapon,
    int spinfac,
    int x0,
    int y0,
    int x1,
    int y1,
    float scale,
    int realX1,
    int realY1,
    @Nullable ScreenRectangle scissorArea,
    @Nullable ScreenRectangle bounds
) implements PictureInPictureRenderState {
    public TrayModelPIPRenderState(
        Vec3 translation,
        boolean hasWeapon,
        int spinfac,
        int x0,
        int y0,
        int x1,
        int y1,
        float scale,
        int realX1,
        int realY1,
        @Nullable ScreenRectangle scissorArea
    ) {
        ScreenRectangle bounds = new ScreenRectangle(Mth.abs(x0), Mth.abs(y0), Mth.abs(x1 - x0), Mth.abs(y1 - y0));
        this(translation, hasWeapon, spinfac, x0, y0, x1, y1, scale, realX1, realY1, scissorArea, scissorArea != null ? scissorArea.intersection(bounds) : bounds);
    }
}

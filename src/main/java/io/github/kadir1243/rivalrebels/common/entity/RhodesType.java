package io.github.kadir1243.rivalrebels.common.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

public interface RhodesType extends StringRepresentable {
    Identifier getTexture();

    int getColor();

    default Component getName() {
        return Component.literal(getSerializedName());
    }
}

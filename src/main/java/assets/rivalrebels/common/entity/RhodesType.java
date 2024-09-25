package assets.rivalrebels.common.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

// TODO: Make this extendable
public interface RhodesType extends StringRepresentable {
    ResourceLocation getTexture();

    int getColor();

    default Component getName() {
        return Component.literal(getSerializedName());
    }
}

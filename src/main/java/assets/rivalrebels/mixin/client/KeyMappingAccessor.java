package assets.rivalrebels.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
@Environment(EnvType.CLIENT)
public interface KeyMappingAccessor {
    @Accessor
    int getClickCount();

    @Accessor
    void setClickCount(int clickCount);

    @Accessor
    InputConstants.Key getKey();
}

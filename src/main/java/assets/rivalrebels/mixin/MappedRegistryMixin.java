package assets.rivalrebels.mixin;

import net.minecraft.core.MappedRegistry;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin {
    @Redirect(method = "bindTags", at = @At(value = "INVOKE", target = "Ljava/util/Set;isEmpty()Z"))
    private boolean bindTags(Set<TagKey<?>> instance) {
        try {
            return instance.isEmpty();
        } catch (NullPointerException ignored) {
            return true;
        }
    }
}

package assets.rivalrebels.mixin.client;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Map;

/**
 * Fixes Same Key Bug
 */
@Mixin(KeyMapping.class)
@Environment(EnvType.CLIENT)
public class KeyMappingMixin {
    @Shadow
    @Final
    private static Map<String, KeyMapping> ALL;
    @Unique
    private static final Multimap<InputConstants.Key, KeyMapping> MAPPINGS = HashMultimap.create();

    @Inject(method = "click", at = @At("HEAD"), cancellable = true)
    private static void click(InputConstants.Key key, CallbackInfo ci) {
        Collection<KeyMapping> mappings = MAPPINGS.get(key);
        for (KeyMapping keyMapping : mappings) {
            ((KeyMappingAccessor) keyMapping).setClickCount(((KeyMappingAccessor) keyMapping).getClickCount() + 1);
        }
        ci.cancel();
    }

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private static void set(InputConstants.Key key, boolean held, CallbackInfo ci) {
        Collection<KeyMapping> mappings = MAPPINGS.get(key);
        for (KeyMapping keyMapping : mappings) {
            keyMapping.setDown(held);
        }
        ci.cancel();
    }

    @Inject(method = "resetMapping", at = @At("HEAD"), cancellable = true)
    private static void resetMapping(CallbackInfo ci) {
        MAPPINGS.clear();
        for (KeyMapping mapping : ALL.values()) {
            MAPPINGS.put(((KeyMappingAccessor) mapping).getKey(), mapping);
        }
        ci.cancel();
    }

    @Redirect(method = "<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 1))
    private Object init(Map<InputConstants.Key, KeyMapping> instance, Object k, Object v) {
        MAPPINGS.put((InputConstants.Key) k, (KeyMapping) v);
        return null;
    }
}

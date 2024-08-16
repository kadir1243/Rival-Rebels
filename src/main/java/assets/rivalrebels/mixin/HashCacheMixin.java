package assets.rivalrebels.mixin;

import net.minecraft.data.HashCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HashCache.class)
public class HashCacheMixin {// Fixes A Stuck condition
    @Inject(method = "purgeStaleAndWrite", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;[Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private void exitDirectly(CallbackInfo ci) {
        System.exit(0);
    }
}

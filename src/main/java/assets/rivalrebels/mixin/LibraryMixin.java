package assets.rivalrebels.mixin;

import com.mojang.blaze3d.audio.Library;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Library.class)
public class LibraryMixin {
    @Inject(method = "getDefaultDeviceName", at = @At("HEAD"), cancellable = true)
    private static void getDefaultDeviceName(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(null); // Only for linux erroring on my pc
    }

    @Redirect(method = "tryOpenDevice", at = @At(value = "INVOKE", target = "Lorg/lwjgl/openal/ALC10;alcOpenDevice(Ljava/lang/CharSequence;)J"))
    private static long tryOpenDevice(CharSequence deviceSpecifierEncoded) {
        return 0;
    }
}

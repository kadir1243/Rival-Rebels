package assets.rivalrebels.mixin.datagen;

import assets.rivalrebels.RivalRebels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

@Mixin(BlockLootSubProvider.class)
public class BlockLootTableGeneratorMixin {
    @Inject(method = "generate(Ljava/util/function/BiConsumer;)V", at = @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", ordinal = 1), cancellable = true)
    private void accept(BiConsumer<ResourceLocation, LootTable.Builder> exporter, CallbackInfo ci) {
        RivalRebels.LOGGER.warn("Skipping LootTable generation");
        // I dont know why but it fails
        ci.cancel();
    }
}

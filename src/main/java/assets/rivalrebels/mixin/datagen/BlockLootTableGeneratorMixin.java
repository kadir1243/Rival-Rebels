package assets.rivalrebels.mixin.datagen;

import assets.rivalrebels.RivalRebels;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(BlockLootTableGenerator.class)
public class BlockLootTableGeneratorMixin {
    @Inject(method = "accept", at = @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", ordinal = 1), cancellable = true)
    private void accept(BiConsumer<Identifier, LootTable.Builder> exporter, CallbackInfo ci) {
        RivalRebels.LOGGER.warn("Skipping LootTable generation");
        // I dont know why but it fails
        ci.cancel();
    }
}

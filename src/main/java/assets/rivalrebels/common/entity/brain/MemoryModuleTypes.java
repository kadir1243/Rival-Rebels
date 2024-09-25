package assets.rivalrebels.common.entity.brain;

import assets.rivalrebels.RRIdentifiers;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public class MemoryModuleTypes {
    public static final MemoryModuleType<Unit> RHODES_AWAKEN = register("rhodes_awaken", Unit.CODEC);
    public static final MemoryModuleType<BlockEntity> ROCKET_BLOCK_TARGET = register("rocket_block_target");

    public static void init() {
    }

    private static <U> MemoryModuleType<U> register(String identifier, Codec<U> codec) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, RRIdentifiers.create(identifier), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String identifier) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, RRIdentifiers.create(identifier), new MemoryModuleType<>(Optional.empty()));
    }
}

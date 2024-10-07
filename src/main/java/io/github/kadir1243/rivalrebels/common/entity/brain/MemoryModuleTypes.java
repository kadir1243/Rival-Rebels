package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class MemoryModuleTypes {
    private static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, RRIdentifiers.MODID);
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> RHODES_AWAKEN = MEMORY_MODULE_TYPES.register("rhodes_awaken", () -> new MemoryModuleType<>(Optional.of(Unit.CODEC)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<BlockEntity>> ROCKET_BLOCK_TARGET = MEMORY_MODULE_TYPES.register("rocket_block_target", () -> new MemoryModuleType<>(Optional.empty()));

    public static void init(IEventBus bus) {
        MEMORY_MODULE_TYPES.register(bus);
    }
}

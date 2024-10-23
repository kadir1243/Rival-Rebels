package io.github.kadir1243.rivalrebels.common.item.components;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RRComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, RRIdentifiers.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FlameThrowerMode>> FLAME_THROWER_MODE = register("flame_thrower_mode", FlameThrowerMode.CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> REACTOR_FUEL_LEFT = register("reactor_fuel_left", Codec.INT);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ChipData>> CHIP_DATA = register("chip_data", ChipData.CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TESLA_DIAL = register("tesla_dial", Codec.INT);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HAPPY_NEW_YEAR = register("happy_new_year", Codec.INT);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BinocularData>> BINOCULAR_DATA = register("binocular_data", BinocularData.CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> REMOTE_CONTROLLED_BOMB_POS = register("remote_controlled_bomb_pos", BlockPos.CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> CORE_TIME_MULTIPLIER = register("core_time_multiplier", Codec.FLOAT);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ROD_POWER = register("rod_power", Codec.INT);

    public static void init(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec) {
        return DATA_COMPONENTS.registerComponentType(name, objectBuilder -> objectBuilder.persistent(codec));
    }
}

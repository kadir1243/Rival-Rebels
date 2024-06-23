package assets.rivalrebels.common.item.components;

import assets.rivalrebels.RRIdentifiers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class RRComponents {
    public static final DataComponentType<FlameThrowerMode> FLAME_THROWER_MODE = register("flame_thrower_mode", FlameThrowerMode.CODEC);
    public static final DataComponentType<Integer> REACTOR_FUEL_LEFT = register("reactor_fuel_left", Codec.INT);
    public static final DataComponentType<ChipData> CHIP_DATA = register("chip_data", ChipData.CODEC);
    public static final DataComponentType<Integer> TESLA_DIAL = register("tesla_dial", Codec.INT);
    public static final DataComponentType<Integer> HAPPY_NEW_YEAR = register("happy_new_year", Codec.INT);
    public static final DataComponentType<BinocularData> BINOCULAR_DATA = register("binocular_data", BinocularData.CODEC);
    public static final DataComponentType<BlockPos> REMOTE_CONTROLLED_BOMB_POS = register("remote_controlled_bomb_pos", BlockPos.CODEC);

    public static void init() {
    }

    private static <T> DataComponentType<T> register(String name, Codec<T> codec) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, RRIdentifiers.create(name), DataComponentType.<T>builder().persistent(codec).build());
    }
}

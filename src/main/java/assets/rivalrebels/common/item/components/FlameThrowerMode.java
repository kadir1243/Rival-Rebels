package assets.rivalrebels.common.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FlameThrowerMode(int mode, boolean isReady) {
    public static final FlameThrowerMode DEFAULT = new FlameThrowerMode(0, false);
    public static final Codec<FlameThrowerMode> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.INT.fieldOf("mode").forGetter(FlameThrowerMode::mode),
        Codec.BOOL.fieldOf("isReady").forGetter(FlameThrowerMode::isReady)
    ).apply(i, FlameThrowerMode::new));
}

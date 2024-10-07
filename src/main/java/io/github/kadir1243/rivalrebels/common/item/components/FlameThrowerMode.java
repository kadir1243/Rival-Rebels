package io.github.kadir1243.rivalrebels.common.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FlameThrowerMode(int mode) {
    public static final FlameThrowerMode DEFAULT = new FlameThrowerMode(2);
    public static final Codec<FlameThrowerMode> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.INT.fieldOf("mode").forGetter(FlameThrowerMode::mode)
    ).apply(i, FlameThrowerMode::new));
}

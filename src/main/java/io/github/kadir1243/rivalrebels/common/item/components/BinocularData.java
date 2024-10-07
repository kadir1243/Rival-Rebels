package io.github.kadir1243.rivalrebels.common.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

public record BinocularData(BlockPos lpos,
                            int tasks,
                            int carpet,
                            double dist,
                            BlockPos tpos) {
    public static final BinocularData DEFAULT = new BinocularData(BlockPos.ZERO, 0, 0, 0, BlockPos.ZERO);
    public static final Codec<BinocularData> CODEC = RecordCodecBuilder.create(i -> i.group(
        BlockPos.CODEC.fieldOf("lpos").forGetter(BinocularData::lpos),
        Codec.INT.fieldOf("tasks").forGetter(BinocularData::tasks),
        Codec.INT.fieldOf("carpet").forGetter(BinocularData::carpet),
        Codec.DOUBLE.fieldOf("dist").forGetter(BinocularData::dist),
        BlockPos.CODEC.fieldOf("tpos").forGetter(BinocularData::tpos)
    ).apply(i, BinocularData::new));

    public BinocularData withLPos(BlockPos lpos) {
        return new BinocularData(lpos, tasks, carpet, dist, tpos);
    }

    public BinocularData withTPos(BlockPos tpos) {
        return new BinocularData(lpos, tasks, carpet, dist, tpos);
    }

    public BinocularData withData(int tasks,
                                  int carpet,
                                  double dist) {
        return new BinocularData(lpos, tasks, carpet, dist, tpos);
    }
}

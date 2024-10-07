package io.github.kadir1243.rivalrebels.common.round;

import io.github.kadir1243.rivalrebels.RRConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class TeamData {
    public static final Codec<TeamData> CODEC = RecordCodecBuilder.create(
        i -> i.group(
            BlockPos.CODEC.fieldOf("obj_pos").forGetter(TeamData::objPos),
            Codec.INT.fieldOf("win_count").forGetter(TeamData::winCount),
            Codec.INT.fieldOf("health").forGetter(TeamData::health)
        ).apply(i, TeamData::new)
    );
    public static final StreamCodec<FriendlyByteBuf, TeamData> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        TeamData::objPos,
        ByteBufCodecs.INT,
        TeamData::winCount,
        ByteBufCodecs.INT,
        TeamData::health,
        TeamData::new
    );
    public BlockPos objPos;
    public int winCount;
    public int health;

    public TeamData() {
        this(new BlockPos(-1, -1, -1), 0, RRConfig.SERVER.getObjectiveHealth());
    }

    public TeamData(BlockPos objPos, int winCount, int health) {
        this.objPos = objPos;
        this.winCount = winCount;
        this.health = health;
    }

    public BlockPos objPos() {
        return objPos;
    }

    public int winCount() {
        return winCount;
    }

    public int health() {
        return health;
    }
}

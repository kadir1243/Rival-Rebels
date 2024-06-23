package assets.rivalrebels.common.item.components;

import assets.rivalrebels.common.round.RivalRebelsTeam;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record ChipData(UUID player, String username, RivalRebelsTeam team, boolean isReady) {
    public static final ChipData DEFAULT = new ChipData(null, "", RivalRebelsTeam.NONE, false);
    public static final Codec<ChipData> CODEC = RecordCodecBuilder.create(i -> i.group(
        UUIDUtil.CODEC.fieldOf("player").forGetter(ChipData::player),
        Codec.STRING.fieldOf("username").forGetter(ChipData::username),
        RivalRebelsTeam.CODEC.fieldOf("team").forGetter(ChipData::team),
        Codec.BOOL.fieldOf("isReady").forGetter(ChipData::isReady)
    ).apply(i, ChipData::new));

}

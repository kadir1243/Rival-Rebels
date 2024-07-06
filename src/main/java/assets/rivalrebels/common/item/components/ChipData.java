package assets.rivalrebels.common.item.components;

import assets.rivalrebels.common.round.RivalRebelsTeam;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.core.UUIDUtil;

public record ChipData(GameProfile gameProfile, RivalRebelsTeam team, boolean isReady) {
    public static final ChipData DEFAULT = new ChipData(new GameProfile(FakePlayer.DEFAULT_UUID, ""), RivalRebelsTeam.NONE, false);
    public static final Codec<ChipData> CODEC = RecordCodecBuilder.create(i -> i.group(
        UUIDUtil.CODEC.fieldOf("player").forGetter(chipData -> chipData.gameProfile().getId()),
        Codec.STRING.fieldOf("username").forGetter(chipData -> chipData.gameProfile().getName()),
        RivalRebelsTeam.CODEC.fieldOf("team").forGetter(ChipData::team),
        Codec.BOOL.fieldOf("isReady").forGetter(ChipData::isReady)
    ).apply(i, (uuid, name, team, isReady) -> new ChipData(new GameProfile(uuid, name), team, isReady)));
}

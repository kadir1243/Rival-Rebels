package assets.rivalrebels.common.item.components;

import assets.rivalrebels.common.round.RivalRebelsTeam;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.util.ExtraCodecs;

public record ChipData(GameProfile gameProfile, RivalRebelsTeam team) {
    public static final ChipData DEFAULT = new ChipData(new GameProfile(FakePlayer.DEFAULT_UUID, ""), RivalRebelsTeam.NONE);
    public static final Codec<ChipData> CODEC = RecordCodecBuilder.create(i -> i.group(
        ExtraCodecs.GAME_PROFILE.fieldOf("game_profile").forGetter(ChipData::gameProfile),
        RivalRebelsTeam.CODEC.fieldOf("team").forGetter(ChipData::team)
    ).apply(i, ChipData::new));
}

package io.github.kadir1243.rivalrebels.common.item.components;

import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.UUID;

public record ChipData(GameProfile gameProfile, RivalRebelsTeam team) {
    public static final UUID FAKE_PLAYER = UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77");
    public static final ChipData DEFAULT = new ChipData(new GameProfile(FAKE_PLAYER, ""), RivalRebelsTeam.NONE);
    public static final Codec<ChipData> CODEC = RecordCodecBuilder.create(i -> i.group(
        ExtraCodecs.GAME_PROFILE.fieldOf("game_profile").forGetter(ChipData::gameProfile),
        RivalRebelsTeam.CODEC.fieldOf("team").forGetter(ChipData::team)
    ).apply(i, ChipData::new));
}

/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package io.github.kadir1243.rivalrebels.common.round;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public class RivalRebelsPlayer {
    public static final Codec<RivalRebelsPlayer> CODEC = RecordCodecBuilder.create(
        i -> i.group(
            ExtraCodecs.GAME_PROFILE.fieldOf("profile").forGetter(RivalRebelsPlayer::getProfile),
            RivalRebelsTeam.CODEC.fieldOf("team").forGetter(RivalRebelsPlayer::getTeam),
            RivalRebelsClass.CODEC.fieldOf("fight_class").forGetter(RivalRebelsPlayer::getFightClass),
            RivalRebelsRank.CODEC.fieldOf("rank").forGetter(RivalRebelsPlayer::getRank),
            Codec.INT.fieldOf("resets").forGetter(RivalRebelsPlayer::getResets),
            Codec.BOOL.fieldOf("is_reset").forGetter(RivalRebelsPlayer::isReset)
        ).apply(i, RivalRebelsPlayer::new)
    );
    public static final StreamCodec<FriendlyByteBuf, RivalRebelsPlayer> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.GAME_PROFILE,
        RivalRebelsPlayer::getProfile,
        RivalRebelsTeam.STREAM_CODEC,
        RivalRebelsPlayer::getTeam,
        RivalRebelsClass.STREAM_CODEC,
        RivalRebelsPlayer::getFightClass,
        RivalRebelsRank.STREAM_CODEC,
        RivalRebelsPlayer::getRank,
        ByteBufCodecs.INT,
        RivalRebelsPlayer::getResets,
        ByteBufCodecs.BOOL,
        RivalRebelsPlayer::isReset,
        RivalRebelsPlayer::new
    );
    public GameProfile profile;
    public RivalRebelsClass rrclass;
    public RivalRebelsTeam rrteam;
    public RivalRebelsRank rrrank;
    public int resets;
    public boolean isreset;
    public boolean voted;

    public RivalRebelsPlayer(GameProfile profile, int resets) {
        this(profile, RivalRebelsTeam.NONE, RivalRebelsClass.NONE, RivalRebelsRank.REGULAR, resets);
    }

    public RivalRebelsPlayer(GameProfile profile, RivalRebelsTeam rteam, RivalRebelsClass rclass, RivalRebelsRank rrank, int resets) {
        this(profile, rteam, rclass, rrank, resets, true);
    }

    public RivalRebelsPlayer(GameProfile profile, RivalRebelsTeam rteam, RivalRebelsClass rclass, RivalRebelsRank rrank, int resets, boolean isReset) {
        this.profile = profile;
        this.rrteam = rteam;
        this.rrclass = rclass;
        this.rrrank = rrank;
        this.resets = resets;
        this.isreset = isReset;
    }

    public GameProfile getProfile() {
        return profile;
    }

    public boolean isReset() {
        return isreset;
    }

    public RivalRebelsClass getFightClass() {
        return rrclass;
    }

    public RivalRebelsRank getRank() {
        return rrrank;
    }

    public RivalRebelsTeam getTeam() {
        return rrteam;
    }

    public int getResets() {
        return resets;
    }

    public boolean equals(RivalRebelsPlayer o) {
        return profile.equals(o.profile);
    }

    public UUID getId() {
        return profile.getId();
    }

    public String getUsername() {
        return profile.getName();
    }

    public void reset() {
        resets++;
        isreset = true;
    }

    public void clear() {
        rrclass = RivalRebelsClass.NONE;
        isreset = true;
        resets = -1;
    }

    public void clearTeam() {
        rrclass = RivalRebelsClass.NONE;
        rrteam = RivalRebelsTeam.NONE;
        isreset = true;
        resets = -1;
    }

}

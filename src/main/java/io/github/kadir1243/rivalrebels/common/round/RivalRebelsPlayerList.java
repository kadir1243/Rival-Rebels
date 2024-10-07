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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record RivalRebelsPlayerList(List<RivalRebelsPlayer> players) implements CustomPacketPayload {
    public static final Codec<RivalRebelsPlayerList> CODEC = RecordCodecBuilder.create(
        i -> i.group(
            RivalRebelsPlayer.CODEC.listOf().fieldOf("players").forGetter(RivalRebelsPlayerList::players)
        ).apply(i, RivalRebelsPlayerList::new)
    );
    public static final StreamCodec<FriendlyByteBuf, RivalRebelsPlayerList> STREAM_CODEC = StreamCodec.composite(RivalRebelsPlayer.STREAM_CODEC.apply(ByteBufCodecs.list()), RivalRebelsPlayerList::players, RivalRebelsPlayerList::new);
    public static final Type<RivalRebelsPlayerList> PACKET_TYPE = new Type<>(RRIdentifiers.create("rivalrebelsplayerlist"));

	public RivalRebelsPlayerList() {
        this(new ArrayList<>());
    }

    public int getSize()
	{
		return players.size();
	}

	public RivalRebelsPlayer add(RivalRebelsPlayer o) {
        players.add(o);
        return o;
	}

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public void clear() {
        players.forEach(RivalRebelsPlayer::clear);
	}

	public void clearTeam() {
        players.forEach(RivalRebelsPlayer::clearTeam);
	}

	public boolean contains(GameProfile o) {
		return players.stream().map(rivalRebelsPlayer -> rivalRebelsPlayer.profile).anyMatch(gameProfile -> gameProfile.equals(o));
	}

    public RivalRebelsPlayer getForGameProfile(GameProfile profile) {
        for (RivalRebelsPlayer player : players) if (player.profile.equals(profile)) return player;
        return add(new RivalRebelsPlayer(profile, RRConfig.SERVER.getMaximumResets()));
    }

	public void clearVotes() {
        players.forEach(rivalRebelsPlayer -> rivalRebelsPlayer.voted = false);
	}

    public static void onMessage(RivalRebelsPlayerList m, IPayloadContext context) {
		RivalRebels.round.rrplayerlist = m;
	}

    public void refreshForWorld(Level world) {
        if (world.isClientSide()) return;
        for (Player player : world.players()) {
            ((ServerPlayer) player).connection.send(this);
        }
    }
}

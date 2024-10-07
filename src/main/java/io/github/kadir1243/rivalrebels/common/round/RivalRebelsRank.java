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

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum RivalRebelsRank implements StringRepresentable {
	REGULAR(0,13),
	REBEL(1,17),
	OFFICER(2,16),
	LEADER(3,3),
	REP(4,5);
    public static final Codec<RivalRebelsRank> CODEC = StringRepresentable.fromValues(RivalRebelsRank::values);
    public static final StreamCodec<ByteBuf, RivalRebelsRank> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public final int id;
	public final int snf;

	RivalRebelsRank(int id, int sound) {
		this.id = id;
		snf = sound;
	}

    @Override
    public String getSerializedName() {
        return name();
    }
}

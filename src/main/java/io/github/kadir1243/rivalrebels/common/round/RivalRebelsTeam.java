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

public enum RivalRebelsTeam implements StringRepresentable {
	NONE(0),
	OMEGA(1),
	SIGMA(2);
    public static final Codec<RivalRebelsTeam> CODEC = StringRepresentable.fromValues(RivalRebelsTeam::values);
    public static final StreamCodec<ByteBuf, RivalRebelsTeam> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

	public final int id;

	RivalRebelsTeam(int i) {
		id = i;
	}

    @Override
    public String getSerializedName() {
        return name();
    }

    public static RivalRebelsTeam getForID(int i) {
        return switch (i) {
            case 1 -> OMEGA;
            case 2 -> SIGMA;
            default -> NONE;
        };
    }
}

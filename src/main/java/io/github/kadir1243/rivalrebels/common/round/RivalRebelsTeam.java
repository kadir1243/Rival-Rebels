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
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum RivalRebelsTeam implements StringRepresentable {
	NONE,
	OMEGA,
	SIGMA;
    public static final Codec<RivalRebelsTeam> CODEC = StringRepresentable.fromValues(RivalRebelsTeam::values);
    public static final StreamCodec<ByteBuf, RivalRebelsTeam> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    @Override
    public String getSerializedName() {
        return name();
    }

    public Component getBlockName() {
        return switch (this) {
            case NONE -> Component.literal("NONE");
            case OMEGA -> RRBlocks.sigmaobj.get().getName();
            case SIGMA -> RRBlocks.omegaobj.get().getName();
        };
    }
}

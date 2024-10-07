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
package io.github.kadir1243.rivalrebels.common.packet;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.entity.EntityB2Spirit;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityLaptop;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record LaptopEngagePacket(BlockPos tpos, BlockPos lpos, boolean carpet) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, LaptopEngagePacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        LaptopEngagePacket::tpos,
        BlockPos.STREAM_CODEC,
        LaptopEngagePacket::lpos,
        ByteBufCodecs.BOOL,
        LaptopEngagePacket::carpet,
        LaptopEngagePacket::new
    );
    public static final Type<LaptopEngagePacket> PACKET_TYPE = new Type<>(RRIdentifiers.create("laptopengage"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void onMessage(LaptopEngagePacket m, IPayloadContext context) {
        Player player = context.player();
        Level world = player.level();
        context.enqueueWork(() -> {
            if (player.distanceToSqr(m.lpos.getX(), m.lpos.getY(), m.lpos.getZ()) < 100) {
                BlockEntity te = world.getBlockEntity(m.lpos);
                if (te instanceof TileEntityLaptop tel) {
                    if (!m.carpet && tel.b2spirit > 0) {
                        int XX = 11;
                        int ZZ = 10;
                        if (tel.rrteam == RivalRebelsTeam.OMEGA) {
                            XX = (m.tpos.getX() - RivalRebels.round.omegaData.objPos().getX());
                            ZZ = (m.tpos.getZ() - RivalRebels.round.omegaData.objPos().getZ());
                        }
                        if (tel.rrteam == RivalRebelsTeam.SIGMA) {
                            XX = (m.tpos.getX() - RivalRebels.round.sigmaData.objPos().getX());
                            ZZ = (m.tpos.getZ() - RivalRebels.round.sigmaData.objPos().getZ());
                        }
                        int xx = m.tpos.getX() - m.lpos.getX();
                        int zz = m.tpos.getZ() - m.lpos.getZ();
                        if (xx * xx + zz * zz > 625 && XX * XX + ZZ * ZZ > 200) {
                            tel.b2spirit--;
                            world.addFreshEntity(new EntityB2Spirit(world, m.tpos.getX(), m.tpos.getY(), m.tpos.getZ(), player.getX(), player.getY(), player.getZ(), false, player.isShiftKeyDown()));
                        }
                    }
                    if (m.carpet && tel.b2carpet > 0) {
                        int XX = 11;
                        int ZZ = 10;
                        int xx = m.tpos.getX() - m.lpos.getX();
                        int zz = m.tpos.getZ() - m.lpos.getZ();
                        if (xx * xx + zz * zz > 625 && XX * XX + ZZ * ZZ > 200) {
                            tel.b2carpet--;
                            world.addFreshEntity(new EntityB2Spirit(world, m.tpos.getX(), m.tpos.getY(), m.tpos.getZ(), player.getX(), player.getY(), player.getZ(), true, false));
                        }
                    }
                }
            }
        });
    }
}

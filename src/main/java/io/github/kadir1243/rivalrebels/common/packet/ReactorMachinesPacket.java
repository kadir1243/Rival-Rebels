package io.github.kadir1243.rivalrebels.common.packet;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record ReactorMachinesPacket(BlockPos reactorPos, List<MachineEntry> machines) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, ReactorMachinesPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        ReactorMachinesPacket::reactorPos,
        MachineEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
        ReactorMachinesPacket::machines,
        ReactorMachinesPacket::new
    );
    public static final CustomPacketPayload.Type<ReactorMachinesPacket> TYPE = new CustomPacketPayload.Type<>(RRIdentifiers.create("reactor_machines_packet"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public record MachineEntry(BlockPos pos, BlockState block, boolean enabled) {
        public static final StreamCodec<RegistryFriendlyByteBuf, MachineEntry> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            MachineEntry::pos,
            ByteBufCodecs.fromCodecWithRegistries(BlockState.FULL_CODEC),
            MachineEntry::block,
            ByteBufCodecs.BOOL,
            MachineEntry::enabled,
            MachineEntry::new
        );
    }
}

package assets.rivalrebels.common.packet;

import assets.rivalrebels.RRIdentifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ReactorStatePacket(BlockPos pos, Type packetType) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ReactorStatePacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        ReactorStatePacket::pos,
        Type.STREAM_CODEC,
        ReactorStatePacket::packetType,
        ReactorStatePacket::new
    );
    public static final CustomPacketPayload.Type<ReactorStatePacket> TYPE = new CustomPacketPayload.Type<>(RRIdentifiers.create("reactor_state_packet"));

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Type {
        TOGGLE_ON,
        EJECT_CORE;
        public static final StreamCodec<FriendlyByteBuf, Type> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public Type decode(FriendlyByteBuf buffer) {
                return buffer.readBoolean() ? TOGGLE_ON : EJECT_CORE;
            }

            @Override
            public void encode(FriendlyByteBuf buffer, Type type) {
                buffer.writeBoolean(type == TOGGLE_ON);
            }
        };
    }
}

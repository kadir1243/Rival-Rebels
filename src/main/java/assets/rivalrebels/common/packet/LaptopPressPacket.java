package assets.rivalrebels.common.packet;

import assets.rivalrebels.RRIdentifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record LaptopPressPacket(BlockPos pos) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, LaptopPressPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        LaptopPressPacket::pos,
        LaptopPressPacket::new
    );
    public static final Type<LaptopPressPacket> TYPE = new Type<>(RRIdentifiers.create("laptop_press_packet"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

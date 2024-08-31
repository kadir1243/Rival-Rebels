package assets.rivalrebels.common.entity;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.util.StringRepresentable;

public enum RhodesType implements StringRepresentable {
    Rhodes,
    Magnesium,
    Arsenic,
    Vanadium,
    Aurum,
    Iodine,
    Iron,
    Astatine,
    Cobalt,
    Strontium,
    Bismuth,
    Zinc,
    Osmium,
    Neon,
    Argent,
    Wolfram,
    Space;

    public static final Codec<RhodesType> CODEC = StringRepresentable.fromValues(RhodesType::values);
    public static final StreamCodec<ByteBuf, RhodesType> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
    public static final EntityDataSerializer<RhodesType> DATA_SERIALIZER = EntityDataSerializer.forValueType(STREAM_CODEC);

    @Override
    public String getSerializedName() {
        return name();
    }

    static {
        EntityDataSerializers.registerSerializer(DATA_SERIALIZER);
    }
}

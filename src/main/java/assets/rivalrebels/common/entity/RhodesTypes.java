package assets.rivalrebels.common.entity;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

public enum RhodesTypes implements RhodesType {
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

    public static final EntityDataSerializer<Holder<RhodesType>> HOLDER_DATA_SERIALIZER = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(RivalRebels.RHODES_TYPE_REGISTRY_KEY));
    public static final ResourceLocation DEFAULT_TEXTURE = RRIdentifiers.create("textures/entity/rhodes.png");
    private static final float[] colors = {
        255/255f,     255/255f,     255/255f, //1
        125/255f,     142/255f,     180/255f, //2
        146/255f,      68/255f,      68/255f, //3
        102/255f,     102/255f,      96/255f, //4
        217/255f,     202/255f,     119/255f, //5
        176/255f,     127/255f,     250/255f, //6
        153/255f,     137/255f,      89/255f, //7
        253/255f,     178/255f,     142/255f, //8
        114/255f,     187/255f,     255/255f, //9
        251/255f,     209/255f,      97/255f, //10
        137/255f,     160/255f,     143/255f, //11
        230/255f,     150/255f,     250/255f, //12
        129/255f,     123/255f,     163/255f, //13
        211/255f,     235/255f,     113/255f, //14
        145/255f,     163/255f,     175/255f, //15
        34/255f,      31/255f,      31/255f, //16
        255/255f,     255/255f,     255/255f, //17
    };

    public int getColor() {
        int colorType = ordinal();
        return FastColor.ARGB32.colorFromFloat(1F, colors[colorType *3], colors[colorType *3+1], colors[colorType *3+2]);
    }

    @Override
    public String getSerializedName() {
        return name();
    }

    @Override
    public ResourceLocation getTexture() {
        return DEFAULT_TEXTURE;
    }

    public static void init() {
        for (RhodesTypes types : values()) {
            Registry.register(RivalRebels.RHODES_TYPE_REGISTRY, RRIdentifiers.create(types.name()), types);
        }
    }

    static {
        EntityDataSerializers.registerSerializer(HOLDER_DATA_SERIALIZER);
    }
}

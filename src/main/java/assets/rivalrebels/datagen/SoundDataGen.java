package assets.rivalrebels.datagen;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.datagen.internal.FabricSoundProvider;
import assets.rivalrebels.datagen.internal.SoundBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceLocation;
import java.util.Arrays;

public class SoundDataGen extends FabricSoundProvider {
    public SoundDataGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateSounds(SoundGenerator soundGenerator) {
        soundGenerator.add(RRSounds.ARTILLERY_EXPLODE, sounds("aa/a1", "aa/a2"));
        soundGenerator.add(RRSounds.ARTILLERY_FLY, sounds("aa/b1", "aa/b2", "aa/b3"));
        soundGenerator.add(RRSounds.ARTILLERY_DESTROY, sounds("aa/c"));
        soundGenerator.add(RRSounds.ARTILLERY_FIRE, sounds("aa/d1","aa/d2","aa/d3"));

        soundGenerator.add(RRSounds.AUTO_BUILD, sounds("ab/a1", "ab/a2"));

        soundGenerator.add(RRSounds.BLASTER_WARMING_UP, sounds("ac/a"));
        soundGenerator.add(RRSounds.BLASTER_FIRE, sounds("ac/b1", "ac/b2", "ac/b3"));
        soundGenerator.add(RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS, sounds("ac/c"));
        soundGenerator.add(RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS2, sounds("ac/d"));

        soundGenerator.add(RRSounds.CRATE, sounds("ad/a1", "ad/a2", "ad/a3", "ad/a4"));

        soundGenerator.add(RRSounds.CUCHILLO_GLASS_BREAK, sounds("ae/a1","ae/a2","ae/a3"));
        soundGenerator.add(RRSounds.CUCHILLO_UNKNOWN, sounds("ae/b1","ae/b2","ae/b3","ae/b4"));
        soundGenerator.add(RRSounds.CUCHILLO_UNKNOWN2, sounds("ae/c1","ae/c2","ae/c3"));
        soundGenerator.add(RRSounds.CUCHILLO_UNKNOWN3, sounds("ae/d1","ae/d2","ae/d3"));
        soundGenerator.add(RRSounds.CUCHILLO_UNKNOWN4, sounds("ae/e"));

        soundGenerator.add(RRSounds.FORCE_FIELD, sounds("af/a"));
        soundGenerator.add(RRSounds.ROD_DISK_HIT_ENTITY, sounds("af/b1","af/b2","af/b3","af/b4"));
        soundGenerator.add(RRSounds.ROD_DISK_MIRROR_FROM_OBJECT, sounds("af/c"));

        soundGenerator.add(RRSounds.RODDISK_UNKNOWN0, sounds("af/a/a"));
        soundGenerator.add(RRSounds.RODDISK_UNKNOWN1, sounds("af/a/b"));
        soundGenerator.add(RRSounds.RODDISK_UNKNOWN2, sounds("af/a/c"));
        soundGenerator.add(RRSounds.RODDISK_UNKNOWN3, sounds("af/a/d"));

        soundGenerator.add(RRSounds.RODDISK_UNKNOWN4, sounds("af/b/a"));
        soundGenerator.add(RRSounds.RODDISK_UNKNOWN5, sounds("af/b/b"));
        soundGenerator.add(RRSounds.RODDISK_UNKNOWN6, sounds("af/b/c"));
        soundGenerator.add(RRSounds.RODDISK_UNKNOWN7, sounds("af/b/d"));

        soundGenerator.add(RRSounds.FLAME_THROWER_USE, sounds("ag/a1","ag/a2"));
        soundGenerator.add(RRSounds.FLAME_THROWER_EXTINGUISH, sounds("ag/b"));
        soundGenerator.add(RRSounds.FLAME_THROWER_UNKNOWN, sounds("ag/c"));
    }

    private static SoundBuilder[] sounds(String... sounds) {
        return Arrays.stream(sounds).map(SoundDataGen::id).map(SoundBuilder::sound).toArray(SoundBuilder[]::new);
    }

    private static ResourceLocation id(String s) {
        return RRIdentifiers.create(s);
    }
}

package assets.rivalrebels.datagen;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.datagen.internal.FabricSoundProvider;
import assets.rivalrebels.datagen.internal.SoundBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.util.Identifier;

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
    }

    private static SoundBuilder[] sounds(String... sounds) {
        return Arrays.stream(sounds).map(SoundDataGen::id).map(SoundBuilder::sound).toArray(SoundBuilder[]::new);
    }

    private static Identifier id(String s) {
        return RRIdentifiers.create(s);
    }
}

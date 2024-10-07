package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.Arrays;

public class SoundDataGen extends SoundDefinitionsProvider {
    public SoundDataGen(PackOutput dataOutput, ExistingFileHelper helper) {
        super(dataOutput, RRIdentifiers.MODID, helper);
    }

    @Override
    public void registerSounds() {
        add(RRSounds.ARTILLERY_EXPLODE, sounds("aa/a1", "aa/a2"));
        add(RRSounds.ARTILLERY_FLY, sounds("aa/b1", "aa/b2", "aa/b3"));
        add(RRSounds.ARTILLERY_DESTROY, sounds("aa/c"));
        add(RRSounds.ARTILLERY_FIRE, sounds("aa/d1","aa/d2","aa/d3"));

        add(RRSounds.AUTO_BUILD, sounds("ab/a1", "ab/a2"));

        add(RRSounds.BLASTER_WARMING_UP, sounds("ac/a"));
        add(RRSounds.BLASTER_FIRE, sounds("ac/b1", "ac/b2", "ac/b3"));
        add(RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS, sounds("ac/c"));
        add(RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS2, sounds("ac/d"));

        add(RRSounds.CRATE, sounds("ad/a1", "ad/a2", "ad/a3", "ad/a4"));

        add(RRSounds.CUCHILLO_GLASS_BREAK, sounds("ae/a1","ae/a2","ae/a3"));
        add(RRSounds.CUCHILLO_UNKNOWN, sounds("ae/b1","ae/b2","ae/b3","ae/b4"));
        add(RRSounds.CUCHILLO_UNKNOWN2, sounds("ae/c1","ae/c2","ae/c3"));
        add(RRSounds.CUCHILLO_UNKNOWN3, sounds("ae/d1","ae/d2","ae/d3"));
        add(RRSounds.CUCHILLO_UNKNOWN4, sounds("ae/e"));

        add(RRSounds.FORCE_FIELD, sounds("af/a"));
        add(RRSounds.ROD_DISK_HIT_ENTITY, sounds("af/b1","af/b2","af/b3","af/b4"));
        add(RRSounds.ROD_DISK_MIRROR_FROM_OBJECT, sounds("af/c"));

        add(RRSounds.RODDISK_UNKNOWN0, sounds("af/a/a"));
        add(RRSounds.RODDISK_UNKNOWN1, sounds("af/a/b"));
        add(RRSounds.RODDISK_UNKNOWN2, sounds("af/a/c"));
        add(RRSounds.RODDISK_UNKNOWN3, sounds("af/a/d"));

        add(RRSounds.RODDISK_UNKNOWN4, sounds("af/b/a"));
        add(RRSounds.RODDISK_UNKNOWN5, sounds("af/b/b"));
        add(RRSounds.RODDISK_UNKNOWN6, sounds("af/b/c"));
        add(RRSounds.RODDISK_UNKNOWN7, sounds("af/b/d"));

        add(RRSounds.FLAME_THROWER_USE, sounds("ag/a1","ag/a2"));
        add(RRSounds.FLAME_THROWER_EXTINGUISH, sounds("ag/b"));
        add(RRSounds.FLAME_THROWER_UNKNOWN, sounds("ag/c"));

        add(RRSounds.GRENADE_UNKNOWN, sounds("ah/a1","ah/a2"));
        add(RRSounds.GRENADE_UNKNOWN2, sounds("ah/b"));
        add(RRSounds.GRENADE_UNKNOWN3, sounds("ah/c"));
        add(RRSounds.GRENADE_UNKNOWN4, sounds("ah/d1","ah/d2","ah/d3"));

        add(RRSounds.GUI_UNKNOWN,  sounds("ai/a"));
        add(RRSounds.GUI_UNKNOWN2, sounds("ai/b"));
        add(RRSounds.GUI_UNKNOWN3, sounds("ai/c"));
        add(RRSounds.GUI_UNKNOWN4, sounds("ai/d"));
        add(RRSounds.GUI_UNKNOWN5, sounds("ai/e"));
        add(RRSounds.GUI_UNKNOWN6, sounds("ai/f"));
        add(RRSounds.GUI_UNKNOWN7, sounds("ai/g"));
        add(RRSounds.GUI_UNKNOWN8, sounds("ai/h"));
        add(RRSounds.GUI_UNKNOWN9, sounds("ai/i"));

        add(RRSounds.LAND_MINE, sounds("aj/a"));
        add(RRSounds.LAND_MINE2, sounds("aj/b"));

        add(RRSounds.LAPTOP,  sounds("ak/a"));
        add(RRSounds.LAPTOP2, sounds("ak/b"));
        add(RRSounds.LAPTOP3, sounds("ak/c"));

        add(RRSounds.MANDELEED,  sounds("al/a"));
        add(RRSounds.MANDELEED2, sounds("al/b"));

        add(RRSounds.NUKE, sounds("am/a"));

        add(RRSounds.PILL,  sounds("an/a1","an/a2"));
        add(RRSounds.PILL2, sounds("an/b1","an/b2"));

        add(RRSounds.PLASMA,  sounds("ao/a"));
        add(RRSounds.PLASMA2, sounds("ao/b"));
        add(RRSounds.PLASMA3, sounds("ao/c"));

        add(RRSounds.PLIERS, sounds("ap/a1","ap/a2","ap/a3","ap/a4"));

        add(RRSounds.PRECURSOR,  sounds("aq/a"));
        add(RRSounds.PRECURSOR2, sounds("aq/b"));

        add(RRSounds.PRINTER,  sounds("ar/a"));
        add(RRSounds.PRINTER2, sounds("ar/b"));
        add(RRSounds.PRINTER3, sounds("ar/c"));

        add(RRSounds.QUICK_SAND, sounds("as/a1","as/a2"));

    }

    private static SoundDefinition sounds(String... sounds) {
        return definition().with(Arrays.stream(sounds).map(RRIdentifiers::create).map(SoundDefinitionsProvider::sound).toArray(SoundDefinition.Sound[]::new));
    }
}

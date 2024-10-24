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
package io.github.kadir1243.rivalrebels.common.core;

import io.github.kadir1243.rivalrebels.RRIdentifiers;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RRSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, RRIdentifiers.MODID);
    // TODO: Is This Names correct ?
    public static final DeferredHolder<SoundEvent, SoundEvent> ARTILLERY_EXPLODE = register("artillery_explode"); // 0.0
    public static final DeferredHolder<SoundEvent, SoundEvent> ARTILLERY_FLY = register("artillery_fly"); // 0.1
    public static final DeferredHolder<SoundEvent, SoundEvent> ARTILLERY_DESTROY = register("artillery_destroy"); // 0.2
    public static final DeferredHolder<SoundEvent, SoundEvent> ARTILLERY_FIRE = register("artillery_fire"); // 0.3
    public static final DeferredHolder<SoundEvent, SoundEvent> AUTO_BUILD = register("auto_build"); // 1.0
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_WARMING_UP = register("blaster_warming_up"); // 2.0
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_FIRE = register("blaster_fire_off"); // 2.1
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_MESSAGE_FROM_OTHER_PLANETS /*:D i dont know what is it*/ = register("blaster_message_from_other_planets"); // 2.2
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTER_MESSAGE_FROM_OTHER_PLANETS2 /*:D i dont know what is it 2*/ = register("blaster_message_from_other_planets2"); // 2.3
    public static final DeferredHolder<SoundEvent, SoundEvent> CRATE = register("crate"); // 3.0
    public static final DeferredHolder<SoundEvent, SoundEvent> CUCHILLO_GLASS_BREAK = register("cuchillo_glass_break"); // 4.0
    public static final DeferredHolder<SoundEvent, SoundEvent> CUCHILLO_UNKNOWN = register("cuchillo_unknown"); // 4.1
    public static final DeferredHolder<SoundEvent, SoundEvent> CUCHILLO_UNKNOWN2 = register("cuchillo_unknown2"); // 4.2
    public static final DeferredHolder<SoundEvent, SoundEvent> CUCHILLO_UNKNOWN3 = register("cuchillo_unknown3"); // 4.3
    public static final DeferredHolder<SoundEvent, SoundEvent> CUCHILLO_UNKNOWN4 = register("cuchillo_unknown4"); // 4.4
    public static final DeferredHolder<SoundEvent, SoundEvent> FORCE_FIELD = register("force_field"); // 5.0
    public static final DeferredHolder<SoundEvent, SoundEvent> ROD_DISK_HIT_ENTITY = register("rod_disk_hit_entity"); // 5.1
    public static final DeferredHolder<SoundEvent, SoundEvent> ROD_DISK_MIRROR_FROM_OBJECT = register("rod_disk_mirror_from_object"); // 5.2
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN0 = register("roddisk_unknown0"); // 6.0
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN1 = register("roddisk_unknown1"); // 6.1
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN2 = register("roddisk_unknown2"); // 6.2
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN3 = register("roddisk_unknown3"); // 6.3
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN4 = register("roddisk_unknown4"); // 7.0
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN5 = register("roddisk_unknown5"); // 7.1
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN6 = register("roddisk_unknown6"); // 7.2
    public static final DeferredHolder<SoundEvent, SoundEvent> RODDISK_UNKNOWN7 = register("roddisk_unknown7"); // 7.3
    public static final DeferredHolder<SoundEvent, SoundEvent> FLAME_THROWER_USE = register("flame_thrower_use"); // 8.0
    public static final DeferredHolder<SoundEvent, SoundEvent> FLAME_THROWER_EXTINGUISH = register("flame_thrower_extinguish"); // 8.1
    public static final DeferredHolder<SoundEvent, SoundEvent> FLAME_THROWER_UNKNOWN = register("flame_thrower_unknown"); // 8.2
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADE_UNKNOWN = register("grenade_unknown"); // 9.0
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADE_UNKNOWN2 = register("grenade_unknown2"); // 9.1
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADE_UNKNOWN3 = register("grenade_unknown3"); // 9.2
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADE_UNKNOWN4 = register("grenade_unknown4"); // 9.3
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN = register("gui_unknown"); // 10.0
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN2 = register("gui_unknown2"); // 10.1
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN3 = register("gui_unknown3"); // 10.2
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN4 = register("gui_unknown4"); // 10.3
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN5 = register("gui_unknown5"); // 10.4
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN6 = register("gui_unknown6"); // 10.5
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN7 = register("gui_unknown7"); // 10.6
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN8 = register("gui_unknown8"); // 10.7
    public static final DeferredHolder<SoundEvent, SoundEvent> GUI_UNKNOWN9 = register("gui_unknown9"); // 10.8
    public static final DeferredHolder<SoundEvent, SoundEvent> LAND_MINE = register("land_mine"); // 11.0
    public static final DeferredHolder<SoundEvent, SoundEvent> LAND_MINE2 = register("land_mine2"); // 11.1
    public static final DeferredHolder<SoundEvent, SoundEvent> LAPTOP = register("laptop"); // 12.0
    public static final DeferredHolder<SoundEvent, SoundEvent> LAPTOP2 = register("laptop2"); // 12.1
    public static final DeferredHolder<SoundEvent, SoundEvent> LAPTOP3 = register("laptop3"); // 12.2
    public static final DeferredHolder<SoundEvent, SoundEvent> MANDELEED = register("mendeleed"); // 13.0
    public static final DeferredHolder<SoundEvent, SoundEvent> MANDELEED2 = register("mendeleed2"); // 13.1
    public static final DeferredHolder<SoundEvent, SoundEvent> NUKE = register("nuke"); // 14.0
    public static final DeferredHolder<SoundEvent, SoundEvent> PILL = register("pill"); // 15.0
    public static final DeferredHolder<SoundEvent, SoundEvent> PILL2 = register("pill2"); // 15.1
    public static final DeferredHolder<SoundEvent, SoundEvent> PLASMA = register("plasma"); // 16.0
    public static final DeferredHolder<SoundEvent, SoundEvent> PLASMA2 = register("plasma2"); // 16.1
    public static final DeferredHolder<SoundEvent, SoundEvent> PLASMA3 = register("plasma3"); // 16.2
    public static final DeferredHolder<SoundEvent, SoundEvent> PLIERS = register("pliers"); // 17.0
    public static final DeferredHolder<SoundEvent, SoundEvent> PRECURSOR = register("precursor"); // 18.0
    public static final DeferredHolder<SoundEvent, SoundEvent> PRECURSOR2 = register("precursor2"); // 18.1
    public static final DeferredHolder<SoundEvent, SoundEvent> PRINTER = register("printer"); // 19.0
    public static final DeferredHolder<SoundEvent, SoundEvent> PRINTER2 = register("printer2"); // 19.1
    public static final DeferredHolder<SoundEvent, SoundEvent> PRINTER3 = register("printer3"); // 19.2
    public static final DeferredHolder<SoundEvent, SoundEvent> QUICK_SAND = register("quick_sand"); // 20.0

    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }

	public static void onSoundLoad() {
        /*String[] soundFiles = {
				// artillery
				"aa/a1.ogg",
				"aa/a2.ogg",
				"aa/b1.ogg",
				"aa/b2.ogg",
				"aa/b3.ogg",
				"aa/c.ogg",
				"aa/d1.ogg",
				"aa/d2.ogg",
				"aa/d3.ogg",
				// autobuild
				"ab/a1.ogg",
				"ab/a2.ogg",
				// blaster
				"ac/a.ogg",
				"ac/b1.ogg",
				"ac/b2.ogg",
				"ac/b3.ogg",
				"ac/c.ogg",
				"ac/d.ogg",
				// crate
				"ad/a1.ogg",
				"ad/a2.ogg",
				"ad/a3.ogg",
				"ad/a4.ogg",
				// cuchillo
				"ae/a1.ogg",
				"ae/a2.ogg",
				"ae/a3.ogg",
				"ae/b1.ogg",
				"ae/b2.ogg",
				"ae/b3.ogg",
				"ae/b4.ogg",
				"ae/c1.ogg",
				"ae/c2.ogg",
				"ae/c3.ogg",
				"ae/d1.ogg",
				"ae/d2.ogg",
				"ae/d3.ogg",
				"ae/e.ogg",
				// disk
				"af/a.ogg",
				"af/b1.ogg",
				"af/b2.ogg",
				"af/b3.ogg",
				"af/b4.ogg",
				"af/c.ogg",
				// diskhigh
				"af/a/a.ogg",
				"af/a/b.ogg",
				"af/a/c.ogg",
				"af/a/d.ogg",
				// disklow
				"af/b/a.ogg",
				"af/b/b.ogg",
				"af/b/c.ogg",
				"af/b/d.ogg",
				// fire
				"ag/a1.ogg",
				"ag/a2.ogg",
				"ag/b.ogg",
				"ag/c.ogg",
				// grenade
				"ah/a1.ogg",
				"ah/a2.ogg",
				"ah/b.ogg",
				"ah/c.ogg",
				"ah/d1.ogg",
				"ah/d2.ogg",
				"ah/d3.ogg",
				// gui
				"ai/a.ogg",
				"ai/b.ogg",
				"ai/c.ogg",
				"ai/d.ogg",
				"ai/e.ogg",
				"ai/f.ogg",
				"ai/g.ogg",
				"ai/h.ogg",
				"ai/i.ogg",
				// landmine
				"aj/a.ogg",
				"aj/b.ogg",
				// laptop
				"ak/a.ogg",
				"ak/b.ogg",
				"ak/c.ogg",
				// mendeleed
				"al/a.ogg",
				"al/b.ogg",
				// nuke
				"am/a.ogg",
				// pill
				"an/a1.ogg",
				"an/a2.ogg",
				"an/b1.ogg",
				"an/b2.ogg",
				// plasma
				"ao/a.ogg",
				"ao/b.ogg",
				"ao/c.ogg",
				// pliers
				"ap/a1.ogg",
				"ap/a2.ogg",
				"ap/a3.ogg",
				"ap/a4.ogg",
				// precursor
				"aq/a.ogg",
				"aq/b.ogg",
				// printer
				"ar/a.ogg",
				"ar/b.ogg",
				"ar/c.ogg",
				// quicksand
				"as/a1.ogg",
				"as/a2.ogg",
				// reactor
				"at/a.ogg",
				"at/b.ogg",
				"at/c.ogg",
				"at/d.ogg",
				// remote
				"au/a.ogg",
				"au/b1.ogg",
				"au/b2.ogg",
				"au/c.ogg",
				"au/d.ogg",
				// rocket
				"av/a1.ogg",
				"av/a2.ogg",
				"av/b.ogg",
				"av/c1.ogg",
				"av/c2.ogg",
				"av/d.ogg",
				"av/e.ogg",
				// rod
				"aw/a.ogg",
				"aw/b.ogg",
				"aw/c.ogg",
				"aw/d1.ogg",
				"aw/d2.ogg",
				"aw/e.ogg",
				// tesla
				"ax/a.ogg",
				"ax/b1.ogg",
				"ax/b2.ogg",
				"ax/b3.ogg",
				"ax/b4.ogg",
				// timedbomb
				"ay/a1.ogg",
				"ay/a2.ogg",
				"ay/b.ogg",
				"ay/c.ogg",
				// toaster
				"az/a1.ogg",
				"az/a2.ogg",
				// voice
				"ba/a1.ogg",
				"ba/a2.ogg",
				"ba/b.ogg",
				"ba/c.ogg",
				"ba/d.ogg",
				"ba/e.ogg",
				"ba/f.ogg",
				"ba/g.ogg",
				"ba/h.ogg",
				"ba/i.ogg",
				"ba/j.ogg",
				"ba/k.ogg",
				"ba/l.ogg",
				"ba/m.ogg",
				"ba/n.ogg",
				"ba/o.ogg",
				"ba/p.ogg",
				"ba/q.ogg",
				"ba/r.ogg",
				"ba/s.ogg",
		};*/
	}

    private static DeferredHolder<SoundEvent, SoundEvent> register(String sound) {
        return SOUNDS.register(sound, SoundEvent::createVariableRangeEvent);
    }
}

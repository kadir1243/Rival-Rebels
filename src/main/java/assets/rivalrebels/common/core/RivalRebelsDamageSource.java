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
package assets.rivalrebels.common.core;

import assets.rivalrebels.RRIdentifiers;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.Optional;

public class RivalRebelsDamageSource {
    public static DamageSource electricity(World world) {
        return of(world, RRDamageTypes.ELECTRICITY);
    }

    public static DamageSource radioactivePoisoning(World world) {
        return of(world, RRDamageTypes.RADIOACTIVE_POISONING);
    }

    public static DamageSource nuclearBlast(World world) {
        return of(world, RRDamageTypes.NUCLEAR_BLAST);
    }

    public static DamageSource cooked(World world) {
        return of(world, RRDamageTypes.COOKED);
    }

    public static DamageSource gasGrenade(World world) {
        return of(world, RRDamageTypes.GAS_GRENADE);
    }

    public static DamageSource cuchillo(World world) {
        return of(world, RRDamageTypes.CUCHILLO);
    }

    public static DamageSource tron(World world) {
        return of(world, RRDamageTypes.TRON);
    }

    public static DamageSource cyanide(World world) {
        return of(world, RRDamageTypes.CYANIDE);
    }

    public static DamageSource landmine(World world) {
        return of(world, RRDamageTypes.LANDMINE);
    }

    public static DamageSource timedBomb(World world) {
        return of(world, RRDamageTypes.TIMED_BOMB);
    }

    public static DamageSource flare(World world) {
        return of(world, RRDamageTypes.FLARE);
    }

    public static DamageSource charge(World world) {
        return of(world, RRDamageTypes.CHARGE);
    }

    public static DamageSource plasmaExplosion(World world) {
        return of(world, RRDamageTypes.PLASMA_EXPLOSION);
    }

    public static DamageSource rocket(World world) {
        return of(world, RRDamageTypes.ROCKET);
    }

    public static DamageSource laserBurst(World world) {
        return of(world, RRDamageTypes.LASER_BURST);
    }

    private static DamageSource of(World world, RegistryKey<DamageType> key) {
        Optional<RegistryEntry.Reference<DamageType>> entry = world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(key);

        // TODO: Implement damage sources
        return entry.map(DamageSource::new).orElseGet(() -> world.getDamageSources().generic());
    }

    /*public static final DamageSource electricity = new DamageSource("electricity").setBypassesArmor().setUnblockable();
    public static final DamageSource radioactivepoisoning = new DamageSource("radioactivepoisoning").setBypassesArmor().setUnblockable();
    public static final DamageSource nuclearblast = new DamageSource("nuclearblast").setUnblockable();
    public static final DamageSource cooked = new DamageSource("cooked").setUnblockable();
    public static final DamageSource gasgrenade = new DamageSource("gasgrenade").setUnblockable();
    public static final DamageSource cuchillo = new DamageSource("cuchillo");
    public static final DamageSource tron = new DamageSource("tron").setUnblockable();
    public static final DamageSource cyanide = new DamageSource("cyanide").setUnblockable();
    public static final DamageSource landmine = new DamageSource("landmine").setUnblockable();
    public static final DamageSource timebomb = new DamageSource("timebomb").setUnblockable();
    public static final DamageSource flare = new DamageSource("flare").setUnblockable();
    public static final DamageSource charge = new DamageSource("charge").setUnblockable();
    public static final DamageSource plasmaexplosion = new DamageSource("plasmaexplosion").setUnblockable();
    public static final DamageSource rocket = new DamageSource("rocket").setUnblockable();
    public static final DamageSource laserburst = new DamageSource("laserburst").setUnblockable();*/

    public static class RRDamageTypes {
        public static final RegistryKey<DamageType> ELECTRICITY = register("electricity");
        public static final RegistryKey<DamageType> RADIOACTIVE_POISONING = register("radioactive_poisoning");
        public static final RegistryKey<DamageType> NUCLEAR_BLAST = register("nuclear_blast");
        public static final RegistryKey<DamageType> COOKED = register("cooked");
        public static final RegistryKey<DamageType> GAS_GRENADE = register("gas_grenade");
        public static final RegistryKey<DamageType> CUCHILLO = register("cuchillo");
        public static final RegistryKey<DamageType> TRON = register("tron");
        public static final RegistryKey<DamageType> CYANIDE = register("cyanide");
        public static final RegistryKey<DamageType> LANDMINE = register("landmine");
        public static final RegistryKey<DamageType> TIMED_BOMB = register("timed_bomb");
        public static final RegistryKey<DamageType> FLARE = register("flare");
        public static final RegistryKey<DamageType> CHARGE = register("charge");
        public static final RegistryKey<DamageType> PLASMA_EXPLOSION = register("plasma_explosion");
        public static final RegistryKey<DamageType> ROCKET = register("rocket");
        public static final RegistryKey<DamageType> LASER_BURST = register("laser_burst");

        private static RegistryKey<DamageType> register(String name) {
            return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RRIdentifiers.create(name));
        }

        public static void init() {

        }
    }
}

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class RivalRebelsDamageSource {
    public static DamageSource electricity(Level world) {
        return of(world, RRDamageTypes.ELECTRICITY);
    }

    public static DamageSource radioactivePoisoning(Level world) {
        return of(world, RRDamageTypes.RADIOACTIVE_POISONING);
    }

    public static DamageSource nuclearBlast(Level world) {
        return of(world, RRDamageTypes.NUCLEAR_BLAST);
    }

    public static DamageSource cooked(Level world) {
        return of(world, RRDamageTypes.COOKED);
    }

    public static DamageSource gasGrenade(Level world) {
        return of(world, RRDamageTypes.GAS_GRENADE);
    }

    public static DamageSource cuchillo(Level world) {
        return of(world, RRDamageTypes.CUCHILLO);
    }

    public static DamageSource tron(Level world) {
        return of(world, RRDamageTypes.TRON);
    }

    public static DamageSource cyanide(Level world) {
        return of(world, RRDamageTypes.CYANIDE);
    }

    public static DamageSource landmine(Level world) {
        return of(world, RRDamageTypes.LANDMINE);
    }

    public static DamageSource timedBomb(Level world) {
        return of(world, RRDamageTypes.TIMED_BOMB);
    }

    public static DamageSource flare(Level world) {
        return of(world, RRDamageTypes.FLARE);
    }

    public static DamageSource charge(Level world) {
        return of(world, RRDamageTypes.CHARGE);
    }

    public static DamageSource plasmaExplosion(Level world) {
        return of(world, RRDamageTypes.PLASMA_EXPLOSION);
    }

    public static DamageSource rocket(Level world) {
        return of(world, RRDamageTypes.ROCKET);
    }

    public static DamageSource laserBurst(Level world) {
        return of(world, RRDamageTypes.LASER_BURST);
    }

    private static DamageSource of(Level world, ResourceKey<DamageType> key) {
        Optional<Holder.Reference<DamageType>> entry = world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolder(key);

        // TODO: Implement damage sources
        return entry.map(DamageSource::new).orElseGet(() -> world.damageSources().generic());
    }

    public static class RRDamageTypes {
        public static final List<ResourceKey<DamageType>> REGISTERED_DAMAGE_TYPES = new ArrayList<>();
        public static final ResourceKey<DamageType> ELECTRICITY = register("electricity");
        public static final ResourceKey<DamageType> RADIOACTIVE_POISONING = register("radioactive_poisoning");
        public static final ResourceKey<DamageType> NUCLEAR_BLAST = register("nuclear_blast");
        public static final ResourceKey<DamageType> COOKED = register("cooked");
        public static final ResourceKey<DamageType> GAS_GRENADE = register("gas_grenade");
        public static final ResourceKey<DamageType> CUCHILLO = register("cuchillo");
        public static final ResourceKey<DamageType> TRON = register("tron");
        public static final ResourceKey<DamageType> CYANIDE = register("cyanide");
        public static final ResourceKey<DamageType> LANDMINE = register("landmine");
        public static final ResourceKey<DamageType> TIMED_BOMB = register("timed_bomb");
        public static final ResourceKey<DamageType> FLARE = register("flare");
        public static final ResourceKey<DamageType> CHARGE = register("charge");
        public static final ResourceKey<DamageType> PLASMA_EXPLOSION = register("plasma_explosion");
        public static final ResourceKey<DamageType> ROCKET = register("rocket");
        public static final ResourceKey<DamageType> LASER_BURST = register("laser_burst");

        private static ResourceKey<DamageType> register(String name) {
            ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE, RRIdentifiers.create(name));
            REGISTERED_DAMAGE_TYPES.add(key);
            return key;
        }

        public static void init() {
        }
    }
}

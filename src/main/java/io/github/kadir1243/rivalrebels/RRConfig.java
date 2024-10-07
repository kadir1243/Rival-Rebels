package io.github.kadir1243.rivalrebels;

import io.github.kadir1243.rivalrebels.common.entity.RhodesType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RRConfig {
    public static class Server {
        private final BooleanValue rhodesRoundsBase;
        private final BooleanValue flareExplodeOnBreak;
        private final IntValue teleportDistance;
        private final IntValue rhodesHealth;
        private final IntValue landmineExplodeSize;
        private final IntValue spawnDomeRadius;
        private final IntValue bunkerRadius;
        private final BooleanValue infiniteAmmo;
        private final BooleanValue infiniteNukes;
        private final IntValue chargeExplosionSize;
        private final IntValue timedbombExplosionSize;
        private final BooleanValue infiniteGrenades;
        private final ConfigValue<List<? extends String>> rhodesTeams;
        private final BooleanValue prefillrhodes;
        private final ConfigValue<Integer> rhodesNukes;
        private final ConfigValue<Integer> objectiveHealth;
        private final ConfigValue<Integer> objectiveDistance;
        private final ConfigValue<Integer> rhodesInRoundsChance;
        private final BooleanValue freeb83nukes;
        private final BooleanValue scoreboardEnabled;
        private final BooleanValue stopSelfnukeinSP;
        private final IntValue rhodesRandomAmmoBonus;
        private final ConfigValue<Integer> rhodesRandomSeed;
        private final BooleanValue rhodesAI;
        private final IntValue rocketExplosionSize;
        private final ConfigValue<Float> rhodesSpeedScale;
        private final ConfigValue<Integer> teslaDecay;
        private final IntValue nuclearBombStrength;
        private final IntValue nuclearBombCountdown;
        private final IntValue maximumResets;
        private final ConfigValue<Integer> b2spirithealth;
        private final IntValue timedbombTimer;
        private final IntValue tsarBombaStrength;
        private final IntValue b83Strength;
        private final BooleanValue nukedrop;
        private final IntValue tsarBombaSpeed;
        private final ConfigValue<Integer> plasmoidDecay;
        private final BooleanValue rhodesFF;
        private final BooleanValue rhodesCC;
        private final BooleanValue rhodesPromode;
        private final ConfigValue<Integer> bombDamageToRhodes;
        private final BooleanValue freeDragonAmmo;
        private final BooleanValue rhodesScaleSpeed;
        private final BooleanValue rhodesBlockBreak;
        private final BooleanValue elevation;
        private final ConfigValue<Integer> flamethrowerDecay;
        private final ConfigValue<Integer> rpgDecay;

        private Server(ModConfigSpec.Builder builder) {
            builder.push("server");

            builder.comment("Measured in blocks.").push("buildsize");

            teleportDistance = builder.defineInRange("TeleportDistance", 150, 70, 500);
            spawnDomeRadius = builder.defineInRange("spawnDomeRadius", 20, 10, 30);
            bunkerRadius = builder.defineInRange("bunkerRadius", 10, 7, 15);
            objectiveDistance = builder.define("objectiveDistance", 200);

            builder.pop();

            builder.comment("Miscellaneous.").push("misc");

            rhodesRoundsBase = builder.define("rhodesRoundsBase", true);
            flareExplodeOnBreak = builder.define("flareExplodeOnBreak", true);
            rhodesHealth = builder.defineInRange("rhodesHealth", 15000, 15000, Integer.MAX_VALUE);
            objectiveHealth = builder.define("objectiveHealth", 15000);
            b2spirithealth = builder.define("b2spirithealth", 1000);
            infiniteAmmo = builder.define("infiniteAmmo", false);
            infiniteNukes = builder.define("infiniteNukes", false);
            infiniteGrenades = builder.define("infiniteGrenades", false);
            rhodesTeams = builder.comment("Repeat the type for multiple occurences of the same rhodes. Leave Empty For Random Rhodes Types").defineList("rhodesTeams", List.of(), o -> RivalRebels.RHODES_TYPE_REGISTRY.containsKey(ResourceLocation.tryParse(o.toString())));
            prefillrhodes = builder.define("prefillrhodes", true);
            rhodesNukes = builder.define("rhodesNukes", 8);
            rhodesInRoundsChance = builder.define("rhodesInRoundsChance", 0);
            rhodesRandomAmmoBonus = builder.comment("Multiplies the Rhodes' random ammo bonus. Set to 0 to disable bonus.").defineInRange("rhodesRandomAmmoBonus", 1, 0, 5);
            rhodesRandomSeed = builder.define("rhodesRandomSeed", 2168344);
            rhodesAI = builder.define("rhodesAIEnabled", true);
            rhodesSpeedScale = builder.define("rhodesSpeedScale", 1F);
            freeb83nukes = builder.define("freeb83nukes", false);
            scoreboardEnabled = builder.define("scoreboardEnabled", true);
            stopSelfnukeinSP = builder.define("stopSelfnukeinSP", false);
            maximumResets = builder.defineInRange("MaximumResets", 2, 0, 100);
            rhodesFF = builder.define("rhodesFriendlyFire", true);
            rhodesCC = builder.define("rhodesTeamFriendlyFire", true);
            rhodesPromode = builder.define("rhodesPromode", false);
            freeDragonAmmo = builder.define("freeDragonAmmo", false);
            bombDamageToRhodes = builder.define("bombDamageToRhodes", 20);
            rhodesScaleSpeed = builder.define("rhodesScaleSpeed", false);
            rhodesBlockBreak = builder.define("rhodesBlockBreak", true);

            builder.pop();

            builder.comment("Measured in blocks. Nuclear bomb just adds the specified number to its calculation.").push("ExplosionSize");

            landmineExplodeSize = builder.defineInRange("landmineExplodeSize", 2, 1, 15);
            chargeExplosionSize = builder.defineInRange("chargeExplosionSize", 5, 1, 15);
            timedbombExplosionSize = builder.defineInRange("timedbombExplosionSize", 6, 1, 20);
            rocketExplosionSize = builder.defineInRange("rocketExplosionSize", 4, 1, 10);
            nuclearBombStrength = builder.defineInRange("NuclearBombStrength", 10, 2, 30);
            tsarBombaStrength = builder.defineInRange("tsarBombaStrength", 24, 0, 50);
            b83Strength = builder.defineInRange("b83Strength", 15, 0, 50);
            nukedrop = builder.define("nukedrop", true);
            tsarBombaSpeed = builder.defineInRange("tsarBombaSpeed", 8, 4, Integer.MAX_VALUE);
            elevation = builder.define("elevation", true);

            builder.pop();

            builder.comment("Measured in ticks of existence. Tesla is in blocks.").push("decay");

            teslaDecay = builder.defineInRange("TeslaDecay", 250, 20, 400);
            plasmoidDecay = builder.define("plasmoidDecay", 70);
            flamethrowerDecay = builder.define("FlamethrowerDecay", 64);
            rpgDecay = builder.define("RPGDecay", 200);

            builder.pop();

            builder.comment("Measured in seconds.").push("timing");

            nuclearBombCountdown = builder.defineInRange("NuclearBombCountdown", 25, 0, Integer.MAX_VALUE);
            timedbombTimer = builder.defineInRange("timedbombTimer", 25, 10, 300);

            builder.pop();

            builder.pop();
        }

        public int getFlamethrowerDecay() {
            return flamethrowerDecay.get();
        }

        public int getRpgDecay() {
            return rpgDecay.get();
        }

        public boolean isElevation() {
            return elevation.get();
        }

        public boolean isTeamFriendlyFireRhodesEnabled() {
            return rhodesCC.get();
        }

        public boolean isFriendlyFireRhodesEnabled() {
            return rhodesFF.get();
        }

        public boolean isRhodesPromode() {
            return rhodesPromode.get();
        }

        public int getBombDamageToRhodes() {
            return bombDamageToRhodes.get();
        }

        public boolean isFreeDragonAmmo() {
            return freeDragonAmmo.get();
        }

        public boolean isRhodesScaleSpeed() {
            return rhodesScaleSpeed.get();
        }

        public boolean getRhodesBlockBreak() {
            return rhodesBlockBreak.get();
        }

        public int getTsarBombaSpeed() {
            return tsarBombaSpeed.get();
        }

        public int getPlasmoidDecay() {
            return plasmoidDecay.get();
        }

        public boolean isNukedrop() {
            return nukedrop.get();
        }

        public int getB83Strength() {
            return b83Strength.get();
        }

        public int getTsarBombaStrength() {
            return tsarBombaStrength.get();
        }

        public int getB2spirithealth() {
            return b2spirithealth.get();
        }

        public int getTimedbombTimer() {
            return timedbombTimer.get();
        }

        public int getTeslaDecay() {
            return teslaDecay.get();
        }

        public int getNuclearBombStrength() {
            return nuclearBombStrength.get();
        }

        public int getMaximumResets() {
            return maximumResets.get();
        }

        public int getNuclearBombCountdown() {
            return nuclearBombCountdown.get();
        }

        public boolean isRhodesRoundsBase() {
            return rhodesRoundsBase.get();
        }

        public int getTeleportDistance() {
            return teleportDistance.get();
        }

        public float getRhodesSpeedScale() {
            return rhodesSpeedScale.get();
        }

        public int getRocketExplosionSize() {
            return rocketExplosionSize.get();
        }

        public boolean isFlareExplodeOnBreak() {
            return flareExplodeOnBreak.get();
        }

        public int getRhodesHealth() {
            return rhodesHealth.get();
        }

        public int getLandmineExplodeSize() {
            return landmineExplodeSize.get();
        }

        public int getSpawnDomeRadius() {
            return spawnDomeRadius.get();
        }

        public int getBunkerRadius() {
            return bunkerRadius.get();
        }

        public boolean isInfiniteAmmo() {
            return infiniteAmmo.get();
        }

        public boolean isInfiniteNukes() {
            return infiniteNukes.get();
        }

        public int getChargeExplosionSize() {
            return chargeExplosionSize.get();
        }

        public int getTimedbombExplosionSize() {
            return timedbombExplosionSize.get();
        }

        public boolean isInfiniteGrenades() {
            return infiniteGrenades.get();
        }

        public RhodesType[] getRhodesTeams() {
            List<? extends String> list = rhodesTeams.get();
            if (list.isEmpty()) {
                return RivalRebels.RHODES_TYPE_REGISTRY.stream().collect(RHODES_TYPES_SHUFFLER).toArray(new RhodesType[0]);
            }
            return list.stream().map(ResourceLocation::tryParse).filter(Objects::nonNull).map(RivalRebels.RHODES_TYPE_REGISTRY::get).filter(Objects::nonNull).toArray(RhodesType[]::new);
        }
        private static final Collector<RhodesType, ?, List<RhodesType>> RHODES_TYPES_SHUFFLER = Collectors.collectingAndThen(
            Collectors.<RhodesType>toList(),
            list -> {
                Collections.shuffle(list);
                return list;
            }
        );
        public boolean isPrefillrhodes() {
            return prefillrhodes.get();
        }

        public int getRhodesNukes() {
            return rhodesNukes.get();
        }

        public int getObjectiveHealth() {
            return objectiveHealth.get();
        }

        public int getObjectiveDistance() {
            return objectiveDistance.get();
        }

        public float getRhodesInRoundsChance() {
            return rhodesInRoundsChance.get() / 100F;
        }

        public boolean isFreeb83nukes() {
            return freeb83nukes.get();
        }

        public boolean isScoreboardEnabled() {
            return scoreboardEnabled.get();
        }

        public boolean isStopSelfnukeinSP() {
            return stopSelfnukeinSP.get();
        }

        public int getRhodesRandomAmmoBonus() {
            return rhodesRandomAmmoBonus.get();
        }

        public int getRhodesRandomSeed() {
            return rhodesRandomSeed.get();
        }

        public boolean isRhodesAIEnabled() {
            return rhodesAI.get();
        }
    }

    public static class Client {
        private final ConfigValue<Float> nukeScale;
        private final ConfigValue<Float> shroomScale;
        private final BooleanValue antimatterFlash;
        private final ConfigValue<String> bomberType;
        private final BooleanValue goreEnabled;
        private final ConfigValue<Integer> teslasegments;

        private Client(ModConfigSpec.Builder builder) {
            builder.push("client");

            builder.push("explosion");

            nukeScale = builder.define("nuke_scale", 1F);
            shroomScale = builder.define("shroom_scale", 1F);
            antimatterFlash = builder.define("antimatter_flash", true);

            builder.pop();

            builder.push("misc");

            bomberType = builder.comment("For the B2 bomber, set to 'b2', for Warfare Shuttle 'sh', for Tupolev-95 'tu'.").define("bomber_type", "b2", o -> o instanceof String && ("b2".equalsIgnoreCase(o.toString()) || "sh".equalsIgnoreCase(o.toString()) || "tu".equalsIgnoreCase(o.toString())));
            goreEnabled = builder.define("gore_enabled", true);
            teslasegments = builder.define("teslasegments", 2);

            builder.pop();

            builder.pop();
        }

        public float getNukeScale() {
            return nukeScale.get();
        }

        public float getShroomScale() {
            return shroomScale.get();
        }

        public boolean isAntimatterFlash() {
            return antimatterFlash.get();
        }

        public String getBomberType() {
            return bomberType.get();
        }

        public boolean isGoreEnabled() {
            return goreEnabled.get();
        }

        public int getTeslaSegments() {
            return teslasegments.get();
        }
    }


    public static class Common {
        private Common(ModConfigSpec.Builder builder) {
            builder.push("general");

            builder.pop();
        }
    }

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;
    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;
    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        final Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();

        final Pair<Server, ModConfigSpec> serverSpecPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();
    }
}

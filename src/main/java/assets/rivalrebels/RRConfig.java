package assets.rivalrebels;

import assets.rivalrebels.common.entity.RhodesType;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class RRConfig {
    public static class Server {
        private final ForgeConfigSpec.BooleanValue rhodesRoundsBase;
        private final ForgeConfigSpec.BooleanValue flareExplodeOnBreak;
        private final ForgeConfigSpec.IntValue teleportDistance;
        private final ForgeConfigSpec.IntValue rhodesHealth;
        private final ForgeConfigSpec.IntValue landmineExplodeSize;
        private final ForgeConfigSpec.IntValue spawnDomeRadius;
        private final ForgeConfigSpec.IntValue bunkerRadius;
        private final ForgeConfigSpec.BooleanValue infiniteAmmo;
        private final ForgeConfigSpec.BooleanValue infiniteNukes;
        private final ForgeConfigSpec.IntValue chargeExplosionSize;
        private final ForgeConfigSpec.IntValue timedbombExplosionSize;
        private final ForgeConfigSpec.BooleanValue infiniteGrenades;
        private final ForgeConfigSpec.ConfigValue<List<? extends RhodesType>> rhodesTeams;
        private final ForgeConfigSpec.BooleanValue prefillrhodes;
        private final ForgeConfigSpec.ConfigValue<Integer> rhodesNukes;
        private final ForgeConfigSpec.ConfigValue<Integer> objectiveHealth;
        private final ForgeConfigSpec.ConfigValue<Integer> objectiveDistance;
        private final ForgeConfigSpec.ConfigValue<Integer> rhodesInRoundsChance;
        private final ForgeConfigSpec.BooleanValue freeb83nukes;
        private final ForgeConfigSpec.BooleanValue scoreboardEnabled;
        private final ForgeConfigSpec.BooleanValue stopSelfnukeinSP;
        private final ForgeConfigSpec.IntValue rhodesRandomAmmoBonus;
        private final ForgeConfigSpec.ConfigValue<Integer> rhodesRandomSeed;
        private final ForgeConfigSpec.BooleanValue rhodesAI;
        private final ForgeConfigSpec.IntValue rocketExplosionSize;
        private final ForgeConfigSpec.ConfigValue<Float> rhodesSpeedScale;
        private final ForgeConfigSpec.ConfigValue<Integer> teslaDecay;
        private final ForgeConfigSpec.IntValue nuclearBombStrength;
        private final ForgeConfigSpec.IntValue nuclearBombCountdown;
        private final ForgeConfigSpec.IntValue maximumResets;
        private final ForgeConfigSpec.ConfigValue<Integer> b2spirithealth;
        private final ForgeConfigSpec.IntValue timedbombTimer;
        private final ForgeConfigSpec.IntValue tsarBombaStrength;
        private final ForgeConfigSpec.IntValue b83Strength;
        private final ForgeConfigSpec.BooleanValue nukedrop;
        private final ForgeConfigSpec.IntValue tsarBombaSpeed;
        private final ForgeConfigSpec.ConfigValue<Integer> plasmoidDecay;
        private final ForgeConfigSpec.BooleanValue rhodesFF;
        private final ForgeConfigSpec.BooleanValue rhodesCC;
        private final ForgeConfigSpec.BooleanValue rhodesPromode;
        private final ForgeConfigSpec.ConfigValue<Integer> bombDamageToRhodes;
        private final ForgeConfigSpec.BooleanValue freeDragonAmmo;
        private final ForgeConfigSpec.BooleanValue rhodesScaleSpeed;
        private final ForgeConfigSpec.BooleanValue rhodesBlockBreak;
        private final ForgeConfigSpec.BooleanValue elevation;
        private final ForgeConfigSpec.ConfigValue<Integer> flamethrowerDecay;
        private final ForgeConfigSpec.ConfigValue<Integer> rpgDecay;

        private Server(ForgeConfigSpec.Builder builder) {
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
            rhodesTeams = builder.comment("Repeat the type for multiple occurences of the same rhodes.").defineList("rhodesTeams", Arrays.stream(RhodesType.values()).toList(), o -> Arrays.stream(RhodesType.values()).anyMatch(type -> type.getSerializedName().equalsIgnoreCase(o.toString())));
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
            return rhodesTeams.get().toArray(new RhodesType[0]);
        }

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
        private final ForgeConfigSpec.ConfigValue<Float> nukeScale;
        private final ForgeConfigSpec.ConfigValue<Float> shroomScale;
        private final ForgeConfigSpec.BooleanValue antimatterFlash;
        private final ForgeConfigSpec.ConfigValue<String> bomberType;
        private final ForgeConfigSpec.BooleanValue goreEnabled;
        private final ForgeConfigSpec.ConfigValue<Integer> teslasegments;

        private Client(ForgeConfigSpec.Builder builder) {
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
        private Common(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            builder.pop();
        }
    }

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();

        final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();
    }
}

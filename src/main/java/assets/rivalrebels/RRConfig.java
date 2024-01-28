package assets.rivalrebels;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class RRConfig {
    public static class Server {
        private final ForgeConfigSpec.BooleanValue rhodesRoundsBase;

        private Server(ForgeConfigSpec.Builder builder) {
            builder.push("server");

            builder.push("misc");

            rhodesRoundsBase = builder.define("rhodesRoundsBase", true);

            builder.pop();

            builder.pop();
        }

        public boolean isRhodesRoundsBase() {
            return rhodesRoundsBase.get();
        }
    }

    public static class Client {
        private final ForgeConfigSpec.ConfigValue<Float> nukeScale;
        private final ForgeConfigSpec.ConfigValue<Float> shroomScale;
        private final ForgeConfigSpec.BooleanValue antimatterFlash;
        private final ForgeConfigSpec.ConfigValue<String> bomberType;
        private final ForgeConfigSpec.BooleanValue goreEnabled;

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

package io.github.kadir1243.rivalrebels.common.util;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.datagen.LangGen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * All Rival Rebels Mod Translation Keys are here <p>
 * Translations should be added with {@link LangGen DataGen}
 */
public class Translations {
    public static final TranslationKey CREATIVE_TAB = new TranslationKey("creative_tab_name");
    public static final TranslationKey WARNING_TRANSLATION = new TranslationKey("warning");
    public static final TranslationKey BOMB_TIMER = new TranslationKey("bomb.timer");
    public static final TranslationKey UNBALANCED_BOMB = new TranslationKey("bomb.unbalanced");
    public static final TranslationKey BOMB_MEGATONS = new TranslationKey("bomb.megatons");
    public static final TranslationKey BOMB_ARMED = new TranslationKey("bomb.armed");
    public static final TranslationKey SHIFT_CLICK = new TranslationKey("sneak.click");
    public static final TranslationKey AMMUNITION_TRANSLATION = new TranslationKey("ammunition");
    public static final TranslationKey ORDERS_TRANSLATION = new TranslationKey("orders");
    public static final TranslationKey STATUS_TRANSLATION = new TranslationKey("status");
    public static final TranslationKey DEFUSE_TRANSLATION = new TranslationKey("defuse");
    public static final TranslationKey NUKE_TRANSLATION = new TranslationKey("nuke_name");
    public static final TranslationKey OVERHEAT_TRANSLATION = new TranslationKey("overheat");
    public static final TranslationKey USE_PLIERS_TO_BUILD_TRANSLATION = new TranslationKey("use_pliers_to_build");
    public static final TranslationKey USE_PLIERS_TO_OPEN_TRANSLATION = new TranslationKey("use_pliers_to_open");
    public static final TranslationKey LAPTOP_B2_SPIRIT = new TranslationKey("laptop_b2_spirit");
    public static final TranslationKey BUILDING_TOKAMAK = new TranslationKey("building_tokamak");
    public static final TranslationKey BUILDING = new TranslationKey("building_crate");
    public static final TranslationKey SPAWN_RESET_WARNING = new TranslationKey("spawn_reset_warning");
    public static final TranslationKey RHODES_IS_ARMED = new TranslationKey("rhodes_is_armed");
    public static final TranslationKey TSAR_NAME = new TranslationKey("tsar_name");

    private static final MutableComponent WARNING = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.DARK_RED))
        .append(WARNING_TRANSLATION.translate().withStyle(ChatFormatting.RED))
        .append(Component.literal("]").withStyle(ChatFormatting.DARK_RED));
    private static final MutableComponent ORDERS = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.GRAY))
        .append(ORDERS_TRANSLATION.translate().withStyle(ChatFormatting.DARK_RED))
        .append(Component.literal("]").withStyle(ChatFormatting.GRAY));
    private static final MutableComponent STATUS = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.GRAY))
        .append(STATUS_TRANSLATION.translate().withStyle(ChatFormatting.GOLD))
        .append(Component.literal("]").withStyle(ChatFormatting.GRAY));
    private static final MutableComponent DEFUSE = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.GRAY))
        .append(DEFUSE_TRANSLATION.translate().withStyle(ChatFormatting.GOLD))
        .append(Component.literal("]").withStyle(ChatFormatting.GRAY));

    public static MutableComponent warning() {
        return WARNING.copy();
    }

    public static MutableComponent orders() {
        return ORDERS.copy();
    }

    public static MutableComponent status() {
        return STATUS.copy();
    }

    public static MutableComponent defuse() {
        return DEFUSE.copy();
    }

    public static MutableComponent ammunition() {
        return AMMUNITION_TRANSLATION.translate();
    }

    public static void sendWarningBombWillExplodeMessageToPlayers(Level level) {
        MutableComponent line1 = warning().append(" ").append(Component.translatable(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_1").withStyle(ChatFormatting.RED));
        MutableComponent line2 = warning().append(" ").append(Component.translatable(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_2").withStyle(ChatFormatting.RED));
        MutableComponent line3 = warning().append(" ").append(Component.translatable(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_3").withStyle(ChatFormatting.RED));
        for (Player player : level.players()) {
            player.sendSystemMessage(line1);
            player.sendSystemMessage(line2);
            player.sendSystemMessage(line3);
        }
    }

    public record TranslationKey(String key, String subKey) {
        public TranslationKey(String key) {
            this(key, null);
        }

        public TranslationKey(String key, String subKey) {
            if (!key.startsWith(RRIdentifiers.MODID + ".")) key = RRIdentifiers.MODID + "." + key;
            if (subKey == null) {
                this.key = key;
            } else {
                this.key = key + "." + subKey;
            }
            this.subKey = subKey;
        }

        public TranslationKey getSubKey(String subKey) {
            return new TranslationKey(key, subKey);
        }

        public MutableComponent translate() {
            return Component.translatable(key);
        }

        public MutableComponent translate(Object... args) {
            return Component.translatable(key, args);
        }
    }
}

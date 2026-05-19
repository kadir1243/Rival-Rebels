package io.github.kadir1243.rivalrebels.common.util;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class Translations {
    public static final TranslationKey CREATIVE_TAB = new TranslationKey("creative_tab_name");
    public static final TranslationKey WARNING_TRANSLATION = new TranslationKey("warning");
    public static final TranslationKey WARNING_MELTDOWN = new TranslationKey("reactor.meltdown");
    public static final TranslationKey BOMB_TIMER = new TranslationKey("bomb.timer");
    public static final TranslationKey UNBALANCED_BOMB = new TranslationKey("bomb.unbalanced");
    public static final TranslationKey BOMB_MEGATONS = new TranslationKey("bomb.megatons");
    public static final TranslationKey BOMB_ARMED = new TranslationKey("bomb.armed");
    public static final TranslationKey SHIFT_CLICK = new TranslationKey("sneak.click");
    public static final TranslationKey AMMUNITION = new TranslationKey("ammunition");
    public static final TranslationKey ORDERS_TRANSLATION = new TranslationKey("orders");
    public static final TranslationKey STATUS_TRANSLATION = new TranslationKey("status");
    public static final TranslationKey DEFUSE_TRANSLATION = new TranslationKey("defuse");
    public static final TranslationKey INVENTORY_TRANSLATION = new TranslationKey("inventory");
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
    public static final TranslationKey ANTIMATTER_BOMB_CONTAINER_NAME = new Translations.TranslationKey("container.antimatterbomb");
    public static final TranslationKey NEXT_BATTLE_TITLE = new TranslationKey("next_battle.title");
    public static final TranslationKey NEXT_BATTLE_SUBTITLE = new TranslationKey("next_battle.subtitle");
    public static final TranslationKey NEXT_BATTLE_QUESTION = new TranslationKey("next_battle.question");
    public static final TranslationKey NEXT_BATTLE_YES = new TranslationKey("next_battle.question.yes");
    public static final TranslationKey NEXT_BATTLE_NO = new TranslationKey("next_battle.question.no");
    public static final TranslationKey BINOCULARS_WEST = new TranslationKey("binoculars.west");
    public static final TranslationKey BINOCULARS_EAST = new TranslationKey("binoculars.east");
    public static final TranslationKey BINOCULARS_NORTH = new TranslationKey("binoculars.north");
    public static final TranslationKey BINOCULARS_SOUTH = new TranslationKey("binoculars.south");
    public static final TranslationKey BINOCULARS_TARGET = new TranslationKey("binoculars.target");
    public static final TranslationKey BINOCULARS_COORDINATES = new TranslationKey("binoculars.coordinates");
    public static final TranslationKey USE_MESSAGE = new TranslationKey("use");
    public static final TranslationKey SIGMA_WIN_TITLE = new TranslationKey("team.sigma.win.title");
    public static final TranslationKey SIGMA_WIN_SUBTITLE = new TranslationKey("team.sigma.win.subtitle");
    public static final TranslationKey OMEGA_WIN_TITLE = new TranslationKey("team.omega.win.title");
    public static final TranslationKey OMEGA_WIN_SUBTITLE = new TranslationKey("team.omega.win.subtitle");
    public static final TranslationKey TEAM_OMEGA = new TranslationKey("team.omega");
    public static final TranslationKey TEAM_SIGMA = new TranslationKey("team.sigma");
    public static final TranslationKey JOIN_SIGMA = new TranslationKey("team.sigma.join");
    public static final TranslationKey JOIN_OMEGA = new TranslationKey("team.omega.join");
    public static final TranslationKey SPAWN_RESET = new TranslationKey("spawn.reset");
    public static final TranslationKey SELECT_CLASS_TITLE = new TranslationKey("spawn.class.title");
    public static final TranslationKey CLASS_DESCRIPTION = new TranslationKey("spawn.class.description");
    public static final TranslationKey CLASS_READY = new TranslationKey("spawn.class.ready");
    public static final TranslationKey CLASS_NEXT = new TranslationKey("spawn.class.next");
    public static final TranslationKey CHEMICAL_WEAPON = new TranslationKey("chemical_weapon");
    public static final TranslationKey WELCOME_REBEL = new TranslationKey("password.confirm.welcome.rebel");
    public static final TranslationKey WELCOME_OFFICER = new TranslationKey("password.confirm.welcome.officer");
    public static final TranslationKey WELCOME_LEADER = new TranslationKey("password.confirm.welcome.leader");
    public static final TranslationKey WELCOME_REPRESENTATIVE = new TranslationKey("password.confirm.welcome.representative");
    public static final TranslationKey WELCOME_REGULAR = new TranslationKey("password.confirm.welcome.regular");
    public static final TranslationKey ADS_TRAY = new TranslationKey("ads.tray");
    public static final TranslationKey ADS_DRAGON = new TranslationKey("ads.dragon");
    public static final TranslationKey EQUIP_WEAPONS_MESSAGE = new TranslationKey("equip_weapons");
    public static final TranslationKey CONTROLLER_OUT_OF_RANGE = new TranslationKey("controller.range");
    public static final TranslationKey CONTROLLER_B83 = new TranslationKey("controller.b83");
    public static final TranslationKey OPS_KNIFE = new TranslationKey("ops_knife");
    public static final TranslationKey REQUIRES = new TranslationKey("requires");
    public static final TranslationKey TACTICAL_NUKE_NAME = new TranslationKey("tactical_nuke.name");
    public static final TranslationKey PRESS_TO_SELECT_BOMB_TYPE = new TranslationKey("rhodes.select_bomb_type");

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
    private static final MutableComponent INVENTORY = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.GRAY))
        .append(INVENTORY_TRANSLATION.translate().withStyle(ChatFormatting.DARK_GREEN))
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

    public static MutableComponent inventory() {
        return INVENTORY.copy();
    }

    public static MutableComponent use(Component msg) {
        return orders().append(" ").append(USE_MESSAGE.translate()).append(" ").append(msg);
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

    public record TranslationKey(String key) {
        private static final Map<TranslationKey, Component> CACHE = new HashMap<>();
        public TranslationKey(String key) {
            this.key = RRIdentifiers.MODID + "." + key;
        }

        public TranslationKey getSubKey(String subKey) {
            return new TranslationKey(key + '.' + subKey);
        }

        public MutableComponent translate() {
            return CACHE.computeIfAbsent(this, k -> Component.translatable(k.key)).copy();
        }

        public MutableComponent translate(Object... args) {
            return Component.translatable(key, args);
        }
    }
}

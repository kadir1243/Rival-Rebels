package assets.rivalrebels.common.util;

import assets.rivalrebels.RRIdentifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * All Rival Rebels Mod Translation Keys are here <p>
 * Translations should be added with {@link assets.rivalrebels.datagen.LangGen DataGen}
 */
public class Translations {
    public static final ResourceLocation CREATIVE_TAB = RRIdentifiers.create("creative_tab_name");
    public static final ResourceLocation WARNING_TRANSLATION = RRIdentifiers.create("warning");
    public static final ResourceLocation BOMB_TIMER = RRIdentifiers.create("bomb.timer");
    public static final ResourceLocation UNBALANCED_BOMB = RRIdentifiers.create("bomb.unbalanced");
    public static final ResourceLocation BOMB_MEGATONS = RRIdentifiers.create("bomb.megatons");
    public static final ResourceLocation BOMB_ARMED = RRIdentifiers.create("bomb.armed");
    public static final ResourceLocation SHIFT_CLICK = RRIdentifiers.create("sneak.click");
    public static final ResourceLocation AMMUNITION_TRANSLATION = RRIdentifiers.create("ammunition");
    public static final ResourceLocation ORDERS_TRANSLATION = RRIdentifiers.create("orders");
    public static final ResourceLocation STATUS_TRANSLATION = RRIdentifiers.create("status");
    public static final ResourceLocation DEFUSE_TRANSLATION = RRIdentifiers.create("defuse");
    public static final ResourceLocation NUKE_TRANSLATION = RRIdentifiers.create("nuke_name");
    public static final ResourceLocation OVERHEAT_TRANSLATION = RRIdentifiers.create("overheat");
    public static final ResourceLocation USE_PLIERS_TO_BUILD_TRANSLATION = RRIdentifiers.create("use_pliers_to_build");
    public static final ResourceLocation USE_PLIERS_TO_OPEN_TRANSLATION = RRIdentifiers.create("use_pliers_to_open");
    public static final ResourceLocation LAPTOP_B2_SPIRIT = RRIdentifiers.create("laptop_b2_spirit");
    public static final ResourceLocation BUILDING_TOKAMAK = RRIdentifiers.create("building_tokamak");
    public static final ResourceLocation BUILDING = RRIdentifiers.create("building_crate");
    public static final ResourceLocation SPAWN_RESET_WARNING = RRIdentifiers.create("spawn_reset_warning");
    public static final ResourceLocation RHODES_IS_ARMED = RRIdentifiers.create("rhodes_is_armed");
    private static final MutableComponent WARNING = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.DARK_RED))
        .append(Component.translatable(WARNING_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.RED))
        .append(Component.literal("]").withStyle(ChatFormatting.DARK_RED));
    private static final MutableComponent ORDERS = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.GRAY))
        .append(Component.translatable(ORDERS_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.DARK_RED))
        .append(Component.literal("]").withStyle(ChatFormatting.GRAY));
    private static final MutableComponent STATUS = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.GRAY))
        .append(Component.translatable(STATUS_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.GOLD))
        .append(Component.literal("]").withStyle(ChatFormatting.GRAY));
    private static final MutableComponent DEFUSE = Component.empty()
        .append(Component.literal("[").withStyle(ChatFormatting.GRAY))
        .append(Component.translatable(DEFUSE_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.GOLD))
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
        return Component.translatable(AMMUNITION_TRANSLATION.toLanguageKey());
    }

    public static void sendWarningBombWillExplodeMessageToPlayers(Level level) {
        MutableComponent line1 = warning().append(" ").append(Component.translatable(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_1").withStyle(ChatFormatting.RED));
        MutableComponent line2 = warning().append(" ").append(Component.translatable(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_2").withStyle(ChatFormatting.RED));
        MutableComponent line3 = warning().append(" ").append(Component.translatable(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_3").withStyle(ChatFormatting.RED));
        for (Player player : level.players()) {
            player.displayClientMessage(line1, false);
            player.displayClientMessage(line2, false);
            player.displayClientMessage(line3, false);
        }
    }
}

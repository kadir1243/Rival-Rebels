package assets.rivalrebels;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RRIdentifiers {
    public static final ResourceLocation WARNING_TRANSLATION = create("warning");
    public static final ResourceLocation ORDERS_TRANSLATION = create("orders");
    public static final ResourceLocation STATUS_TRANSLATION = create("status");
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
    public static final ResourceLocation BOMB_TIMER = create("bomb.timer");
    public static final ResourceLocation UNBALANCED_BOMB = create("bomb.unbalanced");

    public static final ResourceLocation AMMUNITION_TRANSLATION = create("ammunition");
    public static final ResourceLocation guitrivalrebels = create("textures/gui/h.png");
    public static final ResourceLocation guitbutton = create("textures/gui/a.png");
    public static final ResourceLocation guitspawn = create("textures/gui/b.png");
    public static final ResourceLocation guitclass = create("textures/gui/c.png");
    public static final ResourceLocation guitrebel = create("textures/gui/d.png");
    public static final ResourceLocation guitnuker = create("textures/gui/e.png");
    public static final ResourceLocation guitintel = create("textures/gui/f.png");
    public static final ResourceLocation guithacker = create("textures/gui/g.png");
    public static final ResourceLocation guitnuke = create("textures/gui/j.png");
    public static final ResourceLocation guittsar = create("textures/gui/k.png");
    public static final ResourceLocation guitwarning0 = create("textures/gui/l.png");
    public static final ResourceLocation guitwarning1 = create("textures/gui/m.png");
    public static final ResourceLocation guitloader = create("textures/gui/i.png");
    public static final ResourceLocation guittokamak = create("textures/gui/n.png");
    public static final ResourceLocation guibinoculars = create("textures/gui/o.png");
    public static final ResourceLocation guibinocularsoverlay = create("textures/gui/p.png");
    public static final ResourceLocation guilaptopnuke = create("textures/gui/q.png");
    public static final ResourceLocation guitesla = create("textures/gui/r.png");
    public static final ResourceLocation guitray = create("textures/gui/s.png");
    public static final ResourceLocation guiflamethrower = create("textures/gui/u.png");
    public static final ResourceLocation guirhodesline = create("textures/gui/rhodes-gui_line.png");
    public static final ResourceLocation guirhodesout = create("textures/gui/rhodes-gui_out.png");
    public static final ResourceLocation guirhodeshelp = create("textures/gui/rhodes-gui_help.png");
    public static final ResourceLocation guicarpet = create("textures/gui/v.png");
    public static final ResourceLocation guitheoreticaltsar = create("textures/gui/w.png");
    public static final ResourceLocation guitantimatterbomb = create("textures/gui/x.png");
    public static final ResourceLocation guitachyonbomb = create("textures/gui/y.png");
    public static final ResourceLocation etdisk0 = create("entity/ba");
    public static final ResourceLocation etdisk1 = create("entity/bb");
    public static final ResourceLocation etdisk2 = create("entity/bc");
    public static final ResourceLocation etdisk3 = create("entity/bd");
    public static final ResourceLocation etrocket = create("entity/az");
    public static final ResourceLocation etfire = create("entity/ar");
    public static final ResourceLocation etflame = create("entity/as");
    public static final ResourceLocation etgasgrenade = create("entity/bf");
    public static final ResourceLocation etknife = create("entity/be");
    public static final ResourceLocation etloader = create("entity/ac");
    public static final ResourceLocation etomegaobj = create("entity/aa");
    public static final ResourceLocation etsigmaobj = create("entity/ab");
    public static final ResourceLocation etplasmacannon = create("entity/ay");
    public static final ResourceLocation ethydrod = create("entity/ao");
    public static final ResourceLocation etradrod = create("entity/an");
    public static final ResourceLocation etredrod = create("entity/ap");
    public static final ResourceLocation ettroll = create("entity/am");
    public static final ResourceLocation etreactor = create("entity/ad");
    public static final ResourceLocation etlaptop = create("entity/ah");
    public static final ResourceLocation etubuntu = create("entity/aj");
    public static final ResourceLocation etscreen = create("entity/ai");
    public static final ResourceLocation ettsarshell = create("entity/af");
    public static final ResourceLocation ettsarfins = create("entity/ag");
    public static final ResourceLocation ettsarflame = create("entity/al");
    public static final ResourceLocation etnuke = create("entity/ae");
    public static final ResourceLocation etradiation = create("entity/ak");
    public static final ResourceLocation eteinstenbarrel = create("entity/av");
    public static final ResourceLocation eteinstenback = create("entity/aw");
    public static final ResourceLocation eteinstenhandle = create("entity/ax");
    public static final ResourceLocation etblood = create("entity/at");
    public static final ResourceLocation etgoo = create("entity/au");
    public static final ResourceLocation etemptyrod = create("entity/aq");
    public static final ResourceLocation etrocketlauncherbody = create("entity/bh");
    public static final ResourceLocation etrocketlauncherhandle = create("entity/bg");
    public static final ResourceLocation etrocketlaunchertube = create("entity/bi");
    public static final ResourceLocation etbinoculars = create("entity/bj");
    public static final ResourceLocation etelectrode = create("entity/bk");
    public static final ResourceLocation etb83 = create("entity/bl");
    public static final ResourceLocation etb2spirit = create("entity/bm");
    public static final ResourceLocation etrust = create("entity/bn");
    public static final ResourceLocation etreciever = create("entity/bo");
    public static final ResourceLocation ettesla = create("entity/bp");
    public static final ResourceLocation etbattery = create("entity/bq");
    public static final ResourceLocation etflamethrower = create("entity/bt");
    public static final ResourceLocation ettube = create("entity/br");
    public static final ResourceLocation etadsdragon = create("entity/bs");
    public static final ResourceLocation etflamecone = create("entity/bu");
    public static final ResourceLocation etflameball = create("entity/bv");
    public static final ResourceLocation etflamebluered = create("entity/bx");
    public static final ResourceLocation etflameblue = create("entity/bw");
    public static final ResourceLocation ethack202 = create("entity/by");
    public static final ResourceLocation etseek202 = create("entity/bz");
    public static final ResourceLocation etrocketseek202 = create("entity/ca");
    public static final ResourceLocation etrocketseekhandle202 = create("entity/cb");
    public static final ResourceLocation etrocketseektube202 = create("entity/cc");
    public static final ResourceLocation ettheoreticaltsarshell1 = create("entity/cd");
    public static final ResourceLocation ettheoreticaltsarshell2 = create("entity/ce");
    public static final ResourceLocation etblacktsar = create("entity/cf");
    public static final ResourceLocation etwacknuke = create("entity/cg");
    public static final ResourceLocation ettupolev = create("entity/tupolev");
    public static final ResourceLocation etbooster = create("entity/booster");
    public static final ResourceLocation etflameballgreen = create("entity/ch");
    public static final ResourceLocation etantimatterbomb = create("entity/ci");
    public static final ResourceLocation etantimatterblast = create("entity/cj");
    public static final ResourceLocation ettachyonbomb = create("entity/ck");
    public static final ResourceLocation btcrate = create("block/ah");
    public static final ResourceLocation btnuketop = create("block/ay");
    public static final ResourceLocation btnukebottom = create("block/ax");
    public static final ResourceLocation btsteel = create("block/bx");
    public static final ResourceLocation btsplash1 = create("block/br");
    public static final ResourceLocation btsplash2 = create("block/bs");
    public static final ResourceLocation btsplash3 = create("block/bt");
    public static final ResourceLocation btsplash4 = create("block/bu");
    public static final ResourceLocation btsplash5 = create("block/bv");
    public static final ResourceLocation btsplash6 = create("block/bw");
    public static final ResourceLocation ittaskb83 = create("items/bc");
    public static ResourceLocation banner;

    public static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath(RivalRebels.MODID, path);
    }

    public static MutableComponent warning() {
        return WARNING.copy();
    }

    public static MutableComponent orders() {
        return ORDERS.copy();
    }

    public static MutableComponent status() {
        return STATUS.copy();
    }

    public static MutableComponent ammunition() {
        return Component.translatable(AMMUNITION_TRANSLATION.toLanguageKey());
    }

    public static void sendWarningBombWillExplodeMessageToPlayers(Level level) {
        MutableComponent line1 = warning().append(" ").append(Component.translatable(RivalRebels.MODID + ".warning_bomb_will_explode_line_1").withStyle(ChatFormatting.RED));
        MutableComponent line2 = warning().append(" ").append(Component.translatable(RivalRebels.MODID + ".warning_bomb_will_explode_line_2").withStyle(ChatFormatting.RED));
        MutableComponent line3 = warning().append(" ").append(Component.translatable(RivalRebels.MODID + ".warning_bomb_will_explode_line_3").withStyle(ChatFormatting.RED));
        for (Player player : level.players()) {
            player.displayClientMessage(line1, false);
            player.displayClientMessage(line2, false);
            player.displayClientMessage(line3, false);
        }
    }
}

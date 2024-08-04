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
    public static final ResourceLocation NUKE_TRANSLATION = create("nuke_name");
    public static final String MODID = "rivalrebels";
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
    public static final ResourceLocation BOMB_MEGATONS = create("bomb.megatons");
    public static final ResourceLocation BOMB_ARMED = create("bomb.armed");
    public static final ResourceLocation SHIFT_CLICK = create("sneak.click");

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
    public static final ResourceLocation etdisk0 = create("textures/entity/ba.png");
    public static final ResourceLocation etdisk1 = create("textures/entity/bb.png");
    public static final ResourceLocation etdisk2 = create("textures/entity/bc.png");
    public static final ResourceLocation etdisk3 = create("textures/entity/bd.png");
    public static final ResourceLocation etrocket = create("textures/entity/az.png");
    public static final ResourceLocation etfire = create("textures/entity/ar.png");
    public static final ResourceLocation etflame = create("textures/entity/as.png");
    public static final ResourceLocation etgasgrenade = create("textures/entity/bf.png");
    public static final ResourceLocation etknife = create("textures/entity/be.png");
    public static final ResourceLocation etloader = create("textures/entity/ac.png");
    public static final ResourceLocation etomegaobj = create("textures/entity/aa.png");
    public static final ResourceLocation etsigmaobj = create("textures/entity/ab.png");
    public static final ResourceLocation etplasmacannon = create("textures/entity/ay.png");
    public static final ResourceLocation ethydrod = create("textures/entity/ao.png");
    public static final ResourceLocation etradrod = create("textures/entity/an.png");
    public static final ResourceLocation etredrod = create("textures/entity/ap.png");
    public static final ResourceLocation ettroll = create("textures/entity/am.png");
    public static final ResourceLocation etreactor = create("textures/entity/ad.png");
    public static final ResourceLocation etlaptop = create("textures/entity/ah.png");
    public static final ResourceLocation etubuntu = create("textures/entity/aj.png");
    public static final ResourceLocation etscreen = create("textures/entity/ai.png");
    public static final ResourceLocation ettsarshell = create("textures/entity/af.png");
    public static final ResourceLocation ettsarfins = create("textures/entity/ag.png");
    public static final ResourceLocation ettsarflame = create("textures/entity/al.png");
    public static final ResourceLocation etnuke = create("textures/entity/ae.png");
    public static final ResourceLocation etradiation = create("textures/entity/ak.png");
    public static final ResourceLocation eteinstenbarrel = create("textures/entity/av.png");
    public static final ResourceLocation eteinstenback = create("textures/entity/aw.png");
    public static final ResourceLocation eteinstenhandle = create("textures/entity/ax.png");
    public static final ResourceLocation etblood = create("textures/entity/at.png");
    public static final ResourceLocation etgoo = create("textures/entity/au.png");
    public static final ResourceLocation etemptyrod = create("textures/entity/aq.png");
    public static final ResourceLocation etrocketlauncherbody = create("textures/entity/bh.png");
    public static final ResourceLocation etrocketlauncherhandle = create("textures/entity/bg.png");
    public static final ResourceLocation etrocketlaunchertube = create("textures/entity/bi.png");
    public static final ResourceLocation etbinoculars = create("textures/entity/bj.png");
    public static final ResourceLocation etelectrode = create("textures/entity/bk.png");
    public static final ResourceLocation etb83 = create("textures/entity/bl.png");
    public static final ResourceLocation etb2spirit = create("textures/entity/bm.png");
    public static final ResourceLocation etrust = create("textures/entity/bn.png");
    public static final ResourceLocation etreciever = create("textures/entity/bo.png");
    public static final ResourceLocation ettesla = create("textures/entity/bp.png");
    public static final ResourceLocation etbattery = create("textures/entity/bq.png");
    public static final ResourceLocation etflamethrower = create("textures/entity/bt.png");
    public static final ResourceLocation ettube = create("textures/entity/br.png");
    public static final ResourceLocation etadsdragon = create("textures/entity/bs.png");
    public static final ResourceLocation etflamecone = create("textures/entity/bu.png");
    public static final ResourceLocation etflameball = create("textures/entity/bv.png");
    public static final ResourceLocation etflamebluered = create("textures/entity/bx.png");
    public static final ResourceLocation etflameblue = create("textures/entity/bw.png");
    public static final ResourceLocation ethack202 = create("textures/entity/by.png");
    public static final ResourceLocation etseek202 = create("textures/entity/bz.png");
    public static final ResourceLocation etrocketseek202 = create("textures/entity/ca.png");
    public static final ResourceLocation etrocketseekhandle202 = create("textures/entity/cb.png");
    public static final ResourceLocation etrocketseektube202 = create("textures/entity/cc.png");
    public static final ResourceLocation ettheoreticaltsarshell1 = create("textures/entity/cd.png");
    public static final ResourceLocation ettheoreticaltsarshell2 = create("textures/entity/ce.png");
    public static final ResourceLocation etblacktsar = create("textures/entity/cf.png");
    public static final ResourceLocation etwacknuke = create("textures/entity/cg.png");
    public static final ResourceLocation ettupolev = create("textures/entity/tupolev.png");
    public static final ResourceLocation etbooster = create("textures/entity/booster.png");
    public static final ResourceLocation etflameballgreen = create("textures/entity/ch.png");
    public static final ResourceLocation etantimatterbomb = create("textures/entity/ci.png");
    public static final ResourceLocation etantimatterblast = create("textures/entity/cj.png");
    public static final ResourceLocation ettachyonbomb = create("textures/entity/ck.png");
    public static final ResourceLocation btcrate = create("textures/block/ah.png");
    public static final ResourceLocation btnuketop = create("textures/block/ay.png");
    public static final ResourceLocation btnukebottom = create("textures/block/ax.png");
    public static final ResourceLocation btsteel = create("textures/block/bx.png");
    public static final ResourceLocation btsplash1 = create("textures/block/br.png");
    public static final ResourceLocation btsplash2 = create("textures/block/bs.png");
    public static final ResourceLocation btsplash3 = create("textures/block/bt.png");
    public static final ResourceLocation btsplash4 = create("textures/block/bu.png");
    public static final ResourceLocation btsplash5 = create("textures/block/bv.png");
    public static final ResourceLocation btsplash6 = create("textures/block/bw.png");
    public static final ResourceLocation ittaskb83 = create("textures/items/bc.png");
    public static ResourceLocation banner;

    public static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
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
        MutableComponent line1 = warning().append(" ").append(Component.translatable(MODID + ".warning_bomb_will_explode_line_1").withStyle(ChatFormatting.RED));
        MutableComponent line2 = warning().append(" ").append(Component.translatable(MODID + ".warning_bomb_will_explode_line_2").withStyle(ChatFormatting.RED));
        MutableComponent line3 = warning().append(" ").append(Component.translatable(MODID + ".warning_bomb_will_explode_line_3").withStyle(ChatFormatting.RED));
        for (Player player : level.players()) {
            player.displayClientMessage(line1, false);
            player.displayClientMessage(line2, false);
            player.displayClientMessage(line3, false);
        }
    }
}

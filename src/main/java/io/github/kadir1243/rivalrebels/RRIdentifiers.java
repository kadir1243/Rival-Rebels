package io.github.kadir1243.rivalrebels;

import net.minecraft.resources.Identifier;

public class RRIdentifiers {
    public static final String MODID = "rivalrebels";

    public static final Identifier etdisk0 = create("textures/entity/ba.png");
    public static final Identifier etdisk1 = create("textures/entity/bb.png");
    public static final Identifier etdisk2 = create("textures/entity/bc.png");
    public static final Identifier etdisk3 = create("textures/entity/bd.png");
    public static final Identifier etrocket = create("textures/entity/az.png");
    public static final Identifier etfire = create("textures/entity/ar.png");
    public static final Identifier etflame = create("textures/entity/as.png");
    public static final Identifier etgasgrenade = create("textures/entity/bf.png");
    public static final Identifier etknife = create("textures/entity/be.png");
    public static final Identifier etloader = create("textures/entity/ac.png");
    public static final Identifier etomegaobj = create("textures/entity/aa.png");
    public static final Identifier etsigmaobj = create("textures/entity/ab.png");
    public static final Identifier etplasmacannon = create("textures/entity/ay.png");
    public static final Identifier ethydrod = create("textures/entity/ao.png");
    public static final Identifier etradrod = create("textures/entity/an.png");
    public static final Identifier etredrod = create("textures/entity/ap.png");
    public static final Identifier ettroll = create("textures/entity/am.png");
    public static final Identifier etreactor = create("textures/entity/ad.png");
    public static final Identifier etlaptop = create("textures/entity/ah.png");
    public static final Identifier etubuntu = create("textures/entity/aj.png");
    public static final Identifier etscreen = create("textures/entity/ai.png");
    public static final Identifier ettsarshell = create("textures/entity/af.png");
    public static final Identifier ettsarfins = create("textures/entity/ag.png");
    public static final Identifier ettsarflame = create("textures/entity/al.png");
    public static final Identifier etnuke = create("textures/entity/ae.png");
    public static final Identifier etradiation = create("textures/entity/ak.png");
    public static final Identifier eteinstenbarrel = create("textures/entity/av.png");
    public static final Identifier eteinstenback = create("textures/entity/aw.png");
    public static final Identifier eteinstenhandle = create("textures/entity/ax.png");
    public static final Identifier etblood = create("textures/entity/at.png");
    public static final Identifier etgoo = create("textures/entity/au.png");
    public static final Identifier etemptyrod = create("textures/entity/aq.png");
    public static final Identifier etrocketlauncherbody = create("textures/entity/bh.png");
    public static final Identifier etrocketlauncherhandle = create("textures/entity/bg.png");
    public static final Identifier etrocketlaunchertube = create("textures/entity/bi.png");
    public static final Identifier etbinoculars = create("textures/entity/bj.png");
    public static final Identifier etelectrode = create("textures/entity/bk.png");
    public static final Identifier etb83 = create("textures/entity/bl.png");
    public static final Identifier etb2spirit = create("textures/entity/bm.png");
    public static final Identifier etrust = create("textures/entity/bn.png");
    public static final Identifier etreciever = create("textures/entity/bo.png");
    public static final Identifier ettesla = create("textures/entity/bp.png");
    public static final Identifier etbattery = create("textures/entity/bq.png");
    public static final Identifier etflamethrower = create("textures/entity/bt.png");
    public static final Identifier ettube = create("textures/entity/br.png");
    public static final Identifier etadsdragon = create("textures/entity/bs.png");
    public static final Identifier etflamecone = create("textures/entity/bu.png");
    public static final Identifier etflameball = create("textures/entity/bv.png");
    public static final Identifier etflamebluered = create("textures/entity/bx.png");
    public static final Identifier etflameblue = create("textures/entity/bw.png");
    public static final Identifier ethack202 = create("textures/entity/by.png");
    public static final Identifier etseek202 = create("textures/entity/bz.png");
    public static final Identifier etrocketseek202 = create("textures/entity/ca.png");
    public static final Identifier etrocketseekhandle202 = create("textures/entity/cb.png");
    public static final Identifier etrocketseektube202 = create("textures/entity/cc.png");
    public static final Identifier ettheoreticaltsarshell1 = create("textures/entity/cd.png");
    public static final Identifier ettheoreticaltsarshell2 = create("textures/entity/ce.png");
    public static final Identifier etblacktsar = create("textures/entity/cf.png");
    public static final Identifier etwacknuke = create("textures/entity/cg.png");
    public static final Identifier ettupolev = create("textures/entity/tupolev.png");
    public static final Identifier etbooster = create("textures/entity/booster.png");
    public static final Identifier etflameballgreen = create("textures/entity/ch.png");
    public static final Identifier etantimatterbomb = create("textures/entity/ci.png");
    public static final Identifier etantimatterblast = create("textures/entity/cj.png");
    public static final Identifier ettachyonbomb = create("textures/entity/ck.png");
    public static final Identifier btcrate = create("textures/block/ah.png");
    public static final Identifier btnuketop = create("textures/block/ay.png");
    public static final Identifier btnukebottom = create("textures/block/ax.png");
    public static final Identifier btsteel = create("textures/block/bx.png");
    public static final Identifier btsplash1 = create("textures/block/br.png");
    public static final Identifier btsplash2 = create("textures/block/bs.png");
    public static final Identifier btsplash3 = create("textures/block/bt.png");
    public static final Identifier btsplash4 = create("textures/block/bu.png");
    public static final Identifier btsplash5 = create("textures/block/bv.png");
    public static final Identifier btsplash6 = create("textures/block/bw.png");
    public static final Identifier ittaskb83 = create("textures/items/bc.png");
    public static Identifier banner;

    public static Identifier create(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static Identifier getModelLocation(String model) {
        return create(model).withPrefix("models/").withSuffix(".obj");
    }
}

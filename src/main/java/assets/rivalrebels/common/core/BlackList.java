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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.HashSet;
import java.util.Set;

import static assets.rivalrebels.RivalRebels.getBlocks;

public class BlackList {
    public static Set<Block> tesla = new HashSet<>();

    static {
        tesla.add(RRBlocks.omegaobj);
        tesla.add(RRBlocks.sigmaobj);
        tesla.add(RRBlocks.fshield);
        tesla.add(Blocks.BEDROCK);
        tesla.add(Blocks.WATER);
        tesla.add(Blocks.LAVA);
        tesla.addAll(getBlocks(Tags.Blocks.GLASS));
        tesla.add(Blocks.OBSIDIAN);
        tesla.addAll(getBlocks(Tags.Blocks.GLASS_PANES));
        tesla.addAll(getBlocks(Tags.Blocks.STAINED_GLASS));
        tesla.addAll(getBlocks(Tags.Blocks.STAINED_GLASS_PANES));
        tesla.add(RRBlocks.nuclearBomb);
        tesla.add(RRBlocks.tsarbombablock);
        tesla.add(RRBlocks.loader);
        tesla.add(RRBlocks.reactor);
        tesla.add(RRBlocks.forcefieldnode);
        tesla.add(RRBlocks.conduit);
        tesla.add(RRBlocks.forcefield);
        tesla.add(RRBlocks.meltdown);
        tesla.add(RRBlocks.ffreciever);
    }
    public static Block[] explosion =
        {
            RRBlocks.fshield,
            RRBlocks.nuclearBomb,
            RRBlocks.tsarbombablock,
            RRBlocks.omegaobj,
            RRBlocks.sigmaobj,
            RRBlocks.forcefieldnode,
            RRBlocks.reactor,
            RRBlocks.loader,
            RRBlocks.meltdown,
            RRBlocks.forcefield,
            RRBlocks.ffreciever,
        };
    public static Block[] plasmaExplosion =
        {
            RRBlocks.omegaobj,
            RRBlocks.sigmaobj,
            RRBlocks.tsarbombablock,
            RRBlocks.nuclearBomb,
            RRBlocks.reactor,
            RRBlocks.loader,
            RRBlocks.controller,
            RRBlocks.meltdown,
            RRBlocks.forcefield,
            RRBlocks.ffreciever,
        };
    public static Block[] autobuild =
        {
            RRBlocks.fshield,
            RRBlocks.nuclearBomb,
            RRBlocks.tsarbombablock,
            RRBlocks.omegaobj,
            RRBlocks.sigmaobj,
            RRBlocks.forcefieldnode,
            RRBlocks.reactive,
            RRBlocks.conduit,
            RRBlocks.reactor,
            RRBlocks.loader,
            RRBlocks.meltdown,
            RRBlocks.forcefield,
            RRBlocks.ffreciever,
            Blocks.BEDROCK,
        };

    public static boolean tesla(Block block) {
        for (Block value : tesla) {
            if (value == block) return true;
        }
        return false;
    }

    public static boolean explosion(Block block) {
        for (Block value : explosion) {
            if (value == block) return true;
        }
        return false;
    }

    public static boolean plasmaExplosion(Block block) {
        for (Block value : plasmaExplosion) {
            if (value == block) return true;
        }
        return false;
    }

    public static boolean autobuild(Block block) {
        for (Block value : autobuild) {
            if (value == block) return true;
        }
        return false;
    }
}

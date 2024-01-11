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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class BlackList {
    public static Block[] tesla =
        {
            RivalRebels.omegaobj,
            RivalRebels.sigmaobj,
            RivalRebels.fshield,
            Blocks.BEDROCK,
            Blocks.WATER,
            Blocks.FLOWING_WATER,
            Blocks.LAVA,
            Blocks.FLOWING_LAVA,
            Blocks.GLASS,
            Blocks.OBSIDIAN,
            Blocks.GLASS_PANE,
            Blocks.STAINED_GLASS,
            Blocks.STAINED_GLASS_PANE,
            RivalRebels.nuclearBomb,
            RivalRebels.tsarbombablock,
            RivalRebels.loader,
            RivalRebels.reactor,
            RivalRebels.forcefieldnode,
            RivalRebels.conduit,
            RivalRebels.forcefield,
            RivalRebels.meltdown,
            RivalRebels.ffreciever,
        };
    public static Block[] explosion =
        {
            RivalRebels.fshield,
            RivalRebels.nuclearBomb,
            RivalRebels.tsarbombablock,
            RivalRebels.omegaobj,
            RivalRebels.sigmaobj,
            RivalRebels.forcefieldnode,
            RivalRebels.reactor,
            RivalRebels.loader,
            RivalRebels.meltdown,
            RivalRebels.forcefield,
            RivalRebels.ffreciever,
        };
    public static Block[] plasmaExplosion =
        {
            RivalRebels.omegaobj,
            RivalRebels.sigmaobj,
            RivalRebels.tsarbombablock,
            RivalRebels.nuclearBomb,
            RivalRebels.reactor,
            RivalRebels.loader,
            RivalRebels.controller,
            RivalRebels.meltdown,
            RivalRebels.forcefield,
            RivalRebels.ffreciever,
        };
    public static Block[] autobuild =
        {
            RivalRebels.fshield,
            RivalRebels.nuclearBomb,
            RivalRebels.tsarbombablock,
            RivalRebels.omegaobj,
            RivalRebels.sigmaobj,
            RivalRebels.forcefieldnode,
            RivalRebels.reactive,
            RivalRebels.conduit,
            RivalRebels.reactor,
            RivalRebels.loader,
            RivalRebels.meltdown,
            RivalRebels.forcefield,
            RivalRebels.ffreciever,
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

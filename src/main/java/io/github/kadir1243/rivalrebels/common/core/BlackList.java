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
package io.github.kadir1243.rivalrebels.common.core;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.util.ModBlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class BlackList {
    public static final Set<Predicate<BlockState>> explosion = createSet(
        RRBlocks.fshield.get(),
        RRBlocks.nuclearBomb.get(),
        RRBlocks.tsarbombablock.get(),
        RRBlocks.omegaobj.get(),
        RRBlocks.sigmaobj.get(),
        RRBlocks.forcefieldnode.get(),
        RRBlocks.reactor.get(),
        RRBlocks.loader.get(),
        RRBlocks.meltdown.get(),
        RRBlocks.forcefield.get(),
        RRBlocks.ffreciever.get()
    );
    private static final Set<Predicate<BlockState>> tesla = new HashSet<>(Set.of(
        of(RRBlocks.omegaobj.get()),
        of(RRBlocks.sigmaobj.get()),
        of(RRBlocks.fshield.get()),
        of(Blocks.BEDROCK),
        of(Blocks.WATER),
        of(Blocks.LAVA),
        of(ModBlockTags.GLASS_BLOCKS),
        of(Blocks.OBSIDIAN),
        of(ModBlockTags.GLASS_PANES),
        of(RRBlocks.nuclearBomb.get()),
        of(RRBlocks.tsarbombablock.get()),
        of(RRBlocks.loader.get()),
        of(RRBlocks.reactor.get()),
        of(RRBlocks.forcefieldnode.get()),
        of(RRBlocks.conduit.get()),
        of(RRBlocks.forcefield.get()),
        of(RRBlocks.meltdown.get()),
        of(RRBlocks.ffreciever.get())
    ));
    private static final Set<Predicate<BlockState>> plasmaExplosion =
        createSet(
            RRBlocks.omegaobj.get(),
            RRBlocks.sigmaobj.get(),
            RRBlocks.tsarbombablock.get(),
            RRBlocks.nuclearBomb.get(),
            RRBlocks.reactor.get(),
            RRBlocks.loader.get(),
            RRBlocks.controller.get(),
            RRBlocks.meltdown.get(),
            RRBlocks.forcefield.get(),
            RRBlocks.ffreciever.get()
        );
    private static final Set<Predicate<BlockState>> autobuild =
        createSet(
            RRBlocks.fshield.get(),
            RRBlocks.nuclearBomb.get(),
            RRBlocks.tsarbombablock.get(),
            RRBlocks.omegaobj.get(),
            RRBlocks.sigmaobj.get(),
            RRBlocks.forcefieldnode.get(),
            RRBlocks.reactive.get(),
            RRBlocks.conduit.get(),
            RRBlocks.reactor.get(),
            RRBlocks.loader.get(),
            RRBlocks.meltdown.get(),
            RRBlocks.forcefield.get(),
            RRBlocks.ffreciever.get(),
            Blocks.BEDROCK
        );

    public static Predicate<BlockState> of(Block block) {
        return state -> state.is(block);
    }

    public static Predicate<BlockState> of(TagKey<Block> tag) {
        return state -> state.is(tag);
    }

    public static Set<Predicate<BlockState>> createSet(Block... blocks) {
        return new HashSet<>(Arrays.stream(blocks).map(BlackList::of).toList());
    }

    public static boolean tesla(BlockState state) {
        return tesla.stream().anyMatch(value -> value.test(state));
    }

    public static boolean explosion(BlockState state) {
        return explosion.stream().anyMatch(value -> value.test(state));
    }

    public static boolean plasmaExplosion(BlockState state) {
        return plasmaExplosion.stream().anyMatch(value -> value.test(state));
    }

    public static boolean autobuild(BlockState state) {
        return autobuild.stream().anyMatch(value -> value.test(state));
    }
}

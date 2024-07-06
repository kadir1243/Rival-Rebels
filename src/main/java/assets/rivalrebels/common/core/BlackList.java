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

import assets.rivalrebels.common.block.RRBlocks;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class BlackList {
    private static final Set<Predicate<BlockState>> tesla = new HashSet<>(Set.of(
        of(RRBlocks.omegaobj),
        of(RRBlocks.sigmaobj),
        of(RRBlocks.fshield),
        of(Blocks.BEDROCK),
        of(Blocks.WATER),
        of(Blocks.LAVA),
        of(ConventionalBlockTags.GLASS_BLOCKS),
        of(Blocks.OBSIDIAN),
        of(ConventionalBlockTags.GLASS_PANES),
        of(RRBlocks.nuclearBomb),
        of(RRBlocks.tsarbombablock),
        of(RRBlocks.loader),
        of(RRBlocks.reactor),
        of(RRBlocks.forcefieldnode),
        of(RRBlocks.conduit),
        of(RRBlocks.forcefield),
        of(RRBlocks.meltdown),
        of(RRBlocks.ffreciever)
    ));

    private static Predicate<BlockState> of(Block block) {
        return state -> state.is(block);
    }

    private static Predicate<BlockState> of(TagKey<Block> tag) {
        return state -> state.is(tag);
    }

    public static final Set<Predicate<BlockState>> explosion = createSet(
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
            RRBlocks.ffreciever
        );
    private static final Set<Predicate<BlockState>> plasmaExplosion =
        createSet(
            RRBlocks.omegaobj,
            RRBlocks.sigmaobj,
            RRBlocks.tsarbombablock,
            RRBlocks.nuclearBomb,
            RRBlocks.reactor,
            RRBlocks.loader,
            RRBlocks.controller,
            RRBlocks.meltdown,
            RRBlocks.forcefield,
            RRBlocks.ffreciever
        );
    private static final Set<Predicate<BlockState>> autobuild =
        createSet(
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
            Blocks.BEDROCK
        );

    private static Set<Predicate<BlockState>> createSet(Block... blocks) {
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

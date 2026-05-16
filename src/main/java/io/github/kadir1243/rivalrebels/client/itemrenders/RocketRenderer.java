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
package io.github.kadir1243.rivalrebels.client.itemrenders;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelRocket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3fc;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public record RocketRenderer(Identifier texture) implements NoDataSpecialModelRenderer {
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        ModelRocket.render(poseStack, submitNodeCollector, texture, true, lightCoords, overlayCoords);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }

    public record Unbaked(Identifier texture) implements NoDataSpecialModelRenderer.Unbaked {
        public static final Identifier ID = RRIdentifiers.create("rocket_renderer");
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i
            .group(Identifier.CODEC.fieldOf("texture").forGetter(Unbaked::texture))
            .apply(i, Unbaked::new));
        @Override
        public SpecialModelRenderer<Void> bake(BakingContext context) {
            return new RocketRenderer(texture);
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }

}


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
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelLaptop;
import io.github.kadir1243.rivalrebels.client.model.ModelReactor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3fc;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ReactorRenderer implements NoDataSpecialModelRenderer {
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.1875F, 0.5F);
            ModelLaptop.renderModel(submitNodeCollector, poseStack, 0, true, lightCoords, overlayCoords);
            ModelLaptop.renderScreen(submitNodeCollector, RRIdentifiers.etscreen, poseStack, 0, lightCoords, overlayCoords);
            poseStack.popPose();
        }
        {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(RRIdentifiers.etreactor), (pose, consumer) -> {
                ModelReactor.renderModel(pose, consumer, lightCoords, overlayCoords);
            });
            poseStack.popPose();
        }
	}

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<Void> {
        public static final Identifier ID = RRIdentifiers.create("reactor_renderer");
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);
        @Override
        public SpecialModelRenderer<Void> bake(BakingContext context) {
            return new ReactorRenderer();
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }
}


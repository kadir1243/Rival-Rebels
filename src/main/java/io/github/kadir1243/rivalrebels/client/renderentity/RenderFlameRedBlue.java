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
package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.common.entity.EntityFlameBall1;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class RenderFlameRedBlue extends FlameBallRenderer<EntityFlameBall1> {
    public RenderFlameRedBlue(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public float getSize(EntityFlameBall1 entity) {
        float size = 0.055F * entity.tickCount;
        size *= size;
        // if (size >= 0.5) size = 0.5F;
        size += 0.05F;
        return size;
    }
}

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

import io.github.kadir1243.rivalrebels.common.entity.EntityFlameBallGreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class RenderFlameBallGreen extends FlameBallRenderer<EntityFlameBallGreen> {
    public RenderFlameBallGreen(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public float getSize(EntityFlameBallGreen entity) {
        float size = 0.05F * entity.tickCount;
        //size *= size;
        return size;
    }
}

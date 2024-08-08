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
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.common.entity.EntityFlameBall;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@Environment(EnvType.CLIENT)
public class RenderFlame extends FlameBallRenderer<EntityFlameBall> {
    public RenderFlame(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public float getSize(EntityFlameBall entity) {
        float size = 0.05F * entity.tickCount;
        size *= size;
        return size;
    }
}

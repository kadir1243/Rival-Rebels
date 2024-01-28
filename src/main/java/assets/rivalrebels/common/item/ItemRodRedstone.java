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
package assets.rivalrebels.common.item;

import assets.rivalrebels.client.itemrenders.RedstoneRodRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ItemRodRedstone extends ItemRod {
	public ItemRodRedstone() {
		super(new Settings().maxDamage(256).group(RRItems.rralltab));
		power = 300000;
	}

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new RedstoneRodRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
    }
}

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
package assets.rivalrebels.client.model;

import net.minecraft.client.render.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModelTrollFlag
{
	public void renderModel(VertexConsumer buffer, int metadata) {
        double var18 = 0.05;

		if (metadata == 5) {
			buffer.vertex(var18, 1, 1).texture(0, 0).next();
			buffer.vertex(var18, 0, 1).texture(0, 1).next();
			buffer.vertex(var18, 0, 0).texture(1, 1).next();
			buffer.vertex(var18, 1, 0).texture(1, 0).next();
		} else if (metadata == 4) {
			buffer.vertex(1 - var18, 0, 1).texture(1, 1).next();
			buffer.vertex(1 - var18, 1, 1).texture(1, 0).next();
			buffer.vertex(1 - var18, 1, 0).texture(0, 0).next();
			buffer.vertex(1 - var18, 0, 0).texture(0, 1).next();
		} else if (metadata == 3) {
			buffer.vertex(1, 0, var18).texture(1, 1).next();
			buffer.vertex(1, 1, var18).texture(1, 0).next();
			buffer.vertex(0, 1, var18).texture(0, 0).next();
			buffer.vertex(0, 0, var18).texture(0, 1).next();
		} else if (metadata == 2) {
			buffer.vertex(1, 1, 1 - var18).texture(0, 0).next();
			buffer.vertex(1, 0, 1 - var18).texture(0, 1).next();
			buffer.vertex(0, 0, 1 - var18).texture(1, 1).next();
			buffer.vertex(0, 1, 1 - var18).texture(1, 0).next();
		}
	}
}

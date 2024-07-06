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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModelTrollFlag
{
	public static void renderModel(PoseStack pose, VertexConsumer buffer, int metadata) {
        float var18 = 0.05F;

		if (metadata == 5) {
			buffer.addVertex(pose.last(), var18, 1, 1).setUv(0, 0);
			buffer.addVertex(pose.last(), var18, 0, 1).setUv(0, 1);
			buffer.addVertex(pose.last(), var18, 0, 0).setUv(1, 1);
			buffer.addVertex(pose.last(), var18, 1, 0).setUv(1, 0);
		} else if (metadata == 4) {
			buffer.addVertex(pose.last(), 1 - var18, 0, 1).setUv(1, 1);
			buffer.addVertex(pose.last(), 1 - var18, 1, 1).setUv(1, 0);
			buffer.addVertex(pose.last(), 1 - var18, 1, 0).setUv(0, 0);
			buffer.addVertex(pose.last(), 1 - var18, 0, 0).setUv(0, 1);
		} else if (metadata == 3) {
			buffer.addVertex(pose.last(), 1, 0, var18).setUv(1, 1);
			buffer.addVertex(pose.last(), 1, 1, var18).setUv(1, 0);
			buffer.addVertex(pose.last(), 0, 1, var18).setUv(0, 0);
			buffer.addVertex(pose.last(), 0, 0, var18).setUv(0, 1);
		} else if (metadata == 2) {
			buffer.addVertex(pose.last(), 1, 1, 1 - var18).setUv(0, 0);
			buffer.addVertex(pose.last(), 1, 0, 1 - var18).setUv(0, 1);
			buffer.addVertex(pose.last(), 0, 0, 1 - var18).setUv(1, 1);
			buffer.addVertex(pose.last(), 0, 1, 1 - var18).setUv(1, 0);
		}
	}
}

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
package assets.rivalrebels.client.objfileloader;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class Vertice {
	private final Vec3 vertex;
	private final Vec3 normal;
	private final Vec2 textureCoords;

    public Vertice(Vec3 vertex, Vec3 normal, Vec2 textureCoords) {
		this.vertex = vertex;
		this.normal = normal;
		this.textureCoords = textureCoords;
	}

	public Vertice normalize() {
        return new Vertice(vertex, normal.normalize(), textureCoords);
	}

    public void render(PoseStack pose, VertexConsumer buffer, int color, int light, int overlay) {
        buffer
            .addVertex(pose.last(), vertex.toVector3f())
            .setColor(color)
            .setUv(textureCoords.x, textureCoords.y)
            .setOverlay(overlay)
            .setLight(light)
            .setNormal(pose.last(), (float) normal.x, (float) normal.y, (float) normal.z);
    }

    public static Vertice average(Vertice v1, Vertice v2) {
        Vec3 vertex = v1.vertex.add(v2.vertex).scale(1/2F);
        Vec3 normal = v1.normal.add(v2.normal).scale(1/2F);
        Vec2 textureCoords = v1.textureCoords.add(v2.textureCoords).scale(1/2F);
        return new Vertice(vertex, normal, textureCoords);
	}

	public Vertice scale(Vec3 v) {
        return new Vertice(vertex.multiply(v), normal, textureCoords);
	}
}

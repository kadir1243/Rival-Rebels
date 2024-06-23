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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.resource.Resource;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector4f;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ModelFromObj {
    public static final ModelFromObj EMPTY = new ModelFromObj(new Triangle[0]);
	public Triangle[] pa;
	public String name;

	public ModelFromObj(Triangle[] PA) {
		pa = PA;
	}

	public static ModelFromObj readObjFile(String path) {
        Resource result;
        byte[] bytes;
        try {
            result = MinecraftClient.getInstance().getResourceManager().getResource(RRIdentifiers.create("models/" + path)).orElseThrow();
            bytes = result.getInputStream().readAllBytes();
        } catch (IOException e) {
            RivalRebels.LOGGER.error("Failed to get model: " + path, e);
            return EMPTY;
        } catch (Exception e) {
            RivalRebels.LOGGER.error("Failed to load model: " + path, e);
            return EMPTY;
        }
        List<String> lines = new String(bytes, StandardCharsets.UTF_8).lines().toList();
		String name = "";
		List<Triangle> tri = new ArrayList<>();
		List<Vec3d> v = new ArrayList<>();
		List<Vec3d> nv = new ArrayList<>();
		List<Vec2f> tv = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("vt")) {
                String[] tex = line.split(" ");
                tv.add(new Vec2f(Float.parseFloat(tex[1]), 1f - Float.parseFloat(tex[2])));
            } else if (line.startsWith("vn")) {
                String[] norm = line.split(" ");
                nv.add(new Vec3d(Float.parseFloat(norm[1]), Float.parseFloat(norm[2]), Float.parseFloat(norm[3])));
            } else if (line.startsWith("v")) {
                String[] vert = line.split(" ");
                v.add(new Vec3d(Float.parseFloat(vert[1]), Float.parseFloat(vert[2]), Float.parseFloat(vert[3])));
            } else if (line.startsWith("f")) {
                String[] coords = line.split(" ");
                Vertice[] vs = new Vertice[coords.length - 1];
                for (int j = 1; j < coords.length; j++) {
                    String[] v1 = coords[j].split("/");
                    vs[j - 1] = new Vertice(v.get(Integer.parseInt(v1[0]) - 1), nv.get(Integer.parseInt(v1[2]) - 1), tv.get(Integer.parseInt(v1[1]) - 1));
                }
                tri.add(new Triangle(vs));
            } else if (line.startsWith("o")) {
                String[] l = line.split(" ");
                name = l[1];
            }
        }

		Triangle[] polygon = new Triangle[tri.size()];

		for (int i = 0; i < tri.size(); i++)
		{
			polygon[i] = tri.get(i);
		}
		ModelFromObj modelFromObj = new ModelFromObj(polygon);
		modelFromObj.name = name;
		return modelFromObj;
	}

    public void normalize()
	{
        for (Triangle triangle : pa) {
            triangle.normalize();
        }
	}

	public void scale(float x, float y, float z)
	{
        for (Triangle triangle : pa) {
            triangle.scale(new Vec3d(x, y, z));
        }
	}

    @Deprecated
	public void render(VertexConsumer buffer) {
        for (Triangle triangle : pa) {
            triangle.render(buffer);
        }
	}

    public void render(VertexConsumer buffer, int light, int overlay) {
        render(buffer, new Vector4f(1, 1, 1, 1), light, overlay);
    }

    public void render(VertexConsumer buffer, Vector4f color, int light, int overlay) {
        for (Triangle triangle : pa) {
            triangle.render(buffer, color, light, overlay);
        }
    }

    public void render(VertexConsumer buffer, int light) {
        render(buffer, light, OverlayTexture.DEFAULT_UV);
    }

	public void refine() {
        pa = Arrays.stream(pa).map(Triangle::refine).flatMap(Arrays::stream).toArray(Triangle[]::new);
	}


}

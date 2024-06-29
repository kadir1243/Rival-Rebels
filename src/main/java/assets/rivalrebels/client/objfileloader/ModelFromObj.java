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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.InputStream;
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
        byte[] bytes;
        try {
            Resource result = Minecraft.getInstance().getResourceManager().getResource(RRIdentifiers.create("models/" + path)).orElseThrow();
            InputStream stream = result.open();
            bytes = stream.readAllBytes();
            stream.close();
        } catch (IOException e) {
            RivalRebels.LOGGER.error("Failed to get model: {}", path, e);
            return EMPTY;
        } catch (Exception e) {
            RivalRebels.LOGGER.error("Failed to load model: {}", path, e);
            return EMPTY;
        }
        List<String> lines = new String(bytes, StandardCharsets.UTF_8).lines().toList();
		String name = "";
		List<Triangle> tri = new ArrayList<>();
		List<Vec3> v = new ArrayList<>();
		List<Vec3> nv = new ArrayList<>();
		List<Vec2> tv = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("vt")) {
                String[] tex = line.split(" ");
                tv.add(new Vec2(Float.parseFloat(tex[1]), 1f - Float.parseFloat(tex[2])));
            } else if (line.startsWith("vn")) {
                String[] norm = line.split(" ");
                nv.add(new Vec3(Float.parseFloat(norm[1]), Float.parseFloat(norm[2]), Float.parseFloat(norm[3])));
            } else if (line.startsWith("v")) {
                String[] vert = line.split(" ");
                v.add(new Vec3(Float.parseFloat(vert[1]), Float.parseFloat(vert[2]), Float.parseFloat(vert[3])));
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
            triangle.scale(new Vec3(x, y, z));
        }
	}

    public void render(PoseStack pose, VertexConsumer buffer, int light, int overlay) {
        render(pose, buffer, CommonColors.WHITE, light, overlay);
    }

    public void render(PoseStack pose, VertexConsumer buffer, int color, int light, int overlay) {
        for (Triangle triangle : pa) {
            triangle.render(pose, buffer, color, light, overlay);
        }
    }

    public void render(PoseStack pose, VertexConsumer buffer, int light) {
        render(pose, buffer, light, OverlayTexture.NO_OVERLAY);
    }

	public void refine() {
        pa = Arrays.stream(pa).map(Triangle::refine).flatMap(Arrays::stream).toArray(Triangle[]::new);
	}


}

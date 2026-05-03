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
package io.github.kadir1243.rivalrebels.common.noise;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ARGB;

import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class RivalRebelsCellularNoise {
    private static final RandomSource random = RandomSource.create();
    private static final int pointa3D = 32;
	private static final Vec3[] points3D = new Vec3[pointa3D];
    private static final int frames = 28;
    private static final DynamicTexture[] id = genTexture(28, 28, frames);

    private static void refresh3D(RandomSource random) {
		for (int i = 0; i < pointa3D; i++) {
			points3D[i] = new Vec3(random.nextDouble(), random.nextDouble(), random.nextDouble());
		}
	}

    private static double noise(double xin, double yin, double zin) {
		double result = 1;
		for (int i = 0; i < pointa3D; i++)
		{
            Vec3 point = points3D[i];
			double dist = getDist(point, xin, yin, zin);
			if (dist <= result)
			{
				result = dist;
			}
		}
		return (Math.sqrt(result) * 4) - 0.75d;
	}

    private static double getDist(Vec3 point, double xin, double yin, double zin) {
        double result = 1;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Vec3 vec = point.subtract(xin + x, yin + y, zin + z);
                    if (vec.lengthSqr() < result) result = vec.lengthSqr();
                }
            }
        }
        return result;
    }

    private static DynamicTexture[] genTexture(int xs, int zs, int ys) {
        DynamicTexture[] ids = new DynamicTexture[ys];
		refresh3D(random);
		byte red = (byte) 0xBB;
		byte grn = (byte) 0x88;
		byte blu = (byte) 0xFF;
		for (int i = 0; i < ys; i++) {
            NativeImage image = new NativeImage(xs, zs, false);
			for (int x = 0; x < xs; x++) {
				for (int z = 0; z < zs; z++) {
                    image.setPixel(x, z, ARGB.color((byte) ((noise(x / (double) xs, z / (double) zs, (double) i / (double) ys) + 1) * 127), red, grn, blu));
                }
			}
            DynamicTexture texture = new DynamicTexture(
                "Rival Rebels Cellular Noise",
                xs,
                zs,
                false
            );
            texture.setPixels(image);
			ids[i] = texture;
		}
		return ids;
	}

    public static DynamicTexture getCurrentRandomId() {
        return RivalRebelsCellularNoise.id[(int) ((System.currentTimeMillis() / 100) % RivalRebelsCellularNoise.frames)];
    }

}

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
package assets.rivalrebels.common.noise;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class RivalRebelsCellularNoise {
    private static final RandomSource random = RandomSource.create();
    private static final int pointa3D = 32;
	private static final Vec3[] points3D = new Vec3[pointa3D];
    private static final int frames = 28;
    private static final int[] id = genTexture(28, 28, frames);

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

    private static double getDist(Vec3 point, double xin, double yin, double zin)
	{
		double result = 1;
		for (int x = -1; x <= 1; x++)
		{
			double xx = point.x - (xin + x);
			double X = xx * xx;
			for (int y = -1; y <= 1; y++)
			{
				double yy = point.y - (yin + y);
				double Y = yy * yy + X;
				for (int z = -1; z <= 1; z++)
				{
					double zz = point.z - (zin + z);
					double Z = zz * zz + Y;
					if (Z < result) result = Z;
				}
			}
		}
		return result;
	}

    private static int[] genTexture(int xs, int zs, int ys) {
		int[] ids = new int[ys];
		refresh3D(random);
		int size = xs * zs * 4;
		byte red = (byte) 0xBB;
		byte grn = (byte) 0x88;
		byte blu = (byte) 0xFF;
		for (int i = 0; i < ys; i++) {
			ByteBuffer bb = BufferUtils.createByteBuffer(size);
			for (int x = 0; x < xs; x++) {
				for (int z = 0; z < zs; z++) {
					bb.put(red);
					bb.put(grn);
					bb.put(blu);
					bb.put((byte) ((noise((double) x / (double) xs, (double) z / (double) zs, (double) i / (double) ys) + 1) * 127));
				}
			}
			int id = TextureUtil.generateTextureId();
			RenderSystem.bindTexture(id);
			RenderSystem.texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			RenderSystem.texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
            GlStateManager._texImage2D(GlConst.GL_TEXTURE_2D, 0, GlConst.GL_RGBA8, xs, zs, 0, GlConst.GL_RGBA, GlConst.GL_UNSIGNED_BYTE, bb.asIntBuffer());
			ids[i] = id;
		}
		return ids;
	}

    private static long getTime()
	{
		return System.currentTimeMillis();
	}

    public static int getCurrentRandomId() {
        return RivalRebelsCellularNoise.id[(int) ((RivalRebelsCellularNoise.getTime() / 100) % RivalRebelsCellularNoise.frames)];
    }

    public static final RenderType CELLULAR_NOISE = RenderType.create(
        "cellular_noise",
        DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
        VertexFormat.Mode.QUADS,
        999,
        RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
            .setTextureState(new RenderStateShard.EmptyTextureStateShard(() -> RenderSystem.setShaderTexture(0, getCurrentRandomId()), () -> {}))
            .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
            .createCompositeState(false)
    );
}

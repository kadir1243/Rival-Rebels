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

import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class RivalRebelsCellularNoise {
    public static int		pointa3D	= 32;
	public static Vec3d[]	points3D	= new Vec3d[pointa3D];

    public static void refresh3D(Random random)
	{
		for (int i = 0; i < pointa3D; i++)
		{
			points3D[i] = new Vec3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
		}
	}

    public static double noise(double xin, double yin, double zin)
	{
		double result = 1;
		for (int i = 0; i < pointa3D; i++)
		{
            Vec3d point = points3D[i];
			double dist = getDist(point, xin, yin, zin);
			if (dist <= result)
			{
				result = dist;
			}
		}
		return (Math.sqrt(result) * 4) - 0.75d;
	}

	private static double getDist(Vec3d point, double xin, double yin)
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
				if (Y < result) result = Y;
			}
		}
		return result;
	}

	private static double getDist(Vec3d point, double xin, double yin, double zin)
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
}

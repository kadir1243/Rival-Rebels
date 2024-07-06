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
package assets.rivalrebels.common.explosion;

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.BlackList;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityDebris;
import assets.rivalrebels.common.entity.EntityFlameBall;
import assets.rivalrebels.common.entity.EntityRhodes;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Explosion
{
	public Explosion(Level world, double x, double y, double z, int strength, boolean fire, boolean crater, DamageSource dmgsrc)
	{
		world.addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
		if (!world.isClientSide())
		{
			if (fire)
			{
				fireSpread(world, x, y, z, strength);
			}
			else
			{
				createHole(world, x, y, z, strength * strength, crater, 4);
			}
			pushAndHurtEntities(world, x, y, z, strength, dmgsrc);
		}
	}

	private void fireSpread(Level world, double x, double y, double z, int radius)
	{
        int halfradius = radius / 2;
		int tworadius = radius * 2;
		for (int X = -tworadius; X <= tworadius; X++)
		{
			for (int Y = -tworadius; Y <= tworadius; Y++)
			{
				for (int Z = -tworadius; Z <= tworadius; Z++)
				{
					int xx = (int) x + X;
					int yy = (int) y + Y;
					int zz = (int) z + Z;
                    BlockPos pos = new BlockPos(xx, yy, zz);
					if (world.isEmptyBlock(pos))
					{
						int dist = (int) Math.sqrt(X * X + Y * Y + Z * Z);
						if (dist < radius)
						{
							int varrand = 1 + dist - halfradius;
							if (dist < halfradius)
							{
								world.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
							}
							else if (varrand > 0)
							{
								if (world.random.nextInt(varrand) == 0 || world.random.nextInt(varrand / 2 + 1) == 0)
								{
									world.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
								}
							}
						}
					}
				}
			}
		}
	}

	private void createHole(Level world, double x, double y, double z, int radius, boolean crater, int delete)
	{
        int halfradius = radius >> 2;
		int tworadius = radius << 2;
		for (int X = -tworadius; X <= tworadius; X++)
		{
			int xx = (int) x + X;
			for (int Y = -tworadius; Y <= tworadius; Y++)
			{
				int yy = (int) y + Y;
				for (int Z = -tworadius; Z <= tworadius; Z++)
				{
					int zz = (int) z + Z;
                    BlockPos pos = new BlockPos(xx, yy, zz);
                    Block block = world.getBlockState(pos).getBlock();
					if (block != Blocks.AIR && block != Blocks.BEDROCK)
					{
						int dist = X * X + Y * Y + Z * Z;
						if (dist <= delete && block == RRBlocks.camo1 && block == RRBlocks.camo2 && block == RRBlocks.camo3)
						{
                            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						}
						else if (dist < radius)
						{
							int varrand = 1 + dist - halfradius;
							if (dist < halfradius)
							{
								breakBlock(world, xx, yy, zz, radius, x, y, z);
							}
							else if (varrand > 0)
							{
								if ((world.random.nextInt(varrand) == 0 || world.random.nextInt(varrand / 2 + 1) == 0))
								{
									breakBlock(world, xx, yy, zz, radius, x, y, z);
								}
							}
						}
						else if (dist < tworadius)
						{
							if ((Y >= 2 || (dist < radius * 1.5 && Y == 1)) && crater)
							{
								breakBlock(world, xx, yy, zz, radius, x, y, z);
							}
						}
					}
				}
			}
		}
	}

	private void breakBlock(Level world, int xx, int yy, int zz, int strength, double x, double y, double z)
	{
        BlockPos pos = new BlockPos(xx, yy, zz);
        BlockState state = world.getBlockState(pos);
		if (state.is(RRBlocks.remotecharge))
		{
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			RivalRebelsSoundPlayer.playSound(world, 22, 0, xx, yy, zz, 0.5f, 0.3f);
			new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RRConfig.SERVER.getChargeExplosionSize(), false, false, RivalRebelsDamageSource.charge(world));
			return;
		}
		if (state.is(RRBlocks.toxicgas) || state.is(Blocks.CHEST) || state.is(Blocks.VINE) || state.is(Blocks.TALL_GRASS) || state.is(RRBlocks.flare) || state.is(RRBlocks.light) || state.is(RRBlocks.light2) || state.is(RRBlocks.reactive) || state.is(RRBlocks.timedbomb))
		{
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return;
		}
		if (state.is(BlockTags.WOODEN_STAIRS)) world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		if (state.is(RRBlocks.camo1) || state.is(RRBlocks.camo2) || state.is(RRBlocks.camo3) || state.is(RRBlocks.conduit))
		{
			if (world.random.nextInt(20) != 0) return;
		}
		if (BlackList.explosion(state))
		{
			return;
		}

		EntityDebris e = new EntityDebris(world, xx, yy, zz);
		double xmo = x - xx;
		double ymo = y - yy;
		double zmo = z - zz;
		e.push(xmo * 0.2f, ymo * 0.2f, zmo * 0.2f);
		world.addFreshEntity(e);
	}

	private void pushAndHurtEntities(Level world, double x, double y, double z, int radius, DamageSource dmgsrc)
	{
		int var3 = Mth.floor(x - radius - 1.0D);
		int var4 = Mth.floor(x + radius + 1.0D);
		int var5 = Mth.floor(y - radius - 1.0D);
		int var28 = Mth.floor(y + radius + 1.0D);
		int var7 = Mth.floor(z - radius - 1.0D);
		int var29 = Mth.floor(z + radius + 1.0D);
		List<Entity> var9 = world.getEntities(null, new AABB(var3, var5, var7, var4, var28, var29));
		Vec3 var30 = new Vec3(x, y, z);

		radius *= 4;

        for (Entity entity : var9) {
            if (!(entity instanceof EntityDebris) && !(entity instanceof EntityFlameBall) && !(entity instanceof EntityRhodes)) {
                double var13 = Math.sqrt(entity.distanceToSqr(x, y, z)) / radius;

                if (var13 <= 1.0D) {
                    double var15 = entity.getX() - x;
                    double var17 = entity.getY() + entity.getEyeHeight(entity.getPose()) - y;
                    double var19 = entity.getZ() - z;
                    double var33 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                    if (var33 != 0.0D) {
                        var15 /= var33;
                        var17 /= var33;
                        var19 /= var33;
                        double var32 = net.minecraft.world.level.Explosion.getSeenPercent(var30, entity);
                        double var34 = (1.0D - var13) * var32;
                        entity.hurt(dmgsrc, (int) ((var34 * var34 + var34) / 2.0D * radius + 1.0D));
                        entity.push(
                            var15 * var34,
                            var17 * var34,
                            var19 * var34);
                    }
                }
            }
        }
	}
}

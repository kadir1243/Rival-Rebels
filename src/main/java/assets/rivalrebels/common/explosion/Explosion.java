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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.BlackList;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityDebris;
import assets.rivalrebels.common.entity.EntityFlameBall;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class Explosion
{
	public Explosion(World world, double x, double y, double z, int strength, boolean fire, boolean crater, DamageSource dmgsrc)
	{
		world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, x, y, z, 0, 0, 0);
		if (!world.isRemote)
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

	private void fireSpread(World world, double x, double y, double z, int radius)
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
                    IBlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();
					if (block.isAir(state, world, pos))
					{
						int dist = (int) Math.sqrt(X * X + Y * Y + Z * Z);
						if (dist < radius)
						{
							int varrand = 1 + dist - halfradius;
							if (dist < halfradius)
							{
								world.setBlockState(pos, Blocks.FIRE.getDefaultState());
							}
							else if (varrand > 0)
							{
								if (world.rand.nextInt(varrand) == 0 || world.rand.nextInt(varrand / 2 + 1) == 0)
								{
									world.setBlockState(pos, Blocks.FIRE.getDefaultState());
								}
							}
						}
					}
				}
			}
		}
	}

	private void createHole(World world, double x, double y, double z, int radius, boolean crater, int delete)
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
						if (dist <= delete && block == RivalRebels.camo1 && block == RivalRebels.camo2 && block == RivalRebels.camo3)
						{
                            world.setBlockToAir(pos);
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
								if ((world.rand.nextInt(varrand) == 0 || world.rand.nextInt(varrand / 2 + 1) == 0))
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

	private void breakBlock(World world, int xx, int yy, int zz, int strength, double x, double y, double z)
	{
        BlockPos pos = new BlockPos(xx, yy, zz);
		Block block = world.getBlockState(pos).getBlock();
		if (block == RivalRebels.remotecharge)
		{
			world.setBlockToAir(pos);
			RivalRebelsSoundPlayer.playSound(world, 22, 0, xx, yy, zz, 0.5f, 0.3f);
			new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RivalRebels.chargeExplodeSize, false, false, RivalRebelsDamageSource.charge);
			return;
		}
		if (block == RivalRebels.toxicgas || block == Blocks.CHEST || block == Blocks.VINE || block == Blocks.TALLGRASS || block == RivalRebels.flare || block == RivalRebels.light || block == RivalRebels.light2 || block == RivalRebels.reactive || block == RivalRebels.timedbomb)
		{
			world.setBlockToAir(pos);
			return;
		}
		if (OreDictionary.getOres("stairWood").contains(Item.getItemFromBlock(block).getDefaultInstance())) world.setBlockState(pos, Blocks.PLANKS.getDefaultState());
		if (block == RivalRebels.camo1 || block == RivalRebels.camo2 || block == RivalRebels.camo3 || block == RivalRebels.conduit)
		{
			if (world.rand.nextInt(20) != 0) return;
		}
		if (BlackList.explosion(block))
		{
			return;
		}

		EntityDebris e = new EntityDebris(world, xx, yy, zz);
		double xmo = x - xx;
		double ymo = y - yy;
		double zmo = z - zz;
		e.addVelocity(xmo * 0.2f, ymo * 0.2f, zmo * 0.2f);
		world.spawnEntity(e);
	}

	private void pushAndHurtEntities(World world, double x, double y, double z, int radius, DamageSource dmgsrc)
	{
		int var3 = MathHelper.floor(x - radius - 1.0D);
		int var4 = MathHelper.floor(x + radius + 1.0D);
		int var5 = MathHelper.floor(y - radius - 1.0D);
		int var28 = MathHelper.floor(y + radius + 1.0D);
		int var7 = MathHelper.floor(z - radius - 1.0D);
		int var29 = MathHelper.floor(z + radius + 1.0D);
		List<Entity> var9 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(var3, var5, var7, var4, var28, var29));
		Vec3d var30 = new Vec3d(x, y, z);

		radius *= 4;

        for (Entity entity : var9) {
            if (!(entity instanceof EntityDebris) && !(entity instanceof EntityFlameBall) && !(entity instanceof EntityRhodes)) {
                double var13 = entity.getDistance(x, y, z) / radius;

                if (var13 <= 1.0D) {
                    double var15 = entity.posX - x;
                    double var17 = entity.posY + entity.getEyeHeight() - y;
                    double var19 = entity.posZ - z;
                    double var33 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                    if (var33 != 0.0D) {
                        var15 /= var33;
                        var17 /= var33;
                        var19 /= var33;
                        double var32 = world.getBlockDensity(var30, entity.getEntityBoundingBox());
                        double var34 = (1.0D - var13) * var32;
                        entity.attackEntityFrom(dmgsrc, (int) ((var34 * var34 + var34) / 2.0D * radius + 1.0D));
                        entity.motionX += var15 * var34;
                        entity.motionY += var17 * var34;
                        entity.motionZ += var19 * var34;
                    }
                }
            }
        }
	}
}

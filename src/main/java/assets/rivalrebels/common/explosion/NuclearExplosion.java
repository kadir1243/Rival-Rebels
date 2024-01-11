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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.entity.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class NuclearExplosion
{
	public static Block[]	prblocks	= {
						Blocks.COAL_ORE,
						Blocks.IRON_ORE,
						Blocks.REDSTONE_ORE,
						Blocks.GOLD_ORE,
						Blocks.LAPIS_ORE,
						Blocks.DIAMOND_ORE,
						Blocks.EMERALD_ORE,
						};

	public static Block[]	pgblocks	= {
						Blocks.STONE,
						Blocks.COBBLESTONE,
						Blocks.DIRT,
						};

	public NuclearExplosion(World world, int x, int y, int z, int strength) {
		if (!world.isRemote)
		{
			createHole(world, x, y, z, strength, true);
			// pushAndHurtEntities(world, x, y, z, strength);
			fixLag(world, x, y, z, strength);
		}
	}

	public NuclearExplosion(World world, int x, int y, int z, int strength, boolean breakobj) {
		if (!world.isRemote)
		{
			createHole(world, x, y, z, strength, breakobj);
			// pushAndHurtEntities(world, x, y, z, strength);
			fixLag(world, x, y, z, strength);
		}
	}

	private void createHole(World world, int x, int y, int z, int radius, boolean breakobj)
	{
        int halfradius = radius / 2;
		int onepointfiveradius = halfradius * 3;
		int AOC = radius / RivalRebels.nuclearBombStrength;
		int onepointfiveradiussqrd = onepointfiveradius * onepointfiveradius;
		int twoAOC = AOC * 2; // twoaoc its a twoAOC

		for (int X = -onepointfiveradius; X <= onepointfiveradius; X++)
		{
			int xx = x + X;
			int XX = X * X;
			for (int Z = -onepointfiveradius; Z <= onepointfiveradius; Z++)
			{
				int ZZ = Z * Z + XX;
				int zz = z + Z;
				for (int Y = -onepointfiveradius; Y <= onepointfiveradius; Y++)
				{
					int YY = Y * Y + ZZ;
					int yy = y + Y;
					if (YY < onepointfiveradiussqrd)
					{
                        BlockPos pos = new BlockPos(xx, yy, zz);
                        Block block = world.getBlockState(pos).getBlock();
						if (block != Blocks.AIR)
						{
							int dist = (int) Math.sqrt(YY);
							if (dist < radius && block != Blocks.BEDROCK)
							{
								int varrand = 1 + dist - halfradius;
								if (dist < halfradius)
								{
									if (breakobj && block == RivalRebels.omegaobj)
									{
										RivalRebels.round.winSigma();
										block = RivalRebels.plasmaexplosion;
									}
									else if (breakobj && block == RivalRebels.sigmaobj)
									{
										RivalRebels.round.winOmega();
										block = RivalRebels.plasmaexplosion;
									}
									else if (block == RivalRebels.reactive)
									{
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (world.rand.nextDouble() * 2); i++)
											world.setBlockToAir(pos);
									}
									else
									{
										world.setBlockToAir(pos);
										block = Blocks.AIR;
									}
								}
								else if (varrand > 0)
								{
									int randomness = halfradius - varrand / 2;
									if (breakobj && block == RivalRebels.omegaobj)
									{
										RivalRebels.round.winSigma();
										block = RivalRebels.plasmaexplosion;
									}
									else if (breakobj && block == RivalRebels.sigmaobj)
									{
										RivalRebels.round.winOmega();
										block = RivalRebels.plasmaexplosion;
									}
									else if (block == RivalRebels.reactive)
									{
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (world.rand.nextDouble() * 2); i++)
                                            world.setBlockToAir(pos);
									}
									else if (block == Blocks.WATER || block == Blocks.LAVA || block == Blocks.FLOWING_WATER || block == Blocks.FLOWING_LAVA)
									{
										world.setBlockToAir(pos);
										block = Blocks.AIR;
									}
									else if (block == Blocks.STONE && world.rand.nextInt(randomness) < randomness / 2)
									{
										world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
										block = Blocks.COBBLESTONE;
									}
									else if ((block == Blocks.GRASS || block == Blocks.DIRT))
									{
										world.setBlockState(pos, RivalRebels.radioactivedirt.getDefaultState());
									}
									else if ((block == Blocks.SAND || block == Blocks.SANDSTONE))
									{
										world.setBlockState(pos, RivalRebels.radioactivesand.getDefaultState());
									}
									else if ((world.rand.nextInt(varrand) == 0 || world.rand.nextInt(varrand / 2 + 1) == 0))
									{
										world.setBlockToAir(pos);
										block = Blocks.AIR;
									}
								}
							}
							if (dist < onepointfiveradius && block != Blocks.AIR && block != Blocks.BEDROCK)
							{
								if (Y >= twoAOC || (dist < onepointfiveradius && Y >= AOC))
								{
									world.setBlockToAir(pos);
								}
								else if (world.getBlockState(pos.down()).getBlock() == Blocks.LOG)
								{
									world.setBlockState(pos, Blocks.FIRE.getDefaultState());
								}
								else if ((block == Blocks.GRASS || block == Blocks.DIRT))
								{
									world.setBlockState(pos, RivalRebels.radioactivedirt.getDefaultState());
								}
								else if ((block == Blocks.SAND || block == Blocks.SANDSTONE))
								{
									world.setBlockState(pos, RivalRebels.radioactivesand.getDefaultState());
								}
							}
						}
					}
				}
			}
		}
		world.playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F, true);
	}

	private void pushAndHurtEntities(World world, int x, int y, int z, int radius)
	{
		radius *= 4;
		int var3 = MathHelper.floor(x - (double) radius - 1.0D);
		int var4 = MathHelper.floor(x + (double) radius + 1.0D);
		int var5 = MathHelper.floor(y - (double) radius - 1.0D);
		int var28 = MathHelper.floor(y + (double) radius + 1.0D);
		int var7 = MathHelper.floor(z - (double) radius - 1.0D);
		int var29 = MathHelper.floor(z + (double) radius + 1.0D);
		List<Entity> var9 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(var3, var5, var7, var4, var28, var29));
		Vec3d var30 = new Vec3d(x, y, z);

        for (Entity var31 : var9) {
            double var13 = var31.getDistance(x, y, z) / radius;

            if (var13 <= 1.0D) {
                double var15 = var31.posX - x;
                double var17 = var31.posY + var31.getEyeHeight() - y;
                double var19 = var31.posZ - z;
                double var33 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = world.getBlockDensity(var30, var31.getEntityBoundingBox());
                    double var34 = (1.0D - var13) * var32 * ((var31 instanceof EntityB83 || var31 instanceof EntityHackB83) ? -1 : 1);
                    if (!(var31 instanceof EntityNuclearBlast) && !(var31 instanceof EntityTsarBlast) && !(var31 instanceof EntityRhodes)) {
                        if (var31 instanceof EntityFallingBlock) var31.setDead();
                        var31.attackEntityFrom(RivalRebelsDamageSource.nuclearblast, (int) ((var34 * var34 + var34) / 2.0D * 8.0D * radius + 1.0D) * 4);
                        var31.motionX -= var15 * var34 * 8;
                        var31.motionY -= var17 * var34 * 8;
                        var31.motionZ -= var19 * var34 * 8;
                    }
                    if (var31 instanceof EntityRhodes) {
                        var31.attackEntityFrom(RivalRebelsDamageSource.nuclearblast, (int) (radius * var34 * 0.2f));
                    }
                }
            }
        }
	}

	private void fixLag(World world, int x, int y, int z, int strength)
	{
		for (int X = -strength; X <= strength; X++)
		{
			int xx = x + X;
			for (int Y = -strength; Y <= strength; Y++)
			{
				int yy = y + Y;
				for (int Z = -strength; Z <= strength; Z++)
				{
					int zz = z + Z;
                    BlockPos pos = new BlockPos(xx, yy, zz);
                    if (world.isAirBlock(pos) && world.getBlockLightOpacity(pos) == 0)
					{
						if (!world.isAirBlock(pos.up()) &&
                            !world.isAirBlock(pos.down()) &&
                            !world.isAirBlock(pos.south()) &&
                            !world.isAirBlock(pos.east()) &&
                            !world.isAirBlock(pos.west()) &&
                            !world.isAirBlock(pos.north()))
						{
							int r = world.rand.nextInt(50);
							Block id;
							if (r == 0)
							{
								id = prblocks[world.rand.nextInt(prblocks.length)];
							}
							else
							{
								id = pgblocks[world.rand.nextInt(pgblocks.length)];
							}
							world.setBlockState(pos, id.getDefaultState());
						}
					}
				}
			}
		}
	}
}

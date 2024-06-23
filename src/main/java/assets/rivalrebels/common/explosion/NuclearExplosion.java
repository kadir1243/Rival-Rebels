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
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.entity.*;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static assets.rivalrebels.RivalRebels.getBlocks;

public class NuclearExplosion
{
	public static Block[]	prblocks;
    public static Block[]	pgblocks;

    static {
        Set<Block> pgblocks = new HashSet<>(getBlocks(BlockTags.BASE_STONE_OVERWORLD));
        pgblocks.add(Blocks.COBBLESTONE);
        pgblocks.addAll(getBlocks(BlockTags.DIRT));
        NuclearExplosion.pgblocks = pgblocks.toArray(new Block[0]);
        Set<Block> prblocks = new HashSet<>();
        prblocks.addAll(getBlocks(BlockTags.COAL_ORES));
        prblocks.addAll(getBlocks(BlockTags.IRON_ORES));
        prblocks.addAll(getBlocks(BlockTags.REDSTONE_ORES));
        prblocks.addAll(getBlocks(BlockTags.GOLD_ORES));
        prblocks.addAll(getBlocks(BlockTags.LAPIS_ORES));
        prblocks.addAll(getBlocks(BlockTags.DIAMOND_ORES));
        prblocks.addAll(getBlocks(BlockTags.EMERALD_ORES));

        NuclearExplosion.prblocks = prblocks.toArray(new Block[0]);
    }

	public NuclearExplosion(World world, int x, int y, int z, int strength) {
		if (!world.isClient) {
			createHole(world, x, y, z, strength, true);
			// pushAndHurtEntities(world, x, y, z, strength);
			fixLag(world, x, y, z, strength);
		}
	}

	public NuclearExplosion(World world, int x, int y, int z, int strength, boolean breakobj) {
		if (!world.isClient)
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
                        BlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
						if (!world.isAir(pos))
						{
							int dist = (int) Math.sqrt(YY);
							if (dist < radius && block != Blocks.BEDROCK)
							{
								int varrand = 1 + dist - halfradius;
								if (dist < halfradius)
								{
									if (breakobj && block == RRBlocks.omegaobj)
									{
										RivalRebels.round.winSigma();
										state = RRBlocks.plasmaexplosion.getDefaultState();
									}
									else if (breakobj && block == RRBlocks.sigmaobj)
									{
										RivalRebels.round.winOmega();
										state = RRBlocks.plasmaexplosion.getDefaultState();
									}
									else if (block == RRBlocks.reactive)
									{
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (world.random.nextDouble() * 2); i++)
											world.setBlockState(pos, Blocks.AIR.getDefaultState());
									}
									else
									{
										world.setBlockState(pos, Blocks.AIR.getDefaultState());
										state = Blocks.AIR.getDefaultState();
									}
								}
								else if (varrand > 0)
								{
									int randomness = halfradius - varrand / 2;
									if (breakobj && state.isOf(RRBlocks.omegaobj))
									{
										RivalRebels.round.winSigma();
										state = RRBlocks.plasmaexplosion.getDefaultState();
									}
									else if (breakobj && state.isOf(RRBlocks.sigmaobj))
									{
										RivalRebels.round.winOmega();
										state = RRBlocks.plasmaexplosion.getDefaultState();
									}
									else if (state.isOf(RRBlocks.reactive))
									{
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (world.random.nextDouble() * 2); i++)
                                            world.setBlockState(pos, Blocks.AIR.getDefaultState());
									}
									else if (!state.getFluidState().isEmpty())
									{
										world.setBlockState(pos, Blocks.AIR.getDefaultState());
										state = Blocks.AIR.getDefaultState();
									}
									else if (state.isIn(BlockTags.BASE_STONE_OVERWORLD) && world.random.nextInt(randomness) < randomness / 2)
									{
										world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
										state = Blocks.COBBLESTONE.getDefaultState();
									}
									else if (state.isIn(BlockTags.DIRT))
									{
										world.setBlockState(pos, RRBlocks.radioactivedirt.getDefaultState());
									}
									else if ((state.isIn(BlockTags.SAND) || state.isIn(ConventionalBlockTags.SANDSTONE_BLOCKS)))
									{
										world.setBlockState(pos, RRBlocks.radioactivesand.getDefaultState());
									}
									else if ((world.random.nextInt(varrand) == 0 || world.random.nextInt(varrand / 2 + 1) == 0))
									{
										world.setBlockState(pos, Blocks.AIR.getDefaultState());
										state = Blocks.AIR.getDefaultState();
									}
								}
							}
							if (dist < onepointfiveradius && !state.isAir() && block != Blocks.BEDROCK)
							{
								if (Y >= twoAOC || (dist < onepointfiveradius && Y >= AOC))
								{
									world.setBlockState(pos, Blocks.AIR.getDefaultState());
								}
								else if (world.getBlockState(pos.down()).isIn(BlockTags.LOGS_THAT_BURN))
								{
									world.setBlockState(pos, Blocks.FIRE.getDefaultState());
								}
								else if (state.isIn(BlockTags.DIRT))
								{
									world.setBlockState(pos, RRBlocks.radioactivedirt.getDefaultState());
								}
								else if ((state.isIn(BlockTags.SAND) || state.isIn(ConventionalBlockTags.SANDSTONE_BLOCKS)))
								{
									world.setBlockState(pos, RRBlocks.radioactivesand.getDefaultState());
								}
							}
						}
					}
				}
			}
		}
		world.playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 4.0F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F, true);
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
		List<Entity> var9 = world.getOtherEntities(null, new Box(var3, var5, var7, var4, var28, var29));
		Vec3d var30 = new Vec3d(x, y, z);

        for (Entity var31 : var9) {
            double var13 = Math.sqrt(var31.squaredDistanceTo(x, y, z)) / radius;

            if (var13 <= 1.0D) {
                double var15 = var31.getX() - x;
                double var17 = var31.getY() + var31.getEyeHeight(var31.getPose()) - y;
                double var19 = var31.getZ() - z;
                double var33 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = net.minecraft.world.explosion.Explosion.getExposure(var30, var31);
                    double var34 = (1.0D - var13) * var32 * ((var31 instanceof EntityB83 || var31 instanceof EntityHackB83) ? -1 : 1);
                    if (!(var31 instanceof EntityNuclearBlast) && !(var31 instanceof EntityTsarBlast) && !(var31 instanceof EntityRhodes)) {
                        if (var31 instanceof FallingBlockEntity) var31.kill();
                        var31.damage(RivalRebelsDamageSource.nuclearBlast(world), (int) ((var34 * var34 + var34) / 2.0D * 8.0D * radius + 1.0D) * 4);
                        var31.setVelocity(var31.getVelocity().subtract(
                            var15 * var34 * 8,
                            var17 * var34 * 8,
                            var19 * var34 * 8
                        ));
                    }
                    if (var31 instanceof EntityRhodes) {
                        var31.damage(RivalRebelsDamageSource.nuclearBlast(world), (int) (radius * var34 * 0.2f));
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
                    if (world.isAir(pos) && world.getLightLevel(pos) == 0) {
						if (!world.isAir(pos.up()) &&
                            !world.isAir(pos.down()) &&
                            !world.isAir(pos.south()) &&
                            !world.isAir(pos.east()) &&
                            !world.isAir(pos.west()) &&
                            !world.isAir(pos.north()))
						{
							int r = world.random.nextInt(50);
							Block id;
							if (r == 0)
							{
								id = prblocks[world.random.nextInt(prblocks.length)];
							}
							else
							{
								id = pgblocks[world.random.nextInt(pgblocks.length)];
							}
							world.setBlockState(pos, id.getDefaultState());
						}
					}
				}
			}
		}
	}
}

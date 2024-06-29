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
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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

	public NuclearExplosion(Level world, int x, int y, int z, int strength) {
		if (!world.isClientSide) {
			createHole(world, x, y, z, strength, true);
			pushAndHurtEntities(world, x, y, z, strength);
			fixLag(world, x, y, z, strength);
		}
	}

	public NuclearExplosion(Level world, int x, int y, int z, int strength, boolean breakobj) {
		if (!world.isClientSide)
		{
			createHole(world, x, y, z, strength, breakobj);
			pushAndHurtEntities(world, x, y, z, strength);
			fixLag(world, x, y, z, strength);
		}
	}

	private void createHole(Level world, int x, int y, int z, int radius, boolean breakobj)
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
						if (!world.isEmptyBlock(pos))
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
										state = RRBlocks.plasmaexplosion.defaultBlockState();
									}
									else if (breakobj && block == RRBlocks.sigmaobj)
									{
										RivalRebels.round.winOmega();
										state = RRBlocks.plasmaexplosion.defaultBlockState();
									}
									else if (block == RRBlocks.reactive)
									{
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (world.random.nextDouble() * 2); i++)
											world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
									}
									else
									{
										world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
										state = Blocks.AIR.defaultBlockState();
									}
								}
								else if (varrand > 0)
								{
									int randomness = halfradius - varrand / 2;
									if (breakobj && state.is(RRBlocks.omegaobj))
									{
										RivalRebels.round.winSigma();
										state = RRBlocks.plasmaexplosion.defaultBlockState();
									}
									else if (breakobj && state.is(RRBlocks.sigmaobj))
									{
										RivalRebels.round.winOmega();
										state = RRBlocks.plasmaexplosion.defaultBlockState();
									}
									else if (state.is(RRBlocks.reactive))
									{
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (world.random.nextDouble() * 2); i++)
                                            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
									}
									else if (!state.getFluidState().isEmpty())
									{
										world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
										state = Blocks.AIR.defaultBlockState();
									}
									else if (state.is(BlockTags.BASE_STONE_OVERWORLD) && world.random.nextInt(randomness) < randomness / 2)
									{
										world.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
										state = Blocks.COBBLESTONE.defaultBlockState();
									}
									else if (state.is(BlockTags.DIRT))
									{
										world.setBlockAndUpdate(pos, RRBlocks.radioactivedirt.defaultBlockState());
									}
									else if ((state.is(BlockTags.SAND) || state.is(ConventionalBlockTags.SANDSTONE_BLOCKS)))
									{
										world.setBlockAndUpdate(pos, RRBlocks.radioactivesand.defaultBlockState());
									}
									else if ((world.random.nextInt(varrand) == 0 || world.random.nextInt(varrand / 2 + 1) == 0))
									{
										world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
										state = Blocks.AIR.defaultBlockState();
									}
								}
							}
							if (dist < onepointfiveradius && !state.isAir() && block != Blocks.BEDROCK)
							{
								if (Y >= twoAOC || (dist < onepointfiveradius && Y >= AOC))
								{
									world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
								}
								else if (world.getBlockState(pos.below()).is(BlockTags.LOGS_THAT_BURN))
								{
									world.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
								}
								else if (state.is(BlockTags.DIRT))
								{
									world.setBlockAndUpdate(pos, RRBlocks.radioactivedirt.defaultBlockState());
								}
								else if ((state.is(BlockTags.SAND) || state.is(ConventionalBlockTags.SANDSTONE_BLOCKS)))
								{
									world.setBlockAndUpdate(pos, RRBlocks.radioactivesand.defaultBlockState());
								}
							}
						}
					}
				}
			}
		}
		world.playLocalSound(x, y, z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.MASTER, 4.0F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F, true);
	}

	private void pushAndHurtEntities(Level world, int x, int y, int z, int radius)
	{
		radius *= 4;
		int var3 = Mth.floor(x - (double) radius - 1.0D);
		int var4 = Mth.floor(x + (double) radius + 1.0D);
		int var5 = Mth.floor(y - (double) radius - 1.0D);
		int var28 = Mth.floor(y + (double) radius + 1.0D);
		int var7 = Mth.floor(z - (double) radius - 1.0D);
		int var29 = Mth.floor(z + (double) radius + 1.0D);
		List<Entity> var9 = world.getEntities(null, new AABB(var3, var5, var7, var4, var28, var29));
		Vec3 var30 = new Vec3(x, y, z);

        for (Entity var31 : var9) {
            double var13 = Math.sqrt(var31.distanceToSqr(x, y, z)) / radius;

            if (var13 <= 1.0D) {
                double var15 = var31.getX() - x;
                double var17 = var31.getY() + var31.getEyeHeight(var31.getPose()) - y;
                double var19 = var31.getZ() - z;
                double var33 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = net.minecraft.world.level.Explosion.getSeenPercent(var30, var31);
                    double var34 = (1.0D - var13) * var32 * ((var31 instanceof EntityB83 || var31 instanceof EntityHackB83) ? -1 : 1);
                    if (!(var31 instanceof EntityNuclearBlast) && !(var31 instanceof EntityTsarBlast) && !(var31 instanceof EntityRhodes)) {
                        if (var31 instanceof FallingBlockEntity) var31.kill();
                        var31.hurt(RivalRebelsDamageSource.nuclearBlast(world), (int) ((var34 * var34 + var34) / 2.0D * 8.0D * radius + 1.0D) * 4);
                        var31.setDeltaMovement(var31.getDeltaMovement().subtract(
                            var15 * var34 * 8,
                            var17 * var34 * 8,
                            var19 * var34 * 8
                        ));
                    }
                    if (var31 instanceof EntityRhodes) {
                        var31.hurt(RivalRebelsDamageSource.nuclearBlast(world), (int) (radius * var34 * 0.2f));
                    }
                }
            }
        }
	}

	private void fixLag(Level world, int x, int y, int z, int strength)
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
                    if (world.isEmptyBlock(pos) && world.getMaxLocalRawBrightness(pos) == 0) {
						if (!world.isEmptyBlock(pos.above()) &&
                            !world.isEmptyBlock(pos.below()) &&
                            !world.isEmptyBlock(pos.south()) &&
                            !world.isEmptyBlock(pos.east()) &&
                            !world.isEmptyBlock(pos.west()) &&
                            !world.isEmptyBlock(pos.north()))
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
							world.setBlockAndUpdate(pos, id.defaultBlockState());
						}
					}
				}
			}
		}
	}
}

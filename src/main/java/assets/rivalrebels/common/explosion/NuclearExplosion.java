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

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.entity.EntityB83;
import assets.rivalrebels.common.entity.EntityHackB83;
import assets.rivalrebels.common.entity.EntityNuclearBlast;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.entity.EntityTsarBlast;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class NuclearExplosion
{
	public static Block[]	prblocks	= {
						Blocks.coal_ore,
						Blocks.iron_ore,
						Blocks.redstone_ore,
						Blocks.gold_ore,
						Blocks.lapis_ore,
						Blocks.diamond_ore,
						Blocks.emerald_ore,
						};

	public static Block[]	pgblocks	= {
						Blocks.stone,
						Blocks.cobblestone,
						Blocks.dirt,
						};

	public NuclearExplosion(World world, int x, int y, int z, int strength)
	{
		if (!world.isRemote) {
			createHole(world, x, y, z, strength, true);
			// pushAndHurtEntities(world, x, y, z, strength);
			fixLag(world, x, y, z, strength);
		}
	}

	public NuclearExplosion(World world, int x, int y, int z, int strength, boolean breakobj)
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			createHole(world, x, y, z, strength, breakobj);
			// pushAndHurtEntities(world, x, y, z, strength);
			fixLag(world, x, y, z, strength);
		}
	}

	private void createHole(World world, int x, int y, int z, int radius, boolean breakobj)
	{
		Random rand = new Random();
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
				for (int Y = -onepointfiveradius; Y <= onepointfiveradius; Y++) {
					int YY = Y * Y + ZZ;
					int yy = y + Y;
                    BlockPos pos = new BlockPos(xx, yy, zz);
                    if (YY < onepointfiveradiussqrd) {
                        Block block = world.getBlockState(pos).getBlock();
						if (block != Blocks.air)
						{
							int dist = (int) Math.sqrt(YY);
							if (dist < radius && block != Blocks.bedrock)
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
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (Math.random() * 2); i++)
											world.setBlockToAir(pos);
									}
									else
									{
										world.setBlockToAir(pos);
										block = Blocks.air;
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
										for (int i = 0; i < ((1 - (dist / onepointfiveradius)) * 4) + (Math.random() * 2); i++)
											world.setBlockToAir(pos);
									}
									else if (block == Blocks.water || block == Blocks.lava || block == Blocks.flowing_water || block == Blocks.flowing_lava)
									{
										world.setBlockToAir(pos);
										block = Blocks.air;
									}
									else if (block == Blocks.stone && rand.nextInt(randomness) < randomness / 2)
									{
										world.setBlockState(pos, Blocks.cobblestone.getDefaultState());
										block = Blocks.cobblestone;
									}
									else if ((block == Blocks.grass || block == Blocks.dirt))
									{
										world.setBlockState(pos, RivalRebels.radioactivedirt.getDefaultState());
									}
									else if ((block == Blocks.sand || block == Blocks.sandstone))
									{
										world.setBlockState(pos, RivalRebels.radioactivesand.getDefaultState());
									}
									else if ((rand.nextInt(varrand) == 0 || rand.nextInt(varrand / 2 + 1) == 0))
									{
										world.setBlockToAir(pos);
										block = Blocks.air;
									}
								}
							}
							if (dist < onepointfiveradius && block != Blocks.air && block != Blocks.bedrock)
							{
								if (Y >= twoAOC || (dist < onepointfiveradius && Y >= AOC))
								{
									world.setBlockToAir(pos);
								}
								else if (world.getBlockState(pos.down()).getBlock() == Blocks.log)
								{
									world.setBlockState(pos, Blocks.fire.getDefaultState());
								}
								else if ((block == Blocks.grass || block == Blocks.dirt))
								{
									world.setBlockState(pos, RivalRebels.radioactivedirt.getDefaultState());
								}
								else if ((block == Blocks.sand || block == Blocks.sandstone))
								{
									world.setBlockState(pos, RivalRebels.radioactivesand.getDefaultState());
								}
							}
						}
					}
				}
			}
		}
		world.playSoundEffect(x, y, z, "random.explode", 4.0F, (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F);
	}

	private void pushAndHurtEntities(World world, int x, int y, int z, int radius)
	{
		radius *= 4;
		int var3 = MathHelper.floor_double(x - (double) radius - 1.0D);
		int var4 = MathHelper.floor_double(x + (double) radius + 1.0D);
		int var5 = MathHelper.floor_double(y - (double) radius - 1.0D);
		int var28 = MathHelper.floor_double(y + (double) radius + 1.0D);
		int var7 = MathHelper.floor_double(z - (double) radius - 1.0D);
		int var29 = MathHelper.floor_double(z + (double) radius + 1.0D);
		List<Entity> var9 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(var3, var5, var7, var4, var28, var29));
		Vec3 var30 = new Vec3(x, y, z);

		for (int var11 = 0; var11 < var9.size(); ++var11)
		{
			Entity var31 = var9.get(var11);
			double var13 = var31.getDistance(x, y, z) / radius;

			if (var13 <= 1.0D)
			{
				double var15 = var31.posX - x;
				double var17 = var31.posY + var31.getEyeHeight() - y;
				double var19 = var31.posZ - z;
				double var33 = MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);

				if (var33 != 0.0D)
				{
					var15 /= var33;
					var17 /= var33;
					var19 /= var33;
					double var32 = world.getBlockDensity(var30, var31.getEntityBoundingBox());
					double var34 = (1.0D - var13) * var32 * ((var31 instanceof EntityB83 || var31 instanceof EntityHackB83) ? -1 : 1);
					if (!(var31 instanceof EntityNuclearBlast) && !(var31 instanceof EntityTsarBlast) && !(var31 instanceof EntityRhodes))
					{
						if (var31 instanceof EntityFallingBlock) var31.setDead();
						var31.attackEntityFrom(RivalRebelsDamageSource.nuclearblast, (int) ((var34 * var34 + var34) / 2.0D * 8.0D * radius + 1.0D) * 4);
						var31.motionX -= var15 * var34 * 8;
						var31.motionY -= var17 * var34 * 8;
						var31.motionZ -= var19 * var34 * 8;
					}
					if (var31 instanceof EntityRhodes)
					{
						var31.attackEntityFrom(RivalRebelsDamageSource.nuclearblast, (int) (radius*var34*0.2f));
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
                    if (world.isAirBlock(pos) && world.getBlockLightValue(xx, yy, zz) == 0)
					{
						if (!world.isAirBlock(pos.up()) && !world.isAirBlock(pos.down()) && !world.isAirBlock(pos.east()) && !world.isAirBlock(pos.west()) && !world.isAirBlock(pos.south()) && !world.isAirBlock(pos.north()))
						{
							int r = world.rand.nextInt(50);
							Block id;
							if (r == 0) {
								id = prblocks[world.rand.nextInt(prblocks.length)];
							} else {
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

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
package assets.rivalrebels.common.entity;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityLaserBurst extends EntityInanimate {
	private LivingEntity shooter;

    public EntityLaserBurst(EntityType<? extends EntityLaserBurst> type, World world) {
        super(type, world);
    }

	public EntityLaserBurst(World par1World) {
		this(RREntities.LASER_BURST, par1World);
	}

	public EntityLaserBurst(World par1World, LivingEntity player)
	{
		this(par1World);
		shooter = player;
		refreshPositionAndAngles(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYaw(), player.getPitch());
        setPos(getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.2F),
            getY() - 0.12D,
            getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.2F));
		setPosition(getX(), getY(), getZ());
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));

        setAccurateHeading(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), 4F, 0.075f);
	}

	public EntityLaserBurst(World par1World, PlayerEntity player, boolean accurate)
	{
		this(par1World);
		shooter = player;
		refreshPositionAndAngles(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYaw(), player.getPitch());
        setPos(getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.2F),
            getY() - 0.12D,
            getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.2F));
		setPosition(getX(), getY(), getZ());
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));

        setAccurateHeading(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), 4F * (float)random.nextDouble() + 1.0F, accurate?0.001F:0.075F);
	}
	public EntityLaserBurst(World par1World, double x, double y,double z, double mx, double my, double mz) {
		this(par1World);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

	public EntityLaserBurst(World par1World, double x, double y, double z, double mx, double my, double mz, LivingEntity player)
	{
		this(par1World);
		shooter = player;
		setPosition(x, y, z);
        setVelocity(mx, my, mz);
	}

	public void setAccurateHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += random.nextGaussian() * par8;
		par3 += random.nextGaussian() * par8;
		par5 += random.nextGaussian() * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
        setVelocity(par1, par3, par5);
		float var10 = MathHelper.sqrt((float) (par1 * par1 + par5 * par5));
		setYaw(prevYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
	public void tick()
	{
		super.tick();

		++age;
		if (age > 60) kill();

		Vec3d var15 = getPos();
		Vec3d var2 = getPos().add(getVelocity());
		HitResult var3 = world.raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		var15 = getPos();
		var2 = getPos().add(getVelocity());

		if (var3 != null)
		{
			var2 = var3.getPos();
		}

		if (!world.isClient)
		{
			Entity var4 = null;
			List<Entity> var5 = world.getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.collides() && var9 != shooter) {
                    float var10 = 0.3F;
                    Box var11 = var9.getBoundingBox().expand(var10, var10, var10);
                    Optional<Vec3d> var12 = var11.raycast(var15, var2);

                    if (var12.isPresent()) {
                        double var13 = var15.distanceTo(var12.get());

                        if (var13 < var6 || var6 == 0.0D) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

			if (var4 != null)
			{
				var3 = new EntityHitResult(var4);
			}
		}

		if (var3 != null)
		{
			if (var3.getType() == HitResult.Type.BLOCK)
			{
                BlockPos pos = ((BlockHitResult) var3).getBlockPos();
                BlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
				if (block == Blocks.TNT) {
					if (!world.isClient) {
						TntEntity entitytntprimed = new TntEntity(world, (pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), shooter);
						entitytntprimed.setFuse(world.random.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
						world.spawnEntity(entitytntprimed);
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
					}
				} else if (block == RRBlocks.remotecharge) {
					state.onBlockExploded(world, pos, null);
				} else if (block == RRBlocks.timedbomb) {
					state.onBlockExploded(world, pos, null);
				}
				kill();
			}
			else
			{
                Entity hitEntity = ((EntityHitResult) var3).getEntity();
                if (hitEntity instanceof PlayerEntity player && shooter != hitEntity) {
                    DefaultedList<ItemStack> armorSlots = player.getInventory().armor;
					int i = world.random.nextInt(4);
					if (!armorSlots.get(i).isEmpty() && !world.isClient)
					{
						armorSlots.get(i).damage(24, player, player1 -> {});
					}
					player.damage(RivalRebelsDamageSource.laserburst, 16);
					if (player.getHealth() < 3 && player.isAlive())
					{
						player.damage(RivalRebelsDamageSource.laserburst, 2000000);
						player.deathTime = 0;
						world.spawnEntity(new EntityGore(world, hitEntity, 0, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 1, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 2, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 2, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 3, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 3, 0));
					}
					kill();
				}
				else if ((hitEntity instanceof LivingEntity entity
						&& !(hitEntity instanceof AnimalEntity)
						&& !(hitEntity instanceof BatEntity)
						&& !(hitEntity instanceof VillagerEntity)
						&& !(hitEntity instanceof SquidEntity))) {
					entity.damage(RivalRebelsDamageSource.laserburst, 6);
					if (entity.getHealth() < 3)
					{
						int legs = -1;
						int arms = -1;
						int mobs = -1;
						entity.kill();
						RivalRebelsSoundPlayer.playSound(this, 2, 1, 4);
						if (entity instanceof ZombieEntity && !(entity instanceof ZombifiedPiglinEntity))
						{
							legs = 2;
							arms = 2;
							mobs = 1;
						}
						else if (entity instanceof ZombifiedPiglinEntity)
						{
							legs = 2;
							arms = 2;
							mobs = 2;
						}
						else if (entity instanceof SkeletonEntity)
						{
							legs = 2;
							arms = 2;
							mobs = 3;
						}
						else if (entity instanceof EndermanEntity)
						{
							legs = 2;
							arms = 2;
							mobs = 4;
						}
						else if (entity instanceof CreeperEntity)
						{
							legs = 4;
							arms = 0;
							mobs = 5;
						}
                        else if (entity instanceof MagmaCubeEntity)
                        {
                            legs = 0;
                            arms = 0;
                            mobs = 7;
                        }
						else if (entity instanceof SlimeEntity)
						{
							legs = 0;
							arms = 0;
							mobs = 6;
						}
						else if (entity instanceof SpiderEntity && !(entity instanceof CaveSpiderEntity))
						{
							legs = 8;
							arms = 0;
							mobs = 8;
						}
						else if (entity instanceof CaveSpiderEntity)
						{
							legs = 8;
							arms = 0;
							mobs = 9;
						}
						else if (entity instanceof GhastEntity)
						{
							legs = 9;
							arms = 0;
							mobs = 10;
						}
						else
						{
							legs = (int) (entity.getBoundingBox().getAverageSideLength() * 2);
							arms = (int) (entity.getBoundingBox().getAverageSideLength() * 2);
							mobs = 11;
						}
						world.spawnEntity(new EntityGore(world, hitEntity, 0, mobs));
						world.spawnEntity(new EntityGore(world, hitEntity, 1, mobs));
						for (int i = 0; i < arms; i++)
							world.spawnEntity(new EntityGore(world, hitEntity, 2, mobs));
						for (int i = 0; i < legs; i++)
							world.spawnEntity(new EntityGore(world, hitEntity, 3, mobs));
					}
					kill();
				}
				else if((hitEntity instanceof EntityRhodesHead
					  || hitEntity instanceof EntityRhodesLeftLowerArm
				      || hitEntity instanceof EntityRhodesLeftLowerLeg
				      || hitEntity instanceof EntityRhodesLeftUpperArm
				      || hitEntity instanceof EntityRhodesLeftUpperLeg
				      || hitEntity instanceof EntityRhodesRightLowerArm
				      || hitEntity instanceof EntityRhodesRightLowerLeg
				      || hitEntity instanceof EntityRhodesRightUpperArm
				      || hitEntity instanceof EntityRhodesRightUpperLeg
				      || hitEntity instanceof EntityRhodesTorso))
				{
					hitEntity.damage(RivalRebelsDamageSource.laserburst, 6);
				}
			}
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		float var16 = MathHelper.sqrt((float) (getVelocity().getX() * getVelocity().getX() + getVelocity().getZ() * getVelocity().getZ()));
		setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));

		for (setPitch((float) (Math.atan2(getVelocity().getY(), var16) * 180.0D / Math.PI)); getPitch() - prevPitch < -180.0F; prevPitch -= 360.0F)
		{
        }
		while (getPitch() - prevPitch >= 180.0F)
		{
			prevPitch += 360.0F;
		}

		while (getYaw() - prevYaw < -180.0F)
		{
			prevYaw -= 360.0F;
		}

		while (getYaw() - prevYaw >= 180.0F)
		{
			prevYaw += 360.0F;
		}

		setPitch(prevPitch + (getPitch() - prevPitch) * 0.2F);
		setYaw(prevYaw + (getYaw() - prevYaw) * 0.2F);
		setPosition(getX(), getY(), getZ());
	}
}

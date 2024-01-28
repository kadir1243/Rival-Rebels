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
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Optional;

public class EntityRoddiskRep extends RoddiskBase {
    public EntityRoddiskRep(EntityType<? extends EntityRoddiskRep> type, World world) {
        super(type, world);
    }

	public EntityRoddiskRep(World par1World, PlayerEntity shooter, float par3) {
		super(RREntities.RODDISK_REP, par1World, shooter);
		this.refreshPositionAndAngles(shooter.getX(), shooter.getY() + shooter.getEyeHeight(shooter.getPose()), shooter.getZ(), shooter.getYaw(), shooter.getPitch());
        setPos(getX() - (MathHelper.cos(this.getYaw() / 180.0F * (float) Math.PI) * 0.16F),
            getY() - 0.1,
            getZ() - (MathHelper.sin(this.getYaw() / 180.0F * (float) Math.PI) * 0.16F)
        );
		this.setPosition(this.getX(), this.getY(), this.getZ());
		setVelocity((-MathHelper.sin(this.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(this.getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(this.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(this.getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(this.getPitch() / 180.0F * (float) Math.PI)));
		this.setHeading(this.getVelocity().getX(), this.getVelocity().getY(), this.getVelocity().getZ(), par3 * 1.5F, 1.0F);
	}

	public void setHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += this.random.nextGaussian() * 0.005 * par8;
		par3 += this.random.nextGaussian() * 0.005 * par8;
		par5 += this.random.nextGaussian() * 0.005 * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		setVelocity(par1, par3, par5);
		float var10 = MathHelper.sqrt((float) (par1 * par1 + par5 * par5));
		this.setYaw(prevYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
		this.setPitch(prevPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
	}

    @Override
	public void tick() {
		if (age > 100 && shooter == null && !world.isClient)
		{
			//world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(RivalRebels.roddisk)));
			kill();
			RivalRebelsSoundPlayer.playSound(this, 5, 0);
		}
		if (age >= 120 && !world.isClient && shooter != null)
		{
			ItemEntity ei = new ItemEntity(world, shooter.getX(), shooter.getY(), shooter.getZ(), new ItemStack(RRItems.roddisk));
			world.spawnEntity(ei);
			kill();
			RivalRebelsSoundPlayer.playSound(this, 6, 1);
		}
		if (age == 10)
		{
			RivalRebelsSoundPlayer.playSound(this, 6, 0);
		}
		if (!world.isClient)
		{
			double randx = world.random.nextGaussian();
			double randy = world.random.nextGaussian();
			double d = 1.0f/Math.sqrt(randx*randx+randy*randy);
			world.spawnEntity(new EntityLaserBurst(world, getX(), getY(), getZ(), randx*d, -Math.abs(getVelocity().getY()), randy*d, shooter));
		}

		int radius = 2;
		int nx = MathHelper.floor(getX() - radius - 1.0D);
		int px = MathHelper.floor(getX() + radius + 1.0D);
		int ny = MathHelper.floor(getY() - radius - 1.0D);
		int py = MathHelper.floor(getY() + radius + 1.0D);
		int nz = MathHelper.floor(getZ() - radius - 1.0D);
		int pz = MathHelper.floor(getZ() + radius + 1.0D);
		List<Entity> par9 = world.getOtherEntities(null, new Box(nx, ny, nz, px, py, pz));

        for (Entity var31 : par9) {
            if (var31 instanceof ArrowEntity) {
                var31.kill();
            }
        }

		Vec3d var15 = getPos();
		Vec3d var2 = getPos().add(getVelocity());
		HitResult var3 = this.world.raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		var15 = getPos();
		var2 = getPos().add(getVelocity());

		if (var3 != null)
		{
			var2 = var3.getPos();
		}

		if (!this.world.isClient)
		{
			Entity var4 = null;
			List<Entity> var5 = this.world.getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRoddiskRegular || var9 instanceof EntityRoddiskRebel || var9 instanceof EntityRoddiskLeader || var9 instanceof EntityRoddiskOfficer) {
                    var9.kill();
                    ItemEntity ei = new ItemEntity(world, var9.getX(), var9.getY(), var9.getZ(), new ItemStack(RRItems.roddisk));
                    world.spawnEntity(ei);
                } else if (var9.collides() && var9 != this.shooter) {
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
			world.addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);
			world.addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);
			world.addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);
			world.addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);

			if (var3.getType() == HitResult.Type.ENTITY)
			{
                Entity hitEntity = ((EntityHitResult) var3).getEntity();
                RivalRebelsSoundPlayer.playSound(this, 5, 1);
				if (hitEntity instanceof PlayerEntity entityPlayerHit && hitEntity != shooter)
				{
                    DefaultedList<ItemStack> armorSlots = entityPlayerHit.getInventory().armor;
					for (int i = 0; i < 4; i++)
					{
						if (!armorSlots.get(i).isEmpty())
						{
							armorSlots.get(i).damage(30, entityPlayerHit, player -> {});
							entityPlayerHit.damage(RivalRebelsDamageSource.tron, 1);
						}
						else
						{
							entityPlayerHit.damage(RivalRebelsDamageSource.tron, 15);
						}
					}
					if (entityPlayerHit.getHealth() < 3 && entityPlayerHit.isAlive())
					{
						entityPlayerHit.damage(RivalRebelsDamageSource.tron, 2000000);
						entityPlayerHit.deathTime = 0;
						world.spawnEntity(new EntityGore(world, hitEntity, 0, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 1, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 2, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 2, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 3, 0));
						world.spawnEntity(new EntityGore(world, hitEntity, 3, 0));
					}
				}
				else if ((hitEntity instanceof LivingEntity entity
						&& !(hitEntity instanceof AnimalEntity)
						&& !(hitEntity instanceof BatEntity)
						&& !(hitEntity instanceof VillagerEntity)
						&& !(hitEntity instanceof SquidEntity)))
				{
                    entity.damage(RivalRebelsDamageSource.tron, 40);
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
						else if (entity instanceof SlimeEntity && !(entity instanceof MagmaCubeEntity))
						{
							legs = 0;
							arms = 0;
							mobs = 6;
						}
						else if (entity instanceof MagmaCubeEntity)
						{
							legs = 0;
							arms = 0;
							mobs = 7;
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
					hitEntity.damage(RivalRebelsDamageSource.tron, 20);
				}
			}
			else {
                BlockPos pos = ((BlockHitResult) var3).getBlockPos();
                BlockState state = world.getBlockState(pos);
                if (state.isOf(RRBlocks.flare))
                {
                    state.getBlock().onBroken(world, pos, state);
                }
                else if (state.getBlock() == RRBlocks.landmine || state.getBlock() == RRBlocks.alandmine)
                {
                    state.onEntityCollision(world, pos, this);
                }
                else
                {
                    if (state.isIn(Tags.Blocks.GLASS) || state.isIn(Tags.Blocks.GLASS_PANES))
                    {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                    RivalRebelsSoundPlayer.playSound(this, 5, 2);

                    Direction side = ((BlockHitResult) var3).getSide();
                    if (side == Direction.WEST || side == Direction.EAST) this.setVelocity(getVelocity().multiply(-1, 1, 1));
                    if (side == Direction.DOWN || side == Direction.UP) this.setVelocity(getVelocity().multiply(1, -1, 1));
                    if (side == Direction.NORTH || side == Direction.SOUTH) this.setVelocity(getVelocity().multiply(1, 1, -1));
                }
            }
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		float var16 = MathHelper.sqrt((float) (this.getVelocity().getX() * this.getVelocity().getX() + this.getVelocity().getZ() * this.getVelocity().getZ()));
		this.setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));

		for (this.setPitch((float) (Math.atan2(getVelocity().getY(), var16) * 180.0D / Math.PI)); this.getPitch() - this.prevPitch < -180.0F; this.prevPitch -= 360.0F)
		{
        }

		while (this.getPitch() - this.prevPitch >= 180.0F)
		{
			this.prevPitch += 360.0F;
		}

		while (this.getYaw() - this.prevYaw < -180.0F)
		{
			this.prevYaw -= 360.0F;
		}

		while (this.getYaw() - this.prevYaw >= 180.0F)
		{
			this.prevYaw += 360.0F;
		}

		this.setPitch(this.prevPitch + (this.getPitch() - this.prevPitch) * 0.2F);
		this.setYaw(this.prevYaw + (this.getYaw() - this.prevYaw) * 0.2F);

		if (shooter != null)
		{
            setVelocity(getVelocity().add(
                (shooter.getX() - getX()) * 0.01f,
                ((shooter.getY() + 1.62) - getY()) * 0.01f,
                (shooter.getZ() - getZ()) * 0.01f
            ));
		}
        setVelocity(getVelocity().multiply(0.995f));

		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
		if (age < 10 || player != shooter) return ActionResult.PASS;
		if (player.getInventory().insertStack(RRItems.roddisk.getDefaultStack()))
		{
			kill();
			RivalRebelsSoundPlayer.playSound(this, 6, 1);
		}
		return ActionResult.success(world.isClient);
	}

}

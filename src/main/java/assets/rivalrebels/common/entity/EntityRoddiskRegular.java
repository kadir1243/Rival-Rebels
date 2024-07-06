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
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;

public class EntityRoddiskRegular extends RoddiskBase {
	public EntityRoddiskRegular(EntityType<? extends EntityRoddiskRegular> type, Level world) {
        super(type, world);
    }
    public EntityRoddiskRegular(Level par1World, LivingEntity shooter, float par3) {
		super(RREntities.RODDISK_REGULAR, par1World,  shooter);
		this.moveTo(shooter.getX(), shooter.getY() + shooter.getEyeHeight(shooter.getPose()), shooter.getZ(), shooter.getYRot(), shooter.getXRot());
		        setPosRaw(getX() - (Mth.cos(this.getYRot() / 180.0F * (float) Math.PI) * 0.16F),
            getY() - 0.1,
            getZ() - (Mth.sin(this.getYRot() / 180.0F * (float) Math.PI) * 0.16F)
        );

		this.setPos(this.getX(), this.getY(), this.getZ());
		setDeltaMovement((-Mth.sin(this.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(this.getXRot() / 180.0F * (float) Math.PI)),
            (Mth.cos(this.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(this.getXRot() / 180.0F * (float) Math.PI)),
            (-Mth.sin(this.getXRot() / 180.0F * (float) Math.PI)));
		this.setHeading(this.getDeltaMovement().x(), this.getDeltaMovement().y(), this.getDeltaMovement().z(), par3 * 1.5F, 1.0F);
	}

	public void setHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = Mth.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += this.random.nextGaussian() * 0.01 * par8;
		par3 += this.random.nextGaussian() * 0.01 * par8;
		par5 += this.random.nextGaussian() * 0.01 * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		setDeltaMovement(par1, par3, par5);
		float var10 = Mth.sqrt((float) (par1 * par1 + par5 * par5));
		this.setYRot(yRotO = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
		this.setXRot(xRotO = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
	}

    @Override
	public void tick() {
		if (tickCount > 100 && shooter == null && !level().isClientSide())
		{
			//world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(RivalRebels.roddisk)));
			kill();
            this.playSound(RRSounds.FORCE_FIELD);
		}
		if (tickCount >= 100 && !level().isClientSide && shooter != null)
		{
			ItemEntity ei = new ItemEntity(level(), shooter.getX(), shooter.getY(), shooter.getZ(), RRItems.roddisk.getDefaultInstance());
			level().addFreshEntity(ei);
			kill();
			RivalRebelsSoundPlayer.playSound(this, 7, 1);
		}
		if (tickCount == 10)
		{
			RivalRebelsSoundPlayer.playSound(this, 7, 0);
		}

		int radius = 2;
		int nx = Mth.floor(getX() - radius - 1.0D);
		int px = Mth.floor(getX() + radius + 1.0D);
		int ny = Mth.floor(getY() - radius - 1.0D);
		int py = Mth.floor(getY() + radius + 1.0D);
		int nz = Mth.floor(getZ() - radius - 1.0D);
		int pz = Mth.floor(getZ() + radius + 1.0D);
		List<Entity> par9 = level().getEntities(null, new AABB(nx, ny, nz, px, py, pz));

        for (Entity var31 : par9) {
            if (var31 instanceof Arrow) {
                var31.kill();
            }
        }

		Vec3 var15 = position();
		Vec3 var2 = position().add(getDeltaMovement());
		HitResult var3 = this.level().clip(new ClipContext(var15, var2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

		if (var3 != null)
		{
			var2 = var3.getLocation();
		}

		if (!this.level().isClientSide())
		{
			Entity var4 = null;
			List<Entity> var5 = this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRoddiskRegular) {
                    if (this.getDeltaMovement().x() + this.getDeltaMovement().y() + this.getDeltaMovement().z() >= var9.getDeltaMovement().x() + var9.getDeltaMovement().y() + var9.getDeltaMovement().z()) {
                        var9.kill();
                    } else {
                        kill();
                    }
                    ItemEntity ei = new ItemEntity(level(), var9.getX(), var9.getY(), var9.getZ(), RRItems.roddisk.getDefaultInstance());
                    level().addFreshEntity(ei);
                } else if (var9.canBeCollidedWith() && var9 != this.shooter) {
                    float var10 = 0.3F;
                    AABB var11 = var9.getBoundingBox().inflate(var10, var10, var10);
                    Optional<Vec3> var12 = var11.clip(var15, var2);

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
			level().addParticle(ParticleTypes.SMOKE, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);
			level().addParticle(ParticleTypes.SMOKE, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);
			level().addParticle(ParticleTypes.SMOKE, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);
			level().addParticle(ParticleTypes.SMOKE, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);

			if (var3.getType() == HitResult.Type.ENTITY)
			{
				this.playSound(RRSounds.ROD_DISK_HIT_ENTITY);
                Entity entity = ((EntityHitResult) var3).getEntity();
                if (entity instanceof Player && entity instanceof RoddiskBase)
				{
                }
				else
				{
					entity.hurt(RivalRebelsDamageSource.tron(level()), 5);
				}
			}
			else {
                BlockPos pos = ((BlockHitResult) var3).getBlockPos();
                Direction side = ((BlockHitResult) var3).getDirection();
                BlockState state = level().getBlockState(pos);
                if (state.is(RRBlocks.flare))
                {
                    state.getBlock().destroy(level(), pos, state);
                }
                else if (state.is(RRBlocks.landmine) || state.is(RRBlocks.alandmine))
                {
                    state.entityInside(level(), pos, this);
                }
                else
                {
                    if (state.is(ConventionalBlockTags.GLASS_BLOCKS) || state.is(ConventionalBlockTags.GLASS_PANES))
                    {
                        level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                    this.playSound(RRSounds.ROD_DISK_MIRROR_FROM_OBJECT);

                    if (side == Direction.WEST || side == Direction.EAST) this.setDeltaMovement(getDeltaMovement().multiply(-1, 1, 1));
                    if (side == Direction.DOWN || side == Direction.UP) this.setDeltaMovement(getDeltaMovement().multiply(1, -1, 1));
                    if (side == Direction.NORTH || side == Direction.SOUTH) this.setDeltaMovement(getDeltaMovement().multiply(1, 1, -1));
                }
            }
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var16 = Mth.sqrt((float) (this.getDeltaMovement().x() * this.getDeltaMovement().x() + this.getDeltaMovement().z() * this.getDeltaMovement().z()));
		this.setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));

		for (this.setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * 180.0D / Math.PI)); this.getXRot() - this.xRotO < -180.0F; this.xRotO -= 360.0F)
		{
        }

		while (this.getXRot() - this.xRotO >= 180.0F)
		{
			this.xRotO += 360.0F;
		}

		while (this.getYRot() - this.yRotO < -180.0F)
		{
			this.yRotO -= 360.0F;
		}

		while (this.getYRot() - this.yRotO >= 180.0F)
		{
			this.yRotO += 360.0F;
		}

		this.setXRot(this.xRotO + (this.getXRot() - this.xRotO) * 0.2F);
		this.setYRot(this.yRotO + (this.getYRot() - this.yRotO) * 0.2F);

		if (shooter != null)
		{
            setDeltaMovement(getDeltaMovement().add(
                (shooter.getX() - getX()) * 0.01f,
                ((shooter.getY() + 1.62) - getY()) * 0.01f,
                (shooter.getZ() - getZ()) * 0.01f
            ));
		}

        setDeltaMovement(getDeltaMovement().scale(0.98f));

		this.setPos(this.getX(), this.getY(), this.getZ());
	}

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
		if (tickCount < 10) return InteractionResult.PASS;
		if (player.getInventory().add(RRItems.roddisk.getDefaultInstance()))
		{
			kill();
			RivalRebelsSoundPlayer.playSound(this, 7, 1);
		}
		return InteractionResult.sidedSuccess(level().isClientSide());
	}

}

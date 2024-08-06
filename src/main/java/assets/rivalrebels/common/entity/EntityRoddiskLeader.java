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
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
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

public class EntityRoddiskLeader extends RoddiskBase {
    public EntityRoddiskLeader(EntityType<? extends EntityRoddiskLeader> type, Level par1World)
    {
        super(type, par1World);
    }

    public EntityRoddiskLeader(Level par1World)
	{
		this(RREntities.RODDISK_LEADER, par1World);
	}

	public EntityRoddiskLeader(Level par1World, LivingEntity shooter, float par3) {
		super(RREntities.RODDISK_LEADER, par1World, shooter);
		this.moveTo(shooter.getEyePosition(), shooter.getYRot(), shooter.getXRot());
        setPosRaw(getX() - (Mth.cos(this.getYRot() / 180.0F * Mth.PI) * 0.16F),
            getY() - 0.1,
            getZ() - (Mth.sin(this.getYRot() / 180.0F * Mth.PI) * 0.16F)
        );
		this.setPos(this.getX(), this.getY(), this.getZ());
        setDeltaMovement((-Mth.sin(this.getYRot() / 180.0F * Mth.PI) * Mth.cos(this.getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(this.getYRot() / 180.0F * Mth.PI) * Mth.cos(this.getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(this.getXRot() / 180.0F * Mth.PI)));
		this.shoot(this.getDeltaMovement().x(), this.getDeltaMovement().y(), this.getDeltaMovement().z(), par3 * 1.5F, 1.0F);
	}

    @Override
	public void tick() {
		if (tickCount > 100 && getOwner() == null && !level().isClientSide()) {
			//world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(RivalRebels.roddisk)));
			kill();
            this.playSound(RRSounds.FORCE_FIELD);
		}
		if (tickCount >= 120 && !level().isClientSide && getOwner() != null)
		{
			ItemEntity ei = new ItemEntity(level(), getOwner().getX(), getOwner().getY(), getOwner().getZ(), RRItems.roddisk.getDefaultInstance());
			level().addFreshEntity(ei);
			kill();
            this.playSound(RRSounds.RODDISK_UNKNOWN1);
		}
		if (tickCount == 10)
		{
            this.playSound(RRSounds.RODDISK_UNKNOWN0);
		}

		int radius = 2;
        AABB aabb = new AABB(getX(), getY(), getZ(), getX(), getY(), getZ()).inflate(radius + 1, -(radius + 1), radius + 1);
		List<Entity> par9 = level().getEntities(null, aabb);

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
                if (var9 instanceof EntityRoddiskRegular || var9 instanceof EntityRoddiskRebel) {
                    var9.kill();
                    ItemEntity ei = new ItemEntity(level(), var9.getX(), var9.getY(), var9.getZ(), RRItems.roddisk.getDefaultInstance());
                    level().addFreshEntity(ei);
                } else if (var9 instanceof EntityRoddiskOfficer) {
                    if (this.getDeltaMovement().x() + this.getDeltaMovement().y() + this.getDeltaMovement().z() >= var9.getDeltaMovement().x() + var9.getDeltaMovement().y() + var9.getDeltaMovement().z()) {
                        var9.kill();
                    } else {
                        kill();
                    }
                    ItemEntity ei = new ItemEntity(level(), var9.getX(), var9.getY(), var9.getZ(), RRItems.roddisk.getDefaultInstance());
                    level().addFreshEntity(ei);
                } else if (var9.canBeCollidedWith() && var9 != this.getOwner()) {
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
			level().addParticle(ParticleTypes.EXPLOSION, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);
			level().addParticle(ParticleTypes.EXPLOSION, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);
			level().addParticle(ParticleTypes.EXPLOSION, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);
			level().addParticle(ParticleTypes.EXPLOSION, var3.getLocation().x, var3.getLocation().y, var3.getLocation().z, getDeltaMovement().x() * 0.1, getDeltaMovement().y() * 0.1, getDeltaMovement().z() * 0.1);

			if (var3.getType() == HitResult.Type.ENTITY)
			{
                Entity entityHit = ((EntityHitResult) var3).getEntity();
                this.playSound(RRSounds.ROD_DISK_HIT_ENTITY);

				if (entityHit instanceof Player entityPlayerHit && getOwner() instanceof Player && entityHit != getOwner()) {
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        if (!slot.isArmor()) return;
                        ItemStack armorStack = entityPlayerHit.getItemBySlot(slot);
                        if (!armorStack.isEmpty()) {
							armorStack.hurtAndBreak(30, entityPlayerHit, slot);
							entityPlayerHit.hurt(RivalRebelsDamageSource.tron(level()), 1);
						} else {
							entityPlayerHit.hurt(RivalRebelsDamageSource.tron(level()), 15);
						}
					}
				}
				else
				{
					entityHit.hurt(RivalRebelsDamageSource.tron(level()), 15);
					if (entityHit instanceof Skeleton)
					{
						entityHit.kill();
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 0, 3));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 1, 3));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 2, 3));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 2, 3));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 3, 3));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 3, 3));
					}
					if (entityHit instanceof Zombie && !(entityHit instanceof ZombifiedPiglin))
					{
						entityHit.kill();
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 0, 1));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 1, 1));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 2, 1));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 2, 1));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 3, 1));
						this.level().addFreshEntity(new EntityGore(level(), entityHit, 3, 1));
					}
				}
			}
			else if (var3.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ((BlockHitResult) var3).getBlockPos();
                Direction side = ((BlockHitResult) var3).getDirection();
                BlockState state = level().getBlockState(pos);
                if (state.is(RRBlocks.flare)) {
                    state.getBlock().destroy(level(), pos, state);
                } else if (state.is(RRBlocks.landmine) || state.is(RRBlocks.alandmine)) {
                    state.entityInside(level(), pos, this);
                } else {
                    if (state.is(ModBlockTags.GLASS_BLOCKS) || state.is(ModBlockTags.GLASS_PANES))
                    {
                        level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                    this.playSound(RRSounds.ROD_DISK_MIRROR_FROM_OBJECT);

                    if (side == Direction.EAST || side == Direction.WEST) this.setDeltaMovement(getDeltaMovement().multiply(-1, 1, 1));
                    if (side == Direction.UP || side == Direction.DOWN) this.setDeltaMovement(getDeltaMovement().multiply(1, -1, 1));
                    if (side == Direction.NORTH || side == Direction.SOUTH) this.setDeltaMovement(getDeltaMovement().multiply(1, 1, -1));
                }
            }
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var16 = (float) this.getDeltaMovement().horizontalDistance();
		this.setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * Mth.RAD_TO_DEG));

		for (this.setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * Mth.RAD_TO_DEG)); this.getXRot() - this.xRotO < -180.0F; this.xRotO -= 360.0F)
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

		if (getOwner() != null)
		{
            setDeltaMovement(getDeltaMovement().add(
                (getOwner().getX() - getX()) * 0.01f,
                ((getOwner().getY() + 1.62) - getY()) * 0.01f,
                (getOwner().getZ() - getZ()) * 0.01f
            ));
		}
        setDeltaMovement(getDeltaMovement().scale(0.995f));

		this.setPos(this.getX(), this.getY(), this.getZ());
	}

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
		if (tickCount < 10 || player != getOwner()) return InteractionResult.PASS;
		if (player.getInventory().add(RRItems.roddisk.getDefaultInstance()))
		{
			kill();
            this.playSound(RRSounds.RODDISK_UNKNOWN1);
		}
		return InteractionResult.sidedSuccess(level().isClientSide());
	}
}

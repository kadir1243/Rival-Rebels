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
package io.github.kadir1243.rivalrebels.common.entity;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
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

public class EntityRoddiskRebel extends RoddiskBase {
    public EntityRoddiskRebel(EntityType<? extends EntityRoddiskRebel> type, Level world) {
        super(type, world);
    }

    public EntityRoddiskRebel(Level level) {
        this(RREntities.RODDISK_REBEL.get(), level);
    }

	public EntityRoddiskRebel(Level world, Entity shooter, float speed) {
		super(RREntities.RODDISK_REBEL.get(), world, shooter, speed);
	}

    @Override
	public void tick() {
		if (tickCount > 100 && getOwner() == null && !level().isClientSide())
		{
			//world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(RivalRebels.roddisk)));
			kill();
            this.playSound(RRSounds.FORCE_FIELD.get());
		}
		if (tickCount >= 100 && !level().isClientSide && getOwner() != null)
		{
			ItemEntity ei = new ItemEntity(level(), getOwner().getX(), getOwner().getY(), getOwner().getZ(), RRItems.roddisk.toStack());
			level().addFreshEntity(ei);
			kill();
            this.playSound(RRSounds.RODDISK_UNKNOWN5.get());
		}
		if (tickCount == 10)
		{
            this.playSound(RRSounds.RODDISK_UNKNOWN4.get());
		}

		int radius = 2;
        AABB aabb = new AABB(getX(), getY(), getZ(), getX(), getY(), getZ()).inflate(radius + 1, -(radius + 1), radius + 1);

        level().getEntities((Entity) null, aabb, entity -> entity instanceof Arrow).forEach(Entity::kill);

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
                    var9.kill();
                    ItemEntity ei = new ItemEntity(level(), var9.getX(), var9.getY(), var9.getZ(), RRItems.roddisk.toStack());
                    level().addFreshEntity(ei);
                } else if (var9 instanceof EntityRoddiskRebel) {
                    if (this.getDeltaMovement().x() + this.getDeltaMovement().y() + this.getDeltaMovement().z() >= var9.getDeltaMovement().x() + var9.getDeltaMovement().y() + var9.getDeltaMovement().z()) {
                        var9.kill();
                    } else {
                        kill();
                    }
                    ItemEntity ei = new ItemEntity(level(), var9.getX(), var9.getY(), var9.getZ(), RRItems.roddisk.toStack());
                    level().addFreshEntity(ei);
                } else if (var9.canBeCollidedWith() && !this.ownedBy(var9)) {
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

			if (var3.getType() == HitResult.Type.ENTITY) {
                Entity entityHit = ((EntityHitResult) var3).getEntity();
                this.playSound(RRSounds.ROD_DISK_HIT_ENTITY.get());
				if (entityHit instanceof Player entityPlayerHit && getOwner() instanceof Player && entityHit != getOwner())
				{
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        if (!slot.isArmor()) return;
                        ItemStack armorStack = entityPlayerHit.getItemBySlot(slot);
                        if (!armorStack.isEmpty()) {
                            armorStack.hurtAndBreak(10, entityPlayerHit, slot);
                            entityPlayerHit.hurt(RivalRebelsDamageSource.tron(level()), 1);
                        } else {
                            entityPlayerHit.hurt(RivalRebelsDamageSource.tron(level()), 10);
                        }
                    }
				}
				else
				{
					entityHit.hurt(RivalRebelsDamageSource.tron(level()), 10);
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
				}
			}
			else if (var3.getType() == HitResult.Type.BLOCK) {
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
                    if (state.is(ModBlockTags.GLASS_BLOCKS) || state.is(ModBlockTags.GLASS_PANES))
                    {
                        level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                    this.playSound(RRSounds.ROD_DISK_MIRROR_FROM_OBJECT.get());

                    if (side == Direction.EAST || side == Direction.WEST) this.setDeltaMovement(getDeltaMovement().multiply(-1, 1, 1));
                    if (side == Direction.UP || side == Direction.DOWN) this.setDeltaMovement(getDeltaMovement().multiply(1, -1, 1));
                    if (side == Direction.NORTH || side == Direction.SOUTH) this.setDeltaMovement(getDeltaMovement().multiply(1, 1, -1));
                }
            }
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
        this.updateRotation();

		if (getOwner() != null)
		{
            setDeltaMovement(getDeltaMovement().add(
                (getOwner().getX() - getX()) * 0.01f,
                ((getOwner().getY() + 1.62) - getY()) * 0.01f,
                (getOwner().getZ() - getZ()) * 0.01f
            ));
		}
        setDeltaMovement(getDeltaMovement().scale(0.995f));

        this.reapplyPosition();
	}

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
		if (tickCount < 10) return InteractionResult.PASS;
		if (player.getInventory().add(RRItems.roddisk.toStack()))
		{
			kill();
            this.playSound(RRSounds.RODDISK_UNKNOWN5.get());
		}
		return InteractionResult.sidedSuccess(level().isClientSide());
	}

}

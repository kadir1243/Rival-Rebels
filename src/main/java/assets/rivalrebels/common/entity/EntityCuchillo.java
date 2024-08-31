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

import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityCuchillo extends Projectile {
    private boolean	inGround;
	private int		ticksInGround;

    public EntityCuchillo(EntityType<? extends EntityCuchillo> type, Level level) {
        super(type, level);
    }

	public EntityCuchillo(Level level) {
		this(RREntities.CUCHILLO, level);
	}

	public EntityCuchillo(Level level, Entity player, float speed) {
		this(level);
        this.setOwner(player);
		moveTo(player.getEyePosition(), player.getYRot(), player.getXRot());
        setPos(getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
            getY() - 0.1f,
            getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F));
		setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)) * speed,
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)) * speed,
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)) * speed);
	}

    public EntityCuchillo(Level level, double x, double y,double z, double mx, double my, double mz) {
		this(level);
		setPos(x,y,z);
        setDeltaMovement(mx, my, mz);
        setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
        setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick()
	{
		super.tick();
		tickCount++;
		if (!inGround) {
			HitResult mop = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

			if (mop.getType() != HitResult.Type.MISS) {
				if (!level().isClientSide()) {
					if (mop.getType() == HitResult.Type.ENTITY) {
                        ((EntityHitResult) mop).getEntity().hurt(RivalRebelsDamageSource.cuchillo(level()), 7);
						kill();
					} else if (mop.getType() == HitResult.Type.BLOCK) {
                        BlockPos pos = ((BlockHitResult) mop).getBlockPos();
                        BlockState state = level().getBlockState(pos);
						if (state.is(ModBlockTags.GLASS_BLOCKS) || state.is(ModBlockTags.GLASS_PANES)) {
							level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            this.playSound(RRSounds.CUCHILLO_GLASS_BREAK, 5F, 0.3F);
                        } else if (state.is(BlockTags.BASE_STONE_OVERWORLD)) {
							level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.knife.getDefaultInstance()));
							kill();
						} else {
							inGround = true;
						}
					}
				}
			} else {
                setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
                this.updateRotation();

				float friction = 0.98f;

				if (isInWaterOrBubble()) {
					for (int var26 = 0; var26 < 4; ++var26)
						level().addParticle(ParticleTypes.BUBBLE, getX() - getDeltaMovement().x() * 0.25F, getY() - getDeltaMovement().y() * 0.25F, getZ() - getDeltaMovement().z() * 0.25F, getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z());
					friction = 0.8F;
				}
                setDeltaMovement(getDeltaMovement().scale(friction));
                applyGravity();
                reapplyPosition();
			}
		} else {
            setDeltaMovement(getDeltaMovement().scale(0.2));
			ticksInGround++;
			if (ticksInGround == 60) {
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.knife.getDefaultInstance()));
				kill();
			}
		}
	}

    @Override
    protected void updateRotation() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setXRot(getXRot() - 30);
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI)));
    }

    @Override
	public void playerTouch(Player player) {
		if (!level().isClientSide && inGround) {
			if (!player.hasInfiniteMaterials()) player.getInventory().add(RRItems.knife.getDefaultInstance());
			level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LAVA_POP, SoundSource.NEUTRAL, 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F, true);
			kill();
		}
	}

    @Override
    protected double getDefaultGravity() {
        return 0.026;
    }

    @Override
	public boolean isAttackable()
	{
		return false;
	}
}

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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityBomb extends ThrowableProjectile {
	public int	ticksInAir	= 0;
	public int timeleft = 20;
	public boolean exploded = false;
	public boolean hit = false;

	public EntityBomb(EntityType<? extends EntityBomb> type, Level level) {
		super(type, level);
	}

    public EntityBomb(Level level) {
        this(RREntities.BOMB, level);
    }

	public EntityBomb(Level level, double x, double y, double z, float yaw, float pitch) {
		this(level);
		moveTo(x, y, z, yaw, pitch);
		setDeltaMovement(-(-Mth.sin(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)),
            (-Mth.sin(pitch / 180.0F * Mth.PI)),
            (Mth.cos(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)));
	}

	public EntityBomb(Level level, double x, double y,double z, double mx, double my, double mz) {
		this(level);
		setPos(x+mx*1.4f,y+my*1.4f,z+mz*1.4f);
		setAnglesMotion(mx, my, mz);
	}

	public EntityBomb(Level level, Entity entity, float inaccuracy) {
		this(level);
        this.setOwner(entity);
		moveTo(entity.getEyePosition(), entity.getYRot(), entity.getXRot());
        setPos(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
        shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 2.5f, inaccuracy);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setDeltaMovement(mx, my, mz);
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
		if (ticksInAir == - 100) explode(false);
		++this.ticksInAir;

		if (exploded) {
            setDeltaMovement(0, hit ? 1 : 0, 0);
			timeleft--;
			if (timeleft < 0) kill();
			tickCount++;
		} else {
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

			if (hitResult.getType() != HitResult.Type.MISS) {
				this.onHit(hitResult);
			}

            setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
            this.updateRotation();
			float var17 = 0.95f;

            setDeltaMovement(getDeltaMovement().scale(var17));
            applyGravity();
		}
        this.reapplyPosition();
	}

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.hurt(RivalRebelsDamageSource.rocket(level()), (entity instanceof Player ? 20 : 300));
        explode(true);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        explode(false);
    }

    @Override
    protected double getDefaultGravity() {
		return 0.1F;
	}

	public void explode(boolean b)
	{
		exploded = true;
		hit = b;
		tickCount = 0;
		if (random.nextDouble() > 0.8f) RivalRebelsSoundPlayer.playSound(this, 23, 0, 20, 0.4f + (float)random.nextDouble() * 0.3f);
		if (!level().isClientSide && !b) {
			int r = 2;
			for (int x = -r; x <= r; x++) {
				for (int y = -r; y <= r; y++) {
					for (int z = -r; z <= r; z++) {
						level().setBlockAndUpdate(new BlockPos((int)(getX()+x), (int)(Math.max(getY(), r+1)+y), (int)(getZ()+z)), Blocks.AIR.defaultBlockState());
					}
				}
			}
		}
	}
}

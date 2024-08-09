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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.BlackList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class EntityPlasmoid extends Projectile {
    public int				rotation	= 45;
    public float			slide		= 0;

    public EntityPlasmoid(EntityType<? extends EntityPlasmoid> type, Level world) {
        super(type, world);
    }

	public EntityPlasmoid(Level level) {
		this(RREntities.PLASMOID, level);
        rotation = level.random.nextInt(360);
        slide = level.random.nextInt(21) - 10;
    }

	public EntityPlasmoid(Level par1World, double x, double y, double z) {
		this(par1World);
		setPos(x, y, z);
	}

    public EntityPlasmoid(Level par1World, Entity thrower, float speed, boolean drop)
	{
		this(par1World);
        setNoGravity(!drop);
        speed *= (isNoGravity() ? 1 : 3);
        this.setOwner(thrower);
		moveTo(
            thrower.getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
            thrower.getEyeY(),
            thrower.getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F),
            thrower.getYRot(),
            thrower.getXRot());
		shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0, speed * 1.5F, 1.0F);
	}

	public EntityPlasmoid(Level world, double px, double py, double pz, double x, double y, double z, float d) {
		this(world);
		double f = d/Math.sqrt(x*x+y*y+z*z);
		setPos(px+x*f, py+y*f, pz+z*f);
        setDeltaMovement(x, y, z);
		float var10 = Mth.sqrt((float) (x * x + z * z));
		setYRot(yRotO = (float) (Math.atan2(x, z) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(y, var10) * Mth.RAD_TO_DEG));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
		super.tick();
		applyGravity();
		++tickCount;
		rotation += (int) slide;
		slide *= 0.9F;
		if (tickCount >= RRConfig.SERVER.getPlasmoidDecay() * (isNoGravity() ? 1 : 3)) explode();

        HitResult mop = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

		if (mop.getType() != HitResult.Type.MISS) explode();

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
        this.updateRotation();
        reapplyPosition();
	}

    @Override
    protected double getDefaultGravity() {
        return 0.05;
    }

    protected void explode() {
		if (!level().isClientSide()) {
			kill();
			BlockState state = Blocks.STONE.defaultBlockState();
			int i = -1;
            while ((state.canOcclude() || BlackList.plasmaExplosion(state)) && i < 4) {
				++i;
				state = level().getBlockState(BlockPos.containing(position().subtract(getDeltaMovement().scale(i))));
			}
			level().setBlockAndUpdate(BlockPos.containing(position().subtract(getDeltaMovement().scale(i))), RRBlocks.plasmaexplosion.defaultBlockState());
		}
	}

}

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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.command.CommandHotPotato;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.TsarBomba;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityHotPotato extends ThrowableProjectile
{
	public int	age	= 0;
	public int round = 0;
	public int nextx = 0;
	public int nexty = 0;
	public int nextz = 0;
	public boolean dorounds = false;
	public int charges = RRConfig.SERVER.getTsarBombaStrength() + 9;

    public EntityHotPotato(EntityType<? extends EntityHotPotato> type, Level world) {
        super(type, world);
    }

	public EntityHotPotato(Level par1World)
	{
		this(RREntities.HOT_POTATO, par1World);
	}

	public EntityHotPotato(Level par1World, int x, int y, int z, int count)
	{
		this(par1World);
		setPos(x+0.5f, y+0.5f, z+0.5f);
		round = count;
		nextx = x;
		nexty = y;
		nextz = z;
		dorounds = true;
	}

	public EntityHotPotato(Level world, double px, double py, double pz, double f, double g, double h)
	{
		this(world);
		setPos(px, py, pz);
        setDeltaMovement(f, g, h);
		round = 1;
		nextx = (int)px;
		nexty = (int)py;
		nextz = (int)pz;
		tickCount = 1;
		dorounds = true;
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

	@Override
	public void tick()
	{
		if (tickCount == -100) explode();
		++this.tickCount;
		if (tickCount < 2 && dorounds)
		{
			RivalRebelsSoundPlayer.playSound(level(), 14, 0, getX(), getY(), getZ(), 100);
            setDeltaMovement(Vec3.ZERO);
			setPos(nextx+0.5f, nexty+0.5f, nextz+0.5f);
			level().setBlockAndUpdate(new BlockPos(nextx, nexty-400, nextz), RRBlocks.jump.defaultBlockState());
			setPos(getX(), getY(), getZ());
			return;
		}

		if (!level().isClientSide())
		{
			Vec3 var15 = position();
			Vec3 var2 = position().add(getDeltaMovement());
			HitResult var3 = this.level().clip(new ClipContext(var15, var2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

			if (var3 != null)
			{
				var2 = var3.getLocation();
			}
			Entity var4 = null;
			List<Entity> var5 = this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.canBeCollidedWith()) {
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

			if (var3 != null)
			{
				this.onHit(var3);
			}

			if (level().getBlockState(blockPosition()).getFluidState().is(FluidTags.WATER)) {
                setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() + 0.06F, getDeltaMovement().z());
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		if (getY() < level().getMinBuildHeight()) kill();

		if (this.isPassenger())
		{
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

		this.setXRot(this.xRotO + (this.getXRot() - this.xRotO) * 0.05F);
		this.setYRot(this.yRotO + (this.getYRot() - this.yRotO) * 0.05F);
		}
		float var17 = 0.98f;
		float var18 = (float) this.getGravity();

        setDeltaMovement(getDeltaMovement().scale(var17));
        setDeltaMovement(getDeltaMovement().subtract(0, var18, 0));
		this.setPos(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt)
	{
		nbt.putInt("charge", charges);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt)
	{
		charges = nbt.getInt("charge");
		if (charges == 0) charges = RRConfig.SERVER.getTsarBombaStrength() + 9;
		setYRot(yRotO = nbt.getFloat("rot"));
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
		if (state.is(RRBlocks.jump) || state.is(BlockTags.ICE)) {
			setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
			return;
		}
		if (level().random.nextInt(10)!=0) {
			setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
			return;
		}
		explode();
	}

    public void explode()
	{
		if (!level().isClientSide())
		{
			TsarBomba tsar = new TsarBomba((int)getX(), (int)getY(), (int)getZ(), level(), charges);
			EntityTsarBlast tsarblast = new EntityTsarBlast(level(), (int)getX(), (int)getY(), (int)getZ(), tsar, charges);
			level().addFreshEntity(tsarblast);
			tickCount = 0;
			round = round - 1;
			CommandHotPotato.roundinprogress = false;
			if (round <= 0) this.kill();
		}
	}
}

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
import assets.rivalrebels.common.explosion.AntimatterBomb;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;

public class EntityAntimatterBomb extends ThrowableProjectile {
	public int	ticksInAir	= 0;
	public int aoc = 0;
	public boolean hasTrollface;

	public EntityAntimatterBomb(EntityType<? extends EntityAntimatterBomb> entityType, Level world) {
		super(entityType, world);
	}

    public EntityAntimatterBomb(Level world) {
        this(RREntities.ANTIMATTER_BOMB, world);
    }

	public EntityAntimatterBomb(Level world, double x, double y, double z, float yaw, float pitch, int charges, boolean troll)
	{
		this(world);
		moveTo(x, y, z, yaw, pitch);
		yRotO = yaw;
		xRotO = pitch;
		aoc = charges;
		hasTrollface = troll;
		if (!RRConfig.SERVER.isNukedrop() && !world.isClientSide())
		{
			explode();
		}
	}

	public EntityAntimatterBomb(Level world, float px, float py, float pz, float f, float g, float h)
	{
		this(world);
		setPos(px, py, pz);
        setDeltaMovement(f, g, h);
		aoc = 5;
		hasTrollface = true;
	}
	public EntityAntimatterBomb(Level par1World, double x, double y,double z, double mx, double my, double mz, int charges)
	{
		this(par1World);
		setPos(x,y,z);
		aoc = charges;
		setAnglesMotion(mx, my, mz);
	}

    public void setAnglesMotion(double mx, double my, double mz) {
        setDeltaMovement(mx, my, mz);
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick()
	{
		if (!level().isClientSide())
		{
			if (ticksInAir == - 100) explode();
			++this.ticksInAir;

			Vec3 var15 = position();
			Vec3 var2 = position().add(getDeltaMovement());
			HitResult var3 = this.level().clip(new ClipContext(var15, var2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			var15 = position();
			var2 = position().add(getDeltaMovement());

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
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		if (getY() < 0) kill();

		if (this.isPassenger())
		{
		double var16 = Math.sqrt(this.getDeltaMovement().x() * this.getDeltaMovement().x() + this.getDeltaMovement().z() * this.getDeltaMovement().z());
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
		nbt.putInt("charge", aoc);
		nbt.putBoolean("troll", hasTrollface);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		aoc = nbt.getInt("charge");
		hasTrollface = nbt.getBoolean("troll");
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
        Block b = state.getBlock();
        MapColor color = state.getMapColor(level(), blockHitResult.getBlockPos());
        if (state.is(RRBlocks.jump) || state.is(BlockTags.ICE)) {
            setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
            return;
        }
        if (hasTrollface && level().random.nextInt(10)!=0)
        {
            setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
            return;
        }
        else if (!hasTrollface &&
            (state.is(BlockTags.LEAVES) ||
                color == MapColor.COLOR_GREEN ||
                color == MapColor.DIRT ||
                state.is(BlockTags.FLOWERS) ||
                state.is(BlockTags.CROPS) ||
                state.is(Blocks.CAKE) ||
                state.getBlock().getExplosionResistance() < 1 ||
                state.is(BlockTags.WOOL) ||
                state.is(BlockTags.SNOW) ||
                state.is(ConventionalBlockTags.GLASS_BLOCKS) ||
                state.is(BlockTags.SAND) ||
                state.is(Blocks.SNOW_BLOCK) ||
                state.ignitedByLava() ||
                state.canBeReplaced() ||
                state.getFluidState().is(FluidTags.WATER) ||
                state.is(Blocks.SPONGE) ||
                state.is(BlockTags.ICE))) {
            level().setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState());
            return;
        }
        explode();
	}

    public void explode()
	{
		if (!level().isClientSide())
		{
			AntimatterBomb tsar = new AntimatterBomb((int)getX(), (int)getY(), (int)getZ(), level(), (int) ((RRConfig.SERVER.getTsarBombaStrength() + (aoc * aoc)) * 0.8f));
			EntityAntimatterBombBlast tsarblast = new EntityAntimatterBombBlast(level(), (int)getX(), (int)getY(), (int)getZ(), tsar, RRConfig.SERVER.getTsarBombaStrength() + (aoc * aoc));
			level().addFreshEntity(tsarblast);
			this.kill();
		}
	}
}

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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityNuke extends ThrownEntity {
	public int	ticksInAir	= 0;
	public int aoc = 0;
	public boolean troll = false;

    public EntityNuke(EntityType<? extends EntityNuke> type, World world) {
        super(type, world);
    }

	public EntityNuke(World par1World) {
		this(RREntities.NUKE, par1World);
	}

	public EntityNuke(World par1World, double x, double y, double z, float yaw, float pitch, int charges, boolean troll)
	{
		this(par1World);
		refreshPositionAndAngles(x, y, z, yaw, pitch);
		this.setYaw(prevYaw = yaw);
		this.setPitch(prevPitch = pitch);
		aoc = charges;
		this.troll = troll;
		if (!RivalRebels.nukedrop)
		{
			explode();
		}
	}
	public EntityNuke(World par1World, double x, double y,double z, double mx, double my, double mz)
	{
		this(par1World);
		setPosition(x,y,z);
		aoc = 1;
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
    protected void initDataTracker() {
    }

	@Override
	public void tick() {
		if (!getWorld().isClient)
		{
			if (ticksInAir == - 100) explode();
			++this.ticksInAir;

			Vec3d var15 = getPos();
			Vec3d var2 = getPos().add(getVelocity());
			HitResult var3 = this.getWorld().raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
			var15 = getPos();
			var2 = getPos().add(getVelocity());

			if (var3 != null)
			{
				var2 = var3.getPos();
			}
			Entity var4 = null;
			List<Entity> var5 = this.getWorld().getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.isCollidable()) {
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

			if (var3 != null)
			{
				this.onCollision(var3);
			}
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		if (getY() < 0) kill();

		if (this.hasVehicle())
		{
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

		this.setPitch(this.prevPitch + (this.getPitch() - this.prevPitch) * 0.05F);
		this.setYaw(this.prevYaw + (this.getYaw() - this.prevYaw) * 0.05F);
		}
		float var17 = 0.98f;
		float var18 = this.getGravity();

        setVelocity(getVelocity().multiply(var17));
        setVelocity(getVelocity().subtract(0, var18, 0));
		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putInt("charge", aoc);
		nbt.putBoolean("troll", troll);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		aoc = nbt.getInt("charge");
		troll = nbt.getBoolean("troll");
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState state = getWorld().getBlockState(blockHitResult.getBlockPos());
        Block b = state.getBlock();
        MapColor color = state.getMapColor(getWorld(), blockHitResult.getBlockPos());
        if (b == RRBlocks.jump || state.isIn(BlockTags.ICE)) {
            setVelocity(getVelocity().getX(), Math.max(-getVelocity().getY(), 0.2F), getVelocity().getZ());
        } else if (state.isIn(BlockTags.LEAVES) || color == MapColor.GREEN || color == MapColor.DIRT_BROWN || state.isIn(BlockTags.FLOWERS) || state.isIn(BlockTags.CROPS) || state.isOf(Blocks.CAKE) || state.getBlock().getBlastResistance() < 1 || state.isIn(BlockTags.WOOL) || state.isOf(Blocks.SNOW_BLOCK) || state.isIn(ConventionalBlockTags.GLASS_BLOCKS) || state.isIn(BlockTags.SAND) || b instanceof SnowBlock || state.isBurnable() || state.isReplaceable() || state.getFluidState().isIn(FluidTags.WATER) || b instanceof SpongeBlock || state.isIn(BlockTags.ICE)) {
            getWorld().setBlockState(blockHitResult.getBlockPos(), Blocks.AIR.getDefaultState());
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
		explode();
	}

    public void explode()
	{
		if (!getWorld().isClient)
		{
			getWorld().spawnEntity(new EntityNuclearBlast(getWorld(), getX(), getY(), getZ(), aoc, troll));
			this.kill();
		}
	}
}

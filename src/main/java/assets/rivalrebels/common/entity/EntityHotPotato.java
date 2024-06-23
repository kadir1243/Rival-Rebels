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
import assets.rivalrebels.common.command.CommandHotPotato;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.TsarBomba;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityHotPotato extends ThrownEntity
{
	public int	age	= 0;
	public int round = 0;
	public int nextx = 0;
	public int nexty = 0;
	public int nextz = 0;
	public boolean dorounds = false;
	public int charges = RivalRebels.tsarBombaStrength + 9;

    public EntityHotPotato(EntityType<? extends EntityHotPotato> type, World world) {
        super(type, world);
    }

	public EntityHotPotato(World par1World)
	{
		this(RREntities.HOT_POTATO, par1World);
	}

	public EntityHotPotato(World par1World, int x, int y, int z, int count)
	{
		this(par1World);
		setPosition(x+0.5f, y+0.5f, z+0.5f);
		round = count;
		nextx = x;
		nexty = y;
		nextz = z;
		dorounds = true;
	}

	public EntityHotPotato(World world, double px, double py, double pz, double f, double g, double h)
	{
		this(world);
		setPosition(px, py, pz);
        setVelocity(f, g, h);
		round = 1;
		nextx = (int)px;
		nexty = (int)py;
		nextz = (int)pz;
		age = 1;
		dorounds = true;
	}

    @Override
    protected void initDataTracker() {
    }

	@Override
	public void tick()
	{
		if (age == -100) explode();
		++this.age;
		if (age < 2 && dorounds)
		{
			RivalRebelsSoundPlayer.playSound(getWorld(), 14, 0, getX(), getY(), getZ(), 100);
            setVelocity(Vec3d.ZERO);
			setPosition(nextx+0.5f, nexty+0.5f, nextz+0.5f);
			getWorld().setBlockState(new BlockPos(nextx, nexty-400, nextz), RRBlocks.jump.getDefaultState());
			setPosition(getX(), getY(), getZ());
			return;
		}

		if (!getWorld().isClient)
		{
			Vec3d var15 = getPos();
			Vec3d var2 = getPos().add(getVelocity());
			HitResult var3 = this.getWorld().raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

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

			if (getWorld().getBlockState(getBlockPos()).getFluidState().isIn(FluidTags.WATER)) {
                setVelocity(getVelocity().getX(), getVelocity().getY() + 0.06F, getVelocity().getZ());
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
	public void writeCustomDataToNbt(NbtCompound nbt)
	{
		nbt.putInt("charge", charges);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt)
	{
		charges = nbt.getInt("charge");
		if (charges == 0) charges = RivalRebels.tsarBombaStrength + 9;
		setYaw(prevYaw = nbt.getFloat("rot"));
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState state = getWorld().getBlockState(blockHitResult.getBlockPos());
		if (state.isOf(RRBlocks.jump) || state.isIn(BlockTags.ICE)) {
			setVelocity(getVelocity().getX(), Math.max(-getVelocity().getY(), 0.2F), getVelocity().getZ());
			return;
		}
		if (getWorld().random.nextInt(10)!=0) {
			setVelocity(getVelocity().getX(), Math.max(-getVelocity().getY(), 0.2F), getVelocity().getZ());
			return;
		}
		explode();
	}

    public void explode()
	{
		if (!getWorld().isClient)
		{
			TsarBomba tsar = new TsarBomba((int)getX(), (int)getY(), (int)getZ(), getWorld(), charges);
			EntityTsarBlast tsarblast = new EntityTsarBlast(getWorld(), (int)getX(), (int)getY(), (int)getZ(), tsar, charges);
			getWorld().spawnEntity(tsarblast);
			age = 0;
			round = round - 1;
			CommandHotPotato.roundinprogress = false;
			if (round <= 0) this.kill();
		}
	}
}

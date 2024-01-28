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
import assets.rivalrebels.common.explosion.AntimatterBomb;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Optional;

public class EntityAntimatterBomb extends ThrownEntity {
	public int	ticksInAir	= 0;
	public int aoc = 0;
	public boolean hasTrollface;

	public EntityAntimatterBomb(EntityType<? extends EntityAntimatterBomb> entityType, World world) {
		super(entityType, world);
	}

    public EntityAntimatterBomb(World world) {
        this(RREntities.ANTIMATTER_BOMB, world);
    }

	public EntityAntimatterBomb(World world, double x, double y, double z, float yaw, float pitch, int charges, boolean troll)
	{
		this(world);
		refreshPositionAndAngles(x, y, z, yaw, pitch);
		prevYaw = yaw;
		prevPitch = pitch;
		aoc = charges;
		hasTrollface = troll;
		if (!RivalRebels.nukedrop && !world.isClient)
		{
			explode();
		}
	}

	public EntityAntimatterBomb(World world, float px, float py, float pz, float f, float g, float h)
	{
		this(world);
		setPosition(px, py, pz);
        setVelocity(f, g, h);
		aoc = 5;
		hasTrollface = true;
	}
	public EntityAntimatterBomb(World par1World, double x, double y,double z, double mx, double my, double mz, int charges)
	{
		this(par1World);
		setPosition(x,y,z);
		aoc = charges;
		setAnglesMotion(mx, my, mz);
	}

    public void setAnglesMotion(double mx, double my, double mz) {
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
    protected void initDataTracker() {

    }

    @Override
	public void tick()
	{
		if (!world.isClient)
		{
			if (ticksInAir == - 100) explode();
			++this.ticksInAir;

			Vec3d var15 = getPos();
			Vec3d var2 = getPos().add(getVelocity());
			HitResult var3 = this.world.raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
			var15 = getPos();
			var2 = getPos().add(getVelocity());

			if (var3 != null)
			{
				var2 = var3.getPos();
			}
			Entity var4 = null;
			List<Entity> var5 = this.world.getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.collides()) {
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
		double var16 = Math.sqrt(this.getVelocity().getX() * this.getVelocity().getX() + this.getVelocity().getZ() * this.getVelocity().getZ());
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
		nbt.putInt("charge", aoc);
		nbt.putBoolean("troll", hasTrollface);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		aoc = nbt.getInt("charge");
		hasTrollface = nbt.getBoolean("troll");
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState state = world.getBlockState(blockHitResult.getBlockPos());
        Block b = state.getBlock();
        Material m = state.getMaterial();
        if (b == RRBlocks.jump || state.isIn(BlockTags.ICE)) {
            setVelocity(getVelocity().getX(), Math.max(-getVelocity().getY(), 0.2F), getVelocity().getZ());
            return;
        }
        if (hasTrollface && world.random.nextInt(10)!=0)
        {
            setVelocity(getVelocity().getX(), Math.max(-getVelocity().getY(), 0.2F), getVelocity().getZ());
            return;
        }
        else if (!hasTrollface && (state.isIn(BlockTags.LEAVES) || m == Material.ORGANIC_PRODUCT || m == Material.SOIL || state.isIn(BlockTags.FLOWERS) || state.isIn(BlockTags.CROPS) || m == Material.CAKE || m == Material.DECORATION || state.isIn(BlockTags.WOOL) || m == Material.SNOW_BLOCK || state.isIn(Tags.Blocks.GLASS) || m == Material.SOLID_ORGANIC || state.isIn(Tags.Blocks.SAND) || m == Material.SNOW_LAYER || m == Material.WOOD || m == Material.REPLACEABLE_PLANT || state.getFluidState().isIn(FluidTags.WATER) || m == Material.SPONGE || state.isIn(BlockTags.ICE)))
        {
            world.setBlockState(blockHitResult.getBlockPos(), Blocks.AIR.getDefaultState());
            return;
        }
        explode();
	}

    public void explode()
	{
		if (!world.isClient)
		{
			AntimatterBomb tsar = new AntimatterBomb((int)getX(), (int)getY(), (int)getZ(), world, (int) ((RivalRebels.tsarBombaStrength + (aoc * aoc)) * 0.8f));
			EntityAntimatterBombBlast tsarblast = new EntityAntimatterBombBlast(world, (int)getX(), (int)getY(), (int)getZ(), tsar, RivalRebels.tsarBombaStrength + (aoc * aoc));
			world.spawnEntity(tsarblast);
			this.kill();
		}
	}
}

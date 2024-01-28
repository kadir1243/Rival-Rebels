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
import assets.rivalrebels.common.core.BlackList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityPlasmoid extends EntityInanimate
{
	private Entity thrower;
	public int				rotation	= 45;
	public int				size		= 3;
	public float			slide		= 0;
	boolean gravity = false;

    public EntityPlasmoid(EntityType<? extends EntityPlasmoid> type, World world) {
        super(type, world);
    }

	public EntityPlasmoid(World par1World)
	{
		this(RREntities.PLASMOID, par1World);
		size = 3;
	}

	public EntityPlasmoid(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4, par6);
	}

	public EntityPlasmoid(World par1World, PlayerEntity thrower, MobEntity par3EntityLiving, float par4, float par5)
	{
		this(par1World);
		this.thrower = thrower;

        setPos(getX(), thrower.getY() + thrower.getEyeHeight(thrower.getPose()) - 0.10000000149011612D, getZ());
		double var6 = par3EntityLiving.getX() - thrower.getX();
		double var8 = par3EntityLiving.getY() + par3EntityLiving.getEyeHeight(par3EntityLiving.getPose()) - 0.699999988079071D - getY();
		double var10 = par3EntityLiving.getZ() - thrower.getZ();
		double var12 = Math.sqrt(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D)
		{
			float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			float var15 = (float) (-(Math.atan2(var8, var12) * 180.0D / Math.PI));
			double var16 = var6 / var12;
			double var18 = var10 / var12;
			refreshPositionAndAngles(thrower.getX() + var16, getY(), thrower.getZ() + var18, var14, var15);
			float var20 = (float) var12 * 0.2F;
			setAccurateHeading(new Vec3d(var6, var8 + var20, var10), par4, par5);
		}
	}

	public EntityPlasmoid(World par1World, Entity thrower, float par3, boolean drop)
	{
		this(par1World);
		par3 *= (gravity ? 3 : 1);
		gravity = drop;
		this.thrower = thrower;
		refreshPositionAndAngles(thrower.getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F), thrower.getY() + thrower.getEyeHeight(thrower.getPose()), thrower.getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F), thrower.getYaw(), thrower.getPitch());
		refreshPosition();
		setVelocity(-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI),
		 (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
		 (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));
		setAccurateHeading(getVelocity(), par3 * 1.5F, 1.0F);
	}

	public EntityPlasmoid(World world, double px, double py, double pz, double x, double y, double z, float d) {
		this(world);
		double f = d/Math.sqrt(x*x+y*y+z*z);
		setPosition(px+x*f, py+y*f, pz+z*f);
        setVelocity(x, y, z);
		float var10 = MathHelper.sqrt((float) (x * x + z * z));
		setYaw(prevYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(y, var10) * 180.0D / Math.PI));
	}

	public void setAccurateHeading(Vec3d vec, float speed, float par8) {
        vec = vec.multiply(1/vec.length()).multiply(speed);
        setVelocity(vec);
		float var10 = MathHelper.sqrt((float) (vec.x * vec.x + vec.z * vec.z));
		setYaw(prevYaw = (float) (Math.atan2(vec.x, vec.z) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(vec.y, var10) * 180.0D / Math.PI));
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

	@Override
	public void tick()
	{
		super.tick();
		if (age == 0)
		{
			rotation = world.random.nextInt(360);
			slide = world.random.nextInt(21) - 10;
		}
		if (gravity)
		{
            setVelocity(getVelocity().subtract(0, 0.05, 0));
		}
		++age;
		rotation += (int) slide;
		slide *= 0.9;
		if (age >= RivalRebels.plasmoidDecay * (gravity ? 3 : 1)) explode();

		Vec3d vec31 = getPos();
		Vec3d vec3 = getPos().add(getVelocity());
        HitResult mop = world.raycast(new RaycastContext(vec31, vec3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		vec31 = getPos();
		if (mop != null) vec3 = mop.getPos();
		else vec3 = getPos().add(getVelocity());

		List<Entity> list = world.getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
		double d0 = Double.MAX_VALUE;
        for (Entity entity : list) {
            if (entity.collides() && (entity != thrower || age >= 5)) {
                Optional<Vec3d> mop1 = entity.getBoundingBox().expand(0.5f, 0.5f, 0.5f).raycast(vec31, vec3);
                if (mop1.isPresent()) {
                    double d1 = vec31.squaredDistanceTo(mop1.get());
                    if (d1 < d0) {
                        mop = new EntityHitResult(entity, mop1.get());
                        d0 = d1;
                    }
                }
            }
        }
		if (mop != null) explode();

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		float var16 = MathHelper.sqrt((float) (getVelocity().getX() * getVelocity().getX() + getVelocity().getZ() * getVelocity().getZ()));
		setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));
		for (setPitch((float) (Math.atan2(getVelocity().getY(), var16) * 180.0D / Math.PI)); getPitch() - prevPitch < -180.0F; prevPitch -= 360.0F)
			;
		while (getPitch() - prevPitch >= 180.0F)
			prevPitch += 360.0F;
		while (getYaw() - prevYaw < -180.0F)
			prevYaw -= 360.0F;
		while (getYaw() - prevYaw >= 180.0F)
			prevYaw += 360.0F;
		setPitch(prevPitch + (getPitch() - prevPitch) * 0.2F);
		setYaw(prevYaw + (getYaw() - prevYaw) * 0.2F);
		setPosition(getX(), getY(), getZ());
	}

	protected void explode()
	{
		if (!world.isClient)
		{
			kill();
			BlockState state = Blocks.STONE.getDefaultState();
			int i = -1;
            Vec3d subtract = getPos().subtract(getVelocity().multiply(i));
            BlockPos pos = new BlockPos(subtract);
            while ((state.isOpaque() || BlackList.plasmaExplosion(state.getBlock())) && i < 4)
			{
				++i;
				state = world.getBlockState(pos);
			}
			world.setBlockState(pos, RRBlocks.plasmaexplosion.getDefaultState());
		}
	}

}

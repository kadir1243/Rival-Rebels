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
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPlasmoid extends EntityInanimate
{
	private Entity thrower;
	public int				rotation	= 45;
	public int				size		= 3;
	public float			slide		= 0;
	boolean gravity = false;

    public EntityPlasmoid(EntityType<? extends EntityPlasmoid> type, Level world) {
        super(type, world);
    }

	public EntityPlasmoid(Level par1World)
	{
		this(RREntities.PLASMOID, par1World);
		size = 3;
	}

	public EntityPlasmoid(Level par1World, double par2, double par4, double par6) {
		this(par1World);
		setPos(par2, par4, par6);
	}

	public EntityPlasmoid(Level par1World, Player thrower, Mob par3EntityLiving, float par4, float par5)
	{
		this(par1World);
		this.thrower = thrower;

        setPosRaw(getX(), thrower.getY() + thrower.getEyeHeight(thrower.getPose()) - 0.10000000149011612D, getZ());
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
			moveTo(thrower.getX() + var16, getY(), thrower.getZ() + var18, var14, var15);
			float var20 = (float) var12 * 0.2F;
			setAccurateHeading(new Vec3(var6, var8 + var20, var10), par4, par5);
		}
	}

	public EntityPlasmoid(Level par1World, Entity thrower, float par3, boolean drop)
	{
		this(par1World);
		par3 *= (gravity ? 3 : 1);
		gravity = drop;
		this.thrower = thrower;
		moveTo(thrower.getX() - (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * 0.16F), thrower.getY() + thrower.getEyeHeight(thrower.getPose()), thrower.getZ() - (Mth.sin(getYRot() / 180.0F * (float) Math.PI) * 0.16F), thrower.getYRot(), thrower.getXRot());
		reapplyPosition();
		setDeltaMovement(-Mth.sin(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI),
		 (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
		 (-Mth.sin(getXRot() / 180.0F * (float) Math.PI)));
		setAccurateHeading(getDeltaMovement(), par3 * 1.5F, 1.0F);
	}

	public EntityPlasmoid(Level world, double px, double py, double pz, double x, double y, double z, float d) {
		this(world);
		double f = d/Math.sqrt(x*x+y*y+z*z);
		setPos(px+x*f, py+y*f, pz+z*f);
        setDeltaMovement(x, y, z);
		float var10 = Mth.sqrt((float) (x * x + z * z));
		setYRot(yRotO = (float) (Math.atan2(x, z) * 180.0D / Math.PI));
		setXRot(xRotO = (float) (Math.atan2(y, var10) * 180.0D / Math.PI));
	}

	public void setAccurateHeading(Vec3 vec, float speed, float par8) {
        vec = vec.scale(1/vec.length()).scale(speed);
        setDeltaMovement(vec);
		float var10 = Mth.sqrt((float) (vec.x * vec.x + vec.z * vec.z));
		setYRot(yRotO = (float) (Math.atan2(vec.x, vec.z) * 180.0D / Math.PI));
		setXRot(xRotO = (float) (Math.atan2(vec.y, var10) * 180.0D / Math.PI));
	}

	@Override
	public float getLightLevelDependentMagicValue()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

	@Override
	public void tick()
	{
		super.tick();
		if (tickCount == 0)
		{
			rotation = level().random.nextInt(360);
			slide = level().random.nextInt(21) - 10;
		}
		if (gravity)
		{
            setDeltaMovement(getDeltaMovement().subtract(0, 0.05, 0));
		}
		++tickCount;
		rotation += (int) slide;
		slide *= 0.9;
		if (tickCount >= RRConfig.SERVER.getPlasmoidDecay() * (gravity ? 3 : 1)) explode();

		Vec3 vec31 = position();
		Vec3 vec3 = position().add(getDeltaMovement());
        HitResult mop = level().clip(new ClipContext(vec31, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		vec31 = position();
		if (mop != null) vec3 = mop.getLocation();
		else vec3 = position().add(getDeltaMovement());

		List<Entity> list = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
		double d0 = Double.MAX_VALUE;
        for (Entity entity : list) {
            if (entity.canBeCollidedWith() && (entity != thrower || tickCount >= 5)) {
                Optional<Vec3> mop1 = entity.getBoundingBox().inflate(0.5f, 0.5f, 0.5f).clip(vec31, vec3);
                if (mop1.isPresent()) {
                    double d1 = vec31.distanceToSqr(mop1.get());
                    if (d1 < d0) {
                        mop = new EntityHitResult(entity, mop1.get());
                        d0 = d1;
                    }
                }
            }
        }
		if (mop != null) explode();

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var16 = Mth.sqrt((float) (getDeltaMovement().x() * getDeltaMovement().x() + getDeltaMovement().z() * getDeltaMovement().z()));
		setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));
		for (setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * 180.0D / Math.PI)); getXRot() - xRotO < -180.0F; xRotO -= 360.0F)
			;
		while (getXRot() - xRotO >= 180.0F)
			xRotO += 360.0F;
		while (getYRot() - yRotO < -180.0F)
			yRotO -= 360.0F;
		while (getYRot() - yRotO >= 180.0F)
			yRotO += 360.0F;
		setXRot(xRotO + (getXRot() - xRotO) * 0.2F);
		setYRot(yRotO + (getYRot() - yRotO) * 0.2F);
		setPos(getX(), getY(), getZ());
	}

	protected void explode()
	{
		if (!level().isClientSide())
		{
			kill();
			BlockState state = Blocks.STONE.defaultBlockState();
			int i = -1;
            Vec3 subtract = position().subtract(getDeltaMovement().scale(i));
            BlockPos pos = BlockPos.containing(subtract);
            while ((state.canOcclude() || BlackList.plasmaExplosion(state)) && i < 4)
			{
				++i;
				state = level().getBlockState(pos);
			}
			level().setBlockAndUpdate(pos, RRBlocks.plasmaexplosion.defaultBlockState());
		}
	}

}

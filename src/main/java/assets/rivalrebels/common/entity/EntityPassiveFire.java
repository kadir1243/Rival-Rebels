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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityPassiveFire extends EntityInanimate {
    private boolean	inGround;

	public Entity	shootingEntity;
	private int		ticksInAir;
	private double	damage;

    public EntityPassiveFire(EntityType<? extends EntityPassiveFire> type, World world) {
        super(type, world);
    }

	public EntityPassiveFire(World par1World) {
		this(RREntities.PASSIVE_FIRE, par1World);
        inGround = false;
		ticksInAir = 0;
		damage = 2D;
	}

	public EntityPassiveFire(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4, par6);
	}

	public EntityPassiveFire(World par1World, MobEntity shootingEntity, MobEntity par3EntityLiving, float par4, float par5) {
		this(par1World);
		this.shootingEntity = shootingEntity;
        setPos(getX(), (shootingEntity.getY() + shootingEntity.getEyeHeight(shootingEntity.getPose())) - 0.1D, getZ());
		double d = par3EntityLiving.getX() - shootingEntity.getX();
		double d1 = (par3EntityLiving.getY() + par3EntityLiving.getEyeHeight(par3EntityLiving.getPose())) - 0.7D - getY();
		double d2 = par3EntityLiving.getZ() - shootingEntity.getZ();
		double d3 = Math.sqrt(d * d + d2 * d2);

		if (d3 < 9.9999999999999995E-008D) {
        } else {
			float f = (float) ((Math.atan2(d2, d) * 180D) / Math.PI) - 90F;
			float f1 = (float) (-((Math.atan2(d1, d3) * 180D) / Math.PI));
			double d4 = d / d3;
			double d5 = d2 / d3;
			refreshPositionAndAngles(shootingEntity.getX() + d4, getY(), shootingEntity.getZ() + d5, f, f1);
			float f2 = (float) d3 * 0.2F;
			setVelocity(d, d1 + f2, d2);
        }
	}

	public EntityPassiveFire(World par1World, Entity entity, float par3) {
		this(par1World);
		shootingEntity = entity;
		refreshPositionAndAngles(entity.getX(), entity.getY() + entity.getEyeHeight(entity.getPose()), entity.getZ(), entity.getYaw(), entity.getPitch());
		setYaw((getYaw() + 25) % 360);
        setPos(
            getX() - MathHelper.cos((getYaw() / 180F) * (float) Math.PI) * 0.16F,
            getY() - 0.2D,
            getZ() - MathHelper.sin((getYaw() / 180F) * (float) Math.PI) * 0.16F
        );
		setPosition(getX(), getY(), getZ());
        super.setVelocity(-MathHelper.sin((getYaw() / 180F) * (float) Math.PI) * MathHelper.cos((getPitch() / 180F) * (float) Math.PI),
		 MathHelper.cos((getYaw() / 180F) * (float) Math.PI) * MathHelper.cos((getPitch() / 180F) * (float) Math.PI),
		 -MathHelper.sin((getPitch() / 180F) * (float) Math.PI));
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return (distance <= 16);
	}

	public EntityPassiveFire(World world, int x, int y, int z, int mx, int my, int mz)
	{
		this(world);
		setPosition(x, y, z);
        super.setVelocity(mx, my, mz);
	}

    @Override
	public void setVelocity(double x, double y, double z) {
        super.setVelocity(
            x + (world.random.nextFloat() - 0.5) / 50,
            y + (world.random.nextFloat() - 0.5) / 50,
            z + (world.random.nextFloat() - 0.5) / 50);
	}

	@Override
	public void tick() {
		super.tick();

		if (ticksInAir > 7)
		{
			this.kill();
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		float var17 = 0.4F;
		float var18 = -0.02F;

		if (this.isInsideWaterOrBubbleColumn())
		{
			kill();
		}

        setVelocity(getVelocity().multiply(var17));
        setVelocity(getVelocity().subtract(0, var18, 0));
		this.setPosition(this.getX(), this.getY(), this.getZ());
		ticksInAir++;
	}

    @Override
	public void writeCustomDataToNbt(NbtCompound nbt)
	{
		nbt.putBoolean("inGround", inGround);
		nbt.putDouble("damage", damage);
	}

    @Override
	public void readCustomDataFromNbt(NbtCompound nbt)
	{
		inGround = nbt.getBoolean("inGround");
        damage = nbt.getDouble("damage");
	}

	@Override
	public boolean isAttackable()
	{
		return false;
	}
}

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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Optional;

public class EntityRocket extends PersistentProjectileEntity
{
	private LivingEntity	thrower;
	public boolean				fins			= false;
	public int					rotation		= 45;
	public float				slide			= 0;
	private boolean				inwaterprevtick	= false;
	private int					soundfile		= 0;

	public EntityRocket(EntityType<? extends EntityRocket> type, World par1World) {
		super(type, par1World);
	}

    public EntityRocket(World world) {
        this(RREntities.ROCKET, world);
    }

	public EntityRocket(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4, par6);
	}

	public EntityRocket(World par1World, PlayerEntity entity2, float par3) {
		this(par1World);
		thrower = entity2;
		fins = false;
		refreshPositionAndAngles(entity2.getX(), entity2.getY() + entity2.getEyeHeight(entity2.getPose()), entity2.getZ(), entity2.getYaw(), entity2.getPitch());
        setPos(
            getX() - MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F,
            getY(),
            getZ() - MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F
        );
		setPosition(getX(), getY(), getZ());
		setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
		(MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
		(-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));
        setVelocity(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), 0.5f, 0.1f);
	}

	public EntityRocket(World par1World, double x, double y,double z, double mx, double my, double mz) {
		this(par1World);
		fins = false;
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
	public void setVelocity(double mx, double my, double mz, float speed, float randomness)
	{
		float f2 = MathHelper.sqrt((float) (mx * mx + my * my + mz * mz));
		mx /= f2;
		my /= f2;
		mz /= f2;
		mx += random.nextGaussian() * 0.0075 * randomness;
		my += random.nextGaussian() * 0.0075 * randomness;
		mz += random.nextGaussian() * 0.0075 * randomness;
		mx *= speed;
		my *= speed;
		mz *= speed;
		setAnglesMotion(mx, my, mz);
	}

    @Override
	public void tick()
	{
		super.tick();

		if (age == 0)
		{
			rotation = world.random.nextInt(360);
			slide = world.random.nextInt(21) - 10;
			for (int i = 0; i < 10; i++)
			{
				world.addParticle(ParticleTypes.EXPLOSION, getX() - getVelocity().getX() * 2, getY() - getVelocity().getY() * 2, getZ() - getVelocity().getZ() * 2, -getVelocity().getX() + (world.random.nextFloat() - 0.5f) * 0.1f, -getVelocity().getY() + (world.random.nextFloat() - 0.5) * 0.1f, -getVelocity().getZ() + (world.random.nextFloat() - 0.5f) * 0.1f);
			}
		}
		rotation += (int) slide;
		slide *= 0.9;

		if (age >= RivalRebels.rpgDecay)
		{
			explode(null);
		}
		// world.spawnEntity(new EntityLightningLink(world, getX(), getY(), getZ(), yaw, pitch, 100));

		if (world.isClient && age >= 5 && !isInsideWaterOrBubbleColumn() && age <= 100)
		{
			world.spawnEntity(new EntityPropulsionFX(world, getX(), getY(), getZ(), -getVelocity().getX() * 0.5, -getVelocity().getY() * 0.5 - 0.1, -getVelocity().getZ() * 0.5));
		}
		Vec3d vec31 = getPos();
		Vec3d vec3 = getPos().add(getVelocity());
		HitResult mop = world.raycast(new RaycastContext(vec31, vec3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		if (!world.isClient)
		{
			vec31 = getPos();
			if (mop != null) vec3 = mop.getPos();
			else vec3 = getPos().add(getVelocity());

			List<Entity> list = world.getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
            for (Entity entity : list) {
                if (entity.collides() && age >= 7 && entity != thrower) {
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
		}
		if (mop != null) explode(mop);
        setPos(getX() + getVelocity().getY(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
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
		float var17 = 1.1f;
		if (age > 25) var17 = 0.9999F;

		if (isInsideWaterOrBubbleColumn())
		{
			for (int var7 = 0; var7 < 4; ++var7)
			{
				world.addParticle(ParticleTypes.BUBBLE, getX() - getVelocity().getX() * 0.25F, getY() - getVelocity().getY() * 0.25F, getZ() - getVelocity().getZ() * 0.25F, getVelocity().getX(), getVelocity().getY(), getVelocity().getZ());
			}
			if (!inwaterprevtick)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 4, 0.5F, 0.5F);
			}
			soundfile = 3;
			var17 = 0.8F;
			inwaterprevtick = true;
		}
		else
		{
			soundfile = 0;
		}

        setVelocity(getVelocity().multiply(var17));
		if (age == 3)
		{
			fins = true;
            setPitch(getPitch() + 22.5F);
		}
		setPosition(getX(), getY(), getZ());
		++age;
	}

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    public void explode(HitResult mop)
	{
		if (mop != null && mop.getType() == HitResult.Type.ENTITY && ((EntityHitResult) mop).getEntity() instanceof PlayerEntity player) {
            player.damage(RivalRebelsDamageSource.rocket, 48);
		} else if (mop != null && mop.getType() == HitResult.Type.BLOCK) {
            BlockState state = world.getBlockState(((BlockHitResult) mop).getBlockPos());
			if (state.isIn(Tags.Blocks.GLASS) || state.isIn(Tags.Blocks.GLASS_PANES) || state.isIn(Tags.Blocks.STAINED_GLASS) || state.isIn(Tags.Blocks.STAINED_GLASS_PANES)) {
				world.setBlockState(((BlockHitResult) mop).getBlockPos(), Blocks.AIR.getDefaultState());
				RivalRebelsSoundPlayer.playSound(this, 4, 0, 5F, 0.3F);
				return;
			}
		}
		RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
		new Explosion(world, getX(), getY(), getZ(), RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
		kill();
	}

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

}

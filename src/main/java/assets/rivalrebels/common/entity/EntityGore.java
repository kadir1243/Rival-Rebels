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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class EntityGore extends EntityInanimate {
    public static final TrackedData<Integer> MOB = DataTracker.registerData(EntityGore.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> TYPE_OF_GORE = DataTracker.registerData(EntityGore.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Float> SIZE = DataTracker.registerData(EntityGore.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Boolean> IS_GREEN = DataTracker.registerData(EntityGore.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(EntityGore.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected boolean		inGround;
	public Entity			origin;
    private boolean			isSliding	= false;
    private int				slideCount	= 0;
	float					x			= 0;
	float					y			= 0;
	float					z			= 0;
	float					rotYaw		= 0;
	float					rotPitch	= 0;
	float					motionyaw	= 0;
	float					motionpitch	= 0;
	int						pitchLock	= 0;
	float					offset		= 0;
    // @Environment(EnvType.CLIENT)
	public Identifier	playerSkin	= null;
	private int				bounces		= -1;

    public EntityGore(EntityType<? extends EntityGore> type, World world) {
        super(type, world);
    }

	public EntityGore(World par1World)
	{
		this(RREntities.GORE, par1World);
	}
	public EntityGore(World par1World, double x, double y,double z, double mx, double my, double mz, int Type, int Mob)
	{
		this(par1World);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
		setTypeOfGore(Type);
		setMob(Mob);
	}

    public void setMob(int mob) {
        dataTracker.set(MOB, mob);
    }

    public int getMob() {
        return dataTracker.get(MOB);
    }

    public boolean isGreen() {
        return dataTracker.get(IS_GREEN);
    }

    public void setGreen(boolean green) {
        dataTracker.set(IS_GREEN, green);
    }

    public void setAnglesMotion(double mx, double my, double mz)
	{
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

	public EntityGore(World par1World, Entity toBeGibbed, int Type, int mob)
	{
		this(par1World);
		origin = toBeGibbed;
		isSliding = false;
		slideCount = 0;
		bounces = -1;
		pitchLock = 0;
		setTypeOfGore(Type);
		setGreen(false);
		setMob(mob);
		if (mob == 0)
		{
            setOwner(origin instanceof PlayerEntity player ? player.getGameProfile().getId() : null);
			setBiped(0);
		}
		else if (mob == 1)
		{
			setBiped(0);
		}
		else if (mob == 2)
		{
			setBiped(0);
		}
		else if (mob == 3)
		{
			setBiped(1);
			setGreen(true);
		}
		else if (mob == 4)
		{
			setBiped(2);
		}
		else if (mob == 5)
		{
			setQuadruped();
			setGreen(true);
		}
		else if (mob == 6)
		{
			setNoped();
			setGreen(true);
		}
		else if (mob == 7)
		{
			setNoped();
		}
		else if (mob == 8)
		{
			setOctoped(0);
		}
		else if (mob == 9)
		{
			setOctoped(1);
			setGreen(true);
		}
		else if (mob == 10)
		{
			setOctoped(2);
		}
		else if (mob == 11)
		{
			setSize((float) origin.getBoundingBox().getAverageSideLength());
			if (getSize() > 2 || getSize() < 1) setGreen(true);
			setDefault();
		}

		motionyaw = (float) ((random.nextDouble() - 0.5) * 135);
		motionpitch = (float) ((random.nextDouble() - 0.5) * 135);

		refreshPositionAndAngles(toBeGibbed.getX() + x, toBeGibbed.getY() + y, toBeGibbed.getZ() + z, rotYaw, rotPitch);
		setPosition(getX(), getY(), getZ());
		shoot(0.3f);
	}

	private void setNoped()
	{
		if (getTypeOfGore() == 0) {
			y = origin.getEyeHeight(origin.getPose());
			offset = 0.5f;
		} else if (getTypeOfGore() == 1) {
			y = origin.getEyeHeight(origin.getPose());
			offset = 0.25F;
		}
		rotYaw = origin.getYaw();
	}

    public int getTypeOfGore() {
        return dataTracker.get(TYPE_OF_GORE);
    }

    public void setTypeOfGore(int type) {
        dataTracker.set(TYPE_OF_GORE, type);
    }

    private void setBiped(int thin)
	{
		if (getTypeOfGore() == 0) {
			y = origin.getEyeHeight(origin.getPose()) + 0.20f;
			offset = 0.25f;
			rotYaw = origin.getYaw();
			rotPitch = origin.getPitch();
		} else if (getTypeOfGore() == 1) {
			if (thin == 2) y = 2f;
			else y = 1.1f;
			offset = 0.125F;
			rotYaw = origin.getYaw();
		} else if (getTypeOfGore() == 2) {
			x = (float) random.nextDouble() - 0.5f;
			if (thin == 2) y = 1.6f;
			else y = 1.1f;
			z = (float) random.nextDouble() - 0.5f;
			if (thin == 0) offset = 0.125F;
			else offset = 0.0625F;
			rotYaw = origin.getYaw();
		} else if (getTypeOfGore() == 3) {
			x = (float) random.nextDouble() - 0.5f;
			if (thin == 2) y = 0.95f;
			else y = 0.35f;
			z = (float) random.nextDouble() - 0.5f;
			if (thin == 0) offset = 0.125F;
			else offset = 0.0625F;
			rotYaw = origin.getYaw();
		}
	}

	private void setQuadruped()
	{
		if (getTypeOfGore() == 0) {
			y = origin.getEyeHeight(origin.getPose());
			offset = 0.25f;
			rotYaw = origin.getYaw();
			rotPitch = origin.getPitch();
		} else if (getTypeOfGore() == 1) {
			y = 0.875f;
			offset = 0.125F;
			rotYaw = origin.getYaw();
		} else if (getTypeOfGore() == 3) {
			x = (float) (random.nextDouble() - 0.5);
			y = 0.25f;
			z = (float) (random.nextDouble() - 0.5);
			offset = 0.125F;
			rotYaw = origin.getYaw();
		}
	}

	private void setOctoped(int mode)
	{
		if (mode != 2)
		{
			float scale = 1f;
			if (mode == 1) scale = 0.666f;
			if (getTypeOfGore() == 0)
			{
				y = origin.getEyeHeight(origin.getPose());
				offset = 0.25f * scale;
				rotYaw = origin.getYaw();
				rotPitch = origin.getPitch();
			} else if (getTypeOfGore() == 1) {
				x = (float) (random.nextDouble() - 0.5f) * scale;
				y = 0.25f * scale;
				z = (float) (random.nextDouble() - 0.5f) * scale;
				offset = 0.1F * scale;
				rotYaw = origin.getYaw();
			} else if (getTypeOfGore() == 3) {
				x = (float) (random.nextDouble() - 0.5f) * scale;
				y = 0.25f * scale;
				z = (float) (random.nextDouble() - 0.5f) * scale;
				offset = 0.0625F * scale;
				rotYaw = (float) (random.nextDouble() * 360);
				rotPitch = 25;
			}
		}
		else
		{
			if (getTypeOfGore() == 0) {
				y = origin.getEyeHeight(origin.getPose());
				offset = 1.5f;
				rotYaw = origin.getYaw();
				rotPitch = origin.getPitch();
			} else if (getTypeOfGore() == 3) {
				x = (float) (random.nextDouble() - 0.5f) * 4f;
				z = (float) (random.nextDouble() - 0.5f) * 4f;
				offset = 0.125F;
			}
		}
	}

	private void setDefault()
	{
        if (getTypeOfGore() == 0) {
            y = origin.getEyeHeight(origin.getPose()) + 0.20f;
            offset = 0.25f;
            rotYaw = origin.getYaw();
            rotPitch = origin.getPitch();
        } else if (getTypeOfGore() == 1) {
            y = origin.getEyeHeight(origin.getPose()) / 2;
            offset = 0.125F;
            rotYaw = origin.getYaw();
        } else if (getTypeOfGore() == 2) {
            x = (float) ((random.nextDouble() - 0.5f) * getSize());
            y = origin.getEyeHeight(origin.getPose()) / 2;
            z = (float) ((random.nextDouble() - 0.5f) * getSize());
            offset = 0.0625F;
            rotYaw = origin.getYaw();
        } else if (getTypeOfGore() == 3) {
            x = (float) ((random.nextDouble() - 0.5f) * getSize());
            y = origin.getEyeHeight(origin.getPose()) / 4;
            z = (float) ((random.nextDouble() - 0.5f) * getSize());
            offset = 0.0625F;
            rotYaw = origin.getYaw();
        }
	}

    public float getSize() {
        return dataTracker.get(SIZE);
    }

    public void setSize(float size) {
        dataTracker.set(SIZE, size);
    }

    public void shoot(float par7)
	{
		setVelocity(random.nextGaussian() * par7,
            Math.abs(random.nextGaussian() * par7),
            random.nextGaussian() * par7);
	}

    @Nullable
    public UUID getOwner() {
        return dataTracker.get(OWNER).orElse(null);
    }

    public void setOwner(@Nullable UUID owner) {
        dataTracker.set(OWNER, Optional.ofNullable(owner));
    }

	@Override
	public void tick()
	{
		if (playerSkin == null && getWorld().isClient && getOwner() != null) {
            for (PlayerEntity player : getWorld().getPlayers()) {
                if (player.getGameProfile().getId().equals(getOwner())) {
                    AbstractClientPlayerEntity acp = (AbstractClientPlayerEntity) player;
                    playerSkin = acp.getSkinTextures().texture();
                }
            }
		}

		++age;

		super.tick();

		if (inGround)
		{
			inGround = false;
            setVelocity(getVelocity().multiply(random.nextFloat() * 0.2F,
                random.nextFloat() * 0.2F,
                random.nextFloat() * 0.2F));
		}
		if (isSliding)
		{
			slideCount++;
			if (slideCount == 140) kill();
		}

		Vec3d vec3 = getPos();
		Vec3d vec31 = getPos().add(getVelocity());
		BlockHitResult hitResult = getWorld().raycast(new RaycastContext(vec3, vec31, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

		if (hitResult != null)
		{
			isSliding = true;
            setPos(getX(), hitResult.getPos().getY() + offset, getZ());
		}

		setPitch(getPitch() + motionpitch);
        setYaw(getYaw() + motionyaw);
        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());

		setPitch(prevPitch + (getPitch() - prevPitch) * 0.5F);
		setYaw(prevYaw + (getYaw() - prevYaw) * 0.5F);

		float f2 = 0.99F;
		float f3 = 0.05F;

		if (isInsideWaterOrBubbleColumn())
		{
            for (int k = 0; k < 4; ++k) {
                float f4 = 0.25F;
                getWorld().addParticle(ParticleTypes.BUBBLE, getX() - getVelocity().getX() * (double)f4, getY() - getVelocity().getY() * (double)f4, getZ() - getVelocity().getZ() * (double)f4, getVelocity().getX(), getVelocity().getY(), getVelocity().getZ());
            }

			f2 = 0.9F;
		}
		else if (!isSliding)
		{
			if (getWorld().isClient && RRConfig.CLIENT.isGoreEnabled())
			{
				spawnBlood();
			}
		}
		else
		{
			f2 = 0.8f;
		}

		motionpitch *= (double) f2;
		motionyaw *= (double) f2;
        setVelocity(getVelocity().multiply(f2));
        setVelocity(getVelocity().subtract(0, f3, 0));

		if (isSliding)
		{
			if (bounces == -1) bounces = getWorld().random.nextInt(2) + 2;
			if (bounces > 0)
			{
				bounces--;
                setVelocity(getVelocity().multiply(1, -((random.nextDouble() * 0.5) + 0.35), 1));
				isSliding = false;
				slideCount = 0;
				pitchLock = (int) ((Math.ceil((getPitch() / 90f))) * 90);
			}
			else setVelocity(getVelocity().getX(), 0, getVelocity().getZ());
			motionpitch = 0;
			setPitch(pitchLock);
		}

		setPosition(getX(), getY(), getZ());
	}

	@Environment(EnvType.CLIENT)
	private void spawnBlood()
	{
		for (int i = 0; i < 3; ++i)
		{
			RivalRebels.proxy.spawnGore(getWorld(), this, !isGreen());
		}
	}

    @Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putInt("Mob", getMob());
		nbt.putInt("TypeOfGore", getTypeOfGore());
		nbt.putBoolean("Green", isGreen());
        if (getOwner() != null) {
            nbt.putUuid("owner", getOwner());
        }
	}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
		setMob(nbt.getInt("Mob"));
		setTypeOfGore(nbt.getInt("TypeOfGore"));
		setGreen(nbt.getBoolean("Green"));
        if (nbt.contains("owner")) {
            setOwner(nbt.getUuid("owner"));
        }
	}

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(MOB, -1);
        dataTracker.startTracking(TYPE_OF_GORE, 0);
        dataTracker.startTracking(SIZE, 0F);
        dataTracker.startTracking(IS_GREEN, false);
        dataTracker.startTracking(OWNER, Optional.empty());
    }
}

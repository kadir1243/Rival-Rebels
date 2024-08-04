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
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class EntityGore extends EntityInanimate {
    public static final EntityDataAccessor<Integer> MOB = SynchedEntityData.defineId(EntityGore.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TYPE_OF_GORE = SynchedEntityData.defineId(EntityGore.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(EntityGore.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> IS_GREEN = SynchedEntityData.defineId(EntityGore.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(EntityGore.class, EntityDataSerializers.OPTIONAL_UUID);
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
	public ResourceLocation	playerSkin	= null;
	private int				bounces		= -1;

    public EntityGore(EntityType<? extends EntityGore> type, Level world) {
        super(type, world);
    }

	public EntityGore(Level par1World)
	{
		this(RREntities.GORE, par1World);
	}
	public EntityGore(Level par1World, double x, double y,double z, double mx, double my, double mz, int Type, int Mob)
	{
		this(par1World);
		setPos(x,y,z);
		setAnglesMotion(mx, my, mz);
		setTypeOfGore(Type);
		setMob(Mob);
	}

    public void setMob(int mob) {
        entityData.set(MOB, mob);
    }

    public int getMob() {
        return entityData.get(MOB);
    }

    public boolean isGreen() {
        return entityData.get(IS_GREEN);
    }

    public void setGreen(boolean green) {
        entityData.set(IS_GREEN, green);
    }

    public void setAnglesMotion(double mx, double my, double mz)
	{
        setDeltaMovement(mx, my, mz);
		setYRot(yRotO = (float) (Mth.atan2(mx, mz) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
	}

	public EntityGore(Level par1World, Entity toBeGibbed, int Type, int mob)
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
            setOwner(origin instanceof Player player ? player.getGameProfile().getId() : null);
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
			setSize((float) origin.getBoundingBox().getSize());
			if (getSize() > 2 || getSize() < 1) setGreen(true);
			setDefault();
		}

		motionyaw = (float) ((random.nextDouble() - 0.5) * 135);
		motionpitch = (float) ((random.nextDouble() - 0.5) * 135);

		moveTo(toBeGibbed.getX() + x, toBeGibbed.getY() + y, toBeGibbed.getZ() + z, rotYaw, rotPitch);
		setPos(getX(), getY(), getZ());
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
		rotYaw = origin.getYRot();
	}

    public int getTypeOfGore() {
        return entityData.get(TYPE_OF_GORE);
    }

    public void setTypeOfGore(int type) {
        entityData.set(TYPE_OF_GORE, type);
    }

    private void setBiped(int thin)
	{
		if (getTypeOfGore() == 0) {
			y = origin.getEyeHeight(origin.getPose()) + 0.20f;
			offset = 0.25f;
			rotYaw = origin.getYRot();
			rotPitch = origin.getXRot();
		} else if (getTypeOfGore() == 1) {
			if (thin == 2) y = 2f;
			else y = 1.1f;
			offset = 0.125F;
			rotYaw = origin.getYRot();
		} else if (getTypeOfGore() == 2) {
			x = (float) random.nextDouble() - 0.5f;
			if (thin == 2) y = 1.6f;
			else y = 1.1f;
			z = (float) random.nextDouble() - 0.5f;
			if (thin == 0) offset = 0.125F;
			else offset = 0.0625F;
			rotYaw = origin.getYRot();
		} else if (getTypeOfGore() == 3) {
			x = (float) random.nextDouble() - 0.5f;
			if (thin == 2) y = 0.95f;
			else y = 0.35f;
			z = (float) random.nextDouble() - 0.5f;
			if (thin == 0) offset = 0.125F;
			else offset = 0.0625F;
			rotYaw = origin.getYRot();
		}
	}

	private void setQuadruped()
	{
		if (getTypeOfGore() == 0) {
			y = origin.getEyeHeight(origin.getPose());
			offset = 0.25f;
			rotYaw = origin.getYRot();
			rotPitch = origin.getXRot();
		} else if (getTypeOfGore() == 1) {
			y = 0.875f;
			offset = 0.125F;
			rotYaw = origin.getYRot();
		} else if (getTypeOfGore() == 3) {
			x = (float) (random.nextDouble() - 0.5);
			y = 0.25f;
			z = (float) (random.nextDouble() - 0.5);
			offset = 0.125F;
			rotYaw = origin.getYRot();
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
				rotYaw = origin.getYRot();
				rotPitch = origin.getXRot();
			} else if (getTypeOfGore() == 1) {
				x = (float) (random.nextDouble() - 0.5f) * scale;
				y = 0.25f * scale;
				z = (float) (random.nextDouble() - 0.5f) * scale;
				offset = 0.1F * scale;
				rotYaw = origin.getYRot();
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
				rotYaw = origin.getYRot();
				rotPitch = origin.getXRot();
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
            rotYaw = origin.getYRot();
            rotPitch = origin.getXRot();
        } else if (getTypeOfGore() == 1) {
            y = origin.getEyeHeight(origin.getPose()) / 2;
            offset = 0.125F;
            rotYaw = origin.getYRot();
        } else if (getTypeOfGore() == 2) {
            x = (float) ((random.nextDouble() - 0.5f) * getSize());
            y = origin.getEyeHeight(origin.getPose()) / 2;
            z = (float) ((random.nextDouble() - 0.5f) * getSize());
            offset = 0.0625F;
            rotYaw = origin.getYRot();
        } else if (getTypeOfGore() == 3) {
            x = (float) ((random.nextDouble() - 0.5f) * getSize());
            y = origin.getEyeHeight(origin.getPose()) / 4;
            z = (float) ((random.nextDouble() - 0.5f) * getSize());
            offset = 0.0625F;
            rotYaw = origin.getYRot();
        }
	}

    public float getSize() {
        return entityData.get(SIZE);
    }

    public void setSize(float size) {
        entityData.set(SIZE, size);
    }

    public void shoot(float par7)
	{
		setDeltaMovement(random.nextGaussian() * par7,
            Mth.abs((float) (random.nextGaussian() * par7)),
            random.nextGaussian() * par7);
	}

    @Nullable
    public UUID getOwner() {
        return entityData.get(OWNER).orElse(null);
    }

    public void setOwner(@Nullable UUID owner) {
        entityData.set(OWNER, Optional.ofNullable(owner));
    }

	@Override
	public void tick()
	{
		if (playerSkin == null && level().isClientSide && getOwner() != null) {
            for (Player player : level().players()) {
                if (player.getGameProfile().getId().equals(getOwner())) {
                    AbstractClientPlayer acp = (AbstractClientPlayer) player;
                    playerSkin = acp.getSkin().texture();
                }
            }
		}

		++tickCount;

		super.tick();

		if (inGround)
		{
			inGround = false;
            setDeltaMovement(getDeltaMovement().multiply(random.nextFloat() * 0.2F,
                random.nextFloat() * 0.2F,
                random.nextFloat() * 0.2F));
		}
		if (isSliding)
		{
			slideCount++;
			if (slideCount == 140) kill();
		}

		Vec3 vec3 = position();
		Vec3 vec31 = position().add(getDeltaMovement());
		BlockHitResult hitResult = level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

		if (hitResult != null)
		{
			isSliding = true;
            setPosRaw(getX(), hitResult.getLocation().y() + offset, getZ());
		}

		setXRot(getXRot() + motionpitch);
        setYRot(getYRot() + motionyaw);
        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

		setXRot(xRotO + (getXRot() - xRotO) * 0.5F);
		setYRot(yRotO + (getYRot() - yRotO) * 0.5F);

		float f2 = 0.99F;
		float f3 = 0.05F;

		if (isInWaterOrBubble())
		{
            for (int k = 0; k < 4; ++k) {
                float f4 = 0.25F;
                level().addParticle(ParticleTypes.BUBBLE, getX() - getDeltaMovement().x() * (double)f4, getY() - getDeltaMovement().y() * (double)f4, getZ() - getDeltaMovement().z() * (double)f4, getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z());
            }

			f2 = 0.9F;
		}
		else if (!isSliding)
		{
			if (level().isClientSide && RRConfig.CLIENT.isGoreEnabled())
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
        setDeltaMovement(getDeltaMovement().scale(f2));
        setDeltaMovement(getDeltaMovement().subtract(0, f3, 0));

		if (isSliding)
		{
			if (bounces == -1) bounces = level().random.nextInt(2) + 2;
			if (bounces > 0)
			{
				bounces--;
                setDeltaMovement(getDeltaMovement().multiply(1, -((random.nextDouble() * 0.5) + 0.35), 1));
				isSliding = false;
				slideCount = 0;
				pitchLock = (Mth.ceil((getXRot() / 90f))) * 90;
			}
			else setDeltaMovement(getDeltaMovement().x(), 0, getDeltaMovement().z());
			motionpitch = 0;
			setXRot(pitchLock);
		}

		setPos(getX(), getY(), getZ());
	}

	@Environment(EnvType.CLIENT)
	private void spawnBlood()
	{
		for (int i = 0; i < 3; ++i)
		{
			RivalRebels.proxy.spawnGore(level(), this, !isGreen());
		}
	}

    @Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putInt("Mob", getMob());
		nbt.putInt("TypeOfGore", getTypeOfGore());
		nbt.putBoolean("Green", isGreen());
        if (getOwner() != null) {
            nbt.putUUID("owner", getOwner());
        }
	}

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
		setMob(nbt.getInt("Mob"));
		setTypeOfGore(nbt.getInt("TypeOfGore"));
		setGreen(nbt.getBoolean("Green"));
        if (nbt.contains("owner")) {
            setOwner(nbt.getUUID("owner"));
        }
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(MOB, -1);
        builder.define(TYPE_OF_GORE, 0);
        builder.define(SIZE, 0F);
        builder.define(IS_GREEN, false);
        builder.define(OWNER, Optional.empty());
    }
}

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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.explosion.Explosion;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityB2Frag extends EntityInanimate
{
    protected boolean	inGround;
	private int			ticksInGround;
    private boolean		isSliding	= false;
	public int			type		= 0;
	float				motionyaw	= 0;
	float				motionpitch	= 0;
	float				offset		= 0;
    public int			health;

    public EntityB2Frag(EntityType<? extends EntityB2Frag> type, Level world) {
        super(type, world);
    }

	public EntityB2Frag(Level par1World) {
		this(RREntities.B2FRAG, par1World);
		health = 300;
		setBoundingBox(new AABB(-2.5, -2.5, -2.5, 2.5, 2.5, 2.5));
		noCulling = true;
	}

	public EntityB2Frag(Level par1World, Entity toBeGibbed, int Type)
	{
		this(par1World);
		health = 300;
		setBoundingBox(new AABB(-2.5, -2.5, -2.5, 2.5, 2.5, 2.5));
		noCulling = true;

		isSliding = false;
		type = Type;

		motionyaw = (float) ((random.nextDouble() - 0.5) * 35);
		motionpitch = (float) ((random.nextDouble() - 0.5) * 25);

		moveTo(toBeGibbed.getX(), toBeGibbed.getY(), toBeGibbed.getZ(), toBeGibbed.getYRot(), toBeGibbed.getXRot());

		double ox = getX();
		double oz = getZ();

		if (Type == 1)
		{
            setPosRaw(getX() - (Mth.cos(((-getYRot()) / 180.0F) * (float) Math.PI) * 7.5F),
                getY(),
                getZ() - (Mth.sin(((-getYRot()) / 180.0F) * (float) Math.PI) * 7.5F));
		}
		else if (Type == 0)
		{
            setPosRaw(getX() - (Mth.cos(((-getYRot() + 180) / 180.0F) * (float) Math.PI) * 7.5F),
                getY(),
                getZ() - (Mth.sin(((-getYRot() + 180) / 180.0F) * (float) Math.PI) * 7.5F));
		}

		setPos(getX(), getY(), getZ());

        setDeltaMovement(toBeGibbed.getDeltaMovement());

        setDeltaMovement(getDeltaMovement().add((-ox + getX()) * 0.1, 0, (-oz + getZ()) * 0.1));

		igniteForSeconds(10);
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public void tick()
	{
		/*if (ticksInAir == 0)
		{
			EnvType side = FMLCommonHandler.instance().getEffectiveSide();
			if (side == EnvType.SERVER)
			{
                for (PlayerEntity player : world.playerEntities) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(9);
                    try (DataOutputStream outputStream = new DataOutputStream(bos)) {
                        outputStream.writeInt(17);
                        outputStream.writeInt(getId());
                        outputStream.writeByte(type);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Packet250CustomPayload packet = new Packet250CustomPayload();
                    packet.data = bos.toByteArray();
                    packet.length = bos.size();
                    PacketDispatcher.sendPacketToPlayer(packet, player);
                }
			}
		}*/

		super.tick();

		if (inGround)
		{
			++ticksInGround;

			if (ticksInGround == 1200)
			{
				kill();
			}

			inGround = false;
			setDeltaMovement(getDeltaMovement().multiply((random.nextFloat() * 0.2F),
                (random.nextFloat() * 0.2F),
                (random.nextFloat() * 0.2F)));
			ticksInGround = 0;
        }

		Vec3 vec3 = position();
		Vec3 vec31 = position().add(getDeltaMovement());
		BlockHitResult hitResult = level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

		if (hitResult != null)
		{
			isSliding = true;
            setPosRaw(getX(), hitResult.getLocation().y() + offset, getZ());
		}

		if (!level().isClientSide)
		{
			List<Entity> var5 = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRocket) {
                    ((EntityRocket) var9).explode(null);
                }

                if (var9 instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) var9).explode();
                }

                if (var9 instanceof EntityLaserBurst) {
                    var9.kill();
                    hurt(damageSources().generic(), 6);
                }
            }
		}

        setXRot(getXRot() + motionpitch);
		setYRot(getYRot() + motionyaw);
        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

		setXRot(xRotO + (getXRot() - xRotO) * 0.5F);
		setYRot(yRotO + (getYRot() - yRotO) * 0.5F);

		float f2 = 0.99F;
		float f3 = 0.05F;

		if (isSliding)
		{
			motionpitch = 0;
			motionyaw = 0;
            setDeltaMovement(getDeltaMovement().x(), 0, getDeltaMovement().z());
			f2 = 0.7f;
			f3 = 0.0f;
		}

		motionpitch *= (double) f2;
		motionyaw *= (double) f2;
        setDeltaMovement(getDeltaMovement().scale(f2));
        setDeltaMovement(getDeltaMovement().subtract(0, f3, 0));

		setPos(getX(), getY(), getZ());
	}

    @Override
	public void addAdditionalSaveData(CompoundTag par1NBTTagCompound)
	{
		par1NBTTagCompound.putInt("Type", type);
		par1NBTTagCompound.putBoolean("inGround", inGround);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag par1NBTTagCompound)
	{
		type = par1NBTTagCompound.getInt("Type");
		inGround = par1NBTTagCompound.getBoolean("inGround");
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (isAlive()) {
			health -= amount;

			if (health <= 0) {
				kill();
				new Explosion(level(), getX(), getY(), getZ(), 6, true, true, RivalRebelsDamageSource.rocket(level()));
                level().playLocalSound(this, RRSounds.ARTILLERY_EXPLODE, getSoundSource(), 30, 1);
			}
		}

		return true;
	}

}

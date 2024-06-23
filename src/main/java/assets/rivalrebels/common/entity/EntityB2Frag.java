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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

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

    public EntityB2Frag(EntityType<? extends EntityB2Frag> type, World world) {
        super(type, world);
    }

	public EntityB2Frag(World par1World) {
		this(RREntities.B2FRAG, par1World);
		health = 300;
		setBoundingBox(new Box(-2.5, -2.5, -2.5, 2.5, 2.5, 2.5));
		ignoreCameraFrustum = true;
	}

	public EntityB2Frag(World par1World, Entity toBeGibbed, int Type)
	{
		this(par1World);
		health = 300;
		setBoundingBox(new Box(-2.5, -2.5, -2.5, 2.5, 2.5, 2.5));
		ignoreCameraFrustum = true;

		isSliding = false;
		type = Type;

		motionyaw = (float) ((random.nextDouble() - 0.5) * 35);
		motionpitch = (float) ((random.nextDouble() - 0.5) * 25);

		refreshPositionAndAngles(toBeGibbed.getX(), toBeGibbed.getY(), toBeGibbed.getZ(), toBeGibbed.getYaw(), toBeGibbed.getPitch());

		double ox = getX();
		double oz = getZ();

		if (Type == 1)
		{
            setPos(getX() - (MathHelper.cos(((-getYaw()) / 180.0F) * (float) Math.PI) * 7.5F),
                getY(),
                getZ() - (MathHelper.sin(((-getYaw()) / 180.0F) * (float) Math.PI) * 7.5F));
		}
		else if (Type == 0)
		{
            setPos(getX() - (MathHelper.cos(((-getYaw() + 180) / 180.0F) * (float) Math.PI) * 7.5F),
                getY(),
                getZ() - (MathHelper.sin(((-getYaw() + 180) / 180.0F) * (float) Math.PI) * 7.5F));
		}

		setPosition(getX(), getY(), getZ());

        setVelocity(toBeGibbed.getVelocity());

        setVelocity(getVelocity().add((-ox + getX()) * 0.1, 0, (-oz + getZ()) * 0.1));

		setOnFireFor(10);
	}

	@Override
	public boolean isCollidable()
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
			setVelocity(getVelocity().multiply((random.nextFloat() * 0.2F),
                (random.nextFloat() * 0.2F),
                (random.nextFloat() * 0.2F)));
			ticksInGround = 0;
        }

		Vec3d vec3 = getPos();
		Vec3d vec31 = getPos().add(getVelocity());
		BlockHitResult hitResult = getWorld().raycast(new RaycastContext(vec3, vec31, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

		if (hitResult != null)
		{
			isSliding = true;
            setPos(getX(), hitResult.getPos().getY() + offset, getZ());
		}

		if (!getWorld().isClient)
		{
			List<Entity> var5 = getWorld().getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRocket) {
                    ((EntityRocket) var9).explode(null);
                }

                if (var9 instanceof EntityPlasmoid) {
                    ((EntityPlasmoid) var9).explode();
                }

                if (var9 instanceof EntityLaserBurst) {
                    var9.kill();
                    damage(getDamageSources().generic(), 6);
                }
            }
		}

        setPitch(getPitch() + motionpitch);
		setYaw(getYaw() + motionyaw);
        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());

		setPitch(prevPitch + (getPitch() - prevPitch) * 0.5F);
		setYaw(prevYaw + (getYaw() - prevYaw) * 0.5F);

		float f2 = 0.99F;
		float f3 = 0.05F;

		if (isSliding)
		{
			motionpitch = 0;
			motionyaw = 0;
            setVelocity(getVelocity().getX(), 0, getVelocity().getZ());
			f2 = 0.7f;
			f3 = 0.0f;
		}

		motionpitch *= (double) f2;
		motionyaw *= (double) f2;
        setVelocity(getVelocity().multiply(f2));
        setVelocity(getVelocity().subtract(0, f3, 0));

		setPosition(getX(), getY(), getZ());
	}

    @Override
	public void writeCustomDataToNbt(NbtCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.putInt("Type", type);
		par1NBTTagCompound.putBoolean("inGround", inGround);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound par1NBTTagCompound)
	{
		type = par1NBTTagCompound.getInt("Type");
		inGround = par1NBTTagCompound.getBoolean("inGround");
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (isAlive()) {
			health -= amount;

			if (health <= 0) {
				kill();
				new Explosion(getWorld(), getX(), getY(), getZ(), 6, true, true, RivalRebelsDamageSource.rocket(getWorld()));
                getWorld().playSoundFromEntity(this, RRSounds.ARTILLERY_EXPLODE, getSoundCategory(), 30, 1);
			}
		}

		return true;
	}

}

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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityRaytrace extends EntityInanimate
{
	public Entity	shootingEntity;
    private float	range		= 0;
	private float	c;

    public EntityRaytrace(EntityType<? extends EntityRaytrace> type, World world) {
        super(type, world);
    }

	public EntityRaytrace(World par1World) {
		this(RREntities.RAYTRACE, par1World);
	}

	public EntityRaytrace(World par1World, double x, double y,double z, double mx, double my, double mz) {
		this(par1World);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
		c = 1.0f;
		range = MathHelper.sqrt((float) (mx*mx+my*my+mz*mz));
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
        setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

	public EntityRaytrace(World par1World, Entity player, float distance, float randomness, float chance, boolean shift)
	{
		this(par1World);
		c = chance;
		range = distance;
		shootingEntity = player;
		refreshPositionAndAngles(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYaw(), player.getPitch());
		setPosition(getX(), getY(), getZ());
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));

        if (shift)
		{
            setPos(
                getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F),
                getY(),
                getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F)
            );
            setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		}
		else
		{
            setPos(
                getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.3F),
                getY() - 0.05f,
                getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.3F)
            );
		}
		setArrowHeading(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), range, randomness);
	}

    public void setArrowHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += getWorld().random.nextGaussian() * par8;
		par3 += getWorld().random.nextGaussian() * par8;
		par5 += getWorld().random.nextGaussian() * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		setVelocity(par1, par3, par5);
		float var10 = MathHelper.sqrt((float) (par1 * par1 + par5 * par5));
		setYaw(prevYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
	}

    @Override
	public void tick()
	{
		super.tick();
		Vec3d vec31 = getPos();
		Vec3d vec3 = getPos().add(getVelocity());
		HitResult MOP = getWorld().raycast(new RaycastContext(vec31, vec3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		vec31 = getPos();
		if (MOP != null) vec3 = MOP.getPos();
		else vec3 = getPos().add(getVelocity());

		List<Entity> list = getWorld().getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 30.0D, 1.0D));
		double d0 = Double.MAX_VALUE;
        for (Entity entity : list) {
            if ((entity.isCollidable() || entity instanceof EntityRhodes) && entity != shootingEntity) {
                Optional<Vec3d> mop1 = entity.getBoundingBox().expand(0.5f, 0.5f, 0.5f).raycast(vec31, vec3);
                if (mop1.isPresent()) {
                    double d1 = vec31.squaredDistanceTo(mop1.get());
                    if (d1 < d0) {
                        MOP = new EntityHitResult(entity, mop1.get());
                        d0 = d1;
                    }
                }
            }
        }
		if (MOP != null)
		{
			if (MOP.getType() == HitResult.Type.BLOCK)
			{
                BlockPos pos = ((BlockHitResult) MOP).getBlockPos();
                if (!getWorld().isClient) getWorld().spawnEntity(new EntityLightningLink(getWorld(), this, Math.sqrt(squaredDistanceTo(MOP.getPos().x, MOP.getPos().y, MOP.getPos().z))));
				// world.spawnEntity(new EntityNuclearBlast(world, MOP.blockX, pos.getY(), pos.getZ(), 5, false));
				Block BlockHit = getWorld().getBlockState(pos).getBlock();
				float r = getWorld().random.nextFloat();
				if (BlockHit == RRBlocks.camo1 || BlockHit == RRBlocks.camo2 || BlockHit == RRBlocks.camo3)
				{
					if (r * 10 <= c)
					{
						if (!getWorld().isClient) getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
						for (int i = 0; i < 4; i++)
						{
							getWorld().addParticle(ParticleTypes.EXPLOSION, pos.getX(), pos.getY() - 1 + i * 0.5, pos.getZ(), (getWorld().random.nextFloat() - 0.5F) * 0.1, getWorld().random.nextFloat() * 0.05, (getWorld().random.nextFloat() - 0.5F) * 0.1);
						}
					}
				}
				else if (BlockHit == RRBlocks.reactive)
				{
					if (r * 15 <= c)
					{
						if (!getWorld().isClient) getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
						for (int i = 0; i < 4; i++)
						{
							getWorld().addParticle(ParticleTypes.EXPLOSION, pos.getX(), pos.getY() - 1 + i * 0.5, pos.getZ(), (getWorld().random.nextFloat() - 0.5F) * 0.1, getWorld().random.nextFloat() * 0.05, (getWorld().random.nextFloat() - 0.5F) * 0.1);
						}
					}
				}
				else if (!BlackList.tesla(BlockHit) && r <= c)
				{
					if (!getWorld().isClient) getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
					for (int i = 0; i < 4; i++)
					{
						getWorld().addParticle(ParticleTypes.EXPLOSION, pos.getX(), pos.getY() - 1 + i * 0.5, pos.getZ(), (getWorld().random.nextFloat() - 0.5F) * 0.1, getWorld().random.nextFloat() * 0.05, (getWorld().random.nextFloat() - 0.5F) * 0.1);
					}
				}
			}
			else if (MOP.getType() == HitResult.Type.ENTITY)
			{
                Entity entityHit = ((EntityHitResult) MOP).getEntity();
                if (!getWorld().isClient) getWorld().spawnEntity(new EntityLightningLink(getWorld(), this, distanceTo(entityHit)));
				if (entityHit instanceof PlayerEntity entityPlayerHit)
				{
                    EquipmentSlot slot = EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, getWorld().random.nextInt(4));
					int i = slot.getEntitySlotId();
					if (!entityPlayerHit.getEquippedStack(slot).isEmpty())
					{
						entityPlayerHit.getEquippedStack(slot).damage(14, entityPlayerHit, player -> player.sendEquipmentBreakStatus(slot));
						entityPlayerHit.damage(RivalRebelsDamageSource.electricity(getWorld()), 1);
					}
					else
					{
						entityPlayerHit.damage(RivalRebelsDamageSource.electricity(getWorld()), (RivalRebels.teslaDecay / ((int) entityHit.distanceTo(this) + 1) / (i + 1)));
					}
				}
				else if (entityHit instanceof EntityB2Spirit)
				{
					entityHit.damage(RivalRebelsDamageSource.electricity(getWorld()), (RivalRebels.teslaDecay / 1.5f) / ((int) entityHit.distanceTo(this) + 1));
				}
				else
				{
					entityHit.damage(RivalRebelsDamageSource.electricity(getWorld()), RivalRebels.teslaDecay / ((int) entityHit.distanceTo(this) + 1));
				}
			}
		}
		else
		{
			if (!getWorld().isClient) getWorld().spawnEntity(new EntityLightningLink(getWorld(), this, range));
		}
		kill();
	}
}

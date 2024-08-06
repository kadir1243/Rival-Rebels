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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityRaytrace extends EntityInanimate
{
	public Entity	shootingEntity;
    private float	range		= 0;
	private float	c;

    public EntityRaytrace(EntityType<? extends EntityRaytrace> type, Level world) {
        super(type, world);
    }

	public EntityRaytrace(Level par1World) {
		this(RREntities.RAYTRACE, par1World);
	}

	public EntityRaytrace(Level par1World, double x, double y,double z, double mx, double my, double mz) {
		this(par1World);
		setPos(x,y,z);
		setAnglesMotion(mx, my, mz);
		c = 1.0f;
		range = Mth.sqrt((float) (mx*mx+my*my+mz*mz));
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setDeltaMovement(mx, my, mz);
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
        setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
	}

	public EntityRaytrace(Level par1World, Entity player, float distance, float randomness, float chance, boolean shift)
	{
		this(par1World);
		c = chance;
		range = distance;
		shootingEntity = player;
		moveTo(player.getEyePosition(),
            player.getYRot(),
            player.getXRot()
        );

        setDeltaMovement(
            (-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

        if (shift) {
            setPosRaw(
                getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
                getY(),
                getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F)
            );
            setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		} else {
            setPosRaw(
                getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.3F),
                getY() - 0.05f,
                getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.3F)
            );
		}
		setArrowHeading(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), range, randomness);
	}

    public void setArrowHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = Mth.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += level().random.nextGaussian() * par8;
		par3 += level().random.nextGaussian() * par8;
		par5 += level().random.nextGaussian() * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		setDeltaMovement(par1, par3, par5);
		float var10 = Mth.sqrt((float) (par1 * par1 + par5 * par5));
		setYRot(yRotO = (float) (Math.atan2(par1, par5) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(par3, var10) * Mth.RAD_TO_DEG));
	}

    @Override
	public void tick()
	{
		super.tick();
		Vec3 vec31 = position();
		Vec3 vec3 = position().add(getDeltaMovement());
		HitResult MOP = level().clip(new ClipContext(vec31, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		vec31 = position();
		if (MOP != null) vec3 = MOP.getLocation();
		else vec3 = position().add(getDeltaMovement());

		List<Entity> list = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 30.0D, 1.0D));
		double d0 = Double.MAX_VALUE;
        for (Entity entity : list) {
            if ((entity.canBeCollidedWith() || entity instanceof EntityRhodes) && entity != shootingEntity) {
                Optional<Vec3> mop1 = entity.getBoundingBox().inflate(0.5f, 0.5f, 0.5f).clip(vec31, vec3);
                if (mop1.isPresent()) {
                    double d1 = vec31.distanceToSqr(mop1.get());
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
                if (!level().isClientSide()) level().addFreshEntity(new EntityLightningLink(level(), this, Math.sqrt(distanceToSqr(MOP.getLocation().x, MOP.getLocation().y, MOP.getLocation().z))));
				// world.spawnEntity(new EntityNuclearBlast(world, MOP.blockX, pos.getY(), pos.getZ(), 5, false));
				BlockState BlockHit = level().getBlockState(pos);
				float r = level().random.nextFloat();
				if (BlockHit.is(RRBlocks.camo1) || BlockHit.is(RRBlocks.camo2) || BlockHit.is(RRBlocks.camo3))
				{
					if (r * 10 <= c)
					{
						if (!level().isClientSide()) level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						for (int i = 0; i < 4; i++)
						{
							level().addParticle(ParticleTypes.EXPLOSION, pos.getX(), pos.getY() - 1 + i * 0.5, pos.getZ(), (level().random.nextFloat() - 0.5F) * 0.1, level().random.nextFloat() * 0.05, (level().random.nextFloat() - 0.5F) * 0.1);
						}
					}
				}
				else if (BlockHit.is(RRBlocks.reactive))
				{
					if (r * 15 <= c)
					{
						if (!level().isClientSide()) level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						for (int i = 0; i < 4; i++)
						{
							level().addParticle(ParticleTypes.EXPLOSION, pos.getX(), pos.getY() - 1 + i * 0.5, pos.getZ(), (level().random.nextFloat() - 0.5F) * 0.1, level().random.nextFloat() * 0.05, (level().random.nextFloat() - 0.5F) * 0.1);
						}
					}
				}
				else if (!BlackList.tesla(BlockHit) && r <= c)
				{
					if (!level().isClientSide()) level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					for (int i = 0; i < 4; i++)
					{
						level().addParticle(ParticleTypes.EXPLOSION, pos.getX(), pos.getY() - 1 + i * 0.5, pos.getZ(), (level().random.nextFloat() - 0.5F) * 0.1, level().random.nextFloat() * 0.05, (level().random.nextFloat() - 0.5F) * 0.1);
					}
				}
			}
			else if (MOP.getType() == HitResult.Type.ENTITY)
			{
                Entity entityHit = ((EntityHitResult) MOP).getEntity();
                if (!level().isClientSide()) level().addFreshEntity(new EntityLightningLink(level(), this, distanceTo(entityHit)));
				if (entityHit instanceof Player entityPlayerHit)
				{
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
					int i = slot.getIndex();
					if (!entityPlayerHit.getItemBySlot(slot).isEmpty())
					{
						entityPlayerHit.getItemBySlot(slot).hurtAndBreak(14, entityPlayerHit, slot);
						entityPlayerHit.hurt(RivalRebelsDamageSource.electricity(level()), 1);
					}
					else
					{
						entityPlayerHit.hurt(RivalRebelsDamageSource.electricity(level()), (RRConfig.SERVER.getTeslaDecay() / ((int) entityHit.distanceTo(this) + 1) / (i + 1)));
					}
				}
				else if (entityHit instanceof EntityB2Spirit)
				{
					entityHit.hurt(RivalRebelsDamageSource.electricity(level()), (RRConfig.SERVER.getTeslaDecay() / 1.5f) / ((int) entityHit.distanceTo(this) + 1));
				}
				else
				{
					entityHit.hurt(RivalRebelsDamageSource.electricity(level()), RRConfig.SERVER.getTeslaDecay() / ((int) entityHit.distanceTo(this) + 1));
				}
			}
		}
		else
		{
			if (!level().isClientSide()) level().addFreshEntity(new EntityLightningLink(level(), this, range));
		}
		kill();
	}
}

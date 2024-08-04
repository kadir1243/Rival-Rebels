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

import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;

public class EntityCuchillo extends EntityInanimate
{
	public Entity	shootingEntity;
	private boolean	inGround;
	private int		ticksInGround;

    public EntityCuchillo(EntityType<? extends EntityCuchillo> type, Level par1World) {
        super(type, par1World);
    }

	public EntityCuchillo(Level par1World) {
		this(RREntities.CUCHILLO, par1World);
	}

	public EntityCuchillo(Level par1World, Player player, float par3)
	{
		this(par1World);
		shootingEntity = player;
		moveTo(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYRot(), player.getXRot());
        setPosRaw(getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
            getY() - 0.1f,
            getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F));
		setPos(getX(), getY(), getZ());
		setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)) * par3,
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)) * par3,
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)) * par3);
	}
	public EntityCuchillo(Level par1World, double x, double y,double z, double mx, double my, double mz)
	{
		this(par1World);
		setPos(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz) {
        setDeltaMovement(mx, my, mz);
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
	}

    @Override
	public void tick()
	{
		super.tick();
		tickCount++;
		if (!inGround)
		{
			Vec3 vec31 = position();
			Vec3 vec3 = position().add(getDeltaMovement());
			HitResult mop = level().clip(new ClipContext(vec31, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			vec31 = position();
			if (mop != null) vec3 = mop.getLocation();
			else vec3 = position().add(getDeltaMovement());

			List<Entity> list = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
            for (Entity entity : list) {
                if (entity.canBeCollidedWith() && (tickCount >= 10 || entity != shootingEntity)) {
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

			if (mop != null)
			{
				if (!level().isClientSide())
				{
					if (mop.getType() == HitResult.Type.ENTITY)
					{
                        ((EntityHitResult) mop).getEntity().hurt(RivalRebelsDamageSource.cuchillo(level()), 7);
						kill();
					}
					else if (mop.getType() == HitResult.Type.BLOCK)
					{
                        BlockPos pos = ((BlockHitResult) mop).getBlockPos();
                        BlockState state = level().getBlockState(pos);
						if (state.is(ModBlockTags.GLASS_BLOCKS) || state.is(ModBlockTags.GLASS_PANES)) {
							level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            this.playSound(RRSounds.CUCHILLO_GLASS_BREAK, 5F, 0.3F);
                        }
						else if (state.is(BlockTags.BASE_STONE_OVERWORLD))
						{
							level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.knife.getDefaultInstance()));
							kill();
						}
						else
						{
							inGround = true;
						}
					}
				}
			}
			else
			{
                setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
				setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * Mth.RAD_TO_DEG));
				while (getYRot() - yRotO < -180.0F)
					yRotO -= 360.0F;
				while (getYRot() - yRotO >= 180.0F)
					yRotO += 360.0F;
                setXRot(getXRot() - 30);
				setYRot(yRotO + (getYRot() - yRotO) * 0.2F);

				float friction = 0.98f;

				if (isInWaterOrBubble())
				{
					for (int var26 = 0; var26 < 4; ++var26)
						level().addParticle(ParticleTypes.BUBBLE, getX() - getDeltaMovement().x() * 0.25F, getY() - getDeltaMovement().y() * 0.25F, getZ() - getDeltaMovement().z() * 0.25F, getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z());
					friction = 0.8F;
				}
                setDeltaMovement(getDeltaMovement().scale(friction));
                setDeltaMovement(getDeltaMovement().subtract(0, 0.026F, 0));
				setPos(getX(), getY(), getZ());
			}
		}
		else
		{
            setDeltaMovement(getDeltaMovement().scale(0.2));
			ticksInGround++;
			if (ticksInGround == 60)
			{
				level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), RRItems.knife.getDefaultInstance()));
				kill();
			}
		}
	}

	@Override
	public void playerTouch(Player player) {
		if (!level().isClientSide && inGround) {
			if (!player.hasInfiniteMaterials()) player.getInventory().add(RRItems.knife.getDefaultInstance());
			level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LAVA_POP, SoundSource.NEUTRAL, 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F, true);
			kill();
		}
	}

	@Override
	public boolean isAttackable()
	{
		return false;
	}
}

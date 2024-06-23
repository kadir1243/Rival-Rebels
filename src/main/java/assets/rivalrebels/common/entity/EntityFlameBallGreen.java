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
import assets.rivalrebels.common.tileentity.TileEntityReciever;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityFlameBallGreen extends EntityInanimate
{
	public int		sequence;
	public float	rotation;
	public float	motionr;

    public EntityFlameBallGreen(EntityType<? extends EntityFlameBallGreen> type, Level world) {
        super(type, world);
    }

	public EntityFlameBallGreen(Level par1World)
	{
		this(RREntities.FLAME_BALL_GREEN, par1World);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBallGreen(Level par1World, double par2, double par4, double par6)
	{
		this(par1World);
		setPos(par2, par4, par6);
	}

	public EntityFlameBallGreen(Level par1World, Player player, float par3)
	{
		this(par1World);
		setPos(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
		setDeltaMovement((-Mth.sin(player.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float) Math.PI)),
            (Mth.cos(player.getYRot() / 180.0F * (float) Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float) Math.PI)),
            (-Mth.sin(player.getXRot() / 180.0F * (float) Math.PI)));
		setPosRaw(
            getX() - (Mth.cos(player.getYRot() / 180.0F * (float) Math.PI) * 0.2F),
            getY() - 0.13,
            getZ() - (Mth.sin(player.getYRot() / 180.0F * (float) Math.PI) * 0.2F)
        );
        setDeltaMovement(getDeltaMovement().scale(par3));
    }

	public EntityFlameBallGreen(Level par1World, TileEntityReciever ter, float f)
	{
		this(par1World);
		setYRot((float) (180 - ter.yaw));
		setXRot((float) (-ter.pitch));
		setPos(ter.getBlockPos().getX() + ter.xO + 0.5, ter.getBlockPos().getY() + 0.5, ter.getBlockPos().getZ() + ter.zO + 0.5);
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (-Mth.sin(getXRot() / 180.0F * (float) Math.PI)));

        setDeltaMovement(getDeltaMovement().scale(f));
	}

	public EntityFlameBallGreen(Level world, double x, double y, double z, double mx, double my, double mz)
	{
		this(world);
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
	}

	public EntityFlameBallGreen(Level world, double x, double y, double z, double mx, double my, double mz, double d, double r)
	{
		this(world);
		setPos(x+mx*d+random.nextGaussian()*r, y+my*d+random.nextGaussian()*r, z+mz*d+random.nextGaussian()*r);
        setDeltaMovement(mx, my, mz);
	}

	@Override
	public void tick() {
		super.tick();
		tickCount++;
		if (tickCount > 100) kill();
		if (tickCount % 3 == 0) sequence++;
		if (sequence > 3) sequence = 0;

		Vec3 start = position();
		Vec3 end = position().add(getDeltaMovement());
		HitResult mop = level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		start = position();
		end = position().add(getDeltaMovement());

		if (mop != null) end = mop.getLocation();

		Entity e = null;
		List<Entity> var5 = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;
		Iterator<Entity> var8 = var5.iterator();

		if (!level().isClientSide)
		{
			while (var8.hasNext())
			{
				Entity var9 = var8.next();

				if (var9.canBeCollidedWith())
				{
					float var10 = 0.3F;
					AABB var11 = var9.getBoundingBox().inflate(var10, var10, var10);
					Optional<Vec3> var12 = var11.clip(start, end);

					if (var12.isPresent())
					{
						double var13 = start.distanceTo(var12.get());

						if (var13 < var6 || var6 == 0.0D)
						{
							e = var9;
							var6 = var13;
						}
					}
				}
			}
		}

		if (e != null)
		{
			mop = new EntityHitResult(e);
		}

		if (mop != null && tickCount >= 5)
		{
			fire();
			kill();
			if (mop.getType() == HitResult.Type.ENTITY)
			{
                Entity entity = ((EntityHitResult) mop).getEntity();
                entity.igniteForSeconds(3);
				entity.hurt(RivalRebelsDamageSource.cooked(level()), 12);
				if (entity instanceof Player player) {
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
					if (!player.getItemBySlot(slot).isEmpty() && !level().isClientSide) {
						player.getItemBySlot(slot).hurtAndBreak(8, player, slot);
					}
				}
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

		rotation += motionr;

		if (isInWaterOrBubble()) kill();
		setPos(getX(), getY(), getZ());
	}

	@Override
	public float getLightLevelDependentMagicValue()
	{
		return 1000;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	private void fire()
	{
		if (!level().isClientSide)
		{
			for (int x = -3; x < 4; x++)
			{
				for (int y = -1; y < 2; y++)
				{
					for (int z = -3; z < 4; z++)
					{
                        BlockPos pos = new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z);
                        BlockState state = level().getBlockState(pos);
                        Block id = state.getBlock();
						if (level().isEmptyBlock(pos) || id == Blocks.SNOW || state.is(BlockTags.ICE) || state.is(BlockTags.LEAVES)) level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
					}
				}
			}
		}
	}
}

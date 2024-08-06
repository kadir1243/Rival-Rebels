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

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.tileentity.TileEntityReciever;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityFlameBall1 extends EntityInanimate {
	public int		sequence;
	public float	rotation;
	public float	motionr;

    public EntityFlameBall1(EntityType<? extends EntityFlameBall1> type, Level world) {
        super(type, world);
    }

	public EntityFlameBall1(Level par1World) {
		this(RREntities.FLAME_BALL1, par1World);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall1(Level par1World, double par2, double par4, double par6) {
		this(par1World);
		setPos(par2, par4, par6);
	}

	public EntityFlameBall1(Level par1World, Entity player, float par3) {
		this(par1World);
		setPos(player.getEyePosition());
		setDeltaMovement((-Mth.sin(player.getYRot() / 180.0F * Mth.PI) * Mth.cos(player.getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(player.getYRot() / 180.0F * Mth.PI) * Mth.cos(player.getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(player.getXRot() / 180.0F * Mth.PI)));
		setPosRaw(
            getX() - (Mth.cos(player.getYRot() / 180.0F * Mth.PI) * 0.2F),
            getY() - 0.13,
            getZ() - (Mth.sin(player.getYRot() / 180.0F * Mth.PI) * 0.2F)
        );
        setDeltaMovement(getDeltaMovement().scale(par3));
	}

	public EntityFlameBall1(Level par1World, TileEntityReciever ter, float f) {
		this(par1World);
		setYRot(180 - ter.yaw);
		setXRot(-ter.pitch);
		setPos(ter.getBlockPos().getX() + ter.xO + 0.5, ter.getBlockPos().getY() + 0.75, ter.getBlockPos().getZ() + ter.zO + 0.5);
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

        setDeltaMovement(getDeltaMovement().scale(f));
	}

	public EntityFlameBall1(Level world, double x, double y, double z, double mx, double my, double mz)
	{
		this(world);
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	@Override
	public void tick() {
		super.tick();
		tickCount++;
		if (tickCount > 5) sequence++;
		if (sequence > 15/* > RRConfig.SERVER.getFlamethrowerDecay() */) kill();

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, Entity::canBeCollidedWith);

		if (hitResult.getType() == HitResult.Type.ENTITY && tickCount >= 5) {
			fire();
			kill();
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            if (entity != null) {
				entity.igniteForSeconds(3);
				entity.hurt(RivalRebelsDamageSource.cooked(level()), 12);
				if (entity instanceof Player player) {
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
					if (!player.getItemBySlot(slot).isEmpty() && !level().isClientSide())
					{
						player.getItemBySlot(slot).hurtAndBreak(8, player, slot);
					}
				}
			} else {
				level().addFreshEntity(new EntityLightningLink(level(), this, this.distanceTo(entity)));
				if (entity instanceof Player player)
				{
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
					if (!player.getItemBySlot(slot).isEmpty()) {
						player.getItemBySlot(slot).hurtAndBreak(44, player, slot);
					}
					player.hurt(RivalRebelsDamageSource.cooked(level()), 250 / ((int) entity.distanceTo(this) + 1));
				}
				else if (entity instanceof EntityB2Spirit)
				{
					entity.hurt(RivalRebelsDamageSource.cooked(level()), 250 / ((int) entity.distanceTo(this) + 1));
				}
				else if (entity.isAttackable())
				{
					entity.hurt(RivalRebelsDamageSource.cooked(level()), 250 / ((int) entity.distanceTo(this) + 1));
				}
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		rotation += motionr;
		motionr *= 1.06f;

		if (isInWaterOrBubble()) kill();
		float airFriction = 0.97F;
		float gravity = 0.01F;
        setDeltaMovement(getDeltaMovement().scale(airFriction));
        setDeltaMovement(getDeltaMovement().subtract(0, gravity, 0));
		setPos(getX(), getY(), getZ());
	}

	@Override
	public boolean isAttackable()
	{
		return false;
	}

	private void fire()
	{
		if (!level().isClientSide())
		{
			for (int x = -1; x < 2; x++)
			{
				for (int y = -1; y < 2; y++)
				{
					for (int z = -1; z < 2; z++)
					{
                        BlockPos pos = blockPosition().offset(x, y, z);
                        BlockState state = level().getBlockState(pos);
                        Block id = state.getBlock();
						if (state.isAir() || state.is(BlockTags.SNOW) || state.is(BlockTags.ICE)) level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
						else if (state.is(BlockTags.LEAVES)) level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
						else if (id == Blocks.GRASS_BLOCK && level().random.nextInt(5) == 0) level().setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
						else if (state.is(RRBlocks.flare)) id.destroy(level(), pos, state);
					}
				}
			}
		}
	}
}

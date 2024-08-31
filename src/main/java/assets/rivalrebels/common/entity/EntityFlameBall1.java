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
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityFlameBall1 extends FlameBallProjectile {
    public EntityFlameBall1(EntityType<? extends EntityFlameBall1> type, Level level) {
        super(type, level);
    }

	public EntityFlameBall1(Level level) {
		this(RREntities.FLAME_BALL1, level);
	}

    public EntityFlameBall1(Level level, Entity entity, float speed) {
		this(level);
        this.setOwner(entity);
		setPos(entity.getEyePosition());
		setDeltaMovement((-Mth.sin(entity.getYRot() / 180.0F * Mth.PI) * Mth.cos(entity.getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(entity.getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(entity.getYRot() / 180.0F * Mth.PI) * Mth.cos(entity.getXRot() / 180.0F * Mth.PI)));
		setPosRaw(
            getX() - (Mth.cos(entity.getYRot() / 180.0F * Mth.PI) * 0.2F),
            getY() - 0.13,
            getZ() - (Mth.sin(entity.getYRot() / 180.0F * Mth.PI) * 0.2F)
        );
        setDeltaMovement(getDeltaMovement().scale(speed));
	}

	public EntityFlameBall1(Level level, TileEntityReciever ter, float speed) {
		this(level);
		setYRot(180 - ter.yaw);
		setXRot(-ter.pitch);
		setPos(ter.getBlockPos().getX() + ter.xO + 0.5, ter.getBlockPos().getY() + 0.75, ter.getBlockPos().getZ() + ter.zO + 0.5);
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

        setDeltaMovement(getDeltaMovement().scale(speed));
	}

	public EntityFlameBall1(Level level, double x, double y, double z, double mx, double my, double mz)
	{
		this(level);
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount > 5) sequence++;
		if (sequence > 15/* > RRConfig.SERVER.getFlamethrowerDecay() */) kill();

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

		if (hitResult.getType() == HitResult.Type.ENTITY && tickCount >= 5) {
			fire();
			kill();
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            if (entity != null) {
				entity.igniteForSeconds(3);
				entity.hurt(RivalRebelsDamageSource.cooked(level()), 12);
				if (entity instanceof Player player) {
                    ItemUtil.damageRandomArmor(player, 8, random);
				}
			} else {
				level().addFreshEntity(new EntityLightningLink(level(), this, this.distanceTo(entity)));
				if (entity instanceof Player player)
				{
                    ItemUtil.damageRandomArmor(player, 44, random);
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
        setDeltaMovement(getDeltaMovement().scale(airFriction));
        applyGravity();
        reapplyPosition();
	}

    @Override
    protected double getDefaultGravity() {
        return 0.01F;
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
						else if (id == Blocks.GRASS_BLOCK && random.nextInt(5) == 0) level().setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
						else if (state.is(RRBlocks.flare)) id.destroy(level(), pos, state);
					}
				}
			}
		}
	}
}

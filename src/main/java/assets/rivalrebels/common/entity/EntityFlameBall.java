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
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityFlameBall extends FlameBallProjectile {
    public EntityFlameBall(EntityType<? extends EntityFlameBall> entityType, Level level) {
        super(entityType, level);
    }

	public EntityFlameBall(Level level)
	{
		this(RREntities.FLAME_BALL, level);
	}

    public EntityFlameBall(Level level, Entity entity, float speed)
	{
		this(level);
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

	public EntityFlameBall(Level level, TileEntityReciever ter, float speed)
	{
		this(level);
		setYRot(180 - ter.yaw);
		setXRot(-ter.pitch);
		setPos(ter.getBlockPos().getX() + ter.xO + 0.5, ter.getBlockPos().getY() + 0.5, ter.getBlockPos().getZ() + ter.zO + 0.5);
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

        setDeltaMovement(getDeltaMovement().scale(speed));
	}

	public EntityFlameBall(Level level, double x, double y, double z, double mx, double my, double mz)
	{
		this(level);
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
	}

	public EntityFlameBall(Level level, double x, double y, double z, double mx, double my, double mz, double d, double r) {
		this(level);
		setPos(x+mx*d+random.nextGaussian()*r, y+my*d+random.nextGaussian()*r, z+mz*d+random.nextGaussian()*r);
        setDeltaMovement(mx, my, mz);
	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount % 3 == 0) sequence++;
		if (sequence > 15/* > RRConfig.SERVER.getFlamethrowerDecay() */) kill();

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

		if (hitResult.getType() != HitResult.Type.MISS && tickCount >= 5) {
			fire();
			kill();
			if (hitResult.getType() == HitResult.Type.ENTITY) {
                Entity hitEntity = ((EntityHitResult) hitResult).getEntity();
                hitEntity.igniteForSeconds(3);
				hitEntity.hurt(RivalRebelsDamageSource.cooked(level()), 12);
				if (hitEntity instanceof Player player) {
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
                    ItemStack equippedStack = player.getItemBySlot(slot);
                    if (!equippedStack.isEmpty() && !level().isClientSide()) {
						equippedStack.hurtAndBreak(8, player, slot);
					}
				}
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

		rotation += motionr;
		motionr *= 1.06f;

		if (isInWaterOrBubble()) kill();
		float airFriction = 0.96F;
        setDeltaMovement(getDeltaMovement().scale(airFriction));
        applyGravity();
        reapplyPosition();
	}

    @Override
    protected double getDefaultGravity() {
        return 0.01;
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
                        BlockPos pos = new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z);
                        BlockState state = level().getBlockState(pos);
						if (level().isEmptyBlock(pos) || state.is(BlockTags.SNOW) || state.is(BlockTags.ICE) || state.is(BlockTags.LEAVES)) level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
					}
				}
			}
		}
	}
}

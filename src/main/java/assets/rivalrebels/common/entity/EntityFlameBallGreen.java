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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityFlameBallGreen extends FlameBallProjectile {
    public EntityFlameBallGreen(EntityType<? extends EntityFlameBallGreen> type, Level world) {
        super(type, world);
    }

	public EntityFlameBallGreen(Level level) {
		this(RREntities.FLAME_BALL_GREEN, level);
	}

	public EntityFlameBallGreen(Level level, double x, double y, double z) {
		this(level);
		setPos(x, y, z);
	}

	public EntityFlameBallGreen(Level level, Entity entity, float speed) {
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

	public EntityFlameBallGreen(Level level, TileEntityReciever ter, float speed) {
		this(level);
		setYRot(180 - ter.yaw);
		setXRot(-ter.pitch);
		setPos(ter.getBlockPos().getX() + ter.xO + 0.5, ter.getBlockPos().getY() + 0.5, ter.getBlockPos().getZ() + ter.zO + 0.5);
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

        setDeltaMovement(getDeltaMovement().scale(speed));
	}

	public EntityFlameBallGreen(Level world, double x, double y, double z, double mx, double my, double mz) {
		this(world);
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
	}

	public EntityFlameBallGreen(Level world, double x, double y, double z, double mx, double my, double mz, double d, double r) {
		this(world);
		setPos(x+mx*d+random.nextGaussian()*r, y+my*d+random.nextGaussian()*r, z+mz*d+random.nextGaussian()*r);
        setDeltaMovement(mx, my, mz);
	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount > 100) kill();
		if (tickCount % 3 == 0) sequence++;
		if (sequence > 3) sequence = 0;

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, Entity::canBeCollidedWith);

		if (hitResult.getType() != HitResult.Type.MISS && tickCount >= 5)
		{
			fire();
			kill();
			if (hitResult.getType() == HitResult.Type.ENTITY)
			{
                Entity entity = ((EntityHitResult) hitResult).getEntity();
                entity.igniteForSeconds(3);
				entity.hurt(RivalRebelsDamageSource.cooked(level()), 12);
				if (entity instanceof Player player) {
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
					if (!player.getItemBySlot(slot).isEmpty() && !level().isClientSide()) {
						player.getItemBySlot(slot).hurtAndBreak(8, player, slot);
					}
				}
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

		rotation += motionr;

		if (isInWaterOrBubble()) kill();
		reapplyPosition();
	}

    private void fire() {
		if (!level().isClientSide()) {
			for (int x = -3; x < 4; x++) {
				for (int y = -1; y < 2; y++) {
					for (int z = -3; z < 4; z++) {
                        BlockPos pos = new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z);
                        BlockState state = level().getBlockState(pos);
						if (level().isEmptyBlock(pos) || state.is(BlockTags.SNOW) || state.is(BlockTags.ICE) || state.is(BlockTags.LEAVES)) level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
					}
				}
			}
		}
	}
}

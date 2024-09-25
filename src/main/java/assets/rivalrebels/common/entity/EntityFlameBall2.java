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
import assets.rivalrebels.common.util.ModBlockTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityFlameBall2 extends FlameBallProjectile {
	public boolean	gonnadie;

    public EntityFlameBall2(EntityType<? extends EntityFlameBall2> type, Level level) {
        super(type, level);
    }

	public EntityFlameBall2(Level level) {
		this(RREntities.FLAME_BALL2, level);
	}

    public EntityFlameBall2(Level level, Entity entity, float speed) {
		this(level);
		// speed/=3f;
		setPos(entity.getEyePosition());
		setDeltaMovement((-Mth.sin(entity.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(entity.getXRot() * Mth.DEG_TO_RAD)),
            (-Mth.sin(entity.getXRot() * Mth.DEG_TO_RAD)),
            (Mth.cos(entity.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(entity.getXRot() * Mth.DEG_TO_RAD)));
        setPosRaw(
            getX() - (Mth.cos(entity.getYRot() * Mth.DEG_TO_RAD) * 0.2F),
            getY() - 0.13,
            getZ() - (Mth.sin(entity.getYRot() * Mth.DEG_TO_RAD) * 0.2F)
        );
        setDeltaMovement(getDeltaMovement().scale(speed));
	}

	public EntityFlameBall2(Level level, TileEntityReciever ter, float speed)
	{
		this(level);
		setYRot(180 - ter.yaw);
		setXRot(-ter.pitch);
		setPos(ter.getBlockPos().getX() + ter.xO + 0.5, ter.getBlockPos().getY() + 0.5, ter.getBlockPos().getZ() + ter.zO + 0.5);
        setDeltaMovement((-Mth.sin(getYRot() * Mth.DEG_TO_RAD) * Mth.cos(getXRot() * Mth.DEG_TO_RAD)),
            (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * Mth.cos(getXRot() * Mth.DEG_TO_RAD)),
            (-Mth.sin(getXRot() * Mth.DEG_TO_RAD)));

        setDeltaMovement(getDeltaMovement().scale(speed));
	}

    @Override
	public void tick() {
		super.tick();
		sequence++;
		if (sequence > 15/* > RRConfig.SERVER.getFlamethrowerDecay() */) kill();
		if (tickCount > 5 && random.nextDouble() > 0.5) gonnadie = true;

		if (gonnadie && sequence < 15) sequence++;

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

		if (hitResult.getType() != HitResult.Type.MISS && tickCount >= 6) {
            setPosRaw(getX() - 0.5F, getY() - 0.5, getZ() - 0.5);
			fire();
            setPosRaw(getX() + 1, getY(), getZ());
			fire();
            setPosRaw(getX() - 2, getY(), getZ());
			fire();
            setPosRaw(getX() + 1, getY() + 1, getZ());
			fire();
            setPosRaw(getX(), getY() - 2, getZ());
			fire();
            setPosRaw(getX(), getY() + 1, getZ() + 1);
			fire();
            setPosRaw(getX(), getY(), getZ() - 2);
			fire();
            setPosRaw(getX(), getY(), getZ() + 1);
            setPosRaw(getX() + 0.5, getY() + 0.5, getZ() + 0.5);
			kill();
			if (hitResult.getType() == HitResult.Type.ENTITY)
			{
                Entity entityHit = ((EntityHitResult) hitResult).getEntity();
                entityHit.igniteForSeconds(3);
				entityHit.hurt(RivalRebelsDamageSource.cooked(level()), 12);
				if (entityHit instanceof Player player) {
                    ItemUtil.damageRandomArmor(player, 8, random);
				}
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

		rotation += motionr;
		motionr *= 1.06f;

		if (isInWaterOrBubble()) kill();
		float airFriction = 0.9F;
		if (gonnadie) {
            setDeltaMovement(getDeltaMovement().add(0, 0.05, 0));
			airFriction = 0.7f;
		}
        setDeltaMovement(getDeltaMovement().scale(airFriction));
        reapplyPosition();
	}

    private void fire() {
		if (!level().isClientSide()) {
            BlockState state = level().getBlockState(blockPosition());
            Block id = state.getBlock();
			if (state.isAir() || state.is(BlockTags.SNOW) || state.is(BlockTags.ICE)) level().setBlockAndUpdate(blockPosition(), Blocks.FIRE.defaultBlockState());
			else if (state.is(BlockTags.SAND) && random.nextInt(60) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.GLASS.defaultBlockState());
			else if (state.is(ModBlockTags.GLASS_BLOCKS) && random.nextInt(120) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.OBSIDIAN.defaultBlockState());
			else if (state.is(Blocks.OBSIDIAN) && random.nextInt(90) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if ((state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(Blocks.COBBLESTONE)) && random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.IRON_ORES) && random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.COAL_ORES) && random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.GOLD_ORES) && random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.LAPIS_ORES) && random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(Blocks.GRAVEL) && random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(ModBlockTags.SANDSTONE_BLOCKS) && random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(Blocks.IRON_BLOCK) && random.nextInt(50) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(Blocks.BEDROCK) && random.nextInt(500) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.OBSIDIAN.defaultBlockState());
			else if (state.getFluidState().is(FluidTags.LAVA)) level().setBlockAndUpdate(blockPosition(), Blocks.AIR.defaultBlockState());
			else if (state.is(BlockTags.LEAVES)) level().setBlockAndUpdate(blockPosition(), Blocks.FIRE.defaultBlockState());
			else if (state.is(BlockTags.DIRT)) level().setBlockAndUpdate(blockPosition(), Blocks.FIRE.defaultBlockState());
			else if (state.is(RRBlocks.flare)) id.destroy(level(), blockPosition(), state);
			else if (state.is(RRBlocks.timedbomb)) id.destroy(level(), blockPosition(), state);
			else if (state.is(RRBlocks.remotecharge)) id.destroy(level(), blockPosition(), state);
			else if (state.is(RRBlocks.landmine)) id.destroy(level(), blockPosition(), state);
			else if (state.is(RRBlocks.alandmine)) id.destroy(level(), blockPosition(), state);
			else if (state.is(Blocks.TNT)) {
                TntBlock.explode(level(), blockPosition());
                level().removeBlock(blockPosition(), false);
			}
			else if (state.is(RRBlocks.conduit)) level().setBlockAndUpdate(blockPosition(), Blocks.FIRE.defaultBlockState());
			else if (state.is(RRBlocks.reactive) && random.nextInt(20) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if ((state.is(RRBlocks.camo1) || state.is(RRBlocks.camo2) || state.is(RRBlocks.camo3)) && random.nextInt(40) == 0) level().setBlockAndUpdate(blockPosition(), RRBlocks.steel.defaultBlockState());
			else if (state.is(RRBlocks.steel) && random.nextInt(40) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.FIRE.defaultBlockState());
		}
	}
}

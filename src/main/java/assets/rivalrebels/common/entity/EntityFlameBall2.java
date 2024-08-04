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
import assets.rivalrebels.common.util.ModBlockTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class EntityFlameBall2 extends EntityInanimate
{
	public int		sequence;
	public float	rotation;
	public float	motionr;
	public boolean	gonnadie;

    public EntityFlameBall2(EntityType<? extends EntityFlameBall2> type, Level world) {
        super(type, world);
    }

	public EntityFlameBall2(Level par1World) {
		this(RREntities.FLAME_BALL2, par1World);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall2(Level par1World, double par2, double par4, double par6) {
		this(par1World);
		setPos(par2, par4, par6);
	}

	public EntityFlameBall2(Level par1World, Entity player, float par3)
	{
		this(par1World);
		// par3/=3f;
		setPos(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
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

	public EntityFlameBall2(Level par1World, TileEntityReciever ter, float f)
	{
		this(par1World);
		setYRot(180 - ter.yaw);
		setXRot(-ter.pitch);
		setPos(ter.getBlockPos().getX() + ter.xO + 0.5, ter.getBlockPos().getY() + 0.5, ter.getBlockPos().getZ() + ter.zO + 0.5);
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));

        setDeltaMovement(getDeltaMovement().scale(f));
	}

	public EntityFlameBall2(Level world, double x, double y, double z, double mx, double my, double mz)
	{
		this(world);
		setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
	}

	@Override
	public void tick() {
		super.tick();
		tickCount++;
		sequence++;
		if (sequence > 15/* > RRConfig.SERVER.getFlamethrowerDecay() */) kill();
		if (tickCount > 5 && random.nextDouble() > 0.5) gonnadie = true;

		if (gonnadie && sequence < 15) sequence++;

		Vec3 start = position();
		Vec3 end = position().add(getDeltaMovement());
		HitResult mop = level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

		if (mop != null) end = mop.getLocation();

		Entity e = null;
		List<Entity> var5 = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;
		Iterator<Entity> var8 = var5.iterator();

		if (!level().isClientSide())
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

		if (mop != null && tickCount >= 6) {
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
			if (mop.getType() == HitResult.Type.ENTITY)
			{
                Entity entityHit = ((EntityHitResult) mop).getEntity();
                entityHit.igniteForSeconds(3);
				entityHit.hurt(RivalRebelsDamageSource.cooked(level()), 12);
				if (entityHit instanceof Player player)
				{
                    EquipmentSlot slot = EquipmentSlot.values()[level().random.nextInt(4) + 2];
                    ItemStack equippedStack = player.getItemBySlot(slot);
                    if (!equippedStack.isEmpty() && !level().isClientSide())
					{
						equippedStack.hurtAndBreak(8, player, slot);
					}
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
	public boolean isAttackable()
	{
		return false;
	}

	private void fire()
	{
		if (!level().isClientSide())
		{
            BlockState state = level().getBlockState(blockPosition());
            Block id = state.getBlock();
			if (state.isAir() || state.is(BlockTags.SNOW) || state.is(BlockTags.ICE)) level().setBlockAndUpdate(blockPosition(), Blocks.FIRE.defaultBlockState());
			else if (state.is(BlockTags.SAND) && level().random.nextInt(60) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.GLASS.defaultBlockState());
			else if (state.is(ModBlockTags.GLASS_BLOCKS) && level().random.nextInt(120) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.OBSIDIAN.defaultBlockState());
			else if (state.is(Blocks.OBSIDIAN) && level().random.nextInt(90) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if ((state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(Blocks.COBBLESTONE)) && level().random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.IRON_ORES) && level().random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.COAL_ORES) && level().random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.GOLD_ORES) && level().random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(BlockTags.LAPIS_ORES) && level().random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(Blocks.GRAVEL) && level().random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(ModBlockTags.SANDSTONE_BLOCKS) && level().random.nextInt(30) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(Blocks.IRON_BLOCK) && level().random.nextInt(50) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if (state.is(Blocks.BEDROCK) && level().random.nextInt(500) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.OBSIDIAN.defaultBlockState());
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
			else if (state.is(RRBlocks.reactive) && level().random.nextInt(20) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.LAVA.defaultBlockState());
			else if ((state.is(RRBlocks.camo1) || state.is(RRBlocks.camo2) || state.is(RRBlocks.camo3)) && level().random.nextInt(40) == 0) level().setBlockAndUpdate(blockPosition(), RRBlocks.steel.defaultBlockState());
			else if (state.is(RRBlocks.steel) && level().random.nextInt(40) == 0) level().setBlockAndUpdate(blockPosition(), Blocks.FIRE.defaultBlockState());
		}
	}
}

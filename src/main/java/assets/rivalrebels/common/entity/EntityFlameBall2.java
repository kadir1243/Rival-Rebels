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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraftforge.common.Tags;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class EntityFlameBall2 extends EntityInanimate
{
	public int		sequence;
	public float	rotation;
	public float	motionr;
	public boolean	gonnadie;

    public EntityFlameBall2(EntityType<? extends EntityFlameBall2> type, World world) {
        super(type, world);
    }

	public EntityFlameBall2(World par1World) {
		this(RREntities.FLAME_BALL2, par1World);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall2(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4, par6);
	}

	public EntityFlameBall2(World par1World, Entity player, float par3)
	{
		this(par1World);
		// par3/=3f;
		setPosition(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
		setVelocity((-MathHelper.sin(player.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(player.getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(player.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(player.getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(player.getPitch() / 180.0F * (float) Math.PI)));
        setPos(
            getX() - (MathHelper.cos(player.getYaw() / 180.0F * (float) Math.PI) * 0.2F),
            getY() - 0.13,
            getZ() - (MathHelper.sin(player.getYaw() / 180.0F * (float) Math.PI) * 0.2F)
        );
        setVelocity(getVelocity().multiply(par3));
	}

	public EntityFlameBall2(World par1World, TileEntityReciever ter, float f)
	{
		this(par1World);
		setYaw((float) (180 - ter.yaw));
		setPitch((float) (-ter.pitch));
		setPosition(ter.getPos().getX() + ter.xO + 0.5, ter.getPos().getY() + 0.5, ter.getPos().getZ() + ter.zO + 0.5);
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));

        setVelocity(getVelocity().multiply(f));
	}

	public EntityFlameBall2(World world, double x, double y, double z, double mx, double my, double mz)
	{
		this(world);
		setPosition(x, y, z);
        setVelocity(mx, my, mz);
	}

	@Override
	public void tick() {
		super.tick();
		age++;
		sequence++;
		if (sequence > 15/* > RivalRebels.flamethrowerDecay */) kill();
		if (age > 5 && random.nextDouble() > 0.5) gonnadie = true;

		if (gonnadie && sequence < 15) sequence++;

		Vec3d start = getPos();
		Vec3d end = getPos().add(getVelocity());
		HitResult mop = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

		if (mop != null) end = mop.getPos();

		Entity e = null;
		List<Entity> var5 = world.getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;
		Iterator<Entity> var8 = var5.iterator();

		if (!world.isClient)
		{
			while (var8.hasNext())
			{
				Entity var9 = var8.next();

				if (var9.collides())
				{
					float var10 = 0.3F;
					Box var11 = var9.getBoundingBox().expand(var10, var10, var10);
					Optional<Vec3d> var12 = var11.raycast(start, end);

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

		if (mop != null && age >= 6) {
            setPos(getX() - 0.5F, getY() - 0.5, getZ() - 0.5);
			fire();
            setPos(getX() + 1, getY(), getZ());
			fire();
            setPos(getX() - 2, getY(), getZ());
			fire();
            setPos(getX() + 1, getY() + 1, getZ());
			fire();
            setPos(getX(), getY() - 2, getZ());
			fire();
            setPos(getX(), getY() + 1, getZ() + 1);
			fire();
            setPos(getX(), getY(), getZ() - 2);
			fire();
            setPos(getX(), getY(), getZ() + 1);
            setPos(getX() + 0.5, getY() + 0.5, getZ() + 0.5);
			kill();
			if (mop.getType() == HitResult.Type.ENTITY)
			{
                Entity entityHit = ((EntityHitResult) mop).getEntity();
                entityHit.setOnFireFor(3);
				entityHit.damage(RivalRebelsDamageSource.cooked, 12);
				if (entityHit instanceof PlayerEntity player)
				{
					EquipmentSlot equipmentSlot = EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, world.random.nextInt(4));
                    ItemStack equippedStack = player.getEquippedStack(equipmentSlot);
                    if (!equippedStack.isEmpty() && !world.isClient)
					{
						equippedStack.damage(8, player, player1 -> player1.sendEquipmentBreakStatus(equipmentSlot));
					}
				}
			}
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());

		rotation += motionr;
		motionr *= 1.06f;

		if (isInsideWaterOrBubbleColumn()) kill();
		float airFriction = 0.9F;
		if (gonnadie) {
            setVelocity(getVelocity().add(0, 0.05, 0));
			airFriction = 0.7f;
		}
        setVelocity(getVelocity().multiply(airFriction));
		setPosition(getX(), getY(), getZ());
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000;
	}

	@Override
	public boolean shouldRender(double distance)
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
		if (!world.isClient)
		{
            BlockState state = world.getBlockState(getBlockPos());
            Block id = state.getBlock();
			if (state.isAir() || id == Blocks.SNOW || state.isIn(BlockTags.ICE)) world.setBlockState(getBlockPos(), Blocks.FIRE.getDefaultState());
			else if (state.isIn(BlockTags.SAND) && world.random.nextInt(60) == 0) world.setBlockState(getBlockPos(), Blocks.GLASS.getDefaultState());
			else if (state.isIn(Tags.Blocks.GLASS) && world.random.nextInt(120) == 0) world.setBlockState(getBlockPos(), Blocks.OBSIDIAN.getDefaultState());
			else if (state.isIn(Tags.Blocks.OBSIDIAN) && world.random.nextInt(90) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if ((state.isIn(BlockTags.BASE_STONE_OVERWORLD) || state.isIn(Tags.Blocks.COBBLESTONE)) && world.random.nextInt(30) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (state.isIn(Tags.Blocks.ORES_IRON) && world.random.nextInt(30) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (state.isIn(Tags.Blocks.ORES_COAL) && world.random.nextInt(30) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (state.isIn(Tags.Blocks.ORES_GOLD) && world.random.nextInt(30) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (state.isIn(Tags.Blocks.ORES_LAPIS) && world.random.nextInt(30) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (state.isIn(Tags.Blocks.GRAVEL) && world.random.nextInt(30) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (state.isIn(Tags.Blocks.SANDSTONE) && world.random.nextInt(30) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (state.isIn(Tags.Blocks.STORAGE_BLOCKS_IRON) && world.random.nextInt(50) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if (id == Blocks.BEDROCK && world.random.nextInt(500) == 0) world.setBlockState(getBlockPos(), Blocks.OBSIDIAN.getDefaultState());
			else if (state.getFluidState().isIn(FluidTags.LAVA)) world.setBlockState(getBlockPos(), Blocks.AIR.getDefaultState());
			else if (state.isIn(BlockTags.LEAVES)) world.setBlockState(getBlockPos(), Blocks.FIRE.getDefaultState());
			else if (state.isIn(BlockTags.DIRT)) world.setBlockState(getBlockPos(), Blocks.FIRE.getDefaultState());
			else if (id == RRBlocks.flare) id.onBroken(world, getBlockPos(), state);
			else if (id == RRBlocks.timedbomb) id.onBroken(world, getBlockPos(), state);
			else if (id == RRBlocks.remotecharge) id.onBroken(world, getBlockPos(), state);
			else if (id == RRBlocks.landmine) id.onBroken(world, getBlockPos(), state);
			else if (id == RRBlocks.alandmine) id.onBroken(world, getBlockPos(), state);
			else if (id == Blocks.TNT)
			{
				world.setBlockState(getBlockPos(), Blocks.AIR.getDefaultState());
				world.createExplosion(null, getX(), getY(), getZ(), 4, Explosion.DestructionType.NONE);
			}
			else if (id == RRBlocks.conduit) world.setBlockState(getBlockPos(), Blocks.FIRE.getDefaultState());
			else if (id == RRBlocks.reactive && world.random.nextInt(20) == 0) world.setBlockState(getBlockPos(), Blocks.LAVA.getDefaultState());
			else if ((id == RRBlocks.camo1 || id == RRBlocks.camo2 || id == RRBlocks.camo3) && world.random.nextInt(40) == 0) world.setBlockState(getBlockPos(), RRBlocks.steel.getDefaultState());
			else if (id == RRBlocks.steel && world.random.nextInt(40) == 0) world.setBlockState(getBlockPos(), Blocks.FIRE.getDefaultState());
		}
	}
}

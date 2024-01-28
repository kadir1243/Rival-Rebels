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
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityFlameBall1 extends EntityInanimate
{
	public int		sequence;
	public float	rotation;
	public float	motionr;

    public EntityFlameBall1(EntityType<? extends EntityFlameBall1> type, World world) {
        super(type, world);
    }

	public EntityFlameBall1(World par1World) {
		this(RREntities.FLAME_BALL1, par1World);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall1(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4, par6);
	}

	public EntityFlameBall1(World par1World, Entity player, float par3) {
		this(par1World);
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

	public EntityFlameBall1(World par1World, TileEntityReciever ter, float f) {
		this(par1World);
		setYaw((float) (180 - ter.yaw));
		setPitch((float) (-ter.pitch));
		setPosition(ter.getPos().getX() + ter.xO + 0.5, ter.getPos().getY() + 0.75, ter.getPos().getZ() + ter.zO + 0.5);
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));

        setVelocity(getVelocity().multiply(f));
	}

	public EntityFlameBall1(World world, double x, double y, double z, double mx, double my, double mz)
	{
		this(world);
		setPosition(x, y, z);
        setVelocity(mx, my, mz);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	@Override
	public void tick() {
		super.tick();
		age++;
		if (age > 5) sequence++;
		if (sequence > 15/* > RivalRebels.flamethrowerDecay */) kill();

		Vec3d start = getPos();
		Vec3d end = getPos().add(getVelocity());
		HitResult mop = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		start = getPos();
		end = getPos().add(getVelocity());

		if (mop != null) end = mop.getPos();

		Entity e = null;
		List<Entity> var5 = world.getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;

		if (!world.isClient)
		{
            for (Entity var9 : var5) {
                if (var9.collides()) {
                    float var10 = 0.3F;
                    Box var11 = var9.getBoundingBox().expand(var10, var10, var10);
                    Optional<Vec3d> var12 = var11.raycast(start, end);

                    if (var12.isPresent()) {
                        double var13 = start.distanceTo(var12.get());

                        if (var13 < var6 || var6 == 0.0D) {
                            e = var9;
                            var6 = var13;
                        }
                    }
                }
            }
		}

		if (e != null)
		{
			mop = new EntityHitResult(e, mop.getPos());
		}

		if (mop != null && mop.getType() == HitResult.Type.ENTITY && age >= 5)
		{
			fire();
			kill();
			if (((EntityHitResult) mop).getEntity() != null)
			{
				((EntityHitResult) mop).getEntity().setOnFireFor(3);
				((EntityHitResult) mop).getEntity().damage(RivalRebelsDamageSource.cooked, 12);
				if (((EntityHitResult) mop).getEntity() instanceof PlayerEntity player) {
                    EquipmentSlot slot = EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, world.random.nextInt(4));
					if (!player.getEquippedStack(slot).isEmpty() && !world.isClient)
					{
						player.getEquippedStack(slot).damage(8, player, player1 -> player1.sendEquipmentBreakStatus(slot));
					}
				}
			}
			else if (mop.getType() == BlockHitResult.Type.ENTITY)
			{
				world.spawnEntity(new EntityLightningLink(world, this, this.distanceTo(((EntityHitResult) mop).getEntity())));
				if (((EntityHitResult) mop).getEntity() instanceof PlayerEntity player)
				{
                    EquipmentSlot slot = EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, world.random.nextInt(4));
					if (!player.getEquippedStack(slot).isEmpty()) {
						player.getEquippedStack(slot).damage(44, player, player1 -> player1.sendEquipmentBreakStatus(slot));
					}
					player.damage(RivalRebelsDamageSource.cooked, 250 / ((int) ((EntityHitResult) mop).getEntity().distanceTo(this) + 1));
				}
				else if (((EntityHitResult) mop).getEntity() instanceof EntityB2Spirit)
				{
					((EntityHitResult) mop).getEntity().damage(RivalRebelsDamageSource.cooked, 250 / ((int) ((EntityHitResult) mop).getEntity().distanceTo(this) + 1));
				}
				else if (((EntityHitResult) mop).getEntity().isAttackable())
				{
					((EntityHitResult) mop).getEntity().damage(RivalRebelsDamageSource.cooked, 250 / ((int) ((EntityHitResult) mop).getEntity().distanceTo(this) + 1));
				}
			}
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		rotation += motionr;
		motionr *= 1.06f;

		if (isInsideWaterOrBubbleColumn()) kill();
		float airFriction = 0.97F;
		float gravity = 0.01F;
        setVelocity(getVelocity().multiply(airFriction));
        setVelocity(getVelocity().subtract(0, gravity, 0));
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
			for (int x = -1; x < 2; x++)
			{
				for (int y = -1; y < 2; y++)
				{
					for (int z = -1; z < 2; z++)
					{
                        BlockPos pos = new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z);
                        BlockState state = world.getBlockState(pos);
                        Block id = state.getBlock();
						if (id == Blocks.AIR || id == Blocks.SNOW || state.isIn(BlockTags.ICE)) world.setBlockState(pos, Blocks.FIRE.getDefaultState());
						else if (state.isIn(BlockTags.LEAVES)) world.setBlockState(pos, Blocks.FIRE.getDefaultState());
						else if (id == Blocks.GRASS && world.random.nextInt(5) == 0) world.setBlockState(pos, Blocks.DIRT.getDefaultState());
						else if (id == RRBlocks.flare) id.onBroken(world, pos, state);
					}
				}
			}
		}
	}
}

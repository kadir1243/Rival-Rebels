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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class EntityFlameBall extends EntityInanimate
{
	public int		sequence;
	public float	rotation;
	public float	motionr;

    public EntityFlameBall(EntityType<? extends EntityFlameBall> entityType, World world) {
        super(entityType, world);
    }

	public EntityFlameBall(World par1World)
	{
		this(RREntities.FLAME_BALL, par1World);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		setPosition(par2, par4, par6);
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall(World par1World, Entity player, float par3)
	{
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
		rotation = (float) (random.nextDouble() * 360);
		motionr = (float) (random.nextDouble() - 0.5f) * 5;
	}

	public EntityFlameBall(World par1World, TileEntityReciever ter, float f)
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

	public EntityFlameBall(World world, double x, double y, double z, double mx, double my, double mz)
	{
		this(world);
		setPosition(x, y, z);
        setVelocity(mx, my, mz);
	}

	public EntityFlameBall(World world, double x, double y, double z, double mx, double my, double mz, double d, double r) {
		this(world);
		setPosition(x+mx*d+random.nextGaussian()*r, y+my*d+random.nextGaussian()*r, z+mz*d+random.nextGaussian()*r);
        setVelocity(mx, my, mz);
	}

	@Override
	public void tick() {
		super.tick();
		age++;
		if (age % 3 == 0) sequence++;
		if (sequence > 15/* > RivalRebels.flamethrowerDecay */) kill();

		Vec3d start = getPos();
		Vec3d end = getPos().add(getVelocity());
		HitResult mop = getWorld().raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

		if (mop != null) end = mop.getPos();

		Entity e = null;
		List<Entity> var5 = getWorld().getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;
		Iterator<Entity> var8 = var5.iterator();

		if (!getWorld().isClient)
		{
			while (var8.hasNext())
			{
				Entity var9 = var8.next();

				if (var9.isCollidable())
				{
					float var10 = 0.3F;
					Box var11 = var9.getBoundingBox().expand(var10, var10, var10);
					Optional<Vec3d> var12 = var11.raycast(start, end);

					if (var12.isPresent()) {
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

		if (mop != null && age >= 5)
		{
			fire();
			kill();
			if (mop.getType() == HitResult.Type.ENTITY)
			{
                Entity hitEntity = ((EntityHitResult) mop).getEntity();
                hitEntity.setOnFireFor(3);
				hitEntity.damage(RivalRebelsDamageSource.cooked(getWorld()), 12);
				if (hitEntity instanceof PlayerEntity player)
				{
                    EquipmentSlot equipmentSlot = EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, getWorld().random.nextInt(4));
                    ItemStack equippedStack = player.getEquippedStack(equipmentSlot);
                    if (!equippedStack.isEmpty() && !getWorld().isClient) {
						equippedStack.damage(8, player, player1 -> player1.sendEquipmentBreakStatus(equipmentSlot));
					}
				}
			}
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());

		rotation += motionr;
		motionr *= 1.06f;

		if (isInsideWaterOrBubbleColumn()) kill();
		float airFriction = 0.96F;
		float gravity = 0.01F;
        setVelocity(getVelocity().multiply(airFriction));
        setVelocity(getVelocity().subtract(0, gravity, 0));
		setPosition(getX(), getY(), getZ());
	}

	@Override
	public float getBrightnessAtEyes() {
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
		if (!getWorld().isClient)
		{
			for (int x = -1; x < 2; x++)
			{
				for (int y = -1; y < 2; y++)
				{
					for (int z = -1; z < 2; z++)
					{
                        BlockPos pos = new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z);
                        BlockState state = getWorld().getBlockState(pos);
                        Block id = state.getBlock();
						if (getWorld().isAir(pos) || id == Blocks.SNOW || state.isIn(BlockTags.ICE) || state.isIn(BlockTags.LEAVES)) getWorld().setBlockState(pos, Blocks.FIRE.getDefaultState());
					}
				}
			}
		}
	}
}

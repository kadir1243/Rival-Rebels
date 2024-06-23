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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityCuchillo extends EntityInanimate
{
	public Entity	shootingEntity;
	private boolean	inGround;
	private int		ticksInGround;

    public EntityCuchillo(EntityType<? extends EntityCuchillo> type, World par1World) {
        super(type, par1World);
    }

	public EntityCuchillo(World par1World) {
		this(RREntities.CUCHILLO, par1World);
	}

	public EntityCuchillo(World par1World, PlayerEntity player, float par3)
	{
		this(par1World);
		shootingEntity = player;
		refreshPositionAndAngles(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYaw(), player.getPitch());
        setPos(getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F),
            getY() - 0.1f,
            getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F));
		setPosition(getX(), getY(), getZ());
		setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)) * par3,
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)) * par3,
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)) * par3);
	}
	public EntityCuchillo(World par1World, double x, double y,double z, double mx, double my, double mz)
	{
		this(par1World);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz) {
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
	public void tick()
	{
		super.tick();
		age++;
		if (!inGround)
		{
			Vec3d vec31 = getPos();
			Vec3d vec3 = getPos().add(getVelocity());
			HitResult mop = getWorld().raycast(new RaycastContext(vec31, vec3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
			vec31 = getPos();
			if (mop != null) vec3 = mop.getPos();
			else vec3 = getPos().add(getVelocity());

			List<Entity> list = getWorld().getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
            for (Entity entity : list) {
                if (entity.isCollidable() && (age >= 10 || entity != shootingEntity)) {
                    Optional<Vec3d> mop1 = entity.getBoundingBox().expand(0.5f, 0.5f, 0.5f).raycast(vec31, vec3);
                    if (mop1.isPresent()) {
                        double d1 = vec31.squaredDistanceTo(mop1.get());
                        if (d1 < d0) {
                            mop = new EntityHitResult(entity, mop1.get());
                            d0 = d1;
                        }
                    }
                }
            }

			if (mop != null)
			{
				if (!getWorld().isClient)
				{
					if (mop.getType() == HitResult.Type.ENTITY)
					{
                        ((EntityHitResult) mop).getEntity().damage(RivalRebelsDamageSource.cuchillo(getWorld()), 7);
						kill();
					}
					else if (mop.getType() == HitResult.Type.BLOCK)
					{
                        BlockPos pos = ((BlockHitResult) mop).getBlockPos();
                        BlockState state = getWorld().getBlockState(pos);
						if (state.isIn(ConventionalBlockTags.GLASS_BLOCKS) || state.isIn(ConventionalBlockTags.GLASS_PANES)) {
							getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
							RivalRebelsSoundPlayer.playSound(this, 4, 0, 5F, 0.3F);
                        }
						else if (state.isIn(BlockTags.BASE_STONE_OVERWORLD))
						{
							getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), RRItems.knife.getDefaultStack()));
							kill();
						}
						else
						{
							inGround = true;
						}
					}
				}
			}
			else
			{
                setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
				setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));
				while (getYaw() - prevYaw < -180.0F)
					prevYaw -= 360.0F;
				while (getYaw() - prevYaw >= 180.0F)
					prevYaw += 360.0F;
                setPitch(getPitch() - 30);
				setYaw(prevYaw + (getYaw() - prevYaw) * 0.2F);

				float friction = 0.98f;

				if (isInsideWaterOrBubbleColumn())
				{
					for (int var26 = 0; var26 < 4; ++var26)
						getWorld().addParticle(ParticleTypes.BUBBLE, getX() - getVelocity().getX() * 0.25F, getY() - getVelocity().getY() * 0.25F, getZ() - getVelocity().getZ() * 0.25F, getVelocity().getX(), getVelocity().getY(), getVelocity().getZ());
					friction = 0.8F;
				}
                setVelocity(getVelocity().multiply(friction));
                setVelocity(getVelocity().subtract(0, 0.026F, 0));
				setPosition(getX(), getY(), getZ());
			}
		}
		else
		{
            setVelocity(getVelocity().multiply(0.2));
			ticksInGround++;
			if (ticksInGround == 60)
			{
				getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), RRItems.knife.getDefaultStack()));
				kill();
			}
		}
	}

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		if (!getWorld().isClient && inGround) {
			if (!player.getAbilities().invulnerable) player.getInventory().insertStack(RRItems.knife.getDefaultStack());
			getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.NEUTRAL, 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F, true);
			kill();
		}
	}

	@Override
	public boolean isAttackable()
	{
		return false;
	}
}

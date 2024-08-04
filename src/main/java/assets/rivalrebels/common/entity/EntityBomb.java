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
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityBomb extends ThrowableProjectile {
	public int	ticksInAir	= 0;
	public int timeleft = 20;
	public boolean exploded = false;
	public boolean hit = false;

	public EntityBomb(EntityType<? extends EntityBomb> type, Level par1World) {
		super(type, par1World);
	}

    public EntityBomb(Level par1World) {
        this(RREntities.BOMB, par1World);
    }

	public EntityBomb(Level par1World, double x, double y, double z, float yaw, float pitch) {
		this(par1World);
		moveTo(x, y, z, yaw, pitch);
		setDeltaMovement(-(-Mth.sin(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)),
            (Mth.cos(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)),
            (-Mth.sin(pitch / 180.0F * Mth.PI)));
	}

	public EntityBomb(Level par1World, double x, double y,double z, double mx, double my, double mz)
	{
		this(par1World);
		setPos(x+mx*1.4f,y+my*1.4f,z+mz*1.4f);
		setAnglesMotion(mx, my, mz);
	}

	public EntityBomb(Level par1World, Player entity2, float par3) {
		this(par1World);
		moveTo(entity2.getX(), entity2.getY() + entity2.getEyeHeight(entity2.getPose()), entity2.getZ(), entity2.getYRot(), entity2.getXRot());
		setPos(getX(), getY(), getZ());
		setDeltaMovement((-Mth.sin(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * Mth.cos(getXRot() / 180.0F * Mth.PI)),
            (-Mth.sin(getXRot() / 180.0F * Mth.PI)));
        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
        shoot(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), 2.5f, 0.1f);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setDeltaMovement(mx, my, mz);
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
		if (ticksInAir == - 100) explode(false);
		++this.ticksInAir;

		if (exploded) {
            setDeltaMovement(0, hit ? 1 : 0, 0);
			timeleft--;
			if (timeleft < 0) kill();
			tickCount++;
		}
		else
		{
			Vec3 var15 = position();
			Vec3 var2 = position().add(getDeltaMovement());
			HitResult var3 = this.level().clip(new ClipContext(var15, var2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			var15 = position();
			var2 = position().add(getDeltaMovement());

			if (var3 != null)
			{
				var2 = var3.getLocation();
			}

			if (!this.level().isClientSide())
			{
				Entity var4 = null;
				List<Entity> var5 = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
				double var6 = 0.0D;

                for (Entity var9 : var5) {
                    if (var9.canBeCollidedWith()) {
                        float var10 = 0.3F;
                        AABB var11 = var9.getBoundingBox().inflate(var10, var10, var10);
                        Optional<Vec3> var12 = var11.clip(var15, var2);

                        if (var12.isPresent()) {
                            double var13 = var15.distanceTo(var12.get());

                            if (var13 < var6 || var6 == 0.0D) {
                                var4 = var9;
                                var6 = var13;
                            }
                        }
                    }
                }

				if (var4 != null)
				{
					var3 = new EntityHitResult(var4);
				}
			}

			if (var3 != null)
			{
				this.onHit(var3);
			}

            setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
			float var16 = Mth.sqrt((float) (this.getDeltaMovement().x() * this.getDeltaMovement().x() + this.getDeltaMovement().z() * this.getDeltaMovement().z()));
			this.setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * Mth.RAD_TO_DEG));

			for (this.setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * Mth.RAD_TO_DEG)); this.getXRot() - this.xRotO < -180.0F; this.xRotO -= 360.0F)
			{
            }

			while (this.getXRot() - this.xRotO >= 180.0F)
			{
				this.xRotO += 360.0F;
			}

			while (this.getYRot() - this.yRotO < -180.0F)
			{
				this.yRotO -= 360.0F;
			}

			while (this.getYRot() - this.yRotO >= 180.0F)
			{
				this.yRotO += 360.0F;
			}

			this.setXRot(this.xRotO + (this.getXRot() - this.xRotO) * 0.2F);
			this.setYRot(this.yRotO + (this.getYRot() - this.yRotO) * 0.2F);
			float var17 = 0.95f;
			float var18 = (float) this.getGravity();

            setDeltaMovement(getDeltaMovement().scale(var17));
            setDeltaMovement(getDeltaMovement().subtract(0, var18, 0));
		}
		this.setPos(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.hurt(RivalRebelsDamageSource.rocket(level()), (entity instanceof Player ? 20 : 300));
        explode(true);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        explode(false);
    }

    @Override
    protected double getDefaultGravity() {
		return 0.1F;
	}

	public void explode(boolean b)
	{
		exploded = true;
		hit = b;
		tickCount = 0;
		if (random.nextDouble() > 0.8f) RivalRebelsSoundPlayer.playSound(this, 23, 0, 20, 0.4f + (float)random.nextDouble() * 0.3f);
		if (!level().isClientSide && !b)
		{
			int r = 2;
			for (int x = -r; x <= r; x++)
			{
				for (int y = -r; y <= r; y++)
				{
					for (int z = -r; z <= r; z++)
					{
						level().setBlockAndUpdate(new BlockPos((int)(getX()+x), (int)(Math.max(getY(), r+1)+y), (int)(getZ()+z)), Blocks.AIR.defaultBlockState());
					}
				}
			}
		}
	}
}

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
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
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

public class EntityBomb extends ThrownEntity {
	public int	ticksInAir	= 0;
	public int timeleft = 20;
	public boolean exploded = false;
	public boolean hit = false;

	public EntityBomb(EntityType<? extends EntityBomb> type, World par1World) {
		super(type, par1World);
	}

    public EntityBomb(World par1World) {
        this(RREntities.BOMB, par1World);
    }

	public EntityBomb(World par1World, double x, double y, double z, float yaw, float pitch) {
		this(par1World);
		refreshPositionAndAngles(x, y, z, yaw, pitch);
		setVelocity(-(-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)),
            (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(pitch / 180.0F * (float) Math.PI)));
	}

	public EntityBomb(World par1World, double x, double y,double z, double mx, double my, double mz)
	{
		this(par1World);
		setPosition(x+mx*1.4f,y+my*1.4f,z+mz*1.4f);
		setAnglesMotion(mx, my, mz);
	}

	public EntityBomb(World par1World, PlayerEntity entity2, float par3) {
		this(par1World);
		refreshPositionAndAngles(entity2.getX(), entity2.getY() + entity2.getEyeHeight(entity2.getPose()), entity2.getZ(), entity2.getYaw(), entity2.getPitch());
		setPosition(getX(), getY(), getZ());
		setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));
        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
        setVelocity(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), 2.5f, 0.1f);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setVelocity(mx, my, mz);
		setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
    protected void initDataTracker() {

    }

    @Override
	public void tick() {
		if (ticksInAir == - 100) explode(false);
		++this.ticksInAir;

		if (exploded) {
            setVelocity(0, hit ? 1 : 0, 0);
			timeleft--;
			if (timeleft < 0) kill();
			age++;
		}
		else
		{
			Vec3d var15 = getPos();
			Vec3d var2 = getPos().add(getVelocity());
			HitResult var3 = this.world.raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
			var15 = getPos();
			var2 = getPos().add(getVelocity());

			if (var3 != null)
			{
				var2 = var3.getPos();
			}

			if (!this.world.isClient)
			{
				Entity var4 = null;
				List<Entity> var5 = world.getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
				double var6 = 0.0D;

                for (Entity var9 : var5) {
                    if (var9.collides()) {
                        float var10 = 0.3F;
                        Box var11 = var9.getBoundingBox().expand(var10, var10, var10);
                        Optional<Vec3d> var12 = var11.raycast(var15, var2);

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
				this.onCollision(var3);
			}

            setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
			float var16 = MathHelper.sqrt((float) (this.getVelocity().getX() * this.getVelocity().getX() + this.getVelocity().getZ() * this.getVelocity().getZ()));
			this.setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));

			for (this.setPitch((float) (Math.atan2(getVelocity().getY(), var16) * 180.0D / Math.PI)); this.getPitch() - this.prevPitch < -180.0F; this.prevPitch -= 360.0F)
			{
            }

			while (this.getPitch() - this.prevPitch >= 180.0F)
			{
				this.prevPitch += 360.0F;
			}

			while (this.getYaw() - this.prevYaw < -180.0F)
			{
				this.prevYaw -= 360.0F;
			}

			while (this.getYaw() - this.prevYaw >= 180.0F)
			{
				this.prevYaw += 360.0F;
			}

			this.setPitch(this.prevPitch + (this.getPitch() - this.prevPitch) * 0.2F);
			this.setYaw(this.prevYaw + (this.getYaw() - this.prevYaw) * 0.2F);
			float var17 = 0.95f;
			float var18 = this.getGravity();

            setVelocity(getVelocity().multiply(var17));
            setVelocity(getVelocity().subtract(0, var18, 0));
		}
		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.damage(RivalRebelsDamageSource.rocket, (entity instanceof PlayerEntity ? 20 : 300));
        explode(true);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        explode(false);
    }

	@Override
	protected float getGravity()
	{
		return 0.1F;
	}

	public void explode(boolean b)
	{
		exploded = true;
		hit = b;
		age = 0;
		if (random.nextDouble() > 0.8f) RivalRebelsSoundPlayer.playSound(this, 23, 0, 20, 0.4f + (float)random.nextDouble() * 0.3f);
		if (!world.isClient && !b)
		{
			int r = 2;
			for (int x = -r; x <= r; x++)
			{
				for (int y = -r; y <= r; y++)
				{
					for (int z = -r; z <= r; z++)
					{
						world.setBlockState(new BlockPos((int)(getX()+x), (int)(Math.max(getY(), r+1)+y), (int)(getZ()+z)), Blocks.AIR.getDefaultState());
					}
				}
			}
		}
	}
}

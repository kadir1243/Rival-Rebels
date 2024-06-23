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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityRoddiskRebel extends RoddiskBase {
    public EntityRoddiskRebel(EntityType<? extends EntityRoddiskRebel> type, World world) {
        super(type, world);
    }

    public EntityRoddiskRebel(World par1World) {
        this(RREntities.RODDISK_REBEL, par1World);
    }

	public EntityRoddiskRebel(World world, PlayerEntity shooter, float par3) {
		super(RREntities.RODDISK_REBEL, world, shooter);
		this.refreshPositionAndAngles(shooter.getX(), shooter.getY() + shooter.getEyeHeight(shooter.getPose()), shooter.getZ(), shooter.getYaw(), shooter.getPitch());
		        setPos(getX() - (MathHelper.cos(this.getYaw() / 180.0F * (float) Math.PI) * 0.16F),
            getY() - 0.1,
            getZ() - (MathHelper.sin(this.getYaw() / 180.0F * (float) Math.PI) * 0.16F)
        );

		this.setPosition(this.getX(), this.getY(), this.getZ());
		setVelocity((-MathHelper.sin(this.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(this.getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(this.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(this.getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(this.getPitch() / 180.0F * (float) Math.PI)));
		this.setHeading(this.getVelocity().getX(), this.getVelocity().getY(), this.getVelocity().getZ(), par3 * 1.5F, 1.0F);
	}

	public void setHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += this.random.nextGaussian() * 0.0075 * par8;
		par3 += this.random.nextGaussian() * 0.0075 * par8;
		par5 += this.random.nextGaussian() * 0.0075 * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		setVelocity(par1, par3, par5);
		float var10 = MathHelper.sqrt((float) (par1 * par1 + par5 * par5));
		this.setYaw(prevYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
		this.setPitch(prevPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
	}

    @Override
	public void tick() {
		if (age > 100 && shooter == null && !getWorld().isClient)
		{
			//world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(RivalRebels.roddisk)));
			kill();
			RivalRebelsSoundPlayer.playSound(this, 5, 0);
		}
		if (age >= 100 && !getWorld().isClient && shooter != null)
		{
			ItemEntity ei = new ItemEntity(getWorld(), shooter.getX(), shooter.getY(), shooter.getZ(), new ItemStack(RRItems.roddisk));
			getWorld().spawnEntity(ei);
			kill();
			RivalRebelsSoundPlayer.playSound(this, 7, 1);
		}
		if (age == 10)
		{
			RivalRebelsSoundPlayer.playSound(this, 7, 0);
		}

		int radius = 2;
		int nx = MathHelper.floor(getX() - radius - 1.0D);
		int px = MathHelper.floor(getX() + radius + 1.0D);
		int ny = MathHelper.floor(getY() - radius - 1.0D);
		int py = MathHelper.floor(getY() + radius + 1.0D);
		int nz = MathHelper.floor(getZ() - radius - 1.0D);
		int pz = MathHelper.floor(getZ() + radius + 1.0D);

        getWorld().getOtherEntities(null, new Box(nx, ny, nz, px, py, pz), entity -> entity instanceof ArrowEntity).forEach(Entity::kill);

		Vec3d var15 = getPos();
		Vec3d var2 = getPos().add(getVelocity());
		HitResult var3 = this.getWorld().raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

		if (var3 != null)
		{
			var2 = var3.getPos();
		}

		if (!this.getWorld().isClient)
		{
			Entity var4 = null;
			List<Entity> var5 = this.getWorld().getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9 instanceof EntityRoddiskRegular) {
                    var9.kill();
                    ItemEntity ei = new ItemEntity(getWorld(), var9.getX(), var9.getY(), var9.getZ(), new ItemStack(RRItems.roddisk));
                    getWorld().spawnEntity(ei);
                } else if (var9 instanceof EntityRoddiskRebel) {
                    if (this.getVelocity().getX() + this.getVelocity().getY() + this.getVelocity().getZ() >= var9.getVelocity().getX() + var9.getVelocity().getY() + var9.getVelocity().getZ()) {
                        var9.kill();
                    } else {
                        kill();
                    }
                    ItemEntity ei = new ItemEntity(getWorld(), var9.getX(), var9.getY(), var9.getZ(), new ItemStack(RRItems.roddisk));
                    getWorld().spawnEntity(ei);
                } else if (var9.isCollidable() && var9 != this.shooter) {
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
			getWorld().addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);
			getWorld().addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);
			getWorld().addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);
			getWorld().addParticle(ParticleTypes.EXPLOSION, var3.getPos().x, var3.getPos().y, var3.getPos().z, getVelocity().getX() * 0.1, getVelocity().getY() * 0.1, getVelocity().getZ() * 0.1);

			if (var3.getType() == HitResult.Type.ENTITY) {
                Entity entityHit = ((EntityHitResult) var3).getEntity();
                RivalRebelsSoundPlayer.playSound(this, 5, 1);
				if (entityHit instanceof PlayerEntity entityPlayerHit && shooter instanceof PlayerEntity && entityHit != shooter)
				{
                    for (ItemStack armorSlot : entityPlayerHit.getInventory().armor) {
                        if (!armorSlot.isEmpty()) {
                            armorSlot.damage(10, entityPlayerHit, player -> {});
                            entityPlayerHit.damage(RivalRebelsDamageSource.tron(getWorld()), 1);
                        } else {
                            entityPlayerHit.damage(RivalRebelsDamageSource.tron(getWorld()), 10);
                        }
                    }
				}
				else
				{
					entityHit.damage(RivalRebelsDamageSource.tron(getWorld()), 10);
					if (entityHit instanceof SkeletonEntity)
					{
						entityHit.kill();
						this.getWorld().spawnEntity(new EntityGore(getWorld(), entityHit, 0, 3));
						this.getWorld().spawnEntity(new EntityGore(getWorld(), entityHit, 1, 3));
						this.getWorld().spawnEntity(new EntityGore(getWorld(), entityHit, 2, 3));
						this.getWorld().spawnEntity(new EntityGore(getWorld(), entityHit, 2, 3));
						this.getWorld().spawnEntity(new EntityGore(getWorld(), entityHit, 3, 3));
						this.getWorld().spawnEntity(new EntityGore(getWorld(), entityHit, 3, 3));
					}
				}
			}
			else if (var3.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ((BlockHitResult) var3).getBlockPos();
                Direction side = ((BlockHitResult) var3).getSide();
                BlockState state = getWorld().getBlockState(pos);
                if (state.getBlock() == RRBlocks.flare)
                {
                    state.getBlock().onBroken(getWorld(), pos, state);
                }
                else if (state.getBlock() == RRBlocks.landmine || state.getBlock() == RRBlocks.alandmine)
                {
                    state.onEntityCollision(getWorld(), pos, this);
                }
                else
                {
                    if (state.isIn(ConventionalBlockTags.GLASS_BLOCKS) || state.isIn(ConventionalBlockTags.GLASS_PANES))
                    {
                        getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                    RivalRebelsSoundPlayer.playSound(this, 5, 2);

                    if (side == Direction.EAST || side == Direction.WEST) this.setVelocity(getVelocity().multiply(-1, 1, 1));
                    if (side == Direction.UP || side == Direction.DOWN) this.setVelocity(getVelocity().multiply(1, -1, 1));
                    if (side == Direction.NORTH || side == Direction.SOUTH) this.setVelocity(getVelocity().multiply(1, 1, -1));
                }
            }
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

		this.setPitch((this.prevPitch + (this.getPitch() - this.prevPitch) * 0.2F));
		this.setYaw(this.prevYaw + (this.getYaw() - this.prevYaw) * 0.2F);

		if (shooter != null)
		{
            setVelocity(getVelocity().add(
                (shooter.getX() - getX()) * 0.01f,
                ((shooter.getY() + 1.62) - getY()) * 0.01f,
                (shooter.getZ() - getZ()) * 0.01f
            ));
		}
        setVelocity(getVelocity().multiply(0.995f));

		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
		if (age < 10) return ActionResult.PASS;
		if (player.getInventory().insertStack(new ItemStack(RRItems.roddisk)))
		{
			kill();
			RivalRebelsSoundPlayer.playSound(this, 7, 1);
		}
		return ActionResult.success(getWorld().isClient);
	}

}

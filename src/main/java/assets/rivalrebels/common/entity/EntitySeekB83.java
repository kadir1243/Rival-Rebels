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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class EntitySeekB83 extends PersistentProjectileEntity {
    public boolean				fins			= false;
	public int					rotation		= 45;
	public float				slide			= 0;
	private boolean				inwaterprevtick	= false;
	private int					soundfile		= 0;

    public EntitySeekB83(EntityType<? extends EntitySeekB83> type, World world) {
        super(type, world);
    }

	public EntitySeekB83(World par1World) {
		this(RREntities.SEEK_B83, par1World);
	}

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    public EntitySeekB83(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4, par6);
	}

	public EntitySeekB83(World par1World, PlayerEntity entity2, float par3) {
		this(par1World);
		fins = false;
		refreshPositionAndAngles(entity2.getX(), entity2.getY() + entity2.getEyeHeight(entity2.getPose()), entity2.getZ(), entity2.getYaw(), entity2.getPitch());
        setPos(
            getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F),
            getY(),
            getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F)
        );
		setPosition(getX(), getY(), getZ());
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));

        setVelocity(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), 0.5f, 1f);
	}

	public EntitySeekB83(World par1World, PlayerEntity entity2, float par3, float yawdelta)
	{
		this(par1World);
		fins = false;
		refreshPositionAndAngles(entity2.getX(), entity2.getY() + entity2.getEyeHeight(entity2.getPose()), entity2.getZ(), entity2.getYaw() + yawdelta, entity2.getPitch());
        setPos(
            getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F),
            getY(),
            getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F)
        );
		setPosition(getX(), getY(), getZ());
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));

        setVelocity(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), 0.5f, 1f);
	}

	public EntitySeekB83(World w, double x, double y, double z, double mx, double my, double mz) {
		this(w);
		setPosition(x+mx*16, y+my*16, z+mz*16);
		fins = false;
		setVelocity(mx, my, mz, 0.5f, 0.1f);
	}

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        Vec3d vec3d = new Vec3d(x, y, z);
        double f2 = vec3d.length();
        vec3d = vec3d.multiply(1/f2);
		vec3d = vec3d.add(random.nextGaussian() * 0.0075 * divergence,
            random.nextGaussian() * 0.0075 * divergence,
            random.nextGaussian() * 0.0075 * divergence).multiply(speed);
        setVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
		setYaw(prevYaw = (float) (Math.atan2(vec3d.getX(), vec3d.getZ()) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(vec3d.getY(), MathHelper.sqrt((float) (vec3d.getX() * vec3d.getX() + vec3d.getZ() * vec3d.getZ()))) * 180.0D / Math.PI));
	}

    @Override
	public void tick()
	{
		super.tick();

		if (age == 0)
		{
			rotation = world.random.nextInt(360);
			slide = world.random.nextInt(21) - 10;
			for (int i = 0; i < 10; i++)
			{
				world.addParticle(ParticleTypes.EXPLOSION, getX() - getVelocity().getX() * 2, getY() - getVelocity().getY() * 2, getZ() - getVelocity().getZ() * 2, -getVelocity().getX() + (world.random.nextFloat() - 0.5f) * 0.1f, -getVelocity().getY() + (world.random.nextFloat() - 0.5) * 0.1f, -getVelocity().getZ() + (world.random.nextFloat() - 0.5f) * 0.1f);
			}
		}
		rotation += (int) slide;
		slide *= 0.9;

		if (age >= 800)
		{
			explode(null);
		}
		// world.spawnEntity(new EntityLightningLink(world, getX(), getY(), getZ(), yaw, pitch, 100));

		if (world.isClient && age >= 5 && !isInsideWaterOrBubbleColumn() && age <= 100)
		{
			world.spawnEntity(new EntityPropulsionFX(world, getX(), getY(), getZ(), -getVelocity().getX() * 0.5, -getVelocity().getY() * 0.5 - 0.1, -getVelocity().getZ() * 0.5));
		}
		Vec3d vec31 = getPos();
		Vec3d vec3 = getPos().add(getVelocity());
		HitResult mop = world.raycast(new RaycastContext(vec31, vec3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		if (!world.isClient)
		{
			vec31 = getPos();
			if (mop != null) vec3 = mop.getPos();
			else vec3 = getPos().add(getVelocity());

			List<Entity> list = world.getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
            for (Entity entity : list) {
                if ((entity.collides() && age >= 7 && entity != getOwner()) || entity instanceof EntityHackB83 || entity instanceof EntityB83) {
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
		}
		if (mop != null) explode(mop);

		Iterator<Entity> iter = world.getOtherEntities(this, IForgeBlockEntity.INFINITE_EXTENT_AABB).iterator();
        Vec3d ddvec = getVelocity();
		double dist = 1000000;
		while (iter.hasNext())
		{
			Entity e = iter.next();
			if (e instanceof EntityB83 || e instanceof EntityHackB83)
			{
                Vec3d dpos = e.getPos().subtract(getPos());
				double temp = dpos.lengthSquared();
				if (temp < dist) {
                    Vec3d d = dpos.multiply(getVelocity());
                    if (d.getX()+d.getY()+d.getZ()>0f) {
						dist = temp;
						temp = Math.sqrt(temp)*0.9f;
                        ddvec = dpos.multiply(1/temp);
					}
				}
			}
		}
        setVelocity(ddvec);

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		float var16 = MathHelper.sqrt((float) (getVelocity().getX() * getVelocity().getX() + getVelocity().getZ() * getVelocity().getZ()));
		setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));
		for (setPitch((float) (Math.atan2(getVelocity().getY(), var16) * 180.0D / Math.PI)); getPitch() - prevPitch < -180.0F; prevPitch -= 360.0F)
			;
		while (getPitch() - prevPitch >= 180.0F)
			prevPitch += 360.0F;
		while (getYaw() - prevYaw < -180.0F)
			prevYaw -= 360.0F;
		while (getYaw() - prevYaw >= 180.0F)
			prevYaw += 360.0F;
		setPitch(prevPitch + (getPitch() - prevPitch) * 0.2F);
		setYaw(prevYaw + (getYaw() - prevYaw) * 0.2F);
		float var17 = 1.1f;
		if (age > 25) var17 = 0.9999F;

		if (isInsideWaterOrBubbleColumn())
		{
			for (int var7 = 0; var7 < 4; ++var7)
			{
				world.addParticle(ParticleTypes.BUBBLE, getX() - getVelocity().getX() * 0.25F, getY() - getVelocity().getY() * 0.25F, getZ() - getVelocity().getZ() * 0.25F, getVelocity().getX(), getVelocity().getY(), getVelocity().getZ());
			}
			if (!inwaterprevtick)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 4, 0.5F, 0.5F);
			}
			soundfile = 3;
			var17 = 0.8F;
			inwaterprevtick = true;
		}
		else
		{
			soundfile = 0;
		}

        setVelocity(getVelocity().multiply(var17));
		if (age == 3)
		{
			fins = true;
            setPitch(getPitch() + 22.5F);
		}
		setPosition(getX(), getY(), getZ());
		++age;
	}

	public void explode(HitResult mop)
	{
		if (mop != null)
		{
			if (mop.getType() == HitResult.Type.ENTITY)
			{
                Entity entityHit = ((EntityHitResult) mop).getEntity();
                if (entityHit instanceof EntityHackB83)
				{
					entityHit.kill();
					world.setBlockState(getBlockPos(), RRBlocks.plasmaexplosion.getDefaultState());
					kill();
				}
				else if (entityHit instanceof PlayerEntity player) {
                    for (ItemStack armorSlot : player.getInventory().armor) {
                        if (!armorSlot.isEmpty()) {
                            armorSlot.damage(48, player, player1 -> {});
                        }
                    }
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(world, getX(), getY(), getZ(), RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					kill();
				}
				else
				{
					new Explosion(world, getX(), getY(), getZ(), RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					kill();
				}
			}
			else
			{
                BlockPos pos = ((BlockHitResult) mop).getBlockPos();
                BlockState state = world.getBlockState(pos);
                if (state.isIn(Tags.Blocks.GLASS) || state.isIn(Tags.Blocks.GLASS_PANES) || state.isIn(Tags.Blocks.STAINED_GLASS) || state.isIn(Tags.Blocks.STAINED_GLASS_PANES))
				{
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					RivalRebelsSoundPlayer.playSound(this, 4, 0, 5F, 0.3F);
				}
				else
				{
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(world, getX(), getY(), getZ(), RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					kill();
				}
			}
		}
		else
		{
			RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
			new Explosion(world, getX(), getY(), getZ(), RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
			kill();
		}
	}


	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

}

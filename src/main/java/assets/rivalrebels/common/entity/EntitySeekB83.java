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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class EntitySeekB83 extends AbstractArrow {
    public boolean				fins			= false;
	public int					rotation		= 45;
	public float				slide			= 0;
	private boolean				inwaterprevtick	= false;
	private int					soundfile		= 0;

    public EntitySeekB83(EntityType<? extends EntitySeekB83> type, Level world) {
        super(type, world);
    }

	public EntitySeekB83(Level par1World) {
		this(RREntities.SEEK_B83, par1World);
	}

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    public EntitySeekB83(Level par1World, double par2, double par4, double par6) {
		this(par1World);
		setPos(par2, par4, par6);
	}

	public EntitySeekB83(Level par1World, Player entity2, float par3) {
		this(par1World);
		fins = false;
		moveTo(entity2.getX(), entity2.getY() + entity2.getEyeHeight(entity2.getPose()), entity2.getZ(), entity2.getYRot(), entity2.getXRot());
        setPosRaw(
            getX() - (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * 0.16F),
            getY(),
            getZ() - (Mth.sin(getYRot() / 180.0F * (float) Math.PI) * 0.16F)
        );
		setPos(getX(), getY(), getZ());
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (-Mth.sin(getXRot() / 180.0F * (float) Math.PI)));

        shoot(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), 0.5f, 1f);
	}

	public EntitySeekB83(Level par1World, Player entity2, float par3, float yawdelta)
	{
		this(par1World);
		fins = false;
		moveTo(entity2.getX(), entity2.getY() + entity2.getEyeHeight(entity2.getPose()), entity2.getZ(), entity2.getYRot() + yawdelta, entity2.getXRot());
        setPosRaw(
            getX() - (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * 0.16F),
            getY(),
            getZ() - (Mth.sin(getYRot() / 180.0F * (float) Math.PI) * 0.16F)
        );
		setPos(getX(), getY(), getZ());
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
            (-Mth.sin(getXRot() / 180.0F * (float) Math.PI)));

        shoot(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), 0.5f, 1f);
	}

	public EntitySeekB83(Level w, double x, double y, double z, double mx, double my, double mz) {
		this(w);
		setPos(x+mx*16, y+my*16, z+mz*16);
		fins = false;
		shoot(mx, my, mz, 0.5f, 0.1f);
	}

    @Override
    public void shoot(double x, double y, double z, float speed, float divergence) {
        Vec3 vec3d = new Vec3(x, y, z);
        double f2 = vec3d.length();
        vec3d = vec3d.scale(1/f2);
		vec3d = vec3d.add(random.nextGaussian() * 0.0075 * divergence,
            random.nextGaussian() * 0.0075 * divergence,
            random.nextGaussian() * 0.0075 * divergence).scale(speed);
        setDeltaMovement(vec3d.x(), vec3d.y(), vec3d.z());
		setYRot(yRotO = (float) (Math.atan2(vec3d.x(), vec3d.z()) * 180.0D / Math.PI));
		setXRot(xRotO = (float) (Math.atan2(vec3d.y(), Mth.sqrt((float) (vec3d.x() * vec3d.x() + vec3d.z() * vec3d.z()))) * 180.0D / Math.PI));
	}

    @Override
	public void tick()
	{
		super.tick();

		if (tickCount == 0)
		{
			rotation = level().random.nextInt(360);
			slide = level().random.nextInt(21) - 10;
			for (int i = 0; i < 10; i++)
			{
				level().addParticle(ParticleTypes.EXPLOSION, getX() - getDeltaMovement().x() * 2, getY() - getDeltaMovement().y() * 2, getZ() - getDeltaMovement().z() * 2, -getDeltaMovement().x() + (level().random.nextFloat() - 0.5f) * 0.1f, -getDeltaMovement().y() + (level().random.nextFloat() - 0.5) * 0.1f, -getDeltaMovement().z() + (level().random.nextFloat() - 0.5f) * 0.1f);
			}
		}
		rotation += (int) slide;
		slide *= 0.9;

		if (tickCount >= 800)
		{
			explode(null);
		}
		// world.spawnEntity(new EntityLightningLink(world, getX(), getY(), getZ(), yaw, pitch, 100));

		if (level().isClientSide && tickCount >= 5 && !isInWaterOrBubble() && tickCount <= 100)
		{
			level().addFreshEntity(new EntityPropulsionFX(level(), getX(), getY(), getZ(), -getDeltaMovement().x() * 0.5, -getDeltaMovement().y() * 0.5 - 0.1, -getDeltaMovement().z() * 0.5));
		}
		Vec3 vec31 = position();
		Vec3 vec3 = position().add(getDeltaMovement());
		HitResult mop = level().clip(new ClipContext(vec31, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		if (!level().isClientSide())
		{
			vec31 = position();
			if (mop != null) vec3 = mop.getLocation();
			else vec3 = position().add(getDeltaMovement());

			List<Entity> list = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
            for (Entity entity : list) {
                if ((entity.canBeCollidedWith() && tickCount >= 7 && entity != getOwner()) || entity instanceof EntityHackB83 || entity instanceof EntityB83) {
                    Optional<Vec3> mop1 = entity.getBoundingBox().inflate(0.5f, 0.5f, 0.5f).clip(vec31, vec3);
                    if (mop1.isPresent()) {
                        double d1 = vec31.distanceToSqr(mop1.get());
                        if (d1 < d0) {
                            mop = new EntityHitResult(entity, mop1.get());
                            d0 = d1;
                        }
                    }
                }
            }
		}
		if (mop != null) explode(mop);

		Iterator<Entity> iter = level().getEntities(this, Shapes.INFINITY.bounds()).iterator();
        Vec3 ddvec = getDeltaMovement();
		double dist = 1000000;
		while (iter.hasNext())
		{
			Entity e = iter.next();
			if (e instanceof EntityB83 || e instanceof EntityHackB83)
			{
                Vec3 dpos = e.position().subtract(position());
				double temp = dpos.lengthSqr();
				if (temp < dist) {
                    Vec3 d = dpos.multiply(getDeltaMovement());
                    if (d.x()+d.y()+d.z()>0f) {
						dist = temp;
						temp = Math.sqrt(temp)*0.9f;
                        ddvec = dpos.scale(1/temp);
					}
				}
			}
		}
        setDeltaMovement(ddvec);

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var16 = Mth.sqrt((float) (getDeltaMovement().x() * getDeltaMovement().x() + getDeltaMovement().z() * getDeltaMovement().z()));
		setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));
		for (setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * 180.0D / Math.PI)); getXRot() - xRotO < -180.0F; xRotO -= 360.0F)
			;
		while (getXRot() - xRotO >= 180.0F)
			xRotO += 360.0F;
		while (getYRot() - yRotO < -180.0F)
			yRotO -= 360.0F;
		while (getYRot() - yRotO >= 180.0F)
			yRotO += 360.0F;
		setXRot(xRotO + (getXRot() - xRotO) * 0.2F);
		setYRot(yRotO + (getYRot() - yRotO) * 0.2F);
		float var17 = 1.1f;
		if (tickCount > 25) var17 = 0.9999F;

		if (isInWaterOrBubble())
		{
			for (int var7 = 0; var7 < 4; ++var7)
			{
				level().addParticle(ParticleTypes.BUBBLE, getX() - getDeltaMovement().x() * 0.25F, getY() - getDeltaMovement().y() * 0.25F, getZ() - getDeltaMovement().z() * 0.25F, getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z());
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

        setDeltaMovement(getDeltaMovement().scale(var17));
		if (tickCount == 3)
		{
			fins = true;
            setXRot(getXRot() + 22.5F);
		}
		setPos(getX(), getY(), getZ());
		++tickCount;
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
					level().setBlockAndUpdate(blockPosition(), RRBlocks.plasmaexplosion.defaultBlockState());
					kill();
				}
				else if (entityHit instanceof Player player) {
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        if (!slot.isArmor()) return;
                        ItemStack armorStack = player.getItemBySlot(slot);
                        if (!armorStack.isEmpty()) {
                            armorStack.hurtAndBreak(48, player, slot);
                        }
                    }
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(level(), getX(), getY(), getZ(), RRConfig.SERVER.getRocketExplosionSize(), false, false, RivalRebelsDamageSource.rocket(level()));
					kill();
				}
				else
				{
					new Explosion(level(), getX(), getY(), getZ(), RRConfig.SERVER.getRocketExplosionSize(), false, false, RivalRebelsDamageSource.rocket(level()));
					kill();
				}
			}
			else
			{
                BlockPos pos = ((BlockHitResult) mop).getBlockPos();
                BlockState state = level().getBlockState(pos);
                if (state.is(ConventionalBlockTags.GLASS_BLOCKS) || state.is(ConventionalBlockTags.GLASS_PANES))
				{
					level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    this.playSound(RRSounds.CUCHILLO_GLASS_BREAK, 5F, 0.3F);
				}
				else
				{
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(level(), getX(), getY(), getZ(), RRConfig.SERVER.getRocketExplosionSize(), false, false, RivalRebelsDamageSource.rocket(level()));
					kill();
				}
			}
		}
		else
		{
			RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
			new Explosion(level(), getX(), getY(), getZ(), RRConfig.SERVER.getRocketExplosionSize(), false, false, RivalRebelsDamageSource.rocket(level()));
			kill();
		}
	}


	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

	@Override
	public float getLightLevelDependentMagicValue()
	{
		return 1000F;
	}

}

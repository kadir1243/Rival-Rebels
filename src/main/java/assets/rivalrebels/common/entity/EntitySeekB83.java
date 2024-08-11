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
import assets.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import java.util.Iterator;

public class EntitySeekB83 extends AbstractArrow {
    public boolean				fins			= false;
	public int					rotation		= 45;
	public float				slide			= 0;
	private boolean				inwaterprevtick	= false;
	private int					soundfile		= 0;

    public EntitySeekB83(EntityType<? extends EntitySeekB83> type, Level world) {
        super(type, world);
    }

	public EntitySeekB83(Level level) {
		this(RREntities.SEEK_B83, level);
	}

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    public EntitySeekB83(Level level, double x, double y, double z) {
		this(level);
		setPos(x, y, z);
	}

	public EntitySeekB83(Level level, Entity entity, float par3) {
		this(level);
		fins = false;
        this.setOwner(entity);
		moveTo(entity.getEyePosition(), entity.getYRot(), entity.getXRot());
        setPos(
            getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
            getY(),
            getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F)
        );

        shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 0.5f, 1f);
	}

	public EntitySeekB83(Level level, Entity entity, float par3, float yawdelta)
	{
		this(level);
        this.setOwner(entity);
		fins = false;
		moveTo(entity.getEyePosition(), entity.getYRot() + yawdelta, entity.getXRot());
        setPos(
            getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
            getY(),
            getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F)
        );

        shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 0.5f, 1f);
	}

	public EntitySeekB83(Level w, double x, double y, double z, double mx, double my, double mz) {
		this(w);
		setPos(x+mx*16, y+my*16, z+mz*16);
		fins = false;
		shoot(mx, my, mz, 0.5f, 0.1f);
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
		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		if (hitResult.getType() != HitResult.Type.MISS) explode(hitResult);

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
		updateRotation();
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
        reapplyPosition();
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
                if (state.is(ModBlockTags.GLASS_BLOCKS) || state.is(ModBlockTags.GLASS_PANES))
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
    protected boolean canHitEntity(Entity target) {
        return super.canHitEntity(target) || target instanceof EntityHackB83 || target instanceof EntityB83;
    }
}

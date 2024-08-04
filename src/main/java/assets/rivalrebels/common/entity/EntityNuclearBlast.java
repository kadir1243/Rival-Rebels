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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.explosion.NuclearExplosion;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EntityNuclearBlast extends EntityInanimate {
    int			time;
    int			Strength;

    public EntityNuclearBlast(EntityType<? extends EntityNuclearBlast> type, Level world) {
        super(type, world);
    }

	public EntityNuclearBlast(Level par1World)
	{
		this(RREntities.NUCLEAR_BLAST, par1World);
		noCulling = true;
		tickCount = 0;
		time = 0;
	}

	public EntityNuclearBlast(Level par1World, double par2, double par4, double par6, int s, boolean hasTroll)
	{
		this(par1World);
        setDeltaMovement(hasTroll ? 1 : 0, Strength = s, getDeltaMovement().z());
		setPos(par2, par4, par6);
	}

	@Override
	public float getLightLevelDependentMagicValue()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    @Override
	public void tick()
	{
		super.tick();
		if (!level().isClientSide())
		{
			if (tickCount == 0)
			{
				level().explode(null, getX(), getY() - 5, getZ(), 4, Level.ExplosionInteraction.BLOCK);
			}
			if (tickCount % 20 == 0 && tickCount > 60)
			{
				time++;
				if (time <= Strength)
				{
					new NuclearExplosion(level(), (int) getX(), (int) getY() - 5, (int) getZ(), (time * time) / 2 + RRConfig.SERVER.getNuclearBombStrength());
				}
			}
			if (tickCount % 2 == 0 && tickCount < 400) pushAndHurtEntities();
		}
		if (tickCount < 30)
		{
			level().playLocalSound(getX(), getY() + tickCount - 5, getZ(), SoundEvents.GENERIC_EXPLODE.value(), getSoundSource(), 4.0f, level().random.nextFloat() * 0.1f + 0.9f, true);
		}
		if (tickCount % 3 == 0 && tickCount < 40 && tickCount > 30)
		{
			for (int i = 0; i < 21; i++)
			{
				level().playLocalSound(getX() + Mth.sin(i) * (i / 0.5), getY() + 17, getZ() + Mth.cos(i) * (i / 0.5), SoundEvents.GENERIC_EXPLODE.value(), getSoundSource(), 4.0f, level().random.nextFloat() + 1.0f, true);
			}
		}
		if (tickCount < 600)
		{
			if (tickCount % 5 == level().random.nextInt(5))
			{
                for (Player p : level().players()) {
                    level().playSound(p, p.getX(), p.getY(), p.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.MASTER, 10.0F, 0.50F);
                    level().playSound(p, p.getX(), p.getY(), p.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, 5.0F, 0.10F);
                }
			}
		}
		else
		{
			kill();
		}

		tickCount++;
	}

	private void pushAndHurtEntities()
	{
		int radius = Strength * RRConfig.SERVER.getNuclearBombStrength();
		if (radius > 80) radius = 80;
		int var3 = Mth.floor(getX() - radius - 1.0D);
		int var4 = Mth.floor(getX() + radius + 1.0D);
		int var5 = Mth.floor(getY() - radius - 1.0D);
		int var28 = Mth.floor(getY() + radius + 1.0D);
		int var7 = Mth.floor(getZ() - radius - 1.0D);
		int var29 = Mth.floor(getZ() + radius + 1.0D);
		List<Entity> var9 = level().getEntities(this, new AABB(var3, var5, var7, var4, var28, var29));

        for (Entity entity : var9) {
            double var13 = Math.sqrt(entity.distanceToSqr(getX(), getY(), getZ())) / radius;

            if (var13 <= 1.0D) {
                double var15 = entity.getX() - getX();
                double var17 = entity.getY() + entity.getEyeHeight(entity.getPose()) - getY();
                double var19 = entity.getZ() - getZ();
                double var33 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    if (!(entity instanceof EntityNuclearBlast) && !(entity instanceof EntityTsarBlast)) {
                        if (entity instanceof FallingBlockEntity) entity.kill();
                        else {
                            if (entity instanceof Player && entity.isInvulnerable())
                                continue;
                            entity.hurt(RivalRebelsDamageSource.nuclearBlast(level()), 16 * radius);
                            entity.setDeltaMovement(getDeltaMovement().subtract(
                                var15 * 8,
                                var17 * 8,
                                var19 * 8
                            ));
                        }
                    }
                }
            }
        }
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt)
	{
		tickCount = nbt.getInt("age");
		time = nbt.getInt("time");
        setDeltaMovement(nbt.getBoolean("troll") ? 1 : 0, Strength = nbt.getInt("charges"), getDeltaMovement().z());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt)
	{
		nbt.putInt("age", tickCount);
		nbt.putInt("time", time);
		nbt.putInt("charges", Strength);
	}

}

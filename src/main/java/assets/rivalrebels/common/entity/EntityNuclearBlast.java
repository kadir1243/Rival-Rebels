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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.explosion.NuclearExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityNuclearBlast extends EntityInanimate {
    int			time;
    int			Strength;

    public EntityNuclearBlast(EntityType<? extends EntityNuclearBlast> type, World world) {
        super(type, world);
    }

	public EntityNuclearBlast(World par1World)
	{
		this(RREntities.NUCLEAR_BLAST, par1World);
		ignoreCameraFrustum = true;
		age = 0;
		time = 0;
	}

	public EntityNuclearBlast(World par1World, double par2, double par4, double par6, int s, boolean hasTroll)
	{
		this(par1World);
        setVelocity(hasTroll ? 1 : 0, Strength = s, getVelocity().getZ());
		setPosition(par2, par4, par6);
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
	public void tick()
	{
		super.tick();
		if (!getWorld().isClient)
		{
			if (age == 0)
			{
				getWorld().createExplosion(null, getX(), getY() - 5, getZ(), 4, World.ExplosionSourceType.BLOCK);
			}
			if (age % 20 == 0 && age > 60)
			{
				time++;
				if (time <= Strength)
				{
					new NuclearExplosion(getWorld(), (int) getX(), (int) getY() - 5, (int) getZ(), (time * time) / 2 + RivalRebels.nuclearBombStrength);
				}
			}
			if (age % 2 == 0 && age < 400) pushAndHurtEntities();
		}
		if (age < 30)
		{
			getWorld().playSound(getX(), getY() + age - 5, getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, getSoundCategory(), 4.0f, getWorld().random.nextFloat() * 0.1f + 0.9f, true);
		}
		if (age % 3 == 0 && age < 40 && age > 30)
		{
			for (int i = 0; i < 21; i++)
			{
				getWorld().playSound(getX() + Math.sin(i) * (i / 0.5), getY() + 17, getZ() + Math.cos(i) * (i / 0.5), SoundEvents.ENTITY_GENERIC_EXPLODE, getSoundCategory(), 4.0f, getWorld().random.nextFloat() + 1.0f, true);
			}
		}
		if (age < 600)
		{
			if (age % 5 == getWorld().random.nextInt(5))
			{
                for (PlayerEntity p : getWorld().getPlayers()) {
                    getWorld().playSound(p, p.getX(), p.getY(), p.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.MASTER, 10.0F, 0.50F);
                    getWorld().playSound(p, p.getX(), p.getY(), p.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 5.0F, 0.10F);
                }
			}
		}
		else
		{
			kill();
		}

		age++;
	}

	private void pushAndHurtEntities()
	{
		int radius = Strength * RivalRebels.nuclearBombStrength;
		if (radius > 80) radius = 80;
		int var3 = MathHelper.floor(getX() - radius - 1.0D);
		int var4 = MathHelper.floor(getX() + radius + 1.0D);
		int var5 = MathHelper.floor(getY() - radius - 1.0D);
		int var28 = MathHelper.floor(getY() + radius + 1.0D);
		int var7 = MathHelper.floor(getZ() - radius - 1.0D);
		int var29 = MathHelper.floor(getZ() + radius + 1.0D);
		List<Entity> var9 = getWorld().getOtherEntities(this, new Box(var3, var5, var7, var4, var28, var29));

        for (Entity entity : var9) {
            double var13 = Math.sqrt(entity.squaredDistanceTo(getX(), getY(), getZ())) / radius;

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
                            if (entity instanceof PlayerEntity && ((PlayerEntity) entity).getAbilities().invulnerable)
                                continue;
                            entity.damage(RivalRebelsDamageSource.nuclearBlast(getWorld()), 16 * radius);
                            entity.setVelocity(getVelocity().subtract(
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
	public void readCustomDataFromNbt(NbtCompound nbt)
	{
		age = nbt.getInt("age");
		time = nbt.getInt("time");
        setVelocity(nbt.getBoolean("troll") ? 1 : 0, Strength = nbt.getInt("charges"), getVelocity().getZ());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt)
	{
		nbt.putInt("age", age);
		nbt.putInt("time", time);
		nbt.putInt("charges", Strength);
	}

}

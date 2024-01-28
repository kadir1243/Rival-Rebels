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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntitySphereBlast extends EntityTsarBlast {
    public EntitySphereBlast(EntityType<? extends EntitySphereBlast> type, World world) {
        super(type, world);
    }

	public EntitySphereBlast(World par1World) {
		this(RREntities.SPHERE_BLAST, par1World);
		ignoreCameraFrustum = true;
	}

	public EntitySphereBlast(World par1World, double x, double y, double z, float rad) {
		this(par1World);
		radius = rad;
		setVelocity(Math.sqrt(rad - RivalRebels.tsarBombaStrength) / 10, getVelocity().getY(), getVelocity().getZ());
		setPosition(x, y, z);
	}

	@Override
	public void tick()
	{
		if (world.random.nextInt(10) == 0)
		{
			world.playSound(this.getX(), getY(), getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.MASTER, 10.0F, 0.50F, true);
		}
		else
		{
			if (world.random.nextInt(5) == 0) RivalRebelsSoundPlayer.playSound(this, 26, 0, 100, 0.7f);
		}

		if (random.nextBoolean()&&random.nextBoolean()) pushAndHurtEntities();

		age++;

		if (age > 400) kill();
	}

	@Override
	public void pushAndHurtEntities()
	{
		int var3 = MathHelper.floor(getX() - radius - 1.0D);
		int var4 = MathHelper.floor(getX() + radius + 1.0D);
		int var5 = MathHelper.floor(getY() - radius - 1.0D);
		int var28 = MathHelper.floor(getY() + radius + 1.0D);
		int var7 = MathHelper.floor(getZ() - radius - 1.0D);
		int var29 = MathHelper.floor(getZ() + radius + 1.0D);
		List<Entity> var9 = world.getOtherEntities(this, new Box(var3, var5, var7, var4, var28, var29));

        for (Entity var31 : var9) {
            if (var31 instanceof LivingEntity) {
                if (var31 instanceof PlayerEntity && ((PlayerEntity) var31).getAbilities().creativeMode) continue;

                double var13 = Math.sqrt(var31.squaredDistanceTo(getX(), getY(), getZ())) / radius;

                if (var13 <= 1.0D) {
                    double var15 = var31.getX() - getX();
                    double var17 = var31.getY() + var31.getEyeHeight(var31.getPose()) - getY();
                    double var19 = var31.getZ() - getZ();
                    double var33 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                    if (var33 != 0.0D) {
                        var15 /= var33;
                        var17 /= var33;
                        var19 /= var33;
                        double var34 = (1.0D - var13);
                        var31.damage(RivalRebelsDamageSource.nuclearblast, (int) ((var34 * var34 + var34) * 20 * radius + 20) * 200);
                        var31.setVelocity(
                            var31.getVelocity().subtract(
                                var15 * var34 * 8,
                                var17 * var34 * 8,
                                var19 * var34 * 8
                            )
                        );
                    }
                }
            }
            if (var31 instanceof EntityRhodes) {
                var31.damage(RivalRebelsDamageSource.nuclearblast, 30);
            }
        }
	}
}

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
import java.util.List;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EntitySphereBlast extends EntityTsarBlast {
    public EntitySphereBlast(EntityType<? extends EntitySphereBlast> type, Level world) {
        super(type, world);
    }

	public EntitySphereBlast(Level par1World) {
		this(RREntities.SPHERE_BLAST, par1World);
		noCulling = true;
	}

	public EntitySphereBlast(Level par1World, double x, double y, double z, float rad) {
		this(par1World);
		radius = rad;
		setDeltaMovement(Math.sqrt(rad - RivalRebels.tsarBombaStrength) / 10, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(x, y, z);
	}

	@Override
	public void tick()
	{
		if (level().random.nextInt(10) == 0)
		{
			level().playLocalSound(this.getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.MASTER, 10.0F, 0.50F, true);
		}
		else
		{
			if (level().random.nextInt(5) == 0) RivalRebelsSoundPlayer.playSound(this, 26, 0, 100, 0.7f);
		}

		if (random.nextBoolean()&&random.nextBoolean()) pushAndHurtEntities();

		tickCount++;

		if (tickCount > 400) kill();
	}

	@Override
	public void pushAndHurtEntities()
	{
		int var3 = Mth.floor(getX() - radius - 1.0D);
		int var4 = Mth.floor(getX() + radius + 1.0D);
		int var5 = Mth.floor(getY() - radius - 1.0D);
		int var28 = Mth.floor(getY() + radius + 1.0D);
		int var7 = Mth.floor(getZ() - radius - 1.0D);
		int var29 = Mth.floor(getZ() + radius + 1.0D);
		List<Entity> var9 = level().getEntities(this, new AABB(var3, var5, var7, var4, var28, var29));

        for (Entity var31 : var9) {
            if (var31 instanceof LivingEntity) {
                if (var31 instanceof Player && ((Player) var31).getAbilities().instabuild) continue;

                double var13 = Math.sqrt(var31.distanceToSqr(getX(), getY(), getZ())) / radius;

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
                        var31.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) ((var34 * var34 + var34) * 20 * radius + 20) * 200);
                        var31.setDeltaMovement(
                            var31.getDeltaMovement().subtract(
                                var15 * var34 * 8,
                                var17 * var34 * 8,
                                var19 * var34 * 8
                            )
                        );
                    }
                }
            }
            if (var31 instanceof EntityRhodes) {
                var31.hurt(RivalRebelsDamageSource.nuclearBlast(level()), 30);
            }
        }
	}
}

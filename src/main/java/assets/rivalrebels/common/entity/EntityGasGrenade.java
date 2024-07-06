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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;

public class EntityGasGrenade extends EntityInanimate {
    public Entity shootingEntity;
    private int ticksInAir = 0;

    public EntityGasGrenade(EntityType<? extends EntityGasGrenade> type, Level world) {
        super(type, world);
    }

    public EntityGasGrenade(Level par1World) {
        this(RREntities.GAS_GRENADE, par1World);
    }

    public EntityGasGrenade(Level par1World, double par2, double par4, double par6) {
        this(par1World);
        setPos(par2, par4, par6);
    }

    public EntityGasGrenade(Level par1World, double x, double y, double z, double mx, double my, double mz) {
        this(par1World);
        setPos(x, y, z);
        setAnglesMotion(mx, my, mz);
    }

    public EntityGasGrenade(Level par1World, Mob shooting, Mob par3EntityLiving, float par4, float par5) {
        this(par1World);
        shootingEntity = shooting;

        setPosRaw(getX(), shooting.getY() + shooting.getEyeHeight(shooting.getPose()) - 0.10000000149011612D, getZ());
        double var6 = par3EntityLiving.getX() - shooting.getX();
        double var8 = par3EntityLiving.getY() + par3EntityLiving.getEyeHeight(par3EntityLiving.getPose()) - 0.699999988079071D - getY();
        double var10 = par3EntityLiving.getZ() - shooting.getZ();
        double var12 = Math.sqrt(var6 * var6 + var10 * var10);

        if (var12 >= 1.0E-7D) {
            float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
            float var15 = (float) (-(Math.atan2(var8, var12) * 180.0D / Math.PI));
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            moveTo(shooting.getX() + var16, getY(), shooting.getZ() + var18, var14, var15);
            float var20 = (float) var12 * 0.2F;
            setArrowHeading(var6, var8 + var20, var10, par4, par5);
        }
    }

    public EntityGasGrenade(Level par1World, Player player, float par3) {
        this(par1World);
        shootingEntity = player;

        moveTo(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYRot(), player.getXRot());
        setPosRaw(getX() - (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * 0.16F),
        getY() - 0.10000000149011612D,
        getZ() - (Mth.sin(getYRot() / 180.0F * (float) Math.PI) * 0.16F));
        setPos(getX(), getY(), getZ());
        setDeltaMovement((-Mth.sin(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
        (Mth.cos(getYRot() / 180.0F * (float) Math.PI) * Mth.cos(getXRot() / 180.0F * (float) Math.PI)),
        (-Mth.sin(getXRot() / 180.0F * (float) Math.PI)));
        setArrowHeading(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z(), par3 * 1.5F, 1.0F);
    }

    public void setAnglesMotion(double mx, double my, double mz) {
        setDeltaMovement(mx, my, mz);
        setYRot(yRotO = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
        setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
    }

    /**
     * Uses the provided coordinates as a heading and determines the velocity from it with the set force and random variance. Args: x, y, z, force, forceVariation
     */
    public void setArrowHeading(double par1, double par3, double par5, float par7, float par8) {
        float var9 = Mth.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
        par1 /= var9;
        par3 /= var9;
        par5 /= var9;
        par1 += random.nextGaussian() * 0.007499999832361937D * par8;
        par3 += random.nextGaussian() * 0.007499999832361937D * par8;
        par5 += random.nextGaussian() * 0.007499999832361937D * par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        super.setDeltaMovement(par1, par3, par5);
        float var10 = Mth.sqrt((float) (par1 * par1 + par5 * par5));
        setYRot(yRotO = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
        setXRot(xRotO = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setDeltaMovement(double par1, double par3, double par5) {
        super.setDeltaMovement(par1, par3, par5);

        if (xRotO == 0.0F && yRotO == 0.0F) {
            float var7 = Mth.sqrt((float) (par1 * par1 + par5 * par5));
            setYRot(yRotO = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
            setXRot(xRotO = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI));
            moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (xRotO == 0.0F && yRotO == 0.0F) {
            float var1 = Mth.sqrt((float) (getDeltaMovement().x() * getDeltaMovement().x() + getDeltaMovement().z() * getDeltaMovement().z()));
            setYRot(yRotO = (float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));
            setXRot(xRotO = (float) (Math.atan2(getDeltaMovement().y(), var1) * 180.0D / Math.PI));
        }
        ++ticksInAir;
        Vec3 var17 = position();
        Vec3 var3 = position().add(getDeltaMovement());
        HitResult var4 = level().clip(new ClipContext(var17, var3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        if (var4 != null) {
            var3 = var4.getLocation();
        }

        Entity var5 = null;
        List<Entity> var6 = level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z()).inflate(1.0D, 1.0D, 1.0D));
        double var7 = 0.0D;

        if (!level().isClientSide()) {
            for (Entity var10 : var6) {
                if (var10.canBeCollidedWith() && (var10 != shootingEntity || ticksInAir >= 5)) {
                    AABB var12 = var10.getBoundingBox().inflate(0.3f, 0.3f, 0.3f);
                    Optional<Vec3> var13 = var12.clip(var17, var3);

                    if (var13.isPresent()) {
                        double var14 = var17.distanceTo(var13.get());

                        if (var14 < var7 || var7 == 0.0D) {
                            var5 = var10;
                            var7 = var14;
                        }
                    }
                }
            }
        }

        if (var5 != null) {
            var4 = new EntityHitResult(var5);
        }

        float var20;

        if (var4 != null) {
            pop();
            kill();
        }

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
        var20 = Mth.sqrt((float) (getDeltaMovement().x() * getDeltaMovement().x() + getDeltaMovement().z() * getDeltaMovement().z()));
        setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));

        for (setXRot((float) (Math.atan2(getDeltaMovement().y(), var20) * 180.0D / Math.PI)); getXRot() - xRotO < -180.0F; xRotO -= 360.0F) {
        }

        while (getXRot() - xRotO >= 180.0F) {
            xRotO += 360.0F;
        }

        while (getYRot() - yRotO < -180.0F) {
            yRotO -= 360.0F;
        }

        while (getYRot() - yRotO >= 180.0F) {
            yRotO += 360.0F;
        }

        setXRot(xRotO + (getXRot() - xRotO) * 0.2F);
        setYRot(yRotO + (getYRot() - yRotO) * 0.2F);
        setXRot(getXRot() + 90);
        if (getXRot() <= -270) {
            setXRot(90);
        }
        float var23 = 0.9999F;

        if (isInWaterOrBubble()) {
            for (int var26 = 0; var26 < 4; ++var26) {
                float var27 = 0.25F;
                level().addParticle(ParticleTypes.BUBBLE, getX() - getDeltaMovement().x() * var27, getY() - getDeltaMovement().y() * var27, getZ() - getDeltaMovement().z() * var27, getDeltaMovement().x(), getDeltaMovement().y(), getDeltaMovement().z());
            }

            var23 = 0.8F;
        }

        setDeltaMovement(getDeltaMovement().scale(var23).subtract(0, 0.03, 0));
        setPos(getX(), getY(), getZ());
        checkInsideBlocks();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    private void pop() {
        RivalRebelsSoundPlayer.playSound(this, 9, 1);
        for (int x = -4; x <= 4; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -4; z <= 4; z++) {
                    if (level().isEmptyBlock(new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z))) {
                        level().setBlockAndUpdate(new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z), RRBlocks.toxicgas.defaultBlockState());
                    }
                }
            }
        }
    }
}

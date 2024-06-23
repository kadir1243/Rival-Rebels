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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;
import java.util.Optional;

public class EntityGasGrenade extends EntityInanimate {
    public Entity shootingEntity;
    private int ticksInAir = 0;

    public EntityGasGrenade(EntityType<? extends EntityGasGrenade> type, World world) {
        super(type, world);
    }

    public EntityGasGrenade(World par1World) {
        this(RREntities.GAS_GRENADE, par1World);
    }

    public EntityGasGrenade(World par1World, double par2, double par4, double par6) {
        this(par1World);
        setPosition(par2, par4, par6);
    }

    public EntityGasGrenade(World par1World, double x, double y, double z, double mx, double my, double mz) {
        this(par1World);
        setPosition(x, y, z);
        setAnglesMotion(mx, my, mz);
    }

    public EntityGasGrenade(World par1World, MobEntity shooting, MobEntity par3EntityLiving, float par4, float par5) {
        this(par1World);
        shootingEntity = shooting;

        setPos(getX(), shooting.getY() + shooting.getEyeHeight(shooting.getPose()) - 0.10000000149011612D, getZ());
        double var6 = par3EntityLiving.getX() - shooting.getX();
        double var8 = par3EntityLiving.getY() + par3EntityLiving.getEyeHeight(par3EntityLiving.getPose()) - 0.699999988079071D - getY();
        double var10 = par3EntityLiving.getZ() - shooting.getZ();
        double var12 = Math.sqrt(var6 * var6 + var10 * var10);

        if (var12 >= 1.0E-7D) {
            float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
            float var15 = (float) (-(Math.atan2(var8, var12) * 180.0D / Math.PI));
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            refreshPositionAndAngles(shooting.getX() + var16, getY(), shooting.getZ() + var18, var14, var15);
            float var20 = (float) var12 * 0.2F;
            setArrowHeading(var6, var8 + var20, var10, par4, par5);
        }
    }

    public EntityGasGrenade(World par1World, PlayerEntity player, float par3) {
        this(par1World);
        shootingEntity = player;

        refreshPositionAndAngles(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ(), player.getYaw(), player.getPitch());
        setPos(getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F),
        getY() - 0.10000000149011612D,
        getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F));
        setPosition(getX(), getY(), getZ());
        setVelocity((-MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
        (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(getPitch() / 180.0F * (float) Math.PI)),
        (-MathHelper.sin(getPitch() / 180.0F * (float) Math.PI)));
        setArrowHeading(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ(), par3 * 1.5F, 1.0F);
    }

    public void setAnglesMotion(double mx, double my, double mz) {
        setVelocity(mx, my, mz);
        setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
        setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
    }

    /**
     * Uses the provided coordinates as a heading and determines the velocity from it with the set force and random variance. Args: x, y, z, force, forceVariation
     */
    public void setArrowHeading(double par1, double par3, double par5, float par7, float par8) {
        float var9 = MathHelper.sqrt((float) (par1 * par1 + par3 * par3 + par5 * par5));
        par1 /= var9;
        par3 /= var9;
        par5 /= var9;
        par1 += random.nextGaussian() * 0.007499999832361937D * par8;
        par3 += random.nextGaussian() * 0.007499999832361937D * par8;
        par5 += random.nextGaussian() * 0.007499999832361937D * par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        super.setVelocity(par1, par3, par5);
        float var10 = MathHelper.sqrt((float) (par1 * par1 + par5 * par5));
        setYaw(prevYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
        setPitch(prevPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        super.setVelocity(par1, par3, par5);

        if (prevPitch == 0.0F && prevYaw == 0.0F) {
            float var7 = MathHelper.sqrt((float) (par1 * par1 + par5 * par5));
            setYaw(prevYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI));
            setPitch(prevPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI));
            refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (prevPitch == 0.0F && prevYaw == 0.0F) {
            float var1 = MathHelper.sqrt((float) (getVelocity().getX() * getVelocity().getX() + getVelocity().getZ() * getVelocity().getZ()));
            setYaw(prevYaw = (float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));
            setPitch(prevPitch = (float) (Math.atan2(getVelocity().getY(), var1) * 180.0D / Math.PI));
        }
        ++ticksInAir;
        Vec3d var17 = getPos();
        Vec3d var3 = getPos().add(getVelocity());
        HitResult var4 = getWorld().raycast(new RaycastContext(var17, var3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

        if (var4 != null) {
            var3 = var4.getPos();
        }

        Entity var5 = null;
        List<Entity> var6 = getWorld().getOtherEntities(this, getBoundingBox().stretch(getVelocity().getX(), getVelocity().getY(), getVelocity().getZ()).expand(1.0D, 1.0D, 1.0D));
        double var7 = 0.0D;

        if (!getWorld().isClient) {
            for (Entity var10 : var6) {
                if (var10.isCollidable() && (var10 != shootingEntity || ticksInAir >= 5)) {
                    Box var12 = var10.getBoundingBox().expand(0.3f, 0.3f, 0.3f);
                    Optional<Vec3d> var13 = var12.raycast(var17, var3);

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

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
        var20 = MathHelper.sqrt((float) (getVelocity().getX() * getVelocity().getX() + getVelocity().getZ() * getVelocity().getZ()));
        setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));

        for (setPitch((float) (Math.atan2(getVelocity().getY(), var20) * 180.0D / Math.PI)); getPitch() - prevPitch < -180.0F; prevPitch -= 360.0F) {
        }

        while (getPitch() - prevPitch >= 180.0F) {
            prevPitch += 360.0F;
        }

        while (getYaw() - prevYaw < -180.0F) {
            prevYaw -= 360.0F;
        }

        while (getYaw() - prevYaw >= 180.0F) {
            prevYaw += 360.0F;
        }

        setPitch(prevPitch + (getPitch() - prevPitch) * 0.2F);
        setYaw(prevYaw + (getYaw() - prevYaw) * 0.2F);
        setPitch(getPitch() + 90);
        if (getPitch() <= -270) {
            setPitch(90);
        }
        float var23 = 0.9999F;

        if (isInsideWaterOrBubbleColumn()) {
            for (int var26 = 0; var26 < 4; ++var26) {
                float var27 = 0.25F;
                getWorld().addParticle(ParticleTypes.BUBBLE, getX() - getVelocity().getX() * var27, getY() - getVelocity().getY() * var27, getZ() - getVelocity().getZ() * var27, getVelocity().getX(), getVelocity().getY(), getVelocity().getZ());
            }

            var23 = 0.8F;
        }

        setVelocity(getVelocity().multiply(var23).subtract(0, 0.03, 0));
        setPosition(getX(), getY(), getZ());
        checkBlockCollision();
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
                    if (getWorld().isAir(new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z))) {
                        getWorld().setBlockState(new BlockPos((int) getX() + x, (int) getY() + y, (int) getZ() + z), RRBlocks.toxicgas.getDefaultState());
                    }
                }
            }
        }
    }
}

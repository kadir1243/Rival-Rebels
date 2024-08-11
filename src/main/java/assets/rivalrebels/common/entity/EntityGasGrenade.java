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
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class EntityGasGrenade extends Projectile {
    public EntityGasGrenade(EntityType<? extends EntityGasGrenade> type, Level world) {
        super(type, world);
    }

    public EntityGasGrenade(Level level) {
        this(RREntities.GAS_GRENADE, level);
    }

    public EntityGasGrenade(Level level, double x, double y, double z) {
        this(level);
        setPos(x, y, z);
    }

    public EntityGasGrenade(Level level, double x, double y, double z, double mx, double my, double mz) {
        this(level);
        setPos(x, y, z);
        setDeltaMovement(mx, my, mz);
        setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
        setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
    }

    public EntityGasGrenade(Level level, Entity player, float speed) {
        this(level);
        this.setOwner(player);

        moveTo(player.getEyePosition(), player.getYRot(), player.getXRot());
        setPos(getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
        getY() - 0.1F,
        getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F));
        shootFromRotation(player, player.getXRot(), player.getYRot(), 0, speed * 1.5F, 1.0F);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setDeltaMovement(double x, double y, double z) {
        super.setDeltaMovement(x, y, z);

        if (xRotO == 0.0F && yRotO == 0.0F) {
            float var7 = Mth.sqrt((float) (x * x + z * z));
            setYRot(yRotO = (float) (Math.atan2(x, z) * Mth.RAD_TO_DEG));
            setXRot(xRotO = (float) (Math.atan2(y, var7) * Mth.RAD_TO_DEG));
            moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();

        if (xRotO == 0.0F && yRotO == 0.0F) {
            float var1 = (float) this.getDeltaMovement().horizontalDistance();
            setYRot(yRotO = (float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * Mth.RAD_TO_DEG));
            setXRot(xRotO = (float) (Math.atan2(getDeltaMovement().y(), var1) * Mth.RAD_TO_DEG));
        }
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

        if (hitResult.getType() != HitResult.Type.MISS) {
            pop();
            kill();
        }

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
        this.updateRotation();
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

        setDeltaMovement(getDeltaMovement().scale(var23));
        applyGravity();
        reapplyPosition();
        checkInsideBlocks();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.03;
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

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
package io.github.kadir1243.rivalrebels.common.item.weapon;

import io.github.kadir1243.rivalrebels.RRClient;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.item.components.BinocularData;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.packet.LaptopEngagePacket;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityLaptop;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import java.util.ArrayList;
import java.util.List;

public class ItemBinoculars extends Item {
    private static final List<TileEntityLaptop> ltel = new ArrayList<>();
    public static float distblock = 0;
    public static boolean hasLaptop = false;
    public static boolean tooClose = false;
    public static boolean tooFar = true;
    public static boolean ready = false;
    public static boolean c = false;
    public static boolean sc = false;
    float zoom = 30f;
    float fovset = 0f;
    float senset = 0f;
    boolean zoomed = false;
    boolean prevzoomed = false;
    boolean prevmclick;
    public ItemBinoculars() {
        super(new Properties().stacksTo(1).component(RRComponents.BINOCULAR_DATA, BinocularData.DEFAULT));
    }

    public static void add(TileEntityLaptop tel) {
        if (!ltel.contains(tel)) {
            ltel.add(tel);
        }
    }

    public static void remove(TileEntityLaptop tel) {
        ltel.remove(tel);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 200;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world.isClientSide && entity == Minecraft.getInstance().player) {
            boolean strike = isMousePressed() && !prevmclick;
            c ^= RRClient.USE_BINOCULARS_ITEM.isDown() && !sc;
            sc = RRClient.USE_BINOCULARS_ITEM.isDown();
            prevzoomed = zoomed;
            zoomed = ((Minecraft.getInstance().mouseHandler.isRightPressed() && (selected || zoomed))) && !Minecraft.getInstance().options.keyDrop.isDown() && Minecraft.getInstance().screen == null;
            if (zoomed) {
                if (!prevzoomed) {
                    fovset = (float) Minecraft.getInstance().options.fov().get();
                    senset = Minecraft.getInstance().options.sensitivity().get().floatValue();
                }
                stack.set(RRComponents.BINOCULAR_DATA, new BinocularData(
                    new BlockPos(-1, -1, -1),
                    0,
                    0,
                    6,
                    new BlockPos(-1, -1, -1)
                ));
                distblock = 130;
                hasLaptop = false;
                tooFar = true;
                tooClose = false;
                ready = false;
                zoom += (Minecraft.getInstance().mouseHandler.ypos() * 0.01f);
                if (zoom < 10) zoom = 10;
                if (zoom > 67) zoom = 67;
                Minecraft.getInstance().options.fov().set((int) (zoom + (Minecraft.getInstance().options.fov().get() - zoom) * 0.85f));
                Minecraft.getInstance().options.sensitivity().set((double) (senset * Mth.sqrt(zoom) * 0.1f));

                if (Minecraft.getInstance().mouseHandler.isRightPressed()) {
                    double cospitch = Mth.cos(entity.getXRot() * Mth.DEG_TO_RAD);
                    double sinpitch = Mth.sin(entity.getXRot() * Mth.DEG_TO_RAD);
                    double cosyaw = Mth.cos(entity.getYRot() * Mth.DEG_TO_RAD);
                    double sinyaw = Mth.sin(entity.getYRot() * Mth.DEG_TO_RAD);

                    double dx = (-sinyaw * cospitch) * 130;
                    double dz = (cosyaw * cospitch) * 130;
                    double dy = (-sinpitch) * 130;

                    Vec3 var3 = entity.position().add(dx, dy, dz);
                    BlockHitResult MOP = world.clip(new ClipContext(entity.position(), var3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));

                    TileEntityLaptop t = null;
                    double d = 100;
                    for (TileEntityLaptop tileEntityLaptop : ltel) {
                        t = tileEntityLaptop;
                        if (t.b2spirit > 0 || t.b2carpet > 0) {
                            hasLaptop = true;
                            double temp = entity.distanceToSqr(t.getBlockPos().getX() + 0.5, t.getBlockPos().getY() + 0.5, t.getBlockPos().getZ() + 0.5);
                            if (temp < d) {
                                d = temp;
                                stack.set(RRComponents.BINOCULAR_DATA, stack.get(RRComponents.BINOCULAR_DATA).withLPos(t.getBlockPos()));
                            }
                        }
                    }
                    if (MOP != null) {
                        BlockPos pos = MOP.getBlockPos();
                        stack.set(RRComponents.BINOCULAR_DATA, stack.get(RRComponents.BINOCULAR_DATA).withTPos(pos));
                        BlockPos lpos = stack.get(RRComponents.BINOCULAR_DATA).lpos();
                        distblock = Mth.sqrt((float) entity.distanceToSqr(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f));
                        tooFar = false;
                        if (t != null) {
                            stack.set(RRComponents.BINOCULAR_DATA,
                                stack.get(RRComponents.BINOCULAR_DATA).withData(
                                    t.b2spirit,
                                    t.b2carpet,
                                    Math.sqrt(d)
                                )
                            );
                            int XX = 11;
                            int ZZ = 10;
                            if (t.rrteam == RivalRebelsTeam.OMEGA) {
                                XX = (pos.getX() - RivalRebels.round.omegaData.objPos().getX());
                                ZZ = (pos.getZ() - RivalRebels.round.omegaData.objPos().getZ());
                            }
                            if (t.rrteam == RivalRebelsTeam.SIGMA) {
                                XX = (pos.getX() - RivalRebels.round.sigmaData.objPos().getX());
                                ZZ = (pos.getZ() - RivalRebels.round.sigmaData.objPos().getZ());
                            }
                            tooClose = (pos.getX() - lpos.getX()) * (pos.getX() - lpos.getX()) + (pos.getZ() - lpos.getZ()) * (pos.getZ() - lpos.getZ()) < 625;
                            if (!tooClose && XX * XX + ZZ * ZZ > 200) {
                                ready = true;
                                if (strike) {
                                    Minecraft.getInstance().getConnection().send(new LaptopEngagePacket(pos, lpos, c));
                                }
                            }
                        }
                    }
                }
            } else if (prevzoomed) {
                Minecraft.getInstance().options.fov().set((int) fovset);
                Minecraft.getInstance().options.sensitivity().set((double) senset);
            }
            prevmclick = isMousePressed();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isMousePressed() {
        return RRClient.TARGET_KEY.isDown();
    }
}

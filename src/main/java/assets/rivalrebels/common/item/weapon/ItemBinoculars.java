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
package assets.rivalrebels.common.item.weapon;

import assets.rivalrebels.ClientProxy;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.item.components.BinocularData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.packet.LaptopEngagePacket;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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
    static public float distblock = 0;
    static public boolean hasLaptop = false;
    static public boolean tooClose = false;
    static public boolean tooFar = true;
    static public boolean ready = false;
    static public boolean c = false;
    static public boolean sc = false;
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
    public UseAnim getUseAnimation(ItemStack par1ItemStack) {
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
        if (entity instanceof Player player && world.isClientSide && entity == Minecraft.getInstance().player) {
            boolean strike = isMousePressed() && !prevmclick;
            c ^= RivalRebels.proxy.c() && !sc;
            sc = RivalRebels.proxy.c();
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
                    double cospitch = Mth.cos(player.getXRot() / 180.0F * (float) Math.PI);
                    double sinpitch = Mth.sin(player.getXRot() / 180.0F * (float) Math.PI);
                    double cosyaw = Mth.cos(player.getYRot() / 180.0F * (float) Math.PI);
                    double sinyaw = Mth.sin(player.getYRot() / 180.0F * (float) Math.PI);

                    double dx = (-sinyaw * cospitch) * 130;
                    double dz = (cosyaw * cospitch) * 130;
                    double dy = (-sinpitch) * 130;

                    Vec3 var3 = player.position().add(dx, dy, dz);
                    BlockHitResult MOP = world.clip(new ClipContext(player.position(), var3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));

                    TileEntityLaptop t = null;
                    double d = 100;
                    for (TileEntityLaptop tileEntityLaptop : ltel) {
                        t = tileEntityLaptop;
                        if (t.b2spirit > 0 || t.b2carpet > 0) {
                            hasLaptop = true;
                            double temp = player.distanceToSqr(t.getBlockPos().getX() + 0.5, t.getBlockPos().getY() + 0.5, t.getBlockPos().getZ() + 0.5);
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
                        distblock = Mth.sqrt((float) player.distanceToSqr(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f));
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
                                XX = (pos.getX() - RivalRebels.round.omegaObjPos.getX());
                                ZZ = (pos.getZ() - RivalRebels.round.omegaObjPos.getZ());
                            }
                            if (t.rrteam == RivalRebelsTeam.SIGMA) {
                                XX = (pos.getX() - RivalRebels.round.sigmaObjPos.getX());
                                ZZ = (pos.getZ() - RivalRebels.round.sigmaObjPos.getZ());
                            }
                            tooClose = (pos.getX() - lpos.getX()) * (pos.getX() - lpos.getX()) + (pos.getZ() - lpos.getZ()) * (pos.getZ() - lpos.getZ()) < 625;
                            if (!tooClose && XX * XX + ZZ * ZZ > 200) {
                                ready = true;
                                if (strike) {
                                    ClientPlayNetworking.send(new LaptopEngagePacket(pos, lpos, c));
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

    public boolean isMousePressed() {
        return (ClientProxy.USE_KEY.isDown() && RivalRebels.rtarget) || (Minecraft.getInstance().mouseHandler.isLeftPressed() && RivalRebels.lctarget);
    }
}

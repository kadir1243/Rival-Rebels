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
import assets.rivalrebels.client.itemrenders.BinocularsRenderer;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.packet.LaptopEngagePacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemBinoculars extends Item {
    public static BlockPos tpos;
    public static BlockPos lpos;
    static public int tasks = 0;
    static public int carpet = 0;
    static public float dist = 0;
    static public float distblock = 0;
    static public boolean hasLaptop = false;
    static public boolean tooClose = false;
    static public boolean tooFar = true;
    static public boolean ready = false;
    static public boolean c = false;
    static public boolean sc = false;
    private static final List<TileEntityLaptop> ltel = new ArrayList<>();
    float zoom = 30f;
    float fovset = 0f;
    float senset = 0f;
    boolean zoomed = false;
    boolean prevzoomed = false;
    boolean prevmclick;
    public ItemBinoculars() {
        super(new Settings().maxCount(1).group(RRItems.rralltab));
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new BinocularsRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
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
    public UseAction getUseAction(ItemStack par1ItemStack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack par1ItemStack) {
        return 200;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && world.isClient && entity == MinecraftClient.getInstance().player) {
            boolean strike = isMousePressed() && !prevmclick;
            c ^= RivalRebels.proxy.c() && !sc;
            sc = RivalRebels.proxy.c();
            prevzoomed = zoomed;
            zoomed = ((MinecraftClient.getInstance().mouse.wasRightButtonClicked() && (selected || zoomed))) && !MinecraftClient.getInstance().options.dropKey.isPressed() && MinecraftClient.getInstance().currentScreen == null;
            if (zoomed) {
                if (!prevzoomed) {
                    fovset = (float) MinecraftClient.getInstance().options.fov;
                    senset = (float) MinecraftClient.getInstance().options.mouseSensitivity;
                }
                lpos = new BlockPos(-1, -1, -1);
                distblock = 130;
                hasLaptop = false;
                tasks = 0;
                carpet = 0;
                dist = 6;
                tooFar = true;
                tooClose = false;
                tpos = new BlockPos(-1, -1, -1);
                ready = false;
                zoom += (MinecraftClient.getInstance().mouse.getYVelocity() * 0.01f);
                if (zoom < 10) zoom = 10;
                if (zoom > 67) zoom = 67;
                MinecraftClient.getInstance().options.fov = zoom + (MinecraftClient.getInstance().options.fov - zoom) * 0.85f;
                MinecraftClient.getInstance().options.mouseSensitivity = senset * MathHelper.sqrt(zoom) * 0.1f;

                if (MinecraftClient.getInstance().mouse.wasRightButtonClicked()) {
                    double cospitch = MathHelper.cos(player.getPitch() / 180.0F * (float) Math.PI);
                    double sinpitch = MathHelper.sin(player.getPitch() / 180.0F * (float) Math.PI);
                    double cosyaw = MathHelper.cos(player.getYaw() / 180.0F * (float) Math.PI);
                    double sinyaw = MathHelper.sin(player.getYaw() / 180.0F * (float) Math.PI);

                    double dx = (-sinyaw * cospitch) * 130;
                    double dz = (cosyaw * cospitch) * 130;
                    double dy = (-sinpitch) * 130;

                    Vec3d var3 = player.getPos().add(dx, dy, dz);
                    BlockHitResult MOP = world.raycast(new RaycastContext(player.getPos(), var3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, null));

                    TileEntityLaptop t = null;
                    double d = 100;
                    for (TileEntityLaptop tileEntityLaptop : ltel) {
                        t = tileEntityLaptop;
                        if (t.b2spirit > 0 || t.b2carpet > 0) {
                            hasLaptop = true;
                            double temp = player.squaredDistanceTo(t.getPos().getX() + 0.5, t.getPos().getY() + 0.5, t.getPos().getZ() + 0.5);
                            if (temp < d) {
                                d = temp;
                                lpos = t.getPos();
                            }
                        }
                    }
                    if (MOP != null) {
                        tpos = MOP.getBlockPos();
                        distblock = MathHelper.sqrt((float) player.squaredDistanceTo(tpos.getX() + 0.5f, tpos.getY() + 0.5f, tpos.getZ() + 0.5f));
                        tooFar = false;
                        if (t != null) {
                            tasks = t.b2spirit;
                            carpet = t.b2carpet;
                            dist = (float) Math.sqrt(d);
                            int XX = 11;
                            int ZZ = 10;
                            if (t.rrteam == RivalRebelsTeam.OMEGA) {
                                XX = (MOP.getBlockPos().getX() - RivalRebels.round.omegaObjPos.getX());
                                ZZ = (MOP.getBlockPos().getZ() - RivalRebels.round.omegaObjPos.getZ());
                            }
                            if (t.rrteam == RivalRebelsTeam.SIGMA) {
                                XX = (MOP.getBlockPos().getX() - RivalRebels.round.sigmaObjPos.getX());
                                ZZ = (MOP.getBlockPos().getZ() - RivalRebels.round.sigmaObjPos.getZ());
                            }
                            tooClose = (tpos.getX() - lpos.getX()) * (tpos.getX() - lpos.getX()) + (tpos.getZ() - lpos.getZ()) * (tpos.getZ() - lpos.getZ()) < 625;
                            if (!tooClose && XX * XX + ZZ * ZZ > 200) {
                                ready = true;
                                if (strike) {
                                    PacketDispatcher.packetsys.sendToServer(new LaptopEngagePacket(tpos, lpos, c));
                                }
                            }
                        }
                    }
                }
            } else if (prevzoomed) {
                MinecraftClient.getInstance().options.fov = fovset;
                MinecraftClient.getInstance().options.mouseSensitivity = senset;
            }
            prevmclick = isMousePressed();
        }
    }

    public boolean isMousePressed() {
        return (ClientProxy.USE_KEY.isPressed() && RivalRebels.rtarget) || (MinecraftClient.getInstance().mouse.wasLeftButtonClicked() && RivalRebels.lctarget);
    }

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bb");
	}*/
}

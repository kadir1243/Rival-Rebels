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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.packet.LaptopEngagePacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

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
    private static List<TileEntityLaptop> ltel = new ArrayList<>();
    float zoom = 30f;
    float fovset = 0f;
    float senset = 0f;
    boolean zoomed = false;
    boolean prevzoomed = false;
    boolean prevmclick;
    public ItemBinoculars() {
        super();
        setMaxStackSize(1);
        setCreativeTab(RivalRebels.rralltab);
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
    public boolean isFull3D() {
        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 200;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUpdate(ItemStack item, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof EntityPlayer player && world.isRemote && entity == Minecraft.getMinecraft().player) {
            boolean strike = isMousePressed() && !prevmclick;
            c ^= Keyboard.isKeyDown(Keyboard.KEY_C) && !sc;
            sc = Keyboard.isKeyDown(Keyboard.KEY_C);
            prevzoomed = zoomed;
            zoomed = ((Mouse.isButtonDown(1) && (isSelected || zoomed))) && !GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindDrop) && Minecraft.getMinecraft().currentScreen == null;
            if (zoomed) {
                if (!prevzoomed) {
                    fovset = Minecraft.getMinecraft().gameSettings.fovSetting;
                    senset = Minecraft.getMinecraft().gameSettings.mouseSensitivity;
                }
                int slot = -1;
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
                if (true) {
                    zoom += (Mouse.getDWheel() * 0.01f);
                    if (zoom < 10) zoom = 10;
                    if (zoom > 67) zoom = 67;
                    Minecraft.getMinecraft().gameSettings.fovSetting = zoom + (Minecraft.getMinecraft().gameSettings.fovSetting - zoom) * 0.85f;
                    Minecraft.getMinecraft().gameSettings.mouseSensitivity = senset * MathHelper.sqrt(zoom) * 0.1f;

                    if (Mouse.isButtonDown(1)) {
                        double cospitch = MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI);
                        double sinpitch = MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI);
                        double cosyaw = MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI);
                        double sinyaw = MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI);

                        double dx = (-sinyaw * cospitch) * 130;
                        double dz = (cosyaw * cospitch) * 130;
                        double dy = (-sinpitch) * 130;

                        Vec3d var3 = player.getPositionVector().add(dx, dy, dz);
                        RayTraceResult MOP = world.rayTraceBlocks(player.getPositionVector(), var3, false);

                        TileEntityLaptop t = null;
                        double d = 100;
                        for (TileEntityLaptop tileEntityLaptop : ltel) {
                            t = tileEntityLaptop;
                            if (t.b2spirit > 0 || t.b2carpet > 0) {
                                hasLaptop = true;
                                double temp = player.getDistanceSq(t.getPos().getX() + 0.5, t.getPos().getY() + 0.5, t.getPos().getZ() + 0.5);
                                if (temp < d) {
                                    d = temp;
                                    lpos = t.getPos();
                                }
                            }
                        }
                        if (MOP != null) {
                            tpos = MOP.getBlockPos();
                            distblock = (float) player.getDistance(tpos.getX() + 0.5f, tpos.getY() + 0.5f, tpos.getZ() + 0.5f);
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
                }
            } else if (prevzoomed) {
                Minecraft.getMinecraft().gameSettings.fovSetting = fovset;
                Minecraft.getMinecraft().gameSettings.mouseSensitivity = senset;
            }
            prevmclick = isMousePressed();
        }
    }

    public boolean isMousePressed() {
        return ((RivalRebels.altRkey ? Keyboard.isKeyDown(Keyboard.KEY_F) : Keyboard.isKeyDown(Keyboard.KEY_R)) && RivalRebels.rtarget) || (Mouse.isButtonDown(0) && RivalRebels.lctarget);
    }

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bb");
	}*/
}

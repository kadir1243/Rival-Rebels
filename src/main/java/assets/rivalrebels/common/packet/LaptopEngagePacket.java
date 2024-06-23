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
package assets.rivalrebels.common.packet;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityB2Spirit;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaptopEngagePacket implements FabricPacket {
    public static final PacketType<LaptopEngagePacket> PACKET_TYPE = PacketType.create(new Identifier(RivalRebels.MODID, "laptopengage"), LaptopEngagePacket::fromBytes);
    private final BlockPos tpos;
    private final BlockPos lpos;
	private final boolean carpet;

	public LaptopEngagePacket(BlockPos tpos, BlockPos lpos, boolean carpet) {
        this.tpos = tpos;
        this.lpos = lpos;
		this.carpet = carpet;
	}

	public static LaptopEngagePacket fromBytes(PacketByteBuf buf) {
        return new LaptopEngagePacket(buf.readBlockPos(), buf.readBlockPos(), buf.readBoolean());
	}

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(tpos);
		buf.writeBlockPos(lpos);
		buf.writeBoolean(carpet);
	}

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }

    public static void onMessage(LaptopEngagePacket m, PlayerEntity player) {
        World world = player.getWorld();
        if (player.squaredDistanceTo(m.lpos.getX(), m.lpos.getY(), m.lpos.getZ()) < 100) {
            BlockEntity te = world.getBlockEntity(m.lpos);
            if (te instanceof TileEntityLaptop tel) {
                if (!m.carpet && tel.b2spirit > 0) {
                    int XX = 11;
                    int ZZ = 10;
                    if (tel.rrteam == RivalRebelsTeam.OMEGA) {
                        XX = (m.tpos.getX() - RivalRebels.round.omegaObjPos.getX());
                        ZZ = (m.tpos.getZ() - RivalRebels.round.omegaObjPos.getZ());
                    }
                    if (tel.rrteam == RivalRebelsTeam.SIGMA) {
                        XX = (m.tpos.getX() - RivalRebels.round.sigmaObjPos.getX());
                        ZZ = (m.tpos.getZ() - RivalRebels.round.sigmaObjPos.getZ());
                    }
                    int xx = m.tpos.getX() - m.lpos.getX();
                    int zz = m.tpos.getZ() - m.lpos.getZ();
                    if (xx * xx + zz * zz > 625 && XX * XX + ZZ * ZZ > 200) {
                        tel.b2spirit--;
                        world.spawnEntity(new EntityB2Spirit(world, m.tpos.getX(), m.tpos.getY(), m.tpos.getZ(), player.getX(), player.getY(), player.getZ(), false, player.isSneaking()));
                    }
                }
                if (m.carpet && tel.b2carpet > 0) {
                    int XX = 11;
                    int ZZ = 10;
                    int xx = m.tpos.getX() - m.lpos.getX();
                    int zz = m.tpos.getZ() - m.lpos.getZ();
                    if (xx * xx + zz * zz > 625 && XX * XX + ZZ * ZZ > 200) {
                        tel.b2carpet--;
                        world.spawnEntity(new EntityB2Spirit(world, m.tpos.getX(), m.tpos.getY(), m.tpos.getZ(), player.getX(), player.getY(), player.getZ(), true, false));
                    }
                }
            }
        }
    }
}

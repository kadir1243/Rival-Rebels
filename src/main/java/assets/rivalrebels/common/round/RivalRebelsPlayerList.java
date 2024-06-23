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
package assets.rivalrebels.common.round;

import assets.rivalrebels.RivalRebels;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Arrays;

public class RivalRebelsPlayerList implements FabricPacket {
    public static final PacketType<RivalRebelsPlayerList> PACKET_TYPE = PacketType.create(new Identifier(RivalRebels.MODID, "rivalrebelsplayerlist"), RivalRebelsPlayerList::fromBytes);
	private int	size = 0;
	private RivalRebelsPlayer[]	list = new RivalRebelsPlayer[0];

	public RivalRebelsPlayerList()
	{
	}

	public int getSize()
	{
		return size;
	}

	public RivalRebelsPlayer add(RivalRebelsPlayer o)
	{
		size++;
        if (size > list.length) {
            int nsize = ((list.length * 3) / 2) + 1;
            if (nsize < size) nsize = size;
            list = Arrays.copyOf(list, nsize);
        }
        list[size - 1] = o;
        return o;
	}

    @Override
    public void write(PacketByteBuf buf) {
        toBytes(this, buf);
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }

    public void clear()
	{
		for (int i = 0; i < size; i++) list[i].clear();
	}

	public void clearTeam()
	{
		for (int i = 0; i < size; i++) list[i].clearTeam();
	}

	public boolean contains(GameProfile o)
	{
		for (int i = 0; i < size; i++) if (list[i].profile.equals(o)) return true;
		return false;
	}

    public RivalRebelsPlayer getForGameProfile(GameProfile profile) {
        for (int i = 0; i < size; i++) if (list[i].profile.equals(profile)) return list[i];
        return add(new RivalRebelsPlayer(profile, RivalRebelsTeam.NONE, RivalRebelsClass.NONE, RivalRebelsRank.REGULAR, RivalRebels.resetMax));
    }

	public void clearVotes()
	{
		for (int i = 0; i < size; i++)
		{
			list[i].voted = false;
		}
	}

	public static RivalRebelsPlayerList fromBytes(PacketByteBuf buf) {
        RivalRebelsPlayerList rivalRebelsPlayerList = new RivalRebelsPlayerList();
        rivalRebelsPlayerList.size = buf.readInt();
        rivalRebelsPlayerList.list = new RivalRebelsPlayer[rivalRebelsPlayerList.size];
		for (int i = 0; i < rivalRebelsPlayerList.size; i++) rivalRebelsPlayerList.list[i] = new RivalRebelsPlayer(buf);
        return rivalRebelsPlayerList;
	}

    public static RivalRebelsPlayerList fromNbt(NbtCompound nbt) {
        RivalRebelsPlayerList rivalRebelsPlayerList = new RivalRebelsPlayerList();
        rivalRebelsPlayerList.size = nbt.getInt("size");
        rivalRebelsPlayerList.list = new RivalRebelsPlayer[rivalRebelsPlayerList.size];
        for (int i = 0; i < rivalRebelsPlayerList.size; i++) {
            rivalRebelsPlayerList.list[i] = new RivalRebelsPlayer(nbt.getCompound("list_" + i));
        }
        return rivalRebelsPlayerList;
    }

	public static void toBytes(RivalRebelsPlayerList playerList, PacketByteBuf buf) {
		buf.writeInt(playerList.size);
		for (int i = 0; i < playerList.size; i++) playerList.list[i].toBytes(buf);
	}

    public void toNbt(NbtCompound nbt) {
        nbt.putInt("size", size);
        for (int i = 0; i < size; i++) {
            NbtCompound compound = new NbtCompound();
            list[i].toNbt(compound);
            nbt.put("list_" + i, compound);
        }
    }

	public static void onMessage(RivalRebelsPlayerList m, PlayerEntity player, PacketSender responseHandler) {
		RivalRebels.round.rrplayerlist = m;
	}

	public RivalRebelsPlayer[] getArray()
	{
		return list;
	}

    public void refreshForWorld(World world) {
        if (world.isClient) return;
        for (PlayerEntity player : world.getPlayers()) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, this);
        }
    }
}

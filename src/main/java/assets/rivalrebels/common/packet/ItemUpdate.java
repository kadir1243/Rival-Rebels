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

import assets.rivalrebels.common.item.weapon.ItemFlameThrower;
import assets.rivalrebels.common.item.weapon.ItemTesla;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemUpdate {
	private final int item;
	private final int value;

	public ItemUpdate(int currentItem, int i) {
		item = currentItem;
		value = i;
	}

	public static ItemUpdate fromBytes(PacketByteBuf buf) {
        return new ItemUpdate(buf.readInt(), buf.readInt());
	}

	public static void toBytes(ItemUpdate packet, PacketByteBuf buf) {
		buf.writeInt(packet.item);
		buf.writeInt(packet.value);
	}


    public static void onMessage(ItemUpdate message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        ServerPlayerEntity sender = context.getSender();
        context.enqueueWork(() -> {
            ItemStack itemstack = sender.getInventory().getStack(message.item);
            if (itemstack.getItem() instanceof ItemTesla) {
                itemstack.getOrCreateNbt().putInt("dial", message.value);
            }
            if (itemstack.getItem() instanceof ItemFlameThrower) {
                itemstack.getOrCreateNbt().putInt("mode", message.value);
            }
        });
    }
}

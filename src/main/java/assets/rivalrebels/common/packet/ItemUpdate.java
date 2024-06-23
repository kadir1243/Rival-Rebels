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
import assets.rivalrebels.common.item.weapon.ItemFlameThrower;
import assets.rivalrebels.common.item.weapon.ItemTesla;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ItemUpdate implements FabricPacket {
    public static final PacketType<ItemUpdate> TYPE = PacketType.create(new Identifier(RivalRebels.MODID, "item_update"), ItemUpdate::new);
    private final int item;
	private final int value;

	public ItemUpdate(int currentItem, int i) {
		item = currentItem;
		value = i;
	}

	public ItemUpdate(PacketByteBuf buf) {
        this(buf.readInt(), buf.readInt());
	}

    @Override
    public void write(PacketByteBuf buf) {
		buf.writeInt(item);
		buf.writeInt(value);
	}

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void onMessage(ItemUpdate message, PlayerEntity player) {
        ItemStack itemstack = player.getInventory().getStack(message.item);
        if (itemstack.getItem() instanceof ItemTesla) {
            itemstack.getOrCreateNbt().putInt("dial", message.value);
        }
        if (itemstack.getItem() instanceof ItemFlameThrower) {
            itemstack.getOrCreateNbt().putInt("mode", message.value);
        }
    }
}

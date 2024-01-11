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

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import assets.rivalrebels.common.item.weapon.ItemFlameThrower;
import assets.rivalrebels.common.item.weapon.ItemTesla;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ItemUpdate implements IMessage
{
	public int item;
	public int value;

	public ItemUpdate() {
	}

	public ItemUpdate(int currentItem, int i)
	{
		item = currentItem;
		value = i;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		item = buf.readInt();
		value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(item);
		buf.writeInt(value);
	}

	public static class Handler implements IMessageHandler<ItemUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(ItemUpdate message, MessageContext ctx)
		{
            ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
                ItemStack itemstack = ctx.getServerHandler().player.inventory.getStackInSlot(message.item);
                if (itemstack.getItem() instanceof ItemTesla) {
                    if (!itemstack.hasTagCompound()) itemstack.setTagCompound(new NBTTagCompound());
                    itemstack.getTagCompound().setInteger("dial", message.value);
                }
                if (itemstack.getItem() instanceof ItemFlameThrower) {
                    if (!itemstack.hasTagCompound()) itemstack.setTagCompound(new NBTTagCompound());
                    itemstack.getTagCompound().setInteger("mode", message.value);
                }
            });
			return null;
		}
	}
}

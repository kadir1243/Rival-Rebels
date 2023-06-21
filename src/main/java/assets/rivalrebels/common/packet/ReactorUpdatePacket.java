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

import assets.rivalrebels.common.tileentity.TileEntityList;
import assets.rivalrebels.common.tileentity.TileEntityMachineBase;
import assets.rivalrebels.common.tileentity.TileEntityReactive;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ReactorUpdatePacket implements IMessage {
    private BlockPos pos;
	float consumed;
	float lasttick;
	boolean melt;
	boolean eject;
	boolean on;
	TileEntityList machines;

    public ReactorUpdatePacket() {
	}

	public ReactorUpdatePacket(BlockPos pos, float cons, float last, boolean mt, boolean ej, boolean o, TileEntityList mach)
	{
		this.pos = pos;
		consumed = cons;
		lasttick = last;
		melt = mt;
		eject = ej;
		on = o;
		machines = mach;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
        if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) return;
        PacketBuffer buffer = new PacketBuffer(buf);
        pos = buffer.readBlockPos();
        consumed = buffer.readFloat();
        lasttick = buffer.readFloat();
        melt = buffer.readBoolean();
        eject = buffer.readBoolean();
        on = buffer.readBoolean();
        int machineAmount = buffer.readInt();
        if (machineAmount > 0) {
            machines = new TileEntityList();
        }
        for (int i = 0; i < machineAmount; i++) {
            boolean shouldBeRead = buffer.readBoolean();
            if (!shouldBeRead) {
                continue;
            }
            BlockPos tilePos = buffer.readBlockPos();
            float powerGiven = buffer.readFloat();
            float pInR = buffer.readFloat();
            TileEntityMachineBase tileEntity = (TileEntityMachineBase) Minecraft.getMinecraft().theWorld.getTileEntity(tilePos);
            tileEntity.powerGiven = powerGiven;
            tileEntity.pInR = pInR;
            tileEntity.rpos = pos;
            tileEntity.edist = (float) Math.sqrt(tileEntity.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));

            machines.add(tileEntity);
        }
    }

	@Override
	public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);

		buffer.writeFloat(consumed);
		buffer.writeFloat(lasttick);
		buffer.writeBoolean(melt);
		buffer.writeBoolean(eject);
		buffer.writeBoolean(on);
		buffer.writeInt(machines == null ? 0 : machines.size());
        if (machines != null) {
            for (int i = 0; i < machines.size(); i++) {
                TileEntityMachineBase te = (TileEntityMachineBase) machines.get(i);
                if (te != null && !(te instanceof TileEntityReactive)) {
                    buffer.writeBoolean(true);
                    buffer.writeBlockPos(te.getPos());
                    buffer.writeFloat(te.powerGiven);
                    buffer.writeFloat(te.pInR);
                } else {
                    buffer.writeBoolean(false);
                }
            }
        }
	}

	public static class Handler implements IMessageHandler<ReactorUpdatePacket, IMessage> {
		@Override
		public IMessage onMessage(ReactorUpdatePacket m, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
                BlockPos pos = m.pos;
                TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(pos);

                if (te instanceof TileEntityReactor ter) {
                    ter.consumed = m.consumed;
                    ter.lasttickconsumed = m.lasttick;
                    ter.melt = m.melt;
                    ter.eject = m.eject;
                    ter.on = m.on;
                    ter.machines.clear();

                    ter.machines.addAll(m.machines);
                }
            }

			return null;
		}
	}
}

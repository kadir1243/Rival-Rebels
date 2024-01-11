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

import assets.rivalrebels.common.tileentity.TileEntityMachineBase;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReactorUpdatePacket implements IMessage {
    private BlockPos pos;
	float consumed;
	float lasttick;
	boolean melt;
	boolean eject;
	boolean on;
	private List<TileWrapper> machines;

    public ReactorUpdatePacket() {
	}

	public ReactorUpdatePacket(BlockPos pos, float cons, float last, boolean mt, boolean ej, boolean o, List<TileEntity> mach)
	{
		this.pos = pos;
		consumed = cons;
		lasttick = last;
		melt = mt;
		eject = ej;
		on = o;
		machines = mach.stream().map(tileEntity -> {
            if (tileEntity instanceof TileEntityMachineBase tembr) {
                return new TileWrapper(tileEntity.getPos(), tembr.powerGiven, tembr.pInR);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        pos = buffer.readBlockPos();

        consumed = buf.readFloat();
        lasttick = buf.readFloat();
        melt = buf.readBoolean();
        eject = buf.readBoolean();
        on = buf.readBoolean();

        int sizeOfTileEntityList = buf.readInt();
        List<TileWrapper> list = new ArrayList<>(sizeOfTileEntityList + 1);
        if (sizeOfTileEntityList > 0) {
            for (int i = 0; i < sizeOfTileEntityList; i++) {
                BlockPos pos = buffer.readBlockPos();
                float powerGiven = buffer.readFloat();
                float pInR = buffer.readFloat();
                list.add(i, new TileWrapper(pos, powerGiven, pInR));
            }
        }
        machines = list;
    }

	@Override
	public void toBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);

		buf.writeFloat(consumed);
		buf.writeFloat(lasttick);
		buf.writeBoolean(melt);
		buf.writeBoolean(eject);
		buf.writeBoolean(on);
		if (machines == null) {
            buf.writeInt(0);
            return;
        }
		buf.writeInt(machines.size());
        for (TileWrapper machine : machines) {
            buffer.writeBlockPos(machine.getPos());
            buf.writeFloat(machine.getPowerGiven());
            buf.writeFloat(machine.getpInR());
        }
	}

	public static class Handler implements IMessageHandler<ReactorUpdatePacket, IMessage>
	{
		@Override
		public IMessage onMessage(ReactorUpdatePacket m, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(m.pos);

                if (te instanceof TileEntityReactor ter) {
                    ter.consumed = m.consumed;
                    ter.lasttickconsumed = m.lasttick;
                    ter.melt = m.melt;
                    ter.eject = m.eject;
                    ter.on = m.on;
                    ter.machines.clear();
                    for (TileWrapper machine : m.machines) {
                        TileEntity temb = Minecraft.getMinecraft().world.getTileEntity(machine.getPos());
                        if (temb instanceof TileEntityMachineBase tembr) {
                            tembr.powerGiven = machine.getPowerGiven();
                            tembr.pInR = machine.getpInR();
                            tembr.pos = m.pos;
                            tembr.edist = (float) Math.sqrt(tembr.getDistanceSq(m.pos.getX(), m.pos.getY(), m.pos.getZ()));
                            ter.machines.add(temb);
                        }
                    }
                }
            });
			return null;
		}
	}

    private static class TileWrapper {
        private final BlockPos pos;
        private final float powerGiven;
        private final float pInR;

        private TileWrapper(BlockPos pos, float powerGiven, float pInR) {
            this.pos = pos;
            this.powerGiven = powerGiven;
            this.pInR = pInR;
        }

        public BlockPos getPos() {
            return pos;
        }

        public float getpInR() {
            return pInR;
        }

        public float getPowerGiven() {
            return powerGiven;
        }
    }
}

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

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class RivalRebelsPlayer
{
    public GameProfile profile;
	public RivalRebelsClass	rrclass	= RivalRebelsClass.NONE;
	public RivalRebelsTeam	rrteam	= RivalRebelsTeam.NONE;
	public RivalRebelsRank	rrrank	= RivalRebelsRank.REGULAR;
	public int				resets	= -1;
	public boolean			isreset	= true;
	public boolean			voted	= false;

    public RivalRebelsPlayer(GameProfile profile, RivalRebelsTeam rteam, RivalRebelsClass rclass, RivalRebelsRank rrank, int r) {
        this.profile = profile;
        rrteam = rteam;
        rrclass = rclass;
        rrrank = rrank;
        resets = r;
    }

	public RivalRebelsPlayer(PacketByteBuf buf) {
		fromBytes(buf);
	}

    public RivalRebelsPlayer(NbtCompound nbt) {
        fromNbt(nbt);
    }

	public boolean equals(RivalRebelsPlayer o)
	{
        return profile.equals(o.profile);
    }

    public UUID getId() {
        return profile.getId();
    }

    public String getUsername() {
        return profile.getName();
    }

	public void reset()
	{
		resets++;
		isreset = true;
	}

	public void clear()
	{
		rrclass = RivalRebelsClass.NONE;
		isreset = true;
		resets = -1;
	}

	public void clearTeam()
	{
		rrclass = RivalRebelsClass.NONE;
		rrteam = RivalRebelsTeam.NONE;
		isreset = true;
		resets = -1;
	}

	public void toBytes(PacketByteBuf buf) {
		buf.writeByte(rrclass.id);
		buf.writeByte(rrteam.id);
		buf.writeByte(rrrank.id);
		buf.writeInt(resets);
		buf.writeBoolean(isreset);
        buf.writeUuid(getId());
        buf.writeString(getUsername());
	}

    public void toNbt(NbtCompound nbt) {
        nbt.putByte("rrclass", (byte) rrclass.id);
        nbt.putByte("rrteam", (byte) rrteam.id);
        nbt.putByte("rrrank", (byte) rrrank.id);
        nbt.putInt("resets", (byte) resets);
        nbt.putBoolean("isreset", isreset);
        nbt.putUuid("id", getId());
        nbt.putString("username", getUsername());
    }

	public void fromBytes(PacketByteBuf buf) {
		rrclass = RivalRebelsClass.getForID(buf.readByte());
		rrteam = RivalRebelsTeam.getForID(buf.readByte());
		rrrank = RivalRebelsRank.getForID(buf.readByte());
		resets = buf.readInt();
		isreset = buf.readBoolean();
        this.profile = new GameProfile(buf.readUuid(), buf.readString());
	}

    public void fromNbt(NbtCompound nbt) {
        rrclass = RivalRebelsClass.getForID(nbt.getByte("rrclass"));
        rrteam = RivalRebelsTeam.getForID(nbt.getByte("rrteam"));
        rrrank = RivalRebelsRank.getForID(nbt.getByte("rrrank"));
        resets = nbt.getInt("resets");
        isreset = nbt.getBoolean("isreset");
        this.profile = new GameProfile(nbt.getUuid("id"), nbt.getString("username"));
    }
}

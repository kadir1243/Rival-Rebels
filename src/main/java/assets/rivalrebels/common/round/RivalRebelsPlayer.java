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
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

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

	public RivalRebelsPlayer(FriendlyByteBuf buf) {
		fromBytes(buf);
	}

    public RivalRebelsPlayer(CompoundTag nbt) {
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

	public void toBytes(FriendlyByteBuf buf) {
		buf.writeByte(rrclass.id);
		buf.writeByte(rrteam.id);
		buf.writeByte(rrrank.id);
		buf.writeInt(resets);
		buf.writeBoolean(isreset);
        buf.writeUUID(getId());
        buf.writeUtf(getUsername());
	}

    public void toNbt(CompoundTag nbt) {
        nbt.putByte("rrclass", (byte) rrclass.id);
        nbt.putByte("rrteam", (byte) rrteam.id);
        nbt.putByte("rrrank", (byte) rrrank.id);
        nbt.putInt("resets", (byte) resets);
        nbt.putBoolean("isreset", isreset);
        nbt.putUUID("id", getId());
        nbt.putString("username", getUsername());
    }

	public void fromBytes(FriendlyByteBuf buf) {
		rrclass = RivalRebelsClass.getForID(buf.readByte());
		rrteam = RivalRebelsTeam.getForID(buf.readByte());
		rrrank = RivalRebelsRank.getForID(buf.readByte());
		resets = buf.readInt();
		isreset = buf.readBoolean();
        this.profile = new GameProfile(buf.readUUID(), buf.readUtf());
	}

    public void fromNbt(CompoundTag nbt) {
        rrclass = RivalRebelsClass.getForID(nbt.getByte("rrclass"));
        rrteam = RivalRebelsTeam.getForID(nbt.getByte("rrteam"));
        rrrank = RivalRebelsRank.getForID(nbt.getByte("rrrank"));
        resets = nbt.getInt("resets");
        isreset = nbt.getBoolean("isreset");
        this.profile = new GameProfile(nbt.getUUID("id"), nbt.getString("username"));
    }
}

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
package assets.rivalrebels.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTarget extends EntityLivingBase
{
	private Entity relay;
	public EntityTarget(World par1World)
	{
		super(par1World);
    }
	public EntityTarget(World par1World, Entity relay)
	{
		super(par1World);
		this.relay = relay;
        this.setEntityBoundingBox(this.relay.getEntityBoundingBox());
		height= this.relay.height;
		width = this.relay.width;
    }

    @Override
    public double getYOffset() {
        return relay.getYOffset();
    }

    @Override
	public void onUpdate()
	{
		if (relay ==null|| relay.isDead) {
			setDead();
		} else {
			setPosition(relay.posX, relay.posY, relay.posZ);
		}
	}
	@Override
	public boolean attackEntityFrom(DamageSource ds, float f)
    {
		relay.attackEntityFrom(ds, f);
		return true;
    }
	@Override
	public ItemStack getHeldItem()
	{
		return null;
	}
	@Override
	public ItemStack getEquipmentInSlot(int p_71124_1_)
	{
		return null;
	}

    @Override
    public ItemStack getCurrentArmor(int slotIn) {
        return null;
    }

    @Override
	public void setCurrentItemOrArmor(int slotIn, ItemStack itemStackIn) {
	}

    @Override
    public ItemStack[] getInventory() {
        return null;
    }

    @Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("id", relay.getEntityId());
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		relay = worldObj.getEntityByID(nbt.getInteger("id"));
	}
}

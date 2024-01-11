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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;

import java.util.Collections;

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
		setEntityBoundingBox(this.relay.getEntityBoundingBox());
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
		if (relay ==null|| relay.isDead)
		{
			setDead();
		}
		else
		{
			setPosition(relay.posX, relay.posY, relay.posZ);
		}
	}

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

    @Override
	public boolean attackEntityFrom(DamageSource ds, float f)
    {
		relay.attackEntityFrom(ds, f);
		return true;
    }

    @Override
    public ItemStack getHeldItem(EnumHand hand) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean hasItemInSlot(EntityEquipmentSlot slot) {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public Iterable<ItemStack> getEquipmentAndArmor() {
        return Collections.emptyList();
    }

    @Override
    public Iterable<ItemStack> getHeldEquipment() {
        return Collections.emptyList();
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slot, ItemStack stack) {
    }

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("id", relay.getEntityId());
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		relay = world.getEntityByID(nbt.getInteger("id"));
	}
}

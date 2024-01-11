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
package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.item.weapon.ItemRoda;
import assets.rivalrebels.common.packet.ADSUpdatePacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityReciever extends TileEntityMachineBase implements IInventory
{
	public double			yaw;
	public double			pitch;
	public Entity			target;
	public double			xO						= 0;
	public double			zO						= 0;
	int						direction;
	double					ll						= -50;
	double					ul						= 90;
	double					scale					= 1.5;
	public NonNullList<ItemStack> chestContents			= NonNullList.withSize(9, ItemStack.EMPTY);
	private int				ticksSinceLastTarget	= 0;
	public int				yawLimit				= 180;
	public boolean			kTeam					= true;
	public boolean			kPlayers				= false;
	public boolean			kMobs					= true;
	public boolean			hasWeapon				= false;
	private RivalRebelsTeam	team;
	private int				ammoCounter;
	private double			prevTx					= 0;
	private double			prevTy					= 0;
	private double			prevTz					= 0;
	private Entity			le						= null;
	public int				wepSelected;
	public static int		staticEntityIndex		= 1;
	public int				entityIndex				= 1;
	public String			username				= "nohbdy";
	private int ticksincepacket;
	int ticksSinceLastShot = 0;

	public TileEntityReciever()
	{
		entityIndex = staticEntityIndex;
		pInM = 400;
		if (RivalRebels.freeDragonAmmo)
		{
			hasWeapon = true;
			team = RivalRebelsTeam.NONE;
			kPlayers = true;
			chestContents.set(3, new ItemStack(RivalRebels.battery, 64));
			chestContents.set(4, new ItemStack(RivalRebels.battery, 64));
			chestContents.set(5, new ItemStack(RivalRebels.battery, 64));
			chestContents.set(0, new ItemStack(RivalRebels.fuel, 64));
			chestContents.set(1, new ItemStack(RivalRebels.fuel, 64));
			chestContents.set(2, new ItemStack(RivalRebels.fuel, 64));
		}
	}

	@Override
	public void update()
	{
		super.update();
		if (xO == zO) updateDirection();
		powered(0, 0);
		convertBatteryToEnergy();
		if (!hasWeapon && wepSelected != 0 && hasWepReqs()) setWep(wepSelected);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 16384.0D;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(getPos().add(-1, -1, -1), getPos().add(2, 2, 2));
	}

	private boolean hasBattery()
	{
		return !chestContents.get(3).isEmpty() ||
            !chestContents.get(4).isEmpty() ||
            !chestContents.get(5).isEmpty() ||
            RivalRebels.infiniteAmmo;
	}

	private void convertBatteryToEnergy()
	{
		while (pInR < pInM / 2 && hasBattery())
		{
			pInR += 800;
			consumeBattery();
		}
	}

	private void consumeBattery()
	{
		if (!chestContents.get(3).isEmpty()) decrStackSize(3, 1);
		else if (!chestContents.get(4).isEmpty()) decrStackSize(4, 1);
		else if (!chestContents.get(5).isEmpty()) decrStackSize(5, 1);
	}

	public boolean hasWepReqs()
	{
		return !chestContents.get(6).isEmpty() &&
            !chestContents.get(7).isEmpty() &&
            !chestContents.get(8).isEmpty();
	}

	public void setWep(int wep)
	{
		if (wep != 0)
		{
			if (!chestContents.get(6).isEmpty() && chestContents.get(6).hasTagCompound())
			{
				team = RivalRebelsTeam.getForID(chestContents.get(6).getTagCompound().getInteger("team"));
				username = chestContents.get(6).getTagCompound().getString("username");
			}
			chestContents.set(6, ItemStack.EMPTY);
            chestContents.set(7, ItemStack.EMPTY);
            chestContents.set(8, ItemStack.EMPTY);
			hasWeapon = true;
			wepSelected = 0;
		}
	}

	@Override
	public float powered(float power, float distance)
	{
		if (hasWeapon)
		{
			ticksSinceLastTarget += 1;
			if (ticksSinceLastTarget == 3)
			{
				target = getTarget();
				ticksSinceLastTarget = 0;
			}
			ticksSinceLastShot++;
			if (ticksSinceLastShot > ItemRoda.rates[entityIndex])
			{
				if (target != null)
				{
					ticksSinceLastShot = 0;
					lookAt(target);
					if (hasAmmo())
					{
						if (world.rand.nextInt(3) == 0)
						{
							RivalRebelsSoundPlayer.playSound(world, getPos().getX(), getPos().getY(), getPos().getZ(), 8, 1, 0.1f);
						}
						float rotationYaw = (float) (180 - yaw);
						float rotationPitch = (float) (-pitch);
						double motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
						double motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
						double motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
						ItemRoda.spawn(entityIndex,world,getPos().getX() + xO + 0.5, getPos().getY() + 0.75, getPos().getZ() + zO + 0.5,motionX,motionY,motionZ,1.0f,0.0f);
						useAmmo();
					}
					return power - 4;
				}
			}
			ticksincepacket++;
			if (ticksincepacket > 6 && !world.isRemote)
			{
				ticksincepacket = 0;
				PacketDispatcher.packetsys.sendToAll(new ADSUpdatePacket(getPos(), yawLimit, kMobs, kTeam, kPlayers, hasWeapon, username));
			}
		}
		return power - 1;
	}

	/*
	 * public Packet getDescriptionPacket() { Packet132TileEntityData p = new Packet132TileEntityData(); NBTTagCompound nbt = new NBTTagCompound(); writeToNBT(nbt); p.data = nbt; p.xPosition = getPos().getX();
	 * p.yPosition = getPos().getY(); p.zPosition = getPos().getZ(); return p; } public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) { readFromNBT(pkt.data); }
	 */

	private boolean hasAmmo()
	{
		return !chestContents.get(0).isEmpty() ||
            !chestContents.get(1).isEmpty() ||
            !chestContents.get(2).isEmpty() ||
            RivalRebels.infiniteAmmo;
	}

	private boolean useAmmo()
	{
		ammoCounter++;
		if (ammoCounter == 9)
		{
			ammoCounter = 0;
			if (!chestContents.get(0).isEmpty()) decrStackSize(0, 1);
			else if (!chestContents.get(1).isEmpty()) decrStackSize(1, 1);
			else if (!chestContents.get(2).isEmpty()) decrStackSize(2, 1);
			else return false;
			return true;
		}
		return true;
	}

	private Entity getTarget()
	{
        double ldist = 40*40;
		Entity result = null;
        for (Entity e : world.loadedEntityList) {
            double dist = e.getDistanceSq(getPos().getX() + 0.5 + xO, getPos().getY() + 0.5, getPos().getZ() + 0.5 + zO);
            if (dist < ldist) {
                if (canTarget(e)) {
                    ldist = dist;
                    result = e;
                }
            }
        }
		return result;
	}

	private boolean canTarget(Entity e)
	{
		return e != null && ((e instanceof EntityLivingBase && ((EntityLivingBase) e).getHealth() > 0) || e instanceof EntityRhodes) && getPitchTo(e, 0) > ll && getPitchTo(e, 0) < ul && isValidTarget(e) && canSee(e);
	}

	private boolean isValidTarget(Entity e)
	{
		if (e == null) return false;
		else if (e instanceof EntityPlayer p)
		{
            if (p.capabilities.isCreativeMode) return false;
			else
			{
				if (kPlayers) return true;
				else if (!kTeam) return false;
				RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(((EntityPlayer) e).getGameProfile());
				if (rrp == null) return kTeam;
				if (rrp.rrteam == RivalRebelsTeam.NONE) return !p.getName().equals(username);
				if (rrp.rrteam != team) return kTeam;
				else return false;
			}
		}
		else return (kMobs && (e instanceof EntityRhodes || (e instanceof EntityMob && !(e instanceof EntityAnimal) && !(e instanceof EntityBat) && !(e instanceof EntityVillager) && !(e instanceof EntitySquid)) || e instanceof EntityGhast));
	}

	private boolean canSee(Entity e)
	{
		int yaw = (int) (getYawTo(e, 0) - getBaseRotation() + 360) % 360;
		if (Math.abs(yaw) > yawLimit / 2 && Math.abs(yaw) < 360 - (yawLimit / 2)) return false;
		Vec3d start = new Vec3d(e.posX, e.posY + e.getEyeHeight(), e.posZ);
		Vec3d end = new Vec3d(getPos()).add(0.5 + xO, 0.5, 0.5 + zO);
		RayTraceResult mop = world.rayTraceBlocks(start, end, false, true, false);
		return mop == null || (mop.getBlockPos().equals(this.getPos()));
	}

	private void updateDirection()
	{
		direction = this.getBlockMetadata();
		xO = 0.0;
		zO = 0.0;
		if (direction == 2) zO = -0.76f;
		if (direction == 4) xO = -0.76f;
		if (direction == 3) zO = 0.76f;
		if (direction == 5) xO = 0.76f;
	}

	public int lookAt(Entity t)
	{
		double dist = t.getDistance(getPos().getX() + 0.5 + xO, getPos().getY() + 0.5, getPos().getZ() + 0.5 + zO);
		double ya = getYawTo(t, le == t ? dist * 1.00 : 0);
		double pi = getPitchTo(t, le == t ? dist * 1.00 : 0);
		if (pi > ll && pi < ul)
		{
			pitch = (pitch + pitch + pitch + pi) / 4;
			if (yaw - ya < -180) yaw += 360;
			else if (yaw - ya > 180) yaw -= 360;
			yaw = (yaw + yaw + yaw + ya) / 4;
			//pitch += dist / 10;
			prevTx = t.posX;
			prevTy = t.posY;
			prevTz = t.posZ;
			le = t;
			return 1;
		}
		else return 0;
	}

	public double getYawTo(Entity t, double off)
	{
		double x = getPos().getX() + 0.5 + xO - t.posX - (t.posX - prevTx) * off;
		double z = getPos().getZ() + 0.5 + zO - t.posZ - (t.posZ - prevTz) * off;
		double ya = Math.atan2(x, z);
		return ((ya / Math.PI) * 180);
	}

	public double getPitchTo(Entity t, double off)
	{
		double x = getPos().getX() + 0.5 + xO - t.posX - (t.posX - prevTx) * off;
		double y = getPos().getY() + (0.5 * scale) - t.posY - t.getEyeHeight() - (t.posY - prevTy) * off;
		double z = getPos().getZ() + 0.5 + zO - t.posZ - (t.posZ - prevTz) * off;
		double d = Math.sqrt(x * x + z * z);
		double pi = Math.atan2(d, -y);
		return 90 - ((pi / Math.PI) * 180);
	}

	public int getBaseRotation()
	{
		int m = getBlockMetadata();
		short r = 0;
		if (m == 2) r = 0;
		if (m == 3) r = 180;
		if (m == 4) r = 90;
		if (m == 5) r = 270;
		return r;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return 9;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1)
	{
		if (par1 >= getSizeInventory()) return ItemStack.EMPTY;
		return this.chestContents.get(par1);
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (!this.chestContents.get(par1).isEmpty())
		{
			ItemStack var3;

			if (this.chestContents.get(par1).getCount() <= par2)
			{
				var3 = this.chestContents.get(par1);
				this.chestContents.set(par1, ItemStack.EMPTY);
            }
			else
			{
				var3 = this.chestContents.get(par1).splitStack(par2);

				if (this.chestContents.get(par1).isEmpty())
				{
					this.chestContents.set(par1, ItemStack.EMPTY);
				}
            }
            return var3;
        }
		return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeStackFromSlot(int index) {
		if (index >= getSizeInventory()) return ItemStack.EMPTY;
		if (!this.chestContents.get(index).isEmpty())
		{
			ItemStack var2 = this.chestContents.get(index);
			this.chestContents.set(index, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index >= getSizeInventory()) return;
		this.chestContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?*
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.world.getTileEntity(this.getPos()) == this && par1EntityPlayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

        ItemStackHelper.loadAllItems(nbt, this.chestContents);
		yawLimit = nbt.getInteger("yawLimit");
		kPlayers = nbt.getBoolean("kPlayers");
		kTeam = nbt.getBoolean("kTeam");
		kMobs = nbt.getBoolean("kMobs");
		hasWeapon = nbt.getBoolean("hasWeapon");
		username = nbt.getString("username");
		team = RivalRebelsTeam.getForID(nbt.getInteger("team"));
		entityIndex = nbt.getInteger("entityIndex");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

        ItemStackHelper.saveAllItems(nbt, this.chestContents);
		nbt.setInteger("yawLimit", yawLimit);
		nbt.setBoolean("kPlayers", kPlayers);
		nbt.setBoolean("kTeam", kTeam);
		nbt.setBoolean("kMobs", kMobs);
		nbt.setBoolean("hasWeapon", hasWeapon);
		nbt.setString("username", username);
		nbt.setInteger("entityIndex", entityIndex);
		if (team != null) nbt.setInteger("team", team.ordinal());
        return nbt;
    }

	@Override
	public String getName()
	{
		return "Automated Defense System";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

    @Override
    public void clear() {
        this.chestContents.clear();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.chestContents) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}

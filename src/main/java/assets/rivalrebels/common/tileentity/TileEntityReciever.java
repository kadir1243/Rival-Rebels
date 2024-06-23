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
import assets.rivalrebels.common.block.machine.BlockReciever;
import assets.rivalrebels.common.container.ContainerReciever;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.weapon.ItemRoda;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import org.jetbrains.annotations.Nullable;

public class TileEntityReciever extends TileEntityMachineBase implements Inventory, NamedScreenHandlerFactory
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
	public DefaultedList<ItemStack> chestContents			= DefaultedList.ofSize(9, ItemStack.EMPTY);
	private int				ticksSinceLastTarget	= 0;
	public int				yawLimit				= 180;
	public boolean			kTeam					= true;
	public boolean			kPlayers				= false;
	public boolean			kMobs					= true;
	public boolean			hasWeapon				= false;
	private RivalRebelsTeam	team;
	private int				ammoCounter;
    private Vec3d prevTpos = Vec3d.ZERO;
	private Entity			le						= null;
	public int				wepSelected;
	public static int		staticEntityIndex		= 1;
	public int				entityIndex				= 1;
	public String			username				= "nohbdy";
	private int ticksincepacket;
	int ticksSinceLastShot = 0;

	public TileEntityReciever(BlockPos pos, BlockState state) {
        super(RRTileEntities.RECIEVER, pos, state);
		entityIndex = staticEntityIndex;
		pInM = 400;
		if (RivalRebels.freeDragonAmmo)
		{
			hasWeapon = true;
			team = RivalRebelsTeam.NONE;
			kPlayers = true;
			chestContents.set(3, new ItemStack(RRItems.battery, 64));
			chestContents.set(4, new ItemStack(RRItems.battery, 64));
			chestContents.set(5, new ItemStack(RRItems.battery, 64));
			chestContents.set(0, new ItemStack(RRItems.fuel, 64));
			chestContents.set(1, new ItemStack(RRItems.fuel, 64));
			chestContents.set(2, new ItemStack(RRItems.fuel, 64));
		}
	}

	@Override
	public void tick()
	{
		super.tick();
		if (xO == zO) updateDirection();
		powered(0, 0);
		convertBatteryToEnergy();
		if (!hasWeapon && wepSelected != 0 && hasWepReqs()) setWep(wepSelected);
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
		if (!chestContents.get(3).isEmpty()) removeStack(3, 1);
		else if (!chestContents.get(4).isEmpty()) removeStack(4, 1);
		else if (!chestContents.get(5).isEmpty()) removeStack(5, 1);
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
			if (!chestContents.get(6).isEmpty() && chestContents.get(6).hasNbt())
			{
				team = RivalRebelsTeam.getForID(chestContents.get(6).getNbt().getInt("team"));
				username = chestContents.get(6).getNbt().getString("username");
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
						if (world.random.nextInt(3) == 0)
						{
							RivalRebelsSoundPlayer.playSound(world, getPos().getX(), getPos().getY(), getPos().getZ(), 8, 1, 0.1f);
						}
						float yaw = (float) (180 - this.yaw);
						float pitch = (float) (-this.pitch);
						double motionX = (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI));
						double motionZ = (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI));
						double motionY = (-MathHelper.sin(pitch / 180.0F * (float) Math.PI));
						ItemRoda.spawn(entityIndex,world,getPos().getX() + xO + 0.5, getPos().getY() + 0.75, getPos().getZ() + zO + 0.5,motionX,motionY,motionZ,1.0f,0.0f);
						useAmmo();
					}
					return power - 4;
				}
			}
			ticksincepacket++;
			if (ticksincepacket > 6 && !world.isClient) {
				ticksincepacket = 0;
			}
		}
		return power - 1;
	}

    @Nullable
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
        return nbt;
    }

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
			if (!chestContents.get(0).isEmpty()) removeStack(0, 1);
			else if (!chestContents.get(1).isEmpty()) removeStack(1, 1);
			else if (!chestContents.get(2).isEmpty()) removeStack(2, 1);
			else return false;
			return true;
		}
		return true;
	}

	private Entity getTarget()
	{
        double ldist = 40*40;
		Entity result = null;
        Box box = Box.from(BlockBox.create(getPos().add(40, 40, 40), getPos().add(-40, -40, -40)));
        for (Entity e : world.getEntitiesByType(null, box, this::canTarget)) {
            double dist = e.squaredDistanceTo(getPos().getX() + 0.5 + xO, getPos().getY() + 0.5, getPos().getZ() + 0.5 + zO);
            if (dist < ldist) {
                ldist = dist;
                result = e;
            }
        }
        return result;
	}

	private boolean canTarget(Entity e)
	{
		return ((e instanceof LivingEntity && ((LivingEntity) e).getHealth() > 0) || e instanceof EntityRhodes) && getPitchTo(e, 0) > ll && getPitchTo(e, 0) < ul && isValidTarget(e) && canSee(e);
	}

	private boolean isValidTarget(Entity e)
	{
		if (e == null) return false;
		else if (e instanceof PlayerEntity p)
		{
            if (p.getAbilities().invulnerable) return false;
			else
			{
				if (kPlayers) return true;
				else if (!kTeam) return false;
				RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(((PlayerEntity) e).getGameProfile());
				if (rrp == null) return kTeam;
				if (rrp.rrteam == RivalRebelsTeam.NONE) return !p.getGameProfile().getName().equals(username);
				if (rrp.rrteam != team) return kTeam;
				else return false;
			}
		}
		else return (kMobs && (e instanceof EntityRhodes || (e instanceof MobEntity && !(e instanceof AnimalEntity) && !(e instanceof BatEntity) && !(e instanceof VillagerEntity) && !(e instanceof SquidEntity)) || e instanceof GhastEntity));
	}

	private boolean canSee(Entity e)
	{
		int yaw = (int) (getYawTo(e, 0) - getBaseRotation() + 360) % 360;
		if (Math.abs(yaw) > yawLimit / 2 && Math.abs(yaw) < 360 - (yawLimit / 2)) return false;
		Vec3d start = e.getPos().add(0, e.getEyeHeight(e.getPose()), 0);
		Vec3d end = new Vec3d(getPos().getX(), getPos().getY(), getPos().getZ()).add(0.5 + xO, 0.5, 0.5 + zO);
		BlockHitResult mop = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, ShapeContext.absent()));
		return mop == null || (mop.getBlockPos().equals(this.getPos()));
	}

	private void updateDirection()
	{
		direction = this.getCachedState().get(BlockReciever.META);
		xO = 0.0;
		zO = 0.0;
		if (direction == 2) zO = -0.76f;
		if (direction == 4) xO = -0.76f;
		if (direction == 3) zO = 0.76f;
		if (direction == 5) xO = 0.76f;
	}

	public int lookAt(Entity t)
	{
		double dist = Math.sqrt(t.squaredDistanceTo(getPos().getX() + 0.5 + xO, getPos().getY() + 0.5, getPos().getZ() + 0.5 + zO));
		double ya = getYawTo(t, le == t ? dist : 0);
		double pi = getPitchTo(t, le == t ? dist : 0);
		if (pi > ll && pi < ul)
		{
			pitch = (pitch + pitch + pitch + pi) / 4;
			if (yaw - ya < -180) yaw += 360;
			else if (yaw - ya > 180) yaw -= 360;
			yaw = (yaw + yaw + yaw + ya) / 4;
			//pitch += dist / 10;
            prevTpos = t.getPos();
			le = t;
			return 1;
		}
		else return 0;
	}

	public double getYawTo(Entity t, double off)
	{
		double x = getPos().getX() + 0.5 + xO - t.getX() - (t.getX() - prevTpos.getX()) * off;
		double z = getPos().getZ() + 0.5 + zO - t.getZ() - (t.getZ() - prevTpos.getZ()) * off;
		double ya = Math.atan2(x, z);
		return ((ya / Math.PI) * 180);
	}

	public double getPitchTo(Entity t, double off) {
		double x = getPos().getX() + 0.5 + xO - t.getX() - (t.getX() - prevTpos.getX()) * off;
		double y = getPos().getY() + (0.5 * scale) - t.getY() - t.getEyeHeight(t.getPose()) - (t.getY() - prevTpos.getY()) * off;
		double z = getPos().getZ() + 0.5 + zO - t.getZ() - (t.getZ() - prevTpos.getZ()) * off;
		double d = Math.sqrt(x * x + z * z);
		double pi = Math.atan2(d, -y);
		return 90 - ((pi / Math.PI) * 180);
	}

	public int getBaseRotation()
	{
		int m = getCachedState().get(BlockReciever.META);
		int r = 0;
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
	public int size()
	{
		return 9;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStack(int par1)
	{
		if (par1 >= size()) return ItemStack.EMPTY;
		return this.chestContents.get(par1);
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack removeStack(int par1, int par2)
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
				var3 = this.chestContents.get(par1).split(par2);

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
    public ItemStack removeStack(int index) {
		if (index >= size()) return ItemStack.EMPTY;
		if (!this.chestContents.get(index).isEmpty())
		{
			ItemStack var2 = this.chestContents.get(index);
			this.chestContents.set(index, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

    @Override
	public void setStack(int index, ItemStack stack)
	{
		if (index >= size()) return;
		this.chestContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack())
		{
			stack.setCount(this.getMaxCountPerStack());
		}
	}

    @Override
	public boolean canPlayerUse(PlayerEntity par1EntityPlayer)
	{
		return this.world.getBlockEntity(this.getPos()) == this && par1EntityPlayer.squaredDistanceTo(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        Inventories.readNbt(nbt, this.chestContents);
		yawLimit = nbt.getInt("yawLimit");
		kPlayers = nbt.getBoolean("kPlayers");
		kTeam = nbt.getBoolean("kTeam");
		kMobs = nbt.getBoolean("kMobs");
		hasWeapon = nbt.getBoolean("hasWeapon");
		username = nbt.getString("username");
		team = RivalRebelsTeam.getForID(nbt.getInt("team"));
		entityIndex = nbt.getInt("entityIndex");
	}

    @Override
    public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

        Inventories.writeNbt(nbt, this.chestContents);
		nbt.putInt("yawLimit", yawLimit);
		nbt.putBoolean("kPlayers", kPlayers);
		nbt.putBoolean("kTeam", kTeam);
		nbt.putBoolean("kMobs", kMobs);
		nbt.putBoolean("hasWeapon", hasWeapon);
		nbt.putString("username", username);
		nbt.putInt("entityIndex", entityIndex);
		if (team != null) nbt.putInt("team", team.ordinal());
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Automated Defense System");
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

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ContainerReciever(syncId, inv, this, propertyDelegate);
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> yawLimit;
                case 1 -> kTeam ? 1 : 0;
                case 2 -> kPlayers ? 1 : 0;
                case 3 -> kMobs ? 1 : 0;
                case 4 -> hasWeapon ? 1 : 0;
                case 5 -> hasWepReqs() ? 1 : 0;
                case 6 -> Float.floatToIntBits(pInR);

                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 1 -> kTeam = value == 1;
                case 2 -> kPlayers = value == 1;
                case 3 -> kMobs = value == 1;
                case 4 -> hasWeapon = value == 1;
                case 5 -> setWep(value);
                default -> {}
            }
        }

        @Override
        public int size() {
            return 7;
        }
    };
}

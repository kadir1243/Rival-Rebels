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
import assets.rivalrebels.common.item.components.ChipData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.item.weapon.ItemRoda;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public class TileEntityReciever extends TileEntityMachineBase implements Container, MenuProvider
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
    private Vec3 prevTpos = Vec3.ZERO;
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
		if (!chestContents.get(3).isEmpty()) removeItem(3, 1);
		else if (!chestContents.get(4).isEmpty()) removeItem(4, 1);
		else if (!chestContents.get(5).isEmpty()) removeItem(5, 1);
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
			if (!chestContents.get(6).isEmpty() && chestContents.get(6).has(RRComponents.CHIP_DATA))
			{
                ChipData chipData = chestContents.get(6).get(RRComponents.CHIP_DATA);
                team = chipData.team();
				username = chipData.username();
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
						if (level.random.nextInt(3) == 0)
						{
							RivalRebelsSoundPlayer.playSound(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 8, 1, 0.1f);
						}
						float yaw = (float) (180 - this.yaw);
						float pitch = (float) (-this.pitch);
						double motionX = (-Mth.sin(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI));
						double motionZ = (Mth.cos(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI));
						double motionY = (-Mth.sin(pitch / 180.0F * (float) Math.PI));
						ItemRoda.spawn(entityIndex,level,getBlockPos().getX() + xO + 0.5, getBlockPos().getY() + 0.75, getBlockPos().getZ() + zO + 0.5,motionX,motionY,motionZ,1.0f,0.0f);
						useAmmo();
					}
					return power - 4;
				}
			}
			ticksincepacket++;
			if (ticksincepacket > 6 && !level.isClientSide) {
				ticksincepacket = 0;
			}
		}
		return power - 1;
	}

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbt = new CompoundTag();
        saveAdditional(nbt, registries);
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
			if (!chestContents.get(0).isEmpty()) removeItem(0, 1);
			else if (!chestContents.get(1).isEmpty()) removeItem(1, 1);
			else if (!chestContents.get(2).isEmpty()) removeItem(2, 1);
			else return false;
			return true;
		}
		return true;
	}

	private Entity getTarget()
	{
        double ldist = 40*40;
		Entity result = null;
        AABB box = AABB.of(BoundingBox.fromCorners(getBlockPos().offset(40, 40, 40), getBlockPos().offset(-40, -40, -40)));
        for (Entity e : level.getEntities((Entity) null, box, this::canTarget)) {
            double dist = e.distanceToSqr(getBlockPos().getX() + 0.5 + xO, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5 + zO);
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
		else if (e instanceof Player p)
		{
            if (p.getAbilities().invulnerable) return false;
			else
			{
				if (kPlayers) return true;
				else if (!kTeam) return false;
				RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(((Player) e).getGameProfile());
				if (rrp == null) return kTeam;
				if (rrp.rrteam == RivalRebelsTeam.NONE) return !p.getGameProfile().getName().equals(username);
				if (rrp.rrteam != team) return kTeam;
				else return false;
			}
		}
		else return (kMobs && (e instanceof EntityRhodes || (e instanceof Mob && !(e instanceof Animal) && !(e instanceof Bat) && !(e instanceof Villager) && !(e instanceof Squid)) || e instanceof Ghast));
	}

	private boolean canSee(Entity e)
	{
		int yaw = (int) (getYawTo(e, 0) - getBaseRotation() + 360) % 360;
		if (Math.abs(yaw) > yawLimit / 2 && Math.abs(yaw) < 360 - (yawLimit / 2)) return false;
		Vec3 start = e.position().add(0, e.getEyeHeight(e.getPose()), 0);
		Vec3 end = new Vec3(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()).add(0.5 + xO, 0.5, 0.5 + zO);
		BlockHitResult mop = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, CollisionContext.empty()));
		return mop == null || (mop.getBlockPos().equals(this.getBlockPos()));
	}

	private void updateDirection()
	{
		direction = this.getBlockState().getValue(BlockReciever.META);
		xO = 0.0;
		zO = 0.0;
		if (direction == 2) zO = -0.76f;
		if (direction == 4) xO = -0.76f;
		if (direction == 3) zO = 0.76f;
		if (direction == 5) xO = 0.76f;
	}

	public int lookAt(Entity t)
	{
		double dist = Math.sqrt(t.distanceToSqr(getBlockPos().getX() + 0.5 + xO, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5 + zO));
		double ya = getYawTo(t, le == t ? dist : 0);
		double pi = getPitchTo(t, le == t ? dist : 0);
		if (pi > ll && pi < ul)
		{
			pitch = (pitch + pitch + pitch + pi) / 4;
			if (yaw - ya < -180) yaw += 360;
			else if (yaw - ya > 180) yaw -= 360;
			yaw = (yaw + yaw + yaw + ya) / 4;
			//pitch += dist / 10;
            prevTpos = t.position();
			le = t;
			return 1;
		}
		else return 0;
	}

	public double getYawTo(Entity t, double off)
	{
		double x = getBlockPos().getX() + 0.5 + xO - t.getX() - (t.getX() - prevTpos.x()) * off;
		double z = getBlockPos().getZ() + 0.5 + zO - t.getZ() - (t.getZ() - prevTpos.z()) * off;
		double ya = Math.atan2(x, z);
		return ((ya / Math.PI) * 180);
	}

	public double getPitchTo(Entity t, double off) {
		double x = getBlockPos().getX() + 0.5 + xO - t.getX() - (t.getX() - prevTpos.x()) * off;
		double y = getBlockPos().getY() + (0.5 * scale) - t.getY() - t.getEyeHeight(t.getPose()) - (t.getY() - prevTpos.y()) * off;
		double z = getBlockPos().getZ() + 0.5 + zO - t.getZ() - (t.getZ() - prevTpos.z()) * off;
		double d = Math.sqrt(x * x + z * z);
		double pi = Math.atan2(d, -y);
		return 90 - ((pi / Math.PI) * 180);
	}

	public int getBaseRotation()
	{
		int m = getBlockState().getValue(BlockReciever.META);
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
	public int getContainerSize()
	{
		return 9;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getItem(int par1)
	{
		if (par1 >= getContainerSize()) return ItemStack.EMPTY;
		return this.chestContents.get(par1);
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack removeItem(int par1, int par2)
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
    public ItemStack removeItemNoUpdate(int index) {
		if (index >= getContainerSize()) return ItemStack.EMPTY;
		if (!this.chestContents.get(index).isEmpty())
		{
			ItemStack var2 = this.chestContents.get(index);
			this.chestContents.set(index, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

    @Override
	public void setItem(int index, ItemStack stack)
	{
		if (index >= getContainerSize()) return;
		this.chestContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize())
		{
			stack.setCount(this.getMaxStackSize());
		}
	}

    @Override
	public boolean stillValid(Player par1EntityPlayer)
	{
		return this.level.getBlockEntity(this.getBlockPos()) == this && par1EntityPlayer.distanceToSqr(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D) <= 64.0D;
	}

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        ContainerHelper.loadAllItems(nbt, this.chestContents, provider);
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
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
		super.saveAdditional(nbt, provider);

        ContainerHelper.saveAllItems(nbt, this.chestContents, provider);
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
    public Component getDisplayName() {
        return Component.nullToEmpty("Automated Defense System");
    }

    @Override
    public void clearContent() {
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
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new ContainerReciever(syncId, inv, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
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
        public int getCount() {
            return 7;
        }
    };
}

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
package io.github.kadir1243.rivalrebels.common.tileentity;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockReciever;
import io.github.kadir1243.rivalrebels.common.container.ContainerReciever;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.ChipData;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.ItemRoda;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
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
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public class TileEntityReciever extends TileEntityMachineBase implements Container, MenuConstructor {
	public float			yaw;
	public float			pitch;
	public Entity			target;
	public double			xO						= 0;
	public double			zO						= 0;
    double					ll						= -50;
	double					ul						= 90;
	double					scale					= 1.5;
	public final NonNullList<ItemStack> chestContents			= NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
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
	public int				entityIndex;
	public GameProfile player = new GameProfile(ChipData.FAKE_PLAYER, "nobody");
	private int ticksincepacket;
	int ticksSinceLastShot = 0;

	public TileEntityReciever(BlockPos pos, BlockState state) {
        super(RRTileEntities.RECIEVER.get(), pos, state);
		entityIndex = staticEntityIndex;
		pInM = 400;
		if (RRConfig.SERVER.isFreeDragonAmmo())
		{
			hasWeapon = true;
			team = RivalRebelsTeam.NONE;
			kPlayers = true;
			setItem(3, RRItems.battery.toStack(64));
			setItem(4, RRItems.battery.toStack(64));
			setItem(5, RRItems.battery.toStack(64));
			setItem(0, RRItems.fuel.toStack(64));
			setItem(1, RRItems.fuel.toStack(64));
			setItem(2, RRItems.fuel.toStack(64));
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
		return !getItem(3).isEmpty() ||
            !getItem(4).isEmpty() ||
            !getItem(5).isEmpty() ||
            RRConfig.SERVER.isInfiniteAmmo();
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
		if (!getItem(3).isEmpty()) removeItem(3, 1);
		else if (!getItem(4).isEmpty()) removeItem(4, 1);
		else if (!getItem(5).isEmpty()) removeItem(5, 1);
	}

	public boolean hasWepReqs()
	{
		return !getItem(6).isEmpty() &&
            !getItem(7).isEmpty() &&
            !getItem(8).isEmpty();
	}

	public void setWep(int wep)
	{
		if (wep != 0)
		{
			if (!getItem(6).isEmpty() && getItem(6).has(RRComponents.CHIP_DATA))
			{
                ChipData chipData = getItem(6).get(RRComponents.CHIP_DATA);
                team = chipData.team();
				player = chipData.gameProfile();
			}
			setItem(6, ItemStack.EMPTY);
            setItem(7, ItemStack.EMPTY);
            setItem(8, ItemStack.EMPTY);
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
						if (level.random.nextInt(3) == 0) {
							getLevel().playLocalSound(getBlockPos(), RRSounds.FLAME_THROWER_EXTINGUISH.get(), SoundSource.BLOCKS, 0.1F, 1F, false);
						}
						float yaw = 180 - this.yaw;
						float pitch = -this.pitch;
						double motionX = (-Mth.sin(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD));
						double motionZ = (Mth.cos(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD));
						double motionY = (-Mth.sin(pitch * Mth.DEG_TO_RAD));
						ItemRoda.spawn(entityIndex,level,getBlockPos().getX() + xO + 0.5, getBlockPos().getY() + 0.75, getBlockPos().getZ() + zO + 0.5,motionX,motionY,motionZ,1.0f,0.0f);
						useAmmo();
					}
					return power - 4;
				}
			}
			ticksincepacket++;
			if (ticksincepacket > 6 && !level.isClientSide()) {
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
		return !getItem(0).isEmpty() ||
            !getItem(1).isEmpty() ||
            !getItem(2).isEmpty() ||
            RRConfig.SERVER.isInfiniteAmmo();
	}

	private boolean useAmmo()
	{
		ammoCounter++;
		if (ammoCounter == 9)
		{
			ammoCounter = 0;
			if (!getItem(0).isEmpty()) removeItem(0, 1);
			else if (!getItem(1).isEmpty()) removeItem(1, 1);
			else if (!getItem(2).isEmpty()) removeItem(2, 1);
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
		if (e == null || e.isInvulnerable()) return false;
		else if (e instanceof Player p) {
            if (kPlayers) return true;
            else if (!kTeam) return false;
            RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(((Player) e).getGameProfile());
            if (rrp == null) return kTeam;
            if (rrp.rrteam == RivalRebelsTeam.NONE) return !p.getGameProfile().equals(player);
            if (rrp.rrteam != team) return kTeam;
            else return false;
        }
		else return (kMobs && (e instanceof EntityRhodes || (e instanceof Mob && !(e instanceof Animal) && !(e instanceof Bat) && !(e instanceof Villager) && !(e instanceof Squid)) || e instanceof Ghast));
	}

	private boolean canSee(Entity e)
	{
		int yaw = (int) (getYawTo(e, 0) - getBaseRotation() + 360) % 360;
		if (Mth.abs(yaw) > yawLimit / 2 && Mth.abs(yaw) < 360 - (yawLimit / 2)) return false;
		Vec3 start = e.getEyePosition();
		Vec3 end = new Vec3(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()).add(0.5 + xO, 0.5, 0.5 + zO);
		BlockHitResult mop = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, CollisionContext.empty()));
		return mop == null || (mop.getBlockPos().equals(this.getBlockPos()));
	}

	private void updateDirection()
	{
        Direction direction = this.getBlockState().getValue(BlockReciever.FACING);
		xO = 0.0;
		zO = 0.0;
		if (direction == Direction.NORTH) zO = -0.76f;
		else if (direction == Direction.WEST) xO = -0.76f;
		else if (direction == Direction.SOUTH) zO = 0.76f;
		else if (direction == Direction.EAST) xO = 0.76f;
	}

	public int lookAt(Entity t)
	{
		float dist = Mth.sqrt((float) t.distanceToSqr(getBlockPos().getX() + 0.5 + xO, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5 + zO));
		float ya = getYawTo(t, le == t ? dist : 0);
		float pi = getPitchTo(t, le == t ? dist : 0);
		if (pi > ll && pi < ul)
		{
			pitch = (pitch + pitch + pitch + pi) / 4F;
			if (yaw - ya < -180) yaw += 360;
			else if (yaw - ya > 180) yaw -= 360;
			yaw = (yaw + yaw + yaw + ya) / 4F;
			//pitch += dist / 10;
            prevTpos = t.position();
			le = t;
			return 1;
		}
		else return 0;
	}

	public float getYawTo(Entity t, float off)
	{
		double x = getBlockPos().getX() + 0.5 + xO - t.getX() - (t.getX() - prevTpos.x()) * off;
		double z = getBlockPos().getZ() + 0.5 + zO - t.getZ() - (t.getZ() - prevTpos.z()) * off;
		double ya = Math.atan2(x, z);
		return (float) ((ya / Mth.PI) * 180);
	}

	public float getPitchTo(Entity t, float off) {
		double x = getBlockPos().getX() + 0.5 + xO - t.getX() - (t.getX() - prevTpos.x()) * off;
		double y = getBlockPos().getY() + (0.5 * scale) - t.getEyeY() - (t.getY() - prevTpos.y()) * off;
		double z = getBlockPos().getZ() + 0.5 + zO - t.getZ() - (t.getZ() - prevTpos.z()) * off;
		double d = Math.sqrt(x * x + z * z);
		double pi = Math.atan2(d, -y);
		return (float) (90 - ((pi / Mth.PI) * 180));
	}

	public int getBaseRotation() {
        return (int) getBlockState().getValue(BlockReciever.FACING).toYRot();
	}

	@Override
	public int getContainerSize()
	{
		return 9;
	}

	@Override
	public ItemStack getItem(int slot) {
		if (slot >= getContainerSize()) return ItemStack.EMPTY;
		return this.chestContents.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		if (!this.getItem(slot).isEmpty()) {
			ItemStack var3;

			if (this.getItem(slot).getCount() <= amount) {
				var3 = this.getItem(slot);
				this.setItem(slot, ItemStack.EMPTY);
            } else {
				var3 = this.getItem(slot).split(amount);

				if (this.getItem(slot).isEmpty()) {
					this.setItem(slot, ItemStack.EMPTY);
				}
            }
            return var3;
        }
		return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
		if (slot >= getContainerSize()) return ItemStack.EMPTY;
		if (!this.getItem(slot).isEmpty())
		{
			ItemStack var2 = this.getItem(slot);
			this.setItem(slot, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

    @Override
	public void setItem(int slot, ItemStack stack)
	{
		if (slot >= getContainerSize()) return;
		this.chestContents.set(slot, stack);

        stack.limitSize(this.getMaxStackSize(stack));
	}

    @Override
	public boolean stillValid(Player player)
	{
        return Container.stillValidBlockEntity(this, player, 64);
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
        player = new GameProfile(nbt.getUUID("uuid"), nbt.getString("username"));
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
		nbt.putString("username", player.getName());
        nbt.putUUID("uuid", player.getId());
		nbt.putInt("entityIndex", entityIndex);
		if (team != null) nbt.putInt("team", team.ordinal());
    }

    @Override
    public void clearContent() {
        this.chestContents.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.chestContents.stream().allMatch(ItemStack::isEmpty);
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

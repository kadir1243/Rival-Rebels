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

import assets.rivalrebels.RRClient;
import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.command.CommandRobot;
import assets.rivalrebels.common.core.BlackList;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.brain.*;
import assets.rivalrebels.common.explosion.Explosion;
import assets.rivalrebels.common.packet.RhodesJumpPacket;
import assets.rivalrebels.common.packet.RhodesPacket;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityNukeCrate;
import assets.rivalrebels.common.tileentity.TileEntityReactor;
import assets.rivalrebels.common.tileentity.TileEntityRhodesActivator;
import assets.rivalrebels.common.util.ItemUtil;
import assets.rivalrebels.common.util.ModBlockTags;
import assets.rivalrebels.common.util.Translations;
import com.mojang.serialization.Dynamic;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.Predicate;

public class EntityRhodes extends LivingEntity implements VariantHolder<Holder<RhodesType>> {
    public static final EntityDataAccessor<Boolean> FIRE = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> PLASMA = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ENERGY = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> NUKE_COUNT = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> B2_ENERGY = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROCKET_COUNT = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FLAME_COUNT = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> FORCE_FIELD = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> FLAG_TEXTURE_LOCATION = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Holder<RhodesType>> TYPE = SynchedEntityData.defineId(EntityRhodes.class, RhodesTypes.HOLDER_DATA_SERIALIZER);
    public static final EntityDataAccessor<Integer> ON_LASERS = SynchedEntityData.defineId(EntityRhodes.class, EntityDataSerializers.INT);

    private int damageUntilWake = 100;
    private static final Set<Predicate<BlockState>> blocklist = new HashSet<>(Set.of(
        BlackList.of(Blocks.BEDROCK),
        BlackList.of(BlockTags.DIRT),
        BlackList.of(BlockTags.BASE_STONE_OVERWORLD),
        BlackList.of(BlockTags.BASE_STONE_NETHER),
        BlackList.of(BlockTags.SAND),
        BlackList.of(Blocks.SANDSTONE),
        BlackList.of(Blocks.GRAVEL),
        BlackList.of(Blocks.IRON_BLOCK),
        BlackList.of(Blocks.LAPIS_BLOCK),
        BlackList.of(BlockTags.SLABS),
        BlackList.of(BlockTags.STONE_BRICKS),
        BlackList.of(Blocks.COBBLESTONE),
        BlackList.of(Blocks.OBSIDIAN),
        BlackList.of(Blocks.END_STONE),
        BlackList.of(BlockTags.TERRACOTTA),
        BlackList.of(ModBlockTags.GLAZED_TERRACOTTAS),
        BlackList.of(RRBlocks.reactive)
    ));
    public float bodyyaw = 0;
    public float headpitch = 0;
	public float leftarmyaw = 0;
	public float leftarmpitch = 0;
	public float rightarmyaw = 0;
	public float rightarmpitch = 0;
	public float leftthighpitch = 0;
	public float rightthighpitch = 0;
	public float leftshinpitch = 0;
	public float rightshinpitch = 0;
	public float lastbodyyaw = 0;
    public float lastheadpitch = 0;
	public float lastleftarmyaw = 0;
	public float lastleftarmpitch = 0;
	public float lastrightarmyaw = 0;
	public float lastrightarmpitch = 0;
	public float lastleftthighpitch = 0;
	public float lastrightthighpitch = 0;
	public float lastleftshinpitch = 0;
	public float lastrightshinpitch = 0;
    public boolean endangered = false;
	public int flying = 0;

	public int ticksSinceLastPacket = 0;
    public static byte lastct = 0;
	public static Holder<RhodesType> forcecolor = RivalRebels.RHODES_TYPE_REGISTRY.wrapAsHolder(RhodesTypes.Rhodes);
	public Player rider;
	public Player passenger1;
	public Player passenger2;
	public static final int recharge = 3;
	public static final int ecjet = 10+recharge;
	public static final int eclaser = 6+recharge;
	public static final int ecshield = 8+recharge;
	public static final int MAX_ENERGY = 800;
    public boolean rocket = false;
	public boolean laser = false;
	public boolean prevflame = false;
	public boolean flame = false;
    public boolean bomb = false;
	public boolean jet = false;
	public boolean stop = true;
	public boolean guard = false;
	public boolean b2spirit = false;
	public boolean freeze = false;
	public int plasmacharge = 0;
	public int tickssincenuke = 10;
	public static String texloc = "";

    public BlockPos wakePos = new BlockPos(-1, -1, -1);

    public EntityRhodes(EntityType<? extends EntityRhodes> type, Level world) {
        super(type, world);
    }

	public EntityRhodes(Level w) {
		this(RREntities.RHODES, w);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(RRConfig.SERVER.getRhodesHealth());
		noCulling = true;
		noPhysics = true;
		//pushSpeedReduction = 100;
		RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
        setFlagTextureLocation(texloc);
		if (forcecolor.value() == RhodesTypes.Rhodes) {
            RhodesType[] rhodesTeams = RRConfig.SERVER.getRhodesTeams();
            setVariant(RivalRebels.RHODES_TYPE_REGISTRY.wrapAsHolder(rhodesTeams[lastct]));
			if (!w.isClientSide()) {
				lastct++;
				if (lastct == rhodesTeams.length) lastct = 0;
			}
		} else {
			setVariant(forcecolor);
		}
		RandomSource random = RandomSource.create(RRConfig.SERVER.getRhodesRandomSeed());
        setNukeCount((int) (getNukeCount() + getNukeCount() * random.nextFloat() * RRConfig.SERVER.getRhodesRandomAmmoBonus()));
        setRocketCount((int) (getRocketCount() + getRocketCount() * random.nextFloat() * RRConfig.SERVER.getRhodesRandomAmmoBonus()));
        setFlameCount((int) (getFlameCount() + getFlameCount() * random.nextFloat() * RRConfig.SERVER.getRhodesRandomAmmoBonus()));
    }

	public EntityRhodes(Level w, double x, double y, double z, float s) {
		this(w);
        this.getAttribute(Attributes.SCALE).setBaseValue(s);
        if (getScale() >= 2.0) {
            setNukeCount((int) (getNukeCount() * 0.25));
            setRocketCount((int) (getRocketCount() * 0.004));
		}
        setHealth(getHealth() - 5000 + (int)(5000 * Math.min(getScale(),4)));
		setPos(x, y, z);
		if (!level().isClientSide()) {
            for (Player player : level().players()) {
                player.displayClientMessage(Translations.warning().append(" ").append(Component.translatable(Translations.RHODES_IS_ARMED.toLanguageKey(), getName())), false);
            }
        }
	}

    @Override
	public void tick() {
		if ((wakePos.getY() != -1) && !level().getBlockState(wakePos).is(RRBlocks.rhodesactivator)) {
			damageUntilWake -= 100;
		}
		if (getY() <= 0) {
			kill();
			return;
		}
        makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
        fallDistance = 0.0F;
		if (getHealth() > 0) {
			float headY = 0;
			float syaw = Mth.sin(bodyyaw * Mth.DEG_TO_RAD);
			float cyaw = Mth.cos(bodyyaw * Mth.DEG_TO_RAD);
			float leftlegheight = 7.26756f
					+ (Mth.cos((leftthighpitch+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
					+ (Mth.cos((leftthighpitch+leftshinpitch-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);
			float rightlegheight = 7.26756f
					+ (Mth.cos((rightthighpitch+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
					+ (Mth.cos((rightthighpitch+rightshinpitch-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);
			leftlegheight *= getScale();
			rightlegheight *= getScale();
			float bodyY = Math.max(leftlegheight, rightlegheight);
            this.aiStep();
            if (!level().isClientSide()) {
				doAITick(syaw, cyaw);
				breakBlocks(syaw, cyaw, leftlegheight, rightlegheight, bodyY);
				if (tickCount % 5 == 0) {
                    for (Player player : level().players()) {
                        ServerPlayNetworking.send((ServerPlayer) player, new RhodesPacket(this));
                    }
                }
			}
            Vec3 add = getDeltaMovement().add(position());
            setPosRaw(add.x(), add.y(), add.z());
			if (tickCount % 3 == 0) doCollisions();
			ticksSinceLastPacket++;
            reapplyPosition();

			if (level().isClientSide()) RRClient.rrro.setOverlay(this);

			if (rider!=null) {
				rider.setPos(((getX()+syaw*5.5*getScale()) - rider.getX()) * 0.33f + rider.getX(), ((getY() + bodyY - 10*getScale() - (level().isClientSide?0:rider.getEyeHeight(rider.getPose()))) - rider.getY()) * 0.33f + rider.getY(), ((getZ()+cyaw*5.5*getScale()) - rider.getZ()) * 0.33f + rider.getZ());
				rider.setOnGround(true);
				if (level().isClientSide()) RivalRebels.round.setInvisible(rider);
				rider.makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
				rider.getAbilities().invulnerable = true;
				if (level().isClientSide && rider == Minecraft.getInstance().player) ClientPlayNetworking.send(new RhodesJumpPacket(this.getId()));
			}
			if (passenger1 != null)
			{
				float offset = 1.62f;
				if (level().isClientSide())
				{
					if (Minecraft.getInstance().player == passenger1)
					{
						offset = 0;
					}
				}
				passenger1.setPos(getX()+cyaw*6.5f*getScale(),
										getY() + bodyY - 6.38f*getScale() - offset,
										getZ()-syaw*6.5f*getScale());
				passenger1.setOnGround(true);
				passenger1.makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
			}
			if (passenger2 != null)
			{
				float offset = 1.62f;
				if (level().isClientSide())
				{
					if (Minecraft.getInstance().player == passenger2)
					{
						offset = 0;
					}
				}
				passenger2.setPos(getX()-cyaw*6.5f*getScale(),
										getY() + bodyY - 6.38f*getScale() - offset,
										getZ()+syaw*6.5f*getScale());
				passenger2.setOnGround(true);
				passenger2.makeStuckInBlock(Blocks.COBWEB.defaultBlockState(), new Vec3(0.25, 0.05F, 0.25));
			}
		}
		else
		{
			if (!level().isClientSide())
			{
				if (getHealth() == 0) {
                    MutableComponent text = Translations.status().append(" ").append(getName()).append(" ").append("RivalRebels.meltdown").append((rider == null ? Component.empty() : Component.empty().append(" ").append(rider.getName())));
                    for (Player player : level().players()) {
                        player.sendSystemMessage(text);
                    }
                }
				if (tickCount % 5 == 0) {
                    for (Player player : level().players()) {
                        ServerPlayNetworking.send((ServerPlayer) player, new RhodesPacket(this));
                    }
                }
				if (getHealth() < -100)
				{
					kill();
				}
				if (getHealth() == 0)
				{
					float syaw = Mth.sin(bodyyaw * Mth.DEG_TO_RAD);
					float cyaw = Mth.cos(bodyyaw * Mth.DEG_TO_RAD);
					level().addFreshEntity(new EntityRhodesHead(level(), getX(), getY()+13*getScale(), getZ(), getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesTorso(level(), getX(), getY()+7*getScale(), getZ(), getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesLeftUpperArm(level(), getX()+cyaw*6.4*getScale(), getY()+7*getScale(), getZ()+syaw*6.4*getScale(),getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesRightUpperArm(level(), getX()-cyaw*6.4*getScale(), getY()+7*getScale(), getZ()-syaw*6.4*getScale(),getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesLeftLowerArm(level(), getX()+cyaw*6.4*getScale(), getY()+3*getScale(), getZ()+syaw*6.4*getScale(),getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesRightLowerArm(level(), getX()-cyaw*6.4*getScale(), getY()+3*getScale(), getZ()-syaw*6.4*getScale(),getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesLeftUpperLeg(level(), getX()+cyaw*3*getScale(), getY()-3*getScale(), getZ()+syaw*3*getScale(),getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesRightUpperLeg(level(), getX()-cyaw*3*getScale(), getY()-3*getScale(), getZ()-syaw*3*getScale(),getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesLeftLowerLeg(level(), getX()+cyaw*3*getScale(), getY()-10*getScale(), getZ()+syaw*3*getScale(),getScale(), getVariant()));
					level().addFreshEntity(new EntityRhodesRightLowerLeg(level(), getX()-cyaw*3*getScale(), getY()-10*getScale(), getZ()-syaw*3*getScale(),getScale(), getVariant()));
				}
			}
            setHealth(getHealth() - 1);
		}

		if (rider != null)
		{
			if ((rider.isShiftKeyDown() || !rider.isAlive()) && CommandRobot.rhodesExit)
			{
				freeze = false;
				if (!rider.isCreative()) rider.getAbilities().invulnerable = false;
				rider = null;
			}
			if (isDeadOrDying() && rider != null)
			{
				freeze = false;
				if (!rider.isCreative())
				{
					rider.getAbilities().invulnerable = false;
					rider.hurt(level().damageSources().fellOutOfWorld(), 2000000);
				}
				rider = null;
			}
		}

		if (passenger1 != null)
		{
			if ((passenger1.isShiftKeyDown() || !passenger1.isAlive()) && CommandRobot.rhodesExit)
			{
				if (!passenger1.isCreative()) passenger1.getAbilities().invulnerable = false;
				passenger1 = null;
			}
			if (isDeadOrDying() && passenger1 != null)
			{
				if (!passenger1.isCreative())
				{
					passenger1.getAbilities().invulnerable = false;
					passenger1.hurt(level().damageSources().fellOutOfWorld(), 2000000);
				}
				passenger1 = null;
			}
		}

		if (passenger2 != null)
		{
			if ((passenger2.isShiftKeyDown() || !passenger2.isAlive()) && CommandRobot.rhodesExit)
			{
				if (!passenger2.isCreative()) passenger2.getAbilities().invulnerable = false;
				passenger2 = null;
			}
			if (isDeadOrDying() && passenger2 != null)
			{
				if (!passenger2.isCreative())
				{
					passenger2.getAbilities().invulnerable = false;
					passenger2.hurt(level().damageSources().fellOutOfWorld(), 2000000);
				}
				passenger2 = null;
			}
		}

		prevflame = flame;
	}

    @Override
    protected void serverAiStep() {
        super.serverAiStep();
        this.getBrain().tick((ServerLevel) level(), this);
    }

    private void breakBlocks(float syaw, float cyaw, float leftlegheight, float rightlegheight, float bodyY)
	{
		float leftlegstride = (Mth.sin((leftthighpitch+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
				+ (Mth.sin((leftthighpitch+leftshinpitch-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);
		float rightlegstride = (Mth.sin((rightthighpitch+11.99684962f)*Mth.DEG_TO_RAD) * 7.331691240f)
				+ (Mth.sin((rightthighpitch+rightshinpitch-12.2153067f)*Mth.DEG_TO_RAD) * 8.521366426f);
		leftlegstride *= getScale();
		rightlegstride *= getScale();

		float lpx = (float)getX() - syaw * leftlegstride + cyaw * 3.6846f*getScale();
		float lpy = (float)getY()-15*getScale() + bodyY - leftlegheight;
		float lpz = (float)getZ() - cyaw * leftlegstride - syaw * 3.6846f*getScale();
		float rpx = (float)getX() - syaw * rightlegstride - cyaw * 3.6846f*getScale();
		float rpy = (float)getY()-15*getScale() + bodyY - rightlegheight;
		float rpz = (float)getZ() - cyaw * rightlegstride + syaw * 3.6846f*getScale();
		int ilpx = (int)lpx;
		int ilpy = (int)lpy;
		int ilpz = (int)lpz;
		int irpx = (int)rpx;
		int irpy = (int)rpy;
		int irpz = (int)rpz;

		if (!getBrain().isActive(Activity.IDLE)) {
			if (RRConfig.SERVER.getRhodesBlockBreak()) {
				int sx = (int) (getX() - 5.0f * getScale());
				int sy = (int) (getY() - 15.0f * getScale());
				int sz = (int) (getZ() - 5.0f * getScale());
				int ex = (int) (getX() + 5.0f * getScale());
				int ey = (int) (getY() + 15.0f * getScale());
				int ez = (int) (getZ() + 5.0f * getScale());
				for (int y = sy; y < ey; y++)
				{
					if ((y + tickCount) % 8 == 0)
					{
						for (int x = sx; x < ex; x++)
						{
							for (int z = sz; z < ez; z++)
							{
								setBlock(x,y,z, Blocks.AIR);
							}
						}
					}
				}
			}
			else
			{
				int irpyyoff = irpy + (tickCount % 6);
				int ilpyyoff = ilpy + (tickCount % 6);
				Block b = getBlock(irpx, irpyyoff, irpz);
				if (b != Blocks.WATER && b != Blocks.AIR)
				{
					setBlock(irpx-2, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx-2, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx-1, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx+1, irpyyoff, irpz+2, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz-2, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz-1, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz+1, Blocks.AIR);
					setBlock(irpx+2, irpyyoff, irpz+2, Blocks.AIR);
				}
				b = getBlock(ilpx, ilpyyoff, ilpz);
				if (b != Blocks.WATER && b != Blocks.AIR)
				{
					setBlock(ilpx-2, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx-2, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx-1, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx+1, ilpyyoff, ilpz+2, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz-2, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz-1, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz+1, Blocks.AIR);
					setBlock(ilpx+2, ilpyyoff, ilpz+2, Blocks.AIR);
				}
				int px = (int) getX();
				int py = (int) (getY()-5*getScale() + (tickCount%20)*getScale());
				int pz = (int) getZ();
				for (int x = -4; x < 5; x++)
				{
					for (int z = -4; z < 5; z++)
					{
						b = getBlock(px+x, py, pz+z);
						if (b != Blocks.AIR && b != Blocks.WATER)
						{
							setBlock(px+x, py, pz+z, Blocks.AIR);
							if (random.nextInt(333)==0)
							{
								new Explosion(level(), px, py, pz, 3, false, true, RivalRebelsDamageSource.rocket(level()));
								RivalRebelsSoundPlayer.playSound(this, 23, 3, 4.5f, (float) (0.8f + random.nextDouble()*0.3f));
							}
						}
					}
				}
			}
		}

		boolean bl = blocklist.stream().anyMatch(state -> state.test(getBlockState(ilpx, ilpy-1, ilpz)));
		boolean ol = blocklist.stream().anyMatch(state -> state.test(getBlockState(ilpx, ilpy, ilpz)));
		boolean br = blocklist.stream().anyMatch(state -> state.test(getBlockState(irpx, irpy-1, irpz)));
		boolean or = blocklist.stream().anyMatch(state -> state.test(getBlockState(irpx, irpy, irpz)));

		if (bl||br)
		{
			if (ol||or)
			{
				if (getDeltaMovement().y() < 0.125)
				{
                    setDeltaMovement(getDeltaMovement().x(), 0.125, getDeltaMovement().z());
					if (!isFire() && random.nextInt(32)==0)
					{
						RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
						setFire(true);
					}
				}
			} else if (getDeltaMovement().y() < 0) {
                setDeltaMovement(getDeltaMovement().x(), 0, getDeltaMovement().z());
                setFire(false);
                setPosRaw(getX(), (int) getY(), getZ());
			}
		}
		else if (flying == 0) {
            push(0, -0.03f, 0);
			if (!isFire() && random.nextInt(32)==0)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
				setFire(true);
			}
		}
		if (flying!=0)
		{
			flying--;
			if (b2spirit)
			{
                setDeltaMovement(getDeltaMovement().x(), 0.03, getDeltaMovement().z());
			}
			else
			{
                push(0, 0.03f, 0);
			}
			if (random.nextInt(32)==0)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 1, 4.5f, (float) (0.8f + random.nextDouble()*0.2f));
			}
			setFire(true);
		}
	}

	private void doCollisions() {
        for (Entity e : level().getEntities(this, AABB.of(BoundingBox.infinite()))) {
            if (e == rider || e == passenger1 || e == passenger2) continue;
            double bbd = (e.getBbWidth() + 10 * getScale()) * 0.5;
            double bbh = (e.getBbHeight() + 30 * getScale()) * 0.5;
            if (e instanceof EntityRhodes) {
                bbd = 10 * (((EntityRhodes) e).getScale() + getScale()) * 0.5;
                bbh = 30 * (((EntityRhodes) e).getScale() + getScale()) * 0.5;
            }
            double dist = (e.getX() - getX()) * (e.getX() - getX()) + (e.getZ() - getZ()) * (e.getZ() - getZ());
            if ((getBrain().getActiveNonCoreActivity().isEmpty() || !RRConfig.SERVER.isRhodesAIEnabled()) && e instanceof Player && dist < bbd * bbd * 0.25f && e.getY() < getY() + bbh + 1 && e.getY() > getY() - bbh + 1) {
                if (rider == null) {
                    rider = (Player) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                } else if (passenger1 == null) {
                    passenger1 = (Player) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                } else if (passenger2 == null) {
                    passenger2 = (Player) e;
                    RivalRebelsSoundPlayer.playSound(this, 12, 1, 90f, 1f);
                }
            } else if (dist < bbd * bbd && e.getY() > getY() - bbh && e.getY() < getY() + bbh) {
                if (e != this && !(e instanceof FallingBlockEntity) && !(e instanceof EntityDebris)) {
                    if (e.getY() > getY() + 15) {
                        e.setDeltaMovement(getDeltaMovement().multiply(1, -0.5, 1));
                        e.setPos(getX(), getY() + bbh, getZ());
                    } else if (e.getY() < getY() - 15) {
                        e.setDeltaMovement(getDeltaMovement().multiply(1, -0.5, 1));
                        e.setPos(getX(), getY() - bbh, getZ());
                    } else {
                        e.setDeltaMovement(getDeltaMovement().multiply(-1, 1, -1));
                        double d3 = bbd / Math.sqrt(dist);
                        e.setPos(getX() + (e.getX() - getX()) * d3,
                             e.getY(),
                            getZ() + (e.getZ() - getZ()) * d3);
                    }
                }

                switch (e) {
                    case EntityRocket ignored -> {
                        e.tickCount = RRConfig.SERVER.getRpgDecay();
                        this.hurt(level().damageSources().generic(), 20);
                    }
                    case EntitySeekB83 ignored -> {
                        e.tickCount = 800;
                        this.hurt(level().damageSources().generic(), 24);
                    }
                    case EntityHackB83 entity -> {
                        entity.ticksInAir = -100;
                        this.hurt(level().damageSources().generic(), 40);
                    }
                    case EntityB83 entity -> {
                        entity.ticksInAir = -100;
                        this.hurt(level().damageSources().generic(), 40);
                    }
                    case EntityBomb entityBomb -> {
                        entityBomb.explode(true);
                        for (int i = 0; i < RRConfig.SERVER.getBombDamageToRhodes(); i++)
                            this.hurt(level().damageSources().generic(), 50);
                    }
                    case EntityNuke entityNuke -> {
                        entityNuke.ticksInAir = -100;
                        this.hurt(level().damageSources().generic(), 80);
                    }
                    case EntityTsar entityTsar -> {
                        entityTsar.ticksInAir = -100;
                        this.hurt(level().damageSources().generic(), 100);
                    }
                    case EntityTheoreticalTsar entityTheoreticalTsar -> {
                        entityTheoreticalTsar.ticksInAir = -100;
                        this.hurt(level().damageSources().generic(), 100);
                    }
                    case EntityAntimatterBomb entityAntimatterBomb -> {
                        entityAntimatterBomb.ticksInAir = -100;
                        this.hurt(level().damageSources().generic(), 100);
                    }
                    case EntityTachyonBomb entityTachyonBomb -> {
                        entityTachyonBomb.ticksInAir = -100;
                        this.hurt(level().damageSources().generic(), 100);
                    }
                    case EntityHotPotato ignored -> {
                        e.tickCount = -100;
                        this.hurt(level().damageSources().generic(), 100);
                    }
                    case EntityPlasmoid entityPlasmoid -> {
                        entityPlasmoid.explode();
                        this.hurt(level().damageSources().generic(), 8);
                    }
                    case EntityFlameBall ignored -> {
                        e.kill();
                        this.hurt(level().damageSources().generic(), 3);
                    }
                    case EntityFlameBall1 ignored -> {
                        e.kill();
                        this.hurt(level().damageSources().generic(), 4);
                    }
                    case EntityFlameBall2 ignored -> {
                        e.kill();
                        this.hurt(level().damageSources().generic(), 2);
                    }
                    case EntityLaserBurst ignored -> {
                        e.kill();
                        this.hurt(level().damageSources().generic(), 4);
                    }
                    default -> {}
                }
            }
        }
	}

    @SuppressWarnings("unchecked")
    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return RhodesAi.makeBrain((Brain<EntityRhodes>) super.makeBrain(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<EntityRhodes> getBrain() {
        return (Brain<EntityRhodes>) super.getBrain();
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return RhodesAi.brainProvider();
    }

    public void shootAllWeapons() {
        float syaw = -Mth.sin(bodyyaw * Mth.DEG_TO_RAD);
        float cyaw = Mth.cos(bodyyaw * Mth.DEG_TO_RAD);

        LivingEntity target = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        Brain<EntityRhodes> brain = this.getBrain();
        boolean hasTarget = brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
        if (getRocketCount() >= 0 && !brain.hasMemoryValue(MemoryModuleTypes.ROCKET_BLOCK_TARGET) && !hasTarget) {
            float px = (float) getX() + cyaw * 6.4f * getScale();
            float py = (float) getY() + 6.26759f * getScale();
            float pz = (float) getZ() + syaw * 6.4f * getScale();
            BlockEntity te = null;
            float priority1 = 0;
            for (LivingEntity e1 : level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, new AABB(px - 100 * getScale(), py - 100 * getScale(), pz - 100 * getScale(), px + 100 * getScale(), py + 100 * getScale(), pz + 100 * getScale()))) {
                float dx = (float) e1.getX() - px;
                float dz = (float) e1.getZ() - pz;
                float dot = (cyaw * dx + syaw * dz);
                if (dot * Mth.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                    float dy = (float) e1.getY() - py;
                    float dist1 = dx * dx + dy * dy + dz * dz;
                    if (dist1 < 100 * 100 * getScale() * getScale()) {
                        float prio1 = getPriority(e1) - Mth.sqrt(dist1);
                        if (prio1 > priority1 && rayTraceBlocks(px, py, pz, (float) e1.getX(), (float) e1.getY() + e1.getBbHeight() / 2f, (float) e1.getZ()) == null) {
                            target = e1;
                            priority1 = prio1;
                        }
                    }
                }
            }
            for (BlockEntity e1 : BLOCK_ENTITIES.values()) {
                if (e1 instanceof TileEntityRhodesActivator || e1 instanceof TileEntityNukeCrate || e1 instanceof TileEntityReactor) {
                    float dx = (float) e1.getBlockPos().getX() - px;
                    float dz = (float) e1.getBlockPos().getZ() - pz;
                    float dot = (cyaw * dx + syaw * dz);
                    if (dot * Mth.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                        float dy = (float) e1.getBlockPos().getY() - py;
                        float dist1 = dx * dx + dy * dy + dz * dz;
                        if (dist1 < 100 * 100) {
                            float prio1 = 300 - Mth.sqrt(dist1);
                            if (prio1 > priority1 && rayTraceBlocks(px, py, pz, (float) e1.getBlockPos().getX(), (float) e1.getBlockPos().getY(), (float) e1.getBlockPos().getZ()) == null) {
                                te = e1;
                                target = null;
                                priority1 = prio1;
                            }
                        }
                    }
                }
            }
            brain.setMemoryWithExpiry(MemoryModuleTypes.ROCKET_BLOCK_TARGET, te, 60);
        }

        if (!hasTarget) {
            float px = (float) getX() - cyaw * 6.4f * getScale();
            float py = (float) getY() + 6.26759f * getScale();
            float pz = (float) getZ() - syaw * 6.4f * getScale();
            float priority1 = 0;
            for (LivingEntity e1 : level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, new AABB(px - 40 * getScale(), py - 40 * getScale(), pz - 40 * getScale(), px + 40 * getScale(), py + 40 * getScale(), pz + 40 * getScale()))) {
                float dx = (float) e1.getX() - px;
                float dz = (float) e1.getZ() - pz;
                float dot = (-cyaw * dx + -syaw * dz);
                if (dot * Mth.abs(dot) > -0.25 * (dx * dx + dz * dz)) {
                    float dy = (float) e1.getY() - py;
                    float dist1 = dx * dx + dy * dy + dz * dz;
                    if (dist1 < 40 * 40 * getScale() * getScale()) {
                        float prio1 = getPriority(e1) - Mth.sqrt(dist1);
                        if (prio1 > priority1 && rayTraceBlocks(px, py, pz, (float) e1.getX(), (float) e1.getY() + e1.getBbHeight(), (float) e1.getZ()) == null) {
                            target = e1;
                            priority1 = prio1;
                        }
                    }
                }
            }
        }
        if (!hasTarget) {
            float priority = 0;

            for (LivingEntity e : level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, new AABB(getX() - 70 * getScale(), getY() + 13 * getScale() - 70 * getScale(), getZ() - 70 * getScale(), getX() + 70 * getScale(), getY() + 13 * getScale() + 70 * getScale(), getZ() + 70 * getScale()))) {
                float x = (float) (e.getX() - getX());
                float z = (float) (e.getZ() - getZ());
                if (Mth.abs(cyaw * x + syaw * z) < 2) {
                    float y = (float) (e.getY() - 13 - getY());
                    float dist = x * x + y * y + z * z;
                    if (dist < 70 * 70 * getScale() * getScale()) {
                        if (y * Mth.abs(y) > -0.64f * dist) {
                            float prio = getPriority(e) - Mth.sqrt(dist);
                            if (prio > priority && rayTraceBlocks((float) getX(), (float) getY() + 13, (float) getZ(), (float) e.getX(), (float) e.getY() + e.getBbHeight() / 2f, (float) e.getZ()) == null) {
                                target = e;
                                priority = prio;
                            }
                        }
                    }
                }
            }
        }
        brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, target, 60);
        brain.setActiveActivityIfPossible(Activity.FIGHT);
    }

    public RivalRebelsTeam teamToRaid;
	public boolean raidedOmegaAlready = false;
	public boolean raidedSigmaAlready = false;
	private void doAITick(float syaw, float cyaw) {
		if (getHealth()*2 < RRConfig.SERVER.getRhodesHealth()) endangered = true;
		if (!b2spirit) {
            setDeltaMovement(0, getDeltaMovement().y(), 0);
		}
		if (rider != null) {
			if (b2spirit && !freeze && getB2Energy() == 0 && getScale() < 1.5f && getScale() > 0.5f) {
				freeze = true;
                setNukeCount(getNukeCount() - 1);
                setHealth(getHealth() - 1000);
				level().addFreshEntity(new EntityB2Spirit(this));
			}
			if (CommandRobot.rhodesHold) return;
			if (getEnergy() < MAX_ENERGY) setEnergy(getEnergy() + recharge);
			if (!RRConfig.SERVER.isInfiniteAmmo())
			{
				rocket &= getRocketCount() > 0;
				flame &= getFlameCount() > 0;
			}
			if (!RRConfig.SERVER.isInfiniteNukes())
			{
				bomb &= getNukeCount() > 0;
			}
            setForceField(isForceFieldEnabled() && getEnergy() > ecshield);
			laser &= getEnergy() > eclaser;
			jet &= (getEnergy()+getB2Energy()) > ecjet;
			b2spirit &= getB2Energy() > 0;

			if (isForceFieldEnabled())
			{
                setEnergy(getEnergy() - ecshield);
				if (tickCount%8==0) {
                    this.playSound(RRSounds.FORCE_FIELD, 10, 1);
                }
			}
			if (laser) setEnergy(getEnergy() - eclaser);
			if (jet || b2spirit)
			{
				if (getB2Energy() > 0) {
                    setB2Energy(getB2Energy() - ecjet);
					if (getB2Energy() <= 0) {
                        setB2Energy(0);
						level().explode(null, this.getX(), this.getY(), this.getZ(), 6.0F, Level.ExplosionInteraction.MOB);
						level().addFreshEntity(new EntityB2Frag(level(), this, 0));
						level().addFreshEntity(new EntityB2Frag(level(), this, 1));
					}
				}
				else
				{
                    setEnergy(getEnergy() - ecjet);
				}
				flying = 3;
			}
			else
			{
				flying = 0;
			}
			disableAllLasers();
			if (!stop)
			{
				float goal = ((((rider.getYHeadRot()+bodyyaw)%360)+360+360)%360)-180;
				bodyyaw += Math.max(Math.min(goal, 2), -2);
				if (flying > 0)
				{
					if (b2spirit)
					{
                        setDeltaMovement(getDeltaMovement().add(syaw * 0.1f, 0, cyaw * 0.1f));
						double speed = (float) this.getDeltaMovement().horizontalDistance();
						if (speed > 0.7) {
                            Vec3 vec3d = getDeltaMovement().scale(1 / speed).scale(0.7);
                            setDeltaMovement(vec3d.x(), getDeltaMovement().y(), vec3d.z());
						}
					}
					else
					{
                        setDeltaMovement(syaw * 0.5f, getDeltaMovement().y(), cyaw * 0.5f);
					}
					rightthighpitch = approach(rightthighpitch,-30);
					leftthighpitch  = approach(leftthighpitch, -30);
					rightshinpitch  = approach(rightshinpitch, 60);
					leftshinpitch   = approach(leftshinpitch,  60);
				}
				else
				{
					doWalkingAnimation(syaw, cyaw);
				}
			}
			else
			{
				flying = 0;
				rightthighpitch = approach(rightthighpitch,0);
				leftthighpitch  = approach(leftthighpitch, 0);
				rightshinpitch  = approach(rightshinpitch, 0);
				leftshinpitch   = approach(leftshinpitch,  0);
			}
			double startx = rider.getX();
			double starty = rider.getEyeY();
			double startz = rider.getZ();
			double range = 100;
			double endx = startx + range * (-Mth.sin(rider.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(rider.getXRot() * Mth.DEG_TO_RAD));
			double endy = starty + range * (-Mth.sin(rider.getXRot() * Mth.DEG_TO_RAD));
			double endz = startz + range * (Mth.cos(rider.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(rider.getXRot() * Mth.DEG_TO_RAD));

			Vec3 hit = rayTraceBlocks((float)startx, (float)starty, (float)startz, (float)endx, (float)endy, (float)endz);

			if (hit != null)
			{
				endx = hit.x;
				endy = hit.y;
				endz = hit.z;
			}
			/*if (b2spirit && tickssincenuke >= 40 && getNukeCount() > 0 && getHealth() > 2000)
 			{
				tickssincenuke = 0;
				setNukeCount(getNukeCount() - 1);
				setHealth(getHealth() - 1000);
				world.spawnEntity(new EntityB2Spirit(world, endx, endy, endz, getX(), getY(), getZ(), null, false, false));
 			}*/

			if (laser)
			{
				setOnLaserData(random.nextInt(2)+1);
				RivalRebelsSoundPlayer.playSound(this, 22, 1, 30f, 0f);
				float x = (float) (getX() - endx);
				float y = (float) (getY() + 13*getScale() - endy);
				float z = (float) (getZ() - endz);
				float pitch = (720f-atan2(Mth.sqrt(x*x+z*z) *(syaw*x+cyaw*z>0?-1f:1f), y)) %360-270;

				headpitch += Math.max(-20, Math.min(20, (pitch-headpitch)));

				if (Mth.abs(headpitch-pitch) < 10f && tickCount % 3 == 0)
				{
					range = 70*getScale();
					Vec3 start = new Vec3(getX(), getY()+13*getScale(), getZ());
					Vec3 end = new Vec3(0, 0, range);
					end = end.xRot(-headpitch * Mth.DEG_TO_RAD);
					end = end.yRot(bodyyaw * Mth.DEG_TO_RAD);
					end = end.add(getX(), getY()+13*getScale(), getZ());
                    List<LivingEntity> entities = level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, new AABB(start, end).inflate(5));
                    for (LivingEntity e : entities) {
                        if (e != rider) {
                            Vec3 entity = new Vec3(e.getX(), e.getY(), e.getZ());
                            double bbx = 1;
                            if (e instanceof EntityRhodes) bbx = 20 * e.getScale();
                            if (entity.subtract(start).cross(entity.subtract(end)).distanceToSqr(0, 0, 0) < 10000 * bbx) {
                                e.hurt(RivalRebelsDamageSource.laserBurst(level()), 24);
                                if (e instanceof Player player) {
                                    ItemUtil.damageRandomArmor(player, 24, random);
                                    if (player.getHealth() < 3 && player.isAlive()) {
                                        player.hurt(RivalRebelsDamageSource.laserBurst(level()), 2000000);
                                        player.deathTime = 0;
                                        level().addFreshEntity(new EntityGore(level(), e, 0, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 1, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 2, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 2, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 3, 0));
                                        level().addFreshEntity(new EntityGore(level(), e, 3, 0));
                                    }
                                } else {
                                    if (!e.isAlive() || e.getHealth() < 3) {
                                        int legs;
                                        int arms;
                                        int mobs;
                                        level().playLocalSound(this, RRSounds.BLASTER_FIRE, getSoundSource(), 1, 4);
                                        if (e instanceof ZombifiedPiglin) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 2;
                                        } else if (e instanceof Zombie) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 1;
                                        } else if (e instanceof Skeleton) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 3;
                                        } else if (e instanceof EnderMan) {
                                            legs = 2;
                                            arms = 2;
                                            mobs = 4;
                                        } else if (e instanceof Creeper) {
                                            legs = 4;
                                            arms = 0;
                                            mobs = 5;
                                        } else if (e instanceof MagmaCube) {
                                            legs = 0;
                                            arms = 0;
                                            mobs = 7;
                                        } else if (e instanceof Slime) {
                                            legs = 0;
                                            arms = 0;
                                            mobs = 6;
                                        } else if (e instanceof CaveSpider) {
                                            legs = 8;
                                            arms = 0;
                                            mobs = 9;
                                        } else if (e instanceof Spider) {
                                            legs = 8;
                                            arms = 0;
                                            mobs = 8;
                                        } else if (e instanceof Ghast) {
                                            legs = 9;
                                            arms = 0;
                                            mobs = 10;
                                        } else if (e instanceof EntityRhodes) {
                                            return;
                                        } else {
                                            legs = (int) (e.getBoundingBox().getSize() * 2);
                                            arms = (int) (e.getBoundingBox().getSize() * 2);
                                            mobs = 11;
                                        }
                                        level().addFreshEntity(new EntityGore(level(), e, 0, mobs));
                                        level().addFreshEntity(new EntityGore(level(), e, 1, mobs));
                                        for (int i = 0; i < arms; i++)
                                            level().addFreshEntity(new EntityGore(level(), e, 2, mobs));
                                        for (int i = 0; i < legs; i++)
                                            level().addFreshEntity(new EntityGore(level(), e, 3, mobs));
                                        e.kill();
                                    }
                                }
                            }
                        }
                    }
				}
			}
			else
			{
				headpitch = rider.getXRot();
			}

			if (flame || prevflame)
			{
				float px = (float)getX()-cyaw*6.4f*getScale();
				float py = (float)getY()+6.26759f*getScale();
				float pz = (float)getZ()+syaw*6.4f*getScale();
				float x = px - (float)endx;
				float y = py - (float)endy;
				float z = pz - (float)endz;
				float yaw = ((atan2(x, z)-bodyyaw+810)%360)-270;
				float pitch = -(atan2(Mth.sqrt(x*x+z*z), y));
				rightarmyaw += Math.max(-3, Math.min(3, (yaw-rightarmyaw)));
				rightarmpitch += Math.max(-3, Math.min(3, (pitch-rightarmpitch)));

				if (isPlasma())
				{
					plasmacharge++;
					if (Mth.abs(rightarmyaw-yaw) < 3f && Mth.abs(rightarmpitch-pitch) < 3f)
					{
						if (!flame && prevflame)
						{
							float f = plasmacharge / 20.0F;
							f = (f * f + f * 2) * 0.3333f;
							if (f > 1.0F) f = 1.0F;
							f+=0.7f;
							RivalRebelsSoundPlayer.playSound(this, 16, 2, 1, 0.5f);
							float cp = -f/ Mth.sqrt(x*x+y*y+z*z) *getScale();
							x*=cp;
							y*=cp;
							z*=cp;
							level().addFreshEntity(new EntityPlasmoid(level(), px, py, pz,
									x, y, z, 8));
                            setFlameCount(getFlameCount() - plasmacharge);
							plasmacharge = 0;
						}
					}
					if (flame && !prevflame)
					{
						RivalRebelsSoundPlayer.playSound(this, 16, 1, 0.0f);
					}
				}
				else
				{
					if (Mth.abs(rightarmyaw-yaw) < 3f && Mth.abs(rightarmpitch-pitch) < 3f)
					{
                        setFlameCount(getFlameCount() - 1);
                        this.playSound(RRSounds.FLAME_THROWER_EXTINGUISH);
						float cp = -1f/ Mth.sqrt(x*x+y*y+z*z);
						x*=cp;
						y*=cp;
						z*=cp;
						level().addFreshEntity(new EntityFlameBall(level(), px, py, pz,
								x, y, z, (8+random.nextDouble()*8)*getScale(), 0.4f));
						level().addFreshEntity(new EntityFlameBall(level(), px, py, pz,
								x, y, z, (8+random.nextDouble()*8)*getScale(), 0.4f));
					}
				}
			}
			tickssincenuke++;
			if (rocket || bomb)
			{
				float px = (float)getX()+cyaw*6.4f*getScale();
				float py = (float)getY()+6.26759f*getScale();
				float pz = (float)getZ()-syaw*6.4f*getScale();
				float x = px - (float)endx;
				float y = py - (float)endy;
				float z = pz - (float)endz;
				float yaw = ((atan2(x, z)-bodyyaw+630)%360)-90;
				float pitch = -(atan2(Mth.sqrt(x*x+z*z), y));
				leftarmyaw += Math.max(-3, Math.min(3, (yaw-leftarmyaw)));
				leftarmpitch += Math.max(-3, Math.min(3, (pitch-leftarmpitch)));

				if (Mth.abs(leftarmyaw-yaw) < 3f && Mth.abs(leftarmpitch-pitch) < 3f)
				{
					if (bomb && !rocket)
					{
						if (tickssincenuke >= 10)
						{
							tickssincenuke = 0;
                            setNukeCount(getNukeCount() - 1);
							RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
							float cp = -0.5f/ Mth.sqrt(x*x+y*y+z*z);
							if (getScale() >= 3.0) {
                                EntityHotPotato entity = new EntityHotPotato(level(), px, py, pz);
                                entity.setPos(px, py, pz);
                                entity.setDeltaMovement(x * cp * 5.0f, y * cp * 5.0f, z * cp * 5.0f);
                                level().addFreshEntity(entity);
                            } else if (getScale() >= 2.0)
								level().addFreshEntity(new EntityTsar(level(), px, py, pz,
										x*cp*5.0f, y*cp*5.0f, z*cp*5.0f));
							else
								level().addFreshEntity(new EntityB83NoShroom(level(), px, py, pz,
										x*cp, y*cp, z*cp));
						}
					}

					if (rocket && tickCount-lastshot > ((getScale() >= 2.0)?30:((shotstaken == 21)?80:5)))
					{
                        setRocketCount(getRocketCount() - 1);
						lastshot = tickCount;
						if (shotstaken == 21) shotstaken = 0;
						shotstaken++;
						RivalRebelsSoundPlayer.playSound(this, 23, 10, 1f);
						float cp = -0.5f/ Mth.sqrt(x*x+y*y+z*z);

						if (getScale() >= 2.0)
							level().addFreshEntity(new EntityB83NoShroom(level(), px, py, pz,
									x*cp, y*cp, z*cp));
						else
							level().addFreshEntity(new EntitySeekB83(level(), px, py, pz,
									x*cp, y*cp, z*cp));
					}
				}
			}
            getBrain().useDefaultActivity();
			return;
		}

		if (CommandRobot.rhodesHold || guard)
		{
			rightthighpitch = approach(rightthighpitch,0);
			leftthighpitch  = approach(leftthighpitch, 0);
			rightshinpitch  = approach(rightshinpitch, 0);
			leftshinpitch   = approach(leftshinpitch,  0);
			if (RRConfig.SERVER.isRhodesAIEnabled()) {
				shootAllWeapons();
			}
			return;
		}

		if (!RRConfig.SERVER.isRhodesAIEnabled() && getBrain().getActiveNonCoreActivity().isPresent()) return;
    }

    public static float atan2(float y, float x) {
		float dx = Mth.sqrt(x*x+y*y) +x;
		if (y > dx) {
			float r = dx/y;
			return 180-(110.8653352702f-20.8654f*r*r)*r;
		} else if (y > -dx) {
			float r = y/dx;
			return (110.8653352702f-20.8654f*r*r)*r;
		} else {
			float r = dx/y;
			return -180-(110.8653352702f-20.8654f*r*r)*r;
		}
	}

	public Entity findTarget() {
		Entity target = null;
		double priority = 0;
        List<LivingEntity> otherEntities = level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, AABB.of(BoundingBox.infinite()));
        for (LivingEntity e : otherEntities) {
            if (e.canBeSeenAsEnemy() && (!(e instanceof EntityRhodes) || RRConfig.SERVER.isFriendlyFireRhodesEnabled() && (RRConfig.SERVER.isTeamFriendlyFireRhodesEnabled() || !((EntityRhodes) e).getVariant().is(getVariant())))) {
                double prio = getPriority(e) - distanceTo(e);
                if (prio > priority) {
                    target = e;
                    priority = prio;
                }
            }
        }
		return target;
	}

    public void doWalkingAnimation() {
        float syaw = Mth.sin(bodyyaw * Mth.DEG_TO_RAD);
        float cyaw = Mth.cos(bodyyaw * Mth.DEG_TO_RAD);
        doWalkingAnimation(syaw, cyaw);
    }

	private int walkstate = 0;
	public void doWalkingAnimation(float syaw, float cyaw) {
        float movescale = getScale();
		if (RRConfig.SERVER.isRhodesScaleSpeed()) movescale *= RRConfig.SERVER.getRhodesSpeedScale();
		else movescale = RRConfig.SERVER.getRhodesSpeedScale();
		setDeltaMovement(syaw * 0.125f*movescale,
            getDeltaMovement().y(),
            cyaw * 0.125f*movescale);
        switch (walkstate) {
            case 0 -> {
                int rtg = -60;
                int ltg = 0;
                int rsg = 60;
                int lsg = 30;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 1 -> {
                int rtg = -60;
                int ltg = 0;
                int rsg = 60;
                int lsg = 60;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) {
                    walkstate++;
                    level().playLocalSound(this, RRSounds.ARTILLERY_DESTROY, getSoundSource(), 4.5f, 0.8f);
                }
            }
            case 2 -> {
                int rtg = -30;
                int ltg = 0;
                int rsg = 30;
                int lsg = 90;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 3 -> {
                int rtg = 0;
                int ltg = -30;
                int rsg = 0;
                int lsg = 90;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 4 -> {
                int rtg = 0;
                int ltg = -60;
                int rsg = 30;
                int lsg = 60;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 5 -> {
                int rtg = 0;
                int ltg = -60;
                int rsg = 60;
                int lsg = 60;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) {
                    walkstate++;
                    level().playLocalSound(this, RRSounds.ARTILLERY_DESTROY, getSoundSource(), 4.5f, 0.8f);
                }
            }
            case 6 -> {
                int rtg = 0;
                int ltg = -30;
                int rsg = 90;
                int lsg = 30;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 7 -> {
                int rtg = -30;
                int ltg = 0;
                int rsg = 90;
                int lsg = 0;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate++;
            }
            case 8 -> {
                int rtg = -60;
                int ltg = 0;
                int rsg = 60;
                int lsg = 30;
                rightthighpitch = approach(rightthighpitch, rtg);
                leftthighpitch = approach(leftthighpitch, ltg);
                rightshinpitch = approach(rightshinpitch, rsg);
                leftshinpitch = approach(leftshinpitch, lsg);
                if ((int) (rightthighpitch) == rtg
                    && (int) (leftthighpitch) == ltg
                    && (int) (rightshinpitch) == rsg
                    && (int) (leftshinpitch) == lsg) walkstate = 0;
            }
        }
	}

	public static float approach(float f, float t) {
		float r = Mth.clamp(f-t, -1, 1);
		return f-r;
	}

	private int lastshot;
	private int shotstaken;

    public static final Map<BlockPos, BlockEntity> BLOCK_ENTITIES = new HashMap<>();

    //lower is less prior
    public float getPriority(Entity e) {
		if (e instanceof Player) return e.isInvulnerable()?-100:600;
		if (e instanceof LivingEntity) return ((LivingEntity)e).getMaxHealth()+100;
		if ((e instanceof EntityRhodes && (RRConfig.SERVER.isFriendlyFireRhodesEnabled() && (RRConfig.SERVER.isTeamFriendlyFireRhodesEnabled() || !((EntityRhodes)e).getVariant().is(getVariant())))) || e instanceof EntityB2Spirit) return 800;
		if (e.getBoundingBox().getSize() > 3) return (float) (e.getBoundingBox().getSize()*3 + 500 + e.getBbHeight());
		return 0;
	}

	public Vec3 rayTraceBlocks(float sx, float sy, float sz, float ex, float ey, float ez) {
		int X = (int) sx;
		int Y = (int) sy;
		int Z = (int) sz;
		float dx = ex-sx;
		float dy = ey-sy;
		float dz = ez-sz;
		float distsq = (dx*dx+dy*dy+dz*dz);
		int stepX = dx>0?1:-1;
		int stepY = dy>0?1:-1;
		int stepZ = dz>0?1:-1;
		int x = X-stepX;
		int y = Y-stepY;
		int z = Z-stepZ;
		float tDeltaX = stepX/dx;
		float tDeltaY = stepY/dy;
		float tDeltaZ = stepZ/dz;
		float tMaxX = ((1+X)-sx)*tDeltaX;
		float tMaxY = ((1+Y)-sy)*tDeltaY;
		float tMaxZ = ((1+Z)-sz)*tDeltaZ;
        while ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) < distsq) {
            if (level().getBlockState(new BlockPos(X, Y, Z)).canOcclude()) {
                return new Vec3(X, Y, Z);
            }
            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    X += stepX;
                    tMaxX += tDeltaX;
                } else {
                    Z += stepZ;
                    tMaxZ += tDeltaZ;
                }
            } else {
                if (tMaxY < tMaxZ) {
                    Y += stepY;
                    tMaxY += tDeltaY;
                } else {
                    Z += stepZ;
                    tMaxZ += tDeltaZ;
                }
            }
        }
		return null;
	}

    public float getbodyyaw(float f) {
        return lastbodyyaw + (bodyyaw - lastbodyyaw) * f;
    }

    public float getheadpitch(float f) {
        return lastheadpitch + (headpitch - lastheadpitch) * f;
    }

    public float getleftarmyaw(float f) {
        return lastleftarmyaw + (leftarmyaw - lastleftarmyaw) * f;
    }

    public float getleftarmpitch(float f) {
        return lastleftarmpitch + (leftarmpitch - lastleftarmpitch) * f;
    }

    public float getrightarmyaw(float f) {
        return lastrightarmyaw + (rightarmyaw - lastrightarmyaw) * f;
    }

    public float getrightarmpitch(float f) {
        return lastrightarmpitch + (rightarmpitch - lastrightarmpitch) * f;
    }

    public float getleftthighpitch(float f) {
        return lastleftthighpitch + (leftthighpitch - lastleftthighpitch) * f;
    }

    public float getrightthighpitch(float f) {
        return lastrightthighpitch + (rightthighpitch - lastrightthighpitch) * f;
    }

    public float getleftshinpitch(float f) {
        return lastleftshinpitch + (leftshinpitch - lastleftshinpitch) * f;
    }

    public float getrightshinpitch(float f) {
        return lastrightshinpitch + (rightshinpitch - lastrightshinpitch) * f;
    }

	public Component getName() {
        return getVariant().value().getName();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
		nbt.putFloat("bodyyaw", bodyyaw);
		nbt.putFloat("headyaw", getYHeadRot());
		nbt.putFloat("headpitch", headpitch);
		nbt.putFloat("leftarmyaw", leftarmyaw);
		nbt.putFloat("leftarmpitch", leftarmpitch);
		nbt.putFloat("rightarmyaw", rightarmyaw);
		nbt.putFloat("rightarmpitch", rightarmpitch);
		nbt.putFloat("leftthighpitch", leftthighpitch);
		nbt.putFloat("rightthighpitch", rightthighpitch);
		nbt.putFloat("leftshinpitch", leftshinpitch);
		nbt.putFloat("rightshinpitch", rightshinpitch);
		nbt.putBoolean("endangered", endangered);
		nbt.putInt("walkstate", walkstate);
		nbt.putInt("damageuntilwake", damageUntilWake);
		nbt.putString("type", getVariant().getRegisteredName());
		nbt.putInt("rocketcount", getRocketCount());
		nbt.putInt("energy", getEnergy());
		nbt.putInt("b2energy", getB2Energy());
		nbt.putInt("flamecount", getFlameCount());
		nbt.putInt("nukecount", getNukeCount());
        nbt.putString("texloc", getFlagTextureLocation());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
		bodyyaw = nbt.getFloat("bodyyaw");
        setYHeadRot(nbt.getFloat("headyaw"));
		headpitch = nbt.getFloat("headpitch");
		leftarmyaw = nbt.getFloat("leftarmyaw");
		leftarmpitch = nbt.getFloat("leftarmpitch");
		rightarmyaw = nbt.getFloat("rightarmyaw");
		rightarmpitch = nbt.getFloat("rightarmpitch");
		leftthighpitch = nbt.getFloat("leftthighpitch");
		rightthighpitch = nbt.getFloat("rightthighpitch");
		leftshinpitch = nbt.getFloat("leftshinpitch");
		rightshinpitch = nbt.getFloat("rightshinpitch");
		endangered = nbt.getBoolean("endangered");
		walkstate = nbt.getInt("walkstate");
		damageUntilWake = nbt.getInt("damageuntilwake");
        RivalRebels.RHODES_TYPE_REGISTRY.getHolder(ResourceLocation.tryParse(nbt.getString("type"))).ifPresent(this::setVariant);
		setRocketCount(nbt.getInt("rocketcount"));
		setEnergy(nbt.getInt("energy"));
		setB2Energy(nbt.getInt("b2energy"));
		setFlameCount(nbt.getInt("flamecount"));
		setNukeCount(nbt.getInt("nukecount"));
        setFlagTextureLocation(nbt.getString("texloc"));
	}

    @Override
    public boolean isInvulnerable() {
        return super.isInvulnerable() || isForceFieldEnabled();
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        super.actuallyHurt(damageSource, damageAmount);
        if (damageAmount > 50) {
            setHealth(getHealth() - 50);
            if (rider == null) damageUntilWake -= 50;
            endangered = true;
        } else {
            setHealth(getHealth() - damageAmount);
            if (rider == null) damageUntilWake -= damageAmount;
        }

        if (isDeadOrDying()) {
            setHealth(0);
            playSound(RRSounds.ARTILLERY_EXPLODE, 30, 1);
        }

        if (damageUntilWake <= 0) {
            this.getBrain().setMemory(MemoryModuleTypes.RHODES_AWAKEN, Unit.INSTANCE);
        }
        this.getBrain().setMemory(MemoryModuleType.HURT_BY, damageSource);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return List.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FIRE, false);
        builder.define(PLASMA, false);
        builder.define(ENERGY, MAX_ENERGY);
        builder.define(NUKE_COUNT, RRConfig.SERVER.getRhodesNukes());
        builder.define(B2_ENERGY, 0);
        builder.define(ROCKET_COUNT, 5000);
        builder.define(FLAME_COUNT, 10000);
        builder.define(FORCE_FIELD, false);
        builder.define(FLAG_TEXTURE_LOCATION, "");
        builder.define(TYPE, RivalRebels.RHODES_TYPE_REGISTRY.wrapAsHolder(RhodesTypes.Rhodes));
        builder.define(ON_LASERS, 0);
    }

    @Override
    public Holder<RhodesType> getVariant() {
        return entityData.get(TYPE);
    }

    @Override
    public void setVariant(Holder<RhodesType> variant) {
        entityData.set(TYPE, variant);
    }

    public String getFlagTextureLocation() {
        return entityData.get(FLAG_TEXTURE_LOCATION);
    }

    public void setFlagTextureLocation(String textureLocation) {
        entityData.set(FLAG_TEXTURE_LOCATION, textureLocation);
    }

    public boolean isForceFieldEnabled() {
        return entityData.get(FORCE_FIELD);
    }

    public void setForceField(boolean forceField) {
        entityData.set(FORCE_FIELD, forceField);
    }

    public int getFlameCount() {
        return this.entityData.get(FLAME_COUNT);
    }

    public void setFlameCount(int flameCount) {
        this.entityData.set(FLAME_COUNT, flameCount);
    }

    public int getRocketCount() {
        return entityData.get(ROCKET_COUNT);
    }

    public void setRocketCount(int rocketCount) {
        entityData.set(ROCKET_COUNT, rocketCount);
    }

    public int getB2Energy() {
        return entityData.get(B2_ENERGY);
    }

    public void setB2Energy(int b2energy) {
        entityData.set(B2_ENERGY, b2energy);
    }

    public int getEnergy() {
        return entityData.get(ENERGY);
    }

    public void setEnergy(int energy) {
        entityData.set(ENERGY, energy);
    }

    public int getNukeCount() {
        return entityData.get(NUKE_COUNT);
    }

    public void setNukeCount(int nukeCount) {
        entityData.set(NUKE_COUNT, nukeCount);
    }

    public boolean isFire() {
        return entityData.get(FIRE);
    }

    public void setFire(boolean fire) {
        entityData.set(FIRE, fire);
    }

    public boolean isPlasma() {
        return entityData.get(PLASMA);
    }

    public void setPlasma(boolean plasma) {
        entityData.set(PLASMA, plasma);
    }

    private void setBlock(int x, int y, int z, Block block) {
        level().setBlockAndUpdate(new BlockPos(x, y, z), block.defaultBlockState());
    }

    private Block getBlock(int x, int y, int z) {
        return level().getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    private BlockState getBlockState(int x, int y, int z) {
        return level().getBlockState(new BlockPos(x, y, z));
    }

    public void enableTopLaser() {
        entityData.set(ON_LASERS, entityData.get(ON_LASERS) | 1);
    }

    public void enableBottomLaser() {
        entityData.set(ON_LASERS, entityData.get(ON_LASERS) | 2);
    }

    public void enableAllLasers() {
        entityData.set(ON_LASERS, 3);
    }

    public void disableAllLasers() {
        entityData.set(ON_LASERS, 0);
    }

    public void setOnLaserData(int data) {
        entityData.set(ON_LASERS, data);
    }

    public boolean isTopLaserEnabled() {
        return (entityData.get(ON_LASERS) & 1) != 0;
    }

    public boolean isBottomLaserEnabled() {
        return (entityData.get(ON_LASERS) & 2) != 0;
    }

    public boolean isAnyLaserEnabled() {
        return isTopLaserEnabled() || isBottomLaserEnabled();
    }

    public boolean isAllLaserEnabled() {
        return isTopLaserEnabled() && isBottomLaserEnabled();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 100)
            .add(Attributes.SCALE, 30); // Dummy data
    }
}

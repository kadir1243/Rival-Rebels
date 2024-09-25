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
package assets.rivalrebels.common.item.weapon;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.entity.*;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsRank;
import assets.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class ItemRoda extends Item
{
	public static String[] entities = new String[]{
			"fire1",//0
			"fire2",
			"fire3",
			"plasma",
			"gas",
			"tesla",//5
			"cuchillo",
			"rocket",
			"einsten",
			"flesh",
			"bomb",//10
			"creeper",
			"snowman",
			"roddisk",
			"seeker",
			"pigman",//15
			"zombie",
			"tnt",
			"irongolem",
			"paratrooper",
			"oreblocks",//20
			"supplies",
			"blocks",
			"roda",
			"b83",
			"hackb83",//25
			"enchantedb83",
			"nuke",
			"tsar",
			"theoreticaltsar",
			"fatnuke",
			"antimatter",
			"tachyon"
		};
	static float[] randoms = new float[]{
			0.1f,
			0.1f,
			0.1f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.2f,
			0.9f,
			0.1f,
			0.0f,
			0.1f,
			0.1f,
			0.1f,
			0.0f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f,
			0.1f
		};
	static float[] speeds = new float[]{
			1.6f,
			1.5f,
			1.2f,
			2.0f,
			1.3f,
			100.0f,
			1.3f,
			1.0f,
			3.0f,
			0.5f,
			4.3f,
			0.0f,
			1.5f,
			1.5f,
			1.7f,
			1.0f,
			1.5f,
			1.5f,
			1.5f,
			1.5f,
			1.5f,
			1.5f,
			1.5f,
			1.5f,
			3.0f,
			3.0f,
			3.0f,
			3.0f,
			3.0f,
			3.0f,
			3.0f,
			3.0f,
			3.0f,
			3.0f
		};
	public static int[] rates = new int[]{
			1,//0
			1,
			1,
			8,
			8,
			1,//5
			6,
			8,
			1,
			2,
			6,//10
			10,
			10,
			10,
			10,
			10,//15
			10,
			2,
			10,
			10,
			1,//20
			1,
			1,
			1,
			20,
			20,//25
			20,
			20,
			20,
			20,
			20,
			1,
			1
		};
	public static int rodaindex = 23;

	public static void spawn(int index, Level world, double x, double y, double z, double mx, double my, double mz, double speed, double random) {
        if (world.isClientSide()) return;
        if ("roda".equals(entities[index])) {
			int newindex = world.random.nextInt(index);
			spawn(newindex, world, x,y,z,mx,my,mz,speed,random);
			return;
		}
		speed *= speeds[index];
		random += randoms[index];
		double rx = world.random.nextGaussian() * random;
		double ry = world.random.nextGaussian() * random;
		double rz = world.random.nextGaussian() * random;
        Vec3 velocity = new Vec3(mx, my, mz).scale(speed).add(rx, ry, rz);
        mx = velocity.x();
        my = velocity.y();
        mz = velocity.z();
		Entity e = switch (index) {
            case 0 -> new EntityFlameBall(world);
            case 1 -> new EntityFlameBall1(world);
            case 2 -> new EntityFlameBall2(world);
            case 3 -> new EntityPlasmoid(world, x, y, z, mx, my, mz, 1.0f);
            case 4 -> new EntityGasGrenade(world, mx, my, mz);
            case 5 -> new EntityRaytrace(world, mx, my, mz);
            case 6 -> new EntityCuchillo(world, mx, my, mz);
            case 7 -> new EntityRocket(world, mx, my, mz);
            case 8 -> new EntityLaserBurst(world, mx, my, mz);
            case 9 -> new EntityGore(world, mx, my, mz, world.random.nextInt(3), world.random.nextInt(11) + 1);
            case 10 -> new EntityBomb(world, x, y, z, mx, my, mz);
            case 11 -> EntityType.CREEPER.create(world);
            case 12 -> EntityType.SNOW_GOLEM.create(world);
            case 13 -> new EntityRoddiskRebel(world);
            case 14 -> new EntitySeekB83(world);
            case 15 -> EntityType.ZOMBIFIED_PIGLIN.create(world);
            case 16 -> EntityType.ZOMBIE.create(world);
            case 17 -> new PrimedTnt(world, x, y, z, null);
            case 18 -> EntityType.IRON_GOLEM.create(world);
            case 19 -> {
                Entity zomb = EntityType.ZOMBIFIED_PIGLIN.create(world);
                zomb.setPos(x, y, z);
                zomb.setDeltaMovement(velocity);
                world.addFreshEntity(zomb);
                Chicken chicken = EntityType.CHICKEN.create(world);
                zomb.startRiding(chicken);
                yield chicken;
            }
            case 20 -> new EntityDebris(world, world.registryAccess().registryOrThrow(Registries.BLOCK).getRandomElementOf(ModBlockTags.ORES, world.getRandom()).map(Holder::value).orElse(Blocks.AIR));
            case 21 -> {
                Block[] blocks2 = new Block[]{RRBlocks.ammunition, RRBlocks.supplies, RRBlocks.weapons, RRBlocks.explosives, RRBlocks.omegaarmor, RRBlocks.sigmaarmor};
                Block b2 = blocks2[world.random.nextInt(blocks2.length)];
                yield new EntityDebris(world, b2);
            }
            case 22 -> {
                Block[] blocks3 = new Block[]{Blocks.SAND, Blocks.GRAVEL, Blocks.COBBLESTONE, Blocks.DIRT};
                Block b3 = blocks3[world.random.nextInt(blocks3.length)];
                yield new EntityDebris(world, b3);
            }
            case 24 -> new EntityB83(world, mx, my, mz);
            case 25 -> new EntityHackB83(world, mx, my, mz, false);
            case 26 -> new EntityHackB83(world, mx, my, mz, true);
            case 27 -> new EntityNuke(world, mx, my, mz);
            case 28 -> new EntityTsar(world, mx, my, mz, 1);
            case 29 -> new EntityTheoreticalTsar(world, mx, my, mz, 1);
            case 30 -> new EntityHotPotato(world, x, y, z);
            case 31 -> new EntityAntimatterBomb(world, mx, my, mz, 1);
            case 32 -> new EntityTachyonBomb(world, mx, my, mz, 1);
            default -> null;
        };
        if (e != null) {
            e.setPos(x, y, z);
            e.setDeltaMovement(velocity);
			world.addFreshEntity(e);
		}
	}

	boolean pass = false;
	public ItemRoda() {
		super(new Properties().stacksTo(1).component(RRComponents.HAPPY_NEW_YEAR, 0));
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!pass) {
			player.displayClientMessage(Component.nullToEmpty("Password?"), true);
			pass = true;
		}
		RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());
		if ((!world.isClientSide && world.getServer().isSingleplayer())
		 || (rrp != null && (rrp.rrrank == RivalRebelsRank.LEADER || rrp.rrrank == RivalRebelsRank.OFFICER || rrp.rrrank == RivalRebelsRank.REP))) {
			player.startUsingItem(hand);
			stack.set(RRComponents.HAPPY_NEW_YEAR, stack.get(RRComponents.HAPPY_NEW_YEAR)+10);
			if (stack.get(RRComponents.HAPPY_NEW_YEAR) > 1400 && !world.isClientSide()) //EXPLODE
			{
				world.addFreshEntity(new EntityNuclearBlast(world, player.getX(), player.getY(), player.getZ(), 6, true));
				player.setItemInHand(hand, ItemStack.EMPTY);
				return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
			}
			double motionX = (-Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD));
			double motionZ = (Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(player.getXRot() * Mth.DEG_TO_RAD));
			double motionY = (-Mth.sin(player.getXRot() * Mth.DEG_TO_RAD));
			spawn(rodaindex, world, player.getX(), player.getY() + 3.0, player.getZ(),motionX,motionY,motionZ, 1.0,0.0);
            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        }
		return InteractionResultHolder.pass(stack);
	}

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide()) return;
		if (stack.get(RRComponents.HAPPY_NEW_YEAR)>0)stack.set(RRComponents.HAPPY_NEW_YEAR, stack.get(RRComponents.HAPPY_NEW_YEAR)-1);
	}

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker.level().isClientSide()) return true;
		RandomSource r = attacker.getRandom();
        Vec3 vector = target.position().subtract(attacker.position());

        switch (r.nextInt(4)) {
            case 0 -> {
                vector = vector.normalize().reverse();

                target.setDeltaMovement(
                    vector.x * 3 + (r.nextFloat() - 0.5f) * 0.1,
                    vector.y * 3 + (r.nextFloat() - 0.5f) * 0.1,
                    vector.z * 3 + (r.nextFloat() - 0.5f) * 0.1);
            }
            case 1 -> {
                vector = vector.normalize().reverse();

                target.setDeltaMovement(
                    vector.x * 2 + (r.nextFloat() - 0.5f) * 0.1,
                    vector.y * 2 + (r.nextFloat() - 0.5f) * 0.1,
                    vector.z * 2 + (r.nextFloat() - 0.5f) * 0.1);
            }
            default -> {}
        }
		return true;
	}
}

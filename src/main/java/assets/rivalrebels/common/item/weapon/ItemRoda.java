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
import assets.rivalrebels.common.explosion.NuclearExplosion;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsRank;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
		if ("roda".equals(entities[index]))
		{
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
		Entity e = null;
		switch(index)
		{
		case 0:
			e = new EntityFlameBall(world, x,y,z,mx,my,mz);
		break;
		case 1:
			e = new EntityFlameBall1(world, x,y,z,mx,my,mz);
		break;
		case 2:
			e = new EntityFlameBall2(world, x,y,z,mx,my,mz);
		break;
		case 3:
			e = new EntityPlasmoid(world, x,y,z,mx,my,mz,1.0f);
		break;
		case 4:
			e = new EntityGasGrenade(world, x,y,z,mx,my,mz);
		break;
		case 5:
			e = new EntityRaytrace(world, x,y,z,mx,my,mz);
		break;
		case 6:
			e = new EntityCuchillo(world, x,y,z,mx,my,mz);
		break;
		case 7:
			e = new EntityRocket(world, x,y,z,mx,my,mz);
		break;
		case 8:
			e = new EntityLaserBurst(world, x,y,z,mx,my,mz);
		break;
		case 9:
			e = new EntityGore(world, x,y,z,mx,my,mz,world.random.nextInt(3), world.random.nextInt(11)+1);
		break;
		case 10:
			e = new EntityBomb(world, x,y,z,mx,my,mz);
		break;
		case 11:
			e = EntityType.CREEPER.create(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
		break;
		case 12:
			e = EntityType.SNOW_GOLEM.create(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
		break;
		case 13:
			e = new EntityRoddiskRebel(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
		break;
		case 14:
			e = new EntitySeekB83(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
		break;
		case 15:
			e = EntityType.ZOMBIFIED_PIGLIN.create(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
		break;
		case 16:
			e = EntityType.ZOMBIE.create(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
		break;
		case 17:
			e = new PrimedTnt(world, x, y, z, null);
            e.setDeltaMovement(velocity);
		break;
		case 18:
			e = EntityType.IRON_GOLEM.create(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
		break;
		case 19:
			Entity zomb = EntityType.ZOMBIFIED_PIGLIN.create(world);
			zomb.setPos(x,y,z);
            zomb.setDeltaMovement(velocity);
			world.addFreshEntity(zomb);
			e = EntityType.CHICKEN.create(world);
			e.setPos(x,y,z);
            e.setDeltaMovement(velocity);
			zomb.startRiding(e);
		break;
		case 20:
			Block[] blocks1 = NuclearExplosion.prblocks;
			Block b1 = blocks1[world.random.nextInt(blocks1.length)];
			e = new EntityDebris(world,x,y,z,mx,my,mz,b1);
		break;
		case 21:
			Block[] blocks2 = new Block[]{RRBlocks.ammunition, RRBlocks.supplies, RRBlocks.weapons, RRBlocks.explosives, RRBlocks.omegaarmor, RRBlocks.sigmaarmor};
			Block b2 = blocks2[world.random.nextInt(blocks2.length)];
			e = new EntityDebris(world,x,y,z,mx,my,mz,b2);
		break;
		case 22:
			Block[] blocks3 = new Block[]{Blocks.SAND,Blocks.GRAVEL,Blocks.COBBLESTONE,Blocks.DIRT};
			Block b3 = blocks3[world.random.nextInt(blocks3.length)];
			e = new EntityDebris(world,x,y,z,mx,my,mz,b3);
		break;
		case 23:
		break;
		case 24:
			e = new EntityB83(world, x,y,z,mx,my,mz);
		break;
		case 25:
			e = new EntityHackB83(world, x,y,z,mx,my,mz,false);
		break;
		case 26:
			e = new EntityHackB83(world, x,y,z,mx,my,mz,true);
		break;
		case 27:
			e = new EntityNuke(world, x,y,z,mx,my,mz);
		break;
		case 28:
			e = new EntityTsar(world, x,y,z,mx,my,mz,1);
		break;
		case 29:
			e = new EntityTheoreticalTsar(world, x,y,z,mx,my,mz,1);
		break;
		case 30:
			e = new EntityHotPotato(world, x,y,z,mx,my,mz);
		break;
		case 31:
			e = new EntityAntimatterBomb(world, x,y,z,mx,my,mz,1);
		break;
		case 32:
			e = new EntityTachyonBomb(world, x,y,z,mx,my,mz,1);
		break;
		}
		if (world.isClientSide()) return;
		if (e != null)
		{
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
			double motionX = (-Mth.sin(player.getYRot() / 180.0F * Mth.PI) * Mth.cos(player.getXRot() / 180.0F * Mth.PI));
			double motionZ = (Mth.cos(player.getYRot() / 180.0F * Mth.PI) * Mth.cos(player.getXRot() / 180.0F * Mth.PI));
			double motionY = (-Mth.sin(player.getXRot() / 180.0F * Mth.PI));
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
		RandomSource r = attacker.level().random;
		double x = target.getX() - attacker.getX();
		double y = target.getY() - attacker.getY();
		double z = target.getZ() - attacker.getZ();

		double dist = Math.sqrt(x * x + y * y + z * z);

		switch (r.nextInt(4))
		{
			case 0:
				x /= -dist;
				y /= -dist;
				z /= -dist;

				target.setDeltaMovement(
                    x * 3 + (r.nextFloat() - 0.5f) * 0.1,
                    y * 3 + (r.nextFloat() - 0.5f) * 0.1,
                    z * 3 + (r.nextFloat() - 0.5f) * 0.1);
			break;
			case 1:
				x /= dist;
				y /= dist;
				z /= dist;

				target.setDeltaMovement(
                    x * 2 + (r.nextFloat() - 0.5f) * 0.1,
                    y * 2 + (r.nextFloat() - 0.5f) * 0.1,
                    z * 2 + (r.nextFloat() - 0.5f) * 0.1);
			break;
		}
		return true;
	}
}

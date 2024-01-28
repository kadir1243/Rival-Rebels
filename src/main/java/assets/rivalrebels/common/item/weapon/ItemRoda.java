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
import assets.rivalrebels.client.itemrenders.RodaRenderer;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.entity.*;
import assets.rivalrebels.common.explosion.NuclearExplosion;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsRank;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.Random;
import java.util.function.Consumer;

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

	public static void spawn(int index, World world, double x, double y, double z, double mx, double my, double mz, double speed, double random) {
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
        Vec3d velocity = new Vec3d(mx, my, mz).multiply(speed).add(rx, ry, rz);
        mx = velocity.getX();
        my = velocity.getY();
        mz = velocity.getZ();
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
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
		break;
		case 12:
			e = EntityType.SNOW_GOLEM.create(world);
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
		break;
		case 13:
			e = new EntityRoddiskRebel(world);
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
		break;
		case 14:
			e = new EntitySeekB83(world);
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
		break;
		case 15:
			e = EntityType.ZOMBIFIED_PIGLIN.create(world);
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
		break;
		case 16:
			e = EntityType.ZOMBIE.create(world);
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
		break;
		case 17:
			e = new TntEntity(world, x, y, z, null);
            e.setVelocity(velocity);
		break;
		case 18:
			e = EntityType.IRON_GOLEM.create(world);
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
		break;
		case 19:
			Entity zomb = EntityType.ZOMBIFIED_PIGLIN.create(world);
			zomb.setPosition(x,y,z);
            zomb.setVelocity(velocity);
			world.spawnEntity(zomb);
			e = EntityType.CHICKEN.create(world);
			e.setPosition(x,y,z);
            e.setVelocity(velocity);
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
		if (world.isClient) return;
		if (e != null)
		{
			world.spawnEntity(e);
		}
	}

	boolean pass = false;
	public ItemRoda() {
		super(new Settings().maxCount(1).group(RRItems.rralltab));
	}
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new RodaRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!pass) {
			player.sendMessage(Text.of("Password?"), true);
			pass = true;
		}
		player.swingHand(hand);
		RivalRebelsPlayer rrp = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());
		if ((!world.isClient && world.getServer().isSingleplayer())
		 || (rrp != null && (rrp.rrrank == RivalRebelsRank.LEADER || rrp.rrrank == RivalRebelsRank.OFFICER || rrp.rrrank == RivalRebelsRank.REP))) {
			player.setCurrentHand(hand);
			stack.getNbt().putInt("happynewyear",stack.getNbt().getInt("happynewyear")+10);
			if (stack.getNbt().getInt("happynewyear") > 1400 && !world.isClient) //EXPLODE
			{
				world.spawnEntity(new EntityNuclearBlast(world, player.getX(), player.getY(), player.getZ(), 6, true));
				player.setStackInHand(hand, ItemStack.EMPTY);
				return TypedActionResult.success(stack, world.isClient);
			}
			double motionX = (-MathHelper.sin(player.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(player.getPitch() / 180.0F * (float) Math.PI));
			double motionZ = (MathHelper.cos(player.getYaw() / 180.0F * (float) Math.PI) * MathHelper.cos(player.getPitch() / 180.0F * (float) Math.PI));
			double motionY = (-MathHelper.sin(player.getPitch() / 180.0F * (float) Math.PI));
			spawn(rodaindex, world, player.getX(), player.getY() + 3.0, player.getZ(),motionX,motionY,motionZ, 1.0,0.0);
		}
		return TypedActionResult.pass(stack);
	}

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) return;
		if (stack.getOrCreateNbt().getInt("happynewyear")>0)stack.getNbt().putInt("happynewyear",stack.getNbt().getInt("happynewyear")-1);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
	{
		if (player.world.isClient) return true;
		Random r = player.world.random;
		double x = entity.getX() - player.getX();
		double y = entity.getY() - player.getY();
		double z = entity.getZ() - player.getZ();

		double dist = Math.sqrt(x * x + y * y + z * z);

		switch (r.nextInt(4))
		{
			case 0:
				x /= -dist;
				y /= -dist;
				z /= -dist;

				entity.setVelocity(
                    x * 3 + (r.nextFloat() - 0.5f) * 0.1,
                    y * 3 + (r.nextFloat() - 0.5f) * 0.1,
                    z * 3 + (r.nextFloat() - 0.5f) * 0.1);
			break;
			case 1:
				x /= dist;
				y /= dist;
				z /= dist;

				entity.setVelocity(
                    x * 2 + (r.nextFloat() - 0.5f) * 0.1,
                    y * 2 + (r.nextFloat() - 0.5f) * 0.1,
                    z * 2 + (r.nextFloat() - 0.5f) * 0.1);
			break;
		}
		return true;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:be");
	}*/
}

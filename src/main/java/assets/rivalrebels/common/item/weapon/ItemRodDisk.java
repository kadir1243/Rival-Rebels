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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemRodDisk extends Item
{
	public ItemRodDisk() {
		super(new Settings().maxCount(1));
	}

    @Override
	public UseAction getUseAction(ItemStack par1ItemStack)
	{
		return UseAction.BOW;
	}

	@Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 300;
	}

	boolean pass = false;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

		if (!pass)
		{
			user.sendMessage(Text.of("Password?"), true);
			pass = true;
		}
		user.setCurrentHand(hand);
		if (RivalRebels.round.rrplayerlist.getForGameProfile(user.getGameProfile()).rrrank.id > 1) RivalRebelsSoundPlayer.playSound(user, 6, 2);
		else RivalRebelsSoundPlayer.playSound(user, 7, 2);
		return TypedActionResult.success(stack, world.isClient());
	}

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		PlayerEntity player;
        if (user instanceof PlayerEntity) player = (PlayerEntity) user;
        else return;
        if (!world.isClient) {
			if (!player.getAbilities().invulnerable) player.getInventory().removeOne(stack);
			int rank = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrrank.id;
			//float f = (getMaxUseTime(item) - i) / 20.0F;
			//f = (f * f + f * 2) * 0.33333f;
			//if (f > 1.0F) f = 1.0F;
			//f *= 1.0f-rank*0.1f;
			//f += 0.3f;
			if (rank == 0)
			{
				Entity entity = new EntityRoddiskRegular(world, player, 1);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 7, 3);
			}
			else if (rank == 1)
			{
				Entity entity = new EntityRoddiskRebel(world, player, 1.1f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 7, 3);
			}
			else if (rank == 2)
			{
				Entity entity = new EntityRoddiskOfficer(world, player, 1.2f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 6, 3);
			}
			else if (rank == 3)
			{
				Entity entity = new EntityRoddiskLeader(world, player, 4f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 6, 3);
			}
			else if (rank == 4)
			{
				Entity entity = new EntityRoddiskRep(world, player, 4f);
				world.spawnEntity(entity);
				RivalRebelsSoundPlayer.playSound(player, 6, 3);
			}
		}
	}
}

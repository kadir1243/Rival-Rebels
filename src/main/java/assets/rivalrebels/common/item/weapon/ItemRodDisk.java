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
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemRodDisk extends Item
{
	public ItemRodDisk() {
		super(new Properties().stacksTo(1));
	}

    @Override
	public UseAnim getUseAnimation(ItemStack par1ItemStack)
	{
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 300;
	}

	boolean pass = false;

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

		if (!pass)
		{
			user.displayClientMessage(Component.nullToEmpty("Password?"), true);
			pass = true;
		}
		user.startUsingItem(hand);
		if (RivalRebels.round.rrplayerlist.getForGameProfile(user.getGameProfile()).rrrank.id > 1) RivalRebelsSoundPlayer.playSound(user, 6, 2);
		else RivalRebelsSoundPlayer.playSound(user, 7, 2);
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player player)) return;
        if (!world.isClientSide()) {
            stack.consume(1, user);
			int rank = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrrank.id;
			//float f = (getMaxUseTime(item) - i) / 20.0F;
			//f = (f * f + f * 2) * 0.33333f;
			//if (f > 1.0F) f = 1.0F;
			//f *= 1.0f-rank*0.1f;
			//f += 0.3f;
			if (rank == 0)
			{
				Entity entity = new EntityRoddiskRegular(world, user, 1);
				world.addFreshEntity(entity);
				RivalRebelsSoundPlayer.playSound(user, 7, 3);
			}
			else if (rank == 1)
			{
				Entity entity = new EntityRoddiskRebel(world, user, 1.1f);
				world.addFreshEntity(entity);
				RivalRebelsSoundPlayer.playSound(user, 7, 3);
			}
			else if (rank == 2)
			{
				Entity entity = new EntityRoddiskOfficer(world, user, 1.2f);
				world.addFreshEntity(entity);
				RivalRebelsSoundPlayer.playSound(user, 6, 3);
			}
			else if (rank == 3)
			{
				Entity entity = new EntityRoddiskLeader(world, user, 4f);
				world.addFreshEntity(entity);
				RivalRebelsSoundPlayer.playSound(user, 6, 3);
			}
			else if (rank == 4)
			{
				Entity entity = new EntityRoddiskRep(world, user, 4f);
				world.addFreshEntity(entity);
				RivalRebelsSoundPlayer.playSound(user, 6, 3);
			}
		}
	}
}

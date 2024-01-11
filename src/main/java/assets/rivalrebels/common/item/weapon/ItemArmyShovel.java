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
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.HashSet;
import java.util.Set;

public class ItemArmyShovel extends ItemTool
{
	private static final Set<Block> blocksEffectiveAgainst;

	public ItemArmyShovel()
	{
		super(1, 1, ToolMaterial.IRON, blocksEffectiveAgainst);
		setCreativeTab(RivalRebels.rralltab);
        setHarvestLevel("shovel", 3);
        setHarvestLevel("pickaxe", 3);
        setHarvestLevel("axe", 3);
	}

	@Override
	public boolean isDamageable()
	{
		return false;
	}

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return blocksEffectiveAgainst.contains(state.getBlock());
    }

    static
	{
		blocksEffectiveAgainst = new HashSet<>();
		blocksEffectiveAgainst.add(RivalRebels.barricade);
		blocksEffectiveAgainst.add(RivalRebels.reactive);
		blocksEffectiveAgainst.add(RivalRebels.conduit);
		blocksEffectiveAgainst.add(RivalRebels.tower);
		blocksEffectiveAgainst.add(RivalRebels.steel);
		blocksEffectiveAgainst.add(RivalRebels.rhodesactivator);
		blocksEffectiveAgainst.add(RivalRebels.camo1);
		blocksEffectiveAgainst.add(RivalRebels.camo2);
		blocksEffectiveAgainst.add(RivalRebels.camo3);
		blocksEffectiveAgainst.add(RivalRebels.jump);
		blocksEffectiveAgainst.add(RivalRebels.landmine);
		blocksEffectiveAgainst.add(RivalRebels.alandmine);
		blocksEffectiveAgainst.add(RivalRebels.quicksand);
		blocksEffectiveAgainst.add(RivalRebels.aquicksand);
		blocksEffectiveAgainst.add(RivalRebels.mario);
		blocksEffectiveAgainst.add(RivalRebels.amario);
		blocksEffectiveAgainst.add(RivalRebels.loader);
		blocksEffectiveAgainst.add(RivalRebels.reactor);
		blocksEffectiveAgainst.add(RivalRebels.radioactivedirt);
		blocksEffectiveAgainst.add(RivalRebels.radioactivesand);
		blocksEffectiveAgainst.add(RivalRebels.petrifiedstone1);
		blocksEffectiveAgainst.add(RivalRebels.petrifiedstone2);
		blocksEffectiveAgainst.add(RivalRebels.petrifiedstone3);
		blocksEffectiveAgainst.add(RivalRebels.petrifiedstone4);
		blocksEffectiveAgainst.add(RivalRebels.petrifiedwood);
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:aw");
	}*/
}

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
package assets.rivalrebels.common.round;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public enum RivalRebelsClass
{
	NONE(0, 0xFFFFFF, "NONE", RRIdentifiers.guitrivalrebels, new ItemStack[0]),

	REBEL(1, 0xFF0000, "REBEL", RRIdentifiers.guitrebel,
			new ItemStack[] {
					RRItems.rpg.getDefaultStack(),
					RRItems.einsten.getDefaultStack(),
					new ItemStack(RRItems.expill, 3),
					RRItems.roddisk.getDefaultStack(),
					//new ItemStack(RivalRebels.binoculars),
					RRItems.pliers.getDefaultStack(),
					RRItems.armyshovel.getDefaultStack(),
					new ItemStack(RRItems.knife, 5),
					new ItemStack(RRItems.gasgrenade, 6),
					RRItems.chip.getDefaultStack(),
					//new ItemStack(RivalRebels.bastion, 2),
					new ItemStack(RRBlocks.tower, 2),
					new ItemStack(RRBlocks.barricade, 2),
					new ItemStack(RRBlocks.quicksandtrap, 2),
					RRBlocks.explosives.asItem().getDefaultStack(),
					//new ItemStack(RivalRebels.ammunition),
					RRBlocks.bunker.asItem().getDefaultStack(),
					new ItemStack(RRItems.rocket, 64),
					RRItems.redrod.getDefaultStack(),
					RRItems.redrod.getDefaultStack(),
					new ItemStack(RRBlocks.jump, 4),
			}),

	NUKER(2, 0xFFFF00, "NUKER", RRIdentifiers.guitnuker,
			new ItemStack[] {
					RRItems.flamethrower.getDefaultStack(),
					new ItemStack(RRItems.safepill, 3),
					RRItems.roddisk.getDefaultStack(),
					//new ItemStack(RivalRebels.binoculars),
					RRItems.pliers.getDefaultStack(),
					RRItems.armyshovel.getDefaultStack(),
					RRItems.chip.getDefaultStack(),
					RRBlocks.loader.asItem().getDefaultStack(),
					new ItemStack(RRBlocks.bunker, 2),
					new ItemStack(RRBlocks.minetrap, 2),
					RRBlocks.nukeCrateTop.asItem().getDefaultStack(),
					RRBlocks.nukeCrateBottom.asItem().getDefaultStack(),
					RRItems.nuclearelement.getDefaultStack(),
					new ItemStack(RRBlocks.explosives, 2),
					new ItemStack(RRBlocks.tower, 2),
					//new ItemStack(RivalRebels.ammunition),
					new ItemStack(RRItems.fuel, 64),
					new ItemStack(RRBlocks.jump, 4),
					//new ItemStack(RivalRebels.steel, 16),
			}),

	INTEL(3, 0x00FFBB, "INTEL", RRIdentifiers.guitintel,
			new ItemStack[] {
					RRItems.tesla.getDefaultStack(),
					new ItemStack(RRItems.safepill, 3),
					RRItems.roddisk.getDefaultStack(),
					//new ItemStack(RivalRebels.binoculars),
					RRItems.pliers.getDefaultStack(),
					RRItems.armyshovel.getDefaultStack(),
					new ItemStack(RRItems.knife, 5),
					new ItemStack(RRItems.gasgrenade, 6),
					RRItems.chip.getDefaultStack(),
					//new ItemStack(RivalRebels.controller),
					RRItems.remote.getDefaultStack(),
					new ItemStack(RRBlocks.remotecharge, 8),
					//new ItemStack(RivalRebels.core1),
					new ItemStack(RRBlocks.barricade, 2),
					new ItemStack(RRBlocks.tower, 4),
					new ItemStack(RRBlocks.quicksandtrap, 4),
					new ItemStack(RRBlocks.mariotrap, 4),
					new ItemStack(RRBlocks.minetrap, 4),
					//new ItemStack(RivalRebels.supplies, 2),
					new ItemStack(RRItems.battery, 64),
					new ItemStack(RRItems.battery, 64),
					new ItemStack(RRBlocks.steel, 16),
					new ItemStack(RRBlocks.jump, 8),
			}),

	HACKER(4, 0x00FF00, "HACKER", RRIdentifiers.guithacker,
			new ItemStack[] {
					RRItems.plasmacannon.getDefaultStack(),
					new ItemStack(RRItems.expill, 3),
					RRItems.roddisk.getDefaultStack(),
					//new ItemStack(RivalRebels.binoculars),
					RRItems.pliers.getDefaultStack(),
					RRItems.armyshovel.getDefaultStack(),
					RRItems.chip.getDefaultStack(),
					//new ItemStack(RivalRebels.controller),
					RRBlocks.loader.asItem().getDefaultStack(),
					RRBlocks.breadbox.asItem().getDefaultStack(),
					//new ItemStack(RivalRebels.remote),
					RRItems.fuse.getDefaultStack(),
					RRItems.antenna.getDefaultStack(),
					//new ItemStack(RivalRebels.forcefieldnode, 2),
					//new ItemStack(RivalRebels.ffreciever, 2),
					new ItemStack(RRBlocks.bastion, 4),
					new ItemStack(RRBlocks.barricade, 4),
					new ItemStack(RRBlocks.bunker, 4),
					//new ItemStack(RivalRebels.mariotrap, 2),
					new ItemStack(RRBlocks.ammunition, 3),
					RRItems.hydrod.getDefaultStack(),
					new ItemStack(RRBlocks.quicksandtrap, 4),
					new ItemStack(RRBlocks.mariotrap, 4),
					new ItemStack(RRBlocks.steel, 32),
					new ItemStack(RRBlocks.jump, 8),
			});

	public final ItemStack[] inventory;
	public final Identifier resource;
	public final String name;
	public final int color;
	public final int id;

	RivalRebelsClass(int i, int c, String n, Identifier r, ItemStack[] inv)
	{
		id = i;
		color = c;
		name = n;
		resource = r;
		inventory = inv;
	}

	public static RivalRebelsClass getForID(int i)
	{
        return switch (i) {
            case 1 -> REBEL;
            case 2 -> NUKER;
            case 3 -> INTEL;
            case 4 -> HACKER;
            default -> NONE;
        };
    }
}

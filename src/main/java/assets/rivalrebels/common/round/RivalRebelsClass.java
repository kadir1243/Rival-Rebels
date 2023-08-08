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

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import assets.rivalrebels.RivalRebels;

public enum RivalRebelsClass
{
	NONE(0, 0xFFFFFF, "NONE", RivalRebels.guitrivalrebels, new ItemStack[0]),

	REBEL(1, 0xFF0000, "REBEL", RivalRebels.guitrebel,
			new ItemStack[] {
					new ItemStack(RivalRebels.rpg),
					new ItemStack(RivalRebels.einsten),
					new ItemStack(RivalRebels.expill, 3),
					new ItemStack(RivalRebels.roddisk),
					//new ItemStack(RivalRebels.binoculars),
					new ItemStack(RivalRebels.pliers),
					new ItemStack(RivalRebels.armyshovel),
					new ItemStack(RivalRebels.knife, 5),
					new ItemStack(RivalRebels.gasgrenade, 6),
					new ItemStack(RivalRebels.chip),
					//new ItemStack(RivalRebels.bastion, 2),
					new ItemStack(RivalRebels.tower, 2),
					new ItemStack(RivalRebels.barricade, 2),
					new ItemStack(RivalRebels.quicksandtrap, 2),
					new ItemStack(RivalRebels.explosives),
					//new ItemStack(RivalRebels.ammunition),
					new ItemStack(RivalRebels.bunker),
					new ItemStack(RivalRebels.rocket, 64),
					new ItemStack(RivalRebels.redrod),
					new ItemStack(RivalRebels.redrod),
					new ItemStack(RivalRebels.jump, 4),
			}),

	NUKER(2, 0xFFFF00, "NUKER", RivalRebels.guitnuker,
			new ItemStack[] {
					new ItemStack(RivalRebels.flamethrower),
					new ItemStack(RivalRebels.safepill, 3),
					new ItemStack(RivalRebels.roddisk),
					//new ItemStack(RivalRebels.binoculars),
					new ItemStack(RivalRebels.pliers),
					new ItemStack(RivalRebels.armyshovel),
					new ItemStack(RivalRebels.chip),
					new ItemStack(RivalRebels.loader),
					new ItemStack(RivalRebels.bunker, 2),
					new ItemStack(RivalRebels.minetrap, 2),
					new ItemStack(RivalRebels.nukeCrateTop),
					new ItemStack(RivalRebels.nukeCrateBottom),
					new ItemStack(RivalRebels.nuclearelement),
					new ItemStack(RivalRebels.explosives, 2),
					new ItemStack(RivalRebels.tower, 2),
					//new ItemStack(RivalRebels.ammunition),
					new ItemStack(RivalRebels.fuel, 64),
					new ItemStack(RivalRebels.jump, 4),
					//new ItemStack(RivalRebels.steel, 16),
			}),

	INTEL(3, 0x00FFBB, "INTEL", RivalRebels.guitintel,
			new ItemStack[] {
					new ItemStack(RivalRebels.tesla),
					new ItemStack(RivalRebels.safepill, 3),
					new ItemStack(RivalRebels.roddisk),
					//new ItemStack(RivalRebels.binoculars),
					new ItemStack(RivalRebels.pliers),
					new ItemStack(RivalRebels.armyshovel),
					new ItemStack(RivalRebels.knife, 5),
					new ItemStack(RivalRebels.gasgrenade, 6),
					new ItemStack(RivalRebels.chip),
					//new ItemStack(RivalRebels.controller),
					new ItemStack(RivalRebels.remote),
					new ItemStack(RivalRebels.remotecharge, 8),
					//new ItemStack(RivalRebels.copperCore),
					new ItemStack(RivalRebels.barricade, 2),
					new ItemStack(RivalRebels.tower, 4),
					new ItemStack(RivalRebels.quicksandtrap, 4),
					new ItemStack(RivalRebels.mariotrap, 4),
					new ItemStack(RivalRebels.minetrap, 4),
					//new ItemStack(RivalRebels.supplies, 2),
					new ItemStack(RivalRebels.battery, 64),
					new ItemStack(RivalRebels.battery, 64),
					new ItemStack(RivalRebels.steel, 16),
					new ItemStack(RivalRebels.jump, 8),
			}),

	HACKER(4, 0x00FF00, "HACKER", RivalRebels.guithacker,
			new ItemStack[] {
					new ItemStack(RivalRebels.plasmacannon),
					new ItemStack(RivalRebels.expill, 3),
					new ItemStack(RivalRebels.roddisk),
					//new ItemStack(RivalRebels.binoculars),
					new ItemStack(RivalRebels.pliers),
					new ItemStack(RivalRebels.armyshovel),
					new ItemStack(RivalRebels.chip),
					//new ItemStack(RivalRebels.controller),
					new ItemStack(RivalRebels.loader),
					new ItemStack(RivalRebels.breadbox),
					//new ItemStack(RivalRebels.remote),
					new ItemStack(RivalRebels.fuse),
					new ItemStack(RivalRebels.antenna),
					//new ItemStack(RivalRebels.forcefieldnode, 2),
					//new ItemStack(RivalRebels.ffreciever, 2),
					new ItemStack(RivalRebels.bastion, 4),
					new ItemStack(RivalRebels.barricade, 4),
					new ItemStack(RivalRebels.bunker, 4),
					//new ItemStack(RivalRebels.mariotrap, 2),
					new ItemStack(RivalRebels.ammunition, 3),
					new ItemStack(RivalRebels.hydrod),
					new ItemStack(RivalRebels.quicksandtrap, 4),
					new ItemStack(RivalRebels.mariotrap, 4),
					new ItemStack(RivalRebels.steel, 32),
					new ItemStack(RivalRebels.jump, 8),
			});

	public ItemStack[]		inventory;
	public ResourceLocation	resource;
	public String			name;
	public int				color;
	public int				id;

	RivalRebelsClass(int i, int c, String n, ResourceLocation r, ItemStack[] inv)
	{
		id = i;
		color = c;
		name = n;
		resource = r;
		inventory = inv;
	}

	public static RivalRebelsClass getForID(int i) {
        switch (i) {
            case 1:
                return REBEL;
            case 2:
                return NUKER;
            case 3:
                return INTEL;
            case 4:
                return HACKER;
            default:
                return NONE;
        }
    }
}

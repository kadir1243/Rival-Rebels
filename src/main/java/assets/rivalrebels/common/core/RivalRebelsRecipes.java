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
package assets.rivalrebels.common.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import assets.rivalrebels.RivalRebels;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RivalRebelsRecipes
{
	public static void registerRecipes()
	{
		// Crafting
		// Tier 1
		// Ammo
		GameRegistry.addRecipe(new ItemStack(RivalRebels.fuel, 16), "DS", "GC", 'C', Blocks.cobblestone, 'S', Blocks.sand, 'G', Blocks.gravel, 'D', Blocks.dirt);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.rocket, 16), "SC", "DG", 'C', Blocks.cobblestone, 'S', Blocks.sand, 'G', Blocks.gravel, 'D', Blocks.dirt);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.battery, 4), "CG", "SD", 'C', Blocks.cobblestone, 'S', Blocks.sand, 'G', Blocks.gravel, 'D', Blocks.dirt);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.gasgrenade, 6), "GD", "CS", 'C', Blocks.cobblestone, 'S', Blocks.sand, 'G', Blocks.gravel, 'D', Blocks.dirt);

		// EasterEgg
		GameRegistry.addRecipe(new ItemStack(RivalRebels.easteregg, 1), "DD", "CC", 'C', Blocks.cobblestone, 'D', Blocks.dirt);

		// Armor
		GameRegistry.addRecipe(new ItemStack(RivalRebels.sigmaarmor, 1), "SSC", "SPB", "SSD", 'C', Blocks.cobblestone, 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'D', Blocks.dirt, 'B', Items.bone);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.omegaarmor, 1), "SSD", "SPB", "SSC", 'C', Blocks.cobblestone, 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'D', Blocks.dirt, 'B', Items.bone);

		// Flagbox
		GameRegistry.addRecipe(new ItemStack(RivalRebels.flagbox1, 1), "W", "P", 'W', Blocks.wool, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.flagbox3, 1), "P", "W", 'W', Blocks.wool, 'P', RivalRebels.pliers);

		// Supplies
		GameRegistry.addRecipe(new ItemStack(RivalRebels.armyshovel, 1), "CD", "SG", 'C', Blocks.cobblestone, 'S', Blocks.sand, 'G', Blocks.gravel, 'D', Blocks.dirt);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.amario, 16), "SS", "GG", 'S', Blocks.sand, 'G', Blocks.gravel);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.aquicksand, 16), "SS", "DD", 'S', Blocks.sand, 'D', Blocks.dirt);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.jump, 8), "CC", "DD", 'C', Blocks.cobblestone, 'D', Blocks.dirt);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.steel, 16), "CC", "CC", 'C', Blocks.cobblestone);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.smartcamo, 16), "SCS", "CPC", "SCS", 'C', Blocks.cobblestone, 'S', RivalRebels.steel, 'P', RivalRebels.pliers);

		// Explosives
		GameRegistry.addRecipe(new ItemStack(RivalRebels.pliers, 1), " C", "C ", 'C', Blocks.cobblestone);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.roda, 1), "IN", "IH", "IR", 'I', Items.iron_ingot, 'N', RivalRebels.nuclearelement, 'H', RivalRebels.hydrod, 'R', RivalRebels.redrod);

		// Weapons
		GameRegistry.addRecipe(new ItemStack(RivalRebels.knife, 5), "CG", "GC", 'C', Blocks.cobblestone, 'G', Blocks.gravel);

		// Miscellaneous
		GameRegistry.addRecipe(new ItemStack(RivalRebels.trollmask, 8), "SS", 'S', Blocks.sand);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.cycle, 1), "TT", "TT", 'T', RivalRebels.trollmask);

		// End Tier 1
		// Tier 2
		// AutoBuilds
		GameRegistry.addRecipe(new ItemStack(RivalRebels.tower, 2), "SS", "SJ", 'S', RivalRebels.steel, 'J', RivalRebels.jump);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.barricade, 1), "SSS", "LPL", "RCR", 'L', RivalRebels.binoculars, 'P', RivalRebels.pliers, 'S', RivalRebels.supplies, 'R', RivalRebels.reactive, 'C', RivalRebels.conduit);

		// Explosives
		GameRegistry.addRecipe(new ItemStack(RivalRebels.timedbomb, 1), "FB", "RP", 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.remotecharge, 4), "GB", "RF", 'G', RivalRebels.gasgrenade, 'B', RivalRebels.battery, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.remote, 1), "BF", "RP", 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.alandmine, 16), "DB", "RP", 'D', Blocks.dirt, 'B', RivalRebels.battery, 'P', RivalRebels.pliers, 'R', RivalRebels.rocket);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.flare, 8), "FD", "RD", 'F', RivalRebels.fuel, 'D', Blocks.dirt, 'R', RivalRebels.rocket);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.nukeCrateTop, 1), " SS", "SP ", " SS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.nukeCrateBottom, 1), "SS ", "SP ", "SS ", 'S', RivalRebels.steel, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.fuse, 1), "BP", "SP", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery);

		// Weapons
		GameRegistry.addRecipe(new ItemStack(RivalRebels.rpg, 1), "RRR", "BP ", "SSS", 'S', RivalRebels.steel, 'R', RivalRebels.rocket, 'B', RivalRebels.battery, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.flamethrower, 1), "FFF", "BP ", "SSS", 'S', RivalRebels.steel, 'F', RivalRebels.fuel, 'B', RivalRebels.battery, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.tesla, 1), "BBB", "BP ", "SSS", 'B', RivalRebels.battery, 'S', RivalRebels.steel, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.plasmacannon, 1), "HHH", "BP ", "SSS", 'S', RivalRebels.steel, 'H', RivalRebels.hydrod, 'B', RivalRebels.battery, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.einsten, 1), "HHH", "BP ", "SSS", 'S', RivalRebels.steel, 'H', RivalRebels.redrod, 'B', RivalRebels.battery, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.roddisk, 1), "HPB", 'H', RivalRebels.hydrod, 'B', RivalRebels.battery, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.hydrod, 1), "W", "S", 'S', RivalRebels.emptyrod, 'W', Items.water_bucket);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.redrod, 1), "W", "S", 'S', RivalRebels.emptyrod, 'W', Items.redstone);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.emptyrod, 4), "SPS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers);

		// Supplies
		GameRegistry.addRecipe(new ItemStack(RivalRebels.expill, 4), "STG", " BR", "F W", 'S', Items.wheat_seeds, 'T', RivalRebels.trollmask, 'G', RivalRebels.gasgrenade, 'B', Items.milk_bucket, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel, 'W', Items.water_bucket);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.safepill, 4), "S  ", " B ", "F W", 'S', Items.wheat_seeds, 'B', Items.milk_bucket, 'F', RivalRebels.fuel, 'W', Items.water_bucket);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.breadbox, 1), " S", "GP", " B", 'G', Items.wheat_seeds, 'S', RivalRebels.steel, 'B', Items.water_bucket, 'P', RivalRebels.pliers);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.loader, 1), "SBS", "SPS", "SBS", 'S', RivalRebels.steel, 'B', RivalRebels.battery, 'P', RivalRebels.pliers);

		// End Tier 2
		// Tier 3
		// Crates
		GameRegistry.addRecipe(new ItemStack(RivalRebels.ammunition, 1), "SFS", "FPF", "SFS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'F', RivalRebels.emptyrod);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.supplies, 1), "SRS", "APB", "SLS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'R', RivalRebels.remote, 'L', RivalRebels.loader, 'A', RivalRebels.antenna, 'B', RivalRebels.battery);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.explosives, 1), "SFS", "GPG", "SFS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'F', RivalRebels.flare, 'G', RivalRebels.gasgrenade);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.weapons, 1), "SFS", "BPB", "SFS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.ammunition, 'F', RivalRebels.battery);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.antenna, 1), "RB", "SP", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.hackm202, 1), "HFR", " P ", "EMA", 'H', RivalRebels.hydrod, 'P', RivalRebels.pliers, 'A', RivalRebels.ammunition, 'R', RivalRebels.nuclearelement, 'M', RivalRebels.rpg, 'E', RivalRebels.explosives, 'F', RivalRebels.fuse);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.seekm202, 1), "RRR", "BPQ", "SMS", 'S', RivalRebels.steel, 'R', RivalRebels.antenna, 'B', RivalRebels.battery, 'P', RivalRebels.pliers, 'Q', RivalRebels.remote, 'M', RivalRebels.rpg);

		// End Tier 3
		// Tier 4
		// Bunker
		GameRegistry.addRecipe(new ItemStack(RivalRebels.chip, 1), " RA", "TPB", "TLB", 'T', RivalRebels.breadbox, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote, 'L', RivalRebels.loader, 'A', RivalRebels.antenna);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.bunker, 1), "CSC", "SPS", "CSC", 'C', RivalRebels.smartcamo, 'P', RivalRebels.pliers, 'S', RivalRebels.conduit);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.binoculars, 1), " RA", "CPB", " SS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote, 'C', RivalRebels.chip, 'A', RivalRebels.antenna);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.controller, 1), " RA", "CPB", " SB", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote, 'C', RivalRebels.chip, 'A', RivalRebels.antenna);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.bastion, 1), "RRR", "CPC", "RRR", 'R', RivalRebels.reactive, 'P', RivalRebels.pliers, 'C', RivalRebels.conduit);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.forcefieldnode, 2), "RCR", "HPC", "RCR", 'R', RivalRebels.reactive, 'P', RivalRebels.pliers, 'C', RivalRebels.conduit, 'H', RivalRebels.chip);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.reactive, 4), "CDC", "DBD", "CDC", 'S', RivalRebels.steel, 'H', RivalRebels.battery, 'D', RivalRebels.conduit, 'C', RivalRebels.smartcamo);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.conduit, 16), "CEC", "EBE", "CEC", 'B', RivalRebels.redrod, 'E', RivalRebels.emptyrod, 'C', RivalRebels.steel);
		// End Tier 4

		GameRegistry.addRecipe(new ItemStack(RivalRebels.copperCore, 1), " DS", "CPD", " DS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'C', RivalRebels.chip, 'D', RivalRebels.conduit);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.tungstenCore, 1), " DS", "CP1", " DS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'C', RivalRebels.chip, 'D', RivalRebels.conduit, '1', RivalRebels.copperCore);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.titaniumCore, 1), " DS", "CP2", " DS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'C', RivalRebels.chip, 'D', RivalRebels.conduit, '2', RivalRebels.tungstenCore);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.minetrap, 2), "SDS", "DPD", "SDS", 'S', RivalRebels.alandmine, 'P', RivalRebels.pliers, 'D', RivalRebels.conduit);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.quicksandtrap, 2), "SDS", "DPD", "SDS", 'S', RivalRebels.aquicksand, 'P', RivalRebels.pliers, 'D', RivalRebels.conduit);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.mariotrap, 2), "SDS", "DPD", "SDS", 'S', RivalRebels.amario, 'P', RivalRebels.pliers, 'D', RivalRebels.conduit);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.ffreciever, 2), "LPS", "RCR", 'L', RivalRebels.binoculars, 'P', RivalRebels.pliers, 'S', RivalRebels.supplies, 'R', RivalRebels.reactive, 'C', RivalRebels.conduit);

		GameRegistry.addRecipe(new ItemStack(RivalRebels.buildrhodes, 2), "SAS", "CPT", "RLB", 'B', RivalRebels.binoculars, 'P', RivalRebels.pliers, 'S', RivalRebels.supplies, 'A', RivalRebels.antenna, 'C', RivalRebels.chip, 'T', RivalRebels.titaniumCore, 'R', RivalRebels.controller, 'L', RivalRebels.loader);
	}
}

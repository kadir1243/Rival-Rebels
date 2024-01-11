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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class RivalRebelsRecipes
{
	public static void registerRecipes(IForgeRegistry<IRecipe> registry)
	{
		// Crafting
		// Tier 1
		// Ammo
		addRecipe(registry, new ItemStack(RivalRebels.fuel, 16), new Object[] { "DS", "GC", 'C', Blocks.COBBLESTONE, 'S', Blocks.SAND, 'G', Blocks.GRAVEL, 'D', Blocks.DIRT });

		addRecipe(registry, new ItemStack(RivalRebels.rocket, 16), new Object[] { "SC", "DG", 'C', Blocks.COBBLESTONE, 'S', Blocks.SAND, 'G', Blocks.GRAVEL, 'D', Blocks.DIRT });

		addRecipe(registry, new ItemStack(RivalRebels.battery, 4), new Object[] { "CG", "SD", 'C', Blocks.COBBLESTONE, 'S', Blocks.SAND, 'G', Blocks.GRAVEL, 'D', Blocks.DIRT });

		addRecipe(registry, new ItemStack(RivalRebels.gasgrenade, 6), new Object[] { "GD", "CS", 'C', Blocks.COBBLESTONE, 'S', Blocks.SAND, 'G', Blocks.GRAVEL, 'D', Blocks.DIRT });

		// EasterEgg
		addRecipe(registry, new ItemStack(RivalRebels.easteregg), new Object[] { "DD", "CC", 'C', Blocks.COBBLESTONE, 'D', Blocks.DIRT });

		// Armor
		addRecipe(registry, new ItemStack(RivalRebels.sigmaarmor), new Object[] { "SSC", "SPB", "SSD", 'C', Blocks.COBBLESTONE, 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'D', Blocks.DIRT, 'B', Items.BONE });

		addRecipe(registry, new ItemStack(RivalRebels.omegaarmor), new Object[] { "SSD", "SPB", "SSC", 'C', Blocks.COBBLESTONE, 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'D', Blocks.DIRT, 'B', Items.BONE });

		// Flagbox
		addRecipe(registry, new ItemStack(RivalRebels.flagbox1), new Object[] { "W", "P", 'W', Blocks.WOOL, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.flagbox3), new Object[] { "P", "W", 'W', Blocks.WOOL, 'P', RivalRebels.pliers });

		// Supplies
		addRecipe(registry, RivalRebels.armyshovel.getDefaultInstance(), new Object[] { "CD", "SG", 'C', Blocks.COBBLESTONE, 'S', Blocks.SAND, 'G', Blocks.GRAVEL, 'D', Blocks.DIRT });

		addRecipe(registry, new ItemStack(RivalRebels.amario, 16), new Object[] { "SS", "GG", 'S', Blocks.SAND, 'G', Blocks.GRAVEL });

		addRecipe(registry, new ItemStack(RivalRebels.aquicksand, 16), new Object[] { "SS", "DD", 'S', Blocks.SAND, 'D', Blocks.DIRT });

		addRecipe(registry, new ItemStack(RivalRebels.jump, 8), new Object[] { "CC", "DD", 'C', Blocks.COBBLESTONE, 'D', Blocks.DIRT });

		addRecipe(registry, new ItemStack(RivalRebels.steel, 16), new Object[] { "CC", "CC", 'C', Blocks.COBBLESTONE });

		addRecipe(registry, new ItemStack(RivalRebels.smartcamo, 16), new Object[] { "SCS", "CPC", "SCS", 'C', Blocks.COBBLESTONE, 'S', RivalRebels.steel, 'P', RivalRebels.pliers });

		// Explosives
		addRecipe(registry, new ItemStack(RivalRebels.pliers, 1), new Object[] { " C", "C ", 'C', Blocks.COBBLESTONE });

		addRecipe(registry, new ItemStack(RivalRebels.roda, 1), new Object[] { "IN", "IH", "IR", 'I', Items.IRON_INGOT, 'N', RivalRebels.nuclearelement, 'H', RivalRebels.hydrod, 'R', RivalRebels.redrod });

		// Weapons
		addRecipe(registry, new ItemStack(RivalRebels.knife, 5), new Object[] { "CG", "GC", 'C', Blocks.COBBLESTONE, 'G', Blocks.GRAVEL });

		// Miscellaneous
		addRecipe(registry, new ItemStack(RivalRebels.trollmask, 8), new Object[] { "SS", 'S', Blocks.SAND });

		addRecipe(registry, new ItemStack(RivalRebels.cycle, 1), new Object[] { "TT", "TT", 'T', RivalRebels.trollmask });

		// End Tier 1
		// Tier 2
		// AutoBuilds
		addRecipe(registry, new ItemStack(RivalRebels.tower, 2), new Object[] { "SS", "SJ", 'S', RivalRebels.steel, 'J', RivalRebels.jump });

		addRecipe(registry, new ItemStack(RivalRebels.barricade, 1), new Object[] { "SSS", "LPL", "RCR", 'L', RivalRebels.binoculars, 'P', RivalRebels.pliers, 'S', RivalRebels.supplies, 'R', RivalRebels.reactive, 'C', RivalRebels.conduit });

		// Explosives
		addRecipe(registry, new ItemStack(RivalRebels.timedbomb, 1), new Object[] { "FB", "RP", 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel });

		addRecipe(registry, new ItemStack(RivalRebels.remotecharge, 4), new Object[] { "GB", "RF", 'G', RivalRebels.gasgrenade, 'B', RivalRebels.battery, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel });

		addRecipe(registry, new ItemStack(RivalRebels.remote, 1), new Object[] { "BF", "RP", 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel });

		addRecipe(registry, new ItemStack(RivalRebels.alandmine, 16), new Object[] { "DB", "RP", 'D', Blocks.DIRT, 'B', RivalRebels.battery, 'P', RivalRebels.pliers, 'R', RivalRebels.rocket });

		addRecipe(registry, new ItemStack(RivalRebels.flare, 8), new Object[] { "FD", "RD", 'F', RivalRebels.fuel, 'D', Blocks.DIRT, 'R', RivalRebels.rocket });

		addRecipe(registry, new ItemStack(RivalRebels.nukeCrateTop, 1), new Object[] { " SS", "SP ", " SS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.nukeCrateBottom, 1), new Object[] { "SS ", "SP ", "SS ", 'S', RivalRebels.steel, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.fuse, 1), new Object[] { "BP", "SP", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery });

		// Weapons
		addRecipe(registry, new ItemStack(RivalRebels.rpg, 1), new Object[] { "RRR", "BP ", "SSS", 'S', RivalRebels.steel, 'R', RivalRebels.rocket, 'B', RivalRebels.battery, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.flamethrower, 1), new Object[] { "FFF", "BP ", "SSS", 'S', RivalRebels.steel, 'F', RivalRebels.fuel, 'B', RivalRebels.battery, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.tesla, 1), new Object[] { "BBB", "BP ", "SSS", 'B', RivalRebels.battery, 'S', RivalRebels.steel, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.plasmacannon, 1), new Object[] { "HHH", "BP ", "SSS", 'S', RivalRebels.steel, 'H', RivalRebels.hydrod, 'B', RivalRebels.battery, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.einsten, 1), new Object[] { "HHH", "BP ", "SSS", 'S', RivalRebels.steel, 'H', RivalRebels.redrod, 'B', RivalRebels.battery, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.roddisk, 1), new Object[] { "HPB", 'H', RivalRebels.hydrod, 'B', RivalRebels.battery, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.hydrod, 1), new Object[] { "W", "S", 'S', RivalRebels.emptyrod, 'W', Items.WATER_BUCKET });

		addRecipe(registry, new ItemStack(RivalRebels.redrod, 1), new Object[] { "W", "S", 'S', RivalRebels.emptyrod, 'W', Items.REDSTONE });

		addRecipe(registry, new ItemStack(RivalRebels.emptyrod, 4), new Object[] { "SPS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers });

		// Supplies
		addRecipe(registry, new ItemStack(RivalRebels.expill, 4), new Object[] { "STG", " BR", "F W", 'S', Items.WHEAT_SEEDS, 'T', RivalRebels.trollmask, 'G', RivalRebels.gasgrenade, 'B', Items.MILK_BUCKET, 'R', RivalRebels.rocket, 'F', RivalRebels.fuel, 'W', Items.WATER_BUCKET });

		addRecipe(registry, new ItemStack(RivalRebels.safepill, 4), new Object[] { "S  ", " B ", "F W", 'S', Items.WHEAT_SEEDS, 'B', Items.MILK_BUCKET, 'F', RivalRebels.fuel, 'W', Items.WATER_BUCKET });

		addRecipe(registry, new ItemStack(RivalRebels.breadbox), new Object[] { " S", "GP", " B", 'G', Items.WHEAT_SEEDS, 'S', RivalRebels.steel, 'B', Items.WATER_BUCKET, 'P', RivalRebels.pliers });

		addRecipe(registry, new ItemStack(RivalRebels.loader), new Object[] { "SBS", "SPS", "SBS", 'S', RivalRebels.steel, 'B', RivalRebels.battery, 'P', RivalRebels.pliers });

		// End Tier 2
		// Tier 3
		// Crates
		addRecipe(registry, new ItemStack(RivalRebels.ammunition), new Object[] { "SFS", "FPF", "SFS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'F', RivalRebels.emptyrod });

		addRecipe(registry, new ItemStack(RivalRebels.supplies), new Object[] { "SRS", "APB", "SLS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'R', RivalRebels.remote, 'L', RivalRebels.loader, 'A', RivalRebels.antenna, 'B', RivalRebels.battery });

		addRecipe(registry, new ItemStack(RivalRebels.explosives), new Object[] { "SFS", "GPG", "SFS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'F', RivalRebels.flare, 'G', RivalRebels.gasgrenade });

		addRecipe(registry, new ItemStack(RivalRebels.weapons), new Object[] { "SFS", "BPB", "SFS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.ammunition, 'F', RivalRebels.battery });

		addRecipe(registry, RivalRebels.antenna.getDefaultInstance(), new Object[] { "RB", "SP", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote });

		addRecipe(registry, RivalRebels.hackm202.getDefaultInstance(), new Object[] { "HFR", " P ", "EMA", 'H', RivalRebels.hydrod, 'P', RivalRebels.pliers, 'A', RivalRebels.ammunition, 'R', RivalRebels.nuclearelement, 'M', RivalRebels.rpg, 'E', RivalRebels.explosives, 'F', RivalRebels.fuse });

		addRecipe(registry, RivalRebels.seekm202.getDefaultInstance(), new Object[] { "RRR", "BPQ", "SMS", 'S', RivalRebels.steel, 'R', RivalRebels.antenna, 'B', RivalRebels.battery, 'P', RivalRebels.pliers, 'Q', RivalRebels.remote, 'M', RivalRebels.rpg });

		// End Tier 3
		// Tier 4
		// Bunker
		addRecipe(registry, new ItemStack(RivalRebels.chip, 1), new Object[] { " RA", "TPB", "TLB", 'T', RivalRebels.breadbox, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote, 'L', RivalRebels.loader, 'A', RivalRebels.antenna });

		addRecipe(registry, new ItemStack(RivalRebels.bunker, 1), new Object[] { "CSC", "SPS", "CSC", 'C', RivalRebels.smartcamo, 'P', RivalRebels.pliers, 'S', RivalRebels.conduit });

		addRecipe(registry, new ItemStack(RivalRebels.binoculars, 1), new Object[] { " RA", "CPB", " SS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote, 'C', RivalRebels.chip, 'A', RivalRebels.antenna });

		addRecipe(registry, new ItemStack(RivalRebels.controller, 1), new Object[] { " RA", "CPB", " SB", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'B', RivalRebels.battery, 'R', RivalRebels.remote, 'C', RivalRebels.chip, 'A', RivalRebels.antenna });

		addRecipe(registry, new ItemStack(RivalRebels.bastion, 1), new Object[] { "RRR", "CPC", "RRR", 'R', RivalRebels.reactive, 'P', RivalRebels.pliers, 'C', RivalRebels.conduit });

		addRecipe(registry, new ItemStack(RivalRebels.forcefieldnode, 2), new Object[] { "RCR", "HPC", "RCR", 'R', RivalRebels.reactive, 'P', RivalRebels.pliers, 'C', RivalRebels.conduit, 'H', RivalRebels.chip });

		addRecipe(registry, new ItemStack(RivalRebels.reactive, 4), new Object[] { "CDC", "DBD", "CDC", 'B', RivalRebels.battery, 'D', RivalRebels.conduit, 'C', RivalRebels.smartcamo });

		addRecipe(registry, new ItemStack(RivalRebels.conduit, 16), new Object[] { "CEC", "EBE", "CEC", 'B', RivalRebels.redrod, 'E', RivalRebels.emptyrod, 'C', RivalRebels.steel });
		// End Tier 4

		addRecipe(registry, RivalRebels.core1.getDefaultInstance(), new Object[] { " DS", "CPD", " DS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'C', RivalRebels.chip, 'D', RivalRebels.conduit });

		addRecipe(registry, RivalRebels.core2.getDefaultInstance(), new Object[] { " DS", "CP1", " DS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'C', RivalRebels.chip, 'D', RivalRebels.conduit, '1', RivalRebels.core1 });

		addRecipe(registry, RivalRebels.core3.getDefaultInstance(), new Object[] { " DS", "CP2", " DS", 'S', RivalRebels.steel, 'P', RivalRebels.pliers, 'C', RivalRebels.chip, 'D', RivalRebels.conduit, '2', RivalRebels.core2 });

		addRecipe(registry, new ItemStack(RivalRebels.minetrap, 2), new Object[] { "SDS", "DPD", "SDS", 'S', RivalRebels.alandmine, 'P', RivalRebels.pliers, 'D', RivalRebels.conduit });

		addRecipe(registry, new ItemStack(RivalRebels.quicksandtrap, 2), new Object[] { "SDS", "DPD", "SDS", 'S', RivalRebels.aquicksand, 'P', RivalRebels.pliers, 'D', RivalRebels.conduit });

		addRecipe(registry, new ItemStack(RivalRebels.mariotrap, 2), new Object[] { "SDS", "DPD", "SDS", 'S', RivalRebels.amario, 'P', RivalRebels.pliers, 'D', RivalRebels.conduit });

		addRecipe(registry, new ItemStack(RivalRebels.ffreciever, 2), new Object[] { "LPS", "RCR", 'L', RivalRebels.binoculars, 'P', RivalRebels.pliers, 'S', RivalRebels.supplies, 'R', RivalRebels.reactive, 'C', RivalRebels.conduit });

		addRecipe(registry, new ItemStack(RivalRebels.buildrhodes, 2), new Object[] { "SAS", "CPT", "RLB", 'B', RivalRebels.binoculars, 'P', RivalRebels.pliers, 'S', RivalRebels.supplies, 'A', RivalRebels.antenna, 'C', RivalRebels.chip, 'T', RivalRebels.core3, 'R', RivalRebels.controller, 'L', RivalRebels.loader});
	}

    private static final ResourceLocation MAIN = new ResourceLocation(RivalRebels.MODID, "main_recipe_group");
    private static int id = 0;

    private static void addRecipe(IForgeRegistry<IRecipe> registry, ItemStack output, Object[] input) {
        GameRegistry.addShapedRecipe(new ResourceLocation(RivalRebels.MODID, "recipe" + (id++)), MAIN, output, input);
    }
}

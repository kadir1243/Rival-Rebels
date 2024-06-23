package assets.rivalrebels.datagen;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class RecipeDataGen extends FabricRecipeProvider {
    public RecipeDataGen(FabricDataOutput arg) {
        super(arg);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRItems.fuel, 16)
            .input('C', Items.COBBLESTONE)
            .input('S', Items.SAND)
            .input('G', Items.GRAVEL)
            .input('D', Items.DIRT)
            .pattern("DS")
            .pattern("GC")
            .showNotification(false)
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, RRItems.rocket, 16)
            .input('C', Items.COBBLESTONE)
            .input('S', Items.SAND)
            .input('G', Items.GRAVEL)
            .input('D', Items.DIRT)
            .pattern("SC")
            .pattern("DG")
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRItems.battery, 4)
            .input('C', Items.COBBLESTONE)
            .input('S', Items.SAND)
            .input('G', Items.GRAVEL)
            .input('D', ItemTags.DIRT)
            .pattern("CG")
            .pattern("SD")
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRItems.gasgrenade, 6)
            .input('C', Items.COBBLESTONE)
            .input('S', Items.SAND)
            .input('G', Items.GRAVEL)
            .input('D', ItemTags.DIRT)
            .pattern("GD")
            .pattern("CS")
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRBlocks.easteregg)
            .input('C', Items.COBBLESTONE)
            .input('D', ItemTags.DIRT)
            .pattern("DD")
            .pattern("CC")
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRBlocks.sigmaarmor)
            .input('C', Items.COBBLESTONE)
            .input('S', RRBlocks.steel)
            .input('P', RRItems.pliers)
            .input('D', ItemTags.DIRT)
            .input('B', Items.BONE)
            .pattern("SSC")
            .pattern("SPB")
            .pattern("SSD")
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRBlocks.omegaarmor)
            .input('C', Items.COBBLESTONE)
            .input('S', RRBlocks.steel)
            .input('P', RRItems.pliers)
            .input('D', ItemTags.DIRT)
            .input('B', Items.BONE)
            .pattern("SSD")
            .pattern("SPB")
            .pattern("SSC")
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRBlocks.flagbox1)
            .input('W', ItemTags.WOOL)
            .input('P', RRItems.pliers)
            .pattern("WP")
            .criterion("has_wool", conditionsFromTag(ItemTags.WOOL))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRBlocks.flagbox3)
            .input('W', ItemTags.WOOL)
            .input('P', RRItems.pliers)
            .pattern("PW")
            .criterion("has_wool", conditionsFromTag(ItemTags.WOOL))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RRItems.armyshovel)
            .input('C', Items.COBBLESTONE)
            .input('S', Items.SAND)
            .input('G', Items.GRAVEL)
            .input('D', ItemTags.DIRT)
            .pattern("CD")
            .pattern("SG")
            .criterion("has_cobblestone", conditionsFromItem(Items.COBBLESTONE))
            .offerTo(exporter);

        // Supplies
        addRecipe(RRBlocks.amario, 16, "SS", "GG", 'S', Items.SAND, 'G', Items.GRAVEL);

        addRecipe(RRBlocks.aquicksand, 16, "SS", "DD", 'S', Items.SAND, 'D', BlockTags.DIRT);

        addRecipe(RRBlocks.jump, 8, "CC", "DD", 'C', Items.COBBLESTONE, 'D', BlockTags.DIRT);

        addRecipe(RRBlocks.steel, 16, "CC", "CC", 'C', Items.COBBLESTONE);

        addRecipe(RRBlocks.smartcamo, 16, "SCS", "CPC", "SCS", 'C', Items.COBBLESTONE, 'S', RRBlocks.steel, 'P', RRItems.pliers);

        // Explosives
        addRecipee(RRItems.pliers, " C", "C ", 'C', Items.COBBLESTONE);

        addRecipee(RRItems.roda, "IN", "IH", "IR", 'I', Items.IRON_INGOT, 'N', RRItems.nuclearelement, 'H', RRItems.hydrod, 'R', RRItems.redrod);

        // Weapons
        addRecipe(RRItems.knife, 5, "CG", "GC", 'C', Items.COBBLESTONE, 'G', Items.GRAVEL);

        // Miscellaneous
        addRecipe(RRItems.trollmask, 8, "SS", 'S', Items.SAND);

        addRecipee(RRBlocks.cycle, "TT", "TT", 'T', RRItems.trollmask);

        // End Tier 1
        // Tier 2
        // AutoBuilds
        addRecipe(RRBlocks.tower, 2, "SS", "SJ", 'S', RRBlocks.steel, 'J', RRBlocks.jump);

        addRecipee(RRBlocks.barricade, "SSS", "LPL", "RCR", 'L', RRItems.binoculars, 'P', RRItems.pliers, 'S', RRBlocks.supplies, 'R', RRBlocks.reactive, 'C', RRBlocks.conduit);

        addRecipee(RRBlocks.timedbomb, "FB", "RP", 'P', RRItems.pliers, 'B', RRItems.battery, 'R', RRItems.rocket, 'F', RRItems.fuel);

        addRecipe(RRBlocks.remotecharge, 4, "GB", "RF", 'G', RRItems.gasgrenade, 'B', RRItems.battery, 'R', RRItems.rocket, 'F', RRItems.fuel);

        addRecipee(RRItems.remote, "BF", "RP", 'P', RRItems.pliers, 'B', RRItems.battery, 'R', RRItems.rocket, 'F', RRItems.fuel);

        addRecipe(RRBlocks.alandmine, 16, "DB", "RP", 'D', BlockTags.DIRT, 'B', RRItems.battery, 'P', RRItems.pliers, 'R', RRItems.rocket);

        addRecipe(RRBlocks.flare, 8, "FD", "RD", 'F', RRItems.fuel, 'D', BlockTags.DIRT, 'R', RRItems.rocket);

        addRecipee(RRBlocks.nukeCrateTop, " SS", "SP ", " SS", 'S', RRBlocks.steel, 'P', RRItems.pliers);

        addRecipee(RRBlocks.nukeCrateBottom, "SS ", "SP ", "SS ", 'S', RRBlocks.steel, 'P', RRItems.pliers);

        addRecipee(RRItems.fuse, "BP", "SP", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'B', RRItems.battery);

        // Weapons
        addRecipee(RRItems.rpg, "RRR", "BP ", "SSS", 'S', RRBlocks.steel, 'R', RRItems.rocket, 'B', RRItems.battery, 'P', RRItems.pliers);

        addRecipee(RRItems.flamethrower, "FFF", "BP ", "SSS", 'S', RRBlocks.steel, 'F', RRItems.fuel, 'B', RRItems.battery, 'P', RRItems.pliers);

        addRecipee(RRItems.tesla, "BBB", "BP ", "SSS", 'B', RRItems.battery, 'S', RRBlocks.steel, 'P', RRItems.pliers);

        addRecipee(RRItems.plasmacannon, "HHH", "BP ", "SSS", 'S', RRBlocks.steel, 'H', RRItems.hydrod, 'B', RRItems.battery, 'P', RRItems.pliers);

        addRecipee(RRItems.einsten, "HHH", "BP ", "SSS", 'S', RRBlocks.steel, 'H', RRItems.redrod, 'B', RRItems.battery, 'P', RRItems.pliers);

        addRecipee(RRItems.roddisk, "HPB", 'H', RRItems.hydrod, 'B', RRItems.battery, 'P', RRItems.pliers);

        addRecipee(RRItems.hydrod, "W", "S", 'S', RRItems.emptyrod, 'W', Items.WATER_BUCKET);

        addRecipee(RRItems.redrod, "W", "S", 'S', RRItems.emptyrod, 'W', Items.REDSTONE);

        addRecipe(RRItems.emptyrod, 4, "SPS", 'S', RRBlocks.steel, 'P', RRItems.pliers);

        addRecipe(RRItems.expill, 4, "STG", " BR", "F W", 'S', Items.WHEAT_SEEDS, 'T', RRItems.trollmask, 'G', RRItems.gasgrenade, 'B', Items.MILK_BUCKET, 'R', RRItems.rocket, 'F', RRItems.fuel, 'W', Items.WATER_BUCKET);

        addRecipe(RRItems.safepill, 4, "S  ", " B ", "F W", 'S', Items.WHEAT_SEEDS, 'B', Items.MILK_BUCKET, 'F', RRItems.fuel, 'W', Items.WATER_BUCKET);

        addRecipee(RRBlocks.breadbox, " S", "GP", " B", 'G', Items.WHEAT_SEEDS, 'S', RRBlocks.steel, 'B', Items.WATER_BUCKET, 'P', RRItems.pliers);

        addRecipee(RRBlocks.loader, "SBS", "SPS", "SBS", 'S', RRBlocks.steel, 'B', RRItems.battery, 'P', RRItems.pliers);

        // End Tier 2
        // Tier 3
        // Crates
        addRecipee(RRBlocks.ammunition, "SFS", "FPF", "SFS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'F', RRItems.emptyrod);

        addRecipee(RRBlocks.supplies, "SRS", "APB", "SLS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'R', RRItems.remote, 'L', RRBlocks.loader, 'A', RRItems.antenna, 'B', RRItems.battery);

        addRecipee(RRBlocks.explosives, "SFS", "GPG", "SFS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'F', RRBlocks.flare, 'G', RRItems.gasgrenade);

        addRecipee(RRBlocks.weapons, "SFS", "BPB", "SFS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'B', RRBlocks.ammunition, 'F', RRItems.battery);

        addRecipee(RRItems.antenna, "RB", "SP", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'B', RRItems.battery, 'R', RRItems.remote);

        addRecipee(RRItems.hackm202, "HFR", " P ", "EMA", 'H', RRItems.hydrod, 'P', RRItems.pliers, 'A', RRBlocks.ammunition, 'R', RRItems.nuclearelement, 'M', RRItems.rpg, 'E', RRBlocks.explosives, 'F', RRItems.fuse);

        addRecipee(RRItems.seekm202, "RRR", "BPQ", "SMS", 'S', RRBlocks.steel, 'R', RRItems.antenna, 'B', RRItems.battery, 'P', RRItems.pliers, 'Q', RRItems.remote, 'M', RRItems.rpg);

        // End Tier 3
        // Tier 4
        // Bunker
        addRecipee(RRItems.chip, " RA", "TPB", "TLB", 'T', RRBlocks.breadbox, 'P', RRItems.pliers, 'B', RRItems.battery, 'R', RRItems.remote, 'L', RRBlocks.loader, 'A', RRItems.antenna);

        addRecipee(RRBlocks.bunker, "CSC", "SPS", "CSC", 'C', RRBlocks.smartcamo, 'P', RRItems.pliers, 'S', RRBlocks.conduit);

        addRecipee(RRItems.binoculars, " RA", "CPB", " SS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'B', RRItems.battery, 'R', RRItems.remote, 'C', RRItems.chip, 'A', RRItems.antenna);

        addRecipee(RRBlocks.controller, " RA", "CPB", " SB", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'B', RRItems.battery, 'R', RRItems.remote, 'C', RRItems.chip, 'A', RRItems.antenna);

        addRecipee(RRBlocks.bastion, "RRR", "CPC", "RRR", 'R', RRBlocks.reactive, 'P', RRItems.pliers, 'C', RRBlocks.conduit);

        addRecipe(RRBlocks.forcefieldnode, 2, "RCR", "HPC", "RCR", 'R', RRBlocks.reactive, 'P', RRItems.pliers, 'C', RRBlocks.conduit, 'H', RRItems.chip);

        addRecipe(RRBlocks.reactive, 4, "CDC", "DBD", "CDC", 'B', RRItems.battery, 'D', RRBlocks.conduit, 'C', RRBlocks.smartcamo);

        addRecipe(RRBlocks.conduit, 16, "CEC", "EBE", "CEC", 'B', RRItems.redrod, 'E', RRItems.emptyrod, 'C', RRBlocks.steel);
        // End Tier 4

        addRecipee(RRItems.core1, " DS", "CPD", " DS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'C', RRItems.chip, 'D', RRBlocks.conduit);

        addRecipee(RRItems.core2, " DS", "CP1", " DS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'C', RRItems.chip, 'D', RRBlocks.conduit, '1', RRItems.core1);

        addRecipee(RRItems.core3, " DS", "CP2", " DS", 'S', RRBlocks.steel, 'P', RRItems.pliers, 'C', RRItems.chip, 'D', RRBlocks.conduit, '2', RRItems.core2);

        addRecipe(RRBlocks.minetrap, 2, "SDS", "DPD", "SDS", 'S', RRBlocks.alandmine, 'P', RRItems.pliers, 'D', RRBlocks.conduit);

        addRecipe(RRBlocks.quicksandtrap, 2, "SDS", "DPD", "SDS", 'S', RRBlocks.aquicksand, 'P', RRItems.pliers, 'D', RRBlocks.conduit);

        addRecipe(RRBlocks.mariotrap, 2, "SDS", "DPD", "SDS", 'S', RRBlocks.amario, 'P', RRItems.pliers, 'D', RRBlocks.conduit);

        addRecipe(RRBlocks.ffreciever, 2, "LPS", "RCR", 'L', RRItems.binoculars, 'P', RRItems.pliers, 'S', RRBlocks.supplies, 'R', RRBlocks.reactive, 'C', RRBlocks.conduit);

        addRecipe(RRBlocks.buildrhodes, 2, "SAS", "CPT", "RLB", 'B', RRItems.binoculars, 'P', RRItems.pliers, 'S', RRBlocks.supplies, 'A', RRItems.antenna, 'C', RRItems.chip, 'T', RRItems.core3, 'R', RRBlocks.controller, 'L', RRBlocks.loader);
    }

    private static void addRecipee(ItemConvertible output, Object... input) {
    }

    private static void addRecipe(ItemConvertible output, int count, Object... input) {
    }

    @Override
    public String getName() {
        return "Rival Rebels recipes";
    }
}

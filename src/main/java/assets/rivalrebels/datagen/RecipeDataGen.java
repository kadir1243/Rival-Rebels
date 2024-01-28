package assets.rivalrebels.datagen;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraftforge.common.Tags;

import java.util.LinkedList;
import java.util.function.Consumer;

public class RecipeDataGen extends RecipeProvider {
    public RecipeDataGen(DataGenerator arg) {
        super(arg);
    }

    @Override
    protected void generate(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RRItems.fuel, 16)
            .input('C', Tags.Items.COBBLESTONE)
            .input('S', Tags.Items.SAND)
            .input('G', Tags.Items.GRAVEL)
            .input('D', Items.DIRT)
            .pattern("DS")
            .pattern("GC")
            .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RRItems.rocket, 16)
            .input('C', Tags.Items.COBBLESTONE)
            .input('S', Tags.Items.SAND)
            .input('G', Tags.Items.GRAVEL)
            .input('D', Items.DIRT)
            .pattern("SC")
            .pattern("DG")
            .offerTo(consumer);

        providerConsumer = consumer;

        addRecipe(RRItems.battery, 4, "CG", "SD", 'C', Tags.Items.COBBLESTONE, 'S', Tags.Items.SAND, 'G', Tags.Items.GRAVEL, 'D', BlockTags.DIRT);

        addRecipe(RRItems.gasgrenade, 6, "GD", "CS", 'C', Tags.Items.COBBLESTONE, 'S', Tags.Items.SAND, 'G', Tags.Items.GRAVEL, 'D', BlockTags.DIRT);

        // EasterEgg
        addRecipee(RRBlocks.easteregg, "DD", "CC", 'C', Tags.Items.COBBLESTONE, 'D', BlockTags.DIRT);

        // Armor
        addRecipee(RRBlocks.sigmaarmor, "SSC", "SPB", "SSD", 'C', Tags.Items.COBBLESTONE, 'S', RRBlocks.steel, 'P', RRItems.pliers, 'D', BlockTags.DIRT, 'B', Tags.Items.BONES);

        addRecipee(RRBlocks.omegaarmor, "SSD", "SPB", "SSC", 'C', Tags.Items.COBBLESTONE, 'S', RRBlocks.steel, 'P', RRItems.pliers, 'D', BlockTags.DIRT, 'B', Tags.Items.BONES);

        // Flagbox
        addRecipee(RRBlocks.flagbox1, "W", "P", 'W', ItemTags.WOOL, 'P', RRItems.pliers);

        addRecipee(RRBlocks.flagbox3, "P", "W", 'W', ItemTags.WOOL, 'P', RRItems.pliers);

        // Supplies
        addRecipee(RRItems.armyshovel, "CD", "SG", 'C', Tags.Items.COBBLESTONE, 'S', Tags.Items.SAND, 'G', Tags.Items.GRAVEL, 'D', BlockTags.DIRT);

        addRecipe(RRBlocks.amario, 16, "SS", "GG", 'S', Tags.Items.SAND, 'G', Tags.Items.GRAVEL);

        addRecipe(RRBlocks.aquicksand, 16, "SS", "DD", 'S', Tags.Items.SAND, 'D', BlockTags.DIRT);

        addRecipe(RRBlocks.jump, 8, "CC", "DD", 'C', Tags.Items.COBBLESTONE, 'D', BlockTags.DIRT);

        addRecipe(RRBlocks.steel, 16, "CC", "CC", 'C', Tags.Items.COBBLESTONE);

        addRecipe(RRBlocks.smartcamo, 16, "SCS", "CPC", "SCS", 'C', Tags.Items.COBBLESTONE, 'S', RRBlocks.steel, 'P', RRItems.pliers);

        // Explosives
        addRecipee(RRItems.pliers, " C", "C ", 'C', Tags.Items.COBBLESTONE);

        addRecipee(RRItems.roda, "IN", "IH", "IR", 'I', Tags.Items.INGOTS_IRON, 'N', RRItems.nuclearelement, 'H', RRItems.hydrod, 'R', RRItems.redrod);

        // Weapons
        addRecipe(RRItems.knife, 5, "CG", "GC", 'C', Tags.Items.COBBLESTONE, 'G', Tags.Items.GRAVEL);

        // Miscellaneous
        addRecipe(RRItems.trollmask, 8, "SS", 'S', Tags.Items.SAND);

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

        addRecipee(RRItems.redrod, "W", "S", 'S', RRItems.emptyrod, 'W', Tags.Items.DUSTS_REDSTONE);

        addRecipe(RRItems.emptyrod, 4, "SPS", 'S', RRBlocks.steel, 'P', RRItems.pliers);

        addRecipe(RRItems.expill, 4, "STG", " BR", "F W", 'S', Tags.Items.SEEDS, 'T', RRItems.trollmask, 'G', RRItems.gasgrenade, 'B', Items.MILK_BUCKET, 'R', RRItems.rocket, 'F', RRItems.fuel, 'W', Items.WATER_BUCKET);

        addRecipe(RRItems.safepill, 4, "S  ", " B ", "F W", 'S', Tags.Items.SEEDS, 'B', Items.MILK_BUCKET, 'F', RRItems.fuel, 'W', Items.WATER_BUCKET);

        addRecipee(RRBlocks.breadbox, " S", "GP", " B", 'G', Tags.Items.SEEDS, 'S', RRBlocks.steel, 'B', Items.WATER_BUCKET, 'P', RRItems.pliers);

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

    private static Consumer<RecipeJsonProvider> providerConsumer;

    private static void addRecipee(ItemConvertible output, Object... input) {
        addRecipe(output, 1, input);
    }

    @SuppressWarnings("unchecked")
    private static void addRecipe(ItemConvertible output, int count, Object... input) {
        LinkedList<String> pattern = new LinkedList<>();
        Char2ObjectMap<Ingredient> inputs = new Char2ObjectOpenHashMap<>();
        for (int i = 0; i < input.length; i++) {
            Object o = input[i];
            if (o instanceof String) {
                pattern.add((String) o);
            } else if (o instanceof Character) {
                Object o1 = input[i + 1];
                Ingredient ingredient = Ingredient.EMPTY;
                if (o1 instanceof ItemConvertible) {
                    ingredient = Ingredient.ofItems((ItemConvertible) o1);
                } else if (o1 instanceof TagKey<?>) {
                    ingredient = Ingredient.fromTag((TagKey<Item>) o1);
                }

                inputs.put((char) o, ingredient);
            }
        }
        ShapedRecipeJsonBuilder factory = ShapedRecipeJsonBuilder.create(output, count);

        for (String s : pattern) {
            factory.pattern(s);
        }

        for (Char2ObjectMap.Entry<Ingredient> entry : inputs.char2ObjectEntrySet()) {
            factory.input(entry.getCharKey(), entry.getValue());
        }

        factory.offerTo(providerConsumer);
    }
}

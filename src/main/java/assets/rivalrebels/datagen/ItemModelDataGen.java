package assets.rivalrebels.datagen;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ItemModelDataGen extends ItemModelProvider {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, RivalRebels.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(RRItems.trollmask, "bf");
        simpleItem(RRItems.safepill, "ak");
        simpleItem(RRItems.redrod, "al");
        simpleItem(RRItems.nuclearelement, "av");
        simpleItem(RRItems.hydrod, "au");
        simpleItem(RRItems.emptyrod, "at");
        simpleItem(RRItems.rocket, "ar");
        simpleItem(RRItems.remote, "am");
        simpleItem(RRItems.pliers, "ap");
        simpleItem(RRItems.fuse, "ag");
        simpleItem(RRItems.fuel, "af");
        simpleItem(RRItems.expill, "ai");
        simpleItem(RRItems.core2, "az");
        simpleItem(RRItems.core3, "ba");
        simpleItem(RRItems.core1, "ay");
        simpleItem(RRItems.chip, "bd");
        simpleItem(RRItems.battery, "ac");
        simpleItem(RRItems.antenna, "aa");
    }

    private void simpleItem(Item item, String tex) {
        singleTexture(name(item), mcLoc("generated"), modLoc(tex));
    }

    private String name(ForgeRegistryEntry<?> registry) {
        Identifier registryName = registry.getRegistryName();
        if (registryName == null) {
            throw new UnsupportedOperationException("Registry Name is not available");
        }
        return registryName.getNamespace();
    }
}

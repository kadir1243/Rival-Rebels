package assets.rivalrebels.datagen;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.item.Item;

public class ItemModelDataGen extends FabricModelProvider {
    private ItemModelGenerators generator;

    public ItemModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        this.generator = generator;
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
        simpleItem(RRItems.camohat, "oh");
        simpleItem(RRItems.camohat2, "sh");
        simpleItem(RRItems.camoshirt, "ov");
        simpleItem(RRItems.camoshirt2, "sv");
        simpleItem(RRItems.camopants, "op");
        simpleItem(RRItems.camopants2, "sp");
        simpleItem(RRItems.camoshoes, "ob");
        simpleItem(RRItems.camoshoes2, "sb");
        simpleItem(RRItems.armyshovel, "aw");
        simpleItem(RRItems.einsten, "ab");
        simpleItem(RRItems.binoculars, "bb");
        simpleItem(RRItems.camera, "bi");
        simpleItem(RRItems.knife, "ad");
        simpleItem(RRItems.flamethrower, "ae");
        simpleItem(RRItems.gasgrenade, "ah");
        simpleItem(RRItems.hackm202, "bg");
        simpleItem(RRItems.plasmacannon, "ao");
        simpleItem(RRItems.roda, "be");
        simpleItem(RRItems.roddisk, "as");
        simpleItem(RRItems.rpg, "aq");
        simpleItem(RRItems.seekm202, "bh");
        simpleItem(RRItems.tesla, "ax");
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    private void simpleItem(Item item, String tex) {
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(RRIdentifiers.create("item/" + tex)), generator.output);
    }

    @Override
    public String getName() {
        return "RivalRebels Item Models";
    }
}

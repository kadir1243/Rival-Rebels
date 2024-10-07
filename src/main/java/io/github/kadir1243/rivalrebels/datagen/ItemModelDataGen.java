package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelDataGen extends ItemModelProvider {
    public ItemModelDataGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RRIdentifiers.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(RRItems.trollmask, "bf");
        simpleItem(RRItems.safepill, "ak");
        //simpleItem(RRItems.redrod, "al");
        //simpleItem(RRItems.NUCLEAR_ROD, "av");
        //simpleItem(RRItems.hydrod, "au");
        //simpleItem(RRItems.emptyrod, "at");
        //simpleItem(RRItems.rocket, "ar");
        simpleItem(RRItems.remote, "am");
        simpleItem(RRItems.pliers, "ap");
        simpleItem(RRItems.fuse, "ag");
        simpleItem(RRItems.fuel, "af");
        simpleItem(RRItems.expill, "ai");
        simpleItem(RRItems.core2, "az");
        simpleItem(RRItems.core3, "ba");
        simpleItem(RRItems.core1, "ay");
        simpleItem(RRItems.chip, "bd");
        //simpleItem(RRItems.battery, "ac");
        simpleItem(RRItems.antenna, "aa");
        simpleItem(RRItems.armyshovel, "aw");
        //simpleItem(RRItems.einsten, "ab");
        //simpleItem(RRItems.binoculars, "bb");
        simpleItem(RRItems.camera, "bi");
        simpleItem(RRItems.knife, "ad");
        //simpleItem(RRItems.flamethrower, "ae");
        //simpleItem(RRItems.gasgrenade, "ah");
        //simpleItem(RRItems.hackm202, "bg");
        //simpleItem(RRItems.plasmacannon, "ao");
        //simpleItem(RRItems.roda, "be");
        //simpleItem(RRItems.roddisk, "as");
        //simpleItem(RRItems.rpg, "aq");
        //simpleItem(RRItems.seekm202, "bh");
        //simpleItem(RRItems.tesla, "ax");
        customModel(RRItems.battery);
        customModel(RRItems.einsten);
        customModel(RRItems.redrod);
        customModel(RRItems.hydrod);
        customModel(RRItems.NUCLEAR_ROD);
        customModel(RRItems.binoculars);
        customModel(RRItems.emptyrod);
        customModel(RRItems.gasgrenade);
        customModel(RRItems.rocket);
        customModel(RRItems.tesla);
        customModel(RRItems.hackm202);
        customModel(RRItems.plasmacannon);
        customModel(RRItems.rpg);
        customModel(RRItems.roda);
        customModel(RRItems.roddisk);
        customModel(RRItems.seekm202);
        customModel(RRItems.flamethrower);

        simpleItem(RRItems.camohat, "oh");
        simpleItem(RRItems.camoshirt, "ov");
        simpleItem(RRItems.camopants, "op");
        simpleItem(RRItems.camoshoes, "ob");

        simpleItem(RRItems.camohat2, "sh");
        simpleItem(RRItems.camoshirt2, "sv");
        simpleItem(RRItems.camopants2, "sp");
        simpleItem(RRItems.camoshoes2, "sb");

        simpleItem(RRItems.orebelhelmet, "roh");
        simpleItem(RRItems.orebelchest, "roc");
        simpleItem(RRItems.orebelpants, "rop");
        simpleItem(RRItems.orebelboots, "rob");

        simpleItem(RRItems.onukerhelmet, "noh");
        simpleItem(RRItems.onukerchest, "noc");
        simpleItem(RRItems.onukerpants, "nop");
        simpleItem(RRItems.onukerboots, "nob");

        simpleItem(RRItems.ointelhelmet, "ioh");
        simpleItem(RRItems.ointelchest, "ioc");
        simpleItem(RRItems.ointelpants, "iop");
        simpleItem(RRItems.ointelboots, "iob");

        simpleItem(RRItems.ohackerhelmet, "hoh");
        simpleItem(RRItems.ohackerchest, "hoc");
        simpleItem(RRItems.ohackerpants, "hop");
        simpleItem(RRItems.ohackerboots, "hob");

        simpleItem(RRItems.srebelhelmet, "rsh");
        simpleItem(RRItems.srebelchest, "rsc");
        simpleItem(RRItems.srebelpants, "rsp");
        simpleItem(RRItems.srebelboots, "rsb");

        simpleItem(RRItems.snukerhelmet, "nsh");
        simpleItem(RRItems.snukerchest, "nsc");
        simpleItem(RRItems.snukerpants, "nsp");
        simpleItem(RRItems.snukerboots, "nsb");

        simpleItem(RRItems.sintelhelmet, "ish");
        simpleItem(RRItems.sintelchest, "isc");
        simpleItem(RRItems.sintelpants, "isp");
        simpleItem(RRItems.sintelboots, "isb");

        simpleItem(RRItems.shackerhelmet, "hsh");
        simpleItem(RRItems.shackerchest, "hsc");
        simpleItem(RRItems.shackerpants, "hsp");
        simpleItem(RRItems.shackerboots, "hsb");
    }

    private void simpleItem(Holder<?> item, String tex) {
        getBuilder(item.getKey().location().getPath())
            .parent(new ModelFile.UncheckedModelFile("item/generated"))
            .texture("layer0", modLoc("item/" + tex));
    }

    private void customModel(Holder<?> item) {
        this.getBuilder(item.getKey().location().getPath())
            .parent(new ModelFile.UncheckedModelFile("item/generated"));
    }
}

package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

public class EquipmentAssetGen extends EquipmentAssetProvider {
    public EquipmentAssetGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(RRItems.orebelarmor_ASSET_KEY, omegaArmor("rebel_omega"));
        output.accept(RRItems.srebelarmor_ASSET_KEY, sigmaArmor("rebel_sigma"));
        output.accept(RRItems.onukerarmor_ASSET_KEY, omegaArmor("nuker_omega"));
        output.accept(RRItems.snukerarmor_ASSET_KEY, sigmaArmor("nuker_sigma"));
        output.accept(RRItems.ointelarmor_ASSET_KEY, omegaArmor("intel_omega"));
        output.accept(RRItems.sintelarmor_ASSET_KEY, sigmaArmor("intel_sigma"));
        output.accept(RRItems.ohackerarmor_ASSET_KEY, omegaArmor("hacker_omega"));
        output.accept(RRItems.shackerarmor_ASSET_KEY, sigmaArmor("hacker_sigma"));
    }

    public static EquipmentClientInfo omegaArmor(String id) {
        return EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS, EquipmentClientInfo.Layer.leatherDyeable(RRIdentifiers.create("omega_pants"), false)).addMainHumanoidLayer(RRIdentifiers.create(id), false).build();
    }

    public static EquipmentClientInfo sigmaArmor(String id) {
        return EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS, EquipmentClientInfo.Layer.leatherDyeable(RRIdentifiers.create("sigma_pants"), false)).addMainHumanoidLayer(RRIdentifiers.create(id), false).build();
    }

    @Override
    public String getName() {
        return RRIdentifiers.MODID + " Equipment Model Generator";
    }
}

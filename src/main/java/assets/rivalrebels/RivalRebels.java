package assets.rivalrebels;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.command.*;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.entity.RREntities;
import assets.rivalrebels.common.entity.RhodesType;
import assets.rivalrebels.common.entity.RhodesTypes;
import assets.rivalrebels.common.entity.brain.Activities;
import assets.rivalrebels.common.entity.brain.MemoryModuleTypes;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsRound;
import assets.rivalrebels.common.tileentity.RRTileEntities;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class RivalRebels implements ModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ResourceKey<Registry<RhodesType>> RHODES_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(RRIdentifiers.create("rhodes_type"));
    public static final Registry<RhodesType> RHODES_TYPE_REGISTRY = FabricRegistryBuilder.createDefaulted(RHODES_TYPE_REGISTRY_KEY, RRIdentifiers.create("rhodes")).attribute(RegistryAttribute.MODDED).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    public static final Codec<RhodesType> RHODES_TYPE_CODEC = RHODES_TYPE_REGISTRY.byNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, RhodesType> RHODES_TYPE_STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(RHODES_TYPE_CODEC);

    public static RivalRebelsRound round;

    @Override
    public void onInitialize() {
        RhodesTypes.init();
        PacketDispatcher.registerPackets();

        ForgeConfigRegistry.INSTANCE.register(RRIdentifiers.MODID, ModConfig.Type.COMMON, RRConfig.COMMON_SPEC);
        ForgeConfigRegistry.INSTANCE.register(RRIdentifiers.MODID, ModConfig.Type.CLIENT, RRConfig.CLIENT_SPEC);
        ForgeConfigRegistry.INSTANCE.register(RRIdentifiers.MODID, ModConfig.Type.SERVER, RRConfig.SERVER_SPEC);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            round.setRoundDistances(RRConfig.SERVER.getSpawnDomeRadius(), RRConfig.SERVER.getTeleportDistance(), RRConfig.SERVER.getObjectiveDistance());
        });
        round = new RivalRebelsRound();
        round.init();

        RRSounds.init();
        RivalRebelsGuiHandler.init();
        RRTileEntities.register();
        RivalRebelsDamageSource.RRDamageTypes.init();
        registerCommand();
        RRBlocks.init();
        RRComponents.init();
        RRItems.init();
        Activities.init();
        MemoryModuleTypes.init();
        RREntities.TYPES.forEach((name, type) -> Registry.register(BuiltInRegistries.ENTITY_TYPE, RRIdentifiers.create(name), type));
        FabricDefaultAttributeRegistry.register(RREntities.RHODES, EntityRhodes.createAttributes());
    }

    private void registerCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            CommandResetGame.register(dispatcher);
            CommandPassword.register(dispatcher);
            CommandStopRounds.register(dispatcher);
            CommandContinueRound.register(dispatcher);
            CommandMotD.register(dispatcher);
            CommandRobot.register(dispatcher, registryAccess);
            CommandHotPotato.register(dispatcher);
        });
    }

    public static List<Block> getBlocks(TagKey<Block> tagKey) {
        return BuiltInRegistries.BLOCK.asTagAddingLookup().get(tagKey).map(HolderSet.ListBacked::stream).map(s -> s.map(Holder::value).toList()).orElse(Collections.emptyList());
    }

}

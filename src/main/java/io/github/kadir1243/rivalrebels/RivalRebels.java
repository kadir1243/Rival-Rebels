package io.github.kadir1243.rivalrebels;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.command.*;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsGuiHandler;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.RREntities;
import io.github.kadir1243.rivalrebels.common.entity.RhodesType;
import io.github.kadir1243.rivalrebels.common.entity.RhodesTypes;
import io.github.kadir1243.rivalrebels.common.entity.brain.Activities;
import io.github.kadir1243.rivalrebels.common.entity.brain.MemoryModuleTypes;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.packet.PacketDispatcher;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsRound;
import io.github.kadir1243.rivalrebels.common.tileentity.RRTileEntities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import io.github.kadir1243.rivalrebels.datagen.DataGen;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

@Mod(RRIdentifiers.MODID)
public class RivalRebels {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<EntityDataSerializer<?>> DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, RRIdentifiers.MODID);
    private static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPE_INFOS = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, RRIdentifiers.MODID);
    public static final ResourceKey<Registry<RhodesType>> RHODES_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(RRIdentifiers.create("rhodes_type"));
    public static final Registry<RhodesType> RHODES_TYPE_REGISTRY = new RegistryBuilder<>(RHODES_TYPE_REGISTRY_KEY).defaultKey(RRIdentifiers.create("rhodes")).sync(true).create();

    public static RivalRebelsRound round;

    public RivalRebels(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        modContainer.registerConfig(ModConfig.Type.COMMON, RRConfig.COMMON_SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, RRConfig.CLIENT_SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, RRConfig.SERVER_SPEC);

        NeoForge.EVENT_BUS.addListener(RivalRebels::registerCommand);
        RRComponents.init(modEventBus);
        RhodesTypes.init(modEventBus);
        PacketDispatcher.init(modEventBus);

        NeoForge.EVENT_BUS.addListener(RivalRebels::serverStarted);
        round = new RivalRebelsRound();
        round.init(modEventBus, dist);

        RRSounds.init(modEventBus);
        RivalRebelsGuiHandler.init(modEventBus, dist);
        RivalRebelsDamageSource.RRDamageTypes.init();
        RRItems.init(modEventBus);
        RRBlocks.init(modEventBus, dist);
        RRTileEntities.init(modEventBus);
        Activities.init(modEventBus);
        MemoryModuleTypes.init(modEventBus);
        RREntities.init(modEventBus);

        DATA_SERIALIZERS.register(modEventBus);
        ARGUMENT_TYPE_INFOS.register(modEventBus);

        if (dist.isClient()) {
            RRClient.init(modEventBus);
        }

        modEventBus.addListener(NewRegistryEvent.class, event -> event.register(RHODES_TYPE_REGISTRY));
        modEventBus.addListener(DataGen::onGatherData);
    }

    private static void serverStarted(ServerStartedEvent event) {
        round.setRoundDistances(RRConfig.SERVER.getSpawnDomeRadius(), RRConfig.SERVER.getTeleportDistance(), RRConfig.SERVER.getObjectiveDistance());
    }

    private static void registerCommand(RegisterCommandsEvent event) {
        CommandBuildContext context = event.getBuildContext();
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        CommandResetGame.register(dispatcher);
        CommandPassword.register(dispatcher);
        CommandStopRounds.register(dispatcher);
        CommandContinueRound.register(dispatcher);
        CommandMotD.register(dispatcher);
        CommandRobot.register(dispatcher, context);
        CommandHotPotato.register(dispatcher);
    }

    public static List<Block> getBlocks(TagKey<Block> tagKey) {
        return BuiltInRegistries.BLOCK.asTagAddingLookup().get(tagKey).map(HolderSet.ListBacked::stream).map(s -> s.map(Holder::value).toList()).orElse(Collections.emptyList());
    }

    static {
        ARGUMENT_TYPE_INFOS.register("rhodes_type", () -> ArgumentTypeInfos.registerByClass(CommandRobot.RhodesTypeArgumentType.class, SingletonArgumentInfo.contextAware(CommandRobot.RhodesTypeArgumentType::argumentType)));
    }
}

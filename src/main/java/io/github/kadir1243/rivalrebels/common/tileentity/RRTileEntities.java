package io.github.kadir1243.rivalrebels.common.tileentity;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RRTileEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RRIdentifiers.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityAntimatterBomb>> ANTIMATTER_BOMB = BLOCK_ENTITY_TYPES.register("antimatter_bomb", () -> new BlockEntityType<>(TileEntityAntimatterBomb::new, RRBlocks.antimatterbombblock.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityForceFieldNode>> FORCE_FIELD_NODE = BLOCK_ENTITY_TYPES.register("force_field_node", () -> new BlockEntityType<>(TileEntityForceFieldNode::new, RRBlocks.forcefieldnode.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityGore>> GORE = BLOCK_ENTITY_TYPES.register("gore", () -> new BlockEntityType<>(TileEntityGore::new, RRBlocks.goreblock.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityJumpBlock>> JUMP_BLOCK = BLOCK_ENTITY_TYPES.register("jump_block", () -> new BlockEntityType<>(TileEntityJumpBlock::new, RRBlocks.jump.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityLaptop>> LAPTOP = BLOCK_ENTITY_TYPES.register("laptop", () -> new BlockEntityType<>(TileEntityLaptop::new, RRBlocks.controller.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityLoader>> LOADER = BLOCK_ENTITY_TYPES.register("loader", () -> new BlockEntityType<>(TileEntityLoader::new, RRBlocks.loader.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityMeltDown>> MELT_DOWN = BLOCK_ENTITY_TYPES.register("meltdown", () -> new BlockEntityType<>(TileEntityMeltDown::new, RRBlocks.meltdown.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityNuclearBomb>> NUCLEAR_BOMB = BLOCK_ENTITY_TYPES.register("nuclear_bomb", () -> new BlockEntityType<>(TileEntityNuclearBomb::new, RRBlocks.nuclearBomb.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityNukeCrate>> NUKE_CRATE = BLOCK_ENTITY_TYPES.register("nuke_crate", () -> new BlockEntityType<>(TileEntityNukeCrate::new, RRBlocks.nukeCrateBottom.get(), RRBlocks.nukeCrateTop.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OmegaObjectiveBlockEntity>> OMEGA_OBJECTIVE = BLOCK_ENTITY_TYPES.register("omega_objective", () -> new BlockEntityType<>(OmegaObjectiveBlockEntity::new, RRBlocks.omegaobj.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SigmaObjectiveBlockEntity>> SIGMA_OBJECTIVE = BLOCK_ENTITY_TYPES.register("sigma_objective", () -> new BlockEntityType<>(SigmaObjectiveBlockEntity::new, RRBlocks.sigmaobj.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityPlasmaExplosion>> PLASMA_EXPLOSION = BLOCK_ENTITY_TYPES.register("plasma_explosion", () -> new BlockEntityType<>(TileEntityPlasmaExplosion::new, RRBlocks.plasmaexplosion.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityReactive>> REACTIVE = BLOCK_ENTITY_TYPES.register("reactive", () -> new BlockEntityType<>(TileEntityReactive::new, RRBlocks.reactive.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityReactor>> REACTOR = BLOCK_ENTITY_TYPES.register("reactor", () -> new BlockEntityType<>(TileEntityReactor::new, RRBlocks.reactor.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityReciever>> RECIEVER = BLOCK_ENTITY_TYPES.register("reciever", () -> new BlockEntityType<>(TileEntityReciever::new, RRBlocks.ffreciever.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityRhodesActivator>> RHODES_ACTIVATOR = BLOCK_ENTITY_TYPES.register("rhodes_activator", () -> new BlockEntityType<>(TileEntityRhodesActivator::new, RRBlocks.rhodesactivator.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityTachyonBomb>> TACHYON_BOMB = BLOCK_ENTITY_TYPES.register("tachyon_bomb", () -> new BlockEntityType<>(TileEntityTachyonBomb::new, RRBlocks.tachyonbombblock.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityTheoreticalTsarBomba>> THEORETICAL_TSAR_BOMB = BLOCK_ENTITY_TYPES.register("theoretical_tsar_bomb", () -> new BlockEntityType<>(TileEntityTheoreticalTsarBomba::new, RRBlocks.theoreticaltsarbombablock.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityTsarBomba>> TSAR_BOMB = BLOCK_ENTITY_TYPES.register("tsar_bomb", () -> new BlockEntityType<>(TileEntityTsarBomba::new, RRBlocks.tsarbombablock.get()));

    public static void init(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}

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
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityAntimatterBomb>> ANTIMATTER_BOMB = BLOCK_ENTITY_TYPES.register("antimatter_bomb", () -> BlockEntityType.Builder.of(TileEntityAntimatterBomb::new, RRBlocks.antimatterbombblock.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityForceFieldNode>> FORCE_FIELD_NODE = BLOCK_ENTITY_TYPES.register("force_field_node", () -> BlockEntityType.Builder.of(TileEntityForceFieldNode::new, RRBlocks.forcefieldnode.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityGore>> GORE = BLOCK_ENTITY_TYPES.register("gore", () -> BlockEntityType.Builder.of(TileEntityGore::new, RRBlocks.goreblock.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityJumpBlock>> JUMP_BLOCK = BLOCK_ENTITY_TYPES.register("jump_block", () -> BlockEntityType.Builder.of(TileEntityJumpBlock::new, RRBlocks.jump.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityLaptop>> LAPTOP = BLOCK_ENTITY_TYPES.register("laptop", () -> BlockEntityType.Builder.of(TileEntityLaptop::new, RRBlocks.controller.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityLoader>> LOADER = BLOCK_ENTITY_TYPES.register("loader", () -> BlockEntityType.Builder.of(TileEntityLoader::new, RRBlocks.loader.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityMeltDown>> MELT_DOWN = BLOCK_ENTITY_TYPES.register("meltdown", () -> BlockEntityType.Builder.of(TileEntityMeltDown::new, RRBlocks.meltdown.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityNuclearBomb>> NUCLEAR_BOMB = BLOCK_ENTITY_TYPES.register("nuclear_bomb", () -> BlockEntityType.Builder.of(TileEntityNuclearBomb::new, RRBlocks.nuclearBomb.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityNukeCrate>> NUKE_CRATE = BLOCK_ENTITY_TYPES.register("nuke_crate", () -> BlockEntityType.Builder.of(TileEntityNukeCrate::new, RRBlocks.nukeCrateBottom.get(), RRBlocks.nukeCrateTop.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OmegaObjectiveBlockEntity>> OMEGA_OBJECTIVE = BLOCK_ENTITY_TYPES.register("omega_objective", () -> BlockEntityType.Builder.of(OmegaObjectiveBlockEntity::new, RRBlocks.omegaobj.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SigmaObjectiveBlockEntity>> SIGMA_OBJECTIVE = BLOCK_ENTITY_TYPES.register("sigma_objective", () -> BlockEntityType.Builder.of(SigmaObjectiveBlockEntity::new, RRBlocks.sigmaobj.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityPlasmaExplosion>> PLASMA_EXPLOSION = BLOCK_ENTITY_TYPES.register("plasma_explosion", () -> BlockEntityType.Builder.of(TileEntityPlasmaExplosion::new, RRBlocks.plasmaexplosion.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityReactive>> REACTIVE = BLOCK_ENTITY_TYPES.register("reactive", () -> BlockEntityType.Builder.of(TileEntityReactive::new, RRBlocks.reactive.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityReactor>> REACTOR = BLOCK_ENTITY_TYPES.register("reactor", () -> BlockEntityType.Builder.of(TileEntityReactor::new, RRBlocks.reactor.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityReciever>> RECIEVER = BLOCK_ENTITY_TYPES.register("reciever", () -> BlockEntityType.Builder.of(TileEntityReciever::new, RRBlocks.ffreciever.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityRhodesActivator>> RHODES_ACTIVATOR = BLOCK_ENTITY_TYPES.register("rhodes_activator", () -> BlockEntityType.Builder.of(TileEntityRhodesActivator::new, RRBlocks.rhodesactivator.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityTachyonBomb>> TACHYON_BOMB = BLOCK_ENTITY_TYPES.register("tachyon_bomb", () -> BlockEntityType.Builder.of(TileEntityTachyonBomb::new, RRBlocks.tachyonbombblock.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityTheoreticalTsarBomba>> THEORETICAL_TSAR_BOMB = BLOCK_ENTITY_TYPES.register("theoretical_tsar_bomb", () -> BlockEntityType.Builder.of(TileEntityTheoreticalTsarBomba::new, RRBlocks.theoreticaltsarbombablock.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityTsarBomba>> TSAR_BOMB = BLOCK_ENTITY_TYPES.register("tsar_bomb", () -> BlockEntityType.Builder.of(TileEntityTsarBomba::new, RRBlocks.tsarbombablock.get()).build(null));

    public static void init(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}

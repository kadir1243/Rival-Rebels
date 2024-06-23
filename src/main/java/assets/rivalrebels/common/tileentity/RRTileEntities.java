package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.common.block.RRBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

import static assets.rivalrebels.RivalRebels.MODID;

public class RRTileEntities {
    public static final BlockEntityType<TileEntityAntimatterBomb> ANTIMATTER_BOMB = BlockEntityType.Builder.create(TileEntityAntimatterBomb::new, RRBlocks.antimatterbombblock).build(null);
    public static final BlockEntityType<TileEntityForceFieldNode> FORCE_FIELD_NODE = BlockEntityType.Builder.create(TileEntityForceFieldNode::new, RRBlocks.forcefieldnode).build(null);
    public static final BlockEntityType<TileEntityGore> GORE = BlockEntityType.Builder.create(TileEntityGore::new, RRBlocks.goreblock).build(null);
    public static final BlockEntityType<TileEntityJumpBlock> JUMP_BLOCK = BlockEntityType.Builder.create(TileEntityJumpBlock::new, RRBlocks.jump).build(null);
    public static final BlockEntityType<TileEntityLaptop> LAPTOP = BlockEntityType.Builder.create(TileEntityLaptop::new, RRBlocks.controller).build(null);
    public static final BlockEntityType<TileEntityLoader> LOADER = BlockEntityType.Builder.create(TileEntityLoader::new, RRBlocks.loader).build(null);
    public static final BlockEntityType<TileEntityMeltDown> MELT_DOWN = BlockEntityType.Builder.create(TileEntityMeltDown::new, RRBlocks.meltdown).build(null);
    public static final BlockEntityType<TileEntityNuclearBomb> NUCLEAR_BOMB = BlockEntityType.Builder.create(TileEntityNuclearBomb::new, RRBlocks.nuclearBomb).build(null);
    public static final BlockEntityType<TileEntityNukeCrate> NUKE_CRATE = BlockEntityType.Builder.create(TileEntityNukeCrate::new, RRBlocks.nukeCrateBottom, RRBlocks.nukeCrateTop).build(null);
    public static final BlockEntityType<TileEntityOmegaObjective> OMEGA_OBJECTIVE = BlockEntityType.Builder.create(TileEntityOmegaObjective::new, RRBlocks.omegaobj).build(null);
    public static final BlockEntityType<TileEntityPlasmaExplosion> PLASMA_EXPLOSION = BlockEntityType.Builder.create(TileEntityPlasmaExplosion::new, RRBlocks.plasmaexplosion).build(null);
    public static final BlockEntityType<TileEntityReactive> REACTIVE = BlockEntityType.Builder.create(TileEntityReactive::new, RRBlocks.reactive).build(null);
    public static final BlockEntityType<TileEntityReactor> REACTOR = BlockEntityType.Builder.create(TileEntityReactor::new, RRBlocks.reactor).build(null);
    public static final BlockEntityType<TileEntityReciever> RECIEVER = BlockEntityType.Builder.create(TileEntityReciever::new, RRBlocks.ffreciever).build(null);
    public static final BlockEntityType<TileEntityRhodesActivator> RHODES_ACTIVATOR = BlockEntityType.Builder.create(TileEntityRhodesActivator::new, RRBlocks.rhodesactivator).build(null);
    public static final BlockEntityType<TileEntitySigmaObjective> SIGMA_OBJECTIVE = BlockEntityType.Builder.create(TileEntitySigmaObjective::new, RRBlocks.sigmaobj).build(null);
    public static final BlockEntityType<TileEntityTachyonBomb> TACHYON_BOMB = BlockEntityType.Builder.create(TileEntityTachyonBomb::new, RRBlocks.tachyonbombblock).build(null);
    public static final BlockEntityType<TileEntityTheoreticalTsarBomba> THEORETICAL_TSAR_BOMB = BlockEntityType.Builder.create(TileEntityTheoreticalTsarBomba::new, RRBlocks.theoreticaltsarbombablock).build(null);
    public static final BlockEntityType<TileEntityTsarBomba> TSAR_BOMB = BlockEntityType.Builder.create(TileEntityTsarBomba::new, RRBlocks.tsarbombablock).build(null);

    public static void register() {
        BiConsumer<String, BlockEntityType<?>> consumer = (name, type) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MODID, name), type);
        consumer.accept("antimatter_bomb", ANTIMATTER_BOMB);
        consumer.accept("nuke_crate", NUKE_CRATE);
        consumer.accept("jump_block", JUMP_BLOCK);
        consumer.accept("nuclear_bomb", NUCLEAR_BOMB);
        consumer.accept("plasma_explosion", PLASMA_EXPLOSION);
        consumer.accept("reactor", REACTOR);
        consumer.accept("loader", LOADER);
        consumer.accept("omega_objective", OMEGA_OBJECTIVE);
        consumer.accept("sigma_objective", SIGMA_OBJECTIVE);
        consumer.accept("tsar_bomb", TSAR_BOMB);
        consumer.accept("force_field_node", FORCE_FIELD_NODE);
        consumer.accept("gore", GORE);
        consumer.accept("laptop", LAPTOP);
        consumer.accept("reciever", RECIEVER);
        consumer.accept("reactive", REACTIVE);
        consumer.accept("meltdown", MELT_DOWN);
        consumer.accept("rhodes_activator", RHODES_ACTIVATOR);
        consumer.accept("theoretical_tsar_bomb", THEORETICAL_TSAR_BOMB);
        consumer.accept("tachyon_bomb", TACHYON_BOMB);
    }
}

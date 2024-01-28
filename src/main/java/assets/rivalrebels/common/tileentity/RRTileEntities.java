package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.common.block.RRBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;

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

    public static void register(IForgeRegistry<BlockEntityType<?>> registry) {
        ANTIMATTER_BOMB.setRegistryName(MODID, "antimatter_bomb");
        registry.register(ANTIMATTER_BOMB);
        NUKE_CRATE.setRegistryName(MODID, "nuke_crate");
        registry.register(NUKE_CRATE);
        JUMP_BLOCK.setRegistryName(MODID, "jump_block");
        registry.register(JUMP_BLOCK);
        NUCLEAR_BOMB.setRegistryName(MODID, "nuclear_bomb");
        registry.register(NUCLEAR_BOMB);
        PLASMA_EXPLOSION.setRegistryName(MODID, "plasma_explosion");
        registry.register(PLASMA_EXPLOSION);
        REACTOR.setRegistryName(MODID, "reactor");
        registry.register(REACTOR);
        LOADER.setRegistryName(MODID, "loader");
        registry.register(LOADER);
        OMEGA_OBJECTIVE.setRegistryName(MODID, "omega_objective");
        registry.register(OMEGA_OBJECTIVE);
        SIGMA_OBJECTIVE.setRegistryName(MODID, "sigma_objective");
        registry.register(SIGMA_OBJECTIVE);
        TSAR_BOMB.setRegistryName(MODID, "tsar_bomb");
        registry.register(TSAR_BOMB);
        FORCE_FIELD_NODE.setRegistryName(MODID, "force_field_node");
        registry.register(FORCE_FIELD_NODE);
        GORE.setRegistryName(MODID, "gore");
        registry.register(GORE);
        LAPTOP.setRegistryName(MODID, "laptop");
        registry.register(LAPTOP);
        RECIEVER.setRegistryName(MODID, "reciever");
        registry.register(RECIEVER);
        REACTIVE.setRegistryName(MODID, "reactive");
        registry.register(REACTIVE);
        MELT_DOWN.setRegistryName(MODID, "meltdown");
        registry.register(MELT_DOWN);
        RHODES_ACTIVATOR.setRegistryName(MODID, "rhodes_activator");
        registry.register(RHODES_ACTIVATOR);
        THEORETICAL_TSAR_BOMB.setRegistryName(MODID, "theoretical_tsar_bomb");
        registry.register(THEORETICAL_TSAR_BOMB);
        TACHYON_BOMB.setRegistryName(MODID, "tachyon_bomb");
        registry.register(TACHYON_BOMB);
    }
}

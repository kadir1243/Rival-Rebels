package io.github.kadir1243.rivalrebels.common.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class OmegaObjectiveBlockEntity extends AbstractObjectiveBlockEntity {
    public OmegaObjectiveBlockEntity(BlockPos pos, BlockState state) {
        super(RRTileEntities.OMEGA_OBJECTIVE.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Omega Objective");
    }
}

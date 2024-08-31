package assets.rivalrebels.common.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class SigmaObjectiveBlockEntity extends AbstractObjectiveBlockEntity {
    public SigmaObjectiveBlockEntity(BlockPos pos, BlockState state) {
        super(RRTileEntities.SIGMA_OBJECTIVE, pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Sigma Objective");
    }
}

package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.Behavior;

import java.util.Map;

public class RotateToTarget extends Behavior<EntityRhodes> {
    public RotateToTarget() {
        super(Map.of());
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);
        boolean positiveOrNegative;
        if (owner.endangered && RivalRebels.round.isStarted()) {
            float dx = (float) ((owner.teamToRaid == RivalRebelsTeam.OMEGA ? RivalRebels.round.omegaData.objPos().getX() : RivalRebels.round.sigmaData.objPos().getX()) - owner.getX());
            float dz = (float) ((owner.teamToRaid == RivalRebelsTeam.OMEGA ? RivalRebels.round.omegaData.objPos().getZ() : RivalRebels.round.sigmaData.objPos().getZ()) - owner.getZ());
            float angle = ((EntityRhodes.atan2(dx, dz) - owner.bodyyaw) % 360);
            if (angle > 1) positiveOrNegative = true;
            else if (angle < -1) positiveOrNegative = false;
            else return;
        } else {
            Entity t = owner.findTarget();
            if (t == null) return;
            float dx = (float) (t.getX() - owner.getX());
            float dz = (float) (t.getZ() - owner.getZ());
            float angle = ((EntityRhodes.atan2(dx, dz) - owner.bodyyaw) % 360);
            if (angle > 1 && owner.getRandom().nextBoolean()) positiveOrNegative = true;
            else if (angle < -1 && owner.getRandom().nextBoolean()) positiveOrNegative = false;
            else return;
        }
        float movescale = owner.getScale();
        if (RRConfig.SERVER.isRhodesScaleSpeed()) movescale *= RRConfig.SERVER.getRhodesSpeedScale();
        else movescale = RRConfig.SERVER.getRhodesSpeedScale();
        float syaw = Mth.sin(owner.bodyyaw * Mth.DEG_TO_RAD);
        float cyaw = Mth.cos(owner.bodyyaw * Mth.DEG_TO_RAD);
        owner.shootAllWeapons();
        owner.bodyyaw += (1.5f * movescale) * (positiveOrNegative ? 1 : -1);
        owner.doWalkingAnimation(syaw, cyaw);
    }
}

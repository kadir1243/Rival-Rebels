package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.behavior.Behavior;

import java.util.Map;

public class RotateToTarget extends Behavior<EntityRhodes> {
    private final boolean positiveOrNegative;

    public RotateToTarget(int duration, boolean positiveOrNegative) {
        super(Map.of(), duration);
        this.positiveOrNegative = positiveOrNegative;
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);
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

package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.Map;

public class StartingBrain extends Behavior<EntityRhodes> {
    public StartingBrain() {
        super(Map.of(MemoryModuleType.UNIVERSAL_ANGER, MemoryStatus.VALUE_ABSENT), 1);
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);

        owner.rightthighpitch = EntityRhodes.approach(owner.rightthighpitch, 0);
        owner.leftthighpitch  = EntityRhodes.approach(owner.leftthighpitch,  0);
        owner.rightshinpitch  = EntityRhodes.approach(owner.rightshinpitch,  0);
        owner.leftshinpitch   = EntityRhodes.approach(owner.leftshinpitch,   0);
        owner.rightarmpitch   = EntityRhodes.approach(owner.rightarmpitch,   0);
        owner.leftarmpitch    = EntityRhodes.approach(owner.leftarmpitch,    0);
        owner.rightarmyaw     = EntityRhodes.approach(owner.rightarmyaw,     0);
        owner.leftarmyaw      = EntityRhodes.approach(owner.leftarmyaw,      0);
        owner.headpitch       = EntityRhodes.approach(owner.headpitch,       0);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, EntityRhodes entity, long gameTime) {
        return super.canStillUse(level, entity, gameTime) || entity.getBrain().isMemoryValue(MemoryModuleType.UNIVERSAL_ANGER, true);
    }

    @Override
    protected void stop(ServerLevel level, EntityRhodes entity, long gameTime) {
        super.stop(level, entity, gameTime);
        entity.playSound(RRSounds.LAPTOP2.get(), 90, 1);
    }
}

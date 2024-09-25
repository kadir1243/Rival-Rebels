package assets.rivalrebels.common.entity.brain;

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.behavior.Behavior;

import java.util.Map;

public class ShootAndRotate extends Behavior<EntityRhodes> {
    public ShootAndRotate() {
        super(Map.of(), 100);
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
        owner.setDeltaMovement(syaw * 0.5f * movescale,
            owner.getDeltaMovement().y(),
            cyaw * 0.5f * movescale);
        owner.rightthighpitch = EntityRhodes.approach(owner.rightthighpitch,-30);
        owner.leftthighpitch  = EntityRhodes.approach(owner.leftthighpitch, -30);
        owner.rightshinpitch  = EntityRhodes.approach(owner.rightshinpitch, 60);
        owner.leftshinpitch   = EntityRhodes.approach(owner.leftshinpitch,  60);

    }
}

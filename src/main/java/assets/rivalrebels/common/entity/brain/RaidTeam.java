package assets.rivalrebels.common.entity.brain;

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Map;

public class RaidTeam extends Behavior<EntityRhodes> {
    private int counter = 1;
    public RaidTeam() {
        super(Map.of(), 1);
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);
        if (owner.teamToRaid == RivalRebelsTeam.OMEGA && RivalRebels.round.omegaData.health > 0 && owner.level().getBlockState(RivalRebels.round.omegaData.objPos()).is(RRBlocks.omegaobj) || owner.teamToRaid != RivalRebelsTeam.OMEGA && (owner.teamToRaid == RivalRebelsTeam.SIGMA && RivalRebels.round.sigmaData.health > 0 && owner.level().getBlockState(RivalRebels.round.sigmaData.objPos()).is(RRBlocks.sigmaobj))) {
            owner.rightthighpitch = EntityRhodes.approach(owner.rightthighpitch, 0);
            owner.leftthighpitch = EntityRhodes.approach(owner.leftthighpitch, 0);
            owner.rightshinpitch = EntityRhodes.approach(owner.rightshinpitch, 0);
            owner.leftshinpitch = EntityRhodes.approach(owner.leftshinpitch, 0);
            owner.rightarmpitch = EntityRhodes.approach(owner.rightarmpitch, 0);
            owner.leftarmpitch = EntityRhodes.approach(owner.leftarmpitch, 0);
            owner.rightarmyaw = EntityRhodes.approach(owner.rightarmyaw, 0);
            owner.leftarmyaw = EntityRhodes.approach(owner.leftarmyaw, 0);
            owner.headpitch = EntityRhodes.approach(owner.headpitch, 0);
            owner.disableAllLasers();
            if (owner.teamToRaid == RivalRebelsTeam.OMEGA) {
                owner.setHealth(owner.getHealth() + RivalRebels.round.takeOmegaHealth(Math.min(50, RRConfig.SERVER.getRhodesHealth() - owner.getHealth())));
            }
            if (owner.teamToRaid == RivalRebelsTeam.SIGMA) {
                owner.setHealth(owner.getHealth() + RivalRebels.round.takeSigmaHealth(Math.min(50, RRConfig.SERVER.getRhodesHealth() - owner.getHealth())));
            }
            if (owner.getHealth() != RRConfig.SERVER.getRhodesHealth()) counter++;
            else {
                owner.endangered = false;
                owner.teamToRaid = RivalRebelsTeam.NONE;
                owner.getBrain().setActiveActivityIfPossible(Activity.CORE);
            }
        } else {
            owner.setHealth(0);
            owner.playSound(RRSounds.ARTILLERY_EXPLODE, 30, 1);
        }
        counter--;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, EntityRhodes entity, long gameTime) {
        return super.canStillUse(level, entity, gameTime) || counter > 0;
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return super.timedOut(gameTime) || counter < 0;
    }
}

package assets.rivalrebels.common.entity.brain;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Map;

public class IdleBrain extends Behavior<EntityRhodes> {
    private int counter = 1;
    public IdleBrain() {
        super(Map.of(MemoryModuleTypes.RHODES_AWAKEN, MemoryStatus.VALUE_PRESENT), 1);
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);
        if (owner.raidedOmegaAlready == owner.raidedSigmaAlready) owner.raidedSigmaAlready = owner.raidedOmegaAlready = false;
        Brain<EntityRhodes> brain = owner.getBrain();
        RandomSource random = owner.getRandom();
        if (owner.endangered && RivalRebels.round.isStarted()) {
            if (owner.teamToRaid == RivalRebelsTeam.NONE) {
                if (owner.raidedOmegaAlready && RivalRebels.round.sigmaData.health > 0) owner.teamToRaid = RivalRebelsTeam.SIGMA;
                else if (owner.raidedSigmaAlready && RivalRebels.round.omegaData.health > 0)
                    owner.teamToRaid = RivalRebelsTeam.OMEGA;
                else if (RivalRebels.round.omegaData.health > 0 && RivalRebels.round.sigmaData.health > 0) {
                    int o = 0;
                    int s = 0;
                    for (RivalRebelsPlayer rrp : RivalRebels.round.rrplayerlist.players()) {
                        if (rrp.rrteam == RivalRebelsTeam.OMEGA) o++;
                        else if (rrp.rrteam == RivalRebelsTeam.SIGMA) s++;
                    }
                    if (o > s) {
                        owner.teamToRaid = RivalRebelsTeam.OMEGA;
                        owner.raidedOmegaAlready = true;
                    }
                    if (o < s) {
                        owner.teamToRaid = RivalRebelsTeam.SIGMA;
                        owner.raidedSigmaAlready = true;
                    }
                    if (o == s) {
                        owner.teamToRaid = random.nextBoolean() ? RivalRebelsTeam.SIGMA : RivalRebelsTeam.OMEGA;
                        if (owner.teamToRaid == RivalRebelsTeam.OMEGA) owner.raidedOmegaAlready = true;
                        else owner.raidedSigmaAlready = true;
                    }
                }
            }
            if (owner.teamToRaid != RivalRebelsTeam.NONE) {
                float dx = (float) ((owner.teamToRaid == RivalRebelsTeam.OMEGA ? RivalRebels.round.omegaData.objPos().getX() : RivalRebels.round.sigmaData.objPos().getX()) - owner.getX());
                float dz = (float) ((owner.teamToRaid == RivalRebelsTeam.OMEGA ? RivalRebels.round.omegaData.objPos().getZ() : RivalRebels.round.sigmaData.objPos().getZ()) - owner.getZ());
                float angle = ((EntityRhodes.atan2(dx, dz) - owner.bodyyaw) % 360);
                if (angle > 1f) {
                    brain.addActivity(Activity.INVESTIGATE, 0, ImmutableList.of(new RotateToTarget((int) Mth.abs(angle), true)));
                } else if (angle < -1f) {
                    brain.addActivity(Activity.INVESTIGATE, 0, ImmutableList.of(new RotateToTarget((int) Mth.abs(angle), false)));
                } else {
                    float d = Mth.abs(dx) + Mth.abs(dz);
                    if (d < 5) {
                        owner.playSound(RRSounds.MANDELEED, 900, 1);
                        brain.setActiveActivityIfPossible(Activity.RAID);
                    } else {
                        if (d > 50 && random.nextBoolean()) {
                            brain.setActiveActivityIfPossible(Activities.SHOOT_AND_ROTATE);
                            owner.flying = 50;
                        } else {
                            owner.shootAllWeapons();
                            owner.doWalkingAnimation();
                        }
                    }
                }
                brain.setActiveActivityIfPossible(Activity.IDLE);
            }
        } else {
            Entity t = owner.findTarget();
            if (t != null) {
                float dx = (float) (t.getX() - owner.getX());
                float dz = (float) (t.getZ() - owner.getZ());
                float angle = ((EntityRhodes.atan2(dx, dz) - owner.bodyyaw) % 360);
                if (angle > 1 && random.nextBoolean()) {
                    brain.addActivity(Activity.INVESTIGATE, 0, ImmutableList.of(new RotateToTarget((int) Mth.abs(angle), true)));
                } else if (angle < -1 && random.nextBoolean()) {
                    brain.addActivity(Activity.INVESTIGATE, 0, ImmutableList.of(new RotateToTarget((int) Mth.abs(angle), false)));
                } else {
                    if (random.nextInt(20) < (owner.endangered ? 2 : 1)) {
                        brain.setActiveActivityIfPossible(Activities.SHOOT_AND_ROTATE);
                        owner.flying = 50;
                    } else {
                        owner.shootAllWeapons();
                        owner.doWalkingAnimation();
                    }
                }
                brain.setActiveActivityIfPossible(Activity.IDLE);
            } else {
                owner.rightthighpitch = EntityRhodes.approach(owner.rightthighpitch, 0);
                owner.leftthighpitch  = EntityRhodes.approach(owner.leftthighpitch, 0);
                owner.rightshinpitch  = EntityRhodes.approach(owner.rightshinpitch, 0);
                owner.leftshinpitch   = EntityRhodes.approach(owner.leftshinpitch, 0);
                owner.rightarmpitch   = EntityRhodes.approach(owner.rightarmpitch, 0);
                owner.leftarmpitch    = EntityRhodes.approach(owner.leftarmpitch, 0);
                owner.rightarmyaw     = EntityRhodes.approach(owner.rightarmyaw, 0);
                owner.leftarmyaw      = EntityRhodes.approach(owner.leftarmyaw, 0);
                owner.headpitch       = EntityRhodes.approach(owner.headpitch, 0);
                counter++;
            }
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

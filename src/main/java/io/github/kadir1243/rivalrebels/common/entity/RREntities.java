package io.github.kadir1243.rivalrebels.common.entity;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RREntities {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, RRIdentifiers.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntityAntimatterBomb>> ANTIMATTER_BOMB = create(EntityAntimatterBomb::new, "antimatter_bomb", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityAntimatterBombBlast>> ANTIMATTER_BOMB_BLAST = create(EntityAntimatterBombBlast::new, "antimatter_bomb_blast");
    public static final DeferredHolder<EntityType<?>, EntityType<EntityB2Frag>> B2FRAG = create(EntityB2Frag::new, "b2_frag", 7.5F, 7.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityB2Spirit>> B2SPIRIT = create(EntityB2Spirit::new, "b2_spirit", 30F, 4F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityB83>> B83 = create(EntityB83::new, "b83", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityB83NoShroom>> B83_NO_SHROOM = create(EntityB83NoShroom::new, "b83_no_shroom", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityBlood>> BLOOD = create(EntityBlood::new, "blood", 0.25F, 0.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityBomb>> BOMB = create(EntityBomb::new, "bomb", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityCuchillo>> CUCHILLO = create(EntityCuchillo::new, "cuchillo", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityDebris>> DEBRIS = create(EntityDebris::new, "debris", 1F, 1F, b -> b.ridingOffset(0.5F));
    public static final DeferredHolder<EntityType<?>, EntityType<EntityFlameBall>> FLAME_BALL = create(EntityFlameBall::new, "flame_ball", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityFlameBall1>> FLAME_BALL1 = create(EntityFlameBall1::new, "flame_ball1", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityFlameBall2>> FLAME_BALL2 = create(EntityFlameBall2::new, "flame_ball2", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityFlameBallGreen>> FLAME_BALL_GREEN = create(EntityFlameBallGreen::new, "flame_ball_green", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityGasGrenade>> GAS_GRENADE = create(EntityGasGrenade::new, "gas_grenade", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityGoo>> GOO = create(EntityGoo::new, "goo", 0.25F, 0.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityGore>> GORE = create(EntityGore::new, "gore", 0.25F, 0.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityHackB83>> HACK_B83 = create(EntityHackB83::new, "hack_b83", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityHotPotato>> HOT_POTATO = create(EntityHotPotato::new, "hot_potato", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityLaptop>> LAPTOP = create(EntityLaptop::new, "laptop", 1F, 0.6F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityLaserBurst>> LASER_BURST = create(EntityLaserBurst::new, "laser_burst", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityLaserLink>> LASER_LINK = create(EntityLaserLink::new, "laser_link", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityLightningLink>> LIGHTNING_LINK = create(EntityLightningLink::new, "lightning_link", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityNuclearBlast>> NUCLEAR_BLAST = create(EntityNuclearBlast::new, "nuclear_blast", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityNuke>> NUKE = create(EntityNuke::new, "nuke", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityPassiveFire>> PASSIVE_FIRE = create(EntityPassiveFire::new, "passive_fire", 0.1F, 0.1F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityPlasmoid>> PLASMOID = create(EntityPlasmoid::new, "plasmoid", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityPropulsionFX>> PROPULSION_FX = create(EntityPropulsionFX::new, "propulsion_fx", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRaytrace>> RAYTRACE = create(EntityRaytrace::new, "raytrace", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodes>> RHODES = create(EntityRhodes::new, "rhodes", 5F, 15F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesHead>> RHODES_HEAD = create(EntityRhodesHead::new, "rhodes_head", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesLeftLowerArm>> RHODES_LEFT_LOWER_ARM = create(EntityRhodesLeftLowerArm::new, "rhodes_left_lower_arm", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesLeftLowerLeg>> RHODES_LEFT_LOWER_LEG = create(EntityRhodesLeftLowerLeg::new, "rhodes_left_lower_leg", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesLeftUpperArm>> RHODES_LEFT_UPPER_ARM = create(EntityRhodesLeftUpperArm::new, "rhodes_left_upper_arm", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesLeftUpperLeg>> RHODES_LEFT_UPPER_LEG = create(EntityRhodesLeftUpperLeg::new, "rhodes_left_upper_leg", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesRightLowerArm>> RHODES_RIGHT_LOWER_ARM = create(EntityRhodesRightLowerArm::new, "rhodes_right_lower_arm", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesRightLowerLeg>> RHODES_RIGHT_LOWER_LEG = create(EntityRhodesRightLowerLeg::new, "rhodes_right_lower_leg", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesRightUpperArm>> RHODES_RIGHT_UPPER_ARM = create(EntityRhodesRightUpperArm::new, "rhodes_right_upper_arm", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesRightUpperLeg>> RHODES_RIGHT_UPPER_LEG = create(EntityRhodesRightUpperLeg::new, "rhodes_right_upper_leg", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRhodesTorso>> RHODES_TORSO = create(EntityRhodesTorso::new, "rhodes_torso", 4F, 2F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRocket>> ROCKET = create(EntityRocket::new, "rocket", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRoddiskLeader>> RODDISK_LEADER = create(EntityRoddiskLeader::new, "roddisk_leader", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRoddiskOfficer>> RODDISK_OFFICER = create(EntityRoddiskOfficer::new, "roddisk_officer", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRoddiskRebel>> RODDISK_REBEL = create(EntityRoddiskRebel::new, "roddisk_rebel", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRoddiskRegular>> RODDISK_REGULAR = create(EntityRoddiskRegular::new, "roddisk_regular", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRoddiskRep>> RODDISK_REP = create(EntityRoddiskRep::new, "roddisk_rep", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntitySeekB83>> SEEK_B83 = create(EntitySeekB83::new, "seek_b83", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntitySphereBlast>> SPHERE_BLAST = create(EntitySphereBlast::new, "sphere_blast");
    public static final DeferredHolder<EntityType<?>, EntityType<EntityTachyonBomb>> TACHYON_BOMB = create(EntityTachyonBomb::new, "tachyon_bomb", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityTachyonBombBlast>> TACHYON_BOMB_BLAST = create(EntityTachyonBombBlast::new, "tachyon_bomb_blast");
    public static final DeferredHolder<EntityType<?>, EntityType<EntityTheoreticalTsar>> THEORETICAL_TSAR = create(EntityTheoreticalTsar::new, "theoretical_tsar", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityTheoreticalTsarBlast>> THEORETICAL_TSAR_BLAST = create(EntityTheoreticalTsarBlast::new, "theoretical_tsar_blast");
    public static final DeferredHolder<EntityType<?>, EntityType<EntityTsar>> TSAR = create(EntityTsar::new, "tsar", 0.5F, 0.5F);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityTsarBlast>> TSAR_BLAST = create(EntityTsarBlast::new, "tsar_blast");

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> create(BiFunction<EntityType<T>, Level, T> function, String id) {
        return create(function, id, b -> {});
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>,EntityType<T>> create(BiFunction<EntityType<T>, Level, T> function, String id, float width, float height) {
        return create(function, id, width, height, t -> {});
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>,EntityType<T>> create(BiFunction<EntityType<T>, Level, T> function, String id, float width, float height, Consumer<EntityType.Builder<T>> extensions) {
        return create(function, id, b -> {
            b.sized(width, height);
            extensions.accept(b);
        });
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>,EntityType<T>> create(BiFunction<EntityType<T>, Level, T> function, String id, Consumer<EntityType.Builder<T>> extensions) {
        return ENTITY_TYPES.register(id, () -> {
            EntityType.Builder<T> builder = EntityType.Builder.of(function::apply, MobCategory.MISC).noSummon();
            extensions.accept(builder);
            return builder.build(id);
        });
    }

    public static void init(IEventBus bus) {
        ENTITY_TYPES.register(bus);
        bus.addListener(RREntities::registerAttribute);
    }

    private static void registerAttribute(EntityAttributeCreationEvent event) {
        event.put(RHODES.get(), EntityRhodes.createAttributes().build());
    }
}

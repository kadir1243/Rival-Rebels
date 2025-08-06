package io.github.kadir1243.rivalrebels.client.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.obj.ObjGeometry;
import net.neoforged.neoforge.client.model.obj.ObjLoader;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import net.neoforged.neoforge.client.model.standalone.UnbakedStandaloneModel;

public class ObjModels {
    public static final StandaloneModelKey<QuadCollection> B2_JET_FOR_RHODES_MODEL = createKey("b2_jet_for_rhodes");
    public static final StandaloneModelKey<QuadCollection> HEAD_MODEL = createKey("head");
    public static final StandaloneModelKey<QuadCollection> TORSO_MODEL = createKey("torso");
    public static final StandaloneModelKey<QuadCollection> FLAG_MODEL = createKey("flag");
    public static final StandaloneModelKey<QuadCollection> UPPER_ARM_MODEL = createKey("upper_arm_rhodes");
    public static final StandaloneModelKey<QuadCollection> LOWER_ARM_MODEL = createKey("lower_arm_rhodes");
    public static final StandaloneModelKey<QuadCollection> RHODES_FLAMETHROWER_MODEL = createKey("flamethrower_rhodes");
    public static final StandaloneModelKey<QuadCollection> RHODES_ROCKET_LAUNCHER_MODEL = createKey("rocket_launcher_rhodes");
    public static final StandaloneModelKey<QuadCollection> THIGH_MODEL = createKey("thigh");
    public static final StandaloneModelKey<QuadCollection> SHIN_MODEL = createKey("shin");
    public static final StandaloneModelKey<QuadCollection> BOOSTER_MODEL = createKey("booster");
    public static final StandaloneModelKey<QuadCollection> RHODES_FLAME_MODEL = createKey("flame_rhodes");
    public static final StandaloneModelKey<QuadCollection> RHODES_LASER_MODEL = createKey("laser_rhodes");
    public static final StandaloneModelKey<QuadCollection> FF_HEAD_MODEL = createKey("ff_head");
    public static final StandaloneModelKey<QuadCollection> FF_TORSO_MODEL = createKey("ff_torso");
    public static final StandaloneModelKey<QuadCollection> FF_UPPER_ARM_MODEL = createKey("ff_upper_arm_rhodes");
    public static final StandaloneModelKey<QuadCollection> FF_LOWER_ARM_MODEL = createKey("ff_lower_arm_rhodes");
    public static final StandaloneModelKey<QuadCollection> FF_THIGH_MODEL = createKey("ff_thigh");
    public static final StandaloneModelKey<QuadCollection> FF_SHIN_MODEL = createKey("ff_shin");
    public static final StandaloneModelKey<QuadCollection> BOMB_MODEL = createKey("bomb");
    public static final StandaloneModelKey<QuadCollection> NUKE_MODEL = createKey("nuke");
    public static final StandaloneModelKey<QuadCollection> B2_FOR_SPIRIT_MODEL = createKey("b2_for_spirit");
    public static final StandaloneModelKey<QuadCollection> SHUTTLE_MODEL = createKey("shuttle");
    public static final StandaloneModelKey<QuadCollection> TUPOLEV_MODEL = createKey("tupolev");
    public static final StandaloneModelKey<QuadCollection> BATTERY_MODEL = createKey("battery");
    public static final StandaloneModelKey<QuadCollection> PLASMA_CANNON_MODEL = createKey("plasma_cannon");
    public static final StandaloneModelKey<QuadCollection> RODA_MODEL = createKey("roda");
    public static final StandaloneModelKey<QuadCollection> TESLA_MODEL = createKey("tesla");
    public static final StandaloneModelKey<QuadCollection> DYNAMO_MODEL = createKey("dynamo");
    public static final StandaloneModelKey<QuadCollection> B83_MODEL = createKey("b83");
    public static final StandaloneModelKey<QuadCollection> B2_FRAG_SIDE_1_MODEL = createKey("b2_frag_side_1");
    public static final StandaloneModelKey<QuadCollection> B2_FRAG_SIDE_2_MODEL = createKey("b2_frag_side_2");
    public static final StandaloneModelKey<QuadCollection> ELECTRODE_MODEL = createKey("electrode");
    public static final StandaloneModelKey<QuadCollection> TUBE_MODEL = createKey("tube");
    public static final StandaloneModelKey<QuadCollection> TRAY_MODEL = createKey("tray");
    public static final StandaloneModelKey<QuadCollection> ARM_MODEL = createKey("arm");
    public static final StandaloneModelKey<QuadCollection> ADS_DRAGON_MODEL = createKey("ads_dragon");
    public static final StandaloneModelKey<QuadCollection> ASTRO_BLASTER_BODY = createKey("astro_blaster_body");

    @OnlyIn(Dist.CLIENT)
    private static StandaloneModelKey<QuadCollection> createKey(String name) {
        ResourceLocation location = RRIdentifiers.create(name + "_model");
        return new StandaloneModelKey<>(location::toString);
    }

    @OnlyIn(Dist.CLIENT)
    public static void render(QuadCollection model, VertexConsumer buffer, PoseStack pose, int color, int light, int overlay) {
        if (model == null) {
            RivalRebels.LOGGER.error("Model is null", new Throwable());
            return;
        }
        for (BakedQuad quad : model.getAll()) {
            buffer.putBulkData(pose.last(), quad, ARGB.red(color), ARGB.green(color), ARGB.blue(light), ARGB.alpha(color), light, overlay);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(ModelEvent.RegisterStandalone event) {
        event.register(B2_JET_FOR_RHODES_MODEL, getModelFromObj("s"));
        event.register(HEAD_MODEL, getModelFromObj("rhodes/head"));
        event.register(TORSO_MODEL, getModelFromObj("rhodes/torso"));
        event.register(FLAG_MODEL, getModelFromObj("rhodes/flag"));
        event.register(UPPER_ARM_MODEL, getModelFromObj("rhodes/upperarm"));
        event.register(LOWER_ARM_MODEL, getModelFromObj("rhodes/lowerarm"));
        event.register(RHODES_FLAMETHROWER_MODEL, getModelFromObj("rhodes/flamethrower"));
        event.register(RHODES_ROCKET_LAUNCHER_MODEL, getModelFromObj("rhodes/rocketlauncher"));
        event.register(THIGH_MODEL, getModelFromObj("rhodes/thigh"));
        event.register(SHIN_MODEL, getModelFromObj("rhodes/shin"));
        event.register(BOOSTER_MODEL, getModelFromObj("booster"));
        event.register(RHODES_FLAME_MODEL, getModelFromObj("rhodes/flame"));
        event.register(RHODES_LASER_MODEL, getModelFromObj("rhodes/laser"));
        event.register(FF_HEAD_MODEL, getModelFromObj("rhodes/ffhead"));
        event.register(FF_TORSO_MODEL, getModelFromObj("rhodes/fftorso"));
        event.register(FF_UPPER_ARM_MODEL, getModelFromObj("rhodes/ffupperarm"));
        event.register(FF_LOWER_ARM_MODEL, getModelFromObj("rhodes/fflowerarm"));
        event.register(FF_THIGH_MODEL, getModelFromObj("rhodes/ffthigh"));
        event.register(FF_SHIN_MODEL, getModelFromObj("rhodes/ffshin"));
        event.register(BOMB_MODEL, getModelFromObj("t"));
        event.register(NUKE_MODEL, getModelFromObj("wacknuke"));
        event.register(B2_FOR_SPIRIT_MODEL, getModelFromObj("d"));
        event.register(SHUTTLE_MODEL, getModelFromObj("shuttle"));
        event.register(TUPOLEV_MODEL, getModelFromObj("tupolev"));
        event.register(BATTERY_MODEL, getModelFromObj("k"));
        event.register(PLASMA_CANNON_MODEL, getModelFromObj("m"));
        event.register(RODA_MODEL, getModelFromObj("e"));
        event.register(TESLA_MODEL, getModelFromObj("i"));
        event.register(DYNAMO_MODEL, getModelFromObj("j"));
        event.register(B83_MODEL, getModelFromObj("c"));
        event.register(B2_FRAG_SIDE_1_MODEL, getModelFromObj("f"));
        event.register(B2_FRAG_SIDE_2_MODEL, getModelFromObj("g"));
        event.register(ELECTRODE_MODEL, getModelFromObj("a"));
        event.register(TUBE_MODEL, getModelFromObj("l"));
        event.register(TRAY_MODEL, getModelFromObj("p"));
        event.register(ARM_MODEL, getModelFromObj("q"));
        event.register(ADS_DRAGON_MODEL, getModelFromObj("r"));
        event.register(ASTRO_BLASTER_BODY, getModelFromObj("astro_blaster_body"));
    }

    @OnlyIn(Dist.CLIENT)
    private static UnbakedStandaloneModel<QuadCollection> getModelFromObj(String location) {
        ObjGeometry geometry = ObjLoader.INSTANCE.loadGeometry(new ObjGeometry.Settings(RRIdentifiers.getModelLocation(location), false, false, false, false, null));
        return new UnbakedStandaloneModel<>() {
            @Override
            public QuadCollection bake(ModelBaker baker) {
                return geometry.bake(TextureSlots.EMPTY, baker, BlockModelRotation.X0_Y0, () -> location);
            }

            @Override
            public void resolveDependencies(Resolver resolver) {
            }
        };
    }
}

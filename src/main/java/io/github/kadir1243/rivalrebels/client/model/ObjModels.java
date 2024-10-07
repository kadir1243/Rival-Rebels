package io.github.kadir1243.rivalrebels.client.model;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.objfileloader.WavefrontObject;
import io.github.kadir1243.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class ObjModels {
    public static final WavefrontObject b2jetForRhodes = getModel("s");
    public static final WavefrontObject head = getModel("rhodes/head");
    public static final WavefrontObject torso = getModel("rhodes/torso");
    public static final WavefrontObject flag = getModel("rhodes/flag");
    public static final WavefrontObject upperarm = getModel("rhodes/upperarm");
    public static final WavefrontObject lowerarm = getModel("rhodes/lowerarm");
    public static final WavefrontObject rhodes_flamethrower = getModel("rhodes/flamethrower");
    public static final WavefrontObject rhodes_rocketlauncher = getModel("rhodes/rocketlauncher");
    public static final WavefrontObject thigh = getModel("rhodes/thigh");
    public static final WavefrontObject shin = getModel("rhodes/shin");
    public static final WavefrontObject booster = getModel("booster");
    public static final WavefrontObject rhodes_flame = getModel("rhodes/flame");
    public static final WavefrontObject rhodes_laser = getModel("rhodes/laser");
    public static final WavefrontObject ffhead = getModel("rhodes/ffhead");
    public static final WavefrontObject fftorso = getModel("rhodes/fftorso");
    public static final WavefrontObject ffupperarm = getModel("rhodes/ffupperarm");
    public static final WavefrontObject fflowerarm = getModel("rhodes/fflowerarm");
    public static final WavefrontObject ffthigh = getModel("rhodes/ffthigh");
    public static final WavefrontObject ffshin = getModel("rhodes/ffshin");
    public static final WavefrontObject bomb = getModel("t");
    public static final WavefrontObject nuke = getModel("wacknuke");
    public static final WavefrontObject b2ForSpirit = getModel("d");
    public static final WavefrontObject shuttle = getModel("shuttle");
    public static final WavefrontObject tupolev = getModel("tupolev");
    public static final WavefrontObject battery = getModel("k");
    public static final WavefrontObject binoculars = getModel("b");
    public static final WavefrontObject flamethrower = getModel("n");
    public static final WavefrontObject gas = getModel("o");
    public static final WavefrontObject plasma_cannon = getModel("m");
    public static final WavefrontObject roda = getModel("e");
    public static final WavefrontObject tesla = getModel("i");
    public static final WavefrontObject dynamo = getModel("j");
    public static final WavefrontObject b83 = getModel("c");
    public static final WavefrontObject b2FragSide1 = getModel("f");
    public static final WavefrontObject b2FragSide2 = getModel("g");
    public static final WavefrontObject electrode = getModel("a");
    public static final WavefrontObject tube = getModel("l");
    public static final WavefrontObject tray = getModel("p");
    public static final WavefrontObject arm = getModel("q");
    public static final WavefrontObject adsdragon = getModel("r");
    public static final Function<ResourceLocation, RenderType> RENDER_SOLID_TRIANGLES = Util.memoize(
        resourceLocation -> {
            RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_SOLID_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setOverlayState(RenderStateShard.OVERLAY)
                .createCompositeState(true);
            return RenderType.create(RRIdentifiers.MODID + "_render_solid_triangles", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 1536, true, false, compositeState);
        }
    );
    public static final Function<ResourceLocation, RenderType> RENDER_TRANSLUCENT_TRIANGLES = Util.memoize(
        resourceLocation -> {
            RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderType.NO_CULL)
                .setLightmapState(RenderType.LIGHTMAP)
                .setOverlayState(RenderType.OVERLAY)
                .createCompositeState(true);
            return RenderType.create("entity_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 1536, true, true, compositeState);
        }
    );
    private static final Map<ResourceLocation, RenderType> RENDER_SOLID_TRIANGLES_CACHE = new ConcurrentHashMap<>();

    public static void renderSolid(WavefrontObject object, ResourceLocation texture, PoseStack pose, MultiBufferSource buffers, int color, int light, int overlay) {
        object.render(pose, buffers.getBuffer(RENDER_SOLID_TRIANGLES_CACHE.computeIfAbsent(texture, RENDER_SOLID_TRIANGLES)), color, light, overlay);
    }

    public static void renderSolid(WavefrontObject object, ResourceLocation texture, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        renderSolid(object, texture, pose, buffers, CommonColors.WHITE, light, overlay);
    }

    public static void renderNoise(WavefrontObject object, PoseStack pose, MultiBufferSource buffers, int color, int light, int overlay) {
        object.render(pose, buffers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE_TRIANGLES), color, light, overlay);
    }

    public static void renderNoise(WavefrontObject object, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        renderNoise(object, pose, buffers, CommonColors.WHITE, light, overlay);
    }

    public static WavefrontObject getModel(String modelName) {
        return WavefrontObject.loadModel(RRIdentifiers.create("models/" + modelName + ".obj"));
    }
}

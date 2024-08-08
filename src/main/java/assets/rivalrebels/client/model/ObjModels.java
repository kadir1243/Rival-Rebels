package assets.rivalrebels.client.model;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.objfileloader.WavefrontObject;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ObjModels {
    public static final WavefrontObject b2jetForRhodes = RenderHelper.getModel("s");
    public static final WavefrontObject head = RenderHelper.getModel("rhodes/head");
    public static final WavefrontObject torso = RenderHelper.getModel("rhodes/torso");
    public static final WavefrontObject flag = RenderHelper.getModel("rhodes/flag");
    public static final WavefrontObject upperarm = RenderHelper.getModel("rhodes/upperarm");
    public static final WavefrontObject lowerarm = RenderHelper.getModel("rhodes/lowerarm");
    public static final WavefrontObject rhodes_flamethrower = RenderHelper.getModel(("rhodes/flamethrower"));
    public static final WavefrontObject rhodes_rocketlauncher = RenderHelper.getModel(("rhodes/rocketlauncher"));
    public static final WavefrontObject thigh = RenderHelper.getModel(("rhodes/thigh"));
    public static final WavefrontObject shin = RenderHelper.getModel(("rhodes/shin"));
    public static final WavefrontObject booster = RenderHelper.getModel(("booster"));
    public static final WavefrontObject rhodes_flame = RenderHelper.getModel(("rhodes/flame"));
    public static final WavefrontObject rhodes_laser = RenderHelper.getModel(("rhodes/laser"));
    public static final WavefrontObject ffhead = RenderHelper.getModel(("rhodes/ffhead"));
    public static final WavefrontObject fftorso = RenderHelper.getModel(("rhodes/fftorso"));
    public static final WavefrontObject ffupperarm = RenderHelper.getModel("rhodes/ffupperarm");
    public static final WavefrontObject fflowerarm = RenderHelper.getModel("rhodes/fflowerarm");
    public static final WavefrontObject ffthigh = RenderHelper.getModel("rhodes/ffthigh");
    public static final WavefrontObject ffshin = RenderHelper.getModel("rhodes/ffshin");
    public static final WavefrontObject bomb = RenderHelper.getModel("t");
    public static final WavefrontObject nuke = RenderHelper.getModel("wacknuke");
    public static final WavefrontObject b2ForSpirit = RenderHelper.getModel("d");
    public static final WavefrontObject shuttle = RenderHelper.getModel("shuttle");
    public static final WavefrontObject tupolev = RenderHelper.getModel("tupolev");
    public static final WavefrontObject battery = RenderHelper.getModel("k");
    public static final WavefrontObject binoculars = RenderHelper.getModel("b");
    public static final WavefrontObject flamethrower = RenderHelper.getModel("n");
    public static final WavefrontObject gas = RenderHelper.getModel("o");
    public static final WavefrontObject plasma_cannon = RenderHelper.getModel("m");
    public static final WavefrontObject roda = RenderHelper.getModel("e");
    public static final WavefrontObject tesla = RenderHelper.getModel("i");
    public static final WavefrontObject dynamo = RenderHelper.getModel("j");
    public static final WavefrontObject b83 = RenderHelper.getModel("c");
    public static final WavefrontObject b2FragSide1 = RenderHelper.getModel("f");
    public static final WavefrontObject b2FragSide2 = RenderHelper.getModel("g");
    public static final WavefrontObject electrode = RenderHelper.getModel("a");
    public static final WavefrontObject tube = RenderHelper.getModel("l");
    public static final WavefrontObject tray = RenderHelper.getModel("p");
    public static final WavefrontObject arm = RenderHelper.getModel("q");
    public static final WavefrontObject adsdragon = RenderHelper.getModel("r");
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
        (resourceLocation) -> {
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
}

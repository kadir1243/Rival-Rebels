package io.github.kadir1243.rivalrebels.client.renderhelper;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.noise.RivalRebelsCellularNoise;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterNamedRenderTypesEvent;
import net.neoforged.neoforge.client.event.RegisterPictureInPictureRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class RenderTypes {
    public static final RenderPipeline COLOR_WRITE_QUAD =
        RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withColorWrite(true)
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
            .withLocation("pipeline/solid")
            .build();
    public static final RenderPipeline COLOR_WRITE_TRI =
        RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withColorWrite(true)
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
            .withLocation("pipeline/solid")
            .build();
    public static final RenderType LIGHTNING_ASTRO_BLAST = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast",
        99999,
        COLOR_WRITE_QUAD,
        RenderType.CompositeState.builder()
            .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
            .createCompositeState(false)
    );
    public static final RenderType LIGHTNING_ASTRO_BLAST_TRIANGLES = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast_triangles",
        99999,
        COLOR_WRITE_TRI,
        RenderType.CompositeState.builder()
            .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
            .createCompositeState(false)
    );
    public static final RenderType MODEL_BLAST_SPHERE_TRIANGLES = RenderType.create(
        RRIdentifiers.MODID +"_model_blast_sphere_triangles",
        1536,
        COLOR_WRITE_TRI,
        RenderType.CompositeState.builder()
            .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
            .createCompositeState(false)
    );
    public static final RenderPipeline CELLULAR_NOISE_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_solid")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS)
        .withSampler("Sampler1")
        .build();
    public static final RenderType CELLULAR_NOISE = RenderType.create(
        RRIdentifiers.MODID + "_cellular_noise",
        999,
        CELLULAR_NOISE_PIPELINE,
        RenderType.CompositeState.builder()
            .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
            .setTextureState(new RenderStateShard.EmptyTextureStateShard(() -> RenderSystem.setShaderTexture(0, RivalRebelsCellularNoise.getCurrentRandomId().getTextureView()), () -> {}))
            .createCompositeState(false)
    );
    public static final RenderPipeline LASER_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withCull(true)
        .withBlend(BlendFunction.ADDITIVE)
        .withSampler("Sampler1")
        .build();
    public static final RenderPipeline RHODES_LASER_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
        .withColorWrite(false, false)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withBlend(BlendFunction.ADDITIVE)
        .withSampler("Sampler1")
        .build();
    public static final RenderType LASER_RENDER_TYPE = RenderType.create(RRIdentifiers.MODID+"_laser_render_type", 1536, true, false, LASER_PIPELINE, RenderType.CompositeState.builder()
             .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
            .setLightmapState(RenderStateShard.LIGHTMAP)
            .setOverlayState(RenderStateShard.OVERLAY)
            .createCompositeState(true));
    public static final RenderType RHODES_LIGHTNING = RenderType.create(RRIdentifiers.MODID+"_rhodes_lightning",
        1536,
        RHODES_LASER_PIPELINE,
        RenderType.CompositeState.builder()
            .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
            .createCompositeState(false)
    );
    public static final RenderPipeline ENTITY_SOLID_TRIANGLES =
        RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withVertexFormat(DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES)
            .withLocation("pipeline/entity_solid")
            .withSampler("Sampler1")
            .build();
    public static final Function<ResourceLocation, RenderType> RENDER_SOLID_TRIANGLES = Util.memoize(
        resourceLocation -> {
            RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false))
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setOverlayState(RenderStateShard.OVERLAY)
                .createCompositeState(true);
            return RenderType.create(RRIdentifiers.MODID + "_render_solid_triangles", 1536, true, false, ENTITY_SOLID_TRIANGLES, rendertype$compositestate);
        }
    );
    public static final RenderPipeline LASER_LINK_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
        .withColorWrite(false, false)
        .withBlend(BlendFunction.ADDITIVE)
        .withDepthWrite(true)
        .withSampler("Sampler1")
        .build();
    public static final RenderType LASER_LINK_ENTITY = RenderType.create(
        RRIdentifiers.MODID +"_laser_link_entity",
        1536,
        LASER_LINK_PIPELINE,
        RenderType.CompositeState.builder()
            .createCompositeState(false)
    );
    public static final RenderPipeline ANTIMATTER_BOMB_BLAST_ENTITY_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
        .withBlend(BlendFunction.ADDITIVE)
        .withDepthWrite(true)
        .withSampler("Sampler1")
        .build();
    public static final RenderType ANTIMATTER_BOMB_BLAST_ENTITY = RenderType.create(
        RRIdentifiers.MODID +"_antimatter_bomb_blast_entity",
        1536,
        ANTIMATTER_BOMB_BLAST_ENTITY_PIPELINE,
        RenderType.CompositeState.builder()
            .createCompositeState(false)
    );
    public static final RenderPipeline LIGHTNING_LINK_PIPELINE = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.LIGHTNING)
        .withSampler("Sampler1")
        .build();
    public static final RenderType LIGHTNING_LINK = RenderType.create(
        RRIdentifiers.MODID + "_lightning_link",
        1536,
        false,
        true,
        LIGHTNING_LINK_PIPELINE,
        RenderType.CompositeState.builder()
            .createCompositeState(false)
    );
    public static void registerRenderTypes(RegisterNamedRenderTypesEvent event) {
    }
    public static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {

    }

    public static void registerPIPRenderer(RegisterPictureInPictureRenderersEvent event) {
        event.register(TrayModelPIPRenderState.class, bufferSource -> new GuiTrayModelRenderer(bufferSource, Minecraft.getInstance().getModelManager()));
    }
}

package io.github.kadir1243.rivalrebels.client.renderhelper;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterPictureInPictureRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

import java.util.Optional;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class RRRenderTypes {
    public static final RenderPipeline LIGHTNING_ASTRO_BLAST_PIPELINE =
        RenderPipeline.builder()
            .withVertexShader("core/entity")
            .withFragmentShader("core/entity")
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation("pipeline/entity_solid")
            .build();
    public static final RenderPipeline COLOR_WRITE_TRI =
        RenderPipeline.builder()
            .withVertexShader("core/entity")
            .withFragmentShader("core/entity")
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation("pipeline/entity_solid")
            .build();
    public static final RenderType LIGHTNING_ASTRO_BLAST = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast",
        RenderSetup.builder(LIGHTNING_ASTRO_BLAST_PIPELINE).bufferSize(99999).createRenderSetup()
    );
    public static final RenderType LIGHTNING_ASTRO_BLAST_TRIANGLES = RenderType.create(
        RRIdentifiers.MODID+"_lightning_astro_blast_triangles",
        RenderSetup.builder(COLOR_WRITE_TRI).bufferSize(99999).createRenderSetup()
    );
    public static final RenderType MODEL_BLAST_SPHERE_TRIANGLES = RenderType.create(
        RRIdentifiers.MODID +"_model_blast_sphere_triangles",
        RenderSetup.builder(COLOR_WRITE_TRI).bufferSize(1536).createRenderSetup()
    );
    public static final RenderPipeline CELLULAR_NOISE_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_solid")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS)
        .withSampler("Sampler1")
        .build();
    public static final RenderType CELLULAR_NOISE = RenderType.create(
        RRIdentifiers.MODID + "_cellular_noise",
        RenderSetup.builder(CELLULAR_NOISE_PIPELINE).bufferSize(999).createRenderSetup()
    );
    public static final RenderPipeline LASER_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withCull(true)
        .withColorTargetState(new ColorTargetState(BlendFunction.ADDITIVE))
        .withSampler("Sampler1")
        .build();
    public static final RenderPipeline RHODES_LASER_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
        .withColorTargetState(new ColorTargetState(Optional.of(BlendFunction.ADDITIVE), ColorTargetState.WRITE_NONE))
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
        .withSampler("Sampler1")
        .build();
    public static final RenderType LASER_RENDER_TYPE = RenderType.create(RRIdentifiers.MODID+"_laser_render_type",
        RenderSetup.builder(LASER_PIPELINE).bufferSize(1536).useLightmap().useOverlay().createRenderSetup()
    );
    public static final RenderType RHODES_LIGHTNING = RenderType.create(RRIdentifiers.MODID+"_rhodes_lightning",
        RenderSetup.builder(LASER_PIPELINE).bufferSize(1536).createRenderSetup()
    );
    public static final RenderPipeline ENTITY_SOLID_TRIANGLES =
        RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withVertexFormat(DefaultVertexFormat.ENTITY, VertexFormat.Mode.TRIANGLES)
            .withLocation("pipeline/entity_solid")
            .withSampler("Sampler1")
            .build();
    public static final Function<Identifier, RenderType> RENDER_SOLID_TRIANGLES = Util.memoize(
        resourceLocation -> {
            RenderSetup rendersetup = RenderSetup.builder(ENTITY_SOLID_TRIANGLES)
                .withTexture("Sampler0", resourceLocation)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();
            return RenderType.create(RRIdentifiers.MODID + "_render_solid_triangles", rendersetup);
        }
    );
    public static final RenderPipeline LASER_LINK_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
        .withColorTargetState(new ColorTargetState(Optional.of(BlendFunction.ADDITIVE), ColorTargetState.WRITE_NONE))
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withSampler("Sampler1")
        .build();
    public static final RenderType LASER_LINK_ENTITY = RenderType.create(
        RRIdentifiers.MODID +"_laser_link_entity",
        RenderSetup.builder(LASER_LINK_PIPELINE).createRenderSetup()
    );
    public static final RenderPipeline ANTIMATTER_BOMB_BLAST_ENTITY_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES)
        .withColorTargetState(new ColorTargetState(BlendFunction.ADDITIVE))
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withSampler("Sampler1")
        .build();
    public static final RenderType ANTIMATTER_BOMB_BLAST_ENTITY = RenderType.create(
        RRIdentifiers.MODID +"_antimatter_bomb_blast_entity",
        RenderSetup.builder(ANTIMATTER_BOMB_BLAST_ENTITY_PIPELINE).createRenderSetup()
    );
    public static final RenderPipeline LIGHTNING_LINK_PIPELINE = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        .withLocation("pipeline/entity_translucent")
        .withVertexShader("core/rendertype_lightning")
        .withFragmentShader("core/rendertype_lightning")
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
        .withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING))
        .withSampler("Sampler1")
        .build();
    public static final RenderType LIGHTNING_LINK = RenderType.create(
        RRIdentifiers.MODID + "_lightning_link",
        RenderSetup.builder(LIGHTNING_LINK_PIPELINE).createRenderSetup()
    );

    public static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {

    }

    public static void registerPIPRenderer(RegisterPictureInPictureRenderersEvent event) {
        event.register(TrayModelPIPRenderState.class, bufferSource -> new GuiTrayModelRenderer(bufferSource, Minecraft.getInstance().getModelManager()));
    }
}
